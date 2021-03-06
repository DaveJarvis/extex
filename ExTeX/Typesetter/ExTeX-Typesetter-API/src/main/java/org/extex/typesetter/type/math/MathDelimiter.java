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

package org.extex.typesetter.type.math;

import org.extex.core.dimen.FixedDimen;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.noad.MathGlyph;
import org.extex.typesetter.type.noad.Noad;
import org.extex.typesetter.type.noad.NoadList;
import org.extex.typesetter.type.noad.util.MathContext;
import org.extex.typesetter.type.noad.util.MathSpacing;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * This class provides a container for a delimiter consisting of a class, a
 * large, and a small math glyph.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MathDelimiter implements Noad, Serializable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code largeChar} contains the code of the large character.
   */
  private final MathGlyph largeChar;

  /**
   * The field {@code mathClass} contains the class of this delimiter.
   */
  private final MathClass mathClass;

  /**
   * The field {@code smallChar} contains the code of the small character.
   */
  private final MathGlyph smallChar;

  /**
   * The field {@code spaceingClass} contains the spacing class.
   */
  private MathSpacing spacingClass = MathSpacing.UNDEF; // gene: correct?

  /**
   * Creates a new object.
   *
   * @param mathClass the class
   * @param smallChar the small character
   * @param largeChar the large character
   */
  public MathDelimiter( MathClass mathClass, MathGlyph smallChar,
                        MathGlyph largeChar ) {

    this.mathClass = mathClass;
    this.smallChar = smallChar;
    this.largeChar = largeChar;
  }

  /**
   * Getter for largeChar.
   *
   * @return the largeChar.
   */
  public MathGlyph getLargeChar() {

    return this.largeChar;
  }

  /**
   * Getter for mathClass.
   *
   * @return the mathClass.
   */
  public MathClass getMathClass() {

    return this.mathClass;
  }

  /**
   * Getter for smallChar.
   *
   * @return the smallChar.
   */
  public MathGlyph getSmallChar() {

    return this.smallChar;
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

    throw new UnsupportedOperationException( "setSubscript" );
  }

  /**
   * Setter for the superscript.
   *
   * @param superscript the superscript to set.
   * @see org.extex.typesetter.type.noad.Noad#setSuperscript(org.extex.typesetter.type.noad.Noad)
   */
  @Override
  public void setSuperscript( Noad superscript ) {

    throw new UnsupportedOperationException( "setSuperscript" );
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
    toString( sb );
    return sb.toString();
  }

  /**
   * Append the printable representation of the current instance to the string
   * buffer.
   *
   * @param sb the target string buffer
   * @see "TTP [691]"
   */
  @Override
  public void toString( StringBuilder sb ) {

    // sb.append('\"');
    mathClass.toString( sb );
    smallChar.toString( sb );
    largeChar.toString( sb );
  }

  /**
   * Produce a printable representation to a certain depth of the noad.
   *
   * @param sb    the string buffer
   * @param depth the depth to which the full information should be given
   * @see org.extex.typesetter.type.noad.Noad#toString(StringBuilder, int)
   */
  @Override
  public void toString( StringBuilder sb, int depth ) {

    toString( sb );
  }

  /**
   * org.extex.typesetter.type.noad.NoadList, int,
   * org.extex.typesetter.type.NodeList,
   * org.extex.typesetter.type.noad.util.MathContext,
   * java.util.logging.Logger)
   */
  @Override
  public void typeset( Noad previousNoad, NoadList noads, int index,
                       NodeList list, MathContext mathContext, Logger logger )
      throws TypesetterException {

    typeset( list, mathContext, null, null );
  }

  /**
   * Translate a MathDelimter into a NodeList.
   *
   * @param list        the list to add the nodes to. This list contains 
   *                    the Nodes
   *                    previously typeset. Thus it can be used to look back
   * @param mathContext the context to consider
   * @param height      the target height. If {@code null} then the natural
   *                    height is used
   * @param depth       the target depth. If {@code null} then the natural
   *                    depth is used
   * @throws TypesetterException    in case of a problem
   * @throws ConfigurationException in case of a configuration problem
   */
  public void typeset( NodeList list, MathContext mathContext,
                       FixedDimen height, FixedDimen depth )
      throws TypesetterException,
      ConfigurationException {

    if( mathClass == null && smallChar == null && largeChar == null ) {
      return;
    }

    // TODO gene: typeset() unimplemented
    throw new RuntimeException( "unimplemented MathDelimiter" );
  }

}
