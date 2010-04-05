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

package org.extex.exbib.bst2groovy.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.bool.And;
import org.extex.exbib.bst2groovy.data.bool.GBoolean;
import org.extex.exbib.bst2groovy.data.bool.Not;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.CodeBlock;
import org.extex.exbib.bst2groovy.data.types.GIntegerConstant;
import org.extex.exbib.bst2groovy.data.types.GStringConstant;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.data.var.AssignVar;
import org.extex.exbib.bst2groovy.data.var.DeclareVar;
import org.extex.exbib.bst2groovy.data.var.Var;
import org.extex.exbib.bst2groovy.exception.IfComplexException;
import org.extex.exbib.bst2groovy.exception.IfSyntaxException;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.bst2groovy.linker.LinkContainer;
import org.extex.exbib.core.exceptions.ExBibException;

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
         * The field <tt>condition</tt> contains the condition.
         */
        private GCode condition;

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
         * @param condition the condition
         * @param thenBranch the then branch
         * @param elseBranch the else branch
         */
        public If(GCode condition, GCodeContainer thenBranch,
                GCodeContainer elseBranch) {

            super(ReturnType.VOID, "if");
            this.condition = condition;
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

            condition = condition.optimize();
            if (condition instanceof GBoolean) {
                condition = ((GBoolean) condition).getCode();
            }

            if (condition instanceof GIntegerConstant) {
                if (((GIntegerConstant) condition).getValue() != 0) {
                    return thenBranch.optimize();
                } else {
                    return elseBranch.optimize();
                }
            }

            thenBranch.optimize();
            elseBranch.optimize();

            if (thenBranch.isEmpty()) {
                GCodeContainer x = thenBranch;
                thenBranch = elseBranch;
                elseBranch = x;
                condition = new Not(condition).optimize();
            }
            if (elseBranch.isEmpty() && thenBranch.size() == 1) {
                GCode code = thenBranch.get(0);
                if (code instanceof If && ((If) code).elseBranch.isEmpty()) {
                    thenBranch = ((If) code).thenBranch;
                    condition =
                            new And(condition, ((If) code).condition)
                                .optimize();
                }
            }
            return this;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GenericCode#optimize(java.util.List,
         *      int)
         */
        @Override
        public int optimize(List<GCode> list, int index) {

            if (index > 0 //
                    && thenBranch.size() == 1
                    && elseBranch.size() == 1
                    && list.get(index - 1) instanceof DeclareVar
                    && thenBranch.get(0) instanceof AssignVar
                    && elseBranch.get(0) instanceof AssignVar) {
                DeclareVar init = (DeclareVar) list.get(index - 1);
                AssignVar setThen = (AssignVar) thenBranch.get(0);
                AssignVar setElse = (AssignVar) elseBranch.get(0);
                Var var = init.getVar();
                if (init.getValue() == null && var.eq(setThen.getVar())
                        && var.eq(setElse.getVar())) {
                    init.setValue(new IfInline(condition, setThen.getValue(),
                        setElse.getValue()).optimize());
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

            writer.write(prefix);
            print(writer, prefix, prefix + "\t");
        }

        /**
         * Write an if-then-else cascade.
         * 
         * @param writer the writer
         * @param prefix the prefix
         * @param prefix2 the deeper prefix
         * 
         * @throws IOException in case of an I/O error
         */
        public void print(CodeWriter writer, String prefix, String prefix2)
                throws IOException {

            writer.write("if (");
            condition.print(writer, prefix);
            writer.write(") {");
            thenBranch.print(writer, prefix2);
            writer.write(prefix);
            if (elseBranch.isEmpty()) {
                writer.write("}");
            } else if (elseBranch.size() == 1
                    && elseBranch.get(0) instanceof If) {
                writer.write("} else ");
                ((If) (elseBranch.get(0))).print(writer, prefix, prefix2);
            } else {
                writer.write("} else {");
                elseBranch.print(writer, prefix2);
                writer.write(prefix, "}");
            }
        }
    }

    /**
     * This class represents an inline conditional.
     */
    public static final class IfInline extends GenericCode {

        /**
         * The field <tt>LINE_BREAKING_THRESHOLD</tt> contains the line breaking
         * threshold.
         */
        private static final int LINE_BREAKING_THRESHOLD = 40;

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
         * @see org.extex.exbib.bst2groovy.data.GenericCode#optimize()
         */
        @Override
        public GCode optimize() {

            super.optimize();
            GCode a = getArg(0);
            if (a instanceof GIntegerConstant) {
                return ((GIntegerConstant) a).getValue() != 0
                        ? getArg(1)
                        : getArg(2);

            }
            return this;
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
            int col = writer.getColumn() - 1;
            getArg(0).print(writer, prefix);
            if (writer.getColumn() >= LINE_BREAKING_THRESHOLD) {
                writer.nl(col);
            } else {
                col = writer.getColumn() - 1;
                if (col < LINE_BREAKING_THRESHOLD) {
                    col = -1;
                }
            }
            writer.write(" ? ");
            getArg(1).print(writer, prefix);
            if (col > 0) {
                writer.nl(col);
            }
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

        int esize = estack.size();
        int tsize = tstack.size();
        if (esize < tsize) {
            adjustStackSize(estack, tsize);
        } else if (esize > tsize) {
            adjustStackSize(tstack, esize);
            return esize;
        }
        return tsize;
    }

    /**
     * Create a new processor state and evaluate some code in it.
     * 
     * @param evaluator the evaluator
     * @param entry the entry reference
     * @param code the code to evaluate
     * @param then is the error in the then clause?
     * 
     * @return the processor state
     * 
     * @throws ExBibException just in case
     */
    private ProcessorState compileBlock(Evaluator evaluator,
            EntryRefernce entry, GCode code, boolean then)
            throws ExBibException {

        if (!(code instanceof CodeBlock)) {
            throw new IfSyntaxException(then);
        }
        ProcessorState state = evaluator.makeState();
        evaluator.evaluate(((CodeBlock) code).getToken(), entry, state);
        return state;
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
            Evaluator evaluator, LinkContainer linkData) throws ExBibException {

        GCode e = state.pop();
        GCode t = state.pop();
        GCode cond = state.pop();

        if (cond instanceof CodeBlock) {
            ProcessorState condState = evaluator.makeState();
            evaluator.evaluate(((CodeBlock) cond).getToken(), entry, condState);
            if (condState.size() != 1) {
                throw new IfComplexException(true, "");
            }
            state.add(condState.getCode());
            cond = condState.pop();
            for (Var x : condState.getLocals()) {
                GCode v = state.pop();
                if (v instanceof Var) {
                    ((Var) v).unify(x);
                } else if (v instanceof GIntegerConstant
                        || v instanceof GStringConstant) {
                    x.unify(v);
                } else {
                    state.add(new DeclareVar(x, v));
                }
            }
            state.mergeVarInfos(condState);
        }

        ProcessorState thenState = compileBlock(evaluator, entry, t, true);
        ProcessorState elseState = compileBlock(evaluator, entry, e, false);
        int size = adjustStackSize(thenState, elseState);
        state.fix(Var.unify(elseState.getLocals(), thenState.getLocals()));

        ProcessorState os = evaluator.makeState();

        if (size > 0) {
            thenState.eliminateSideEffects();
            elseState.eliminateSideEffects();
            List<GCode> ts = thenState.getStack();
            List<GCode> es = elseState.getStack();
            for (int i = ts.size(); i > 0; i--) {
                os.pop();
            }
            int i = 0;
            for (Var x : os.getLocals()) {
                GCode tsi = ts.get(i);
                GCode esi = es.get(i);
                // if (tsi instanceof Var) {
                // ((Var) tsi).unify(x);
                // if (esi instanceof Var) {
                // ((Var) esi).unify(x);
                // } else {
                // elseState.add(new SetLocal(x, esi));
                // // }
                // } else if (esi instanceof Var) {
                // ((Var) esi).unify(x);
                // thenState.add(new SetLocal(x, tsi));
                // } else {
                state.add(new DeclareVar(x, null));
                thenState.add(new AssignVar(x, tsi));
                elseState.add(new AssignVar(x, esi));
                // }
            }
        }

        state.mergeVarInfos(thenState);
        state.mergeVarInfos(elseState);

        state.add(new If(cond, optimize(thenState), optimize(elseState)));

        for (Var x : os.getLocals()) {
            state.push(x);
        }
    }

    /**
     * This methods performs some optimizations.
     * 
     * @param state the state
     * 
     * @return the code
     */
    private GCodeContainer optimize(ProcessorState state) {

        GCodeContainer code = state.getCode();
        if (code.size() == 2 && code.get(0) instanceof DeclareVar
                && code.get(1) instanceof AssignVar) {
            DeclareVar il = (DeclareVar) code.get(0);
            AssignVar sl = (AssignVar) code.get(1);
            if (il.getVar() == sl.getValue()) {
                sl.setValue(il.getValue());
                code.remove(0);
            }
        }
        return code;
    }

}
