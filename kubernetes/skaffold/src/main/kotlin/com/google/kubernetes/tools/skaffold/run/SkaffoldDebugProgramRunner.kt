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

import com.google.kubernetes.tools.skaffold.run.ui.SkaffoldDevSettingsEditor
import com.intellij.debugger.engine.RemoteStateState
import com.intellij.debugger.impl.GenericDebuggerRunner
import com.intellij.execution.ExecutionManager
import com.intellij.execution.configurations.RemoteConnection
import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.executors.DefaultDebugExecutor
import com.intellij.execution.impl.ExecutionManagerImpl
import com.intellij.execution.impl.RunConfigurationLevel
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl
import com.intellij.execution.remote.RemoteConfiguration
import com.intellij.execution.runners.DefaultProgramRunner
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.ui.RunContentDescriptor

private const val SKAFFOLD_DEBUG_RUNNER_ID = "SkaffoldDebugProgramRunner"

/**
 * A program runner for executing Skaffold continuous deployment in debug mode.
 */
class SkaffoldDebugProgramRunner : DefaultProgramRunner() {

    /**
     * Debug should be enabled if this is a [SkaffoldDevConfiguration] and the executor is a debug
     * executor.
     */
    override fun canRun(executorId: String, profile: RunProfile): Boolean {
        return profile is SkaffoldDevConfiguration && executorId == DefaultDebugExecutor.EXECUTOR_ID
    }

    override fun getRunnerId(): String = SKAFFOLD_DEBUG_RUNNER_ID

    override fun doExecute(state: RunProfileState, environment: ExecutionEnvironment):
            RunContentDescriptor? {

        val remote505 = RemoteConnection(true, "127.0.0.1", "5005", false)
        val runnerAndConfig = RunnerAndConfigurationSettingsImpl(
                (environment.runnerAndConfigurationSettings as RunnerAndConfigurationSettingsImpl).manager,
                RemoteConfiguration(environment.project, SkaffoldDevConfigurationFactory(SkaffoldRunConfigurationType())),
                false,
                RunConfigurationLevel.WORKSPACE
        )
        runnerAndConfig.name = "debug: image-rec:5005"
        val newEnv1 = ExecutionEnvironment(
                environment.executor,
                environment.runner,
                runnerAndConfig,
                environment.project
        )
        val gen = object : GenericDebuggerRunner() {
            override fun doExecute(
                    state: RunProfileState,
                    environment: ExecutionEnvironment
            ): RunContentDescriptor? {
                return attachVirtualMachine(
                        RemoteStateState(environment.project, remote505),
                        newEnv1,
                        remote505,
                        true
                )
            }
        }
        gen.execute(newEnv1)

        state as SkaffoldCommandLineState

        state.killCallack = {
            ExecutionManagerImpl.stopProcess(newEnv1.contentToReuse)
            ExecutionManager.getInstance(environment.project).contentManager.removeRunContent(newEnv1.executor, newEnv1.contentToReuse!!)
        }

        return super.doExecute(
                state,
                environment
        )
    }
}
