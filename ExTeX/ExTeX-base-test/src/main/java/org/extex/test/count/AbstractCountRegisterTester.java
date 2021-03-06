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

package org.extex.test.count;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a abstract base class for testing count registers. It provides some
 * test cases common to all count registers.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractCountRegisterTester extends ExTeXLauncher {

    /**
     * The field {@code primitive} contains the name of the primitive to test.
     */
    private final String primitive;

    /**
     * The field {@code invocation} contains the concatenation of primitive
     * name and arguments.
     */
    private final String invocation;

    /**
     * The field {@code init} contains the default value.
     */
    private final String init;

    /**
     * The field {@code prepare} contains the the preparation code inserted
     * before each test.
     */
    private String prepare = "";

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the integer register to test
     * @param args the parameters for the invocation
     * @param init the default value
     */
    public AbstractCountRegisterTester(String primitive, String args,
            String init) {

        this.primitive = primitive;
        this.invocation = primitive + args;
        this.init = init;
    }

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the integer register to test
     * @param args the arguments for the invocation
     * @param init the default value
     * @param prepare the preparation code inserted before each test
     */
    public AbstractCountRegisterTester(String primitive, String args,
            String init, String prepare) {

        this(primitive, args, init);
        this.prepare = prepare;
    }

    /**
     * Test case showing that an advancement by the constant 12 works
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAdvance1() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=23 " + "\\advance\\" + invocation
                    + " 12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("35"), "");
    }

    /**
     * Test case showing that an advancement by the constant 12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAdvance2() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=23 " + "\\advance\\" + invocation
                    + " by 12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("35"), "");
    }

    /**
     * Test case showing that an advancement by the constant -12 works
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAdvance3() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=23 " + "\\advance\\" + invocation
                    + "-12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("11"), "");
    }

    /**
     * Test case showing that an advancement by the constant -12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAdvance4() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=23 " + "\\advance\\" + invocation
                    + " by -12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("11"), "");
    }

    /**
     * Test case showing that an advancement by the constant -123 works when using {@code \globaldefs}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAdvance5() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\globaldefs=1 " + "\\begingroup\\" + invocation
                    + "-123 \\endgroup" + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("-123"), "");
    }

    /**
     * Test case showing that an assignment respects {@code \\afterassignment}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAfterassignment1() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_BRACES + prepare
                    + "\\def\\x{\\message{b}}\\afterassignment\\x\\message{a}"
                    + "\\" + invocation + "-123 \\message{c}\\showthe\\"
                    + invocation + "\\end",
            // --- output channel ---
            "a b c> -123.\n", "");
    }

    /**
     * Test case showing that an assignment respects {@code \\afterassignment}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAfterassignment2() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_BRACES + prepare + "\\def\\x{\\message{b}}\\" + invocation
                    + "=0 " + "\\afterassignment\\x\\message{a}"
                    + "\\advance\\" + invocation
                    + "-123 \\message{c}\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            "a b c> -123.\n", "");
    }

    /**
     * Test case showing that an assignment respects {@code \\afterassignment}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAfterassignment3() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_BRACES + prepare + "\\def\\x{\\message{b}}\\" + invocation
                    + "=0 " + "\\afterassignment\\x \\message{a}"
                    + "\\multiply\\" + invocation
                    + "-123 \\message{c}\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            "a b c> 0.\n", "");
    }

    /**
     * Test case showing that an assignment respects {@code \\afterassignment}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAfterassignment4() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_BRACES + prepare
                    + "\\def\\x{\\message{b}}\\afterassignment\\x \\message{a}"
                    + "\\divide\\" + invocation + "-123 \\message{c}\\end",
            // --- output channel ---
            "a b c", "");
    }

    /**
     * Test case showing that an assignment of a constant 123 works when using an equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAssign1() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=123 \\showthe\\" + invocation
                    + "\\end",
            // --- output channel ---
            out("123"), "");
    }

    /**
     * Test case showing that an assignment of a constant 123 works when using no equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAssign2() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + " 123 \\showthe\\" + invocation
                    + "\\end",
            // --- output channel ---
            out("123"), "");
    }

    /**
     * Test case showing that an assignment of a constant -123 works when using an equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAssign3() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=-123 \\showthe\\" + invocation
                    + "\\end",
            // --- output channel ---
            out("-123"), "");
    }

    /**
     * Test case showing that an assignment of a constant -123 works when using no equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAssign4() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "-123 \\showthe\\" + invocation
                    + "\\end",
            // --- output channel ---
            out("-123"), "");
    }

    /**
     * Test case showing that an assignment of a constant -123 works when using {@code \globaldefs}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAssign5() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\globaldefs=1 " + "\\begingroup\\" + invocation
                    + "-123 \\endgroup" + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("-123"), "");
    }

    /**
     * Test case showing that the primitive name can not be followed by a letter
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAssignError1() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + " x",
            // --- output channel ---
            "Missing number, treated as zero", "");
    }

    /**
     *  Test case showing that the value is count convertible.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterConvertible1() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "-123 \\count0=\\" + invocation
                    + " \\showthe\\count0 \\end",
            // --- output channel ---
            out("-123"), "");
    }

    /**
     * Test case showing that the primitive is defined and its default value is 0
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDefault1() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out(init), "");
    }

    /**
     * Test case showing that an division by the constant 0 leads to an error
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide0() throws Exception {

        assertFailure(
        // --- input code ---
            prepare + "\\" + invocation + "=36 " + "\\divide\\" + invocation
                    + " 0 " + "\\the\\" + invocation + "\\end",
            // --- error channel ---
            "Arithmetic overflow");
    }

    /**
     *  Test case showing that an division by the constant 12 works.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide1() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=36 " + "\\divide\\" + invocation
                    + " 12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("3"), "");
    }

    /**
     * Test case showing that an division by the constant 12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide2() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=36 " + "\\divide\\" + invocation
                    + " by 12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("3"), "");
    }

    /**
     *  Test case showing that an division by the constant -12 works.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide3() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=36 " + "\\divide\\" + invocation
                    + "-12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("-3"), "");
    }

    /**
     * Test case showing that an multiplication by the constant -12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide4() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=36 " + "\\divide\\" + invocation
                    + " by -12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("-3"), "");
    }

    /**
     * Test case showing that a division by a constant -123 works when using {@code \globaldefs}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide5() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\globaldefs=1 " + "\\" + invocation + "=-246 "
                    + "\\begingroup\\divide\\" + invocation + "-123 \\endgroup"
                    + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("2"), "");
    }

    /**
     * Test case showing that division by the constant -12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide6() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=-36 " + "\\divide\\" + invocation
                    + " by -12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("3"), "");
    }

    /**
     * Test case showing that an division by the constant 12 works rounds to an integer
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide7() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=37 " + "\\divide\\" + invocation
                    + "-12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("-3"), "");
    }

    /**
     * Test case showing that an assignment of a constant 123 works when using an equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterGlobalAssign1() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\begingroup\\global\\" + invocation + "=123 \\endgroup"
                    + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("123"), "");
    }

    /**
     * Test case showing that an assignment of a constant 123 works when using no equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterGlobalAssign2() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\begingroup\\global\\" + invocation + " 123 \\endgroup"
                    + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out("123"), "");
    }

    /**
     *  Test case showing that an assignment respects grouping.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterGroup1() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\begingroup\\" + invocation + "=123 \\endgroup"
                    + " \\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out(init), "");
    }

    /**
     *  Test case showing that an advancing respects grouping.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterGroup2() throws Exception {

        assertOutput(// --- input code ---
            // --- input code ---
            prepare + "\\begingroup\\advance\\" + invocation
                    + " 123 \\endgroup" + " \\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out(init), "");
    }

    /**
     *  Test case showing that multiplication respects grouping.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterGroup3() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\begingroup\\multiply\\"
                    + invocation + " 123 \\endgroup" + " \\showthe\\"
                    + invocation + "\\end",
            // --- output channel ---
            out("3"), "");
    }

    /**
     *  Test case showing that division respects grouping.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterGroup4() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\begingroup\\divide\\"
                    + invocation + " 123 \\endgroup" + " \\showthe\\"
                    + invocation + "\\end",
            // --- output channel ---
            out("3"), "");
    }

    /**
     * Test case showing that the prefix {@code \immediate} is not applicable
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterImmediatePrefix1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\immediate\\" + invocation + "=92 ",
            // --- error channel ---
            "You can't use the prefix `\\immediate' with the control sequence"
                    + (primitive.length() > 14 ? "\n" : " ") + "\\" + primitive);
    }

    /**
     * Test case showing that the prefix {@code \long} is not applicable
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterLongPrefix1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\long\\" + invocation + "=92 ",
            // --- error channel ---
            "You can't use the prefix `\\long' with the control sequence"
                    + (primitive.length() > 19 ? "\n" : " ") + "\\" + primitive);
    }

    /**
     * Test case showing that an multiplication with the constant 0 works
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterMultiply0() throws Exception {

        assertOutput(
        // --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\multiply\\" + invocation
                    + " 0 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            "> 0.\n", "");
    }

    /**
     * Test case showing that an multiplication with the constant 12 works
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterMultiply1() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\multiply\\" + invocation
                    + " 12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            "> 36.\n", "");
    }

    /**
     * Test case showing that an multiplication with the constant 12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterMultiply2() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\multiply\\" + invocation
                    + " by 12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            "> 36.\n", "");
    }

    /**
     * Test case showing that an multiplication by the constant -12 works
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterMultiply3() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\multiply\\" + invocation
                    + "-12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            "> -36.\n", "");
    }

    /**
     * Test case showing that an multiplication by the constant -12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterMultiply4() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\multiply\\" + invocation
                    + " by -12 " + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            "> -36.\n", "");
    }

    /**
     * Test case showing that a multiplication by a constant -123 works when using {@code \globaldefs}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterMultiply5() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\globaldefs=1 " + "\\" + invocation + "=12 "
                    + "\\begingroup\\multiply\\" + invocation + " 3 \\endgroup"
                    + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            "> 36.\n", "");
    }

    /**
     * Test case showing that the prefix {@code \outer} is not applicable
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterOuterPrefix1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\outer\\" + invocation + "=92 ",
            // --- error channel ---
            "You can't use the prefix `\\outer' with the control sequence"
                    + (primitive.length() > 18 ? "\n" : " ") + "\\" + primitive);
    }

    /**
     * Test case showing that the primitive is applicable fro \showthe
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterShowthe1() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out(init), "");
    }

}
