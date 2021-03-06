/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register.dimen;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
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
 * This class provides an implementation for the primitive {@code \dimen}.
 * It sets the named dimen register to the value given, and as a side effect all
 * prefixes are zeroed.
 *
 * <p>The Primitive {@code \dimen}</p>
 * <p>
 * The primitive {@code \dimen} provides access to the dimen registers. Those
 * registers contain length values.
 * </p>
 * <p>
 * TODO gene documentation incomplete
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;dimen&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \dimen} {@linkplain
 *        org.extex.interpreter.TokenSource#scanRegisterName(Context, TokenSource, Typesetter, CodeToken)
 *        &lang;register name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantDimenParser#parse(Context, TokenSource, Typesetter)
 *        &lang;dimen value&rang;}
 *
 *   &lang;optional prefix&rang;
 *     &rarr;
 *      |  {@code \global} &lang;optional prefix&rang;  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *   \dimen1=12 pt </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class DimenPrimitive extends AbstractDimen
    implements
    Advanceable,
    ExpandableCode,
    CountConvertible,
    DimenConvertible,
    Multiplyable,
    Divideable,
    Theable {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public DimenPrimitive( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void advance( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    source.getKeyword( context, "by" );

    Dimen d = source.parseDimen( context, source, typesetter );
    d.add( context.getDimen( key ) );
    context.setDimen( key, d, prefix.clearGlobal() );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    source.getOptionalEquals( context );

    Dimen dimen = source.parseDimen( context, source, typesetter );
    context.setDimen( key, dimen, prefix.clearGlobal() );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    Dimen d = context.getDimen( key );
    return (d != null ? d.getValue() : 0);
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public long convertDimen( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    return convertCount( context, source, typesetter );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void divide( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    source.getKeyword( context, "by" );
    long value = source.parseInteger( context, source, null );

    if( value == 0 ) {
      throw new ArithmeticOverflowException(
          toText( context ) );
    }

    Dimen d = new Dimen( context.getDimen( key ).getValue() / value );
    context.setDimen( key, d, prefix.clearGlobal() );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    try {
      Tokens toks =
          context.getTokenFactory().toTokens(
              context.getDimen( key ).toString() );
      source.push( toks );
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void multiply( Flags prefix, Context context, TokenSource source,
                        Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    source.getKeyword( context, "by" );
    long value = source.parseInteger( context, source, null );
    Dimen d = new Dimen( context.getDimen( key ).getValue() * value );
    context.setDimen( key, d, prefix.clearGlobal() );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    String key = getKey( context, source, typesetter );
    try {
      return context.getTokenFactory().toTokens(
          context.getDimen( key ).toString() );
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
  }

}
