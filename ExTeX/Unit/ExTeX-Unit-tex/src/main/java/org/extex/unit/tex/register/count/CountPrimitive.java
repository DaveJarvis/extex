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

package org.extex.unit.tex.register.count;

import org.extex.core.count.Count;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.code.Advanceable;
import org.extex.interpreter.type.code.Divideable;
import org.extex.interpreter.type.code.Multiplyable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \count}.
 * It sets the named count register to the value given, and as a side effect all
 * prefixes are zeroed.
 *
 * <p>The Primitive {@code \count}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;count&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \count} {@linkplain
 *        org.extex.interpreter.TokenSource#scanRegisterName(Context, TokenSource, Typesetter, CodeToken)
 *        &lang;register name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *        &lang;number&rang;}
 *
 *   &lang;optional prefix&rang;
 *     &rarr;
 *      |  {@code \global} &lang;optional prefix&rang;  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \count23=-456  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @see org.extex.interpreter.type.code.Advanceable
 * @see org.extex.interpreter.type.code.Divideable
 * @see org.extex.interpreter.type.code.Multiplyable
 * @see org.extex.interpreter.type.Theable
 */
public class CountPrimitive extends AbstractCount
    implements
    ExpandableCode,
    Advanceable,
    Multiplyable,
    Divideable,
    Theable,
    CountConvertible {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public CountPrimitive( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void advance( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    source.getKeyword( context, "by" );

    long value = source.parseInteger( context, source, typesetter );
    value += context.getCount( key ).getValue();

    context.setCount( key, value, prefix.clearGlobal() );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    source.getOptionalEquals( context );

    long value = source.parseInteger( context, source, typesetter );
    context.setCount( key, value, prefix.clearGlobal() );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    Count c = context.getCount( key );
    return (c != null ? c.getValue() : 0);
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void divide( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    source.getKeyword( context, "by" );

    long value = source.parseInteger( context, source, typesetter );

    if( value == 0 ) {
      throw new ArithmeticOverflowException( toText( context ) );
    }

    value = context.getCount( key ).getValue() / value;
    context.setCount( key, value, prefix.clearGlobal() );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    try {
      source.push( context.getTokenFactory().toTokens(
          context.getCount( key ).getValue() ) );
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void multiply( Flags prefix, Context context, TokenSource source,
                        Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    source.getKeyword( context, "by" );

    long value = source.parseInteger( context, source, typesetter );
    value *= context.getCount( key ).getValue();
    context.setCount( key, value, prefix.clearGlobal() );
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

    String key = getKey( context, source, typesetter );
    return context.getTokenFactory().toTokens(
        context.getCount( key ).getValue() );
  }

}
