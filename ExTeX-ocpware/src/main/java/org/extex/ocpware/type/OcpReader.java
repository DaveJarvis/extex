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

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This reader applies an &Omega;CP program to an input stream.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpReader extends Reader {

    /**
     * The field <tt>arithStack</tt> contains the stack for execution.
     */
    private Stack<Integer> arithStack = new Stack<Integer>();

    /**
     * The field <tt>buffer</tt> contains the input buffer.
     */
    private char[] buffer = new char[1024];

    /**
     * The field <tt>bufferEnd</tt> contains the pointer to the end of the
     * input buffer.
     */
    private int bufferEnd = 0;

    /**
     * The field <tt>bufferPtr</tt> contains the pointer to the current
     * position in the input buffer.
     */
    private int bufferPtr = 0;

    /**
     * The field <tt>code</tt> contains the code currently run.
     */
    private int[] code;

    /**
     * The field <tt>first</tt> contains the index of the first character of
     * the prefix matched.
     */
    private int first = 0;

    /**
     * The field <tt>last</tt> contains the index of the last character of the
     * prefix matched.
     */
    private int last = 0;

    /**
     * The field <tt>line</tt> contains the ...
     */
    private char[] line = new char[1024];

    /**
     * The field <tt>lineEnd</tt> contains the index of the character
     * following the last one used in the line buffer.
     */
    private int lineEnd = 0;

    /**
     * The field <tt>pc</tt> contains the program counter.
     */
    private int pc = 0;

    /**
     * The field <tt>program</tt> contains the reference to the entire
     * program.
     */
    private OcpProgram program;

    /**
     * The field <tt>reader</tt> contains the reader to acquire input from.
     */
    private Reader reader;

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
     * @param reader the reader for input characters. This is not allowed to be
     *        <code>null</code>.
     * @param program the program code to run. This is not allowed to be
     *        <code>null</code>.
     * 
     * @throws IllegalArgumentException in case that one of the arguments is
     *         <code>null</code>
     */
    public OcpReader(Reader reader, OcpProgram program)
            throws IllegalArgumentException {

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
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#close()
     */
    @Override
    public void close() throws IOException {

        reader.close();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#read()
     */
    @Override
    public int read() throws IOException {

        if (bufferPtr < bufferEnd) {
            return buffer[bufferPtr++];
        }

        int a;
        int c;

        for (;;) {
            c = code[pc++];

            switch (c >> OcpCode.OPCODE_OFFSET) {
                case OcpCode.OP_RIGHT_OUTPUT:
                    return arithStack.pop().intValue();
                case OcpCode.OP_RIGHT_NUM:
                    return c & OcpCode.ARGUMENT_BIT_MASK;
                case OcpCode.OP_RIGHT_CHAR:
                    return line[first + (c & OcpCode.ARGUMENT_BIT_MASK)];
                case OcpCode.OP_RIGHT_LCHAR:
                    return line[last - (c & OcpCode.ARGUMENT_BIT_MASK)];
                case OcpCode.OP_RIGHT_SOME:
                    c &= OcpCode.ARGUMENT_BIT_MASK;
                    a = code[pc++] & OcpCode.ARGUMENT_BIT_MASK;
                    if (a - c + 1 < buffer.length) {
                        // TODO gene: extend buffer unimplemented
                        throw new RuntimeException("unimplemented");
                    }
                    bufferPtr = 0;
                    bufferEnd = 0;
                    // TODO
                    break;
                case OcpCode.OP_PBACK_OUTPUT:
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpCode.OP_PBACK_NUM:
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpCode.OP_PBACK_CHAR:
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpCode.OP_PBACK_LCHAR:
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpCode.OP_PBACK_SOME:
                    a = code[pc++] & OcpCode.ARGUMENT_BIT_MASK;
                    // TODO gene: read unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpCode.OP_ADD:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    arithStack.push(Integer.valueOf(c + a));
                    break;
                case OcpCode.OP_SUB:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    arithStack.push(Integer.valueOf(c - a));
                    break;
                case OcpCode.OP_MULT:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    arithStack.push(Integer.valueOf(c * a));
                    break;
                case OcpCode.OP_DIV:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    arithStack.push(Integer.valueOf(c / a));
                    break;
                case OcpCode.OP_MOD:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    arithStack.push(Integer.valueOf(c % a));
                    break;
                case OcpCode.OP_LOOKUP:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    int[] t = tables.get(c);
                    arithStack.push(Integer.valueOf(t[a]));
                    break;
                case OcpCode.OP_PUSH_NUM:
                    arithStack.push(Integer.valueOf(c
                            & OcpCode.ARGUMENT_BIT_MASK));
                    break;
                case OcpCode.OP_PUSH_CHAR:
                    arithStack.push(Integer.valueOf(line[first
                            + (c & OcpCode.ARGUMENT_BIT_MASK)]));
                    break;
                case OcpCode.OP_PUSH_LCHAR:
                    arithStack.push(Integer.valueOf(line[last
                            - (c & OcpCode.ARGUMENT_BIT_MASK)]));
                    break;
                case OcpCode.OP_STATE_CHANGE:
                    state = c & OcpCode.ARGUMENT_BIT_MASK;
                    break;
                case OcpCode.OP_STATE_PUSH:
                    stateStack.push(Integer.valueOf(state));
                    state = c & OcpCode.ARGUMENT_BIT_MASK;
                    break;
                case OcpCode.OP_STATE_POP:
                    state = stateStack.pop().intValue();
                    break;
                case OcpCode.OP_LEFT_START:
                    first = last + 1;
                    break;
                case OcpCode.OP_LEFT_RETURN:
                    last = first + 1;
                    break;
                case OcpCode.OP_LEFT_BACKUP:
                    last--;
                    if (last <= first) {
                        // TODO gene: read unimplemented
                        throw new RuntimeException("unimplemented");
                    }
                    break;
                case OcpCode.OP_GOTO:
                    pc = c & OcpCode.ARGUMENT_BIT_MASK;
                    break;
                case OcpCode.OP_GOTO_NE:
                    a = code[pc++];
                    if (line[last] != (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;
                case OcpCode.OP_GOTO_EQ:
                    a = code[pc++];
                    if (line[last] == (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;
                case OcpCode.OP_GOTO_LT:
                    a = code[pc++];
                    if (line[last] < (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;
                case OcpCode.OP_GOTO_LE:
                    a = code[pc++];
                    if (line[last] <= (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;
                case OcpCode.OP_GOTO_GT:
                    a = code[pc++];
                    if (line[last] > (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;
                case OcpCode.OP_GOTO_GE:
                    a = code[pc++];
                    if (line[last] >= (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;
                case OcpCode.OP_GOTO_NO_ADVANCE:
                    a = code[pc++];
                    last++;
                    refill();
                    if (false) { // TODO check for end of buffer
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;
                case OcpCode.OP_GOTO_BEG:
                    if (first == 0) { // at beginning
                        pc = c & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;
                case OcpCode.OP_GOTO_END:
                    if (false) { // TODO at end
                        pc = c & OcpCode.ARGUMENT_BIT_MASK;
                        // TODO gene: step unimplemented
                        throw new RuntimeException("unimplemented");
                    }
                    break;
                case OcpCode.OP_STOP:
                    pc = 0; // ???
                    break;
                default:
                    throw new IllegalOpCodeException(//
                        Integer.valueOf(c >> OcpCode.OPCODE_OFFSET).toString());
            }
        }
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
            while (bufferPtr < bufferEnd) {
                cbuf[i++] = buffer[bufferPtr++];
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
     * Gets some more characters into the input buffer.
     */
    private void refill() {

        // TODO gene: refill unimplemented
        throw new RuntimeException("unimplemented");
    }

}
