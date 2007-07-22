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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.ocpware.compiler.left.Left;
import org.extex.ocpware.compiler.left.LeftParser;
import org.extex.ocpware.compiler.type.Expression;
import org.extex.ocpware.compiler.type.Table;

/**
 * This class provides a compiler state to translate an otp file into an ocp.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class CompilerState {

    /**
     * The enumeration names the different parts.
     */
    private enum Part {
        /**
         * The field <tt>ALIASES</tt> contains the value foe aliases.
         */
        aliases,
        /**
         * The field <tt>EXPRESSIONS</tt> contains the value for expressions.
         */
        expressions,
        /**
         * The field <tt>INPUT</tt> contains the value for input.
         */
        input,
        /**
         * The field <tt>OUTPUT</tt> contains the the value for output.
         */
        output,
        /**
         * The field <tt>STATES</tt> contains the the value for states.
         */
        states,
        /**
         * The field <tt>TABLES</tt> contains the the value for tables.
         */
        tables
    }

    /**
     * Parse a list of aliases.
     * 
     * @param s the input stream
     * @return the list of aliases found
     * 
     * @throws IOException in case of an I/O error
     */
    private static Map<String, Left> parseAliases(ParserStream s)
            throws IOException {

        Map<String, Left> map = new HashMap<String, Left>();

        for (String t = s.parseId(); t != null; t = s.parseId()) {
            if ("expressions".equals(t)) {
                s.unread(t.getBytes());
                break;
            }
            s.expect('=');
            map.put(t, LeftParser.parseLeft(s));
            s.expect(';');
        }

        return map;
    }

    /**
     * Parse a comma separated list of states.
     * 
     * @param s the input stream
     * @return the list of states
     * 
     * @throws IOException in case of an I/O error
     */
    private static List<String> parseStates(ParserStream s) throws IOException {

        List<String> list = new ArrayList<String>();
        int c;

        do {
            list.add(s.parseId());
            c = s.skipSpace();
        } while (c == ',');

        if (c != ';') {
            throw s.error(c);
        }

        return list;
    }

    /**
     * Parse a list of tables.
     * 
     * @param s the input stream
     * @return the list of tables found
     * 
     * @throws IOException in case of an I/O error
     */
    private static List<Table> parseTables(ParserStream s) throws IOException {

        List<Table> result = new ArrayList<Table>();
        String id;

        for (id = s.parseId(); id != null && !"expressions".equals(id); id =
                s.parseId()) {

            s.expect('[');
            int n = s.parseNumber(s.skipSpace());
            s.expect(']');
            s.expect('=');
            s.expect('{');
            List<Integer> tab = new ArrayList<Integer>();
            int c;
            do {
                int x = s.parseNumber(s.skipSpace());
                tab.add(new Integer(x));
                c = s.skipSpace();
            } while (c == ',');
            if (c != '}') {
                throw s.error(c);
            }
            s.expect(';');
            int size = tab.size();
            int[] a = new int[size];
            for (int i = 0; i < size; i++) {
                a[i] = tab.get(i).intValue();
            }
            result.add(new Table(id, n, a));
        }
        s.unread(id.getBytes());
        return result;
    }

    /**
     * The field <tt>aliases</tt> contains the aliases.
     */
    private Map<String, Left> aliases;

    /**
     * The field <tt>expressions</tt> contains the list of expressions.
     */
    private List<Expression> expressions;

    /**
     * The field <tt>in</tt> contains the number of bytes in the input stream.
     */
    private int in;

    /**
     * The field <tt>out</tt> contains the number of bytes in the output
     * stream.
     */
    private int out;

    /**
     * The field <tt>states</tt> contains the list of states.
     */
    private List<String> states;

    /**
     * The field <tt>tables</tt> contains the list of tables.
     */
    private List<Table> tables;

    /**
     * Creates a new object.
     */
    public CompilerState() {

        super();
    }

    /**
     * Compile an input stream into an oc program.
     * 
     * @param stream the input stream
     * 
     * @throws IOException in case of an I/O error
     */
    public void parse(InputStream stream) throws IOException {

        ParserStream s = new ParserStream(stream);

        for (String t = s.parseId(); t != null; t = s.parseId()) {
            Part part = Part.valueOf(t);
            if (part == null) {
                throw new IOException("unexpected " + t);
            }
            s.expect(':');

            switch (part) {
                case input:
                    in = s.parseNumber(s.skipSpace());
                    s.expect(';');
                    break;
                case output:
                    out = s.parseNumber(s.skipSpace());
                    s.expect(';');
                    break;
                case states:
                    states = parseStates(s);
                    break;
                case tables:
                    tables = parseTables(s);
                    break;
                case aliases:
                    aliases = parseAliases(s);
                    break;
                case expressions:
                    expressions = Expression.parseExpressions(s);
                    break;
                default:
                    return;
            }
        }
    }

    /**
     * Getter for aliases.
     * 
     * @return the aliases
     */
    public Map<String, Left> getAliases() {

        return aliases;
    }

    /**
     * Setter for aliases.
     * 
     * @param aliases the aliases to set
     */
    public void setAliases(Map<String, Left> aliases) {

        this.aliases = aliases;
    }

    /**
     * Getter for expressions.
     * 
     * @return the expressions
     */
    public List<Expression> getExpressions() {

        return expressions;
    }

    /**
     * Setter for expressions.
     * 
     * @param expressions the expressions to set
     */
    public void setExpressions(List<Expression> expressions) {

        this.expressions = expressions;
    }

    /**
     * Getter for in.
     * 
     * @return the in
     */
    public int getIn() {

        return in;
    }

    /**
     * Setter for in.
     * 
     * @param in the in to set
     */
    public void setIn(int in) {

        this.in = in;
    }

    /**
     * Getter for out.
     * 
     * @return the out
     */
    public int getOut() {

        return out;
    }

    /**
     * Setter for out.
     * 
     * @param out the out to set
     */
    public void setOut(int out) {

        this.out = out;
    }

    /**
     * Getter for states.
     * 
     * @return the states
     */
    public List<String> getStates() {

        return states;
    }

    /**
     * Setter for states.
     * 
     * @param states the states to set
     */
    public void setStates(List<String> states) {

        this.states = states;
    }

    /**
     * Getter for tables.
     * 
     * @return the tables
     */
    public List<Table> getTables() {

        return tables;
    }

    /**
     * Setter for tables.
     * 
     * @param tables the tables to set
     */
    public void setTables(List<Table> tables) {

        this.tables = tables;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("input:  ");
        sb.append(Integer.toString(in));
        sb.append(";\n");
        sb.append("output: ");
        sb.append(Integer.toString(out));
        sb.append(";\n");
        if (tables != null && !tables.isEmpty()) {
            sb.append("tables:\n");
            for (Table t : tables) {
                sb.append(t.toString());
            }
            sb.append("\n");
        }
        if (states != null && !states.isEmpty()) {
            sb.append("states:\n  ");
            boolean sep = false;
            for (String s : states) {
                if (sep) {
                    sb.append(",\n  ");
                } else {
                    sep = true;
                }
                sb.append(s);
            }
            sb.append(";\n");
        }
        if (aliases != null && !aliases.isEmpty()) {
            sb.append("aliases:\n");
            for (String a : aliases.keySet()) {
                sb.append("  ");
                sb.append(a);
                sb.append("=");
                sb.append(aliases.get(a).toString());
                sb.append("\n");
            }
            sb.append("\n");
        }
        if (expressions != null && !expressions.isEmpty()) {
            sb.append("expressions:\n");
            for (Expression ex : expressions) {
                sb.append(ex.toString());
                sb.append("\n");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
