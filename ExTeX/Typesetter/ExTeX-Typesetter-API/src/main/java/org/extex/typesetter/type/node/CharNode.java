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

package org.extex.typesetter.type.node;

import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.NodeVisitor;

/**
 * This is the Node which carries a single character.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 */
public class CharNode extends AbstractNode {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code character} contains the single character represented by
   * this node.
   */
  private final UnicodeChar character;

  /**
   * The field {@code typesettingContext} contains the typesetting context
   */
  private final TypesettingContext typesettingContext;

  /**
   * Creates a new object.
   *
   * @param context the typesetting context
   * @param uc      the Unicode character
   */
  public CharNode( TypesettingContext context, UnicodeChar uc ) {

    typesettingContext = context;
    character = uc;
    Font font = context.getFont();

    FixedGlue x = font.getWidth( uc );
    setWidth( x != null ? x.getLength() : Dimen.ZERO_PT );
    x = font.getHeight( uc );
    setHeight( x != null ? x.getLength() : Dimen.ZERO_PT );
    x = font.getDepth( uc );
    setDepth( x != null ? x.getLength() : Dimen.ZERO_PT );
  }

  /**
   * This method determines the number of characters contained in a node.
   *
   * @return the number of characters contained
   * @see org.extex.typesetter.type.Node#countChars()
   */
  @Override
  public int countChars() {

    return 1;
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
   * Getter for the array of characters enclosed in this node.
   *
   * @return the array of characters
   * @see org.extex.typesetter.type.Node#getChars()
   */
  @Override
  public CharNode[] getChars() {

    return new CharNode[]{this};
  }

  /**
   * Getter for the space factor.
   *
   * @return the space factor
   */
  public int getSpaceFactor() {

    return 1000; // TODO gene: getSpaceFactor() incomplete
  }

  /**
   * Getter for typesetting context.
   *
   * @return the typesetting context.
   */
  public TypesettingContext getTypesettingContext() {

    return this.typesettingContext;
  }

  /**
   * This method puts the printable representation into the string buffer.
   * This is meant to produce a exhaustive form as it is used in tracing
   * output to the log file.
   *
   * @param sb      the output string buffer
   * @param prefix  the prefix string inserted at the beginning of each line
   * @param breadth the breadth of the nodes to display
   * @param depth   the depth of the nodes to display
   * @see org.extex.typesetter.type.Node#toString(java.lang.StringBuilder,
   * java.lang.String, int, int)
   */
  @Override
  public void toString( StringBuilder sb, String prefix, int breadth,
                        int depth ) {

    Font font = typesettingContext.getFont();
    sb.append( getLocalizer().format( "String.Format",
                                      (font == null ? "*" : font.getFontName()),
                                      character.toString() ) );
    /*
     * if (false) { sb.append(" ("); sb.append(getHeight().toString());
     * sb.append("+"); sb.append(getDepth().toString()); sb.append(")x");
     * sb.append(getWidth().toString()); }
     */
  }

  /**
   * This method puts the printable representation into the string buffer.
   * This is meant to produce a short form only as it is used in error
   * messages to the user.
   *
   * @param sb     the output string buffer
   * @param prefix the prefix string inserted at the beginning of each line
   * @see org.extex.typesetter.type.Node#toText(StringBuilder,
   * java.lang.String)
   */
  @Override
  public void toText( StringBuilder sb, String prefix ) {

    Font font = typesettingContext.getFont();
    sb.append( getLocalizer().format( "Text.Format",
                                      (font == null ? "*" : font.getFontName()),
                                      character.toString() ) );
  }

  /**
   * This method provides an entry point for the visitor pattern.
   *
   * @param visitor the visitor to apply
   * @param value   the argument for the visitor
   * @return the result of the method invocation of the visitor
   * @throws GeneralException in case of an error
   * @see org.extex.typesetter.type.Node#visit(org.extex.typesetter.type.NodeVisitor,
   * java.lang.Object)
   */
  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Object visit( NodeVisitor visitor, Object value )
      throws GeneralException {

    return visitor.visitChar( this, value );
  }

}
