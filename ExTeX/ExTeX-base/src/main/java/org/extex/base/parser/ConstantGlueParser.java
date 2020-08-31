/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.base.parser;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.UndefinedControlSequenceException;
import org.extex.core.glue.Glue;
import org.extex.core.glue.GlueComponent;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.GlueConvertible;
import org.extex.interpreter.parser.GlueParser;
import org.extex.interpreter.parser.Parser;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides a parser for constant glue entities.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class ConstantGlueParser implements Parser<Glue>, GlueParser {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * Creates a new object by parsing a token source.
   *
   * <pre class="syntax">
   *   &lang;glue&rang;
   *     &rarr; &lang;component&rang;
   *      |  &lang;component&rang; plus &lang;component&rang;
   *      |  &lang;component&rang; minus &lang;component&rang;
   *      |  &lang;component&rang; plus &lang;component&rang; minus &lang;component&rang;
   *
   *   &lang;component&rang;
   *     &rarr; &lang;dimen;&rang;
   *      |  &lang;float&rang; &lang;unit&rang;
   *
   *   &lang;unit&rang;
   *     &rarr; fi
   *      |  fil
   *      |  fill
   *      |  filll    </pre>
   *
   * <p>
   * TODO gene: documentation incomplete
   * </p>
   *
   * @param source     the source to read new tokens from
   * @param context    the processing context
   * @param typesetter the typesetter
   * @return a new instance of a Glue
   * @throws HelpingException    in case of an error
   * @throws TypesetterException in case of an error in the typesetter
   */
  public static Glue scan( TokenSource source, Context context,
                           Typesetter typesetter )
      throws HelpingException, TypesetterException {

    Dimen length;
    GlueComponent shrink;
    GlueComponent stretch;
    Token t = source.getToken( context );
    if( t instanceof CodeToken ) {
      Code code = context.getCode( (CodeToken) t );
      if( code instanceof GlueConvertible ) {
        Glue g =
            ((GlueConvertible) code).convertGlue( context, source,
                                                  null );
        length = new Dimen( g.getLength() );
        shrink = new GlueComponent( g.getShrink() );
        stretch = new GlueComponent( g.getStretch() );
        return new Glue( length, stretch, shrink );
      }
      else if( code == null ) {
        throw new UndefinedControlSequenceException( t.toText() );
      }
    }
    source.push( t );
    length = ConstantDimenParser.scan( context, source, typesetter );
    if( source.getKeyword( context, "plus" ) ) {
      stretch =
          GlueComponentParser
              .parse( context, source, typesetter, true );
    }
    else {
      stretch = new GlueComponent( 0 );
    }
    if( source.getKeyword( context, "minus" ) ) {
      shrink =
          GlueComponentParser
              .parse( context, source, typesetter, true );
    }
    else {
      shrink = new GlueComponent( 0 );
    }
    return new Glue( length, stretch, shrink );
  }


  public ConstantGlueParser() {

  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public Glue parse( Context context, TokenSource source,
                     Typesetter typesetter )
      throws HelpingException,
      TypesetterException {

    return scan( source, context, typesetter );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  public Glue parseGlue( Context context, TokenSource source,
                         Typesetter typesetter )
      throws HelpingException, TypesetterException {

    return scan( source, context, typesetter );
  }

}
