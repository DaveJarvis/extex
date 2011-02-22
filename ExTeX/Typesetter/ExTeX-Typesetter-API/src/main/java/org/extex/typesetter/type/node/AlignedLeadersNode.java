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
 * This node represents an aligned leaders node as used by the primitive
 * <tt>\leaders</tt>.
 * 
 * @see "<logo>T<span style=
 *      "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 *      >e</span>X</logo> &ndash; The Program [149]"
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class AlignedLeadersNode extends AbstractLeadersNode {

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
    public AlignedLeadersNode(OrientedNode node, FixedGlue glue) {

        super(node, glue);
    }

    /**
     * {@inheritDoc}
     * 
     * @see "<logo>T<span style=
     *      "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     *      >e</span>X</logo> &ndash; The Program [626]"
     * @see org.extex.typesetter.type.node.AbstractLeadersNode#fillHorizontally(long,
     *      org.extex.typesetter.type.Node, org.extex.core.dimen.FixedDimen,
     *      org.extex.core.dimen.FixedDimen)
     */
    @Override
    protected Node fillHorizontally(long total, Node node, FixedDimen posX,
            FixedDimen posY) {

        // TODO: check with TeX how it works there

        long w = node.getWidth().getValue();
        if (w <= 0) {
            return this;
        }
        long offset = posX.getValue() % w;
        if (w + offset > total) {
            return this;
        }

        long n = (total - offset) / w;
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
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.AbstractLeadersNode#fillVertically(long,
     *      org.extex.typesetter.type.Node, org.extex.core.dimen.FixedDimen,
     *      org.extex.core.dimen.FixedDimen)
     */
    @Override
    protected Node fillVertically(long total, Node node, FixedDimen posX,
            FixedDimen posY) {

        // TODO: check with TeX how it works there

        long h = node.getHeight().getValue() + node.getDepth().getValue();
        if (h <= 0) {
            return this;
        }
        long offset = posX.getValue() % h;
        if (h + offset > total) {
            return this;
        }

        long n = (total - offset) / h;
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

        return visitor.visitAlignedLeaders(this, value);
    }

}
