/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.core.dimen;

/**
 * This interface describes the features of a {@link org.extex.core.dimen.Dimen
 * Dimen} which do not modify the value.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface FixedDimen {

  /**
   * Create a copy of this instance with the same order and value.
   *
   * @return a new copy of this instance
   */
  FixedDimen copy();

  /**
   * Compares the current instance with another GlueComponent for equality.
   *
   * @param d the other GlueComponent to compare to. If this parameter is
   *          {@code null} then the comparison fails.
   * @return {@code true} iff |this| == |d|
   */
  boolean eq( FixedDimen d );

  /**
   * Compares the current instance with another GlueComponent.
   *
   * @param d the other GlueComponent to compare to
   * @return {@code true} iff this is greater or equal to d
   */
  boolean ge( FixedDimen d );

  /**
   * Getter for the value in scaled points (sp).
   *
   * @return the value in internal units of scaled points (sp)
   */
  long getValue();

  /**
   * Compares the current instance with another GlueComponent.
   *
   * @param d the other GlueComponent to compare to
   * @return {@code true} iff |this| &gt; |d|
   */
  boolean gt( FixedDimen d );

  /**
   * Compares the current instance with another GlueComponent.
   *
   * @param d the other GlueComponent to compare to
   * @return {@code true} iff this is less or equal to d
   */
  boolean le( FixedDimen d );

  /**
   * Compares the current instance with another GlueComponent.
   *
   * @param d the other GlueComponent to compare to
   * @return {@code true} iff |this| &lt; |d|
   */
  boolean lt( FixedDimen d );

  /**
   * Compares the current instance with another GlueComponent for equality.
   *
   * @param d the other GlueComponent to compare to. If this parameter is
   *          {@code null} then the comparison fails.
   * @return {@code false} iff |this| == |d|
   */
  boolean ne( FixedDimen d );

  /**
   * Determine the printable representation of the object.
   *
   * @return the printable representation
   * @see #toString(StringBuilder)
   */
  @Override
  String toString();

  /**
   * Determine the printable representation of the object and append it to the
   * given StringBuilder.
   *
   * @param sb the output string buffer
   * @see #toString()
   */
  void toString( StringBuilder sb );

}
