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

package org.extex.unit.tex.conditional;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.ComparableCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.unit.base.conditional.AbstractIf;

/**
 * This class provides an implementation for the primitive {@code \ifx}.
 *
 * <p>The Primitive {@code \ifx}</p>
 * <p>
 * The primitive {@code \ifx} compares the following two tokens. If the
 * following tokens are no macros then the comparison succeeds if they agree in
 * category code and character.
 * </p>
 * <p>
 * If the argument tokens are control sequences or active characters then the
 * assigned values are compared. If the arguments are bound to macros then the
 * comparison succeeds if the status of <i>outer</i> and <i>long</i> are the
 * same, the patterns are the same and the body texts are equivalent
 * </p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;ifx&rang;
 *      &rarr; {@code \ifx} {@linkplain
 *        org.extex.interpreter.TokenSource#getToken(Context)
 *        &lang;token<sub>1</sub>&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getToken(Context)
 *        &lang;token<sub>2</sub>&rang;}; &lang;true text&rang; {@code \fi}
 *      | {@code \ifx} {@linkplain
 *        org.extex.interpreter.TokenSource#getToken(Context)
 *        &lang;token<sub>1</sub>&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getToken(Context)
 *        &lang;token<sub>2</sub>&rang;} &lang;true text&rang; {@code \else} &lang;false text&rang; {@code \fi} </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \ifx\a\x ok \fi  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Ifx extends AbstractIf {

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
  public Ifx( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public boolean conditional( Context context, TokenSource source,
                              Typesetter typesetter ) throws HelpingException {

    Token t1 = source.getToken( context );
    Token t2 = source.getToken( context );

    if( t1 == null || t2 == null ) {
      throw new EofException( toText() );
    }
    else if( t1 instanceof CodeToken ) {
      Code c1 = context.getCode( (CodeToken) t1 );

      if( c1 instanceof ComparableCode ) {
        return ((ComparableCode) c1).compare( t2, context );
      }
      if( t2 instanceof CodeToken ) {

        Code c2 = context.getCode( (CodeToken) t2 );

        if( c2 instanceof ComparableCode ) {
          return ((ComparableCode) c2).compare( t1, context );
        }
        else if( c1 == null && c2 == null ) {
          return true;
        }
        return c1.equals( c2 );
      }

    }
    else if( t2 instanceof CodeToken ) {

      Code c2 = context.getCode( (CodeToken) t2 );

      if( c2 instanceof ComparableCode ) {
        return ((ComparableCode) c2).compare( t1, context );
      }

    }

    return t1.equals( t2 );
  }

}
