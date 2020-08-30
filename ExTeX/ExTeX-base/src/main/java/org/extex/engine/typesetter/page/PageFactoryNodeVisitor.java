/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.engine.typesetter.page;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extex.base.parser.ConstantDimenParser;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.max.StringSource;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.node.AdjustNode;
import org.extex.typesetter.type.node.AfterMathNode;
import org.extex.typesetter.type.node.AlignedLeadersNode;
import org.extex.typesetter.type.node.BeforeMathNode;
import org.extex.typesetter.type.node.CenteredLeadersNode;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.DiscretionaryNode;
import org.extex.typesetter.type.node.ExpandedLeadersNode;
import org.extex.typesetter.type.node.GlueNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.InsertionNode;
import org.extex.typesetter.type.node.KernNode;
import org.extex.typesetter.type.node.LigatureNode;
import org.extex.typesetter.type.node.MarkNode;
import org.extex.typesetter.type.node.PenaltyNode;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.SpaceNode;
import org.extex.typesetter.type.node.SpecialNode;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.node.VirtualCharNode;
import org.extex.typesetter.type.node.WhatsItNode;
import org.extex.typesetter.type.page.Page;

/**
 * This interface describes a
 * {@link org.extex.typesetter.type.NodeVisitor NodeVisitor} which is able to
 * take a {@link org.extex.typesetter.type.page.Page Page}, a
 * {@link org.extex.interpreter.context.Context Context}, and a
 * {@link org.extex.typesetter.Typesetter Typesetter}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PageFactoryNodeVisitor
        implements
            NodeVisitor<Node, Boolean>,
            LogEnabled {

    /**
     * The field {@code context} contains the interpreter context.
     */
    private Context context;

    /**
     * The field {@code logger} contains the logger.
     */
    private Logger logger = null;

    /**
     * The field {@code page} contains the page.
     */
    private Page page;

    /**
     * The field {@code posH} contains the current horizontal reference
     * point.
     */
    private Dimen posH = new Dimen();

    /**
     * The field {@code posV} contains the current vertical reference point.
     */
    private Dimen posV = new Dimen();

    /**
     * The field {@code sizePattern} contains the pattern for matching the
     * {@code papersize} special.
     */
    private Pattern sizePattern;

    /**
     * The field {@code typesetter} contains the typesetter.
     */
    private Typesetter typesetter;


    public PageFactoryNodeVisitor() {

        sizePattern =
                Pattern.compile("papersize="
                        + "([0-9.]+[a-z][a-z]),([0-9.]+[a-z][a-z])");
    }

    /**
     * check for an otherwise empty node if it contributes to the positioning.
     * If this is not the case then the node is simply optimized away. Otherwise
     * the method
     * {@link Node#atShipping(org.extex.typesetter.PageContext,
     *  org.extex.typesetter.Typesetter, org.extex.core.dimen.FixedDimen,
     *  org.extex.core.dimen.FixedDimen) atShipping()}
     * is invoked and the result returned.
     * 
     * @param node the current node
     * @param horizontal the indicator for the orientation
     * 
     * @return {@code null} iff the node should be discarded
     * 
     * @throws GeneralException in case of an error
     */
    private Node checkEmptyNode(Node node, Boolean horizontal)
            throws GeneralException {

        if (horizontal.booleanValue()) {
            if (node.getWidth().eq(Dimen.ZERO_PT)) {
                return null;
            }
        } else if (node.getVerticalSize().eq(Dimen.ZERO_PT)) {
            return null;
        }
        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      java.util.logging.Logger)
     */
    public void enableLogging(Logger log) {

        this.logger = log;
    }

    /**
     * Reset the internal to start with a new page.
     * 
     * @param p the page
     * @param c the context
     * @param t the typesetter
     */
    public void reset(Page p, Context c, Typesetter t) {

        this.context = c;
        this.page = p;
        this.typesetter = t;
        this.posH.set(0L);
        this.posV.set(0L);
        c.startMarks();
    }

    /**
*      org.extex.typesetter.type.node.AdjustNode, java.lang.Object)
     */
    public Node visitAdjust(AdjustNode node, Boolean isHMode)
            throws GeneralException {

        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.AfterMathNode, java.lang.Object)
     */
    public Node visitAfterMath(AfterMathNode node, Boolean isHMode)
            throws GeneralException {

        return checkEmptyNode(node, isHMode);
    }

    /**
*      org.extex.typesetter.type.node.AlignedLeadersNode, java.lang.Object)
     */
    public Node visitAlignedLeaders(AlignedLeadersNode node, Boolean isHMode)
            throws GeneralException {

        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.BeforeMathNode, java.lang.Object)
     */
    public Node visitBeforeMath(BeforeMathNode node, Boolean isHMode)
            throws GeneralException {

        return checkEmptyNode(node, isHMode);
    }

    /**
*      org.extex.typesetter.type.node.CenteredLeadersNode,
     *      java.lang.Object)
     */
    public Node visitCenteredLeaders(CenteredLeadersNode node, Boolean isHMode)
            throws GeneralException {

        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.CharNode, java.lang.Object)
     */
    public Node visitChar(CharNode node, Boolean isHMode)
            throws GeneralException {

        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.DiscretionaryNode, java.lang.Object)
     */
    public Node visitDiscretionary(DiscretionaryNode node, Boolean isHMode)
            throws GeneralException {

        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.ExpandedLeadersNode,
     *      java.lang.Object)
     */
    public Node visitExpandedLeaders(ExpandedLeadersNode node, Boolean isHMode)
            throws GeneralException {

        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.GlueNode, java.lang.Object)
     */
    public Node visitGlue(GlueNode node, Boolean isHMode)
            throws GeneralException {

        if (isHMode.booleanValue()) {
            if (node.getWidth().eq(Dimen.ZERO_PT)) {
                return null;
            }
            // } else if (node.getVerticalSize().eq(Dimen.ZERO_PT)
            // && node.getSize().eq(Glue.ZERO)) {
            // return null;
        }
        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.HorizontalListNode, java.lang.Object)
     */
    public Node visitHorizontalList(HorizontalListNode list, Boolean isHMode)
            throws GeneralException {

        long h = posH.getValue();
        long v = posV.getValue();
        posH.add(list.getMove());
        posV.add(list.getShift());

        int size = list.size();

        for (int i = 0; i < size; i++) {
            Node n = (Node) list.get(i).visit(this, Boolean.TRUE);

            if (n == null) {
                list.remove(i--);
                size--;
            } else {
                if (n != this) {
                    list.remove(i);
                    list.add(i, n);
                }
                posH.add(n.getWidth());
            }
        }

        posH.set(h);
        posV.set(v);

        return list.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.InsertionNode, java.lang.Object)
     */
    public Node visitInsertion(InsertionNode node, Boolean isHMode)
            throws GeneralException {

        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.KernNode, java.lang.Object)
     */
    public Node visitKern(KernNode node, Boolean isHMode)
            throws GeneralException {

        return checkEmptyNode(node, isHMode);
    }

    /**
*      org.extex.typesetter.type.node.LigatureNode, java.lang.Object)
     */
    public Node visitLigature(LigatureNode node, Boolean isHMode)
            throws GeneralException {

        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.MarkNode, java.lang.Object)
     */
    public Node visitMark(MarkNode node, Boolean isHMode)
            throws GeneralException {

        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.PenaltyNode, java.lang.Object)
     */
    public Node visitPenalty(PenaltyNode node, Boolean isHMode)
            throws GeneralException {

        return checkEmptyNode(node, isHMode);
    }

    /**
*      org.extex.typesetter.type.node.RuleNode, java.lang.Object)
     */
    public Node visitRule(RuleNode node, Boolean isHMode)
            throws GeneralException {

        return checkEmptyNode(node, isHMode);
    }

    /**
*      org.extex.typesetter.type.node.SpaceNode, java.lang.Object)
     */
    public Node visitSpace(SpaceNode node, Boolean isHMode)
            throws GeneralException {

        return node.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.VerticalListNode, java.lang.Object)
     */
    public Node visitVerticalList(VerticalListNode list, Boolean isHMode)
            throws GeneralException {

        long h = posH.getValue();
        long v = posV.getValue();
        posH.add(list.getMove());
        posV.add(list.getShift());

        int size = list.size();

        for (int i = 0; i < size; i++) {
            Node n = (Node) list.get(i).visit(this, Boolean.FALSE);

            if (n == null) {
                list.remove(i--);
                size--;
            } else {
                if (n != this) {
                    list.remove(i);
                    list.add(i, n);
                }
                posV.add(n.getHeight());
                posV.add(n.getDepth());
            }
        }

        posH.set(h);
        posV.set(v);

        return list.atShipping(context, typesetter, posH, posV);
    }

    /**
*      org.extex.typesetter.type.node.VirtualCharNode, java.lang.Object)
     */
    public Node visitVirtualChar(VirtualCharNode node, Boolean isHMode)
            throws GeneralException {

        NodeList nodes = node.getNodes();
        if (nodes == null) {
            return null;
        }
        return (Node) nodes.visit(this, isHMode);
    }

    /**
*      org.extex.typesetter.type.node.WhatsItNode, java.lang.Object)
     */
    public Node visitWhatsIt(WhatsItNode node, Boolean isHMode)
            throws GeneralException {

        if (node instanceof SpecialNode) {

            String text = ((SpecialNode) node).getText();

            if (text.startsWith("papersize=")) {
                Matcher m = sizePattern.matcher(text);
                if (m.matches()) {
                    try {
                        Dimen width = ConstantDimenParser.scan(context, 
                            new StringSource(m.group(1)), typesetter);
                        Dimen height = ConstantDimenParser.scan(context, 
                            new StringSource(m.group(2)), typesetter);
                        page.setMediaWidth(width);
                        page.setMediaHeight(height);
                    } catch (ConfigurationException e) {
                        logger.log(Level.SEVERE, "", e);
                    }
                } else {
                    logger.warning("...");
                }

            } else if (text.equals("landscape")) {

                Dimen h = page.getMediaHeight();
                page.setMediaHeight(page.getMediaWidth());
                page.setMediaWidth(h);
            }
        }
        return node.atShipping(context, typesetter, posH, posV);
    }

}
