/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
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

package org.extex.latexParser.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import org.extex.core.UnicodeChar;
import org.extex.latexParser.api.LaTeXParser;
import org.extex.latexParser.api.Node;
import org.extex.latexParser.api.NodeList;
import org.extex.latexParser.impl.exception.SyntaxError;
import org.extex.latexParser.impl.exception.SystemException;
import org.extex.latexParser.impl.node.GroupNode;
import org.extex.latexParser.impl.node.MathEnvironment;
import org.extex.latexParser.impl.node.OptGroupNode;
import org.extex.latexParser.impl.node.TokenNode;
import org.extex.latexParser.impl.node.TokensNode;
import org.extex.latexParser.impl.util.DefinitionLoader;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.base.TokenStreamImpl;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.ActiveCharacterToken;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.CrToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.MacroParamToken;
import org.extex.scanner.type.token.MathShiftToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.RightBraceToken;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.SubMarkToken;
import org.extex.scanner.type.token.SupMarkToken;
import org.extex.scanner.type.token.TabMarkToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.extex.scanner.type.token.TokenVisitor;

/**
 * This class represents the empty LaTeX parser in the sense that no macros or
 * active characters are known.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class EmptyLaTeXParser implements LaTeXParser, ResourceAware, Parser {

    /**
     * This class provides a token visitor to switch according to the catcode.
     */
    private class ToVi implements TokenVisitor<Node, TokenStream> {

        /**
    *      java.lang.Object)
         */
        public Node visitActive(ActiveCharacterToken token, TokenStream stream)
                throws Exception {

            Macro m = macros.get(token.getChar());
            if (m == null) {
                return undefinedActive(token);
            }
            return m.parse(token, parser);
        }

        /**
    *      java.lang.Object)
         */
        public Node visitCr(CrToken token, TokenStream stream) throws Exception {

            return new TokenNode(token, getSource(), getLineno());
        }

        /**
    *      java.lang.Object)
         */
        public Node visitEscape(ControlSequenceToken token, TokenStream stream)
                throws Exception {

            Macro m = macros.get(token.getName());
            if (m == null) {
                return undefinedMacro(token);
            }

            return m.parse(token, parser);
        }

        /**
    *      java.lang.Object)
         */
        public Node visitLeftBrace(LeftBraceToken token, TokenStream stream)
                throws Exception {

            return parseGroup(token);
        }

        /**
    *      java.lang.Object)
         */
        public Node visitLetter(LetterToken token, TokenStream stream)
                throws Exception {

            return parseTokens(token, null);
        }

        /**
    *      java.lang.Object)
         */
        public Node visitMacroParam(MacroParamToken token, TokenStream stream)
                throws Exception {

            return new TokenNode(token, getSource(), getLineno());
        }

        /**
    *      java.lang.Object)
         */
        public Node visitMathShift(MathShiftToken start, TokenStream stream)
                throws Exception {

            Token t = stream.get(FACTORY, tokenizer);
            if (t == null) {
                throw new SyntaxError(parser, "unexpected EOF in math");
            }
            if (!(t instanceof MathShiftToken)) {
                stream.put(t);
                return org.extex.latexParser.impl.macro.latex.Math.collectMath(
                    parser, start, start);
            }

            GroupNode env = peek();
            if (env instanceof MathEnvironment) {
                throw new SyntaxError(parser,
                    "trying to use math when already in math started at ",
                    env.getSource(), ":", Integer.toString(env.getLineNumber()));
            }

            env = new MathEnvironment(start, t, start, t,
                getSource(), getLineno());
            push(env);
            for (Node n = parseNode(start); n != null; n = parseNode(start)) {
                env.add(n);
            }
            Token t1 = getToken();
            if (!t1.equals(t)) {
                throw new SyntaxError(parser,
                    "closing math is missing for math started at ",
                    env.getSource(), ":", Integer.toString(env.getLineNumber()));
            }

            GroupNode pop = pop();
            if (pop != env) {
                push(pop);
                throw new SyntaxError(parser,
                    "closing math is missing for math started at ",
                    env.getSource(), ":", Integer.toString(env.getLineNumber()));
            }
            return env;
        }

        /**
    *      java.lang.Object)
         */
        public Node visitOther(OtherToken token, TokenStream stream)
                throws Exception {

            return parseTokens(token, null);
        }

        /**
    *      java.lang.Object)
         */
        public Node visitRightBrace(RightBraceToken token, TokenStream stream)
                throws Exception {

            throw new SyntaxError(parser, "extra right brace (",
                token.toText(), ") encountered");
        }

        /**
    *      java.lang.Object)
         */
        public Node visitSpace(SpaceToken token, TokenStream stream)
                throws Exception {

            return parseTokens(token, null);
        }

        /**
    *      java.lang.Object)
         */
        public Node visitSubMark(SubMarkToken token, TokenStream stream)
                throws SyntaxError {

            GroupNode env = parser.peek();
            if (!(env instanceof MathEnvironment)) {
                throw new SyntaxError(parser, token.toText(),
                    " is defined in math mode only");
            }
            return new TokenNode(token, getSource(), getLineno());
        }

        /**
    *      java.lang.Object)
         */
        public Node visitSupMark(SupMarkToken token, TokenStream stream)
                throws SyntaxError {

            GroupNode env = parser.peek();
            if (!(env instanceof MathEnvironment)) {
                throw new SyntaxError(parser, token.toText(),
                    " is defined in math mode only");
            }
            return new TokenNode(token, getSource(), getLineno());
        }

        /**
    *      java.lang.Object)
         */
        public Node visitTabMark(TabMarkToken token, TokenStream stream) {

            return new TokenNode(token, getSource(), getLineno());
        }
    }

    /**
     * The field {@code factory} contains the token factory to use.
     */
    protected static final TokenFactory FACTORY = new TokenFactoryImpl();

    /**
     * The field {@code tokenizer} contains the tokenizer to use for
     * categorizing characters.
     */
    public static final Tokenizer TOKENIZER = new Tokenizer() {

        /**
         * Getter for the category code of a character.
         * 
         * @param c the Unicode character to analyze
         * 
         * @return the category code of a character
         * 
         * @see org.extex.scanner.api.Tokenizer#getCatcode(org.extex.core.UnicodeChar)
         */
        public Catcode getCatcode(UnicodeChar c) {

            if (c.isLetter()) {
                return Catcode.LETTER;
            }
            switch (c.getCodePoint()) {
                case '$':
                    return Catcode.MATHSHIFT;
                case '^':
                    return Catcode.SUPMARK;
                case '_':
                    return Catcode.SUBMARK;
                case '%':
                    return Catcode.COMMENT;
                case '&':
                    return Catcode.TABMARK;
                case '#':
                    return Catcode.MACROPARAM;
                case '{':
                    return Catcode.LEFTBRACE;
                case '}':
                    return Catcode.RIGHTBRACE;
                case '\\':
                    return Catcode.ESCAPE;
                case '~':
                    return Catcode.ACTIVE;
                case '\r':
                case '\n':
                    return Catcode.CR;
                case '\t':
                case ' ':
                    return Catcode.SPACE;
                case '\0':
                case '\f':
                    return Catcode.IGNORE;
                case '\b':
                    return Catcode.INVALID;
                default:
                    return Catcode.OTHER;
            }
        }

        /**
         * Getter for the name space.
         * 
         * @return the name space
         * 
         * @see org.extex.scanner.api.Tokenizer#getNamespace()
         */
        public String getNamespace() {

            return "";
        }

    };

    /**
     * The field {@code AT_TOKENIZER} contains the tokenizer to use for
     * categorizing characters -- including the @ character as letter.
     */
    public static final Tokenizer AT_TOKENIZER = new Tokenizer() {

        /**
         * Getter for the category code of a character.
         * 
         * @param c the Unicode character to analyze
         * 
         * @return the category code of a character
         * 
         * @see org.extex.scanner.api.Tokenizer#getCatcode(org.extex.core.UnicodeChar)
         */
        public Catcode getCatcode(UnicodeChar c) {

            if (c.isLetter()) {
                return Catcode.LETTER;
            }
            switch (c.getCodePoint()) {
                case '@':
                    return Catcode.LETTER;
                case '$':
                    return Catcode.MATHSHIFT;
                case '^':
                    return Catcode.SUPMARK;
                case '_':
                    return Catcode.SUBMARK;
                case '%':
                    return Catcode.COMMENT;
                case '&':
                    return Catcode.TABMARK;
                case '#':
                    return Catcode.MACROPARAM;
                case '{':
                    return Catcode.LEFTBRACE;
                case '}':
                    return Catcode.RIGHTBRACE;
                case '\\':
                    return Catcode.ESCAPE;
                case '~':
                    return Catcode.ACTIVE;
                case '\r':
                case '\n':
                    return Catcode.CR;
                case '\t':
                case ' ':
                    return Catcode.SPACE;
                case '\0':
                case '\f':
                    return Catcode.IGNORE;
                case '\b':
                    return Catcode.INVALID;
                default:
                    return Catcode.OTHER;
            }
        }

        /**
         * Getter for the name space.
         * 
         * @return the name space
         * 
         * @see org.extex.scanner.api.Tokenizer#getNamespace()
         */
        public String getNamespace() {

            return "";
        }

    };

    /**
     * The field {@code active} contains the definition of active characters.
     */
    private Map<UnicodeChar, Macro> active = new HashMap<UnicodeChar, Macro>();

    /**
     * The field {@code context} contains the context. The context can be used
     * to store arbitrary data
     */
    private Map<String, Object> context = new HashMap<String, Object>();

    /**
     * The field {@code finder} contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * The field {@code macros} contains the definition of macros.
     */
    private Map<String, Macro> macros = new HashMap<String, Macro>();

    /**
     * The field {@code parser} contains the reference to the parser used; i.e.
     * it is a self reference.
     */
    private Parser parser;

    /**
     * The field {@code reader} contains the reader.
     */
    private LineNumberReader reader;

    /**
     * The field {@code scanner} contains the reference to the scanner.
     */
    private TokenStream scanner;

    /**
     * The field {@code source} contains the name of the source.
     */
    private String source;

    /**
     * The field {@code tokenizer} contains the tokenizer.
     */
    private Tokenizer tokenizer = TOKENIZER;

    /**
     * The field {@code visitor} contains the token visitor for normal
     * processing.
     */
    private final TokenVisitor<Node, TokenStream> visitor = new ToVi();

    /**
     * The field {@code visitor} contains the token visitor for processing an
     * optional argument.
     */
    private final TokenVisitor<Node, TokenStream> visitorOpt = new ToVi() {

        /**
    *      org.extex.scanner.api.TokenStream)
         */
        @Override
        public Node visitLetter(LetterToken token, TokenStream stream)
                throws Exception {

            return parseTokens(token,
                FACTORY.createToken(Catcode.OTHER, ']', ""));
        }

        /**
    *      org.extex.scanner.api.TokenStream)
         */
        @Override
        public Node visitOther(OtherToken token, TokenStream stream)
                throws Exception {

            return parseTokens(token,
                FACTORY.createToken(Catcode.OTHER, ']', ""));
        }

        /**
    *      org.extex.scanner.api.TokenStream)
         */
        @Override
        public Node visitSpace(SpaceToken token, TokenStream stream)
                throws Exception {

            return parseTokens(token,
                FACTORY.createToken(Catcode.OTHER, ']', ""));
        }

    };

    /**
     * The field {@code groupStack} contains the stack of groups.
     */
    private Stack<GroupNode> groupStack = new Stack<GroupNode>();

    /**
     * The field {@code logger} contains the logger to use.
     */
    private Logger logger;


    public EmptyLaTeXParser() {

        parser = this;
    }

