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
import java.io.Reader;

import org.extex.exindex.lisp.exception.SyntaxException;
import org.extex.exindex.lisp.type.LList;
import org.extex.exindex.lisp.type.LValue;
import org.extex.exindex.lisp.type.LNumber;
import org.extex.exindex.lisp.type.LString;
import org.extex.exindex.lisp.type.LSymbol;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LParser {

    /**
     * The field <tt>CLOSE</tt> contains the ...
     */
    private static final LValue CLOSE = new LNumber(0);

    /**
     * The field <tt>reader</tt> contains the ...
     */
    private Reader reader;

    /**
     * Creates a new object.
     * 
     * @param reader
     */
    public LParser(Reader reader) {

        super();
        this.reader = reader;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param c
     * @return
     */
    private boolean isSpecial(int c) {

        return Character.isWhitespace(c) || c == '(' || c == ')' || c == ';'
                || c == '"' || c == '\'';
    }

    /**
     * Parse an LNode.
     * 
     * @return the LNode or <code>null</code> at end of file
     * 
     * @throws IOException in case of an I/O error
     */
    public LValue read() throws IOException {

        return read(false);
    }

    /**
     * Parse an LNode.
     * 
     * @return the LNode or <code>null</code> at end of file
     * 
     * @throws IOException in case of an I/O error
     */
    public LValue read(boolean accept) throws IOException {

        if (reader == null) {
            return null;
        }
        for (int c = reader.read(); c >= 0;) {

            if (Character.isWhitespace(c)) {
                // ignore
                c = reader.read();
            } else if (c == ';') {
                for (c = reader.read(); c >= 0 && c != '\n' && c != '\r'; c =
                        reader.read()) {
                    // skip to eol
                }
                c = reader.read();
            } else if (Character.isDigit(c)) {
                long n = c - '0';
                for (c = reader.read(); Character.isDigit(c); c = reader.read()) {
                    n = n * 10 - '0' + c;
                }
                return new LNumber(n);
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
                        throw new SyntaxException();
                    } else if (c == '\\') {
                        c = reader.read();
                        if (c <= 0) {
                            throw new SyntaxException();
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
                            case 'b':
                                c = '\b';
                                break;
                        }
                    }
                    sb.append((char) c);
                }
                return new LString(sb.toString());
            } else if (c == '(') {
                LList list = new LList();
                for (LValue n = read(true); n != CLOSE; n = read(true)) {
                    if (n == null) {
                        throw new SyntaxException();
                    }
                    list.add(n);
                }
                return list;
            } else if (c == ')') {
                if (accept) {
                    return CLOSE;
                }
                throw new SyntaxException();
            } else if (c == '\'') {
                LValue n = read(false);
                if (n == null) {
                    // TODO gene: read unimplemented
                    throw new RuntimeException("unimplemented");
                }
                LList list = new LList();
                list.add(n);
                return list;
            } else {
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append((char) c);
                    c = reader.read();
                } while (c >= 0 && Character.isLetterOrDigit(c));
                String s = sb.toString();
                if ("nil".equals(s)) {
                    return new LList();
                }
                return LSymbol.get(s);
            }
        }

        return null;
    }
}
