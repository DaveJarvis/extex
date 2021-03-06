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

package org.extex.font.format;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.font.ExtexFont;
import org.extex.font.FontKey;

/**
 * This class implements a dummy font which does not contain any characters.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class NullExtexFont implements ExtexFont {

  public FontKey getActualFontKey() {

    return null;
  }

  public FixedDimen getActualSize() {

    return Dimen.ZERO_PT;
  }

  public FixedGlue getDepth( UnicodeChar uc ) {

    return FixedGlue.ZERO;
  }

  public FixedDimen getDesignSize() {

    return Dimen.ONE_PT;
  }

  public FixedDimen getEm() {

    return Dimen.ZERO_PT;
  }

  public FixedDimen getEx() {

    return Dimen.ZERO_PT;
  }

  public FixedDimen getFontDimen( String name ) {

    return null;
  }

  public FontKey getFontKey() {

    return null;
  }

  public String getFontName() {

    return "nullfont";
  }

  public FixedGlue getHeight( UnicodeChar uc ) {

    return FixedGlue.ZERO;
  }

  public FixedDimen getItalicCorrection( UnicodeChar uc ) {

    return Dimen.ZERO_PT;
  }

  /**
   * org.extex.core.UnicodeChar)
   */
  public FixedDimen getKerning( UnicodeChar uc1, UnicodeChar uc2 ) {

    return Dimen.ZERO_PT;
  }

  /**
   * org.extex.core.UnicodeChar)
   */
  public UnicodeChar getLigature( UnicodeChar uc1, UnicodeChar uc2 ) {

    return null;
  }

  public FixedCount getScaleFactor() {

    return Count.THOUSAND;
  }

  public FixedGlue getSpace() {

    return FixedGlue.ZERO;
  }

  public FixedGlue getWidth( UnicodeChar uc ) {

    return FixedGlue.ZERO;
  }

  public boolean hasGlyph( UnicodeChar uc ) {

    return false;
  }

}
