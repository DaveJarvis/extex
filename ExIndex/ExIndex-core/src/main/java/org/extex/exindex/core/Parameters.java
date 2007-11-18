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

package org.extex.exindex.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.core.exception.EofException;
import org.extex.exindex.core.exception.MissingException;
import org.extex.exindex.core.exception.MissingSymbolException;
import org.extex.exindex.core.exception.UnknownAttributeException;
import org.extex.exindex.core.type.token.Cat;
import org.extex.exindex.core.type.token.CharacterToken;
import org.extex.exindex.core.type.token.NumberToken;
import org.extex.exindex.core.type.token.StringToken;
import org.extex.exindex.core.type.token.SymbolToken;
import org.extex.exindex.core.type.token.Value;

/**
 * This class encapsulates a set of parameters of type int, char, and String.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Parameters extends HashMap<String, Value> {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>cat</tt> contains the types of the parameters.
     */
    private static Map<String, Cat> cat = new HashMap<String, Cat>();

    static {
        cat.put("keyword", Cat.S);
        cat.put("arg_open", Cat.C);
        cat.put("arg_close", Cat.C);
        cat.put("range_open", Cat.C);
        cat.put("range_close", Cat.C);
        cat.put("level", Cat.C);
        cat.put("actual", Cat.C);
        cat.put("encap", Cat.C);
        cat.put("quote", Cat.C);
        cat.put("escape", Cat.C);
        cat.put("page_compositor", Cat.S);
        cat.put("preamble", Cat.S);
        cat.put("postamble", Cat.S);
        cat.put("setpage_prefix", Cat.S);
        cat.put("setpage_suffix", Cat.S);
        cat.put("group_skip", Cat.S);
        cat.put("heading_prefix", Cat.S);
        cat.put("heading_suffix", Cat.S);
        cat.put("headings_flag", Cat.N);
        cat.put("item_0", Cat.S);
        cat.put("item_1", Cat.S);
        cat.put("item_2", Cat.S);
        cat.put("item_01", Cat.S);
        cat.put("item_12", Cat.S);
        cat.put("item_x1", Cat.S);
        cat.put("item_x2", Cat.S);
        cat.put("delim_0", Cat.S);
        cat.put("delim_1", Cat.S);
        cat.put("delim_2", Cat.S);
        cat.put("delim_n", Cat.S);
        cat.put("delim_r", Cat.S);
        cat.put("encap_prefix", Cat.S);
        cat.put("encap_infix", Cat.S);
        cat.put("encap_suffix", Cat.S);
        cat.put("page_precedence", Cat.S);
        cat.put("line_max", Cat.N);
        cat.put("indent_space", Cat.S);
        cat.put("indent_length", Cat.N);
    }

    /**
     * Create an instance of Parameters with the compiled in default values.
     * 
     * @return the parameters
     * 
     * @throws IOException in case of an error
     */
    public static Parameters load() throws IOException {

        String s = Parameters.class.getName().replaceAll("\\.", "/");
        InputStream resource =
                Parameters.class.getClassLoader().getResourceAsStream(s);
        if (resource == null) {
            throw new FileNotFoundException(s);
        }

        Parameters p = new Parameters();
        load(new InputStreamReader(resource), p);
        return p;
    }

    /**
     * Read a style file and return it.
     * 
     * @param reader the reader to get new characters from
     * 
     * @return the parameters read
     * 
     * @throws IOException in case of an error
     */
    public static Parameters load(Reader reader) throws IOException {

        Parameters p = load();
        load(reader, p);
        return p;
    }

    /**
     * Read a style file and merge it into a parameter set.
     * 
     * @param reader the reader to get new characters from
     * @param p the parameters to store the values read
     * 
     * @return a pair of numbers denoting the number of attributes set and
     *         rejected
     * 
     * @throws IOException in case of an error
     */
    public static int[] load(Reader reader, Parameters p) throws IOException {

        int[] count = new int[2];
        LineNumberReader r = new LineNumberReader(reader);

        for (Value t = scan(r); t != null; t = scan(r)) {
            if (!(t instanceof SymbolToken)) {
                System.err.println(r.getLineNumber());
                System.err.println(t.toString());
                throw new MissingSymbolException();
            }
            String name = ((SymbolToken) t).get();
            Cat c = cat.get(name);
            if (c == null) {
                throw new UnknownAttributeException(name);
            }
            Value v = scan(r);
            if (v == null) {
                throw new EofException();
            }
            if (c == Cat.S) {
                if (!(v instanceof StringToken)) {
                    // TODO gene: load unimplemented
                    throw new RuntimeException("unimplemented");
                }
            } else if (c == Cat.C) {
                if (!(v instanceof CharacterToken)) {
                    // TODO gene: load unimplemented
                    throw new RuntimeException("unimplemented");
                }
            } else if (c == Cat.N) {
                if (!(v instanceof NumberToken)) {
                    // TODO gene: load unimplemented
                    throw new RuntimeException("unimplemented");
                }
            }
            p.put(name, v);
            count[0]++;
        }
        return count;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param r the reader to get characters from
     * 
     * @return the token found
     * 
     * @throws IOException in case of an error
     */
    private static Value scan(LineNumberReader r) throws IOException {

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
                    new EofException('\'');
                } else if (cc == '\\') {
                    cc = r.read();
                    if (cc < 0) {
                        new EofException('\'');
                    } else if (cc == 'n') {
                        cc = '\n';
                    } else if (cc == 't') {
                        cc = '\t';
                    }
                }
                c = r.read();
                if (c != '\'') {
                    throw (c < 0
                            ? new EofException('\'')
                            : new MissingException('\'', c));
                }
                return new CharacterToken((char) cc);
            } else if (c == '"') {
                StringBuilder sb = new StringBuilder();
                for (c = r.read(); c >= 0 && c != '"'; c = r.read()) {
                    if (c == '\\') {
                        c = r.read();
                        if (c < 0) {
                            throw new EofException('"');
                        } else if (c == 'n') {
                            c = '\n';
                        } else if (c == 't') {
                            c = '\t';
                        }
                    }
                    sb.append((char) c);
                }
                return new StringToken(sb.toString());
            } else if (Character.isDigit(c)) {
                int n = c - '0';
                for (c = r.read(); c >= '0' && c <= '9'; c = r.read()) {
                    n += c - '0';
                }

                return new NumberToken(n);
            } else if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                sb.append((char) c);

                for (c = r.read(); Character.isJavaIdentifierPart(c); c =
                        r.read()) {
                    sb.append((char) c);
                }
                return new SymbolToken(sb.toString());
            } else {
                throw new MissingSymbolException((char) c);
            }
        }

        return null;
    }

    /**
     * Creates a new object.
     */
    public Parameters() {

        super();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * @return
     */
    public char getChar(String name) {

        Value value = get(name);
        if (value instanceof CharacterToken) {
            return ((CharacterToken) value).get();
        }
        return 0;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * @return
     */
    public int getNumber(String name) {

        Value value = get(name);
        if (value instanceof NumberToken) {
            return ((NumberToken) value).get();
        }
        return 0;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * @return
     */
    public String getString(String name) {

        Value value = get(name);
        if (value instanceof StringToken) {
            return ((StringToken) value).get();
        }
        return null;
    }

}
