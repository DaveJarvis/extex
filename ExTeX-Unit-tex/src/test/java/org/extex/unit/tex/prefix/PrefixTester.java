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

package org.extex.unit.tex.prefix;

import java.util.Properties;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test suite for the prefix primitives.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public abstract class PrefixTester extends ExTeXLauncher {

    /**
     * The field <tt>primitive</tt> contains the name of the primitive to be
     * tested.
     */
    private String primitive;

    /**
     * Constructor for PrefixTester.
     * @param primitive the name of the primitive to be tested
     */
    public PrefixTester(String primitive) {

        super();
        this.primitive = primitive;
    }

    /**
     * construct the properties with the unit <tt>showprefix</tt> added.
     *
     * @return the properties
     */
    protected Properties showPrefixProperties() {

        Properties p = getProps();
        p.put("extex.units", "showprefix");
        return p;
    }

    /**
     * <testcase>
     *  Test case checking that the prefix before a letter leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testPrefixLetter1() throws Exception {

        assertFailure(//--- input code ---
            "\\" + primitive + " a",
            //--- log message ---
            "You can\'t use the prefix `\\" + primitive + "' with the letter a");
    }

    /**
     * <testcase>
     *  Test case checking that the prefix before a digit leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testPrefixOther1() throws Exception {

        assertFailure(//--- input code ---
            "\\" + primitive + " 1",
            //--- log message ---
            "You can\'t use the prefix `\\" + primitive
                    + "' with the character 1");
    }

    /**
     * <testcase>
     *  Test case checking that the prefix before a left brace
     *  leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testPrefixBeginGroup1() throws Exception {

        assertFailure(//--- input code ---
            DEFINE_CATCODES + "\\" + primitive + " {",
            //--- log message ---
            "You can\'t use the prefix `\\" + primitive
                    + "' with begin-group character {");
    }

    /**
     * <testcase>
     *  Test case checking that the prefix before a right brace
     *  leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testPrefixEndGroup1() throws Exception {

        assertFailure(//--- input code ---
            DEFINE_CATCODES + "{\\" + primitive + " }",
            //--- log message ---
            "You can\'t use the prefix `\\" + primitive
                    + "' with end-group character }");
    }

    /**
     * <testcase>
     *  Test case checking that the prefix before a math shift
     *  leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testPrefixMathShift1() throws Exception {

        assertFailure(//--- input code ---
            DEFINE_CATCODES + "\\" + primitive + " $ ",
            //--- log message ---
            "You can\'t use the prefix `\\" + primitive
                    + "' with math shift character $");
    }

    /**
     * <testcase>
     *  Test case checking that the prefix before an alignment tab
     *  leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testPrefixTab1() throws Exception {

        assertFailure(//--- input code ---
            DEFINE_CATCODES + "\\" + primitive + " &",
            //--- log message ---
            "You can\'t use the prefix `\\" + primitive
                    + "' with alignment tab character &");
    }

    /**
     * <testcase>
     *  Test case checking that the prefix before a subscript mark
     *  leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testPrefixSub1() throws Exception {

        assertFailure(//--- input code ---
            DEFINE_CATCODES + "$\\" + primitive + " _",
            //--- log message ---
            "You can\'t use the prefix `\\" + primitive
                    + "' with subscript character _");
    }

    /**
     * <testcase>
     *  Test case checking that the prefix before a superscript mark
     *  leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testPrefixSuper1() throws Exception {

        assertFailure(//--- input code ---
            DEFINE_CATCODES + "$\\" + primitive + " ^",
            //--- log message ---
            "You can\'t use the prefix `\\" + primitive
                    + "' with superscript character ^");
    }

}
