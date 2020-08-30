/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.pdf.api.node;

import org.extex.typesetter.type.node.WhatsItNode;

/**
 * This node signals the end of a link. This node type represents the extension
 * node from the perspective of TeX.
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PdfLiteral extends WhatsItNode {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code direct} contains the direct indicator.
     */
    private boolean direct;

    /**
     * The field {@code text} contains the text to pass to the back-end driver.
     */
    private String text;

    /**
     * Creates a new object.
     * 
     * @param text the text of the literal
     * @param direct the indicator that the literal should be inserted
     *        immediately. If it is {@code false} then the insertin is
     *        deferred until shipout.
     */
    public PdfLiteral(String text, boolean direct) {

        this.text = text;
        this.direct = direct;
    }

    /**
     * Getter for text.
     * 
     * @return the text
     */
    public String getText() {

        return this.text;
    }

    /**
     * Getter for direct.
     * 
     * @return the direct
     */
    public boolean isDirect() {

        return this.direct;
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
     * @see org.extex.typesetter.type.Node#toString(StringBuilder,
     *      java.lang.String, int, int)
     */
    @Override
    public void toString(StringBuilder sb, String prefix, int breadth, int depth) {

        sb.append("(pdfliteral " + (direct ? "direct " : "") + text + ")");
    }

}
