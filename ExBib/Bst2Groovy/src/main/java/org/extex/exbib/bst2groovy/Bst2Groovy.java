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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.extex.exbib.bst2groovy.data.local.GLocal;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.CodeBlock;
import org.extex.exbib.bst2groovy.data.types.GFunction;
import org.extex.exbib.bst2groovy.data.types.GInt;
import org.extex.exbib.bst2groovy.data.types.GQuote;
import org.extex.exbib.bst2groovy.data.types.GString;
import org.extex.exbib.core.bst.BstInterpreterCore;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.exception.ExBibBstNotFoundException;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenVisitor;
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
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFunctionExistsException;
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
     * The field <tt>factory</tt> contains the factory for bst readers.
     */
    private BstReaderFactory factory = null;

    /**
     * The field <tt>infos</tt> contains the infos on functions, fields, and
     * variables.
     */
    private Map<String, Compiler> compilers = null;

    /**
     * The field <tt>EVALUATE_TOKEN_VISITOR</tt> contains the token visitor for
     * evaluation.
     */
    private TokenVisitor EVALUATE_TOKEN_VISITOR = new TokenVisitor() {

        public void visitBlock(TBlock block, Object... args) {

            EntryRefernce entryRefernce = (EntryRefernce) args[0];
            ProcessorState state = (ProcessorState) args[1];
            for (Token t : block) {
                evaluatePartially(t, entryRefernce, state);
            }
        }

        public void visitChar(TChar c, Object... args) {

            // this does should happen
            throw new RuntimeException("visitChar()");
        }

        public void visitField(TField field, Object... args) {

            // this does should happen
            throw new RuntimeException("visitField()");
        }

        public void visitInteger(TInteger integer, Object... args) {

            ProcessorState stack = (ProcessorState) args[1];
            stack.push(new GInt(integer.getInt()));
        }

        public void visitLiteral(TLiteral literal, Object... args) {

            String name = literal.getValue().toString();
            Compiler compiler = compilers.get(name);
            if (compiler == null) {
                throw new RuntimeException(name + " is unknown");
            }

            compiler.evaluate((EntryRefernce) args[0],
                (ProcessorState) args[1], (Evaluator) args[2], linkData);
        }

        public void visitLocalInteger(TLocalInteger integer, Object... args) {

            // this does should happen
            throw new RuntimeException("visitLocalInteger()");
        }

        public void visitLocalString(TLocalString string, Object... args) {

            // this does should happen
            throw new RuntimeException("visitLocalString()");
        }

        public void visitQLiteral(TQLiteral qliteral, Object... args) {

            String name = qliteral.getValue();
            Compiler compiler = compilers.get(name);
            if (compiler == null) {
                throw new RuntimeException(name + " is unknown");
            }

            compiler.evaluate((EntryRefernce) args[0],
                (ProcessorState) args[1], (Evaluator) args[2], linkData);
        }

        public void visitString(TString string, Object... args) {

            ProcessorState stack = (ProcessorState) args[1];
            stack.push(new GString(string.getValue()));
        }

        public void visitTokenList(TokenList list, Object... args) {

            EntryRefernce entryRefernce = (EntryRefernce) args[0];
            ProcessorState state = (ProcessorState) args[1];
            for (Token t : list) {
                evaluatePartially(t, entryRefernce, state);

            }
        }
    };

    /**
     * The field <tt>EVALUATE_PARTIALLY_TOKEN_VISITOR</tt> contains the token
     * visitor for partial evaluation.
     */
    private TokenVisitor EVALUATE_PARTIALLY_TOKEN_VISITOR = new TokenVisitor() {

        public void visitBlock(TBlock block, Object... args) {

            ProcessorState stack = (ProcessorState) args[1];
            stack.push(new CodeBlock(block));
        }

        public void visitChar(TChar c, Object... args) {

            // this does should happen
            throw new RuntimeException("visitChar()");
        }

        public void visitField(TField field, Object... args) {

            // this does should happen
            throw new RuntimeException("visitField()");
        }

        public void visitInteger(TInteger integer, Object... args) {

            ProcessorState stack = (ProcessorState) args[1];
            stack.push(new GInt(integer.getInt()));
        }

        public void visitLiteral(TLiteral literal, Object... args) {

            String name = literal.getValue().toString();
            Compiler compiler = compilers.get(name);
            if (compiler == null) {
                throw new RuntimeException(name + " is unknown");
            }

            compiler.evaluate((EntryRefernce) args[0],
                (ProcessorState) args[1], (Evaluator) args[2], linkData);
        }

        public void visitLocalInteger(TLocalInteger integer, Object... args) {

            // this does should happen
            throw new RuntimeException("visitLocalInteger()");
        }

        public void visitLocalString(TLocalString string, Object... args) {

            // this does should happen
            throw new RuntimeException("visitLocalString()");
        }

        public void visitQLiteral(TQLiteral qliteral, Object... args) {

            ProcessorState stack = (ProcessorState) args[1];
            stack.push(new GQuote(qliteral));
        }

        public void visitString(TString string, Object... args) {

            ProcessorState stack = (ProcessorState) args[1];
            stack.push(new GString(string.getValue()));
        }

        public void visitTokenList(TokenList list, Object... args) {

            EntryRefernce entryRefernce = (EntryRefernce) args[0];
            ProcessorState state = (ProcessorState) args[1];
            for (Token t : list) {
                evaluatePartially(t, entryRefernce, state);
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
     * The field <tt>types</tt> contains the supported types.
     */
    private Map<String, GFunction> types = new HashMap<String, GFunction>();

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
             *      org.extex.exbib.bst2groovy.LinkContainer)
             */
            public void evaluate(EntryRefernce entryRefernce,
                    ProcessorState state, Evaluator evaluator,
                    LinkContainer linkData) {

                state.push(new GString("\""));
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
        compilers.put("global.max$", new Compiler() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
             *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
             *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
             *      org.extex.exbib.bst2groovy.LinkContainer)
             */
            public void evaluate(EntryRefernce entryRefernce,
                    ProcessorState state, Evaluator evaluator,
                    LinkContainer linkData) {

                state.push(new GInt(0xffff));
            }
        });
        compilers.put("entry.max$", new Compiler() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
             *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
             *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
             *      org.extex.exbib.bst2groovy.LinkContainer)
             */
            public void evaluate(EntryRefernce entryRefernce,
                    ProcessorState state, Evaluator evaluator,
                    LinkContainer linkData) {

                state.push(new GInt(0xffff));
            }
        });
        compilers.put("crossref", new GetFieldCompiler("crossref"));
    }

    /**
     * Creates a new object.
     * 
     * @throws ExBibImpossibleException this should never happen
     */
    public Bst2Groovy() throws ExBibImpossibleException {

        linkData = new LinkContainer();
        setDB(new DBImpl());
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
    public void addFunction(String name, Code body, Locator locator)
            throws ExBibIllegalValueException,
                ExBibFunctionExistsException {

        if (compilers == null) {
            compilers = new HashMap<String, Compiler>();
        }
        Code code = body;

        if (code instanceof MacroCode) {
            Token token = ((MacroCode) code).getToken();
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
                compilers.put(name, new GetLocalIntegerCompiler(name));
            } else if (token instanceof TLocalString) {
                compilers.put(name, new GetLocalStringCompiler(name));
            }
        } else if (code instanceof TField) {
            compilers.put(name, new GetFieldCompiler(name));
        } else if (code instanceof TInteger) {
            compilers.put(name, new GetIntegerCompiler(name));
        } else if (code instanceof TString) {
            compilers.put(name, new GetStringCompiler(name));
        } else if (code instanceof TLocalInteger) {
            compilers.put(name, new GetLocalIntegerCompiler(name));
        } else if (code instanceof TLocalString) {
            compilers.put(name, new GetLocalStringCompiler(name));
        }
        super.addFunction(name, code, locator);
    }

    /**
     * Analyze a function.
     * 
     * @param name the name
     * @param body the BST code
     */
    private void analyzeFunction(String name, TokenList body) {

        ProcessorState.reset();
        ProcessorState state = new ProcessorState();
        EntryRefernce entry = new EntryRefernce("entry");
        evaluatePartially(body, entry, state);

        List<GCode> stack = state.getStack();
        GCode returnValue;
        if (stack.isEmpty()) {
            returnValue = null;
        } else if (stack.size() == 1) {
            returnValue = stack.get(0);
        } else {
            throw new RuntimeException("function " + name
                    + ": complex return values unimplemented "
                    + stack.toString());
        }

        GFunction function =
                new GFunction(returnValue, GFunction.translate(name), state
                    .getLocals(), state.getCode(), entry.isUsed());
        functionList.add(function);
        compilers.put(name, function);
        if (function.needsEntry() && function.getType() == null) {
            types.put(name, function);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.processor.Evaluator#evaluate(org.extex.exbib.core.bst.token.Token,
     *      org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState)
     */
    public void evaluate(Token token, EntryRefernce entryRefernce,
            ProcessorState state) {

        try {
            token.visit(EVALUATE_TOKEN_VISITOR, entryRefernce, state, this);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.processor.Evaluator#evaluatePartially(org.extex.exbib.core.bst.token.Token,
     *      org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState)
     */
    public void evaluatePartially(Token token, EntryRefernce entryRefernce,
            ProcessorState state) {

        try {
            token.visit(EVALUATE_PARTIALLY_TOKEN_VISITOR, entryRefernce, state,
                this);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
    }

    /**
     * Load the named styles into memory.
     * 
     * @throws ExBibBstNotFoundException
     * @throws ExBibException in case of an error
     */
    public void load() throws ExBibBstNotFoundException, ExBibException {

        if (factory == null) {
            factory = new BstReaderFactory(//
                getConfiguration().getConfiguration("BstReader"), getFinder());
        }
        BstReader reader = factory.newInstance();
        reader.parse(this);
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
     */
    public void write(Writer writer) throws IOException {

        functionList.add(new GFunction(null, "run", new ArrayList<GLocal>(),
            new CommandTranslator(this).translate(this), false));

        linkData.addImports("org.extex.exbib.core.db.DB");
        linkData.addImports("org.extex.exbib.core.io.*");
        linkData.addImports("org.extex.exbib.core.*");
        linkData.writeImports(writer);
        write(writer, //
            "class Style {\n", //
            "  private DB bibDB\n", //
            "  private Writer bibWriter\n", //
            "  private Processor bibProcessor\n", //
            "\n");

        for (String name : getIntegers()) {
            writer.write("  private int ");
            writer.write(GFunction.translate(name));
            writer.write(" = 0\n");
        }
        writer.write("\n");
        for (String name : getStrings()) {
            writer.write("  private String ");
            writer.write(GFunction.translate(name));
            writer.write(" = \"\"\n");
        }
        write(writer, //
            "\n" //
        );
        for (String type : types.keySet()) {
            GFunction fct = types.get(type);
            write(writer, //
                "\n  '", //
                type, //
                "' : {\n  ", //
                "\n  ", //
                "},");
        }

        write(writer, //
            "\n\n", //
            "  Style(bibDB, bibWriter, bibProcessor) {\n", //
            "    this.bibDB = bibDB\n", //
            "    this.bibWriter = bibWriter\n", //
            "    this.bibProcessor = bibProcessor\n", //
            "  }\n");

        linkData.writeMethods(writer);

        for (GFunction fct : functionList) {
            fct.print(writer, "\n" + INDENT);
        }

        List<String> strings = this.getMacroNames();
        if (!strings.isEmpty()) {
            writer.write("  {\n");
            for (String s : strings) {
                write(writer, //
                    INDENT, INDENT, //
                    s, // TODO ?
                    ": ", //
                    GString.translate(getMacro(s)), //
                    ",\n");
            }
            write(writer, //
                INDENT, //
                "].each { name, value ->\n", //
                INDENT, //
                INDENT, //
                "bibDB.bst_storeString(name, value)\n", //
                INDENT, //
                "}\n");
        }
        writer
            .write("\n}\n\nnew Style(bibDB, bibWriter, bibProcessor).run()\n");
    }

    /**
     * Write several arguments to a writer.
     * 
     * @param writer the writer
     * @param args the arguments to write
     * 
     * @throws IOException in case of an I/O error
     */
    private void write(Writer writer, String... args) throws IOException {

        for (String a : args) {
            writer.write(a);
        }
    }

}
