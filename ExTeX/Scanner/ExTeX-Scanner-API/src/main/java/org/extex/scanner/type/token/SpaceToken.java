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

package org.extex.scanner.type.token;

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;

/**
 * This class represents a space token.
 * <p>
 * This class has a protected constructor only. Use the factory
 * {@link org.extex.scanner.type.token.TokenFactory TokenFactory} to get an
 * instance of this class.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class SpaceToken extends AbstractToken implements Token {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2011L;

  /**
   * The Constant {@code SPACE} contains the space character.
   */
  private static final UnicodeChar SPACE = UnicodeChar.get( ' ' );

  /**
   * Creates a new object.
   *
   * @param value the actual value
   * @see "The TeXbook [Chapter 8; p.47]"
   */
  protected SpaceToken( String value ) {

    super( SPACE );
  }

  /**
   * Getter for the catcode.
   *
   * @return the catcode
   * @see org.extex.scanner.type.token.Token#getCatcode()
   */
  @Override
  public Catcode getCatcode() {

    return Catcode.SPACE;
  }

  /**
   * Get the string representation of this object for debugging purposes.
   *
   * @return the string representation
   */
  @Override
  public String toString() {

    return getLocalizer().format( "SpaceToken.Text", super.toString() );
  }

  /**
   * Print the token into a StringBuilder.
   *
   * @param sb the target string builder
   * @see org.extex.scanner.type.token.Token#toString(java.lang.StringBuilder)
   */
  @Override
  public void toString( StringBuilder sb ) {

    sb.append( getLocalizer().format( "SpaceToken.Text", super.toString() ) );
  }

  /**
   * Invoke the appropriate visit method for the current class.
   *
   * @param visitor the calling visitor
   * @param arg1    the first argument to pass
   * @return the result object
   * @throws Exception in case of an error
   * @see org.extex.scanner.type.token.Token#visit(org.extex.scanner.type.token.TokenVisitor,
   * java.lang.Object)
   */
  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Object visit( TokenVisitor visitor, Object arg1 ) throws Exception {

    return visitor.visitSpace( this, arg1 );
  }

}
