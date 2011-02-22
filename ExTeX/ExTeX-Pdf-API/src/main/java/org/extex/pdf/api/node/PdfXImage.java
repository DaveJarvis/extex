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

import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.WhatsItNode;

/**
 * This node denotes an ximage. This node type represents the extension node
 * from the perspective of <logo>T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo>.
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4791 $
 */
public class PdfXImage extends WhatsItNode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>attr</tt> contains the attribute string.
     */
    private String attr;

    /**
     * The field <tt>page</tt> contains the page number.
     */
    private long page;

    /**
     * The field <tt>resource</tt> contains the name of the object.
     */
    private String resource;

    /**
     * The field <tt>rule</tt> contains the rule.
     */
    private RuleNode rule;

    /**
     * Creates a new object.
     * 
     * @param resource the name of the object
     * @param rule the rule
     * @param attr the attribute string. This can be <code>null</code>.
     * @param page the page number
     */
    public PdfXImage(String resource, RuleNode rule, String attr, long page) {

        this.resource = resource;
        this.rule = rule;
        this.attr = attr;
        this.page = page;
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
     * Getter for page number.
     * 
     * @return the page
     */
    public long getPage() {

        return this.page;
    }

    /**
     * Getter for resource name.
     * 
     * @return the resource
     */
    public String getResource() {

        return this.resource;
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
     * @see org.extex.typesetter.type.Node#toString(java.lang.StringBuffer,
     *      java.lang.String, int, int)
     */
    @Override
    public void toString(StringBuffer sb, String prefix, int breadth, int depth) {

        sb.append("(pdfximage " + resource + ")");
    }

}
