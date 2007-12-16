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

package org.extex.exindex.core.makeindex;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.core.exception.EofException;
import org.extex.exindex.core.exception.MissingException;
import org.extex.exindex.core.exception.MissingSymbolException;
import org.extex.exindex.core.exception.UnknownAttributeException;
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
 * @version $Revision$
 */
public final class MakeindexParameters {

    /**
     * The field <tt>map</tt> contains the mapping of makeindex names to
     * parameter names in ExIndex.
     */
    private static final Map<String, String> MAKEINDEX2PARAM =
            new HashMap<String, String>();

    /**
     * The field <tt>defaultParameters</tt> contains the ...
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

        defaultParameters.put("markup-index-hierdepth", new LNumber(2));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param makeindex
     * @param exindex
     * @param value
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
     * @throws IOException in case of an error
     */
    public static Parameters load(Reader reader, String resource)
            throws IOException {

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
     * @throws IOException in case of an error
     */
    public static int[] load(Reader reader, String resource, Parameters p)
            throws IOException {

        int[] count = new int[2];
        LineNumberReader r = new LineNumberReader(reader);

        for (LValue t = scan(r, resource); t != null; t = scan(r, resource)) {
            if (!(t instanceof LSymbol)) {
                throw new MissingSymbolException(resource, r.getLineNumber());
            }
            String name = ((LSymbol) t).getValue();

            String exname = MAKEINDEX2PARAM.get(name);
            if (exname == null) {
                throw new UnknownAttributeException(name);
            }
            LValue val = p.get(name);
            LValue v = scan(r, resource);
            if (v == null) {
                throw new EofException(resource, r.getLineNumber());
            } else if (v.getClass() != val.getClass()) {
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
     * @param r the reader to get characters from
     * @param resource the name of the resource loaded
     * 
     * @return the token found
     * 
     * @throws IOException in case of an error
     */
    private static LValue scan(LineNumberReader r, String resource)
            throws IOException {

        for (int c = r.read(); c >= 0; c = r.read()) {
            if (Character.isWhitespace(c)) {
                // skip whitespace
            } else if (c == '%') {
                for (c = r.read(); c >= 0 && c != '\n' && c != '\r'; c =
                        r.read()) {
                    // ignore comment
                }
            } else if (c == '\'') {
                int cc = r.read();
                if (cc < 0) {
                    new EofException(resource, r.getLineNumber(), '\'');
                } else if (cc == '\\') {
                    cc = r.read();
                    if (cc < 0) {
                        new EofException(resource, r.getLineNumber(), '\'');
                    } else if (cc == 'n') {
                        cc = '\n';
                    } else if (cc == 't') {
                        cc = '\t';
                    }
                }
                c = r.read();
                if (c != '\'') {
                    throw (c < 0 //
                            ? new EofException(resource, r.getLineNumber(),
                                '\'')
                            : new MissingException(resource, r.getLineNumber(),
                                (char) c, '\''));
                }
                return new LChar((char) cc);
            } else if (c == '"') {
                StringBuilder sb = new StringBuilder();
                for (c = r.read(); c >= 0 && c != '"'; c = r.read()) {
                    if (c == '\\') {
                        c = r.read();
                        if (c < 0) {
                            throw new EofException(resource, r.getLineNumber(),
                                '"');
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
                for (c = r.read(); c >= '0' && c <= '9'; c = r.read()) {
                    n += c - '0';
                }

                return new LNumber(n);
            } else if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                sb.append((char) c);

                for (c = r.read(); Character.isJavaIdentifierPart(c); c =
                        r.read()) {
                    sb.append((char) c);
                }
                return LSymbol.get(sb.toString());
            } else {
                throw new MissingSymbolException(resource, r.getLineNumber(),
                    (char) c);
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
