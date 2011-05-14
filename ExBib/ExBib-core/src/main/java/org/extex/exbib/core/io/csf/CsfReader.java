/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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
 * This class is a reader for a CSF &ndash; codepage and sort definition file.
 * <p>
 * The following description of the file format is taken from the
 * B<small>IB</small>TeX8 implementation by Niel Kempson and Alejandro
 * Aguilar-Sierra.
 * </p>
 * <h2>FILE FORMAT</h2>
 * <p>
 * The codepage and sorting order (CS) file defines how B<small>ib</small>T<span
 * style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X will treat an 8-bit character set, specifically which characters
 * are to be treated as letters, the upper/lower case relationships between
 * characters, and the sorting order of characters.
 * </p>
 * <p>
 * The CS file may contain a number of sections, each presented in the form of a
 * T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X macro:
 * </p>
 * 
 * <pre>
 *    \section-name{
 *        &lang;section definitions&rang;
 *    }
 * </pre>
 * <p>
 * Four sections are currently supported: \lowupcase, \lowercase,
 * &#x5c;uppercase and \order. The syntax of the four supported sections is
 * summarized below.
 * </p>
 * <p>
 * 8-bit characters may be entered naturally, but to avoid problems with
 * character set translation or corruption, they can also be entered using the
 * T<span style= *
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * * >e</span>X-style portable notation for character codes, i.e. ^^XX, where XX
 * is the hexadecimal value of the character code.
 * </p>
 * <p>
 * Reading of the sections ends when the first '}' character is reached, so '}'
 * can't be included in a section. You can't use ^^7d either.
 * </p>
 * <p>
 * The percent sign ('%') is used to introduce a trailing comment - it and all
 * remaining characters on a line are ignored. ^^25 has the same effect.
 * </p>
 * <h3>\lowupcase section</h3>
 * <p>
 * The \lowupcase section of the CS file is used to define the lower /upper and
 * upper/lower case relationship of pairs of specified characters. It is only
 * used if the relationship is symmetrical &ndash; use \lowercase or
 * &#x5c;upcase if it isn't.
 * </p>
 * <p>
 * The syntax of the \lowupcase section is:
 * </p>
 * 
 * <pre>
 *    \lowupcase{
 *        &lang;LC<sub>1</sub>&rang; &lang;UC<sub>1</sub>&rang;   % Comment begins with a percent sign
 *        &lang;LC<sub>2</sub>&rang; &lang;UC<sub>2</sub>&rang;
 *        ...
 *        &lang;LC<sub>n</sub>&rang; &lang;UC<sub>n</sub>&rang;
 *    }
 * </pre>
 * <p>
 * Each &lang;LC<sub>n</sub>&rang; &lang;UC<sub>n</sub>&rang; pair of characters
 * defines that the upper case equivalent of &lang;LC<sub>n</sub>&rang; is
 * &lang;UC<sub>n</sub>&rang; <b>and</b> the lower case equivalent of
 * &lang;UC<sub>n</sub>&rang; is &lang;LC<sub>n</sub>&rang;.
 * </p>
 * <p>
 * 
 * You cannot redefine the lower or upper case equivalent of an ASCII character
 * (code &lt; 128), so all instances of &lang;LC<sub>n</sub>&rang; and
 * &lang;UC<sub>n</sub>&rang; (i.e. both sides of the relationship) must have
 * codes &gt; 127.
 * </p>
 * <h3>\lowercase section</h3>
 * <p>
 * The \lowercase section of the CS file is used to define the lower case
 * equivalent of specified characters. It should normally only be used if the
 * relationship isn't symmetrical &ndash; use \lowupcase if it is.
 * </p>
 * <p>
 * The syntax of the \lowercase section is:
 * </p>
 * 
 * <pre>
 *    \lowercase{
 *        &lang;UC<sub>1</sub>&rang; &lang;LC<sub>1</sub>&rang;   % Comment begins with a percent sign
 *        &lang;UC<sub>2</sub>&rang; &lang;LC<sub>2</sub>&rang;
 *        ...
 *        &lang;UC<sub>n</sub>&rang; &lang;LC<sub>n</sub>&rang;
 *    }
 * </pre>
 * <p>
 * Each &lang;LC<sub>n</sub>&rang; &lang;UC<sub>n</sub>&rang; pair of characters
 * defines that the lower case equivalent of &lang;UC<sub>n</sub>&rang; is
 * &lang;LC<sub>n</sub>&rang;.
 * </p>
 * <p>
 * You cannot redefine the lower case equivalent of an ASCII character (code
 * &lt; 128), so all instances of &lang;UC<sub>n</sub>&rang; (i.e. the left hand
 * side of the relationship) must have codes &gt; 127.
 * </p>
 * <h3>&#x5c;uppercase section</h3>
 * <p>
 * The &#x5c;uppercase section of the CS file is used to define the upper case
 * equivalent of specified characters. It should normally only be used if the
 * relationship isn't symmetrical &ndash; use \lowupcase if it is.
 * </p>
 * <p>
 * The syntax of the &#x5c;uppercase section is:
 * </p>
 * <re> &#x5c;uppercase{ &lang;LC<sub>1</sub>&rang; &lang;UC<sub>1</sub>&rang; %
 * Comment begins with a percent sign &lang;LC<sub>2</sub>&rang;
 * &lang;UC<sub>2</sub>&rang; ... &lang;LC<sub>n</sub>&rang;
 * &lang;UC<sub>n</sub>&rang; } </pre>
 * <p>
 * Each &lang;LC<sub>n</sub>&rang; &lang;UC<sub>n</sub>&rang; pair of characters
 * defines that the upper case case equivalent of &lang;LC<sub>n</sub>&rang; is
 * &lang;UC<sub>n</sub>&rang;.
 * </p>
 * <p>
 * You cannot redefine the upper case equivalent of an ASCII character (code
 * &lt; 128), so all instances of &lang;LC<sub>n</sub>&rang; (i.e. the left hand
 * side of the relationship) must have codes &gt; 127.
 * </p>
 * < <h3>\order section</h3>
 * <p>
 * The \order section of the CS file is used to define the order in which
 * characters are sorted.
 * </p>
 * <p>
 * The syntax of the \order section is:
 * </p>
 * 
 * <pre>
 *    \order{
 *        &lang;char<sub>1</sub>&rang;                % Comment begins with a percent sign
 *        &lang;char<sub>2</sub>&rang; &lang;char<sub>3</sub>&rang;       % whitespace between the chars
 *        &lang;char<sub>4</sub>&rang; - &lang;char<sub>5</sub>&rang;     % a hyphen between the chars
 *        &lang;char<sub>4</sub>&rang; _ &lang;char<sub>5</sub>&rang;     % an underscore between the chars
 *        ...
 *        &lang;char<sub>n</sub>&rang;
 *    }
 * </pre>
 * <p>
 * All characters on the same line are given the same sorting weight.
 * </p>
 * <p>
 * The construct &lang;char<sub>1</sub>&rang; &lang;underscore&rang;
 * &lang;char<sub>2</sub>&rang; is used to denote that all characters in the
 * range &lang;char<sub>1</sub>&rang; to &lang;char<sub>2</sub>&rang; should be
 * given the same sorting weight. For example, "A _ Z" would cause all ASCII
 * upper case alphabetical characters to have the same sorting weight and would
 * be equivalent to placing all 26 characters on the same line.
 * </p>
 * <p>
 * The construct &lang;char<sub>1</sub>&rang; &lang;hyphen&rang;
 * &lang;char<sub>2</sub>&rang; is used to denote that all characters in the
 * range &lang;char<sub>1</sub>&rang; to &lang;char<sub>2</sub>&rang; should be
 * given an ascending set of sorting weights, starting with
 * &lang;char<sub>1</sub>&rang; and ending with &lang;char<sub>2</sub>&rang;.
 * For example, "A - Z" would cause all upper case ASCII alphabetical characters
 * to be sorted in ascending order and would be equivalent to placing 'A' on the
 * first line, 'B' on the second, through to 'Z' on the 26<sup>th</sup> line.
 * </p>
 * <p>
 * The characters at the beginning of the order section are given a lower
 * sorting weight than characters occurring later. When sorting alphabetically,
 * characters with the lowest weight come first.
 * 
 * All characters not in the \order section (including ASCII characters) are
 * given the same very high sorting weight to ensure that they come last when
 * sorting alphabetically.
 * </p>
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
        LOWER {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.csf.CsfReader.Mode#encounter(org.extex.exbib.core.io.csf.CsfSorter,
             *      char, char)
             */
            @Override
            void encounter(CsfSorter csf, char c, char d) {

                csf.setLower(c, d);
            }
        },
        /**
         * The field <tt>UPPER</tt> contains the mode for \ uppercase.
         */
        UPPER {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.csf.CsfReader.Mode#encounter(org.extex.exbib.core.io.csf.CsfSorter,
             *      char, char)
             */
            @Override
            void encounter(CsfSorter csf, char c, char d) {

                csf.setUpper(c, d);
            }
        },
        /**
         * The field <tt>LOW_UP</tt> contains the mode for \lowupcase.
         */
        LOW_UP {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.io.csf.CsfReader.Mode#encounter(org.extex.exbib.core.io.csf.CsfSorter,
             *      char, char)
             */
            @Override
            void encounter(CsfSorter csf, char c, char d) {

                csf.setLower(d, c);
                csf.setUpper(c, d);
            }
        };

        /**
         * Encounter a character mapping.
         * 
         * @param csf the sorter
         * @param c the first character
         * @param d the second character
         */
        abstract void encounter(CsfSorter csf, char c, char d);
    };

    /**
     * Creates a new object.
     */
    public CsfReader() {

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

                mode.encounter(csf, (char) c, (char) d);

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
            if (c == '^') {
                c = reader.read();
                if (Character.isSpaceChar(c)) {
                    return '^';
                } else if (c != '^') {
                    throw new CsfException(LocalizerFactory.getLocalizer(
                        getClass()).format("illegal.character.escape"));
                }
                c = readHexChar(reader) << 4 | readHexChar(reader);
            }
            if (c == '\n' || (c != '%' && !Character.isSpaceChar(c))) {
                return c;
            } else if (c == '%') {
                do {
                    c = reader.read();
                } while (c >= 0 && c != '\n');
                return '\n';
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
