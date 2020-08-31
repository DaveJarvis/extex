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

package org.extex.unit.tex.macro;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.PrefixCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \expandafter}.
 *
 * <p>The Primitive {@code \expandafter}</p>
 * <p>
 * The primitive {@code \expandafter} reverses the expansion of the following
 * two tokens. For this purpose the next token following the invocation is read
 * unexpanded and put aside. Then the next token is expanded in whatever way the
 * token requires expansion. This may involve the reading and expansion of
 * further tokens. When this expansion is finished then the saved token is put
 * back into the input stream.
 * </p>
 * <p>
 * Any prefix argument is saved for the expansion of the following token.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;expandafter&rang;
 *     &rarr; {@code \expandafter} {@linkplain
 *         org.extex.interpreter.TokenSource#getToken(Context)
 *         &lang;token&rang;} {@linkplain
 *         org.extex.interpreter.TokenSource#getToken(Context)
 *         &lang;token&rang;}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \expandafter ab  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Expandafter extends AbstractCode
    implements
    ExpandableCode,
    PrefixCode {

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
  public Expandafter( CodeToken token ) {

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

    Token t = source.getToken( context );
    Token token = source.getToken( context );
    if( t == null || token == null ) {
      throw new EofException( toText( context ) );
    }
    source.execute( token, context, typesetter );
    source.push( t );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Token t = source.getToken( context );
    Token token = source.getToken( context );
    if( t == null || token == null ) {
      throw new EofException( toText( context ) );
    }
    source.push( source.expand( token, context, typesetter ) );
    source.push( t );
  }

}
