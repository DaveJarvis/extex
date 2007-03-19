/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

import org.extex.core.dimen.FixedDimen;
import org.extex.typesetter.type.Node;

/**
 * This class provides an extension mechanism for nodes. With this class it is
 * possible to insert arbitrary nodes into the typesetter tree.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class ExtensionNode extends WhatsItNode implements Node {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field <tt>extension</tt> contains the extension object.
     */
    private Extension extension;

    /**
     * Creates a new object.
     *
     * @param theExtension the extension object
     */
    public ExtensionNode(final Extension theExtension) {

        super();
        this.extension = theExtension;
    }

    /**
     * Getter for the depth of the node.
     *
     * @return the depth
     *
     * @see org.extex.typesetter.type.node.AbstractNode#getDepth()
     */
    public FixedDimen getDepth() {

        return this.extension.getDepth();
    }

    /**
     * Getter for the height of the node.
     *
     * @return the height
     *
     * @see org.extex.typesetter.type.node.AbstractNode#getHeight()
     */
    public FixedDimen getHeight() {

        return this.extension.getHeight();
    }

    /**
     * Getter for the width of the node.
     *
     * @return the width
     *
     * @see org.extex.typesetter.type.node.AbstractNode#getWidth()
     */
    public FixedDimen getWidth() {

        return this.extension.getWidth();
    }

    /**
     * Setter for the depth of the node.
     *
     * @param depth the node depth
     *
     * @see org.extex.typesetter.type.Node#setDepth(
     *      org.extex.core.dimen.FixedDimen)
     */
    public void setDepth(final FixedDimen depth) {

        this.extension.setDepth(depth);
    }

    /**
     * Setter for the height of the node.
     *
     * @param height the new height
     *
     * @see org.extex.typesetter.type.Node#setHeight(
     *      org.extex.core.dimen.FixedDimen)
     */
    public void setHeight(final FixedDimen height) {

        this.extension.setHeight(height);
    }

    /**
     * Setter for the width of the node.
     *
     * @param width the new width
     *
     * @see org.extex.typesetter.type.Node#setWidth(
     *      org.extex.core.dimen.FixedDimen)
     */
    public void setWidth(final FixedDimen width) {

        this.extension.setWidth(width);
    }

}
