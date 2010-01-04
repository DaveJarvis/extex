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

package org.extex.exbib.editor.bst.outline;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.extex.exbib.editor.bst.BstEditor;
import org.extex.exbib.editor.bst.model.BstModel;
import org.extex.exbib.editor.bst.model.BstModelNode;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class BstEditorOutline extends ContentOutlinePage {

    /**
     * The field <tt>contentProvider</tt> contains the content provider.
     */
    private ITreeContentProvider contentProvider = new ITreeContentProvider() {

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        @Override
        public void dispose() {

        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
         */
        @Override
        public Object[] getChildren(Object input) {

            if (input instanceof BstModel) {
                return ((BstModel) input).getElements();
            } else if (input instanceof BstModelNode) {
                return ((BstModelNode) input).getElements();
            }
            return new Object[]{};
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        @Override
        public Object[] getElements(Object input) {

            if (input instanceof BstModel) {
                return ((BstModel) input).getElements();
            } else if (input instanceof BstModelNode) {
                return ((BstModelNode) input).getElements();
            }
            return new Object[]{};
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
         */
        @Override
        public Object getParent(Object element) {

            if (element instanceof BstModelNode) {
                // return ((BstModelNode) element).getParent();
            }
            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
         */
        @Override
        public boolean hasChildren(Object input) {

            if (input instanceof BstModel) {
                return ((BstModel) input).hasChildren();
            } else if (input instanceof BstModelNode) {
                return ((BstModelNode) input).hasChildren();
            }
            return false;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

            // TODO gene: inputChanged unimplemented
            // System.err.println("--- " + oldInput + " " + newInput);
        }
    };

    /**
     * The field <tt>labelProvider</tt> contains the label provider.
     */
    private ILabelProvider labelProvider = new LabelProvider() {

        /**
         * The field <tt>imageMap</tt> contains the image map.
         */
        private Map<String, Image> imageMap = new HashMap<String, Image>();

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
         */
        @Override
        public void dispose() {

            for (Image image : imageMap.values()) {
                image.dispose();
            }
            super.dispose();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
         */
        @Override
        public Image getImage(Object element) {

            String name = element.getClass().getSimpleName();
            Image image = imageMap.get(name);
            if (image != null) {
                return image;
            }
            ImageDescriptor desc =
                    ((BstModelNode) element).getImageDescriptor();
            if (desc != null) {
                image = desc.createImage();
                imageMap.put(name, image);
            }
            return image;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
         */
        @Override
        public String getText(Object element) {

            return element.toString();
        }

    };

    /**
     * The field <tt>bstEditor</tt> contains the ...
     */
    private BstEditor bstEditor;

    /**
     * The field <tt>listener</tt> contains the ...
     */
    private ISelectionChangedListener listener =
            new ISelectionChangedListener() {

                @Override
                public void selectionChanged(SelectionChangedEvent event) {

                    ISelection selection = event.getSelection();
                    if (selection.isEmpty()
                            || !(selection instanceof StructuredSelection)
                            || bstEditor == null) {
                        return;
                    }
                    Object element =
                            ((StructuredSelection) selection).getFirstElement();
                    if (!(element instanceof BstModelNode)) {
                        return;
                    }

                    BstModelNode node = (BstModelNode) element;
                    int length = node.getLength();
                    int offset = node.getOffset();
                    Assert.isTrue(length >= 0);
                    Assert.isTrue(offset >= 0);
                    bstEditor.setHighlightRange(offset, length, true);
                }
            };

    /**
     * Creates a new object.
     * 
     * @param documentProvider
     * @param bstEditor
     */
    public BstEditorOutline(IDocumentProvider documentProvider,
            BstEditor bstEditor) {

        this.bstEditor = bstEditor;
        addSelectionChangedListener(listener);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {

        super.createControl(parent);
        TreeViewer viewer = getTreeViewer();
        viewer.setContentProvider(contentProvider);
        viewer.setLabelProvider(labelProvider);
        viewer.addSelectionChangedListener(this);
        viewer.setInput(bstEditor.getModel());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param editorInput
     */
    public void setInput(IEditorInput editorInput) {

        // TODO gene: setInput unimplemented
        // System.err.println(editorInput);
    }

}
