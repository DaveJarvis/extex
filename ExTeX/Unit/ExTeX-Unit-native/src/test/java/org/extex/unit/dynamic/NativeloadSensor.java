/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.dynamic;

import org.extex.core.exception.GeneralException;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.unit.dynamic.java.Loadable;

/**
 * This class provides a sensor for the loading operation.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class NativeloadSensor implements Loadable {

  /**
   * The field {@code sensed} contains the indicator that somethng has been
   * sensed.
   */
  private static boolean sensed = false;

  /**
   * Getter for sensed.
   *
   * @return the sensed
   */
  public static boolean isSensed() {

    return sensed;
  }

  /**
   * Setter for sensed.
   *
   * @param sensed the sensed to set
   */
  public static void setSensed( boolean sensed ) {

    NativeloadSensor.sensed = sensed;
  }


  public NativeloadSensor() {

    sensed = false;
  }

  /**
   * Perform any initializations desirable after the component has been
   * loaded.
   *
   * @param context    the processor context
   * @param typesetter the current typesetter
   * @throws GeneralException in case of an error
   * @see org.extex.unit.dynamic.java.Loadable#init(
   *org.extex.interpreter.context.Context,
   * org.extex.typesetter.Typesetter)
   */
  public void init( Context context, Typesetter typesetter )
      throws GeneralException {

    sensed = true;
  }

}
