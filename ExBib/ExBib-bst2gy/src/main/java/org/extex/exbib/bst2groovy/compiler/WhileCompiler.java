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
import java.util.List;

import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.bool.GBoolean;
import org.extex.exbib.bst2groovy.data.processor.EntryReference;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.CodeBlock;
import org.extex.exbib.bst2groovy.data.types.GIntegerConstant;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.data.var.AssignVar;
import org.extex.exbib.bst2groovy.data.var.DeclareVar;
import org.extex.exbib.bst2groovy.data.var.Var;
import org.extex.exbib.bst2groovy.exception.WhileComplexException;
import org.extex.exbib.bst2groovy.exception.WhileSyntaxException;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.bst2groovy.linker.LinkContainer;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TIntegerOption;
import org.extex.exbib.core.bst.token.impl.TLocalInteger;
import org.extex.exbib.core.exceptions.ExBibException;

/**
 * This class implements the analyzer for the <code>while$</code> instruction.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class WhileCompiler implements Compiler {

    /**
     * This inner class is the expression for a while loop in the target
     * program.
     */
    private static final class While extends GenericCode {

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

            super(ReturnType.VOID, "");
            if (cond == null) {
                throw new NullPointerException("null while condition found");
            }
            if (body == null) {
                throw new NullPointerException("null while body found");
            }
            this.cond = cond;
            this.body = body;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GenericCode#optimize(java.util.List,
         *      int)
         */
        @Override
        public int optimize(List<GCode> list, int index) {

            cond.optimize();
            if ((cond instanceof GIntegerConstant)
                    && ((GIntegerConstant) cond).getValue() == 0) {
                list.remove(index);
                return index;
            }
            body.optimize();
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

            String prefix2 = prefix + "\t";
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
     * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryReference,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.linker.LinkContainer)
     */
    public void evaluate(EntryReference entryReference, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) throws ExBibException {

        GCode body = state.pop();
        GCode cond = state.pop();
        int extra = state.getExtraSize() + state.size();
        boolean strategy = false;
        ProcessorState condState = evaluator.makeState(extra);

        if (cond instanceof CodeBlock) {
            evaluator.evaluate(((CodeBlock) cond).getToken(), entryReference,
                condState);
        } else if (cond instanceof GIntegerConstant
                || cond instanceof TLocalInteger || cond instanceof TInteger
                || cond instanceof TIntegerOption) {
            condState.push(cond);
        } else {
            throw new WhileSyntaxException(true);
        }
        if (condState.size() - condState.getLocals().size() != 1) {
            throw new WhileComplexException(true, condState.toString());
        }
        state.mergeVarInfos(condState);
        cond = condState.pop();
        ProcessorState bodyState = evaluator.makeState(extra);
        if (body instanceof CodeBlock) {
            evaluator.evaluate(((CodeBlock) body).getToken(), entryReference,
                bodyState);
        } else {
            throw new WhileSyntaxException(false);
        }

        List<Var> bodyLocals = bodyState.getLocals();
        if (bodyState.size() > bodyLocals.size()) {
            throw new WhileComplexException(false, bodyState.toString());
        }
        for (Var x : bodyLocals) {
            bodyState.add(new AssignVar(x, bodyState.pop()));
        }

        List<Var> locals = Var.unify(condState.getLocals(), bodyLocals);
        if (strategy) {
            state.fix(locals);
        } else {

            for (Var x : locals) {
                if (extra > 0) {
                    extra--;
                    GCode v = state.pop();
                    if (v instanceof Var) {
                        ((Var) v).unify(x);
                    } else {
                        state.add(new DeclareVar(x, v));
                    }
                } else {
                    state.add(new DeclareVar(x));
                }
            }
        }
        state.add(condState.getCode());
        bodyState.getCode().add(condState.getCode());

        cond = cond.optimize();
        if (cond instanceof GBoolean) {
            cond = ((GBoolean) cond).getCode();
        }

        state.mergeVarInfos(bodyState);

        state.add(new While(cond, bodyState.getCode()));

        for (int i = bodyLocals.size() - 1; i >= 0; i--) {
            state.push(bodyLocals.get(i));
        }
    }

}
