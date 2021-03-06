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

package org.extex.typesetter.core.paragraphBuilder.trivial;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.FixedGlueComponent;
import org.extex.core.glue.Glue;
import org.extex.core.glue.WideGlue;
import org.extex.framework.logger.LogEnabled;
import org.extex.logging.LogFormatter;
import org.extex.typesetter.Discardable;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.common.Parameter;
import org.extex.typesetter.paragraphBuilder.FixedParagraphShape;
import org.extex.typesetter.paragraphBuilder.HangingParagraphShape;
import org.extex.typesetter.paragraphBuilder.ParagraphBuilder;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.AfterMathNode;
import org.extex.typesetter.type.node.BeforeMathNode;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.DiscretionaryNode;
import org.extex.typesetter.type.node.GlueNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.KernNode;
import org.extex.typesetter.type.node.PenaltyNode;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.node.factory.NodeFactory;

/**
 * This class implements a trivial paragraph builder.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TrivialBuilder implements ParagraphBuilder, LogEnabled {

    /**
     * The constant {@code DEVELOP} contains a boolean indicating whether the
     * develop traces should be written.
     */
    private static final boolean DEVELOP = false;

    /**
     * The constant {@code EJECT_PENALTY} contains the penalty which forces a
     * line break. This is an equivalent to -&infin;.
     */
    private static final int EJECT_PENALTY = -10000;

    /**
     * The constant {@code INF_BAD} contains the value for infinite penalty.
     * This is an equivalent to &infin;.
     */
    private static final int INF_PENALTY = 10000;

    /**
     * The field {@code fixedParshape} contains the data object used to
     * transport the fixed paragraph shape to the appropriate places. The values
     * stored in it will be overwritten whenever this object will be used for
     * the current paragraph.
     */
    private final FixedParagraphShape fixedParshape =
            new FixedParagraphShape(Dimen.ZERO_PT);

    /**
     * The field {@code hangingParshape} contains the data object used to
     * transport the hanging paragraph shape to the appropriate places. The
     * values stored in it will be overwritten whenever this object will be used
     * for the current paragraph.
     */
    private final HangingParagraphShape hangingParshape =
            new HangingParagraphShape(0, Dimen.ZERO_PT, Dimen.ZERO_PT);

    /**
     * The field {@code logger} contains the logger to be used. This field is
     * initialized from the framework by invoking the appropriate setter.
     */
    private Logger logger = null;

    /**
     * The field {@code options} contains the reference to the options
     * object.
     */
    private TypesetterOptions options = null;

    /**
     * The field {@code parshape} contains the paragraph shape specification.
     * This field is initialized at the beginning of the line breaking if it is
     * {@code null}. At the end of the line breaking it is reset to
     * {@code null}.
     */
    private ParagraphShape parshape = null;

    /**
     * The field {@code tracer} contains the logger used for tracing.
     */
    private Logger tracer = null;


    public TrivialBuilder() {

        if (DEVELOP) {
            tracer = Logger.getLogger(ParagraphBuilder.class.getName());
            tracer.setUseParentHandlers(false);
            Handler handler = new ConsoleHandler();
            handler.setFormatter(new LogFormatter());
            handler.setLevel(Level.ALL);
            tracer.addHandler(handler);
            tracer.setLevel(Level.ALL);
        }
    }

    /**
     * Add a new line to a vlist. Ensure that a minimum distance between the
     * lines exists. Usually the distance {@code \baselineskip} between the
     * lines is desirable. For this purpose the depth of the previous line and
     * the height of the current line is subtracted. If the remaining distance
     * is less than {@code \lineskiplimit} then the value of
     * {@code \lineskip} is used instead.
     * 
     * @param vlist the target list
     * @param hlist the line to add
     * @param baselineskip the parameter \baselineskip
     * @param lineskip the parameter \lineskip
     * @param lineskiplimit the parameter \lineskiplimit
     */
    private void addLine(VerticalListNode vlist, HorizontalListNode hlist,
            FixedGlue baselineskip, FixedGlue lineskip, FixedDimen lineskiplimit) {

        int end = vlist.size() - 1;

        Glue g = new Glue(baselineskip);
        g.subtract(hlist.getHeight());
        if (end >= 0) {
            g.subtract(vlist.get(end).getDepth());
        }
        FixedGlue fg = g.lt(lineskiplimit) ? lineskip : g;
        if (fg.ne(FixedGlue.ZERO)) {
            vlist.add(new GlueNode(fg, false));
        }

        vlist.add(hlist);
    }

    /**
     * Analyze a node list and cut off the first line.
     * 
     * @param start the index of the first node to consider
     * @param len the length of nodes
     * @param nodes the node list to take the nodes from
     * @param hlist the target list to put the nodes into
     * @param width the target width
     * @param accumulator an accumulator for the glue
     * @param height the accumulator for the height
     * @param depth the accumulator for the depth
     * @return the index of the first node after the ones already processed
     */
    private int breakLine(int start, int len, HorizontalListNode nodes,
            HorizontalListNode hlist, Dimen width, WideGlue accumulator,
            FixedDimen height, FixedDimen depth) {

        Node node = nodes.get(start);
        hlist.add(node);
        node.addWidthTo(accumulator);
        int i = start + 1;
        int point = i;
        WideGlue w = new WideGlue();

        while (i < len) {

            point = findNextBreakPoint(nodes, point, w);
            if (w.getLength().gt(width)) {
                if (i == start + 1) {
                    // avoid infinite loop and accept overfull box
                    i = saveNodes(nodes, i, point, hlist, 
                        accumulator, height, depth);
                }
                return discardNodes(i, len, nodes);
            }

            i = saveNodes(nodes, i, point, hlist, accumulator, height, depth);
            point = i + 1;
        }

        return i;
    }

    /**
     * Break a horizontal list into lines. The horizontal list passed in might
     * be modified under way.
     * 
     * @param nodes the horizontal node list containing all nodes for the
     *        paragraph
     * @return the {@link org.extex.typesetter.type.node.VerticalListNode
     *         VerticalListNode} containing the hboxes of the lines
     * @see org.extex.typesetter.paragraphBuilder.ParagraphBuilder#build(
     *      org.extex.typesetter.type.node.HorizontalListNode)
     */
    public NodeList build(HorizontalListNode nodes) {

        if (options.getCountOption("tracingparagraphs").getValue() > 0) {
            tracer = logger;
        }

        if (DEVELOP) {
            tracer.info("\nbreaking paragraph: " + nodes.toText());
        }

        FixedGlue leftskip = options.getGlueOption("leftskip");
        FixedGlue rightskip = options.getGlueOption("rightskip");

        prepareParshape();

        // remove final node if it is glue; [TTB p99--100]
        int len = nodes.size();
        Node node = (len < 1 ? null : nodes.get(len - 1));
        if (node instanceof GlueNode) {
            len--;
            nodes.remove(len);
        }

        // [TTB p100]
        nodes.add(new PenaltyNode(INF_PENALTY));
        nodes.add(new GlueNode(options.getGlueOption("parfillskip"), true));
        nodes.add(new PenaltyNode(EJECT_PENALTY));

        VerticalListNode vlist = new VerticalListNode();
        FixedGlue parskip = options.getGlueOption("parskip");
        if (parskip.ne(FixedGlue.ZERO)) {
            vlist.add(new GlueNode(parskip, false));
        }

        int i = 0;
        int line = 0;
        Dimen wd = new Dimen();
        Dimen adjustLeftRight = new Dimen(leftskip.getLength());
        adjustLeftRight.add(rightskip.getLength());
        FixedGlue baselineskip = options.getGlueOption(Parameter.BASELINESKIP);
        FixedGlue lineskip = options.getGlueOption(Parameter.LINESKIP);
        FixedDimen lineskiplimit =
                options.getDimenOption(Parameter.LINESKIPLIMIT);
        WideGlue accumulator = new WideGlue();

        while (i < len) {
            accumulator.set(Dimen.ZERO_PT);
            wd.set(parshape.getLength(line));
            wd.subtract(parshape.getIndent(line));
            Dimen width = new Dimen(wd);
            wd.subtract(adjustLeftRight);
            HorizontalListNode hlist = new HorizontalListNode();

            hlist.addSkip(leftskip);
            accumulator.add(leftskip);
            i = breakLine(i, len, nodes, hlist, wd, 
                accumulator, hlist.getHeight(), hlist.getDepth());
            hlist.addSkip(rightskip);
            accumulator.add(rightskip);

            spread(hlist, width, accumulator);

            addLine(vlist, hlist, baselineskip, lineskip, lineskiplimit);
            line++;
        }

        options.setParshape(null);
        return vlist;
    }

    /**
     * Skip over any discardable nodes and return the index of the next
     * non-discardable node.
     * 
     * @param start the index to start at
     * @param len the length of the node list
     * @param nodes the node list to take into account
     * 
     * @return the index of the next non-discardable node
     */
    private int discardNodes(int start, int len, NodeList nodes) {

        int i = start;
        while (i < len && nodes.get(i) instanceof Discardable) {
            i++;
        }
        return i;
    }

    /**
     * Setter for the logger.
     * 
     * @param log the logger to use
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger log) {

        this.logger = log;
    }

    /**
     * Determine the index of the next break point.
     * 
     * @param nodes the list of nodes to consider
     * @param start the initial index
     * @param width an accumulator for the width
     * @return the index of the next break point or the index of the element
     *         past the end of the list if none is found
     */
    private int findNextBreakPoint(NodeList nodes, int start, WideGlue width) {

        Node node;
        boolean math = false;
        int len = nodes.size();
        int i = start;

        while (i < len && nodes.get(i) instanceof Discardable) {
            nodes.get(i).addWidthTo(width);
            i++;
        }

        for (; i < len; i++) {
            node = nodes.get(i);

            if (node instanceof CharNode) {
                // node.addWidthTo(width);
                // continue;
            } else if (node instanceof GlueNode
                    && !(nodes.get(i - 1) instanceof Discardable)) {

                return i;
            } else if (node instanceof KernNode && !math
                    && nodes.get(i + 1) instanceof GlueNode) {

                node.addWidthTo(width);
                return i;
            } else if (node instanceof PenaltyNode) {
                int penalty = (int) ((PenaltyNode) node).getPenalty();
                if (penalty < INF_PENALTY) {
                    node.addWidthTo(width);
                    return i;
                }
            } else if (node instanceof BeforeMathNode) {
                math = true;
            } else if (node instanceof AfterMathNode) {
                if (nodes.get(i + 1) instanceof GlueNode) {

                    node.addWidthTo(width);
                    return i;
                }
                math = false;

            } else if (node instanceof DiscretionaryNode) {

                // breakList.add(new BreakPoint(i, w, wd,
                // (((DiscretionaryNode) node).getPreBreak().length() != 0
                // ? hyphenpenalty
                // : exhyphenpenalty)));
                // node.addWidthTo(width);
                return i;
            }
            node.addWidthTo(width);
        }
        return len;
    }

    /**
     * Initializes the field {@code parshape} if not set already. For this
     * purpose the options are considered.
     */
    private void prepareParshape() {

        parshape = options.getParshape();

        if (parshape == null) {
            int hangafter =
                    (int) options.getCountOption("hangafter").getValue();

            if (hangafter != 0) {
                hangingParshape.setHangafter(hangafter);
                hangingParshape.setHangindent(options
                    .getDimenOption("hangindent"));
                hangingParshape.setHsize(options.getDimenOption("hsize"));
                parshape = hangingParshape;
            } else {
                fixedParshape.setHsize(options.getDimenOption("hsize"));
                parshape = fixedParshape;
            }
        }
    }

    /**
     * Copy nodes from one list into another.
     * 
     * @param nodes the list of nodes to consider
     * @param start the initial index
     * @param end the index of the element after the ones to save
     * @param hlist the destination list
     * @param accumulator the accumulator for the glue of the saved nodes
     * @param height the accumulator for the height
     * @param depth the accumulator for the depth
     * 
     * @return the index of the first node which has not been copied
     */
    private int saveNodes(HorizontalListNode nodes, int start, int end,
            HorizontalListNode hlist, WideGlue accumulator, FixedDimen height,
            FixedDimen depth) {

        Node node;
        for (int i = start; i < end; i++) {
            node = nodes.get(i);
            if (!(node instanceof PenaltyNode)) {
                hlist.add(node);
                node.addWidthTo(accumulator);
            }
        }
        return end;
    }

    /**
     * Setter for the node factory.
     * 
     * @param nodeFactory the node factory
     * @see org.extex.typesetter.paragraphBuilder.ParagraphBuilder#setNodefactory(
     *      org.extex.typesetter.type.node.factory.NodeFactory)
     */
    public void setNodefactory(NodeFactory nodeFactory) {

        // nothing to do
    }

    /**
     * Setter for options.
     * 
     * @param options the options to set.
     * @see org.extex.typesetter.paragraphBuilder.ParagraphBuilder#setOptions(
     *      org.extex.typesetter.TypesetterOptions)
     */
    public void setOptions(TypesetterOptions options) {

        this.options = options;
    }

    /**
     * Adjust the width of the current line.
     * 
     * @param hlist the target list to put the nodes into
     * @param targetWidth the target width to which the hlist should be adjusted
     * @param w the accumulated width of the hlist
     */
    private void spread(HorizontalListNode hlist, FixedDimen targetWidth,
            WideGlue w) {

        FixedDimen width = w.getLength();
        FixedGlueComponent component =
                (width.lt(targetWidth) ? w.getStretch() : w.getShrink());

        Dimen wd = new Dimen(targetWidth.getValue() - width.getValue());

        for (Node n : hlist) {
            n.spreadWidth(wd, component);
        }

        hlist.setWidth(targetWidth);
    }

}
