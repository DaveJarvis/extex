/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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
import java.io.PushbackInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class represents a compiled omega program.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5975 $
 */
public class OcpProgram implements Serializable {

    /**
     * The field <tt>ADD</tt> contains the op code for the ocp instruction to
     * add two numbers from the stack.
     */
    public static final int ADD = 11;

    /**
     * The field <tt>DIV</tt> contains the op code for the ocp instruction to
     * divide two numbers from the stack.
     */
    public static final int DIV = 14;

    /**
     * The field <tt>GOTO</tt> contains the op code for the ocp instruction to
     * adjust the program counter.
     */
    public static final int GOTO = 26;

    /**
     * The field <tt>GOTO_BEG</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int GOTO_BEG = 34;

    /**
     * The field <tt>GOTO_END</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int GOTO_END = 35;

    /**
     * The field <tt>GOTO_EQ</tt> contains the op code for the ocp instruction
     * conditionally adjust the program counter.
     */
    public static final int GOTO_EQ = 28;

    /**
     * The field <tt>GOTO_GE</tt> contains the op code for the ocp instruction
     * conditionally adjust the program counter.
     */
    public static final int GOTO_GE = 32;

    /**
     * The field <tt>GOTO_GT</tt> contains the op code for the ocp instruction
     * conditionally adjust the program counter.
     */
    public static final int GOTO_GT = 31;

    /**
     * The field <tt>GOTO_LE</tt> contains the op code for the ocp instruction
     * to ...
     */
    public static final int GOTO_LE = 30;

    /**
     * The field <tt>GOTO_LT</tt> contains the op code for the ocp instruction
     * conditionally adjust the program counter.
     */
    public static final int GOTO_LT = 29;

    /**
     * The field <tt>GOTO_NE</tt> contains the op code for the ocp instruction
     * conditionally adjust the program counter.
     */
    public static final int GOTO_NE = 27;

    /**
     * The field <tt>GOTO_NO_ADVANCE</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int GOTO_NO_ADVANCE = 33;

    /**
     * The field <tt>LEFT_BACKUP</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int LEFT_BACKUP = 25;

    /**
     * The field <tt>LEFT_RETURN</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int LEFT_RETURN = 24;

    /**
     * The field <tt>LEFT_START</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int LEFT_START = 23;

    /**
     * The field <tt>LOOKUP</tt> contains the op code for the ocp instruction
     * to lookup a table entry.
     */
    public static final int LOOKUP = 16;

    /**
     * The field <tt>MOD</tt> contains the op code for the ocp instruction to
     * compute the remainer of two aruments from the stack.
     */
    public static final int MOD = 15;

    /**
     * The field <tt>MULT</tt> contains the op code for the ocp instruction to
     * multiply two arguments from the stack.
     */
    public static final int MULT = 13;

    /**
     * The field <tt>PBACK_CHAR</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int PBACK_CHAR = 8;

    /**
     * The field <tt>PBACK_LCHAR</tt> contains the op code for the ocp
     * instruction to push back a character.
     */
    public static final int PBACK_LCHAR = 9;

    /**
     * The field <tt>PBACK_NUM</tt> contains the op code for the ocp
     * instruction to push back a number.
     */
    public static final int PBACK_NUM = 7;

    /**
     * The field <tt>PBACK_OUTPUT</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int PBACK_OUTPUT = 6;

    /**
     * The field <tt>PBACK_SOME</tt> contains the op code for the ocp
     * instruction to push back a match.
     */
    public static final int PBACK_SOME = 10;

    /**
     * The field <tt>PUSH_CHAR</tt> contains the op code for the ocp
     * instruction to push back a character.
     */
    public static final int PUSH_CHAR = 18;

    /**
     * The field <tt>PUSH_LCHAR</tt> contains the op code for the ocp
     * instruction to push a referenced character.
     */
    public static final int PUSH_LCHAR = 19;

