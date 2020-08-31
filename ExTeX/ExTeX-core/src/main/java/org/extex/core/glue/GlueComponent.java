/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.dimen.FixedDimen;

/**
 * This class provides a means to store floating numbers with an order. If the
 * order is 0 then the length denotes a length in multiples of scaled points. If
 * the order is larger then it denotes some order of infinity.
 *
 * <p>
 * Examples
 * </p>
 *
 * <pre>
 * 123 pt
 * -123 pt
 * 123.456 pt
 * 123.pt
 * .465 pt
 * -.456pt
 * +456pt
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class GlueComponent extends GlueComponentConstant {

  /**
   * The constant {@code ONE} contains the internal representation for 1pt.
   */
  public static final long ONE = 1 << 16;

  /**
   * The constant {@code MINUS_ONE_FIL} contains the value of -1 fil.
   */
  public static final FixedGlueComponent MINUS_ONE_FIL =
      new GlueComponentConstant( -ONE, (byte) 2 );

  /**
   * The constant {@code ONE_FI} contains the value of 1 fi.
   */
  public static final FixedGlueComponent ONE_FI = new GlueComponentConstant(
      ONE, (byte) 1 );

  /**
   * The constant {@code ONE_FIL} contains the value of 1 fil.
   */
  public static final FixedGlueComponent ONE_FIL = new GlueComponentConstant(
      ONE, (byte) 2 );

  /**
   * The constant {@code ONE_FIL} contains the value of 1 fill.
   */
  public static final FixedGlueComponent ONE_FILL =
      new GlueComponentConstant( ONE, (byte) 3 );

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2006L;

  /**
   * The constant {@code ZERO} contains the non-stretchable and
   * non-shrinkable value of 0&nbsp;pt.
   */
  public static final FixedGlueComponent ZERO = new GlueComponentConstant( 0 );


  public GlueComponent() {

  }

  /**
   * Creates a new object with a fixed width.
   *
   * @param component the fixed value
   */
  public GlueComponent( FixedGlueComponent component ) {

    super( component );
  }

  /**
   * Creates a new object with a fixed width.
   *
   * @param component the fixed value
   */
  public GlueComponent( FixedDimen component ) {

    super( component.getValue() );
  }

  /**
   * Creates a new object with a fixed width.
   *
   * @param value the fixed value
   */
  public GlueComponent( long value ) {

    super( value );
  }

  /**
   * Creates a new object with a width with a possibly higher order.
   *
   * @param value the fixed width or the factor
   * @param order the order
   */
  public GlueComponent( long value, byte order ) {

    super( value, order );
  }

  /**
   * Add another GlueCoponent g to this instance. If the order of g is greater
   * than the order of this instance then this operation does not change the
   * value or order at all. If the order of g is less than the order of this
   * instance then the value and order of g are stored in this instance. If
   * the orders agree then the sum of both values is stored in this instance.
   *
   * @param g the GlueCoponent to add
   */
  public void add( FixedGlueComponent g ) {

    byte o = g.getOrder();
    if( order == o ) {
      value += g.getValue();
    }
    else if( order < o ) {
      order = o;
      value = g.getValue();
    }
  }

  /**
   * Create a copy of this instance with the same order and value.
   *
   * @return a new copy of this instance
   */
  @Override
  public FixedGlueComponent copy() {

    return new GlueComponent( value, order );
  }

  /**
   * Multiply the value by an integer fraction.
   * <p>
   * <i>length</i> = <i>length</i> * <i>nom</i> / <i>denom</i>
   * </p>
   *
   * @param nom   nominator
   * @param denom denominator
   */
  public void multiply( long nom, long denom ) {

    this.value = this.value * nom / denom;
  }

  /**
   * Negate the value. This is the same as multiplying with -1.
   */
  public void negate() {

    this.value = -this.value;
  }

  /**
   * Setter for the value and order.
   *
   * @param d the new value
   */
  public void set( FixedDimen d ) {

    this.value = d.getValue();
    this.order = 0;
  }

  /**
   * Setter for the value and order.
   *
   * @param d the new value
   */
  public void set( FixedGlueComponent d ) {

    this.value = d.getValue();
    this.order = d.getOrder();
  }

  /**
   * Setter for the value in terms of the internal representation. The order
   * is reset to 0.
   *
   * @param theValue the new value in units of scaled points
   */
  public void set( long theValue ) {

    this.value = theValue;
    this.order = 0;
  }

  /**
   * Setter for the value.
   *
   * @param val the new value in units of scaled points
   */
  public void setValue( long val ) {

    this.value = val;
  }

  /**
   * Subtract a Glue component from this glue. If the order of the other glue
   * component is the same as the current one then the values are subtracted.
   * Otherwise the greater order determines which glue to use.
   *
   * @param g the GlueCoponent to subtract
   */
  public void subtract( FixedGlueComponent g ) {

    byte o = g.getOrder();
    if( order == o ) {
      value -= g.getValue();
    }
    else if( order < o ) {
      order = o;
      value = g.getValue();
    }
  }

}
