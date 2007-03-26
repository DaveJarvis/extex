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

import junit.framework.TestCase;

import org.extex.core.count.Count;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.MissingNumberException;
import org.extex.interpreter.expression.term.Accumulator;
import org.extex.interpreter.max.StringSource;
import org.extex.interpreter.max.context.ContextImpl;
import org.extex.scanner.type.token.TokenFactoryImpl;

/**
 * This class is a test suite for the expression evaluator.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4787 $
 */
public class TeXEvaluatorTest extends TestCase {

    /**
     * Main program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(TeXEvaluatorTest.class);
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @param s ...
     * @return ...
     *
     * @throws InterpreterException in case of an error
     */
    protected Accumulator apply(String s) throws InterpreterException {

        Evaluator ev = getInstanceForTest();
        Accumulator accumulator = new Accumulator();
        ContextImpl context = new ContextImpl();
        context.setTokenFactory(new TokenFactoryImpl());
        try {
            context.configure(new ConfigurationFactory().newInstance(
                TeXEvaluatorTest.class.getName().replace('.', '/'))
                .getConfiguration("ExTeX"));
            StringSource source = new StringSource(s);
            source.setContext(context);
            ev.eval(accumulator, context, source, null);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            assertFalse(true);
        }
        return accumulator;
    }

    /**
     * TODO gene: missing JavaDoc
     *
     * @return ...
     */
    Evaluator getInstanceForTest() {

        return new TeXEvaluator();
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        try {
            apply("");
            assertFalse(true);
        } catch (InterpreterException e) {
            assertTrue(e instanceof MissingNumberException);
        }
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCount1() throws Exception {

        Accumulator a = apply("123");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof Count);
        assertEquals(123, ((Count) a.getValue()).getValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCount2() throws Exception {

        Accumulator a = apply("123 ");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof Count);
        assertEquals(123, ((Count) a.getValue()).getValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCount3() throws Exception {

        Accumulator a = apply("-123");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof Count);
        assertEquals(-123, ((Count) a.getValue()).getValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCount4() throws Exception {

        Accumulator a = apply("- 123");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof Count);
        assertEquals(-123, ((Count) a.getValue()).getValue());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCountPlus1() throws Exception {

        Accumulator a = apply("(1+2)");
        assertNotNull(a);
        assertNotNull(a.getValue());
        assertTrue(a.getValue() instanceof Count);
        assertEquals(3, ((Count) a.getValue()).getValue());
    }

}
