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

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.extex.exbib.editor.Activator;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
class ResoruceTreeLabelProvider extends LabelProvider {

    /**
     * The field {@code img} contains the ...
     */
    private Image imgNoLeaves;

    private Image imgIndexedNoLeaves;

    private Image imgIndexed;

    /**
     * Creates a new object.
     * 
     */
    public ResoruceTreeLabelProvider() {

        imgNoLeaves =
                Activator.getImageDescriptor("icons/folder-no-leaves.gif")
                    .createImage();
        imgIndexedNoLeaves =
                Activator.getImageDescriptor(
                    "icons/folder-indexed-no-leaves.gif").createImage();
        imgIndexed =
                Activator.getImageDescriptor("icons/folder-indexed.gif")
                    .createImage();
    }

@Override
    public void dispose() {

        if (imgNoLeaves != null) {
            imgNoLeaves.dispose();
            imgNoLeaves = null;
        }
        if (imgIndexed != null) {
            imgIndexed.dispose();
            imgIndexed = null;
        }
        if (imgIndexedNoLeaves != null) {
            imgIndexedNoLeaves.dispose();
            imgIndexedNoLeaves = null;
        }

        super.dispose();
    }

@Override
    public Image getImage(Object object) {

        if (object instanceof TreeDirectory) {
            TreeDirectory directory = (TreeDirectory) object;
            if (directory.isIndexed()) {
                if (directory.hasLeaves()) {
                    return imgIndexedNoLeaves;
                } else {
                    return imgIndexed;
                }
            } else if (directory.hasLeaves()) {
                return PlatformUI.getWorkbench().getSharedImages().getImage(
                    ISharedImages.IMG_OBJ_FOLDER);
            } else {
                return imgNoLeaves;
            }

        } else {
            return PlatformUI.getWorkbench().getSharedImages().getImage(
                ISharedImages.IMG_OBJ_FILE);
        }

    }

}
