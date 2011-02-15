/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

import org.extex.ocpware.compiler.exception.AliasDefinedException;
import org.extex.ocpware.compiler.exception.AliasNotDefinedException;
import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.IllegalOpcodeException;
import org.extex.ocpware.compiler.exception.MissingExpressionsException;
import org.extex.ocpware.compiler.exception.StateDefinedException;
import org.extex.ocpware.compiler.exception.StateNotDefinedException;
import org.extex.ocpware.compiler.exception.SyntaxException;
import org.extex.ocpware.compiler.exception.TableDefinedException;
import org.extex.ocpware.compiler.exception.TableNotDefinedException;
import org.extex.ocpware.compiler.exception.UnexpectedException;
import org.extex.ocpware.compiler.expression.Expression;
import org.extex.ocpware.compiler.left.Left;
import org.extex.ocpware.compiler.left.LeftParser;
import org.extex.ocpware.compiler.type.Table;
import org.extex.ocpware.type.OcpProgram;

/**
 * This class provides a compiler state to translate an otp file into an ocp.
 * <p>
 * The processing is done in two phases. In the first phase a parse tree is
 * build up. This happens within the method
 * {@link #parse(InputStream) parse(InputStream)} or
 * {@link #CompilerState(InputStream) CompilerState(InputStream)} In the second
 * phase the parse tree is traversed and code is generated. This happens within
 * the method {@link #compile() compile()}.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public final class CompilerState {

    /**
     * The constant <tt>INITIAL</tt> contains the symbolic name for the
     * initial state.
     */
    private static final String INITIAL = "INITIAL";

    /**
     * The field <tt>aliases</tt> contains the aliases.
     */
    private Map<String, Left> aliases = new HashMap<String, Left>();

    /**
     * The field <tt>currentState</tt> contains the current state.
     */
    private State currentState;

    /**
     * The field <tt>currentStateIndex</tt> contains the current state.
     */
    private int currentStateIndex = 0;

    /**
     * The field <tt>expressions</tt> contains the list of expressions.
     */
    private List<Expression> expressions;

    /**
     * The field <tt>in</tt> contains the number of bytes in the input stream.
     * The default is 2.
     */
    private int in = 2;

    /**
     * The field <tt>states</tt> contains the list of states.
     */
    private Map<String, Integer> namedStates = new HashMap<String, Integer>();

    /**
     * The field <tt>states</tt> contains the list of states.
     */
    private Map<String, Integer> namedTables = new HashMap<String, Integer>();

    /**
     * The field <tt>out</tt> contains the number of bytes in the output
     * stream. The default is 2.
     */
    private int out = 2;

    /**
     * The field <tt>states</tt> contains the list of states.
     */
    private List<State> states = new ArrayList<State>();

    /**
     * The field <tt>tables</tt> contains the list of tables.
     */
    private Map<String, Table> tables = new HashMap<String, Table>();

    /**
     * Creates a new object.
     * 
     * @throws StateDefinedException in case that the INITIAL state is already
     *         known &ndash; extremely unlikely
     */
    public CompilerState() throws StateDefinedException {

        storeState(INITIAL);
    }

    /**
     * Creates a new object.
     * 
     * @param stream the input stream
     * 
     * @throws IOException in case of an I/O error
     * @throws ArgmentTooBigException if the argument of the instruction exceeds
     *         the 16 bit value
     * @throws StateDefinedException in case that a state is about to be defined
     *         a second time
     * @throws TableDefinedException in case that a table is about to be defined
     *         a second time
     * @throws SyntaxException in case of a syntax error
     * @throws AliasDefinedException in case that an attempt has been
     *         encountered to define an alias a second time
     * @throws MissingExpressionsException in case of a syntax error
     */
    public CompilerState(InputStream stream)
            throws IOException,
                ArgmentTooBigException,
                StateDefinedException,
                SyntaxException,
                TableDefinedException,
                AliasDefinedException,
                MissingExpressionsException {

        this();
        parse(stream);
    }

    /**
     * Compile the current compiler state into an ocp program.
     * 
     * @return the OCP program
     * 
     * @throws AliasNotDefinedException in case that no matching alias is known
     *         for a symbolic table reference
     * @throws ArgmentTooBigException in case that an argument is encountered
     *         which does not fit into two bytes
     * @throws IOException in case of an I/O error
     * @throws StateNotDefinedException in case that no matching state is known
     *         for a symbolic table reference
     * @throws TableNotDefinedException in case that no matching table is known
     *         for a symbolic table reference
     */
    public OcpProgram compile()
            throws AliasNotDefinedException,
                ArgmentTooBigException,
                IOException,
                StateNotDefinedException,
                TableNotDefinedException {

        OcpProgram ocp = new OcpProgram();
        ocp.setInput(in);
        ocp.setOutput(out);

        for (Table t : tables.values()) {
            ocp.addTable(t.getContents());
        }

        for (Expression e : expressions) {
            e.compile(this);
        }

        for (State state : states) {
            state.close();
            ocp.addState(state.getInstructions());
        }

        return ocp;
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
     * Getter for current state.
     * 
     * @return the current state
     */
    public State getCurrentState() {

        return currentState;
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
     * Getter for in. The default is 2.
     * 
     * @return the in
     */
    public int getIn() {

        return in;
    }

    /**
     * Getter for out. The default is 2.
     * 
     * @return the out
     */
    public int getOut() {

        return out;
    }

    /**
     * Getter for state.
     * 
     * @return the state
     */
    public List<State> getState() {

        return states;
    }

    /**
     * Getter for states.
     * 
     * @return the states
     */
    public Map<String, Integer> getStates() {

        return namedStates;
    }

    /**
     * Getter for tables.
     * 
     * @return the tables
     */
    public Map<String, Table> getTables() {

        return tables;
    }

    /**
     * Increment the number of expressions for the current state.
     */
    public void incrExpr() {

        currentState.incrExpressions();
    }

    /**
     * Find a value for an alias name.
     * 
     * @param alias the string representation of the alias
     * 
     * @return the left item of the alias
     * 
     * @throws AliasNotDefinedException in case that the alias is not defined
     */
    public Left lookupAlias(String alias) throws AliasNotDefinedException {

        Left s = aliases.get(alias);
        if (s == null) {
            throw new AliasNotDefinedException(alias);
        }
        return s;
    }

    /**
     * Find a number representation for a state name.
     * 
     * @param state the string representation of the state
     * 
     * @return the number of the state
     * 
     * @throws StateNotDefinedException in case that the state is not defined
     */
    public int lookupState(String state) throws StateNotDefinedException {

        Integer s = namedStates.get(state);
        if (s == null) {
            throw new StateNotDefinedException(state);
        }
        return s.intValue();
    }

    /**
     * Get the numeric index of a table from the symbolic name.
     * 
     * @param table the name of the table
     * 
     * @return the numeric index of a table
     * 
     * @throws TableNotDefinedException in case that no matching table is known
     */
    public int lookupTable(String table) throws TableNotDefinedException {

        Integer s = namedTables.get(table);
        if (s == null) {
            throw new TableNotDefinedException(table);
        }
        return s.intValue();
    }

    /**
     * Compile an input stream into an oc program.
     * 
     * @param stream the input stream
     * 
     * @throws IOException in case of an I/O error
     * @throws ArgmentTooBigException if the argument of the instruction exceeds
     *         the 16 bit value
     * @throws SyntaxException in case of a syntax error
     * @throws StateDefinedException in case that a state is about to be defined
     *         a second time
     * @throws TableDefinedException in case that a table is about to be defined
     *         a second time
     * @throws AliasDefinedException in case that an attempt has been
     *         encountered to define an alias a second time
     * @throws MissingExpressionsException in case of a syntax error
     */
    public void parse(InputStream stream)
            throws IOException,
                ArgmentTooBigException,
                SyntaxException,
                StateDefinedException,
                TableDefinedException,
                AliasDefinedException,
                MissingExpressionsException {

        ParserStream s = new ParserStream(stream);

        for (String t = s.parseId(); t != null; t = s.parseId()) {

            if ("input:".equals(t)) {
                in = s.parseNumber(s.skipSpace());
                s.expect(';');
            } else if ("output:".equals(t)) {
                out = s.parseNumber(s.skipSpace());
                s.expect(';');
            } else if ("states:".equals(t)) {
                parseStates(s);
            } else if ("tables:".equals(t)) {
                parseTables(s);
            } else if ("aliases:".equals(t)) {
                parseAliases(s);
            } else if ("expressions:".equals(t)) {
                expressions = Expression.parseExpressions(s);
                break;
            } else {
                throw new UnexpectedException(t, s.getLine(), s.getLineno());
            }
        }

        if (expressions == null) {
            throw new MissingExpressionsException();
        }
    }

    /**
     * Parse a list of aliases.
     * 
     * @param s the input stream
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     * @throws AliasDefinedException in case that an attempt has been
     *         encountered to define an alias a second time
     */
    private void parseAliases(ParserStream s)
            throws IOException,
                SyntaxException,
                AliasDefinedException {

        for (String t = s.parseId(); t != null; t = s.parseId()) {
            if ("expressions:".equals(t)) {
                s.unread(t.getBytes());
                break;
            }
            if (aliases.get(t) != null) {
                throw new AliasDefinedException(t);
            }
            s.expect('=');
            aliases.put(t, LeftParser.completeLeft(s));
            s.expect(';');
        }
    }

    /**
     * Parse a comma separated list of states.
     * 
     * @param s the input stream
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     * @throws StateDefinedException in case that a state is about to be defined
     *         a second time
     */
    private void parseStates(ParserStream s)
            throws IOException,
                StateDefinedException,
                SyntaxException {

        int c;
        do {
            storeState(s.parseId());
            c = s.skipSpace();
        } while (c == ',');

        if (c != ';') {
            throw s.error(c);
        }

    }

    /**
     * Parse a list of tables.
     * 
     * @param s the input stream
     * 
     * @throws IOException in case of an I/O error
     * @throws SyntaxException in case of a syntax error
     * @throws TableDefinedException in case that a table is about to be defined
     *         a second time
     */
    private void parseTables(ParserStream s)
            throws IOException,
                TableDefinedException,
                SyntaxException {

        String name;

        for (name = s.parseId(); name != null && !"expressions:".equals(name); name =
                s.parseId()) {

            if (tables.get(name) != null) {
                throw new TableDefinedException(name);
            }
            s.expect('[');
            s.parseNumber(s.skipSpace()); // length; not used
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
            namedTables.put(name, Integer.valueOf(tables.size()));
            tables.put(name, new Table(name, a));
        }
        s.unread(name.getBytes());
    }

    /**
     * Put an instruction of one op code and no argument into the store.
     * 
     * @param opCode the op code
     * 
     * @return the index of the next free position in the instruction array
     * 
     * @throws IOException in case of an I/O error
     * @throws IllegalOpcodeException in case of an illegal op code
     */
    public int putInstruction(int opCode) throws IOException {

        return currentState.putInstruction(opCode);
    }

    /**
     * Put an instruction of one op code and one argument into the store.
     * 
     * @param opCode the op code
     * @param n the first argument
     * 
     * @return the index of the next free position in the instruction array
     * 
     * @throws IOException in case of an I/O error
     * @throws ArgmentTooBigException if the argument of the instruction exceeds
     *         the 16 bit value
     * @throws IllegalOpcodeException in case of an illegal op code
     */
    public int putInstruction(int opCode, int n)
            throws IOException,
                ArgmentTooBigException {

        return currentState.putInstruction(opCode, n);
    }

    /**
     * Put an instruction of one op code and three arguments into the store.
     * 
     * @param opCode the op code
     * @param n the first argument
     * @param a the second argument
     * 
     * @return the index of the next free position in the instruction array
     * 
     * @throws IOException in case of an I/O error
     * @throws ArgmentTooBigException if the argument of the instruction exceeds
     *         the 16 bit value
     * @throws IllegalOpcodeException in case of an illegal op code
     */
    public int putInstruction(int opCode, int n, int a)
            throws IOException,
                ArgmentTooBigException {

        return currentState.putInstruction(opCode, n, a);
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
     * Setter for current state.
     * 
     * @param state the current state index
     */
    public void setCurrentState(int state) {

        this.currentStateIndex = state;
        this.currentState = states.get(currentStateIndex);
    }

    /**
     * Setter for the current state.
     * 
     * @param state the new state
     * 
     * @throws StateNotDefinedException in case that the state is not defined
     */
    public void setCurrentState(String state) throws StateNotDefinedException {

        currentStateIndex = (state == null ? 0 : lookupState(state));
        this.currentState = states.get(currentStateIndex);
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
     * Setter for in.
     * 
     * @param in the in to set
     */
    public void setIn(int in) {

        this.in = in;
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
     * Setter for tables.
     * 
     * @param tables the tables to set
     */
    public void setTables(Map<String, Table> tables) {

        this.tables = tables;
    }

    /**
     * Save a new name as name of a state.
     * 
     * @param name the name of the state
     * 
     * @throws StateDefinedException in case that the name is already registered
     *         as state
     */
    public void storeState(String name) throws StateDefinedException {

        if (namedStates.get(name) != null) {
            throw new StateDefinedException(name);
        }
        namedStates.put(name, Integer.valueOf(namedStates.size()));
        states.add(new State());
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("input:  ");
        sb.append(Integer.toString(in));
        sb.append(";\n");
        sb.append("output: ");
        sb.append(Integer.toString(out));
        sb.append(";\n");
        if (tables != null && !tables.isEmpty()) {
            sb.append("tables:\n");
            for (Table t : tables.values()) {
                sb.append(t.toString());
            }
            sb.append("\n");
        }
        if (namedStates.size() > 1) {
            sb.append("states:\n  ");
            boolean sep = false;
            for (String s : namedStates.keySet()) {
                if (!INITIAL.equals(s)) {
                    if (sep) {
                        sb.append(",\n  ");
                    } else {
                        sep = true;
                    }
                    sb.append(s);
                }
            }
            sb.append(";\n");
        }
        if (!aliases.isEmpty()) {
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
            }
        }

        return sb.toString();
    }

}
