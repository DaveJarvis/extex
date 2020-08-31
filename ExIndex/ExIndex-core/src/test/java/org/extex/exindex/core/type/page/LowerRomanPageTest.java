/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.type.page;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is a test suite for lowercase roman page numbers.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LowerRomanPageTest {

  /**
   * Test method for
   * {@link org.extex.exindex.core.type.page.LowerRomanPage#LowerRomanPage(java.lang.String, java.lang.String)}.
   */
  @Test
  public final void test1() {

    assertEquals( 1, new LowerRomanPage( "", "i" ).getOrd() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.type.page.LowerRomanPage#LowerRomanPage(java.lang.String, java.lang.String)}.
   */
  @Test
  public final void test2() {

    assertEquals( 2, new LowerRomanPage( "", "ii" ).getOrd() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.type.page.LowerRomanPage#LowerRomanPage(java.lang.String, java.lang.String)}.
   */
  @Test
  public final void test3() {

    assertEquals( 3, new LowerRomanPage( "", "iii" ).getOrd() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.type.page.LowerRomanPage#LowerRomanPage(java.lang.String, java.lang.String)}.
   */
  @Test
  public final void test4() {

    assertEquals( 4, new LowerRomanPage( "", "iv" ).getOrd() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.type.page.LowerRomanPage#LowerRomanPage(java.lang.String, java.lang.String)}.
   */
  @Test
  public final void test4o() {

    assertEquals( 4, new LowerRomanPage( "", "iiii" ).getOrd() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.type.page.LowerRomanPage#LowerRomanPage(java.lang.String, java.lang.String)}.
   */
  @Test
  public final void test9() {

    assertEquals( 9, new LowerRomanPage( "", "ix" ).getOrd() );
  }

  /**
   * Test method for
   * {@link org.extex.exindex.core.type.page.LowerRomanPage#LowerRomanPage(java.lang.String, java.lang.String)}.
   */
  @Test(expected = IllegalArgumentException.class)
  public final void testError1() {

    new LowerRomanPage( "", "" );
  }

}
