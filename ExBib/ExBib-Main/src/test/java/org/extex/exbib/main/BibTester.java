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
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Calendar;
import java.util.Locale;

/**
 * This is a tester for ExBib.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BibTester {

    /**
     * Enumeration for the type of comparison.
     */
    public static enum check {
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
     */
    public static final String YEAR =
            Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

    /**
     * The field <tt>BANNER</tt> contains the default banner.
     */
    public static final String BANNER =
            "This is exbib, Version " + ExBib.VERSION + "\n";

    /**
     * Creates a new object.
     * 
     */
    public BibTester() {

        super();
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

        runTest("test", null, 1, check.EQ, msg, args);
    }

    /**
     * Run the command line test. The aux file is written temporarily in the
     * current directory under the name <tt>test.aux</tt>. The contents can
     * be given as argument.
     * 
     * @param basename the base name of the files to prepare and use
     * @param auxContents the contents of the aux file
     * @param exitCode the exit code
     * @param checkOut TODO
     * @param out the expected error output
     * @param args the invocation arguments
     * @return the instance used
     * 
     * @throws Exception in case of an error
     */
    protected ExBib runTest(String basename, String auxContents, int exitCode,
            check checkOut, String out, String... args) throws Exception {

        File aux = new File(basename + ".aux");
        if (auxContents != null) {
            Writer w = new FileWriter(aux);
            try {
                w.write(auxContents);
            } finally {
                w.close();
            }
        }

        Locale.setDefault(Locale.ENGLISH);
        PrintStream err = System.err;
        ExBib exBib = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setErr(new PrintStream(baos));
            exBib = new ExBib();
            assertEquals(exitCode, exBib.processCommandLine(args));
            if (out != null) {
                String s = baos.toString().replaceAll("\r", "");
                switch (checkOut) {
                    case EQ:
                        assertEquals(out, s);
                        break;
                    case START:
                        assertTrue("Fails to start with " + s, s
                            .startsWith(out));
                        break;
                    case REGEX:
                        assertTrue("Fails to match " + s, s.matches(out));
                        break;
                    case NONE:
                        // igonore
                }
            }
        } finally {
            System.setErr(err);
            if (exBib != null) {
                exBib.close();
            }
            aux.delete();
            new File(basename + ".bbl").delete();
            new File(basename + ".blg").delete();
        }
        return exBib;
    }

}
