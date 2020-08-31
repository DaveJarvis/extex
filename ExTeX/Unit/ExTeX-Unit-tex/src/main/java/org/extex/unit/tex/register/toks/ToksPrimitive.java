/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register.toks;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.tokens.TokensConvertible;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.register.toks.AbstractToks;

/**
 * This class provides an implementation for the primitive {@code \toks}.
 * It sets the named tokens register to the value given, and as a side effect
 * all prefixes are zeroed.
 *
 * <p>The Primitive {@code \toks}</p>
 * <p>
 * The primitive {@code \toks} provides access to the named tokens register.
 * token sequences can be stored in them for later use. This means that the
 * tokens do not have to be parsed again. Even a change in the catcode settings
 * does not alter the tokens build once.
 * </p>
 * <p>
 * The primitive {@code \toks} also respects the count register
 * {@code \globaldefs} to enable general global assignment.
 * </p>
 * <p>
 * Since the primitive is classified as assignment the value of
 * {@code \afterassignment} is applied.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;toks&rang;
 *      &rarr; {@code \toks} {@linkplain
 *        org.extex.interpreter.TokenSource#scanRegisterName(Context, TokenSource, Typesetter, CodeToken)
 *        &lang;register name&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context, TokenSource, Typesetter)
 *        &lang;tokens&rang;}  </pre>
 *
 * <p>
 * In TeX the register name is a number in the range 0 to 255.
 * Extensions to this are defined in ε-TeX and  Omega where the limitation of
 * the range is
 * raised. In εχTeX this limit can be configured. In addition tokens can be
 * used to address named token registers.
 * </p>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \toks2={UTF-8}  </pre>
 *
 * <pre class="TeXSample">
 *    \toks42={UTF-8}  </pre>
 *
 * <pre class="TeXSample">
 *    \toks42=\toks0  </pre>
 *
 * <pre class="TeXSample">
 *    \toks{abc}={Hello world}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:mgn@gmx.de">Michael Niedermair</a>
 */
public class ToksPrimitive extends AbstractToks
    implements
    TokensConvertible,
    Theable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public ToksPrimitive( CodeToken token ) {

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

    String key = getKey( context, source, typesetter );
    source.getOptionalEquals( context );
    Tokens toks;
    try {
      toks = source.getTokens( context, source, typesetter );
    } catch( EofException e ) {
      throw new EofInToksException( toText() );
    }
    context.setToks( key, toks, prefix.clearGlobal() );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public Tokens convertTokens( Context context, TokenSource source,
                               Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = getKey( context, source, typesetter );
    return context.getToks( key );
  }

  /**
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public Tokens the( Context context, TokenSource source,
                     Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    return context.getToks( getKey( context, source, typesetter ) );
  }

}
