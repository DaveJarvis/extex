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

package org.extex.unit.tex.hyphen;

import org.extex.core.UnicodeChar;
import org.extex.core.UnicodeCharList;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.unit.base.register.CharCode;

/**
 * This class provides an implementation for the primitive
 * {@code \hyphenation}.
 *
 * <p>The Primitive {@code \hyphenation}</p>
 * <p>
 * The primitive {@code \hyphenation} can be used to add hyphenation exceptions
 * to the current language. The argument is a list of white-space separated
 * words enclosed in braces. The hyphenation points are indicated by including a
 * hyphen character (-) at the appropriate places.
 * </p>
 * <p>
 * When paragraph breaking needs to insert additional break points these
 * hyphenation points are translated into discretionaries. The exceptions
 * specified with the primitive {@code \hyphenation} have precedence before the
 * hyphenation points found with the help of hyphenation patterns.
 * </p>
 * <p>
 * One example which make use of this precedence is the hyphenation exception
 * without any hyphen characters. This can be used to suppress any hyphenation
 * in a single word.
 * </p>
 *
 * <p>Extension</p>
 *
 * <p>
 * In addition to the behavior of the original TeX definition this
 * implementation can be used to insert words
 * with hyphens as well. To specify the places where a hyphen occurs literally
 * you just have to include two hyphens in the hyphenation list.
 * </p>
 *
 * <p>Syntax</p>
 *
 *
 * <pre class="syntax">
 *    &lang;hyphenation&rang;
 *     &rarr; {@code \hyphenation} {&lang;words&rang;}
 *
 *    &lang;words&rang;
 *     &rarr;
 *      |  &lang;word&rang;
 *      |  &lang;word&rang; &lang;spaces&rang; &lang;words&rang;  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *   \hyphenation{as-so-ciate as-so-ciates}  </pre>
 *
 * <pre class="TeXSample">
 *   \hyphenation{Gro&szlig;--Ger-au}  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class Hyphenation extends HyphenationPrimitive {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Hyphenation( CodeToken token ) {

    super( token );
  }

  /**
   * Collect all characters that make up a word.
   *
   * @param context the interpreter context
   * @param source  the source for new tokens
   * @param token   the first token already read
   * @return the first character not included into the word
   * @throws CatcodeException in case of an exception in token creation
   * @throws HelpingException in case of an error
   */
  @Override
  protected UnicodeCharList collectWord( Context context, TokenSource source,
                                         Token token )
      throws CatcodeException, HelpingException {

    UnicodeCharList word = new UnicodeCharList();
    UnicodeChar uc;
    UnicodeChar lc;
    boolean hyphen = false;

    for( Token t = token; t != null; t = source.getToken( context ) ) {

      if( t instanceof LetterToken || t instanceof OtherToken ) {
        uc = t.getChar();
      }
      else if( t instanceof CodeToken ) {
        Code code = context.getCode( (CodeToken) t );
        uc = ((CharCode) code).getCharacter();
      }
      else {
        source.push( t );
        return word;
      }

      if( t.eq( Catcode.OTHER, '-' ) ) {
        if( hyphen ) {
          word.add( t.getChar() );
        }
        else {
          hyphen = true;
        }
        word.add( UnicodeChar.SHY );
      }
      else {
        if( hyphen ) {
          word.add( UnicodeChar.SHY );
          hyphen = false;
        }
        uc = t.getChar();
        lc = context.getLccode( uc );
        word.add( lc == null ? uc : lc );
      }
    }

    return word;
  }

}
