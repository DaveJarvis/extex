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
*/
public class OcpOmegaWriter extends AbstractWriter {

    /**
     * The field {@code adjust} contains the length to adjust the code to
     * this length.
     */
    private int adjust = 20;

    /**
     * The field {@code fill} contains the indicator to fill in some spaces
     * to line up things in columns.
     */
    private boolean fill = true;


    public OcpOmegaWriter() {

        this(20, true);
    }

    /**
     * Creates a new object.
     * 
     * @param adjust the adjust length
     * @param fill the fill indicator
     */
    protected OcpOmegaWriter(int adjust, boolean fill) {

        this.adjust = adjust;
        this.fill = fill;
    }

    /**
     * Generate an alternate description of a character for printable
     * characters.
     * 
     * @param value the character code
     * 
     * @return the character string
     */
    protected String charString(int value) {

        String s = "";
        if (value >= ' ' && value <= 0x7e) {
            StringBuilder sb = new StringBuilder(",`");
            sb.append((char) value);
            sb.append('\'');
            s = sb.toString();
        }
        return s;
    }

    /**
     * Dump a dis-assembled form of an instruction.
     * 
     * @param out the output stream
     * @param t the array
     * @param j the index
     * 
     * @return {@code true} iff another word as argument is required
     */
    protected boolean disassemble(PrintStream out, int[] t, int j) {

        int c = t[j];
        OcpCode code = OcpCode.get(c >> OcpCode.OPCODE_OFFSET);

        if (code != null) {
            out.print(format("Code", code.getInstruction())
                .substring(0, adjust));
            print(out, "Arg", c & OcpCode.ARGUMENT_BIT_MASK);
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

        String s = charString(value);
        String hex = Integer.toHexString(value);
        if (fill && hex.length() == 1) {
            hex = " " + hex;
        }
        String dec = Integer.toString(value);
        if (fill) {
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
        }
        out.print(format(key, hex, dec, s));
    }

    /**
     * Print a number as hex and as decimal to an output stream.
     * 
     * @param off offset
     * @param out the output stream
     * @param key the resource bundle key
     * @param value the value to print
     * @param value2 the second value to print
     */
    protected void printNum(int off, PrintStream out, String key, int value,
            int value2) {

        String hex = Integer.toHexString(value);
        if (fill) {
            switch (hex.length() - off) {
                case 1:
                    hex = "  " + hex;
                    break;
                case 2:
                    hex = " " + hex;
                    break;
                default:
                    // leave alone
            }
        }
        String dec = Integer.toString(value);
        if (fill) {
            switch (dec.length() - off) {
                case 1:
                    dec = "  " + dec;
                    break;
                case 2:
                    dec = " " + dec;
                    break;
                default:
                    // leave alone
            }
        }
        String hex2 = Integer.toHexString(value2);
        if (fill) {
            switch (hex2.length()) {
                case 1:
                    hex2 = "  " + hex2;
                    break;
                case 2:
                    hex2 = " " + hex2;
                    break;
                default:
                    // leave alone
            }
        }
        String dec2 = Integer.toString(value2);
        if (fill) {
            switch (dec2.length()) {
                case 1:
                    dec2 = "  " + dec2;
                    break;
                case 2:
                    dec2 = " " + dec2;
                    break;
                default:
                    // leave alone
            }
        }
        out.print(format(key, hex, dec, "", hex2, dec2, ""));
    }

    /**
     * Print a number as hex and as decimal to an output stream.
     * 
     * @param out the output stream
     * @param key the resource bundle key
     * @param value the value to print
     */
    protected void printNum(PrintStream out, String key, int value) {

        String s = charString(value);
        String hex = Integer.toHexString(value);
        if (fill) {
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
        }
        String dec = Integer.toString(value);
        if (fill) {
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
        }
        out.print(format(key, hex, dec, s));
    }

    /**
*      org.extex.ocpware.type.OcpProgram)
     */
    public void write(OutputStream stream, OcpProgram ocp) {

        PrintStream out = new PrintStream(stream);

        int length = ocp.getLength();
        if (length >= 0) {
            printNum(out, "Length", length);
        }
        printNum(out, "Input", ocp.getInput());
        printNum(out, "Output", ocp.getOutput());
        List<int[]> tables = ocp.getTables();
        printNum(0, out, "Tables", tables.size(), room(tables));
        List<int[]> states = ocp.getStates();
        printNum(0, out, "States", states.size(), room(states));

        if (tables.size() > 0) {
            out.println();

            for (int i = 0; i < tables.size(); i++) {
                int[] table = tables.get(i);
                printNum(-1, out, "Table", i, table.length);
            }

            out.println();

            for (int i = 0; i < tables.size(); i++) {
                int[] table = tables.get(i);

                for (int j = 0; j < table.length; j++) {
                    printNum(-1, out, "TableEntry", i, j);
                    print(out, "TableItem", table[j]);
                }
            }
        }

        out.println();

        for (int i = 0; i < states.size(); i++) {
            printNum(-1, out, "State", i, states.get(i).length);
        }

        out.println();

        for (int i = 0; i < states.size(); i++) {
            int[] instructions = states.get(i);
            boolean dis = true;

            for (int j = 0; j < instructions.length; j++) {
                printNum(-1, out, "StateEntry", i, j);

                if (dis) {
                    dis = disassemble(out, instructions, j);
                } else {
                    out.print("                    ");
                    print(out, "Instruction", instructions[j]);
                    dis = true;
                }
                out.println();
            }
        }
    }

}
