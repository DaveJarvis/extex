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

package org.extex.font.format.ofm;

import org.extex.font.format.tfm.TfmCharInfoArray;
import org.extex.util.file.random.RandomAccessR;

import java.io.IOException;

/**
 * Class for TFM char info.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class OfmCharInfoArray extends TfmCharInfoArray {

  /**
   * The field {@code serialVersionUID}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The char-info-word array.
   */
  private final OfmCharInfoWord[] charinfoword;

  /**
   * Create a new object.
   *
   * @param rar the input
   * @param cc  number of character
   * @throws IOException if an IO-error occurs.
   */
  public OfmCharInfoArray( RandomAccessR rar, int cc ) throws IOException {

    charinfoword = new OfmCharInfoWord[ cc ];
    for( int i = 0; i < cc; i++ ) {
      charinfoword[ i ] = new OfmCharInfoWord( rar, i );
    }
  }

  /**
   * Getter for charinfoword.
   *
   * @return the charinfoword
   */
  @Override
  public OfmCharInfoWord[] getCharinfoword() {

    return charinfoword;
  }

}
