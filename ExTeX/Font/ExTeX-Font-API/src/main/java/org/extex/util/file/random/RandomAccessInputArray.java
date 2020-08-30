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

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * RandomAccess for a Array
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class RandomAccessInputArray implements RandomAccessR {

  /**
   * and long
   */
  private static final long ANDLONG = 0xFFFFFFFFL;

  /**
   * Shift 16
   */
  private static final int SHIFT16 = 16;

  /**
   * Shift 24
   */
  private static final int SHIFT24 = 24;

  /**
   * Shift 32
   */
  private static final int SHIFT32 = 32;

  /**
   * Shift 8
   */
  private static final int SHIFT8 = 8;

  /**
   * 0xff
   */
  public static final int XFF = 0xff;

  /**
   * Buffer
   */
  private byte[] buffer;

  /**
   * pointer
   */
  private int pointer = 0;

  /**
   * Create a new object
   *
   * @param array the array
   */
  public RandomAccessInputArray( byte[] array ) {

    buffer = array;
    pointer = 0;
  }

  @Override
  public void close() throws IOException {

    buffer = null;
    pointer = -1;
  }

  @Override
  public byte[] getData() {

    return buffer;
  }

  @Override
  public long getPointer() {

    return pointer;
  }

  @Override
  public boolean isEOF() throws IOException {

    return (getPointer() >= length());
  }

  @Override
  public long length() throws IOException {

    if( buffer != null ) {
      return buffer.length;
    }
    return -1;
  }

  /**
   * Reads a byte of data from this file. The byte is returned as an integer
   * in the range 0 to 255 ({@code 0x00-0xff}).
   *
   * @return the next byte of data, or {@code -1} if the end of the file
   * has been reached.
   */
  public int read() {

    if( pointer < buffer.length ) {
      return (buffer[ pointer++ ] & XFF);
    }
    return -1;
  }

  @Override
  public boolean readBoolean() throws IOException {

    int ch = this.read();
    if( ch < 0 ) {
      throw new EOFException();
    }
    return (ch != 0);
  }

  @Override
  public byte readByte() throws IOException {

    int ch = this.read();
    if( ch < 0 ) {
      throw new EOFException();
    }
    return (byte) (ch);
  }

  @Override
  public int readByteAsInt() throws IOException {

    int ch = this.read();
    if( ch < 0 ) {
      throw new EOFException();
    }
    return ch;
  }

  /**
   * Reads a sub array as a sequence of bytes.
   *
   * @param b   the data to be written
   * @param off the start offset in the data
   * @param len the number of bytes that are written
   */
  private void readBytes( byte[] b, int off, int len ) {

    for( int i = 0; i < len; i++ ) {
      b[ off + i ] = buffer[ pointer++ ];
      if( pointer >= buffer.length ) {
        throw new IndexOutOfBoundsException();
      }
    }
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
  @Override
  public char readChar() throws IOException {

    int ch1 = this.read();
    int ch2 = this.read();
    if( (ch1 | ch2) < 0 ) {
      throw new EOFException();
    }
    return (char) ((ch1 << SHIFT8) + (ch2 << 0));
  }

  /**
   * Reads a {@code double} from the input.
   */
  @Override
  public double readDouble() throws IOException {

    return Double.longBitsToDouble( readLong() );
  }

  /**
   * Reads a {@code float} from the input.
   */
  @Override
  public float readFloat() throws IOException {

    return Float.intBitsToFloat( readInt() );
  }

  @Override
  public void readFully( byte[] b ) throws IOException {

    readFully( b, 0, b.length );
  }

  @Override
  public void readFully( byte[] b, int off, int len ) throws IOException {

    try {
      readBytes( b, off, len );
    } catch( Exception e ) {
      throw new EOFException();
    }
  }

  /**
   * Reads a signed 32-bit integer from the input.
   * <p>
   * If the bytes read, in order, are {@code b1}, {@code b2},
   * {@code b3}, and {@code b4}, where
   * {@code 0&nbsp;&lt;=&nbsp;b1, b2, b3, b4&nbsp;&lt;=&nbsp;255}, then
   * the result is equal to:
   * </p>
   *
   * <pre>
   *     (b1 &lt;&lt; 24) | (b2 &lt;&lt; 16) + (b3 &lt;&lt; 8) + b4
   * </pre>
   */
  @Override
  public int readInt() throws IOException {

    int ch1 = this.read();
    int ch2 = this.read();
    int ch3 = this.read();
    int ch4 = this.read();
    if( (ch1 | ch2 | ch3 | ch4) < 0 ) {
      throw new EOFException();
    }
    return ((ch1 << SHIFT24) + (ch2 << SHIFT16) + (ch3 << SHIFT8) + (ch4 << 0));
  }

  @Override
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
   * If the bytes read, in order, are {@code b1}, {@code b2}, and
   * {@code b3}, where
   * {@code 0&nbsp;&lt;=&nbsp;b1, b2, b3&nbsp;&lt;=&nbsp;255}, then the
   * result is equal to:
   * </p>
   *
   * <pre>
   *     (b1 &lt;&lt; 16) + (b2 &lt;&lt; 8) + b3
   * </pre>
   */
  @Override
  public int readInt24() throws IOException {

    int ch1 = this.read();
    int ch2 = this.read();
    int ch3 = this.read();
    if( (ch1 | ch2 | ch3) < 0 ) {
      throw new EOFException();
    }
    return ((ch1 << SHIFT16) + (ch2 << SHIFT8) + (ch3 << 0));
  }

  @Override
  public int readInt8() throws IOException {

    return this.read();
  }

  /**
   * Reads the next line of text from the input.
   */
  @Override
  public String readLine() throws IOException {

    StringBuilder input = new StringBuilder();
    int c = -1;
    boolean eol = false;

    while( !eol ) {
      c = read();
      switch( c ) {
        case -1:
        case '\n':
          eol = true;
          break;
        case '\r':
          eol = true;
          long cur = pointer;
          if( (read()) != '\n' ) {
            seek( cur );
          }
          break;
        default:
          input.append( (char) c );
          break;
      }
    }

    if( (c == -1) && (input.length() == 0) ) {
      return null;
    }
    return input.toString();
  }

  /**
   * Reads a signed 64-bit integer from the input.
   * <p>
   * If the bytes read, in order, are {@code b1}, {@code b2},
   * {@code b3}, {@code b4}, {@code b5}, {@code b6},
   * {@code b7}, and {@code b8,} where:
   * </p>
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
  @Override
  public long readLong() throws IOException {

    return ((long) (readInt()) << SHIFT32) + (readInt() & ANDLONG);
  }

  /**
   * Reads a signed 16-bit number from the input.
   * <p>
   * If the two bytes read, in order, are {@code b1} and {@code b2},
   * where each of the two values is between {@code 0} and
   * {@code 255}, inclusive, then the result is equal to:
   * </p>
   *
   * <pre>
   *     (short)((b1 &lt;&lt; 8) | b2)
   * </pre>
   */
  @Override
  public short readShort() throws IOException {

    int ch1 = this.read();
    int ch2 = this.read();
    if( (ch1 | ch2) < 0 ) {
      throw new EOFException();
    }
    return (short) ((ch1 << SHIFT8) + (ch2 << 0));
  }

  @Override
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
  @Override
  public int readUnsignedByte() throws IOException {

    int ch = this.read();
    if( ch < 0 ) {
      throw new EOFException();
    }
    return ch;
  }

  /**
   * Reads an unsigned 16-bit number from the input.
   * <p>
   * If the bytes read, in order, are {@code b1} and {@code b2},
   * where {@code 0&nbsp;&lt;=&nbsp;b1, b2&nbsp;&lt;=&nbsp;255}, then the
   * result is equal to:
   * </p>
   *
   * <pre>
   *     (b1 &lt;&lt; 8) | b2
   * </pre>
   */
  @Override
  public int readUnsignedShort() throws IOException {

    int ch1 = this.read();
    int ch2 = this.read();
    if( (ch1 | ch2) < 0 ) {
      throw new EOFException();
    }
    return (ch1 << SHIFT8) + (ch2 << 0);
  }

  /**
   * Reads in a string from this file. The string has been encoded using a
   * modified UTF-8 format.
   */
  @Override
  public String readUTF() throws IOException {

    return DataInputStream.readUTF( this );
  }

  @Override
  public void seek( long arg0 ) throws IOException {

    if( buffer != null ) {
      if( arg0 < buffer.length && arg0 < Integer.MAX_VALUE ) {
        pointer = (int) arg0;
        return;
      }
    }
    throw new EOFException();

  }

  /**
   * Attempts to skip over {@code n} bytes of input discarding the
   * skipped bytes.
   */
  @Override
  public int skipBytes( int n ) throws IOException {

    long pos;
    long len;
    long newpos;

    if( n <= 0 ) {
      return 0;
    }
    pos = pointer;
    len = length();
    newpos = pos + n;
    if( newpos > len ) {
      newpos = len;
    }
    seek( newpos );

    return (int) (newpos - pos);
  }
}