public void closeFileStream() {

        scanner.closeFileStream();
    }

    /**
     * Define an active character.
     * 
     * @param c the character
     * @param code the code
     * 
     * @see org.extex.latexParser.impl.Memory#def(char,
     *      org.extex.latexParser.impl.Macro)
     */
    public void def(char c, Macro code) {

        active.put(UnicodeChar.get(c), code);
    }

    /**
     * Define a macro.
     * 
     * @param name the name of the macro
     * @param code the code
     * 
     * @see org.extex.latexParser.impl.Memory#def(java.lang.String,
     *      org.extex.latexParser.impl.Macro)
     */
    public void def(String name, Macro code) {

        macros.put(name, code);
    }

public Map<String, Object> getContext() {

        return context;
    }

public Macro getDefinition(char c) {

        return active.get(UnicodeChar.get(c));
    }

public Macro getDefinition(String name) {

        return macros.get(name);
    }

public int getLineno() {

        return reader == null ? -1 : reader.getLineNumber() + 1;
    }

    /**
     * Getter for logger.
     * 
     * @return the logger
     */
    public Logger getLogger() {

        return logger;
    }

    /**
     * Getter for the source.
     * 
     * @return the name of the source
     * 
     * @see org.extex.latexParser.impl.Locator#getSource()
     */
    public String getSource() {

        return source;
    }

