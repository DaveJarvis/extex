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

import org.extex.core.Locator;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingLeftBraceException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.unit.tex.table.util.HAlignListMaker;
import org.extex.unit.tex.table.util.PreambleItem;

import java.util.List;

/**
 * This class provides an implementation for the primitive {@code \halign}.
 *
 * <p>The Primitive {@code \halign}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;halign&rang;
 *       &rarr; {@code \halign} &lang;box specification&rang; {@code {} &lang;preamble&rang; {@code \cr} &lang;rows&rang; {@code }}
 *
 *    &lang;box specification&rang;
 *        &rarr;
 *         | {@code to} &lang;rule dimension&rang;
 *         | {@code spread} &lang;rule dimension&rang;
 *
 *    &lang;rows&rang;
 *       &rarr;
 *       | &lang;row&rang; &lang;rows&rang;
 *
 *    &lang;preamble&rang;
 *       &rarr; ...   </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \halign  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Halign extends AbstractAlign implements Boxable {

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
  public Halign( CodeToken token ) {

    super( token );
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException,
      TypesetterException,
      ConfigurationException {

    Flags f = prefix.copy();
    prefix.clear();
    typesetter.add( getNodes( context, source, typesetter ) );
    prefix.set( f );
  }

  /**
   * org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
   * org.extex.scanner.type.token.Token)
   */
  public Box getBox( Context context, TokenSource source,
                     Typesetter typesetter, Token insert )
      throws HelpingException,
      TypesetterException {

    // TODO gene: treat insert
    try {
      return new Box( getNodes( context, source, typesetter ) );
    } catch( TypesetterException e ) {
      throw new NoHelpException( e );
    }
  }

  /**
   * Getter for the nodes.
   *
   * @param context    the interpreter context
   * @param source     the token source
   * @param typesetter the typesetter
   * @return the list of nodes gathered
   * @throws HelpingException       in case of an error
   * @throws TypesetterException    in case of an error in the typesetter
   * @throws ConfigurationException in case of an configuration error
   */
  private NodeList getNodes( Context context, TokenSource source,
                             Typesetter typesetter )
      throws HelpingException, TypesetterException {

    FixedDimen width = null;
    boolean spread = false;

    if( source.getKeyword( context, "to" ) ) {
      width = source.parseDimen( context, source, typesetter );
    }
    else if( source.getKeyword( context, "spread" ) ) {
      width = source.parseDimen( context, source, typesetter );
      spread = true;
    }
    Token t = source.getToken( context );
    Locator locator = source.getLocator();
    if( t == null ) {
      throw new EofException( toText( context ) );
    }
    else if( t.isa( Catcode.LEFTBRACE ) ) {
      List<PreambleItem> preamble = getPreamble( context, source );
      typesetter.push( new HAlignListMaker( typesetter.getManager(),
                                            context,
                                            source,
                                            preamble,
                                            width,
                                            spread ) );
    }
    else {
      throw new MissingLeftBraceException(
          toText( context ) );
    }

    context.openGroup( GroupType.ALIGN_GROUP, locator, t ); // gene: correct
    // value?

    source.executeGroup();
    return typesetter.complete( (TypesetterOptions) context );
  }

}
