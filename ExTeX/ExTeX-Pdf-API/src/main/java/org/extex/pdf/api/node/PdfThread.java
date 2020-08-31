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

import org.extex.pdf.api.id.IdSpec;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.WhatsItNode;

/**
 * This node contains an PDF Object. This node type represents the extension
 * node from the perspective of TeX.
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PdfThread extends WhatsItNode {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code attr} contains the attribute string.
     */
    private final String attr;

    /**
     * The field {@code id} contains the id.
     */
    private final IdSpec id;

    /**
     * The field {@code rule} contains the rule.
     */
    private final RuleNode rule;

    /**
     * Creates a new object.
     * 
     * @param rule the rule
     * @param attr the attribute string. This can be {@code null}.
     * @param id the id
     */
    public PdfThread(RuleNode rule, String attr, IdSpec id) {

        this.rule = rule;
        this.attr = attr;
        this.id = id;
    }

    /**
     * Getter for the attribute string.
     * 
     * @return the attribute string
     */
    public String getAttr() {

        return this.attr;
    }

    /**
     * Getter for id.
     * 
     * @return the id
     */
    public IdSpec getId() {

        return this.id;
    }

    /**
     * Getter for rule.
     * 
     * @return the rule
     */
    public RuleNode getRule() {

        return this.rule;
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

        sb.append("(pdfthread " + id + ")");
    }

}
