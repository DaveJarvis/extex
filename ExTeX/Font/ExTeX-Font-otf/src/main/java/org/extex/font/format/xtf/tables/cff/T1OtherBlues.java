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

package org.extex.font.format.xtf.tables.cff;

import java.io.IOException;
import java.util.List;

/**
 * OtherBlues.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class T1OtherBlues extends T1DictDelta {

  /**
   * Create a new object.
   *
   * @param stack the stack
   * @throws IOException if an IO.error occurs.
   */
  public T1OtherBlues( List<T2Number> stack ) throws IOException {

    super( stack, new short[]{OTHER_BLUES} );
  }

  /**
   * {@inheritDoc}
   *
   * @see org.extex.font.format.xtf.tables.cff.T1DictKey#getName()
   */
  @Override
  public String getName() {

    return "OtherBlues";
  }

}
