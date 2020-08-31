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
import java.util.ArrayList;
import java.util.List;

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.IllegalOpcodeException;
import org.extex.ocpware.type.OcpCode;

/**
 * The class state represents a state in the &Omega;CP engine for the compiler.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class State {

    /**
     * The field {@code instructions} contains the list of instruction words.
     */
    private final List<Integer> instructions = new ArrayList<Integer>();

    /**
     * The field {@code numberExpressions} contains the number of expressions
     * stored in this state.
     */
    private int numberExpressions = 0;


    public State() {

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

        if (numberExpressions == 0) {
            putInstruction(OcpCode.OP_LEFT_START);
        } else {
            putInstruction(OcpCode.OP_LEFT_RETURN);
        }
        putInstruction(OcpCode.OP_RIGHT_CHAR, 1);
        putInstruction(OcpCode.OP_STOP);
    }

    /**
     * Adjust some instructions by adding the current instruction pointer to
     * them. Usually goto instructions do not know the position in the
     * instructions to jump to when they are generated. Thus a hole is recorded.
     * These holes are filled in at some time later.
     * <p>
     * The conditional gotos contain the jump position in the second argument.
     * This is generated as 0. Adding the position fixes this hole.
     * </p>
     * <p>
     * The other gotos contain the jump position in the first argument. The
     * instruction contains the op code only. Adding the position fixes this
     * hole and leaves the op code intact.
     * </p>
     * 
     * @param holes the instructions to adjust
     */
    public void fillIn(List<Integer> holes) {

        int ptr = instructions.size();
        if (ptr > OcpCode.ARGUMENT_BIT_MASK) {
            throw new ArgmentTooBigException(ptr);
        }

        for (Integer in : holes) {
            int i = in.intValue();
            Integer value =
                    Integer.valueOf(instructions.get(i).intValue() + ptr);
            instructions.set(i, value);
        }
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
     * Getter for the number of expressions.
     * 
     * @return the number of expressions
     */
    public int getNumberExpressions() {

        return numberExpressions;
    }

    /**
     * Getter for the current pointer to the end of the code.
     * 
     * @return the current pointer to the end of the code
     */
    public int getPointer() {

        return instructions.size();
    }

    /**
     * Increment the number of expressions contained in this state.
     */
    public void incrExpressions() {

        numberExpressions++;
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

        if (OcpCode.get(opCode) == null) {
            throw new IllegalOpcodeException(opCode);
        }
        instructions.add(Integer.valueOf(opCode << OcpCode.OPCODE_OFFSET));
        return instructions.size();
    }

    /**
     * Put an instruction of one op code and one argument into the store.
     * 
     * @param opCode the op code
     * @param arg1 the first argument
     * 
     * @return the index of the next free position in the instruction array
     * 
     * @throws IOException in case of an I/O error
     * @throws ArgmentTooBigException if the argument of the instruction exceeds
     *         the 16 bit value
     * @throws IllegalOpcodeException in case of an illegal op code
     */
    public int putInstruction(int opCode, int arg1)
            throws ArgmentTooBigException,
                IOException,
                IllegalOpcodeException {

        if (OcpCode.get(opCode) == null) {
            throw new IllegalOpcodeException(opCode);
        }
        if (arg1 > OcpCode.ARGUMENT_BIT_MASK) {
            throw new ArgmentTooBigException(arg1);
        }
        instructions.add(Integer.valueOf((opCode << OcpCode.OPCODE_OFFSET)
                | arg1));
        return instructions.size();
    }

    /**
     * Put an instruction of one op code and two arguments into the store.
     * 
     * @param opCode the op code
     * @param arg1 the first argument
     * @param arg2 the second argument
     * 
     * @return the index of the next free position in the instruction array
     * 
     * @throws IOException in case of an I/O error
     * @throws ArgmentTooBigException if the argument of the instruction exceeds
     *         the 16 bit value
     * @throws IllegalOpcodeException in case of an illegal op code
     */
    public int putInstruction(int opCode, int arg1, int arg2)
            throws ArgmentTooBigException,
                IOException,
                IllegalOpcodeException {

        if (OcpCode.get(opCode) == null) {
            throw new IllegalOpcodeException(opCode);
        }
        if (arg1 > OcpCode.ARGUMENT_BIT_MASK) {
            throw new ArgmentTooBigException(arg1);
        }
        if (arg2 > OcpCode.ARGUMENT_BIT_MASK) {
            throw new ArgmentTooBigException(arg2);
        }
        instructions.add(Integer.valueOf((opCode << OcpCode.OPCODE_OFFSET)
                | arg1));
        instructions.add(Integer.valueOf(arg2));
        return instructions.size();
    }

}
