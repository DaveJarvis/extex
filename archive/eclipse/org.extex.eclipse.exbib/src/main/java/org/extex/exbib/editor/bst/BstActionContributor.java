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

package org.extex.exbib.editor.bst;

import java.util.ResourceBundle;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.texteditor.BasicTextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.RetargetTextEditorAction;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class BstActionContributor extends BasicTextEditorActionContributor {

    /**
     * The field {@code contentAssist} contains the ...
     */
    private RetargetTextEditorAction contentAssist;

    /**
     * Creates a new object.
     * 
     */
    public BstActionContributor() {

        contentAssist =
                new RetargetTextEditorAction(ResourceBundle
                    .getBundle(getClass().getName().replace('.', '/')),
                    "Editor.ContentAssist.");
        contentAssist.setActionDefinitionId(
            ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
    }

@Override
    public void contributeToMenu(IMenuManager menu) {

        super.contributeToMenu(menu);
        IMenuManager editMenu =
                menu.findMenuUsingPath(IWorkbenchActionConstants.M_EDIT);
        editMenu.appendToGroup(IWorkbenchActionConstants.MB_ADDITIONS,
            contentAssist);
    }

@Override
    public void setActiveEditor(IEditorPart part) {

        super.setActiveEditor(part);
        IAction editorAction =
                getAction((ITextEditor) part,
                    ITextEditorActionConstants.CONTENT_ASSIST);
        contentAssist.setAction(editorAction);
    }

}
