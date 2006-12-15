/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.typesetter.type.node;

import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.exception.ImpossibleException;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.glue.FixedGlueComponent;
import org.extex.interpreter.type.glue.WideGlue;
import org.extex.type.UnicodeChar;
import org.extex.util.exception.GeneralException;

import de.dante.extex.typesetter.Typesetter;
import de.dante.extex.typesetter.type.Node;
import de.dante.extex.typesetter.type.NodeIterator;
import de.dante.extex.typesetter.type.NodeList;
import de.dante.extex.typesetter.type.NodeVisitor;

/**
 * This class exposes itself as character node but contains an hlist internally.
 * This class is used to represent composed characters from virtual fonts.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class VirtualCharNode extends CharNode implements NodeList {

    /**
     * This inner class provides the means to store nodes in a list.
     * It is here to compensate the missing multiple inheritance of Java.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    private class NL extends GenericNodeList {

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
         * @see org.extex.typesetter.type.NodeList#addSkip(
         *      org.extex.interpreter.type.glue.FixedGlue)
         */
        public void addSkip(final FixedGlue glue) {

            // glues are ignored
        }

        /**
         * @see org.extex.typesetter.type.node.AbstractNodeList#updateDimensions(
         *      org.extex.typesetter.type.Node, boolean)
         */
        protected void updateDimensions(final Node n, final boolean first) {

            // This should not be needed
            throw new RuntimeException("unimplemented");
        }

        /**
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
     * @see org.extex.typesetter.type.NodeList#add(int,
     *      org.extex.typesetter.type.Node)
     */
    public void add(final int index, final Node node) {

        this.nodes.add(index, node);
    }

    /**
     * @see org.extex.typesetter.type.NodeList#add(
     *      org.extex.typesetter.type.Node)
     */
    public void add(final Node node) {

        this.nodes.add(node);
    }

    /**
     * @see org.extex.typesetter.type.NodeList#addSkip(
     *      org.extex.interpreter.type.glue.FixedGlue)
     */
    public void addSkip(final FixedGlue glue) {

        this.nodes.addSkip(glue);
    }

    /**
     * @see org.extex.typesetter.type.node.AbstractNode#addWidthTo(
     *      org.extex.interpreter.type.glue.WideGlue)
     */
    public void addWidthTo(final WideGlue glue) {

        this.nodes.addWidthTo(glue);
    }

    /**
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
     * @see org.extex.typesetter.type.NodeList#clear()
     */
    public void clear() {

        this.nodes.clear();
    }

    /**
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
     * @see org.extex.typesetter.type.node.CharNode#countChars()
     */
    public int countChars() {

        return this.nodes.countChars();
    }

    /**
     * @see org.extex.typesetter.type.NodeList#get(int)
     */
    public Node get(final int index) {

        return this.nodes.get(index);
    }

    /**
     * @see org.extex.typesetter.type.node.CharNode#getChars()
     */
    public CharNode[] getChars() {

        return this.nodes.getChars();
    }

    /**
     * @see org.extex.typesetter.type.Node#getDepth()
     */
    public FixedDimen getDepth() {

        return this.nodes.getDepth();
    }

    /**
     * @see org.extex.typesetter.type.Node#getHeight()
     */
    public FixedDimen getHeight() {

        return this.nodes.getHeight();
    }

    /**
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
     * @see org.extex.typesetter.type.NodeList#getShift()
     */
    public Dimen getShift() {

        return this.nodes.getShift();
    }

    /**
     * @see org.extex.typesetter.type.Node#getVerticalSize()
     */
    public FixedDimen getVerticalSize() {

        return this.nodes.getVerticalSize();
    }

    /**
     * @see org.extex.typesetter.type.Node#getWidth()
     */
    public FixedDimen getWidth() {

        return this.nodes.getWidth();
    }

    /**
     * @see org.extex.typesetter.type.NodeList#iterator()
     */
    public NodeIterator iterator() {

        return this.nodes.iterator();
    }

    /**
     * @see org.extex.typesetter.type.NodeList#remove(int)
     */
    public Node remove(final int index) {

        return this.nodes.remove(index);
    }

    /**
     * @see org.extex.typesetter.type.Node#setDepth(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setDepth(final FixedDimen depth) {

        this.nodes.setDepth(depth);
    }

    /**
     * @see org.extex.typesetter.type.Node#setHeight(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setHeight(final FixedDimen height) {

        this.nodes.setHeight(height);
    }

    /**
     * @see org.extex.typesetter.type.NodeList#setMove(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setMove(final FixedDimen d) {

        this.nodes.setMove(d);
    }

    /**
     * @see org.extex.typesetter.type.NodeList#setShift(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setShift(final FixedDimen d) {

        this.nodes.setShift(d);
    }

    /**
     * @see org.extex.typesetter.type.Node#setWidth(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setWidth(final FixedDimen width) {

        this.nodes.setWidth(width);
    }

    /**
     * @see org.extex.typesetter.type.NodeList#size()
     */
    public int size() {

        return this.nodes.size();
    }

    /**
     * @see org.extex.typesetter.type.Node#spreadWidth(
     *      org.extex.interpreter.type.dimen.FixedDimen,
     *      org.extex.interpreter.type.glue.FixedGlueComponent)
     */
    public void spreadWidth(final FixedDimen width, final FixedGlueComponent sum) {

        this.nodes.spreadWidth(width, sum);
    }

    /**
     * @see org.extex.typesetter.type.Node#visit(
     *      org.extex.typesetter.type.NodeVisitor,
     *      java.lang.Object)
     */
    public Object visit(final NodeVisitor visitor, final Object value)
            throws GeneralException {

        return visitor.visitVirtualChar(this, value);
    }

}
