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
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.extex.exbib.editor.bst.model.BstModel;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class BstLaunchShortcut implements ILaunchShortcut {

    private interface IType {
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.ui.IEditorPart,
     *      java.lang.String)
     */
    @Override
    public void launch(IEditorPart editor, String mode) {

        IEditorInput input = editor.getEditorInput();
        BstModel element = (BstModel) input.getAdapter(BstModel.class);
        if (element != null) {
            searchAndLaunch(new Object[]{element}, mode);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.jface.viewers.ISelection,
     *      java.lang.String)
     */
    @Override
    public void launch(ISelection selection, String mode) {

        if (selection instanceof IStructuredSelection) {
            searchAndLaunch(((IStructuredSelection) selection).toArray(), mode);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param type
     * @param mode
     */
    protected void launch(IType type, String mode) {

        try {
            ILaunchConfiguration config = null;
            // findLaunchConfiguration(type, mode);
            if (config != null) {
                config.launch(mode, null);
            }
        } catch (CoreException e) {
            /* Handle exceptions */
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param search
     * @param mode
     */
    protected void searchAndLaunch(Object[] search, String mode) {

        IType[] types = null;
        if (search != null) {
            try {
                types = new IType[0];
                // AppletLaunchConfigurationUtils.findApplets(
                // new ProgressMonitorDialog(getShell()), search);
            } catch (Exception e) {
                /* Handle exceptions */
            }
            IType type = null;
            if (types.length == 0) {
                // MessageDialog.openInformation(
                // getShell(), "Applet Launch", "No applets found."};
                return;
                // } else if (types.length > 1) {
                // type = chooseType(types, mode);
            } else {
                type = types[0];
            }
            if (type != null) {
                launch(type, mode);
            }
        }
    }

}
