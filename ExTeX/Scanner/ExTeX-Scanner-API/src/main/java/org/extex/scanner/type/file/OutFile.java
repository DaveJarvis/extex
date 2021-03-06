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

package org.extex.scanner.type.file;

import org.extex.core.exception.helping.HelpingException;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.type.tokens.Tokens;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * This class holds an output file onto which tokens can be written.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface OutFile extends Serializable {

  /**
   * Close the current file.
   *
   * @throws IOException in case of an error
   */
  void close() throws IOException;

  /**
   * Check whether the output file is open.
   *
   * @return {@code true} iff the instance is open
   */
  boolean isOpen();

  /**
   * Open the current file.
   *
   * @param key      the key
   * @param encoding the encoding or {@code null} for the default
   *                 encoding
   * @param factory  the token stream factory
   * @throws UnsupportedEncodingException in case of an invalid encoding
   */
  void open( String key, String encoding, TokenStreamFactory factory )
      throws UnsupportedEncodingException;

  /**
   * Write out a newline.
   *
   * @throws IOException in case of an error
   */
  void newline() throws IOException;

  /**
   * Write some tokens to the output writer.
   *
   * @param toks tokens to write
   * @return {@code true} iff the writing was successful
   * @throws HelpingException in case of an error
   * @throws IOException      in case of an IO error
   */
  boolean write( Tokens toks ) throws HelpingException, IOException;

}
