/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.db.sorter;

import org.extex.exbib.core.db.Entry;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class provides a Sorter. The sorting order is determined by the byte
 * order of the internal representation of the sorting key. Thus accented
 * characters are located behind all not accented characters.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CodepointSorter
    implements Comparator<Entry>, Sorter, Serializable {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 2008L;

  /**
   * Provide a Comparator which just uses the appropriate keys and compares
   * them without respect to the case.
   */
  public int compare( Entry a, Entry b ) {

    String ka = a.getSortKey();

    if( ka == null ) {
      ka = a.getKey();
    }

    String kb = b.getSortKey();

    if( kb == null ) {
      kb = b.getKey();
    }

    return ka.compareTo( kb );
  }

  /**
   * Sort the given List according to the predefined order. As a side effect
   * the list is modified to reflect the new order.
   *
   * @param list the list to sort
   */
  public void sort( List<Entry> list ) {

    Collections.sort( list, this );
  }

}
