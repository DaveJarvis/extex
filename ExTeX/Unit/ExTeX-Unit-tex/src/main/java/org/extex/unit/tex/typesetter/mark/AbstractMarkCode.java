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

package org.extex.unit.tex.typesetter.mark;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This abstract base class for mark primitives provides the common features.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractMarkCode extends AbstractCode {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public AbstractMarkCode( CodeToken token ) {

    super( token );
  }

  /**
   * Get the key for this mark.
   *
   * @param context    the interpreter context
   * @param source     the source for new tokens
   * @param typesetter the typesetter
   * @return the key for the mark primitive
   * @throws HelpingException    in case of an error
   * @throws TypesetterException in case of an error in the typesetter
   */
  protected String getKey( Context context, TokenSource source,
                           Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Token t = source.getNonSpace( context );
    source.push( t );
    if( t instanceof LeftBraceToken ) {
      Tokens tokens =
          source.scanTokens( context, false, false, getToken() );
      return (tokens == null ? "" : tokens.toText());
    }

    return Long.toString( source.parseInteger( context, source, typesetter ) );
  }

}
