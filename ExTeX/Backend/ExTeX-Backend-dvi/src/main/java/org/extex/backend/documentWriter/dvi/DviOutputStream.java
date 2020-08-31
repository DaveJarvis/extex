/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

// created: 2004-07-29

package org.extex.backend.documentWriter.dvi;

import org.extex.core.exception.GeneralException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class provides the methods to write to the dvi-Stream.
 *
 * @author <a href="mailto:sebastian.waschik@gmx.de">Sebastian Waschik</a>
 */
public class DviOutputStream {

  /**
   * Number of bits in a byte.
   */
  private static final int BITS_PER_BYTE = 8;

  /**
   * Maximum value of a byte.
   */
  private static final int BYTE_MAX_NUM = 255;

  /**
   * Highest Bit in a byte.
   */
  private static final int HIGH_BIT_IN_BYTE = 128;

  /**
   * Value with all bits of a byte set.
   */
  private static final int BYTE_BITMASK = 255;

  /**
   * The OutputStream for output of this class.
   */
  private OutputStream outputStream = null;

  /**
   * Current position in stream (number of written bytes).
   */
  private int streamPosition = 0;

  /**
   * ...
   *
   * @throws GeneralException ...
   * @throws IOException      ...
   */
  public void close() throws GeneralException, IOException {

    outputStream.close();
    outputStream = null;
  }

  /**
   * Get the StreamPosition value.
   *
   * @return the StreamPosition value.
   */
  public int getStreamPosition() {

    return streamPosition;
  }

  /**
   * Creates a new  instance.
   *
   * @param theOutputStream {@code OutputStream} for dvi file
   */
  public DviOutputStream( OutputStream theOutputStream ) {

    outputStream = theOutputStream;
  }

  /**
   * Writes a single byte to the output stream.
   *
   * @param theByte the output
   * @throws GeneralException if an error occurs
   */
  public void writeByte( int theByte ) throws GeneralException {

    try {
      outputStream.write( theByte );
      streamPosition++;
    } catch( IOException e ) {
      throw new GeneralException( e );
    }
  }

  /**
   * Write a number to the output stream.
   *
   * @param number the number
   * @param bytes  the number of bytes for the number
   * @throws GeneralException if an error occurs
   */
  public void writeNumber( int number, int bytes )
      throws GeneralException {

    int shift = (bytes - 1) * BITS_PER_BYTE;
    int mask = BYTE_BITMASK << BITS_PER_BYTE * (bytes - 1);

    while( shift >= 0 ) {
      writeByte( (number & mask) >> shift );

      shift -= BITS_PER_BYTE;
      mask >>>= BITS_PER_BYTE;
    }

    // TODO: exception, if the number is not written completely (TE)
  }

  /**
   * Write a string to the output stream.
   *
   * @param string the string
   * @throws GeneralException if an error occurs
   */
  public void writeString( String string ) throws GeneralException {

    for( int i = 0; i < string.length(); i++ ) {
      writeByte( string.charAt( i ) );
    }
  }

  /**
   * Write a string to the output stream.  Before the string the
   * number of characters is written.
   *
   * @param string the string
   * @throws GeneralException if an error occurs
   */
  public void writeStringAndSize( String string ) throws GeneralException {

    int length = string.length();

    if( length > BYTE_MAX_NUM ) {
      throw new GeneralException( "string to write is to long" );
    }

    writeByte( length );
    writeString( string );
  }

  /**
   * Write a value and the dvi code to the output stream.  The code
   * depends on the number.
   *
   * @param codes     codes for one, two, ... long argument
   * @param argNumber the number for writing
   * @throws GeneralException if an error occurs
   * @see "TTP [610]"
   */
  public void writeCodeNumberAndArg( int[] codes, int argNumber )
      throws GeneralException {

    int numberBytes = 0;
    int number = Math.abs( argNumber );

    // TODO: this is a bit quick&dirty (TE)
    // TODO: TTP [604] (TE)
    while( number > BYTE_MAX_NUM ) {
      number >>>= BITS_PER_BYTE;
      numberBytes++;
    }

    if( (argNumber >= 0) && ((number & HIGH_BIT_IN_BYTE) != 0) ) {
      numberBytes += 2;
    }
    else {
      numberBytes += 1;
    }

    writeByte( codes[ numberBytes - 1 ] );
    writeNumber( argNumber, numberBytes );
  }

  /**
   * Write a command with an number as argument.
   *
   * @param shortCodes codes for numbers including the argument
   * @param codes      codes for longer arguments
   * @param argNumber  the argument
   * @throws GeneralException if an error occurs
   */
  public void writeCodeNumberAndArg( int[] shortCodes,
                                     int[] codes, int argNumber )
      throws GeneralException {

    if( argNumber < shortCodes.length ) {
      writeByte( shortCodes[ argNumber ] );
    }
    else {
      writeCodeNumberAndArg( codes, argNumber );
    }

  }
}