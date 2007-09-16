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

package org.extex.ocpware.engine;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import org.extex.ocpware.type.OcpCode;
import org.extex.ocpware.type.OcpProgram;

/**
 * This reader applies an &Omega;CP program to an input stream.
 * 
 * <dl>
 * <dt>Note:</dt>
 * <dd>This class used buffers which are extended dynamically but not reduced.
 * This is a potential memory leak.</dd>
 * </dl>
 * 
 * @see OcpCode
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpReader extends Reader {

    /**
     * The field <tt>ARITH_STACK_INCREMENT</tt> contains the increment for the
     * arithmetic stack if the current stack is full.
     */
    private static final int ARITH_STACK_INCREMENT = 32;

    /**
     * The constant <tt>ARITH_STACK_SIZE</tt> contains the initial size of the
     * arithmetic stack.
     */
    private static final int ARITH_STACK_SIZE = 32;

    /**
     * The constant <tt>LINE_SIZE_INCREMENT</tt> contains the increment of the
     * size of the line buffer. This value has to be a positive number.
     */
    private static final int LINE_SIZE_INCREMENT = 128;

    /**
     * The constant <tt>LINE_INITIAL_SIZE</tt> contains the initial size of
     * the line buffer. The value must not be negative. The line buffer may be
     * expanded when necessary.
     */
    private static final int LINE_INITIAL_SIZE = 128;

    /**
     * The field <tt>arithStack</tt> contains the stack for execution.
     */
    private int[] arithStack = new int[ARITH_STACK_SIZE];

    /**
     * The field <tt>arithStackPtr</tt> contains the pointer to the first free
     * item on the arithmetic stack.
     */
    private int arithStackPtr = 0;

    /**
     * The field <tt>code</tt> contains the code currently run.
     */
    private int[] code;

    /**
     * The field <tt>last</tt> contains the index of the last character of the
     * prefix matched.
     */
    private int inputLast = 0;

    /**
     * The field <tt>start</tt> contains the index of the first character of
     * the prefix matched.
     */
    private int inputStart = 0;

    /**
     * The field <tt>line</tt> contains the intermediate buffer for the
     * characters read. The buffer will be extended dynamically if required.
     */
    private char[] line = new char[LINE_INITIAL_SIZE];

    /**
     * The field <tt>lineEnd</tt> contains the index of the character
     * following the last one used in the line buffer.
     */
    private int lineEnd = 0;

    /**
     * The field <tt>observers</tt> contains the list of observers.
     */
    private List<OcpReaderObserver> observers = null;

    /**
     * The field <tt>outMax</tt> contains the maximum number requested for an
     * output character.
     */
    private int outMax;

    /**
     * The field <tt>pc</tt> contains the program counter. It must be in the
     * range from 0 to <tt>code.length</tt>.
     */
    private int pc = 0;

    /**
     * The field <tt>program</tt> contains the reference to the entire
     * program.
     */
    private OcpProgram program;

    /**
     * The field <tt>pushbackBuffer</tt> contains the buffer for push-back
     * characters. The first character will never be read and processed but used
     * to determine the beginning of the line.
     */
    private char[] pushbackBuffer = new char[LINE_INITIAL_SIZE];

    /**
     * The field <tt>pushbackEnd</tt> contains the index of the first free
     * position in the push-back buffer.
     */
    private int pushbackEnd = 0;

    /**
     * The field <tt>reader</tt> contains the reader to acquire input from.
     */
    private Reader reader;

    /**
     * The field <tt>bufferEnd</tt> contains the pointer to the end of the
     * input buffer.
     */
    private int someEnd = -1;

    /**
     * The field <tt>somePtr</tt> contains the pointer for delivering <i>some</i>
     * values. If it is less then someEnd then the processing is not continued
     * but a character is delivered from the line position pointed to in this
     * field.
     */
    private int somePtr = 0;

    /**
     * The field <tt>state</tt> contains the current state.
     */
    private int state = 0;

    /**
     * The field <tt>stateStack</tt> contains the stack of states.
     */
    private Stack<Integer> stateStack = new Stack<Integer>();

    /**
     * Creates a new object.
     * 
     * @param reader the reader for input characters. This is not allowed to be
     *        <code>null</code>.
     * @param program the program code to run. This is not allowed to be
     *        <code>null</code>.
     * 
     * @throws IllegalArgumentException in case that one of the arguments is
     *         <code>null</code>
     * @throws UnsupportedOutputException in case of an unknown output size is
     *         encountered
     */
    public OcpReader(Reader reader, OcpProgram program)
            throws IllegalArgumentException,
                UnsupportedOutputException {

        super();
        if (reader == null) {
            throw new IllegalArgumentException("reader");
        }
        if (program == null) {
            throw new IllegalArgumentException("program");
        }
        this.reader = reader;
        this.program = program;
        this.code = program.getCode(state);

        switch (program.getOutput()) {
            case 1:
                outMax = 0xff;
                break;
            case 2:
                outMax = 0xffff;
                break;
            default:
                throw new UnsupportedOutputException(program.getOutput());
        }
    }

    /**
     * Pop a value from the arithmetic stack.
     * 
     * @return the value popped
     * 
     * @throws OcpEmptyStackException in case that the stack is empty
     */
    private int arithPop() throws OcpEmptyStackException {

        if (arithStackPtr == 0) {
            throw new OcpEmptyStackException();
        }
        return arithStack[--arithStackPtr];
    }

    /**
     * Push a value to the arithmetic stack.
     * 
     * @param x the value
     */
    private void arithPush(int x) {

        if (arithStackPtr >= arithStack.length) {
            int[] newStack = new int[arithStack.length + ARITH_STACK_INCREMENT];
            System.arraycopy(arithStack, 0, newStack, 0, arithStack.length);
            arithStack = newStack;
        }
        arithStack[arithStackPtr++] = x;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#close()
     */
    @Override
    public void close() throws IOException {

        if (reader == null) {
            return;
        }

        if (observers != null) {
            for (OcpReaderObserver observer : observers) {
                observer.close(this);
            }
        }
        reader.close();
        reader = null;
    }

    /**
     * Get a character from the right end of the prefix.
     * 
     * @param c the current code word
     * 
     * @return the char
     * 
     * @throws OcpLineUnderflowException in case of a illegal reference
     * @throws OcpLineOverflowException in case of a illegal reference
     */
    private char determineChar(int c)
            throws OcpLineUnderflowException,
                OcpLineOverflowException {

        int arg = inputStart + (c & OcpCode.ARGUMENT_BIT_MASK) - 1;

        if (arg < inputStart) {
            throw new OcpLineUnderflowException();
        }
        if (arg >= lineEnd) {
            throw new OcpLineOverflowException();
        }
        return line[arg];
    }

    /**
     * Get a character from the left end of the prefix.
     * 
     * @param c the current code word
     * 
     * @return the lchar
     * 
     * @throws OcpLineUnderflowException in case of a illegal reference
     */
    private char determineLchar(int c) throws OcpLineUnderflowException {

        int arg = inputLast - (c & OcpCode.ARGUMENT_BIT_MASK) - 1;
        if (arg < inputStart) {
            throw new OcpLineUnderflowException();
        }
        return line[arg];
    }

    /**
     * Gets some more characters into the input buffer.
     * 
     * @return <code>true</code> when end of file is reached
     * 
     * @throws IOException in case of an I/O error
     */
    private boolean fillInLineCharacter() throws IOException {

        if (reader == null) {
            return true;
        }
        int c = reader.read();
        if (c < 0) {
            return true;
        }
        if (lineEnd >= line.length) {
            char[] newLine = new char[line.length + LINE_SIZE_INCREMENT];
            System.arraycopy(line, 0, newLine, 0, line.length);
            line = newLine;
        }
        line[lineEnd++] = (char) c;
        return false;
    }

    /**
     * Getter for arithStack.
     * 
     * @return the arithStack
     */
    public int[] getArithStack() {

        return arithStack;
    }

    /**
     * Getter for the next code word. The word returned is one past the pc. This
     * is useful to access the second argument of a 2-argument instruction. The
     * pc is not incremented.
     * 
     * @return the code word
     * 
     * @throws ArrayIndexOutOfBoundsException in case that the requested word
     *         does not exist
     */
    public int getCodeWord() throws ArrayIndexOutOfBoundsException {

        return code[pc + 1];
    }

    /**
     * Getter for last.
     * 
     * @return the last
     */
    public int getLast() {

        return inputLast;
    }

    /**
     * Get the line of characters already read.
     * 
     * @return the currently processed line as String
     */
    public String getLine() {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lineEnd; i++) {
            sb.append(line[i]);
        }
        return sb.toString();
    }

    /**
     * Getter for the program counter.
     * 
     * @return the pc
     */
    public int getPc() {

        return pc;
    }

    /**
     * Getter for program.
     * 
     * @return the program
     */
    public OcpProgram getProgram() {

        return program;
    }

    /**
     * Getter for someEnd.
     * 
     * @return the someEnd
     */
    public int getSomeEnd() {

        return someEnd;
    }

    /**
     * Getter for somePtr.
     * 
     * @return the somePtr
     */
    public int getSomePtr() {

        return somePtr;
    }

    /**
     * Getter for start.
     * 
     * @return the start
     */
    public int getStart() {

        return inputStart;
    }

    /**
     * Getter for the current state.
     * 
     * @return the state
     */
    public int getState() {

        return state;
    }

    /**
     * Add a character to the push-back list.
     * 
     * @param value the value
     */
    private void pushBack(char value) {

        if (pushbackEnd >= pushbackBuffer.length) {
            char[] b = new char[pushbackBuffer.length //
                    + LINE_SIZE_INCREMENT];
            System.arraycopy(pushbackBuffer, 0, b, 0, pushbackBuffer.length);
            pushbackBuffer = b;
        }
        pushbackBuffer[pushbackEnd++] = value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#read()
     * 
     * @throws OcpEmptyStackException in case of an empty stack
     * @throws IllegalPcException if the program counter points outside the code
     *         area
     */
    @Override
    public int read()
            throws IOException,
                OcpEmptyStackException,
                IllegalPcException {

        if (somePtr <= someEnd) {
            return line[somePtr++];
        }

        int c;

        try {
            c = step();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalPcException(pc);
        }

        if (c >= outMax) {
            throw new CharacterOutOfRangeException(c);
        }
        return c;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#read(char[], int, int)
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {

        int i = off;
        int n = 0;
        do {
            while (somePtr <= someEnd) {
                cbuf[i++] = line[somePtr++];
                if (++n >= len) {
                    return n;
                }
            }
            int c = read();
            if (c < 0) {
                return n;
            }
            cbuf[i++] = (char) c;
            n++;
        } while (n < len);

        return n;
    }

    /**
     * Register an observer to be informed about certain events.
     * 
     * @param observer the observer to register
     */
    public void register(OcpReaderObserver observer) {

        if (observers == null) {
            observers = new ArrayList<OcpReaderObserver>();
        }
        observers.add(observer);
    }

    /**
     * Perform steps of the engine until an output character is encountered or
     * the end of input is reached.
     * 
     * @return the output character or -1 on EOF
     * 
     * @throws IllegalTableItemException in case of a reference to a table item
     *         outside the allowed range
     * @throws IllegalTableException in case of a reference to a not existing
     *         table
     * @throws OcpLineOverflowException in case to a reference after the end of
     *         the line
     * @throws OcpLineUnderflowException in case to a reference before the
     *         beginning of the line
     * @throws IllegalOpCodeException if an unknown op code is encountered
     * @throws IOException in case of an I/O error
     */
    private int step()
            throws IllegalTableItemException,
                IllegalTableException,
                IOException,
                OcpLineOverflowException,
                OcpLineUnderflowException,
                IllegalOpCodeException {

        int a;

        for (;;) {
            int c = code[pc];
            int opcode = c >> OcpCode.OPCODE_OFFSET;

            if (observers != null) {
                int arg = c & OcpCode.ARGUMENT_BIT_MASK;
                for (OcpReaderObserver observer : observers) {
                    observer.step(this, opcode, arg);
                }
            }
            pc++;

            switch (opcode) {
                case OcpCode.OP_RIGHT_OUTPUT:
                    return arithPop();

                case OcpCode.OP_RIGHT_NUM:
                    return c & OcpCode.ARGUMENT_BIT_MASK;

                case OcpCode.OP_RIGHT_CHAR:
                    return determineChar(c);

                case OcpCode.OP_RIGHT_LCHAR:
                    return determineLchar(c);

                case OcpCode.OP_RIGHT_SOME:
                    somePtr = inputStart //
                            + (c & OcpCode.ARGUMENT_BIT_MASK);
                    someEnd = inputStart //
                            + (code[pc++] & OcpCode.ARGUMENT_BIT_MASK);
                    if (somePtr <= someEnd) {
                        if (someEnd > lineEnd) {
                            throw new OcpLineOverflowException();
                        }
                        return line[somePtr++];
                    }
                    break;

                case OcpCode.OP_PBACK_OUTPUT:
                    a = arithPop();
                    if (a < 0 || a > 0xffff) {
                        throw new CharacterOutOfRangeException(a);
                    }
                    pushBack((char) a);
                    break;

                case OcpCode.OP_PBACK_NUM:
                    a = c & OcpCode.ARGUMENT_BIT_MASK;
                    if (a < 0 || a > 0xffff) {
                        throw new CharacterOutOfRangeException(a);
                    }
                    pushBack((char) a);
                    break;

                case OcpCode.OP_PBACK_CHAR:
                    pushBack(determineChar(c));
                    break;

                case OcpCode.OP_PBACK_LCHAR:
                    pushBack(determineLchar(c));
                    break;

                case OcpCode.OP_PBACK_SOME:
                    a = inputLast - (code[pc++] & OcpCode.ARGUMENT_BIT_MASK);

                    for (c = inputStart + (c & OcpCode.ARGUMENT_BIT_MASK); c < a; c++) {
                        pushBack(line[c]);
                    }
                    break;

                case OcpCode.OP_ADD:
                    a = arithPop();
                    arithPush(arithPop() + a);
                    break;

                case OcpCode.OP_SUB:
                    a = arithPop();
                    arithPush(arithPop() - a);
                    break;

                case OcpCode.OP_MULT:
                    a = arithPop();
                    arithPush(arithPop() * a);
                    break;

                case OcpCode.OP_DIV:
                    a = arithPop();
                    arithPush(arithPop() / a);
                    break;

                case OcpCode.OP_MOD:
                    a = arithPop();
                    arithPush(arithPop() % a);
                    break;

                case OcpCode.OP_LOOKUP:
                    a = arithPop();
                    try {
                        arithPush(program.getTable(arithPop())[a]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalTableItemException(a);
                    } catch (IndexOutOfBoundsException e) {
                        throw new IllegalTableException(c);
                    }
                    break;

                case OcpCode.OP_PUSH_NUM:
                    arithPush(c & OcpCode.ARGUMENT_BIT_MASK);
                    break;

                case OcpCode.OP_PUSH_CHAR:
                    arithPush(determineChar(c));
                    break;

                case OcpCode.OP_PUSH_LCHAR:
                    arithPush(determineLchar(c));
                    break;

                case OcpCode.OP_STATE_CHANGE:
                    inputStart = inputLast + 1;
                    state = c & OcpCode.ARGUMENT_BIT_MASK;
                    try {
                        code = program.getCode(state);
                    } catch (IndexOutOfBoundsException e) {
                        throw new IllegalStateException(state);
                    }
                    pc = 0;
                    break;

                case OcpCode.OP_STATE_PUSH:
                    inputStart = inputLast;
                    stateStack.push(Integer.valueOf(state));
                    state = c & OcpCode.ARGUMENT_BIT_MASK;
                    try {
                        code = program.getCode(state);
                    } catch (IndexOutOfBoundsException e) {
                        throw new IllegalStateException(state);
                    }
                    pc = 0;
                    break;

                case OcpCode.OP_STATE_POP:
                    inputStart = inputLast;
                    if (inputStart >= lineEnd && fillInLineCharacter()) {
                        pc--;
                        return -1;
                    }
                    try {
                        state = stateStack.pop().intValue();
                    } catch (EmptyStackException e) {
                        throw new OcpEmptyStackException();
                    }
                    try {
                        code = program.getCode(state);
                    } catch (IndexOutOfBoundsException e) {
                        // this should not happen since the state has been
                        // active before
                        throw new IllegalStateException(state);
                    }
                    pc = 0;
                    break;

                case OcpCode.OP_LEFT_START:
                    inputStart = inputLast;
                    if (inputStart >= lineEnd && fillInLineCharacter()) {
                        pc--;
                        return -1;
                    }
                    inputLast++;
                    break;

                case OcpCode.OP_LEFT_RETURN:
                    inputLast = inputStart;
                    inputLast++;
                    break;

                case OcpCode.OP_LEFT_BACKUP:
                    inputLast--;
                    if (inputLast < inputStart) {
                        throw new OcpLineUnderflowException();
                    }
                    break;

                case OcpCode.OP_GOTO:
                    pc = c & OcpCode.ARGUMENT_BIT_MASK;
                    break;

                case OcpCode.OP_GOTO_NE:
                    a = code[pc++];

                    if (inputLast >= inputStart
                            && line[inputLast - 1] != (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_EQ:
                    a = code[pc++];
                    if (inputLast >= inputStart
                            && line[inputLast - 1] == (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_LT:
                    a = code[pc++];
                    if (inputLast >= inputStart
                            && line[inputLast - 1] < (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_LE:
                    a = code[pc++];
                    if (inputLast >= inputStart
                            && line[inputLast - 1] <= (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_GT:
                    a = code[pc++];
                    if (inputLast >= inputStart
                            && line[inputLast - 1] > (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_GE:
                    a = code[pc++];
                    if (inputLast >= inputStart
                            && line[inputLast - 1] >= (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_NO_ADVANCE:
                    if ((inputLast >= lineEnd && fillInLineCharacter())
                            || line[inputLast++] == '\n') {
                        pc = c & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_BEG:
                    if (inputLast == 1
                            || (inputLast >= 1 && line[inputLast - 1] == '\n')) {
                        pc = c & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_END:
                    if (inputLast >= lineEnd && fillInLineCharacter()) {
                        pc = c & OcpCode.ARGUMENT_BIT_MASK;
                    } else if (line[inputLast] == '\n') {
                        pc = c & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_STOP:
                    inputStart = inputLast;

                    if (pushbackEnd > 0) {
                        while (inputStart < lineEnd) {
                            pushBack(line[inputStart++]);
                        }
                        char[] save = line;
                        line = pushbackBuffer;
                        lineEnd = pushbackEnd;
                        pushbackBuffer = save;
                        pushbackEnd = 0;
                        inputStart = 0;
                        inputLast = 0;
                    } else if (line[inputLast - 1] == '\n') {
                        lineEnd -= inputLast;
                        System.arraycopy(line, inputLast, line, 0, lineEnd);
                        inputStart = 0;
                        inputLast = 0;
                    }

                    if (inputStart >= lineEnd && fillInLineCharacter()) {
                        pc--;
                        return -1;
                    }
                    pc = 0;
                    break;

                default:
                    throw new IllegalOpCodeException(opcode);
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "[" + Integer.toString(state) + "/" + Integer.toString(pc)
                + "] " + getLine() + "\n";
    }

}
