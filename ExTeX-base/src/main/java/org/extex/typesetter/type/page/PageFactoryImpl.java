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

package org.extex.typesetter.type.page;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extex.core.count.FixedCount;
import org.extex.core.count.ImmutableCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.max.StringSource;
import org.extex.scanner.DimenParser;
import org.extex.typesetter.PageContext;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
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

/**
 * This class provides a factory for page instances.
 *
 * <p>
 *  The separation of the page into a logical page and a physical page is
 *  depicted in the figure below.
 * </p>
 * <div class="figure">
 *  <img src="doc-files/page-1.png" title="Dimensions of a Page"/>
 *  <div class="caption">
 *   Dimensions of a page
 *  </div>
 * </div>
 * <p>
 *  The physical page denotes the real paper. DVI has no notion of the physical
 *  page but PDF knows of those bounds. The logical page is placed somewhere on
 *  the physical page. The physical page has the width <tt>\mediawidth</tt>
 *  and the height <tt>\mediaheight</tt>.
 * </p>
 * <p>
 *  The logical page is the area used by <logo>TeX</logo> to place material on.
 *  It has a reference point which is in its upper left corner. This reference
 *  point is 1&nbsp;in right and 1&nbsp;in down from the corner of the physical
 *  page. The reference point can be shifted further by using the dimen
 *  registers <tt>\hoffset</tt> and <tt>\voffset</tt>.
 * </p>
 *
 *
 * <h2>Parameters</h2>
 *
 * <doc name="mediawidth" type="register">
 * <h3>The Dimen Parameter <tt>\mediawidth</tt></h3>
 * <p>
 *  The dimen parameter <tt>\mediawidth</tt> contains the physical width of the
 *  page. The logical page is usually smaller.
 * </p>
 * <p>
 *  The value of this  parameter is used when a page is shipped out and
 *  attached to the page. Any modifications of the parameter have no effect
 *  to the value stored.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;mediawidth&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\mediawidth</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@link
 *        org.extex.scanner.DimenParser#parse(Context,TokenSource,Typesetter)
 *        &lang;dimen value&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt> &lang;optional prefix&rang; </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 *   \mediawidth=210mm </pre>
 * </doc>
 *
 *
 * <doc name="mediaheight" type="register">
 * <h3>The Dimen Parameter <tt>\mediaheight</tt></h3>
 * <p>
 *  The dimen parameter <tt>\mediaheight</tt> contains the physical height of
 *  the page. The logical page is usually smaller.
 * </p>
 * <p>
 *  The value of this  parameter is used when a page is shipped out and
 *  attached to the page. Any modifications of the parameter have no effect
 *  to the value stored.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;mediaheight&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\mediaheight</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@link
 *        org.extex.scanner.DimenParser#parse(Context,TokenSource,Typesetter)
 *        &lang;dimen value&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt> &lang;optional prefix&rang; </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 *   \mediaheight=297mm </pre>
 * </doc>
 *
 *
 * <doc name="hoffset" type="register">
 * <h3>The Dimen Parameter <tt>\hoffset</tt></h3>
 * <p>
 *  The logical page is placed on the physical page such that the upper left
 *  corner of the logical page is 1&nbsp;in down and 1&nbsp;in to the right of
 *  the physical page. This placement can be influence by the dimen parameter
 *  <tt>\hoffset</tt>.
 *  The dimen parameter <tt>\hoffset</tt> contains the horizontal offset
 *  added to the reference point when placing the logical page.
 *  The default value is 0&nbsp;pt. Thus the reference point is 1&nbsp;in to the
 *  right. A positive value shifts the reference point rightwards.
 * </p>
 * <p>
 *  The value of this  parameter is used when a page is shipped out and
 *  attached to the page. Any modifications of the parameter have no effect
 *  to the value stored.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;hoffset&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\hoffset</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@link
 *        org.extex.scanner.DimenParser#parse(Context,TokenSource,Typesetter)
 *        &lang;dimen value&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt> &lang;optional prefix&rang; </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 *   \hoffset=-.5in </pre>
 * </doc>
 *
 *
 * <doc name="voffset" type="register">
 * <h3>The Dimen Parameter <tt>\voffset</tt></h3>
 * <p>
 *  The logical page is placed on the physical page such that the upper left
 *  corner of the logical page is 1&nbsp;in down and 1&nbsp;in to the right of
 *  the physical page. This placement can be influence by the dimen parameter
 *  <tt>\voffset</tt>.
 *  The dimen parameter <tt>\voffset</tt> contains the vertical offset
 *  added to the reference point when placing the logical page.
 *  The default value is 0&nbsp;pt. Thus the reference point is 1&nbsp;in down.
 *  A positive value shifts the reference point downwards.
 * </p>
 * <p>
 *  The value of this  parameter is used when a page is shipped out and
 *  attached to the page. Any modifications of the parameter have no effect
 *  to the value stored.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;voffset&rang;
 *      &rarr; &lang;optional prefix&rang; <tt>\voffset</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@link
 *        org.extex.scanner.DimenParser#parse(Context,TokenSource,Typesetter)
 *        &lang;dimen value&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt> &lang;optional prefix&rang; </pre>
 *
 * <h4>Examples</h4>
 * <pre class="TeXSample">
 *   \voffset=1in </pre>
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4399 $
 */
