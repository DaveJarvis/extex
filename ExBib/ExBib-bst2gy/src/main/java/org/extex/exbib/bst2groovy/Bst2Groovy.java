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

package org.extex.exbib.bst2groovy;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.extex.exbib.bst2groovy.compiler.AddPeriodCompiler;
import org.extex.exbib.bst2groovy.compiler.AssignCompiler;
import org.extex.exbib.bst2groovy.compiler.CallTypeCompiler;
import org.extex.exbib.bst2groovy.compiler.ChangeCaseCompiler;
import org.extex.exbib.bst2groovy.compiler.ChrToIntCompiler;
import org.extex.exbib.bst2groovy.compiler.CiteCompiler;
import org.extex.exbib.bst2groovy.compiler.ConcatCompiler;
import org.extex.exbib.bst2groovy.compiler.DuplicateCompiler;
import org.extex.exbib.bst2groovy.compiler.EmptyCompiler;
import org.extex.exbib.bst2groovy.compiler.EqualsCompiler;
import org.extex.exbib.bst2groovy.compiler.FormatNameCompiler;
import org.extex.exbib.bst2groovy.compiler.GetFieldCompiler;
import org.extex.exbib.bst2groovy.compiler.GetIntegerCompiler;
import org.extex.exbib.bst2groovy.compiler.GetLocalIntegerCompiler;
import org.extex.exbib.bst2groovy.compiler.GetLocalStringCompiler;
import org.extex.exbib.bst2groovy.compiler.GetLocatorCompiler;
import org.extex.exbib.bst2groovy.compiler.GetOptionIntegerCompiler;
import org.extex.exbib.bst2groovy.compiler.GetOptionStringCompiler;
import org.extex.exbib.bst2groovy.compiler.GetStringCompiler;
import org.extex.exbib.bst2groovy.compiler.GreaterCompiler;
import org.extex.exbib.bst2groovy.compiler.IfCompiler;
import org.extex.exbib.bst2groovy.compiler.IntToChrCompiler;
import org.extex.exbib.bst2groovy.compiler.IntToStrCompiler;
import org.extex.exbib.bst2groovy.compiler.LessCompiler;
import org.extex.exbib.bst2groovy.compiler.MinusCompiler;
import org.extex.exbib.bst2groovy.compiler.MissingCompiler;
import org.extex.exbib.bst2groovy.compiler.NewlineCompiler;
import org.extex.exbib.bst2groovy.compiler.NumNamesCompiler;
import org.extex.exbib.bst2groovy.compiler.OptionCompiler;
import org.extex.exbib.bst2groovy.compiler.PlusCompiler;
import org.extex.exbib.bst2groovy.compiler.PopCompiler;
import org.extex.exbib.bst2groovy.compiler.PreambleCompiler;
import org.extex.exbib.bst2groovy.compiler.PurifyCompiler;
import org.extex.exbib.bst2groovy.compiler.SkipCompiler;
import org.extex.exbib.bst2groovy.compiler.SubstringCompiler;
import org.extex.exbib.bst2groovy.compiler.SwapCompiler;
import org.extex.exbib.bst2groovy.compiler.TextLengthCompiler;
import org.extex.exbib.bst2groovy.compiler.TextPrefixCompiler;
import org.extex.exbib.bst2groovy.compiler.TypeCompiler;
import org.extex.exbib.bst2groovy.compiler.WarningCompiler;
import org.extex.exbib.bst2groovy.compiler.WhileCompiler;
import org.extex.exbib.bst2groovy.compiler.WidthCompiler;
import org.extex.exbib.bst2groovy.compiler.WriteCompiler;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.processor.VarInfo;
import org.extex.exbib.bst2groovy.data.types.CodeBlock;
import org.extex.exbib.bst2groovy.data.types.GFunction;
import org.extex.exbib.bst2groovy.data.types.GIntegerConstant;
import org.extex.exbib.bst2groovy.data.types.GQuote;
import org.extex.exbib.bst2groovy.data.types.GStringConstant;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.data.var.Var;
import org.extex.exbib.bst2groovy.data.var.VarManager;
import org.extex.exbib.bst2groovy.exception.ComplexFunctionException;
import org.extex.exbib.bst2groovy.exception.ImpossibleException;
import org.extex.exbib.bst2groovy.exception.UnknownFunctionException;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.bst2groovy.linker.LinkContainer;
import org.extex.exbib.bst2groovy.parameters.Parameter;
import org.extex.exbib.bst2groovy.parameters.ParameterType;
import org.extex.exbib.core.bst.BstInterpreterCore;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.exception.ExBibBstNotFoundException;
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
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.bstio.BstReader;
import org.extex.exbib.core.io.bstio.BstReaderFactory;
import org.extex.framework.configuration.ConfigurationFactory;

