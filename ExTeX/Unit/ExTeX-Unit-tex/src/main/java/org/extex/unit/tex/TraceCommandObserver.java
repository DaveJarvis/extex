/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.unit.tex;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.observer.command.CommandObserver;
import org.extex.interpreter.type.PrefixCode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Observer for tracing the execution of tokens. The token is written to the log
 * file enclosed in braces.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TraceCommandObserver implements CommandObserver {

  /**
   * The field {@code logger} contains the logger for output
   */
  private final Logger logger;

  /**
   * The field {@code context} contains the interpreter context.
   */
  private final Context context;

  /**
   * The field {@code prefix} contains the indicator that the last token
   * encountered has been a prefix primitive. This is used to suppress the
   * following trace output in TeX compatibility mode.
   */
  private boolean prefix = false;

  /**
   * Creates a new object.
   *
   * @param theLogger the logger for potential output
   * @param context   the interpreter context for access to
   *                  {@code \tracingonline}
   */
  public TraceCommandObserver( Logger theLogger, Context context ) {

    this.logger = theLogger;
    this.context = context;
  }

  /**
   * This method is meant to be invoked just before a token is executed. A
   * token following a prefix code is ignored if {@code \tracingcommands} is
   * not positive.
   *
   * @param token the token to be expanded
   */
  public void update( Token token ) {

    long tracing = context.getCount( "tracingcommands" ).getValue();

    if( tracing <= 0 ) {
      return;
    }

    if( !prefix ) {
      long online = context.getCount( "tracingonline" ).getValue();
      logger.log( (online > 0 ? Level.INFO : Level.FINE),
                  "{" + token.toText() + "}\n" );
    }

    try {
      prefix = (tracing > 2 &&
          token instanceof CodeToken &&
          context.getCode( (CodeToken) token ) instanceof PrefixCode);
    } catch( HelpingException e ) {
      logger.warning( e.getMessage() );
    }
  }

}
