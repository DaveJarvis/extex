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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for the AFM CharMetric.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class AfmCharMetric implements Serializable {

  /**
   * The field {@code serialVersionUID}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * B llx.
   */
  private float bllx = AfmHeader.NOTINIT;

  /**
   * B lly.
   */
  private float blly = AfmHeader.NOTINIT;

  /**
   * B urx.
   */
  private float burx = AfmHeader.NOTINIT;

  /**
   * B ury.
   */
  private float bury = AfmHeader.NOTINIT;

  /**
   * C.
   */
  private int c = -1;

  /**
   * Kerning. mgn: nach erstelltem Testfall durch map ersetzen
   */
  private List<AfmKernPairs> k = null;

  /**
   * Ligature.
   */
  private Map<String, String> l = null;

  /**
   * Name.
   */
  private String n = "";

  /**
   * WX.
   */
  private float wx = AfmHeader.NOTINIT;

  /**
   * Add a kerning.
   *
   * @param kp The kerning pairs.
   */
  public void addK( AfmKernPairs kp ) {

    if( k == null ) {
      k = new ArrayList<AfmKernPairs>();
    }
    if( kp != null ) {
      k.add( kp );
    }
  }

  /**
   * Add a ligature.
   *
   * @param letter the basic letter
   * @param lig    the ligature
   */
  public void addL( String letter, String lig ) {

    if( l == null ) {
      l = new HashMap<String, String>();
    }
    l.put( letter, lig );
  }

  /**
   * Returns a kerning pair for a letter, or {@code null}, if no
   * kerning is found.
   *
   * @param charpost The post character.
   * @return Returns a kerning pair for a letter, or {@code null}, if
   * no kerning is found.
   */
  public AfmKernPairs getAfmKernPair( String charpost ) {

    AfmKernPairs kp = null;
    if( k != null && charpost != null ) {
      for( int i = 0, j = k.size(); i < j; i++ ) {
        kp = k.get( i );
        if( kp.getCharpost().equals( charpost ) ) {
          return kp;
        }
      }
    }
    return null;
  }

  /**
   * Returns the bllx.
   *
   * @return Returns the bllx.
   */
  public float getBllx() {

    return bllx;
  }

  /**
   * Returns the blly.
   *
   * @return Returns the blly.
   */
  public float getBlly() {

    return blly;
  }

  /**
   * Returns the burx.
   *
   * @return Returns the burx.
   */
  public float getBurx() {

    return burx;
  }

  /**
   * Returns the bury.
   *
   * @return Returns the bury.
   */
  public float getBury() {

    return bury;
  }

  /**
   * Returns the c.
   *
   * @return Returns the c.
   */
  public int getC() {

    return c;
  }

  /**
   * Returns the depth of a glyph.
   *
   * @return the depth of a glyph.
   */
  public float getDepth() {

    if( getBlly() < 0 ) {
      return -getBlly();
    }
    return 0;
  }

  /**
   * Returns the height of a glyph.
   *
   * @return the height of a glyph.
   */
  public float getHeight() {

    if( getBury() > 0 ) {
      return getBury();
    }
    return 0;
  }

  /**
   * Returns the kerning.
   *
   * @return Returns the kerning.
   */
  public List<AfmKernPairs> getK() {

    return k;
  }

  /**
   * Returns the l.
   *
   * @return Returns the l.
   */
  public Map<String, String> getL() {

    return l;
  }

  /**
   * Returns the ligature for a letter or {@code null}, if no ligature
   * found.
   *
   * @param letter The letter.
   * @return Returns the ligature for a letter or {@code null}, if no
   * ligature found.
   */
  public String getLigature( String letter ) {

    if( l != null && letter != null ) {
      return l.get( letter );
    }
    return null;
  }

  /**
   * Returns the n.
   *
   * @return Returns the n.
   */
  public String getN() {

    return n;
  }

  /**
   * Returns the width of a glyph.
   *
   * @return the width of a glyph.
   */
  public float getWidth() {

    if( getWx() != AfmHeader.NOTINIT ) {
      return getWx();
    }
    // calculate with from bbox
    if( getBllx() != AfmHeader.NOTINIT ) {
      return getBllx() + getBurx();
    }
    return 0;
  }

  /**
   * Returns the wx.
   *
   * @return Returns the wx.
   */
  public float getWx() {

    return wx;
  }

  /**
   * Check, if the char has a kerning.
   *
   * @return Return {@code true}, if the char has a kerning.
   */
  public boolean isKerning() {

    return k != null;
  }

  /**
   * Check, if the char has a ligature.
   *
   * @return Return {@code true}, if the char has a ligature.
   */
  public boolean isLigatur() {

    return l != null;
  }

  /**
   * Set the bllx.
   *
   * @param ibllx The bllx to set.
   */
  public void setBllx( float ibllx ) {

    bllx = ibllx;
  }

  /**
   * Set the blly.
   *
   * @param iblly The blly to set.
   */
  public void setBlly( float iblly ) {

    blly = iblly;
  }

  /**
   * Set the burx.
   *
   * @param iburx The burx to set.
   */
  public void setBurx( float iburx ) {

    burx = iburx;
  }

  /**
   * Set the bury.
   *
   * @param ibury The bury to set.
   */
  public void setBury( float ibury ) {

    bury = ibury;
  }

  /**
   * Set the c.
   *
   * @param ic The c to set.
   */
  public void setC( int ic ) {

    c = ic;
  }

  /**
   * Set the n.
   *
   * @param s The n to set.
   */
  public void setN( String s ) {

    n = s;
  }

  /**
   * Set the wx.
   *
   * @param iwx The wx to set.
   */
  public void setWx( float iwx ) {

    wx = iwx;
  }

  /**
   * Return the name and the number of the glyph.
   *
   * @return Return the name and the number of the glyph.
   */
  @Override
  public String toString() {

    return n + " c=" + c;
  }

}
