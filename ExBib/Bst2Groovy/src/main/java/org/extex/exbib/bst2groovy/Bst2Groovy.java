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
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.exbib.bst2groovy.data.EntryRefernce;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GCodeContainer;
import org.extex.exbib.bst2groovy.data.builtin.Equals;
import org.extex.exbib.bst2groovy.data.builtin.Greater;
import org.extex.exbib.bst2groovy.data.builtin.Less;
import org.extex.exbib.bst2groovy.data.builtin.Minus;
import org.extex.exbib.bst2groovy.data.builtin.Plus;
import org.extex.exbib.bst2groovy.data.builtin.SetField;
import org.extex.exbib.bst2groovy.data.builtin.SetInteger;
import org.extex.exbib.bst2groovy.data.builtin.SetLocalInteger;
import org.extex.exbib.bst2groovy.data.builtin.SetLocalString;
import org.extex.exbib.bst2groovy.data.builtin.SetString;
import org.extex.exbib.bst2groovy.data.types.CodeBlock;
import org.extex.exbib.bst2groovy.data.types.GFunction;
import org.extex.exbib.bst2groovy.data.types.GInt;
import org.extex.exbib.bst2groovy.data.types.GQuote;
import org.extex.exbib.bst2groovy.data.types.GString;
import org.extex.exbib.bst2groovy.evaluator.OpenEndedStack;
import org.extex.exbib.bst2groovy.info.AddPeriodInfo;
import org.extex.exbib.bst2groovy.info.CiteInfo;
import org.extex.exbib.bst2groovy.info.GetFieldInfo;
import org.extex.exbib.bst2groovy.info.GetIntegerInfo;
import org.extex.exbib.bst2groovy.info.GetLocalIntegerInfo;
import org.extex.exbib.bst2groovy.info.GetLocalStringInfo;
import org.extex.exbib.bst2groovy.info.GetStringInfo;
import org.extex.exbib.bst2groovy.info.IfInfo;
import org.extex.exbib.bst2groovy.info.Info;
import org.extex.exbib.bst2groovy.info.MissingInfo;
import org.extex.exbib.bst2groovy.info.NewlineInfo;
import org.extex.exbib.bst2groovy.info.PopInfo;
import org.extex.exbib.bst2groovy.info.PreambleInfo;
import org.extex.exbib.bst2groovy.info.TextLengthInfo;
import org.extex.exbib.bst2groovy.info.TextPrefixInfo;
import org.extex.exbib.bst2groovy.info.TypeInfo;
import org.extex.exbib.bst2groovy.info.WarningInfo;
import org.extex.exbib.bst2groovy.info.WidthInfo;
import org.extex.exbib.bst2groovy.info.WriteInfo;
import org.extex.exbib.core.bst.BstInterpreterCore;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.command.Command;
import org.extex.exbib.core.bst.command.CommandVisitor;
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
    public static String INDENT = "  ";

    /**
     * Translate a bst name int a groovy name for functions and variables.
     * 
     * @param value the value to translate
     * 
     * @return the translated string
     */
    public static String translate(String value) {

        String t = translationMap.get(value);
        if (t != null) {
            return t;
        }
        t = "_" + value.replaceAll("[^a-zA-Z0-9]", "_");
        translationMap.put(value, t);
        return t;
    }

    /**
     * The field <tt>factory</tt> contains the factory for bst readers.
     */
    private BstReaderFactory factory = null;

    /**
     * The field <tt>translationMap</tt> contains the already mapped names.
     */
    private static Map<String, String> translationMap =
            new HashMap<String, String>();

    /**
     * The field <tt>CV</tt> contains the command visitor for printing.
     */
    private final CommandVisitor CV = new CommandVisitor() {

        public void visitBlock(TBlock block, Object... args) throws IOException {

            // TODO gene: enclosing_method unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitChar(TChar c, Object... args) throws IOException {

            // TODO gene: visitChar unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitExecute(Command command, Object... args)
                throws IOException {

            Writer w = (Writer) args[0];
            w.write(INDENT);
            w.write(translate(command.getValue().toString()));
            w.write("()\n");
        }

        public void visitField(TField field, Object... args) throws IOException {

            // TODO gene: visitField unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitInteger(TInteger integer, Object... args)
                throws IOException {

            // TODO gene: visitInteger unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitIterate(Command command, Object... args)
                throws IOException {

            Writer w = (Writer) args[0];
            w.write(INDENT);
            w.write("bibDB.each {\n");
            w.write(INDENT);
            w.write(INDENT);
            w.write(translate(command.getValue().toString()));
            w.write("(it)\n");
            w.write(INDENT);
            w.write(")\n");
        }

        public void visitLiteral(TLiteral literal, Object... args)
                throws IOException {

            // TODO gene: visitLiteral unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitLocalInteger(TLocalInteger integer, Object... args)
                throws IOException {

            // TODO gene: visitLocalInteger unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitLocalString(TLocalString string, Object... args)
                throws IOException {

            // TODO gene: visitLocalString unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitQLiteral(TQLiteral qliteral, Object... args)
                throws IOException {

            // TODO gene: visitQLiteral unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitRead(Command command, Object... args)
                throws IOException {

            Writer writer = (Writer) args[0];
            writer.write(INDENT);
            writer.write("// read\n");
        }

        public void visitReverse(Command command, Object... args)
                throws IOException {

            Writer w = (Writer) args[0];
            w.write(INDENT);
            w.write("bibDB.getEntries().reverse().each {\n");
            w.write(INDENT);
            w.write(INDENT);
            w.write(translate(command.getValue().toString()));
            w.write("(it)\n");
            w.write(INDENT);
            w.write(")\n");
        }

        public void visitSort(Command command, Object... args)
                throws IOException {

            Writer writer = (Writer) args[0];
            writer.write(INDENT);
            writer.write("bibDB.sort();\n");
        }

        public void visitString(TString string, Object... args)
                throws IOException {

            // TODO gene: visitString unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitTokenList(TokenList list, Object... args)
                throws IOException {

            // TODO gene: visitTokenList unimplemented
            throw new RuntimeException("unimplemented");
        }

    };

    /**
     * The field <tt>infos</tt> contains the infos on functions, fields, and
     * variables.
     */
    private Map<String, Info> infos = null;

    /**
     * The field <tt>EVALUATE_TOKEN_VISITOR</tt> contains the token visitor for
     * evaluation.
     */
    private TokenVisitor EVALUATE_TOKEN_VISITOR = new TokenVisitor() {

        public void visitBlock(TBlock block, Object... args) {

            EntryRefernce entryRefernce = (EntryRefernce) args[0];
            OpenEndedStack stack = (OpenEndedStack) args[1];
            GCodeContainer code = (GCodeContainer) args[2];
            for (Token t : block) {
                evaluatePartially(t, entryRefernce, stack, code);
            }
        }

        public void visitChar(TChar c, Object... args) {

            // TODO gene: visitChar unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitField(TField field, Object... args) {

            // TODO gene: visitField unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitInteger(TInteger integer, Object... args) {

            OpenEndedStack stack = (OpenEndedStack) args[1];
            stack.push(new GInt(integer.getInt()));
        }

        public void visitLiteral(TLiteral literal, Object... args) {

            String name = literal.getValue().toString();
            Info info = infos.get(name);
            if (info == null) {
                throw new RuntimeException(name + " is unknown");
            }

            info.evaluate((EntryRefernce) args[0], (OpenEndedStack) args[1],
                (GCodeContainer) args[2], (Evaluator) args[3]);
        }

        public void visitLocalInteger(TLocalInteger integer, Object... args) {

            // TODO gene: visitLocalInteger unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitLocalString(TLocalString string, Object... args) {

            // TODO gene: visitLocalString unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitQLiteral(TQLiteral qliteral, Object... args) {

            String name = qliteral.getValue();
            Info info = infos.get(name);
            if (info == null) {
                throw new RuntimeException(name + " is unknown");
            }

            info.evaluate((EntryRefernce) args[0], (OpenEndedStack) args[1],
                (GCodeContainer) args[2], (Evaluator) args[3]);
        }

        public void visitString(TString string, Object... args) {

            OpenEndedStack stack = (OpenEndedStack) args[1];
            stack.push(new GString(string.getValue()));
        }

        public void visitTokenList(TokenList list, Object... args) {

            EntryRefernce entryRefernce = (EntryRefernce) args[0];
            OpenEndedStack stack = (OpenEndedStack) args[1];
            GCodeContainer code = (GCodeContainer) args[2];
            for (Token t : list) {
                evaluatePartially(t, entryRefernce, stack, code);

            }
        }
    };

    /**
     * The field <tt>EVALUATE_PARTIALLY_TOKEN_VISITOR</tt> contains the token
     * visitor for partial evaluation.
     */
    private TokenVisitor EVALUATE_PARTIALLY_TOKEN_VISITOR = new TokenVisitor() {

        public void visitBlock(TBlock block, Object... args) {

            OpenEndedStack stack = (OpenEndedStack) args[1];
            stack.push(new CodeBlock(block));
        }

        public void visitChar(TChar c, Object... args) {

            // TODO gene: visitChar unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitField(TField field, Object... args) {

            // TODO gene: visitField unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitInteger(TInteger integer, Object... args) {

            OpenEndedStack stack = (OpenEndedStack) args[1];
            stack.push(new GInt(integer.getInt()));
        }

        public void visitLiteral(TLiteral literal, Object... args) {

            String name = literal.getValue().toString();
            Info info = infos.get(name);
            if (info == null) {
                throw new RuntimeException(name + " is unknown");
            }

            info.evaluate((EntryRefernce) args[0], (OpenEndedStack) args[1],
                (GCodeContainer) args[2], (Evaluator) args[3]);
        }

        public void visitLocalInteger(TLocalInteger integer, Object... args) {

            // TODO gene: visitLocalInteger unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitLocalString(TLocalString string, Object... args) {

            // TODO gene: visitLocalString unimplemented
            throw new RuntimeException("unimplemented");
        }

        public void visitQLiteral(TQLiteral qliteral, Object... args) {

            OpenEndedStack stack = (OpenEndedStack) args[1];
            stack.push(new GQuote(qliteral));
        }

        public void visitString(TString string, Object... args) {

            OpenEndedStack stack = (OpenEndedStack) args[1];
            stack.push(new GString(string.getValue()));
        }

        public void visitTokenList(TokenList list, Object... args) {

            EntryRefernce entryRefernce = (EntryRefernce) args[0];
            OpenEndedStack stack = (OpenEndedStack) args[1];
            GCodeContainer code = (GCodeContainer) args[2];
            for (Token t : list) {
                evaluatePartially(t, entryRefernce, stack, code);

            }
        }
    };

    /**
     * The field <tt>functionList</tt> contains the list of functions.
     */
    private List<GFunction> functionList = new ArrayList<GFunction>();

    {
        if (infos == null) {
            infos = new HashMap<String, Info>();
        }
        infos.put(">", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                GCode a = stack.pop();
                GCode b = stack.pop();
                stack.push(new Greater(a, b));
            }
        });
        infos.put("<", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                GCode a = stack.pop();
                GCode b = stack.pop();
                stack.push(new Less(a, b));
            }
        });
        infos.put("=", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                GCode a = stack.pop();
                GCode b = stack.pop();
                stack.push(new Equals(a, b));
            }
        });
        infos.put("+", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                GCode a = stack.pop();
                GCode b = stack.pop();
                stack.push(new Plus(a, b));
            }
        });
        infos.put("-", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                GCode a = stack.pop();
                GCode b = stack.pop();
                stack.push(new Minus(a, b));
            }
        });
        infos.put("*", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put(":=", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                GCode q = stack.pop();
                GCode value = stack.pop();
                if (!(q instanceof GQuote)) {
                    throw new RuntimeException("illegal variable type: "
                            + q.toString());
                }
                String v = ((GQuote) q).getToken().getValue();

                Code f = getFunction(v);
                if (!(f instanceof MacroCode)) {
                    throw new RuntimeException(
                        "unable to determine variable type for " + v);
                }
                Token t = ((MacroCode) f).getToken();

                if (t instanceof TField) {
                    code.add(new SetField(entryRefernce.getName(), v, value));
                } else if (t instanceof TString) {
                    code.add(new SetString(v, value));
                } else if (t instanceof TInteger) {
                    code.add(new SetInteger(v, value));
                } else if (t instanceof TLocalString) {
                    code.add(new SetLocalString(entryRefernce.getName(), v,
                        value));
                } else if (t instanceof TLocalInteger) {
                    code.add(new SetLocalInteger(entryRefernce.getName(), v,
                        value));
                } else {
                    throw new RuntimeException(
                        "unable to determine variable type");
                }
            }
        });
        infos.put("add.period$", new AddPeriodInfo());
        infos.put("call.type$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("change.case$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("chr.to.int$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("cite$", new CiteInfo());
        infos.put("duplicate$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                GCode a = stack.pop();
                stack.push(a);
                stack.push(a);
            }
        });
        infos.put("empty$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("format.name$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("if$", new IfInfo());
        infos.put("int.to.chr$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("int.to.str$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("missing$", new MissingInfo());
        infos.put("newline$", new NewlineInfo());
        infos.put("num.names$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("pop$", new PopInfo());
        infos.put("preamble$", new PreambleInfo());
        infos.put("purify$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("quote$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                stack.push(new GString("\""));
            }
        });
        infos.put("skip$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                //
            }
        });
        infos.put("stack$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("substring$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("swap$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                GCode a = stack.pop();
                GCode b = stack.pop();
                stack.push(a);
                stack.push(b);
            }
        });
        infos.put("text.length$", new TextLengthInfo());
        infos.put("text.prefix$", new TextPrefixInfo());
        infos.put("top$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("type$", new TypeInfo());
        infos.put("warning$", new WarningInfo());
        infos.put("while$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("width$", new WidthInfo());
        infos.put("write$", new WriteInfo());
        infos.put("global.max$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
        infos.put("entry.max$", new Info() {

            public void evaluate(EntryRefernce entryRefernce,
                    OpenEndedStack stack, GCodeContainer code,
                    Evaluator evaluator) {

                // TODO gene: enclosing_method unimplemented
                throw new RuntimeException("unimplemented");
            }
        });
    }

    /**
     * Creates a new object.
     * 
     * @throws ExBibImpossibleException this should never happen
     */
    public Bst2Groovy() throws ExBibImpossibleException {

        super();
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

        if (infos == null) {
            infos = new HashMap<String, Info>();
        }

        if (body instanceof MacroCode) {
            Token token = ((MacroCode) body).getToken();
            if (token instanceof TokenList) {
                analyzeFunction(name, (TokenList) token);
            } else if (token instanceof TField) {
                infos.put(name, new GetFieldInfo(((TField) token).getName()));
            } else if (token instanceof TInteger) {
                infos.put(name, new GetIntegerInfo(name));
            } else if (token instanceof TString) {
                infos.put(name, new GetStringInfo(name));
            } else if (token instanceof TLocalInteger) {
                infos.put(name, new GetLocalIntegerInfo(((TLocalInteger) token)
                    .getName()));
            } else if (token instanceof TLocalString) {
                infos.put(name, new GetLocalStringInfo(((TLocalString) token)
                    .getName()));
            }
        } else if (body instanceof TField) {
            infos.put(name, new GetFieldInfo(((TField) body).getName()));
        } else if (body instanceof TInteger) {
            infos.put(name, new GetIntegerInfo(name));
        } else if (body instanceof TString) {
            infos.put(name, new GetStringInfo(name));
        } else if (body instanceof TLocalInteger) {
            infos.put(name, new GetLocalIntegerInfo(((TLocalInteger) body)
                .getName()));
        } else if (body instanceof TLocalString) {
            infos.put(name, new GetLocalStringInfo(((TLocalString) body)
                .getName()));
        }
        super.addFunction(name, body, locator);
    }

    /**
     * Analyze a function.
     * 
     * @param name the name
     * @param body the BST code
     */
    private void analyzeFunction(String name, TokenList body) {

        OpenEndedStack.reset();
        OpenEndedStack stack = new OpenEndedStack();
        GCodeContainer code = new GCodeContainer();
        evaluatePartially(body, new EntryRefernce("entry"), stack, code);

        List<GCode> st = stack.getStack();
        GCode ret;
        if (st.isEmpty()) {
            ret = null;
        } else if (st.size() == 1) {
            ret = st.get(0);
        } else {
            throw new RuntimeException("function " + name
                    + ": complex return values unimplemented " + st.toString());
        }

        GFunction f = new GFunction(ret, name, stack.getFutures(), code);
        functionList.add(f);

        Writer writer = new OutputStreamWriter(System.out);
        try {
            f.print(writer, "");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("unimplemented");
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.Evaluator#evaluate(org.extex.exbib.core.bst.token.Token,
     *      org.extex.exbib.bst2groovy.data.EntryRefernce,
     *      org.extex.exbib.bst2groovy.evaluator.OpenEndedStack,
     *      org.extex.exbib.bst2groovy.data.GCodeContainer)
     */
    public void evaluate(Token token, EntryRefernce entryRefernce,
            OpenEndedStack stack, GCodeContainer code) {

        try {
            token.visit(EVALUATE_TOKEN_VISITOR, entryRefernce, stack, code,
                this);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
    }

    public void evaluatePartially(Token token, EntryRefernce entryRefernce,
            OpenEndedStack stack, GCodeContainer code) {

        try {
            token.visit(EVALUATE_PARTIALLY_TOKEN_VISITOR, entryRefernce, stack,
                code, this);
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
     */
    public void run(Writer writer, String... styles) throws IOException {

        addBibliographyStyle(styles);
        write(writer);
    }

    /**
     * Write the translated program.
     * 
     * @param writer the target writer
     * 
     * @throws IOException in case of an I/O error
     */
    public void write(Writer writer) throws IOException {

        writer.write("import org.extex.exbib.core.db.DB\n");
        writer.write("import org.extex.exbib.core.io.*\n");
        writer.write("import org.extex.exbib.core.*\n");
        writer.write("\n");
        writer.write("class Style {\n");
        writer.write("  private DB bibDB\n");
        writer.write("  private Writer bibWriter\n");
        writer.write("  private Processor bibProcessor\n");
        writer.write("\n");

        for (String name : getIntegers()) {
            writer.write("  private int ");
            writer.write(translate(name));
            writer.write(" = 0\n");
        }
        for (String name : getStrings()) {
            writer.write("  private String ");
            writer.write(translate(name));
            writer.write(" = 0\n");
        }
        writer.write("  Style(bibDB, bibWriter, bibProcessor) {\n");
        writer.write("    this.bibDB = bibDB\n");
        writer.write("    this.bibWriter = bibWriter\n");
        writer.write("    this.bibProcessor = bibProcessor\n");
        writer.write("  }\n");
        writer.write("\n");
        writer.write("  void run() {\n");

        for (Command c : this) {
            c.visit(CV, writer);
        }
        writer.write("  }\n");
        writer.write("}\n\n");

        List<String> strings = this.getStrings();
        if (!strings.isEmpty()) {
            writer.write("  {\n");
            for (String s : strings) {
                writer.write("  ");
                writer.write(s); // TODO ?
                writer.write(": \"");
                writer.write(GString.translate(getMacro(s)));
                writer.write("\",\n");
            }
            writer.write("].each { name, value ->\n");
            writer.write("    bibDB.bst_storeString(name, value)\n");
            writer.write("}\n");
        }
        writer.write("new Style(bibDB, bibWriter, bibProcessor).run()\n");
    }

}
