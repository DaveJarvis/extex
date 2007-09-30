/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.file;

import org.extex.test.NoFlagsButImmediatePrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\openin</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class OpeninTest extends NoFlagsButImmediatePrimitiveTester {

    /**
     * The constant <tt>EMPTY_TEX</tt> contains the location of an empty file.
     */
    private static final String EMPTY_TEX =
            "../ExTeX-Unit-tex/src/test/resources/tex/empty.tex";

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(OpeninTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public OpeninTest(String arg) {

        super(arg, "openin", "1 " + EMPTY_TEX + " ");
    }

    /**
     * <testcase primitive="\openin"> Test case checking that a lonely
     * <tt>\openin</tt> leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof1() throws Exception {

        assertFailure(// --- input code ---
            "\\openin ",
            // --- log message ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\openin"> Test case checking that a lonely
     * <tt>\openin</tt> with an index leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof2() throws Exception {

        assertFailure(// --- input code ---
            "\\openin 2",
            // --- log message ---
            "Unexpected end of file while processing \\openin");
    }

}
