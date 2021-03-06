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
 * DVI: z
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class DviZ extends DviCommand {

  /**
   * z0
   */
  private static final int Z0 = 166;

  /**
   * the value
   */
  private final int value;

  /**
   * the z0
   */
  private final boolean z0;

  /**
   * Create a new object.
   *
   * @param opc the opcode
   * @param sp  the start pointer
   * @param v   the value
   */
  public DviZ( final int opc, final int sp, final int v ) {

    this( opc, sp, v, false );
  }

  /**
   * Create a new object.
   *
   * @param opc the opcode
   * @param sp  the start pointer
   * @param v   the value
   * @param z   the z0
   */
  public DviZ( final int opc, final int sp, final int v, final boolean z ) {

    super( opc, sp );
    value = v;
    z0 = z;
  }

  @Override
  public String getName() {

    return new StringBuilder( 'z' ).append( getOpcode() - Z0 ).toString();
  }

  /**
   * Returns the value.
   *
   * @return Returns the value.
   */
  public int getValue() {

    return value;
  }

  /**
   * Returns the z0.
   *
   * @return Returns the zy0.
   */
  public boolean isZ0() {

    return z0;
  }

}
