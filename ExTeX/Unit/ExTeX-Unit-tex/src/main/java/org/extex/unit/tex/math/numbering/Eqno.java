/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.math.numbering;

import org.extex.core.exception.helping.CantUseInException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.ListMaker;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.listMaker.math.EqConsumer;
import org.extex.unit.tex.math.AbstractMathCode;

/**
 * This class provides an implementation for the primitive {@code \eqno}.
 *
 * <p>The Math Primitive {@code \eqno}</p>
 * <p>
 * The math primitive {@code \eqno} arranges that the following material is
 * typeset in math mode and placed on the right side of the preceding material.
 * </p>
 * <p>
 * The primitive can be used in display math mode only. If used in another mode
 * an error is raised. An error is also raised when more than one invocations
 * appear in one display math list or {@code \eqno} appears together with
 * {@code \leqno} in a display math list.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;eqno&rang;
 *       &rarr; {@code \eqno}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    $$ 12 \eqno 34 $$ </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Eqno extends AbstractMathCode {

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
  public Eqno( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter ) throws CantUseInException {

    ListMaker lm = typesetter.getListMaker();

    try {

      if( lm instanceof EqConsumer ) {
        ((EqConsumer) lm).switchToNumber( true );
        return;
      }

    } catch( CantUseInException e ) {
      throw new CantUseInException( toText( context ),
                                    "math mode" );
    }
    throw new CantUseInException( toText( context ),
                                  typesetter.getMode().toString() );
  }

}
