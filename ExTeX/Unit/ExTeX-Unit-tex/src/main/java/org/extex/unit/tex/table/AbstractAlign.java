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

package org.extex.unit.tex.table;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.unit.tex.table.util.PreambleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the abstract base class for alignments.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractAlign extends AbstractCode {

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
  public AbstractAlign( CodeToken token ) {

    super( token );
  }

  /**
   * Getter for the Localizer.
   *
   * @return the localizer
   */
  Localizer getMyLocalizer() {

    return LocalizerFactory.getLocalizer( AbstractAlign.class );
  }

  /**
   * Get the preamble. The preamble is composed of PreambleItems.
   *
   * @param context the interpreter context
   * @param source  the source for new tokens
   * @return the preamble as list of PreambleItems
   * @throws HelpingException in case of an error
   */
  protected List<PreambleItem> getPreamble( Context context,
                                            TokenSource source )
      throws HelpingException {

    List<PreambleItem> preamble = new ArrayList<PreambleItem>();

    while( addPreambleItem( context, source, preamble ) ) {
      // nothing more to do
    }
    return preamble;
  }

  /**
   * Parse an item of a preamble and add it to the given list. If the item is
   * ended by a & then {@code true} is returned. If the item is ended
   * by a {@code \cr} then {@code false} is returned.
   *
   * @param context  the interpreter context
   * @param source   the source for new tokens
   * @param preamble the list to add something to
   * @return {@code true} iff the item has been ended by {@code &}.
   * @throws HelpingException in case of an error
   */
  private boolean addPreambleItem( Context context, TokenSource source,
                                   List<PreambleItem> preamble )
      throws HelpingException {

    Tokens pre = new Tokens();
    Tokens post = new Tokens();

    for( Token t = source.getToken( context ); t != null
        && !t.isa( Catcode.MACROPARAM ); t = source.getToken( context ) ) {

      if( t.isa( Catcode.TABMARK ) ) {
        throw new HelpingException( getMyLocalizer(),
                                    "TTP.MissingSharp", toText() );
      }
      else if( t instanceof CodeToken ) {
        Code code = context.getCode( (CodeToken) t );
        if( code instanceof Cr ) {
          throw new HelpingException( getMyLocalizer(),
                                      "TTP.MissingSharp", toText() );
        }
        else if( code != null && code.isOuter() ) {
          throw new HelpingException( getMyLocalizer(),
                                      "TTP.OuterInPreamble", toText() );
        }
      }
      pre.add( t );
    }

    for( Token t = source.getToken( context ); t != null; t =
        source.getToken( context ) ) {

      if( t.isa( Catcode.MACROPARAM ) ) {
        throw new HelpingException( getMyLocalizer(),
                                    "TTP.SecondSharpInTab", toText( context ) );
      }
      else if( t.isa( Catcode.TABMARK ) ) {
        preamble.add( new PreambleItem( pre, post ) );
        return true;
      }
      else if( t instanceof CodeToken ) {
        Code code = context.getCode( (CodeToken) t );
        if( code instanceof Cr ) {
          preamble.add( new PreambleItem( pre, post ) );
          return false;
        }
        else if( code != null && code.isOuter() ) {
          throw new HelpingException( getMyLocalizer(),
                                      "TTP.OuterInPreamble",
                                      toText( context ) );
        }
      }
      post.add( t );
    }

    throw new HelpingException( getMyLocalizer(), "TTP.EOFinPreamble",
                                toText( context ) );
  }

}
