/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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
import java.util.Collections;
import java.util.List;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.data.types.GIntegerConstant;
import org.extex.exbib.bst2groovy.data.types.GStringConstant;
import org.extex.exbib.bst2groovy.data.var.DeclareVar;
import org.extex.exbib.bst2groovy.data.var.Var;

/**
 * This class implements a stack of GCode which returns new instances of Var
 * when an attempt is made to pop an element and the stack is empty. In addition
 * some code can be stored in the processor state.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ProcessorState {

    /**
     * The field <tt>stack</tt> contains the current stack.
     */
    private List<GCode> stack = new ArrayList<GCode>();

    /**
     * The field <tt>locals</tt> contains the list of future items.
     */
    private List<Var> locals = new ArrayList<Var>();

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
     * Eliminate the side effects by introducing intermediary variables.
     */
    public void eliminateSideEffects() {

        int stackSize = stack.size();
        for (int i = 0; i < stackSize; i++) {
            GCode si = stack.get(i);
            if (!(si instanceof Var) && !(si instanceof GIntegerConstant)
                    && !(si instanceof GStringConstant)) {
                Var v = Var.makeVar();
                code.add(new DeclareVar(v, si));
                stack.remove(i);
                stack.add(i, v);
            }
        }
    }

    /**
     * For a list of variables get the corresponding element from the stack.
     * This element is unified with the variable it it is a variable or a
     * declaration is added to the code.
     * 
     * @param list the list of variables to fix
     */
    public void fix(List<Var> list) {

        Collections.reverse(list);
        for (Var x : list) {
            GCode v = pop();
            if (v instanceof Var) {
                ((Var) v).unify(x);
            } else {
                add(new DeclareVar(x, v));
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
    public List<Var> getLocals() {

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
     * Optimize the code contained herein.
     * 
     */
    public void optimize() {

        for (int i = 0; i < code.size();) {
            i = code.get(i).optimize(code, i);
        }
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
        Var ret = Var.makeVar();
        locals.add(ret);
        return ret;
    }

    /**
     * Push some code to the stack.
     * 
     * @param gcode the code to push
     */
    public void push(GCode gcode) {

        stack.add(gcode);
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
        for (Var c : locals) {
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
