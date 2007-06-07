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

/**
 * This is a test suite for the immutable count data type.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CountConstantTest extends BasicCountTester {

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.core.count.BasicCountTester#newC(long)
     */
    @Override
    protected Count newC(long value) {

        // return new CountConstant(value);
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.core.count.BasicCountTester#newC(org.extex.core.count.FixedCount)
     */
    @Override
    protected Count newC(FixedCount value) {

        // return new CountConstant(value);
        return null;
    }

    /**
     * Test method for {@link org.extex.core.count.CountConstant#add(long)}.
     */
    // @Test
    // @SuppressWarnings("boxing")
    // public final void testAddLong0() {
    //
    // CountConstant ic = new CountConstant(0);
    // ic.add(0);
    // assertEquals(0L, ic.getValue());
    // }
    /**
     * Test method for {@link org.extex.core.count.CountConstant#add(long)}.
     */
    // @Test(expected = UnsupportedOperationException.class)
    // public final void testAddLong1() {
    //
    // new CountConstant(0).add(1);
    // }
    /**
     * Test method for {@link org.extex.core.count.CountConstant#divide(long)}.
     */
    // @Test(expected = UnsupportedOperationException.class)
    // public final void testDivideLong2() {
    //
    // new CountConstant(0).divide(2);
    // }
    /**
     * Test method for {@link org.extex.core.count.CountConstant#multiply(long)}.
     */
    // @Test(expected = UnsupportedOperationException.class)
    // public final void testMultiplyLong3() {
    //
    // new CountConstant(1).multiply(2);
    // }
    /**
     * Test method for {@link org.extex.core.count.CountConstant#multiply(long)}.
     */
    // @Test
    // @SuppressWarnings("boxing")
    // public final void testMultiplyLong2() {
    //
    // CountConstant ic = new CountConstant(123);
    // ic.multiply(1);
    // assertEquals(123L, ic.getValue());
    // }
    /**
     * Test method for {@link org.extex.core.count.CountConstant#multiply(long)}.
     */
    // @Test
    // @SuppressWarnings("boxing")
    // public final void testMultiplyLong0() {
    //
    // CountConstant ic = new CountConstant(0);
    // ic.multiply(2);
    // assertEquals(0L, ic.getValue());
    // }
    /**
     * Test method for
     * {@link org.extex.core.count.Count#add(org.extex.core.count.Count)}.
     */
    // @Test(expected = UnsupportedOperationException.class)
    // public final void testAddCount10() {
    //
    // CountConstant ic = new CountConstant(0);
    // ic.add(new Count(2));
    // }
    /**
     * Test method for
     * {@link org.extex.core.count.Count#divide(org.extex.core.count.FixedCount)}.
     */
    // @Test(expected = UnsupportedOperationException.class)
    // public final void testDivideFixedCount10() {
    //
    // CountConstant ic = new CountConstant(0);
    // ic.divide(new Count(2));
    // }
    /**
     * Test method for
     * {@link org.extex.core.count.Count#multiply(org.extex.core.count.FixedCount)}.
     */
    // @Test(expected = UnsupportedOperationException.class)
    // public final void testMultiplyFixedCount10() {
    //
    // CountConstant ic = new CountConstant(0);
    // ic.multiply(new Count(2));
    // }
    /**
     * Test method for {@link org.extex.core.count.Count#set(long)}.
     */
    // @Test(expected = UnsupportedOperationException.class)
    // public final void testSetLong() {
    //
    // CountConstant ic = new CountConstant(0);
    // ic.set(2);
    // }
    /**
     * Test method for
     * {@link org.extex.core.count.Count#set(org.extex.core.count.Count)}.
     */
    // @Test(expected = UnsupportedOperationException.class)
    // public final void testSetCount() {
    //
    // CountConstant ic = new CountConstant(0);
    // ic.set(Count.ONE);
    // }
}
