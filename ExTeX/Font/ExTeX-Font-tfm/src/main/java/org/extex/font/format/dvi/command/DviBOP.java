/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.dvi.command;

/**
 * DVI bop: Beginning of a page.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class DviBOP extends DviCommand {

  /**
   * the c
   */
  private final int[] c;

  /**
   * the p
   */
  private final int p;

  /**
   * Create a new object.
   *
   * @param oc     the opcode
   * @param sp     the start pointer
   * @param carray the c-array
   * @param ap     the p
   */
  public DviBOP( final int oc, final int sp, final int[] carray,
                 final int ap ) {

    super( oc, sp );
    c = carray;
    p = ap;
  }

  /**
   * Returns the c.
   *
   * @return Returns the c.
   */
  public int[] getC() {

    return c;
  }

  @Override
  public String getName() {

    return "bop";
  }

  /**
   * Returns the p.
   *
   * @return Returns the p.
   */
  public int getP() {

    return p;
  }
}
