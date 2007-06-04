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

package org.extex.unit.extex.count;

import org.extex.test.count.AbstractCountRegisterTester;

/**
 * This is a test suite for the primitive <tt>\ignorevoid</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class IgnorevoidTest extends AbstractCountRegisterTester {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(IgnorevoidTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public IgnorevoidTest(String arg) {

        super(arg, "ignorevoid", "", "0");
        setConfig("extex-test");
    }

    /**
     * <testcase primitive="\ignorevoid">
     *  Test case checking that <tt>\ignorevoid</tt> is 0 initially.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(
        //--- input code ---
            DEFINE_BRACES + "\\the\\ignorevoid" + " \\end",
            //--- log message ---
            "0" + TERM);
    }

    /**
     * <testcase primitive="\ignorevoid">
     *  Test case checking that if <tt>\ignorevoid</tt> is 0 then an undefined
     *  control sequence leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertFailure(
        //--- input code ---
            DEFINE_BRACES + "\\ignorevoid=0" + " \\undefined\\end",
            //--- log message ---
            "Undefined control sequence \\undefined");
    }

    /**
     * <testcase primitive="\ignorevoid">
     *  Test case checking that if <tt>\ignorevoid</tt> is 0 then an undefined
     *  active character leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test3() throws Exception {

        assertFailure(
        //--- input code ---
            DEFINE_BRACES + "\\catcode`\\*=13 \\ignorevoid=0" + " *\\end",
            //--- log message ---
            "Undefined control sequence *");
    }

    /**
     * <testcase primitive="\ignorevoid">
     *  Test case checking that if <tt>\ignorevoid</tt> is not 0 then an
     *  undefined control sequence is ignored.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test4() throws Exception {

        assertSuccess(
        //--- input code ---
            DEFINE_BRACES + "\\ignorevoid=1" + " \\undefined\\end",
            //--- log message ---
            "");
    }

    /**
     * <testcase primitive="\ignorevoid">
     *  Test case checking that if <tt>\ignorevoid</tt> is not 0 then an
     *  undefined control sequence is ignored.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test5() throws Exception {

        assertSuccess(
        //--- input code ---
            DEFINE_BRACES + "\\catcode`\\*=13 \\ignorevoid=1" + " *\\end",
            //--- log message ---
            "");
    }

}
