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

package org.extex.unit.tex.macro.util;

import org.extex.test.ExTeXLauncher;

/**
 * This is a test suite for the macro code defined with the primitive
 * <tt>\def</tt> or friends.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MacroCodeTest extends ExTeXLauncher {

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public MacroCodeTest(String arg) {

        super(arg);
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1{-#1-}"
                    + "\\abc987\\end",
            // --- output channel ---
            "-9-87" + TERM);
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1#2{-#1-#2-}"
                    + "\\abc987\\end",
            // --- output channel ---
            "-9-8-7" + TERM);
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1x#2{-#1-#2-}"
                    + "\\abc9x87\\end",
            // --- output channel ---
            "-9-8-7" + TERM);
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test4() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1x#2{-#1-#2-}"
                    + "\\abc98x7\\end",
            // --- output channel ---
            "-98-7-" + TERM);
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test5() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1#2{-#1-#2-}"
                    + "\\abc{98}7\\end",
            // --- output channel ---
            "-98-7-" + TERM);
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test6() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1#2{-#1-#2-}"
                    + "\\abc9{87}6\\end",
            // --- output channel ---
            "-9-87-6" + TERM);
    }

}