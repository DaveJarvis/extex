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

package org.extex.font.format.vf.command;

import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.font.exception.FontException;
import org.extex.font.format.tfm.TfmFixWord;
import org.extex.framework.i18n.Localizer;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

import java.io.IOException;

/**
 * VfCommand: fnt_def.
 *
 * <p>
 * After the {@code pre} command, the preamble continues with font
 * definitions; every font needed to specify 'actual' characters in later
 * {@code set_char} commands is defined here. The font definitions are
 * exactly the same in VF files as they are in DVI files, except that the scaled
 * size {@code s} is relative and the design size {@code d} is
 * absolute:
 * </p>
 *
 * <pre>
 *    fnt_def1   243  k[1]  c[4]  s[4]  d[4]  a[1]  l[1]  n[a+l]
 * </pre>
 *
 * <p>
 * Define font {@code k}, where 0 &lt;= k &lt; 256.
 * </p>
 *
 * <pre>
 *    fnt_def2   244  k[2]  c[4]  s[4]  d[4]  a[1]  l[1]  n[a+l]
 * </pre>
 *
 * <p>
 * Define font {@code k}, where 0 &lt;= k &lt; 65536.
 * </p>
 *
 * <pre>
 *    fnt_def3   245  k[3]  c[4]  s[4]  d[4]  a[1]  l[1]  n[a+l]
 * </pre>
 *
 * <p>
 * Define font {@code k}, where 0 &lt;= k &lt; 2^24
 * </p>
 * .
 *
 * <pre>
 *    fnt_def4   246  k[4]  c[4]  s[4]  d[4]  a[1]  l[1]  n[a+l]
 * </pre>
 *
 * <p>
 * Define font {@code k}, where -2^31 &lt;= k &lt;= 2^31.
 * </p>
 * <p>
 * These font numbers {@code k} are 'local'; they have no relation to font
 * numbers defined in the DVI file that uses this virtual font. The dimension
 * {@code s}, which represents the scaled size of the local font being
 * defined, is a {@code fix_word} relative to the design size of the
 * virtual font. Thus if the local font is to be used at the same size as the
 * design size of the virtual font itself, {@code s} will be the integer
 * value 2^20. The value of {@code s} must be positive and less than 2^24
 * (thus less than 16 when considered as a {@code fix_word}). The dimension
 * {@code d} is a {@code fix_word} in units of printer's points; hence
 * it is identical to the design size found in the corresponding TFM file.
 * </p>
 *
 * <p>
 * The four-byte value {@code c} is the check sum.
 * <p>
 * Parameter {@code s} contains a fixed-point scale factor that is applied
 * to the character widths in font {@code k}; font dimensions in TFM files
 * and other font files are relative to this quantity, which is always positive
 * and less than 227. It is given in the same units as the other dimensions of
 * the DVI file.
 * <p>
 * Parameter {@code d} is similar to {@code s}; it is the design size,
 * and (like {@code s}) it is given in DVI units. Thus, font {@code k}
 * is to be used at mag {@code s}/1000d times its normal size.
 * <p>
 * The remaining part of a font definition gives the external name of the font,
 * which is an ASCII string of length {@code a} + {@code l}. The
 * number a is the length of the area or directory, and {@code l} is the
 * length of the font name itself; the standard local system font area is
 * supposed to be used when a = 0. The n field contains the area in its first a
 * bytes.
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class VfCommandFontDef extends VfCommand {

  /**
   * the checksum
   */
  private final int checksum;

  /**
   * the designsize
   */
  private final TfmFixWord designsize;

  /**
   * the font name
   */
  private final String fontname;

  /**
   * the font numbers (local number)
   */
  private final int fontnumbers;

  /**
   * the scalefactor
   */
  private final TfmFixWord scalefactor;

  /**
   * Create e new object.
   *
   * @param localizer The localizer for the messages.
   * @param rar       the input.
   * @param ccode     the command code.
   * @throws IOException   if a IO-error occurred.
   * @throws FontException if a error reading the font.
   */
  public VfCommandFontDef( Localizer localizer, RandomAccessR rar, int ccode )
      throws IOException,
      FontException {

    super( localizer, ccode );

    switch( ccode ) {
      case FNT_DEF_1:
        // 8 bit
        fontnumbers = rar.readByteAsInt();
        break;
      case FNT_DEF_2:
        // 16 bit
        fontnumbers = rar.readShort();
        break;
      case FNT_DEF_3:
        // 24 bit
        fontnumbers = rar.readInt24();
        break;
      case FNT_DEF_4:
        // 32 bit
        fontnumbers = rar.readInt();
        break;
      default:
        throw new FontException( getLocalizer().format( "VF.WrongCode",
                                                        String.valueOf( ccode ) ) );
    }

    checksum = rar.readInt();
    scalefactor =
        new TfmFixWord( rar.readInt(), TfmFixWord.FIXWORDDENOMINATOR );
    designsize =
        new TfmFixWord( rar.readInt(), TfmFixWord.FIXWORDDENOMINATOR );

    fontname = readFontName( rar );

  }

  /**
   * Getter for checksum.
   *
   * @return the checksum
   */
  public int getChecksum() {

    return checksum;
  }

  /**
   * Getter for designsize.
   *
   * @return the designsize
   */
  public TfmFixWord getDesignsize() {

    return designsize;
  }

  /**
   * Returns the designsize as {@link Dimen}.
   *
   * @return Returns the designsize as {@link Dimen}.
   */
  public FixedDimen getDesignsizeAsDimen() {

    return new Dimen( designsize.getValue() >> 4 );
  }

  /**
   * Getter for fontname.
   *
   * @return the fontname
   */
  public String getFontname() {

    return fontname;
  }

  /**
   * Getter for fontnumbers.
   *
   * @return the fontnumbers
   */
  public int getFontnumbers() {

    return fontnumbers;
  }

  /**
   * Getter for scalefactor.
   *
   * @return the scalefactor
   */
  public TfmFixWord getScalefactor() {

    return scalefactor;
  }

  /**
   * Return the scalefactor as {@link Count}.
   *
   * @return Return the scalefactor as {@link Count}.
   */
  public FixedCount getScalefactorAsCount() {

    return new Count( scalefactor.getValue() >> 4 );
  }

  /**
   * Reads a character string from the header.
   *
   * @param rar the input
   * @return the string
   * @throws IOException if an I/O error occured
   */
  private String readFontName( final RandomAccessR rar ) throws IOException {

    int a = rar.readByteAsInt();
    int l = rar.readByteAsInt();

    int len = a + l;
    StringBuilder buf = new StringBuilder( len );
    for( int i = 0; i < len; i++ ) {
      buf.append( (char) rar.readByteAsInt() );
    }
    return buf.toString();
  }

  @Override
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( "fontdef" );
    writer.writeAttribute( "opcode", getCommandCode() );
    writer.writeAttribute( "fontnumbers", fontnumbers );
    writer.writeAttribute( "fontname", fontname );
    writer.writeAttribute( "checksum", checksum );
    writer.writeAttribute( "scalefactor", scalefactor );
    writer.writeAttribute( "designsize", designsize );
    writer.writeEndElement();
  }

  // /**
  // * @see de.dante.extex.font.type.PlFormat#toPL(
  // * de.dante.extex.font.type.PlWriter)
  // */
  // public void toPL(final PlWriter out) throws IOException, FontException {

  // out.plopen("MAPFONT").addDec(getFontnumbers());
  // out.plopen("FONTNAME").addStr(getFontname()).plclose();
  // out.plopen("FONTCHECKSUM").addOct(getChecksum()).plclose();
  // out.plopen("FONTAT").addReal(getScalefactor()).plclose();
  // out.plopen("FONTDSIZE").addReal(getDesignsize()).plclose();
  // out.plclose();
  // }
}
