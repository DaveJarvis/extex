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

package org.extex.interpreter.expression;

import org.extex.core.exception.helping.MissingNumberException;
import org.extex.interpreter.expression.term.Accumulator;
import org.extex.interpreter.expression.term.TBoolean;
import org.extex.interpreter.expression.term.TCount;
import org.extex.interpreter.expression.term.TDouble;

/**
 * This class is a test suite for the expression evaluator.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4733 $
 */
public class ExTeXEvaluatorTest extends TeXEvaluatorTest {

    /**
     * Main program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(ExTeXEvaluatorTest.class);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.expression.TeXEvaluatorTest#getInstanceForTest()
     */
    @Override
    protected Evaluator getInstanceForTest() {

        return new ExTeXEvaluator();
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCount111() throws Exception {

        Accumulator a = apply("(1+2*3)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TCount);
        assertEquals(7, ((TCount) a.getValue()).getValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCount112() throws Exception {

        Accumulator a = apply("((1+2)*3)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TCount);
        assertEquals(9, ((TCount) a.getValue()).getValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCount113() throws Exception {

        Accumulator a = apply("((1+2)*(3))");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TCount);
        assertEquals(9, ((TCount) a.getValue()).getValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCount114() throws Exception {

        Accumulator a = apply("(((1+2))*3)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TCount);
        assertEquals(9, ((TCount) a.getValue()).getValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCount5() throws Exception {

        Accumulator a = apply("(1+2*3-4)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TCount);
        assertEquals(3, ((TCount) a.getValue()).getValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCountEq1() throws Exception {

        Accumulator a = apply("(1==1)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TBoolean);
        assertTrue(((TBoolean) a.getValue()).isValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCountEq2() throws Exception {

        Accumulator a = apply("(1+2==3)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TBoolean);
        assertTrue(((TBoolean) a.getValue()).isValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCountEq2s() throws Exception {

        Accumulator a = apply("( 1 + 2 == 3 )");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TBoolean);
        assertTrue(((TBoolean) a.getValue()).isValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCountEq3() throws Exception {

        Accumulator a = apply("(3==1+2)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TBoolean);
        assertTrue(((TBoolean) a.getValue()).isValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCountEq4() throws Exception {

        Accumulator a = apply("((3==1+2))");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TBoolean);
        assertTrue(((TBoolean) a.getValue()).isValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCountEq5() throws Exception {

        Accumulator a = apply("(3==(1+2))");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TBoolean);
        assertTrue(((TBoolean) a.getValue()).isValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCountLe1() throws Exception {

        Accumulator a = apply("(1<=2)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TBoolean);
        assertTrue(((TBoolean) a.getValue()).isValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCountLe1u() throws Exception {

        Accumulator a = apply("(1\u2264 2)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TBoolean);
        assertTrue(((TBoolean) a.getValue()).isValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCountPlus11() throws Exception {

        Accumulator a = apply("(1.+2)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TDouble);
        assertEquals(3., ((TDouble) a.getValue()).getValue(), 0.);
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testPi1() throws Exception {

        Accumulator a = apply("pi");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TDouble);
        assertEquals(3.141592, ((TDouble) a.getValue()).getValue(), 0.00001);
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testPi2() throws Exception {

        Accumulator a = apply("(pi/2)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TDouble);
        assertEquals(Math.PI / 2., ((TDouble) a.getValue()).getValue(), 0.00001);
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLogic1() throws Exception {

        Accumulator a = apply("(1=1 && 2<3)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TBoolean);
        assertTrue(((TBoolean) a.getValue()).isValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLogic2() throws Exception {

        Accumulator a = apply("(1=1 || 2<3)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof TBoolean);
        assertTrue(((TBoolean) a.getValue()).isValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testError1() throws Exception {

        try {
            apply("(1==true)");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e instanceof MissingNumberException);
        }
    }

}
