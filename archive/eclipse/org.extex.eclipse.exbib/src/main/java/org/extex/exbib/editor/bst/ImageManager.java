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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.extex.exbib.editor.Activator;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ImageManager {

    /**
     * The field {@code map} contains the ...
     */
    private Map<String, Image> map = new HashMap<String, Image>();

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    public void dispoae() {

        for (Image image : map.values()) {
            image.dispose();
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param path
     * @return
     */
    public Image getImage(String path) {

        Image image = map.get(path);
        if (image != null) {
            return image;
        }
        ImageDescriptor imageDescriptor = Activator.getImageDescriptor(path);
        if (imageDescriptor != null) {
            image = imageDescriptor.createImage();
            map.put(path, image);
        }
        return image;
    }
}
