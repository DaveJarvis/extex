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
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.ImpossibleException;
import org.extex.core.glue.FixedGlue;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathFontParameter;
import org.extex.typesetter.type.noad.util.MathSpacing;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.GlueNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.ImplicitKernNode;
import org.extex.typesetter.type.node.VerticalListNode;

/**
 * This is the abstract base class for Noads. A
 * {@link org.extex.typesetter.type.noad.Noad Noad} is the intermediate data
 * structure which is used for processing mathematical material. Finally Noads
 * are translated into {@link org.extex.typesetter.type.Node Node}s. Thus Noad
 * will never arrive at the DocumentWriter.
 * 
 * 
 * <doc name="scriptspace" type="register">
 * <h3>The Dimen Parameter <tt>\scriptspace</tt></h3>
 * <p>
 * The dimen parameter <tt>\scriptspace</tt> contains the amount of spacing
 * added to the width of subscripts.
 * </p>
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public abstract class AbstractNoad implements Noad {

    /**
     * Arrange that the node has exactly the width given and the old content is
     * centered in it. If the node is a box then this can be achieved by
     * inserting the appropriate glue. Otherwise a new box has to be
     * constructed.
     * 
     * @param node the node to rebox
     * @param width the target width
     * 
     * @return the argument box if it is a horizontal list or a new horizontal
     *         list
     * 
     * @see "TTP [715]"
     */
    protected static Node rebox(Node node, Dimen width) {

        if (node.getWidth().eq(width)) {
            return node;
        }
        HorizontalListNode hlist =
                (node instanceof HorizontalListNode
                        ? (HorizontalListNode) node
                        : new HorizontalListNode());
        hlist.add(0, new GlueNode(FixedGlue.S_S, true));
        hlist.add(new GlueNode(FixedGlue.S_S, true));
        return hlist;
    }

    /**
     * Print a noad to the string buffer preceded by some prefix if the noad is
     * not <code>null</code>.
     * 
     * @param sb the target buffer
     * @param noad the noad to print
     * @param depth the recursion depth
     * @param prefix the prefix to print before the noad
     * 
     * @see "TTP [692]"
     */
    protected static void toStringSubsidiaray(StringBuffer sb, Noad noad,
            int depth, String prefix) {

        if (noad != null) {
            sb.append(prefix);
            noad.toString(sb, depth);
        }
    }

    /**
     * The field <tt>spacingClass</tt> contains the class for spacing.
     */
    private MathSpacing spacingClass = MathSpacing.UNDEF;

    /**
     * The field <tt>subscript</tt> contains the subscript noad.
     */
    private Noad subscript = null;

    /**
     * The field <tt>superscript</tt> contains the superscript noad.
     */
    private Noad superscript = null;

    /**
     * Creates a new object.
     * 
     */
    public AbstractNoad() {

        super();
    }

    /**
     * Getter for the localizer.
     * 
     * @return the localizer
     */
    public Localizer getLocalizer() {

        return LocalizerFactory.getLocalizer(getClass());
    }

    /**
     * Getter for spacingClass.
     * 
     * @return the spacingClass
     */
    public MathSpacing getSpacingClass() {

        return this.spacingClass;
    }

    /**
     * Getter for the subscript.
     * 
     * @return the subscript.
     */
    public Noad getSubscript() {

        return this.subscript;
    }

    /**
     * Getter for the superscript.
     * 
     * @return the superscript.
     */
    public Noad getSuperscript() {

        return this.superscript;
    }

    /**
     * Attach the subscripts and superscripts to the current hlist.
     * 
     * @param node the current node
     * @param mc the math context
     * @param delta superscript is delta to the right of the subscript if both
     *        are present
     * @param logger the logger
     * 
     * @return a node containing the node and the subscripts and superscripts.
     *         If no subscripts and superscripts are present then
     *         <code>null</code> is returned instead.
     * 
     * @throws TypesetterException in case of an error
     * @throws ConfigurationException in case of an configuration error
     * 
     * @see "TTP [756,757]"
     */
    protected Node makeScripts(Node node, MathContext mc, FixedDimen delta,
            Logger logger) throws TypesetterException, ConfigurationException {

        if (superscript == null && subscript == null) {
            return null;
        }

        Dimen shiftDown;
        Dimen shiftUp;
        Dimen clr = new Dimen();
        HorizontalListNode hlist;
        StyleNoad style = mc.getStyle();
        TypesetterOptions options = mc.getOptions();

        if (node instanceof CharNode) {
            hlist = new HorizontalListNode(node);
            shiftDown = new Dimen();
            shiftUp = new Dimen();
        } else if (node instanceof HorizontalListNode) {
            hlist = (HorizontalListNode) node;
            StyleNoad t =
                    (mc.getStyle().less(StyleNoad.SCRIPTSTYLE)
                            ? StyleNoad.SCRIPTSTYLE
                            : StyleNoad.SCRIPTSCRIPTSTYLE);
            FixedDimen subDrop =
                    mc.mathParameter(MathFontParameter.SUB_DROP, t);

            shiftUp = new Dimen(node.getHeight());
            shiftUp.subtract(subDrop);

            shiftDown = new Dimen(node.getDepth());
            shiftDown.add(subDrop);
        } else {
            throw new ImpossibleException("makeScripts");
        }

        HorizontalListNode sub = null;
        if (subscript != null) {
            sub = new HorizontalListNode();
            mc.setStyle(style.sub());
            subscript.typeset(null, null, 0, sub, mc, logger);
            sub.advanceWidth(options.getDimenOption("scriptspace"));
            mc.setStyle(style);
        }

        if (superscript == null) {
            // only subscript
            // @see "TTP [757]"

            shiftDown.max(mc.mathParameter(MathFontParameter.SUB1));

            clr.abs(mc.mathParameter(MathFontParameter.MATH_X_HEIGHT));
            clr.multiply(-4, 5);
            if (sub != null) {
                clr.add(sub.getHeight());
            }
            shiftDown.max(clr);
            if (sub != null) {
                sub.setShift(shiftDown);
                hlist.add(sub);
            }
            return hlist;
        }
        HorizontalListNode sup = new HorizontalListNode();
        mc.setStyle(style.sup());
        superscript.typeset(null, null, 0, sup, mc, logger);
        mc.setStyle(style);
        sup.advanceWidth(options.getDimenOption("scriptspace"));

        if (style.isCramped()) {
            clr.set(mc.mathParameter(MathFontParameter.SUP3));
        } else if (style.less(StyleNoad.TEXTSTYLE)) {
            clr.set(mc.mathParameter(MathFontParameter.SUP1));
        } else {
            clr.set(mc.mathParameter(MathFontParameter.SUP2));
        }
        shiftUp.max(clr);
        clr.abs(mc.mathParameter(MathFontParameter.MATH_X_HEIGHT));
        clr.multiply(1, 4);
        clr.add(sup.getHeight());
        shiftUp.max(clr);

        if (subscript == null) {
            // only superscript
            shiftUp.negate();
            sup.setShift(shiftUp);
            hlist.add(sup);
            return hlist;
        }
        // both subscript and superscript

        shiftDown.max(mc.mathParameter(MathFontParameter.SUP2));

        clr.set(mc.mathParameter(MathFontParameter.DEFAULT_RULE_THICKNESS));
        clr.multiply(4);
        clr.subtract(shiftUp);
        clr.add(sup.getDepth());
        if (sub != null) {
            clr.subtract(sub.getHeight());
        }
        clr.add(shiftDown);

        if (clr.gt(Dimen.ZERO_PT)) {
            shiftDown.add(clr);

            clr.abs(mc.mathParameter(MathFontParameter.MATH_X_HEIGHT));
            clr.multiply(4, 5);
            clr.subtract(shiftUp);
            clr.add(sup.getDepth());
            if (clr.gt(Dimen.ZERO_PT)) {
                shiftUp.add(clr);
                shiftDown.subtract(clr);
            }
        }

        // shift_amount(x) ? delta; {superscript is delta to the right of the
        // subscript}
        sup.setMove(delta);
        VerticalListNode vlist = new VerticalListNode();
        vlist.add(sup);
        // p ? new_kern((shift_up-depth(x))-(height(y)-shift _down));
        clr.set(shiftUp);
        clr.subtract(sup.getDepth());
        if (sub != null) {
            clr.subtract(sub.getHeight());
        }
        clr.add(shiftDown);
        vlist.add(new ImplicitKernNode(clr, false));
        vlist.add(sub);
        // link(x) ? p;
        // link(p) ? y;
        // x ? vpack(x,natural);
        // shift_amount(x) ? shift_down;
        vlist.setShift(shiftDown);
        hlist.add(vlist);
        return hlist;
    }

    /**
     * Setter for spacingClass.
     * 
     * @param spacingClass the spacingClass to set
     */
    protected void setSpacingClass(MathSpacing spacingClass) {

        this.spacingClass = spacingClass;
    }

    /**
     * Setter for the subscript.
     * 
     * @param subscript the subscript to set.
     */
    public void setSubscript(Noad subscript) {

        this.subscript = subscript;
    }

    /**
     * Setter for the superscript.
     * 
     * @param superscript the superscript to set.
     */
    public void setSuperscript(Noad superscript) {

        this.superscript = superscript;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        StringBuffer sb = new StringBuffer();
        toString(sb, Integer.MAX_VALUE);
        return sb.toString();
    }

    /**
     * @see org.extex.typesetter.type.noad.Noad#toString(
     *      java.lang.StringBuffer)
     */
    public void toString(StringBuffer sb) {

        toString(sb, Integer.MAX_VALUE);
    }

    /**
     * @see "TTP [696]"
     * @see org.extex.typesetter.type.noad.Noad#toString(
     *      java.lang.StringBuffer, int)
     */
    public void toString(StringBuffer sb, int depth) {

        if (depth < 0) {
            sb.append(" {}");
        } else {
            sb.append('\\');
            toStringAdd(sb, depth);
            toStringSubsidiaray(sb, superscript, depth, "^");
            toStringSubsidiaray(sb, subscript, depth, "_");
        }
    }

    /**
     * Add some information in the middle of the default toString method.
     * 
     * @param sb the target string buffer
     * @param depth the recursion depth
     */
    protected void toStringAdd(StringBuffer sb, int depth) {

        // default is to do nothing
    }

}
