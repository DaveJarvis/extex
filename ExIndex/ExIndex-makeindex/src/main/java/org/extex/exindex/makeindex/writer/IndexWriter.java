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

package org.extex.exindex.makeindex.writer;

import org.extex.exindex.makeindex.Entry;
import org.extex.exindex.makeindex.pages.PageProcessor;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * This interface describes a writer for an index.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface IndexWriter {

  /**
   * Write the entries to the writer.
   *
   * @param entries       the entries
   * @param logger        the logger
   * @param page          the page
   * @param pageProcessor the page processor
   * @return the number of lines and the number of warnings produced
   * @throws IOException in case of an I/O error
   */
  int[] write( List<Entry> entries, Logger logger, String page,
               PageProcessor pageProcessor ) throws IOException;

}
