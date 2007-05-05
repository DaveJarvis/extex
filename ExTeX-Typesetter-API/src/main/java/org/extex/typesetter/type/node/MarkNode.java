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
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.PageContext;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;

/**
 * A mark node carries some tokens which can be extracted after the page has
 * been completed. It can be used extract the first and last mark for headlines.
 * <p>
 * The document writer should ignore mark nodes.
 * </p>
 * 
 * @see "<logo>TeX</logo> &ndash; The Program [141]"
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4739 $
 */
public class MarkNode extends AbstractNode implements Node {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>index</tt> contains the index of the mark node for eTeX.
     * The index 0 corresponds to the original mark of <logo>TeX</logo>. I.e.
     * \marks0 == \mark
     */
    private String index;

    /**
     * The field <tt>mark</tt> contains the tokens of the mark.
     */
    private Tokens mark;

    /**
     * Creates a new object.
     * 
     * @param mark the mark tokens to store
     * @param index the index of the mark
     */
    public MarkNode(Tokens mark, String index) {

        super();
        this.mark = mark;
        this.index = index;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.AbstractNode#atShipping(
     *      org.extex.typesetter.PageContext, org.extex.typesetter.Typesetter,
     *      org.extex.typesetter.type.NodeVisitor, Boolean)
     */
    public Node atShipping(PageContext context, Typesetter typesetter,
            NodeVisitor<Node, Boolean> visitor, Boolean inHMode)
            throws GeneralException {

        context.setMark(index, mark);
        return (Node) this.visit(visitor, inHMode);
    }

    /**
     * Getter for index.
     * 
     * @return the index.
     */
    public String getIndex() {

        return this.index;
    }

    /**
     * Getter for mark.
     * 
     * @return the mark.
     */
    public Tokens getMark() {

        return this.mark;
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
     * 
     * @see "<logo>TeX</logo> &ndash; The Program [196]"
     * @see org.extex.typesetter.type.Node#toString( java.lang.StringBuffer,
     *      java.lang.String, int, int)
     */
    public void toString(StringBuffer sb, String prefix, int breadth, int depth) {

        sb.append(getLocalizer().format("String.Format", mark.toString()));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.Node#toText( java.lang.StringBuffer,
     *      java.lang.String)
     */
    public void toText(StringBuffer sb, String prefix) {

        sb.append(getLocalizer().format("Text.Format", mark.toString()));
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

        return visitor.visitMark(this, value);
    }

}
