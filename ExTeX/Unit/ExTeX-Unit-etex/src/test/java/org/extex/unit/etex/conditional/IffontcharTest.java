/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.conditional;

import org.extex.unit.tex.conditional.ConditionalTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\iffontchar</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class IffontcharTest extends ConditionalTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(IffontcharTest.class);
    }


    public IffontcharTest() {

        super("iffontchar", "\\nullfont `x\\else");
        setConfig("etex-test");
    }

    /**
     * <testcase primitive="\iffontchar"> Test case checking that
     * <tt>\iffontchar</tt> needs an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            "\\iffontchar ",
            // --- output channel ---
            "Unexpected end of file while processing \\iffontchar");
    }

    /**
     * <testcase primitive="\iffontchar"> Test case checking that
     * <tt>\iffontchar</tt> needs a font argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(// --- input code ---
            "\\iffontchar x",
            // --- output channel ---
            "Missing font identifier");
    }

    /**
     * <testcase primitive="\iffontchar"> Test case checking that
     * <tt>\iffontchar</tt> needs a character token after the font argument.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError3() throws Exception {

        assertFailure(// --- input code ---
            "\\iffontchar \\nullfont",
            // --- output channel ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\iffontchar"> Test case checking that
     * <tt>\iffontchar</tt> needs a character number after the font argument.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError4() throws Exception {

        assertFailure(// --- input code ---
            "\\iffontchar \\nullfont \\relax",
            // --- output channel ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\iffontchar"> Test case checking that
     * <tt>\iffontchar</tt> needs a character number after the font argument.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError5() throws Exception {

        assertFailure(// --- input code ---
            "\\iffontchar \\nullfont x ",
            // --- output channel ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\iffontchar"> Test case checking that
     * <tt>\iffontchar</tt> expands the then branch for A in cmr10.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\font\\f cmr10 " + "\\iffontchar \\f 65 a\\else b\\fi\\end",
            // --- output channel ---
            "a" + TERM);
    }

    // TODO implement more primitive specific test cases
}
