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
 * DVI: post post: Ending of the postamble.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class DviPostPost extends DviCommand {

  /**
   * the identification
   */
  private final int identification;

  /**
   * the pointer of the final post
   */
  private final int pointer;

  /**
   * Create a new object.
   *
   * @param oc the opcode
   * @param sp the start pointer
   * @param p  the pointer
   * @param i  the identification
   */
  public DviPostPost( final int oc, final int sp, final int p, final int i ) {

    super( oc, sp );
    pointer = p;
    identification = i;

  }

  /**
   * Returns the identification.
   *
   * @return Returns the identification.
   */
  public int getIdentification() {

    return identification;
  }

  @Override
  public String getName() {

    return "post_post";
  }

  /**
   * Returns the pointer.
   *
   * @return Returns the pointer.
   */
  public int getPointer() {

    return pointer;
  }
}
