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

import org.junit.Test;

/**
 * This is a test suite for the count data type.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CountTest extends BasicCountTester {

    /**
     * {@inheritDoc}
     *
     * @see org.extex.core.count.BasicCountTester#newC(long)
     */
    @Override
    protected Count newC(long value) {

        return new Count(value);
    }
    /**
     * Test method for {@link org.extex.core.count.Count#add(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testAddLong() {

        Count count = newC(123L);
        count.add(0);
        assertEquals(123L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#add(org.extex.core.count.Count)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testAddCount() {

        Count count = newC(123L);
        count.add(new Count(0));
        assertEquals(123L, count.getValue());
    }

    /**
     * Test method for {@link org.extex.core.count.Count#divide(long)}.
     */
    @Test(expected = ArithmeticException.class)
    public final void testDivideLong0() {

        Count count = newC(123L);
        count.divide(0);
    }

    /**
     * Test method for {@link org.extex.core.count.Count#divide(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testDivideLong1() {

        Count count = newC(123L);
        count.divide(1);
        assertEquals(123L, count.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#divide(org.extex.core.count.Count)}.
     */
    @Test(expected = ArithmeticException.class)
    public final void testDivideCount0() {

        Count count = newC(123L);
        count.divide(newC(0));
    }

    /**
     * Test method for
     * {@link org.extex.core.count.Count#divide(org.extex.core.count.Count)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testDivideCount1() {

        Count count = newC(123L);
        count.divide(Count.ONE);
        assertEquals(123L, count.getValue());
    }
    
    /**
     * Test method for {@link org.extex.core.count.Count#multiply(long)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMultiplyLong1() {
        
        Count count = newC(0);
        count.multiply(1);
        assertEquals(0L, count.getValue());
    }
    
    /**
     * Test method for
     * {@link org.extex.core.count.Count#set(org.extex.core.count.Count)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testSetCount0() {
        
        Count count = newC(123L);
        count.set(newC(123));
        assertEquals(123L, count.getValue());
    }
    
    /**
     * Test method for
     * {@link org.extex.core.count.Count#multiply(org.extex.core.count.Count)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMultiplyCount1() {
        
        Count count = newC(0);
        count.multiply(Count.ONE);
        assertEquals(0L, count.getValue());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.core.count.BasicCountTester#newC(org.extex.core.count.FixedCount)
     */
    @Override
    protected Count newC(FixedCount value) {

        return new Count(value);
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
     * {@link org.extex.core.count.Count#divide(org.extex.core.count.Count)}.
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
     * {@link org.extex.core.count.Count#divide(org.extex.core.count.Count)}.
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
     * {@link org.extex.core.count.Count#multiply(org.extex.core.count.Count)}.
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
     * {@link org.extex.core.count.Count#multiply(org.extex.core.count.Count)}.
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
     * {@link org.extex.core.count.Count#set(org.extex.core.count.Count)}.
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testSetCount() {

        Count count = new Count(123L);
        count.set(new Count(-1));
        assertEquals(-1L, count.getValue());
    }


}
