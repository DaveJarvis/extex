/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.pdftex.util.destination;

import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.typesetter.type.node.RuleNode;

/**
 * This is the fitr destination type for PDF.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4791 $
 */
public class FitrDestType extends DestType {

    /**
     * The field <tt>rule</tt> contains the rule specification.
     */
    private RuleNode rule;

    /**
     * Creates a new object.
     *
     * @param rule the rule
     */
    public FitrDestType(final RuleNode rule) {

        super();
        this.rule = rule;
    }

    /**
     * Getter for rule.
     * The rule carries the width height, and depth. Nothing else.
     * And even those parameters are optional; they might be <code>null</code>.
     *
     * @return the rule
     */
    public RuleNode getRule() {

        return this.rule;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return  a string representation of the object.
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("fitr ");
        FixedDimen x = rule.getWidth();
        if (x != null) {
            sb.append("width ");
            x.toString(sb);
        }
        x = rule.getHeight();
        if (x != null) {
            sb.append("height ");
            x.toString(sb);
        }
        x = rule.getDepth();
        if (x != null) {
            sb.append("depth ");
            x.toString(sb);
        }
        return sb.toString();
    }

    /**
     * This method is the entry point for the visitor pattern.
     *
     * @param visitor the visitor to call back
     *
     * @return an arbitrary return object
     *
     * @see org.extex.unit.pdftex.util.destination.DestType#visit(
     *      org.extex.unit.pdftex.util.destination.DestinationVisitor)
     */
    public Object visit(final DestinationVisitor visitor) {

        return visitor.visitFitr(this);
    }

}
