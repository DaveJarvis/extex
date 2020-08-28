/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.macro;

import org.junit.Test;

/**
 * This is a test suite for the primitive <tt>\def</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 2974 $
 */
public class DefTest extends AbstractDefTester {


    public DefTest() {

        super("def");
    }

    /**
     * <testcase primitive="\def"> Test case checking that <tt>\def</tt>
     * respects <tt>\global</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal0() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\aaa{AAA}" + "{\\def\\aaa{BBB}}"
                    + "--\\aaa--\\end",
            // --- output message ---
            "--AAA--" + TERM);
    }

    /**
     * <testcase primitive="\def"> Test case checking that <tt>\def</tt>
     * respects <tt>\global</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\aaa{AAA}" + "{\\global\\def\\aaa{BBB}}"
                    + "--\\aaa--\\end",
            // --- output message ---
            "--BBB--" + TERM);
    }

    /**
     * <testcase> Test case checking that <tt>\def</tt> with a single argument
     * accepts a single token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testHashArgument3() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + DEFINE_HASH + "\\" + getDef()
                    + "\\a{\\def\\b##1{B}}" + "\\a \\b2\\end",
            // --- output channel ---
            "B" + TERM);
    }

    /**
     * <testcase> Test case checking that <tt>\def</tt> with a single argument
     * inserts the actual value. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testHashArgument4() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + DEFINE_HASH + "\\" + getDef()
                    + "\\a{\\def\\b##1{B##1B}}" + "\\a \\b2\\end",
            // --- output channel ---
            "B2B" + TERM);
    }

    /**
     * <testcase> Test case checking that <tt>\def</tt> with one argument
     * needs something to fill in. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacroError1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + DEFINE_HASH + "\\" + getDef() + "\\a#1{}" + "\\a",
            // --- output channel ---
            "File ended while scanning use of \\a");
    }

    /**
     * <testcase> Test case checking that <tt>\def</tt> with one argument
     * needs something complete to fill in. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacroError2() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + DEFINE_HASH + "\\" + getDef() + "\\a#1{}" + "\\a{",
            // --- output channel ---
            "File ended while scanning use of \\a");
    }

}
