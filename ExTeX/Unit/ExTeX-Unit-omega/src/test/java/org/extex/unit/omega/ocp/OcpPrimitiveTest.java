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

package org.extex.unit.omega.ocp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.extex.core.UnicodeChar;
import org.extex.interpreter.Interpreter;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.ocpware.type.OcpProgram;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.CodeToken;
import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.extex.unit.omega.ocp.util.Ocp;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \Ocp}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class OcpPrimitiveTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * The command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(OcpPrimitiveTest.class);
    }


    public OcpPrimitiveTest() {

        setPrimitive("ocp");setArguments("\\abc=../ExTeX-Unit-omega/src/test/resources/destroy");
        setConfig("omega-test");
    }

    /**
     * <testcase primitive="\ocp"> Test case checking that {@code \ocp} needs
     * a control sequence.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErrEof1() throws Exception {

        assertFailure(// --- input code ---
            "\\ocp",
            // --- output channel ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\ocp"> Test case checking that {@code \ocp} takes
     * an optional =.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErrEof2() throws Exception {

        assertFailure(// --- input code ---
            "\\ocp\\a=",
            // --- output channel ---
            "Unexpected end of file while processing \\ocp");
    }

    /**
     * <testcase primitive="\ocp"> Test case checking that {@code \ocp}
     * defines the control sequence group local.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES
                    + "{\\ocp\\x=../ExTeX-Unit-omega/src/test/resources/destroy }"
                    + "\\x\\end",
            // --- error channel ---
            "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\ocp"> Test case checking that {@code \ocp}
     * defines the control sequence group local.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal2() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES
                    + "{\\global\\ocp\\x=../ExTeX-Unit-omega/src/test/resources/destroy }"
                    + "\\x\\end",
            // --- error channel ---
            "To use ocps, use the \\pushocplist primitive");
    }

    /**
     * <testcase primitive="\ocp"> Test case checking that {@code \ocp}
     * defines the control sequence group local.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal3() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES
                    + "\\globaldefs=1 "
                    + "{\\ocp\\x=../ExTeX-Unit-omega/src/test/resources/destroy }"
                    + "\\x\\end",
            // --- error channel ---
            "To use ocps, use the \\pushocplist primitive");
    }

    /**
     * <testcase primitive="\ocp"> Test case checking that {@code \ocp} bails
     * out an error when the resource can not be found.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        assertFailure(// --- input code ---
            "\\ocp\\x=ocp_which_does_not_exist_at_all \\end",
            // --- error channel ---
            "OCP file ocp_which_does_not_exist_at_all not loadable: ocp file not found.");
    }

    /**
     * <testcase primitive="\ocp"> Test case checking that {@code \ocp}
     * stores the &Omega;CP program in the Context.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        Interpreter interp = assertSuccess(// --- input code ---
            "\\ocp\\x=../ExTeX-Unit-omega/src/test/resources/destroy \\end",
            // --- output channel ---
            "");
        assertNotNull(interp);
        Context context = interp.getContext();
        CodeToken t =
                (CodeToken) context.getTokenFactory().createToken(
                    Catcode.ESCAPE, UnicodeChar.get('\\'), "x",
                    Namespace.DEFAULT_NAMESPACE);
        Code code = context.getCode(t);
        assertNotNull("missing ocp", code);
        assertTrue(code instanceof Ocp);
        OcpProgram program = ((Ocp) code).getProgram();
        assertNotNull("missing ocp program code", program);
    }

    /**
     * <testcase primitive="\ocp"> Test case checking that {@code \ocp}
     * honors {@code \afterassignment}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAfterassignment1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES
                    + "\\afterassignment 1 "
                    + "\\ocp\\x=../ExTeX-Unit-omega/src/test/resources/destroy "
                    + "\\end",
            // --- output channel ---
            "1" + TERM);
    }

    // TODO implement more primitive specific test cases
}
