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

/**
 * A space node represents a simple space character.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4739 $
 */
public class SpaceNode extends GlueNode implements Node {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * The field <tt>DEVELOP</tt> contains the indicator that part of the code
     * is used in the development version.
     */
    private static final boolean DEVELOP = true;

    /**
     * The field <tt>size</tt> contains the width of the space to insert.
     */
    private FixedGlue size;

    /**
     * Creates a new object.
     *
     * @param theWidth the size of the space
     */
    public SpaceNode(FixedGlue theWidth) {

        super(theWidth, true);
        this.size = theWidth;
    }

    /**
     * This method puts the printable representation into the string buffer.
     * This is meant to produce a exhaustive form as it is used in tracing
     * output to the log file.
     *
     * @param sb the output string buffer
     * @param prefix the prefix string inserted at the beginning of each line
     * @param breadth the breadth of the nodes to display
     * @param depth the depth of the nodes to display
     *
     * @see org.extex.typesetter.type.Node#toString(
     *      java.lang.StringBuffer,
     *      java.lang.String,
     *      int,
     *      int)
     */
    public void toString(StringBuffer sb, String prefix,
            int breadth, int depth) {

        if (!DEVELOP || getWidth().eq(size.getLength())) {
            sb.append(getLocalizer().format("String.Format", //
                size.toString()));
        } else {
            sb.append(getLocalizer().format("String.Format.develop", //
                size.toString(), getWidth().toString()));
        }
    }

    /**
     * This method puts the printable representation into the string buffer.
     * This is meant to produce a short form only as it is used in error
     * messages to the user.
     *
     * @param sb the output string buffer
     * @param prefix the prefix string inserted at the beginning of each line
     *
     * @see org.extex.typesetter.type.Node#toText(
     *      java.lang.StringBuffer,
     *      java.lang.String)
     */
    public void toText(StringBuffer sb, String prefix) {

        sb.append(getLocalizer().format("Text.Format", size.toString()));
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
     * @see org.extex.typesetter.type.Node#visit(
     *      org.extex.typesetter.type.NodeVisitor,
     *      java.lang.Object)
     */
    public Object visit(NodeVisitor visitor, Object value)
            throws GeneralException {

        return visitor.visitSpace(this, value);
    }

}
