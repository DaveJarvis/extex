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
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;

/**
 * This node represents a glyph which can be broken if required.
 *
 * @see "<logo>TeX</logo> &ndash; The Program [145]"
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4739 $
 */
public class DiscretionaryNode extends AbstractNode implements Node {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field <tt>noBreak</tt> contains the Tokens to be inserted in case
     * of no line breaking at this position.
     */
    private NodeList noBreak;

    /**
     * The field <tt>postBreak</tt> contains the Tokens to be inserted at the
     * beginning of the next line in case of a line breaking at this position.
     */
    private NodeList postBreak;

    /**
     * The field <tt>preBreak</tt> contains the Tokens to be inserted at the
     * end of the line in case of a line breaking at this position.
     */
    private NodeList preBreak;

    /**
     * Creates a new object.
     *
     * @param pre the Tokens to be inserted at the end of the line in case of a
     *  line breaking at this position.
     * @param post the Tokens to be inserted at the beginning of th next line
     *  in case of a line breaking at this position.
     * @param no the Tokens to be inserted in case of no line breaking at this
     *  position.
     */
    public DiscretionaryNode(final NodeList pre, final NodeList post,
            final NodeList no) {

        super();
        preBreak = pre;
        postBreak = post;
        noBreak = no;
    }

    /**
     * Getter for noBreak.
     *
     * @return the noBreak.
     */
    public NodeList getNoBreak() {

        return noBreak;
    }

    /**
     * Getter for postBreak.
     *
     * @return the postBreak.
     */
    public NodeList getPostBreak() {

        return postBreak;
    }

    /**
     * Getter for preBreak.
     *
     * @return the preBreak.
     */
    public NodeList getPreBreak() {

        return preBreak;
    }

    /**
     * This method returns the printable representation.
     * This is meant to produce a exhaustive form as it is used in tracing
     * output to the log file.
     *
     * @param sb the target buffer
     * @param prefix the prefix for each new line
     * @param breadth the breadth
     * @param depth the depth
     *
     * @see "<logo>TeX</logo> &ndash; The Program [195]"
     * @see org.extex.typesetter.type.Node#toString(
     *      java.lang.StringBuffer,
     *      java.lang.String,
     *      int,
     *      int)
     */
    public void toString(final StringBuffer sb, final String prefix,
            final int breadth, final int depth) {

        String pre = prefix + ".";
        sb.append(getLocalizer().format("String.Format"));
        sb.append("{");
        if (preBreak != null) {
            preBreak.toString(sb, pre, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        sb.append("}{");
        if (postBreak != null) {
            postBreak.toString(sb, pre, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        sb.append("}{");
        if (noBreak != null) {
            noBreak.toString(sb, pre, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        sb.append("}");
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
    public void toText(final StringBuffer sb, final String prefix) {

        sb.append(getLocalizer().format("Text.Format"));
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
    public Object visit(final NodeVisitor visitor, final Object value)
            throws GeneralException {

        return visitor.visitDiscretionary(this, value);
    }

}
