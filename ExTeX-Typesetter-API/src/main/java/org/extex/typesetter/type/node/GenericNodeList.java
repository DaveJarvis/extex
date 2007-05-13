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

import java.util.ArrayList;
import java.util.Iterator;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.ImpossibleException;
import org.extex.core.glue.FixedGlue;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;

/**
 * Abstract base class for all <code>NodeList</code>s.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4739 $
 */
public class GenericNodeList extends AbstractNode implements NodeList {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 20060417L;

    /**
     * The field <tt>list</tt> is the container for the elements of this node
     * list.
     */
    private ArrayList<Node> list = new ArrayList<Node>();

    /**
     * The field <tt>move</tt> contains the offset of the reference point in
     * vertical direction.
     */
    private Dimen move = new Dimen(0);

    /**
     * The field <tt>shift</tt> contains the offset of the reference point in
     * horizontal direction.
     */
    private Dimen shift = new Dimen(0);

    /**
     * The field <tt>targetDepth</tt> contains the requested depth of the node
     * list.
     */
    private Dimen targetDepth = null;

    /**
     * The field <tt>targetHeight</tt> contains the requested height of the
     * node list.
     */
    private Dimen targetHeight = null;

    /**
     * The field <tt>targetWidth</tt> contains the requested width of the node
     * list.
     */
    private Dimen targetWidth = null;

    /**
     * Creates a new object.
     */
    public GenericNodeList() {

        super();
    }

