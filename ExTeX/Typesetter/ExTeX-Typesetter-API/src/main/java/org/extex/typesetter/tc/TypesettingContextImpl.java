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

package org.extex.typesetter.tc;

import org.extex.color.Color;
import org.extex.color.model.ColorFactory;
import org.extex.language.Language;
import org.extex.typesetter.tc.font.Font;

/**
 * This implementation of a typesetting context provides the required
 * functionality for the container or attributes describing the
 * appearance of glyphs or other nodes.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TypesettingContextImpl implements ModifiableTypesettingContext {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2006L;

  /**
   * The field {@code color} contains the color to use.
   * The effect depends on the object to be colored.
   * For instance
   * <ul>
   * <li>in a CharNode it is the color of the text (background is always
   * transparent)</li>
   * <li>in a RuleNode it is the color of the rule</li>
   * <li>in a HListNode or VListNode it is the background color</li>
   * </ul>
   */
  private Color color = ColorFactory.BLACK;

  /**
   * The field {@code direction} contains the direction for advancing the
   * cursor. This is one of the constants in {@link Direction Direction}.
   */
  private Direction direction = Direction.LR;

  /**
   * The field {@code font} contains the font to use.
   */
  private Font font;

  /**
   * The field {@code language} contains the hyphenation table for the
   * current language.
   */
  private Language language;

  /**
   * Creates a new object filled with default values.
   */
  public TypesettingContextImpl() {

  }

  /**
   * Creates a new object with the given initial font, the color black and no
   * hyphenation.
   *
   * @param theFont the font to use
   */
  public TypesettingContextImpl( Font theFont ) {

    this.font = theFont;
  }

  /**
   * Creates a new object (copy constructor).
   *
   * @param tc the typesetting context to copy
   */
  public TypesettingContextImpl( TypesettingContext tc ) {

    if( tc != null ) {
      this.font = tc.getFont();
      this.color = tc.getColor();
      this.direction = tc.getDirection();
      this.language = tc.getLanguage();
    }
  }

  /**
   * Getter for the color.
   *
   * @return the current color
   * @see org.extex.typesetter.tc.TypesettingContext#getColor()
   */
  public Color getColor() {

    return this.color;
  }

  /**
   * Getter for the writing direction.
   *
   * @return the current direction
   * @see org.extex.typesetter.tc.TypesettingContext#getDirection()
   */
  public Direction getDirection() {

    return this.direction;
  }

  /**
   * Getter for the font component.
   *
   * @return the font
   * @see org.extex.typesetter.tc.TypesettingContext#getFont()
   */
  public Font getFont() {

    if( this.font == null ) {
      throw new IllegalStateException( "font undefined" );
    }
    return this.font;
  }

  /**
   * Getter for the hyphenation table.
   *
   * @return the hyphenation table
   * @see org.extex.typesetter.tc.TypesettingContext#getLanguage()
   */
  public Language getLanguage() {

    return this.language;
  }

  /**
   * Setter for all components. The components color, direction, font,
   * language, etc are copied from the instance given.
   *
   * @param context the context to clone
   * @see org.extex.typesetter.tc.ModifiableTypesettingContext#set(
   *org.extex.typesetter.tc.TypesettingContext)
   */
  public void set( TypesettingContext context ) {

    if( context != null ) {
      this.font = context.getFont();
      this.color = context.getColor();
      this.direction = context.getDirection();
      this.language = context.getLanguage();
    }
  }

  /**
   * Setter for the color.
   *
   * @param color the new color
   * @see org.extex.typesetter.tc.ModifiableTypesettingContext#setColor(
   *org.extex.color.Color)
   */
  public void setColor( Color color ) {

    this.color = color;
  }

  /**
   * Setter for the writing direction.
   *
   * @param direction the new direction
   * @see org.extex.typesetter.tc.ModifiableTypesettingContext#setDirection(
   *org.extex.typesetter.tc.Direction)
   */
  public void setDirection( Direction direction ) {

    this.direction = direction;
  }

  /**
   * Setter for the font component.
   *
   * @param font the font to store
   * @see org.extex.typesetter.tc.ModifiableTypesettingContext#setFont(
   *org.extex.typesetter.tc.font.Font)
   */
  public void setFont( Font font ) {

    this.font = font;
  }

  /**
   * Setter for the hyphenation table.
   *
   * @param language the hyphenation table
   * @see org.extex.typesetter.tc.ModifiableTypesettingContext#setLanguage(
   *org.extex.language.Language)
   */
  public void setLanguage( Language language ) {

    this.language = language;
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder( "(" );
    sb.append( font.getFontKey().toString() );
    sb.append( ' ' );
    sb.append( language.getName() );
    sb.append( ' ' );
    sb.append( color.toString() );
    sb.append( ' ' );
    sb.append( direction.toString() );
    sb.append( ')' );
    return sb.toString();
  }

}
