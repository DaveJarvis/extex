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

package org.extex.core.glue;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;

/**
 * This interface describes the features of a {@link org.extex.core.glue.Glue
 * Glue} which do not modify the value.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface FixedGlue {

  /**
   * The constant {@code NORMAL_ORDER} contains the value for the normal
   * &ndash; immutable &ndash; order.
   */
  int NORMAL_ORDER = 0;

  /**
   * The constant {@code ZERO} contains the value of width 0pt without any
   * stretchablity or shrinkability.
   */
  FixedGlue ZERO = new Glue( 0 );

  /**
   * The constant {@code SS} contains the glue for 0pt plus 1fil minus 1fil.
   */
  FixedGlue S_S = new Glue( Dimen.ZERO, GlueComponent.ONE_FIL,
                            GlueComponent.ONE_FIL );

  /**
   * Make a copy of this object.
   *
   * @return a new instance with the same internal values
   */
  Glue copy();

  /**
   * Test that the given Glue is equal to a given one.
   *
   * @param glue the glue to compare with
   * @return {@code true} iff they are different
   */
  boolean eq( FixedGlue glue );

  /**
   * Getter for the length. Note that the value returned is independent from
   * the original object. Changing its value does not affect the length of the
   * glue.
   *
   * @return the natural length
   */
  FixedDimen getLength();

  /**
   * Getter for shrink. Note that the value returned is independent from the
   * original object. Changing its value does not affect the shrink of the
   * glue.
   *
   * @return the shrink.
   */
  FixedGlueComponent getShrink();

  /**
   * Getter for stretch. Note that the value returned is independent from the
   * original object. Changing its value does not affect the stretch of the
   * glue.
   *
   * @return the stretch.
   */
  FixedGlueComponent getStretch();

  /**
   * Test that the given Glue is different from a given one.
   *
   * @param glue the glue to compare with
   * @return {@code true} iff they are different
   */
  boolean ne( FixedGlue glue );

  /**
   * Provide a string representation of this instance.
   *
   * @return the string representation of this glue
   */
  String toString();

}
