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

package org.extex.exindex.core.type.raw;

import org.extex.exindex.core.type.LocationClassContainer;
import org.extex.exindex.core.type.StructuredIndex;
import org.extex.exindex.core.type.attribute.AttributesContainer;

import java.util.List;
import java.util.logging.Logger;

/**
 * This interface describes a reference specification.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface Reference {

  /**
   * Check the reference for validity.
   *
   * @param logger        the logger
   * @param entry         the current index entry
   * @param index         the index for cross-reference lookup
   * @param crossrefClass the container for cross-reference classes
   * @param openPages     the list of open pages
   * @param attributes    the defined attributes
   * @return {@code true} iff everything is ok
   */
  boolean check( Logger logger, RawIndexentry entry, StructuredIndex index,
                 LocationClassContainer crossrefClass,
                 List<OpenLocationReference> openPages,
                 AttributesContainer attributes );

  /**
   * Getter for the layer.
   *
   * @return the layer or {@code null}
   */
  String getLayer();

  /**
   * Getter for the location.
   *
   * @return the location
   */
  String[] getLocation();

}
