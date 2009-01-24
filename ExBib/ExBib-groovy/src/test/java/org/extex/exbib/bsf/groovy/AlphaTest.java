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

package org.extex.exbib.bsf.groovy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import org.extex.exbib.bsf.BsfProcessor;
import org.extex.exbib.bsf.LogFormatter;
import org.extex.exbib.core.ExBib;
import org.junit.Test;

/**
 * This is a test suite for the {@link BsfProcessor} with a Groovy
 * configuration.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class AlphaTest {

    /**
     * Create a file in the target directory and fill it with some content.
     * 
     * @param name the name of the file
     * @param postfix the string to append
     * @param content the contents
     * 
     * @return the file
     * 
     * @throws IOException in case of an I/O error
     */
    public static File makeFile(String name, String postfix, String content)
            throws IOException {

        File file = File.createTempFile(name, postfix, new File("target"));
        Writer w = new PrintWriter(new FileWriter(file));
        try {
            w.write(content);
        } finally {
            w.close();
        }
        return file;
    }

    /**
     * Run a test.
     * 
     * @param bib the bib file
     * @param aux the aux file
     * @param expectedErr the expected error output
     * @param expectedOut the expected output
     * 
     * @throws IOException in case of an I/O error
     */
    private static void run(File bib, File aux, String expectedErr,
            String expectedOut) throws IOException {

        Properties prop = new Properties();
        prop.put(ExBib.PROP_PROCESSOR, "groovy");
        prop.put(ExBib.PROP_FILE, aux.toString());
        prop.put(ExBib.PROP_OUTFILE, "-");

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();

        Logger logger = Logger.getLogger("test");
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);
        Handler handler = new StreamHandler(errStream, new LogFormatter());
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);

        PrintStream out = System.out;
        PrintStream err = System.err;
        try {
            System.setOut(new PrintStream(outStream));
            System.setErr(new PrintStream(errStream));
            ExBib exBib = new ExBib(prop);
            exBib.setLogger(logger);
            boolean code = exBib.run();
            handler.flush();
            System.err.flush();
            assertEquals("error stream", expectedErr, errStream.toString()
                .replaceAll("\r", ""));
            assertTrue("ExBib has signaled a failure", code);
            System.out.flush();
            // TODO: for some reasons the newlines seem to be distorted under
            // Maven; thus all newlines are discarded
            assertEquals("output stream", expectedOut.replaceAll("\n", ""),
                outStream.toString().replaceAll("\n", ""));
            // assertEquals("output stream", expectedOut, outStream.toString()
            // .replaceAll("\r", ""));
        } finally {
            logger.removeHandler(handler);
            System.setOut(out);
            System.setErr(err);
            aux.delete();
            new File(aux.toString().replaceAll(".aux$", ".bbl")).delete();
            bib.delete();
        }
    }

    /**
     * <test> trivial test case </test>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        File bib =
                makeFile("test1", ".bib",
                    "@book{abc,author={Donald E. Knuth}, title={The {\\TeX}book}}\n");
        run(bib, makeFile("test1", ".aux", "\\citation{*}\n\\bibstyle{"
                + "src/test/resources/alpha.groovy" + "}\n\\bibdata{" + bib
                + "}\n"), //
            "empty publisher in abc\n" //
                    + "empty year in abc\n", //
            "\\begin{thebibliography}{Knu}\n" //
                    + "\n" //
                    + "\\bibitem[Knu]{abc}\n"
                    + "Donald~E. Knuth.\n"
                    + "\\newblock {\\em The {\\TeX}book}.\n"
                    + "\n"
                    + "\\end{thebibliography}\n");
    }
}
