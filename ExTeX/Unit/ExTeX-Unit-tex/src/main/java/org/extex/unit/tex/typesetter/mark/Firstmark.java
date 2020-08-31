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

package org.extex.unit.tex.typesetter.mark;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * {@code \firstmark}.
 *
 * <p>The Primitive {@code \firstmark}</p>
 * <p>
 * The primitive {@code \firstmark} expands to the first mark on the current
 * page. If no mark has been encountered on the current page then it expands to
 * the last mark on the previous page. If no mark has been placed ever then the
 * primitive expands to the empty token list.
 * </p>
 * <p>
 * See the documentation of the primitive
 * {@link org.extex.unit.tex.typesetter.mark.Mark {@code \mark}} for further
 * explanation of marks.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;firstmark&rang;
 *      &rarr; {@code \firstmark}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \firstmark  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Firstmark extends Firstmarks {

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
  public Firstmark( CodeToken token ) {

    super( token );
  }

  /**
   * Get the key for this mark.
   *
   * @param context    the interpreter context
   * @param source     the source for new tokens
   * @param typesetter the typesetter
   * @return the key for the mark primitive
   * @see org.extex.unit.tex.typesetter.mark.AbstractMarkCode#getKey(
   *org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  protected String getKey( Context context, TokenSource source,
                           Typesetter typesetter ) {

    return "0";
  }

}
