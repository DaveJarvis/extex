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

package org.extex.unit.tex.register.box;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \dp}.
 *
 * <p>The Primitive {@code \dp}</p>
 * <p>
 * The primitive {@code \dp} refers to the depth of a box register. It can be
 * used in various contexts.
 * </p>
 *
 * <p>Execution of the Primitive</p>
 *
 * <p>
 * If the primitive is used in a context it initiated an assignment to the
 * actual depth of the box register. This has an effect only in the case that
 * the box register is not void.
 * </p>
 * <p>
 * The formal description of this primitive is the following:
 * </p>
 *
 * <pre class="syntax">
 *    &lang;dp&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \dp}
 *        &lang;box register name&rang; &lang;equals&rang; &lang;dimen&rang;
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \global} &lang;optional prefix&rang;  </pre>
 *
 * <p>Examples</p>
 *
 * <pre class="TeXSample">
 *    \dp42 = 12mm  </pre>
 *
 * <pre class="TeXSample">
 *    \dp42 = \dimen3  </pre>
 *
 * <p>Expansion of the Primitive</p>
 *
 * <p>
 * In an expansion context the primitive results in the the current depth of the
 * given box register. In case that the box register is empty the result is
 * 0&nbsp;pt.
 * </p>
 *
 * <p>Syntax</p>
 *
 * <p>
 * The formal description of this primitive is the following:
 * </p>
 *
 * <pre class="syntax">
 *    {@code \dp} {@linkplain
 *      org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *      &lang;8-bit&nbsp;number&rang;} </pre>
 *
 * <p>Examples</p>
 *
 * <pre class="TeXSample">
 *    \dimen0 = \dp42  </pre>
 *
 * <pre class="TeXSample">
 *    \the\dp42  </pre>
 *
 * <p>Conversion to a Count</p>
 *
 * <p>
 * The primitive is convertible into a count. It can be used wherever an integer
 * quantity is expected.
 * </p>
 *
 * <p>Interaction with {@code \the}</p>
 *
 * <p>
 * The primitive {@code \the} can be applied to this primitive. In this case it
 * results in a token list representing the value in points.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Dp extends AbstractAssignment
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
  public Dp( CodeToken token ) {

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

    String key = Setbox.getKey( context, source, typesetter, getToken() );
    source.getOptionalEquals( context );
    Dimen d = source.parseDimen( context, source, typesetter );

    Box box = context.getBox( key );
    if( box != null ) {
      box.setDepth( d );
    }
    // TODO gene: treatment of \global correct?
    prefix.clearGlobal();
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

    Box box = context.getBox(
        Setbox.getKey( context, source, typesetter, getToken() ) );
    return (box == null ? 0 : box.getDepth().getValue());
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    source.push( the( context, source, typesetter ) );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    Box box = context.getBox(
        Setbox.getKey( context, source, typesetter, getToken() ) );
    FixedDimen d = (box == null ? Dimen.ZERO_PT : box.getDepth());
    try {
      return context.getTokenFactory().toTokens( d.toString() );
    } catch( GeneralException e ) {
      throw new NoHelpException( e );
    }
  }

}
