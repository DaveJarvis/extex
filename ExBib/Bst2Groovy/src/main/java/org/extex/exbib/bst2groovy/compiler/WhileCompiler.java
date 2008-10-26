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
import java.io.Writer;
import java.util.List;

import org.extex.exbib.bst2groovy.Bst2Groovy;
import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.LinkContainer;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.VoidGCode;
import org.extex.exbib.bst2groovy.data.local.GLocal;
import org.extex.exbib.bst2groovy.data.local.InitLocal;
import org.extex.exbib.bst2groovy.data.local.SetLocal;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.CodeBlock;

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
    private static class While extends VoidGCode {

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
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            String prefix2 = prefix + Bst2Groovy.INDENT;
            writer.write(prefix);
            writer.write("while (");
            cond.print(writer, prefix);
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
     *      org.extex.exbib.bst2groovy.LinkContainer)
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
            throw new RuntimeException("syntax error in condition for while$");
        }
        GCode condExpr;
        if (condState.size() - condState.getLocals().size() != 1) {
            throw new RuntimeException("complex condition for while$ "
                    + condState.toString());
        }
        condExpr = condState.pop();
        ProcessorState bodyState = new ProcessorState();
        if (body instanceof CodeBlock) {
            evaluator.evaluate(((CodeBlock) body).getToken(), entryRefernce,
                bodyState);
        } else {
            throw new RuntimeException("syntax error in body for while$");
        }

        List<GLocal> bl = bodyState.getLocals();
        if (bodyState.size() > bl.size()) {
            throw new RuntimeException("complex body for while$ "
                    + bodyState.toString());
        }
        for (GLocal x : bl) {
            bodyState.add(new SetLocal(x, bodyState.pop()));
        }

        List<GLocal> locals = IfCompiler.unify(condState.getLocals(), bl);
        for (GLocal x : locals) {
            GCode v = state.pop();
            if (v instanceof GLocal) {
                ((GLocal) v).unify(x);
            } else {
                state.add(new InitLocal(x, v));
            }
        }

        state.add(condState.getCode());
        bodyState.getCode().add(condState.getCode());

        state.add(new While(condExpr, bodyState.getCode()));
        for (int i = bl.size() - 1; i >= 0; i--) {
            state.push(bl.get(i));
        }
    }

}
