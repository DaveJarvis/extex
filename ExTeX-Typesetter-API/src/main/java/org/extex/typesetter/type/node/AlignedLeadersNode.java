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
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.OrientedNode;

/**
 * This node represents an aligned leaders node as used by the primitive
 * <tt>\leaders</tt>.
 * 
 * @see "<logo>TeX</logo> &ndash; The Program [149]"
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4739 $
 */
public class AlignedLeadersNode extends AbstractLeadersNode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
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
     * @see org.extex.typesetter.type.Node#visit(
     *      org.extex.typesetter.type.NodeVisitor, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public Object visit(NodeVisitor visitor, Object value)
            throws GeneralException {

        return visitor.visitAlignedLeaders(this, value);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.AbstractLeadersNode#fillHorizontally(
     *      long, org.extex.typesetter.type.Node)
     */
    protected Node fillHorizontally(long total, Node n) {

        // TODO gene: fillHorizontally unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.AbstractLeadersNode#fillVertically(
     *      long, org.extex.typesetter.type.Node)
     */
    protected Node fillVertically(long total, Node n) {

        // TODO gene: fillVertically unimplemented
        throw new RuntimeException("unimplemented");
    }

}
