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

import org.extex.font.exception.CorruptFontException;
import org.extex.framework.configuration.exception.ConfigurationException;

import java.io.InputStream;

/**
 * Interface for font which can be loaded from an external resource.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public interface LoadableFont extends ExtexFont {

  /**
   * Load a font from the stream.
   *
   * @param in      The stream for reading.
   * @param factory Tthe font factory.
   * @param fontKey The font key.
   * @throws CorruptFontException   if the font is corrupt.
   * @throws ConfigurationException from the configuration system.
   */
  void loadFont( InputStream in, CoreFontFactory factory, FontKey fontKey )
      throws CorruptFontException,
      ConfigurationException;

}
