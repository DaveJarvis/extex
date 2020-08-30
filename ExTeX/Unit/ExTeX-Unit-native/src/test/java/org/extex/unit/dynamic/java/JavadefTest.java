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

package org.extex.unit.dynamic.java;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \javadef}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class JavadefTest extends NoFlagsButGlobalPrimitiveTester {


    public JavadefTest() {

        setPrimitive("javadef");setArguments("\\t{org.extex.unit.base.Relax}");
        setConfig("native-test");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * can assign a Java class to a control sequence.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_BRACES + "\\javadef\\t{org.extex.unit.tex.info.The}"
                    + "\\t\\count42" + " \\end",
            // --- log message ---
            "0" + TERM);
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * can assign a Java class to an active character.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_BRACES + "\\catcode`\\~=13 "
                    + "\\javadef~{org.extex.unit.tex.info.The}" + "~\\count42"
                    + " \\end",
            // --- log message ---
            "0" + TERM);
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * respects \afterassignment.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAfterassignment1() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\afterassignment\\x " + "\\begingroup"
                    + "\\javadef\\t{org.extex.unit.tex.info.The}"
                    + "\\endgroup",
            // --- log message ---
            "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * needs a token to assign.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError0() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef",
            // --- log message ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * needs a token to assign.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError01() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef a",
            // --- log message ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * needs a token to assign.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError02() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef 2",
            // --- log message ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * needs a token to assign.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError03() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef \\x",
            // --- log message ---
            "File ended while scanning text of \\javadef");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * needs a non-empty class name.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError04() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef \\x {}",
            // --- log message ---
            "The string `' is no valid class name");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * needs a proper class name.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError05() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef \\x {a-b-c}",
            // --- log message ---
            "The string `a-b-c' is no valid class name");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * needs a Java class With a proper constructor.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError10() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef\\t{" + String.class.getName() + "}",
            // --- log message ---
            "Method not found:\njava.lang.String.<init>(org.extex.scanner.type.token.CodeToken)");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * needs a Java class of type Code.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError11() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef\\t{" + JavadefTestClass1.class.getName()
                    + "}",
            // --- log message ---
            "The class " + JavadefTestClass1.class.getName()
                    + " does not implement the\n"
                    + "required interface org.extex.interpreter.type.Code.");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * respects the global keyword.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal1() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_BRACES + "\\begingroup"
                    + "\\global\\javadef\\t{org.extex.unit.tex.info.The}"
                    + "\\endgroup" + "\\t\\count42" + " \\end",
            // --- log message ---
            "0" + TERM);
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * respects the \global keyword.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal2() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\begingroup"
                    + "\\javadef\\t{org.extex.unit.tex.info.The}"
                    + "\\endgroup" + "\\t\\count42" + " \\end",
            // --- log message ---
            "Undefined control sequence \\t");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that {@code \javadef}
     * respects \globaldefs.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal3() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_BRACES + "\\globaldefs=1 " + "\\begingroup"
                    + "\\javadef\\t{org.extex.unit.tex.info.The}"
                    + "\\endgroup" + "\\t\\count42" + " \\end",
            // --- log message ---
            "0" + TERM);
    }

}
