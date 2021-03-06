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

package org.extex.unit.tex.string;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.InvalidCharacterException;
import org.extex.core.exception.helping.InvalidCodeException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \lccode}.
 *
 * <p>The Primitive {@code \lccode}</p>
 * <p>
 * The primitive {@code \lccode} provides access to the mapping from characters
 * to their lower case counterpart. This information is used by the primitive
 * {@code \lowercase}
 * </p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;lccode&rang;
 *        &rarr; {@code \lccode} {@linkplain
 *          org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *          &lang;8-bit&nbsp;number&rang;} {@linkplain
 *          org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *          &lang;equals&rang;} {@linkplain
 *          org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *          &lang;8-bit&nbsp;number&rang;} </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \lccode 65=65  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Lccode extends AbstractAssignment
    implements
    ExpandableCode,
    Theable,
    CountConvertible,
    DimenConvertible {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Lccode( CodeToken token ) {

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

    UnicodeChar ucCode =
        source.scanCharacterCode( context, typesetter, getToken() );
    source.getOptionalEquals( context );
    try {
      UnicodeChar lcCode =
          source.scanCharacterCode( context, typesetter, getToken() );
      context.setLccode( ucCode, lcCode, prefix.clearGlobal() );
    } catch( InvalidCharacterException e ) {
      throw new InvalidCodeException( e.getChar(),
                                      Integer.toString( UnicodeChar.MAX_VALUE ) );
    }
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    UnicodeChar ucCode =
        source.scanCharacterCode( context, typesetter, getToken() );
    return context.getLccode( ucCode ).getCodePoint();
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertDimen( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    return convertCount( context, source, typesetter );
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

    return context.getTokenFactory().toTokens(
        convertCount( context, source, typesetter ) );
  }

}
