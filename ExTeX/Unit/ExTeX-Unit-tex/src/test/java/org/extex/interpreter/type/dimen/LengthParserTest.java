/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.type.dimen;

import org.extex.test.ExTeXLauncher;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the for the parser of data type Dimen.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LengthParserTest extends ExTeXLauncher {

    /**
     * <testcase> Test case showing that a dimen variable incremented by a
     * fraction is parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testAdd1() throws Exception {

        assertSuccess(//
            "\\dimen1=2pt" + "\\dimen0=(\\dimen1+1.23pt)"
                    + "\\the\\dimen0\\end",
            //
            "3.23pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a count variable is parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testCount1() throws Exception {

        assertSuccess(//
            "\\count1=123 " + "\\dimen0=\\count1 " + "\\the\\dimen0\\end",
            //
            "???pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a dimen variable divided by zero leads
     * to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testDiv1() throws Exception {

        assertSuccess(//
            "\\dimen1=2pt" + "\\dimen0=(\\dimen1*\\dimen1/1pt)"
                    + "\\the\\dimen0\\end",
            //
            "4.0pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a dimen variable divided by zero leads
     * to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testDivError1() throws Exception {

        assertFailure(//
            "\\dimen1=2pt" + "\\dimen0=(\\dimen1/0)",
            //
            "Arithmetic overflow");
    }

    /**
     * <testcase> Test case showing that unmatched parentheses are recognized.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure("\\dimen0=(/", //
            "Missing number, treated as zero");
    }

    /**
     * <testcase> Test case showing that unmatched parentheses are recognized.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure("\\dimen0=(1pt;", //
            "Missing ) inserted for expression");
    }

    /**
     * <testcase> Test case showing that unmatched parentheses at EOF is
     * recognized. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError3() throws Exception {

        assertFailure("\\dimen0=(1pt", //
            "Missing number, treated as zero");
    }

    /**
     * <testcase> Test case showing that undefined function is recognized.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFunctionError1() throws Exception {

        assertFailure("\\dimen0=(xyzzy())", //
            "Syntax error");
    }

    /**
     * <testcase> Test case showing that max on one dimen results in this value.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMax1() throws Exception {

        assertSuccess(//
            "\\dimen1=1.2pt" + "\\dimen0=max(\\dimen1)" + "\\the\\dimen0\\end",
            //
            "1.2pt" + TERM);
    }

    /**
     * <testcase> Test case showing that max() on three dimens works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMax2() throws Exception {

        assertSuccess(//
            "\\dimen0=max(1pt, 2pt, 3pt)" + "\\the\\dimen0\\end",
            //
            "3.0pt" + TERM);
    }

    /**
     * <testcase> Test case showing that max on dimen works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMax3() throws Exception {

        assertSuccess(//
            "\\dimen0=max(4pt, 2pt, 3pt)" + "\\the\\dimen0\\end",
            //
            "4.0pt" + TERM);
    }

    /**
     * <testcase> Test case showing that the function max needs arguments.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMaxError1() throws Exception {

        assertFailure(//
            "\\dimen0=max()",
            //
            "Missing number, treated as zero");
    }

    /**
     * <testcase> Test case showing that incompatible units lead to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMaxError2() throws Exception {

        assertFailure(//
            "\\dimen0=max(1.2, 12pt)",
            //
            "Incompatible unit for max found: sp^0 <> sp^1");
    }

    /**
     * <testcase> Test case showing that max needs an argument parenthesis.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMaxError3() throws Exception {

        assertFailure("\\dimen0=max", //
            "Unexpected end of file");
    }

    /**
     * <testcase> Test case showing that max needs an argument parenthesis.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMaxError4() throws Exception {

        assertFailure("\\dimen0=max x", //
            "Missing ( inserted for function max instead of the letter x");
    }

    /**
     * <testcase> Test case showing that min() on a single value returns this
     * value. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMin1() throws Exception {

        assertSuccess(//
            "\\dimen1=1.2pt" + "\\dimen0=min(\\dimen1)" + "\\the\\dimen0\\end",
            //
            "1.2pt" + TERM);
    }

    /**
     * <testcase> Test case showing that min on three dimens works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMin2() throws Exception {

        assertSuccess(//
            "\\dimen0=min(1pt, 2pt, 3pt)" + "\\the\\dimen0\\end",
            //
            "1.0pt" + TERM);
    }

    /**
     * <testcase> Test case showing that min() in three dimens works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMin3() throws Exception {

        assertSuccess(//
            "\\dimen0=min(4pt, 2pt, 3pt)" + "\\the\\dimen0\\end",
            //
            "2.0pt" + TERM);
    }

    /**
     * <testcase> Test case showing that the function min() needs an argument.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinError1() throws Exception {

        assertFailure(//
            "\\dimen0=min()",
            //
            "Missing number, treated as zero");
    }

    /**
     * <testcase> Test case showing that min on number and dimen leads to an
     * error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinError2() throws Exception {

        assertFailure(//
            "\\dimen0=min(1.2, 12pt)",
            //
            "Incompatible unit for min found: sp^0 <> sp^1");
    }

    /**
     * <testcase> Test case showing that a integer multiplied dimen variable is
     * parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testMult1() throws Exception {

        assertSuccess(//
            "\\dimen1=1.23pt" + "\\dimen0=(2*\\dimen1)" + "\\the\\dimen0\\end",
            //
            "2.45999pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a dimen variable multiplied by an
     * integer is parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testMult2() throws Exception {

        assertSuccess(//
            "\\dimen1=1.23pt" + "\\dimen0=(\\dimen1 *2)" + "\\the\\dimen0\\end",
            //
            "2.45999pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a fraction multiplied dimen variable is
     * parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testMult3() throws Exception {

        assertSuccess(//
            "\\dimen1=2pt" + "\\dimen0=(1.23*\\dimen1)" + "\\the\\dimen0\\end",
            //
            "2.45999pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a dimen variable multiplied by a
     * fraction is parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testMult4() throws Exception {

        assertSuccess(//
            "\\dimen1=2pt" + "\\dimen0=(\\dimen1*1.23)" + "\\the\\dimen0\\end",
            //
            "2.45999pt" + TERM);
    }

    /**
     * <testcase> Test case showing that 0pt is parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt0() throws Exception {

        assertSuccess(//
            "\\dimen0=0pt\\the\\dimen0\\end",
            //
            "0.0pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a positive number with fraction is
     * parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt1() throws Exception {

        assertSuccess(//
            "\\dimen0=123.4pt\\the\\dimen0\\end",
            //
            "123.4pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a negative number with fraction is
     * parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt2() throws Exception {

        assertSuccess(//
            "\\dimen0=-123.4pt\\the\\dimen0\\end",
            //
            "-123.4pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a positive fraction number is parsed.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt3() throws Exception {

        assertSuccess(//
            "\\dimen0=.45pt\\the\\dimen0\\end",
            //
            "0.45pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a negative fraction number is parsed.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt4() throws Exception {

        assertSuccess(//
            "\\dimen0=-.45pt\\the\\dimen0\\end",
            //
            "-0.45pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a dimen variable subtracted by a
     * fraction is parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testSubtract1() throws Exception {

        assertSuccess(//
            "\\dimen1=2pt" + "\\dimen0=(\\dimen1-1.23pt)"
                    + "\\the\\dimen0\\end",
            //
            "0.77pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a complex formula works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testTerm1() throws Exception {

        assertSuccess(//
            "\\dimen1=1.2pt" + "\\dimen0=((1+2)*\\dimen1)"
                    + "\\the\\dimen0\\end",
            //
            "3.59999pt" + TERM);
    }

    /**
     * <testcase> Test case showing that dimens need a unit. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUnitError1() throws Exception {

        assertFailure(//
            "\\dimen1=2pt" + "\\dimen0=(1)",
            //
            "Illegal unit of measure (pt inserted)");
    }

    /**
     * <testcase> Test case showing that a unit of pt^{-1} can not be assigned
     * to a dimen register. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testUnitError2() throws Exception {

        assertFailure(//
            "\\dimen1=2pt" + "\\dimen0=(1/\\dimen1)",
            //
            "Illegal unit for a length value found: sp^-1");
    }

    /**
     * <testcase> Test case showing that a value with unit pt^2 can not be
     * assigned to a dimen register. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUnitError3() throws Exception {

        assertFailure(//
            "\\dimen0=(1pt*1pt)",
            //
            "Illegal unit for a length value found: sp^2");
    }

    /**
     * <testcase> Test case showing that a value with dimension pt^3 can not be
     * assigned to a dimen. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUnitError4() throws Exception {

        assertFailure(//
            "\\dimen0=(1pt*1pt*1pt)",
            //
            "Illegal unit for a length value found: sp^3");
    }

    /**
     * <testcase> Test case showing that a dimen variable is parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVar1() throws Exception {

        assertSuccess(//
            "\\dimen1=1.23pt" + "\\dimen0=\\dimen1 " + "\\the\\dimen0\\end",
            //
            "1.23pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a negated dimen variable is parsed.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVar2() throws Exception {

        assertSuccess(//
            "\\dimen1=1.23pt" + "\\dimen0=-\\dimen1 " + "\\the\\dimen0\\end",
            //
            "-1.23pt" + TERM);
    }

    /**
     * <testcase> Test case showing that a double negated dimen variable is
     * parsed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVar3() throws Exception {

        assertSuccess(//
            "\\dimen1=1.23pt" + "\\dimen0=--\\dimen1 " + "\\the\\dimen0\\end",
            //
            "1.23pt" + TERM);
    }

}
