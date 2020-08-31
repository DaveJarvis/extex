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
 * DVI: down
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class DviDown extends DviCommand {

  /**
   * down1
   */
  private static final int DOWN1 = 157;

  /**
   * the value
   */
  private final int value;

  /**
   * Create a new object.
   *
   * @param opc the opcode
   * @param sp  the start pointer
   * @param v   the value
   */
  public DviDown( final int opc, final int sp, final int v ) {

    super( opc, sp );
    value = v;
  }

  @Override
  public String getName() {

    return new StringBuilder( "down" ).append( getOpcode() - DOWN1 + 1 )
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

}
