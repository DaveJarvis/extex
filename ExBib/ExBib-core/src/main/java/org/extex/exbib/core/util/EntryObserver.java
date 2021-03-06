/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.util;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.db.Entry;
import org.extex.framework.i18n.LocalizerFactory;

import java.util.logging.Logger;

/**
 * Observer which checks that new entries have associated methods in the bst.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class EntryObserver implements Observer {

  /**
   * The field {@code processor} contains the saved processor context.
   */
  private final Processor processor;

  /**
   * The field {@code logger} contains the logger for output.
   */
  private final Logger logger;

  /**
   * Creates a new object.
   *
   * @param logger    the writer for producing output
   * @param processor the processor context
   * @throws ExBibIllegalValueException in case of an error
   */
  public EntryObserver( Logger logger, Processor processor )
      throws ExBibIllegalValueException {

    this.logger = logger;
    this.processor = processor;

    if( logger == null ) {
      throw new ExBibIllegalValueException( LocalizerFactory.getLocalizer(
          getClass() ).format( "empty.writer" ), null );
    }

    if( processor == null ) {
      throw new ExBibIllegalValueException( LocalizerFactory.getLocalizer(
          getClass() ).format( "empty.processor" ), null );
    }
  }

  /**
   * java.lang.Object)
   */
  public void update( Observable source, Object o ) {

    Entry e = (Entry) o;
    String type = e.getType();

    if( !processor.isKnown( type ) ) {
      logger.warning( LocalizerFactory.getLocalizer( getClass() ).format(
          "not.defined", type, e.getKey() )
                          + "\t" + e.getLocator().toString() + "\n" );
    }
  }

}
