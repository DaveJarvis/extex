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
 * DVI: post: Beginning of the postamble.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class DviPost extends DviCommand {

  /**
   * the den
   */
  private final int den;

  /**
   * height-plus-depth of the tallest page
   */
  private final int heigthdepth;

  /**
   * the mag
   */
  private final int mag;

  /**
   * the num
   */
  private final int num;

  /**
   * the pointer of the final page
   */
  private final int pointer;

  /**
   * the stack depth
   */
  private final int stackdepth;

  /**
   * total pages
   */
  private final int totalpage;

  /**
   * the width of the widest page
   */
  private final int width;

  /**
   * Create a new object.
   *
   * @param oc   the opcode
   * @param sp   the start pointer
   * @param p    the pointer
   * @param anum the num
   * @param aden the den
   * @param amag the mag
   * @param l    the height+depth
   * @param u    the width
   * @param s    the stack depth
   * @param t    the total pages
   */
  public DviPost( final int oc, final int sp, final int p, final int anum,
                  final int aden, final int amag, final int l, final int u,
                  final int s, final int t ) {

    super( oc, sp );
    pointer = p;
    num = anum;
    den = aden;
    mag = amag;
    heigthdepth = l;
    width = u;
    stackdepth = s;
    totalpage = t;

  }

  /**
   * Returns the den.
   *
   * @return Returns the den.
   */
  public int getDen() {

    return den;
  }

  /**
   * Returns the heigthdepth.
   *
   * @return Returns the heigthdepth.
   */
  public int getHeigthdepth() {

    return heigthdepth;
  }

  /**
   * Returns the mag.
   *
   * @return Returns the mag.
   */
  public int getMag() {

    return mag;
  }

  @Override
  public String getName() {

    return "post";
  }

  /**
   * Returns the num.
   *
   * @return Returns the num.
   */
  public int getNum() {

    return num;
  }

  /**
   * Returns the pointer.
   *
   * @return Returns the pointer.
   */
  public int getPointer() {

    return pointer;
  }

  /**
   * Returns the stackdepth.
   *
   * @return Returns the stackdepth.
   */
  public int getStackdepth() {

    return stackdepth;
  }

  /**
   * Returns the totalpage.
   *
   * @return Returns the totalpage.
   */
  public int getTotalpage() {

    return totalpage;
  }

  /**
   * Returns the width.
   *
   * @return Returns the width.
   */
  public int getWidth() {

    return width;
  }

}
