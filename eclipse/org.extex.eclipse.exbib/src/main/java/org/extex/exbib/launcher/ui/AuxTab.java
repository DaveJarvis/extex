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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class AuxTab extends AbstractLaunchConfigurationTab {

    /**
     * The constant <tt>HFILL</tt> contains the specification for an
     * horizontally fillable element.
     */
    private static final GridData HFILL =
            new GridData(GridData.FILL_HORIZONTAL);

    /**
     * The field <tt>style</tt> contains the ...
     */
    private Text style;

    /**
     * The field <tt>cites</tt> contains the list of cites.
     */
    private List cites;

    /**
     * The field <tt>allCheckButton</tt> contains the ...
     */
    private Button allCheckButton;

    /**
     * The field <tt>inputs</tt> contains the ...
     */
    private List inputs;

    /**
     * The field <tt>BST_LAUNCH_STYLE</tt> contains the ...
     */
    private static final String BST_LAUNCH_STYLE = "bst.launch.style";

    /**
     * Creates a new object.
     * 
     */
    public AuxTab() {

        // TODO gene: AuxTab constructor unimplemented
    }

    private void createCites(Composite page) {

        Group group = new Group(page, SWT.NONE);
        group.setText("Citations");
        group.setLayout(new GridLayout(2, false));
        group.setLayoutData(HFILL);

        allCheckButton = createCheckButton(group, "All");
        new Label(group, SWT.NONE);

        cites = new List(group, SWT.BORDER);
        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        data.verticalSpan = 5;
        cites.setLayoutData(data);

        Button b;
        b = createPushButton(group, "New", null);
        b.setLayoutData(HFILL);
        b = createPushButton(group, "Edit", null);
        b.setLayoutData(HFILL);
        b = createPushButton(group, "Delete", null);
        b.setLayoutData(HFILL);
        b = createPushButton(group, "Up", null);
        b.setLayoutData(HFILL);
        b = createPushButton(group, "Down", null);
        b.setLayoutData(HFILL);
    }

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

        createStyle(page);
        createInputs(page);
        createCites(page);

        setControl(page);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param page
     */
    private void createInputs(Composite page) {

        Group group = new Group(page, SWT.NONE);
        group.setText("Databases");
        group.setLayout(new GridLayout(2, false));
        group.setLayoutData(HFILL);

        inputs = new List(group, SWT.BORDER);
        GridData data = new GridData();
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        data.verticalSpan = 5;
        inputs.setLayoutData(data);

        createPushButton(group, "New", null);
        createPushButton(group, "Edit", null);
        createPushButton(group, "Delete", null);
        createPushButton(group, "Up", null);
        createPushButton(group, "Down", null);
    }

    private void createStyle(Composite page) {

        Group group = new Group(page, SWT.NONE);
        group.setText("Style");
        group.setLayout(new GridLayout(2, false));
        group.setLayoutData(HFILL);

        new Label(group, SWT.NONE).setText("Style");
        style = new Text(group, SWT.BORDER);
        style.setLayoutData(HFILL);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
     */
    @Override
    public String getName() {

        return "Aux";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
     */
    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {

        try {
            style.setText(configuration.getAttribute(BST_LAUNCH_STYLE, ""));
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

        configuration.setAttribute(BST_LAUNCH_STYLE, style.getText());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
     */
    @Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {

        configuration.setAttribute(BST_LAUNCH_STYLE, "");
    }

}
