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

import org.extex.interpreter.context.Context;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This class provides an implementation for the primitive
 * {@code \splitfirstmarks}.
 *
 * <p>The Primitive {@code \splitfirstmarks}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;splitfirstmarks&rang;
 *      &rarr; {@code \splitfirstmarks} {@linkplain
 *        org.extex.unit.tex.typesetter.mark.AbstractMarksCode#getKey(
 *org.extex.interpreter.context.Context,
 *        org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
 *        &lang;mark name&rang;}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \splitfirstmarks42  </pre>
 * <pre class="TeXSample">
 *    \splitfirstmarks\count0  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Splitfirstmarks extends AbstractMarksCode {

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
  public Splitfirstmarks( CodeToken token ) {

    super( token );
  }

  /**
   * Get the value for this mark.
   *
   * @param context the interpreter context
   * @param key     the key
   * @return the value
   * @see org.extex.unit.tex.typesetter.mark.AbstractMarksCode#getValue(
   *org.extex.interpreter.context.Context, java.lang.String)
   */
  @Override
  protected Tokens getValue( Context context, String key ) {

    return context.getSplitBottomMark( key );
  }

}
