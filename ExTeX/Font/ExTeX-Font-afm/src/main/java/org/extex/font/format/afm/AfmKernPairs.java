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

package org.extex.font.format.afm;

import java.io.Serializable;

/**
 * AFM kerning pairs.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class AfmKernPairs implements Serializable {

  /**
   * The field {@code serialVersionUID}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * post.
   */
  private String charpost;

  /**
   * pre.
   */
  private String charpre;

  /**
   * kerningsize.
   */
  private float kerningsize;

  /**
   * Returns the charpost.
   *
   * @return Returns the charpost.
   */
  public String getCharpost() {

    return charpost;
  }

  /**
   * Returns the charpre.
   *
   * @return Returns the charpre.
   */
  public String getCharpre() {

    return charpre;
  }

  /**
   * Returns the kerningsize.
   *
   * @return Returns the kerningsize.
   */
  public float getKerningsize() {

    return kerningsize;
  }

  /**
   * Set the charpost.
   *
   * @param cp The charpost to set.
   */
  public void setCharpost( String cp ) {

    charpost = cp;
  }

  /**
   * Set the charpre.
   *
   * @param cp The charpre to set.
   */
  public void setCharpre( String cp ) {

    charpre = cp;
  }

  /**
   * Set the kerningsize.
   *
   * @param ksize The kerningsize to set.
   */
  public void setKerningsize( float ksize ) {

    kerningsize = ksize;
  }
}
