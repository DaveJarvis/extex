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

package org.extex.exbib.editor.bst;

import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.extex.exbib.editor.bst.model.BstModel;
import org.extex.exbib.editor.bst.outline.BstEditorOutline;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class BstEditor extends TextEditor {

    /**
     * The constant <tt>ID</tt> contains the editor id.
     */
    public static final String ID = BstEditor.class.getName();

    /**
     * The field <tt>colorManager</tt> contains the color manager.
     */
    private ColorManager colorManager;

    /**
     * The field <tt>imageManager</tt> contains the image manager.
     */
    private ImageManager imageManager;

    /**
     * The field <tt>fOutlinePage</tt> contains the outline.
     */
    private BstEditorOutline outlinePage = null;

    /**
     * The field <tt>model</tt> contains the model.
     */
    private BstModel model;

    /**
     * Creates a new object.
     * 
     */
    public BstEditor() {

        this.colorManager = new ColorManager();
        this.imageManager = new ImageManager();
        this.model = new BstModel();

        setSourceViewerConfiguration(new BstEditorConfiguration(
            this.colorManager, this.imageManager, this.model));
        setDocumentProvider(new BstDocumentProvider());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.ui.editors.text.TextEditor#dispose()
     */
    @Override
    public void dispose() {

        colorManager.dispose();
        imageManager.dispoae();
        super.dispose();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.ui.editors.text.TextEditor#getAdapter(java.lang.Class)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object getAdapter(Class required) {

        if (IContentOutlinePage.class.equals(required)) {
            if (outlinePage == null) {
                outlinePage = new BstEditorOutline(getDocumentProvider(), this);
                if (getEditorInput() != null) {
                    outlinePage.setInput(getEditorInput());
                }
            }
            return outlinePage;
        } else if (BstModel.class.equals(required)) {
            return model;
        }
        return super.getAdapter(required);
    }

    public BstModel getModel() {

        return model;
    }

}
