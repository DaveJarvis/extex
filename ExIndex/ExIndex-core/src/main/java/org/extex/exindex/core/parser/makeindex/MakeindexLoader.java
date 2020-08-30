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

package org.extex.exindex.core.parser.makeindex;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.core.exception.MissingSymbolException;
import org.extex.exindex.core.exception.RawIndexEofException;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.core.exception.UnknownAttributeException;
import org.extex.exindex.core.parser.reader.ReaderLocator;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.value.LChar;
import org.extex.exindex.lisp.type.value.LNumber;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This class encapsulates reader for a set of parameters of type int, char, and
 * String encoded according to the makeindex definition.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class MakeindexLoader {

    /**
     * The field {@code map} contains the mapping of makeindex names to
     * parameter names in ExIndex.
     */
    private static final Map<String, String> MAKEINDEX2PARAM =
            new HashMap<String, String>();

    /**
     * The field {@code DEFAULT_PARAM} contains the default parameters.
     */
    private static final Map<String, LValue> DEFAULT_PARAM =
            new HashMap<String, LValue>();

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
        def("heading_prefix", "index:", new LString(""));
        def("heading_suffix", "index:", new LString(""));
        def("headings_flag", "index:", new LNumber(0));
        def("item_0", "index:", new LString("\n\\item "));
        def("item_1", "index:", new LString("\n\\subitem "));
        def("item_2", "index:", new LString("\n\\subsubitem "));
        def("item_01", "index:", new LString("\n\\subitem "));
        def("item_12", "index:", new LString("\n\\subsubitem "));
        def("item_x1", "index:", new LString("\n\\subitem "));
        def("item_x2", "index:", new LString("\n\\subsubitem "));
        def("delim_0", "index:", new LString(", "));
        def("delim_1", "index:", new LString(", "));
        def("delim_2", "index:", new LString(", "));
        def("delim_n", "markup:locref-list-sep", new LString(", "));
        def("delim_r", "markup:range", new LString("--"));
        def("encap_prefix", "index:", new LString("\\"));
        def("encap_infix", "index:", new LString("{"));
        def("encap_suffix", "index:", new LString("}"));
        def("page_precedence", "index:", new LString("rnaRA"));
        def("line_max", "markup:line-max", new LNumber(72));
        def("indent_space", "markup:indent-space", new LString("\t\t"));
        def("indent_length", "markup:indent-length", new LNumber(16));

        DEFAULT_PARAM.put("markup-index-hierdepth", new LNumber(2));
    }

    /**
     * Define a mapping and a default for a makeindex parameter.
     * 
     * @param makeindex the makeindex parameter
     * @param exindex the indexer parameter
     * @param value the default
     */
    private static void def(String makeindex, String exindex, LValue value) {

        MAKEINDEX2PARAM.put(makeindex, exindex);
        DEFAULT_PARAM.put(exindex, value);
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
     * @throws IOException in case of an error
     * @throws LSettingConstantException in case of an error
     * @throws RawIndexException in case of an error
     * @throws UnknownAttributeException in case of an error
     */
    public static int[] load(Reader reader, String resource, LInterpreter p)
            throws IOException,
                LSettingConstantException,
                RawIndexException,
                UnknownAttributeException {

        int[] count = new int[2];
        ReaderLocator locator = new ReaderLocator(resource, reader);

        for (LValue t = scan(locator); t != null; t = scan(locator)) {
            if (!(t instanceof LSymbol)) {
                throw new MissingSymbolException(locator);
            }
            String name = ((LSymbol) t).getValue();

            String exname = MAKEINDEX2PARAM.get(name);
            if (exname == null) {
                throw new UnknownAttributeException(locator, name);
            }
            LValue val = p.get(exname);
            LValue v = scan(locator);
            if (v == null) {
                throw new RawIndexEofException(locator);
            } else if (val != null && v.getClass() != val.getClass()) {
                // TODO gene: load unimplemented
                throw new RuntimeException("unimplemented");
            }
            p.setq(name, v);
            count[0]++;
        }
        return count;
    }

    /**
     * Create an instance of Parameters with the compiled in default values.
     * 
     * @param p the interpreter
     * 
     * @throws LSettingConstantException in case of an error
     */
    public static void loadDefaults(LInterpreter p)
            throws LSettingConstantException {

        for (String key : DEFAULT_PARAM.keySet()) {
            p.setq(key, DEFAULT_PARAM.get(key));
        }
    }

    /**
     * Scan the input for a value.
     * 
     * @param locator the locator
     * 
     * @return the token found
     * 
     * @throws IOException in case of an error
     * @throws RawIndexException
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
                    throw (c < 0
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


    private MakeindexLoader() {

        // not in use
    }

}
