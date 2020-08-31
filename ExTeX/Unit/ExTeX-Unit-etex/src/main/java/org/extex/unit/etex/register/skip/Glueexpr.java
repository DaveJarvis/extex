/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.register.skip;

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.muskip.Muskip;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.MuskipConvertible;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \glueexpr}.
 *
 * <p>The Primitive {@code \glueexpr}</p>
 * <p>
 * The primitive {@code \glueexpr} ...
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
 *    &lang;glueexpr&rang;
 *      &rarr; {@code \glueexpr} </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \glueexpr\skip0\relax  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Glueexpr extends AbstractCode implements MuskipConvertible {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Glueexpr( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public Muskip convertMuskip( Context context, TokenSource source,
                               Typesetter typesetter )
      throws HelpingException, TypesetterException {

    // TODO gene: unimplemented
    throw new RuntimeException( "unimplemented" );
  }

}
