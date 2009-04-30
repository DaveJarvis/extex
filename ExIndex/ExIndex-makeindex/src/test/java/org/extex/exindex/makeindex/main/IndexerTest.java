/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for makeindex.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:7790 $
 */
public class IndexerTest {

    /**
     * Create an indexer.
     * 
     * @return the indexer
     * 
     * @throws IOException in case of an error
     */
    protected Makeindex makeIndexer() throws IOException {

        return new Makeindex();
    }

    /**
     * Run a test and compare the results.
     * 
     * @param in the input
     * @param out the standard output
     * @param log the log output
     * @param exit the exit code
     * 
     * @throws IOException in case of an error
     */
    protected void run(String in, String out, String log, int exit)
            throws IOException {

        run(in, null, out, log, exit);
    }

    /**
     * Run a test and compare the results.
     * 
     * @param in the input
     * @param style the content of the style file
     * @param out the standard output
     * @param log the log output
     * @param exit the exit code
     * @throws IOException in case of an error
     */
    protected void run(String in, String style, String out, String log, int exit)
            throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ByteArrayOutputStream es = new ByteArrayOutputStream();
        int ret = run(new String[]{"-q"}, in, os, es, style);
        assertEquals("return code", exit, ret);
        assertEquals("output", out, os.toString());
        assertEquals("log", log, es.toString());
    }

    /**
     * Run a test and compare the results.
     * 
     * @param args the arguments
     * @param in the input string
     * @param os the output stream
     * @param es the error stream
     * @param style the optional style file
     * 
     * @return the exit code
     * 
     * @throws IOException in case of an error
     */
    private int run(String[] args, String in, ByteArrayOutputStream os,
            ByteArrayOutputStream es, String style) throws IOException {

        InputStream inStream = System.in;
        PrintStream outStream = System.out;
        PrintStream errStream = System.err;
        File temp = null;
        try {
            System.setIn(new ByteArrayInputStream(in.getBytes()));
            System.setOut(new PrintStream(os));
            System.setErr(new PrintStream(es));
            Makeindex indexer = makeIndexer();
            if (style != null) {
                temp = File.createTempFile("style", ".ist", new File("target"));
                indexer.addStyle(temp.getAbsolutePath());
                FileWriter w = new FileWriter(temp);
                try {
                    w.write(style);
                } finally {
                    w.close();
                }
            }
            return indexer.run(args);
        } finally {
            System.setIn(inStream);
            System.out.flush();
            System.err.flush();
            System.setOut(outStream);
            System.setErr(errStream);
            if (temp != null) {
                temp.delete();
            }
        }
    }

    /**
     * <testcase> A null argument array leads to an error.</testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test(expected = IllegalArgumentException.class)
    public final void test01() throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ByteArrayOutputStream es = new ByteArrayOutputStream();
        run(null, "", os, es, null);
    }

    /**
     * <testcase> An empty argument array works without side effect. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test02() throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ByteArrayOutputStream es = new ByteArrayOutputStream();
        int ret = run(new String[]{}, "", os, es, null);
        assertEquals(0, ret);
    }

    /**
     * <testcase> An empty raw index leads to an empty theindex
     * environment.</testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test10() throws IOException {

        run("", "\\begin{theindex}\n\n\n\\end{theindex}\n", "", 0);
    }

    /**
     * <testcase> A single index entry is reproduced. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test11() throws IOException {

        run("\\indexentry{abc}{12}",
            "\\begin{theindex}\n\n  \\item abc, 12\n\n\\end{theindex}\n", "", 0);
    }

    /**
     * <testcase> Two single index entries for different keys are
     * reproduced.</testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test12() throws IOException {

        run("\\indexentry{def}{12} " + "\\indexentry{abc}{34}",
            "\\begin{theindex}\n\n  \\item abc, 34\n  \\item def, 12\n\n"
                    + "\\end{theindex}\n", "", 0);
    }

    /**
     * <testcase> Three single index entries for different keys are
     * reproduced.</testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test13() throws IOException {

        run("\\indexentry{ghi}{5} " + "\\indexentry{def}{12} "
                + "\\indexentry{abc}{34}",
            "\\begin{theindex}\n\n  \\item abc, 34\n  \\item def, 12\n"
                    + "  \\item ghi, 5\n\n\\end{theindex}\n", "", 0);
    }

    /**
     * <testcase> Three single index entries for the same key are
     * reproduced.</testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test14() throws IOException {

        run(
            "\\indexentry{abc}{5} " + "\\indexentry{abc}{12} "
                    + "\\indexentry{abc}{34}",
            "\\begin{theindex}\n\n  \\item abc, 5, 12, 34\n\n\\end{theindex}\n",
            "", 0);
    }

    /**
     * <testcase> A display string for an index entry is used when present.
     * </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test21() throws IOException {

        run("\\indexentry{abc@xyz}{12}",
            "\\begin{theindex}\n\n  \\item xyz, 12\n\n\\end{theindex}\n", "", 0);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test31() throws IOException {

        run(
            "\\indexentry{abc|xyz}{12}",
            "\\begin{theindex}\n\n  \\item abc, \\xyz{12}\n\n\\end{theindex}\n",
            "", 0);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test41() throws IOException {

        run("\\indexentry{abc|(}{1}\\indexentry{abc|)}{3}",
            "\\begin{theindex}\n\n  \\item abc, 1--3\n\n\\end{theindex}\n", "",
            0);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test42() throws IOException {

        run(
            "\\indexentry{abc|(xxx}{1}\\indexentry{abc|)xxx}{3}",
            "\\begin{theindex}\n\n  \\item abc, \\xxx{1--3}\n\n\\end{theindex}\n",
            "", 0);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test43() throws IOException {

        run(
            "\\indexentry{abc|(xxx}{1}" //
                    + "\\indexentry{abc|xxx}{2}" //
                    + "\\indexentry{abc|)xxx}{3}",
            "\\begin{theindex}\n\n  \\item abc, \\xxx{1--3}\n\n\\end{theindex}\n",
            "", 0);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test44() throws IOException {

        run(
            "\\indexentry{abc|(xxx}{1}" //
                    + "\\indexentry{abc|yyy}{2}" //
                    + "\\indexentry{abc|)xxx}{3}",
            "\\begin{theindex}\n\n  \\item abc, \\xxx{1--3}, \\yyy{2}\n\n\\end{theindex}\n",
            "", 0);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test45() throws IOException {

        run(
            "\\indexentry{abc|(}{1}" //
                    + "\\indexentry{abc|yyy}{2}" //
                    + "\\indexentry{abc|)}{3}",
            "\\begin{theindex}\n\n  \\item abc, 1--3, \\yyy{2}\n\n\\end{theindex}\n",
            "", 0);
    }

    /**
     * <testcase> A reference in a range is discarded.</testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void test46() throws IOException {

        run("\\indexentry{abc|(}{1}" //
                + "\\indexentry{abc}{2}" //
                + "\\indexentry{abc|)}{3}",
            "\\begin{theindex}\n\n  \\item abc, 1--3\n\n\\end{theindex}\n", "",
            0);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    @Ignore
    public final void test47() throws IOException {

        run("\\indexentry{abc|(}{1}",
            "\\begin{theindex}\n\n  \\item abc, 1\n\n\\end{theindex}\n", "xxx",
            0);
    }

    /**
     * <testcase> A single index entry is reproduced. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void testF0() throws IOException {

        run("\\indexentry{abc}{12}",
            "", "\\begin{theindex}\n\n  \\item abc, 12\n\n\\end{theindex}\n",
            "", 0);
    }

    /**
     * <testcase> A single index entry is reproduced. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void testF1() throws IOException {

        run("\\indexentry{abc}{12}", "preamble \"<preamble>\"\n" //
                + "postamble \"<postamble>\"\n" //
                + "item_0 \"<item_0>\"",
            "<preamble><item_0>abc, 12<postamble>", "", 0);
    }

}
