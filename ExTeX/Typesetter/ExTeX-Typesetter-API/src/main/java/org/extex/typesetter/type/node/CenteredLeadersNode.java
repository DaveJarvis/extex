/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.OrientedNode;

/**
 * This node represents an centered leaders node as used by the primitive
 * <tt>\cleaders</tt>.
 * 
 * @see "<logo>T<span style=
 *      "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 *      >e</span>X</logo> &ndash; The Program [149]"
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class CenteredLeadersNode extends AbstractLeadersNode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param node the node or node list to stretch or repeat
     * @param glue the desired size
     */
    public CenteredLeadersNode(OrientedNode node, FixedGlue glue) {

        super(node, glue);
    }

    /**
     * The method determines how much horizontal space is left and distributes
     * it if necessary.
     * <p>
     * If there is not enough space for at least one instance then the current
     * instance is returned. In this case it acts as placeholder to contribute
     * the dimensions for the update of the current position in the document
     * writer.
     * </p>
     * <p>
     * Otherwise a hlist is created and filled with appropriately many
     * references to the repeat material. Optionally this is preceded by a kern
     * node to achieve the centering.
     * </p>
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.AbstractLeadersNode#fillHorizontally(long,
     *      org.extex.typesetter.type.Node, org.extex.core.dimen.FixedDimen,
     *      org.extex.core.dimen.FixedDimen)
     */
    @Override
    protected Node fillHorizontally(long total, Node node, FixedDimen posX,
            FixedDimen posY) {

        long w = node.getWidth().getValue();
        if (total < w || w <= 0) {
            return this;
        }

        long n = total / w;
        // the rounding error appears at the right side
        long offset = (total - n * w) / 2;
        // Possible improvement: If n==1 && offset == 0 return node?
        NodeList nl = new HorizontalListNode();

        if (offset > 0) {
            nl.add(new ImplicitKernNode(new Dimen(offset), true));
        }

        while (n-- > 0) {
            nl.add(node);
        }

        nl.setDepth(getDepth());
        nl.setHeight(getHeight());
        nl.setWidth(getWidth());
        return nl;
    }

    /**
     * The method determines how much vertical space is left and distributes it
     * if necessary.
     * <p>
     * If there is not enough space for at least one instance then the current
     * instance is returned. In this case it acts as placeholder to contribute
     * the dimensions for the update of the current position in the document
     * writer.
     * </p>
     * <p>
     * Otherwise a vlist is created and filled with appropriately many
     * references to the repeat material. Optionally this is preceded by a kern
     * node to achieve the centering.
     * </p>
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.AbstractLeadersNode#fillVertically(long,
     *      org.extex.typesetter.type.Node, org.extex.core.dimen.FixedDimen,
     *      org.extex.core.dimen.FixedDimen)
     */
    @Override
    protected Node fillVertically(long total, Node node, FixedDimen posX,
            FixedDimen posY) {

        long h = node.getHeight().getValue() + node.getDepth().getValue();
        if (total < h || h <= 0) {
            return this;
        }

        long n = total / h;
        // the rounding error appears at the bottom
        long offset = (total - n * h) / 2;
        // Possible improvement: If n==1 && offset == 0 return node?
        NodeList nl = new VerticalListNode();

        if (offset > 0) {
            nl.add(new ImplicitKernNode(new Dimen(offset), false));
        }

        while (n-- > 0) {
            nl.add(node);
        }

        nl.setDepth(getDepth());
        nl.setHeight(getHeight());
        nl.setWidth(getWidth());
        return nl;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.Node#visit(org.extex.typesetter.type.NodeVisitor,
     *      java.lang.Object)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object visit(NodeVisitor visitor, Object value)
            throws GeneralException {

        return visitor.visitCenteredLeaders(this, value);
    }

}
