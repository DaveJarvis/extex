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

package org.extex.exindex.makeindex;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.core.exception.RawIndexEofException;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.core.parser.reader.ReaderLocator;
import org.extex.exindex.lisp.type.value.LChar;
import org.extex.exindex.lisp.type.value.LNumber;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;
import org.extex.exindex.makeindex.exceptions.MissingSymbolException;
import org.extex.exindex.makeindex.exceptions.UnknownAttributeException;

/**
 * This class encapsulates reader for a set of parameters of type int, char, and
 * String encoded according to the makeindex definition.
 * <p>
 * The following table lists the mapping, types, and default values.
 * </p>
 * <table>
 * <tr>
 * <th>Makeindex name</th>
 * <th>&epsilon;&chi;Index name</th>
 * <th>Type</th>
 * <th>Default Value</th>
 * </tr>
 * <tr>
 * <td>keyword</td>
 * <td>index:keyword</td>
 * <td>String</td>
 * <td>"\\indexentry"</td>
 * </tr>
 * <tr>
 * <td>arg_open</td>
 * <td>index:arg-open</td>
 * <td>Char</td>
 * <td>)</td>
 * </tr>
 * <tr>
 * <td>arg_close</td>
 * <td>index:arg-close</td>
 * <td>Char</td>
 * <td>)</td>
 * </tr>
 * <tr>
 * <td>range_open</td>
 * <td>index:range-open</td>
 * <td>Char</td>
 * <td>(</td>
 * </tr>
 * <tr>
 * <td>range_close</td>
 * <td>indexrange-close:</td>
 * <td>Char</td>
 * <td>)</td>
 * </tr>
 * <tr>
 * <td>level</td>
 * <td>index:level</td>
 * <td>Char</td>
 * <td>!</td>
 * </tr>
 * <tr>
 * <td>actual</td>
 * <td>index:actual</td>
 * <td>Char</td>
 * <td>@</td>
 * </tr>
 * <tr>
 * <td>encap</td>
 * <td>index:encap</td>
 * <td>Char</td>
 * <td>|</td>
 * </tr>
 * <tr>
 * <td>quote</td>
 * <td>index:quote</td>
 * <td>Char</td>
 * <td>"</td>
 * </tr>
 * <tr>
 * <td>escape</td>
 * <td>index:escape</td>
 * <td>Char</td>
 * <td>\\</td>
 * </tr>
 * <tr>
 * <td>page_compositor</td>
 * <td>index:page-compositor</td>
 * <td>String</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>preamble</td>
 * <td>markup:index-open</td>
 * <td>String</td>
 * <td>\\begin{theindex}\n</td>
 * </tr>
 * <tr>
 * <td>postamble</td>
 * <td>markup:index-close</td>
 * <td>String</td>
 * <td>"\n\n\\end{theindex}\n"</td>
 * </tr>
 * <tr>
 * <td>setpage_prefix</td>
 * <td>markup:setpage-prefix</td>
 * <td>String</td>
 * <td>"\n\\setcounter{page}{"</td>
 * </tr>
 * <tr>
 * <td>setpage_suffix</td>
 * <td>markup:setpage-suffix</td>
 * <td>String</td>
 * <td>"\n"</td>
 * </tr>
 * <tr>
 * <td>group_skip</td>
 * <td>markup:letter-group-list-sep</td>
 * <td>String</td>
 * <td>"\n\n\\indexspace\n"</td>
 * </tr>
 * <tr>
 * <td>heading_prefix</td>
 * <td>markup:heading-prefix</td>
 * <td>String</td>
 * <td>""</td>
 * </tr>
 * <tr>
 * <td>heading_suffix</td>
 * <td>markup:heading-suffix</td>
 * <td>String</td>
 * <td>""</td>
 * </tr>
 * <tr>
 * <td>headings_flag</td>
 * <td>index:headings-flag</td>
 * <td>Number</td>
 * <td>0</td>
 * </tr>
 * <tr>
 * <td>item_0</td>
 * <td>index:item_0</td>
 * <td>String</td>
 * <td>"\n\\item "</td>
 * </tr>
 * <tr>
 * <td>item_1</td>
 * <td>index:item_1</td>
 * <td>String</td>
 * <td>"\n\\subitem "</td>
 * </tr>
 * <tr>
 * <td>item_2</td>
 * <td>indexitem_2:</td>
 * <td>String</td>
 * <td>\n\\subsubitem "</td>
 * </tr>
 * <tr>
 * <td>item_01</td>
 * <td>index:item_01</td>
 * <td>String</td>
 * <td>"\n\\subitem "</td>
 * </tr>
 * <tr>
 * <td>item_12</td>
 * <td>index:item_12</td>
 * <td>String</td>
 * <td>"\n\\subsubitem "</td>
 * </tr>
 * <tr>
 * <td>item_x1</td>
 * <td>index:item_x1</td>
 * <td>String</td>
 * <td>"\n\\subitem "</td>
 * </tr>
 * <tr>
 * <td>item_x2</td>
 * <td>index:item_x2</td>
 * <td>String</td>
 * <td>"\n\\subsubitem "</td>
 * </tr>
 * <tr>
 * <td>delim_0</td>
 * <td>index:delim_0</td>
 * <td>String</td>
 * <td>", "</td>
 * </tr>
 * <tr>
 * <td>delim_1</td>
 * <td>index:delim_1</td>
 * <td>String</td>
 * <td>", "</td>
 * </tr>
 * <tr>
 * <td>delim_2</td>
 * <td>index:delim_2</td>
 * <td>String</td>
 * <td>", "</td>
 * </tr>
 * <tr>
 * <td>delim_n</td>
 * <td>markup:locref-list-sep</td>
 * <td>String</td>
 * <td>", "</td>
 * </tr>
 * <tr>
 * <td>delim_r</td>
 * <td>markup:range</td>
 * <td>String</td>
 * <td>"--"</td>
 * </tr>
 * <tr>
 * <td>encap_prefix</td>
 * <td>index:encap_prefix</td>
 * <td>String</td>
 * <td>"\\"</td>
 * </tr>
 * <tr>
 * <td>encap_infix</td>
 * <td>index:encap_infix</td>
 * <td>String</td>
 * <td>"{"</td>
 * </tr>
 * <tr>
 * <td>encap_suffix</td>
 * <td>index:encap_suffix</td>
 * <td>String</td>
 * <td>"}"</td>
 * </tr>
 * <tr>
 * <td>page_precedence</td>
 * <td>index:</td>
 * <td>String</td>
 * <td>"rnaRA"</td>
 * </tr>
 * <tr>
 * <td>line_max</td>
 * <td>markup:line-max</td>
 * <td>Number</td>
 * <td>2</td>
 * </tr>
 * <tr>
 * <td>indent_space</td>
 * <td>markup:indent-space</td>
 * <td>String</td>
 * <td>"\t\t"</td>
 * </tr>
 * <tr>
 * <td>indent_length</td>
 * <td>markup:indent-length</td>
 * <td>Number</td>
 * <td>6</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class MakeindexParameters {

    /**
     * The field <tt>map</tt> contains the mapping of makeindex names to
     * parameter names in &epsilon;&chi;Index.
     */
    private static final Map<String, String> MAKEINDEX2PARAM =
            new HashMap<String, String>();

    /**
     * The field <tt>defaultParameters</tt> contains the default parameters.
     */
    private static Parameters defaultParameters = new Parameters();

    static {
        def("keyword", "index:keyword", new LString("\\indexentry"));
        def("arg_open", "index:arg-open", new LChar('{'));
        def("arg_close", "index:arg-close", new LChar('}'));
        def("range_open", "index:range-open", new LChar('('));
        def("range_close", "indexrange-close:", new LChar(')'));
        def("level", "index:level", new LChar('!'));
        def("actual", "index:actual", new LChar('@'));
        def("encap", "index:encap", new LChar('|'));
        def("quote", "index:quote", new LChar('"'));
        def("escape", "index:escape", new LChar('\\'));
        def("page_compositor", "index:page-compositor", new LString("-"));
        def("preamble", "markup:index-open", new LString("\\begin{theindex}\n"));
        def("postamble", "markup:index-close", new LString(
            "\n\n\\end{theindex}\n"));
        def("setpage_prefix", "markup:setpage-prefix", new LString(
            "\n\\setcounter{page}{"));
        def("setpage_suffix", "markup:setpage-suffix", new LString("}\n"));
        def("group_skip", "markup:letter-group-list-sep", new LString(
            "\n\n\\indexspace\n"));
        def("heading_prefix", "markup:heading-prefix", new LString(""));
        def("heading_suffix", "markup:heading-suffix", new LString(""));
        def("headings_flag", "index:headings-flag", new LNumber(0));
        def("item_0", "index:item_0", new LString("\n\\item "));
        def("item_1", "index:item_1", new LString("\n\\subitem "));
        def("item_2", "indexitem_2:", new LString("\n\\subsubitem "));
        def("item_01", "index:item_01", new LString("\n\\subitem "));
        def("item_12", "index:item_12", new LString("\n\\subsubitem "));
        def("item_x1", "index:item_x1", new LString("\n\\subitem "));
        def("item_x2", "index:item_x2", new LString("\n\\subsubitem "));
        def("delim_0", "index:delim_0", new LString(", "));
        def("delim_1", "index:delim_1", new LString(", "));
        def("delim_2", "index:delim_2", new LString(", "));
        def("delim_n", "markup:locref-list-sep", new LString(", "));
        def("delim_r", "markup:range", new LString("--"));
        def("encap_prefix", "index:encap_prefix", new LString("\\"));
        def("encap_infix", "index:encap_infix", new LString("{"));
        def("encap_suffix", "index:encap_suffix", new LString("}"));
        def("page_precedence", "index:", new LString("rnaRA"));
        def("line_max", "markup:line-max", new LNumber(72));
        def("indent_space", "markup:indent-space", new LString("\t\t"));
        def("indent_length", "markup:indent-length", new LNumber(16));

        defaultParameters.put("markup-index-hierdepth", new LNumber(2));
    }

    /**
     * Define the mapping from makeindex parameters to &epsilon;&chi;Index
     * parameters.
     * 
     * @param makeindex the makeindex name
     * @param exindex the &epsilon;&chi;Index name
     * @param value the default value
     */
    private static void def(String makeindex, String exindex, LValue value) {

        MAKEINDEX2PARAM.put(makeindex, exindex);
        defaultParameters.put(exindex, value);
    }

    /**
     * Create an instance of Parameters with the compiled in default values.
     * 
     * @return the parameters
     */
    public static Parameters load() {

        return (Parameters) defaultParameters.clone();
    }

    /**
     * Read a style file and return it.
     * 
     * @param reader the reader to get new characters from
     * @param resource the name of the resource
     * 
     * @return the parameters read
     * 
     * @throws IOException in case of an I/O error
     * @throws RawIndexException in case of an error
     * @throws RawIndexEofException in case of an error
     * @throws MissingSymbolException in case of an error
     */
    public static Parameters load(Reader reader, String resource)
            throws IOException,
                MissingSymbolException,
                RawIndexEofException,
                RawIndexException {

        Parameters p = load();
        load(reader, resource, p);
        return p;
    }

    /**
     * Read a style file and merge it into a parameter set.
     * 
     * @param reader the reader to get new characters from
     * @param resource the name or the resource
     * @param p the parameters to store the values read
     * 
     * @return a pair of numbers denoting the number of attributes set and
     *         rejected
     * 
     * @throws IOException in case of an I/O error
     * @throws RawIndexException in case of an error
     * @throws RawIndexEofException in case of an error
     * @throws MissingSymbolException in case of an error
     */
    public static int[] load(Reader reader, String resource, Parameters p)
            throws IOException,
                MissingSymbolException,
                RawIndexEofException,
                RawIndexException {

        int[] count = new int[2];
        ReaderLocator locator = new ReaderLocator(resource, reader);

        for (LValue t = scan(locator); t != null; t = scan(locator)) {
            if (!(t instanceof LSymbol)) {
                throw new MissingSymbolException(locator);
            }
            String name = ((LSymbol) t).getValue();

            String exindexName = MAKEINDEX2PARAM.get(name);
            if (exindexName == null) {
                count[1]++;
                throw new UnknownAttributeException(name);
            }
            LValue val = p.get(exindexName);
            LValue v = scan(locator);
            if (v == null) {
                throw new RawIndexEofException(locator);
            } else if (val == null) {
                count[1]++;
                // TODO gene: load unimplemented
                throw new RuntimeException("unimplemented ");
            } else if (v.getClass() != val.getClass()) {
                count[1]++;
                // TODO gene: load unimplemented
                throw new RuntimeException("unimplemented");
            }
            p.put(name, v);
            count[0]++;
        }
        return count;
    }

    /**
     * Scan the input for a value.
     * 
     * @param locator the locator
     * 
     * @return the token found
     * 
     * @throws IOException in case of an I/O error
     * @throws RawIndexException in case of an error
     */
    private static LValue scan(ReaderLocator locator)
            throws IOException,
                RawIndexException {

        for (int c = locator.read(); c >= 0; c = locator.read()) {
            if (Character.isWhitespace(c)) {
                // skip whitespace
            } else if (c == '%') {
                for (c = locator.read(); c >= 0 && c != '\n' && c != '\r'; c =
                        locator.read()) {
                    // ignore comment
                }
            } else if (c == '\'') {
                int cc = locator.read();
                if (cc < 0) {
                    new RawIndexEofException(locator, '\'');
                } else if (cc == '\\') {
                    cc = locator.read();
                    if (cc < 0) {
                        new RawIndexEofException(locator, '\'');
                    } else if (cc == 'n') {
                        cc = '\n';
                    } else if (cc == 't') {
                        cc = '\t';
                    }
                }
                c = locator.read();
                if (c != '\'') {
                    throw (c < 0 //
                            ? new RawIndexEofException(locator, '\'')
                            : new RawIndexMissingCharException(locator,
                                (char) c, '\''));
                }
                return new LChar((char) cc);
            } else if (c == '"') {
                StringBuilder sb = new StringBuilder();
                for (c = locator.read(); c >= 0 && c != '"'; c = locator.read()) {
                    if (c == '\\') {
                        c = locator.read();
                        if (c < 0) {
                            throw new RawIndexEofException(locator, '"');
                        } else if (c == 'n') {
                            c = '\n';
                        } else if (c == 't') {
                            c = '\t';
                        }
                    }
                    sb.append((char) c);
                }
                return new LString(sb.toString());
            } else if (Character.isDigit(c)) {
                int n = c - '0';
                for (c = locator.read(); c >= '0' && c <= '9'; c =
                        locator.read()) {
                    n += c - '0';
                }

                return new LNumber(n);
            } else if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                sb.append((char) c);

                for (c = locator.read(); Character.isJavaIdentifierPart(c); c =
                        locator.read()) {
                    sb.append((char) c);
                }
                return LSymbol.get(sb.toString());
            } else {
                throw new MissingSymbolException(locator, (char) c);
            }
        }

        return null;
    }

    /**
     * Creates a new object.
     */
    private MakeindexParameters() {

        // not in use
    }

}
