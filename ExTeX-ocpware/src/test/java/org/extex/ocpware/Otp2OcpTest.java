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

package org.extex.ocpware;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import org.junit.Test;

/**
 * Test suite for outocp.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class Otp2OcpTest {

    /**
     * Driver for the test cases.
     * 
     * @param args the command line arguments
     * @param expectedErr the expected value on the error stream
     * @param expectedOut the expected value on the output stream
     */
    private void run(String[] args, String expectedErr, String expectedOut) {

        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(stdout);
        PrintStream err = new PrintStream(stderr);
        Otp2Ocp.main(args, out, err);
        out.close();
        err.close();
        assertEquals("stderr", expectedErr, stderr.toString().replaceAll("\r",
            ""));
        assertEquals("stdout", expectedOut, stdout.toString().replaceAll("\r",
            ""));
    }

    /**
     * Test case checking that a missing file name is reported correctly.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{}, "otp2ocp: No file given\n", "");
    }

    /**
     * Test case checking that a missing file name is reported correctly in
     * German.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1de() throws Exception {

        Locale.setDefault(Locale.GERMAN);
        run(new String[]{}, "otp2ocp: Es wurde keine Datei angegeben\n", "");
    }

    /**
     * Test case checking that too many arguments are reported correctly.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"x", "x", "x"}, "otp2ocp: Too many arguments\n", "");
    }

    /**
     * Test case checking that too many arguments are reported correctly in
     * German.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2de() throws Exception {

        Locale.setDefault(Locale.GERMAN);
        run(new String[]{"x", "x", "x"}, "otp2ocp: Zu viele Argumente\n", "");
    }

    /**
     * Test case checking that a non existing file is reported correctly.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        run(new String[]{"nonexistentFile"},
            "otp2ocp: File nonexistentFile not found\n", "");
    }

    /**
     * Test case checking that a non existing file is reported correctly in
     * German.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3de() throws Exception {

        Locale.setDefault(Locale.GERMAN);
        run(new String[]{"nonexistentFile"},
            "otp2ocp: Datei nonexistentFile nicht gefunden\n", "");
    }

}
