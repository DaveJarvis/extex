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

package org.extex.w2l;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * Test cases for <code>W2lConverter</code>.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class W2lConverterTest extends TestCase {

    /**
     * Test 01.
     *
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        W2lConverter w2l = new W2lConverter();
        w2l.setConfigStream(new FileInputStream(
                "../Sandbox-mgn/src/w2l/c01.xml"));

        w2l.setSourceStream(new FileInputStream(
                "../Sandbox-mgn/src/w2l/t01.fxml"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        w2l.setOutput(out);

        w2l.convert();

        String sout = out.toString("ISO8859-1");
        String tex = readFile("../Sandbox-mgn/src/w2l/t01.tex");
        assertNotNull(sout);
        assertNotNull(tex);
        assertEquals(tex, sout);
    }

    /**
     * Read a file.
     *
     * @param filename  The file name.
     * @return Return the file as String.
     * @throws IOException if an IO-error occurred.
     */
    private static String readFile(String filename) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(filename));

        StringBuffer buf = new StringBuffer();

        String line;
        while ((line = in.readLine()) != null) {
            buf.append(line).append("\n");
        }

        return buf.toString();
    }

}
