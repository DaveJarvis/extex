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

import org.extex.core.count.Count;
import org.extex.core.exception.GeneralException;
import org.extex.typesetter.Discardable;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;

/**
 * This class represents a Node which holds a penalty value. It is used during
 * the paragraph breaking or page breaking to control the algorithm. This node
 * should be ignored by the DocumentWriter.
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class PenaltyNode extends AbstractNode implements Node, Discardable {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code penalty} contains the penalty value of this node.
     */
    private long penalty = 0;

    /**
     * Creates a new object.
     * 
     * @param thePenalty the penalty value
*/
    public PenaltyNode(Count thePenalty) {

        this(thePenalty.getValue());
    }

    /**
     * Creates a new object.
     * 
     * @param thePenalty the penalty value
     */
    public PenaltyNode(long thePenalty) {

        this.penalty = thePenalty;
    }

    /**
     * Getter for penalty.
     * 
     * @return the penalty.
     */
    public long getPenalty() {

        return penalty;
    }

    /**
     * This method returns the printable representation. This is meant to
     * produce a exhaustive form as it is used in tracing output to the log
     * file.
     * 
     * @param sb the output string buffer
     * @param prefix the prefix string inserted at the beginning of each line
     * @param breadth the breadth
     * @param depth the depth
* @see org.extex.typesetter.type.Node#toString(java.lang.StringBuilder,
     *      java.lang.String, int, int)
     */
    @Override
    public void toString(StringBuilder sb, String prefix, int breadth, int depth) {

        sb.append(getLocalizer()
            .format("String.Format", Long.toString(penalty)));
    }

    /**
     * This method puts the printable representation into the string buffer.
     * This is meant to produce a short form only as it is used in error
     * messages to the user.
     * 
     * @param sb the output string buffer
     * @param prefix the prefix string inserted at the beginning of each line
     * 
     * @see org.extex.typesetter.type.Node#toText(StringBuilder,
     *      java.lang.String)
     */
    @Override
    public void toText(StringBuilder sb, String prefix) {

        sb.append(getLocalizer().format("Text.Format", Long.toString(penalty)));
    }

    /**
     * This method provides an entry point for the visitor pattern.
     * 
     * @param visitor the visitor to apply
     * @param value the argument for the visitor
     * 
     * @return the result of the method invocation of the visitor
     * 
     * @throws GeneralException in case of an error
     * 
     * @see org.extex.typesetter.type.Node#visit(org.extex.typesetter.type.NodeVisitor,
     *      java.lang.Object)
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object visit(NodeVisitor visitor, Object value)
            throws GeneralException {

        return visitor.visitPenalty(this, value);
    }

}
