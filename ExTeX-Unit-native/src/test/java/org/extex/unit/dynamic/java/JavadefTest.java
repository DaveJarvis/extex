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

package org.extex.unit.dynamic.java;

import org.extex.scanner.type.token.CodeToken;
import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive <tt>\javadef</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class JavadefTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Creates a new object.
     */
    public JavadefTest() {

        super("javadef", "\\t{org.extex.unit.base.Relax}");
        setConfig("native-test");
    }

    /**
     * Creates a new object. This constructor is needed for a test case.
     * 
     * @param arg the (ignored) argument
     */
    public JavadefTest(CodeToken arg) {

        super(null, null);
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that <tt>\javadef</tt>
     * needs a token to assign. </testcase>
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
     * <testcase primitive="\javadef"> Test case checking that <tt>\javadef</tt>
     * needs a token to assign. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef a",
            // --- log message ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that <tt>\javadef</tt>
     * needs a token to assign. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef 2",
            // --- log message ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that <tt>\javadef</tt>
     * needs a token to assign. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError3() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef \\x",
            // --- log message ---
            "File ended while scanning text of \\javadef");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that <tt>\javadef</tt>
     * needs a Java class of type Code. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError10() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_BRACES + "\\javadef\\t{" + getClass().getName() + "}",
            // --- log message ---
            "The class " + getClass().getName() + " does not implement the\n"
                    + "required interface org.extex.interpreter.type.Code.");
    }

    /**
     * <testcase primitive="\javadef"> Test case checking that <tt>\javadef</tt>
     * can assign a Java class to a control sequence. </testcase>
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
     * <testcase primitive="\javadef"> Test case checking that <tt>\javadef</tt>
     * can assign a Java class to an active character. </testcase>
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
     * <testcase primitive="\javadef"> Test case checking that <tt>\javadef</tt>
     * respects the global keyword. </testcase>
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
     * <testcase primitive="\javadef"> Test case checking that <tt>\javadef</tt>
     * respects the \global keyword. </testcase>
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
     * <testcase primitive="\javadef"> Test case checking that <tt>\javadef</tt>
     * respects \globaldefs. </testcase>
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

    /**
     * <testcase primitive="\javadef"> Test case checking that <tt>\javadef</tt>
     * respects \afterassignment. </testcase>
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

}
