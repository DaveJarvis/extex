/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.doctools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.StringWriter;

import org.extex.doctools.PomCollector;
import org.extex.doctools.exceptions.MissingArgumentException;
import org.extex.doctools.exceptions.UnknownArgumentException;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the pom collector.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PomCollectorTest {

    /**
     * The field {@code EXPECTED_DOT} contains the result for analyzing the
     * current directory.
     */
    private static final String EXPECTED_DOT =
            "%%\n\\begin{PomList}\n"
                    + "  \\begin{Pom}{org.extex}{doc-tools}{Doc Tools}\\end{Pom}\n"
                    + "\\end{PomList}\n%%\n";

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test1() throws Exception {

        StringWriter w = new StringWriter();
        new PomCollector().run(w);
        assertEquals("%%\n\\begin{PomList}\\end{PomList}\n%%\n",
            w.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test2() throws Exception {

        PomCollector main = new PomCollector();
        main.addBase(".");
        main.omit(".svn");
        StringWriter w = new StringWriter();
        main.run(w);
        assertEquals(EXPECTED_DOT, w.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void testAll() throws Exception {

        PomCollector main = new PomCollector();
        main.addBase("../..");
        main.omit(".svn");
        StringWriter w = new StringWriter();
        main.run(w);
        assertEquals("%%\n\\begin{PomList}??\\end{PomList}\n%%\n", w.toString()
            .replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCLI01() throws Exception {

        PrintStream out = System.out;
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(myOut));
            PomCollector main = new PomCollector();
            main.omit(".svn");
            main.run(new String[]{"."});
            System.out.flush();
        } finally {
            System.setOut(out);
        }

        assertEquals(EXPECTED_DOT, myOut.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCLI02() throws Exception {

        PrintStream out = System.out;
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(myOut));
            PomCollector main = new PomCollector();
            main.omit(".svn");
            main.run(new String[]{"-", "."});
            System.out.flush();
        } finally {
            System.setOut(out);
        }

        assertEquals(EXPECTED_DOT, myOut.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCLI03() throws Exception {

        PrintStream out = System.out;
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(myOut));
            PomCollector main = new PomCollector();
            main.omit(".svn");
            main.run(new String[]{"-out", "-", "."});
            System.out.flush();
            assertNull(main.getOutput());
        } finally {
            System.setOut(out);
        }

        assertEquals(EXPECTED_DOT, myOut.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCLI04() throws Exception {

        PrintStream out = System.out;
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(myOut));
            PomCollector main = new PomCollector();
            main.omit(".svn");
            main.run(new String[]{"-out", "", "."});
            System.out.flush();
            assertNull(main.getOutput());
        } finally {
            System.setOut(out);
        }

        assertEquals(EXPECTED_DOT, myOut.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCLI05() throws Exception {

        PrintStream out = System.out;
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(myOut));
            PomCollector main = new PomCollector();
            main.run(new String[]{"-omit", ".svn", "-out", "", "."});
            System.out.flush();
        } finally {
            System.setOut(out);
        }

        assertEquals(EXPECTED_DOT, myOut.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCLI11() throws Exception {

        PrintStream out = System.out;
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(myOut));
            assertEquals(0, PomCollector.mainFacade(new String[]{"-omit", ".svn", "."}));
            System.out.flush();
        } finally {
            System.setOut(out);
        }

        assertEquals(EXPECTED_DOT, myOut.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCLI12() throws Exception {

        PrintStream out = System.out;
        PrintStream err = System.err;
        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(myOut));
            System.setErr(new PrintStream(myOut));
            assertEquals(-1, PomCollector.mainFacade(new String[]{"-omit"}));
        } finally {
            System.setOut(out);
            System.setErr(err);
        }

        assertEquals("Option `-omit needs an argument.\n",
            myOut.toString().replaceAll("\r", ""));
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnknownArgumentException.class)
    public final void testError1() throws Exception {

        PomCollector main = new PomCollector();
        main.run(new String[]{"-xyzzy"});
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentException.class)
    public final void testError2() throws Exception {

        PomCollector main = new PomCollector();
        main.run(new String[]{"-out"});
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentException.class)
    public final void testError3() throws Exception {

        PomCollector main = new PomCollector();
        main.run(new String[]{"-omit"});
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentException.class)
    public final void testError4() throws Exception {

        PomCollector main = new PomCollector();
        main.run(new String[]{"-xsl"});
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = MissingArgumentException.class)
    public final void testError5() throws Exception {

        PomCollector main = new PomCollector();
        main.run(new String[]{"-"});
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = FileNotFoundException.class)
    public final void testErrorXls1() throws Exception {

        PomCollector main = new PomCollector();
        main.run(new String[]{"-xsl", "this.does.not.exist"});
    }

    /**
     * Test method for {@link org.extex.doctools.PomCollector#run()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = FileNotFoundException.class)
    public final void testErrorXls2() throws Exception {

        PomCollector main = new PomCollector();
        main.setXslt("this.does.not.exist");
        main.run(new String[]{"."});
    }

}
