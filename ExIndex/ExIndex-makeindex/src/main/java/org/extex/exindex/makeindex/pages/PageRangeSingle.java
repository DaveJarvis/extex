/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.pages;

import org.extex.exindex.core.type.page.PageReference;

import java.io.IOException;
import java.io.Writer;

/**
 * This class represents a single page.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PageRangeSingle extends Pages {

  /**
   * Creates a new object.
   *
   * @param page  the page
   * @param encap the encapsulator or {@code null}
   */
  public PageRangeSingle( PageReference page, String encap ) {

    super( page, encap );
  }

  @Override
  public boolean isOne() {

    return true;
  }

  /**
   * java.lang.String[], java.lang.String)
   */
  @Override
  protected void writeCore( Writer writer, String[] pageParams,
                            String fromPage )
      throws IOException {

  }

}
