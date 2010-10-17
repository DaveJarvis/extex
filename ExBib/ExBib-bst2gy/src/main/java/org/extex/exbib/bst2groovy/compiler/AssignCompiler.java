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

import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.processor.EntryReference;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.GFunction;
import org.extex.exbib.bst2groovy.data.types.GQuote;
import org.extex.exbib.bst2groovy.data.types.GStringConstant;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.exception.ImmutableException;
import org.extex.exbib.bst2groovy.exception.UnknownVariableException;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.bst2groovy.linker.LinkContainer;
import org.extex.exbib.core.bst.FunctionContainer;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenVisitor;
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
 * This class implements the analyzer for the <code>:=</code> built-in.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class AssignCompiler implements Compiler {

    /**
     * This inner class is the expression for the setter of a field in the
     * target program.
     */
    private static class SetField extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param entry the name of the variable for the entry
         * @param name the name of the field
         * @param value the new value
         */
        public SetField(String entry, String name, GCode value) {

            super(ReturnType.VOID, entry + ".set", new GStringConstant(name),
                value);
        }

    }

    /**
     * This inner class is the expression for the setter of a global integer in
     * the target program.
     */
    private static class SetInteger extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param name the name of the filed
         * @param value the new value
         */
        public SetInteger(String name, GCode value) {

            super(ReturnType.VOID, GFunction.translate(name), value);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
         *      java.lang.String)
         */
        @Override
        public void print(CodeWriter writer, String prefix) throws IOException {

            writer.write(prefix, getName(), " = ");
            getArg(0).print(writer, prefix);
        }
    }

    /**
     * This inner class is the expression for the setter of an option integer in
     * the target program.
     */
    private static class SetIntegerOption extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param name the name of the filed
         * @param value the new value
         */
        public SetIntegerOption(String name, GCode value) {

            super(ReturnType.VOID, "bibProcessor.setOption",
                new GStringConstant(name), value);
        }
    }

    /**
     * This inner class is the expression for the setter of a local integer in
     * the target program.
     */
    private static class SetLocalInteger extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param entry the name of the variable for the entry
         * @param name the name of the filed
         * @param value the new value
         */
        public SetLocalInteger(String entry, String name, GCode value) {

            super(ReturnType.VOID, entry + ".setLocal", //
                new GStringConstant(name), value);
        }

    }

    /**
     * This inner class is the expression for the setter of a local string in
     * the target program.
     */
    private static class SetLocalString extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param entry the name of the variable for the entry
         * @param name the name of the filed
         * @param value the new value
         */
        public SetLocalString(String entry, String name, GCode value) {

            super(ReturnType.VOID, entry + ".setLocal", //
                new GStringConstant(name), value);
        }

    }

    /**
     * This inner class is the expression for the setter of a global string in
     * the target program.
     */
    private static class SetString extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param name the name of the filed
         * @param value the new value
         */
        public SetString(String name, GCode value) {

            super(ReturnType.VOID, GFunction.translate(name), value);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
         *      java.lang.String)
         */
        @Override
        public void print(CodeWriter writer, String prefix) throws IOException {

            writer.write(prefix, getName(), " = ");
            getArg(0).print(writer, prefix);
        }
    }

    /**
     * This inner class is the expression for the setter of an option string in
     * the target program.
     */
    private static class SetStringOption extends GenericCode {

        /**
         * Creates a new object.
         * 
         * @param name the name of the filed
         * @param value the new value
         */
        public SetStringOption(String name, GCode value) {

            super(ReturnType.VOID, "bibProcessor.setOption",
                new GStringConstant(name), value);
        }
    }

    /**
     * The field <tt>functionContainer</tt> contains the reference to the
     * compiler.
     */
    private final FunctionContainer functionContainer;

    /**
     * The field <tt>TV</tt> contains the cases for things to set.
     */
    private static final TokenVisitor TV = new TokenVisitor() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitBlock(org.extex.exbib.core.bst.token.impl.TBlock,
         *      java.lang.Object[])
         */
        public void visitBlock(TBlock block, Object... args) {

            throw new ImmutableException(args[2].toString());
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitChar(org.extex.exbib.core.bst.token.impl.TChar,
         *      java.lang.Object[])
         */
        public void visitChar(TChar c, Object... args) {

            throw new ImmutableException(args[2].toString());
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitField(org.extex.exbib.core.bst.token.impl.TField,
         *      java.lang.Object[])
         */
        public void visitField(TField field, Object... args) {

            ProcessorState state = (ProcessorState) args[0];
            EntryReference entry = (EntryReference) args[1];
            state.add(new SetField(entry.getName(), ((String) args[2]),
                ((GCode) args[3])));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitInteger(org.extex.exbib.core.bst.token.impl.TInteger,
         *      java.lang.Object[])
         */
        public void visitInteger(TInteger integer, Object... args) {

            ProcessorState state = (ProcessorState) args[0];
            String name = (String) args[2];
            state.add(new SetInteger(name, ((GCode) args[3])));
            state.getVarInfo(name).writing();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitIntegerOption(org.extex.exbib.core.bst.token.impl.TIntegerOption,
         *      java.lang.Object[])
         */
        public void visitIntegerOption(TIntegerOption option, Object... args) {

            ProcessorState state = (ProcessorState) args[0];
            state.add(new SetIntegerOption(((String) args[2]), //
                ((GCode) args[3])));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLiteral(org.extex.exbib.core.bst.token.impl.TLiteral,
         *      java.lang.Object[])
         */
        public void visitLiteral(TLiteral literal, Object... args) {

            throw new ImmutableException(args[2].toString());
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalInteger(org.extex.exbib.core.bst.token.impl.TLocalInteger,
         *      java.lang.Object[])
         */
        public void visitLocalInteger(TLocalInteger integer, Object... args) {

            ProcessorState state = (ProcessorState) args[0];
            EntryReference entry = (EntryReference) args[1];
            state.add(new SetLocalInteger(entry.getName(), ((String) args[2]),
                ((GCode) args[3])));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalLocator(org.extex.exbib.core.bst.token.impl.TLocalLocator,
         *      java.lang.Object[])
         */
        public void visitLocalLocator(TLocalLocator localLocator, Object[] args) {

            throw new ImmutableException(args[2].toString());
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalString(org.extex.exbib.core.bst.token.impl.TLocalString,
         *      java.lang.Object[])
         */
        public void visitLocalString(TLocalString string, Object... args) {

            ProcessorState state = (ProcessorState) args[0];
            EntryReference entry = (EntryReference) args[1];
            state.add(new SetLocalString(entry.getName(), ((String) args[2]),
                ((GCode) args[3])));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitQLiteral(org.extex.exbib.core.bst.token.impl.TQLiteral,
         *      java.lang.Object[])
         */
        public void visitQLiteral(TQLiteral qliteral, Object... args) {

            throw new ImmutableException(args[2].toString());
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitString(org.extex.exbib.core.bst.token.impl.TString,
         *      java.lang.Object[])
         */
        public void visitString(TString string, Object... args) {

            ProcessorState state = (ProcessorState) args[0];
            String name = (String) args[2];
            state.add(new SetString(name, ((GCode) args[3])));
            state.getVarInfo(name).writing();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitStringOption(org.extex.exbib.core.bst.token.impl.TStringOption,
         *      java.lang.Object[])
         */
        public void visitStringOption(TStringOption option, Object... args)
                throws ExBibException {

            ProcessorState state = (ProcessorState) args[0];
            state.add(new SetStringOption(((String) args[2]), //
                ((GCode) args[3])));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitTokenList(org.extex.exbib.core.bst.token.impl.TokenList,
         *      java.lang.Object[])
         */
        public void visitTokenList(TokenList list, Object... args)
                throws ExBibException {

            throw new ImmutableException(args[2].toString());
        }

    };

    /**
     * Creates a new object.
     * 
     * @param functionContainer reference object for getting information
     */
    public AssignCompiler(FunctionContainer functionContainer) {

        this.functionContainer = functionContainer;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ExBibException this should not happen
     * 
     * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryReference,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.linker.LinkContainer)
     */
    public void evaluate(EntryReference entry, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) throws ExBibException {

        GCode q = state.pop();
        GCode value = state.pop();
        if (!(q instanceof GQuote)) {
            throw new UnknownVariableException(q.toString());
        }
        String v = ((GQuote) q).getToken().getValue();
        Code f = functionContainer.getFunction(v);
        Token t;

        if (f instanceof Token) {
            t = (Token) f;
        } else if (f instanceof MacroCode) {
            t = ((MacroCode) f).getToken();
        } else {
            throw new UnknownVariableException(v);
        }

        t.visit(TV, state, entry, v, value);
    }

}
