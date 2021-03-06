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

package org.extex.test;

import org.junit.Test;

/**
 * This is a abstract base class for testing dimen registers.
 * It provides some test cases common to all dimen registers.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractDimenRegisterTester extends ExTeXLauncher {

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
     * The field {@code prepare} contains the the initializing code which goes
     * before the invocation.
     */
    private String prepare = "";

    /**
     * Creates a new object.
     * @param primitive the name of the integer register to test
     * @param args the parameters for the invocation
     * @param init the initializing code which goes before the invocation
     */
    public AbstractDimenRegisterTester(String primitive,
            String args, String init) {

        this.primitive = primitive;
        this.invocation = primitive + args;
        this.init = init;
    }

    /**
     * Creates a new object.
     * @param primitive the name of the integer register to test
     * @param args the parameters for the invocation
     * @param init the initial value
     * @param prepare the prepare code
     */
    public AbstractDimenRegisterTester(String primitive,
            String args, String init, String prepare) {

        this(primitive, args, init);
        this.prepare = prepare;
    }

    /**
     *
     *  Test case showing that the prefix {@code \immediate} is not applicable.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterImmediatePrefix1() throws Exception {

        assertFailure(//--- input code ---
                prepare + "\\immediate\\" + invocation + "= 2pt ",
                //--- error channel ---
                "You can't use the prefix `\\immediate' with the control sequence"
                        + (primitive.length() > 14 ? "\n" : " ") + "\\"
                        + primitive);
    }

    /**
     *
     *  Test case showing that the prefix {@code \long} is not applicable.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterLongPrefix1() throws Exception {

        assertFailure(//--- input code ---
                prepare + "\\long\\" + invocation + "= 2pt ",
                //--- error channel ---
                "You can't use the prefix `\\long' with the control sequence"
                        + (primitive.length() > 19 ? "\n" : " ") + "\\"
                        + primitive);
    }

    /**
     *
     *  Test case showing that an assignment of a skip constant does not
     *  absorb the plus component.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterNoSkip1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=1230pt plus 1pt \\the\\"
                + invocation + "\\end",
                //--- output channel ---
                "plus 1pt 1230.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that the prefix {@code \outer} is not applicable.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterOuterPrefix1() throws Exception {

        assertFailure(//--- input code ---
                prepare + "\\outer\\" + invocation + "= 2pt ",
                //--- error channel ---
                "You can't use the prefix `\\outer' with the control sequence"
                        + (primitive.length() > 18 ? "\n" : " ") + "\\"
                        + primitive);
    }

    /**
     *
     *  Test case showing that the primitive is defined and has its default
     *  value.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterDefault1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\the\\" + invocation + "\\end",
                //--- output channel ---
                init + TERM);
    }

    /**
     *
     *  Test case showing that the primitive is applicable to \showthe.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenShowthe1() throws Exception {

        assertOutput(//--- input code ---
                prepare + "\\showthe\\" + invocation + "\\end",
                //--- output channel ---
                out(init),
                "");
    }

    /**
     *
     *  Test case showing that an assignment of a constant 12.3pt works when using
     *  an equal sign after the primitive name.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAssign1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=12.3pt \\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "12.3pt" + TERM);
    }

    /**
     *
     *  Test case showing that an assignment of a constant 12.3pt works when using
     *  no equal sign after the primitive name.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAssign2() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + " 12.3pt \\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "12.3pt" + TERM);
    }

    /**
     *
     *  Test case showing that an assignment of a constant -12.3pt works when using
     *  an equal sign after the primitive name.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAssign3() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=-12.3pt \\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "-12.3pt" + TERM);
    }

    /**
     *
     *  Test case showing that an assignment of a constant -12.3pt works when using
     *  no equal sign after the primitive name.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAssign4() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "-12.3pt \\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "-12.3pt" + TERM);
    }

    /**
     *
     *  Test case showing that an assignment of a constant -12.3pt works when using
     *  {@code \globaldefs}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAssign5() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\globaldefs=1 " + "\\begingroup\\" + invocation
                        + "-12.3pt \\endgroup" + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "-12.3pt" + TERM);
    }

    /**
     *
     *  Test case showing that an assignment respects {@code \\afterassignment}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAfterassignment1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\afterassignment b a" + "\\" + invocation
                        + "-12.3ptc\\the\\" + invocation + "\\end",
                //--- output channel ---
                "abc-12.3pt" + TERM);
    }

    /**
     *
     *  Test case showing that the value is dimen convertible.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterConvertible1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "-12.3pt \\dimen0=\\"
                        + invocation + " \\the\\dimen0 \\end",
                //--- output channel ---
                "-12.3pt" + TERM);
    }

    /**
     *
     *  Test case showing that the value is count convertible.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterConvertible2() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "-12.3pt \\count0=\\"
                        + invocation + " \\the\\count0 \\end",
                //--- output channel ---
                "-806093" + TERM);
    }

    /**
     *
     *  Test case showing that an assignment respects grouping.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterGroup1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\begingroup\\" + invocation + "=12.3pt \\endgroup"
                        + " \\the\\" + invocation + "\\end",
                //--- output channel ---
                init + TERM);
    }

    /**
     *
     *  Test case showing that an assignment of a constant 12.3pt works when using
     *  an equal sign after the primitive name.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterGlobalAssign1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\begingroup\\global\\" + invocation
                        + "=12.3pt \\endgroup" + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "12.3pt" + TERM);
    }

    /**
     *
     *  Test case showing that an assignment of a constant 12.3pt works when using
     *  no equal sign after the primitive name.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterGlobalAssign2() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\begingroup\\global\\" + invocation
                        + " 12.3pt \\endgroup" + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "12.3pt" + TERM);
    }

    /**
     *
     *  Test case showing that an advancement by the constant 12pt works.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAdvance1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=23pt " + "\\advance\\"
                        + invocation + " 12pt " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "35.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that an advancement by the constant 12pt works when
     *  using the keyword {@code by}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAdvance2() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=23pt " + "\\advance\\"
                        + invocation + " by 12pt " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "35.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that an advancement by the constant -12pt works.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAdvance3() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=23pt " + "\\advance\\"
                        + invocation + "-12pt " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "11.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that an advancement by the constant -12pt works when
     *  using the keyword {@code by}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAdvance4() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=23pt" + "\\advance\\"
                        + invocation + " by -12pt " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "11.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that an advancement by the constant -12.3pt works when using
     *  {@code \globaldefs}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAdvance5() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\globaldefs=1 " + "\\begingroup\\" + invocation
                        + "-12.3pt \\endgroup" + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "-12.3pt" + TERM);
    }

    /**
     *
     *  Test case showing that an assignment respects {@code \\afterassignment}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAfterassignment2() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=0pt" + "\\afterassignment b a"
                        + "\\advance\\" + invocation + "-12.3ptc\\the\\"
                        + invocation + "\\end",
                //--- output channel ---
                "abc-12.3pt" + TERM);
    }

    /**
     *
     *  Test case showing that an advancing respects grouping.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterGroup2() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\begingroup\\advance\\" + invocation
                        + " 12.3pt \\endgroup" + " \\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                init + TERM);
    }

    /**
     *
     *  Test case showing that an multiplication with the constant 0 works.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterMultiply0() throws Exception {

        assertSuccess(
                //--- input code ---
                prepare + "\\" + invocation + "=3pt " + "\\multiply\\"
                        + invocation + " 0 " + "\\the\\" + invocation + "\\end",
                //--- output channel ---
                "0.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that an multiplication with the constant 12 works.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterMultiply1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=3pt " + "\\multiply\\"
                        + invocation + " 12 " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "36.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that an multiplication with the constant 12 works when
     *  using the keyword {@code by}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterMultiply2() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=3pt " + "\\multiply\\"
                        + invocation + " by 12 " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "36.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that an multiplication by the constant -12 works.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterMultiply3() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=3pt " + "\\multiply\\"
                        + invocation + "-12 " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "-36.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that an multiplication by the constant -12 works when
     *  using the keyword {@code by}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterMultiply4() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=3pt " + "\\multiply\\"
                        + invocation + " by -12 " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "-36.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that a multiplication by a constant -12.3pt works when
     *  using {@code \globaldefs}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterMultiply5() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\globaldefs=1 " + "\\" + invocation + "=12pt "
                        + "\\begingroup\\multiply\\" + invocation
                        + " 3 \\endgroup" + "\\the\\" + invocation + "\\end",
                //--- output channel ---
                "36.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that an assignment respects {@code \\afterassignment}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAfterassignment3() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=0pt " + "\\afterassignment b a"
                        + "\\multiply\\" + invocation + "-12 c\\the\\"
                        + invocation + "\\end",
                //--- output channel ---
                "abc0.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that multiplication respects grouping.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterGroup3() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=3pt "
                        + "\\begingroup\\multiply\\" + invocation
                        + " 12 \\endgroup" + " \\the\\" + invocation + "\\end",
                //--- output channel ---
                "3.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that an division by the constant 12 works.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterDivide0() throws Exception {

        assertFailure(
                //--- input code ---
                prepare + "\\" + invocation + "=3.6pt " + "\\divide\\"
                        + invocation + " 0 " + "\\the\\" + invocation + "\\end",
                //--- error channel ---
                "Arithmetic overflow");
    }

    /**
     *
     *  Test case showing that an division by the constant 12 works.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterDivide1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=3.6pt " + "\\divide\\"
                        + invocation + " 12 " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "0.29999pt" + TERM);
    }

    /**
     *
     *  Test case showing that an division by the constant 12 works when
     *  using the keyword {@code by}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterDivide2() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=3.6pt " + "\\divide\\"
                        + invocation + " by 12 " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "0.29999pt" + TERM);
    }

    /**
     *
     *  Test case showing that an multiplication by the constant -12 works.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterDivide3() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=3.6pt " + "\\divide\\"
                        + invocation + "-12 " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "-0.29999pt" + TERM);
    }

    /**
     *
     *  Test case showing that an multiplication by the constant -12 works when
     *  using the keyword {@code by}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterDivide4() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=3.6pt " + "\\divide\\"
                        + invocation + " by -12 " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "-0.29999pt" + TERM);
    }

    /**
     *
     *  Test case showing that a division by a constant -12.3pt works when
     *  using {@code \globaldefs}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterDivide5() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\globaldefs=1 " + "\\" + invocation + "=-246pt "
                        + "\\begingroup\\divide\\" + invocation
                        + "-123 \\endgroup" + "\\the\\" + invocation + "\\end",
                //--- output channel ---
                "2.0pt" + TERM);
    }

    /**
     *
     *  Test case showing that an assignment respects {@code \\afterassignment}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterAfterassignment4() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\afterassignment b a" + "\\divide\\" + invocation
                        + "-12 c\\end",
                //--- output channel ---
                "abc" + TERM);
    }

    /**
     *
     *  Test case showing that division by the constant -12 works when
     *  using the keyword {@code by}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterDivide6() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\" + invocation + "=-3.6pt " + "\\divide\\"
                        + invocation + " by -12 " + "\\the\\" + invocation
                        + "\\end",
                //--- output channel ---
                "0.29999pt" + TERM);
    }

    /**
     *
     *  Test case showing that division respects grouping.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimenRegisterGroup4() throws Exception {

        assertSuccess(
                //--- input code ---
                prepare + "\\" + invocation + "=3pt "
                        + "\\begingroup\\divide\\" + invocation
                        + " 123 \\endgroup" + " \\the\\" + invocation + "\\end",
                //--- output channel ---
                "3.0pt" + TERM);
    }

}
