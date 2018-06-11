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

import com.intellij.application.options.ModulesComboBox;
import com.intellij.execution.ui.ConfigurationModuleSelector;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link SettingsEditor} UI panel for local-run of App Engine standard applications in Community
 * Edition.
 */
public class AppEngineLocalRunCommunityPanel
    extends SettingsEditor<AppEngineCommunityLocalServerRunConfiguration> {

  private JPanel panel;
  private JTextField textField1;
  private JTextField textField2;
  private ModulesComboBox modulesComboBox;

  @Override
  protected void resetEditorFrom(
      @NotNull AppEngineCommunityLocalServerRunConfiguration configuration) {
    modulesComboBox.setModules(configuration.getValidModules());
    modulesComboBox.setSelectedModule(configuration.getConfigurationModule().getModule());

    modulesComboBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        System.out.println("modules changed");
      }
    });
  }

  @Override
  protected void applyEditorTo(@NotNull AppEngineCommunityLocalServerRunConfiguration configuration)
      throws ConfigurationException {
    configuration.setModule(modulesComboBox.getSelectedModule());
  }

  @NotNull
  @Override
  protected JComponent createEditor() {
    return panel;
  }
}
