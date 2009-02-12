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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

/**
 * Test cases for <code>W2lConverter</code>.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class W2lConverterTest extends TestCase {

    /**
     * The encoding.
     */
    private static final String ENCODING = "ISO8859-1";

    /**
     * Test 01.
     * 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c01.xml",
            "../Sandbox-mgn/src/w2l/t01.fxml", "../Sandbox-mgn/src/w2l/t01.tex");
    }

    /**
     * Test 02.
     * 
     * @throws Exception if an error occurred.
     */
    public void test02() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c02.xml",
            "../Sandbox-mgn/src/w2l/t02.fxml", "../Sandbox-mgn/src/w2l/t02.tex");
    }

    /**
     * Test 03.
     * 
     * @throws Exception if an error occurred.
     */
    public void test03() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c03.xml",
            "../Sandbox-mgn/src/w2l/t03.fxml", "../Sandbox-mgn/src/w2l/t03.tex");
    }

    /**
     * Test 04.
     * 
     * @throws Exception if an error occurred.
     */
    public void test04() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c04.xml",
            "../Sandbox-mgn/src/w2l/t04.fxml", "../Sandbox-mgn/src/w2l/t04.tex");
    }

    /**
     * Test 05.
     * 
     * @throws Exception if an error occurred.
     */
    public void test05() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c05.xml",
            "../Sandbox-mgn/src/w2l/t05.fxml", "../Sandbox-mgn/src/w2l/t05.tex");
    }

    /**
     * Test 06.
     * 
     * @throws Exception if an error occurred.
     */
    public void test06() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c06.xml",
            "../Sandbox-mgn/src/w2l/t06.fxml", "../Sandbox-mgn/src/w2l/t06.tex");
    }

    /**
     * Test 07.
     * 
     * @throws Exception if an error occurred.
     */
    public void test07() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c07.xml",
            "../Sandbox-mgn/src/w2l/t07.fxml", "../Sandbox-mgn/src/w2l/t07.tex");
    }

    /**
     * Test 08.
     * 
     * @throws Exception if an error occurred.
     */
    public void test08() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c08.xml",
            "../Sandbox-mgn/src/w2l/t08.fxml", "../Sandbox-mgn/src/w2l/t08.tex");
    }

    /**
     * Test 09.
     * 
     * @throws Exception if an error occurred.
     */
    public void test09() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c09.xml",
            "../Sandbox-mgn/src/w2l/t09.fxml", "../Sandbox-mgn/src/w2l/t09.tex");
    }

    /**
     * Test 10.
     * 
     * @throws Exception if an error occurred.
     */
    public void test10() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c10.xml",
            "../Sandbox-mgn/src/w2l/t10.fxml", "../Sandbox-mgn/src/w2l/t10.tex");
    }

    /**
     * Test 11.
     * 
     * @throws Exception if an error occurred.
     */
    public void test11() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c11.xml",
            "../Sandbox-mgn/src/w2l/t11.fxml", "../Sandbox-mgn/src/w2l/t11.tex");
    }

    /**
     * Test 12.
     * 
     * @throws Exception if an error occurred.
     */
    public void test12() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c12.xml",
            "../Sandbox-mgn/src/w2l/t12.fxml", "../Sandbox-mgn/src/w2l/t12.tex");
    }

    /**
     * Test 13.
     * 
     * @throws Exception if an error occurred.
     */
    public void test13() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c13.xml",
            "../Sandbox-mgn/src/w2l/t13.fxml", "../Sandbox-mgn/src/w2l/t13.tex");
    }

    /**
     * Test 14.
     * 
     * @throws Exception if an error occurred.
     */
    public void test14() throws Exception {

        runW2l("../Sandbox-mgn/src/w2l/c14.xml",
            "../Sandbox-mgn/src/w2l/t14.fxml", "../Sandbox-mgn/src/w2l/t14.tex");
    }

    /**
     * Run w2l and check the result.
     * 
     * @param stex The name of the tex file.
     * @param sfxml The name of the fxml file.
     * @param sconfig The name of the config file.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private void runW2l(String sconfig, String sfxml, String stex)
            throws FileNotFoundException,
                IOException,
                UnsupportedEncodingException {

        W2lConverter w2l = new W2lConverter();
        w2l.setConfigStream(new FileInputStream(sconfig));

        w2l.setSourceStream(new FileInputStream(sfxml));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        w2l.setOutput(out);

        w2l.convert();

        String sout = out.toString(ENCODING);
        String tex = readFile(stex);
        assertNotNull(sout);
        assertNotNull(tex);
        assertEquals(tex, sout);
    }

    /**
     * Read a file.
     * 
     * @param filename The file name.
     * @return Return the file as String.
     * @throws IOException if an IO-error occurred.
     */
    private static String readFile(String filename) throws IOException {

        BufferedReader in =
                new BufferedReader(new InputStreamReader(new FileInputStream(
                    filename), ENCODING));

        StringBuffer buf = new StringBuffer();

        String line;
        while ((line = in.readLine()) != null) {
            buf.append(line).append("\n");
        }

        return buf.toString();
    }

}
