/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exindex.makeindex.parser;

import org.extex.exindex.core.exception.RawIndexEofException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.makeindex.Index;
import org.extex.exindex.makeindex.normalizer.Collator;

import java.io.IOException;
import java.io.Reader;

/**
 * This interface describes a parser.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface Parser {

  /**
   * Load external sources into an index.
   *
   * @param reader   the reader
   * @param resource the name of the resource
   * @param index    the index to fill
   * @param collator the collator
   * @return a pair of number or read records and number of rejected entries
   * @throws IOException                  in case of an I/O error
   * @throws RawIndexEofException         TODO
   * @throws RawIndexMissingCharException TODO
   */
  int[] load( Reader reader, String resource, Index index, Collator collator )
      throws IOException,
      RawIndexEofException,
      RawIndexMissingCharException;

}
