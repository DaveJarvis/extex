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

package org.extex.ocpware.compiler.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.extex.ocpware.compiler.exception.SyntaxException;

/**
 * This class provides a push-back input stream which can parse certain basic
 * entities.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ParserStream extends PushbackInputStream {

    /**
     * The field <tt>line</tt> contains the characters encountered on the
     * current line so far.
     */
    private StringBuffer line = new StringBuffer();

    /**
     * The field <tt>lineno</tt> contains the number of the current line.
     */
    private int lineno = 1;

    /**
     * Creates a new object.
     * 
     * @param in the stream to acquire characters from
     */
    public ParserStream(InputStream in) {

        super(in, 256);
    }

    /**
     * Create an exception containing the context.
     * 
     * @param c the character read
     * 
     * @return the exception
     */
    public SyntaxException error(int c) {

        return new SyntaxException(c, line, lineno);
    }

    /**
     * Skip to the next non-white-space character and compare it to a given one.
     * If the comparison fails then an exception is raised.
     * 
     * @param ex the expected character
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public void expect(char ex) throws IOException, SyntaxException {

        int c = skipSpace();
        if (c < 0) {
            throw new SyntaxException(line, lineno);
        }
        if (c != ex) {
            throw new SyntaxException(c, ex, line, lineno);
        }
    }

    /**
     * Parse an id.
     * 
     * @param ex the expected characters
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public void expect(String ex) throws IOException, SyntaxException {

        String t = parseId();
        if (t == null) {
            throw new SyntaxException(line, lineno);
        } else if (!t.equals(ex)) {
            throw new IOException("unexpected " + t + " instead of " + ex);
        }
        int c = skipSpace();
        if (c < 0) {
            throw new SyntaxException(line, lineno);
        }
        unread(c);
    }

    /**
     * Getter for line.
     * 
     * @return the line
     */
    public StringBuffer getLine() {

        return line;
    }

    /**
     * Getter for lineno.
     * 
     * @return the lineno
     */
    public int getLineno() {

        return lineno;
    }

    /**
     * Parse an id. An id is a non empty sequence of characters made up of
     * letters (a-z,A-Z), digits (0-9), the underscore character(_), and the
     * colon (:) where the first character is not a digit or a colon.
     * 
     * @return the identifier read
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     */
    public String parseId() throws IOException, SyntaxException {

        StringBuffer sb = new StringBuffer();

        int c = skipSpace();
        if ((c >= 'a' && c <= 'z') //
                || (c >= 'A' && c <= 'Z') //
                || c == '_') {
            sb.append((char) c);
        } else if (c < 0) {
            return null;
        } else {
            throw new SyntaxException(c, line, lineno);
        }

        for (;;) {
            c = read();
            if (c < 0) {
                break;
            } else if ((c >= '0' && c <= '9') //
                    || (c >= 'a' && c <= 'z') //
                    || (c >= 'A' && c <= 'Z') //
                    || c == '_' || c == ':') {
                sb.append((char) c);
            } else {
                unread(c);
                break;
            }
        }
        return sb.toString();
    }

    /**
     * Parse a number.
     * 
     * @param cc the character already scanned
     * 
     * @return the number read
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of an parser error
     */
    public int parseNumber(int cc) throws IOException, SyntaxException {

        int n = 0;
        int c = cc;

        if (c == '@') {
            c = read();
            if (c == '\'') {
                c = read();
                if (c < '0' || c > '7') {
                    throw new SyntaxException(c, line, lineno);
                }
                n = c - '0';
                for (c = read(); c >= '0' && c <= '7'; c = read()) {
                    n = n * 8 + c - '0';
                }
                unread(c);

            } else if (c == '"') {
                c = read();

                if (c >= '0' && c <= '9') {
                    n = c - '0';
                } else if (c >= 'a' && c <= 'f') {
                    n = c - 'a' + 10;
                } else if (c >= 'A' && c <= 'F') {
                    n = c - 'A' + 10;
                } else {
                    throw new SyntaxException(c, line, lineno);
                }
                for (;;) {
                    c = read();
                    if ((c >= '0' && c <= '9')) {
                        n = n * 16 + c - '0';
                    } else if (c >= 'a' && c <= 'f') {
                        n = n * 16 + c - 'a' + 10;
                    } else if (c >= 'A' && c <= 'F') {
                        n = n * 16 + c - 'A' + 10;
                    } else {
                        break;
                    }
                }
                unread(c);

            } else {
                throw new SyntaxException(c, line, lineno);
            }

        } else if (c == '`') {
            n = read();
            if (n < 0) {
                throw new SyntaxException(c, line, lineno);
            }
            c = read();
            if (c != '\'') {
                throw new SyntaxException(c, line, lineno);
            }

        } else if (c >= '0' && c <= '9') {

            n = c - '0';
            for (c = read(); c >= '0' && c <= '9'; c = read()) {
                n = n * 10 + c - '0';
            }
            unread(c);

        } else {
            throw new SyntaxException(c, line, lineno);
        }

        return n;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.PushbackInputStream#read()
     */
    @Override
    public int read() throws IOException {

        int c = super.read();
        if (c == '\n') {
            lineno++;
            line.delete(0, line.length());
        } else if (c >= 0) {
            line.append((char) c);
        }
        return c;
    }

    /**
     * Skip whitespace and comments and advance to the next real character. This
     * character is returned. If end of file is reached -1 is returned.
     * 
     * @return the character read or -1 upon EOF
     * 
     * @throws IOException in case of an I/O error
     */
    public int skipSpace() throws IOException {

        int c;
        do {
            c = read();
            if (c < 0) {
                return c;
            } else if (c == '%') {
                do {
                    c = read();
                    if (c < 0) {
                        return c;
                    }
                } while (c != '\n' && c != '\r');
            }
        } while (Character.isWhitespace(c));

        return c;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return Integer.toString(lineno) + ":" + line;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.PushbackInputStream#unread(int)
     */
    @Override
    public void unread(int b) throws IOException {

        int len = line.length();
        if (b >= 0 && len > 0) {
            line.delete(len - 1, len);
            super.unread(b);
        }
    }

}
