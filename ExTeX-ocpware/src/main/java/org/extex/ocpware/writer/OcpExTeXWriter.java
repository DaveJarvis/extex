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

import org.extex.ocpware.type.OcpProgram;

/**
 * This writer for an &Omega;CP is meant for pretty-printing.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpExTeXWriter extends OcpOmegaWriter {

    private static int[] noArgs = {//
            0, // 1 RIGHT_OUTPUT
                    1, // 2 RIGHT_NUM
                    1, // 3 RIGHT_CHAR
                    1, // 4 RIGHT_LCHAR
                    2, // 5 RIGHT_SOME
                    0, // 6 PBACK_OUTPUT
                    1, // 7 PBACK_NUM
                    1, // 8 PBACK_CHAR
                    1, // 9 PBACK_LCHAR
                    2, // 10 PBACK_SOME
                    0, // 11 ADD
                    0, // 12 SUB
                    0, // 13 MULT
                    0, // 14 DIV
                    0, // 15 MOD
                    0, // 16 LOOKUP
                    1, // 17 PUSH_NUM
                    1, // 18 PUSH_CHAR
                    1, // 19 PUSH_LCHAR
                    1, // 20 STATE_CHANGE
                    1, // 21 STATE_PUSH
                    1, // 22 STATE_POP
                    0, // 23 LEFT_START
                    0, // 24 LEFT_RETURN
                    0, // 25 LEFT_BACKUP
                    1, // 26 GOTO
                    2, // 27 GOTO_NE
                    2, // 28 GOTO_EQ
                    2, // 29 GOTO_LT
                    2, // 30 GOTO_LE
                    2, // 31 GOTO_GT
                    2, // 32 GOTO_GE
                    1, // 33 GOTO_NO_ADVANCE
                    1, // 34 GOTO_BEG
                    1, // 35 GOTO_END
                    0 // 36 STOP
            };

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.writer.OcpOmegaWriter#write(java.io.OutputStream,
     *      org.extex.ocpware.type.OcpProgram)
     */
    @Override
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
                String hex = Integer.toHexString(j);
                out.print("           ".substring(hex.length()));
                out.print(hex);
                out.print("  ");

                if (dis) {
                    dis = disassemble(out, instructions, j);
                } else {
                    out.print("                ");
                    print(out, "INSTRUCTION", instructions[j]);
                    dis = true;
                }
                out.println();
            }
        }

    }
}
