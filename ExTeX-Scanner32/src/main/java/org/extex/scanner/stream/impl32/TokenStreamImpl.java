/*
 * Copyright (C) 2003-2006 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.stream.impl32;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.framework.configuration.Configuration;
import org.extex.scanner.TokenStream;
import org.extex.scanner.Tokenizer;
import org.extex.scanner.base.TokenStreamBaseImpl;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.exception.InvalidCharacterScannerException;
import org.extex.scanner.exception.ScannerException;
import org.extex.scanner.stream.TokenStreamOptions;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.CatcodeVisitor;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;

/**
 * This class contains an implementation of a token stream which is fed from a
 * Reader.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4725 $
 */
public class TokenStreamImpl extends TokenStreamBaseImpl implements TokenStream {

    /**
     * This is a type-safe class to represent state information.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4725 $
     */
    private static final class State {

        /**
         * The field <tt>name</tt> contains the print name of this state for
         * debugging.
         */
        private String name;

        /**
         * Creates a new object.
         * 
         * @param name the print name of the state
         */
        public State(String name) {

            super();
            this.name = name;
        }

        /**
         * Returns a string representation of the object.
         * 
         * @return a string representation of the object.
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return name;
        }

    }

    /**
     * The constant <tt>CARET_LIMIT</tt> contains the threshold for the ^
     * notation.
     */
    protected static final int CARET_LIMIT = 0100; // 0100 = 64

    /**
     * The constant <tt>CR</tt> contains the one and only CR character.
     */
    private static final UnicodeChar CR = UnicodeChar.get(13);

    /**
     * The constant <tt>MID_LINE</tt> contains the state for the processing in
     * the middle of a line.
     */
    protected static final State MID_LINE = new State("mid line");

    /**
     * The constant <tt>NEW_LINE</tt> contains the state for the processing at
     * the beginning of a new line.
     */
    protected static final State NEW_LINE = new State("new line");

    /**
     * The constant <tt>SKIP_BLANKS</tt> contains the state for the processing
     * when spaces are ignored.
     */
    protected static final State SKIP_BLANKS = new State("skip blanks");

    /**
     * The field <tt>in</tt> contains the buffered reader for lines.
     */
    private LineNumberReader in;

    /**
     * The field <tt>line</tt> contains the current line of input.
     */
    private String line = "";

    /**
     * The index in the buffer for the next character to consider. This is an
     * invariant: after a character is read this pointer has to be advanced.
     */
    protected int pointer = 1;

    /**
     * The field <tt>saveChar</tt> contains the saved look-ahead character.
     */
    protected UnicodeChar saveChar = null;

    /**
     * The field <tt>source</tt> contains the description of the source for
     * tokens.
     */
    private String source;

    /**
     * The field <tt>state</tt> contains the current state of operation.
     */
    protected State state = NEW_LINE;

