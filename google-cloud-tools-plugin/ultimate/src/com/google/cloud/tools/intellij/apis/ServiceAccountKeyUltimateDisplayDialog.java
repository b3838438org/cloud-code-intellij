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
import com.google.common.collect.Maps;
import com.intellij.CommonBundle;
import com.intellij.openapi.project.Project;
import com.intellij.ui.BooleanTableCellEditor;
import com.intellij.ui.BooleanTableCellRenderer;
import com.intellij.ui.TableUtil;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.UIUtil;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Dialog visible only in Ultimate Edition that (1) confirms the download of the service account
 * JSON key for the Google Cloud Libraries , (2) provides information on how to set the environment
 * variables for local run and (3) allows the user to select App Engine Standard run configurations
 * to automatically update with these environment variables.
 */
public class ServiceAccountKeyUltimateDisplayDialog extends ServiceAccountKeyDisplayDialog {

  private JTable serverTable;
  private static final int CLOUD_LIBRARY_COL = 0;
  private static final int CLOUD_LIBRARY_SELECT_COL = 1;
  private static final int HORIZONTAL_GAP = 0;
  private static final int VERTICAL_GAP = 10;

  ServiceAccountKeyUltimateDisplayDialog(@Nullable Project project, String downloadPath) {
    super(project, downloadPath);
    JPanel panel = super.getExtensionPanel();

    // TODO: remove
    List<String> fakeServers = new ArrayList<>();
    fakeServers.add("first server");
    fakeServers.add("second server");
    fakeServers.add("third server");
    fakeServers.add("4th server");
    fakeServers.add("5th server");
    fakeServers.add("6th server");
    serverTable = new ServerTable(fakeServers);
    serverTable.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel label =
        new JLabel(
            GctBundle.message("cloud.apis.service.account.key.downloaded.update.server.message"));
    Font originalFont = label.getFont();
    Font boldFont = new Font(originalFont.getFontName(), Font.BOLD, originalFont.getSize());
    label.setFont(boldFont);
    label.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(HORIZONTAL_GAP,VERTICAL_GAP)));
    panel.add(serverTable);
  }

  @NotNull
  @Override
  protected Action[] createActions() {
    return new Action[] {getOKAction(), getCancelAction(), new ApplyAction()};
  }

  /** Adds the environment variables to the selected App Engine run configurations. */
  protected class ApplyAction extends DialogWrapperAction {
    private ApplyAction() {
      super(CommonBundle.getApplyButtonText());
    }

    @Override
    protected void doAction(ActionEvent e) {}
  }

  /** The custom {@link JBTable} for the table of existing Google App Engine run configurations. */
  private static final class ServerTable extends JBTable {

    ServerTable(List<String> libraries) {
      super(new ServerTableModel(libraries));
      setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      setDefaultRenderer(String.class, new DefaultTableCellRenderer());
      setDefaultRenderer(Boolean.class, new BooleanTableCellRenderer());
      setDefaultEditor(Boolean.class, new BooleanTableCellEditor());
      TableUtil.setupCheckboxColumn(getColumnModel().getColumn(1));
    }

    /** See {@link com.intellij.ide.plugins.PluginTable#paint(Graphics)} for reasoning. */
    @Override
    public void paint(@NotNull Graphics g) {
      super.paint(g);
      UIUtil.fixOSXEditorBackground(this);
    }
  }

  // TODO: refactor
  private static final class ServerTableModel implements TableModel {
    private final SortedMap<String, Boolean> serverMap = new TreeMap<>();

    public ServerTableModel(List<String> servers) {
      serverMap.putAll(Maps.toMap(servers, lib -> true));
    }

    @Override
    public int getRowCount() {
      return serverMap.size();
    }

    @Override
    public int getColumnCount() {
      return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
      return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      if (columnIndex == CLOUD_LIBRARY_COL) {
        return String.class;
      }
      return Boolean.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return columnIndex == CLOUD_LIBRARY_SELECT_COL;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      if (columnIndex == CLOUD_LIBRARY_COL) {
        return serverMap.keySet().toArray()[rowIndex];
      }
      return serverMap.values().toArray()[rowIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      if (columnIndex == CLOUD_LIBRARY_COL) {
        throw new UnsupportedOperationException("The first column is immutable.");
      }

      String key = (String) serverMap.keySet().toArray()[rowIndex];
      serverMap.put(key, (Boolean) aValue);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {}

    @Override
    public void removeTableModelListener(TableModelListener l) {}
  }
}
