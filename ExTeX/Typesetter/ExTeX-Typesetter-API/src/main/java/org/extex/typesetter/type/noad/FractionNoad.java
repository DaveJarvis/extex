/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.math.MathDelimiter;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathFontParameter;
import org.extex.typesetter.type.node.GlueNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.VerticalListNode;

/**
 * This Noad represents some mathematical material stacked above some other
 * mathematical material.
 *
 * @see "TTP [683]"
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class FractionNoad extends AbstractNoad {

    /**
     * The field <tt>denominator</tt> contains the denominator part.
     */
    private MathList denominator;

    /**
     * The field <tt>leftDelimiter</tt> contains the left delimiter or
     * <code>null</code> if none is set.
     */
    private MathDelimiter leftDelimiter;

    /**
     * The field <tt>numerator</tt> contains the numerator part.
     */
    private MathList numerator;

    /**
     * The field <tt>tc</tt> contains the typesetting context.
     */
    private TypesettingContext tc;

    /**
     * The field <tt>rightDelimiter</tt> contains the right delimiter or
     * <code>null</code> if none is set
     */
    private MathDelimiter rightDelimiter;

    /**
     * The field <tt>thickness</tt> contains the thickness of the fraction rule.
     * The value <code>null</code> indicates that the default rule thickness of
     * the current size should be used.
     */
    private FixedDimen thickness = null;

    /**
     * Creates a new object.
     *
     * @param denom the denominator
     * @param num the numerator
     * @param leftDelimiter the delimiter for the left side or
     *  <code>null</code> for none
     * @param rightDelimiter the delimiter for the right side or
     *  <code>null</code>  for none
     * @param thickness the thickness of the rule or <code>null</code> for the
     *  default thickness
     * @param tc the typesetting context for the rule
     */
    public FractionNoad(MathList denom, MathList num,
            MathDelimiter leftDelimiter,
            MathDelimiter rightDelimiter, FixedDimen thickness,
            TypesettingContext tc) {

        this.denominator = denom;
        this.numerator = num;
        this.leftDelimiter = leftDelimiter;
        this.rightDelimiter = rightDelimiter;
        this.thickness = thickness;
        this.tc = tc;
    }

    /**
     * Add some information in the middle of the default toString method.
     *
     * @param sb the target string buffer
     * @param depth the recursion depth
     *
     * @see "TTP [697]"
     * @see org.extex.typesetter.type.noad.AbstractNoad#toStringAdd(
     *      StringBuilder,
     *      int)
     */
    @Override
    public void toStringAdd(StringBuilder sb, int depth) {

        sb.append("fraction, thickness ");
        if (thickness == null) {
            sb.append("= default");
        } else {
            thickness.toString(sb);
        }
        if (leftDelimiter != null) {
            sb.append(", left delimiter ");
            leftDelimiter.toString(sb);
        }
        if (rightDelimiter != null) {
            sb.append(", right delimiter ");
            rightDelimiter.toString(sb);
        }
        toStringSubsidiaray(sb, numerator, depth, "\\");
        toStringSubsidiaray(sb, denominator, depth, "/");
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
     * @see "TTP [704,743]"
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

        getSpacingClass().addClearance(
            (previousNoad != null ? previousNoad.getSpacingClass() : null),
            list, mathContext);

        NodeList vlist = new VerticalListNode();

        HorizontalListNode num = new HorizontalListNode();
        StyleNoad style = mathContext.getStyle();
        mathContext.setStyle(style.num());
        numerator.typeset(null, noads, index, num, mathContext, logger);

        mathContext.setStyle(style.denom());
        HorizontalListNode den = new HorizontalListNode();
        denominator.typeset(null, noads, index, den, mathContext, logger);
        mathContext.setStyle(style);

        Dimen wNum = new Dimen(num.getWidth());
        Dimen wDen = new Dimen(den.getWidth());
        if (wNum.lt(wDen)) {
            num.add(0, new GlueNode(FixedGlue.S_S, true));
            num.add(new GlueNode(FixedGlue.S_S, true));
            num.hpack(wDen);
            wNum = wDen;
        } else if (wNum.gt(wDen)) {
            den.add(0, new GlueNode(FixedGlue.S_S, true));
            den.add(new GlueNode(FixedGlue.S_S, true));
            den.hpack(wNum);
            wNum.subtract(wDen);
        }

        vlist.add(num);
        if (thickness == null) {
            thickness =
                    mathContext
                        .mathParameter(MathFontParameter.DEFAULT_RULE_THICKNESS);
        }

        if (!thickness.ne(Dimen.ZERO)) {
            vlist.add(new RuleNode(wNum, thickness, Dimen.ZERO_PT, tc, true));
        }

        vlist.add(den);
        //TODO gene: adjust the move of num and den

        if (leftDelimiter != null) {
            leftDelimiter.typeset(list, mathContext, vlist.getHeight(), vlist
                .getDepth());
        }

        list.add(vlist);

        if (rightDelimiter != null) {
            rightDelimiter.typeset(list, mathContext, vlist.getHeight(), vlist
                .getDepth());
        }
    }

}
