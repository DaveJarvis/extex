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
 * This class implements a color specification in CMYK mode with an alpha
 * channel.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CmykColor implements Color {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 1L;

  /**
   * The field {@code alpha} contains the alpha channel of the color.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   */
  private final int alpha;

  /**
   * The field {@code black} contains the black value of the color.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   */
  private final int black;

  /**
   * The field {@code cyan} contains the cyan value of the color.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   */
  private final int cyan;

  /**
   * The field {@code magenta} contains the magenta value of the color.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   */
  private final int magenta;

  /**
   * The field {@code yellow} contains the value yellow of the color.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   */
  private final int yellow;

  /**
   * Creates a new object.
   *
   * @param cyan    the cyan channel
   * @param magenta the magenta channel
   * @param yellow  the yellow channel
   * @param black   the black channel
   * @param alpha   the alpha channel
   */
  protected CmykColor( int cyan, int magenta, int yellow,
                       int black, int alpha ) {

    this.cyan = (cyan < 0 ? 0 : cyan < MAX_VALUE ? cyan : MAX_VALUE);
    this.magenta = (magenta < 0 ? 0 : magenta < MAX_VALUE
        ? magenta
        : MAX_VALUE);
    this.yellow = (yellow < 0 ? 0 : yellow < MAX_VALUE ? yellow : MAX_VALUE);
    this.black = (black < 0 ? 0 : black < MAX_VALUE ? black : MAX_VALUE);
    this.alpha = (alpha < 0 ? 0 : alpha < MAX_VALUE ? alpha : MAX_VALUE);
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

    if( !(obj instanceof CmykColor) ) {
      return false;
    }
    CmykColor other = (CmykColor) obj;
    return cyan == other.getCyan() && magenta == other.getMagenta()
        && yellow == other.getYellow() && black == other.getBlack()
        && alpha == other.getAlpha();
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
   * Getter for the black component.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   *
   * @return the black component
   */
  public int getBlack() {

    return black;
  }

  /**
   * Getter for the cyan component.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   *
   * @return the cyan component
   */
  public int getCyan() {

    return cyan;
  }

  /**
   * Getter for the magenta component.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   *
   * @return the magenta component
   */
  public int getMagenta() {

    return magenta;
  }

  /**
   * Getter for the yellow component.
   * It has a value in the range from 0 to {@link #MAX_VALUE MAX_VALUE}.
   *
   * @return the yellow component
   */
  public int getYellow() {

    return yellow;
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for this object
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    return (cyan >> 1) | (magenta >> 2) | (yellow >> 3) | (black >> 4)
        | (alpha >> 5);
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
    sb.append( "cmyk {" );
    ColorUtil.formatComponent( sb, cyan );
    sb.append( " " );
    ColorUtil.formatComponent( sb, magenta );
    sb.append( " " );
    ColorUtil.formatComponent( sb, yellow );
    sb.append( " " );
    ColorUtil.formatComponent( sb, black );
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
  public Object visit( ColorVisitor visitor, Object value )
      throws GeneralException {

    return visitor.visitCmyk( this, value );
  }

}
