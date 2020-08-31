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

package org.extex.exindex.core.type.alphabet;

import org.extex.exindex.core.type.page.PageReference;

/**
 * This interface describes the capabilities of a location class to classify
 * strings.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface LocationClass {

  /**
   * Create a page reference for a location. It is assumed that the location
   * belongs to this class. Otherwise an exception might be thrown.
   *
   * @param encap    the encapsulator
   * @param location the string to analyze
   * @return the page reference for the string or {@code null} if the
   * matching failed
   */
  PageReference match( String encap, String location );

  /**
   * Check that the beginning of a given string matches the specification of
   * the location class. As a side effect the characters belonging to this
   * location class are removed from the beginning of the StringBuilder. If
   * the beginning does not match the the argument is left unchanged.
   *
   * @param s the string to match
   * @return {@code true} iff the argument string matches this class
   */
  boolean match( StringBuilder s );

}
