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

package org.extex.exbib.bst2groovy.compiler;

import java.io.IOException;
import java.util.List;

import org.extex.exbib.bst2groovy.Bst2Groovy;
import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.data.VoidGCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.CodeBlock;
import org.extex.exbib.bst2groovy.data.types.GBoolean;
import org.extex.exbib.bst2groovy.data.var.AssignVar;
import org.extex.exbib.bst2groovy.data.var.DeclareVar;
import org.extex.exbib.bst2groovy.data.var.Var;
import org.extex.exbib.bst2groovy.exception.WhileComplexException;
import org.extex.exbib.bst2groovy.exception.WhileSyntaxException;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.bst2groovy.linker.LinkContainer;

/**
 * This class implements the analyzer for a while instruction.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class WhileCompiler implements Compiler {

    /**
     * This inner class is the expression for a while loop in the target
     * program.
     */
    private static final class While extends VoidGCode {

        /**
         * The field <tt>cond</tt> contains the condition code.
         */
        private GCode cond;

        /**
         * The field <tt>body</tt> contains the body code.
         */
        private GCode body;

        /**
         * Creates a new object.
         * 
         * @param cond the argument
         * @param body the body code
         */
        private While(GCode cond, GCode body) {

            this.cond = cond;
            this.body = body;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.VoidGCode#optimize(java.util.List,
         *      int)
         */
        @Override
        public int optimize(List<GCode> list, int index) {

            if (cond instanceof GCodeContainer) {
                ((GCodeContainer) cond).optimize();
            }
            if (body instanceof GCodeContainer) {
                ((GCodeContainer) body).optimize();
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
            writer.write(prefix, "while (");
            cond.print(writer, prefix2);
            writer.write(") {");
            body.print(writer, prefix2);
            writer.write(prefix);
            writer.write("}");
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.linker.LinkContainer)
     */
    public void evaluate(EntryRefernce entryRefernce, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        GCode body = state.pop();
        GCode cond = state.pop();

        //
        ProcessorState condState = new ProcessorState();
        if (cond instanceof CodeBlock) {
            evaluator.evaluate(((CodeBlock) cond).getToken(), entryRefernce,
                condState);
        } else {
            throw new WhileSyntaxException(true);
        }
        if (condState.size() - condState.getLocals().size() != 1) {
            throw new WhileComplexException(true, condState.toString());
        }
        cond = condState.pop();
        ProcessorState bodyState = new ProcessorState();
        if (body instanceof CodeBlock) {
            evaluator.evaluate(((CodeBlock) body).getToken(), entryRefernce,
                bodyState);
        } else {
            throw new WhileSyntaxException(false);
        }

        List<Var> bl = bodyState.getLocals();
        if (bodyState.size() > bl.size()) {
            throw new WhileComplexException(false, bodyState.toString());
        }
        for (Var x : bl) {
            bodyState.add(new AssignVar(x, bodyState.pop()));
        }

        List<Var> locals = IfCompiler.unify(condState.getLocals(), bl);
        for (Var x : locals) {
            GCode v = state.pop();
            if (v instanceof Var) {
                ((Var) v).unify(x);
            } else {
                state.add(new DeclareVar(x, v));
            }
        }

        state.add(condState.getCode());
        bodyState.getCode().add(condState.getCode());

        cond = cond.optimize();
        if (cond instanceof GBoolean) {
            cond = ((GBoolean) cond).getCode();
        }

        state.add(new While(cond, bodyState.getCode()));
        for (int i = bl.size() - 1; i >= 0; i--) {
            state.push(bl.get(i));
        }
    }

}
