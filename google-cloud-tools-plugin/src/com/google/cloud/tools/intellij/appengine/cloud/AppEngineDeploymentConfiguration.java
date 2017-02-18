/*
 * Copyright 2017 Google Inc. All Rights Reserved.
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

package com.google.cloud.tools.intellij.appengine.cloud;

import com.google.cloud.tools.intellij.util.GctBundle;

import com.intellij.remoteServer.util.CloudDeploymentNameConfiguration;
import com.intellij.util.xmlb.annotations.Attribute;

import org.jetbrains.annotations.NotNull;

/**
 * The model for a App Engine based deployment configuration. This state is specific to the artifact
 * that's being deployed, as such there can be multiple per project.
 */
public class AppEngineDeploymentConfiguration
    extends CloudDeploymentNameConfiguration<AppEngineDeploymentConfiguration> {

  public static final String USER_SPECIFIED_ARTIFACT_PATH_ATTRIBUTE = "userSpecifiedArtifactPath";
  public static final String ENVIRONMENT_ATTRIBUTE = "environment";
  private String cloudProjectName;
  private String googleUsername;
  /**
   * Environment is stored here in order to restore the environment when this configuration is
   * deserialized. At deserialization time, we cannot resolve the environment via the normal means
   * of inspecting the Project's modules and artifacts because this happens before the modules have
   * been loaded.
   */
  private String environment;
  private String dockerFilePath;
  private String appYamlPath;
  private boolean userSpecifiedArtifact;
  private String userSpecifiedArtifactPath;
  private ConfigType configType;
  private boolean promote;
  private boolean stopPreviousVersion;
  private String version;

  @Attribute("cloudProjectName")
  public String getCloudProjectName() {
    return cloudProjectName;
  }

  public void setCloudProjectName(String cloudProjectName) {
    this.cloudProjectName = cloudProjectName;
  }

  @Attribute("googleUsername")
  public String getGoogleUsername() {
    return googleUsername;
  }

  public void setGoogleUsername(String googleUsername) {
    this.googleUsername = googleUsername;
  }

  @Attribute(ENVIRONMENT_ATTRIBUTE)
  public String getEnvironment() {
    return environment;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }

  @Attribute("userSpecifiedArtifact")
  public boolean isUserSpecifiedArtifact() {
    return userSpecifiedArtifact;
  }

  public void setUserSpecifiedArtifact(boolean userSpecifiedArtifact) {
    this.userSpecifiedArtifact = userSpecifiedArtifact;
  }

  @Attribute(USER_SPECIFIED_ARTIFACT_PATH_ATTRIBUTE)
  public String getUserSpecifiedArtifactPath() {
    return userSpecifiedArtifactPath;
  }

  public void setUserSpecifiedArtifactPath(String userSpecifiedArtifactPath) {
    this.userSpecifiedArtifactPath = userSpecifiedArtifactPath;
  }

  @Attribute("dockerFilePath")
  public String getDockerFilePath() {
    return dockerFilePath;
  }

  public void setDockerFilePath(String dockerFilePath) {
    this.dockerFilePath = dockerFilePath;
  }

  @Attribute("appYamlPath")
  public String getAppYamlPath() {
    return appYamlPath;
  }

  public void setAppYamlPath(String appYamlPath) {
    this.appYamlPath = appYamlPath;
  }

  @Attribute("configType")
  public ConfigType getConfigType() {
    return configType == null ? ConfigType.AUTO : configType;
  }

  public void setConfigType(@NotNull ConfigType configType) {
    this.configType = configType;
  }

  @Attribute("promote")
  public boolean isPromote() {
    return promote;
  }

  public void setPromote(boolean promote) {
    this.promote = promote;
  }

  @Attribute("stopPreviousVersion")
  public boolean isStopPreviousVersion() {
    return stopPreviousVersion;
  }

  public void setStopPreviousVersion(boolean stopPreviousVersion) {
    this.stopPreviousVersion = stopPreviousVersion;
  }

  @Attribute("version")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public boolean isAuto() {
    return getConfigType() == ConfigType.AUTO;
  }

  public enum ConfigType {
    AUTO("appengine.flex.configtype.auto.label"),
    CUSTOM("appengine.flex.configtype.custom.label");

    private final String label;

    ConfigType(String label) {
      this.label = label;
    }

    @Override
    public String toString() {
      return GctBundle.message(label);
    }
  }
}
