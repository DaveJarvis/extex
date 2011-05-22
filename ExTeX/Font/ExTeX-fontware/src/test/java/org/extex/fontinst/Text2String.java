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

package org.extex.fontinst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Read a text file and store each line in the java string syntax.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class Text2String {

    /**
     * main.
     * 
     * @param args The command line.
     * @throws Exception if an error occurred.
     */
    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.err.println("call org.extex.fontinst.Text2String <file>");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        BufferedWriter out =
                new BufferedWriter(new FileWriter(args[0] + ".javatxt"));

        String line;
        boolean firstline = true;
        while ((line = in.readLine()) != null) {
            if (!firstline) {
                out.write(" + ");
            } else {
                out.write("String text = ");
            }
            firstline = false;
            out.write('"');
            String xline = line.replaceAll("\\\\", "\\\\\\\\");
            xline = xline.replaceAll("\\\"", "\\\\\"");
            out.write(xline);
            out.write("\\n\"\n");
        }
        out.write(";");

        in.close();
        out.close();
    }

}
