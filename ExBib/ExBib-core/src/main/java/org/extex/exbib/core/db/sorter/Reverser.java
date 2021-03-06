/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db.sorter;

import org.extex.exbib.core.db.Entry;
import org.extex.framework.configuration.exception.ConfigurationException;

import java.util.List;

/**
 * This sorter reverses the order of an embedded sorter.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Reverser implements Sorter {

  /**
   * The field {@code sorter} contains the embedded sorter.
   */
  private final Sorter sorter;

  /**
   * Creates a new object.
   *
   * @param sorter the embedded sorter
   */
  public Reverser( Sorter sorter ) {

    this.sorter = sorter;
  }

  public void sort( List<Entry> list ) throws ConfigurationException {

    sorter.sort( list );
  }

}
