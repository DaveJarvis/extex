/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Calendar;
import java.util.Locale;

import org.extex.cli.CLI;

/**
 * This is a tester for {@link ExBibUtilMain}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BibUtilTester {

    /**
     * Enumeration for the type of comparison.
     */
    public static enum Check {
        /**
         * The field <tt>NONE</tt> contains the do not compare.
         */
        NONE,
        /**
         * The field <tt>EQ</tt> contains the compare for equality.
         */
        EQ,
        /**
         * The field <tt>START</tt> contains the compare an initial segment.
         */
        START,
        /**
         * The field <tt>REGEX</tt> contains the compare against a regular
         * expression.
         */
        REGEX
    };

    /**
     * The field <tt>YEAR</tt> contains the current year as four-digit string.
     * This might not be accurate for a long running test started just before
     * the end of the year.
     */
    public static final String YEAR = Integer.toString(Calendar.getInstance()
        .get(Calendar.YEAR));

    /**
     * The field <tt>BANNER</tt> contains the default banner.
     */
    public static final String BANNER = "This is exbibutil, Version "
            + ExBibUtilMain.VERSION + "\n";

    /**
     * The field <tt>BANNER_DE</tt> contains the default banner.
     */
    public static final String BANNER_DE = "Dies ist exbibutil, Version "
            + ExBibUtilMain.VERSION + "\n";

    /**
     * Creates a new object.
     */
    public BibUtilTester() {

    }

    /**
     * Run the command line test and expect a failure.
     * 
     * @param msg the expected error output
     * @param args the invocation arguments
     * 
     * @throws Exception in case of an error
     */
    protected void runFailure(String msg, String... args) throws Exception {

        runTest("test", null, CLI.EXIT_FAIL, Check.EQ, msg, args);
    }

    /**
     * Run the command line test and expect a success.
     * 
     * @param msg the expected error output
     * @param args the invocation arguments
     * 
     * @throws Exception in case of an error
     */
    protected void runSuccess(String msg, String... args) throws Exception {

        runTest("test", null, CLI.EXIT_OK, Check.EQ, msg, args);
    }

    /**
     * Run the command line test. The bib file is written temporarily in the
     * current directory under the name <i>basename</i><tt>.bib</tt>. The
     * contents can be given as argument.
     * 
     * @param basename the base name of the files to prepare and use
     * @param bibContents the contents of the aux file
     * @param exitCode the exit code
     * @param checkOut the type of Check to use
     * @param out the expected error output
     * @param args the invocation arguments
     * 
     * @return the instance used
     * 
     * @throws Exception in case of an error
     */
    protected ExBibUtilMain runTest(String basename, String bibContents,
            int exitCode, Check checkOut, String out, String... args)
            throws Exception {

        File bib = new File(basename + ".bib");
        if (bibContents != null) {
            Writer w = new FileWriter(bib);
            try {
                w.write(bibContents);
            } finally {
                w.close();
            }
        }

        Locale.setDefault(Locale.ENGLISH);
        PrintStream err = System.err;
        ExBibUtilMain exBibUtil = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setErr(new PrintStream(baos));
            exBibUtil = new ExBibUtilMain();
            int code = exBibUtil.processCommandLine(args);
            if (exBibUtil != null) {
                exBibUtil.close();
            }
            if (out != null) {
                String s = baos.toString().replaceAll("\r", "");
                switch (checkOut) {
                    case EQ:
                        assertEquals(out, s);
                        break;
                    case START:
                        assertTrue("Fails to start with " + s,
                            s.startsWith(out));
                        break;
                    case REGEX:
                        assertTrue("Fails to match " + s, s.matches(out));
                        break;
                    case NONE:
                        // ignore
                    default:
                }
            }
            assertEquals("exit code", exitCode, code);
        } finally {
            System.setErr(err);
            if (exBibUtil != null) {
                exBibUtil.close();
            }
            if (bib.exists() && !bib.delete()) {
                assertTrue(bib.toString() + ": deletion failed", false);
            }
            new File(basename + ".out").delete();
        }
        return exBibUtil;
    }

}
