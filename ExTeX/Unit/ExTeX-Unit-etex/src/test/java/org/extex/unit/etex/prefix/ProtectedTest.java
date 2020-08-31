/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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
import org.extex.test.PrefixTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \protected}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ProtectedTest extends PrefixTester {


    public ProtectedTest() {

        super("protected");
        setConfig("etex-test");
    }

    /**
     * <testcase primitive="\protected"> Test case checking that
     * {@code \protected} sets the flag. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertOutput(showPrefixProperties(),
        // --- input code ---
            "\\protected\\showprefix\\end",
            // --- error channel ---
            "protected\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase primitive="\protected"> Test case checking that double
     * {@code \protected} has the same effect as one. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11() throws Exception {

        assertOutput(showPrefixProperties(),
        // --- input code ---
            "\\protected\\protected\\showprefix\\end",
            // --- error channel ---
            "protected\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase primitive="\protected"> Test case checking that
     * {@code \protected} does not interfere with \long. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12() throws Exception {

        assertOutput(showPrefixProperties(),
        // --- input code ---
            "\\protected\\long\\showprefix\\end",
            // --- error channel ---
            "long and protected\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase primitive="\protected"> Test case checking that
     * {@code \protected} can not be used before a begin-group character.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testBegingroupNoProtectedFlag() throws Exception {

        tryFlag("protected", "{", Catcode.LEFTBRACE, "begin-group character {");
    }

    /**
     * <testcase primitive="\protected"> Test case checking that
     * {@code \protected} can not be used before an end-group character.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEndgroupNoProtectedFlag() throws Exception {

        tryFlag("protected", "}", Catcode.RIGHTBRACE, "end-group character }");
    }

    /**
     * <testcase primitive="\protected"> Test case checking that
     * {@code \protected} can not be used before a letter. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLetterNoProtectedFlag() throws Exception {

        tryFlag("protected", "A", Catcode.LETTER, "the letter A");
    }

    /**
     * <testcase primitive="\protected"> Test case checking that
     * {@code \protected} can not be used before a math shift character.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathshiftNoProtectedFlag() throws Exception {

        tryFlag("protected", "$", Catcode.MATHSHIFT, "math shift character $");
    }

    /**
     * <testcase primitive="\protected"> Test case checking that
     * {@code \protected} can not be used before an other character.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOtherNoProtectedFlag() throws Exception {

        tryFlag("protected", "1", Catcode.OTHER, "the character 1");
    }

    /**
     * <testcase primitive="\protected"> Test case checking that
     * {@code \protected} can not be used before a subscript character.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubNoProtectedFlag() throws Exception {

        tryFlag("protected", "_", Catcode.SUBMARK, "subscript character _");
    }

    /**
     * <testcase primitive="\protected"> Test case checking that
     * {@code \protected} can not be used before a superscript character.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSuperNoProtectedFlag() throws Exception {

        tryFlag("protected", "^", Catcode.SUPMARK, "superscript character ^");
    }

    /**
     * <testcase primitive="\protected"> Test case checking that
     * {@code \protected} can not be used before an alignment tab character.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTabNoProtectedFlag() throws Exception {

        tryFlag("protected", "&", Catcode.TABMARK, "alignment tab character &");
    }

    /**
     * Try a prefix which is assumed to be not applicable.
     * 
     * @param prefix the prefix code
     * @param tag the character to be assigned
     * @param catcode the category
     * @param longName the name of tag
     * 
     * @throws Exception in case of an error
     */
    private void tryFlag(String prefix, String tag, Catcode catcode,
            String longName) throws Exception {

        assertFailure(// --- input code ---
            "\\catcode`\\" + tag + "=" + catcode.getCode() + " \\" + prefix
                    + " " + tag + "\\end",
            // --- log message ---
                      "You can't use the prefix `\\" + prefix + "' with " + longName);
    }

}
