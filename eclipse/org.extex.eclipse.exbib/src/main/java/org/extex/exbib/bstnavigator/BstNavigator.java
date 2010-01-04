/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.exbib.bstnavigator;

import java.io.File;

import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.extex.exbib.editor.Activator;
import org.extex.exbib.editor.bst.BstEditor;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class BstNavigator extends ViewPart {

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = BstNavigator.class.getName();

    /**
     * The field <tt>viewer</tt> contains the ...
     */
    private TreeViewer viewer;

    /**
     * The field <tt>drillDownAdapter</tt> contains the ...
     */
    private DrillDownAdapter drillDownAdapter;

    /**
     * The field <tt>refreshAction</tt> contains the ...
     */
    private Action refreshAction;

    private Action action2;

    private Action doubleClickAction;

    /**
     * The constructor.
     */
    public BstNavigator() {

    }

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    @Override
    public void createPartControl(Composite parent) {

        viewer = new TreeViewer(parent, //
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        drillDownAdapter = new DrillDownAdapter(viewer);
        viewer.setContentProvider(new ResourceTreeContentProvider());
        viewer.setLabelProvider(new ResoruceTreeLabelProvider());
        viewer.setSorter(new TreeSorter());
        viewer.setInput(getViewSite());

        makeActions();
        hookContextMenu();
        hookDoubleClickAction();
        fillActionBars();

        PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(),
            ID);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    private void fillActionBars() {

        IActionBars bars = getViewSite().getActionBars();
        IMenuManager pullDownMenu = bars.getMenuManager();
        pullDownMenu.add(refreshAction);
        pullDownMenu.add(new Separator());
        pullDownMenu.add(action2);

        IToolBarManager toolBar = bars.getToolBarManager();
        toolBar.add(refreshAction);
        toolBar.add(action2);
        toolBar.add(new Separator());

        drillDownAdapter.addNavigationActions(toolBar);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    private void hookContextMenu() {

        MenuManager menuMgr = new MenuManager("#ResourceViewPopupMenu");
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {

            public void menuAboutToShow(IMenuManager contextMenu) {

                contextMenu.add(BstNavigator.this.refreshAction);
                contextMenu.add(BstNavigator.this.action2);
                contextMenu.add(new Separator());
                BstNavigator.this.drillDownAdapter
                    .addNavigationActions(contextMenu);
                contextMenu.add(new Separator(
                    IWorkbenchActionConstants.MB_ADDITIONS));
            }
        });
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    private void hookDoubleClickAction() {

        viewer.addDoubleClickListener(new IDoubleClickListener() {

            public void doubleClick(DoubleClickEvent event) {

                doubleClickAction.run();
            }
        });
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    private void makeActions() {

        refreshAction = new Action() {

            @Override
            public void run() {

                viewer.refresh();
            }
        };
        refreshAction.setText("Refresh");
        refreshAction.setToolTipText("Reread the directory tree");
        refreshAction.setImageDescriptor(Activator
            .getImageDescriptor("icons/refresh.gif"));

        action2 = new Action() {

            @Override
            public void run() {

                showMessage("Action 2 executed");
            }
        };
        action2.setText("Action 2");
        action2.setToolTipText("Action 2 tooltip");
        action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
            .getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
        doubleClickAction = new Action() {

            @Override
            public void run() {

                ISelection selection = viewer.getSelection();

                if (selection != null
                        && selection instanceof IStructuredSelection) {
                    Object obj =
                            ((IStructuredSelection) selection)
                                .getFirstElement();
                    if (obj != null) {
                        IWorkbenchPage page =
                                BstNavigator.this.getSite().getPage();
                        try {
                            page.openEditor(new FileStoreEditorInput(
                                new LocalFile(new File(""))), BstEditor.ID);

                        } catch (PartInitException e) {
                            System.out.println(e.getStackTrace());
                        }
                    }
                }

            }
        };
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    @Override
    public void setFocus() {

        viewer.getControl().setFocus();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param message
     */
    private void showMessage(String message) {

        MessageDialog.openInformation(viewer.getControl().getShell(),
            "TeXMF Navigator", message);
    }

}
