/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.paragraphBuilder.impl;

/**
 * This is a container for a list of break points and the associated penalty.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
class Breaks {

  /**
   * The field {@code penalty} contains the accumulated penalty for the
   * breaks contained.
   */
  private final int penalty;

  /**
   * The field {@code points} contains the array of break point actually
   * used.
   */
  private final int[] points;

  /**
   * Creates a new object.
   *
   * @param thePenalty the accumulated penalty
   * @param thePoints  the array of points
   */
  public Breaks( int thePenalty, int[] thePoints ) {

    this.penalty = thePenalty;
    this.points = thePoints;
  }

  /**
   * Getter for penalty.
   *
   * @return the penalty.
   */
  public int getPenalty() {

    return this.penalty;
  }

  /**
   * Getter for points.
   *
   * @return the points.
   */
  public int[] getPoints() {

    return this.points;
  }
}
