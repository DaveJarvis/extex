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

package org.extex.font.format.xtf;

import org.extex.font.format.xtf.tables.tag.FeatureTag;
import org.extex.font.format.xtf.tables.tag.LanguageSystemTag;
import org.extex.font.format.xtf.tables.tag.ScriptTag;
import org.extex.font.format.xtf.tables.tag.Tag;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for the class {@link Tag} and all sub classes.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TagTest {

  /**
   * Test add.
   */
  @Test
  public void testAdd() {

    ScriptTag.getInstance( "new" );

    assertTrue( ScriptTag.containsTag( "new " ) );
  }

  /**
   * Test default.
   */
  @Test
  public void testDefault() {

    assertEquals( "DFLT", ScriptTag.getDefault().getTag() );
  }

  /**
   * Test exists.
   */
  @Test
  public void testExists() {

    assertTrue( ScriptTag.containsTag( "latn" ) );
    assertTrue( ScriptTag.containsTag( "lao" ) );
    assertTrue( ScriptTag.containsTag( "arab" ) );
  }

  // /**
  // * Test not exists.
  // */
  // @Test
  // public void testNotExists() {

  // assertTrue(!ScriptTag.isInList("XXX"));
  // }

  /**
   * Test tag found 01.
   */
  @Test
  public void testTagFound01() {

    Tag tag = Tag.getInstance( "DFLT" );
    Assert.assertNotNull( tag );
    assertTrue( tag instanceof ScriptTag );
  }

  /**
   * Test tag found 02.
   */
  @Test
  public void testTagFound02() {

    Tag tag = Tag.getInstance( "hlig" );
    Assert.assertNotNull( tag );
    assertTrue( tag instanceof FeatureTag );
  }

  /**
   * Test tag found 03.
   */
  @Test
  public void testTagFound03() {

    Tag tag = Tag.getInstance( "DEU" );
    Assert.assertNotNull( tag );
    assertTrue( tag instanceof LanguageSystemTag );
  }

  /**
   * Test tag not found.
   */
  @Test
  public void testTagNotFound() {

    Tag tag = Tag.getInstance( "XXX2" );
    Assert.assertNull( tag );
  }
}