public class PageFactoryImpl implements PageFactory, LogEnabled {

    /**
     * The field <tt>x</tt> contains the static mapping from integers to the
     * corresponding index of the count register for the page number.
     */
    private static final String[] NO =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger = null;

    /**
     * The field <tt>sizePattern</tt> contains the pattern for matching the
     * <tt>papersize</tt> special.
     */
    private Pattern sizePattern;

    /**
     * The field <tt>visitor</tt> contains the node visitor to determine which
     * nodes to keep and to post-process the nodes.
     */
    private PageFactoryNodeVisitor visitor = new PageFactoryNodeVisitor() {

        /**
         * The field <tt>context</tt> contains the interpreter context.
         */
        private Context context;

        /**
         * The field <tt>page</tt> contains the page.
         */
        private Page page;

        /**
         * The field <tt>typesetter</tt> contains the typesetter.
         */
        private Typesetter typesetter;

        /**
         * Setter for the context.
         *
         * @param context the context
         *
         * @see org.extex.typesetter.type.page.PageFactoryNodeVisitor#setContext(
         *      org.extex.interpreter.context.Context)
         */
        public void setContext(Context context) {

            this.context = context;
        }

        /**
         * Setter for the page.
         *
         * @param page the page
         *
         * @see org.extex.typesetter.type.page.PageFactoryNodeVisitor#setPage(
         *      org.extex.typesetter.type.page.Page)
         */
        public void setPage(Page page) {

            this.page = page;
        }

        /**
         * Setter for the typesetter.
         *
         * @param typesetter the typesetter
         *
         * @see org.extex.typesetter.type.page.PageFactoryNodeVisitor#setTypesetter(
         *      org.extex.typesetter.Typesetter)
         */
        public void setTypesetter(Typesetter typesetter) {

            this.typesetter = typesetter;
        }

        /**
         * This method is called when an
         * {@link org.extex.typesetter.type.node.AdjustNode AdjustNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @throws GeneralException in case of an error
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitAdjust(
         *      org.extex.typesetter.type.node.AdjustNode,
         *      java.lang.Object)
         */
        public Node visitAdjust(AdjustNode node, Boolean isHMode)
                throws GeneralException {

            return node;
        }

        /**
         * This method is called when an
         * {@link org.extex.typesetter.type.node.AfterMathNode AfterMathNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitAfterMath(
         *      org.extex.typesetter.type.node.AfterMathNode,
         *      java.lang.Object)
         */
        public Node visitAfterMath(AfterMathNode node,
                Boolean isHMode) {

            if (isHMode.booleanValue()) {
                if (node.getWidth().eq(Dimen.ZERO_PT)) {
                    return null;
                }
            } else if (node.getVerticalSize().eq(Dimen.ZERO_PT)) {
                return null;
            }
            return node;
        }

        /**
         * This method is called when an
         * {@link org.extex.typesetter.type.node.AlignedLeadersNode AlignedLeadersNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitAlignedLeaders(
         *      org.extex.typesetter.type.node.AlignedLeadersNode,
         *      java.lang.Object)
         */
        public Node visitAlignedLeaders(AlignedLeadersNode node,
                Boolean isHMode) {

            return node;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.BeforeMathNode BeforeMathNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitBeforeMath(
         *      org.extex.typesetter.type.node.BeforeMathNode,
         *      java.lang.Object)
         */
        public Node visitBeforeMath(BeforeMathNode node,
                Boolean isHMode) {

            if (isHMode.booleanValue()) {
                if (node.getWidth().eq(Dimen.ZERO_PT)) {
                    return null;
                }
            } else if (node.getVerticalSize().eq(Dimen.ZERO_PT)) {
                return null;
            }
            return node;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.CenteredLeadersNode CenteredLeadersNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitCenteredLeaders(
         *      org.extex.typesetter.type.node.CenteredLeadersNode,
         *      java.lang.Object)
         */
        public Node visitCenteredLeaders(CenteredLeadersNode node,
                Boolean isHMode) {

            return node;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.CharNode CharNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitChar(
         *      org.extex.typesetter.type.node.CharNode,
         *      java.lang.Object)
         */
        public Node visitChar(CharNode node, Boolean isHMode) {

            return node;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.DiscretionaryNode DiscretionaryNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitDiscretionary(
         *      org.extex.typesetter.type.node.DiscretionaryNode,
         *      java.lang.Object)
         */
        public Node visitDiscretionary(DiscretionaryNode node,
                Boolean isHMode) {

            return node;
        }

        /**
         * This method is called when an
         * {@link org.extex.typesetter.type.node.ExpandedLeadersNode ExpandedLeadersNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitExpandedLeaders(
         *      org.extex.typesetter.type.node.ExpandedLeadersNode,
         *      java.lang.Object)
         */
        public Node visitExpandedLeaders(ExpandedLeadersNode node,
                Boolean isHMode) {

            return node;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.GlueNode GlueNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitGlue(
         *      org.extex.typesetter.type.node.GlueNode,
         *      java.lang.Object)
         */
        public Node visitGlue(GlueNode node, Boolean isHMode) {

            if (isHMode.booleanValue()) {
                if (node.getWidth().eq(Dimen.ZERO_PT)) {
                    return null;
                }
                //            } else if (node.getVerticalSize().eq(Dimen.ZERO_PT)
                //                    && node.getSize().eq(Glue.ZERO)) {
                //                return null;
            }
            return node;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.HorizontalListNode HorizontalListNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitHorizontalList(
         *      org.extex.typesetter.type.node.HorizontalListNode,
         *      java.lang.Object)
         */
        public Node visitHorizontalList(HorizontalListNode node,
                Boolean isHMode) {

            return (node.size() == 0 ? null : node);
        }

        /**
         * This method is called when an
         * {@link org.extex.typesetter.type.node.InsertionNode InsertionNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitInsertion(
         *      org.extex.typesetter.type.node.InsertionNode,
         *      java.lang.Object)
         */
        public Node visitInsertion(InsertionNode node,
                Boolean isHMode) {

            return null;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.KernNode KernNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitKern(
         *      org.extex.typesetter.type.node.KernNode,
         *      java.lang.Object)
         */
        public Node visitKern(KernNode node, Boolean isHMode) {

            if (isHMode.booleanValue()) {
                if (node.getWidth().eq(Dimen.ZERO_PT)) {
                    return null;
                }
            } else if (node.getVerticalSize().eq(Dimen.ZERO_PT)) {
                return null;
            }
            return node;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.LigatureNode LigatureNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitLigature(
         *      org.extex.typesetter.type.node.LigatureNode,
         *      java.lang.Object)
         */
        public Node visitLigature(LigatureNode node, Boolean isHMode) {

            return node;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.MarkNode MarkNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitMark(
         *      org.extex.typesetter.type.node.MarkNode,
         *      java.lang.Object)
         */
        public Node visitMark(MarkNode node, Boolean isHMode) {

            return null;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.PenaltyNode PenaltyNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitPenalty(
         *      org.extex.typesetter.type.node.PenaltyNode,
         *      java.lang.Object)
         */
        public Node visitPenalty(PenaltyNode node, Boolean isHMode) {

            return null;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.RuleNode RuleNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitRule(
         *      org.extex.typesetter.type.node.RuleNode,
         *      java.lang.Object)
         */
        public Node visitRule(RuleNode node, Boolean isHMode) {

            return node;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.SpaceNode SpaceNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitSpace(
         *      org.extex.typesetter.type.node.SpaceNode,
         *      java.lang.Object)
         */
        public Node visitSpace(SpaceNode node, Boolean isHMode) {

            return node;
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.VerticalListNode VerticalListNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitVerticalList(
         *      org.extex.typesetter.type.node.VerticalListNode,
         *      java.lang.Object)
         */
        public Node visitVerticalList(VerticalListNode node,
                Boolean isHMode) {

            return (node.size() == 0 ? null : node);
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.VirtualCharNode VirtualCharNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitVirtualChar(
         *      org.extex.typesetter.type.node.VirtualCharNode,
         *      java.lang.Object)
         */
        public Node visitVirtualChar(VirtualCharNode node,
                Boolean isHMode) {

            return node.getNodes();
        }

        /**
         * This method is called when a
         * {@link org.extex.typesetter.type.node.WhatsItNode WhatsItNode}
         * has been encountered.
         *
         * @param node the first parameter for the visitor is the node visited
         * @param isHMode the second parameter for the visitor
         *
         * @return the visitor specific value
         *
         * @throws GeneralException in case of an error
         *
         * @see org.extex.typesetter.type.NodeVisitor#visitWhatsIt(
         *      org.extex.typesetter.type.node.WhatsItNode,
         *      java.lang.Object)
         */
        public Node visitWhatsIt(WhatsItNode node, Boolean isHMode)
                throws GeneralException {

            if (node instanceof SpecialNode) {

                String text = ((SpecialNode) node).getText();

                if (text.startsWith("papersize=")) {
                    Matcher m = sizePattern.matcher(text);
                    if (m.matches()) {
                        try {
                            Dimen width = DimenParser.parse(context, //
                                new StringSource(m.group(1)), typesetter);
                            Dimen height = DimenParser.parse(context, //
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
            return node;
        }

    };

    /**
     * Creates a new object.
     */
    public PageFactoryImpl() {

        super();
        sizePattern =
                Pattern.compile("papersize="
                        + "([0-9.]+[a-z][a-z]),([0-9.]+[a-z][a-z])");
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger log) {

        logger = log;
    }

    /**
     * Get a new instance of a page.
     *
     * @param nodes the nodes contained
     * @param pageContext the interpreter context
     * @param typesetter the typesetter
     *
     * @return the new instance or <code>null</code> if the page would be empty
     *
     * @throws GeneralException in case of an error
     */
    public Page newInstance(NodeList nodes, PageContext pageContext,
            Typesetter typesetter) throws GeneralException {

        //TODO gene: beware of ClassCastException
        Context context = (Context) pageContext;
        FixedCount[] pageNo = new FixedCount[10];
        for (int i = 0; i < 10; i++) {
            pageNo[i] = new ImmutableCount(context.getCount(NO[i]));
        }
        PageImpl page = new PageImpl(nodes, pageNo);

        page.setMediaWidth(context.getDimen("mediawidth"));
        page.setMediaHeight(context.getDimen("mediaheight"));
        Dimen off = new Dimen(Dimen.ONE_INCH);
        off.add(context.getDimen("hoffset"));
        page.setMediaHOffset(off);
        off.set(Dimen.ONE_INCH);
        off.add(context.getDimen("voffset"));
        page.setMediaVOffset(off);

        context.startMarks();
        visitor.setPage(page);
        visitor.setContext(context);
        visitor.setTypesetter(typesetter);

        if (nodes.atShipping(context, typesetter, visitor, false) == null) {
            return null;
        }
        return page;
    }

}
