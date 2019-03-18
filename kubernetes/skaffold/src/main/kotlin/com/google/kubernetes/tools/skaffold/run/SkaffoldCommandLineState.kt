/*
 * Copyright 2018 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.kubernetes.tools.skaffold.run

import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.kubernetes.tools.skaffold.SkaffoldExecutorService
import com.google.kubernetes.tools.skaffold.SkaffoldExecutorSettings
import com.google.kubernetes.tools.skaffold.SkaffoldLabels
import com.google.kubernetes.tools.skaffold.events.proto.Skaffold
import com.google.kubernetes.tools.skaffold.events.proto.SkaffoldServiceGrpc
import com.google.kubernetes.tools.skaffold.message
import com.google.protobuf.Empty
import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.process.KillableProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import java.io.File
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Runs Skaffold on command line in both "run"/"dev" modes based on the given
 * [AbstractSkaffoldRunConfiguration] and current IDE project. [startProcess] checks configuration
 * and constructs command line to launch Skaffold process. Base class manages console
 * window output and graceful process shutdown (also see [KillableProcessHandler])
 *
 * @param environment Execution environment provided by IDE
 * @param executionMode Skaffold  mode execution, i.e. DEV
 */
class SkaffoldCommandLineState(
        environment: ExecutionEnvironment,
        val executionMode: SkaffoldExecutorSettings.ExecutionMode
) : CommandLineState(environment) {
    public override fun startProcess(): ProcessHandler {

        val runConfiguration: RunConfiguration? =
                environment.runnerAndConfigurationSettings?.configuration
        val projectBaseDir: VirtualFile? = environment.project.guessProjectDir()
        // ensure the configuration is valid for execution - settings are of supported type,
        // project is valid and Skaffold file is present.
        if (runConfiguration == null || runConfiguration !is AbstractSkaffoldRunConfiguration ||
                projectBaseDir == null
        ) {
            throw ExecutionException(message("skaffold.corrupted.run.settings"))
        }
        if (runConfiguration.skaffoldConfigurationFilePath == null) {
            throw ExecutionException(message("skaffold.no.file.selected.error"))
        }

        if (!SkaffoldExecutorService.instance.isSkaffoldAvailable()) {
            throw ExecutionException(message("skaffold.not.on.system.error"))
        }

        val configFile: VirtualFile? = LocalFileSystem.getInstance()
                .findFileByPath(runConfiguration.skaffoldConfigurationFilePath!!)
        // use project dir relative location for cleaner command line representation
        val skaffoldConfigurationFilePath: String? = VfsUtilCore.getRelativeLocation(
                configFile, projectBaseDir
        )

        // custom settings for single deployment (run) mode
        val singleRunConfiguration: SkaffoldSingleRunConfiguration? =
                if (runConfiguration is SkaffoldSingleRunConfiguration) runConfiguration else null
        val skaffoldProcess = SkaffoldExecutorService.instance.executeSkaffold(
                SkaffoldExecutorSettings(
                        executionMode,
                        skaffoldConfigurationFilePath,
                        skaffoldProfile = runConfiguration.skaffoldProfile,
                        workingDirectory = File(projectBaseDir.path),
                        skaffoldLabels = SkaffoldLabels.defaultLabels,
                        tailLogsAfterDeploy = singleRunConfiguration?.tailDeploymentLogs,
                        defaultImageRepo = runConfiguration.imageRepositoryOverride
                )
        )

        Executors.newSingleThreadExecutor().submit {
            while (skaffoldProcess.process.isAlive) {
                System.out.println("skaffold is alive")
                System.out.println("will now listen to events")

                try {

                    listenEvents()
                } catch (t: Throwable) {
                    t.printStackTrace()
                }

                break
            }
        }

        return KillableProcessHandler(skaffoldProcess.process, skaffoldProcess.commandLine)
    }

    fun listenEvents() {
        try {
            val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build()

            val stub = SkaffoldServiceGrpc.newStub(channel)


            val finishLatch = CountDownLatch(1)
            val eventLog = stub.eventLog(object : StreamObserver<Skaffold.LogEntry> {
                override fun onNext(value: Skaffold.LogEntry?) {
                    System.out.println(value)
                }

                override fun onError(t: Throwable?) {
                    t?.printStackTrace()
                    System.err.println("error")
                    finishLatch.countDown()
                }

                override fun onCompleted() {
                    System.out.println("complete!!!")
                    finishLatch.countDown()
                }

            })

            eventLog.onCompleted()
            finishLatch.await(1, TimeUnit.MINUTES)


            System.out.println("Shutting down channel")
            channel.shutdown().awaitTermination(10000, TimeUnit.MILLISECONDS)
            System.out.println("Shutdown complete")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
