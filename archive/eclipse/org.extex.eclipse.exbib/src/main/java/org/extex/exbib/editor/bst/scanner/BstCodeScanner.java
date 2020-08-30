/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.exbib.editor.bst.scanner;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.extex.exbib.editor.bst.ColorManager;
import org.extex.exbib.editor.bst.model.BstModel;
import org.extex.exbib.editor.bst.model.Entry;
import org.extex.exbib.editor.bst.model.EntryFields;
import org.extex.exbib.editor.bst.model.EntryIntegers;
import org.extex.exbib.editor.bst.model.EntryStrings;
import org.extex.exbib.editor.bst.model.Execute;
import org.extex.exbib.editor.bst.model.Field;
import org.extex.exbib.editor.bst.model.Function;
import org.extex.exbib.editor.bst.model.FunctionCall;
import org.extex.exbib.editor.bst.model.Iterate;
import org.extex.exbib.editor.bst.model.Macro;
import org.extex.exbib.editor.bst.model.Read;
import org.extex.exbib.editor.bst.model.Reverse;
import org.extex.exbib.editor.bst.model.Sort;
import org.extex.exbib.editor.bst.model.VarInteger;
import org.extex.exbib.editor.bst.model.VarMacro;
import org.extex.exbib.editor.bst.model.VarString;
import org.extex.exbib.editor.bst.properties.BstEditorPreferences;

