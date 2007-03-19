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

package org.extex.typesetter.type.node;

import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.FixedGlueComponent;
import org.extex.core.glue.WideGlue;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.exception.ImpossibleException;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeIterator;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;

/**
 * This class exposes itself as character node but contains an hlist internally.
 * This class is used to represent composed characters from virtual fonts.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4739 $
 */
public class VirtualCharNode extends CharNode implements NodeList {

    /**
     * This inner class provides the means to store nodes in a list.
     * It is here to compensate the missing multiple inheritance of Java.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4739 $
     */
    private static class NL extends GenericNodeList {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for serialization.
         */
        protected static final long serialVersionUID = 2005L;

        /**
         * The field <tt>node</tt> contains the parent node.
         */
        private VirtualCharNode node;

        /**
         * Creates a new object.
         *
         * @param node the parent node
         */
        public NL(final VirtualCharNode node) {

            super();
            this.node = node;
        }

        /**
         * Add some glue to the node list.
         * The other attributes (width, height, depth) are not modified.
         *
         * @param glue the glue to add
         *
         * @see org.extex.typesetter.type.NodeList#addSkip(
         *      org.extex.core.glue.FixedGlue)
         */
        public void addSkip(final FixedGlue glue) {

            // glues are ignored
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
         *      org.extex.typesetter.type.NodeVisitor,
         *      java.lang.Object)
         */
        public Object visit(final NodeVisitor visitor, final Object value)
                throws GeneralException {

            return visitor.visitChar(node, value);
        }
    }

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The field <tt>nodes</tt> contains the encapsulated node list.
     */
    private NodeList nodes;

    /**
     * Creates a new object.
     *
     * @param context the typesetting context
     * @param uc the character represented by this node
     */
    public VirtualCharNode(final TypesettingContext context,
            final UnicodeChar uc) {

        super(context, uc);
        nodes = new NL(this);
    }

    /**
     * Add a node to the node list at a given position.
     *
     * @param index the position of insertion
     * @param node the node to add
     *
     * @see org.extex.typesetter.type.NodeList#add(int,
     *      org.extex.typesetter.type.Node)
     */
    public void add(final int index, final Node node) {

        this.nodes.add(index, node);
    }

    /**
     * Add a node to the node list.
     * The other attributes (width, height, depth) are not modified.
     *
     * @param node the node to add
     *
     * @see org.extex.typesetter.type.NodeList#add(
     *      org.extex.typesetter.type.Node)
     */
    public void add(final Node node) {

        this.nodes.add(node);
    }

    /**
     * Add some glue to the node list.
     * The other attributes (width, height, depth) are not modified.
     *
     * @param glue the glue to add
     *
     * @see org.extex.typesetter.type.NodeList#addSkip(
     *      org.extex.core.glue.FixedGlue)
     */
    public void addSkip(final FixedGlue glue) {

        this.nodes.addSkip(glue);
    }

    /**
     * Add the flexible width of the current node to the given glue.
     *
     * @param glue the glue to add to.
     *
     * @see org.extex.typesetter.type.node.AbstractNode#addWidthTo(
     *      org.extex.core.glue.WideGlue)
     */
    public void addWidthTo(final WideGlue glue) {

        this.nodes.addWidthTo(glue);
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
     *      org.extex.typesetter.type.NodeVisitor, boolean)
     */
    public Node atShipping(final Context context, final Typesetter typesetter,
            final NodeVisitor visitor, final boolean inHMode)
            throws GeneralException {

        return this.nodes.atShipping(context, typesetter, visitor, inHMode);
    }

    /**
     * Remove all nodes from the list. The list is empty afterwards.
     * The dimensions are reset to zero unless target sizes are specified.
     * In this case the target sizes are used.
     *
     * @see org.extex.typesetter.type.NodeList#clear()
     */
    public void clear() {

        this.nodes.clear();
    }

    /**
     * Clone the current object.
     *
     * @return the copy
     *
     * @see org.extex.typesetter.type.NodeList#copy()
     */
    public NodeList copy() {

        try {
            return (NodeList) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new ImpossibleException(e);
        }
    }

    /**
     * This method determines the number of characters contained in a node.
     *
     * @return the number of characters contained
     *
     * @see org.extex.typesetter.type.node.CharNode#countChars()
     */
    public int countChars() {

        return this.nodes.countChars();
    }

    /**
     * Getter for a node at a given position.
     *
     * @param index the position
     *
     * @return the node at position <i>index</i> of <code>null</code> if index
     * is out of bounds
     *
     * @see org.extex.typesetter.type.NodeList#get(int)
     */
    public Node get(final int index) {

        return this.nodes.get(index);
    }

    /**
     * Getter for the array of characters enclosed in this node.
     *
     * @return the array of characters
     *
     * @see org.extex.typesetter.type.node.CharNode#getChars()
     */
    public CharNode[] getChars() {

        return this.nodes.getChars();
    }

    /**
     * Getter for the depth of the node.
     *
     * @return the depth
     *
     * @see org.extex.typesetter.type.Node#getDepth()
     */
    public FixedDimen getDepth() {

        return this.nodes.getDepth();
    }

