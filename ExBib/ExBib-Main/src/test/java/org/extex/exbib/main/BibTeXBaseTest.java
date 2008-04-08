/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

import org.junit.Test;

/**
 * This is a test suite.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class BibTeXBaseTest {

    /**
     * The field <tt>DATA_DIR</tt> contains the directory containing database,
     * styles and results.
     */
    private static final String DATA_DIR = "src/test/resources/bibtex/base";

    /**
     * Read a text file and return its contents.
     * 
     * @param file the file to read
     * 
     * @return the contents of the file
     * 
     * @throws IOException in case of an error
     */
    public static String readFile(File file) throws IOException {

        StringBuilder sb = new StringBuilder();
        Reader r = new FileReader(file);
        for (int c = r.read(); c >= 0; c = r.read()) {
            if (c != '\r') {
                sb.append((char) c);
            }
        }

        return sb.toString();
    }

    /**
     * Run a test case.
     * 
     * @param aux the aux file
     * @param result the file containing the expected result
     * 
     * @throws IOException in case of an error
     */
    public static void runTest(File aux, File result) throws IOException {

        if (!result.exists()) {
            assertTrue(result.toString() + " reference not found", false);
        }
        File bbl = new File(aux.getParent(), //
            aux.getName().replaceAll(".aux$", ".bbl"));

        PrintStream err = System.err;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            System.setErr(new PrintStream(baos));

            int code =
                    ExBib.commandLine(new String[]{"-quiet", "-strict",
                            aux.toString()});
            assertEquals("", baos.toString());
            assertEquals(0, code);

            assertEquals(readFile(result), readFile(bbl));

        } finally {
            System.setErr(err);
            bbl.delete();
            new File(aux.getParent(), //
                aux.getName().replaceAll(".aux$", ".blg")).delete();
        }
    }

    /**
     * Create an aux file and run a test case.
     * 
     * @param style the style file
     * @param data the data file
     * @param citation the citations (comma separated keys
     * @param result the result file
     * 
     * @throws IOException in case of an error
     */
    protected static void runTest(String style, String data, String citation,
            File result) throws IOException {

        File aux = new File("test.aux");
        FileWriter out = new FileWriter(aux);
        try {
            out.write("\\relax\n" //
                    + "\\citation{" + citation + "}\n" //
                    + "\\bibstyle{" + style + "}\n" //
                    + "\\bibdata{" + data + "}\n");
        } finally {
            out.close();
        }

        try {
            runTest(aux, result);
        } finally {
            aux.delete();
        }
    }

    /**
     * Creates a new object.
     */
    public BibTeXBaseTest() {

        super();
    }

    /**
     * Run a test.
     * 
     * @param style the style
     * 
     * @throws IOException in case of an I/O error
     */
    private void runTest(String style) throws IOException {

        runTest(DATA_DIR + "/" + style, //
            DATA_DIR + "/xampl", //
            "*", //
            new File(DATA_DIR, style + ".result"));
    }

    /**
     * <testcase> Apply the abbrev style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAbbrv() throws Exception {

        runTest("abbrv");
    }

    /**
     * <testcase> Apply the alpha style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAlpha() throws Exception {

        runTest("alpha");
    }

    /**
     * <testcase> Apply the plain style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPlain() throws Exception {

        runTest("plain");
    }

    /**
     * <testcase> Apply the unsrt style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUnsrt() throws Exception {

        runTest("unsrt");
    }

}
