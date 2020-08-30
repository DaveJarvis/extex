/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.ImpossibleException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathFontParameter;
import org.extex.typesetter.type.node.HorizontalListNode;

/**
 * This class represents a Noad with vertically centered material.
 *
 * @see "TTP [687]"
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class VCenterNoad extends AbstractNucleusNoad {

    /**
     * Creates a new object.
     *
     * @param nucleus the nucleus to be underlined
     * @param tc the typesetting context for the color
     */
    public VCenterNoad(Noad nucleus, TypesettingContext tc) {

        super(nucleus, tc);
    }

    /**
     * Add some information in the middle of the default toString method.
     *
     * @param sb the target string buffer
     * @param depth the recursion depth
     *
     * @see "TTP [696]"
     * @see org.extex.typesetter.type.noad.AbstractNoad#toStringAdd(
     *      StringBuilder,
     *      int)
     */
    @Override
    protected void toStringAdd(StringBuilder sb, int depth) {

        sb.append("vcenter");
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
     * @see "TTP [736]"
     * @see org.extex.typesetter.type.noad.Noad#typeset(
     *      org.extex.typesetter.type.noad.Noad,
     *      org.extex.typesetter.type.noad.NoadList,
     *      int,
     *      org.extex.typesetter.type.NodeList,
     *      org.extex.typesetter.type.noad.util.MathContext,
     *      java.util.logging.Logger)
     */
    public void typeset(Noad previousNoad, NoadList noads,
            int index, NodeList list,
            MathContext mathContext, Logger logger)
            throws TypesetterException,
                ConfigurationException {

        HorizontalListNode hlist = new HorizontalListNode();
        Noad n = getNucleus();
        n.typeset(previousNoad, noads, index, hlist, mathContext, logger);

        setSpacingClass(n.getSpacingClass());
        getSpacingClass().addClearance(
            (previousNoad != null ? previousNoad.getSpacingClass() : null),
            list, mathContext);

        Dimen d = new Dimen(hlist.getHeight());
        d.add(hlist.getDepth());
        Dimen h = new Dimen(d);
        try {
            h.divide(2);
        } catch (ArithmeticException e) {
            throw new ImpossibleException(e);
        }
        h.add(mathContext.mathParameter(MathFontParameter.AXIS_HEIGHT));
        hlist.setHeight(h);
        d.subtract(h);
        hlist.setDepth(d);

        list.add(hlist);
    }

}
