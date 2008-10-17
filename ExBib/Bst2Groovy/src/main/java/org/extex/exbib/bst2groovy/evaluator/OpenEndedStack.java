/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bst2groovy.evaluator;

import java.util.ArrayList;
import java.util.List;

import org.extex.exbib.bst2groovy.data.GCode;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OpenEndedStack {

    /**
     * The field <tt>no</tt> contains the counter for next items.
     */
    private static int no = 1;

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    public static void reset() {

        no = 1;
    }

    /**
     * The field <tt>stack</tt> contains the current stack.
     */
    private List<GCode> stack = new ArrayList<GCode>();

    /**
     * The field <tt>future</tt> contains the list of future items.
     */
    private List<GLocal> futures = new ArrayList<GLocal>();

    /**
     * Getter for the futures.
     * 
     * @return the futures
     */
    public List<GLocal> getFutures() {

        return futures;
    }

    /**
     * Getter for the stack.
     * 
     * @return the stack
     */
    public List<GCode> getStack() {

        return stack;
    }

    /**
     * Pop the topmost element from the stack. If the stack is empty a new
     * future element is created.
     * 
     * @return the topmost element or a new var
     */
    public GCode pop() {

        if (!stack.isEmpty()) {
            return stack.remove(stack.size() - 1);
        }
        GLocal ret = new GLocal("v" + Integer.toString(no++));
        futures.add(ret);
        return ret;
    }

    /**
     * Push some code to the stack.
     * 
     * @param code the code to push
     */
    public void push(GCode code) {

        stack.add(code);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();
        buffer.append("<");
        buffer.append(Integer.toString(futures.size()));
        buffer.append("> ");
        for (GCode c : stack) {
            buffer.append(c.toString());
            buffer.append(" ");
        }
        return buffer.toString();
    }

}
