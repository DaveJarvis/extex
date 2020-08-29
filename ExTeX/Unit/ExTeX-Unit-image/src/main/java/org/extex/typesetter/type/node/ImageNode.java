/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.node;

import org.extex.interpreter.type.image.Image;
import org.extex.typesetter.type.Node;

/**
 * This node contains an image which should be passed to the back-end driver.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4399 $
 */
@SuppressWarnings("unused")
public class ImageNode extends WhatsItNode implements Node {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field <tt>image</tt> contains the image to pass to the back-end
     * driver.
     */
    private final Image image;

    /**
     * Creates a new object.
     *
     * @param theImage the image to pass to the backend driver
     */
    public ImageNode(Image theImage) {

        this.image = theImage;
    }

    /**
     * Getter for image.
     *
     * @return the image.
     */
    public Image getImage() {

        return this.image;
    }

}
