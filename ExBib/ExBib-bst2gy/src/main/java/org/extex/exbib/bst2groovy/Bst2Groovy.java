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
import org.extex.exbib.bst2groovy.data.processor.EntryReference;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
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
*/
public class Bst2Groovy extends BstInterpreterCore implements Evaluator {

    /**
     * The field {@code comments} contains the comments.
     */
    private final StringBuilder comments = new StringBuilder();

    /**
     * The field {@code compilers} contains the compilers for functions,
     * fields, and variables.
     */
    private Map<String, Compiler> compilers = null;

    /**
     * The field {@code factory} contains the factory for bst readers.
     */
    private BstReaderFactory factory = null;

    /**
     * The field {@code evaluateTokenVisitor} contains the token visitor for
     * evaluation.
     */
    private final TokenVisitor evaluateTokenVisitor = new TokenVisitor() {

        /**
    *      java.lang.Object[])
         */
        public void visitBlock(TBlock block, Object... args)
                throws ExBibException {

            EntryReference entryReference = (EntryReference) args[0];
            ProcessorState state = (ProcessorState) args[1];
            for (Token t : block) {
                evaluatePartially(t, entryReference, state);
            }
        }

        /**
    *      java.lang.Object[])
         */
        public void visitChar(TChar c, Object... args) {

            throw new ImpossibleException("visitChar()");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitField(TField field, Object... args) {

            throw new ImpossibleException("visitField()");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitInteger(TInteger integer, Object... args) {

            ((ProcessorState) args[1]).push(new GIntegerConstant(integer
                .getInt()));
        }

        /**
    *      java.lang.Object[])
         */
        public void visitIntegerOption(TIntegerOption option, Object... args) {

            throw new ImpossibleException("visitIntegerOption()");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitLiteral(TLiteral literal, Object... args)
                throws ExBibException {

            String name = literal.getValue();
            Compiler compiler = compilers.get(name);
            if (compiler == null) {
                throw new UnknownFunctionException(name);
            }

            compiler.evaluate((EntryReference) args[0],
                (ProcessorState) args[1], (Evaluator) args[2], linkData);
        }

        /**
    *      java.lang.Object[])
         */
        public void visitLocalInteger(TLocalInteger integer, Object... args) {

            throw new ImpossibleException("visitLocalInteger()");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitLocalLocator(TLocalLocator localLocator, Object[] args)
                throws ExBibException {

            throw new ImpossibleException("visitLocalLocator()");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitLocalString(TLocalString string, Object... args) {

            throw new ImpossibleException("visitLocalString()");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitQLiteral(TQLiteral qliteral, Object... args)
                throws ExBibException {

            String name = qliteral.getValue();
            Compiler compiler = compilers.get(name);
            if (compiler == null) {
                throw new UnknownFunctionException(name);
            }

            compiler.evaluate((EntryReference) args[0],
                (ProcessorState) args[1], (Evaluator) args[2], linkData);
        }

        /**
    *      java.lang.Object[])
         */
        public void visitString(TString string, Object... args) {

            ProcessorState stack = (ProcessorState) args[1];
            stack.push(new GStringConstant(string.getValue()));
        }

        /**
    *      java.lang.Object[])
         */
        public void visitStringOption(TStringOption option, Object... args) {

            throw new ImpossibleException("visitStringOption()");
        }

        /**
    *      java.lang.Object[])
         */
        public void visitTokenList(TokenList list, Object... args)
                throws ExBibException {

            EntryReference entryReference = (EntryReference) args[0];
            ProcessorState state = (ProcessorState) args[1];
            for (Token t : list) {
                evaluatePartially(t, entryReference, state);
            }
        }
    };

    /**
     * The field {@code evaluatePartiallyTokenVisitor} contains the token
     * visitor for partial evaluation.
     */
    private final TokenVisitor evaluatePartiallyTokenVisitor =
            new TokenVisitor() {

                /**
            *      java.lang.Object[])
                 */
                public void visitBlock(TBlock block, Object... args) {

                    ((ProcessorState) args[1]).push(new CodeBlock(block));
                }

                /**
            *      java.lang.Object[])
                 */
                public void visitChar(TChar c, Object... args) {

                    throw new ImpossibleException("visitChar()");
                }

                /**
            *      java.lang.Object[])
                 */
                public void visitField(TField field, Object... args) {

                    throw new ImpossibleException("visitField()");
                }

                /**
            *      java.lang.Object[])
                 */
                public void visitInteger(TInteger integer, Object... args) {

                    ((ProcessorState) args[1]).push(new GIntegerConstant(
                        integer.getInt()));
                }

                /**
            *      java.lang.Object[])
                 */
                public void visitIntegerOption(TIntegerOption option,
                        Object... args) {

                    throw new ImpossibleException("visitLocalInteger()");
                }

                /**
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
                        .evaluate((EntryReference) args[0],
                            (ProcessorState) args[1], (Evaluator) args[2],
                            linkData);
                }

                /**
            *      java.lang.Object[])
                 */
                public void visitLocalInteger(TLocalInteger integer,
                        Object... args) {

                    throw new ImpossibleException("visitLocalInteger()");
                }

                /**
            *      java.lang.Object[])
                 */
                public void visitLocalLocator(TLocalLocator localLocator,
                        Object[] args) throws ExBibException {

                    throw new ImpossibleException("visitLocalInteger()");
                }

                /**
            *      java.lang.Object[])
                 */
                public void visitLocalString(TLocalString string,
                        Object... args) {

                    throw new ImpossibleException("visitLocalString()");
                }

                /**
            *      java.lang.Object[])
                 */
                public void visitQLiteral(TQLiteral qliteral, Object... args) {

                    ((ProcessorState) args[1]).push(new GQuote(qliteral));
                }

                /**
            *      java.lang.Object[])
                 */
                public void visitString(TString string, Object... args) {

                    ((ProcessorState) args[1]).push(new GStringConstant(string
                        .getValue()));
                }

                /**
            *      java.lang.Object[])
                 */
                public void visitStringOption(TStringOption option,
                        Object... args) {

                    throw new ImpossibleException("visitLocalInteger()");
                }

                /**
            *      java.lang.Object[])
                 */
                public void visitTokenList(TokenList list, Object... args)
                        throws ExBibException {

                    EntryReference entry = (EntryReference) args[0];
                    ProcessorState state = (ProcessorState) args[1];
                    for (Token t : list) {
                        evaluatePartially(t, entry, state);
                    }
                }
            };

    /**
     * The field {@code functionList} contains the list of functions.
     */
    private final List<GFunction> functionList = new ArrayList<GFunction>();

    /**
     * The field {@code linkData} contains the data for linking.
     */
    private final LinkContainer linkData;

    /**
     * The field {@code types} contains the supported types.
     */
    private final Map<String, GFunction> types = new HashMap<String, GFunction>();

    /**
     * The field {@code parameters} contains the parameters.
     */
    private final Map<ParameterType, Parameter> parameters =
            new HashMap<ParameterType, Parameter>();

    /**
     * The field {@code varManager} contains the variable manager.
     */
    private final VarManager varManager = new VarManager( "v");

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
        *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
             *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
             *      org.extex.exbib.bst2groovy.linker.LinkContainer)
             */
            public void evaluate(EntryReference entryReference,
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
        setDb(new DBImpl());
        defaultParameters();
        configure(ConfigurationFactory.newInstance(getClass().getName()
            .replace('.', '/') + ".config"));
    }

    /**
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
            compilers.put(name, 
                new GetIntegerCompiler(GFunction.translate(name)));
        } else if (token instanceof TString) {
            compilers.put(name, 
                new GetStringCompiler(GFunction.translate(name)));
        } else if (token instanceof TLocalInteger) {
            compilers.put(name, 
                new GetLocalIntegerCompiler(name));
        } else if (token instanceof TLocalString) {
            compilers.put(name, 
                new GetLocalStringCompiler(name));
        } else if (token instanceof TStringOption) {
            compilers.put(name, 
                new GetOptionStringCompiler(name));
        } else if (token instanceof TIntegerOption) {
            compilers.put(name, 
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
        ProcessorState state = makeState(0);
        EntryReference entry = new EntryReference("entry");
        evaluatePartially(body, entry, state);

        List<GCode> stack = state.getStack();
        GCode returnValue;
        if (stack.isEmpty()) {
            returnValue = null;
        } else if (stack.size() == 1) {
            returnValue = stack.get(0);
            if (returnValue instanceof CodeBlock) {
                // TODO gene: code return unimplemented
                throw new RuntimeException("unimplemented");
            }
        } else {
            StringBuilder sb = new StringBuilder();
            for (GCode c : stack) {
                sb.append(" ");
                sb.append(c.toString());
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(0);
            }
            throw new ComplexFunctionException(name, sb.toString());
        }

        List<Var> locals = state.getLocals();
        varManager.reassign(locals, "p");
        Collections.reverse(locals);
        GFunction function = new GFunction(returnValue, name, 
            locals, state.getCode(), entry);

        if (parameters.get(ParameterType.OPTIMIZE).toBoolean()) {
            function.optimize();
        }

        functionList.add(function);
        compilers.put(name, function);
        if (function.needsEntry() && function.getType() == ReturnType.VOID
                && function.getParameters().size() == 0) {
            types.put(name, function);
        }
        // saveVarInfo(state.getVarInfo(), function);
    }

    /**
     * Initialize the parameters.
     */
    private void defaultParameters() {

        parameters.put(ParameterType.OPTIMIZE, new Parameter(true));
        parameters.put(ParameterType.STYLE_NAME, new Parameter("Style"));
        parameters.put(ParameterType.TAB_SIZE, new Parameter(2));
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ExBibException this should not happen
     * 
     * @see org.extex.exbib.bst2groovy.data.processor.Evaluator#evaluate(org.extex.exbib.core.bst.token.Token,
     *      org.extex.exbib.bst2groovy.data.processor.EntryReference,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState)
     */
    public void evaluate(Token token, EntryReference entryReference,
            ProcessorState state) throws ExBibException {

        token.visit(evaluateTokenVisitor, entryReference, state, this);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ExBibException this should not happen
     * 
     * @see org.extex.exbib.bst2groovy.data.processor.Evaluator#evaluatePartially(org.extex.exbib.core.bst.token.Token,
     *      org.extex.exbib.bst2groovy.data.processor.EntryReference,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState)
     */
    public void evaluatePartially(Token token, EntryReference entryReference,
            ProcessorState state) throws ExBibException {

        token.visit(evaluatePartiallyTokenVisitor, entryReference, state, this);
    }

    /**
     * Getter for a parameter.
     * 
     * @param name the name of the parameter
     * 
     * @return the value
     */
    public Parameter getParameter(ParameterType name) {

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
            factory = new BstReaderFactory(
                getConfiguration().getConfiguration("BstReader"), getFinder());
        }
        BstReader reader = factory.newInstance();
        reader.setSaveComment(comments);
        reader.parse(this);
    }

public ProcessorState makeState(int size) {

        return new ProcessorState(varManager, size);
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
     * Setter for a parameter.
     * 
     * @param type the name
     * @param value the value
     */
    public void setParameter(ParameterType type, Parameter value) {

        if (type == null) {
            throw new NullPointerException("setParameter()");
        }
        parameters.put(type, value);
    }

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
                    new ArrayList<Var>(), 
                    new CommandTranslator(this).translate(this),
                    new EntryReference("entry"));
        run.use();
        functionList.add(run);

        writeComments(writer);

        CodeWriter w = new CodeWriter(writer);
        w.setTabSize(getParameter(ParameterType.TAB_SIZE).toInteger());

        writeImports(w);
        writeHead(w);
        writeTypes(w);
        writeConstructor(w);

        linkData.writeMethods(w);

        for (GFunction fct : functionList) {
            fct.print(w, "\n\t");
        }

        w.write(
            "\n}\n\nnew ", 
            getParameter(ParameterType.STYLE_NAME).toString(),
            "(bibDB, bibWriter, bibProcessor).", 
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
            w.write(comments.toString().replaceAll("\n", "\n// ")
                .replaceAll("// % ", "// ").replaceFirst("^% ?", ""));
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

        writer.write(
            "\n\n", 
            "  ", getParameter(ParameterType.STYLE_NAME).toString(),
            "(bibDB, bibWriter, bibProcessor) {\n\n", 
            "    super(bibDB, bibWriter, bibProcessor)\n\n");

        List<String> strings = this.getMacroNames();
        if (!strings.isEmpty()) {
            writer.write("\t\t[\n");
            for (String s : strings) {
                writer.write("\t\t\t");
                writeMapKey(writer, s);
                writer.write(":\t", GStringConstant.translate(getMacro(s)),
                    ",\n");
            }
            writer
                .write("\t\t].each { name, value -> defineString(name, value) }\n");
        }

        Map<String, Token> options = this.getOptions();
        if (!options.isEmpty()) {
            writer.write("\t\t[\n");
            for (String s : options.keySet()) {
                writer.write("\t\t\t");
                writeMapKey(writer, s);
                writer.write(": ",
                    GStringConstant.translate(getOption(s).getValue()), ",\n");
            }
            writer.write("\t\t].each { name, value ->\n",
                "\t\t\t", 
                "if (bibProcessor.getOption(name) == null) {\n\t\t\t\t",
                "bibProcessor.setOption(name, value)\n", 
                "\t\t\t}\n\t\t", "}\n");
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

        writer.write("class ", getParameter(ParameterType.STYLE_NAME)
            .toString(), " extends org.extex.exbib.groovy.Style {\n\n", 
            "\n");

        for (String name : getIntegers()) {
            writer.write("\tint ", GFunction.translate(name), " = 0\n");
        }
        for (String name : getStrings()) {
            writer.write("\n\tString ", GFunction.translate(name), " = ''");
        }
        writer.write("\n\n");
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
            writer.write("\tMap types = [");
            String[] keys = keySet.toArray(new String[0]);
            Arrays.sort(keys);
            for (String key : keys) {
                GFunction function = types.get(key);
                writer.write("\n\t\t");
                writeMapKey(writer, key);
                writer.write(" : { entry -> ");
                writer.write(function.getName(), "(entry", ")", " },");
            }
            writer.write("\n\t]\n");
        }
    }

}
