/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.tex.paragraph;

/**
 * The passive nodes constitute linked lists with break points.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PassiveNode {

  /**
   * The field {@code curBreak} contains the index of this break point.
   */
  private final int curBreak;

  /**
   * The field {@code nextBreak} contains the next passive node.
   */
  private PassiveNode nextBreak = null;

  /**
   * The field {@code prevBreak} contains the previous passive node.
   */
  private final PassiveNode prevBreak;

  /**
   * The field {@code serial} contains the serial number for printing.
   */
  private final int serial;

  /**
   * Creates a new object.
   *
   * @param curBreak  the index of this break point
   * @param serial    the serial number for printing
   * @param prevBreak the previous passive node
   */
  public PassiveNode( int curBreak, int serial, PassiveNode prevBreak ) {

    this.curBreak = curBreak;
    this.serial = serial;
    this.prevBreak = prevBreak;
  }

  /**
   * Getter for curBreak.
   *
   * @return the curBreak
   */
  public int getCurBreak() {

    return this.curBreak;
  }

  /**
   * Getter for nextBreak.
   *
   * @return the nextBreak
   */
  public PassiveNode getNextBreak() {

    return this.nextBreak;
  }

  /**
   * Getter for prevBreak.
   *
   * @return the prevBreak
   */
  public PassiveNode getPrevBreak() {

    return this.prevBreak;
  }

  /**
   * Getter for serial.
   *
   * @return the serial
   */
  public int getSerial() {

    return this.serial;
  }

  /**
   * Setter for the next break node.
   *
   * @param pn the next passive node
   */
  public void setNextBreak( PassiveNode pn ) {

    nextBreak = pn;
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    toString( sb );
    return sb.toString();
  }

  /**
   * Print the current object to a target string buffer.
   *
   * @param sb the target string buffer
   */
  public void toString( StringBuilder sb ) {

    sb.append( '<' ).append( curBreak ).append( '>' );
    if( nextBreak != null ) {
      nextBreak.toString( sb );
    }
  }

}
