/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bsf.jacl;

import org.extex.exbib.bsf.LogFormatter;
import org.extex.exbib.core.ExBib;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Properties;
import java.util.logging.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is a test suite for the {@link org.extex.exbib.bsf.BsfProcessor} with a
 * Jacl configuration.
 *
 * <p>
 *   Ignored: The file $HOME/.m2/repository/jacl/jacl/1.2.6/jacl-1.2.6.jar
 *   contains tcl/lang/library/init.tcl, which is not currently made
 *   available to the unit test. Extracting jacl-1.2.6.jar prior to running
 *   the test should allow the test to find the resource. It is not known
 *   whether the test will run successfully with the resource in place.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
@Ignore
public class JaclTest {

    /**
     * Create a file and fill it with some content.
     * 
     * @param name the name of the file
     * @param content the contents
     * 
     * @throws IOException in case of an I/O error
     */
    public static void makeFile(String name, String content) throws IOException {
        try( Writer w = new PrintWriter( new FileWriter( name ) ) ) {
            w.write( content );
        }
    }

    /**
     * Run some Tcl code and compare the result.
     * 
     * @param tclCode the Tcl code to evaluate
     * @param expected the expected result
     * @param bibEntries the contents of the bib file
     * 
     * @throws IOException just in case
     */
    private void runTest(String tclCode, String expected, String bibEntries)
            throws IOException {

        String tcl = "target/test.tcl";
        makeFile(tcl, "package require java\n" + tclCode);
        String bib = "target/test.bib";
        makeFile(bib, bibEntries);
        String aux = "target/test.aux";
        makeFile(aux, "\\citation{*}\n\\bibstyle{" + tcl
                + "}\n\\bibdata{target/test.bib}\n");
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        Logger logger = Logger.getLogger(getClass().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);
        Handler handler = new StreamHandler(errStream, new LogFormatter());
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);
        ConsoleHandler handler2 = new ConsoleHandler();
        handler2.setFormatter(new LogFormatter());
        logger.addHandler(handler2);
        PrintStream out = System.out;
        try {
            System.setOut(new PrintStream(outStream));
            Properties prop = new Properties();
            prop.put(ExBib.PROP_PROCESSOR, "jacl");
            prop.put(ExBib.PROP_FILE, aux);
            ExBib exBib = new ExBib(prop);
            exBib.setLogger(logger);
            boolean code = exBib.run();
            System.err.flush();
            assertEquals("", errStream.toString().replaceAll("\r", ""));
            assertTrue("ExBib.run() failed", code);
            assertEquals(expected, outStream.toString().replaceAll("\r", ""));
        } finally {
            System.setOut(out);
            new File(aux).delete();
            new File(aux.replaceAll(".aux$", ".bbl")).delete();
            new File(tcl).delete();
            new File(bib).delete();
        }
    }

    /**
     * <test>The parameter minCrossrefs from bibDB can be read.</test>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        runTest(
            "set bibDB [bsf lookupBean \"bibDB\"]\n" //
                    + "puts [$bibDB getMinCrossrefs]\n", //
            "2\n",
            "@book{abc,author={Donald E. Knuth, title={The {\\TeX}book}}}\n");
    }

    /**
     * <test>The macro names from bibDB can be read.</test>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        runTest(
            "set bibDB [bsf lookupBean \"bibDB\"]\n" //
                    + "set x [$bibDB getMacroNames]\n" //
                    + "puts [$x size]\n" //
                    + "puts [[$x get 0] toString]\n", //
            "1\nabc\n", //
            "@string{abc={ABC}}" //
                    + "@book{xyz,author={Donald E. Knuth, title={The {\\TeX}book}}}\n");
    }

    /**
     * <test>The entries from bibDB can be read.</test>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        runTest(
            "set bibDB [bsf lookupBean \"bibDB\"]\n" //
                    + "set x [$bibDB getEntries]\n" //
                    + "puts [$x size]\n" //
                    + "puts [[$x get 0] toString]\n", //
            "1\n@book{xyz,...}\n", //
            "@string{abc={ABC}}" //
                    + "@book{xyz,author={Donald E. Knuth, title={The {\\TeX}book}}}\n");
    }

}