/**
 * Partially evaluate a BST program to create an equivalent Groovy program.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Bst2Groovy extends BstInterpreterCore implements Evaluator {

    /**
     * The field <tt>INDENT</tt> contains the String used for indentation.
     */
    public static final String INDENT = "  ";

    /**
     * The field <tt>NL_INDENT</tt> contains the newline character followed by a
     * single indentation.
     */
    public static final String NL_INDENT = "\n" + INDENT;

    /**
     * The field <tt>comments</tt> contains the comments.
     */
    private StringBuilder comments = new StringBuilder();

    /**
     * The field <tt>infos</tt> contains the infos on functions, fields, and
     * variables.
     */
    private Map<String, Compiler> compilers = null;

    /**
     * The field <tt>factory</tt> contains the factory for bst readers.
     */
    private BstReaderFactory factory = null;

    /**
     * The field <tt>evaluateTokenVisitor</tt> contains the token visitor for
     * evaluation.
     */
    private TokenVisitor evaluateTokenVisitor = new TokenVisitor() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitBlock(org.extex.exbib.core.bst.token.impl.TBlock,
         *      java.lang.Object[])
         */
        public void visitBlock(TBlock block, Object... args)
                throws ExBibException {

            EntryRefernce entryRefernce = (EntryRefernce) args[0];
            ProcessorState state = (ProcessorState) args[1];
            for (Token t : block) {
                evaluatePartially(t, entryRefernce, state);
            }
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitChar(org.extex.exbib.core.bst.token.impl.TChar,
         *      java.lang.Object[])
         */
        public void visitChar(TChar c, Object... args) {

            throw new ImpossibleException("visitChar()");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitField(org.extex.exbib.core.bst.token.impl.TField,
         *      java.lang.Object[])
         */
        public void visitField(TField field, Object... args) {

            throw new ImpossibleException("visitField()");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitInteger(org.extex.exbib.core.bst.token.impl.TInteger,
         *      java.lang.Object[])
         */
        public void visitInteger(TInteger integer, Object... args) {

            ((ProcessorState) args[1]).push(new GIntegerConstant(integer
                .getInt()));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitIntegerOption(org.extex.exbib.core.bst.token.impl.TIntegerOption,
         *      java.lang.Object[])
         */
        public void visitIntegerOption(TIntegerOption option, Object... args) {

            throw new ImpossibleException("visitIntegerOption()");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLiteral(org.extex.exbib.core.bst.token.impl.TLiteral,
         *      java.lang.Object[])
         */
        public void visitLiteral(TLiteral literal, Object... args)
                throws ExBibException {

            String name = literal.getValue();
            Compiler compiler = compilers.get(name);
            if (compiler == null) {
                throw new UnknownFunctionException(name);
            }

            compiler.evaluate((EntryRefernce) args[0],
                (ProcessorState) args[1], (Evaluator) args[2], linkData);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalInteger(org.extex.exbib.core.bst.token.impl.TLocalInteger,
         *      java.lang.Object[])
         */
        public void visitLocalInteger(TLocalInteger integer, Object... args) {

            throw new ImpossibleException("visitLocalInteger()");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalLocator(org.extex.exbib.core.bst.token.impl.TLocalLocator,
         *      java.lang.Object[])
         */
        public void visitLocalLocator(TLocalLocator localLocator, Object[] args)
                throws ExBibException {

            throw new ImpossibleException("visitLocalLocator()");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalString(org.extex.exbib.core.bst.token.impl.TLocalString,
         *      java.lang.Object[])
         */
        public void visitLocalString(TLocalString string, Object... args) {

            throw new ImpossibleException("visitLocalString()");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitQLiteral(org.extex.exbib.core.bst.token.impl.TQLiteral,
         *      java.lang.Object[])
         */
        public void visitQLiteral(TQLiteral qliteral, Object... args)
                throws ExBibException {

            String name = qliteral.getValue();
            Compiler compiler = compilers.get(name);
            if (compiler == null) {
                throw new UnknownFunctionException(name);
            }

            compiler.evaluate((EntryRefernce) args[0],
                (ProcessorState) args[1], (Evaluator) args[2], linkData);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitString(org.extex.exbib.core.bst.token.impl.TString,
         *      java.lang.Object[])
         */
        public void visitString(TString string, Object... args) {

            ProcessorState stack = (ProcessorState) args[1];
            stack.push(new GStringConstant(string.getValue()));
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitStringOption(org.extex.exbib.core.bst.token.impl.TStringOption,
         *      java.lang.Object[])
         */
        public void visitStringOption(TStringOption option, Object... args) {

            throw new ImpossibleException("visitStringOption()");
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.token.TokenVisitor#visitTokenList(org.extex.exbib.core.bst.token.impl.TokenList,
         *      java.lang.Object[])
         */
        public void visitTokenList(TokenList list, Object... args)
                throws ExBibException {

            EntryRefernce entryRefernce = (EntryRefernce) args[0];
            ProcessorState state = (ProcessorState) args[1];
            for (Token t : list) {
                evaluatePartially(t, entryRefernce, state);
            }
        }
    };

    /**
     * The field <tt>evaluatePartiallyTokenVisitor</tt> contains the token
     * visitor for partial evaluation.
     */
    private final TokenVisitor evaluatePartiallyTokenVisitor =
            new TokenVisitor() {

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitBlock(org.extex.exbib.core.bst.token.impl.TBlock,
                 *      java.lang.Object[])
                 */
                public void visitBlock(TBlock block, Object... args) {

                    ((ProcessorState) args[1]).push(new CodeBlock(block));
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitChar(org.extex.exbib.core.bst.token.impl.TChar,
                 *      java.lang.Object[])
                 */
                public void visitChar(TChar c, Object... args) {

                    throw new ImpossibleException("visitChar()");
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitField(org.extex.exbib.core.bst.token.impl.TField,
                 *      java.lang.Object[])
                 */
                public void visitField(TField field, Object... args) {

                    throw new ImpossibleException("visitField()");
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitInteger(org.extex.exbib.core.bst.token.impl.TInteger,
                 *      java.lang.Object[])
                 */
                public void visitInteger(TInteger integer, Object... args) {

                    ((ProcessorState) args[1]).push(new GIntegerConstant(
                        integer.getInt()));
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitIntegerOption(org.extex.exbib.core.bst.token.impl.TIntegerOption,
                 *      java.lang.Object[])
                 */
                public void visitIntegerOption(TIntegerOption option,
                        Object... args) {

                    throw new ImpossibleException("visitLocalInteger()");
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLiteral(org.extex.exbib.core.bst.token.impl.TLiteral,
                 *      java.lang.Object[])
                 */
                public void visitLiteral(TLiteral literal, Object... args)
                        throws ExBibException {

                    String name = literal.getValue();
                    Compiler compiler = compilers.get(name);
                    if (compiler == null) {
                        throw new UnknownFunctionException(name);
                    }

                    compiler
                        .evaluate((EntryRefernce) args[0],
                            (ProcessorState) args[1], (Evaluator) args[2],
                            linkData);
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalInteger(org.extex.exbib.core.bst.token.impl.TLocalInteger,
                 *      java.lang.Object[])
                 */
                public void visitLocalInteger(TLocalInteger integer,
                        Object... args) {

                    throw new ImpossibleException("visitLocalInteger()");
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalLocator(org.extex.exbib.core.bst.token.impl.TLocalLocator,
                 *      java.lang.Object[])
                 */
                public void visitLocalLocator(TLocalLocator localLocator,
                        Object[] args) throws ExBibException {

                    throw new ImpossibleException("visitLocalInteger()");
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLocalString(org.extex.exbib.core.bst.token.impl.TLocalString,
                 *      java.lang.Object[])
                 */
                public void visitLocalString(TLocalString string,
                        Object... args) {

                    throw new ImpossibleException("visitLocalString()");
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitQLiteral(org.extex.exbib.core.bst.token.impl.TQLiteral,
                 *      java.lang.Object[])
                 */
                public void visitQLiteral(TQLiteral qliteral, Object... args) {

                    ((ProcessorState) args[1]).push(new GQuote(qliteral));
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitString(org.extex.exbib.core.bst.token.impl.TString,
                 *      java.lang.Object[])
                 */
                public void visitString(TString string, Object... args) {

                    ((ProcessorState) args[1]).push(new GStringConstant(string
                        .getValue()));
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitStringOption(org.extex.exbib.core.bst.token.impl.TStringOption,
                 *      java.lang.Object[])
                 */
                public void visitStringOption(TStringOption option,
                        Object... args) {

                    throw new ImpossibleException("visitLocalInteger()");
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.exbib.core.bst.token.TokenVisitor#visitTokenList(org.extex.exbib.core.bst.token.impl.TokenList,
                 *      java.lang.Object[])
                 */
                public void visitTokenList(TokenList list, Object... args)
                        throws ExBibException {

                    EntryRefernce entry = (EntryRefernce) args[0];
                    ProcessorState state = (ProcessorState) args[1];
                    for (Token t : list) {
                        evaluatePartially(t, entry, state);
                    }
                }
            };

    /**
     * The field <tt>functionList</tt> contains the list of functions.
     */
    private List<GFunction> functionList = new ArrayList<GFunction>();

    /**
     * The field <tt>linkData</tt> contains the data for linking.
     */
    private LinkContainer linkData;

    /**
     * The field <tt>styleName</tt> contains the name of the style assembled.
     */
    private String styleName = "Style";

    /**
     * The field <tt>types</tt> contains the supported types.
     */
    private Map<String, GFunction> types = new HashMap<String, GFunction>();

    /**
     * The field <tt>parameters</tt> contains the parameters.
     */
    private Map<ParameterType, Parameter> parameters =
            new HashMap<ParameterType, Parameter>();

    /**
     * The field <tt>varManager</tt> contains the variable manager.
     */
    private VarManager varManager = new VarManager("v");

    {
        if (compilers == null) {
            compilers = new HashMap<String, Compiler>();
        }
        compilers.put(">", new GreaterCompiler());
        compilers.put("<", new LessCompiler());
        compilers.put("=", new EqualsCompiler());
        compilers.put("+", new PlusCompiler());
        compilers.put("-", new MinusCompiler());
        compilers.put("*", new ConcatCompiler());
        compilers.put(":=", new AssignCompiler(this));
        compilers.put("add.period$", new AddPeriodCompiler());
        compilers.put("call.type$", new CallTypeCompiler());
        compilers.put("change.case$", new ChangeCaseCompiler());
        compilers.put("chr.to.int$", new ChrToIntCompiler());
        compilers.put("cite$", new CiteCompiler());
        compilers.put("duplicate$", new DuplicateCompiler());
        compilers.put("empty$", new EmptyCompiler());
        compilers.put("format.name$", new FormatNameCompiler());
        compilers.put("if$", new IfCompiler());
        compilers.put("int.to.chr$", new IntToChrCompiler());
        compilers.put("int.to.str$", new IntToStrCompiler());
        compilers.put("missing$", new MissingCompiler());
        compilers.put("newline$", new NewlineCompiler());
        compilers.put("num.names$", new NumNamesCompiler());
        compilers.put("pop$", new PopCompiler());
        compilers.put("preamble$", new PreambleCompiler());
        compilers.put("purify$", new PurifyCompiler());
        compilers.put("quote$", new Compiler() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
             *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
             *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
             *      org.extex.exbib.bst2groovy.linker.LinkContainer)
             */
            public void evaluate(EntryRefernce entryRefernce,
                    ProcessorState state, Evaluator evaluator,
                    LinkContainer linker) {

                state.push(new GStringConstant("\""));
            }
        });
        compilers.put("skip$", new SkipCompiler());
        compilers.put("stack$", new SkipCompiler());
        compilers.put("substring$", new SubstringCompiler());
        compilers.put("swap$", new SwapCompiler());
        compilers.put("text.length$", new TextLengthCompiler());
        compilers.put("text.prefix$", new TextPrefixCompiler());
        compilers.put("top$", new SkipCompiler());
        compilers.put("type$", new TypeCompiler());
        compilers.put("warning$", new WarningCompiler());
        compilers.put("while$", new WhileCompiler());
        compilers.put("width$", new WidthCompiler());
        compilers.put("write$", new WriteCompiler());
        compilers.put("global.max$", new OptionCompiler("GLOBAL_MAX", 0xffff));
        compilers.put("entry.max$", new OptionCompiler("ENTRY_MAX", 0xffff));
        compilers.put("locator.resource$", new GetLocatorCompiler(
            TLocalLocator.LocatorField.RESOURCE));
        compilers.put("locator.line$", new GetLocatorCompiler(
            TLocalLocator.LocatorField.LINE));
        compilers.put("crossref", new GetFieldCompiler("crossref"));
    }

    /**
     * Creates a new object.
     * 
     * @throws ExBibImpossibleException this should never happen
     */
    public Bst2Groovy() throws ExBibImpossibleException {

        this.linkData = new LinkContainer();
        setDB(new DBImpl());
        defaultParameters();
        configure(ConfigurationFactory.newInstance(getClass().getName()
            .replace('.', '/')
                + ".config"));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.BstInterpreterCore#addFunction(java.lang.String,
     *      org.extex.exbib.core.bst.code.Code, org.extex.exbib.core.io.Locator)
     */
    @Override
    public void addFunction(String name, Code code, Locator locator)
            throws ExBibException {

        if (compilers == null) {
            compilers = new HashMap<String, Compiler>();
        }

        if (code instanceof MacroCode) {
            addFunction(name, ((MacroCode) code).getToken());
        } else if (code instanceof Token) {
            addFunction(name, (Token) code);
        }
        super.addFunction(name, code, locator);
    }

    /**
     * Add a function from a token.
     * 
     * @param name the name
     * @param token the body
     * 
     * @throws ExBibException just in case
     */
    private void addFunction(String name, Token token) throws ExBibException {

        if (token instanceof TokenList) {
            analyzeFunction(name, (TokenList) token);
        } else if (token instanceof TField) {
            compilers.put(name, new GetFieldCompiler(name));
        } else if (token instanceof TInteger) {
            compilers.put(name, //
                new GetIntegerCompiler(GFunction.translate(name)));
        } else if (token instanceof TString) {
            compilers.put(name, //
                new GetStringCompiler(GFunction.translate(name)));
        } else if (token instanceof TLocalInteger) {
            compilers.put(name, //
                new GetLocalIntegerCompiler(name));
        } else if (token instanceof TLocalString) {
            compilers.put(name, //
                new GetLocalStringCompiler(name));
        } else if (token instanceof TStringOption) {
            compilers.put(name, //
                new GetOptionStringCompiler(name));
        } else if (token instanceof TIntegerOption) {
            compilers.put(name, //
                new GetOptionIntegerCompiler(name));
        }
    }

    /**
     * Analyze a function.
     * 
     * @param name the name
     * @param body the BST code
     * 
     * @throws ExBibException just in case
     */
    private void analyzeFunction(String name, TokenList body)
            throws ExBibException {

        varManager.reset();
        ProcessorState state = new ProcessorState(varManager);
        EntryRefernce entry = new EntryRefernce("entry");
        evaluatePartially(body, entry, state);

        List<GCode> stack = state.getStack();
        GCode returnValue;
        if (stack.isEmpty()) {
            returnValue = null;
        } else if (stack.size() == 1) {
            returnValue = stack.get(0);
            if (returnValue instanceof CodeBlock) {
                // TODO gene: analyzeFunction unimplemented
                throw new RuntimeException("unimplemented");
            }
        } else {
            throw new ComplexFunctionException(name, stack.toString());
        }

        List<Var> locals = state.getLocals();
        varManager.reassign(locals, "p");
        Collections.reverse(locals);
        GFunction function = new GFunction(returnValue, name, //
            locals, state.getCode(), entry);

        if (parameters.get(ParameterType.OPTIMIZE).toBoolean()) {
            function.optimize();
        }

        functionList.add(function);
        compilers.put(name, function);
        if (function.needsEntry() && function.getType() == ReturnType.VOID) {
            types.put(name, function);
        }
        saveVarInfo(state.getVarInfo(), function);
    }

    /**
     * Initialize the parameters.
     * 
     */
    private void defaultParameters() {

        parameters.put(ParameterType.OPTIMIZE, new Parameter(true));
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ExBibException this should not happen
     * 
     * @see org.extex.exbib.bst2groovy.data.processor.Evaluator#evaluate(org.extex.exbib.core.bst.token.Token,
     *      org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState)
     */
    public void evaluate(Token token, EntryRefernce entryRefernce,
            ProcessorState state) throws ExBibException {

        token.visit(evaluateTokenVisitor, entryRefernce, state, this);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ExBibException this should not happen
     * 
     * @see org.extex.exbib.bst2groovy.data.processor.Evaluator#evaluatePartially(org.extex.exbib.core.bst.token.Token,
     *      org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState)
     */
    public void evaluatePartially(Token token, EntryRefernce entryRefernce,
            ProcessorState state) throws ExBibException {

        token.visit(evaluatePartiallyTokenVisitor, entryRefernce, state, this);
    }

    /**
     * Getter for a parameter.
     * 
     * @param name the name of the parameter
     * 
     * @return the value
     */
    public Parameter getParameter(String name) {

        return parameters.get(name);
    }

    /**
     * Load the named styles into memory.
     * 
     * @throws ExBibBstNotFoundException in case the BST could not be found
     * @throws ExBibException in case of an error
     */
    public void load() throws ExBibBstNotFoundException, ExBibException {

        if (factory == null) {
            factory = new BstReaderFactory(//
                getConfiguration().getConfiguration("BstReader"), getFinder());
        }
        BstReader reader = factory.newInstance();
        reader.setSaveComment(comments);
        reader.parse(this);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.processor.Evaluator#makeState()
     */
    public ProcessorState makeState() {

        return new ProcessorState(varManager);
    }

    /**
     * Load BST styles and write a Groovy program for them.
     * 
     * @param writer the writer for the program
     * @param styles the styles to incorporate
     * 
     * @throws IOException in case of an I/O error
     * @throws ExBibException in case of an error
     * @throws ExBibBstNotFoundException in case of an error
     */
    public void run(Writer writer, String... styles)
            throws IOException,
                ExBibBstNotFoundException,
                ExBibException {

        addBibliographyStyle(styles);
        load();
        write(writer);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param varInfos
     * @param function the function
     */
    private void saveVarInfo(Map<String, VarInfo> varInfos, GFunction function) {

        // System.err.println(function.getName());
        //
        // for (VarInfo v : varInfos.values()) {
        // System.err.println(v.toString());
        // }
        // System.err.println();
    }

    /**
     * Setter for a parameter.
     * 
     * @param type the name
     * @param value the value
     */
    public void setParameter(ParameterType type, Parameter value) {

        parameters.put(type, value);
    }

    /**
     * Setter for the styleName.
     * 
     * @param styleName the styleName to set
     */
    public void setStyleName(String styleName) {

        this.styleName = styleName;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "<" + getClass().getName().replaceAll(".*\\.", "") + ">";
    }

    /**
     * Write the translated program.
     * 
     * @param writer the target writer
     * 
     * @throws IOException in case of an I/O error
     * @throws ExBibException in case of an error
     */
    public void write(Writer writer) throws IOException, ExBibException {

        GFunction run =
                new GFunction(null, "run",
                    new ArrayList<Var>(), //
                    new CommandTranslator(this).translate(this),
                    new EntryRefernce("entry"));
        run.use();
        functionList.add(run);

        writeComments(writer);

        CodeWriter w = new CodeWriter(writer);

        writeImports(w);
        writeHead(w);

        for (String name : getIntegers()) {
            w.write("  int ", GFunction.translate(name), " = 0\n");
        }
        w.write("\n");
        for (String name : getStrings()) {
            w.write("  String ", GFunction.translate(name), " = ''\n");
        }
        w.write("\n");
        writeTypes(w);
        writeConstructor(w);

        linkData.writeMethods(w, "\n" + INDENT, INDENT);

        for (GFunction fct : functionList) {
            fct.print(w, NL_INDENT);
        }

        w.write("\n}\n\nnew ", styleName, "(bibDB, bibWriter, bibProcessor).", //
            run.getName(), "()\n");
        w.flush();
    }

    /**
     * Write the comments to the output stream.
     * 
     * @param w the target writer
     * 
     * @throws IOException in case of an I/O error
     */
    private void writeComments(Writer w) throws IOException {

        if (comments.length() > 0) {
            w.write("// ");
            w.write(comments.toString().replaceAll("\n", "\n// ").replaceAll(
                "// % ", "// ").replaceFirst("^% ?", ""));
            w.write('\n');
        }
    }

    /**
     * Write the constructor for the style.
     * 
     * @param writer the writer
     * 
     * @throws IOException in case of an I/O error
     */
    private void writeConstructor(CodeWriter writer) throws IOException {

        writer.write("\n\n", //
            "  ", styleName, "(bibDB, bibWriter, bibProcessor) {\n", //
            "    this.bibDB = bibDB\n", //
            "    this.bibWriter = bibWriter\n", //
            "    this.bibProcessor = bibProcessor\n");

        List<String> strings = this.getMacroNames();
        if (!strings.isEmpty()) {
            writer.write(INDENT, INDENT, "[\n");
            for (String s : strings) {
                writer.write(INDENT, INDENT, INDENT);
                writeMapKey(writer, s);
                writer.write(": ", GStringConstant.translate(getMacro(s)),
                    ",\n");
            }
            writer.write(INDENT, INDENT, //
                "].each { name, value ->\n", //
                INDENT, INDENT, //
                INDENT, //
                "bibDB.storeString(name, value)\n", //
                INDENT, INDENT, //
                "}\n");
        }

        Map<String, Token> options = this.getOptions();
        if (!options.isEmpty()) {
            writer.write(INDENT, INDENT, "[\n");
            for (String s : options.keySet()) {
                writer.write(INDENT, INDENT, INDENT);
                writeMapKey(writer, s);
                writer.write(": ", GStringConstant.translate(getOption(s)
                    .getValue()), ",\n");
            }
            writer.write(INDENT,
                INDENT, //
                "].each { name, value ->\n", INDENT, INDENT,
                INDENT, //
                "if (bibProcessor.getOption(name) == null) {\n", INDENT,
                INDENT, INDENT, INDENT,//
                "bibProcessor.setOption(name, value)\n", //
                INDENT, INDENT, INDENT,//
                "}\n", INDENT, INDENT, //
                "}\n");
        }

        writer.write("  }\n");
    }

    /**
     * Write the beginning of the style class.
     * 
     * @param writer the writer
     * 
     * @throws IOException in case of an I/O error
     */
    private void writeHead(CodeWriter writer) throws IOException {

        writer.write("class ", styleName, " {\n\n", //
            "  DB bibDB\n", //
            "  Writer bibWriter\n", //
            "  Processor bibProcessor\n", //
            "\n");
    }

    /**
     * Write the imports.
     * 
     * @param writer the writer
     * 
     * @throws IOException in case of an I/O error
     */
    private void writeImports(CodeWriter writer) throws IOException {

        linkData.addImports("org.extex.exbib.core.db.DB");
        linkData.addImports("org.extex.exbib.core.db.Entry");
        linkData.addImports("org.extex.exbib.core.io.Writer");
        linkData.addImports("org.extex.exbib.core.Processor");
        linkData.writeImports(writer);
    }

    /**
     * Write a map key optionally enclosed in quotes.
     * 
     * @param writer the writer
     * @param key the key
     * 
     * @throws IOException in case of an I/O error
     */
    private void writeMapKey(CodeWriter writer, String key) throws IOException {

        if (key.matches("^[a-z]+$")) {
            writer.write(key);
        } else {
            writer.write("'", key, "'");
        }
    }

    /**
     * Write the types.
     * 
     * @param writer the writer
     * 
     * @throws IOException in case of an I/O error
     */
    private void writeTypes(CodeWriter writer) throws IOException {

        Set<String> keySet = types.keySet();
        if (keySet.size() != 0) {
            writer.write(NL_INDENT, "Map types = [");
            String[] keys = keySet.toArray(new String[0]);
            Arrays.sort(keys);
            for (String key : keys) {
                GFunction function = types.get(key);
                writer.write(NL_INDENT, INDENT);
                writeMapKey(writer, key);
                writer.write(" : { entry -> ");
                writer.write(function.getName(), "(entry");
                for (Var x : function.getParameters()) {
                    writer.write(", ", x.getType().getArg());
                }
                writer.write(")", " },");
            }
            writer.write(NL_INDENT, "]\n" //
            );
        }
    }

}
