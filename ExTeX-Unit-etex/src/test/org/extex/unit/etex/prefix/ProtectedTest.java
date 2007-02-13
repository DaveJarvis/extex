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

package org.extex.unit.etex.prefix;

import org.extex.scanner.type.Catcode;
import org.extex.unit.tex.prefix.PrefixTester;

/**
 * This is a test suite for the primitive <tt>\protected</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4306 $
 */
public class ProtectedTest extends PrefixTester {

    /**
     * Method for running the tests standalone.
     *
     * @param args command line parameter
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(ProtectedTest.class);
    }

    /**
     * Constructor for RelaxTest.
     *
     * @param arg the name
     */
    public ProtectedTest(final String arg) {

        super(arg, "protected");
        setConfig("etex-test");
    }

    /**
     * ...
     *
     * @param prefix the prefix code
     * @param tag the character to be assigned
     * @param catcode the category
     * @param longName the name of tag
     *
     * @throws Exception in case of an error
     */
    private void tryFlag(final String prefix, final String tag,
            final Catcode catcode, final String longName) throws Exception {

        assertFailure(//--- input code ---
                "\\catcode`\\" + tag + "=" + catcode.getCode() + " \\" + prefix
                        + " " + tag + "\\end",
                //--- log message ---
                "You can\'t use the prefix `\\" + prefix + "\' with "
                        + longName);
    }

    /**
     * <testcase primitive="\protected">
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test10() throws Exception {

        assertOutput(showPrefixProperties(),
        //--- input code ---
                "\\protected\\showprefix\\end",
                //--- error channel ---
                "protected\n",
                //--- output channel ---
                "");
    }

    /**
     * <testcase primitive="\protected">
     *  Test case checking that double <tt>\protected</tt> has the same effect
     *  as one.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test11() throws Exception {

        assertOutput(showPrefixProperties(),
        //--- input code ---
                "\\protected\\protected\\showprefix\\end",
                //--- error channel ---
                "protected\n",
                //--- output channel ---
                "");
    }

    /**
     * <testcase primitive="\protected">
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test12() throws Exception {

        assertOutput(showPrefixProperties(),
        //--- input code ---
                "\\protected\\long\\showprefix\\end",
                //--- error channel ---
                "long and protected\n",
                //--- output channel ---
                "");
    }

    /**
     * <testcase>
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTabNoProtectedFlag() throws Exception {

        tryFlag("protected", "&", Catcode.TABMARK, "alignment tab character &");
    }

    /**
     * <testcase>
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testSubNoProtectedFlag() throws Exception {

        tryFlag("protected", "_", Catcode.SUBMARK, "subscript character _");
    }

    /**
     * <testcase>
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testSuperNoProtectedFlag() throws Exception {

        tryFlag("protected", "^", Catcode.SUPMARK, "superscript character ^");
    }

    /**
     * <testcase>
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetterNoProtectedFlag() throws Exception {

        tryFlag("protected", "A", Catcode.LETTER, "the letter A");
    }

    /**
     * <testcase>
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testOtherNoProtectedFlag() throws Exception {

        tryFlag("protected", "1", Catcode.OTHER, "the character 1");
    }

    /**
     * <testcase>
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testMathshiftNoProtectedFlag() throws Exception {

        tryFlag("protected", "$", Catcode.MATHSHIFT, "math shift character $");
    }

    /**
     * <testcase>
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testBegingroupNoProtectedFlag() throws Exception {

        tryFlag("protected", "{", Catcode.LEFTBRACE, "begin-group character {");
    }

    /**
     * <testcase>
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEndgroupNoProtectedFlag() throws Exception {

        tryFlag("protected", "}", Catcode.RIGHTBRACE, "end-group character }");
    }

}
