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

package org.extex.font.format.tfm;

import org.extex.util.file.random.RandomAccessR;

import java.io.IOException;
import java.io.Serializable;

/**
 * Class for TFM header length table.
 *
 * <p>
 * The first 24 bytes of a TFM file contain the lengths of the various
 * subsequent portions of the file. (12 x 16-bit interger) or (6 words x 32 bit)
 * </p>
 *
 * <table> <caption>TBD</caption> <thead>
 * <tr>
 * <td><b>name</b></td>
 * <td><b>description</b></td>
 * </tr>
 * </thead>
 * <tr>
 * <td>lf</td>
 * <td>length of the entire file, in words</td>
 * </tr>
 * <tr>
 * <td>lh</td>
 * <td>length of the header data, in words</td>
 * </tr>
 * <tr>
 * <td>bc</td>
 * <td>smallest character code in the font</td>
 * </tr>
 * <tr>
 * <td>ec</td>
 * <td>largest character code in the font</td>
 * </tr>
 * <tr>
 * <td>nw</td>
 * <td>number of words in the width table</td>
 * </tr>
 * <tr>
 * <td>nh</td>
 * <td>number of words in the height table</td>
 * </tr>
 * <tr>
 * <td>nd</td>
 * <td>number of words in the depth table</td>
 * </tr>
 * <tr>
 * <td>ni</td>
 * <td>number of words in the italic correction table</td>
 * </tr>
 * <tr>
 * <td>nl</td>
 * <td>number of words in the lig/kern table</td>
 * </tr>
 * <tr>
 * <td>nk</td>
 * <td>number of words in the kern table</td>
 * </tr>
 * <tr>
 * <td>ne</td>
 * <td>number of words in the extensible character table</td>
 * </tr>
 * <tr>
 * <td>np</td>
 * <td>number of font parameter words</td>
 * </tr>
 * </table>
 *
 * <p>
 * Information from: The DVI Driver Standard, Level 0 The TUG DVI Driver
 * Standards Committee
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TfmHeaderLengths implements Serializable {

  /**
   * Bytes in the Header of the TFM-file.
   */
  private static final int HEADERBYTES = 6;

  /**
   * max chars.
   */
  private static final int MAXCHARS = 255;

  /**
   * The field {@code serialVersionUID}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * smallest character code in the font.
   */
  protected int bc;

  /**
   * number of character.
   */
  protected int cc;

  /**
   * largest character code in the font.
   */
  protected int ec;

  /**
   * length of the entire file.
   */
  protected int lf;

  /**
   * length of the header data.
   */
  protected int lh;

  /**
   * number of words in the depth table.
   */
  protected int nd;

  /**
   * number of words in the extensible character table.
   */
  protected int ne;

  /**
   * number of words in the height table.
   */
  protected int nh;

  /**
   * number of words in the italic correction table.
   */
  protected int ni;

  /**
   * number of words in the kern table.
   */
  protected int nk;

  /**
   * number of words in the lig/kern table.
   */
  protected int nl;

  /**
   * number of font parameter words.
   */
  protected int np;

  /**
   * number of words in the width table.
   */
  protected int nw;

  /**
   * Creates a new object.
   * <p>
   * Only for OfmHeaderLengths
   */
  protected TfmHeaderLengths() {

  }

  /**
   * Create a new object.
   *
   * @param rar the input
   * @throws IOException if an IO-error occurs.
   */
  public TfmHeaderLengths( RandomAccessR rar ) throws IOException {

    lf = rar.readShort();
    lh = rar.readShort();
    bc = rar.readShort();
    ec = rar.readShort();
    nw = rar.readShort();
    nh = rar.readShort();
    nd = rar.readShort();
    ni = rar.readShort();
    nl = rar.readShort();
    nk = rar.readShort();
    ne = rar.readShort();
    np = rar.readShort();

    // check
    if( lf == 0
        || lf < HEADERBYTES
        || lh < 2
        || bc > ec + 1
        || ec > MAXCHARS
        || ne > MAXCHARS + 1
        || (HEADERBYTES + lh + (ec - bc + 1) + nw + nh + nd + ni + nl
        + nk + ne + np) != lf || nw == 0 || nh == 0 || nd == 0
        || ni == 0 ) {
      throw new IOException();
      // mgn: umbauen
      // throw new TFMReadFileException();
    }

    cc = ec + 1 - bc;

    if( cc == 0 ) {
      bc = 0;
    }
  }

  /**
   * Returns the bc.
   *
   * @return Returns the bc.
   */
  public int getBc() {

    return bc;
  }

  /**
   * Returns the cc.
   *
   * @return Returns the cc.
   */
  public int getCc() {

    return cc;
  }

  /**
   * Returns the ec.
   *
   * @return Returns the ec.
   */
  public int getEc() {

    return ec;
  }

  /**
   * Returns the lf.
   *
   * @return Returns the lf.
   */
  public int getLf() {

    return lf;
  }

  /**
   * Returns the lh.
   *
   * @return Returns the lh.
   */
  public int getLh() {

    return lh;
  }

  /**
   * Returns the nd.
   *
   * @return Returns the nd.
   */
  public int getNd() {

    return nd;
  }

  /**
   * Returns the ne.
   *
   * @return Returns the ne.
   */
  public int getNe() {

    return ne;
  }

  /**
   * Returns the nh.
   *
   * @return Returns the nh.
   */
  public int getNh() {

    return nh;
  }

  /**
   * Returns the ni.
   *
   * @return Returns the ni.
   */
  public int getNi() {

    return ni;
  }

  /**
   * Returns the nk.
   *
   * @return Returns the nk.
   */
  public int getNk() {

    return nk;
  }

  /**
   * Returns the nl.
   *
   * @return Returns the nl.
   */
  public int getNl() {

    return nl;
  }

  /**
   * Returns the np.
   *
   * @return Returns the np.
   */
  public int getNp() {

    return np;
  }

  /**
   * Returns the nw.
   *
   * @return Returns the nw.
   */
  public int getNw() {

    return nw;
  }

}
