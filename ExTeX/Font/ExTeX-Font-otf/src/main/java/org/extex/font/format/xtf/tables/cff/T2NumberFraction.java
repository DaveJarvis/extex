/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables.cff;

import org.extex.font.format.xtf.tables.XtfConstants;
import org.extex.util.file.random.RandomAccessR;

import java.io.IOException;

/**
 * T2 Number Fraction.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class T2NumberFraction extends T2Number {

  /**
   * the bytes as short-array
   */
  private final short[] bytes;

  /**
   * den (nenner)
   */
  private final int den;

  /**
   * num (zaehler)
   */
  private final int num;

  /**
   * Create a new object.
   *
   * @param rar the input
   * @param b0  the b0
   * @throws IOException if an IO-error occurs.
   */
  T2NumberFraction( RandomAccessR rar, int b0 ) throws IOException {

    int b1 = rar.readUnsignedByte();
    int b2 = rar.readUnsignedByte();
    int b3 = rar.readUnsignedByte();
    int b4 = rar.readUnsignedByte();
    num = (b1 << XtfConstants.SHIFT8) + b2;
    den = (b3 << XtfConstants.SHIFT8) + b4;

    bytes = new short[ 5 ];
    bytes[ 0 ] = (short) b0;
    bytes[ 1 ] = (short) b1;
    bytes[ 2 ] = (short) b2;
    bytes[ 3 ] = (short) b3;
    bytes[ 4 ] = (short) b4;
  }

  @Override
  public short[] getBytes() {

    return bytes;
  }

  @Override
  public double getDouble() {

    return num + (double) den / 0xffff;
  }

  @Override
  public int getInteger() {

    return num;
  }

  @Override
  public boolean isDouble() {

    return true;
  }

  @Override
  public String toString() {

    return String.valueOf( getDouble() );
  }

}
