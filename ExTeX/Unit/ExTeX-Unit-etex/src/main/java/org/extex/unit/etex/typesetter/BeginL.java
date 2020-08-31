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

package org.extex.unit.etex.typesetter;

import org.extex.core.count.Count;
import org.extex.core.exception.helping.ExtensionDisabledException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.Direction;

/**
 * This class provides an implementation for the primitive {@code \beginL}.
 *
 * <p>The Primitive {@code \beginL}</p>
 * <p>
 * The primitive {@code \beginL} indicates that the following text is typeset
 * left to right.
 * </p>
 * <p>
 * This primitive is deactivated unless the count register {@code \TeXXeTstate}
 * has a positive value.
 * </p>
 *
 * <p>Syntax</p>
 *
 *
 * <pre class="syntax">
 *    &lang;beginL&rang;
 *     &rarr; {@code \beginL} </pre>
 *
 * <p>Example:</p>
 *
 *
 * <pre class="TeXSample">
 *   \beginL  ... \endL</pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BeginL extends AbstractCode {

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
  public BeginL( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    if( context.getCount( "TeXXeTstate" ).le( Count.ZERO ) ) {
      throw new ExtensionDisabledException(
          toText( context ) );
    }
    context.pushDirection( context.getTypesettingContext().getDirection() );
    context.set( Direction.LR, false );
  }

}
