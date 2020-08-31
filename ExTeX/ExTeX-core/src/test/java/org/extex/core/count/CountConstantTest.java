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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is a test suite for the immutable count data type.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CountConstantTest {

  /**
   * Test method for
   * {@link org.extex.core.count.Count#Count(org.extex.core.count.FixedCount)}
   * .
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testCountFixedCount() {

    FixedCount count = new CountConstant( 1L );
    assertEquals( 1L, new CountConstant( count ).getValue() );
  }

  /**
   * Test method for {@link org.extex.core.count.Count#Count(long)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testCountLong0() {

    FixedCount count = new CountConstant( 0L );
    assertEquals( 0L, count.getValue() );
  }

  /**
   * Test method for {@link org.extex.core.count.Count#Count(long)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testCountLong1() {

    FixedCount count = new CountConstant( 1L );
    assertEquals( 1L, count.getValue() );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#eq(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testEq1() {

    assertEquals( true, new Count( 0 ).eq( new Count( 0 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#eq(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testEq2() {

    assertEquals( false, new Count( 0 ).eq( new Count( 1 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#ge(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testGe1() {

    assertEquals( true, new Count( 0 ).ge( new Count( 0 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#ge(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testGe2() {

    assertEquals( false, new Count( 0 ).ge( new Count( 1 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#ge(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testGe3() {

    assertEquals( true, new Count( 1 ).ge( new Count( 0 ) ) );
  }

  /**
   * Test method for {@link org.extex.core.count.Count#getValue()}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testGetValue() {

    FixedCount count = new CountConstant( -123L );
    assertEquals( -123L, count.getValue() );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#gt(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testGt1() {

    assertEquals( true, new Count( 1 ).gt( new Count( 0 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#gt(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testGt2() {

    assertEquals( false, new Count( 1 ).gt( new Count( 1 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#le(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testLe1() {

    assertEquals( true, new Count( 1 ).le( new Count( 1 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#le(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testLe2() {

    assertEquals( true, new Count( 0 ).le( new Count( 1 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#le(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testLe3() {

    assertEquals( false, new Count( 1 ).le( new Count( 0 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#lt(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testLt1() {

    assertEquals( false, new Count( 1 ).lt( new Count( 0 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#lt(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testLt2() {

    assertEquals( false, new Count( 0 ).lt( new Count( 0 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#lt(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testLt3() {

    assertEquals( true, new Count( 0 ).lt( new Count( 1 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#ne(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testNe1() {

    assertEquals( false, new Count( 0 ).ne( new Count( 0 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#ne(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testNe2() {

    assertEquals( true, new Count( 0 ).ne( new Count( 1 ) ) );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#ne(org.extex.core.count.FixedCount)}.
   */
  @Test
  @SuppressWarnings("boxing")
  public final void testNe3() {

    assertEquals( true, new Count( 1 ).ne( new Count( 0 ) ) );
  }

  /**
   * Test method for {@link org.extex.core.count.Count#toString()}.
   */
  @Test
  public final void testToString0() {

    assertEquals( "0", new CountConstant( 0 ).toString() );
  }

  /**
   * Test method for {@link org.extex.core.count.Count#toString()}.
   */
  @Test
  public final void testToString1() {

    assertEquals( "123", new CountConstant( 123 ).toString() );
  }

  /**
   * Test method for {@link org.extex.core.count.Count#toString()}.
   */
  @Test
  public final void testToString2() {

    assertEquals( "-123", new CountConstant( -123 ).toString() );
  }

  /**
   * Test method for
   * {@link org.extex.core.count.Count#toString(StringBuilder)}.
   */
  @Test
  public final void testToStringStringBuilder() {

    StringBuilder sb = new StringBuilder();
    new CountConstant( -123 ).toString( sb );
    assertEquals( "-123", sb.toString() );
  }

}
