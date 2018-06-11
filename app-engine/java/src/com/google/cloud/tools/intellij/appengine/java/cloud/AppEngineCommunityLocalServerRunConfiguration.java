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

package com.google.cloud.tools.intellij.appengine.java.cloud;

import com.google.cloud.tools.intellij.appengine.java.util.AppEngineUtil;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.JavaRunConfigurationModule;
import com.intellij.execution.configurations.ModuleBasedConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.remoteServer.configuration.deployment.ModuleDeploymentSource;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A local-run configuration for App Engine standard application in Community Edition. */
class AppEngineCommunityLocalServerRunConfiguration
    extends ModuleBasedConfiguration<JavaRunConfigurationModule> {

  private Map<Module, ModuleDeploymentSource> moduleToDeploymentSource;

  AppEngineCommunityLocalServerRunConfiguration(
      @NotNull String name, @NotNull Project project, @NotNull ConfigurationFactory factory) {
    super(name, new JavaRunConfigurationModule(project, false), factory);
  }

  @Override
  public Collection<Module> getValidModules() {
    // todo this should not collect all modules,
    // todo can we change the icon??
    // todo is this called once on creation? yes
    // prefer Maven sources. i.e. if the same module has both gradle and maven, deploy from maven
    // it should collect the same thing that is collected by the gradle deployment support
    // or maven modules.
    //    Module[] modules = ModuleManager.getInstance(getProject()).getModules();

    // todo do this for maven too
    moduleToDeploymentSource = AppEngineUtil
        .createGradlePluginDeploymentSources(getProject())
        .stream()
        .collect(Collectors.toMap(ModuleDeploymentSource::getModule, Function.identity()));

    return moduleToDeploymentSource.keySet();
  }

  @Override
  public void checkConfiguration() throws RuntimeConfigurationException {
    // todo validate here
  }

  @NotNull
  @Override
  public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
    return new AppEngineLocalRunCommunityPanel();
  }

  @Nullable
  @Override
  public RunProfileState getState(
      @NotNull Executor executor, @NotNull ExecutionEnvironment environment)
      throws ExecutionException {
    // todo is this called when running it? YES
    return null;
  }
}
