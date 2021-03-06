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

package org.extex.backend.pageFilter.selector;

/**
 * A Rule containing an interval of pages.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
class IntervalRule implements Rule {

  /**
   * The field {@code from} contains the lower limit.
   */
  private final int from;

  /**
   * The field {@code to} contains the upper limit.
   */
  private final int to;

  /**
   * Creates a new object.
   *
   * @param from the lower limit
   * @param to   the upper limit
   */
  public IntervalRule( int from, int to ) {

    this.from = from;
    this.to = to;
  }

  /**
   * Check that a given page is covered by this interval.
   *
   * @param value the value to check
   * @return {@code true} iff the value is in the interval
   */
  public boolean check( int value ) {

    return value >= from && value <= to;
  }

}
