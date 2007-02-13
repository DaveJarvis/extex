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

import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.glue.FixedGlueComponent;
import org.extex.interpreter.type.glue.Glue;
import org.extex.interpreter.type.glue.WideGlue;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.util.exception.GeneralException;
import org.extex.util.framework.i18n.Localizer;
import org.extex.util.framework.i18n.LocalizerFactory;

/**
 * This abstract class provides some methods common to all Nodes.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public abstract class AbstractNode implements Node {

    /**
     * The constant <tt>NO_CHAR</tt> contains the empty array of CharNode.
     */
    protected static final CharNode[] NO_CHARS = new CharNode[0];

    /**
     * The field <tt>depth</tt> contains the depth of the node.
     * The depth is the extend of the node below the baseline.
     */
    private Glue depth;

    /**
     * The field <tt>height</tt> contains the height of the node.
     * The height is the extend of the node above the baseline.
     */
    private Glue height;

    /**
     * The field <tt>localizer</tt> contains the localizer.
     */
    private Localizer localizer = null;

    /**
     * This is the width of the node.
     * The width is the extend of the node along the baseline.
     */
    private Glue width;

    /**
     * Creates a new object.
     * All dimensions (width, height, depth) are initially set to 0pt.
     */
    public AbstractNode() {

        super();
        width = new Glue();
        height = new Glue();
        depth = new Glue();
    }

    /**
     * Creates a new object.
     *
     * @param aWidth the width of the node
     */
    public AbstractNode(final FixedDimen aWidth) {

        super();
        this.width = new Glue(aWidth);
        this.height = new Glue();
        this.depth = new Glue();
    }

    /**
     * Creates a new object.
     *
     * @param aWidth the width of the node
     * @param aHeight the height of the node
     * @param aDepth the depth of the node
     */
    public AbstractNode(final FixedDimen aWidth, final FixedDimen aHeight,
            final FixedDimen aDepth) {

        super();
        this.width = new Glue(aWidth);
        this.height = new Glue(aHeight);
        this.depth = new Glue(aDepth);
    }

    /**
     * Add the flexible depth of the current node to the given glue.
     *
     * @param glue the glue to add to.
     *
     * @see org.extex.typesetter.type.Node#addDepthTo(
     *      org.extex.interpreter.type.glue.WideGlue)
     */
    public void addDepthTo(final WideGlue glue) {

        glue.add(depth);
    }

    /**
     * Add the flexible height of the current node to the given glue.
     *
     * @param glue the glue to add to.
     *
     * @see org.extex.typesetter.type.Node#addHeightTo(
     *      org.extex.interpreter.type.glue.WideGlue)
     */
    public void addHeightTo(final WideGlue glue) {

        glue.add(height);
    }

    /**
     * Add the flexible width of the current node to the given glue.
     *
     * @param glue the glue to add to.
     *
     * @see org.extex.typesetter.type.Node#addWidthTo(
     *      org.extex.interpreter.type.glue.WideGlue)
     */
    public void addWidthTo(final WideGlue glue) {

        glue.add(width);
    }

    /**
     * Advance the depth by some length. The length can also be negative.
     *
     * @param x the length to add
     */
    public void advanceDepth(final FixedDimen x) {

        depth.add(x);
    }

    /**
     * Advance the height by some length. The length can also be negative.
     *
     * @param x the length to add
     */
    public void advanceHeight(final FixedDimen x) {

        height.add(x);
    }

    /**
     * Advance the width by some length. The length can also be negative.
     *
     * @param x the length to add
     */
    public void advanceWidth(final FixedDimen x) {

        width.add(x);
    }

    /**
     * This method performs any action which are required to executed at the
     * time of shipping the node to the DocumentWriter. It is a NOOP in the
     * abstract base class and should be overwritten by sub-classes if
     * required.
     *
     * @param context the interpreter context
     * @param typesetter the typesetter
     * @param visitor the node visitor to be invoked when the node is hit. Note
     *  that each node in the output page is visited this way. Thus there is no
     *  need to implement a node traversal for the NodeList types
     * @param inHMode <code>true</code> iff the container is a horizontal list.
     *  Otherwise the container is a vertical list
     *
     * @return the node to be used instead of the current one in the output
     *  list. If the value is <code>null</code> then the node is deleted. If
     *  the value is the node itself then it is preserved.
     *
     * @throws GeneralException in case of an error
     *
     * @see org.extex.typesetter.type.Node#atShipping(
     *      org.extex.interpreter.context.Context,
     *      org.extex.typesetter.Typesetter,
     *      org.extex.typesetter.type.NodeVisitor,
     *      boolean)
     */
    public Node atShipping(final Context context, final Typesetter typesetter,
            final NodeVisitor visitor, final boolean inHMode)
            throws GeneralException {

        return (Node) this.visit(visitor, inHMode
                ? Boolean.TRUE
                : Boolean.FALSE);
    }

    /**
     * Compute the amount of adjustment needed to achieve a certain size.
     *
     * @param size the current size in scaled points
     * @param glue the glue
     * @param sum the total stretchability or shrinkability
     *
     * @return the adjustment
     */
    protected long computeAdjustment(final long size, final FixedGlue glue,
            final FixedGlueComponent sum) {

        FixedGlueComponent s =
                (size > 0 ? glue.getStretch() : glue.getShrink());

        int order = s.getOrder();
        long value = sum.getValue();
        if (order < sum.getOrder() || value == 0) {
            return 0;
        }

        long sValue = s.getValue();
        long adjust = sValue * size / value;
        if (order == 0) {
            if (adjust > sValue) {
                adjust = sValue;
            } else if (adjust < -sValue) {
                adjust = -sValue;
            }
        }
        return adjust;
    }

    /**
     * @see org.extex.typesetter.type.Node#countChars()
     */
    public int countChars() {

        return 0;
    }

    /**
     * @see org.extex.typesetter.type.Node#getChars()
     */
    public CharNode[] getChars() {

        return NO_CHARS;
    }

    /**
     * Getter for the depth of the node.
     *
     * @return the depth
     *
     * @see org.extex.typesetter.type.Node#getDepth()
     */
    public FixedDimen getDepth() {

        return depth.getLength();
    }

    /**
     * Getter for the height of the node.
     *
     * @return the height
     *
     * @see org.extex.typesetter.type.Node#getHeight()
     */
    public FixedDimen getHeight() {

        return height.getLength();
    }

    /**
     * Getter for localizer.
     *
     * @return the localizer
     */
    protected Localizer getLocalizer() {

        if (this.localizer == null) {
            this.localizer = LocalizerFactory.getLocalizer(this.getClass());
        }
        return this.localizer;
    }

    /**
     * @see org.extex.typesetter.type.Node#getVerticalSize()
     */
    public FixedDimen getVerticalSize() {

        FixedDimen h = getHeight();
        Dimen d = new Dimen(getDepth());

        if (h.ge(Dimen.ZERO)) {
            if (d.ge(Dimen.ZERO)) {
                d.add(h);
            } else {
                d.negate();
                d.max(h);
            }
        } else {
            if (d.ge(Dimen.ZERO)) {
                d.negate();
                d.min(h);
                d.negate();
            } else {
                d.add(h);
                d.negate();
            }
        }
        return d;
    }

    /**
     * Getter for the width of the node.
     *
     * @return the width
     *
     * @see org.extex.typesetter.type.Node#getWidth()
     */
    public FixedDimen getWidth() {

        return width.getLength();
    }

    /**
     * Assign the maximum of the current value and a comparison value to the
     * depth.
     *
     * @param x the length to compare to
     */
    public void maxDepth(final FixedDimen x) {

        if (depth.getLength().lt(x)) {
            depth.setLength(x);
        }
    }

    /**
     * Assign the maximum of the current value and a comparison value to the
     * height.
     *
     * @param x the length to compare to
     */
    public void maxHeight(final FixedDimen x) {

        if (height.getLength().lt(x)) {
            height.setLength(x);
        }
    }

    /**
     * Assign the maximum of the current value and a comparison value to the
     * width.
     *
     * @param x the length to compare to
     */
    public void maxWidth(final FixedDimen x) {

        if (width.getLength().lt(x)) {
            width.setLength(x);
        }
    }

    /**
     * Setter for the depth of the node.
     *
     * @param depth the node depth
     *
     * @see org.extex.typesetter.type.Node#setDepth(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setDepth(final FixedDimen glue) {

        depth.set(glue);
    }

    /**
     * Setter for the height of the node.
     *
     * @param height the new height
     *
     * @see org.extex.typesetter.type.Node#setHeight(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setHeight(final FixedDimen glue) {

        height.set(glue);
    }

    /**
     * Setter for the width of the node.
     *
     * @param width the new width
     *
     * @see org.extex.typesetter.type.Node#setWidth(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setWidth(final FixedDimen glue) {

        width.set(glue);
    }

    /**
     * Adjust the height of a flexible node. This method is a noop for any but
     * the flexible nodes.
     *
     * @param h the desired height
     * @param sum the total sum of the glues
     *
     * @see org.extex.typesetter.type.Node#spreadHeight(
     *      org.extex.interpreter.type.dimen.FixedDimen,
     *      org.extex.interpreter.type.glue.FixedGlueComponent)
     */
    public void spreadHeight(final FixedDimen h, final FixedGlueComponent sum) {

    }

    /**
     * Adjust the width of a flexible node. This method is a noop for any but
     * the flexible nodes.
     *
     * @param w the desired width
     * @param sum the total sum of the glues
     *
     * @see org.extex.typesetter.type.Node#spreadWidth(
     *      org.extex.interpreter.type.dimen.FixedDimen,
     *      org.extex.interpreter.type.glue.FixedGlueComponent)
     */
    public void spreadWidth(final FixedDimen w, final FixedGlueComponent sum) {

    }

    /**
     * This method returns the printable representation.
     * This is meant to produce a exhaustive form as it is used in tracing
     * output to the log file.
     *
     * @return the printable representation
     */
    public String toString() {

        StringBuffer sb = new StringBuffer();
        toString(sb, "", Integer.MAX_VALUE, Integer.MAX_VALUE);
        return sb.toString();
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
     * @see org.extex.typesetter.type.Node#toString(
     *      java.lang.StringBuffer,
     *      java.lang.String,
     *      int,
     *      int)
     */
    public void toString(final StringBuffer sb, final String prefix,
            final int b, final int d) {

        sb.append(getLocalizer().format("String.Format"));
    }

    /**
     * Compute a text representation of this object.
     *
     * @param prefix the string prepended to each line of the resulting text
     *
     * @return the text representation of this object
     */
    protected String toText(final String prefix) {

        StringBuffer sb = new StringBuffer();
        toText(sb, prefix);
        return sb.toString();
    }

    /**
     * Puts a text representation of the object into a string buffer.
     *
     * @param sb the output string buffer
     * @param prefix the string prepended to each line of the resulting text
     *
     * @see org.extex.typesetter.type.Node#toText(java.lang.StringBuffer,
     *      java.lang.String)
     */
    public void toText(final StringBuffer sb, final String prefix) {

        sb.append(getLocalizer().format("Text.Format"));
    }
}
