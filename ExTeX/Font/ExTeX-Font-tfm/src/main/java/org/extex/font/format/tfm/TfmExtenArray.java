/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.tfm;

import org.extex.util.file.random.RandomAccessR;

import java.io.IOException;
import java.io.Serializable;

/**
 * Class for TFM exten array.
 *
 * <p>
 * Extensible characters are specified by an extensible_recipe, which consists
 * of four bytes called top, mid, bot, and rep (in this order). These bytes are
 * the character codes of individual pieces used to build up a large symbol.
 * </p>
 *
 * <p>
 * Information from: The DVI Driver Standard, Level 0 The TUG DVI Driver
 * Standards Committee
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class TfmExtenArray implements Serializable {

  /**
   * The field {@code serialVersionUID}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * the array.
   */
  private final TfmExtensibleRecipe[] extensiblerecipe;

  /**
   * Create a new object.
   *
   * @param rar the input
   * @param ne  number of words in the extensible character table
   * @throws IOException if an IO-error occurs.
   */
  public TfmExtenArray( RandomAccessR rar, int ne ) throws IOException {

    extensiblerecipe = new TfmExtensibleRecipe[ ne ];
    for( int i = 0; i < ne; i++ ) {
      extensiblerecipe[ i ] = new TfmExtensibleRecipe( rar, i );
    }
  }

  /**
   * Returns the extensiblerecipe.
   *
   * @return Returns the extensiblerecipe.
   */
  public TfmExtensibleRecipe[] getExtensiblerecipe() {

    return extensiblerecipe;
  }

}
