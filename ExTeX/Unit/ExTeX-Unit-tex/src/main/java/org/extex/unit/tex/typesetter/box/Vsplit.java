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

package org.extex.unit.tex.typesetter.box;

import org.extex.core.count.Count;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.box.Boxable;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.NodeList;
import org.extex.unit.tex.register.box.Setbox;

import javax.naming.OperationNotSupportedException;
import java.util.logging.Logger;

/**
 * This class provides an implementation for the primitive {@code \vsplit}.
 *
 * <p>The Primitive {@code \vsplit}</p>
 * <p>
 * TODO missing documentation
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;vsplit&rang;
 *       &rarr; {@code \vsplit} {@linkplain
 *        org.extex.unit.tex.register.box.Setbox#getKey(Context, TokenSource, Typesetter, CodeToken)
 *        &lang;box register name&rang;} </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 *    \vsplit 2 to 123pt  </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Vsplit extends AbstractCode implements Boxable, LogEnabled {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code logger} contains the target channel for the message.
   */
  private transient Logger logger = null;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Vsplit( CodeToken token ) {

    super( token );
  }

  /**
   * Setter for the logger.
   *
   * @param log the logger to use
   * @see org.extex.framework.logger.LogEnabled#enableLogging(
   *java.util.logging.Logger)
   */
  public void enableLogging( Logger log ) {

    this.logger = log;
  }

  /**
   * org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
   * org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
   */
  @Override
  public void execute( Flags prefix, Context context, TokenSource source,
                       Typesetter typesetter )
      throws HelpingException, TypesetterException {

    NodeList nl = vsplit( context, source, typesetter );
    typesetter.add( nl );
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
    return new Box( vsplit( context, source, typesetter ) );
  }

  /**
   * Perform the operation of the primitive {@code \vsplit} and return the
   * result as a NodeList.
   *
   * @param context    the interpreter context
   * @param source     the source for new tokens
   * @param typesetter the typesetter
   * @return the nodes of the vlist cut off
   * @throws HelpingException    in case of an error
   * @throws TypesetterException in case of an error in the typesetter
   */
  private NodeList vsplit( Context context, TokenSource source,
                           Typesetter typesetter )
      throws HelpingException, TypesetterException {

    String key = Setbox.getKey( context, source, typesetter, getToken() );
    if( !source.getKeyword( context, "to" ) ) {
      throw new HelpingException( getLocalizer(), "TTP.MissingToForVsplit" );
    }
    Dimen ht = source.parseDimen( context, source, typesetter );
    Box b = context.getBox( key );
    if( b == null || !b.isVbox() ) {
      throw new HelpingException( getLocalizer(), "TTP.SplittingNonVbox",
                                  toText( context ), context.esc( "vbox" ) );
    }
    // TODO gene: set splitmark etc
    try {
      return b
          .vsplit( ht,
                   (Count.ONE.le( context.getCount( "tracingpages" ) )
                       ? logger
                       : null) );
    } catch( OperationNotSupportedException e ) {
      // just to be sure. This should not happen
      throw new HelpingException( getLocalizer(), "TTP.SplittingNonVbox",
                                  toText( context ), context.esc( "vbox" ) );
    }
  }

}
