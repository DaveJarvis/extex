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

package org.extex.unit.tex.font;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;

/**
 * This class provides an implementation for the primitive
 * {@code \hyphenchar}.
 *
 * <p>The Primitive {@code \hyphenchar}</p>
 * <p>
 * The primitive {@code \hyphenchar} can be used to set the hyphenation
 * character for a given font. The undefined character &ndash; represented by a
 * negative value &ndash; indicates that no hyphenation should be applied.
 * Otherwise the given character will be used when hyphenating words.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *     &lang;hyphenchar&rang;
 *       &rarr; {@code \hyphenchar} &lang;font&rang; {@linkplain
 *         org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *         &lang;equals&rang;} {@linkplain
 *         org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *         &lang;8-bit number&rang;}
 * </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *     \hyphenchar\font=132
 * </pre>
 *
 * <p>Incompatibility</p>
 *
 * <p>
 * The TeXbook gives no indication how the primitive should react for
 * negative values &ndash; except -1. The implementation of TeX allows to
 * store and retrieve arbitrary negative values.
 * This behavior of TeX is not preserved in εχTeX.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Hyphenchar extends AbstractAssignment
    implements
    CountConvertible,
    ExpandableCode,
    Theable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2011L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Hyphenchar( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    try {
      Font font = source.getFont( context, getToken() );
      source.getOptionalEquals( context );
      long c = source.parseInteger( context, source, typesetter );
      font.setHyphenChar( UnicodeChar.get( (int) c ) );
    } catch( EofException e ) {
      throw new EofException( toText() );
    }
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    UnicodeChar uc;
    try {
      Font font = source.getFont( context, getToken() );
      uc = font.getHyphenChar();
    } catch( EofException e ) {
      throw new EofException( toText() );
    }
    return (uc == null ? -1 : uc.getCodePoint());
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    try {
      source.push( the( context, source, typesetter ) );
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

    Font font = source.getFont( context, getToken() );
    UnicodeChar uc = font.getHyphenChar();
    return context.getTokenFactory().toTokens(
        uc == null ? "-1" : Integer.toString( uc.getCodePoint() ) );
  }

}