    /**
     * Getter for the height of the node.
     *
     * @return the height
     *
     * @see org.extex.typesetter.type.Node#getHeight()
     */
    public FixedDimen getHeight() {

        return this.nodes.getHeight();
    }

    /**
     * Getter for the move value of the node list.
     * The move parameter describes how far from its original position the box
     * is moved leftwards or rightwards. Positive values indicate a move
     * rightwards.
     *
     * @return the move value
     *
     * @see org.extex.typesetter.type.NodeList#getMove()
     */
    public Dimen getMove() {

        return this.nodes.getMove();
    }

    /**
     * Getter for nodes.
     *
     * @return the nodes
     */
    public NodeList getNodes() {

        return this.nodes;
    }

    /**
     * Getter for the shift value of the node list.
     * The shift parameter describes how far from its original position the box
     * is shifted up or down. Positive values indicate a shift downwards.
     *
     * @return the shift value
     *
     * @see org.extex.typesetter.type.NodeList#getShift()
     */
    public Dimen getShift() {

        return this.nodes.getShift();
    }

    /**
     * Compute the vertical size of a node.
     * The vertical size is the size of the box enclosing the bounding
     * box and containing the base line.
     *
     * @return the vertical size
     *
     * @see org.extex.typesetter.type.Node#getVerticalSize()
     */
    public FixedDimen getVerticalSize() {

        return this.nodes.getVerticalSize();
    }

    /**
     * Getter for the width of the node.
     *
     * @return the width
     *
     * @see org.extex.typesetter.type.Node#getWidth()
     */
    public FixedDimen getWidth() {

        return this.nodes.getWidth();
    }

    /**
     * Get a new iterator for all nodes in the list.
     * This method is just provided for completeness. Consider a conventional
     * loop because of performance issues.
     *
     * @return the iterator for all nodes in the list
     *
     * @see org.extex.typesetter.type.NodeList#iterator()
     */
    public NodeIterator iterator() {

        return this.nodes.iterator();
    }

    /**
     * Remove an element at a given position.
     * The other attributes (width, height, depth) are not modified.
     *
     * @param index the position
     *
     * @return the element previously located at position <i>index</i>
     *
     * @see org.extex.typesetter.type.NodeList#remove(int)
     */
    public Node remove(final int index) {

        return this.nodes.remove(index);
    }

    /**
     * Setter for the depth of the node.
     *
     * @param depth the node depth
     *
     * @see org.extex.typesetter.type.Node#setDepth(
     *      org.extex.core.dimen.FixedDimen)
     */
    public void setDepth(final FixedDimen depth) {

        this.nodes.setDepth(depth);
    }

    /**
     * Setter for the height of the node.
     *
     * @param height the new height
     *
     * @see org.extex.typesetter.type.Node#setHeight(
     *      org.extex.core.dimen.FixedDimen)
     */
    public void setHeight(final FixedDimen height) {

        this.nodes.setHeight(height);
    }

    /**
     * Setter for the move value of the node list.
     * The move parameter describes how far from its original position the box
     * is moved leftwards or rightwards. Positive values indicate a move
     * rightwards.
     *
     * @param d the move value
     *
     * @see org.extex.typesetter.type.NodeList#setMove(
     *      org.extex.core.dimen.FixedDimen)
     */
    public void setMove(final FixedDimen d) {

        this.nodes.setMove(d);
    }

    /**
     * Setter for the shift value of the node list.
     * The shift parameter describes how far from its original position the box
     * is shifted up or down. Positive values indicate a shift downwards.
     *
     * @param d the amount to be shifted
     *
     * @see org.extex.typesetter.type.NodeList#setShift(
     *      org.extex.core.dimen.FixedDimen)
     */
    public void setShift(final FixedDimen d) {

        this.nodes.setShift(d);
    }

    /**
     * Setter for the width of the node.
     *
     * @param width the new width
     *
     * @see org.extex.typesetter.type.Node#setWidth(
     *      org.extex.core.dimen.FixedDimen)
     */
    public void setWidth(final FixedDimen width) {

        this.nodes.setWidth(width);
    }

    /**
     * Getter for the number of elements of the list.
     *
     * @return the length of the list
     *
     * @see org.extex.typesetter.type.NodeList#size()
     */
    public int size() {

        return this.nodes.size();
    }

    /**
     * Adjust the width of a flexible node. This method is a noop for any but
     * the flexible nodes.
     *
     * @param width the desired width
     * @param sum the total sum of the glues
     *
     * @see org.extex.typesetter.type.Node#spreadWidth(
     *      org.extex.core.dimen.FixedDimen,
     *      org.extex.core.glue.FixedGlueComponent)
     */
    public void spreadWidth(final FixedDimen width, final FixedGlueComponent sum) {

        this.nodes.spreadWidth(width, sum);
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
     *      org.extex.typesetter.type.NodeVisitor,
     *      java.lang.Object)
     */
    public Object visit(final NodeVisitor visitor, final Object value)
            throws GeneralException {

        return visitor.visitVirtualChar(this, value);
    }

}
