/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.base.type.file;

import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.type.tokens.Tokens;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * This is a class presenting itself as an OutFile which sends the tokens
 * written to it to the underlying operating system for the execution in a
 * shell.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ExecuteFile extends OutputFile {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 2006L;

  /**
   * The field {@code logger} contains the logger for tracing and error
   * messages.
   */
  private final transient Logger logger;

  /**
   * Creates a new object.
   *
   * @param logger the target Logger
   */
  public ExecuteFile( Logger logger ) {

    super( null );
    this.logger = logger;
  }

  /**
   * Close the current file.
   *
   * @throws IOException in case of an error
   * @see org.extex.scanner.type.file.OutFile#close()
   */
  @Override
  public void close() throws IOException {

    // nothing to do
  }

  /**
   * Check whether the output file is open.
   *
   * @return {@code true} iff the instance is open
   * @see org.extex.scanner.type.file.OutFile#isOpen()
   */
  @Override
  public boolean isOpen() {

    return true;
  }

  @Override
  public void newline() throws IOException {

    // nothing to do
  }

  /**
   * java.lang.String, TokenStreamFactory)
   */
  @Override
  public void open( String key, String encoding, TokenStreamFactory factory ) {

    // nothing to do
  }

  /**
   * org.extex.scanner.type.tokens.Tokens)
   */
  @Override
  public boolean write( Tokens toks ) throws IOException {

    Localizer localizer = LocalizerFactory.getLocalizer( ExecuteFile.class );
    String command = toks.toText();
    logger.fine( localizer.format( "Start.Message", command ) );
    Process process = Runtime.getRuntime().exec( command );
    try {
      int exitCode = process.waitFor();
      if( exitCode == 0 ) {
        logger.fine( localizer.format( "End.Message", command ) );
      }
      else {
        logger.fine( localizer.format( "Failed.Message", command,
                                       Integer.toString( exitCode ) ) );
      }
    } catch( InterruptedException e ) {
      logger.fine( localizer.format( "Interrupted.Message", command ) );
    }
    return true;
  }

}
