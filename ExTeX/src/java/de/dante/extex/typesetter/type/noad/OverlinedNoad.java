/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.typesetter.type.noad;

import java.util.logging.Logger;

import de.dante.extex.interpreter.context.TypesettingContext;
import de.dante.extex.interpreter.type.dimen.Dimen;
import de.dante.extex.interpreter.type.dimen.FixedDimen;
import de.dante.extex.typesetter.exception.TypesetterException;
import de.dante.extex.typesetter.type.NodeList;
import de.dante.extex.typesetter.type.noad.util.MathContext;
import de.dante.extex.typesetter.type.noad.util.MathFontParameter;
import de.dante.extex.typesetter.type.node.ExplicitKernNode;
import de.dante.extex.typesetter.type.node.HorizontalListNode;
import de.dante.extex.typesetter.type.node.RuleNode;
import de.dante.extex.typesetter.type.node.VerticalListNode;
import de.dante.util.framework.configuration.exception.ConfigurationException;

/**
 * This class provides an over-lining for the nucleus.
 *
 * @see "TTP [687]"
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OverlinedNoad extends AbstractNucleusNoad {

    /**
     * Creates a new object.
     *
     * @param nucleus the nucleus to be underlined
     * @param tc the typesetting context for the color
     */
    public OverlinedNoad(final Noad nucleus, final TypesettingContext tc) {

        super(nucleus, tc);
    }

    /**
     * Add some information in the middle of the default toString method.
     *
     * @param sb the target string buffer
     * @param depth the recursion depth
     *
     * @see "TTP [696]"
     * @see de.dante.extex.typesetter.type.noad.AbstractNoad#toStringAdd(
     *      java.lang.StringBuffer,
     *      int)
     */
    protected void toStringAdd(final StringBuffer sb, final int depth) {

        sb.append("overline");
    }

    /**
     * Translate a Noad into a NodeList.
     *
     * @param noads the list of noads currently processed
     * @param index the index of the current node in the list
     * @param list the list to add the nodes to. This list contains the Nodes
     *  previously typeset. Thus it can be used to look back
     * @param mathContext the context to consider
     * @param logger the logger for debugging and tracing information
     *
     * @return the index of the next noad to consider
     *
     * @throws TypesetterException in case of a problem
     * @throws ConfigurationException in case of a configuration problem
     *
     * @see "TTP [705,734]"
     *
     * @see de.dante.extex.typesetter.type.noad.Noad#typeset(
     *      de.dante.extex.typesetter.type.noad.Noad,
     *      de.dante.extex.typesetter.type.noad.NoadList,
     *      int,
     *      de.dante.extex.typesetter.type.NodeList,
     *      de.dante.extex.typesetter.type.noad.util.MathContext,
     *      java.util.logging.Logger)
     */
    public void typeset(final Noad previousNoad, final NoadList noads,
            final int index, final NodeList list,
            final MathContext mathContext, final Logger logger)
            throws TypesetterException,
                ConfigurationException {

        getSpacingClass().addClearance(
                (previousNoad != null ? previousNoad.getSpacingClass() : null),
                list, mathContext);

        HorizontalListNode hlist = new HorizontalListNode();
        FixedDimen thickness = mathContext
                .mathParameter(MathFontParameter.DEFAULT_RULE_THICKNESS);
        StyleNoad style = mathContext.getStyle();
        mathContext.setStyle(style.cramped());
        Noad n = getNucleus();
        n.typeset(previousNoad, noads, index, hlist, mathContext,
                logger);
        mathContext.setStyle(style);

        setSpacingClass(n.getSpacingClass());
        getSpacingClass().addClearance(
                (previousNoad != null ? previousNoad.getSpacingClass() : null),
                list, mathContext);

        VerticalListNode vlist = new VerticalListNode();
        vlist.add(new ExplicitKernNode(thickness, false));
        vlist.add(new RuleNode(hlist.getWidth(), thickness, Dimen.ZERO_PT,
                getTypesettingContext(), true));
        vlist.add(new ExplicitKernNode(new Dimen(3 * thickness.getValue()),
                false));
        vlist.add(hlist);
        list.add(vlist);

        Dimen h = new Dimen(vlist.getHeight());
        h.add(vlist.getDepth());
        Dimen d = hlist.getDepth();
        vlist.setDepth(d);
        h.subtract(d);
        vlist.setHeight(h);
    }

}
