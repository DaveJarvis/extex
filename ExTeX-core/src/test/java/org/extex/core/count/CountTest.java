/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.core.count;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * This is a test suite for the count data type.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CountTest {

    /**
     * Test method for {@link org.extex.core.count.Count#add(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testAddLong() {

        Count count = new Count(123L);
        count.add(0);
        assertEquals(123L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#add(FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testAddCount() {

        Count count = new Count(123L);
        count.add(new Count(0));
        assertEquals(123L, count.getValue());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#divide(long)}.
     */
    @Test(expected = ArithmeticException.class)
    public final void testDivideLong0() {

        Count count = new Count(123L);
        try {
            count.divide(0);
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for {@link org.extex.core.count.Count#divide(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testDivideLong1() {

        Count count = new Count(123L);
        count.divide(1);
        assertEquals(123L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#divide(FixedCount)}.
     */
    @Test(expected = ArithmeticException.class)
    public final void testDivideCount0() {

        Count count = new Count(123L);
        try {
            count.divide(new Count(0));
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#divide(FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testDivideCount1() {

        Count count = new Count(123L);
        count.divide(Count.ONE);
        assertEquals(123L, count.getValue());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#multiply(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMultiplyLong1() {

        Count count = new Count(0);
        count.multiply(1);
        assertEquals(0L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#set(FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testSetCount0() {

        Count count = new Count(123L);
        count.set(new Count(123));
        assertEquals(123L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#multiply(FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMultiplyCount1() {

        Count count = new Count(0);
        count.multiply(Count.ONE);
        assertEquals(0L, count.getValue());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#divide(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testDivideLong2() {

        Count count = new Count(123L);
        count.divide(-1);
        assertEquals(-123L, count.getValue());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#divide(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testDivideLong3() {

        Count count = new Count(123L);
        count.divide(2);
        assertEquals(61L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#divide(FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testDivideCount2() {

        Count count = new Count(123L);
        count.divide(new Count(-1));
        assertEquals(-123L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#divide(FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testDivideCount3() {

        Count count = new Count(123L);
        count.divide(new Count(2));
        assertEquals(61L, count.getValue());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#multiply(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMultiplyLong2() {

        Count count = new Count(123);
        count.multiply(0);
        assertEquals(0L, count.getValue());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#multiply(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMultiplyLong3() {

        Count count = new Count(123);
        count.multiply(-1);
        assertEquals(-123L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#multiply(FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMultiplyCount2() {

        Count count = new Count(123);
        count.multiply(new Count(0));
        assertEquals(0L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#multiply(FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMultiplyCount3() {

        Count count = new Count(123);
        count.multiply(new Count(-1));
        assertEquals(-123L, count.getValue());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#set(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testSetLong1() {

        Count count = new Count(123L);
        count.set(-1);
        assertEquals(-1L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#set(FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testSetCount() {

        Count count = new Count(123L);
        count.set(new Count(-1));
        assertEquals(-1L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#Count(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testCountFixedCount() {

        FixedCount count = new Count(1L);
        assertEquals(1L, new Count(count).getValue());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#Count(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testCountLong0() {

        FixedCount count = new Count(0L);
        assertEquals(0L, count.getValue());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#Count(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testCountLong1() {

        FixedCount count = new Count(1L);
        assertEquals(1L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#eq(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testEq1() {

        assertEquals(true, new Count(0).eq(new Count(0)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#eq(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testEq2() {

        assertEquals(false, new Count(0).eq(new Count(1)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#ge(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGe1() {

        assertEquals(true, new Count(0).ge(new Count(0)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#ge(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGe2() {

        assertEquals(false, new Count(0).ge(new Count(1)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#ge(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGe3() {

        assertEquals(true, new Count(1).ge(new Count(0)));
    }

    /**
     * Test method for {@link org.extex.core.count.Count#getValue()}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGetValue() {

        FixedCount count = new Count(-123L);
        assertEquals(-123L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#gt(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGt1() {

        assertEquals(true, new Count(1).gt(new Count(0)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#gt(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGt2() {

        assertEquals(false, new Count(1).gt(new Count(1)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#le(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testLe1() {

        assertEquals(true, new Count(1).le(new Count(1)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#le(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testLe2() {

        assertEquals(true, new Count(0).le(new Count(1)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#le(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testLe3() {

        assertEquals(false, new Count(1).le(new Count(0)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#lt(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testLt1() {

        assertEquals(false, new Count(1).lt(new Count(0)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#lt(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testLt2() {

        assertEquals(false, new Count(0).lt(new Count(0)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#lt(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testLt3() {

        assertEquals(true, new Count(0).lt(new Count(1)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#ne(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testNe1() {

        assertEquals(false, new Count(0).ne(new Count(0)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#ne(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testNe2() {

        assertEquals(true, new Count(0).ne(new Count(1)));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#ne(org.extex.core.count.FixedCount)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testNe3() {

        assertEquals(true, new Count(1).ne(new Count(0)));
    }

    /**
     * Test method for {@link org.extex.core.count.Count#toString()}.
     */
    @Test
    public final void testToString0() {

        assertEquals("0", new Count(0).toString());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#toString()}.
     */
    @Test
    public final void testToString1() {

        assertEquals("123", new Count(123).toString());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#toString()}.
     */
    @Test
    public final void testToString2() {

        assertEquals("-123", new Count(-123).toString());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#toString(java.lang.StringBuffer)}.
     */
    @Test
    public final void testToStringStringBuffer() {

        StringBuffer sb = new StringBuffer();
        new Count(-123).toString(sb);
        assertEquals("-123", sb.toString());
    }

}
