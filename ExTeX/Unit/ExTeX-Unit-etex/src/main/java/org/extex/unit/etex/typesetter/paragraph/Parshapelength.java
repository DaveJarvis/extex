/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.typesetter.paragraph;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;

/**
 * This class provides an implementation for the primitive {@code \relax}.
 *
 * <p>The Primitive {@code \parshapelength}</p>
 * <p>
 * The primitive {@code \parshapelength} gives access to the settings for the
 * current paragraph shape. The primitive takes a number as parameter. If this
 * number is positive then the length of the line denoted by the parameter is
 * returned. The line numbering starts with 1. If the argument is less than 1
 * then 0 is returned.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;parshapelength&rang;
 *        &rarr; {@code \parshapelength} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *        &lang;8-bit&nbsp;number&rang;} </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \dimen2=\parshapelength 3  </pre>
 *
 * <pre class="TeXSample">
 *    \dimen2=\parshapelength -3  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Parshapelength extends AbstractCode
    implements
    CountConvertible,
    DimenConvertible,
    Theable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Parshapelength( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    return convertDimen( context, source, typesetter );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public long convertDimen( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    long l = source.parseInteger( context, source, typesetter );
    int n = (l < Integer.MAX_VALUE ? (int) l : Integer.MAX_VALUE);
    ParagraphShape parshape = context.getParshape();
    return (parshape == null || n < 0 ? 0 : parshape.getLength( n )
                                                    .getValue());
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    long l = source.parseInteger( context, source, typesetter );
    int n = (l < Integer.MAX_VALUE ? (int) l : Integer.MAX_VALUE);
    ParagraphShape parshape = context.getParshape();
    FixedDimen d = (parshape == null || n < 0)
        ? Dimen.ZERO_PT
        : parshape.getLength( n );

    try {
      return context.getTokenFactory().toTokens( d.toString() );
    } catch( GeneralException e ) {
      throw new NoHelpException( e );
    }
  }

}
