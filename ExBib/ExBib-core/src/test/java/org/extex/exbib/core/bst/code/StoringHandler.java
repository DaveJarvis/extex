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

package org.extex.exbib.core.bst.code;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * This class is a log handler which stores the log record in a buffer.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public final class StoringHandler extends Handler {

  /**
   * The field {@code buffer} contains the buffer.
   */
  private final StringBuilder buffer = new StringBuilder();


  public StoringHandler() {

  }

  @Override
  public void close() {


  }

  @Override
  public void flush() {


  }

  @Override
  public void publish( LogRecord record ) {

    buffer.append( record.getMessage() );
  }

  @Override
  public String toString() {

    return buffer.toString();
  }

}
