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

package org.extex.exindex.makeindex.pages;

import java.util.List;
import java.util.logging.Logger;

/**
 * This interface describes a processor for a list of pages.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public interface PageProcessor {

  /**
   * Process the pages given.
   *
   * @param pages  the list of pages
   * @param logger the logger
   * @return the number of warnings produced
   */
  int join( List<Pages> pages, Logger logger );

  /**
   * Sort a list of pages.
   *
   * @param list the list to be sorted
   */
  void sort( List<Pages> list );
}
