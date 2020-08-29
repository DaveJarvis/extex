/*
 * Copyright (C) 2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.launcher.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.extex.exbib.editor.Activator;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class ArgumentsTab extends AbstractLaunchConfigurationTab {

    /**
     * TODO gene: missing JavaDoc.
     */
    private interface L extends ModifyListener, SelectionListener {
    }

    /**
     * The field <tt>BST_LAUNCH_MIN_CROSSREF</tt> contains the ...
     */
    private static final String BST_LAUNCH_MIN_CROSSREF =
            "bst.launch.min.crossref";

    /**
     * The field <tt>BST_LAUNCH_TERSE</tt> contains the ...
     */
    private static final String BST_LAUNCH_TERSE = "bst.launch.terse";

    /**
     * The field <tt>BST_LAUNCH_AUX_FILE</tt> contains the ...
     */
    private static final String BST_LAUNCH_AUX_FILE = "bst.launch.aux.file";

    /**
     * The field <tt>BST_LAUNCH_EXECUTABLE</tt> contains the ...
     */
    private static final String BST_LAUNCH_EXECUTABLE = "bst.launch.executable";

    /**
     * The field <tt>BST_LAUNCH_EXTERNAL</tt> contains the ...
     */
    private static final String BST_LAUNCH_EXTERNAL = "bst.launch.external";

    /**
     * The field <tt>minCrossrefControl</tt> contains the ...
     */
    private Text minCrossrefControl;

    /**
     * The field <tt>terseControl</tt> contains the ...
     */
    private Button terseControl;

    /**
     * The field <tt>auxFileControl</tt> contains the ...
     */
    private Text auxFileControl;

    /**
     * The field <tt>exbibFlag</tt> contains the ...
     */
    private Button exbibFlag;

    /**
     * The field <tt>bibtexFlag</tt> contains the ...
     */
    private Button bibtexFlag;

    /**
     * The field <tt>bibtexControl</tt> contains the ...
     */
    private Combo bibtexControl;

    /**
     * The field <tt>HFILL</tt> contains the ...
     */
    private static final GridData HFILL =
            new GridData(GridData.FILL_HORIZONTAL);

    /**
     * The field <tt>HFILL</tt> contains the ...
     */
    private static final GridData HFILL2 =
            new GridData(GridData.FILL_HORIZONTAL);

    static {
        HFILL2.horizontalSpan = 2;
    }

    /**
     * The field <tt>modifyListener</tt> contains the ...
     */
    private L modifyListener = new L() {

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
         */
        public void modifyText(ModifyEvent evt) {

            updateLaunchConfigurationDialog();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetDefaultSelected(SelectionEvent e) {

            
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetSelected(SelectionEvent e) {

            updateLaunchConfigurationDialog();
        }
    };

    /**
     * The field <tt>image</tt> contains the ...
     */
    private Image image = null;

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {

        Composite page = new Composite(parent, SWT.NONE);
        page.setLayout(new GridLayout(1, false));
        page.setLayoutData(HFILL);
        page.setFont(parent.getFont());

        createParameterControl(page);
        createInputControl(page);
        createExecutableControl(page);

        setControl(page);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param page
     */
    private void createExecutableControl(Composite page) {

        Group group = new Group(page, SWT.NONE);
        group.setText("Executable");
        group.setLayout(new GridLayout(3, false));
        group.setLayoutData(HFILL);

        exbibFlag = createRadioButton(group, "ExBib");
        new Label(group, SWT.NONE).setLayoutData(HFILL2);

        bibtexFlag = createRadioButton(group, "External BibTeX");
        bibtexFlag.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

                
            }

            @Override
            public void widgetSelected(SelectionEvent e) {

                bibtexControl.setEnabled(bibtexFlag.getSelection());
                updateLaunchConfigurationDialog();
            }
        });

        bibtexControl = new Combo(group, SWT.BORDER);
        bibtexControl.setItems(findPrograms());
        // bibtexControl = new Text(group, SWT.BORDER);
        bibtexControl.addModifyListener(modifyListener);
        bibtexControl.setLayoutData(HFILL);
        
        // Button fileButton = new Button(group, SWT.NONE);
        // fileButton.setText("Select");
        // fileButton.addSelectionListener(new SelectionListener() {
        
        // @Override
        // public void widgetDefaultSelected(SelectionEvent e) {
        
        // 
        // }
        
        // @Override
        // public void widgetSelected(SelectionEvent e) {
        
        // FileDialog fileDialog = new FileDialog(getShell());
        // fileDialog.setFileName(bibtexControl.getText());
        // String file = fileDialog.open();
        // if (file != null) {
        // bibtexControl.setText(file);
        // }
        // }
        // });
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param page
     */
    private void createInputControl(Composite page) {

        Group group = new Group(page, SWT.FILL);
        group.setText("Input Control");
        group.setLayout(new GridLayout(3, false));
        group.setLayoutData(HFILL);

        new Label(group, SWT.NONE).setText("Aux File");
        auxFileControl = new Text(group, SWT.BORDER);
        auxFileControl.addModifyListener(modifyListener);
        auxFileControl.setLayoutData(HFILL);

        Button fileButton = new Button(group, SWT.NONE);
        fileButton.setText("Select");
        fileButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

                
            }

            @Override
            public void widgetSelected(SelectionEvent e) {

                FileDialog fileDialog = new FileDialog(getShell());
                fileDialog.setFileName(bibtexControl.getText());
                String file = fileDialog.open();
                if (file != null) {
                    auxFileControl.setText(file);
                }
            }
        });
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param page
     */
    private void createParameterControl(Composite page) {

        Group group = new Group(page, SWT.NONE);
        group.setText("Executable");
        group.setLayout(new GridLayout(2, false));
        group.setLayoutData(HFILL);

        new Label(group, SWT.NONE).setText("-min-crossrefs");
        minCrossrefControl = new Text(group, SWT.BORDER);
        minCrossrefControl.setText("2");
        minCrossrefControl.setLayoutData(HFILL);
        minCrossrefControl.addModifyListener(modifyListener);
        minCrossrefControl.addVerifyListener(new VerifyListener() {

            @Override
            public void verifyText(VerifyEvent e) {

                if ("".equals(e.text)) {

                } else if (e.text.length() > 1) {
                    e.text = e.text.replaceAll("[^0-9]", "");
                } else if (e.character == '\0') {
                    e.doit = e.text.matches("[0-9]*");
                } else if (e.character < '0' || e.character > '9') {
                    e.doit = false;
                }
            }
        });

        new Label(group, SWT.NONE).setText("-terse");
        terseControl = new Button(group, SWT.CHECK);
        terseControl.addSelectionListener(modifyListener);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#dispose()
     */
    @Override
    public void dispose() {

        if (image != null) {
            image.dispose();
        }
        super.dispose();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    private String findBibTeX() {

        String path = System.getenv("PATH");
        if (path == null) {
            return "";
        }
        String[] pathList = path.split(System.getProperty("path.separator"));
        for (String x : new String[]{"bibtex", "bibtex8", "exbib"}) {
            for (String p : pathList) {
                File file = new File(p, x);
                if (file.canExecute()) {
                    return file.getPath();
                }
            }
        }
        return "";
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    private String[] findPrograms() {

        String path = System.getenv("PATH");
        if (path == null) {
            return new String[]{};
        }
        List<String> list = new ArrayList<String>();
        String[] pathList = path.split(System.getProperty("path.separator"));
        for (String x : new String[]{"bibtex", "bibtex8", "exbib"}) {
            for (String p : pathList) {
                File file = new File(p, x);
                if (file.canExecute()) {
                    list.add(file.getPath());
                }
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#getImage()
     */
    @Override
    public Image getImage() {

        ImageDescriptor desc =
                Activator.getImageDescriptor("icons/Bib-Prog.png");
        image = desc == null ? null : desc.createImage();
        return image;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
     */
    @Override
    public String getName() {

        return "Arguments";
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {

        try {
            minCrossrefControl.setText(Integer.toString(configuration
                .getAttribute(BST_LAUNCH_MIN_CROSSREF, 2)));
            terseControl.setSelection(configuration.getAttribute(
                BST_LAUNCH_TERSE, false));
            auxFileControl.setText(configuration.getAttribute(
                BST_LAUNCH_AUX_FILE, ""));
            bibtexControl.setText(configuration.getAttribute(
                BST_LAUNCH_EXECUTABLE, ""));
            bibtexFlag.setSelection(configuration.getAttribute(
                BST_LAUNCH_EXTERNAL, false));
            exbibFlag.setSelection(!configuration.getAttribute(
                BST_LAUNCH_EXTERNAL, true));
        } catch (CoreException e) {
            // TODO gene: error handling unimplemented
            throw new RuntimeException("unimplemented");
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
     */
    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {

        String s = minCrossrefControl.getText().replaceAll("[^0-9]", "");

        configuration.setAttribute(BST_LAUNCH_MIN_CROSSREF, s.equals("")
                ? 2
                : Integer.parseInt(s));
        configuration.setAttribute(BST_LAUNCH_TERSE, terseControl
            .getSelection());
        configuration.setAttribute(BST_LAUNCH_AUX_FILE, auxFileControl
            .getText());
        configuration.setAttribute(BST_LAUNCH_EXTERNAL, bibtexFlag
            .getSelection());
        configuration.setAttribute(BST_LAUNCH_EXECUTABLE, bibtexControl
            .getText());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
     */
    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {

        configuration.setAttribute(BST_LAUNCH_MIN_CROSSREF, 2);
        configuration.setAttribute(BST_LAUNCH_TERSE, false);
        configuration.setAttribute(BST_LAUNCH_AUX_FILE, "");
        configuration.setAttribute(BST_LAUNCH_EXECUTABLE, findBibTeX());
        configuration.setAttribute(BST_LAUNCH_EXTERNAL, "");
    }

}
