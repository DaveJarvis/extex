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

package org.extex.unit.tex.math.util;

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingMathException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.Showable;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.listMaker.math.NoadConsumer;
import org.extex.typesetter.type.math.MathCode;
import org.extex.unit.tex.math.AbstractTeXMathCode;

/**
 * This class is used to dynamically define mathematical characters having the
 * TeX encoding into a count value.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TeXMathcharCode extends AbstractTeXMathCode
    implements
    MathCodeConvertible,
    CountConvertible,
    Showable,
    Theable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code mathchar} contains the actual character in the form of
   * a MathCode which can immediately be passed to the typesetter.
   */
  private final MathCode mathchar;

  /**
   * Creates a new object.
   *
   * @param token    the initial token for the primitive
   * @param charCode the code of the math char
   */
  public TeXMathcharCode( CodeToken token, MathCode charCode ) {

    super( token );
    mathchar = charCode;
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    return mathCodeToLong( mathchar );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public MathCode convertMathCode( Context context, TokenSource source,
                                   Typesetter typesetter )
      throws TypesetterException {

    return mathchar;
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws TypesetterException,
      MissingMathException {

    NoadConsumer nc = getListMaker( context, typesetter );
    nc.add( mathchar, context.getTypesettingContext() );
  }

  @Override
  public Tokens show( Context context ) throws HelpingException {

    try {
      return context.getTokenFactory().toTokens(
          context.esc( "mathchar" )
              + "\""
              + Long.toHexString( mathCodeToLong( mathchar ) )
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

    return context.getTokenFactory().toTokens( mathCodeToLong( mathchar ) );
  }

}
