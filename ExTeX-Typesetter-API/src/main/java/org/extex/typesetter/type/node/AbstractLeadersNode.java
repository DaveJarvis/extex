/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.typesetter.PageContext;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.OrientedNode;

/**
 * This node represents a leaders node as used by the primitives
 * <tt>\leaders</tt>, <tt>\cleaders</tt>, and <tt>\xleaders</tt>.
 * 
 * @see "<logo>TeX</logo> &ndash; The Program [149]"
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class AbstractLeadersNode extends AbstractExpandableNode
        implements
            SkipNode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>node</tt> contains the node to repeat or expand.
     */
    private Node node;

    /**
     * Creates a new object.
     * 
     * @param node the node or node list to stretch or repeat
     * @param glue the desired size
     */
    public AbstractLeadersNode(OrientedNode node, FixedGlue glue) {

        super(glue, node != null && node.isHorizontal());
        this.node = node;
        if (node == null) {
            // ignore
        } else if (node.isHorizontal()) {
            setHeight(node.getHeight());
            setDepth(node.getDepth());
        } else {
            setWidth(node.getWidth());
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.AbstractNode#atShipping(
     *      org.extex.typesetter.PageContext, org.extex.typesetter.Typesetter,
     *      org.extex.core.dimen.FixedDimen, org.extex.core.dimen.FixedDimen)
     */
    @Override
    public Node atShipping(PageContext context, Typesetter typesetter,
            FixedDimen posX, FixedDimen posY) throws GeneralException {

        Node result;
        if (node instanceof RuleNode) {
            node.setWidth(getWidth());
            node.setHeight(getHeight());
            node.setDepth(getDepth());
            result = node;
        } else if (isHorizontal()) {
            result = fillHorizontally(getWidth().getValue(), node, posX, posY);
        } else {
            result =
                    fillVertically(getHeight().getValue()
                            + getDepth().getValue(), node, posX, posY);
        }

        return result;
    }

    /**
     * Compute the horizontal list with appropriately many instances of the
     * repeat box. If not enough space is left then the leaders node itself is
     * returned as place holder. Otherwise a hlist is returned.
     * 
     * @param total the width in scaled points
     * @param n the repeated node
     * @param posX the x coordinate of the absolute position of the element on
     *        the page
     * @param posY the y coordinate of the absolute position of the element on
     *        the page
     * @return the appropriate node
     */
    protected abstract Node fillHorizontally(long total, Node n,
            FixedDimen posX, FixedDimen posY);

    /**
     * Compute the vertical list with appropriately many instances of the repeat
     * box. If not enough space is left then the leaders node itself is returned
     * as place holder. Otherwise a vlist is returned.
     * 
     * @param total the total height; i.e. height plus depth
     * @param n the repeated node
     * @param posX the x coordinate of the absolute position of the element on
     *        the page
     * @param posY the y coordinate of the absolute position of the element on
     *        the page
     * @return the appropriate node
     */
    protected abstract Node fillVertically(long total, Node n, FixedDimen posX,
            FixedDimen posY);

    /**
     * Getter for the repeated construction.
     * 
     * @return the repeated node
     */
    public Node getRepeat() {

        return node;
    }

    /**
     * This method puts the printable representation into the string buffer.
     * This is meant to produce a short form only as it is used in error
     * messages to the user.
     * 
     * @param sb the output string buffer
     * @param prefix the prefix string inserted at the beginning of each line
     * @param breadth the breadth
     * @param depth the depth
     * 
     * @see "<logo>TeX</logo> &ndash; The Program [190]"
     * @see org.extex.typesetter.type.Node#toString( java.lang.StringBuffer,
     *      java.lang.String, int, int)
     */
    @Override
    public void toString(StringBuffer sb, String prefix, int breadth, int depth) {

        sb.append(getLocalizer().format("String.Format", getSize().toString()));
        node.toString(sb, prefix, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

}