    /**
     * The field <tt>PUSH_NUM</tt> contains the op code for the ocp
     * instruction to push a number.
     */
    public static final int PUSH_NUM = 17;

    /**
     * The field <tt>RIGHT_CHAR</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int RIGHT_CHAR = 3;

    /**
     * The field <tt>RIGHT_LCHAR</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int RIGHT_LCHAR = 4;

    /**
     * The field <tt>RIGHT_NUM</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int RIGHT_NUM = 2;

    /**
     * The field <tt>RIGHT_OUTPUT</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int RIGHT_OUTPUT = 1;

    /**
     * The field <tt>RIGHT_SOME</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int RIGHT_SOME = 5;

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2006L;

    /**
     * The field <tt>SIXTEEN_BIT_MASK</tt> contains the bit mask with 16 bits.
     */
    private static final int SIXTEEN_BIT_MASK = 0xffff;

    /**
     * The field <tt>STATE_CHANGE</tt> contains the op code for the ocp
     * instruction to ...
     */
    public static final int STATE_CHANGE = 20;

    /**
     * The field <tt>STATE_POP</tt> contains the op code for the ocp
     * instruction to pop a state fron the state stack.
     */
    public static final int STATE_POP = 22;

    /**
     * The field <tt>STATE_PUSH</tt> contains the op code for the ocp
     * instruction to push a state onto the state stack.
     */
    public static final int STATE_PUSH = 21;

    /**
     * The field <tt>STOP</tt> contains the op code for the ocp instruction to
     * end the processing.
     */
    public static final int STOP = 36;

    /**
     * The field <tt>SUB</tt> contains the op code for the ocp instruction to
     * subtract two numbers from the stack.
     */
    public static final int SUB = 12;

    /**
     * The field <tt>TWELVE_BIT_MASK</tt> contains the bit mask with 12 bits.
     */
    private static final int TWELVE_BIT_MASK = 0xfff;

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
        int tableSpace = readWord(stream);
        int states = readWord(stream);
        int stateSpace = readWord(stream);

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
     * The field <tt>arithStack</tt> contains the stack for execution.
     */
    private Stack<Integer> arithStack = new Stack<Integer>();

    /**
     * The field <tt>first</tt> contains the ...
     */
    private int first = 0;

    /**
     * The field <tt>input</tt> contains the input parameter. The default is
     * 2.
     */
    private int input = 2;

    /**
     * The field <tt>last</tt> contains the ...
     */
    private int last = 0;

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
     * The field <tt>pc</tt> contains the program counter.
     */
    private int pc = 0;

    /**
     * The field <tt>state</tt> contains the current state.
     */
    private int state = 0;

    /**
     * The field <tt>states</tt> contains the states.
     */
    private List<int[]> states = new ArrayList<int[]>();

    /**
     * The field <tt>stateStack</tt> contains the stack of states.
     */
    private Stack<Integer> stateStack = new Stack<Integer>();

    /**
     * The field <tt>tables</tt> contains the tables.
     */
    private List<int[]> tables = new ArrayList<int[]>();

