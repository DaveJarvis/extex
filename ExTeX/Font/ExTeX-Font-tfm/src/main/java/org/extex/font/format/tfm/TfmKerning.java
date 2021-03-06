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

import java.io.Serializable;

/**
 * TFM-Kerning.
 * <p>
 * Kerning instruction
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TfmKerning extends TfmLigKern implements Serializable {

  /**
   * The field {@code serialVersionUID}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The amount of kerning.
   */
  private final TfmFixWord kern;

  /**
   * Create a new object.
   *
   * @param skip the skip amount to the next instruction.
   * @param next the code of the next character.
   * @param k    the amount of kerning between the current and the next
   *             characters.
   */
  public TfmKerning( int skip, short next, TfmFixWord k ) {

    super( skip, next );
    kern = k;
  }

  @Override
  public TfmFixWord getKern() {

    return kern;
  }

  @Override
  public String toString() {

    StringBuilder buf = new StringBuilder( super.toString() );
    buf.append( " kern=" ).append( kern.toString() );
    return buf.toString();
  }

}
