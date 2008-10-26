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

package org.extex.exbib.bst2groovy.data.processor;

import java.util.ArrayList;
import java.util.List;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.data.local.GLocal;
import org.extex.exbib.bst2groovy.data.local.InitLocal;
import org.extex.exbib.bst2groovy.data.types.GInt;
import org.extex.exbib.bst2groovy.data.types.GString;

/**
 * This class implements a stack of GCode which returns new instances of GLocal
 * when an attempt is made to pop an element and the stack is empty. In addition
 * some code can be stored in the processor state.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ProcessorState {

    /**
     * The field <tt>no</tt> contains the counter for next items.
     */
    private static int no = 1;

    /**
     * Reset the internal numbering for generated locals.
     */
    public static void reset() {

        no = 1;
    }

    /**
     * The field <tt>stack</tt> contains the current stack.
     */
    private List<GCode> stack = new ArrayList<GCode>();

    /**
     * The field <tt>locals</tt> contains the list of future items.
     */
    private List<GLocal> locals = new ArrayList<GLocal>();

    /**
     * The field <tt>localPrefix</tt> contains the prefix for the name of new
     * instances of GLocal.
     */
    private String localPrefix = "v";

    /**
     * The field <tt>code</tt> contains the finished code.
     */
    private GCodeContainer code = new GCodeContainer();

    /**
     * Add an element to the code list.
     * 
     * @param element the element to add
     * 
     * @see java.util.ArrayList#add(java.lang.Object)
     */
    public void add(GCode element) {

        eliminateSideEffects();
        code.add(element);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    public void eliminateSideEffects() {

        int stackSize = stack.size();
        for (int i = 0; i < stackSize; i++) {
            GCode si = stack.get(i);
            if (!(si instanceof GLocal) && !(si instanceof GInt)
                    && !(si instanceof GString)) {
                GLocal v = new GLocal(localPrefix + Integer.toString(no++));
                code.add(new InitLocal(v, si));
                stack.remove(i);
                stack.add(i, v);
            }

        }
    }

    /**
     * Getter for the code.
     * 
     * @return the code
     */
    public GCodeContainer getCode() {

        return code;
    }

    /**
     * Getter for the locals.
     * 
     * @return the locals
     */
    public List<GLocal> getLocals() {

        return locals;
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
        GLocal ret = new GLocal(localPrefix + Integer.toString(no++));
        locals.add(ret);
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
     * Setter for the localPrefix.
     * 
     * @param localPrefix the localPrefix to set
     */
    public void setLocalPrefix(String localPrefix) {

        this.localPrefix = localPrefix;
    }

    /**
     * Getter for the size.
     * 
     * @return the size
     * 
     * @see java.util.List#size()
     */
    public int size() {

        return stack.size();
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
        boolean first = true;
        for (GLocal c : locals) {
            if (first) {
                first = false;
                buffer.append("\t");
            } else {
                buffer.append("\n\t");
            }
            buffer.append(c.toString());
        }
        buffer.append("> [");
        first = true;
        for (GCode c : stack) {
            if (first) {
                first = false;
                buffer.append("\t");
            } else {
                buffer.append("\n\t");
            }
            buffer.append(c.toString());
        }
        buffer.append("]: ");
        buffer.append(code.toString());
        return buffer.toString();
    }

}
