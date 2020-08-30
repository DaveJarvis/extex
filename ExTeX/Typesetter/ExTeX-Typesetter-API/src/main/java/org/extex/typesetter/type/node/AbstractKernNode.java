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

package org.extex.typesetter.type.node;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.typesetter.type.NodeVisitor;

/**
 * This class provides the same functionality as
 * {@link org.extex.typesetter.type.node.KernNode KernNode} but is
 * distinguishable for the sake of some fine differentiations in TeX.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractKernNode extends AbstractNode implements KernNode {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2009L;

    /**
     * Creates a new object.
     * 
     * @param kern the natural size
     * @param horizontal the indicator that the kerning works horizontally
     */
    public AbstractKernNode(FixedDimen kern, boolean horizontal) {

        super((horizontal ? kern : Dimen.ZERO_PT), 
            (horizontal ? Dimen.ZERO_PT : kern), 
            Dimen.ZERO_PT);
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
* @see org.extex.typesetter.type.Node#toString(StringBuilder,
     *      java.lang.String, int, int)
     */
    @Override
    public void toString(StringBuilder sb, String prefix, int breadth, int depth) {

        sb.append(getLocalizer().format("String.Format", 
            getWidth().toString()));
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

        sb.append(getLocalizer().format("Text.Format", getWidth().toString()));
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

        return visitor.visitKern(this, value);
    }

}
