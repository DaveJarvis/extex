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
     * Creates a new object.
     */
    public BibTeXBaseTest() {

        super();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param wd
     * @param file
     * 
     * @throws IOException in case of an error
     */
    protected void runTest(String wd, String file) throws IOException {

        File result = new File(wd, file + ".result");
        if (!result.exists()) {
            assertTrue(result.toString() + " reference not found", false);
        }
        File bbl = new File(wd, file + ".bbl");
        ExBib exBib = new ExBib();
        File aux = new File(wd, file + ".aux");

        setupAux(aux, wd, file);

        PrintStream err = System.err;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            System.setErr(new PrintStream(baos));

            int code =
                    exBib.processCommandLine(new String[]{"-quiet", "-strict",
                            aux.toString()});
            assertEquals("", baos.toString());
            assertEquals(0, code);

            assertEquals(readFile(result), readFile(bbl));

        } finally {
            System.setErr(err);
            exBib.close();
            aux.delete();
            bbl.delete();
            new File(wd, file + ".blg").delete();
        }

    }

    /**
     * Create an appropriate aux file for testing.
     * 
     * @param aux the file to create
     * @param wd the working directory
     * @param name the name of the style
     * 
     * @throws IOException in case of an error
     */
    protected void setupAux(File aux, String wd, String name)
            throws IOException {

        FileWriter out = new FileWriter(aux);
        try {
            out.write("\\relax\n");
            out.write("\\citation{*}\n");
            out.write("\\bibstyle{" + wd + "/" + name + "}\n");
            out.write("\\bibdata{" + wd + "/xampl}\n");
        } finally {
            out.close();
        }
    }

    /**
     * <testcase> Apply the abbrev style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAbbrv() throws Exception {

        runTest("src/test/resources/bibtex/base", "abbrv");
    }

    /**
     * <testcase> Apply the alpha style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAlpha() throws Exception {

        runTest("src/test/resources/bibtex/base", "alpha");
    }

    /**
     * <testcase> Apply the plain style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPlain() throws Exception {

        runTest("src/test/resources/bibtex/base", "plain");
    }

    /**
     * <testcase> Apply the unsrt style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUnsrt() throws Exception {

        runTest("src/test/resources/bibtex/base", "unsrt");
    }

}
