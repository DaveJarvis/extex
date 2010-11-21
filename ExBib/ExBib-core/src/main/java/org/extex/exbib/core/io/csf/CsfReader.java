/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.csf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class is a reader for a csf file.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CsfReader {

    /**
     * This enumeration names the modes for casing sections.
     */
    private enum Mode {
        /**
         * The field <tt>LOWER</tt> contains the mode for \lowercase.
         */
        LOWER,
        /**
         * The field <tt>UPPER</tt> contains the mode for \ uppercase.
         */
        UPPER,
        /**
         * The field <tt>LOW_UP</tt> contains the mode for \lowupcase.
         */
        LOW_UP
    };

    /**
     * Creates a new object.
     */
    public CsfReader() {

        super();
    }

    /**
     * Expect a certain character and complain if it is not found. Newline
     * characters are skipped.
     * 
     * @param c the expected character
     * @param reader the reader to acquire characters from
     * 
     * @throws IOException in case of an I/O error
     * @throws CsfException in case of an error
     */
    private void expect(char c, Reader reader) throws IOException, CsfException {

        for (int cc = readChar(reader); cc >= 0; cc = readChar(reader)) {
            if (cc == c) {
                return;
            } else if (cc != '\n') {
                throw new CsfException(LocalizerFactory
                    .getLocalizer(getClass()).format("unexpected",
                        Character.toString(c), Character.toString((char) cc)));
            }
        }
        throw new CsfException(LocalizerFactory.getLocalizer(getClass())
            .format("unexpected.eof"));
    }

    /**
     * Parse an \lowercase, \lowupcase, or \ uppercase section.
     * 
     * @param csf the transport object
     * @param reader the reader to acquire characters from
     * @param mode the mode of operation
     * 
     * @throws IOException in case of an I/O error
     * @throws CsfException in case of an error
     */
    private void parse(CsfSorter csf, Reader reader, Mode mode)
            throws IOException,
                CsfException {

        expect('{', reader);

        for (int c = readChar(reader); c != '}'; c = readChar(reader)) {
            if (c < 0) {
                throw new CsfException(LocalizerFactory
                    .getLocalizer(getClass()).format("unexpected.eof"));

            } else if (c == '\n') {
                continue;

            } else {
                int d = readChar(reader);
                if (d < 0) {
                    throw new CsfException(LocalizerFactory.getLocalizer(
                        getClass()).format("unexpected.eof"));
                } else if (d == '\n') {
                    throw new CsfException(LocalizerFactory.getLocalizer(
                        getClass()).format("unexpected.nl"));
                }

                switch (mode) {
                    case LOWER:
                        csf.setLower((char) c, (char) d);
                        break;
                    case LOW_UP:
                        csf.setLower((char) d, (char) c);
                        csf.setUpper((char) c, (char) d);
                        break;
                    case UPPER:
                        csf.setUpper((char) c, (char) d);
                        break;
                    default:
                        // this can not happen
                }
                c = readChar(reader);
                if (c != '\n') {
                    if (c == '}') {
                        return;
                    } else if (c < 0) {
                        throw new CsfException(LocalizerFactory.getLocalizer(
                            getClass()).format("unexpected.eof"));
                    }
                    throw new CsfException(LocalizerFactory.getLocalizer(
                        getClass()).format("nl.missing",
                        Character.toString((char) c)));
                }
            }
        }
    }

    /**
     * Parse an \order section.
     * 
     * @param csf the transport object
     * @param reader the reader to acquire characters from
     * 
     * @throws IOException in case of an I/O error
     * @throws CsfException in case of an error
     */
    private void parseOrder(CsfSorter csf, Reader reader)
            throws IOException,
                CsfException {

        int lastC = -1;
        int ord = 1;
        expect('{', reader);

        for (int c = readChar(reader); c != '}'; c = readChar(reader)) {
            if (c < 0) {
                throw new CsfException(LocalizerFactory
                    .getLocalizer(getClass()).format("unexpected.eof"));

            } else if (c == '\n') {
                ord++;
                lastC = -1;

            } else if (lastC < 0) {
                csf.setOrder((char) c, ord);
                lastC = c;

            } else if (c == '-') {
                c = readChar(reader);
                if (c < 0) {
                    throw new CsfException(LocalizerFactory.getLocalizer(
                        getClass()).format("nl.missing"));
                }
                for (; lastC < c; lastC++) {
                    csf.setOrder((char) lastC, ord++);
                }
                lastC = -1;

            } else if (c == '_') {
                c = readChar(reader);
                if (c < 0) {
                    throw new CsfException(LocalizerFactory.getLocalizer(
                        getClass()).format("unexpected.eof"));
                }
                for (; lastC < c; lastC++) {
                    csf.setOrder((char) lastC, ord);
                }
                lastC = -1;

            } else {
                csf.setOrder((char) c, ord);
                lastC = c;
            }
        }
    }

    /**
     * Read the configuration from a csf.
     * 
     * @param reader the reader to acquire characters from
     * 
     * @return the sorter containing the configuration read
     * 
     * @throws IOException in case of an I/O error
     * @throws CsfException in case of a csf error
     */
    public CsfSorter read(Reader reader) throws IOException, CsfException {

        return read(reader, new CsfSorter());
    }

    /**
     * Read the configuration from a csf.
     * 
     * @param reader the reader to acquire characters from
     * @param csf the CSF to store the read information in
     * 
     * @return the sorter containing the configuration read
     * 
     * @throws IOException in case of an I/O error
     * @throws CsfException in case of a csf error
     */
    public CsfSorter read(Reader reader, CsfSorter csf)
            throws IOException,
                CsfException {

        Reader r = new BufferedReader(reader);

        for (String s = readToken(r); s != null; s = readToken(r)) {
            if ("\\order".equals(s)) {
                parseOrder(csf, r);
            } else if ("\\uppercase".equals(s)) {
                parse(csf, r, Mode.UPPER);
            } else if ("\\lowercase".equals(s)) {
                parse(csf, r, Mode.LOWER);
            } else if ("\\lowupcase".equals(s)) {
                parse(csf, r, Mode.LOW_UP);
            } else {
                throw new CsfException(LocalizerFactory
                    .getLocalizer(getClass()).format("unknown.section", s));
            }
        }

        return csf;
    }

    /**
     * Read a single character.
     * 
     * @param reader the reader to acquire characters from
     * 
     * @return the character read or -1 on EOF
     * 
     * @throws IOException in case of an I/O error
     * @throws CsfException in case of a syntax error
     */
    private int readChar(Reader reader) throws IOException, CsfException {

        for (int c = reader.read(); c >= 0; c = reader.read()) {
            if (c == '%') {
                do {
                    c = reader.read();
                } while (c >= 0 && c != '\n');
                return '\n';
            } else if (c == '^') {
                c = reader.read();
                if (Character.isSpaceChar(c)) {
                    return '^';
                } else if (c != '^') {
                    throw new CsfException(LocalizerFactory.getLocalizer(
                        getClass()).format("illegal.character.escape"));
                }
                return readHexChar(reader) << 4 | readHexChar(reader);
            } else if (!Character.isSpaceChar(c) || c == '\n') {
                return c;
            }
        }

        return -1;
    }

    /**
     * Read a single hexadecimal character and return its integer value.
     * 
     * @param reader the reader to acquire characters from
     * 
     * @return the value, i.e. 0 for '0' to 9 for '9'; 10 for 'a' or 'A' to 15
     *         for 'f' or 'F'
     * 
     * @throws IOException in case of an I/O error
     * @throws CsfException in case of a syntax error
     */
    private int readHexChar(Reader reader) throws IOException, CsfException {

        int c = reader.read();
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        } else if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        }
        throw new CsfException(LocalizerFactory.getLocalizer(getClass())
            .format("illegal.character.escape"));
    }

    /**
     * Read a token started by backslash and consisting of letters.
     * 
     * @param reader the reader to get characters from
     * 
     * @return the token found or <code>null</code> ON eof
     * 
     * @throws IOException in case of an I/O error
     * @throws CsfException in case of an error
     */
    private String readToken(Reader reader) throws IOException, CsfException {

        for (int c = readChar(reader); c >= 0; c = readChar(reader)) {
            if (c == '\\') {
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append((char) c);
                    reader.mark(1);
                    c = reader.read();
                } while (c >= 0 && Character.isLetter(c));
                reader.reset();
                return sb.toString();

            } else if (c != '\n') {
                throw new CsfException(LocalizerFactory
                    .getLocalizer(getClass()).format("section.missing",
                        Character.toString((char) c)));
            }
        }

        return null;
    }

}
