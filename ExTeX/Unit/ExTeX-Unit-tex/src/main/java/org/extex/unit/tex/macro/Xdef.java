/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.unit.tex.macro;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \xdef}.
 *
 * <p>The Primitive {@code \xdef}</p>
 * <p>
 * This primitive is an abbreviation for {@code \global}{@code \edef}.
 * Thus the description of {@code \edef} can be consulted for details. TODO
 * missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;xdef&rang;
 *       &rarr; &lang;prefix&rang; {@code \xdef} {@linkplain
 *         org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *         &lang;control sequence&rang;} &lang;parameter text&rang; {@code {} &lang;replacement text&rang; {@code }}
 *
 *    &lang;prefix&rang;
 *      &rarr;
 *       | {@code \global} &lang;prefix&rang;
 *       | {@code \long} &lang;prefix&rang;
 *       | {@code \outer} &lang;prefix&rang;</pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \xdef#1{--#1--}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Xdef extends Edef {

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
  public Xdef( CodeToken token ) {

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

    prefix.setGlobal();
    super.assign( prefix, context, source, typesetter );
  }

}
