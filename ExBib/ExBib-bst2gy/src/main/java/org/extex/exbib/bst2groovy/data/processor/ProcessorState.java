/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.data.types.GIntegerConstant;
import org.extex.exbib.bst2groovy.data.types.GStringConstant;
import org.extex.exbib.bst2groovy.data.var.DeclareVar;
import org.extex.exbib.bst2groovy.data.var.Var;
import org.extex.exbib.bst2groovy.data.var.VarManager;

/**
 * This class implements a stack of GCode which returns new instances of Var
 * when an attempt is made to pop an element and the stack is empty. In addition
 * some code can be stored in the processor state.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ProcessorState {

    /**
     * The field {@code code} contains the finished code.
     */
    private final GCodeContainer code = new GCodeContainer();

    /**
     * The field {@code locals} contains the list of future items. They will be
     * translated into local variables later on.
     */
    private final List<Var> locals = new ArrayList<Var>();

    /**
     * The field {@code stack} contains the current stack.
     */
    private final List<GCode> stack = new ArrayList<GCode>();

    /**
     * The field {@code varInfo} contains the variable information.
     */
    private final Map<String, VarInfo> varInfo = new HashMap<String, VarInfo>();

    /**
     * The field {@code varManager} contains the variable manager.
     */
    private final VarManager varManager;

    /**
     * The field {@code extraSize} contains the parameter for the extra stack
     * size.
     */
    private final int extraSize;

    /**
     * Creates a new object.
     * 
     * @param varManager the variable manager
     * @param extraSize the extra stack size parameter
     */
    public ProcessorState(VarManager varManager, int extraSize) {

        this.varManager = varManager;
        this.extraSize = extraSize;
    }

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
                Var v = varManager.makeVar();
                code.add(new DeclareVar(v, si));
                stack.remove(i);
                stack.add(i, v);
            }
        }
    }

    /**
     * For a list of variables get the corresponding element from the stack.
     * This element is unified with the variable if it is a variable or a
     * declaration is added to the code.
     * 
     * @param list the list of variables to fix
     */
    public void fix(List<Var> list) {

        for (Var x : list) {
            GCode v = pop();
            if (v instanceof Var) {
                v.unify( x);
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
     * Getter for the extra size.
     * 
     * @return the extra size
     */
    public int getExtraSize() {

        return extraSize;
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
     * Getter for the variable information.
     * 
     * @return the info map
     */
    public Map<String, VarInfo> getVarInfo() {

        return varInfo;
    }

    /**
     * Getter for the variable information.
     * 
     * @param name the name of the variable
     * 
     * @return the info &ndash; either an existing or a new one
     */
    public VarInfo getVarInfo(String name) {

        VarInfo info = varInfo.get(name);
        if (info == null) {
            info = new VarInfo(name);
            varInfo.put(name, info);
        }
        return info;
    }

    /**
     * Merge the variable infos.
     * 
     * @param state the processor state to get the variable informations to be
     *        merged in
     */
    public void mergeVarInfos(ProcessorState state) {

        for (VarInfo v : state.varInfo.values()) {
            getVarInfo(v.getName()).merge(v);
        }
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
        Var ret = varManager.makeVar();
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
     * Check whether the stack contains code with side effects.
     * 
     * @return {@code true} iff the stack contains code with side effects
     */
    public boolean stackHasSideEffects() {

        for (GCode gcode : stack) {
            if (gcode.hasSideEffect()) {
                return true;
            }
        }
        return false;
    }

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
