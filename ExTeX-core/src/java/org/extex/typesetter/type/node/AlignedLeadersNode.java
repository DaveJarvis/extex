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

import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;

/**
 * This node represents an aligned leaders node as used by the primitive
 * <tt>\leaders</tt>.
 *
 * @see "<logo>TeX</logo> &ndash; The Program [149]"
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4739 $
 */
public class AlignedLeadersNode extends AbstractExpandableNode
        implements
            SkipNode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060402L;

    /**
     * The field <tt>node</tt> contains the node to repeat or expand.
     */
    private Node node;

    /**
     * Creates a new object.
     *
     * @param node the node or node list to stretch or repeat
     * @param glue the desired size
     * @param horizontal the indicator for the stretchability mode
     */
    public AlignedLeadersNode(final Node node, final FixedGlue glue,
            final boolean horizontal) {

        super(glue, horizontal);
        this.node = node;
    }

    /**
     * Getter for the repeated construction.
     *
     * @return the repeated node
     */
    public Node getRepeat() {

        return node;
    }

    /**
     * @see org.extex.typesetter.type.node.AbstractNode#atShipping(
     *      org.extex.interpreter.context.Context,
     *      org.extex.typesetter.Typesetter,
     *      org.extex.typesetter.type.NodeVisitor,
     *      boolean)
     */
    public Node atShipping(final Context context, final Typesetter typesetter,
            final NodeVisitor visitor, final boolean inHMode)
            throws GeneralException {

        if (node instanceof RuleNode) {
            node.setWidth(getWidth());
            node.setHeight(getHeight());
            node.setDepth(getDepth());
            return (Node) node.visit(visitor, inHMode
                    ? Boolean.TRUE
                    : Boolean.FALSE);
        }

        //TODO gene: unimplemented
        throw new RuntimeException("unimplemented");
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
     * @see org.extex.typesetter.type.Node#toString(
     *      java.lang.StringBuffer,
     *      java.lang.String,
     *      int,
     *      int)
     */
    public void toString(final StringBuffer sb, final String prefix,
            final int breadth, final int depth) {

        sb.append(getLocalizer().format("String.Format", getSize().toString()));
        node.toString(sb, prefix, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * @see org.extex.typesetter.type.Node#visit(
     *      org.extex.typesetter.type.NodeVisitor,
     *      java.lang.Object)
     */
    public Object visit(final NodeVisitor visitor, final Object value)
            throws GeneralException {

        return visitor.visitAlignedLeaders(this, value);
    }

}
