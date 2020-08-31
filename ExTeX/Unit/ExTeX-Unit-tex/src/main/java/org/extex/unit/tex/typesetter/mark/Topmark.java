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
 * This class provides an implementation for the primitive {@code \topmark}.
 *
 * <p>The Primitive {@code \topmark}</p>
 * <p>
 * The primitive {@code \topmark} provides access to the topmost mark
 * encountered on the current page &ndash; when processing the page in the
 * output routine.
 * </p>
 * <p>
 * The primitive is a tokens register. It can be used wherever a tokens value is
 * expected. The value is empty when no top mark is available yet.
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
 *    &lang;topmark&rang;
 *      &rarr; {@code \topmark}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \topmark  </pre>
 * <pre class="TeXSample">
 *    \toks0=\topmark  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Topmark extends Topmarks {

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
  public Topmark( CodeToken token ) {

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
