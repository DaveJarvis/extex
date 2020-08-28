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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.extex.ocpware.engine.IllegalOpCodeException;
import org.extex.ocpware.type.OcpArgumentType;
import org.extex.ocpware.type.OcpCode;
import org.extex.ocpware.type.OcpProgram;

/**
 * This writer for an &Omega;CP is meant for pretty-printing.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpExTeXWriter extends AbstractWriter {


    public OcpExTeXWriter() {

    }

    /**
     * Fill a list of labels for all instructions which are addressed within
     * some goto statements in the code.
     * 
     * @param instructions the list of instructions
     * @param labels the list of labels for each instruction
     * 
     * @throws IllegalOpCodeException in case of an illegal op code
     */
    private void collectLabels(int[] instructions, String[] labels)
            throws IllegalOpCodeException {

        int no = 1;

        for (int codePointer = 0; codePointer < instructions.length;) {

            int arg = instructions[codePointer++];

            OcpCode code = OcpCode.get(arg >> OcpCode.OPCODE_OFFSET);
            if (code == null) {
                throw new IllegalOpCodeException(arg >> OcpCode.OPCODE_OFFSET);
            }

            OcpArgumentType[] args = code.getArguments();

            boolean first = true;

            for (OcpArgumentType a : args) {
                if (first) {
                    first = false;
                    arg = arg & OcpCode.ARGUMENT_BIT_MASK;
                } else {
                    arg =
                            instructions[codePointer++]
                                    & OcpCode.ARGUMENT_BIT_MASK;
                }
                if (a == OcpArgumentType.LABEL) {
                    if (labels[arg] == null) {
                        labels[arg] = "_" + Integer.toString(no++);
                    }
                    // case 's':
                    // out.print("_S" + Integer.toString(arg));
                    // break;
                }
            }
        }
    }

    /**
     * Disassemble a single instruction.
     * 
     * @param out the output stream
     * @param instructions the list of instructions
     * @param cp the code pointer
     * @param stateNames the list of names for each state
     * @param labels the list of labels for each instruction
     * 
     * @return the index of the next unprocessed instruction
     * 
     * @throws IllegalOpCodeException in case of an illegal op code
     */
    private int disassemble(PrintStream out, int[] instructions, int cp,
            String[] stateNames, String[] labels) throws IllegalOpCodeException {

        int codePointer = cp;
        int arg = instructions[codePointer];

        OcpCode code = OcpCode.get(arg >> OcpCode.OPCODE_OFFSET);
        if (code == null) {
            throw new IllegalOpCodeException(arg >> OcpCode.OPCODE_OFFSET);
        }

        String label = labels[codePointer++];
        out.print("  ");
        if (label == null) {
            out.print("              ");
        } else {
            out.print(label);
            out.print(":");
            if (label.length() < 14) {
                out.print("                ".substring(0, 13 - label.length()));
            } else {
                out.print("\n                  ");
            }
        }

        String s = code.getInstruction();
        OcpArgumentType[] args = code.getArguments();
        out.print(s);
        if (args.length != 0) {
            int len = s.length();
            out.print("                ".substring(0, len > 16 ? 1 : 16 - len));
        }

        boolean first = true;

        for (OcpArgumentType a : args) {
            if (first) {
                first = false;
                arg = arg & OcpCode.ARGUMENT_BIT_MASK;
            } else {
                out.print(", ");
                arg = instructions[codePointer++] & OcpCode.ARGUMENT_BIT_MASK;
            }
            if (a == OcpArgumentType.NUMBER) {
                out.print(Integer.toString(arg));
            } else if (a == OcpArgumentType.CHARACTER) {
                out.print("0x" + Integer.toHexString(arg));
            } else if (a == OcpArgumentType.LABEL) {
                out.print(labels[arg]);
            } else if (a == OcpArgumentType.STATE) {
                out.print(stateNames[arg]);
            } else {
                out.print("???");
            }
        }

        out.println();

        return codePointer;
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
            StringBuilder sb = new StringBuilder(",`");
            sb.append((char) value);
            sb.append('\'');
            s = sb.toString();
        }
        out.print(format(key,
            Integer.toHexString(value), Integer.toString(value), s));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.writer.OcpOmegaWriter#write(java.io.OutputStream,
     *      org.extex.ocpware.type.OcpProgram)
     */
    public void write(OutputStream stream, OcpProgram ocp) throws IOException {

        PrintStream out = new PrintStream(stream);

        int length = ocp.getLength();
        if (length >= 0) {
            print(out, "Length", length);
        }
        List<int[]> tables = ocp.getTables();
        print(out, "NO_TABLES", tables.size());
        print(out, "ROOM_TABLES", room(tables));
        List<int[]> states = ocp.getStates();
        print(out, "NO_STATES", states.size());
        print(out, "ROOM_STATES", room(states));

        print(out, "Input", ocp.getInput());
        print(out, "Output", ocp.getOutput());

        if (tables.size() > 0) {
            out.append("\ntables:");

            for (int i = 0; i < tables.size(); i++) {
                int[] table = tables.get(i);
                print(out, "TABLE", i);
                print(out, "TABLE_ENTRIES", table.length);

                boolean first = true;
                for (int j = 0; j < table.length; j++) {
                    if (first) {
                        first = false;
                        out.append("  ");
                    } else if (j % 4 == 0) {
                        out.append(",\n  ");
                    } else {
                        out.append(", ");
                    }
                    String hex = Integer.toHexString(table[j]);
                    out.append("0x0000000".substring(0, hex.length() < 4
                            ? 6 - hex.length()
                            : 2));
                    out.append(hex);
                }
                out.append("}\n");
            }
        }

        String[] stateNames = new String[states.size()];
        for (int i = 0; i < states.size(); i++) {
            stateNames[i] = "_S" + Integer.toString(i);
        }
        stateNames[0] = "INITIAL";

        for (int i = 0; i < states.size(); i++) {
            int[] instructions = states.get(i);
            out.append("\nState ");
            out.append(stateNames[i]);
            out.append(":");
            print(out, "ENTRIES", instructions.length);

            String[] labels = new String[instructions.length];
            collectLabels(instructions, labels);

            for (int codePointer = 0; codePointer < instructions.length;) {
                codePointer =
                        disassemble(out, instructions, codePointer, stateNames,
                            labels);
            }
        }

    }
}
