/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables.gps;

import org.extex.font.format.xtf.tables.XtfGlyphName;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Lookup tables provide a way of looking up information about a glyph index.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public abstract class XtfLookupTable implements XMLWriterConvertible {

  /**
   * The subtable count.
   */
  protected int count;

  /**
   * The table format
   */
  private final int format;

  /**
   * The glyph name.
   */
  private final XtfGlyphName xtfGlyph;

  /**
   * Create a new object.
   *
   * @param f        the format.
   * @param xtfGlyph The glyph name.
   */
  public XtfLookupTable( int f, XtfGlyphName xtfGlyph ) {

    format = f;
    this.xtfGlyph = xtfGlyph;
  }

  /**
   * Getter for count.
   *
   * @return the count
   */
  public int getCount() {

    return count;
  }

  /**
   * Returns the format.
   *
   * @return Returns the format.
   */
  public int getFormat() {

    return format;
  }

  /**
   * Getter for xtfGlyph.
   *
   * @return the xtfGlyph
   */
  public XtfGlyphName getXtfGlyph() {

    return xtfGlyph;
  }
}
