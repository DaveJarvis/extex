/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.lisp.parser;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.extex.exindex.lisp.exception.SyntaxEofException;
import org.extex.exindex.lisp.exception.SyntaxException;
import org.extex.exindex.lisp.type.value.LChar;
import org.extex.exindex.lisp.type.value.LDouble;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LNumber;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is a parser for a Lisp-like language.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LParser implements ResourceLocator {

    /**
     * The field <tt>CLOSE</tt> contains the internal indicator that a closing
     * parenthesis has been found.
     */
    private static final LValue CLOSE = new LNumber(0);

    /**
     * The field <tt>reader</tt> contains the reader.
     */
    private LineNumberReader reader;

    /**
     * The field <tt>c</tt> contains the push-back character or -1 for none.
     */
    private int c = -1;

    /**
     * The field <tt>resource</tt> contains the resource for error messages or
     * <code>null</code>.
     */
    private final String resource;

    /**
     * The field <tt>escape</tt> contains the escape character.
     */
    private char escape = '\\';

    /**
     * Creates a new object.
     * 
     * @param reader the reader
     * @param resource the resource for error messages or <code>null</code>
     */
    public LParser(Reader reader, String resource) {

        this.resource = resource;
        this.reader = new LineNumberReader(reader);
    }

    /**
     * Close the parser and the reader associated with it. Attempts to read
     * afterwards will result in an EOF.
     * 
     * @throws IOException in case of an I/O error
     */
    public void close() throws IOException {

        if (reader != null) {
            reader.close();
            reader = null;
        }
    }

    /**
     * Getter for escape.
     * 
     * @return the escape
     */
    public char getEscape() {

        return escape;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.parser.ResourceLocator#getLineNumber()
     */
    public int getLineNumber() {

        return reader.getLineNumber();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.parser.ResourceLocator#getResource()
     */
    public String getResource() {

        return resource;
    }

    /**
     * Check whether the given character is not one of the special characters
     * ending a symbol.
     * 
     * @param cc the character to check
     * 
     * @return <code>true</code> iff the character is special
     */
    private boolean isNotSpecial(int cc) {

        return !Character.isWhitespace(cc) && cc != '(' && cc != ')'
                && cc != ';' && cc != '"' && cc != '\'' && cc != '#';
    }

    /**
     * Parse an LValue.
     * 
     * @return the LValue or <code>null</code> at end of file
     * 
     * @throws IOException in case of an I/O error
     */
    public LValue read() throws IOException {

        if (reader == null) {
            return null;
        }
        return read(false);
    }

    /**
     * Parse an LNode.
     * 
     * @param accept indicator whether a closing parenthesis is acceptable
     * 
     * @return the LNode or <code>null</code> at end of file
     * 
     * @throws IOException in case of an I/O error
     */
    private LValue read(boolean accept) throws IOException {

        if (c < 0) {
            c = reader.read();
        }

        while (c >= 0) {

            if (Character.isWhitespace(c)) {
                // ignore
                c = reader.read();
            } else if (c == ';') {
                for (c = reader.read(); c >= 0 && c != '\n' && c != '\r'; c =
                        reader.read()) {
                    // skip to eol
                }
                c = reader.read();
            } else if (Character.isDigit(c) || c == '.' || c == '-') {
                return readNumber();
            } else if (c == '"') {
                return readString();
            } else if (c == '(') {
                LList list = new LList();
                c = -1;
                for (LValue n = read(true); n != CLOSE; n = read(true)) {
                    if (n == null) {
                        throw new SyntaxEofException(resource);
                    }
                    list.add(n);
                }
                return list;
            } else if (c == ')') {
                if (accept) {
                    c = -1;
                    return CLOSE;
                }
                throw new SyntaxException(resource);
            } else if (c == '\'') {
                c = -1;
                LValue node = read(false);
                if (node == null) {
                    throw new SyntaxEofException(resource);
                }
                return new LList(LSymbol.get("quote"), node);
            } else if (c == '#') {
                return readCharacter();
            } else {
                return readSymbol();
            }
        }

        return null;
    }

    /**
     * Read a character constant.
     * 
     * @return the character object
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     * @throws SyntaxEofException in case of an unexpected EOF
     */
    private LChar readCharacter()
            throws IOException,
                SyntaxException,
                SyntaxEofException {

        c = reader.read();
        if (c == '\\') {
            c = reader.read();
            if (c < 0) {
                throw new SyntaxEofException(resource);
            }
        } else if (c == 'u') {
            c = readUnicode();
        } else {
            throw new SyntaxException(resource);
        }
        LChar lc = new LChar((char) c);
        c = reader.read();
        return lc;
    }

    /**
     * Read a hexadecimal digit and return its value 0 to 15.
     * 
     * @return the value
     * 
     * @throws IOException in case of an error
     */
    private int readHex() throws IOException {

        int c = reader.read();
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'a' && c <= 'f') {
            return c - 'a';
        } else if (c >= 'A' && c <= 'F') {
            return c - 'A';
        }
        throw new SyntaxException(resource);
    }

    /**
     * Read a number. <br/> Note: The e notation is not supported yet
     * 
     * @return the number object
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    private LValue readNumber() throws IOException, SyntaxException {

        boolean dot = (c == '.');
        boolean hasChar = Character.isDigit(c);
        StringBuilder sb = new StringBuilder();

        sb.append((char) c);
        for (;;) {
            c = reader.read();
            if (c < 0) {
                break;
            } else if (Character.isDigit(c)) {
                hasChar = true;
            } else if (c == '.' && !dot) {
                dot = true;
            } else {
                break;
            }
            sb.append((char) c);
        }
        if (!hasChar) {
            throw new SyntaxException(resource);
        }
        String s = sb.toString();
        return (dot
                ? new LDouble(Double.parseDouble(s))
                : new LNumber(Long.parseLong(s)));
    }

    /**
     * Read a string constant.
     * 
     * @return the string object
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxEofException in case of an unexpected EOF
     */
    private LValue readString() throws IOException, SyntaxEofException {

        StringBuilder sb = new StringBuilder();
        for (c = reader.read(); c != '"'; c = reader.read()) {
            if (c <= 0) {
                throw new SyntaxEofException(resource);
            } else if (c == escape) {
                c = reader.read();
                if (c <= 0) {
                    throw new SyntaxEofException(resource);
                }
                switch (c) {
                    case 'n':
                        c = '\n';
                        break;
                    case 'r':
                        c = '\r';
                        break;
                    case 'f':
                        c = '\f';
                        break;
                    case 't':
                        c = '\t';
                        break;
                    case 'b':
                        c = Character.MIN_CODE_POINT;
                        break;
                    case 'e':
                        c = Character.MAX_CODE_POINT;
                        break;
                    case 'u':
                        c = readUnicode();
                        break;
                    default:
                        // silently ignore the escape character
                }
            }
            sb.append((char) c);
        }
        c = reader.read();
        return new LString(sb.toString());
    }

    /**
     * Read a symbol.
     * 
     * @return the symbol object
     * 
     * @throws IOException in case of an I/O error
     */
    private LValue readSymbol() throws IOException {

        StringBuilder sb = new StringBuilder();
        do {
            sb.append((char) c);
            c = reader.read();
        } while (c >= 0 && isNotSpecial(c));
        String s = sb.toString();
        if ("nil".equals(s)) {
            return new LList();
        }
        return LSymbol.get(s);
    }

    /**
     * Read four hex digit and convert it to a char.
     * 
     * @return the char
     * 
     * @throws IOException in case of an error
     */
    private int readUnicode() throws IOException {

        int cc = readHex() << 24;
        cc |= readHex() << 16;
        cc |= readHex() << 8;
        cc |= readHex();
        return (char) cc;
    }

    /**
     * Setter for escape.
     * 
     * @param escape the escape to set
     */
    public void setEscape(char escape) {

        this.escape = escape;
    }
}
