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

package org.extex.exindex.core.type;

import org.extex.exindex.core.type.alphabet.LocationClass;
import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.lisp.exception.LException;

/**
 * This interface describes the capability to read information for a location
 * class from a container.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface LocationClassContainer {

  /**
   * Add a location class to the ones defined. An already defined location
   * class for the same key is silently overwritten.
   *
   * @param name          the name
   * @param locationClass the location class
   * @return {@code true} iff the location class is new and has been
   * defined
   */
  boolean addLocationClass( String name, LocationClass locationClass );

  /**
   * Get a named location class.
   *
   * @param name the name
   * @return the location class or {@code null}
   */
  LocationClass lookupLocationClass( String name );

  /**
   * Create a page reference for this location class.
   *
   * @param encap the encapsulation
   * @param page  the page
   * @return the page reference
   */
  PageReference makePageReference( String encap, String page );

  /**
   * Order the location classes according to the given list. The other
   * location classes follow in the original order.
   *
   * @param list the list of location class names
   * @throws LException in case that a location class name is not defined
   */
  void orderLocationClasses( String[] list ) throws LException;

}
