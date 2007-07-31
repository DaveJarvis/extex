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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a compiled omega program.
 * 
 * 
 * <h3>The &Omega;CP File Format</h3>
 * 
 * <h3>The &Omega;CP Programming Model</h3>
 * 
 * <table style="borderstyle:solid;bordercolor:black;borderwidth:1pt">
 * <tr>
 * <td>Tables </td>
 * <td>table[] </td>
 * </tr>
 * <tr>
 * <td>States </td>
 * <td>state[] </td>
 * </tr>
 * <tr>
 * <td>Program counter </td>
 * <td>pc </td>
 * </tr>
 * <tr>
 * <td>Stack </td>
 * <td>stack[] </td>
 * </tr>
 * <tr>
 * <td>State stack </td>
 * <td>stateStack[] </td>
 * </tr>
 * </table>
 * 
 * The following instructions are available:
 * 
 * {@link #ADD ADD}, {@link #DIV DIV}, {@link #GOTO GOTO}, {@link #GOTO_BEG
 * GOTO_BEG}, {@link #GOTO_END GOTO_END}, {@link #GOTO_EQ GOTO_EQ},
 * {@link #GOTO_GE GOTO_GE}, {@link #GOTO_GT GOTO_GT}, {@link #GOTO_LE GOTO_LE},
 * {@link #GOTO_LT GOTO_LT}, {@link #GOTO_NE GOTO_NE},
 * {@link #GOTO_NO_ADVANCE GOTO_NO_ADVANCE}, {@link #LEFT_BACKUP LEFT_BACKUP},
 * {@link #LEFT_RETURN LEFT_RETURN}, {@link #LEFT_START LEFT_START},
 * {@link #LOOKUP LOOKUP}, {@link #MOD MOD}, {@link #MULT MULT},
 * {@link #PBACK_CHAR PBACK_CHAR}, {@link #PBACK_LCHAR PBACK_LCHAR}, {@link
 * #PBACK_NUM PBACK_NUM}, {@link #PBACK_OUTPUT PBACK_OUTPUT},
 * {@link #PBACK_SOME PBACK_SOME}, {@link #PUSH_CHAR PUSH_CHAR},
 * {@link #PUSH_LCHAR PUSH_LCHAR}, {@link #PUSH_NUM PUSH_NUM},
 * {@link #RIGHT_CHAR RIGHT_CHAR}, {@link #RIGHT_LCHAR RIGHT_LCHAR},
 * {@link #RIGHT_NUM RIGHT_NUM}, {@link #RIGHT_OUTPUT RIGHT_OUTPUT},
 * {@link #RIGHT_SOME RIGHT_SOME}, {@link #STATE_CHANGE STATE_CHANGE}, {@link
 * #STATE_POP STATE_POP}, {@link #STATE_PUSH STATE_PUSH}, {@link #STOP STOP},
 * {@link #SUB SUB}.
 * 
 * 
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
     * instruction to conditionally adjust the program counter at the beginning
     * of input.
     */
    public static final int GOTO_BEG = 34;

    /**
     * The field <tt>GOTO_END</tt> contains the op code for the ocp
     * instruction to conditionally adjust the program counter at the end of
     * input.
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
     * to conditionally adjust the program counter.
     */
    public static final int GOTO_GT = 31;

    /**
     * The field <tt>GOTO_LE</tt> contains the op code for the ocp instruction
     * to conditionally adjust the program counter.
     */
    public static final int GOTO_LE = 30;

    /**
     * The field <tt>GOTO_LT</tt> contains the op code for the ocp instruction
     * to conditionally adjust the program counter.
     */
    public static final int GOTO_LT = 29;

    /**
     * The field <tt>GOTO_NE</tt> contains the op code for the ocp instruction
     * to conditionally adjust the program counter.
     */
    public static final int GOTO_NE = 27;

    /**
     * The field <tt>GOTO_NO_ADVANCE</tt> contains the op code for the ocp
     * instruction to conditionally adjust the program counter after advancing
     * last.
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
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>STATE_CHANGE</tt> contains the op code for the ocp
     * instruction to set a new state.
     */
    public static final int STATE_CHANGE = 20;

    /**
     * The field <tt>STATE_POP</tt> contains the op code for the ocp
     * instruction to pop a state from the state stack.
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

    /**
     * Creates a new object.
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
