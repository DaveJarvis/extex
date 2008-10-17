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

package org.extex.exbib.bst2groovy.info;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.extex.exbib.bst2groovy.Bst2Groovy;
import org.extex.exbib.bst2groovy.Evaluator;
import org.extex.exbib.bst2groovy.data.EntryRefernce;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.data.builtin.InitLocal;
import org.extex.exbib.bst2groovy.data.builtin.SetLocal;
import org.extex.exbib.bst2groovy.data.types.CodeBlock;
import org.extex.exbib.bst2groovy.data.types.GType;
import org.extex.exbib.bst2groovy.evaluator.GLocal;
import org.extex.exbib.bst2groovy.evaluator.OpenEndedStack;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class IfInfo implements Info {

    /**
     * TODO gene: missing JavaDoc.
     */
    private static class If implements GCode {

        /**
         * The field <tt>arg</tt> contains the ...
         */
        private GCode cond;

        /**
         * The field <tt>thenBranch</tt> contains the ...
         */
        private GCode thenBranch;

        /**
         * The field <tt>elseBranch</tt> contains the ...
         */
        private GCode elseBranch;

        /**
         * Creates a new object.
         * 
         * @param cond the argument
         * @param thenBranch ...
         * @param elseBranch ...
         */
        private If(GCode cond, GCode thenBranch, GCode elseBranch) {

            super();
            this.cond = cond;
            this.thenBranch = thenBranch;
            this.elseBranch = elseBranch;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#getType()
         */
        public GType getType() {

            return GType.VOID;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            String prefix2 = prefix + Bst2Groovy.INDENT;
            writer.write("\n");
            writer.write(prefix);
            writer.write("if (");
            cond.print(writer, prefix);
            writer.write(") {");
            thenBranch.print(writer, prefix2);
            writer.write("\n");
            writer.write(prefix);
            writer.write("} {");
            elseBranch.print(writer, prefix2);
            writer.write("\n");
            writer.write(prefix);
            writer.write("}");
        }
    }

    private int adjustStackSize(OpenEndedStack tstack, OpenEndedStack estack) {

        List<GCode> ts = tstack.getStack();
        List<GCode> es = estack.getStack();
        for (int i = es.size() - ts.size(); i > 0; i--) {
            tstack.push(tstack.pop());
        }
        for (int i = ts.size() - es.size(); i > 0; i--) {
            estack.push(estack.pop());
        }
        return ts.size();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.info.Info#evaluate(org.extex.exbib.bst2groovy.data.EntryRefernce,
     *      org.extex.exbib.bst2groovy.evaluator.OpenEndedStack,
     *      org.extex.exbib.bst2groovy.data.GCodeContainer, Evaluator)
     */
    public void evaluate(EntryRefernce entryRefernce, OpenEndedStack stack,
            GCodeContainer code, Evaluator evaluator) {

        GCode e = stack.pop();
        GCode t = stack.pop();
        GCode cond = stack.pop();

        //
        OpenEndedStack tstack = new OpenEndedStack();
        GCodeContainer tcode = new GCodeContainer();
        if (t instanceof CodeBlock) {
            evaluator.evaluate(((CodeBlock) t).getToken(), entryRefernce,
                tstack, tcode);
        } else {
            throw new RuntimeException("syntax error in then for if$");
        }
        OpenEndedStack estack = new OpenEndedStack();
        GCodeContainer ecode = new GCodeContainer();
        if (e instanceof CodeBlock) {
            evaluator.evaluate(((CodeBlock) e).getToken(), entryRefernce,
                estack, ecode);
        } else {
            throw new RuntimeException("syntax error in else for if$");
        }
        //
        OpenEndedStack os = new OpenEndedStack();
        List<GCode> ts = tstack.getStack();
        List<GCode> es = estack.getStack();
        for (int i = es.size() - ts.size(); i > 0; i--) {
            tstack.push(tstack.pop());
        }
        for (int i = ts.size() - es.size(); i > 0; i--) {
            estack.push(estack.pop());
        }
        int size = ts.size();
        //
        List<GLocal> locals = unify(estack.getFutures(), tstack.getFutures());
        for (GLocal x : locals) {
            GCode v = stack.pop();
            if (v instanceof GLocal) {
                ((GLocal) v).unify(x);
            } else {
                x.setValue(v);
                code.add(new InitLocal(x));
            }
        }

        if (size > 0) {
            for (GCode x : ts) {
                os.pop();
            }
            int i = 0;
            for (GLocal x : os.getFutures()) {
                code.add(new InitLocal(x));
                tcode.add(new SetLocal(x, ts.get(i)));
                ecode.add(new SetLocal(x, es.get(i)));
            }
        }

        code.add(new If(cond, tcode, ecode));
        for (GLocal x : os.getFutures()) {
            stack.push(x);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param l1
     * @param l2
     * 
     * @return the longer list
     */
    private List<GLocal> unify(List<GLocal> l1, List<GLocal> l2) {

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
}
