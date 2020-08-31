/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.language;

import org.extex.language.hyphenation.Hyphenator;
import org.extex.language.ligature.LigatureBuilder;
import org.extex.language.word.WordTokenizer;

/**
 * This interface describes a container for all language-dependent information.
 * <p>
 * This container subsumes a hyphenator. In addition the access to the ligature
 * builder is enabled.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface Language extends Hyphenator, LigatureBuilder, WordTokenizer {

  /**
   * Getter for the name.
   *
   * @return the name
   */
  String getName();

  /**
   * Setter for the name.
   *
   * @param name the name
   */
  void setName( String name );

}
