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
 * &#x5c;uppercase and {@code \order}. The syntax of the four supported sections is
 * summarized below.
 * </p>
 * <p>
 * 8-bit characters may be entered naturally, but to avoid problems with
 * character set translation or corruption, they can also be entered using the
 * TeX-style portable notation for character codes, i.e. ^^XX, where XX
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
 * <p>\lowupcase section</p>
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
 *        &lang;LC_1&rang; &lang;UC_1&rang;   % Comment begins with a percent sign
 *        &lang;LC_2&rang; &lang;UC_2&rang;
 *        ...
 *        &lang;LC_n&rang; &lang;UC_n&rang;
 *    }
 * </pre>
 * <p>
 * Each &lang;LC_n&rang; &lang;UC_n&rang; pair of characters
 * defines that the upper case equivalent of &lang;LC_n&rang; is
 * &lang;UC_n&rang; <b>and</b> the lower case equivalent of
 * &lang;UC_n&rang; is &lang;LC_n&rang;.
 * </p>
 * <p>
 * 
 * You cannot redefine the lower or upper case equivalent of an ASCII character
 * (code &lt; 128), so all instances of &lang;LC_n&rang; and
 * &lang;UC_n&rang; (i.e. both sides of the relationship) must have
 * codes &gt; 127.
 * </p>
 * <p>\lowercase section</p>
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
 *        &lang;UC_1&rang; &lang;LC_1&rang;   % Comment begins with a percent sign
 *        &lang;UC_2&rang; &lang;LC_2&rang;
 *        ...
 *        &lang;UC_n&rang; &lang;LC_n&rang;
 *    }
 * </pre>
 * <p>
 * Each &lang;LC_n&rang; &lang;UC_n&rang; pair of characters
 * defines that the lower case equivalent of &lang;UC_n&rang; is
 * &lang;LC_n&rang;.
 * </p>
 * <p>
 * You cannot redefine the lower case equivalent of an ASCII character (code
 * &lt; 128), so all instances of &lang;UC_n&rang; (i.e. the left hand
 * side of the relationship) must have codes &gt; 127.
 * </p>
 * <p>&#x5c;uppercase section</p>
 * <p>
 * The &#x5c;uppercase section of the CS file is used to define the upper case
 * equivalent of specified characters. It should normally only be used if the
 * relationship isn't symmetrical &ndash; use \lowupcase if it is.
 * </p>
 * <p>
 * The syntax of the &#x5c;uppercase section is:
 * </p>
 * <pre> &#x5c;uppercase{ &lang;LC_1&rang; &lang;UC_1&rang; %
 * Comment begins with a percent sign &lang;LC_2&rang;
 * &lang;UC_2&rang; ... &lang;LC_n&rang;
 * &lang;UC_n&rang; } </pre>
 * <p>
 * Each &lang;LC_n&rang; &lang;UC_n&rang; pair of characters
 * defines that the upper case case equivalent of &lang;LC_n&rang; is
 * &lang;UC_n&rang;.
 * </p>
 * <p>
 * You cannot redefine the upper case equivalent of an ASCII character (code
 * &lt; 128), so all instances of &lang;LC_n&rang; (i.e. the left hand
 * side of the relationship) must have codes &gt; 127.
 * </p>
 * <p>
 * The {@code \order} section
 * </p>
 * <p>
 * The {@code \order} section of the CS file is used to define the order in which
 * characters are sorted.
 * </p>
 * <p>
 * The syntax of the {@code \order} section is:
 * </p>
 * 
 * <pre>
 *    \order{
 *        &lang;char_1&rang;                % Comment begins with a percent sign
 *        &lang;char_2&rang; &lang;char_3&rang;       % whitespace between the chars
 *        &lang;char_4&rang; - &lang;char_5&rang;     % a hyphen between the chars
 *        &lang;char_4&rang; _ &lang;char_5&rang;     % an underscore between the chars
 *        ...
 *        &lang;char_n&rang;
 *    }
 * </pre>
 * <p>
 * All characters on the same line are given the same sorting weight.
 * </p>
 * <p>
 * The construct &lang;char_1&rang; &lang;underscore&rang;
 * &lang;char_2&rang; is used to denote that all characters in the
 * range &lang;char_1&rang; to &lang;char_2&rang; should be
 * given the same sorting weight. For example, "A _ Z" would cause all ASCII
 * upper case alphabetical characters to have the same sorting weight and would
 * be equivalent to placing all 26 characters on the same line.
 * </p>
 * <p>
 * The construct &lang;char_1&rang; &lang;hyphen&rang;
 * &lang;char_2&rang; is used to denote that all characters in the
 * range &lang;char_1&rang; to &lang;char_2&rang; should be
 * given an ascending set of sorting weights, starting with
 * &lang;char_1&rang; and ending with &lang;char_2&rang;.
 * For example, "A - Z" would cause all upper case ASCII alphabetical characters
 * to be sorted in ascending order and would be equivalent to placing 'A' on the
 * first line, 'B' on the second, through to 'Z' on the 26<sup>th</sup> line.
 * </p>
 * <p>
 * The characters at the beginning of the order section are given a lower
 * sorting weight than characters occurring later. When sorting alphabetically,
 * characters with the lowest weight come first.
 * 
 * All characters not in the {@code \order} section (including ASCII characters) are
 * given the same very high sorting weight to ensure that they come last when
 * sorting alphabetically.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class CsfReader {

    /**
     * This enumeration names the modes for casing sections.
     */
    private enum Parser {
        /**
         * The field {@code LOWER} contains the mode for \lowercase.
         */
        LOWER {

            /**
        *      char, char)
             */
            @Override
            void encounter(CsfSorter csf, char c, char d) {

                csf.setLower(c, d);
            }
        },
        /**
         * The field {@code UPPER} contains the mode for &#x5c;uppercase.
         */
        UPPER {

            /**
        *      char, char)
             */
            @Override
            void encounter(CsfSorter csf, char c, char d) {

                csf.setUpper(c, d);
            }
        },
        /**
         * The field {@code LOW_UP} contains the mode for \lowupcase.
         */
        LOW_UP {

            /**
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

        /**
         * Parse an &#x5c;lowercase, &#x5c;lowupcase, or &#x5c;uppercase
         * section.
         * 
         * @param csf the transport object
         * @param reader the reader to acquire characters from
         * 
         * @throws IOException in case of an I/O error
         * @throws CsfException in case of an error
         */
        private void parse(CsfSorter csf, Reader reader)
                throws IOException,
                    CsfException {

            expect('{', reader);

            for (int c = readMandatoryChar(reader); c != '}'; c =
                    readMandatoryChar(reader)) {
                if (c != '\n') {
                    int d = readMandatoryChar(reader);
                    if (d == '\n') {
                        throw makeException("unexpected.nl");
                    }

                    encounter(csf, (char) c, (char) d);

                    c = readMandatoryChar(reader);
                    if (c == '}') {
                        return;
                    } else if (c != '\n') {
                        throw makeException("nl.missing",
                            Character.toString((char) c));
                    }
                }
            }
        }

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
    private static void expect(char c, Reader reader)
            throws IOException,
                CsfException {

        for (int cc = readMandatoryChar(reader); cc != c; cc =
                readMandatoryChar(reader)) {
            if (cc != '\n') {
                throw makeException("unexpected", Character.toString(c),
                    Character.toString((char) cc));
            }
        }
    }

    /**
     * Create a localized exception.
     * 
     * @param key the format key
     * @param args the optional arguments
     * 
     * @return the exception
     */
    private static CsfException makeException(String key, String... args) {

        return new CsfException(LocalizerFactory.getLocalizer(CsfReader.class)
            .format(key, args));
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
    private static int readChar(Reader reader) throws IOException, CsfException {

        for (int c = reader.read(); c >= 0; c = reader.read()) {
            if (c == '^') {
                c = reader.read();
                if (Character.isSpaceChar(c)) {
                    return '^';
                } else if (c != '^') {
                    throw makeException("illegal.character.escape");
                }
                c = readHexChar(reader) << 4 | readHexChar(reader);
            }
            if (c == '%') {
                do {
                    c = reader.read();
                } while (c >= 0 && c != '\n');
                return '\n';
            }
            if (c == '\n' || !Character.isSpaceChar(c)) {
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
    private static int readHexChar(Reader reader)
            throws IOException,
                CsfException {

        int c = Character.digit(reader.read(), 16);
        if (c < 0) {
            throw makeException("illegal.character.escape");
        }
        return c;
    }

    /**
     * Read a single character of throw an exception if none is available.
     * 
     * @param reader the reader to acquire characters from
     * 
     * @return the character read
     * 
     * @throws IOException in case of an I/O error
     * @throws CsfException in case of a syntax error
     */
    private static int readMandatoryChar(Reader reader)
            throws IOException,
                CsfException {

        int c = readChar(reader);
        if (c < 0) {
            throw makeException("unexpected.eof");
        }
        return c;
    }

    /**
     * Parse an {@code \order} section.
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

        for (int c = readMandatoryChar(reader); c != '}'; c =
                readMandatoryChar(reader)) {
            if (c == '\n') {
                ord++;
                lastC = -1;
            } else if (lastC < 0) {
                csf.setOrder((char) c, ord);
                lastC = c;
            } else if (c == '-') {
                c = readChar(reader);
                if (c < 0) {
                    throw makeException("nl.missing");
                }
                for (; lastC < c; lastC++) {
                    csf.setOrder((char) lastC, ord++);
                }
                lastC = -1;

            } else if (c == '_') {
                c = readMandatoryChar(reader);
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
            switch( s ) {
                case "\\order":
                    parseOrder( csf, r );
                    break;
                case "\\uppercase":
                    Parser.UPPER.parse( csf, r );
                    break;
                case "\\lowercase":
                    Parser.LOWER.parse( csf, r );
                    break;
                case "\\lowupcase":
                    Parser.LOW_UP.parse( csf, r );
                    break;
                default:
                    throw makeException( "unknown.section", s );
            }
        }

        return csf;
    }

    /**
     * Read a token started by backslash and consisting of letters.
     * 
     * @param reader the reader to get characters from
     * 
     * @return the token found or {@code null} ON eof
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
                throw makeException("section.missing",
                    Character.toString((char) c));
            }
        }

        return null;
    }

}
