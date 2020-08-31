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

package org.extex.font.format.pfb;

import org.extex.font.exception.FontException;
import org.extex.util.file.random.RandomAccessInputArray;
import org.extex.util.file.random.RandomAccessInputFile;
import org.extex.util.file.random.RandomAccessInputStream;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parser for a pfb-file.
 *
 * <p>
 * Adobe Type 1 BaseFont Format
 * </p>
 * <p>
 * see 2.2 BaseFont Dictionary
 * </p>
 * <p>
 * Type 1 BaseFont Program [ASCII - eexec encryption (Binary only) - ASCII]
 * <p>
 * TODO mgn: use {@link RandomAccessR} and put indexOf in it
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class PfbParser implements XMLWriterConvertible, Serializable {

  /**
   * the ascii marker.
   */
  private static final int ASCII_MARKER = 0x01;

  /**
   * the binary marker.
   */
  private static final int BINARY_MARKER = 0x02;

  /**
   * C1.
   */
  public final static int C1 = 52845;

  /**
   * C2.
   */
  public final static int C2 = 22719;

  /**
   * In charstring encryption, the initial key for the variable R is 4330
   * (decimal)
   */
  public static final int CHARSTRING_R = 4330;

  /**
   * the dup string.
   */
  private static final String DUP = "dup ";

  /**
   * In eexec encryption, the initial key for the variable R is 55665
   * (decimal).
   */
  public static final int EEXEC_R = 55665;

  /**
   * the encoding string.
   */
  private static final String ENCODING = "/Encoding ";

  /**
   * How many hex values per line?
   */
  private static final int HEX_PER_LINE = 30;

  /**
   * the pdf header length. (start-marker (1 byte), ascii-/binary-marker (1
   * byte), size (4 byte)) 3*6 == 18
   */
  private static final int PFB_HEADER_LENGTH = 18;

  /**
   * The record types in the pfb-file.
   */
  private static final int[] PFB_RECORDS = {ASCII_MARKER, BINARY_MARKER,
      ASCII_MARKER};

  /**
   * the put string.
   */
  private static final String PUT = "put";

  // sample (pfb-file)
  // 00000000 80 01 8b 15 00 00 25 21 50 53 2d 41 64 6f 62 65

  /**
   * The field {@code serialVersionUID}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * the start marker.
   */
  private static final int START_MARKER = 0x80;

  /**
   * To Decrypt a Sequence of Ciphertext Bytes to Produce the Original
   * Sequence of Plaintext Bytes.
   * <p>
   * 1. Initialize an unsigned 16-bit integer variable R to the encryption key
   * (the same key as used to encrypt).
   * <p>
   * 2. For each 8-bit byte, C, of ciphertext the following steps are
   * executed:
   * <p>
   * a. Assign the high order 8 bits of R to a temporary variable, T.
   * <p>
   * b. Exclusive-OR C with T, producing a plaintext byte, P.
   * <p>
   * c. Compute the next value of R by the formula ((C + R) * c1 + c2) mod
   * 65536, where c1 and c2 are the same constants that were used to encrypt.
   * <p>
   * 3. Discard the first n bytes of plaintext; these are the random bytes
   * added during encryption. The remainder of the plaintext bytes are the
   * original sequence.
   *
   * @param code The code to decrypt.
   * @param r    The initial key r.
   * @param n    The number of random bytes.
   * @return Returns the decrypt data.
   */
  private static byte[] decrypt( byte[] code, int r, int n ) {

    ByteArrayOutputStream data = new ByteArrayOutputStream( code.length );
    int rr = r;
    for( byte xx : code ) {
      int x = xx & 0xff;
      data.write( x ^ (rr >> 8) );
      rr = ((x + rr) * C1 + C2) & 0xffff;
    }
    return data.toByteArray();
  }

  /**
   * Found the end marker.
   */
  private boolean endfound = false;

  /**
   * The list of all glyph names.
   */
  private String[] glyphnames = null;

  /**
   * the lengths of the records.
   */
  private int[] lengths;

  /**
   * the parsed pfb-data.
   */
  private byte[] pfbdata;

  /**
   * The complete font data.
   */
  private byte[] fontdata;

  /**
   * The pos counter.
   */
  private int pos;

  /**
   * the starts of the records.
   */
  private int[] starts;

  /**
   * Create a new object.
   *
   * @param data the data array.
   * @throws FontException if an font error occurs.
   */
  public PfbParser( byte[] data ) throws FontException {

    try {
      parsePfb( new RandomAccessInputArray( data ) );
    } catch( IOException e ) {
      throw new FontException( e.getMessage() );
    }
  }

  /**
   * Create a new object.
   *
   * @param file The input file.
   * @throws FontException if an IO-error occurs.
   */
  public PfbParser( File file ) throws FontException {

    try {
      parsePfb( new RandomAccessInputFile( file ) );
    } catch( IOException e ) {
      throw new FontException( e.getMessage() );
    }
  }

  /**
   * Create a new object.
   *
   * @param in The input.
   * @throws FontException if an IO-error occurs.
   */
  public PfbParser( InputStream in ) throws FontException {

    try {
      parsePfb( new RandomAccessInputStream( in ) );
    } catch( IOException e ) {
      throw new FontException( e.getMessage() );
    }
  }

  /**
   * Create a new object.
   *
   * @param filename the file name
   * @throws FontException if an font error occurs.
   */
  public PfbParser( String filename ) throws FontException {

    try {
      parsePfb( new RandomAccessInputFile( filename ) );
    } catch( IOException e ) {
      throw new FontException( e.getMessage() );
    }
  }

  /**
   * Returns all the glyph names in the font, or {@code null}, if not
   * available.
   *
   * @return Returns all the glyph names in the font.
   */
  public String[] getAllGylyphNames() {

    if( glyphnames == null ) {
      byte[] code = getPart( 1 );
      byte[] datafull = decrypt( code, EEXEC_R, 4 );
      int leniv = readLenIV( datafull );
      byte[] data = new byte[ datafull.length - leniv ];
      System.arraycopy( datafull, leniv, data, 0, datafull.length - leniv );

      // '/CharString'
      // 2 index /CharStrings 2291 dict dup begin
      // /.notdef 95 RD xxxx ND
      // /exclam 40 RD xxxx ND
      // ...
      // end

      pos = indexOf( "/CharStrings".getBytes(), data );
      if( pos >= 0 ) {
        List<String> list = new ArrayList<String>( 256 );
        pos = indexOf( "begin".getBytes(), data, pos );
        if( pos >= 0 ) {
          pos += 5; // begin
          String name;
          while( (name = readGlygp( data )) != null && !endfound ) {
            list.add( name );
          }
        }
        if( list.size() > 0 ) {
          glyphnames = new String[ list.size() ];
          for( int i = 0, n = list.size(); i < n; i++ ) {
            glyphnames[ i ] = list.get( i ).replaceAll( "/", "" );
          }
        }
      }
    }

    return glyphnames;
  }

  /**
   * Returns the encoding (id - glyphanme).
   *
   * @return Returns the encoding (id - glyphanme).
   */
  public String[] getEncoding() {

    String s = new String( getPart( 0 ) );

    // read '/Encoding 256 array'
    int pos = s.indexOf( ENCODING );
    int size = 0;
    if( pos >= 0 ) {
      pos += ENCODING.length();
      StringBuilder b = new StringBuilder();
      char c;
      do {
        c = s.charAt( pos++ );
        b.append( c );
      } while( c != ' ' );
      try {
        size = Integer.parseInt( b.toString().trim() );
      } catch( Exception e ) {
        // use zero
        size = 0;
      }
    }
    String[] enc = new String[ size ];
    Arrays.fill( enc, ".notdef" );

    // search 'dup 32/space put'
    while( pos < s.length() ) {
      int start = -1;
      pos = s.indexOf( DUP, pos );
      if( pos >= 0 ) {
        pos += DUP.length();
        start = pos;
      }
      int stop = -1;
      pos = s.indexOf( PUT, pos );
      if( pos >= 0 ) {
        stop = pos;
        pos += PUT.length();
      }
      if( start >= 0 && stop >= 0 ) {
        insert( enc, s.substring( start, stop ) );
      }
      else {
        break;
      }
    }

    return enc;
  }

  /**
   * Returns the pfb data as stream.
   *
   * @return Returns the pfb data as stream.
   */
  public InputStream getInputStream() {

    return new ByteArrayInputStream( pfbdata );
  }

  // /**
  // * To Encrypt a Sequence of Plaintext Bytes to Produce a Sequence of
  // * Ciphertext Bytes.
  // *
  // * 1. Generate n random bytes to be additional plaintext bytes at the
  // * beginning of the sequence of plaintext bytes, for some value of n.
  // *
  // * 2. Initialize an unsigned 16-bit integer variable R to the encryption
  // * key.
  // *
  // * 3. For each 8-bit byte, P, of plaintext (beginning with the newly added
  // * random bytes) execute the following steps:
  // *
  // * a. Assign the high order 8 bits of R to a temporary variable, T.
  // *
  // * b. Exclusive-OR P with T, producing a ciphertext byte, C.
  // *
  // * c. Compute the next value of R by the formula ((C + R) * c1 + c2) mod
  // * 65536, where c1 is 52845 (decimal) and c2 is 22719 (decimal).
  // *
  // * @param data The data.
  // * @param r The initial key r.
  // * @param random The random values.
  // * @return Returns the encrypt data.
  // */
  // private byte[] encrypt(byte[] data, int r, byte[] random) {

  // ByteArrayOutputStream code =
  // new ByteArrayOutputStream(random.length + data.length);
  // int rr = r;
  // byte d[] = new byte[data.length + random.length];
  // System.arraycopy(random, 0, d, 0, random.length);
  // System.arraycopy(data, 0, d, random.length, data.length);

  // for (byte xx : d) {
  // int x = xx & 0xff;
  // int cipher = x ^ (rr >> 8);
  // code.write(cipher);
  // rr = ((cipher + rr) * C1 + C2) & 0xffff;
  // }
  // return code.toByteArray();
  // }

  /**
   * Returns the lengths.
   *
   * @return Returns the lengths.
   */
  public int[] getLengths() {

    return lengths;
  }

  /**
   * Returns the part of the array.
   *
   * @param idx the part index.
   * @return Returns the part of the array
   */
  public byte[] getPart( int idx ) {

    byte[] tmp = new byte[ lengths[ idx ] ];
    System.arraycopy( pfbdata, starts[ idx ], tmp, 0, lengths[ idx ] );
    return tmp;
  }

  /**
   * Returns the pfbdata (complete).
   *
   * @return Returns the pfbdata (complete).
   */
  public byte[] getPfbdata() {

    return fontdata;
  }

  /**
   * Returns the index of the first occurrence of the specified bytes. if not
   * found, {@code -1} is return.
   *
   * @param data any data to search.
   * @param src  the source data.
   * @return Returns the index of the first occurrence of the specified bytes.
   * if not found, {@code -1} is return.
   */
  private int indexOf( byte[] data, byte[] src ) {

    return indexOf( data, src, 0 );
  }

  /**
   * Returns the index of the first occurrence of the specified bytes. if not
   * found, {@code -1} is return.
   *
   * @param data any data.
   * @return Returns the index of the first occurrence of the specified bytes.
   * if not found, {@code -1} is return.
   */
  private int indexOf( byte[] data, byte[] src, int fromIndex ) {

    if( src == null || fromIndex >= src.length || fromIndex < 0 ) {
      return -1;
    }

    byte first = data[ 0 ];
    int max = src.length - data.length;

    for( int i = fromIndex; i <= max; i++ ) {

      // look for first byte
      if( src[ i ] != first ) {
        while( ++i <= max && src[ i ] != first ) {
        }
      }

      // found first byte, now look at the rest
      if( i <= max ) {
        int j = i + 1;
        int end = j + data.length - 1;
        for( int k = 1; j < end && src[ j ] == data[ k ]; j++, k++ ) {
        }

        if( j == end ) {
          // found
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * Insert the values 'dup 32/space put' into the string array.
   *
   * @param enc the string array
   * @param s   the string
   */
  private void insert( String[] enc, String s ) {

    String[] split = s.split( "/" );
    try {
      int nr = Integer.parseInt( split[ 0 ].trim() );
      enc[ nr ] = split[ 1 ].trim();
    } catch( Exception e ) {
      // ignore
    }
  }

  /**
   * Parse the pfb-array.
   *
   * @param rar The input.
   * @throws IOException   in an IO-error occurs.
   * @throws FontException if a font error occurs.
   */
  private void parsePfb( RandomAccessR rar ) throws IOException, FontException {

    fontdata = rar.getData();
    pfbdata = new byte[ (int) (rar.length() - PFB_HEADER_LENGTH) ];
    lengths = new int[ PFB_RECORDS.length ];
    starts = new int[ PFB_RECORDS.length ];
    int pointer = 0;
    for( int records = 0; records < PFB_RECORDS.length; records++ ) {

      if( rar.readByteAsInt() != START_MARKER ) {
        throw new PfbStartMarkerMissingException();
      }

      if( rar.readByteAsInt() != PFB_RECORDS[ records ] ) {
        throw new PfbIncorrectRecordTypeException();
      }

      starts[ records ] = pointer;
      int size = rar.readByteAsInt();
      size += rar.readByteAsInt() << 8;
      size += rar.readByteAsInt() << 16;
      size += rar.readByteAsInt() << 24;
      lengths[ records ] = size;
      rar.readFully( pfbdata, pointer, size );
      pointer += size;
    }
  }

  /**
   * Read the glyph name.
   *
   * @param data The data.
   * @return Returns the glyph name or {@code null}.
   */
  private String readGlygp( byte[] data ) {

    while( pos < data.length ) {
      // search '/' or 'end'
      char ch = (char) (data[ pos ] & 0xff);
      if( ch == '/' ) {
        // read until ' '
        StringBuilder buf = new StringBuilder();
        while( (ch = (char) (data[ ++pos ] & 0xff)) != ' ' ) {
          buf.append( ch );
        }
        // read until 'ND'
        pos = indexOf( "ND".getBytes(), data, pos );
        if( buf.length() > 0 ) {
          return buf.toString();
        }
        return null;
      }
      if( ch == 'e' ) {
        if( pos + 2 < data.length ) {
          char ch2 = (char) (data[ pos + 1 ] & 0xff);
          char ch3 = (char) (data[ pos + 2 ] & 0xff);
          if( ch2 == 'n' && ch3 == 'd' ) {
            endfound = true;
            return null;
          }
        }
      }
      pos++;
    }
    endfound = true;
    return null;
  }

  /**
   * Read the lenIV entry. If not found, the default is 4.
   *
   * @param data The data.
   * @return Returns the lenIV value.
   */
  private int readLenIV( byte[] data ) {

    String text = new String( data, StandardCharsets.US_ASCII );
    int pos = text.indexOf( "/lenIV" );
    if( pos >= 0 ) {
      String rest = text.substring( pos + 6, 10 ).trim();
      StringBuilder ii = new StringBuilder();
      for( int i = 0; i < 10; i++ ) {
        char ch = rest.charAt( i );
        if( ch == ' ' ) {
          break;
        }
        ii.append( ch );
      }
      if( ii.length() > 0 ) {
        try {
          int n = Integer.parseInt( ii.toString() );
          return n;
        } catch( NumberFormatException e ) {
          // ignore it
        }
      }
    }
    return 4;
  }

  /**
   * Returns the size of the pfb-data.
   *
   * @return Returns the size of the pfb-data.
   */
  public int size() {

    return pfbdata.length;
  }

  /**
   * Write the data as <b>pfa</b> to the output stream.
   *
   * @param out The output.
   * @throws IOException if an IO-error occurs.
   */
  public void toPfa( OutputStream out ) throws IOException {

    // write part 1 - idx 0 ASCII
    byte[] tmp = getPart( 0 );
    out.write( tmp );

    // write part 2 - idx 1 BINARY
    tmp = getPart( 1 );
    for( int i = 0; i < tmp.length; i++ ) {
      String hex = "00" + Integer.toHexString( tmp[ i ] );
      out.write( hex.charAt( hex.length() - 2 ) );
      out.write( hex.charAt( hex.length() - 1 ) );
      if( (i + 1) % HEX_PER_LINE == 0 ) {
        out.write( '\n' );
      }
    }
    out.write( '\n' );

    // write part 3 - idx 2 ASCII
    tmp = getPart( 2 );
    out.write( tmp );

  }

  /**
   * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
   */
  @Override
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( "pfb" );
    writer.writeStartElement( "encoding" );
    String[] enc = getEncoding();
    for( int i = 0; i < enc.length; i++ ) {
      writer.writeStartElement( "enc" );
      writer.writeAttribute( "id", String.valueOf( i ) );
      writer.writeAttribute( "name", enc[ i ] == null ? "" : enc[ i ] );
      writer.writeEndElement();
    }
    writer.writeEndElement();
    writer.writeStartElement( "glyphs" );
    String[] g = getAllGylyphNames();
    for( int i = 0; i < g.length; i++ ) {
      writer.writeStartElement( "g" );
      writer.writeAttribute( "id", String.valueOf( i ) );
      writer.writeAttribute( "name", g[ i ] );
      writer.writeEndElement();
    }
    writer.writeEndElement();

    for( int i = 0; i < lengths.length; i++ ) {
      writer.writeStartElement( "part" );
      writer.writeAttribute( "number", String.valueOf( i ) );
      // write binary as Base64 data in a CDATA
      if( PFB_RECORDS[ i ] == ASCII_MARKER ) {
        writer.writeCDATA( getPart( i ) );
      }
      else {
        // writer.writeCDATA(Base64.encode(getPart(i)));
      }
      writer.writeEndElement();
    }
    writer.writeEndElement();
  }

}
