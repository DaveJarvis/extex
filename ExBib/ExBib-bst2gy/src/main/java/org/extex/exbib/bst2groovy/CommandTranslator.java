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

package org.extex.exbib.bst2groovy;

import java.io.IOException;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.processor.EntryReference;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.exception.CommandWithArgumentsException;
import org.extex.exbib.bst2groovy.exception.CommandWithEntryException;
import org.extex.exbib.bst2groovy.exception.CommandWithReturnException;
import org.extex.exbib.bst2groovy.exception.ImpossibleException;
import org.extex.exbib.bst2groovy.exception.WrappingException;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.command.CommandVisitor;
import org.extex.exbib.core.bst.exception.ExBibEmptyFunctionNameException;
import org.extex.exbib.core.bst.token.impl.TBlock;
import org.extex.exbib.core.bst.token.impl.TChar;
import org.extex.exbib.core.bst.token.impl.TField;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TIntegerOption;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TLocalInteger;
import org.extex.exbib.core.bst.token.impl.TLocalLocator;
import org.extex.exbib.core.bst.token.impl.TLocalString;
import org.extex.exbib.core.bst.token.impl.TQLiteral;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.bst.token.impl.TStringOption;
import org.extex.exbib.core.bst.token.impl.TokenList;
import org.extex.exbib.core.exceptions.ExBibException;

/**
 * This class is a compiler from a list of commands to a code container.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class CommandTranslator {

    /**
     * This internal class represents a loop construction as used by the
     * commands ITERATE and REVERSE.
     */
    private static class GLoop extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param pre the prefix code
         * @param body the body code
         */
        public GLoop(String pre, GCode body) {

            super(ReturnType.VOID, pre, body);
        }

        /**
    *      java.lang.String)
         */
        @Override
        public void print(CodeWriter writer, String prefix) throws IOException {

            writer.write(prefix, getName(), " {");
            getArg(0).print(writer, prefix + "\t");
            writer.write(prefix, "}");
        }
    }

    /**
     * The field {@code commandVisitor} contains the command visitor for
     * printing.
     */
    private final CommandVisitor commandVisitor = new CommandVisitor() {

        /**
    *      java.lang.Object[])
         */
        public void visitBlock(TBlock block, Object... args) {

            throw new ImpossibleException("block");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitChar(TChar c, Object... args) {

            throw new ImpossibleException("char");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitExecute(Command command, Object... args)
                throws ExBibException {

            TLiteral literal;
            try {
                literal = new TLiteral(command.getValue().getValue(), 
                    command.getLocator());
            } catch (ExBibEmptyFunctionNameException e) {
                throw new WrappingException(e);
            }
            visitLiteral(literal, args);
        }

        /**
    *      java.lang.Object[])
         */
        public void visitField(TField field, Object... args) {

            throw new ImpossibleException("field");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitInteger(TInteger integer, Object... args) {

            throw new ImpossibleException("integer");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitIntegerOption(TIntegerOption option, Object... args) {

            throw new ImpossibleException("integer option");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitIterate(Command command, Object... args)
                throws ExBibException {

            GCodeContainer code = (GCodeContainer) args[0];
            ProcessorState state = evaluator.makeState(0);
            EntryReference entryReference = new EntryReference("it");
            try {
                evaluator.evaluate(new TLiteral(command.getValue().getValue(),
                    command.getLocator()), entryReference, state);
            } catch (ExBibEmptyFunctionNameException e) {
                throw new WrappingException(e);
            }
            if (state.size() != 0) {
                throw new CommandWithReturnException("ITERATE",
                    command.toString());
            } else if (state.getLocals().size() != 0) {
                throw new CommandWithArgumentsException("ITERATE",
                    command.toString());
            }
            code.add(new GLoop("getDB().each", state.getCode()));
        }

        /**
    *      java.lang.Object[])
         */
        public void visitLiteral(TLiteral literal, Object... args)
                throws ExBibException {

            GCodeContainer code = (GCodeContainer) args[0];
            ProcessorState state = evaluator.makeState(0);
            EntryReference entryReference = new EntryReference("it");
            evaluator.evaluate(literal, entryReference, state);
            if (state.size() != 0) {
                throw new CommandWithReturnException("EXECUTE", 
                    literal.getValue());
            } else if (state.getLocals().size() != 0) {
                throw new CommandWithArgumentsException("EXECUTE", 
                    literal.getValue());
            } else if (entryReference.isUsed()) {
                throw new CommandWithEntryException("EXECUTE", 
                    literal.getValue());
            }
            code.addAll(state.getCode());
        }

        /**
    *      java.lang.Object[])
         */
        public void visitLocalInteger(TLocalInteger integer, Object... args) {

            throw new ImpossibleException("local integer");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitLocalLocator(TLocalLocator localLocator, Object[] args)
                throws ExBibException {

            throw new ImpossibleException("local locator");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitLocalString(TLocalString string, Object... args) {

            throw new ImpossibleException("local string");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitQLiteral(TQLiteral qliteral, Object... args) {

            throw new ImpossibleException("qliteral");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitRead(Command command, Object... args) {

            ((GCodeContainer) args[0]).add(new GenericCode(ReturnType.VOID,
                "// read"));
        }

        /**
    *      java.lang.Object[])
         */
        public void visitReverse(Command command, Object... args)
                throws ExBibException {

            GCodeContainer code = (GCodeContainer) args[0];
            ProcessorState state = evaluator.makeState(0);
            EntryReference entryReference = new EntryReference("it");
            try {
                evaluator.evaluate(new TLiteral(command.getValue().getValue(),
                    command.getLocator()), entryReference, state);
            } catch (ExBibEmptyFunctionNameException e) {
                throw new WrappingException(e);
            }
            if (state.size() != 0) {
                throw new CommandWithReturnException("REVERSE", 
                    command.toString());
            } else if (state.getLocals().size() != 0) {
                throw new CommandWithArgumentsException("REVERSE", 
                    command.toString());
            }
            code.add(new GLoop("getDB().getEntries().reverse().each", state
                .getCode()));
        }

        /**
    *      java.lang.Object[])
         */
        public void visitSort(Command command, Object... args) {

            ((GCodeContainer) args[0]).add(new GenericCode(ReturnType.VOID,
                "getDB().sort"));
        }

        /**
    *      java.lang.Object[])
         */
        public void visitString(TString string, Object... args) {

            throw new ImpossibleException("string");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitStringOption(TStringOption option, Object... args) {

            throw new ImpossibleException("string option");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitTokenList(TokenList list, Object... args) {

            throw new ImpossibleException("token list");
        }

    };

    /**
     * The field {@code evaluator} contains the reference to an evaluator.
     */
    private final Evaluator evaluator;

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
     * 
     * @throws IOException in case of an I/O error
     * @throws ExBibException in case of an error
     */
    public GCodeContainer translate(Iterable<Command> it)
            throws IOException,
                ExBibException {

        GCodeContainer code = new GCodeContainer();

        for (Command c : it) {
            c.visit(commandVisitor, code);
        }

        return code;
    }

}
