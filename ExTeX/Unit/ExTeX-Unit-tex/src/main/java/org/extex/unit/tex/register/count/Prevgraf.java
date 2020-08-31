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

package org.extex.unit.tex.register.count;

import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \prevgraf}.
 *
 * <p>The Primitive {@code \prevgraf}</p>
 * <p>
 * TODO missing documentation
 * </p>
 * <p>
 * The formal description of this primitive is the following:
 * </p>
 *
 * <pre class="syntax">
 *    &lang;prevgraf&rang;
 *       &rarr; {@code \prevgraf}  </pre>
 *
 * <p>
 * Examples:
 * </p>
 *
 * <pre class="TeXSample">
 *    \prevgraf  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Prevgraf extends CountPrimitive {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Prevgraf( CodeToken token ) {

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

    long value = source.parseInteger( context, source, null )
        + context.getCount( key ).getValue();

    if( value < 0 ) {
      throw new HelpingException( getLocalizer(), "TTP.BadPrevGraf",
                                  toText(), Long.toString( value ) );
    }
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
    if( value < 0 ) {
      throw new HelpingException( getLocalizer(), "TTP.BadPrevGraf",
                                  toText(), Long.toString( value ) );
    }
    context.setCount( key, value, prefix.clearGlobal() );
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

    long value = source.parseInteger( context, source, null );

    if( value == 0 ) {
      throw new ArithmeticOverflowException( toText( context ) );
    }

    value = context.getCount( key ).getValue() / value;
    if( value < 0 ) {
      throw new HelpingException( getLocalizer(), "TTP.BadPrevGraf",
                                  toText( context ), Long.toString( value ) );
    }
    context.setCount( key, value, prefix.clearGlobal() );
  }

  /**
   * Return the key (the name of the primitive) for the numbered count
   * register.
   *
   * @param context    the interpreter context to use
   * @param source     the source for new tokens
   * @param typesetter the typesetter
   * @return the key for the current register
   * @see org.extex.unit.tex.register.count.AbstractCount#getKey(org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  protected String getKey( Context context, TokenSource source,
                           Typesetter typesetter ) {

    return getName();
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

    long value = source.parseInteger( context, source, null );
    value *= context.getCount( key ).getValue();
    if( value < 0 ) {
      throw new HelpingException( getLocalizer(), "TTP.BadPrevGraf",
                                  toText( context ), Long.toString( value ) );
    }
    context.setCount( key, value, prefix.clearGlobal() );
  }

}
