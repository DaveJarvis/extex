/*
 * Copyright (C) 2003-2004 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */
package de.dante.extex.interpreter.type.node;

import de.dante.extex.interpreter.type.Dimen;
import de.dante.extex.interpreter.type.Glue;
import de.dante.extex.typesetter.Node;
import de.dante.extex.typesetter.NodeIterator;
import de.dante.extex.typesetter.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for all <code>NodeList</code>s.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class AbstractNodeList extends AbstractNode
        implements NodeList {

    /**
     * The field <tt>shift</tt> contains the offset of the reference point in
     * vertical direction.
     */
    private Dimen shift = new Dimen(0);

    /**
     * The field <tt>move</tt> contains the offset of the reference point in
     * horizontal direction.
     */
    private Dimen move = new Dimen(0);

    /**
     * The field <tt>targetWidth</tt> contains the requested width of the node
     * list.
     */
    private Dimen targetWidth = null;

    /**
     * The field <tt>targetDepth</tt> contains the requested depth of the node
     * list.
     */
    private Dimen targetDepth = null;

    /**
     * The field <tt>targetHeight</tt> contains the requested height of the node
     * list.
     */
    private Dimen targetHeight = null;

    /**
     * The field <tt>list</tt> is the container for the elements of this node
     * list.
     */
    private List list = new ArrayList();

    /**
     * Creates a new object.
     */
    public AbstractNodeList() {
        super();
    }

    /**
     * @see de.dante.extex.typesetter.NodeList#getMove()
     */
    public Dimen getMove() {
        return move;
    }

    /**
     * @see de.dante.extex.typesetter.NodeList#setMove(de.dante.extex.interpreter.type.Dimen)
     */
    public void setMove(final Dimen d) {
        move.set(d);
    }

    /**
     * @see de.dante.extex.typesetter.NodeList#getShift()
     */
    public Dimen getShift() {
        return shift;
    }

    /**
     * @see de.dante.extex.typesetter.NodeList#setShift(de.dante.extex.interpreter.type.Dimen)
     */
    public void setShift(final Dimen d) {
        shift.set(d);
    }

    /**
     * @see de.dante.extex.typesetter.NodeList#add(de.dante.extex.typesetter.Node)
     */
    public void add(final Node node) {
        list.add(node);
        updateDimensions(node);
    }

    /**
     * ...
     *
     * @param node ...
     */
    protected abstract void updateDimensions(final Node node);

    /**
     * Getter for targetDepth.
     *
     * @return the targetDepth.
     */
    public Dimen getTargetDepth() {
        return targetDepth;
    }
    /**
     * Setter for targetDepth.
     *
     * @param targetDepth the targetDepth to set.
     */
    public void setTargetDepth(final Dimen targetDepth) {
        this.targetDepth = targetDepth;
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
     * Setter for targetHeight.
     *
     * @param targetHeight the targetHeight to set.
     */
    public void setTargetHeight(final Dimen targetHeight) {
        this.targetHeight = targetHeight;
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
     * Setter for targetWidth.
     *
     * @param targetWidth the targetWidth to set.
     */
    public void setTargetWidth(final Dimen targetWidth) {
        this.targetWidth = targetWidth;
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
     * Return <code>true</code>, if the <code>NodeList</code> ist emtpy,
     * otherwise <code>false</code>.
     */
    public boolean empty() {
        return (list.size() == 0);
    }

    /**
     * @see de.dante.extex.typesetter.NodeList#addGlyph(de.dante.extex.interpreter.type.node.CharNode)
     */
    public void addGlyph(final CharNode node) {
        add(node);
    }

    /**
     * @see de.dante.extex.typesetter.NodeList#addSkip(de.dante.extex.interpreter.type.Glue)
     */
    public abstract void addSkip(final Glue glue);

    /**
     * @see de.dante.extex.typesetter.NodeList#iterator()
     */
    public NodeIterator iterator() {
        return new NodeIterator(list);
    }

    /**
     * ...
     *
     * @return the String representation of the object
     * @see "TeX -- The Program [182]"
     */
    public String toText() {
        StringBuffer sb = new StringBuffer();
        toText(sb, "");
        return sb.toString();
    }

    /**
     * @see de.dante.extex.typesetter.Node#toText(java.lang.StringBuffer,
     *      java.lang.String)
     */
    public void toText(final StringBuffer sb, final String prefix) {
        sb.append("(");
        sb.append(prefix);

        for (int i = 0; i < list.size(); i++) {
            ((Node) list.get(i)).toText(sb, prefix + ".");
        }

        sb.append(")");
    }

    /**
     * @see de.dante.extex.typesetter.Node#toString(java.lang.StringBuffer,
     *      java.lang.String)
     */
    public void toString(final StringBuffer sb, final String prefix) {
        sb.append("(");
        sb.append(getHeight().toString());
        sb.append("+");
        sb.append(getDepth().toString());
        sb.append(")x");
        sb.append(getWidth().toString());

        if (shift.getValue() != 0) {
            sb.append(", shifted ");
            sb.append(shift.toString());
        }

        String prefix2 = prefix + ".";

        for (int i = 0; i < list.size(); i++) {
            sb.append(prefix2);
            ((Node) list.get(i)).toString(sb, prefix2);
        }
    }

}
