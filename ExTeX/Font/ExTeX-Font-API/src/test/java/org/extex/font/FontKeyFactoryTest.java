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

package org.extex.font;

import junit.framework.TestCase;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test suite for the font key factory.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class FontKeyFactoryTest extends TestCase {

  /**
   * The font key factory.
   */
  private static final FontKeyFactory factory = new FontKeyFactory();

  /**
   * Test 01.
   */
  @Test
  public final void test01() {

    assertNotNull( factory );

    FontKey key = factory.newInstance( "cmr10" );
    key.put( FontKey.SIZE, Dimen.ONE_INCH );

    assertEquals( "cmr10 size=72.26999pt", key.toString() );
  }

  /**
   * Test 02.
   */
  @Test
  public final void test02() {

    assertNotNull( factory );

    FontKey key = factory.newInstance( "cmr10" );
    key.put( FontKey.SIZE, (FixedDimen) null );

    assertEquals( "cmr10 size=null", key.toString() );
  }

  /**
   * Test 03.
   */
  @Test
  public final void test03() {

    assertNotNull( factory );

    FontKey key = factory.newInstance( "cmr10" );
    key.put( FontKey.SIZE, (FixedDimen) null );
    java.util.List<String> list = new ArrayList<String>();
    list.add( "kern" );
    list.add( "latn" );
    key.add( list );

    assertEquals( "cmr10 size=null kern latn", key.toString() );
  }

  /**
   * Test 04.
   */
  @Test
  public final void test04() {

    assertNotNull( factory );

    FontKey key = factory.newInstance( "cmr10" );
    java.util.List<String> list = new ArrayList<String>();
    list.add( "kern" );
    list.add( "latn" );
    key.add( list );

    assertEquals( "cmr10 kern latn", key.toString() );
  }

  /**
   * Test 04.
   */
  @Test
  public final void test05() {

    assertNotNull( factory );

    FontKey key = factory.newInstance( "cmr10", Dimen.ONE_INCH );
    java.util.List<String> list = new ArrayList<String>();
    list.add( "kern" );
    list.add( "latn" );
    key.add( list );

    assertEquals( "cmr10 size=72.26999pt kern latn", key.toString() );
  }

}
