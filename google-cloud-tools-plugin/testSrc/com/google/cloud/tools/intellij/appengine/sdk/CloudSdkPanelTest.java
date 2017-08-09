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

package com.google.cloud.tools.intellij.appengine.sdk;

import static com.google.cloud.tools.intellij.appengine.sdk.CloudSdkValidationResult.CLOUD_SDK_NOT_FOUND;
import static com.google.cloud.tools.intellij.appengine.sdk.CloudSdkValidationResult.CLOUD_SDK_VERSION_NOT_SUPPORTED;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.google.cloud.tools.intellij.testing.CloudToolsRule;
import com.google.cloud.tools.intellij.testing.TestService;
import com.google.cloud.tools.intellij.util.ThreadUtil;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.MoreExecutors;
import com.intellij.ide.IdeEventQueue;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.intellij.util.concurrency.AppScheduledExecutorService;
import com.intellij.util.ui.UIUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

/** Unit tests for {@link CloudSdkPanel}. */
@RunWith(JUnit4.class)
public final class CloudSdkPanelTest {

  private static final String SDK_DOWNLOAD_TEXT = "Click here to download the Cloud SDK.";
  private static final String MISSING_SDK_DIR_WARNING =
      "Cloud SDK home directory is not specified. " + SDK_DOWNLOAD_TEXT;
  private static final String INVALID_SDK_DIR_WARNING =
      "No Cloud SDK was found in the specified directory. " + SDK_DOWNLOAD_TEXT;

  @Rule public final CloudToolsRule cloudToolsRule = new CloudToolsRule(this);

  @Mock @TestService private CloudSdkService mockCloudSdkService;

  private final CloudSdkPanel panel = new CloudSdkPanel();

  @Before
  public void setUpDirectoryText() {
    // Since several test methods rely on the field's document listeners to trigger after a change,
    // this set-up guarantees the calls in the test methods will create a text change.
    invokeOnEDT(() -> panel.setCloudSdkDirectoryText("/setup/path"));
  }

  @Test
  public void getCloudSdkDirectoryText_doesReturnFieldText() {
    assertThat(panel.getCloudSdkDirectoryText()).isEqualTo("/setup/path");
  }

  @Test
  public void setCloudSdkDirectoryText_toNull_doesShowWarning() {
    invokeOnEDT(
        () -> {
          panel.setCloudSdkDirectoryText(null);

          assertWarningIsShown(MISSING_SDK_DIR_WARNING);
        });
  }

  @Test
  public void setCloudSdkDirectoryText_toEmptyString_doesShowWarning() {
    invokeOnEDT(
        () -> {
          panel.setCloudSdkDirectoryText("");

          assertWarningIsShown(MISSING_SDK_DIR_WARNING);
        });
  }

  @Test
  public void setCloudSdkDirectoryText_withCloudSdkNotFound_doesShowWarning() {
    invokeOnEDT(
        () -> {
          when(mockCloudSdkService.validateCloudSdk(anyString()))
              .thenReturn(ImmutableSet.of(CLOUD_SDK_NOT_FOUND));

          panel.setCloudSdkDirectoryText("/some/path");

          assertWarningIsShown(INVALID_SDK_DIR_WARNING);
        });
  }

  @Test
  public void setCloudSdkDirectoryText_withUnsupportedSdk_doesShowWarning() {
    invokeOnEDT(
        () -> {
          when(mockCloudSdkService.validateCloudSdk(anyString()))
              .thenReturn(ImmutableSet.of(CLOUD_SDK_VERSION_NOT_SUPPORTED));

          panel.setCloudSdkDirectoryText("/some/path");

          assertWarningIsShown(CLOUD_SDK_VERSION_NOT_SUPPORTED.getMessage());
        });
  }

  @Test
  public void
      setCloudSdkDirectoryText_withMultipleValidationResults_doesShowWarningWithCombinedText() {
    invokeOnEDT(
        () -> {
          when(mockCloudSdkService.validateCloudSdk(anyString()))
              .thenReturn(ImmutableSet.of(CLOUD_SDK_NOT_FOUND, CLOUD_SDK_VERSION_NOT_SUPPORTED));

          panel.setCloudSdkDirectoryText("/some/path");

          assertWarningIsShown(
              INVALID_SDK_DIR_WARNING + "\n" + CLOUD_SDK_VERSION_NOT_SUPPORTED.getMessage());
        });
  }

  @Test
  public void setCloudSdkDirectoryText_toValidSdk_doesNotShowWarning() {
    invokeOnEDT(
        () -> {
          String sdkPath = "/some/sdk/path";
          when(mockCloudSdkService.isValidCloudSdk(sdkPath)).thenReturn(true);

          panel.setCloudSdkDirectoryText(sdkPath);

          assertWarningIsHidden();
        });
  }

  @Test
  public void apply_withInvalidSdk_doesNotThrowException() {
    String sdkPath = "/some/sdk/path";
    when(mockCloudSdkService.validateCloudSdk(sdkPath))
        .thenReturn(ImmutableSet.of(CLOUD_SDK_NOT_FOUND));
    invokeOnEDT(() -> panel.setCloudSdkDirectoryText(sdkPath));

    panel.apply();
  }

  @Test
  public void apply_withValidSdk_doesNotThrowException() throws ConfigurationException {
    String sdkPath = "/some/sdk/path";
    when(mockCloudSdkService.validateCloudSdk(sdkPath)).thenReturn(ImmutableSet.of());
    invokeOnEDT(() -> panel.setCloudSdkDirectoryText(sdkPath));

    panel.apply();
  }

  /** Runs the given {@link Runnable} on the EDT. */
  private static void invokeOnEDT(Runnable runnable) {
    ApplicationManager.getApplication().invokeAndWait(runnable);
  }

  /**
   * Asserts the warning icon and message are shown in the {@link #panel} with the given message.
   */
  private void assertWarningIsShown(String message) {
    assertThat(panel.getWarningIcon().isVisible()).isTrue();
    assertThat(panel.getWarningMessage().isVisible()).isTrue();

    try {
      Document document = panel.getWarningMessage().getDocument();
      assertThat(document.getText(0, document.getLength())).contains(message);
    } catch (BadLocationException e) {
      throw new AssertionError(e);
    }
  }

  /** Asserts the warning icon and message are hidden. */
  private void assertWarningIsHidden() {
    assertThat(panel.getWarningIcon().isVisible()).isFalse();
    assertThat(panel.getWarningMessage().isVisible()).isFalse();
  }
}
