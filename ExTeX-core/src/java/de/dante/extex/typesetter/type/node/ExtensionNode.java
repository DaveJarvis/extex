/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.typesetter.type.node;

import org.extex.interpreter.type.dimen.FixedDimen;

import de.dante.extex.typesetter.type.Node;

/**
 * This class provides an extension mechanism for nodes. With this class it is
 * possible to insert arbitrary nodes into the typesetter tree.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
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
     * @see org.extex.typesetter.type.node.AbstractNode#getDepth()
     */
    public FixedDimen getDepth() {

        return this.extension.getDepth();
    }

    /**
     * @see org.extex.typesetter.type.node.AbstractNode#getHeight()
     */
    public FixedDimen getHeight() {

        return this.extension.getHeight();
    }

    /**
     * @see org.extex.typesetter.type.node.AbstractNode#getWidth()
     */
    public FixedDimen getWidth() {

        return this.extension.getWidth();
    }

    /**
     * @see org.extex.typesetter.type.Node#setDepth(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setDepth(final FixedDimen depth) {

        this.extension.setDepth(depth);
    }

    /**
     * @see org.extex.typesetter.type.Node#setHeight(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setHeight(final FixedDimen height) {

        this.extension.setHeight(height);
    }

    /**
     * @see org.extex.typesetter.type.Node#setWidth(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setWidth(final FixedDimen width) {

        this.extension.setWidth(width);
    }

}