    /**
     * The field <tt>visitor</tt> contains the visitor to separate the cases
     * according to the catcode.
     */
    private CatcodeVisitor<Token, TokenFactory, Tokenizer, UnicodeChar> visitor =
            new CatcodeVisitor<Token, TokenFactory, Tokenizer, UnicodeChar>() {

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitActive(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitActive(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws CatcodeException {

                    state = MID_LINE;

                    return factory.createToken(Catcode.ACTIVE, uc, //
                        tokenizer.getNamespace());
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitComment(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitComment(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc) {

                    endLine();
                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitCr(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitCr(TokenFactory factory, Tokenizer tokenizer,
                        UnicodeChar uchar) throws CatcodeException {

                    Token t = null;

                    if (state == MID_LINE) {
                        t = factory.createToken(Catcode.SPACE, ' ', //
                            tokenizer.getNamespace());
                    } else if (state == NEW_LINE) {
                        t = factory.createToken(Catcode.ESCAPE, uchar, //
                            "par", tokenizer.getNamespace());
                    } else {
                        // drop the character
                    }

                    endLine();
                    return t;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitEscape(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitEscape(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uchar)
                        throws CatcodeException, ScannerException {

                    String namespace = tokenizer.getNamespace();

                    if (atEndOfLine()) {
                        // empty control sequence; see "The TeXbook, Chapter 8,
                        // p. 47"
                        return factory.createToken(Catcode.ESCAPE, uchar, "",
                            namespace);
                    }

                    UnicodeChar uc = getChar(tokenizer);

                    if (uc == null) {
                        return factory.createToken(Catcode.ESCAPE, uchar, "",
                            namespace);

                    } else if (tokenizer.getCatcode(uc) == Catcode.LETTER) {
                        StringBuffer sb = new StringBuffer();
                        sb.append((char) (uc.getCodePoint()));
                        state = SKIP_BLANKS;

                        while (!atEndOfLine()
                                && (uc = getChar(tokenizer)) != null) {
                            if (tokenizer.getCatcode(uc) != Catcode.LETTER) {
                                ungetChar(uc);
                                return factory.createToken(Catcode.ESCAPE,
                                    uchar, sb.toString(), namespace);
                            }
                            sb.append((char) (uc.getCodePoint()));
                        }

                        return factory.createToken(Catcode.ESCAPE, uchar, //
                            sb.toString(), namespace);

                    } else {
                        state = MID_LINE;
                        return factory.createToken(Catcode.ESCAPE, uc,
                            namespace);

                    }
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitIgnore(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitIgnore(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc) {

                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitInvalid(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitInvalid(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws InvalidCharacterScannerException {

                    state = MID_LINE;

                    throw new InvalidCharacterScannerException(uc);
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitLeftBrace(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitLeftBrace(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws CatcodeException {

                    state = MID_LINE;

                    return factory.createToken(Catcode.LEFTBRACE, uc, //
                        tokenizer.getNamespace());
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitLetter(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitLetter(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws CatcodeException {

                    state = MID_LINE;

                    return factory.createToken(Catcode.LETTER, uc, //
                        tokenizer.getNamespace());
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitMacroParam(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitMacroParam(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws CatcodeException {

                    state = MID_LINE;

                    return factory.createToken(Catcode.MACROPARAM, uc, //
                        tokenizer.getNamespace());
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitMathShift(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitMathShift(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws CatcodeException {

                    state = MID_LINE;

                    return factory.createToken(Catcode.MATHSHIFT, uc, //
                        tokenizer.getNamespace());
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitOther(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitOther(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws CatcodeException {

                    state = MID_LINE;

                    return factory.createToken(Catcode.OTHER, uc, //
                        tokenizer.getNamespace());
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitRightBrace(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitRightBrace(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws CatcodeException {

                    state = MID_LINE;

                    return factory.createToken(Catcode.RIGHTBRACE, uc, //
                        tokenizer.getNamespace());
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitSpace(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 * @see "The TeXbook [Chapter 8, page 47]"
                 */
                public Token visitSpace(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws CatcodeException {

                    if (state == MID_LINE) {
                        state = SKIP_BLANKS;
                        return factory.createToken(Catcode.SPACE, ' ',
                            Namespace.DEFAULT_NAMESPACE);
                    }

                    return null;
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitSubMark(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitSubMark(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws CatcodeException {

                    state = MID_LINE;

                    return factory.createToken(Catcode.SUBMARK, uc, //
                        tokenizer.getNamespace());
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitSupMark(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitSupMark(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws CatcodeException {

                    state = MID_LINE;

                    return factory.createToken(Catcode.SUPMARK, uc, //
                        tokenizer.getNamespace());
                }

                /**
                 * {@inheritDoc}
                 * 
                 * @see org.extex.scanner.type.CatcodeVisitor#visitTabMark(
                 *      java.lang.Object, java.lang.Object, java.lang.Object)
                 */
                public Token visitTabMark(TokenFactory factory,
                        Tokenizer tokenizer, UnicodeChar uc)
                        throws CatcodeException {

                    state = MID_LINE;

                    return factory.createToken(Catcode.TABMARK, uc, //
                        tokenizer.getNamespace());
                }

            };

    /**
     * Creates a new object.
     * 
     * @param config the configuration object for this instance; This
     *        configuration is ignored in this implementation.
     * @param options ignored here
     * @param reader the reader
     * @param isFile indicator for file streams
     * @param theSource the description of the input source
     * 
     * @throws IOException in case of an IO error
     */
    public TokenStreamImpl(Configuration config, TokenStreamOptions options,
            Reader reader, Boolean isFile, String theSource) throws IOException {

        super(isFile.booleanValue());
        this.in = new LineNumberReader(reader);
        this.source = theSource;
    }

    /**
     * Checks whether the pointer is at the end of line.
     * 
     * @return <code>true</code> iff the next reading operation would try to
     *         refill the line buffer
     */
    protected boolean atEndOfLine() {

        return (pointer > line.length());
    }

    /**
     * End the current line.
     */
    protected void endLine() {

        pointer = line.length() + 1;
    }

    /**
     * Return the next character to process. The pointer is advanced and points
     * to the character returned.
     * <p>
     * This operation might involve that an additional bunch of characters is
     * read in (with {@link #refill() refill()}).
     * </p>
     * 
     * @param tokenizer the classifier for characters
     * @return the character or <code>null</code> if no more character is
     *         available
     * @throws ScannerException in the rare case that an IO Exception has
     *         occurred.
     */
    protected UnicodeChar getChar(Tokenizer tokenizer) throws ScannerException {

        if (saveChar != null) {
            UnicodeChar uc = saveChar;
            saveChar = null;
            return uc;
        }

        UnicodeChar uc = getRawChar();

        if (uc == null) {
            do {
                if (!refill()) {
                    return null;
                }
                uc = getRawChar();

            } while (uc == null);

            state = NEW_LINE;
        }

        if (tokenizer.getCatcode(uc) == Catcode.SUPMARK) {

            int savePointer = pointer;
            UnicodeChar c = getRawChar();

            if (uc.equals(c)) {
                c = getRawChar();
                if (c == null) {
                    return null; // ^^ at end is silently ignored
                }
                int hexHigh = hex2int(c.getCodePoint());
                if (hexHigh >= 0) {
                    savePointer = pointer;
                    uc = getRawChar();
                    if (uc == null) {
                        uc = UnicodeChar.get(hexHigh);
                    } else {
                        int hexLow = hex2int(uc.getCodePoint());
                        if (hexLow < 0) {
                            pointer = savePointer;
                            uc = UnicodeChar.get(hexHigh);
                        } else {
                            uc = UnicodeChar.get((hexHigh << 4) + hexLow);
                        }
                    }
                } else {
                    hexHigh = c.getCodePoint();
                    uc = UnicodeChar.get(((hexHigh < CARET_LIMIT) //
                            ? hexHigh + CARET_LIMIT
                            : hexHigh - CARET_LIMIT));
                }
            } else {
                pointer = savePointer;
            }
        }

        return uc;
    }

    /**
     * @see org.extex.scanner.TokenStream#getLocator()
     */
    @Override
    public Locator getLocator() {

        return new Locator(source, (in == null ? 0 : in.getLineNumber()), line,
            pointer - 1);
    }

    /**
     * Get the next token when the stack is empty. This method is meant to be
     * overloaded by derived classes.
     * 
     * @param factory the factory for new tokens
     * @param tokenizer the classifies for characters
     * 
     * @return the next Token or <code>null</code>
     * @throws ScannerException in case of an error
     * 
     * @see org.extex.scanner.base.TokenStreamBaseImpl#getNext(
     *      org.extex.scanner.type.token.TokenFactory,
     *      org.extex.scanner.Tokenizer)
     */
    @Override
    protected Token getNext(TokenFactory factory, Tokenizer tokenizer)
            throws ScannerException {

        Token t = null;

        do {
            UnicodeChar uc = getChar(tokenizer);
            if (uc == null) {
                return null;
            }

            try {
                t =
                        (Token) tokenizer.getCatcode(uc).visit(visitor,
                            factory, tokenizer, uc);
            } catch (Exception e) {
                throw new ScannerException(e);
            }
        } while (t == null);

        return t;
    }

    /**
     * Get the next character from the input line.
     * 
     * @return the next raw character or <code>null</code> if none is
     *         available.
     */
    protected UnicodeChar getRawChar() {

        if (line == null) {
            return null;
        }

        if (pointer < line.length()) {
            return UnicodeChar.get(line.charAt(pointer++));
        }
        return (pointer++ > line.length() ? null : CR);
    }

    /**
     * Analyze a character and return its hex value, i.e. '0' to '9' are mapped
     * to 0 to 9 and 'a' to 'f' (case sensitive) are mapped to 10 to 15.
     * 
     * @param c the character code to analyze
     * 
     * @return the integer value of a hex digit or -1 if no hex digit is given
     */
    protected int hex2int(int c) {

        if ('0' <= c && c <= '9') {
            return c - '0';
        } else if ('a' <= c && c <= 'f') {
            return c - 'a' + 10;
        } else {
            return -1;
        }
    }

    /**
     * Check to see if a further token can be acquired from the token stream.
     * 
     * @return <code>true</code> if the stream is at its end
     * 
     * @throws ScannerException in case that an error has been encountered.
     *         Especially if an IO exceptions occurs it is delivered as chained
     *         exception in a ScannerException.
     * 
     * @see org.extex.scanner.TokenStream#isEof()
     */
    @Override
    public boolean isEof() throws ScannerException {

        if (!super.isEof()) {
            return false;
        }

        try {
            do {
                if (pointer < line.length()) {
                    return false;
                }
            } while (refill());
        } catch (ScannerException e) {
            return true;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.scanner.TokenStream#isEol()
     */
    @Override
    public boolean isEol() {

        return pointer > line.length();
    }

    /**
     * Get the next line from the input reader to be processed.
     * 
     * @return <code>true</code> iff the next line could be acquired.
     * 
     * @throws ScannerException in case of some kind of IO error
     */
    protected boolean refill() throws ScannerException {

        if (in == null) {
            return false;
        }
        try {
            if ((line = in.readLine()) == null) {
                in.close();
                in = null;
                return false;
            }
        } catch (IOException e) {
            throw new ScannerException(e);
        }
        pointer = 0;
        return true;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return source + ":" + in.getLineNumber() + "[" + pointer + "]:" + line;
    }

    /**
     * Save the look-ahead character.
     * 
     * @param uc the character to save
     */
    private void ungetChar(UnicodeChar uc) {

        saveChar = uc;
    }

}