    /**
     * Creates a new object.
     * 
     */
    public OcpProgram() {

        super();
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
     */
    public void addTable(int[] t) {

        tables.add(t);
    }

    /**
     * Getter for input.
     * 
     * @return the input
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

        return length;
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
     * Reset the internal state of the program to the initial values.
     */
    public void reset() {

        pc = 0;
        state = 0;
        stateStack.clear();
        arithStack.clear();
    }

    /**
     * Start the execution.
     */
    public void run() {

        step(null, null, states.get(state));
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

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param in the input stream
     * @param out the output stream
     * @param ds the stack of states
     */
    private void step(PushbackInputStream in, OutputStream out, int[] ds) {

        int a;
        int b;
        int c;

        for (;;) {
            c = ds[pc++];

            switch (c >> 24) {
                case RIGHT_OUTPUT:
                    break;
                case RIGHT_NUM:
                    break;
                case RIGHT_CHAR:
                    break;
                case RIGHT_LCHAR:
                    break;
                case RIGHT_SOME:
                    // two = false;
                    break;
                case PBACK_OUTPUT:
                    break;
                case PBACK_NUM:
                    break;
                case PBACK_CHAR:
                    break;
                case PBACK_LCHAR:
                    break;
                case PBACK_SOME:
                    a = ds[pc++];
                    break;
                case ADD:
                    a = arithStack.pop().intValue();
                    b = arithStack.pop().intValue();
                    arithStack.push(new Integer(b + a));
                    break;
                case SUB:
                    a = arithStack.pop().intValue();
                    b = arithStack.pop().intValue();
                    arithStack.push(new Integer(b - a));
                    break;
                case MULT:
                    a = arithStack.pop().intValue();
                    b = arithStack.pop().intValue();
                    arithStack.push(new Integer(b * a));
                    break;
                case DIV:
                    a = arithStack.pop().intValue();
                    b = arithStack.pop().intValue();
                    arithStack.push(new Integer(b / a));
                    break;
                case MOD:
                    a = arithStack.pop().intValue();
                    b = arithStack.pop().intValue();
                    arithStack.push(new Integer(b % a));
                    break;
                case LOOKUP:
                    break;
                case PUSH_NUM:
                    break;
                case PUSH_CHAR:
                    break;
                case PUSH_LCHAR:
                    break;
                case STATE_CHANGE:
                    state = c & TWELVE_BIT_MASK;
                    break;
                case STATE_PUSH:
                    stateStack.push(new Integer(state));
                    state = c & TWELVE_BIT_MASK;
                    break;
                case STATE_POP:
                    break;
                case LEFT_START:
                    first = last + 1;
                    break;
                case LEFT_RETURN:
                    last = first + 1;
                    break;
                case LEFT_BACKUP:
                    last--; // ???
                    break;
                case GOTO:
                    pc = c & TWELVE_BIT_MASK;
                    break;
                case GOTO_NE:
                    a = ds[pc++];
                    if ((a & SIXTEEN_BIT_MASK) != (a >> 16)) {
                        pc = c & TWELVE_BIT_MASK;
                    }
                    break;
                case GOTO_EQ:
                    a = ds[pc++];
                    if ((a & SIXTEEN_BIT_MASK) == (a >> 16)) {
                        pc = c & TWELVE_BIT_MASK;
                    }
                    break;
                case GOTO_LT:
                    a = ds[pc++];
                    if ((a & SIXTEEN_BIT_MASK) < (a >> 16)) {
                        pc = c & TWELVE_BIT_MASK;
                    }
                    break;
                case GOTO_LE:
                    a = ds[pc++];
                    if ((a & SIXTEEN_BIT_MASK) <= (a >> 16)) {
                        pc = c & TWELVE_BIT_MASK;
                    }
                    break;
                case GOTO_GT:
                    a = ds[pc++];
                    if ((a & SIXTEEN_BIT_MASK) > (a >> 16)) {
                        pc = c & TWELVE_BIT_MASK;
                    }
                    break;
                case GOTO_GE:
                    a = ds[pc++];
                    if ((a & SIXTEEN_BIT_MASK) >= (a >> 16)) {
                        pc = c & TWELVE_BIT_MASK;
                    }
                    break;
                case GOTO_NO_ADVANCE:
                    break;
                case GOTO_BEG:
                    if (false) { // TODO at beginning
                        pc = c & TWELVE_BIT_MASK;
                    }
                    break;
                case GOTO_END:
                    if (false) { // TODO at end
                        pc = c & TWELVE_BIT_MASK;
                    }
                    break;
                case STOP:
                    return;
                default:
                    // TODO gene: unimplemented
                    throw new RuntimeException("unimplemented");
            }
        }
    }

}
