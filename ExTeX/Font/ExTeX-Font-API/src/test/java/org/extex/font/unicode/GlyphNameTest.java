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

package org.extex.font.unicode;

import junit.framework.TestCase;
import org.extex.core.UnicodeChar;

/**
 * Test for the mapping between the glyph name and the Unicode char.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class GlyphNameTest extends TestCase {

  /**
   * Test method for
   * {@link org.extex.font.unicode.GlyphName#getUnicode(java.lang.String)}.
   *
   * @throws Exception if an error occurred.
   */
  public void testGetUnicode01() throws Exception {

    GlyphName g = GlyphName.getInstance();
    assertNotNull( g );

    // A;0041
    assertEquals( UnicodeChar.get( Integer.parseInt( "0041", 16 ) ), g
        .getUnicode( "A" ) );

    // zukatakana;30BA
    assertEquals( UnicodeChar.get( Integer.parseInt( "30BA", 16 ) ), g
        .getUnicode( "zukatakana" ) );

    assertNull( g.getUnicode( "notdefined!" ) );

  }

  /**
   * Test method for
   * {@link org.extex.font.unicode.GlyphName#getGlyphname(org.extex.core.UnicodeChar)}.
   *
   * @throws Exception if an error occurred.
   */
  public void testGetGlyphname() throws Exception {

    GlyphName g = GlyphName.getInstance();
    assertNotNull( g );

    // A;0041
    assertEquals( "A", g.getGlyphname( UnicodeChar.get( Integer.parseInt(
        "0041", 16 ) ) ) );
    // zukatakana;30BA
    assertEquals( "zukatakana", g.getGlyphname( UnicodeChar.get( Integer
                                                                     .parseInt(
                                                                         "30BA",
                                                                         16 ) ) ) );

    assertNull( g
                    .getGlyphname( UnicodeChar.get( Integer.parseInt( "FFFF",
                                                                      16 ) ) ) );

    assertEquals( "space", g.getGlyphname( UnicodeChar.get( 32 ) ) );

  }

}
