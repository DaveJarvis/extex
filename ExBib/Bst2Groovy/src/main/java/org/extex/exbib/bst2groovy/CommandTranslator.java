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

package org.extex.exbib.bst2groovy;

import java.io.IOException;
import java.io.Writer;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.VoidGCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.command.CommandVisitor;
import org.extex.exbib.core.bst.exception.ExBibEmptyFunctionNameException;
import org.extex.exbib.core.bst.token.impl.TBlock;
import org.extex.exbib.core.bst.token.impl.TChar;
import org.extex.exbib.core.bst.token.impl.TField;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TLocalInteger;
import org.extex.exbib.core.bst.token.impl.TLocalString;
import org.extex.exbib.core.bst.token.impl.TQLiteral;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.bst.token.impl.TokenList;

/**
 * This class is a compiler from a list of commands to a code container.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CommandTranslator {

    /**
     * This internal class represents a loop construction as used by the
     * commands ITERATE and REVERSE.
     */
    private static class GLoop extends VoidGCode {

        /**
         * The field <tt>pre</tt> contains the loop driver code.
         */
        private String pre;

        /**
         * The field <tt>body</tt> contains the loop body code.
         */
        private GCode body;

        /**
         * Creates a new object.
         * 
         * @param pre the prefix code
         * @param body the body code
         */
        public GLoop(String pre, GCode body) {

            this.pre = pre;
            this.body = body;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            writer.write(prefix);
            writer.write(pre);
            writer.write(" {");
            body.print(writer, prefix + Bst2Groovy.INDENT);
            writer.write(prefix);
            writer.write(")");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            String prefix = "\n";
            return prefix + pre + " {" + prefix + body.toString() + prefix
                    + ")";
        }
    }

    /**
     * The field <tt>CV</tt> contains the command visitor for printing.
     */
    private final CommandVisitor COMMAND_VISITOR = new CommandVisitor() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitBlock(org.extex.exbib.core.bst.token.impl.TBlock,
         *      java.lang.Object[])
         */
        public void visitBlock(TBlock block, Object... args) throws IOException {

            // this does should happen
            throw new RuntimeException("block");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitChar(org.extex.exbib.core.bst.token.impl.TChar,
         *      java.lang.Object[])
         */
        public void visitChar(TChar c, Object... args) throws IOException {

            // this does should happen
            throw new RuntimeException("char");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.command.CommandVisitor#visitExecute(org.extex.exbib.core.bst.command.Command,
         *      java.lang.Object[])
         */
        public void visitExecute(Command command, Object... args)
                throws IOException {

            GCodeContainer code = (GCodeContainer) args[0];
            ProcessorState state = new ProcessorState();
            EntryRefernce entryRefernce = new EntryRefernce("it");
            try {
                evaluator.evaluate(new TLiteral(command.getValue().getValue(),
                    command.getLocator()), entryRefernce, state);
            } catch (ExBibEmptyFunctionNameException e) {
                throw new RuntimeException(e);
            }
            if (state.size() != 0) {
                throw new RuntimeException(
                    "Invoking EXECUTE on a function which return a value: "
                            + command.getValue().getValue());
            } else if (state.getLocals().size() != 0) {
                throw new RuntimeException(
                    "Invoking EXECUTE on a function which needs arguments: "
                            + command.getValue().getValue());
            } else if (entryRefernce.isUsed()) {
                throw new RuntimeException(
                    "Invoking EXECUTE on a function which needs an entry: "
                            + command.getValue().getValue());
            }
            code.add(state.getCode());
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitField(org.extex.exbib.core.bst.token.impl.TField,
         *      java.lang.Object[])
         */
        public void visitField(TField field, Object... args) throws IOException {

            // this should not happen
            throw new RuntimeException("field");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitInteger(org.extex.exbib.core.bst.token.impl.TInteger,
         *      java.lang.Object[])
         */
        public void visitInteger(TInteger integer, Object... args)
                throws IOException {

            // this should not happen
            throw new RuntimeException("integer");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.command.CommandVisitor#visitIterate(org.extex.exbib.core.bst.command.Command,
         *      java.lang.Object[])
         */
        public void visitIterate(Command command, Object... args)
                throws IOException {

            GCodeContainer code = (GCodeContainer) args[0];
            ProcessorState state = new ProcessorState();
            EntryRefernce entryRefernce = new EntryRefernce("it");
            try {
                evaluator.evaluate(new TLiteral(command.getValue().getValue(),
                    command.getLocator()), entryRefernce, state);
            } catch (ExBibEmptyFunctionNameException e) {
                throw new RuntimeException(e);
            }
            if (state.size() != 0) {
                throw new RuntimeException("*** error ***");
            } else if (state.getLocals().size() != 0) {
                throw new RuntimeException("*** error ***");
            }
            code.add(new GLoop("bibDB.each", state.getCode()));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLiteral(org.extex.exbib.core.bst.token.impl.TLiteral,
         *      java.lang.Object[])
         */
        public void visitLiteral(TLiteral literal, Object... args)
                throws IOException {

            // this should not happen

            GCodeContainer code = (GCodeContainer) args[0];
            ProcessorState state = new ProcessorState();
            EntryRefernce entryRefernce = new EntryRefernce("it");
            try {
                evaluator.evaluate(new TLiteral(literal.getValue(), literal
                    .getLocator()), entryRefernce, state);
            } catch (ExBibEmptyFunctionNameException e) {
                throw new RuntimeException(e);
            }
            if (state.size() != 0) {
                throw new RuntimeException("*** error ***");
            } else if (state.getLocals().size() != 0) {
                throw new RuntimeException("*** error ***");
            }
            code.addAll(state.getCode());
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalInteger(org.extex.exbib.core.bst.token.impl.TLocalInteger,
         *      java.lang.Object[])
         */
        public void visitLocalInteger(TLocalInteger integer, Object... args)
                throws IOException {

            // this should not happen
            throw new RuntimeException("local integer");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalString(org.extex.exbib.core.bst.token.impl.TLocalString,
         *      java.lang.Object[])
         */
        public void visitLocalString(TLocalString string, Object... args)
                throws IOException {

            // this should not happen
            throw new RuntimeException("local string");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitQLiteral(org.extex.exbib.core.bst.token.impl.TQLiteral,
         *      java.lang.Object[])
         */
        public void visitQLiteral(TQLiteral qliteral, Object... args)
                throws IOException {

            // this should not happen
            throw new RuntimeException("qliteral");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.command.CommandVisitor#visitRead(org.extex.exbib.core.bst.command.Command,
         *      java.lang.Object[])
         */
        public void visitRead(Command command, Object... args)
                throws IOException {

            ((GCodeContainer) args[0]).add(new GenericCode(ReturnType.VOID,
                "// read"));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.command.CommandVisitor#visitReverse(org.extex.exbib.core.bst.command.Command,
         *      java.lang.Object[])
         */
        public void visitReverse(Command command, Object... args)
                throws IOException {

            GCodeContainer code = (GCodeContainer) args[0];
            ProcessorState state = new ProcessorState();
            EntryRefernce entryRefernce = new EntryRefernce("it");
            try {
                evaluator.evaluate(new TLiteral(command.getValue().getValue(),
                    command.getLocator()), entryRefernce, state);
            } catch (ExBibEmptyFunctionNameException e) {
                throw new RuntimeException(e);
            }
            if (state.size() != 0) {
                throw new RuntimeException("*** error ***");
            } else if (state.getLocals().size() != 0) {
                throw new RuntimeException("*** error ***");
            }
            code.add(new GLoop("bibDB.getEntries().reverse().each", state
                .getCode()));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.command.CommandVisitor#visitSort(org.extex.exbib.core.bst.command.Command,
         *      java.lang.Object[])
         */
        public void visitSort(Command command, Object... args)
                throws IOException {

            ((GCodeContainer) args[0]).add(new GenericCode(ReturnType.VOID,
                "bibDB.sort"));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitString(org.extex.exbib.core.bst.token.impl.TString,
         *      java.lang.Object[])
         */
        public void visitString(TString string, Object... args)
                throws IOException {

            // this should not happen
            throw new RuntimeException("string");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitTokenList(org.extex.exbib.core.bst.token.impl.TokenList,
         *      java.lang.Object[])
         */
        public void visitTokenList(TokenList list, Object... args)
                throws IOException {

            // this should not happen
            throw new RuntimeException("token list");
        }

    };

    /**
     * The field <tt>evaluator</tt> contains the reference to an evaluator.
     */
    private Evaluator evaluator;

    /**
     * Creates a new object.
     * 
     * @param evaluator the evaluator
     */
    public CommandTranslator(Evaluator evaluator) {

        this.evaluator = evaluator;
    }

    /**
     * Translate a list of commands into a code container.
     * 
     * @param it the iterable
     * 
     * @return the translated code
     */
    public GCodeContainer translate(Iterable<Command> it) {

        GCodeContainer code = new GCodeContainer();

        try {
            for (Command c : it) {
                c.visit(COMMAND_VISITOR, code);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return code;
    }

}