/**
 * This is a parser for BST input which provides its information to a model.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class BstCodeScanner extends RuleBasedScanner {

    /**
     * This is an abstract class for a parser of an list.
     */
    private abstract class ListParser<T> {

        /**
         * Do something when the start of the list is encountered. The default
         * implementation does nothing.
         * 
         * @param r
         * @param scanner
         */
        public void atStart(ParserRule r, RuleBasedScanner scanner) {

        }

        /**
         * Create an element of the list.
         * 
         * @param offset the offset to be recorded
         * @param value the name
         * 
         * @return
         */
        abstract T createElement(int offset, String value);

        /**
         * Parse a list of variable items enclosed in braces.
         * 
         * @param r the parser rule
         * @param scanner the character source
         * 
         * @return the token list or {@code null} at EOF before the list
         *         was complete
         */
        T[] parse(ParserRule r, RuleBasedScanner scanner, T[] a) {

            int c = r.skipSpace();
            if (c != '{') {
                return null;
            }
            atStart(r, scanner);

            List<T> list = new ArrayList<T>();

            for (c = r.skipSpace(); c >= 0; c = r.skipSpace()) {
                if (c == '}') {
                    return list.toArray(a);
                } else if ("{#%\"\'".indexOf(c) >= 0) {
                    break;
                }

                Object t = r.tokenize(c);
                if (t instanceof String) {
                    list.add(createElement(scanner.getTokenOffset(),
                        (String) t));
                } else {
                    break;
                }
            }

            return null;
        }
    }

    /**
     * TODO gene: missing JavaDoc.
     */
    private final class ParserRule implements IRule {

        /**
         * The field {@code skip} contains the number of characters read so
         * far.
         */
        private int skip;

        /**
         * The field {@code scanner} contains the scanner. It is used to avoid
         * passing around the scanner in arguments.
         */
        private RuleBasedScanner scanner;

    @Override
        public IToken evaluate(ICharacterScanner characterScanner) {

            scanner = (RuleBasedScanner) characterScanner;
            skip = 0;
            int c = nextChar();
            Object t = tokenize(c);
            if (t instanceof IToken) {
                return (IToken) t;
            } else if (t instanceof String) {
                int offset = scanner.getTokenOffset();
                switch (model.getClassification((String) t)) {
                    case COMMAND_ENTRY:
                        return processEntry(offset);
                    case COMMAND_EXECUTE:
                        return processExecute(offset);
                    case COMMAND_FUNCTION:
                        return processFunction(offset);
                    case COMMAND_INTEGERS:
                        return processIntegers(offset);
                    case COMMAND_ITERATE:
                        return processIterate(offset);
                    case COMMAND_MACRO:
                        return processMacro(offset);
                    case COMMAND_READ:
                        return processRead(offset);
                    case COMMAND_REVERSE:
                        return processReverse(offset);
                    case COMMAND_STRINGS:
                        return processStrings(offset);
                    case COMMAND_SORT:
                        return processSort(offset);
                    case BUILTIN:
                        return keywordToken;
                    case FIELD:
                        return fieldToken;
                    case FUNCTION:
                        return functionToken;
                    case LOCAL_STRING:
                        return localStringToken;
                    case LOCAL_NUMBER:
                        return localNumberToken;
                    case GLOBAL_STRING:
                        return globalStringToken;
                    case GLOBAL_NUMBER:
                        return globalNumberToken;
                    case MACRO:
                        return macroToken;
                    default:
                        return errorToken;
                }
            }
            return errorToken;
        }

        /**
         * Retrieve the next character and keep track of the number or
         * characters already read.
         * 
         * @return the next character or EOF
         */
        private int nextChar() {

            int c = scanner.read();
            if (c >= 0) {
                skip++;
            }
            return c;
        }

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param offset the offset in the document
         * 
         * @return
         */
        private IToken processEntry(int offset) {

            final int[] pos = new int[]{0};
            Field[] arg = new ListParser<Field>() {

                /**
            *      org.eclipse.jface.text.rules.RuleBasedScanner)
                 */
                @Override
                public void atStart(ParserRule r, RuleBasedScanner scanner) {

                    pos[0] = skip;
                }

                @Override
                public Field createElement(int offset, String value) {

                    return new Field(offset + skip, value);
                }

            }.parse(this, scanner, new Field[]{});

            if (arg != null) {
                EntryFields fields = new EntryFields(offset + pos[0], arg);
                model.define(arg);
                VarInteger[] arg2 = new ListParser<VarInteger>() {

                    /**
                *      org.eclipse.jface.text.rules.RuleBasedScanner)
                     */
                    @Override
                    public void atStart(ParserRule r, RuleBasedScanner scanner) {

                        pos[0] = skip;
                    }

                    @Override
                    public VarInteger createElement(int offset, String value) {

                        return new VarInteger(offset + skip, value, true);
                    }

                }.parse(this, scanner, new VarInteger[]{});

                if (arg2 != null) {
                    model.define(offset + pos[0], true, arg2);
                    EntryIntegers ints =
                            new EntryIntegers(offset + pos[0], arg2);
                    VarString[] arg3 = new ListParser<VarString>() {

                        /**
                    *      org.eclipse.jface.text.rules.RuleBasedScanner)
                         */
                        @Override
                        public void atStart(ParserRule r,
                                RuleBasedScanner scanner) {

                            pos[0] = skip;
                        }

                        @Override
                        public VarString createElement(int offset, String value) {

                            return new VarString(offset + skip, value, true);
                        }

                    }.parse(this, scanner, new VarString[]{});

                    if (arg3 != null) {
                        model.define(offset + pos[0], true, arg3);
                        EntryStrings strs =
                                new EntryStrings(offset + pos[0], arg3);
                        model.add(new Entry(offset, fields, ints, strs));
                        rewind(5);
                        return commandToken;
                    }
                }
            }
            rewind(5);
            return errorToken;
        }

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param offset
         * 
         * @return
         */
        private IToken processExecute(int offset) {

            FunctionCall[] arg = new ListParser<FunctionCall>() {

                @Override
                public FunctionCall createElement(int offset, String value) {

                    return new FunctionCall(offset + skip, value);
                }
            }.parse(this, scanner, new FunctionCall[]{});

            if (arg == null || arg.length != 1) {
                rewind(7);
                return errorToken;
            }
            model.add(new Execute(offset, arg[0]));
            rewind(7);
            return commandToken;
        }

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param offset
         * @return
         */
        private IToken processFunction(int offset) {

            FunctionCall[] arg = new ListParser<FunctionCall>() {

                @Override
                public FunctionCall createElement(int offset, String value) {

                    return new FunctionCall(offset + skip, value);
                }
            }.parse(this, scanner, new FunctionCall[]{});
            if (arg == null || arg.length != 1) {
                rewind(8);
                return errorToken;
            }
            model.defineFunction(new Function(offset, arg[0]));
            rewind(8);
            return commandToken;
        }

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param offset
         * @param token
         * @return
         */
        private IToken processIntegers(int offset) {

            VarInteger[] arg = new ListParser<VarInteger>() {

                @Override
                public VarInteger createElement(int offset, String value) {

                    return new VarInteger(offset + skip, value, false);
                }
            }.parse(this, scanner, new VarInteger[]{});
            if (arg == null) {
                rewind(8);
                return errorToken;
            }
            model.define(offset + skip, false, arg);
            rewind(8);
            return commandToken;
        }

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param offset
         * 
         * @return
         */
        private IToken processIterate(int offset) {

            FunctionCall[] arg = new ListParser<FunctionCall>() {

                @Override
                public FunctionCall createElement(int offset, String value) {

                    return new FunctionCall(offset + skip, value);
                }
            }.parse(this, scanner, new FunctionCall[]{});

            if (arg == null || arg.length != 1) {
                rewind(7);
                return errorToken;
            }
            model.add(new Iterate(offset, arg[0]));
            rewind(7);
            return commandToken;
        }

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param offset
         * 
         * @return
         */
        private IToken processMacro(int offset) {

            VarMacro[] arg = new ListParser<VarMacro>() {

                @Override
                public VarMacro createElement(int offset, String value) {

                    return new VarMacro(offset + skip, value);
                }
            }.parse(this, scanner, new VarMacro[]{});

            if (arg == null || arg.length != 1) {
                rewind(5);
                return Token.UNDEFINED;
            }
            model.defineMacro(new Macro(offset, arg[0]));
            rewind(5);
            return commandToken;
        }

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param offset
         */
        private IToken processRead(int offset) {

            model.add(new Read(offset));
            return commandToken;
        }

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param offset
         * @param token
         * @return
         */
        private IToken processReverse(int offset) {

            FunctionCall[] arg = new ListParser<FunctionCall>() {

                @Override
                public FunctionCall createElement(int offset, String value) {

                    return new FunctionCall(offset + skip, value);
                }
            }.parse(this, scanner, new FunctionCall[]{});

            if (arg == null || arg.length != 1) {
                rewind(7);
                return errorToken;
            }
            model.add(new Reverse(offset, arg[0]));
            rewind(7);
            return commandToken;
        }

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param offset
         */
        private IToken processSort(int offset) {

            model.add(new Sort(offset));
            return commandToken;
        }

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param offset
         * 
         * @return the token
         */
        private IToken processStrings(int offset) {

            VarString[] arg = new ListParser<VarString>() {

                @Override
                public VarString createElement(int offset, String value) {

                    return new VarString(offset + skip, value, false);
                }
            }.parse(this, scanner, new VarString[]{});
            if (arg == null) {
                rewind(7);
                return errorToken;
            }
            model.define(offset, false, arg);
            rewind(7);
            return commandToken;
        }

        /**
         * Rewind the input stream to the position pointed at by the skip value.
         * 
         * @param leave the number of characters to leave untouched
         */
        private void rewind(int leave) {

            for (; skip > leave; skip--) {
                scanner.unread();
            }
            skip = 0;
        }

        /**
         * Read ahead until the next non-whitespace character is found.
         * 
         * @return the next character or EOF
         */
        private int skipSpace() {

            int c;

            for (c = nextChar(); c >= 0; c = nextChar()) {
                if (c == '%') {
                    do {
                        c = nextChar();
                        if (c < 0) {
                            return c;
                        }
                    } while (c != '\r' && c != '\n');
                } else if (!Character.isWhitespace(c)) {
                    return c;
                }
            }
            return c;
        }

        /**
         * Retrieve the next token from the input stream.
         * 
         * @param ch the initial character
         * 
         * @return the next token or {@code null}
         */
        private Object tokenize(int ch) {

            int c;

            switch (ch) {
                case RuleBasedScanner.EOF:
                    return Token.EOF;
                case '\'':
                case '}':
                case '{':
                    return defaultToken;
                case '"':
                    for (c = nextChar(); c != '"'; c = nextChar()) {
                        if (c < 0) {
                            return errorToken;
                        }
                    }
                    return stringToken;
                case '#':
                    c = nextChar();
                    if (c == '-' || c == '+') {
                        c = nextChar();
                    }
                    if (!Character.isDigit(c)) {
                        return errorToken;
                    }
                    do {
                        c = nextChar();
                    } while (Character.isDigit(c));
                    unread(scanner, c);
                    return numberToken;
                case '%':
                    do {
                        c = nextChar();
                    } while (c >= 0 && c != '\r' && c != '\n');
                    return commentToken;
                default:
                    if (Character.isWhitespace(ch)) {
                        do {
                            c = nextChar();
                        } while (Character.isWhitespace(c));
                        unread(scanner, c);
                        return Token.WHITESPACE;
                    }
                    // continue with extracting a string
            }
            StringBuilder buffer = new StringBuilder();
            buffer.append((char) ch);
            for (c = nextChar(); c >= 0 && !Character.isWhitespace(c)
                    && "{}#%\"\'".indexOf(c) < 0; c = nextChar()) {
                buffer.append((char) c);
            }
            unread(scanner, c);
            return buffer.toString();
        }

        /**
         * Undo the last read instruction.
         * 
         * @param scanner the character source
         * @param c TODO
         */
        public void unread(RuleBasedScanner scanner, int c) {

            if (c >= 0) {
                scanner.unread();
                skip--;
            }
        }
    }

    /**
     * The field {@code commandToken} contains the token used for commands.
     */
    private Token commandToken;

    private Token keywordToken;

    private Token stringToken;

    private Token numberToken;

    private Token defaultToken;

    private Token errorToken;

    private Token functionToken;

    private Token localStringToken;

    private Token localNumberToken;

    private Token globalStringToken;

    private Token globalNumberToken;

    private Token macroToken;

    private Token fieldToken;

    /**
     * The field {@code model} contains the model.
     */
    private final BstModel model;

    private Token commentToken;

    /**
     * Creates a new object.
     * 
     * @param manager the color manager
     */
    public BstCodeScanner(ColorManager manager, BstModel model) {

        this.model = model;

        defaultToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_DEFAULT)));
        setDefaultReturnToken(defaultToken);

        commandToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_COMMAND)));
        commentToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_COMMENT)));
        functionToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_FUNCTION)));
        fieldToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_FIELD)));
        localStringToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_LOCAL_STRING)));
        globalStringToken =
                new Token(
                    new TextAttribute(
                        manager
                            .getColor(BstEditorPreferences.PREFERENCE_FG_GLOBAL_STRING)));
        localNumberToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_LOCAL_NUMBER)));
        globalNumberToken =
                new Token(
                    new TextAttribute(
                        manager
                            .getColor(BstEditorPreferences.PREFERENCE_FG_GLOBAL_NUMBER)));
        keywordToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_KEYWORD)));
        stringToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_STRING)));
        numberToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_NUMBER)));
        functionToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_FUNCTION)));
        macroToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_MACRO)));

        errorToken =
                new Token(new TextAttribute(manager
                    .getColor(BstEditorPreferences.PREFERENCE_FG_ERROR), null,
                    TextAttribute.UNDERLINE));

        setRules(new IRule[]{// new EndOfLineRule("%", commentToken),
        new ParserRule()// ,
        // new WhitespaceRule(new BstWhitespaceDetector())
        });
    }

}
