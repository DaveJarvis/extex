/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.dimen.FixedDimen;

import java.util.List;
import java.util.Map;

/**
 * Factory for the {@link FontKey}.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class FontKeyFactory {

  /**
   * Fontkey for null.
   */
  public static final FontKey NULL_KEY = new FontKey( "nullfont" );

  /**
   * Create a new object.
   */
  public FontKeyFactory() {

  }

  /**
   * Returns a new font key instance.
   *
   * @param fk  The font key.
   * @param tag The list of tags.
   * @return Returns a new font key instance.
   */
  public FontKey newInstance( FontKey fk, List<String> tag ) {

    FontKey newfk = new FontKey( fk );
    newfk.add( tag );

    return newfk;
  }

  /**
   * Returns a new font key instance.
   *
   * @param fk     The font key.
   * @param theMap The map with key value entries.
   * @return Returns a new font key instance.
   */
  public FontKey newInstance( FontKey fk, Map<String, ?> theMap ) {

    FontKey newfk = new FontKey( fk );
    newfk.put( theMap );

    return newfk;
  }

  /**
   * Returns a new font key instance.
   *
   * @param fk     The font key.
   * @param theMap The map with key value entries.
   * @param tag    The list of tags.
   * @return Returns a new font key instance.
   */
  public FontKey newInstance( FontKey fk, Map<String, ?> theMap,
                              List<String> tag ) {

    FontKey newfk = new FontKey( fk );
    newfk.put( theMap );
    newfk.add( tag );

    return newfk;
  }

  /**
   * Returns a new font key instance.
   *
   * @param fk    The font key.
   * @param key   The key.
   * @param value The value.
   * @return Returns a new font key instance.
   */
  public FontKey newInstance( FontKey fk, String key, boolean value ) {

    FontKey newfk = new FontKey( fk );
    newfk.put( key, value );

    return newfk;
  }

  /**
   * Returns a new font key instance.
   *
   * @param fk    The font key.
   * @param key   The key.
   * @param value The value.
   * @return Returns a new font key instance.
   */
  public FontKey newInstance( FontKey fk, String key, FixedDimen value ) {

    FontKey newfk = new FontKey( fk );
    newfk.put( key, value );

    return newfk;
  }

  /**
   * Returns a new font key instance.
   *
   * @param fk    The font key.
   * @param key   The key.
   * @param value The value.
   * @return Returns a new font key instance.
   */
  public FontKey newInstance( FontKey fk, String key, String value ) {

    FontKey newfk = new FontKey( fk );
    newfk.put( key, value );

    return newfk;
  }

  /**
   * Returns a new font key instance.
   *
   * @param theFontname The font name.
   * @return Returns a new font key instance.
   */
  public FontKey newInstance( String theFontname ) {

    return new FontKey( theFontname );
  }

  /**
   * Returns a new font key instance.
   *
   * @param theFontname The font name.
   * @param size        The size.
   * @return Returns a new font key instance.
   */
  public FontKey newInstance( String theFontname, FixedDimen size ) {

    FontKey newfk = new FontKey( theFontname );
    newfk.put( FontKey.SIZE, size );

    return newfk;
  }
}
