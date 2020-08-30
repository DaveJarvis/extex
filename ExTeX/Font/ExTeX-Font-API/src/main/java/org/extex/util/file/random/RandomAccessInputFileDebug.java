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

package org.extex.util.file.random;

import java.io.*;

/**
 * RandomAccess for a File (Input) DEBUG-VERSION
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class RandomAccessInputFileDebug implements RandomAccessR {

  /**
   * Shift 16
   */
  private static final int SHIFT16 = 16;

  /**
   * Shift 8
   */
  private static final int SHIFT8 = 8;

  /**
   * Stream for debug
   */
  private final PrintStream out = System.out;

  /**
   * RandomAccessFile
   */
  private final RandomAccessFile raf;

  /**
   * Create a new object
   *
   * @param file file for reading
   * @throws IOException if an IO-error occured
   */
  public RandomAccessInputFileDebug( File file ) throws IOException {

    raf = new RandomAccessFile( file, "r" );
  }

  /**
   * Create a new object
   *
   * @param filename filename for reading
   * @throws IOException if an IO-error occured
   */
  public RandomAccessInputFileDebug( String filename ) throws IOException {

    this( new File( filename ) );
  }

  public void close() throws IOException {

    raf.close();
  }

  public byte[] getData() throws IOException {

    long p = getPointer();
    seek( 0 );
    byte[] buffer = new byte[ (int) length() ];
    readFully( buffer );
    seek( p );
    return buffer;
  }

  public long getPointer() throws IOException {

    return raf.getFilePointer();
  }

  public boolean isEOF() throws IOException {

    return (getPointer() >= length());
  }

  public long length() throws IOException {

    return raf.length();
  }

  /**
   * Reads a byte of data from this file. The byte is returned as an integer
   * in the range 0 to 255 ({@code 0x00-0x0ff}).
   *
   * @return the next byte of data, or {@code -1} if the end of the
   * file has been reached.
   * @throws IOException if an IO-error occurs.
   */
  public int read() throws IOException {

    int b = raf.read();
    out.print( "(" + b + ":0x" + Integer.toHexString( b ) + ")" );
    return b;
  }

  public boolean readBoolean() throws IOException {

    return raf.readBoolean();
  }

  public byte readByte() throws IOException {

    byte b = raf.readByte();
    out.println( "\nByte: (" + b + ":0x" + Integer.toHexString( b ) + ")" );
    return b;
  }

  public int readByteAsInt() throws IOException {

    int ch = this.read();
    if( ch < 0 ) {
      throw new EOFException();
    }
    return ch;
  }

  /**
   * Reads a Unicode character from the input.
   * <p>
   * If the bytes read, in order, are {@code b1} and {@code b2},
   * where {@code 0&nbsp;&lt;=&nbsp;b1,&nbsp;b2&nbsp;&lt;=&nbsp;255},
   * then the result is equal to:
   *
   * <pre>
   *     (char)((b1 &lt;&lt; 8) | b2)
   * </pre>
   */
  public char readChar() throws IOException {

    return raf.readChar();
  }

  /**
   * Reads a {@code double} from the input.
   */
  public double readDouble() throws IOException {

    return raf.readDouble();
  }

  /**
   * Reads a {@code float} from the input.
   */
  public float readFloat() throws IOException {

    return raf.readFloat();
  }

  public void readFully( byte[] b ) throws IOException {

    raf.readFully( b );
  }

  public void readFully( byte[] b, int off, int len ) throws IOException {

    raf.readFully( b, off, len );
  }

  /**
   * Reads a signed 32-bit integer from the input.
   * <p>
   * If the bytes read, in order, are {@code b1}, {@code b2},
   * {@code b3}, and {@code b4}, where
   * {@code 0&nbsp;&lt;=&nbsp;b1, b2, b3, b4&nbsp;&lt;=&nbsp;255},
   * then the result is equal to:
   *
   * <pre>
   *     (b1 &lt;&lt; 24) | (b2 &lt;&lt; 16) + (b3 &lt;&lt; 8) + b4
   * </pre>
   */
  public int readInt() throws IOException {

    int b = raf.readInt();
    out.println( "\nInt: (" + b + " :0x" + Integer.toHexString( b ) + ") " );
    return b;
  }

  public int readInt16() throws IOException {

    int ch1 = this.read();
    int ch2 = this.read();
    if( (ch1 | ch2) < 0 ) {
      throw new EOFException();
    }
    return ((ch1 << SHIFT8) + (ch2 << 0));
  }

  /**
   * Reads a signed 24-bit integer from the input.
   * <p>
   * If the bytes read, in order, are {@code b1}, {@code b2},
   * and {@code b3}, where
   * {@code 0&nbsp;&lt;=&nbsp;b1, b2, b3&nbsp;&lt;=&nbsp;255}, then
   * the result is equal to:
   *
   * <pre>
   *     (b1 &lt;&lt; 16) + (b2 &lt;&lt; 8) + b3
   * </pre>
   */
  public int readInt24() throws IOException {

    int ch1 = this.read();
    int ch2 = this.read();
    int ch3 = this.read();
    if( (ch1 | ch2 | ch3) < 0 ) {
      throw new EOFException();
    }
    return ((ch1 << SHIFT16) + (ch2 << SHIFT8) + (ch3 << 0));
  }

  public int readInt8() throws IOException {

    return this.read();
  }

  /**
   * Reads the next line of text from the input.
   */
  public String readLine() throws IOException {

    return raf.readLine();
  }

  /**
   * Reads a signed 64-bit integer from the input.
   * <p>
   * If the bytes read, in order, are {@code b1}, {@code b2},
   * {@code b3}, {@code b4}, {@code b5},
   * {@code b6}, {@code b7}, and {@code b8,} where:
   *
   * <pre>
   *     0 &lt;= b1, b2, b3, b4, b5, b6, b7, b8 &lt;=255,
   * </pre>
   *
   * <p>
   * then the result is equal to:
   * </p>
   *
   * <pre>
   *     ((long)b1 &lt;&lt; 56) + ((long)b2 &lt;&lt; 48)
   *     + ((long)b3 &lt;&lt; 40) + ((long)b4 &lt;&lt; 32)
   *     + ((long)b5 &lt;&lt; 24) + ((long)b6 &lt;&lt; 16)
   *     + ((long)b7 &lt;&lt; 8) + b8
   * </pre>
   */
  public long readLong() throws IOException {

    return raf.readLong();
  }

  /**
   * Reads a signed 16-bit number from the input.
   * <p>
   * If the two bytes read, in order, are {@code b1} and
   * {@code b2}, where each of the two values is between
   * {@code 0} and {@code 255}, inclusive, then the result is
   * equal to:
   *
   * <pre>
   *     (short)((b1 &lt;&lt; 8) | b2)
   * </pre>
   */
  public short readShort() throws IOException {

    short b = raf.readShort();
    out.println( "\nShort: (" + b + ":0x" + Integer.toHexString( b ) + ")" );
    return b;
  }

  public int readSignInt24() throws IOException {

    int v = readInt24();
    if( (v & X24) > 0 ) {
      v = -((~(v | KILL32)) + 1);
    }
    return v;
  }

  /**
   * Reads an unsigned eight-bit number from the input.
   */
  public int readUnsignedByte() throws IOException {

    return raf.readUnsignedByte();
  }

  /**
   * Reads an unsigned 16-bit number from the input.
   * <p>
   * If the bytes read, in order, are {@code b1} and {@code b2},
   * where {@code 0&nbsp;&lt;=&nbsp;b1, b2&nbsp;&lt;=&nbsp;255}, then
   * the result is equal to:
   *
   * <pre>
   *     (b1 &lt;&lt; 8) | b2
   * </pre>
   */
  public int readUnsignedShort() throws IOException {

    return raf.readUnsignedShort();
  }

  /**
   * Reads in a string from this file. The string has been encoded using a
   * modified UTF-8 format.
   */
  public String readUTF() throws IOException {

    return raf.readUTF();
  }

  public void seek( long arg0 ) throws IOException {

    raf.seek( arg0 );
  }

  /**
   * Attempts to skip over {@code n} bytes of input discarding the
   * skipped bytes.
   */
  public int skipBytes( int n ) throws IOException {

    return raf.skipBytes( n );
  }

}
