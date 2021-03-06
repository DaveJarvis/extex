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
 * DVI: w: move right
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class DviW extends DviCommand {

  /**
   * w0
   */
  private static final int W0 = 147;

  /**
   * the value
   */
  private final int value;

  /**
   * the w0
   */
  private final boolean w0;

  /**
   * Create a new object.
   *
   * @param opc the opcode
   * @param sp  the start pointer
   * @param v   the value
   */
  public DviW( final int opc, final int sp, final int v ) {

    this( opc, sp, v, false );
  }

  /**
   * Create a new object.
   *
   * @param opc the opcode
   * @param sp  the start pointer
   * @param v   the value
   * @param w   the w0
   */
  public DviW( final int opc, final int sp, final int v, final boolean w ) {

    super( opc, sp );
    value = v;
    w0 = w;
  }

  @Override
  public String getName() {

    return new StringBuilder().append( "w" ).append( getOpcode() - W0 )
                              .toString();
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
   * Returns the w0.
   *
   * @return Returns the w0.
   */
  public boolean isW0() {

    return w0;
  }
}
