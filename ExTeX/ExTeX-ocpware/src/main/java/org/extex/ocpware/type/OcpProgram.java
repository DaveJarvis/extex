/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.ocpware.type;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a compiled omega program.
 * 
 * 
 * <h3>The &Omega;CP File Format</h3>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5975 $
 */
public class OcpProgram implements Serializable {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Load an OCP program from an input stream.
     * 
     * @param stream the input stream
     * 
     * @return the program
     * 
     * @throws IOException in case of an IO error
     */
    public static OcpProgram load(InputStream stream) throws IOException {

        OcpProgram ocp = new OcpProgram();

        ocp.setLength(readWord(stream));
        ocp.setInput(readWord(stream));
        ocp.setOutput(readWord(stream));

        int tables = readWord(stream);
        readWord(stream); // tableSpace
        int states = readWord(stream);
        readWord(stream); // stateSpace

        int[] t = read(stream, tables);
        for (int i = 0; i < t.length; i++) {
            ocp.addTable(read(stream, t[i]));
        }

        t = read(stream, states);
        for (int i = 0; i < t.length; i++) {
            ocp.addState(read(stream, t[i]));
        }

        return ocp;
    }

    /**
     * Read an array of words.
     * 
     * @param in the input stream
     * @param len the number of words to read
     * 
     * @return the array read
     * 
     * @throws IOException in case of an error
     */
    private static int[] read(InputStream in, int len) throws IOException {

        int[] a = new int[len];
        for (int i = 0; i < len; i++) {
            a[i] = readWord(in);
        }
        return a;
    }

    /**
     * Read a single word of 4 bytes (octets).
     * 
     * @param in the input stream
     * 
     * @return the word read
     * 
     * @throws IOException in case of an error
     */
    private static int readWord(InputStream in) throws IOException {

        int a = in.read();
        if (a < 0) {
            throw new IOException("unexpected EOF");
        }
        int b = in.read();
        if (b < 0) {
            throw new IOException("unexpected EOF");
        }
        int c = in.read();
        if (c < 0) {
            throw new IOException("unexpected EOF");
        }
        int d = in.read();
        if (d < 0) {
            throw new IOException("unexpected EOF");
        }

        return (a << 24) | (b << 16) | (c << 8) | d;
    }

    /**
     * Write a single word of 4 bytes (octets).
     * 
     * @param out the input stream
     * @param w the word to write
     * 
     * @throws IOException in case of an error
     */
    private static void writeWord(OutputStream out, int w) throws IOException {

        out.write(w >> 24);
        out.write(w >> 16);
        out.write(w >> 8);
        out.write(w);
    }

    /**
     * The field <tt>input</tt> contains the input parameter. The default is
     * 2.
     */
    private int input = 2;

    /**
     * The field <tt>length</tt> contains the length for dumping. A negative
     * value is used to indicate an undefined state.
     */
    private int length = -1;

    /**
     * The field <tt>output</tt> contains the output parameter. The default is
     * 2.
     */
    private int output = 2;

    /**
     * The field <tt>states</tt> contains the states.
     */
    private List<int[]> states = new ArrayList<int[]>();

    /**
     * The field <tt>tables</tt> contains the tables.
     */
    private List<int[]> tables = new ArrayList<int[]>();


    public OcpProgram() {

    }

    /**
     * Add a state.
     * 
     * @param t the state to add
     */
    public void addState(int[] t) {

        states.add(t);
    }

    /**
     * Add a table.
     * 
     * @param t the table to add
     * 
     * @throws IndexOutOfBoundsException if the index is out of range (index
     *        &lt; 0 || index &gt;= tables.size()).
     */
    public void addTable(int[] t) throws IndexOutOfBoundsException {

        tables.add(t);
    }

    /**
     * Getter for a state's code.
     *
     * @param state the state
     * 
     * @return the code
     *
     * @throws IndexOutOfBoundsException if the index is out of range (index
     *        &lt; 0 || index &gt;= states.size()).
     */
    public int[] getCode(int state) throws IndexOutOfBoundsException {

        return states.get(state);
    }

    /**
     * Getter for the number of input bytes.
     * 
     * @return the number of input bytes
     */
    public int getInput() {

        return this.input;
    }

    /**
     * Getter for the length.
     * 
     * @return the length
     */
    public int getLength() {

        if (length >= 0) {
            return length;
        }

        int tableSpace = 0;
        for (int[] t : tables) {
            tableSpace += t.length;
        }
        int stateSpace = 0;
        for (int[] t : states) {
            stateSpace += t.length;
        }
        return 7 + tableSpace + tables.size() + stateSpace + states.size();
    }

    /**
     * Getter for output.
     * 
     * @return the output
     */
    public int getOutput() {

        return this.output;
    }

    /**
     * Getter for states.
     * 
     * @return the states
     */
    public List<int[]> getStates() {

        return states;
    }

    /**
     * Getter for tables.
     * 
     * @return the tables
     */
    public List<int[]> getTables() {

        return tables;
    }

    /**
     * Getter for a table.
     *
     * @param i the index
     * 
     * @return the table
     */
    public int[] getTable(int i) {

        return tables.get(i);
    }

    /**
     * Save an OCP program to an output stream.
     * 
     * @param stream the output stream
     * 
     * @throws IOException in case of an IO error
     */
    public void save(OutputStream stream) throws IOException {

        int tableSpace = 0;
        for (int[] t : tables) {
            tableSpace += t.length;
        }
        int stateSpace = 0;
        for (int[] t : states) {
            stateSpace += t.length;
        }
        int len = 6 + tableSpace + tables.size() + stateSpace + states.size();

        writeWord(stream, len);
        writeWord(stream, input);
        writeWord(stream, output);

        writeWord(stream, tables.size());
        writeWord(stream, tableSpace);

        writeWord(stream, states.size());
        writeWord(stream, stateSpace);

        for (int[] t : tables) {
            writeWord(stream, t.length);
        }
        for (int[] t : tables) {
            for (int i : t) {
                writeWord(stream, i);
            }
        }

        for (int[] s : states) {
            writeWord(stream, s.length);
        }
        for (int[] s : states) {
            for (int i : s) {
                writeWord(stream, i);
            }
        }
    }

    /**
     * Setter for input.
     * 
     * @param input the input to set
     */
    public void setInput(int input) {

        this.input = input;
    }

    /**
     * Setter for the length.
     * 
     * @param len the length
     */
    private void setLength(int len) {

        this.length = len;
    }

    /**
     * Setter for output.
     * 
     * @param output the output to set
     */
    public void setOutput(int output) {

        this.output = output;
    }

}
