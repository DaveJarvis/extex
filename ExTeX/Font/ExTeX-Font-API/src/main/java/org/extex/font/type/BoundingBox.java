/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.font.type;

import org.extex.core.dimen.Dimen;

import java.io.Serializable;


/**
 * Container for a BoundingBox.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class BoundingBox implements Serializable {

  /**
   * The field {@code serialVersionUID} ...
   */
  private static final long serialVersionUID = 1L;

  /**
   * llx
   */
  private Dimen llx;

  /**
   * lly
   */
  private Dimen lly;

  /**
   * urx
   */
  private Dimen urx;

  /**
   * ury
   */
  private Dimen ury;

  /**
   * Create a new object.
   *
   * @param lx the llx
   * @param ly the lly
   * @param rx the urx
   * @param ry the ury
   */
  public BoundingBox( Dimen lx, Dimen ly, Dimen rx,
                      Dimen ry ) {

    llx = lx;
    lly = ly;
    urx = rx;
    ury = ry;
  }

  /**
   * Returns the llx.
   *
   * @return Returns the llx.
   */
  public Dimen getLlx() {

    return llx;
  }

  /**
   * @param lx The llx to set.
   */
  public void setLlx( Dimen lx ) {

    llx = lx;
  }

  /**
   * Returns the lly.
   *
   * @return Returns the lly.
   */
  public Dimen getLly() {

    return lly;
  }

  /**
   * @param ly The lly to set.
   */
  public void setLly( Dimen ly ) {

    lly = ly;
  }

  /**
   * Returns the urx.
   *
   * @return Returns the urx.
   */
  public Dimen getUrx() {

    return urx;
  }

  /**
   * @param rx The urx to set.
   */
  public void setUrx( Dimen rx ) {

    urx = rx;
  }

  /**
   * Returns the ury.
   *
   * @return Returns the ury.
   */
  public Dimen getUry() {

    return ury;
  }

  /**
   * @param ry The ury to set.
   */
  public void setUry( Dimen ry ) {

    ury = ry;
  }
}