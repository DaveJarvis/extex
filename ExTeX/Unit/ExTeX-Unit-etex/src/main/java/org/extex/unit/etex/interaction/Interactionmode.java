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

package org.extex.unit.etex.interaction;

import org.extex.core.exception.ImpossibleException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InteractionUnknownException;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \interactionmode}.
 *
 * <p>The Primitive {@code \interactionmode}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;interactionmode&rang;
 *      &rarr; {@code \interactionmode}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \interactionmode  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Interactionmode extends AbstractAssignment
    implements
    CountConvertible,
    Theable {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 2005L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Interactionmode( CodeToken token ) {

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
    long mode = source.parseInteger( context, source, typesetter );
    context.setInteraction( Interaction.get( (int) mode ) );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public long convertCount( Context context, TokenSource source,
                            Typesetter typesetter )
      throws HelpingException, TypesetterException {

    try {
      return Interaction.get( context.getInteraction() );
    } catch( InteractionUnknownException e ) {
      throw new ImpossibleException( "unknown interaction" );
    }
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    try {
      return context.getTokenFactory().toTokens(
          context.getInteraction().getIndex() );
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
  }

}
