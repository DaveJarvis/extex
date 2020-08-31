/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides an ordered list of {@link Observer Observers}s.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ObserverList implements Observer {

  /**
   * The field {@code list} contains the internal list of observers.
   */
  private final List<Observer> list = new ArrayList<Observer>();

  /**
   * Creates a new object containing no elements.
   */
  public ObserverList() {

  }

  /**
   * Add an observer to the list. It is not checked that whether the observer
   * is already contained, i.e. it is possible to have the same observer in
   * the list multiple times.
   *
   * @param observer the observer to add
   */
  public void add( Observer observer ) {

    list.add( observer );
  }

  /**
   * The update methods of all contained observers are invoked in turn with
   * the same arguments.
   */
  @Override
  public void update( Observable source, Object object ) {

    for( Observer obs : list ) {
      obs.update( source, object );
    }
  }

}
