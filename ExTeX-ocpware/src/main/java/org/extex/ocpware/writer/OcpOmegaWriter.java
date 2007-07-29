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

package org.extex.ocpware.writer;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.extex.ocpware.type.OcpProgram;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpOmegaWriter implements OcpWriter {

    /**
     * The field <tt>SIXTEEN_BIT_MASK</tt> contains the bit mask with 16 bits.
     */
    protected static final int SIXTEEN_BIT_MASK = 0xffff;

    /**
     * The field <tt>bundle</tt> contains the resource bundle for i18n.
     */
    private ResourceBundle bundle;

    /**
     * Creates a new object.
     */
    public OcpOmegaWriter() {

        super();
        bundle = ResourceBundle.getBundle(getClass().getName());
    }

    /**
     * Dump a dis-assembled form of an instruction.
     * 
     * @param out the output stream
     * @param t the array
     * @param j the index
     * 
     * @return <code>true</code> iff another word as argument is required
     */
    protected boolean disassemble(PrintStream out, int[] t, int j) {

        boolean two = true;
        int c = t[j];
        switch (c >> 24) {
            case OcpProgram.RIGHT_OUTPUT:
                out.print("RIGHT_OUTPUT    ");
                break;
            case OcpProgram.RIGHT_NUM:
                out.print("RIGHT_NUM       ");
                break;
            case OcpProgram.RIGHT_CHAR:
                out.print("RIGHT_CHAR      ");
                break;
            case OcpProgram.RIGHT_LCHAR:
                out.print("RIGHT_LCHAR     ");
                break;
            case OcpProgram.RIGHT_SOME:
                out.print("RIGHT_SOME      ");
                two = false;
                break;
            case OcpProgram.PBACK_OUTPUT:
                out.print("PBACK_OUTPUT    ");
                break;
            case OcpProgram.PBACK_NUM:
                out.print("PBACK_NUM       ");
                break;
            case OcpProgram.PBACK_CHAR:
                out.print("PBACK_CHAR      ");
                break;
            case OcpProgram.PBACK_LCHAR:
                out.print("PBACK_LCHAR     ");
                break;
            case OcpProgram.PBACK_SOME:
                out.print("PBACK_SOME      ");
                two = false;
                break;
            case OcpProgram.ADD:
                out.print("ADD             ");
                break;
            case OcpProgram.SUB:
                out.print("SUB             ");
                break;
            case OcpProgram.MULT:
                out.print("MULT            ");
                break;
            case OcpProgram.DIV:
                out.print("DIV             ");
                break;
            case OcpProgram.MOD:
                out.print("MOD             ");
                break;
            case OcpProgram.LOOKUP:
                out.print("LOOKUP          ");
                break;
            case OcpProgram.PUSH_NUM:
                out.print("PUSH_NUM        ");
                break;
            case OcpProgram.PUSH_CHAR:
                out.print("PUSH_CHAR       ");
                break;
            case OcpProgram.PUSH_LCHAR:
                out.print("PUSH_LCHAR      ");
                break;
            case OcpProgram.STATE_CHANGE:
                out.print("STATE_CHANGE    ");
                break;
            case OcpProgram.STATE_PUSH:
                out.print("STATE_PUSH      ");
                break;
            case OcpProgram.STATE_POP:
                out.print("STATE_POP       ");
                break;
            case OcpProgram.LEFT_START:
                out.print("LEFT_START      ");
                break;
            case OcpProgram.LEFT_RETURN:
                out.print("LEFT_RETURN     ");
                break;
            case OcpProgram.LEFT_BACKUP:
                out.print("LEFT_BACKUP     ");
                break;
            case OcpProgram.GOTO:
                out.print("GOTO            ");
                break;
            case OcpProgram.GOTO_NE:
                out.print("GOTO_NE         ");
                two = false;
                break;
            case OcpProgram.GOTO_EQ:
                out.print("GOTO_EQ         ");
                two = false;
                break;
            case OcpProgram.GOTO_LT:
                out.print("GOTO_LT         ");
                two = false;
                break;
            case OcpProgram.GOTO_LE:
                out.print("GOTO_LE         ");
                two = false;
                break;
            case OcpProgram.GOTO_GT:
                out.print("GOTO_GT         ");
                two = false;
                break;
            case OcpProgram.GOTO_GE:
                out.print("GOTO_GE         ");
                two = false;
                break;
            case OcpProgram.GOTO_NO_ADVANCE:
                out.print("GOTO_NO_ADVANCE ");
                break;
            case OcpProgram.GOTO_BEG:
                out.print("GOTO_BEG        ");
                break;
            case OcpProgram.GOTO_END:
                out.print("GOTO_END        ");
                break;
            case OcpProgram.STOP:
                out.print("STOP            ");
                break;
            default:
                print(out, "                ", c);
                return true;
        }
        print(out, "ARG", c & SIXTEEN_BIT_MASK);
        return two;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param key the resource key
     * @param a the arguments
     * 
     * @return the formatted result
     */
    protected String format(String key, Object... a) {

        try {
            String fmt = bundle.getString(key);
            return MessageFormat.format(fmt, a);
        } catch (MissingResourceException e) {
            return "???" + key + "???";
        }
    }

    /**
     * Print a number as hex and as decimal and optionally as character to an
     * output stream.
     * 
     * @param out the output stream
     * @param key the resource bundle key
     * @param value the value to print
     */
    void print(PrintStream out, String key, int value) {

        String s = "";
        if (value >= ' ' && value <= 0x7d) {
            StringBuffer sb = new StringBuffer(",`");
            sb.append((char) value);
            sb.append('\'');
            s = sb.toString();
        }
        out.print(format(key, Integer.toHexString(value), //
            Integer.toString(value), s));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.writer.OcpWriter#write(java.io.OutputStream,
     *      org.extex.ocpware.type.OcpProgram)
     */
    public void write(OutputStream stream, OcpProgram ocp) {

        PrintStream out = new PrintStream(stream);

        int length = ocp.getLength();
        if (length >= 0) {
            print(out, "LENGTH", length);
        }
        print(out, "INPUT", ocp.getInput());
        print(out, "OUTPUT", ocp.getOutput());
        List<int[]> tables = ocp.getTables();
        print(out, "NO_TABLES", tables.size());
        int mem = 0;
        for (int i = 0; i < tables.size(); i++) {
            mem += tables.get(i).length;
        }
        print(out, "ROOM_TABLES", mem);
        List<int[]> states = ocp.getStates();
        print(out, "NO_STATES", states.size());
        mem = 0;
        for (int i = 0; i < states.size(); i++) {
            mem += states.get(i).length;
        }
        print(out, "ROOM_STATES", mem);

        for (int i = 0; i < states.size(); i++) {
            int[] instructions = states.get(i);
            print(out, "STATE", i);
            print(out, "ENTRIES", instructions.length);

            boolean dis = true;

            for (int j = 0; j < instructions.length; j++) {
                print(out, "ENTRY_STATE", i);
                print(out, "ENTRY", j);

                if (dis) {
                    out.print("OTP_");
                    dis = disassemble(out, instructions, j);
                } else {
                    out.print("                    ");
                    print(out, "INSTRUCTION", instructions[j]);
                    dis = true;
                }
                out.println();
            }
        }
    }

}