public Token getToken() throws ScannerException {

        return scanner.get(FACTORY, tokenizer);
    }

public TokenFactory getTokenFactory() {

        return FACTORY;
    }

public boolean isDefined(char c) {

        return active.containsKey(UnicodeChar.get(c));
    }

public boolean isDefined(String name) {

        return macros.containsKey(name);
    }

    /**
     * Load the definition of macros and active characters from a resource on
     * the classpath.
     * 
     * <pre>
     * % comment
     * \relax
     * ~
     * \begin[1]=org.extex.latexParser.impl.macro.Begin
     * </pre>
     * 
*/
    public void load(String name) throws IOException, ScannerException {

        String resource = "tex/" + name;
        InputStream stream =
                getClass().getClassLoader().getResourceAsStream(resource);
        if (stream == null) {
            throw new FileNotFoundException(resource);
        }

        try {
            DefinitionLoader.load(stream, this);
        } finally {
            stream.close();
        }
    }

    /**
*      java.lang.Object[])
     */
    public void log(String format, Object... args) {

        logger.severe(getSource() + ":" + Integer.toString(getLineno()) + ": "
                + MessageFormat.format(format, args));
    }

    /**
*      java.lang.String)
     */
    public NodeList parse(InputStream stream, String source)
            throws SyntaxError,
                ScannerException,
                IOException {

        this.source = source;
        NodeList content = new NodeList();
        LineNumberReader reader =
                new LineNumberReader(new InputStreamReader(stream));
        try {
            this.reader = reader;
            scanner = new TokenStreamImpl(null, null, reader, Boolean.TRUE,
                source);

            for (Token t = scanner.get(FACTORY, tokenizer); t != null; t =
                    scanner.get(FACTORY, tokenizer)) {
                content.add((Node) t.visit(visitor, scanner));
            }
        } catch (SyntaxError e) {
            logger.severe(e.getMessage());
        } catch (ScannerException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException("", e);
        } finally {
            stream.close();
        }
        return content;
    }

