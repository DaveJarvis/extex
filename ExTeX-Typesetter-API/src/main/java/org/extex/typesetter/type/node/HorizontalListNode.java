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

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.FixedGlueComponent;
import org.extex.core.glue.WideGlue;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.OrientedNodeList;

/**
 * This class provides a container for nodes which is interpreted as horizontal
 * list.
 * 
 * @see "<logo>TeX</logo> &ndash; The Program [135]"
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4739 $
 */
public class HorizontalListNode extends GenericNodeList
        implements
            OrientedNodeList {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object. The list is empty initially.
     * 
     * @see "<logo>TeX</logo> &ndash; The Program [136]"
     */
    public HorizontalListNode() {

        super();
    }

    /**
     * Creates a new object. The list is filled with the node given.
     * 
     * @param width the width of the box
     */
    public HorizontalListNode(FixedDimen width) {

        super();
        setWidth(width);
        setTargetWidth(width);
    }

    /**
     * Creates a new object. The hlist is filled with the node given.
     * 
     * @param node the initial node to add
     */
    public HorizontalListNode(Node node) {

        super(node);
    }

    /**
     * Creates a new object. The hlist is initially filled with two nodes.
     * 
     * @param node1 the initial node
     * @param node2 the node to add after node1
     */
    public HorizontalListNode(Node node1, Node node2) {

        super(node1);
        add(node2);
    }

    /**
     * Add a node to the node list at a given position. The position starts with
     * a value of 0 to insert the node before the first existing node and goes
     * up to the length of the list to insert the new node at the end.
     * <p>
     * If the node is <code>null</code> then it is silently ignored. This
     * means that a horizontal list will never contain <code>null</code>
     * values.
     * </p>
     * 
     * @param index the position of insertion
     * @param node the node to add
     * 
     * @throws IndexOutOfBoundsException in case that the index is negative or
     *         greater than the length of the list
     * 
     * @see org.extex.typesetter.type.node.GenericNodeList#add( int,
     *      org.extex.typesetter.type.Node)
     */
    public void add(int index, Node node) throws IndexOutOfBoundsException {

        if (node != null) {
            super.add(index, node);
            advanceWidth(node.getWidth());
            maxHeight(node.getHeight());
            maxDepth(node.getDepth());
        }
    }

    /**
     * Add a node to the node list. The other attributes (width, height, depth)
     * are not modified.
     * <p>
     * If the node is <code>null</code> then it is silently ignored. This
     * means that a horizontal list will never contain <code>null</code>
     * values.
     * </p>
     * 
     * @param node the node to add
     * 
     * @see org.extex.typesetter.type.node.GenericNodeList#add(
     *      org.extex.typesetter.type.Node)
     */
    public void add(Node node) {

        if (node != null) {
            super.add(node);
            advanceWidth(node.getWidth());
            maxHeight(node.getHeight());
            maxDepth(node.getDepth());
        }
    }

    /**
     * Add some glue to the node list. The other attributes (width, height,
     * depth) are not modified.
     * 
     * @param glue the glue to add
     * 
     * @see org.extex.typesetter.type.NodeList#addSkip( FixedGlue)
     */
    public void addSkip(FixedGlue glue) {

        Node gNode = new GlueNode(glue, true);
        gNode.setWidth(glue.getLength());
        add(gNode);
    }

    /**
     * Adjust the variable nodes to achieve a given target width.
     */
    public void hpack() {

        Dimen wd = new Dimen();
        WideGlue wg = new WideGlue();
        int size = size();
        for (int i = 0; i < size; i++) {
            Node node = get(i);
            wd.add(node.getWidth());
            node.addWidthTo(wg);
        }

        Dimen target = getTargetWidth();
        if (target != null && target.ne(wd)) {
            wd.subtract(target);
            wd.negate();
            FixedGlueComponent s = (wd.le(Dimen.ZERO) //
                    ? wg.getShrink() //
                    : wg.getStretch());
            for (int i = 0; i < size; i++) {
                get(i).spreadWidth(wd, s);
            }
            setWidth(target);
        } else {
            setWidth(wd);
        }
    }

    /**
     * Adjust the variable nodes to achieve a given target width.
     * 
     * @param width the new target width
     */
    public void hpack(FixedDimen width) {

        setTargetWidth(width);
        hpack();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.OrientedNode#isHorizontal()
     */
    public boolean isHorizontal() {

        return true;
    }

    /**
     * The <logo>TeX</logo> definition of a hlist states that a box is not
     * variable neither in width nor in height. Thus this method is simply a
     * noop.
     * 
     * @param w the desired width
     * @param sum the total sum of the glues
     * 
     * @see org.extex.typesetter.type.node.AbstractNode#spreadWidth(
     *      org.extex.core.dimen.FixedDimen,
     *      org.extex.core.glue.FixedGlueComponent)
     */
    public void spreadWidth(FixedDimen w, FixedGlueComponent sum) {

        // noop
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

        sb.append("\\hbox");
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

        sb.append("\n");
        sb.append(prefix);
        sb.append("(hlist ");
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

        return visitor.visitHorizontalList(this, value);
    }

}
