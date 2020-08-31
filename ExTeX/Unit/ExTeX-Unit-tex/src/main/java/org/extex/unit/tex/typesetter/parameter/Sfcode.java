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

package org.extex.unit.tex.typesetter.parameter;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.InvalidCodeException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \sfcode}.
 *
 * <p>The Primitive {@code \sfcode}</p>
 * <p>
 * The primitive {@code \sfcode} provides reading and writing access to the
 * space factor assigned to each character.
 * </p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;sfcode&rang;
 *      &rarr; {@code \sfcode} {@linkplain
 *        TokenSource#scanCharacterCode(Context, Typesetter, CodeToken)
 *        &lang;character code&rang;} {@linkplain
 *          org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *          &lang;equals&rang;} {@linkplain
 *          org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *        &lang;space factor&rang;}   </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \sfcode `a 1000  </pre>
 *
 * <pre class="TeXSample">
 *    \sfcode `a = 1000  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Sfcode extends AbstractAssignment implements
// ExpandableCode,
    CountConvertible,
    DimenConvertible,
    Theable {

  /**
   * The field {@code MAX_SF_CODE} contains the maximal value for a space
   * factor code.
   */
  private static final int MAX_SF_CODE = 32767;

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Sfcode( CodeToken token ) {

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

    UnicodeChar charCode =
        source.scanCharacterCode( context, typesetter, getToken() );
    source.getOptionalEquals( context );
    long sfCode = source.parseInteger( context, source, typesetter );

    if( sfCode < 0 || sfCode > MAX_SF_CODE ) {
      throw new InvalidCodeException( Long.toString( sfCode ),
                                      Integer.toString( MAX_SF_CODE ) );
    }

    context.setSfcode( charCode, new Count( sfCode ), prefix.clearGlobal() );
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
    return context.getSfcode( ucCode ).getValue();
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
   * This method takes the first token and expands it. The result is placed on
   * the stack. This means that expandable code does one step of expansion and
   * puts the result on the stack. To expand a token it might be necessary to
   * consume further tokens.
   *
   * @param prefix     the prefix flags controlling the expansion
   * @param context    the interpreter context
   * @param source     the token source
   * @param typesetter the typesetter
   * @throws ConfigurationException in case of an configuration error
   * @throws HelpingException       in case of an error
   * @throws TypesetterException    in case of an error in the typesetter
   * @see org.extex.interpreter.type.ExpandableCode#expand(org.extex.interpreter.Flags,
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
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
