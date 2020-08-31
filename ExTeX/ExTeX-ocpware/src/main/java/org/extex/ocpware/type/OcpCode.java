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

package org.extex.ocpware.type;

/**
 * This enumeration provides information about the &Omega;CP instructions.
 * 
 * 
 * <p>The &Omega;CP Code Format</p>
 * <p>
 * A &Omega;CP Program consists of a set of instructions. The instructions are
 * stored in words of the length of 4 bytes (of 8 bits each). Each instruction
 * is determined by an op code of one byte. The instruction can have 0 to 2
 * arguments. Any argument is at most 3 bytes (24 bits) long.
 * </p>
 * <p>
 * An instruction without arguments has the following layout:
 * </p>
 * 
* <table>
 * <caption>TBD</caption>
 * <tr>
* <td style="width:6em; border-style:none; font-size:60%;">8 bit</td>
 * <td style="width:18em; border-style:none;; font-size:60%;">24 bit</td>
 * </tr>
 * <tr style="padding:0pt; spacing:0pt;">
 * <td style="border-color:gray; border-width:1pt; border-style:solid;">op code</td>
 * <td style="border-color:gray; border-width:1pt; border-style:solid;">unused</td>
 * </tr>
 * </table>
 * 
 * <p>
 * An instruction with one argument has the following layout:
 * </p>
 * 
* <table>
 * <caption>TBD</caption>
 * <tr>
* <td style="width:6em; border-style:none; font-size:60%;">8 bit</td>
 * <td style="width:18em; border-style:none;; font-size:60%;">24 bit</td>
 * </tr>
 * <tr style="padding:0pt; spacing:0pt;">
 * <td style="border-color:gray; border-width:1pt; border-style:solid;">op code</td>
 * <td style="border-color:gray; border-width:1pt; border-style:solid;">argument
 * 1</td>
 * </tr>
 * </table>
 * 
 * <p>
 * An instruction with two arguments has the following layout:
 * </p>
 * 
* <table>
 * <caption>TBD</caption>
 * <tr>
* <td style="width:6em; border-style:none; font-size:60%;">8 bit</td>
 * <td style="width:18em; border-style:none;; font-size:60%;">24 bit</td>
 * </tr>
 * <tr style="padding:0pt; spacing:0pt;">
 * <td style="border-color:gray; border-width:1pt; border-style:solid;">op code</td>
 * <td style="border-color:gray; border-width:1pt; border-style:solid;">argument
 * 1</td>
 * </tr>
 * <tr>
 * <td style="border-color:gray; border-width:1pt; border-style:solid;">unused</td>
 * <td style="border-color:gray; border-width:1pt; border-style:solid;">argument
 * 2</td>
 * </tr>
 * </table>
 * 
 * <p>The &Omega;CP Programming Model</p>
 * 
* <table>
 * <caption>TBD</caption>
 * <tr>
* <td>table[] </td>
 * <td>Tables </td>
 * </tr>
 * <tr>
 * <td>state[] </td>
 * <td>States </td>
 * </tr>
 * <tr>
 * <td>pc </td>
 * <td>Program counter </td>
 * </tr>
 * <tr>
 * <td>cs </td>
 * <td>Current state </td>
 * </tr>
 * <tr>
 * <td>first </td>
 * <td>The pointer to the first character of the prefix </td>
 * </tr>
 * <tr>
 * <td>last </td>
 * <td>The pointer to the last character of the prefix </td>
 * </tr>
 * <tr>
 * <td>stack[] </td>
 * <td>Stack </td>
 * </tr>
 * <tr>
 * <td>stateStack[] </td>
 * <td>State stack </td>
 * </tr>
 * </table>
 * 
 * The following instructions are available:
 * 
 * {@link OcpCode#OP_ADD ADD}, {@link OcpCode#OP_DIV DIV},
 * {@link OcpCode#OP_GOTO GOTO}, {@link OcpCode#OP_GOTO_BEG GOTO_BEG},
 * {@link OcpCode#OP_GOTO_END GOTO_END}, {@link OcpCode#OP_GOTO_EQ GOTO_EQ},
 * {@link OcpCode#OP_GOTO_GE GOTO_GE}, {@link OcpCode#OP_GOTO_GT GOTO_GT},
 * {@link OcpCode#OP_GOTO_LE GOTO_LE}, {@link OcpCode#OP_GOTO_LT GOTO_LT},
 * {@link OcpCode#OP_GOTO_NE GOTO_NE},
 * {@link OcpCode#OP_GOTO_NO_ADVANCE GOTO_NO_ADVANCE},
 * {@link OcpCode#OP_LEFT_BACKUP LEFT_BACKUP},
 * {@link OcpCode#OP_LEFT_RETURN LEFT_RETURN},
 * {@link OcpCode#OP_LEFT_START LEFT_START}, {@link OcpCode#OP_LOOKUP LOOKUP},
 * {@link OcpCode#OP_MOD MOD}, {@link OcpCode#OP_MULT MULT},
 * {@link OcpCode#OP_PBACK_CHAR PBACK_CHAR},
 * {@link OcpCode#OP_PBACK_LCHAR PBACK_LCHAR},
 * {@link OcpCode#OP_PBACK_NUM PBACK_NUM},
 * {@link OcpCode#OP_PBACK_OUTPUT PBACK_OUTPUT},
 * {@link OcpCode#OP_PBACK_SOME PBACK_SOME},
 * {@link OcpCode#OP_PUSH_CHAR PUSH_CHAR},
 * {@link OcpCode#OP_PUSH_LCHAR PUSH_LCHAR},
 * {@link OcpCode#OP_PUSH_NUM PUSH_NUM},
 * {@link OcpCode#OP_RIGHT_CHAR RIGHT_CHAR},
 * {@link OcpCode#OP_RIGHT_LCHAR RIGHT_LCHAR},
 * {@link OcpCode#OP_RIGHT_NUM RIGHT_NUM},
 * {@link OcpCode#OP_RIGHT_OUTPUT RIGHT_OUTPUT},
 * {@link OcpCode#OP_RIGHT_SOME RIGHT_SOME},
 * {@link OcpCode#OP_STATE_CHANGE STATE_CHANGE},
 * {@link OcpCode#OP_STATE_POP STATE_POP},
 * {@link OcpCode#OP_STATE_PUSH STATE_PUSH}, {@link OcpCode#OP_STOP STOP},
 * {@link OcpCode#OP_SUB SUB}.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public enum OcpCode {

    /**
     * The constant {@code RIGHT_OUTPUT} contains the description of the
     * &Omega;CP instruction to output the topmost number from the arithmetic
     * stack. This element is removed from the stack.
     */
    RIGHT_OUTPUT(1, "RIGHT_OUTPUT"),

    /**
     * The constant {@code RIGHT_NUM} contains the description of the
     * &Omega;CP instruction to output a constant character number. The constant
     * is passed in as argument.
     */
    RIGHT_NUM(2, "RIGHT_NUM", OcpArgumentType.CHARACTER),

    /**
     * The constant {@code RIGHT_CHAR} contains the description of the
     * &Omega;CP instruction to output a character from the right end of the
     * prefix.
     */
    RIGHT_CHAR(3, "RIGHT_CHAR", OcpArgumentType.NUMBER),

    /**
     * The constant {@code RIGHT_LCHAR} contains the description of the
     * &Omega;CP instruction to output a character from the left end of the
     * prefix.
     */
    RIGHT_LCHAR(4, "RIGHT_LCHAR", OcpArgumentType.NUMBER),

    /**
     * The constant {@code RIGHT_SOME} contains the description of the
     * &Omega;CP instruction to output some characters from the prefix.
     */
    RIGHT_SOME(5, "RIGHT_SOME", OcpArgumentType.NUMBER, OcpArgumentType.NUMBER),

    /**
     * The constant {@code PBACK_OUTPUT} contains the description of the
     * &Omega;CP instruction to push back the top value from the arithmetic
     * stack.
     */
    PBACK_OUTPUT(6, "PBACK_OUTPUT"),

    /**
     * The constant {@code PBACK_NUM} contains the description of the
     * &Omega;CP instruction to push back a constant taken from the argument.
     */
    PBACK_NUM(7, "PBACK_NUM", OcpArgumentType.CHARACTER),

    /**
     * The constant {@code PBACK_CHAR} contains the description of the
     * &Omega;CP instruction to push back a character from the right end of the
     * prefix.
     */
    PBACK_CHAR(8, "PBACK_CHAR", OcpArgumentType.NUMBER),

    /**
     * The constant {@code PBACK_LCHAR} contains the description of the
     * &Omega;CP instruction to push back a character from the left end of the
     * prefix.
     */
    PBACK_LCHAR(9, "PBACK_LCHAR", OcpArgumentType.NUMBER),

    /**
     * The constant {@code PBACK_SOME} contains the description of the
     * &Omega;CP instruction to push back some characters from the prefix.
     */
    PBACK_SOME(10, "PBACK_SOME", OcpArgumentType.NUMBER, OcpArgumentType.NUMBER),

    /**
     * The constant {@code ADD} contains the description of the &Omega;CP
     * instruction to add two numbers from the stack. The result is returned via
     * the stack.
     */
    ADD(11, "ADD"),

    /**
     * The constant {@code SUB} contains the description of the &Omega;CP
     * instruction to subtract two numbers from the stack. The result is
     * returned via the stack.
     */
    SUB(12, "SUB"),

    /**
     * The constant {@code MULT} contains the description of the &Omega;CP
     * instruction to multiply two numbers from the stack. The result is
     * returned via the stack.
     */
    MULT(13, "MULT"),

    /**
     * The constant {@code DIV} contains the description of the &Omega;CP
     * instruction to divide two numbers from the stack. The result is returned
     * via the stack.
     */
    DIV(14, "DIV"),

    /**
     * The constant {@code MOD} contains the description of the &Omega;CP
     * instruction to compute the remainder of two numbers from the stack. The
     * result is returned via the stack.
     */
    MOD(15, "MOD"),

    /**
     * The constant {@code LOOKUP} contains the description of the &Omega;CP
     * instruction to look-up a value from a table. The index and the table
     * number are passed in via the stack. The result is returned on the stack.
     */
    LOOKUP(16, "LOOKUP"),

    /**
     * The constant {@code PUSH_NUM} contains the description of the
     * &Omega;CP instruction to push a constant to the stack.
     */
    PUSH_NUM(17, "PUSH_NUM", OcpArgumentType.CHARACTER),

    /**
     * The constant {@code PUSH_CHAR} contains the description of the
     * &Omega;CP instruction to push a character from the prefix to the stack.
     * The argument passed in is the index of the character counted from the
     * right end.
     */
    PUSH_CHAR(18, "PUSH_CHAR", OcpArgumentType.NUMBER),

    /**
     * The constant {@code PUSH_LCHAR} contains the description of the
     * &Omega;CP instruction to push a character from the prefix to the stack.
     * The argument passed in is the index of the character counted from the
     * left end.
     */
    PUSH_LCHAR(19, "PUSH_LCHAR", OcpArgumentType.NUMBER),

    /**
     * The constant {@code STATE_CHANGE} contains the description of the
     * &Omega;CP instruction to set the current state to a given value. The
     * argument is the state to use.
     */
    STATE_CHANGE(20, "STATE_CHANGE", OcpArgumentType.STATE),

    /**
     * The constant {@code STATE_PUSH} contains the description of the
     * &Omega;CP instruction to push a state to the state stack.
     */
    STATE_PUSH(21, "STATE_PUSH", OcpArgumentType.STATE),

    /**
     * The constant {@code STATE_POP} contains the description of the
     * &Omega;CP instruction to pop the current state from the state stack.
     */
    STATE_POP(22, "STATE_POP"),

    /**
     * The constant {@code LEFT_START} contains the description of the
     * &Omega;CP instruction to set the first character position to last + 1.
     */
    LEFT_START(23, "LEFT_START"),

    /**
     * The constant {@code LEFT_RETURN} contains the description of the
     * &Omega;CP instruction to reset the last pointer to first - 1.
     */
    LEFT_RETURN(24, "LEFT_RETURN"),

    /**
     * The constant {@code LEFT_BACKUP} contains the description of the
     * &Omega;CP instruction to back up the last pointer by 1.
     */
    LEFT_BACKUP(25, "LEFT_BACKUP"),

    /**
     * The constant {@code GOTO} contains the description of the &Omega;CP
     * instruction to unconditionally branch to another instruction. The
     * argument is the number of the instruction to set the program counter to.
     */
    GOTO(26, "GOTO", OcpArgumentType.LABEL),

    /**
     * The constant {@code GOTO_NE} contains the description of the &Omega;CP
     * instruction to conditionally branch to another instruction. The first
     * argument is compared against the character pointed to by last and the
     * branching is performed if they are not equal. The second argument is the
     * number of the instruction to set the program counter to.
     */
    GOTO_NE(27, "GOTO_NE", OcpArgumentType.CHARACTER, OcpArgumentType.LABEL),

    /**
     * The constant {@code GOTO_EQ} contains the description of the &Omega;CP
     * instruction to conditionally branch to another instruction. The first
     * argument is compared against the character pointed to by last and the
     * branching is performed if they are equal. The second argument is the
     * number of the instruction to set the program counter to.
     */
    GOTO_EQ(28, "GOTO_EQ", OcpArgumentType.CHARACTER, OcpArgumentType.LABEL),

    /**
     * The constant {@code GOTO_LT} contains the description of the &Omega;CP
     * instruction to conditionally branch to another instruction. The first
     * argument is compared against the character pointed to by last and the
     * branching is performed if the last character is less than the argument.
     * The second argument is the number of the instruction to set the program
     * counter to.
     */
    GOTO_LT(29, "GOTO_LT", OcpArgumentType.CHARACTER, OcpArgumentType.LABEL),

    /**
     * The constant {@code GOTO_LE} contains the description of the &Omega;CP
     * instruction to conditionally branch to another instruction. The first
     * argument is compared against the character pointed to by last and the
     * branching is performed if the last character is less or equal than the
     * argument. The second argument is the number of the instruction to set the
     * program counter to.
     */
    GOTO_LE(30, "GOTO_LE", OcpArgumentType.CHARACTER, OcpArgumentType.LABEL),

    /**
     * The constant {@code GOTO_GT} contains the description of the &Omega;CP
     * instruction to conditionally branch to another instruction. The first
     * argument is compared against the character pointed to by last and the
     * branching is performed if the last character is greater than the
     * argument. The second argument is the number of the instruction to set the
     * program counter to.
     */
    GOTO_GT(31, "GOTO_GT", OcpArgumentType.CHARACTER, OcpArgumentType.LABEL),

    /**
     * The constant {@code GOTO_GE} contains the description of the &Omega;CP
     * instruction to conditionally branch to another instruction. The first
     * argument is compared against the character pointed to by last and the
     * branching is performed if the last character is greater or equal than the
     * argument. The second argument is the number of the instruction to set the
     * program counter to.
     */
    GOTO_GE(32, "GOTO_GE", OcpArgumentType.CHARACTER, OcpArgumentType.LABEL),

    /**
     * The constant {@code GOTO_NO_ADVANCE} contains the description of the
     * &Omega;CP instruction to conditionally branch to another instruction. The
     * value of last is incremented and the branching is performed if last has
     * reached the end of input. The argument is the number of the instruction
     * to set the program counter to.
     */
    GOTO_NO_ADVANCE(33, "GOTO_NO_ADVANCE", OcpArgumentType.LABEL),

    /**
     * The constant {@code GOTO_BEG} contains the description of the
     * &Omega;CP instruction to conditionally branch to another instruction. The
     * branching is performed at the beginning of input. The argument is the
     * number of the instruction to set the program counter to.
     */
    GOTO_BEG(34, "GOTO_BEG", OcpArgumentType.LABEL),

    /**
     * The constant {@code GOTO_END} contains the description of the
     * &Omega;CP instruction to conditionally branch to another instruction. The
     * branching is performed at the end of input. The argument is the number of
     * the instruction to set the program counter to.
     */
    GOTO_END(35, "GOTO_END", OcpArgumentType.LABEL),

    /**
     * The constant {@code STOP} contains the description of the &Omega;CP
     * instruction to stop the processing of the current prefix.
     */
    STOP(36, "STOP");

    /**
     * The constant {@code OP_ADD} contains the op code for the &Omega;CP
     * instruction to add two numbers from the stack.
     * 
     * @see #ADD
     */
    public static final int OP_ADD = 11;

    /**
     * The constant {@code OP_DIV} contains the op code for the &Omega;CP
     * instruction to divide two numbers from the stack.
     * 
     * @see #DIV
     */
    public static final int OP_DIV = 14;

    /**
     * The constant {@code OP_GOTO} contains the op code for the &Omega;CP
     * instruction to adjust the program counter.
     * 
     * @see #GOTO
     */
    public static final int OP_GOTO = 26;

    /**
     * The constant {@code OP_GOTO_BEG} contains the op code for the
     * &Omega;CP instruction to conditionally adjust the program counter at the
     * beginning of input.
     * 
     * @see #GOTO_BEG
     */
    public static final int OP_GOTO_BEG = 34;

    /**
     * The constant {@code OP_GOTO_END} contains the op code for the
     * &Omega;CP instruction to conditionally adjust the program counter at the
     * end of input.
     * 
     * @see #GOTO_END
     */
    public static final int OP_GOTO_END = 35;

    /**
     * The constant {@code OP_GOTO_EQ} contains the op code for the &Omega;CP
     * instruction conditionally adjust the program counter.
     * 
     * @see #GOTO_EQ
     */
    public static final int OP_GOTO_EQ = 28;

    /**
     * The constant {@code OP_GOTO_GE} contains the op code for the &Omega;CP
     * instruction conditionally adjust the program counter.
     * 
     * @see #GOTO_GE
     */
    public static final int OP_GOTO_GE = 32;

    /**
     * The constant {@code OP_GOTO_GT} contains the op code for the &Omega;CP
     * instruction to conditionally adjust the program counter.
     * 
     * @see #GOTO_GT
     */
    public static final int OP_GOTO_GT = 31;

    /**
     * The constant {@code OP_GOTO_LE} contains the op code for the &Omega;CP
     * instruction to conditionally adjust the program counter.
     * 
     * @see #GOTO_LE
     */
    public static final int OP_GOTO_LE = 30;

    /**
     * The constant {@code OP_GOTO_LT} contains the op code for the &Omega;CP
     * instruction to conditionally adjust the program counter.
     * 
     * @see #GOTO_LT
     */
    public static final int OP_GOTO_LT = 29;

    /**
     * The constant {@code OP_GOTO_NE} contains the op code for the &Omega;CP
     * instruction to conditionally adjust the program counter.
     * 
     * @see #GOTO_NE
     */
    public static final int OP_GOTO_NE = 27;

    /**
     * The constant {@code OP_GOTO_NO_ADVANCE} contains the op code for the
     * ocp instruction to conditionally adjust the program counter after
     * advancing last.
     * 
     * @see #GOTO_NO_ADVANCE
     */
    public static final int OP_GOTO_NO_ADVANCE = 33;

    /**
     * The constant {@code OP_LEFT_BACKUP} contains the op code for the
     * &Omega;CP instruction to decrement the last pointer by 1.
     * 
     * @see #LEFT_BACKUP
     */
    public static final int OP_LEFT_BACKUP = 25;

    /**
     * The constant {@code OP_LEFT_RETURN} contains the op code for the
     * &Omega;CP instruction to rest last to first - 1.
     * 
     * @see #LEFT_RETURN
     */
    public static final int OP_LEFT_RETURN = 24;

    /**
     * The constant {@code OP_LEFT_START} contains the op code for the
     * &Omega;CP instruction to set first to last + 1.
     * 
     * @see #LEFT_START
     */
    public static final int OP_LEFT_START = 23;

    /**
     * The constant {@code OP_LOOKUP} contains the op code for the &Omega;CP
     * instruction to lookup a table entry.
     * 
     * @see #LOOKUP
     */
    public static final int OP_LOOKUP = 16;

    /**
     * The constant {@code OP_MOD} contains the op code for the &Omega;CP
     * instruction to compute the remainder of two arguments from the stack.
     * 
     * @see #MOD
     */
    public static final int OP_MOD = 15;

    /**
     * The constant {@code OP_MULT} contains the op code for the &Omega;CP
     * instruction to multiply two arguments from the stack.
     * 
     * @see #MULT
     */
    public static final int OP_MULT = 13;

    /**
     * The constant {@code OP_PBACK_CHAR} contains the op code for the
     * &Omega;CP instruction to push back a character from the right end of the
     * prefix
     * 
     * @see #PBACK_CHAR
     */
    public static final int OP_PBACK_CHAR = 8;

    /**
     * The constant {@code OP_PBACK_LCHAR} contains the op code for the
     * &Omega;CP instruction to push back a character from the left end of the
     * prefix.
     * 
     * @see #PBACK_LCHAR
     */
    public static final int OP_PBACK_LCHAR = 9;

    /**
     * The constant {@code OP_PBACK_NUM} contains the op code for the
     * &Omega;CP instruction to push back a number.
     * 
     * @see #PBACK_NUM
     */
    public static final int OP_PBACK_NUM = 7;

    /**
     * The constant {@code OP_PBACK_OUTPUT} contains the op code for the
     * &Omega;CP instruction to push back a character from the arithmetic stack.
     * 
     * @see #PBACK_OUTPUT
     */
    public static final int OP_PBACK_OUTPUT = 6;

    /**
     * The constant {@code OP_PBACK_SOME} contains the op code for the
     * &Omega;CP instruction to push back a match.
     * 
     * @see #PBACK_SOME
     */
    public static final int OP_PBACK_SOME = 10;

    /**
     * The constant {@code OP_PUSH_CHAR} contains the op code for the
     * &Omega;CP instruction to push back a character.
     * 
     * @see #PUSH_CHAR
     */
    public static final int OP_PUSH_CHAR = 18;

    /**
     * The constant {@code OP_PUSH_LCHAR} contains the op code for the
     * &Omega;CP instruction to push a referenced character.
     * 
     * @see #PUSH_LCHAR
     */
    public static final int OP_PUSH_LCHAR = 19;

    /**
     * The constant {@code OP_PUSH_NUM} contains the op code for the
     * &Omega;CP instruction to push a number.
     * 
     * @see #PUSH_NUM
     */
    public static final int OP_PUSH_NUM = 17;

    /**
     * The constant {@code OP_RIGHT_CHAR} contains the op code for the
     * &Omega;CP instruction to output a character from the right end of the
     * prefix.
     * 
     * @see #RIGHT_CHAR
     */
    public static final int OP_RIGHT_CHAR = 3;

    /**
     * The constant {@code OP_RIGHT_LCHAR} contains the op code for the
     * &Omega;CP instruction to output a character from the left end of the
     * prefix.
     * 
     * @see #RIGHT_LCHAR
     */
    public static final int OP_RIGHT_LCHAR = 4;

    /**
     * The constant {@code OP_RIGHT_NUM} contains the op code for the
     * &Omega;CP instruction to output a constant.
     * 
     * @see #RIGHT_NUM
     */
    public static final int OP_RIGHT_NUM = 2;

    /**
     * The constant {@code OP_RIGHT_OUTPUT} contains the op code for the
     * &Omega;CP instruction to pop a value off the arithmetic stack and output
     * it.
     * 
     * @see #RIGHT_OUTPUT
     */
    public static final int OP_RIGHT_OUTPUT = 1;

    /**
     * The constant {@code OP_RIGHT_SOME} contains the op code for the
     * &Omega;CP instruction to output some characters of the prefix.
     * 
     * @see #RIGHT_SOME
     */
    public static final int OP_RIGHT_SOME = 5;

    /**
     * The constant {@code OP_STATE_CHANGE} contains the op code for the
     * &Omega;CP instruction to set a new state.
     * 
     * @see #STATE_CHANGE
     */
    public static final int OP_STATE_CHANGE = 20;

    /**
     * The constant {@code OP_STATE_POP} contains the op code for the
     * &Omega;CP instruction to pop a state from the state stack.
     * 
     * @see #STATE_POP
     */
    public static final int OP_STATE_POP = 22;

    /**
     * The constant {@code OP_STATE_PUSH} contains the op code for the
     * &Omega;CP instruction to push a state onto the state stack.
     * 
     * @see #STATE_PUSH
     */
    public static final int OP_STATE_PUSH = 21;

    /**
     * The constant {@code OP_STOP} contains the op code for the &Omega;CP
     * instruction to end the processing.
     * 
     * @see #STOP
     */
    public static final int OP_STOP = 36;

    /**
     * The constant {@code OP_SUB} contains the op code for the &Omega;CP
     * instruction to subtract two numbers from the stack.
     * 
     * @see #SUB
     */
    public static final int OP_SUB = 12;

    /**
     * The constant {@code ARGUMENT_BIT_MASK} contains the bit mask for the
     * lower 24 bits used as arguments in the &Omega;CP engine.
     */
    public static final int ARGUMENT_BIT_MASK = 0xffffff;

    /**
     * The constant {@code OPCODE_OFFSET} contains the offset for the op
     * code.
     */
    public static final int OPCODE_OFFSET = 24;

    /**
     * The constant {@code a} contains the list of ocp codes.
     */
    private static final OcpCode[] OCP_CODES =
            {RIGHT_OUTPUT, RIGHT_NUM, RIGHT_CHAR, RIGHT_LCHAR, RIGHT_SOME,
                    PBACK_OUTPUT, PBACK_NUM, PBACK_CHAR, PBACK_LCHAR,
                    PBACK_SOME, ADD, SUB, MULT, DIV, MOD, LOOKUP, PUSH_NUM,
                    PUSH_CHAR, PUSH_LCHAR, STATE_CHANGE, STATE_PUSH, STATE_POP,
                    LEFT_START, LEFT_RETURN, LEFT_BACKUP, GOTO, GOTO_NE,
                    GOTO_EQ, GOTO_LT, GOTO_LE, GOTO_GT, GOTO_GE,
                    GOTO_NO_ADVANCE, GOTO_BEG, GOTO_END, STOP};

    /**
     * Get the OcpCode for a given op code.
     * 
     * @param opcode the op code
     * 
     * @return the op code or {@code null} for an unknown op code
     */
    public static OcpCode get(int opcode) {

        return opcode > 0 && opcode <= OCP_CODES.length
                ? OCP_CODES[opcode - 1]
                : null;
    }

    /**
     * The field {@code arguments} contains the descriptions of the
     * arguments.
     */
    private final OcpArgumentType[] arguments;

    /**
     * The field {@code opcode} contains the op code.
     */
    private final int opcode;

    /**
     * The field {@code instruction} contains the name of the instruction.
     */
    private final String instruction;

    /**
     * Creates a new object.
     * 
     * @param opcode the op code
     * @param instruction the name of the instruction
     * @param arguments the specification of the arguments
     */
    OcpCode(int opcode, String instruction, OcpArgumentType... arguments) {

        this.instruction = instruction;
        this.arguments = arguments;
        this.opcode = opcode;
    }

    /**
     * Getter for the description of the arguments of the instruction. The
     * description is a list of the following bytes:
     * <dl>
     * <dd>n</dd>
     * <dt>The corresponding argument is a number.</dt>
     * <dd>c</dd>
     * <dt>The corresponding argument is a character code.</dt>
     * <dd>l</dd>
     * <dt>The corresponding argument is a label, i.e. a reference to a code
     * position.</dt>
     * <dd>s</dd>
     * <dt>The corresponding argument is a state, i.e. a reference to a state
     * number.</dt>
     * </dl>
     * 
     * @return the args
     */
    public OcpArgumentType[] getArguments() {

        return arguments;
    }

    /**
     * Getter for opcode.
     * 
     * @return the opcode
     */
    public int getOpcode() {

        return opcode;
    }

    /**
     * Getter for instruction.
     * 
     * @return the instruction
     */
    public String getInstruction() {

        return instruction;
    }

    /**
     * Get the first word of the instruction withput argument.
     *
     * @return the instruction without argument
     */
    public int inst() {

        return opcode << OPCODE_OFFSET;
    }

    /**
     * Get the first word of the instruction with one argument.
     *
     * @param arg the argument
     *
     * @return the instruction with one argument
     */
    public int inst(int arg) {

        return (opcode << OPCODE_OFFSET) | arg;
    }

}
