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

package org.extex.unit.tex.file;

import org.extex.base.type.file.LogFile;
import org.extex.base.type.file.UserAndLogFile;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.TokensWriter;
import org.extex.scanner.type.file.OutFile;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.file.AbstractFileCode;
import org.extex.unit.tex.file.nodes.WhatsItWriteNode;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * This class provides an implementation for the primitive {@code \write}.
 *
 * <p>The Primitive {@code \write}</p>
 * <p>
 * The primitive {@code \write} can be used to write some text to an output
 * stream. There are two modes of operation: Either the writing is delayed until
 * the page is shipped or the writing is performed immediately. The default mode
 * of operation is the delayed writing. The prefix {@code \immediate} can be
 * used to switch to the immediate writing.
 * </p>
 * <p>
 * The first argument to {@code \write} is the stream. It is usually opened
 * with {@code \openin}. If the stream has not been opened this way or has
 * been closed in the mean time then the result is written to the console and
 * the log file.
 * </p>
 * <p>
 * The second argument is a block of text. It is stored away and expanded just
 * before the writing occurs. This means that the values of control sequences or
 * registers are in fact used with their meaning when the page is shipped in the
 * case of delayed writing.
 * </p>
 *
 * <p>Syntax</p>
 * <p>
 * The formal description of this primitive is the following:
 *
 * <pre class="syntax">
 *    &lang;write&rang;
 *      &rarr; &lang;modifier&rang; {@code \write} {@linkplain
 *        org.extex.unit.base.file.AbstractFileCode#scanOutFileKey(Context, TokenSource, Typesetter)
 *        &lang;outfile&nbsp;name&rang;} {@link TokenSource#getToken(Context)
 *        &lang;replacement&nbsp;text&rang;}
 *
 *    &lang;modifier&rang;
 *      &rarr;
 *       |  {@code \immediate} &lang;modifier&rang;  </pre>
 *
 * <p>Examples</p>
 *
 *
 * <pre class="TeXSample">
 * \immediate\openout3= abc.def
 * \write3{Hi there!}
 * \closeout3 </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Write extends AbstractCode implements TokensWriter, LogEnabled {

  /**
   * The constant {@code LOG_FILE} contains the key for the log file.
   */
  private static final String LOG_FILE = "-1";

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code USER_AND_LOG} contains the key for the user trace and
   * log file.
   */
  private static final String USER_AND_LOG = "17";

  /**
   * The field {@code logger} contains the target channel for the message.
   */
  private transient Logger logger = null;

  /**
   * Creates a new object.
   *
   * @param token the initial token for the primitive
   */
  public Write( CodeToken token ) {

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

    String key =
        AbstractFileCode.scanOutFileKey( context, source, typesetter );

    if( prefix.clearImmediate() ) {

      Tokens toks = source.scanUnprotectedTokens( context, false, false,
                                                  getToken() );
      try {
        toks = source.expand( toks, typesetter );
      } catch( HelpingException e ) {
        throw e;
      } catch( TypesetterException e ) {
        throw e;
      } catch( GeneralException e ) {
        throw new NoHelpException( e );
      }
      OutFile of = write( key, toks, context );
      if( of != null ) {
        try {
          of.newline();
        } catch( IOException e ) {
          throw new NoHelpException( e );
        }
      }

    }
    else {

      Tokens tokens;
      try {
        tokens = source.getTokens( context, source, typesetter );
      } catch( EofException e ) {
        throw new EofInToksException( toText( context ) );
      }

      typesetter.add( new WhatsItWriteNode( key, tokens, source, this ) );
    }
  }

  /**
   * org.extex.scanner.type.tokens.Tokens,
   * org.extex.interpreter.context.Context)
   */
  public OutFile write( String key, Tokens toks, Context context )
      throws HelpingException {

    OutFile file = context.getOutFile( key );

    if( file == null || !file.isOpen() ) {

      if( key == null || "".equals( key ) || key.charAt( 0 ) == '-' ) {
        file = context.getOutFile( LOG_FILE );
        if( file == null ) {
          // this should not be necessary
          file = new LogFile( logger );
          context.setOutFile( LOG_FILE, file, false );
        }
      }
      else {
        file = context.getOutFile( USER_AND_LOG );
        if( file == null ) {
          // this should not be necessary
          file = new UserAndLogFile( logger );
          context.setOutFile( USER_AND_LOG, file, false );
        }
      }
    }

    try {
      if( !file.write( toks ) ) {

        // second try
        file = context.getOutFile( USER_AND_LOG );
        if( file == null ) {
          // this should not be necessary
          file = new UserAndLogFile( logger );
          context.setOutFile( USER_AND_LOG, file, false );
        }
        file.write( toks );
      }

    } catch( IOException e ) {
      throw new NoHelpException( e );
    }
    return file;
  }

}