public NodeList parse(String source) throws IOException, ScannerException {

        InputStream stream = finder.findResource(source, "tex");
        if (stream == null) {
            throw new FileNotFoundException(source);
        }
        return parse(stream, source);
    }

public GroupNode parseGroup() throws ScannerException {

        Token t = getToken();
        if (t == null) {
            throw new SyntaxError(parser, "unexpected end of file");
        } else if (!(t instanceof LeftBraceToken)) {
            throw new SyntaxError(parser, "left brace expected instead of ", t
                .toText());
        }

        return parseGroup((LeftBraceToken) t);
    }

    /**
     * Parse a group enclosed in braces.
     * 
     * @param token the opening token
     * 
     * @return the group node
     * 
     * @throws ScannerException in case of an error
     */
    private GroupNode parseGroup(LeftBraceToken token) throws ScannerException {

        GroupNode group = new GroupNode(token, getSource(), getLineno());
        push(group);
        try {
            for (Token t = getToken(); t != null; t = getToken()) {
                if (t instanceof RightBraceToken) {
                    group.close((RightBraceToken) t);
                    GroupNode pop = pop();
                    if (pop != group) {
                        push(pop);
                        log("right brace expected instead of ", t.toText());
                    }
                    return group;
                }
                group.add((Node) t.visit(visitor, scanner));
            }
        } catch (ScannerException e) {
            throw e;
        } catch (Exception e) {
            // this should not happen
            throw new RuntimeException("severe programming mistake");
        }

        log("missing right brace for group started at {0}:{1}", getSource(),
            Integer.toString(getLineno()));
        try {
            group.close((RightBraceToken) getTokenFactory().createToken(
                Catcode.RIGHTBRACE, UnicodeChar.get('}'), ""));
        } catch (CatcodeException e) {
            throw new SystemException("impossible", e);
        }
        return group;
    }

