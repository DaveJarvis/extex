/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

import java.util.logging.Logger;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.FixedGlueComponent;
import org.extex.core.glue.WideGlue;
import org.extex.typesetter.Badness;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.OrientedNodeList;

/**
 * This class provides an implementation for a vertical list.
 * 
 * @see "<logo>TeX</logo> &ndash; The Program [137]"
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4739 $
 */
public class VerticalListNode extends GenericNodeList
        implements
            OrientedNodeList {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>top</tt> contains the indicator that the adjustment
     * should use the reference point of the fist box. This is the mode for
     * <tt>\vtop</tt>. In contrast the last box is used. This is the mode for
     * <tt>\vbox</tt>.
     */
    private boolean top = false;

    /**
     * Creates a new empty list.
     * 
     * @see "<logo>TeX</logo> &ndash; The Program [136]"
     */
    public VerticalListNode() {

        super();
    }

    /**
     * Creates a new list with a node in it.
     * 
     * @param node the initial node
     */
    public VerticalListNode(Node node) {

        super();
        add(node);
    }

    /**
     * Add a node to the node list at a given position.
     * 
     * @param index the position of insertion
     * @param node the node to add
     * 
     * @see org.extex.typesetter.type.node.GenericNodeList#add( int,
     *      org.extex.typesetter.type.Node)
     */
    public void add(int index, Node node) {

        if (node == null) {
            return;
        }
        super.add(index, node);
        maxWidth(node.getWidth());

        int size = size();

        if (size == 1) {
            setHeight(node.getHeight());
            setDepth(node.getDepth());
        } else if (top) {
            if (index == 0) {
                advanceDepth(getHeight());
                setHeight(node.getHeight());
            } else {
                advanceDepth(node.getHeight());
            }
            advanceDepth(node.getDepth());
        } else if (index == size) {
            advanceHeight(getDepth());
            advanceHeight(node.getHeight());
            setDepth(node.getDepth());
        } else {
            advanceHeight(node.getHeight());
            advanceHeight(node.getDepth());
        }
    }

    /**
     * Add a node to the node list. The other attributes (width, height, depth)
     * are not modified.
     * 
     * @param node the node to add
     * 
     * @see org.extex.typesetter.type.node.GenericNodeList#add(
     *      org.extex.typesetter.type.Node)
     */
    public void add(Node node) {

        if (node == null) {
            return;
        }
        super.add(node);
        maxWidth(node.getWidth());

        if (size() == 1) {
            setHeight(node.getHeight());
            setDepth(node.getDepth());
        } else if (top) {
            advanceDepth(node.getDepth());
            advanceDepth(node.getHeight());
        } else {
            advanceHeight(getDepth());
            advanceHeight(node.getHeight());
            setDepth(node.getDepth());
        }
    }

    /**
     * Add some glue to the node list. The other attributes (width, height,
     * depth) are not modified.
     * 
     * @param glue the glue to add
     * 
     * @see org.extex.typesetter.type.NodeList#addSkip(
     *      org.extex.core.glue.FixedGlue)
     */
    public void addSkip(FixedGlue glue) {

        add(new GlueNode(glue, false));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.OrientedNode#isHorizontal()
     */
    public boolean isHorizontal() {

        return false;
    }

    /**
     * Getter for top.
     * 
     * @return the top
     */
    public boolean isTop() {

        return this.top;
    }

    /**
     * Setter for top.
     * 
     * @param top the top to set
     */
    public void setTop(boolean top) {

        this.top = top;
    }

    /**
     * Split-off material from a vertical list of a desired height. The
     * splitting is performed at a position with minimal penalty. The list is
     * stretched to the desired height.
     * 
     * @param height the target height
     * @param logger the logger for normal logging output
     * @param traceLogger the logger for tracing
     * 
     * @return the split off material
     */
    public VerticalListNode split(FixedDimen height, Logger logger,
            Logger traceLogger) {

        long penalty;
        long bestPenalty = Badness.INF_PENALTY + 1;
        FixedDimen bestHeight = Dimen.ZERO_PT;
        WideGlue ht = new WideGlue();
        int size = size();
        int bestSplit = size;

        for (int i = 0; i < size; i++) {
            Node node = get(i);
            node.addHeightTo(ht);
            node.addDepthTo(ht);
            if (i + 1 >= size || !(get(i + 1) instanceof PenaltyNode)) {
                penalty =
                        splitPenalty((node instanceof PenaltyNode
                                ? ((PenaltyNode) node).getPenalty()
                                : 0), ht, height);
                if (penalty < bestPenalty) {
                    bestPenalty = penalty;
                    bestSplit = i;
                    bestHeight = ht.getLength();
                }
            }
        }

        if (traceLogger != null) {
            traceLogger.info("% split" + "???" + " to " + height + ","
                    + bestHeight + " p=" + bestPenalty);
        }
        VerticalListNode result = new VerticalListNode();
        for (int i = 0; i < bestSplit; i++) {
            result.add(get(i));
        }

        return result;
    }

    /**
     * Compute the penalty for the split at a given position.
     * 
     * @param penalty the base penalty optionally coming from a penalty node
     * @param ht the actual height
     * @param height the desired height
     * 
     * @return the penalty in the range 0 to 10000, including
     */
    private long splitPenalty(long penalty, WideGlue ht, FixedDimen height) {

        // long badness = Badness.badness(height.getValue(), //
        // ht.getLength().getValue());
        long p = penalty;
        // TODO gene: splitPenalty() unimplemented
        return p;
    }

    /**
     * Spread the list vertically to a desired size by distributing the
     * differences to the glues contained.
     * 
     * @param height the target size
     * 
     * @return the badness of the spread
     */
    public long spread(FixedDimen height) {

        int size = size();

        WideGlue ht = new WideGlue();

        for (int i = 0; i < size; i++) {
            get(i).addHeightTo(ht);
            get(i).addDepthTo(ht);
        }

        FixedDimen length = ht.getLength();
        for (int i = 0; i < size; i++) {
            get(i).spreadHeight(height, length);
        }

        return Badness.badness(height.getValue(), length.getValue());
    }

    /**
     * Adjust the height of a flexible node. This method is a noop for any but
     * the flexible nodes.
     * 
     * @param height the desired height
     * @param sum the total sum of the glues
     * 
     * @see org.extex.typesetter.type.Node#spreadHeight(
     *      org.extex.core.dimen.FixedDimen,
     *      org.extex.core.glue.FixedGlueComponent)
     */
    public void spreadHeight(FixedDimen height, FixedGlueComponent sum) {

    }

    /**
     * This method puts the printable representation into the string buffer.
     * This is meant to produce a exhaustive form as it is used in tracing
     * output to the log file.
     * 
     * @param sb the output string buffer
     * @param prefix the prefix string inserted at the beginning of each line
     * @param breadth the breadth of the nodes to display
     * @param depth the depth of the nodes to display
     * 
     * @see org.extex.typesetter.type.Node#toString( java.lang.StringBuffer,
     *      java.lang.String, int, int)
     */
    public void toString(StringBuffer sb, String prefix, int breadth, int depth) {

        sb.append("\\vbox");
        super.toString(sb, prefix, breadth, depth);
    }

    /**
     * This method puts the printable representation into the string buffer.
     * This is meant to produce a short form only as it is used in error
     * messages to the user.
     * 
     * @param sb the output string buffer
     * @param prefix the prefix string inserted at the beginning of each line
     * 
     * @see org.extex.typesetter.type.Node#toText( java.lang.StringBuffer,
     *      java.lang.String)
     */
    public void toText(StringBuffer sb, String prefix) {

        sb.append("(vlist ");
        super.toText(sb, prefix);
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
     * @see org.extex.typesetter.type.Node#visit(
     *      org.extex.typesetter.type.NodeVisitor, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public Object visit(NodeVisitor visitor, Object value)
            throws GeneralException {

        return visitor.visitVerticalList(this, value);
    }

    /**
     * Adjust the variable nodes to achieve a given target height.
     * 
     * @param targetHeight the target height
     * 
     * @return the badness
     */
    public long vpack(FixedDimen targetHeight) {

        int size = size();

        if (size == 0) {
            setHeight(targetHeight);
            return 0;
        }

        if (top) {
            size = 1;
        }

        Dimen ht = new Dimen();
        WideGlue flexibleHeight = new WideGlue();

        ht.set(get(size - 1).getHeight());

        for (int i = 0; i < size - 1; i++) {
            Node node = get(i);
            ht.add(node.getHeight());
            ht.add(node.getDepth());
            node.addHeightTo(flexibleHeight);
            node.addDepthTo(flexibleHeight);
        }

        if (targetHeight.ne(ht)) {
            ht.subtract(targetHeight);
            ht.negate();
            FixedGlueComponent s = (ht.le(Dimen.ZERO) //
                    ? flexibleHeight.getShrink() //
                    : flexibleHeight.getStretch());
            for (int i = 0; i < size; i++) {
                get(i).spreadHeight(ht, s);
            }
            setHeight(targetHeight);
        } else {
            setHeight(ht);
        }

        return 0; // TODO gene: compute badness
    }

}