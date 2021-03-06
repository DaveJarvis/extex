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

package org.extex.unit.tex.typesetter.parameter;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.CantUseInException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
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
import org.extex.typesetter.exception.TypesetterUnsupportedException;

/**
 * This class provides an implementation for the primitive
 * {@code \prevdepth}.
 *
 * <p>The Primitive {@code \prevdepth}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;prevdepth&rang;
 *      &rarr; {@code \prevdepth} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} &lang;dimen value&rang;  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \prevdepth=123pt  </pre>
 * <pre class="TeXSample">
 *    \dimen0=\prevdepth  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Prevdepth extends AbstractAssignment
    implements
    CountConvertible,
    DimenConvertible,
    Theable {

  /**
   * The field {@code IGNORE} contains the numerical value which represents
   * the ignored value. This will be mapped to null.
   */
  private static final long IGNORE = -65536000;

  /**
   * The field {@code IGNORE_DIMEN} contains the value which represents the
   * ignored value. This will be mapped to null.
   */
  private static final Dimen IGNORE_DIMEN = new Dimen( IGNORE );

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
  public Prevdepth( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void assign( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    source.getOptionalEquals( context );
    Dimen pd = source.parseDimen( context, source, typesetter );
    if( pd.getValue() < IGNORE ) {
      pd = null;
    }
    try {
      typesetter.setPrevDepth( pd );
    } catch( TypesetterUnsupportedException e ) {
      throw new CantUseInException( toText(),
                                    typesetter.getMode().toString() );
    }
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    FixedDimen prevDepth;
    try {
      prevDepth = typesetter.getListMaker().getPrevDepth();
    } catch( TypesetterUnsupportedException e ) {
      throw new HelpingException( getLocalizer(), "TTP.ImproperSForPD",
                                  toText() );
    }

    return prevDepth != null ? prevDepth.getValue() : IGNORE;
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public long convertDimen( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    FixedDimen prevDepth;
    try {
      prevDepth = typesetter.getListMaker().getPrevDepth();
    } catch( TypesetterUnsupportedException e ) {
      throw new HelpingException( getLocalizer(), "TTP.ImproperSForPD",
                                  toText() );
    }

    return prevDepth != null ? prevDepth.getValue() : IGNORE;
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    FixedDimen prevDepth;
    try {
      prevDepth = typesetter.getListMaker().getPrevDepth();
    } catch( TypesetterUnsupportedException e ) {
      throw new HelpingException( getLocalizer(), "TTP.ImproperSForPD",
                                  toText( context ) );
    }

    if( prevDepth == null ) {
      prevDepth = IGNORE_DIMEN;
    }

    try {
      return context.getTokenFactory().toTokens( prevDepth.toString() );
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
  }

}
