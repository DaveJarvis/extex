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
import java.util.ArrayList;
import java.util.List;

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.IllegalOpcodeException;
import org.extex.ocpware.type.OcpProgram;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class State {

    /**
     * The field <tt>instructions</tt> contains the list of instruction words.
     */
    private List<Integer> instructions = new ArrayList<Integer>();

    /**
     * The field <tt>numExpr</tt> contains the number of expressions.
     */
    private int numExpr = 0;

    /**
     * Creates a new object.
     */
    public State() {

        super();
    }

    /**
     * Close the state by adding some final instructions.
     * 
     * @throws IOException in case of an I/O error
     * @throws ArgmentTooBigException if the argument of the instruction exceeds
     *         the 16 bit value
     * @throws IllegalOpcodeException in case of an illegal op code
     */
    public void close()
            throws ArgmentTooBigException,
                IOException,
                IllegalOpcodeException {

        if (numExpr == 0) {
            putInstruction(OcpProgram.LEFT_START);
        } else {
            putInstruction(OcpProgram.LEFT_RETURN);
        }
        putInstruction(OcpProgram.RIGHT_CHAR, 1);
        putInstruction(OcpProgram.STOP);
    }

    public int getPointer() {
        
        return instructions.size();
    }

    /**
     * Getter for the array of instructions.
     * 
     * @return the array of instructions
     */
    public int[] getInstructions() {

        int[] ia = new int[instructions.size()];
        int i = 0;

        for (Integer v : instructions) {
            ia[i++] = v.intValue();
        }
        return ia;
    }

    /**
     * Getter for numExpr.
     * 
     * @return the numExpr
     */
    public int getNumExpr() {

        return numExpr;
    }

    /**
     * Increment the number of expressions contained.
     */
    public void incrExpr() {

        numExpr++;
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
    public int putInstruction(int opCode)
            throws IOException,
                IllegalOpcodeException {

        if (opCode < 1 || opCode > 36) {
            throw new IllegalOpcodeException(opCode);
        }
        instructions.add(Integer.valueOf(opCode << 24));
        return instructions.size();
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
            throws ArgmentTooBigException,
                IOException,
                IllegalOpcodeException {

        if (opCode < 1 || opCode > 36) {
            throw new IllegalOpcodeException(opCode);
        }
        if (n > 0xffffff) {
            throw new ArgmentTooBigException(n);
        }
        instructions.add(Integer.valueOf((opCode << 24) | n));
        return instructions.size();
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
            throws ArgmentTooBigException,
                IOException,
                IllegalOpcodeException {

        if (opCode < 1 || opCode > 36) {
            throw new IllegalOpcodeException(opCode);
        }
        if (n > 0xffffff) {
            throw new ArgmentTooBigException(n);
        }
        if (a > 0xffffff) {
            throw new ArgmentTooBigException(a);
        }
        instructions.add(Integer.valueOf((opCode << 24) | n));
        instructions.add(Integer.valueOf(a));
        return instructions.size();
    }

    /**
     * Adjust some instructions by adding the current pointer to them.
     * 
     * @param holes the instructions to adjust
     */
    public void fillIn(List<Integer> holes) {

        int ptr = instructions.size();
        for (Integer in : holes) {
            int i = in.intValue();
            int x = instructions.get(i).intValue();
            instructions.set(i, Integer.valueOf(x + ptr));
        }
    }

}
