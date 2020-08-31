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

package org.extex.unit.tex.info;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.tex.info.util.FixedHelpingException;

/**
 * This class provides an implementation for the primitive
 * {@code \errmessage}.
 *
 * <p>The Primitive {@code \errmessage}</p>
 * <p>
 * The primitive {@code \errmessage} takes one argument. This argument is an
 * expanded list of tokens. Those tokens are presented as error message. The
 * help text is taken from the token register {@code \errhelp}
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;errmessage&rang;
 *       &rarr; {@code \errmessage} {@linkplain
 *        org.extex.interpreter.TokenSource#scanTokens(Context, boolean, boolean, CodeToken)
 *        &lang;tokens&rang;}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \errhelp={Too bad}
 *    \errmessage{I found an error}  </pre>
 *
 *
 *
 * <p>The Token Register {@code \errhelp}</p>
 * <p>
 * The token register {@code \errhelp} contains the help text for the
 * primitive {@link org.extex.unit.tex.info.Errmessage {@code \errmessage}}.
 * Nevertheless this register can be used in any place where a token register is
 * needed.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;errhelp&rang;
 *      &rarr; {@code \errhelp} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context, TokenSource, Typesetter)
 *        &lang;tokens&rang;}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \errhelp={Too bad}
 *    \errmessage{I found an error}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class Errmessage extends AbstractCode {

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
  public Errmessage( CodeToken token ) {

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

    String message =
        source.scanUnprotectedTokens( context, false, false, getToken() )
              .toText();
    String help = context.getToks( "errhelp" ).toText();
    throw new FixedHelpingException( message, help );
  }

}
