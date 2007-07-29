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
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpExTeXWriter extends OcpOmegaWriter {

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