    /**
     * Creates a new object. The list is filled with the node given.
     * 
     * @param node the node to add initially
     */
    public GenericNodeList(Node node) {

        super();
        add(node);
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
    public void add(int index, Node node) {

        if (node != null) {
            list.add(index, node);
        }
    }

    /**
     * Add a node to the node list. The other attributes (width, height, depth)
     * are not modified.
     * 
     * @param node the node to add
     * 
     * @see org.extex.typesetter.type.NodeList#add(
     *      org.extex.typesetter.type.Node)
     */
    public void add(Node node) {

        if (node != null) {
            list.add(node);
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

        throw new UnsupportedOperationException(getClass().getName()
                + "#addSkip()");
    }

    /**
     * Remove all nodes from the list. The list is empty afterwards. The
     * dimensions are reset to zero unless target sizes are specified. In this
     * case the target sizes are used.
     * 
     * @see org.extex.typesetter.type.NodeList#clear()
     */
    public void clear() {

        list.clear();
        setWidth(Dimen.ZERO_PT);
        setHeight(Dimen.ZERO_PT);
        setDepth(Dimen.ZERO_PT);
    }

    /**
     * Clone the current object.
     * 
     * @return the copy
     * 
     * @see org.extex.typesetter.type.NodeList#copy()
     */
    @SuppressWarnings("unchecked")
    public NodeList copy() {

        try {
            GenericNodeList clone = (GenericNodeList) this.clone();
            clone.list = (ArrayList<Node>) list.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new ImpossibleException(e);
        }
    }

    /**
     * This method determines the number of characters contained in a node.
     * 
     * @return the number of characters contained
     * 
     * @see org.extex.typesetter.type.Node#countChars()
     */
    public int countChars() {

        int count = 0;
        for (Node n : list) {
            count += n.countChars();
        }
        return count;
    }

    /**
     * Test whether the node list is empty.
     * 
     * @return <code>true</code>, if the <code>NodeList</code> is empty,
     *         otherwise <code>false</code>.
     */
    public boolean isEmpty() {

        return (list.size() == 0);
    }

    /**
     * Getter for a node at a given position.
     * 
     * @param index the position
     * 
     * @return the node at position <i>index</i> of <code>null</code> if
     *         index is out of bounds
     * 
     * @see org.extex.typesetter.type.NodeList#get(int)
     */
    public Node get(int index) {

        return list.get(index);
    }

    /**
     * Getter for the array of characters enclosed in this node.
     * 
     * @return the array of characters
     * 
     * @see org.extex.typesetter.type.Node#getChars()
     */
    public CharNode[] getChars() {

        int len = countChars();
        CharNode[] chars = new CharNode[len];
        if (len == 0) {
            return chars;
        }
        int idx = 0;
        for (int i = 0; i < list.size(); i++) {
            CharNode[] uca = list.get(i).getChars();
            for (int j = 0; j < uca.length; j++) {
                chars[idx++] = uca[j];
            }
        }

        return chars;
    }

    /**
     * Getter for the move value of the node list. The move parameter describes
     * how far from its original position the box is moved leftwards or
     * rightwards. Positive values indicate a move rightwards.
     * 
     * @return the move value
     * 
     * @see org.extex.typesetter.type.NodeList#getMove()
     */
    public Dimen getMove() {

        return move;
    }

    /**
     * Getter for the shift value of the node list. The shift parameter
     * describes how far from its original position the box is shifted up or
     * down. Positive values indicate a shift downwards.
     * 
     * @return the shift value
     * 
     * @see org.extex.typesetter.type.NodeList#getShift()
     */
    public Dimen getShift() {

        return shift;
    }

    /**
     * Getter for targetDepth.
     * 
     * @return the targetDepth.
     */
    public Dimen getTargetDepth() {

        return targetDepth;
    }

    /**
     * Getter for targetHeight.
     * 
     * @return the targetHeight.
     */

    public Dimen getTargetHeight() {

        return targetHeight;
    }

    /**
     * Getter for targetWidth.
     * 
     * @return the targetWidth.
     */
    public Dimen getTargetWidth() {

        return targetWidth;
    }

    /**
     * Get a new iterator for all nodes in the list. This method is just
     * provided for completeness. Consider a conventional loop because of
     * performance issues.
     * 
     * @return the iterator for all nodes in the list
     * 
     * @see org.extex.typesetter.type.NodeList#iterator()
     */
    public Iterator<Node> iterator() {

        return list.iterator();
    }

    /**
     * Remove an element at a given position. The other attributes (width,
     * height, depth) are not modified.
     * 
     * @param index the position
     * 
     * @return the element previously located at position <i>index</i>
     * 
     * @see org.extex.typesetter.type.NodeList#remove(int)
     */
    public Node remove(int index) {

        return list.remove(index);
    }

    /**
     * Setter for the move value of the node list. The move parameter describes
     * how far from its original position the box is moved leftwards or
     * rightwards. Positive values indicate a move rightwards.
     * 
     * @param d the move value
     * 
     * @see org.extex.typesetter.type.NodeList#setMove(
     *      org.extex.core.dimen.FixedDimen)
     */
    public void setMove(FixedDimen d) {

        move.set(d);
    }

    /**
     * Setter for the shift value of the node list. The shift parameter
     * describes how far from its original position the box is shifted up or
     * down. Positive values indicate a shift downwards.
     * 
     * @param d the amount to be shifted
     * 
     * @see org.extex.typesetter.type.NodeList#setShift(
     *      org.extex.core.dimen.FixedDimen)
     */
    public void setShift(FixedDimen d) {

        shift.set(d);
    }

    /**
     * Setter for the target depth.
     * 
     * @param depth the target depth to set.
     */
    public void setTargetDepth(FixedDimen depth) {

        if (this.targetDepth == null) {
            this.targetDepth = new Dimen(depth);
        } else {
            this.targetDepth.set(depth);
        }
    }

    /**
     * Setter for the target height.
     * 
     * @param height the target height to set.
     */
    public void setTargetHeight(FixedDimen height) {

        if (this.targetHeight == null) {
            this.targetHeight = new Dimen(height);
        } else {
            this.targetHeight.set(height);
        }
    }

    /**
     * Setter for the target width.
     * 
     * @param width the target width to set.
     */
    public void setTargetWidth(FixedDimen width) {

        if (this.targetWidth == null) {
            this.targetWidth = new Dimen(width);
        } else {
            this.targetWidth.set(width);
        }
    }

    /**
     * Return the size of the <code>NodeList</code>.
     * 
     * @return the size of the <code>NodeList</code>
     */
    public int size() {

        return list.size();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {

        StringBuffer sb = new StringBuffer();
        toString(sb, "\n", Integer.MAX_VALUE, Integer.MAX_VALUE);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.AbstractNode#toString(java.lang.StringBuffer,
     *      java.lang.String, int, int)
     */
    public void toString(StringBuffer sb, String prefix, int breadth, int depth) {

        sb.append("(");
        sb.append(getHeight().toString());
        FixedDimen d = getDepth();
        if (d.ge(Dimen.ZERO)) {
            sb.append("+");
        }
        sb.append(d.toString());
        sb.append(")x");
        sb.append(getWidth().toString());

        if (shift.getValue() != 0) {
            sb.append(", shifted ");
            sb.append(shift.toString());
        }

        if (move.getValue() != 0) {
            sb.append(", moved ");
            sb.append(move.toString());
        }

        String prefix2 = prefix + ".";

        for (int i = 0; i < list.size() && i < breadth; i++) {
            sb.append(prefix2);
            if (depth >= 0) {
                list.get(i).toString(sb, prefix2, breadth, depth - 1);
            }
        }
    }

    /**
     * Provides a string representation of the current instance.
     * 
     * @return the String representation of the object
     * @see "<logo>TeX</logo> &ndash; The Program [182]"
     */
    public String toText() {

        StringBuffer sb = new StringBuffer();
        toText(sb, "");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.Node#toText(java.lang.StringBuffer,
     *      java.lang.String)
     */
    public void toText(StringBuffer sb, String prefix) {

        String p = prefix + "  ";

        for (Node n : list) {
            n.toText(sb, p);
        }

        sb.append(")");
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

        throw new ImpossibleException(getClass().getName() + "#visit()");
    }

}
