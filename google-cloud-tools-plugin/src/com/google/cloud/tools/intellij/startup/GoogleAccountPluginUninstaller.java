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

package com.google.cloud.tools.intellij.startup;

import com.google.cloud.tools.intellij.util.GctBundle;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.ide.plugins.PluginManagerConfigurable;
import com.intellij.ide.plugins.PluginManagerUISettings;
import com.intellij.ide.plugins.UninstallPluginAction;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;

/**
 * A plugin post startup activity which checks to ensure that the Google Cloud Tools and Account
 * plugins are running the same version. If there is a version mismatch, then a warning dialog is
 * displayed with a link to check for updates.
 */
public class GoogleAccountPluginUninstaller {

  private static final Logger LOGGER = Logger.getInstance(GoogleAccountPluginUninstaller.class);

  public void uninstallIfPresent() {

    IdeaPluginDescriptor accountPlugin =
        PluginManager.getPlugin(PluginId.findId("com.google.gct.login"));
    if (accountPlugin != null) {
      LOGGER.info("legacy google account plugin found.");
      NotificationGroup notification =
          new NotificationGroup(
              GctBundle.message("account.plugin.removal.error.title"),
              NotificationDisplayType.STICKY_BALLOON,
              true);

      notification
          .createNotification(
              GctBundle.message("account.plugin.removal.error.message"),
              NotificationType.INFORMATION)
          .notify(null);
      accountPlugin.setEnabled(false);
      PluginManagerConfigurable managerConfigurable =
          new PluginManagerConfigurable(PluginManagerUISettings.getInstance());
      UninstallPluginAction.uninstall(managerConfigurable.getOrCreatePanel(), true, accountPlugin);
      LOGGER.info(
          "legacy google account plugin has been disabled and uninstalled. This will take effect on the next IDE restart.");
    }
  }
}