public Node parseNode(Token end) throws ScannerException {

        Token t = getToken();
        if (t == null || t.equals(end)) {
            return null;
        }
        try {
            return (Node) t.visit(visitor, scanner);
        } catch (ScannerException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException("error", e);
        }
    }

    /**
*      org.extex.scanner.type.token.OtherToken)
     */
    public Node parseOptionalArgument(Token cs, OtherToken token)
            throws ScannerException {

        OptGroupNode content =
                new OptGroupNode(token, getSource(), getLineno());

        try {
            for (Token t = getToken(); t != null; t = getToken()) {

                if (t.eq(Catcode.OTHER, ']')) {
                    content.close((OtherToken) t);
                    return content;
                }
                content.add((Node) t.visit(visitorOpt, scanner));
            }
        } catch (ScannerException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("unimplemented");
        }

        throw new SyntaxError(parser,
            "unexpected end of file in optional argument of {0}", cs.toText());
    }

public Node parseTokenOrGroup() throws ScannerException {

        Token t = getToken();
        if (t == null) {
            throw new SyntaxError(parser, "unexpected end of file");
        } else if (!(t instanceof LeftBraceToken)) {
            return new TokenNode(t, getSource(), getLineno());
        }

        return parseGroup((LeftBraceToken) t);
    }

    /**
     * Collect a sequence of letter, other or space tokens.
     * 
     * @param token the first token
     * @param endToken the last token
     * 
     * @return the node containing the tokens collected
     * 
     * @throws ScannerException in case of an error
     */
    private TokensNode parseTokens(Token token, Token endToken)
            throws ScannerException {

        TokensNode list = new TokensNode(token, getSource(), getLineno());
        for (Token t = getToken(); t != null; t = getToken()) {
            if (t.equals(endToken)) {
                scanner.put(t);
                return list;
            } else if (t instanceof LetterToken || t instanceof OtherToken
                    || t instanceof SpaceToken) {
                list.add(t);
            } else {
                scanner.put(t);
                return list;
            }
        }

        return list;
    }

public GroupNode peek() {

        if (groupStack.isEmpty()) {
            return null;
        }
        return groupStack.peek();
    }

public GroupNode pop() {

        if (groupStack.isEmpty()) {
            return null;
        }
        return groupStack.pop();
    }

public void push(GroupNode content) {

        groupStack.push(content);
    }

public void put(Token t) {

        scanner.put(t);
    }

    /**
     * Setter for logger.
     * 
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

public void setResourceFinder(ResourceFinder finder) {

        this.finder = finder;
    }

public Tokenizer setTokenizer(Tokenizer tokenizer) {

        Tokenizer t = this.tokenizer;
        this.tokenizer = tokenizer;
        return t;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param token the token
     * 
     * @return the node to store
     * 
     * @throws SyntaxError in case of an error
     */
    protected Node undefinedActive(ActiveCharacterToken token)
            throws SyntaxError {

        throw new SyntaxError(parser, "undefined active character {0}",
            token.toText());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param token the token
     * 
     * @return the node to store
     * 
     * @throws SyntaxError in case of an error
     */
    protected Node undefinedMacro(ControlSequenceToken token)
            throws SyntaxError {

        throw new SyntaxError(parser, "undefined control sequence {0}",
            token.toText());
    }

}
