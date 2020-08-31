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

package org.extex.exindex.core.type.alphabet;


/**
 * This interface describes an container for named alphabets.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface AlphabetContainer {

  /**
   * Add an alphabet.
   *
   * @param name     the name
   * @param alphabet the alphabet
   */
  void addAlphabet( String name, Alphabet alphabet );

  /**
   * Get a named alphabet.
   *
   * @param name the name of the alphabet
   * @return the alphabet or {@code null} if it does not exist
   */
  Alphabet lookupAlphabet( String name );

}
