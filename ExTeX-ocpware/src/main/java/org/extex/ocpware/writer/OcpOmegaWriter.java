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
import java.util.List;

import org.extex.ocpware.type.OcpCode;
import org.extex.ocpware.type.OcpProgram;

/**
 * This is a writer for &Omega;CP programs which conforms to the one provided
 * with Omega.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpOmegaWriter extends AbstractWriter {

    /**
     * Creates a new object.
     */
    public OcpOmegaWriter() {

        super();
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

        int c = t[j];
        OcpCode code = OcpCode.get(c >> OcpCode.OPCODE_OFFSET);

        if (code != null) {
            out.print((code.getInstruction() + "                ").substring(0,
                16));
            print(out, "ARG", c & OcpCode.ARGUMENT_BIT_MASK);
            return (code.getArguments().length < 2);
        }

        print(out, "                ", c);
        return true;
    }

    /**
     * Print a number as hex and as decimal and optionally as character to an
     * output stream.
     * 
     * @param out the output stream
     * @param key the resource bundle key
     * @param value the value to print
     */
    protected void print(PrintStream out, String key, int value) {

        String s = "";
        if (value >= ' ' && value <= 0x7d) {
            StringBuffer sb = new StringBuffer(",`");
            sb.append((char) value);
            sb.append('\'');
            s = sb.toString();
        }
        String hex = Integer.toHexString(value);
        String dec = Integer.toString(value);
        out.print(format(key, hex, dec, s));
    }

    /**
     * Print a number as hex and as decimal to an output stream.
     * 
     * @param out the output stream
     * @param key the resource bundle key
     * @param value the value to print
     */
    protected void printNum(PrintStream out, String key, int value) {

        String hex = Integer.toHexString(value);
        switch (hex.length()) {
            case 1:
                hex = "  " + hex;
                break;
            case 2:
                hex = " " + hex;
                break;
            default:
                // leave alone
        }
        String dec = Integer.toString(value);
        switch (dec.length()) {
            case 1:
                dec = "  " + dec;
                break;
            case 2:
                dec = " " + dec;
                break;
            default:
                // leave alone
        }
        out.print(format(key, hex, dec, ""));
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
            printNum(out, "LENGTH", length);
        }
        printNum(out, "INPUT", ocp.getInput());
        printNum(out, "OUTPUT", ocp.getOutput());
        List<int[]> tables = ocp.getTables();
        printNum(out, "NO_TABLES", tables.size());
        int mem = 0;
        for (int i = 0; i < tables.size(); i++) {
            mem += tables.get(i).length;
        }
        printNum(out, "ROOM_TABLES", mem);
        List<int[]> states = ocp.getStates();
        printNum(out, "NO_STATES", states.size());
        mem = 0;
        for (int i = 0; i < states.size(); i++) {
            mem += states.get(i).length;
        }
        printNum(out, "ROOM_STATES", mem);

        for (int i = 0; i < tables.size(); i++) {
            int[] table = tables.get(i);
            printNum(out, "TABLE", i);
            printNum(out, "TABLE_ENTRIES", table.length);

            for (int j = 0; j < table.length; j++) {
                printNum(out, "ENTRY_TABLE", i);
                printNum(out, "TABLE_ENTRY", j);
                print(out, "TABLE_ITEM", table[j]);
            }
        }

        for (int i = 0; i < states.size(); i++) {
            int[] instructions = states.get(i);
            printNum(out, "STATE", i);
            printNum(out, "ENTRIES", instructions.length);

            boolean dis = true;

            for (int j = 0; j < instructions.length; j++) {
                printNum(out, "ENTRY_STATE", i);
                printNum(out, "ENTRY", j);

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
