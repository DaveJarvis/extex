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

package org.extex.typesetter.type;

/**
 * This class carries a two-dimensional transformation.
 *
 * <table> <caption>TBD</caption>
 *  <tr><td>a</td>  <td>b</td>  <td>0</td> </tr>
 *  <tr><td>c</td>  <td>d</td>  <td>0</td> </tr>
 *  <tr><td>tx</td> <td>ty</td> <td>1</td> </tr>
 * </table>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Transformation {

  /**
   * The field {@code a} contains the a component.
   */
  private float a;

  /**
   * The field {@code b} contains the b component.
   */
  private float b;

  /**
   * The field {@code c} contains the c component.
   */
  private float c;

  /**
   * The field {@code d} contains the d component.
   */
  private float d;

  /**
   * The field {@code tx} contains the tx component.
   */
  private float tx;

  /**
   * The field {@code ty} contains the ty component.
   */
  private float ty;


  public Transformation() {

  }

  /**
   * Getter for a.
   *
   * @return the a
   */
  public String getA() {

    return Float.toString( this.a );
  }

  /**
   * Getter for b.
   *
   * @return the b
   */
  public String getB() {

    return Float.toString( this.b );
  }

  /**
   * Getter for c.
   *
   * @return the c
   */
  public String getC() {

    return Float.toString( this.c );
  }

  /**
   * Getter for d.
   *
   * @return the d
   */
  public String getD() {

    return Float.toString( this.d );
  }

  /**
   * Getter for tx component.
   *
   * @return the tx
   */
  public String getTx() {

    return Float.toString( this.tx );
  }

  /**
   * Getter for ty component.
   *
   * @return the ty
   */
  public String getTy() {

    return Float.toString( this.ty );
  }
}
