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

package org.extex.unit.tex.string;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.*;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code &#x5c;uppercase}.
 *
 * <p>The Primitive {@code &#x5c;uppercase}</p>
 * <p>
 * The primitive {@code &#x5c;uppercase} takes as argument a token list in
 * braces and translates all letters to their lowercase form. The translation
 * table is stored in {@code &#x5c;uccode}. Thus the translation can be
 * modified freely.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;uppercase&rang;
 *        &rarr; {@code &#x5c;uppercase} {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context, TokenSource, Typesetter)
 *        &lang;tokens&rang;}  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    &#x5c;uppercase {ABC}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Uppercase extends AbstractCode {

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
  public Uppercase( CodeToken token ) {

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

    expand( prefix, context, source, typesetter );
  }

  /**
   * This method takes the first token and expands it. The result is placed on
   * the stack. This means that expandable code does one step of expansion and
   * puts the result on the stack. To expand a token it might be necessary to
   * consume further tokens.
   *
   * @param prefix     the prefix flags controlling the expansion
   * @param context    the interpreter context
   * @param source     the token source
   * @param typesetter the typesetter
   * @throws HelpingException    in case of an error
   * @throws TypesetterException in case of an error in the typesetter
   * @see org.extex.interpreter.type.ExpandableCode#expand(
   *org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Tokens toks;
    try {
      toks = source.getTokens( context, source, typesetter );
    } catch( EofException e ) {
      throw new EofInToksException( toText( context ) );
    }

    String namespace = context.getNamespace();
    Token[] result = new Token[ toks.length() ];
    TokenFactory factory = context.getTokenFactory();
    Token t;

    for( int i = 0; i < toks.length(); i++ ) {
      t = toks.get( i );
      if( t instanceof LetterToken || t instanceof OtherToken ) {
        UnicodeChar uc = context.getUccode( t.getChar() );
        if( uc != null &&
            uc.getCodePoint() != 0 &&
            !uc.equals( t.getChar() ) ) {
          try {
            t = factory.createToken( t.getCatcode(), uc, namespace );
          } catch( CatcodeException e ) {
            throw new NoHelpException( e );
          }
        }
      }
      result[ i ] = t;
    }

    source.push( result );
  }

}
