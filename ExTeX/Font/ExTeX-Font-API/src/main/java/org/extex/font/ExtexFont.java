/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.font;

import org.extex.core.UnicodeChar;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;

/**
 * Interface for the extex font.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public interface ExtexFont extends BaseFont {

  /**
   * Returns the actual size of the font.
   *
   * @return Returns the actual size of the font.
   */
  FixedDimen getActualSize();

  /**
   * Returns the depth of the char.
   * <p>
   * If the character is not defined or an invalid value is given, then the
   * depth 0pt is returned.
   *
   * @param uc The Unicode char.
   * @return Returns the depth of the char.
   */
  FixedGlue getDepth( UnicodeChar uc );

  /**
   * Returns the design size of the font.
   *
   * @return Returns the design size of the font.
   */
  FixedDimen getDesignSize();

  /**
   * Returns the size of 'M'.
   *
   * @return Returns the size of 'M'.
   */
  FixedDimen getEm();

  /**
   * Returns the size of 'x'.
   *
   * @return Returns the size of 'x'.
   */
  FixedDimen getEx();

  /**
   * Returns the size of the parameter with the name 'name'.
   * <p>
   * The size are multiples of the design size!
   * </p>
   *
   * @param name The name of the parameter.
   * @return Returns the size of the parameter with the name 'name'.
   */
  FixedDimen getFontDimen( String name );

  /**
   * Returns the name of the font.
   *
   * @return Returns the name of the font.
   */
  String getFontName();

  /**
   * Returns the height of the char.
   * <p>
   * If the character is not defined or an invalid value is given, then the
   * height 0pt is returned.
   *
   * @param uc The Unicode char.
   * @return Returns the height of the char.
   */
  FixedGlue getHeight( UnicodeChar uc );

  /**
   * Returns the italic correction of the char.
   * <p>
   * If the character is not defined or an invalid value is given, then the
   * italic correction 0pt is returned.
   *
   * @param uc The Unicode char.
   * @return Returns the italic correction of the char.
   */
  FixedDimen getItalicCorrection( UnicodeChar uc );

  /**
   * Returns the kerning between two chars.
   *
   * @param uc1 The Unicode char (first one).
   * @param uc2 The Unicode char (second one).
   * @return Returns the kerning between two chars.
   */
  FixedDimen getKerning( UnicodeChar uc1, UnicodeChar uc2 );

  /**
   * Returns the ligature for two chars.
   * <p>
   * If no ligature exists then {@code null} is returned.
   *
   * @param uc1 The Unicode char (first one).
   * @param uc2 The Unicode char (second one).
   * @return Returns the ligature for two chars.
   */
  UnicodeChar getLigature( UnicodeChar uc1, UnicodeChar uc2 );

  /**
   * Returns the scale factor of the font. The scale factor is a multiple of
   * 1000.
   *
   * @return Returns the scale factor of the font.
   */
  FixedCount getScaleFactor();

  /**
   * Returns the size of the 'space'.
   *
   * @return Returns the size of the 'space'.
   */
  FixedGlue getSpace();

  /**
   * Returns the width of the char.
   * <p>
   * If the character is not defined or an invalid value is given, then the
   * width 0pt is returned.
   *
   * @param uc The Unicode char.
   * @return Returns the width of the char.
   */
  FixedGlue getWidth( UnicodeChar uc );

  /**
   * Returns {@code true}, if the glyph exists.
   *
   * @param uc the unicode char
   * @return {@code true}, if the glyph exists.
   */
  boolean hasGlyph( UnicodeChar uc );
}
