/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.parser;

import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.lisp.exception.LException;

import java.io.IOException;

/**
 * This interface describes the ability to retrieve an index entry from some
 * source.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface RawIndexParser {

  /**
   * Close the parser.
   *
   * @throws IOException in case of an I/O error
   */
  void close() throws IOException;

  /**
   * Read the next index entry.
   *
   * @return the next index entry or {@code null} at end of file
   * @throws LException  in case of an error
   * @throws IOException in case of an I/O error
   */
  RawIndexentry parse() throws LException, IOException;

}
