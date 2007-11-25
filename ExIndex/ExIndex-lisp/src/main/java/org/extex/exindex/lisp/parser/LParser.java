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

package org.extex.exindex.lisp.parser;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.extex.exindex.lisp.exception.SyntaxEofException;
import org.extex.exindex.lisp.exception.SyntaxException;
import org.extex.exindex.lisp.type.value.LDouble;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LNumber;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This is a parser foe a Lisp-like language.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LParser {

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
    private String resource;

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

        super();
        this.resource = resource;
        this.reader = new LineNumberReader(reader);
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
     * Check whether the given character is not one of the special characters
     * ending a symbol.
     * 
     * @param cc the character to check
     * 
     * @return <code>true</code> iff the character is special
     */
    private boolean isNotSpecial(int cc) {

        return !Character.isWhitespace(cc) && cc != '(' && cc != ')'
                && cc != ';' && cc != '"' && cc != '\'';
    }

    /**
     * Parse an LValue.
     * 
     * @return the LValue or <code>null</code> at end of file
     * 
     * @throws IOException in case of an I/O error
     */
    public LValue read() throws IOException {

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

        if (reader == null) {
            return null;
        }
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
            } else if (Character.isDigit(c) || c == '.') {
                boolean dot = true;
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(c);
                    c = reader.read();
                    if (c == '.' && !dot) {
                        sb.append(c);
                        c = reader.read();
                        dot = true;
                    }
                } while (c >= 0 && Character.isDigit(c));

                return (dot
                        ? new LDouble(Double.parseDouble(sb.toString()))
                        : new LNumber(Long.parseLong(sb.toString())));

            } else if (c == '-') {
                long n = 0;
                for (c = reader.read(); Character.isDigit(c); c = reader.read()) {
                    n = n * 10 - '0' + c;
                }
                return new LNumber(-n);
            } else if (c == '"') {
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
                                c = '\b';
                                break;

                        }
                    }
                    sb.append((char) c);
                }
                c = -1;
                return new LString(sb.toString());
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
                LValue n = read(false);
                if (n == null) {
                    throw new SyntaxEofException(resource);
                }
                LList list = new LList();
                list.add(LSymbol.get("quote"));
                list.add(n);
                return list;
            } else {
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
        }

        return null;
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
