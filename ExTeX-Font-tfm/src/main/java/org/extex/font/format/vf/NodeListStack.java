/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.vf;

import java.util.Stack;

import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.HorizontalListNode;

/**
 * Stack for the {@link NodeList}.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class NodeListStack {

    /**
     * The stack.
     */
    private Stack<NodeList> stack;

    /**
     * Creates a new object.
     */
    public NodeListStack() {

        stack = new Stack<NodeList>();
    }

    /**
     * 
     * @see java.util.Vector#clear()
     */
    public void clear() {

        stack.clear();
    }

    /**
     * Check, if the last element is a horizontal box.
     * 
     * @return Returns <code>true</code>, if the last element is a horizontal
     *         box, otherwise <code>false</code>.
     */
    public boolean isHBox() {

        if (!stack.empty()) {
            NodeList nodelist = stack.lastElement();
            if (nodelist instanceof HorizontalListNode) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return
     * @see java.util.Stack#pop()
     */
    public NodeList pop() {

        return stack.pop();
    }

    /**
     * @param item
     * @return
     * @see java.util.Stack#push(java.lang.Object)
     */
    public NodeList push(NodeList item) {

        return stack.push(item);
    }

    /**
     * @return
     * @see java.util.Vector#size()
     */
    public int size() {

        return stack.size();
    }

}
