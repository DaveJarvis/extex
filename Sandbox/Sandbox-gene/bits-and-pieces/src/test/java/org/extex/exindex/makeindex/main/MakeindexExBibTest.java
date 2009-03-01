/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exindex.makeindex.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;

import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MakeindexExBibTest {

    /**
     * The field <tt>BANNER</tt> contains the expected banner.
     */
    private static final String BANNER =
            "This is exindex version " + Makeindex.VERSION + " (revision "
                    + Makeindex.REVISION + ")\n";

    /**
     * Run a single test case.
     * 
     * @param args the command line arguments
     * @param expectedErr the expected error output
     * @param expectedOut the expected standard output
     * @param exitCode the expected exit code
     */
    @SuppressWarnings("boxing")
    private static void run(String[] args, String expectedErr,
            String expectedOut, int exitCode) {

        InputStream in = System.in;
        PrintStream err = System.err;
        PrintStream out = System.out;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        int exit = Integer.MIN_VALUE;
        try {
            System.setIn(new ByteArrayInputStream("".getBytes()));
            System.setErr(new PrintStream(errStream));
            System.setOut(new PrintStream(outStream));
            exit = new Makeindex().run(args);
        } finally {
            System.setIn(in);
            System.err.flush();
            System.out.flush();
            System.setErr(err);
            System.setOut(out);
        }
        assertEquals(expectedErr, errStream.toString().replaceAll("\r", ""));
        assertEquals(expectedOut, outStream.toString().replaceAll("\r", ""));
        assertEquals(exitCode, exit);
    }

    /**
     * Create a file and fill it with contents; then run a usual test.
     * 
     * @param args the command line arguments
     * @param f the name of the file
     * @param input the input
     * @param log the expected log output
     * @param output the expected output
     * 
     * @throws IOException in case of an I/O error
     */
    private void runOnFile(String[] args, String f, String input, String log,
            String output) throws IOException {

        Writer w = new BufferedWriter(new FileWriter(f));
        File ind = new File(f.replaceAll(".idx$", "") + ".ind");
        try {
            w.write(input);
        } finally {
            w.close();
        }
        Reader r = null;
        try {
            run(args, log, "", 0);
            assertTrue("Missing " + ind.toString(), ind.exists());
            r = new FileReader(ind);
            StringBuilder buffer = new StringBuilder();
            for (int c = r.read(); c >= 0; c = r.read()) {
                buffer.append((char) c);
            }
            assertEquals(output, buffer.toString());
        } finally {
            new File(f).delete();
            ind.delete();
            new File(f.replaceAll(".idx$", "") + ".ilg").delete();
            if (r != null) {
                r.close();
            }
        }
    }

    /**
     * <testcase> An index can be sorted. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void test1() throws IOException {

        Reader r = null;
        try {
            run(
                new String[]{"src/test/resources/makeindex/exbib-manual.idx",
                        "-s", "src/test/resources/makeindex/doc.ist", "-out",
                        "target/exbib-manual.ind"}, //
                BANNER
                        + "Scanning style file src/test/resources/makeindex/doc.ist...done (7 attributes\n"
                        + "redefined, 0 ignored).\n"
                        + "Scanning input file\n"
                        + "src/test/resources/makeindex/exbib-manual.idx...done (714 entries accepted, 0\n"
                        + "rejected)\n"
                        + "Sorting...done (5958 comparisons).\n"
                        + "Generating output file target/exbib-manual.ind...done (0 entries written, 0\n"
                        + "warnings).\n"
                        + "Output written in target/exbib-manual.ind.\n"
                        + "Transcript written in src/test/resources/makeindex/exbib-manual.ilg.\n",
                "", 0);
            File ind = new File("target/exbib-manual.ind");
            assertTrue("Missing " + ind.toString(), ind.exists());
            r = new BufferedReader(new FileReader(ind));
            StringBuilder buffer = new StringBuilder();
            for (int c = r.read(); c >= 0; c = r.read()) {
                buffer.append((char) c);
            }
            r.close();
            r = null;
            r = new BufferedReader(new FileReader(//
                "src/test/resources/makeindex/exbib-manual.ind"));
            StringBuilder expected = new StringBuilder();
            for (int c = r.read(); c >= 0; c = r.read()) {
                expected.append((char) c);
            }
            assertEquals(expected.toString(), buffer.toString());
        } finally {
            if (r != null) {
                r.close();
            }
            new File("target/exbib-manual.ind").delete();
        }
    }

}
