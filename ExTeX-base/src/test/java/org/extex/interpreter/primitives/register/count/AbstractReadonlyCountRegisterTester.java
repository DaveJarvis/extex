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

package org.extex.interpreter.primitives.register.count;

import org.extex.test.ExTeXLauncher;

/**
 * This is a test suite for read-only count registers.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractReadonlyCountRegisterTester extends ExTeXLauncher {

    /**
     * The field <tt>primitive</tt> contains the name of the primitive to test.
     */
    private String primitive;

    /**
     * The field <tt>defaultValue</tt> contains the default value.
     */
    private String defaultValue;

    /**
     * The field <tt>argument</tt> contains the argument.
     */
    private String argument = "";

    /**
     * The field <tt>prepare</tt> contains the ...
     */
    private String prepare = "";

    /**
     * Creates a new object.
     *
     * @param arg the name
     * @param primitive the name of the primitive
     * @param defaultValue the default value
     */
    public AbstractReadonlyCountRegisterTester(String arg,
            String primitive, String defaultValue) {

        super(arg);
        this.primitive = primitive;
        this.defaultValue = defaultValue;
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     * @param primitive the name of the primitive
     * @param argument the argument
     * @param defaultValue the default value
     */
    public AbstractReadonlyCountRegisterTester(String arg,
            String primitive, String argument,
            String defaultValue) {

        super(arg);
        this.primitive = primitive;
        this.defaultValue = defaultValue;
        this.argument = argument;
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     * @param primitive the name of the primitive
     * @param argument the argument
     * @param defaultValue the default value
     * @param prepare ...
     */
    public AbstractReadonlyCountRegisterTester(String arg,
            String primitive, String argument,
            String defaultValue, String prepare) {

        super(arg);
        this.primitive = primitive;
        this.defaultValue = defaultValue;
        this.argument = argument;
        this.prepare = prepare;
    }

    /**
     * <testcase>
     *  Test case checking that the primitive is not allowed in vertical mode.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErrorVerticalMode1() throws Exception {

        assertFailure(//--- input code ---
                "\\" + primitive + " ",
                //--- log message ---
                "You can't use `\\" + primitive + "' in vertical mode");
    }

    /**
     * <testcase>
     *  Test case checking that the primitive is not allowed in inner vertical
     *  mode.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErrorVerticalMode2() throws Exception {

        assertFailure(//--- input code ---
                DEFINE_BRACES + "\\vbox{\\" + primitive + "} ",
                //--- log message ---
                "You can't use `\\" + primitive + "' in inner vertical mode");
    }

    /**
     * <testcase>
     *  Test case checking that the primitive is not allowed in horizontal mode.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErrorHorizonalMode1() throws Exception {

        assertFailure(//--- input code ---
                "x\\" + primitive + " ",
                //--- log message ---
                "You can't use `\\" + primitive + "' in horizontal mode");
    }

    /**
     * <testcase>
     *  Test case checking that the primitive is not allowed in restricted
     *  horizontal mode.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErrorHorizonalMode2() throws Exception {

        assertFailure(//--- input code ---
                DEFINE_BRACES + "\\hbox{\\" + primitive + "} ",
                //--- log message ---
                "You can't use `\\" + primitive
                        + "' in restricted horizontal mode");
    }

    /**
     * <testcase>
     *  Test case checking that the primitive is not allowed in math mode.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErrorMathMode1() throws Exception {

        assertFailure(//--- input code ---
                DEFINE_MATH + "$\\" + primitive + "$ ",
                //--- log message ---
                "You can't use `\\" + primitive + "' in math mode");
    }

    /**
     * <testcase>
     *  Test case checking that the primitive is not allowed in math mode.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErrorMathMode2() throws Exception {

        assertFailure(//--- input code ---
                DEFINE_MATH + "$$\\" + primitive + "$$ ",
                //--- log message ---
                "You can't use `\\" + primitive + "' in displaymath mode");
    }

    /**
     * <testcase>
     *  Test case checking that the primitive is theable and has the default
     *  value 0.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testDefaultValue1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\the\\" + primitive + argument + " \\end",
                //--- log message ---
                defaultValue + TERM);
    }

    /**
     * <testcase>
     *  Test case checking that the primitive is assignable to a count register.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCountAssignment1() throws Exception {

        assertSuccess(//--- input code ---
                prepare + "\\count0=\\" + primitive + argument + "\\the\\count0\\end",
                //--- log message ---
                defaultValue + TERM);
    }

}