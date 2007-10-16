/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.latexParser.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.core.UnicodeChar;
import org.extex.latexParser.api.LaTeXParser;
import org.extex.latexParser.api.Node;
import org.extex.latexParser.impl.node.GroupNode;
import org.extex.latexParser.impl.node.OptGroupNode;
import org.extex.latexParser.impl.node.TokenNode;
import org.extex.latexParser.impl.node.TokensNode;
import org.extex.latexParser.impl.util.DefinitionLoader;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
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
 * @version $Revision$
 */
public class EmptyLaTeXParser implements LaTeXParser, ResourceAware, Parser {

    /**
     * The field <tt>factory</tt> contains the token factory to use.
     */
    protected static final TokenFactory FACTORY = new TokenFactoryImpl();

    /**
     * The field <tt>tokenizer</tt> contains the tokenizer to use for
     * categorizing characters.
     */
    protected static final Tokenizer TOKENIZER = new Tokenizer() {

        /**
         * Getter for the category code of a character.
         * 
         * @param c the Unicode character to analyze
         * 
         * @return the category code of a character
         * 
         * @see org.extex.scanner.api.Tokenizer#getCatcode(
         *      org.extex.core.UnicodeChar)
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
     * The field <tt>macros</tt> contains the ...
     */
    private Map<String, Macro> macros = new HashMap<String, Macro>();

    /**
     * The field <tt>context</tt> contains the ...
     */
    private Map<String, Object> context = new HashMap<String, Object>();

    /**
     * The field <tt>active</tt> contains the ...
     */
    private Map<UnicodeChar, Macro> active = new HashMap<UnicodeChar, Macro>();

    /**
     * The field <tt>visitor</tt> contains the ...
     */
    private final TokenVisitor<Node, TokenStream> visitor =
            new TokenVisitor<Node, TokenStream>() {

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 * @throws Exception
                 */
                public Node visitActive(ActiveCharacterToken token,
                        TokenStream stream) throws Exception {

                    Macro m = macros.get(token.getChar());
                    if (m == null) {
                        throw new SyntaxError("undefined active character");
                    }
                    return m.parse(token, parser);
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 * @throws Exception
                 */
                public Node visitCr(CrToken token, TokenStream stream)
                        throws Exception {

                    return new TokenNode(token);
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 * @throws Exception
                 */
                public Node visitEscape(ControlSequenceToken token,
                        TokenStream stream) throws Exception {

                    Macro m = macros.get(token.getName());
                    if (m == null) {
                        throw new SyntaxError(token.toText()
                                + ": undefined control sequence");
                    }
                    return m.parse(token, parser);
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 * @throws Exception
                 */
                public Node visitLeftBrace(LeftBraceToken token,
                        TokenStream stream) throws Exception {

                    return parseGroup(token);
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 * @throws Exception
                 */
                public Node visitLetter(LetterToken token, TokenStream stream)
                        throws Exception {

                    return parseTokens(token);
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 * @throws Exception
                 */
                public Node visitMacroParam(MacroParamToken token,
                        TokenStream stream) throws Exception {

                    return new TokenNode(token);
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 * @throws Exception
                 */
                public Node visitMathShift(MathShiftToken token,
                        TokenStream stream) throws Exception {

                    Token t = stream.get(FACTORY, TOKENIZER);
                    if (t == null) {
                        throw new SyntaxError("Unexpected EOF in math");
                    }
                    if (t instanceof MathShiftToken) {
                        return collectMath(token, t);
                    }
                    stream.put(t);
                    return collectMath(token, null);
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 * @throws Exception
                 */
                public Node visitOther(OtherToken token, TokenStream stream)
                        throws Exception {

                    return parseTokens(token);
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 * @throws Exception
                 */
                public Node visitRightBrace(RightBraceToken token,
                        TokenStream stream) throws Exception {

                    throw new SyntaxError("Extra right brace encountered");
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 * @throws ScannerException in case of an error
                 */
                public Node visitSpace(SpaceToken token, TokenStream stream)
                        throws ScannerException {

                    return parseTokens(token);
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 */
                public Node visitSubMark(SubMarkToken token, TokenStream stream) {

                    return new TokenNode(token);
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 */
                public Node visitSupMark(SupMarkToken token, TokenStream stream) {

                    return new TokenNode(token);
                }

                /**
                 * TODO gene: missing JavaDoc
                 * 
                 * @param token
                 * @param stream
                 * @return
                 */
                public Node visitTabMark(TabMarkToken token, TokenStream stream) {

                    return new TokenNode(token);
                }
            };

    /**
     * The field <tt>finder</tt> contains the resource finder.
     */
    private ResourceFinder finder;

    /**
     * The field <tt>parser</tt> contains the reference to the parser used;
     * i.e. it is a self reference.
     */
    private Parser parser;

    /**
     * The field <tt>scanner</tt> contains the ...
     */
    private TokenStream scanner;

    /**
     * Creates a new object.
     */
    public EmptyLaTeXParser() {

        super();
        parser = this;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param token
     * @param t
     * @return
     */
    private Node collectMath(MathShiftToken token, Token t) {

        // TODO gene: collectMath unimplemented
        return null;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param c
     * @param code
     */
    public void def(char c, Macro code) {

        active.put(UnicodeChar.get(c), code);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * @param code
     */
    public void def(String name, Macro code) {

        macros.put(name, code);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Parser#getContext()
     */
    public Map<String, Object> getContext() {

        return context;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Parser#getToken()
     */
    public Token getToken() throws ScannerException {

        return scanner.get(FACTORY, TOKENIZER);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Parser#isDefined(char)
     */
    public boolean isDefined(char c) {

        return active.containsKey(UnicodeChar.get(c));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Parser#isDefined(java.lang.String)
     */
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
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Parser#load(java.lang.String)
     */
    public void load(String name) throws IOException, ScannerException {

        String resource = "sty/" + name;
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
     * TODO gene: missing JavaDoc
     * 
     * @param reader
     * @param source
     * @param content
     * 
     * @throws Exception in case of an error
     */
    private void parse(Reader reader, String source, List<Node> content)
            throws Exception {

        scanner = new TokenStreamImpl(null, null, reader, Boolean.TRUE, source);

        for (Token t = scanner.get(FACTORY, TOKENIZER); t != null; t =
                scanner.get(FACTORY, TOKENIZER)) {
            content.add((Node) t.visit(visitor, scanner));
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.api.LaTeXParser#parse(java.lang.String)
     */
    public List<Node> parse(String source) throws IOException, ScannerException {

        List<Node> content = new ArrayList<Node>();
        InputStream stream = finder.findResource(source, "tex");
        if (stream == null) {
            throw new FileNotFoundException(source);
        }
        LineNumberReader reader =
                new LineNumberReader(new InputStreamReader(stream));
        try {
            parse(reader, source, content);
        } catch (SyntaxError e) {
            e.setLineNumber(reader.getLineNumber());
            throw e;
        } catch (ScannerException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO
        } finally {
            stream.close();
        }
        return content;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Parser#parseGroup()
     */
    public GroupNode parseGroup() throws ScannerException {

        Token t = getToken();
        if (t == null) {
            throw new SyntaxError("Unexpected EOF");
        } else if (!(t instanceof LeftBraceToken)) {
            throw new SyntaxError("Brace expected");
        }

        return parseGroup((LeftBraceToken) t);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param token the opening token
     * 
     * @return
     * 
     * @throws ScannerException in case of an error
     */
    private GroupNode parseGroup(LeftBraceToken token) throws ScannerException {

        GroupNode content = new GroupNode(token);
        try {
            for (Token t = getToken(); t != null; t = getToken()) {
                if (t instanceof RightBraceToken) {
                    content.close((RightBraceToken) t);
                    return content;
                }
                content.add((Node) t.visit(visitor, scanner));
            }
        } catch (ScannerException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("unimplemented");
        }

        throw new SyntaxError("missing right brace");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Parser#parseOptionalArgument( OtherToken)
     */
    public OptGroupNode parseOptionalArgument(OtherToken token)
            throws ScannerException {

        OptGroupNode content = new OptGroupNode(token);

        try {
            for (Token t = getToken(); t != null; t = getToken()) {

                if (t.eq(Catcode.OTHER, ']')) {
                    content.close((OtherToken) t);
                    return content;
                }
                content.add((Node) t.visit(visitor, scanner));
            }
        } catch (ScannerException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("unimplemented");
        }

        throw new SyntaxError("Unexpected EOF in optional argument");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.impl.Parser#parseTokenOrGroup()
     */
    public Node parseTokenOrGroup() throws ScannerException {

        Token t = getToken();
        if (t == null) {
            throw new SyntaxError("Unexpected EOF");
        } else if (!(t instanceof LeftBraceToken)) {
            return new TokenNode(t);
        }

        return parseGroup((LeftBraceToken) t);
    }

    /**
     * Collect a sequence of letter, other or space tokens.
     * 
     * @param token the first token
     * 
     * @return the node containing the tokens collected
     * 
     * @throws ScannerException in case of an error
     */
    private TokensNode parseTokens(Token token) throws ScannerException {

        TokensNode list = new TokensNode(token);
        for (Token t = getToken(); t != null; t = getToken()) {
            if (t instanceof LetterToken || t instanceof OtherToken
                    || t instanceof SpaceToken) {
                list.add(t);
            } else {
                scanner.put(t);
                return list;
            }
        }

        return list;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceAware#setResourceFinder(
     *      org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder finder) {

        this.finder = finder;
    }

}
