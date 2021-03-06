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

package org.extex.typesetter.type.noad;

import org.extex.core.UnicodeChar;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathSpacing;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * This class provides a container for a mathematical glyph.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MathGlyph implements Noad, Serializable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2005L;

  /**
   * The field {@code character} contains the character of this glyph.
   */
  private final UnicodeChar character;

  /**
   * The field {@code family} contains the math family.
   */
  private final int family;

  /**
   * The field {@code spacingClass} contains the spacing class.
   */
  private MathSpacing spacingClass = MathSpacing.ORD; // gene: correct?

  /**
   * Creates a new object.
   *
   * @param family    the math family of the glyph
   * @param character the character in the font
   */
  public MathGlyph( int family, UnicodeChar character ) {

    this.family = family;
    this.character = character;
  }

  /**
   * Getter for character.
   *
   * @return the character.
   */
  public UnicodeChar getCharacter() {

    return this.character;
  }

  /**
   * Getter for family.
   *
   * @return the family.
   */
  public int getFamily() {

    return this.family;
  }

  /**
   * Getter for spacing class.
   *
   * @return the spacing class
   * @see org.extex.typesetter.type.noad.Noad#getSpacingClass()
   */
  @Override
  public MathSpacing getSpacingClass() {

    return spacingClass;
  }

  /**
   * Getter for the subscript.
   *
   * @return the subscript.
   * @see org.extex.typesetter.type.noad.Noad#getSubscript()
   */
  @Override
  public Noad getSubscript() {

    return null;
  }

  /**
   * Getter for the superscript.
   *
   * @return the superscript.
   * @see org.extex.typesetter.type.noad.Noad#getSuperscript()
   */
  @Override
  public Noad getSuperscript() {

    return null;
  }

  @Override
  public void setSpacingClass( MathSpacing spacingClass ) {

    this.spacingClass = spacingClass;
  }

  /**
   * Setter for the subscript.
   *
   * @param subscript the subscript to set.
   * @see org.extex.typesetter.type.noad.Noad#setSubscript(org.extex.typesetter.type.noad.Noad)
   */
  @Override
  public void setSubscript( Noad subscript ) {

    throw new UnsupportedOperationException( "subscript" );
  }

  /**
   * Setter for the superscript.
   *
   * @param superscript the superscript to set.
   * @see org.extex.typesetter.type.noad.Noad#setSuperscript(org.extex.typesetter.type.noad.Noad)
   */
  @Override
  public void setSuperscript( Noad superscript ) {

    throw new UnsupportedOperationException( "superscript" );
  }

  /**
   * Get the string representation of this object for debugging purposes.
   *
   * @return the string representation
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    toString( sb, 1 );
    return sb.toString();
  }

  /**
   * Produce a printable representation of the noad in a StringBuilder.
   *
   * @param sb the string buffer
   * @see org.extex.typesetter.type.noad.Noad#toString(StringBuilder)
   */
  @Override
  public void toString( StringBuilder sb ) {

    toString( sb, 1 );
  }

  /**
   * Produce a printable representation to a certain depth of the noad.
   *
   * @param sb    the string buffer
   * @param depth the depth to which the full information should be given
   * @see "TTP [691]"
   * @see org.extex.typesetter.type.noad.Noad#toString(StringBuilder, int)
   */
  @Override
  public void toString( StringBuilder sb, int depth ) {

    if( depth >= 0 ) {
      sb.append( "\\fam" );
      sb.append( family );
      sb.append( ' ' );
      if( character.isPrintable() ) {
        sb.append( character.toString() );
      }
      else {
        sb.append( '"' );
        sb.append( Integer.toHexString( character.getCodePoint() ) );
      }
    }
  }

  /**
   * Translate a Noad into a NodeList.
   *
   * @param previousNoad the previous noad
   * @param noads        the list of noads currently processed
   * @param index        the index of the current node in the list
   * @param list         the list to add the nodes to. This list contains
   *                     the Nodes
   *                     previously typeset. Thus it can be used to look back
   * @param mathContext  the context to consider
   * @param logger       the logger for debugging and tracing information
   * @throws TypesetterException    in case of a problem
   * @throws ConfigurationException in case of a configuration problem
   * @see org.extex.typesetter.type.noad.Noad#typeset(org.extex.typesetter.type.noad.Noad,
   * org.extex.typesetter.type.noad.NoadList, int,
   * org.extex.typesetter.type.NodeList,
   * org.extex.typesetter.type.noad.util.MathContext,
   * java.util.logging.Logger)
   */
  @Override
  public void typeset( Noad previousNoad, NoadList noads, int index,
                       NodeList list, MathContext mathContext, Logger logger )
      throws TypesetterException,
      ConfigurationException {

    // TODO gene: typeset() unimplemented
    throw new RuntimeException( "unimplemented" );
  }

}
