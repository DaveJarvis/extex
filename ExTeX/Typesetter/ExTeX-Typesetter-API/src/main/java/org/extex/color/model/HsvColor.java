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

package org.extex.color.model;

import org.extex.color.Color;
import org.extex.color.ColorUtil;
import org.extex.color.ColorVisitor;
import org.extex.core.exception.GeneralException;

/**
 * This class implements a color specification in HSV mode with an alpha
 * channel.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class HsvColor implements Color {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 1L;

  /**
   * The field {@code hue} contains the hue value of the color.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   */
  private final int hue;

  /**
   * The field {@code saturation} contains the saturation value of the color.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   */
  private final int saturation;

  /**
   * The field {@code value} contains the value value of the color.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   */
  private final int value;

  /**
   * The field {@code alpha} contains the alpha channel of the color.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   */
  private final int alpha;

  /**
   * Creates a new object.
   *
   * @param thehue        the hue channel
   * @param thesaturation the saturation channel
   * @param thevalue      the value channel
   * @param theAlpha      the alpha channel
   */
  protected HsvColor( int thehue, int thesaturation,
                      int thevalue, int theAlpha ) {

    this.hue = (thehue < 0 ? 0 : thehue < MAX_VALUE ? thehue : MAX_VALUE);
    this.saturation = (thesaturation < 0 ? 0 : thesaturation < MAX_VALUE
        ? thesaturation
        : MAX_VALUE);
    this.value = (thevalue < 0 ? 0 : thevalue < MAX_VALUE
        ? thevalue
        : MAX_VALUE);
    this.alpha = (theAlpha < 0 ? 0 : theAlpha < MAX_VALUE
        ? theAlpha
        : MAX_VALUE);
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param obj the reference object with which to compare.
   * @return {@code true} if this object is the same as the obj
   * argument; {@code false} otherwise.
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals( Object obj ) {

    if( !(obj instanceof HsvColor) ) {
      return false;
    }
    HsvColor other = (HsvColor) obj;
    return hue == other.getHue() && saturation == other.getSaturation()
        && value == other.getValue() && alpha == other.getAlpha();
  }

  /**
   * Getter for the hue value.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   *
   * @return the hue value.
   */
  public int getHue() {

    return hue;
  }

  /**
   * Getter for the saturation value.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   *
   * @return the saturation value.
   */
  public int getSaturation() {

    return saturation;
  }

  /**
   * Getter for the value.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   *
   * @return the value.
   */
  public int getValue() {

    return value;
  }

  /**
   * Getter for the alpha channel.
   * The range of the value is 0x00 to 0xffff.
   *
   * @return the alpha channel
   * @see org.extex.color.Color#getAlpha()
   */
  public int getAlpha() {

    return alpha;
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for this object
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    return (hue >> 1) | (saturation >> 2) | (value >> 3) | (alpha >> 4);
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    ColorUtil.formatAlpha( sb, alpha );
    sb.append( "hsv {" );
    ColorUtil.formatComponent( sb, hue );
    sb.append( " " );
    ColorUtil.formatComponent( sb, saturation );
    sb.append( " " );
    ColorUtil.formatComponent( sb, value );
    sb.append( "}" );
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.color.Color#visit(
   *org.extex.color.ColorVisitor,
   * java.lang.Object)
   */
  public Object visit( ColorVisitor visitor, Object argument )
      throws GeneralException {

    return visitor.visitHsv( this, argument );
  }

}
