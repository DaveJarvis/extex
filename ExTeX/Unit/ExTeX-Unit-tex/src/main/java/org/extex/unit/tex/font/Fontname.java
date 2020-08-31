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

package org.extex.unit.tex.font;

import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;

/**
 * This class provides an implementation for the primitive
 * {@code \fontname}.
 *
 * <p>The Primitive {@code \fontname}</p>
 * <p>
 * The primitive {@code \fontname} can be used to retrieve the name of a
 * font. It takes a font specification as argument. It expands to the name of
 * the font. If this font is not loaded at its design size then the actual size
 * is appended after the tokens {@code  at }. All tokens produced this way are
 * <i>other</i> tokens except of the spaces. This means that even the letters
 * are of category <i>other</i>.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;fontname&rang;
 *       &rarr; {@code \fontname} {@linkplain
 *          org.extex.interpreter.TokenSource#getFont(Context, CodeToken)
 *          &lang;font&rang;}  </pre>
 *
 * <p>Example</p>
 *
 *
 * <pre class="TeXSample">
 *  \font\myFont=cmr12
 *  \fontname\myfont
 *  &rArr; cmr12
 * </pre>
 *
 * <pre class="TeXSample">
 * \font\myFont=cmr12 at 24pt
 * \fontname\myfont
 * &rArr; cmr12 at 24pt
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class Fontname extends AbstractCode implements ExpandableCode {

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
  public Fontname( CodeToken token ) {

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

    source.skipSpace();
    Font font;
    Tokens fontname;
    TokenFactory tokenFactory = context.getTokenFactory();
    try {
      font = source.getFont( context, getToken() );
      fontname = tokenFactory.toTokens( font.getFontName() );
    } catch( EofException e ) {
      throw new EofException( toText( context ) );
    } catch( CatcodeException e ) {
      throw new NoHelpException( e );
    }
    FixedDimen size = font.getActualSize();
    if( font.getDesignSize().ne( size ) ) {
      try {
        fontname.add( tokenFactory.toTokens( " at " ) );
        fontname.add( tokenFactory.toTokens( size.toString() ) );
      } catch( CatcodeException e ) {
        throw new NoHelpException( e );
      }
    }
    source.push( fontname );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public void expand( Flags prefix, Context context, TokenSource source,
                      Typesetter typesetter )
      throws HelpingException, TypesetterException {

    execute( prefix, context, source, typesetter );
  }

}
