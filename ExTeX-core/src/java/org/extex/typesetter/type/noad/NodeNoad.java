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

package org.extex.typesetter.type.noad;

import java.util.logging.Logger;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathSpacing;
import org.extex.typesetter.type.node.GlueNode;
import org.extex.typesetter.type.node.KernNode;

/**
 * This noad contains a node which is passed through the math apparatus.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class NodeNoad implements Noad {

    /**
     * The field <tt>node</tt> contains the encapsulated node.
     */
    private Node node;

    /**
     * Creates a new object.
     *
     * @param node the node to
     */
    public NodeNoad(final Node node) {

        super();
        this.node = node;
    }

    /**
     * Getter for spacing class.
     *
     * @return the spacing class
     *
     * @see org.extex.typesetter.type.noad.Noad#getSpacingClass()
     */
    public MathSpacing getSpacingClass() {

        return MathSpacing.UNDEF; // TODO gene: correct?
    }

    /**
     * Getter for the subscript.
     *
     * @return the subscript.
     *
     * @see org.extex.typesetter.type.noad.Noad#getSubscript()
     */
    public Noad getSubscript() {

        return null;
    }

    /**
     * Getter for the superscript.
     *
     * @return the superscript.
     *
     * @see org.extex.typesetter.type.noad.Noad#getSuperscript()
     */
    public Noad getSuperscript() {

        return null;
    }

    /**
     * Setter for the subscript.
     * This operation is not supported and leads to an exception.
     *
     * @param subscript the subscript to set.
     *
     * @see org.extex.typesetter.type.noad.Noad#setSubscript(
     *      org.extex.typesetter.type.noad.Noad)
     */
    public void setSubscript(final Noad subscript) {

        throw new UnsupportedOperationException();
    }

    /**
     * Setter for the superscript.
     * This operation is not supported and leads to an exception.
     *
     * @param superscript the superscript to set.
     *
     * @see org.extex.typesetter.type.noad.Noad#setSuperscript(
     *      org.extex.typesetter.type.noad.Noad)
     */
    public void setSuperscript(final Noad superscript) {

        throw new UnsupportedOperationException();
    }

    /**
     * Produce a printable representation of the noad in a StringBuffer.
     *
     * @param sb the string buffer
     *
     * @see org.extex.typesetter.type.noad.Noad#toString(
     *      java.lang.StringBuffer)
     */
    public void toString(final StringBuffer sb) {

        node.toString(sb, "", Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Produce a printable representation to a certain depth of the noad.
     *
     * @param sb the string buffer
     * @param depth the depth to which the full information should be given
     *
     * @see org.extex.typesetter.type.noad.Noad#toString(
     *      java.lang.StringBuffer, int)
     */
    public void toString(final StringBuffer sb, final int depth) {

        node.toString(sb, "", Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Translate a Noad into a NodeList.
     *
     * @param previousNoad the previous noad
     * @param noads the list of noads currently processed
     * @param index the index of the current node in the list
     * @param list the list to add the nodes to. This list contains the Nodes
     *  previously typeset. Thus it can be used to look back
     * @param mathContext the context to consider
     * @param logger the logger for debugging and tracing information
     *
     * @throws TypesetterException in case of a problem
     * @throws ConfigurationException in case of a configuration problem
     *
     * @see org.extex.typesetter.type.noad.Noad#typeset(
     *      org.extex.typesetter.type.noad.Noad,
     *      org.extex.typesetter.type.noad.NoadList,
     *      int,
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.type.noad.util.MathContext,
     *      java.util.logging.Logger)
     */
    public void typeset(final Noad previousNoad, final NoadList noads,
            final int index, final NodeList list,
            final MathContext mathContext, final Logger logger)
            throws TypesetterException,
                ConfigurationException {

        if (node instanceof GlueNode || node instanceof KernNode) {
            if (previousNoad instanceof GlueNoad
                    && ((GlueNoad) previousNoad).isKill()) {
                StyleNoad style = mathContext.getStyle();
                if (style == StyleNoad.SCRIPTSTYLE
                        || style == StyleNoad.SCRIPTSCRIPTSTYLE) {
                    return;
                }
            }
        }

        getSpacingClass().addClearance(
            (previousNoad != null ? previousNoad.getSpacingClass() : null),
            list, mathContext);

        list.add(node);
    }

}
