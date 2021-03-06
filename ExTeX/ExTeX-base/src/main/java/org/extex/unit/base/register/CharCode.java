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

package org.extex.unit.base.register;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Showable;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for a Code which represents a single
 * character. The code is executable, expandable, and convertible into a count
 * register. The token returned by expansion depends on the category code at the
 * time of expansion.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CharCode extends AbstractCode
    implements
    ExpandableCode,
    CountConvertible,
    Showable,
    Theable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code character} contains the encapsulated Unicode character.
   */
  private final UnicodeChar character;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   * @param uc    the Unicode character to encapsulate
   */
  public CharCode( CodeToken token, UnicodeChar uc ) {

    super( token );
    this.character = uc;
  }

  /**
   * org.extex.interpreter.TokenSource, Typesetter)
   */
  @Override
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    return character.getCodePoint();
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    expand( prefix, context, source, typesetter );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter ) throws HelpingException {

    try {
      Token t =
          context.getTokenFactory().createToken( Catcode.OTHER,
                                                 character,
                                                 context.getNamespace() );
      source.push( t );
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
  }

  /**
   * Getter for character.
   *
   * @return the character
   */
  public UnicodeChar getCharacter() {

    return this.character;
  }

  @Override
  public Tokens show( Context context ) throws HelpingException {

    try {
      return context.getTokenFactory().toTokens(
          context.esc( "char" )
              + "\""
              + Integer.toHexString( getCharacter().getCodePoint() )
                       .toUpperCase() );
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws CatcodeException,
      HelpingException,
      TypesetterException {

    return context.getTokenFactory().toTokens(
        Integer.toString( character.getCodePoint() ) );
  }

  @Override
  public String toString() {

    return character.toString();
  }

}
