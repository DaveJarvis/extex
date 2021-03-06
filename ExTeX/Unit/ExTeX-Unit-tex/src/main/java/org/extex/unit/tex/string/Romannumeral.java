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

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * {@code \romannumeral}.
 *
 * <p>The Primitive {@code \romannumeral}</p>
 * <p>
 * The primitive {@code \romannumeral} takes a single argument of a number and
 * produces the representation of this number in lower case roman numerals. If
 * the number is less than one than nothing is produced at all.
 * </p>
 *
 * <p>Syntax</p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;romannumeral&rang;
 *        &rarr; {@code \romannumeral} {@linkplain
 *           org.extex.base.parser.ConstantCountParser#parseNumber(Context, TokenSource, Typesetter)
 *           &lang;number&rang;} </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \romannumeral\count1  </pre>
 *
 * <pre class="TeXSample">
 *    \romannumeral 2004  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Romannumeral extends AbstractCode implements ExpandableCode {

  /**
   * The field {@code magic} contains the magical values used to compute the
   * string representation.
   */
  private static final char[] MAGIC = {'m', '2', 'd', '5', 'c', '2', 'l',
      '5', 'x', '2', 'v', '5', 'i'};

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Romannumeral( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    expand( prefix, context, source, typesetter );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    long n = source.parseInteger( context, source, typesetter );
    Tokens toks = new Tokens();
    TokenFactory factory = context.getTokenFactory();
    int j = 0;
    int v = 1000;

    try {
      for( ; ; ) {
        while( n >= v ) {
          toks.add( factory.createToken( Catcode.LETTER, MAGIC[ j ],
                                         Namespace.DEFAULT_NAMESPACE ) );
          n = n - v;
        }

        if( n <= 0 ) {
          source.push( toks );
          return; // non-positive input produces no output
        }

        int k = j + 2;
        int u = v / (MAGIC[ k - 1 ] - '0');
        if( MAGIC[ k - 1 ] == '2' ) {
          k = k + 2;
          u = u / (MAGIC[ k - 1 ] - '0');
        }
        if( n + u >= v ) {
          toks.add( factory.createToken( Catcode.LETTER, MAGIC[ k ],
                                         Namespace.DEFAULT_NAMESPACE ) );
          n = n + u;
        }
        else {
          j = j + 2;
          v = v / (MAGIC[ j - 1 ] - '0');
        }
      }
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
  }

}
