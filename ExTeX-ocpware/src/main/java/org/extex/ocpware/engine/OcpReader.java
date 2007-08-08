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
     * The field <tt>last</tt> contains the index of the last character of the
     * prefix matched.
     */
    private int inputLast = 0;

    /**
     * The field <tt>first</tt> contains the index of the first character of
     * the prefix matched.
     */
    private int inputStart = 0;

    /**
     * The field <tt>line</tt> contains the intermediate buffer for the
     * characters read. The buffer will be extended dynamically if required.
     */
    private char[] line = new char[128];

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
     * The field <tt>reader</tt> contains the reader to acquire input from.
     */
    private Reader reader;

    /**
     * The field <tt>state</tt> contains the current state.
     */
    private int state = 0;

    /**
     * The field <tt>stateStack</tt> contains the stack of states.
     */
    private Stack<Integer> stateStack = new Stack<Integer>();

    /**
     * The field <tt>tables</tt> contains the tables.
     */
    private List<int[]> tables = new ArrayList<int[]>();

    private int outMax;

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

        switch (program.getOutput()) {
            case 1:
                outMax = 0xff;
                break;
            case 2:
                outMax = 0xffff;
                break;
            default:
                // TODO gene: read unimplemented
                throw new RuntimeException("unimplemented");
        }
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
        reader.close();
        reader = null;

        if (observers != null) {
            for (OcpReaderObserver observer : observers) {
                observer.close(this);
            }
        }
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
            char[] l = new char[line.length + 128];
            System.arraycopy(line, 0, l, 0, line.length);
            line = l;
        }
        line[lineEnd++] = (char) c;
        return false;
    }

    /**
     * Getter for arithStack.
     * 
     * @return the arithStack
     */
    public Stack<Integer> getArithStack() {

        return arithStack;
    }

    /**
     * Getter for buffer.
     * 
     * @return the buffer
     */
    public char[] getBuffer() {

        return buffer;
    }

    /**
     * Getter for bufferEnd.
     * 
     * @return the bufferEnd
     */
    public int getBufferEnd() {

        return bufferEnd;
    }

    /**
     * Getter for bufferPtr.
     * 
     * @return the bufferPtr
     */
    public int getBufferPtr() {

        return bufferPtr;
    }

    /**
     * Getter for inputLast.
     * 
     * @return the inputLast
     */
    public int getInputLast() {

        return inputLast;
    }

    /**
     * Getter for inputStart.
     * 
     * @return the inputStart
     */
    public int getInputStart() {

        return inputStart;
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
     * Getter for pc.
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
     * Getter for state.
     * 
     * @return the state
     */
    public int getState() {

        return state;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#read()
     * 
     * @throws OcpEmptyStackException ...
     * @throws IllegalPcException if the program counter points outside the code
     *         area
     */
    @Override
    public int read()
            throws IOException,
                OcpEmptyStackException,
                IllegalPcException {

        if (bufferPtr < bufferEnd) {
            return buffer[bufferPtr++];
        }

        int c;

        try {
            c = step();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalPcException(pc);
        } catch (EmptyStackException e) {
            throw new OcpEmptyStackException();
        }

        if (c >= outMax) {
            // TODO gene: read unimplemented
            throw new RuntimeException("unimplemented");
        }
        return c;
    }

    /**
     * Perform steps of the engine until an output character is encountered or
     * the end of input is reached.
     * 
     * @return the output character or -1 on EOF
     * 
     * @throws IllegalTableItemException ...
     * @throws IllegalTableException ...
     * @throws OcpLineUnderflowException ...
     * @throws IllegalOpCodeException if an unknown op code is encountered
     * @throws IOException in case of an I/O error
     */
    private int step()
            throws IllegalTableItemException,
                IllegalTableException,
                IOException,
                OcpLineUnderflowException,
                IllegalOpCodeException {

        int a;
        int c;

        for (;;) {
            c = code[pc];
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
                    // otp_right_output: begin
                    // incr(otp_output_end);
                    // if otp_output_end >ocp_buf_size then
                    // overflow_ocp_buf_size;
                    // otp_output_buf[otp_output_end]:=otp_calcs[otp_calc_ptr];
                    // decr(otp_calc_ptr);
                    // incr(otp_pc);
                    // end;
                    return arithStack.pop().intValue();

                case OcpCode.OP_RIGHT_NUM:
                    // otp_right_num: begin
                    // incr(otp_output_end);
                    // if otp_output_end >ocp_buf_size then
                    // overflow_ocp_buf_size;
                    // otp_output_buf[otp_output_end]:=otp_arg;
                    // incr(otp_pc);
                    // end;
                    return c & OcpCode.ARGUMENT_BIT_MASK;

                case OcpCode.OP_RIGHT_CHAR:
                    // otp_right_char: begin
                    // otp_get_char(otp_arg);
                    a = c & OcpCode.ARGUMENT_BIT_MASK;
                    // incr(otp_output_end);
                    // if otp_output_end >ocp_buf_size then
                    // overflow_ocp_buf_size;
                    // otp_output_buf[otp_output_end]:=otp_calculated_char;
                    // incr(otp_pc);
                    // end;
                    if (a >= lineEnd) {
                        // TODO gene: read unimplemented
                        throw new RuntimeException("unimplemented");
                    }
                    return line[inputLast - a - 1];

                case OcpCode.OP_RIGHT_LCHAR:
                    // otp_right_lchar: begin
                    // otp_get_char(otp_no_input_chars-otp_arg);
                    a = c & OcpCode.ARGUMENT_BIT_MASK;
                    // incr(otp_output_end);
                    // if otp_output_end >ocp_buf_size then
                    // overflow_ocp_buf_size;
                    // otp_output_buf[otp_output_end]:=otp_calculated_char;
                    // incr(otp_pc);
                    // end;
                    if (a >= lineEnd) {
                        // TODO gene: read unimplemented
                        throw new RuntimeException("unimplemented");
                    }
                    return line[inputStart + a];

                case OcpCode.OP_RIGHT_SOME:
                    // otp_right_some: begin
                    // otp_first_arg:=otp_arg+1;
                    // incr(otp_pc);
                    // otp_set_instruction;
                    // otp_second_arg:=otp_no_input_chars-otp_arg;
                    // for otp_counter:=otp_first_arg to otp_second_arg do
                    // begin
                    // otp_get_char(otp_counter);
                    // incr(otp_output_end);
                    // if otp_output_end >ocp_buf_size then
                    // overflow_ocp_buf_size;
                    // otp_output_buf[otp_output_end]:=otp_calculated_char;
                    // end;
                    // incr(otp_pc);
                    // end
                    c &= OcpCode.ARGUMENT_BIT_MASK;
                    a = code[pc++] & OcpCode.ARGUMENT_BIT_MASK;
                    if (a - c + 1 >= buffer.length) {
                        // enlarge the buffer
                        buffer = new char[a - c + 16];
                        // a little bit more space than needed
                    }
                    bufferPtr = 0;
                    bufferEnd = 0;

                    while (c < a) {
                        buffer[bufferEnd++] = line[inputStart + c];
                        c++;
                    }
                    if (bufferPtr < bufferEnd) {
                        return buffer[bufferPtr++];
                    }
                    break;

                case OcpCode.OP_PBACK_OUTPUT:
                    // otp_pback_output: begin
                    // incr(otp_stack_new);
                    // if otp_stack_new >= ocp_stack_size then
                    // overflow_ocp_stack_size;
                    // otp_stack_buf[otp_stack_new]:=otp_calcs[otp_calc_ptr];
                    // decr(otp_calc_ptr);
                    // incr(otp_pc);
                    // end;
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");

                case OcpCode.OP_PBACK_NUM:
                    // otp_pback_num: begin
                    // incr(otp_stack_new);
                    // if otp_stack_new >= ocp_stack_size then
                    // overflow_ocp_stack_size;
                    // otp_stack_buf[otp_stack_new]:=otp_arg;
                    // incr(otp_pc);
                    // end;
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");

                case OcpCode.OP_PBACK_CHAR:
                    // otp_pback_char: begin
                    // otp_get_char(otp_arg);
                    // incr(otp_stack_new);
                    // if otp_stack_new >= ocp_stack_size then
                    // overflow_ocp_stack_size;
                    // otp_stack_buf[otp_stack_new]:=otp_calculated_char;
                    // incr(otp_pc);
                    // end;
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");

                case OcpCode.OP_PBACK_LCHAR:
                    // otp_pback_lchar: begin
                    // otp_get_char(otp_no_input_chars-otp_arg);
                    // incr(otp_stack_new);
                    // if otp_stack_new >= ocp_stack_size then
                    // overflow_ocp_stack_size;
                    // otp_stack_buf[otp_stack_new]:=otp_calculated_char;
                    // incr(otp_pc);
                    // end;
                    // TODO gene: step unimplemented
                    throw new RuntimeException("unimplemented");

                case OcpCode.OP_PBACK_SOME:
                    // otp_pback_some: begin
                    // otp_first_arg:=otp_arg+1;
                    // incr(otp_pc);
                    // otp_set_instruction;
                    // otp_second_arg:=otp_no_input_chars-otp_arg;
                    // for otp_counter:=otp_first_arg to otp_second_arg do
                    // begin
                    // otp_get_char(otp_counter);
                    // incr(otp_stack_new);
                    // if otp_stack_new >= ocp_stack_size then
                    // overflow_ocp_stack_size;
                    // otp_stack_buf[otp_stack_new]:=otp_calculated_char;
                    // end;
                    // incr(otp_pc);
                    // end
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
                    try {
                        int[] t = tables.get(c);
                        arithStack.push(Integer.valueOf(t[a]));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalTableItemException(a);
                    } catch (IndexOutOfBoundsException e) {
                        throw new IllegalTableException(c);
                    }
                    break;

                case OcpCode.OP_PUSH_NUM:
                    arithStack.push(Integer.valueOf(c
                            & OcpCode.ARGUMENT_BIT_MASK));
                    break;

                case OcpCode.OP_PUSH_CHAR:
                    // otp_push_char: begin
                    // otp_get_char(otp_arg);
                    // incr(otp_calc_ptr);
                    // if otp_calc_ptr >= ocp_stack_size then
                    // overflow_ocp_stack_size;
                    // otp_calcs[otp_calc_ptr]:=otp_calculated_char;
                    // incr(otp_pc);
                    // end;

                    a = c & OcpCode.ARGUMENT_BIT_MASK;
                    if (a >= lineEnd) {
                        // TODO gene: read unimplemented
                        throw new RuntimeException("unimplemented");
                    }
                    arithStack.push(Integer.valueOf(line[inputLast - a - 1]));
                    break;

                case OcpCode.OP_PUSH_LCHAR:
                    a = c & OcpCode.ARGUMENT_BIT_MASK;
                    if (a >= lineEnd) {
                        // TODO gene: read unimplemented
                        throw new RuntimeException("unimplemented");
                    }
                    arithStack.push(Integer.valueOf(line[inputStart + a]));
                    break;

                case OcpCode.OP_STATE_CHANGE:
                    // otp_state_change: begin
                    // otp_input_start:=otp_input_last;
                    inputStart = inputLast + 1;
                    // for otp_counter:=1 to (otp_stack_new-otp_stack_used)
                    // do
                    // begin
                    // otp_stack_buf[otp_counter] :=
                    // otp_stack_buf[otp_counter+otp_stack_used];
                    // end;
                    // otp_stack_new:=otp_stack_new-otp_stack_used;
                    // otp_stack_last:=otp_stack_new;
                    // otp_stack_used:=0;
                    // otp_states[otp_state_ptr]:=otp_arg;
                    state = c & OcpCode.ARGUMENT_BIT_MASK;
                    code = program.getCode(state);
                    // otp_pc:=0;
                    pc = 0;
                    // end;
                    break;

                case OcpCode.OP_STATE_PUSH:
                    // otp_state_push: begin
                    // otp_input_start:=otp_input_last;
                    inputStart = inputLast;
                    // for otp_counter:=1 to (otp_stack_new-otp_stack_used)
                    // do
                    // begin
                    // otp_stack_buf[otp_counter] :=
                    // otp_stack_buf[otp_counter+otp_stack_used];
                    // end;
                    // otp_stack_new:=otp_stack_new-otp_stack_used;
                    // otp_stack_last:=otp_stack_new;
                    // otp_stack_used:=0;
                    // incr(otp_state_ptr);
                    // if otp_state_ptr >= ocp_stack_size then
                    // overflow_ocp_stack_size;
                    // otp_states[otp_state_ptr]:=otp_arg;
                    // otp_pc:=0;
                    // end;
                    stateStack.push(Integer.valueOf(state));
                    state = c & OcpCode.ARGUMENT_BIT_MASK;
                    this.code = program.getCode(state);
                    pc = 0;
                    break;

                case OcpCode.OP_STATE_POP:
                    // otp_state_pop: begin
                    // otp_input_start:=otp_input_last;
                    inputStart = inputLast;
                    if (inputStart >= lineEnd && fillInLineCharacter()) {
                        pc--;
                        return -1;
                    }
                    // for otp_counter:=1 to (otp_stack_new-otp_stack_used)
                    // do
                    // begin
                    // otp_stack_buf[otp_counter] :=
                    // otp_stack_buf[otp_counter+otp_stack_used];
                    // end;
                    // otp_stack_new:=otp_stack_new-otp_stack_used;
                    // otp_stack_last:=otp_stack_new;
                    // otp_stack_used:=0;
                    // if otp_state_ptr>0 then decr(otp_state_ptr);
                    state = stateStack.pop().intValue();
                    code = program.getCode(state);
                    // otp_pc:=0;
                    pc = 0;
                    // end
                    break;

                case OcpCode.OP_LEFT_START:
                    // otp_left_start: begin
                    // otp_input_start:=otp_input_last;
                    inputStart = inputLast;
                    // otp_input_last:=otp_input_start;
                    // otp_stack_used:=0;
                    if (inputStart >= lineEnd && fillInLineCharacter()) {
                        pc--;
                        return -1;
                    }
                    // if (otp_stack_last=0) and
                    // (otp_input_last>=otp_input_end)
                    // then
                    // otp_finished:=true
                    // else if (otp_stack_used < otp_stack_last) then begin
                    // incr(otp_stack_used); {no overflow problem}
                    // otp_input_char:=otp_stack_buf[otp_stack_used];
                    // otp_no_input_chars:=1;
                    // incr(otp_pc);
                    // end
                    // else begin
                    // incr(otp_input_last); {no overflow problem}
                    inputLast++;
                    // otp_input_char:=otp_input_buf[otp_input_last];
                    // otp_no_input_chars:=1;
                    // incr(otp_pc);
                    // end;
                    // end;
                    break;

                case OcpCode.OP_LEFT_RETURN:
                    // otp_left_return: begin
                    // otp_input_last:=otp_input_start;
                    inputLast = inputStart + 1;
                    if (inputStart >= lineEnd && fillInLineCharacter()) {
                        pc--;
                        return -1;
                    }
                    // otp_stack_used:=0;
                    // if (otp_stack_used < otp_stack_last) then begin
                    // incr(otp_stack_used); {no overflow problem}
                    // otp_input_char:=otp_stack_buf[otp_stack_used];
                    // otp_no_input_chars:=1;
                    // incr(otp_pc);
                    // end
                    // else begin
                    // incr(otp_input_last); {no overflow problem}
                    // otp_input_char:=otp_input_buf[otp_input_last];
                    // otp_no_input_chars:=1;
                    // incr(otp_pc);
                    // end;
                    // end;

                    break;

                case OcpCode.OP_LEFT_BACKUP:
                    // otp_left_backup: begin
                    // if otp_input_start < otp_input_last then begin
                    // decr(otp_input_last);
                    // otp_input_char:=otp_input_buf[otp_input_last];
                    // end
                    // else begin
                    // decr(otp_stack_used);
                    // otp_input_char:=otp_stack_buf[otp_stack_used];
                    // end;
                    // decr(otp_no_input_chars);
                    // incr(otp_pc);
                    // end

                    inputLast--;
                    if (inputLast <= inputStart) {
                        throw new OcpLineUnderflowException();
                    }
                    break;

                case OcpCode.OP_GOTO:
                    // otp_goto: begin
                    // otp_pc:=otp_arg;
                    // end;
                    pc = c & OcpCode.ARGUMENT_BIT_MASK;
                    break;

                case OcpCode.OP_GOTO_NE:
                    // otp_goto_ne: begin
                    // otp_first_arg:=otp_arg;
                    // incr(otp_pc);
                    // otp_set_instruction;
                    // if otp_input_char <> otp_first_arg then begin
                    // otp_pc:=otp_arg;
                    // end
                    // else begin
                    // incr(otp_pc);
                    // end;
                    // end;
                    a = code[pc++];
                    if (line[inputLast] != (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_EQ:
                    // otp_goto_eq: begin
                    // otp_first_arg:=otp_arg;
                    // incr(otp_pc);
                    // otp_set_instruction;
                    // if otp_input_char = otp_first_arg then begin
                    // otp_pc:=otp_arg;
                    // end
                    // else begin
                    // incr(otp_pc);
                    // end;
                    // end;
                    a = code[pc++];
                    if (line[inputLast] == (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_LT:
                    a = code[pc++];
                    if (line[inputLast] < (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_LE:
                    a = code[pc++];
                    if (line[inputLast] <= (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_GT:
                    a = code[pc++];
                    if (line[inputLast] > (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_GE:
                    a = code[pc++];
                    if (line[inputLast] >= (c & OcpCode.ARGUMENT_BIT_MASK)) {
                        pc = a & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_NO_ADVANCE:
                    // otp_goto_no_advance: begin
                    // if (otp_stack_used < otp_stack_last) then begin
                    // incr(otp_stack_used); {no overflow problem}
                    // otp_input_char:=otp_stack_buf[otp_stack_used];
                    // incr(otp_no_input_chars); {no overflow problem}
                    // incr(otp_pc);
                    // end
                    // else if otp_input_last>=otp_input_end then begin
                    // otp_pc:=otp_arg;
                    // end
                    // else begin
                    // incr(otp_input_last); {no overflow problem}
                    inputLast++;
                    if (inputLast >= lineEnd && fillInLineCharacter()) {
                        pc--;
                        return -1;
                    }
                    // otp_input_char:=otp_input_buf[otp_input_last];
                    // incr(otp_no_input_chars); {no overflow problem}
                    // incr(otp_pc);
                    // end;
                    // end;
                    if (false) { // TODO check for end of buffer
                        pc = c & OcpCode.ARGUMENT_BIT_MASK;
                    }
                    break;

                case OcpCode.OP_GOTO_BEG:
                    if (inputStart == 0) { // at beginning
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
                    // otp_stop: begin
                    // otp_input_start:=otp_input_last;
                    inputStart = inputLast + 1;
                    if (inputStart >= lineEnd && fillInLineCharacter()) {
                        pc--;
                        return -1;
                    }
                    // for otp_counter:=1 to (otp_stack_new-otp_stack_used)
                    // do
                    // begin
                    // otp_stack_buf[otp_counter] :=
                    // otp_stack_buf[otp_counter+otp_stack_used];
                    // end;
                    // otp_stack_new:=otp_stack_new-otp_stack_used;
                    // otp_stack_last:=otp_stack_new;
                    // otp_stack_used:=0;
                    // otp_pc:=0;
                    pc = 0;
                    // end
                    break;

                default:
                    throw new IllegalOpCodeException(opcode);
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
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return Integer.toString(pc);
    }

}
