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
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpReader extends Reader {

    /**
     * The field <tt>TWENTYFOUR_BIT_MASK</tt> contains the bit mask with 24
     * bits.
     */
    private static final int TWENTYFOUR_BIT_MASK = 0xffffff;

    /**
     * The field <tt>arithStack</tt> contains the stack for execution.
     */
    private Stack<Integer> arithStack = new Stack<Integer>();

    /**
     * The field <tt>buffer</tt> contains the ...
     */
    private char[] buffer = new char[1024];

    /**
     * The field <tt>bufferEnd</tt> contains the ...
     */
    private int bufferEnd = 0;

    /**
     * The field <tt>bufferPtr</tt> contains the ...
     */
    private int bufferPtr = 0;

    /**
     * The field <tt>code</tt> contains the ...
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
     * The field <tt>program</tt> contains the reference to the entire program.
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
     * @param reader the reader for input characters
     * @param program the program code to run
     */
    public OcpReader(Reader reader, OcpProgram program) {

        super();
        this.reader = reader;
        this.program = program;
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
     * TODO gene: missing JavaDoc
     * 
     * @return <code>true</code> iff end of file has been reached
     */
    private boolean fillLine() {

        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#read()
     */
    @Override
    public int read() throws IOException {

        int a;
        int c;

        for (;;) {
            c = code[pc++];

            switch (c >> 24) {
                case OcpProgram.RIGHT_OUTPUT:
                    return arithStack.pop().intValue();
                case OcpProgram.RIGHT_NUM:
                    return c & TWENTYFOUR_BIT_MASK;
                case OcpProgram.RIGHT_CHAR:
                    return line[first + (c & TWENTYFOUR_BIT_MASK)];
                case OcpProgram.RIGHT_LCHAR:
                    return line[last - (c & TWENTYFOUR_BIT_MASK)];
                case OcpProgram.RIGHT_SOME:
                    a = code[pc++] & TWENTYFOUR_BIT_MASK;
                    //TODO
                    break;
                case OcpProgram.PBACK_OUTPUT:
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpProgram.PBACK_NUM:
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpProgram.PBACK_CHAR:
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpProgram.PBACK_LCHAR:
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpProgram.PBACK_SOME:
                    a = code[pc++] & TWENTYFOUR_BIT_MASK;
                    // TODO gene: read unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpProgram.ADD:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    arithStack.push(Integer.valueOf(c + a));
                    break;
                case OcpProgram.SUB:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    arithStack.push(Integer.valueOf(c - a));
                    break;
                case OcpProgram.MULT:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    arithStack.push(Integer.valueOf(c * a));
                    break;
                case OcpProgram.DIV:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    arithStack.push(Integer.valueOf(c / a));
                    break;
                case OcpProgram.MOD:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    arithStack.push(Integer.valueOf(c % a));
                    break;
                case OcpProgram.LOOKUP:
                    a = arithStack.pop().intValue();
                    c = arithStack.pop().intValue();
                    int[] t = tables.get(c);
                    arithStack.push(Integer.valueOf(t[a]));
                    break;
                case OcpProgram.PUSH_NUM:
                    arithStack.push(Integer.valueOf(c & TWENTYFOUR_BIT_MASK));
                    break;
                case OcpProgram.PUSH_CHAR:
                    arithStack.push(Integer.valueOf(line[first
                            + (c & TWENTYFOUR_BIT_MASK)]));
                    break;
                case OcpProgram.PUSH_LCHAR:
                    arithStack.push(Integer.valueOf(line[last
                            - (c & TWENTYFOUR_BIT_MASK)]));
                    break;
                case OcpProgram.STATE_CHANGE:
                    state = c & TWENTYFOUR_BIT_MASK;
                    break;
                case OcpProgram.STATE_PUSH:
                    stateStack.push(Integer.valueOf(state));
                    state = c & TWENTYFOUR_BIT_MASK;
                    break;
                case OcpProgram.STATE_POP:
                    state = stateStack.pop().intValue();
                    break;
                case OcpProgram.LEFT_START:
                    first = last + 1;
                    break;
                case OcpProgram.LEFT_RETURN:
                    last = first + 1;
                    break;
                case OcpProgram.LEFT_BACKUP:
                    last--; // ???
                    break;
                case OcpProgram.GOTO:
                    pc = c & TWENTYFOUR_BIT_MASK;
                    break;
                case OcpProgram.GOTO_NE:
                    a = code[pc++];
                    if (line[last] != (c & TWENTYFOUR_BIT_MASK)) {
                        pc = a & TWENTYFOUR_BIT_MASK;
                    }
                    break;
                case OcpProgram.GOTO_EQ:
                    a = code[pc++];
                    if (line[last] == (c & TWENTYFOUR_BIT_MASK)) {
                        pc = a & TWENTYFOUR_BIT_MASK;
                    }
                    break;
                case OcpProgram.GOTO_LT:
                    a = code[pc++];
                    if (line[last] < (c & TWENTYFOUR_BIT_MASK)) {
                        pc = a & TWENTYFOUR_BIT_MASK;
                    }
                    break;
                case OcpProgram.GOTO_LE:
                    a = code[pc++];
                    if (line[last] <= (c & TWENTYFOUR_BIT_MASK)) {
                        pc = a & TWENTYFOUR_BIT_MASK;
                    }
                    break;
                case OcpProgram.GOTO_GT:
                    a = code[pc++];
                    if (line[last] > (c & TWENTYFOUR_BIT_MASK)) {
                        pc = a & TWENTYFOUR_BIT_MASK;
                    }
                    break;
                case OcpProgram.GOTO_GE:
                    a = code[pc++];
                    if (line[last] >= (c & TWENTYFOUR_BIT_MASK)) {
                        pc = a & TWENTYFOUR_BIT_MASK;
                    }
                    break;
                case OcpProgram.GOTO_NO_ADVANCE:
                    a = code[pc++];
                    last++;
                    refill();
                    if (false) { // TODO check for end of buffer
                        pc = a & TWENTYFOUR_BIT_MASK;
                    }
                    break;
                case OcpProgram.GOTO_BEG:
                    if (false) { // TODO at beginning
                        pc = c & TWENTYFOUR_BIT_MASK;
                    }
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpProgram.GOTO_END:
                    if (false) { // TODO at end
                        pc = c & TWENTYFOUR_BIT_MASK;
                    }
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");
                case OcpProgram.STOP:
                    return 1;
                default:
                    throw new IllegalOpCodeException(//
                        Integer.valueOf(c >> 24).toString());
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
     * TODO gene: missing JavaDoc
     * 
     */
    private void refill() {

        // TODO gene: refill unimplemented

    }

}
