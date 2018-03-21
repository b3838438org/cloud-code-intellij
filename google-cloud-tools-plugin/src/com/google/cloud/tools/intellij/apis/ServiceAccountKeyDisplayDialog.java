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

package com.google.cloud.tools.intellij.apis;

import com.google.cloud.tools.intellij.util.GctBundle;
import com.intellij.CommonBundle;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO:
// 1. do we need the cancel button?
// 2. implement print to console
/**
 * Dialog confirming the download of the service account JSON key with information on how to set the
 * credential environment variables for local run.
 */
public class ServiceAccountKeyDisplayDialog extends DialogWrapper {

  private JLabel yourServiceAccountKeyLabel;
  private JLabel envVarInfoText;
  private JBList envVarList;
  private JButton printButton;
  private JList serverList;
  private JLabel downloadPathLabel;
  private JPanel panel;
  private static final String CREDENTIAL_ENV_VAR_KEY = "GOOGLE_APPLICATION_CREDENTIALS";
  private static final String ENV_VAR_DISPLAY_FORMAT = "%s=%s";
  private Action applyAction;

  ServiceAccountKeyDisplayDialog(@Nullable Project project, String downloadPath) {
    super(project);
    init();

    setTitle(GctBundle.message("cloud.apis.service.account.key.downloaded.title"));
    downloadPathLabel.setText(downloadPath);

    envVarInfoText.setBackground(panel.getBackground());

    String credentialEnvVar =
        String.format(ENV_VAR_DISPLAY_FORMAT, CREDENTIAL_ENV_VAR_KEY, downloadPath);
    // credentialEnvVarLabel.setText(credentialEnvVar);

    DefaultListModel<String> model = new DefaultListModel();
    model.addElement(credentialEnvVar);
    envVarList.setModel(model);

    // copyToClipboardButton.addActionListener(new CopyToClipboardActionListener(credentialEnvVar));
    printButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {}
        });
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    return panel;
  }

  @NotNull
  @Override
  protected Action[] createActions() {
    applyAction =
        new DialogWrapperAction(CommonBundle.getApplyButtonText()) {
          @Override
          protected void doAction(ActionEvent e) {}
        };
    return new Action[] {getOKAction(), getCancelAction(), applyAction};
  }
}
