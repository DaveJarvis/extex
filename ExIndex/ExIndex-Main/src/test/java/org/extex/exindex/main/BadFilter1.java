/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.main;

import java.io.IOException;
import java.io.Reader;

/**
 * This is a reader for testing purposes which throws an exception in the
 * constructor.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class BadFilter1 extends Reader {

  /**
   * Creates a new object.
   *
   * @param r the argument
   */
  public BadFilter1( Reader r ) {

    throw new IllegalArgumentException();
  }

  @Override
  public void close() throws IOException {


  }

  @Override
  public int read( char[] cbuf, int off, int len ) throws IOException {

    return 0;
  }

}
