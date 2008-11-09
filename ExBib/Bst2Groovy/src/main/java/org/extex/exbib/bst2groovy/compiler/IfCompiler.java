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

package org.extex.exbib.bst2groovy.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.exbib.bst2groovy.Bst2Groovy;
import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.bool.And;
import org.extex.exbib.bst2groovy.data.bool.Not;
import org.extex.exbib.bst2groovy.data.local.GLocal;
import org.extex.exbib.bst2groovy.data.local.InitLocal;
import org.extex.exbib.bst2groovy.data.local.SetLocal;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.CodeBlock;
import org.extex.exbib.bst2groovy.data.types.GBoolean;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.exception.IfSyntaxException;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.bst2groovy.linker.LinkContainer;

/**
 * This class implements the analyzer for an if-then-else instruction.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class IfCompiler implements Compiler {

    /**
     * This inner class is the expression for a if-then-else in the target
     * program.
     */
    public static final class If extends GenericCode {

        /**
         * The field <tt>arg</tt> contains the condition.
         */
        private GCode cond;

        /**
         * The field <tt>thenBranch</tt> contains the then branch.
         */
        private GCodeContainer thenBranch;

        /**
         * The field <tt>elseBranch</tt> contains the else branch.
         */
        private GCodeContainer elseBranch;

        /**
         * Creates a new object.
         * 
         * @param cond the condition
         * @param thenBranch the then branch
         * @param elseBranch the else branch
         */
        public If(GCode cond, GCodeContainer thenBranch,
                GCodeContainer elseBranch) {

            super(ReturnType.VOID, "");
            this.cond = cond;
            this.thenBranch = thenBranch;
            this.elseBranch = elseBranch;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GenericCode#optimize()
         */
        @Override
        public GCode optimize() {

            thenBranch.optimize();
            elseBranch.optimize();

            if (thenBranch.isEmpty()) {
                GCodeContainer x = thenBranch;
                thenBranch = elseBranch;
                elseBranch = x;
                cond = new Not(cond).optimize();
            } else {
                cond = cond.optimize();
            }
            if (elseBranch.isEmpty() && thenBranch.size() == 1) {
                GCode code = thenBranch.get(0);
                if (code instanceof If && ((If) code).elseBranch.isEmpty()) {
                    thenBranch = ((If) code).thenBranch;
                    cond = new And(cond, ((If) code).cond);
                }
            }
            return this;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.VoidGCode#optimize(java.util.List,
         *      int)
         */
        @Override
        public int optimize(List<GCode> list, int index) {

            optimize();

            if (index > 0 && list.get(index - 1) instanceof InitLocal
                    && thenBranch.size() == 1 && elseBranch.size() == 1
                    && thenBranch.get(0) instanceof SetLocal
                    && elseBranch.get(0) instanceof SetLocal) {
                InitLocal init = (InitLocal) list.get(index - 1);
                SetLocal setThen = (SetLocal) thenBranch.get(0);
                SetLocal setElse = (SetLocal) elseBranch.get(0);
                GLocal var = init.getVar();
                if (init.getValue() == null && var == setThen.getVar()
                        && var == setElse.getVar()) {
                    init.setValue(new IfInline(cond, setThen.getValue(),
                        setElse.getValue()));
                    list.remove(index);
                    return index;
                }
            }

            return index + 1;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
         *      java.lang.String)
         */
        @Override
        public void print(CodeWriter writer, String prefix) throws IOException {

            String prefix2 = prefix + Bst2Groovy.INDENT;
            writer.write(prefix, "if (");
            cond.print(writer, prefix);
            writer.write(") {");
            thenBranch.print(writer, prefix2);
            writer.write(prefix);
            if (!elseBranch.isEmpty()) {
                writer.write("} else {");
                elseBranch.print(writer, prefix2);
                writer.write(prefix);
            }
            writer.write("}");
        }
    }

    /**
     * This class represents an inline conditional.
     */
    public static final class IfInline extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param cond the condition
         * @param thenBranch the then branch
         * @param elseBranch the else branch
         */
        public IfInline(GCode cond, GCode thenBranch, GCode elseBranch) {

            super(thenBranch.getType(), "if", cond, thenBranch, elseBranch);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GenericCode#print(org.extex.exbib.bst2groovy.io.CodeWriter,
         *      java.lang.String)
         */
        @Override
        public void print(CodeWriter writer, String prefix) throws IOException {

            writer.write("( ");
            getArg(0).print(writer, prefix);
            writer.write(" ? ");
            getArg(1).print(writer, prefix);
            writer.write(" : ");
            getArg(2).print(writer, prefix);
            writer.write(" )");
        }

    }

    /**
     * Adjust a stack to a given size by optionally popping elements and pushing
     * them back. The open ended stack will create locals for the missing
     * elements.
     * 
     * @param stack the stack
     * @param size the target size
     */
    public static void adjustStackSize(ProcessorState stack, int size) {

        List<GCode> ts = stack.getStack();
        List<GCode> diff = new ArrayList<GCode>();
        for (int i = size - ts.size(); i > 0; i--) {
            diff.add(stack.pop());
        }
        for (int i = diff.size() - 1; i >= 0; i--) {
            stack.push(diff.get(i));
        }
    }

    /**
     * Adjust the two argument stacks to the same size.
     * 
     * @param tstack the first stack
     * @param estack the second stack
     * 
     * @return the final size
     */
    public static int adjustStackSize(ProcessorState tstack,
            ProcessorState estack) {

        List<GCode> ts = tstack.getStack();
        List<GCode> es = estack.getStack();
        if (estack.size() != ts.size()) {
            adjustStackSize(tstack, es.size());
            adjustStackSize(estack, ts.size());
        }
        return ts.size();
    }

    /**
     * Unify two lists of variables.
     * 
     * @param l1 the first list
     * @param l2 the second list
     * 
     * @return the longer list
     */
    public static List<GLocal> unify(List<GLocal> l1, List<GLocal> l2) {

        int s1 = l1.size();
        int s2 = l2.size();
        if (s1 >= s2) {
            for (int i = 0; i < s2; i++) {
                l1.get(i).unify(l2.get(i));
            }
            return l1;
        }
        for (int i = 0; i < s1; i++) {
            l2.get(i).unify(l1.get(i));
        }
        return l2;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.linker.LinkContainer)
     */
    public void evaluate(EntryRefernce entry, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        GCode e = state.pop();
        GCode t = state.pop();
        GCode cond = state.pop();

        ProcessorState thenState = new ProcessorState();
        if (t instanceof CodeBlock) {
            evaluator.evaluate(((CodeBlock) t).getToken(), entry, thenState);
        } else {
            throw new IfSyntaxException("then");
        }
        ProcessorState elseState = new ProcessorState();
        if (e instanceof CodeBlock) {
            evaluator.evaluate(((CodeBlock) e).getToken(), entry, elseState);
        } else {
            throw new IfSyntaxException("else");
        }

        int size = adjustStackSize(thenState, elseState);

        List<GLocal> locals =
                unify(elseState.getLocals(), thenState.getLocals());
        for (GLocal x : locals) {
            GCode v = state.pop();
            if (v instanceof GLocal) {
                ((GLocal) v).unify(x);
            } else {
                state.add(new InitLocal(x, v));
            }
        }

        ProcessorState os = new ProcessorState();

        if (size > 0) {
            thenState.eliminateSideEffects();
            elseState.eliminateSideEffects();
            List<GCode> ts = thenState.getStack();
            List<GCode> es = elseState.getStack();
            for (GCode x : ts) {
                os.pop();
            }
            int i = 0;
            for (GLocal x : os.getLocals()) {
                GCode tsi = ts.get(i);
                GCode esi = es.get(i);
                // if (tsi instanceof GLocal) {
                // ((GLocal) tsi).unify(x);
                // if (esi instanceof GLocal) {
                // ((GLocal) esi).unify(x);
                // } else {
                // elseState.add(new SetLocal(x, esi));
                // // }
                // } else if (esi instanceof GLocal) {
                // ((GLocal) esi).unify(x);
                // thenState.add(new SetLocal(x, tsi));
                // } else {
                state.add(new InitLocal(x, null));
                thenState.add(new SetLocal(x, tsi));
                elseState.add(new SetLocal(x, esi));
                // }
            }
        }

        optimize(thenState);
        optimize(elseState);
        cond = cond.optimize();
        if (cond instanceof GBoolean) {
            cond = ((GBoolean) cond).getCode();
        }

        state.add(new If(cond, thenState.getCode(), elseState.getCode()));

        for (GLocal x : os.getLocals()) {
            state.push(x);
        }
    }

    /**
     * This methods performs some optimizations.
     * 
     * @param state the state
     */
    private void optimize(ProcessorState state) {

        GCodeContainer code = state.getCode();
        if (code.size() == 2 && code.get(0) instanceof InitLocal
                && code.get(1) instanceof SetLocal) {
            InitLocal il = (InitLocal) code.get(0);
            SetLocal sl = (SetLocal) code.get(1);
            if (il.getVar() == sl.getValue()) {
                sl.setValue(il.getValue());
                code.remove(0);
            }
        }
    }

}
