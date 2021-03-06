/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables;

import org.extex.font.format.xtf.XtfReader;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

import java.io.IOException;

/**
 * The 'OS/2' table consists of a set of metrics that are required by OS/2 and
 * Windows. It is not used by the Mac OS.
 *
 *  <table> <caption>TBD</caption> <tbody>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name of Entry</b></td>
 * <td><b>Comments</b></td>
 * </tr>
 * </tbody>
 * <tr>
 * <td>USHORT</td>
 * <td>version</td>
 * <td> table version number (set to 0)</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>xAvgCharWidth;</td>
 * <td> average weighted advance width of lower case letters and space</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>usWeightClass;</td>
 * <td> visual weight (degree of blackness or thickness) of stroke in
 * glyphs</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>usWidthClass;</td>
 * <td> relative change from the normal aspect ratio (width to height ratio) as
 * specified by a font designer for the glyphs in the font</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>fsType;</td>
 * <td> characteristics and properties of this font (set undefined bits to
 * zero)</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>ySubscriptXSize;</td>
 * <td> recommended horizontal size in pixels for subscripts</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>ySubscriptYSize;</td>
 * <td> recommended vertical size in pixels for subscripts</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>ySubscriptXOffset;</td>
 * <td> recommended horizontal offset for subscripts</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>ySubscriptYOffset;</td>
 * <td> recommended vertical offset form the baseline for subscripts</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>ySuperscriptXSize;</td>
 * <td> recommended horizontal size in pixels for superscripts</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>ySuperscriptYSize;</td>
 * <td> recommended vertical size in pixels for superscripts</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>ySuperscriptXOffset;</td>
 * <td> recommended horizontal offset for superscripts</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>ySuperscriptYOffset;</td>
 * <td> recommended vertical offset from the baseline for superscripts</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>yStrikeoutSize;</td>
 * <td> width of the strikeout stroke</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>yStrikeoutPosition;</td>
 * <td> position of the strikeout stroke relative to the baseline</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>sFamilyClass;</td>
 * <td> classification of font-family design.</td>
 * </tr>
 * <tr>
 * <td>PANOSE</td>
 * <td>panose;</td>
 * <td> 10 byte series of number used to describe the visual characteristics of
 * a given typeface</td>
 * </tr>
 * <tr>
 * <td>ULONG</td>
 * <td>ulUnicodeRange1</td>
 * <td> Field is split into two bit fields of 96 and 36 bits each. The low 96
 * bits are used to specify the Unicode blocks encompassed by the font file. The
 * high 32 bits are used to specify the character or script sets covered by the
 * font file. Bit assignments are pending. Set to 0<br> Bits 0&ndash;31</td>
 * </tr>
 * <tr>
 * <td>ULONG</td>
 * <td>ulUnicodeRange2</td>
 * <td>Bits 32&ndash;63</td>
 * </tr>
 * <tr>
 * <td>ULONG</td>
 * <td>ulUnicodeRange3</td>
 * <td>Bits 64&ndash;95</td>
 * </tr>
 * <tr>
 * <td>ULONG</td>
 * <td>ulUnicodeRange4</td>
 * <td>Bits 96&ndash;127</td>
 * </tr>
 * <tr>
 * <td>CHAR</td>
 * <td>achVendID[4];</td>
 * <td> four character identifier for the font vendor</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>fsSelection;</td>
 * <td> 2-byte bit field containing information concerning the nature of the
 * font patterns</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>usFirstCharIndex</td>
 * <td> The minimum Unicode index in this font.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>usLastCharIndex</td>
 * <td> The maximum Unicode index in this font.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>sTypoAscender</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>sTypoDescender</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>sTypoLineGap</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>usWinAscent</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>usWinDescent</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>ULONG</td>
 * <td>ulCodePageRange1</td>
 * <td>Bits 0-31</td>
 * </tr>
 * <tr>
 * <td>ULONG</td>
 * <td>ulCodePageRange2</td>
 * <td>Bits 32-63</td>
 * </tr>
 * </table>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TtfTableOS2 extends AbstractXtfTable
    implements
    XtfTable,
    XMLWriterConvertible {

  /**
   * panose.
   * <p>
   * This 10 byte series of numbers are used to describe the visual
   * characteristics of a given typeface. These characteristics are then used
   * to associate the font with other fonts of similar appearance having
   * different names.
   * </p>
   */
  public static class Panose implements XMLWriterConvertible {

    /**
     * Position in array: ARMSTYLE
     */
    private static final int ARMSTYLE = 6;

    /**
     * arm style name
     */
    public static final String[] ARMSTYLENAME = {"Any", // 0
        "No Fit", // 1
        "Straight Arms/Horizontal", // 2
        "Straight Arms/Wedge", // 3
        "Straight Arms/Vertical", // 4
        "Straight Arms/Single Serif", // 5
        "Straight Arms/Double Serif", // 6
        "Non-Straight Arms/Horizontal", // 7
        "Non-Straight Arms/Wedge", // 8
        "Non-Straight Arms/Vertical", // 9
        "Non-Straight Arms/Single Serif", // 10
        "Non-Straight Arms/Double Serif" // 11
    };

    /**
     * Position in array: CONTRAST
     */
    private static final int CONTRAST = 4;

    /**
     * contrastname
     */
    public static final String[] CONTRASTNAME = {"Any", // 0
        "No Fit", // 1
        "None", // 2
        "Very Low", // 3
        "Low", // 4
        "Medium Low", // 5
        "Medium", // 6
        "Medium High", // 7
        "High", // 8
        "Very High" // 9
    };

    /**
     * Position in array: FAMILYTYPE
     */
    private static final int FAMILYTYPE = 0;

    /**
     * familytypename
     */
    public static final String[] FAMILYTYPENAME = {"Any", // 0
        "No Fit", // 1
        "Text and Display", // 2
        "Script", // 3
        "Decorative", // 4
        "Pictorial" // 5
    };

    /**
     * Position in array: LETTERFORM
     */
    private static final int LETTERFORM = 7;

    /**
     * letterformname
     */
    public static final String[] LETTERFORMNAME = {"Any", // 0
        "No Fit", // 1
        "Normal/Contact", // 2
        "Normal/Weighted", // 3
        "Normal/Boxed", // 4
        "Normal/Flattened", // 5
        "Normal/Rounded", // 6
        "Normal/Off Center", // 7
        "Normal/Square", // 8
        "Oblique/Contact", // 9
        "Oblique/Weighted", // 10
        "Oblique/Boxed", // 11
        "Oblique/Flattened", // 12
        "Oblique/Rounded", // 13
        "Oblique/Off Center", // 14
        "Oblique/Square" // 15
    };

    /**
     * Position in array: MIDLINE
     */
    private static final int MIDLINE = 8;

    /**
     * midlinename
     */
    public static final String[] MIDLINENAME = {"Any", // 0
        "No Fit", // 1
        "Standard/Trimmed", // 2
        "Standard/Pointed", // 3
        "Standard/Serifed", // 4
        "High/Trimmed", // 5
        "High/Pointed", // 6
        "High/Serifed", // 7
        "Constant/Trimmed", // 8
        "Constant/Pointed", // 9
        "Constant/Serifed", // 10
        "Low/Trimmed", // 11
        "Low/Pointed", // 12
        "Low/Serifed" // 13
    };

    /**
     * Position in array: PROPORTION
     */
    private static final int PROPORTION = 3;

    /**
     * proportionname
     */
    public static final String[] PROPORTIONAME = {"Any", // 0
        "No Fit", // 1
        "Old Style", // 2
        "Modern", // 3
        "Even Width", // 4
        "Expanded", // 5
        "Condensed", // 6
        "Very Expanded", // 7
        "Very Condensed", // 8
        "Monospaced" // 9
    };

    /**
     * Position in array: SERIFSTYLE
     */
    private static final int SERIFSTYLE = 1;

    /**
     * seriftypename
     */
    public static final String[] SERIFSTYLENAME = {"Any", // 0
        "No Fit", // 1
        "Cove", // 2
        "Obtuse Cove", // 3
        "Square Cove", // 4
        "Obtuse Square Cove", // 5
        "Square", // 6
        "Thin", // 7
        "Bone", // 8
        "Exaggerated", // 9
        "Triangle", // 10
        "Normal Sans", // 11
        "Obtuse Sans", // 12
        "Perp Sans", // 13
        "Flared", // 14
        "Rounded" // 15
    };

    /**
     * Position in array: STROKEVARIATION
     */
    private static final int STROKEVARIATION = 5;

    /**
     * strokevariationame
     */
    public static final String[] STROKEVARIATIONNAME = {"Any", // 0
        "No Fit", // 1
        "Gradual/Diagonal", // 2
        "Gradual/Transitional", // 3
        "Gradual/Vertical", // 4
        "Gradual/Horizontal", // 5
        "Rapid/Vertical", // 6
        "Rapid/Horizontal", // 7
        "Instant/Vertical" // 8
    };

    /**
     * Position in array: WEIGHT
     */
    private static final int WEIGHT = 2;

    /**
     * weight name
     */
    public static final String[] WEIGHTNAME = {"Any", // 0
        "No Fit", // 1
        "Very Light", // 2
        "Light", // 3
        "Thin", // 4
        "Book", // 5
        "Medium", // 6
        "Demi", // 7
        "Bold", // 8
        "Heavy", // 9
        "Black", // 10
        "Nord" // 11
    };

    /**
     * Position in array: XHEIGHT
     */
    private static final int XHEIGHT = 9;

    /**
     * xheightname
     */
    public static final String[] XHEIGHTNAME = {"Any", // 0
        "No Fit", // 1
        "Constant/Small", // 2
        "Constant/Standard", // 3
        "Constant/Large", // 4
        "Ducking/Small", // 5
        "Ducking/Standard", // 6
        "Ducking/Large" // 7
    };

    /**
     * armStyle
     */
    private byte armStyle = 0;

    /**
     * contrast
     */
    private byte contrast = 0;

    /**
     * familyType
     */
    private byte familyType = 0;

    /**
     * letterform
     */
    private byte letterform = 0;

    /**
     * midline
     */
    private byte midline = 0;

    /**
     * proportion
     */
    private byte proportion = 0;

    /**
     * serifStyle
     */
    private byte serifStyle = 0;

    /**
     * strokeVariation
     */
    private byte strokeVariation = 0;

    /**
     * weight
     */
    private byte weight = 0;

    /**
     * xHeight
     */
    private byte xHeight = 0;

    /**
     * Create a new object.
     *
     * @param panosearray the panose
     */
    public Panose( byte[] panosearray ) {

      familyType = panosearray[ FAMILYTYPE ];
      serifStyle = panosearray[ SERIFSTYLE ];
      weight = panosearray[ WEIGHT ];
      proportion = panosearray[ PROPORTION ];
      contrast = panosearray[ CONTRAST ];
      strokeVariation = panosearray[ STROKEVARIATION ];
      armStyle = panosearray[ ARMSTYLE ];
      letterform = panosearray[ LETTERFORM ];
      midline = panosearray[ MIDLINE ];
      xHeight = panosearray[ XHEIGHT ];
    }

    /**
     * Returns the armStyle.
     *
     * @return Returns the armStyle.
     */
    public byte getArmStyle() {

      return armStyle;
    }

    /**
     * Returns the contrast.
     *
     * @return Returns the contrast.
     */
    public byte getContrast() {

      return contrast;
    }

    /**
     * Returns the familyType.
     *
     * @return Returns the familyType.
     */
    public byte getFamilyType() {

      return familyType;
    }

    /**
     * Returns the ferifStyle.
     *
     * @return Returns the ferifStyle.
     */
    public byte getFerifStyle() {

      return serifStyle;
    }

    /**
     * Returns the letterform.
     *
     * @return Returns the letterform.
     */
    public byte getLetterform() {

      return letterform;
    }

    /**
     * Returns the midline.
     *
     * @return Returns the midline.
     */
    public byte getMidline() {

      return midline;
    }

    /**
     * Returns the proportion.
     *
     * @return Returns the proportion.
     */
    public byte getProportion() {

      return proportion;
    }

    /**
     * Returns the strokeVariation.
     *
     * @return Returns the strokeVariation.
     */
    public byte getStrokeVariation() {

      return strokeVariation;
    }

    /**
     * Returns the weight.
     *
     * @return Returns the weight.
     */
    public byte getWeight() {

      return weight;
    }

    /**
     * Returns the xHeight.
     *
     * @return Returns the xHeight.
     */
    public byte getXHeight() {

      return xHeight;
    }

    /**
     * org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "panose" );
      writer.writeAttribute( "familytype", String.valueOf( familyType ) );
      writer.writeAttribute( "familytypename",
                             familyType < FAMILYTYPENAME.length
                                 ? FAMILYTYPENAME[ familyType ]
                                 : "" );
      writer.writeAttribute( "serifstyle", String.valueOf( serifStyle ) );
      writer.writeAttribute( "serifstylename",
                             serifStyle < SERIFSTYLENAME.length
                                 ? SERIFSTYLENAME[ serifStyle ]
                                 : "" );
      writer.writeAttribute( "weight", String.valueOf( weight ) );
      writer.writeAttribute( "weigthname", weight < WEIGHTNAME.length
          ? WEIGHTNAME[ weight ]
          : "" );
      writer.writeAttribute( "proportion", String.valueOf( proportion ) );
      writer.writeAttribute( "proportionname",
                             proportion < PROPORTIONAME.length
                                 ? PROPORTIONAME[ proportion ]
                                 : "" );
      writer.writeAttribute( "contrast", String.valueOf( contrast ) );
      writer.writeAttribute( "contrastname",
                             contrast < CONTRASTNAME.length ?
                                 CONTRASTNAME[ contrast ] : "" );
      writer.writeAttribute( "strokevariation", String
          .valueOf( strokeVariation ) );
      writer.writeAttribute( "strokevariationname",
                             strokeVariation < STROKEVARIATIONNAME.length
                                 ? STROKEVARIATIONNAME[ strokeVariation ]
                                 : "" );
      writer.writeAttribute( "armstyle", String.valueOf( armStyle ) );
      writer.writeAttribute( "armstylename",
                             armStyle < ARMSTYLENAME.length ?
                                 ARMSTYLENAME[ armStyle ] : "" );
      writer.writeAttribute( "letterform", String.valueOf( letterform ) );
      writer.writeAttribute( "letterformname",
                             letterform < LETTERFORMNAME.length
                                 ? LETTERFORMNAME[ letterform ]
                                 : "" );
      writer.writeAttribute( "midline", String.valueOf( midline ) );
      writer.writeAttribute( "midlinename", midline < MIDLINENAME.length
          ? MIDLINENAME[ midline ]
          : "" );
      writer.writeAttribute( "xheight", String.valueOf( xHeight ) );
      writer.writeAttribute( "xheightname", xHeight < XHEIGHTNAME.length
          ? XHEIGHTNAME[ xHeight ]
          : "" );
      writer.writeEndElement();
    }
  }

  /**
   * panose array length
   */
  private static final int PANOSELENGTH = 10;

  /**
   * achVendorID
   */
  private final int achVendorID;

  /**
   * fsSelection
   */
  private final short fsSelection;

  /**
   * fsType
   */
  private final short fsType;

  /**
   * panose
   */
  private final Panose panose;

  /**
   * sFamilyClass
   */
  private final short sFamilyClass;

  /**
   * sTypoAscender
   */
  private final short sTypoAscender;

  /**
   * sTypoDescender
   */
  private final short sTypoDescender;

  /**
   * sTypoLineGap
   */
  private final short sTypoLineGap;

  /**
   * ulCodePageRange1
   */
  private final int ulCodePageRange1;

  /**
   * ulCodePageRange2
   */
  private final int ulCodePageRange2;

  /**
   * ulUnicodeRange1
   */
  private final int ulUnicodeRange1;

  /**
   * ulUnicodeRange2
   */
  private final int ulUnicodeRange2;

  /**
   * ulUnicodeRange3
   */
  private final int ulUnicodeRange3;

  /**
   * ulUnicodeRange4
   */
  private final int ulUnicodeRange4;

  /**
   * usFirstCharIndex
   */
  private final int usFirstCharIndex;

  /**
   * usLastCharIndex
   */
  private final int usLastCharIndex;

  /**
   * usWeightClass
   */
  private final int usWeightClass;

  /**
   * usWidthClass
   */
  private final int usWidthClass;

  /**
   * usWinAscent
   */
  private final int usWinAscent;

  /**
   * usWinDescent
   */
  private final int usWinDescent;

  /**
   * version
   */
  private final int version;

  /**
   * xAvgCharWidth
   */
  private final short xAvgCharWidth;

  /**
   * yStrikeoutPosition
   */
  private final short yStrikeoutPosition;

  /**
   * yStrikeoutSize
   */
  private final short yStrikeoutSize;

  /**
   * ySubscriptXOffset
   */
  private final short ySubscriptXOffset;

  /**
   * ySubscriptXSize
   */
  private final short ySubscriptXSize;

  /**
   * ySubscriptYOffset
   */
  private final short ySubscriptYOffset;

  /**
   * ySubscriptYSize
   */
  private final short ySubscriptYSize;

  /**
   * ySuperscriptXOffset
   */
  private final short ySuperscriptXOffset;

  /**
   * ySuperscriptXSize
   */
  private final short ySuperscriptXSize;

  /**
   * ySuperscriptYOffset
   */
  private final short ySuperscriptYOffset;

  /**
   * ySuperscriptYSize
   */
  private final short ySuperscriptYSize;

  /**
   * Create a new object.
   *
   * @param tablemap the tablemap
   * @param de       directory entry
   * @param rar      the RandomAccessInput
   * @throws IOException if an error occured
   */
  public TtfTableOS2( XtfTableMap tablemap, XtfTableDirectory.Entry de,
                      RandomAccessR rar ) throws IOException {

    super( tablemap );
    rar.seek( de.getOffset() );
    version = rar.readUnsignedShort();
    xAvgCharWidth = rar.readShort();
    usWeightClass = rar.readUnsignedShort();
    usWidthClass = rar.readUnsignedShort();
    fsType = rar.readShort();
    ySubscriptXSize = rar.readShort();
    ySubscriptYSize = rar.readShort();
    ySubscriptXOffset = rar.readShort();
    ySubscriptYOffset = rar.readShort();
    ySuperscriptXSize = rar.readShort();
    ySuperscriptYSize = rar.readShort();
    ySuperscriptXOffset = rar.readShort();
    ySuperscriptYOffset = rar.readShort();
    yStrikeoutSize = rar.readShort();
    yStrikeoutPosition = rar.readShort();
    sFamilyClass = rar.readShort();
    byte[] buf = new byte[ PANOSELENGTH ];
    rar.readFully( buf );
    panose = new Panose( buf );
    ulUnicodeRange1 = rar.readInt();
    ulUnicodeRange2 = rar.readInt();
    ulUnicodeRange3 = rar.readInt();
    ulUnicodeRange4 = rar.readInt();
    achVendorID = rar.readInt();
    fsSelection = rar.readShort();
    usFirstCharIndex = rar.readUnsignedShort();
    usLastCharIndex = rar.readUnsignedShort();
    sTypoAscender = rar.readShort();
    sTypoDescender = rar.readShort();
    sTypoLineGap = rar.readShort();
    usWinAscent = rar.readUnsignedShort();
    usWinDescent = rar.readUnsignedShort();
    ulCodePageRange1 = rar.readInt();
    ulCodePageRange2 = rar.readInt();
  }

  /**
   * Returns the achVendorID.
   *
   * @return Returns the achVendorID.
   */
  public int getAchVendorID() {

    return achVendorID;
  }

  /**
   * Returns the fsSelection.
   *
   * @return Returns the fsSelection.
   */
  public short getFsSelection() {

    return fsSelection;
  }

  /**
   * Returns the fsType.
   *
   * @return Returns the fsType.
   */
  public short getFsType() {

    return fsType;
  }

  /**
   * Returns the panose.
   *
   * @return Returns the panose.
   */
  public Panose getPanose() {

    return panose;
  }

  /**
   * Returns the sFamilyClass.
   *
   * @return Returns the sFamilyClass.
   */
  public short getSFamilyClass() {

    return sFamilyClass;
  }

  public String getShortcut() {

    return "os2";
  }

  /**
   * Returns the sTypoAscender.
   *
   * @return Returns the sTypoAscender.
   */
  public short getSTypoAscender() {

    return sTypoAscender;
  }

  /**
   * Returns the sTypoDescender.
   *
   * @return Returns the sTypoDescender.
   */
  public short getSTypoDescender() {

    return sTypoDescender;
  }

  /**
   * Returns the sTypoLineGap.
   *
   * @return Returns the sTypoLineGap.
   */
  public short getSTypoLineGap() {

    return sTypoLineGap;
  }

  /**
   * Get the table type, as a table directory value.
   *
   * @return Returns the table type
   */
  public int getType() {

    return XtfReader.OS_2;
  }

  /**
   * Returns the ulCodePageRange1.
   *
   * @return Returns the ulCodePageRange1.
   */
  public int getUlCodePageRange1() {

    return ulCodePageRange1;
  }

  /**
   * Returns the ulCodePageRange2.
   *
   * @return Returns the ulCodePageRange2.
   */
  public int getUlCodePageRange2() {

    return ulCodePageRange2;
  }

  /**
   * Returns the ulUnicodeRange1.
   *
   * @return Returns the ulUnicodeRange1.
   */
  public int getUlUnicodeRange1() {

    return ulUnicodeRange1;
  }

  /**
   * Returns the ulUnicodeRange2.
   *
   * @return Returns the ulUnicodeRange2.
   */
  public int getUlUnicodeRange2() {

    return ulUnicodeRange2;
  }

  /**
   * Returns the ulUnicodeRange3.
   *
   * @return Returns the ulUnicodeRange3.
   */
  public int getUlUnicodeRange3() {

    return ulUnicodeRange3;
  }

  /**
   * Returns the ulUnicodeRange4.
   *
   * @return Returns the ulUnicodeRange4.
   */
  public int getUlUnicodeRange4() {

    return ulUnicodeRange4;
  }

  /**
   * Returns the usFirstCharIndex.
   *
   * @return Returns the usFirstCharIndex.
   */
  public int getUsFirstCharIndex() {

    return usFirstCharIndex;
  }

  /**
   * Returns the usLastCharIndex.
   *
   * @return Returns the usLastCharIndex.
   */
  public int getUsLastCharIndex() {

    return usLastCharIndex;
  }

  /**
   * Returns the usWeightClass.
   *
   * @return Returns the usWeightClass.
   */
  public int getUsWeightClass() {

    return usWeightClass;
  }

  /**
   * Returns the usWidthClass.
   *
   * @return Returns the usWidthClass.
   */
  public int getUsWidthClass() {

    return usWidthClass;
  }

  /**
   * Returns the usWinAscent.
   *
   * @return Returns the usWinAscent.
   */
  public int getUsWinAscent() {

    return usWinAscent;
  }

  /**
   * Returns the usWinDescent.
   *
   * @return Returns the usWinDescent.
   */
  public int getUsWinDescent() {

    return usWinDescent;
  }

  /**
   * Returns the version.
   *
   * @return Returns the version.
   */
  public int getVersion() {

    return version;
  }

  /**
   * Returns the xAvgCharWidth.
   *
   * @return Returns the xAvgCharWidth.
   */
  public short getXAvgCharWidth() {

    return xAvgCharWidth;
  }

  /**
   * Returns the yStrikeoutPosition.
   *
   * @return Returns the yStrikeoutPosition.
   */
  public short getYStrikeoutPosition() {

    return yStrikeoutPosition;
  }

  /**
   * Returns the yStrikeoutSize.
   *
   * @return Returns the yStrikeoutSize.
   */
  public short getYStrikeoutSize() {

    return yStrikeoutSize;
  }

  /**
   * Returns the ySubscriptXOffset.
   *
   * @return Returns the ySubscriptXOffset.
   */
  public short getYSubscriptXOffset() {

    return ySubscriptXOffset;
  }

  /**
   * Returns the ySubscriptXSize.
   *
   * @return Returns the ySubscriptXSize.
   */
  public short getYSubscriptXSize() {

    return ySubscriptXSize;
  }

  /**
   * Returns the ySubscriptYOffset.
   *
   * @return Returns the ySubscriptYOffset.
   */
  public short getYSubscriptYOffset() {

    return ySubscriptYOffset;
  }

  /**
   * Returns the ySubscriptYSize.
   *
   * @return Returns the ySubscriptYSize.
   */
  public short getYSubscriptYSize() {

    return ySubscriptYSize;
  }

  /**
   * Returns the ySuperscriptXOffset.
   *
   * @return Returns the ySuperscriptXOffset.
   */
  public short getYSuperscriptXOffset() {

    return ySuperscriptXOffset;
  }

  /**
   * Returns the ySuperscriptXSize.
   *
   * @return Returns the ySuperscriptXSize.
   */
  public short getYSuperscriptXSize() {

    return ySuperscriptXSize;
  }

  /**
   * Returns the ySuperscriptYOffset.
   *
   * @return Returns the ySuperscriptYOffset.
   */
  public short getYSuperscriptYOffset() {

    return ySuperscriptYOffset;
  }

  /**
   * Returns the ySuperscriptYSize.
   *
   * @return Returns the ySuperscriptYSize.
   */
  public short getYSuperscriptYSize() {

    return ySuperscriptYSize;
  }

  /**
   * org.extex.util.xml.XMLStreamWriter)
   */
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writeStartElement( writer );
    writer.writeAttribute( "version", XtfReader
        .convertIntToHexString( version ) );
    writer.writeAttribute( "xavgcharwidth", String.valueOf( xAvgCharWidth ) );
    writer.writeAttribute( "usweightclass", String.valueOf( usWeightClass ) );
    writer.writeAttribute( "uswidthclass", String.valueOf( usWidthClass ) );
    writer.writeAttribute( "fstype", XtfReader
        .convertIntToBinaryString( fsType ) );
    writer.writeAttribute( "ysubscriptxsize", String
        .valueOf( ySubscriptXSize ) );
    writer.writeAttribute( "ysubscriptysize", String
        .valueOf( ySubscriptYSize ) );
    writer.writeAttribute( "ysubscriptxoffset", String
        .valueOf( ySubscriptXOffset ) );
    writer.writeAttribute( "ysubscriptyoffset", String
        .valueOf( ySubscriptYOffset ) );
    writer.writeAttribute( "ysuberscriptxsize", String
        .valueOf( ySuperscriptXSize ) );
    writer.writeAttribute( "ysuberscriptysize", String
        .valueOf( ySuperscriptYSize ) );
    writer.writeAttribute( "ysuperscriptxoffset", String
        .valueOf( ySuperscriptXOffset ) );
    writer.writeAttribute( "ysuperscriptyoffset", String
        .valueOf( ySuperscriptYOffset ) );
    writer.writeAttribute( "ystrikeoutsize", String.valueOf( yStrikeoutSize ) );
    writer.writeAttribute( "ystrikeoutposition", String
        .valueOf( yStrikeoutPosition ) );
    writer.writeAttribute( "sfamilyclass", String.valueOf( sFamilyClass ) );
    writer.writeAttribute( "ulunicoderange1", XtfReader
        .convertIntToBinaryString( ulUnicodeRange1 ) );
    writer.writeAttribute( "ulunicoderange2", XtfReader
        .convertIntToBinaryString( ulUnicodeRange2 ) );
    writer.writeAttribute( "ulunicoderange3", XtfReader
        .convertIntToBinaryString( ulUnicodeRange3 ) );
    writer.writeAttribute( "ulunicoderange4", XtfReader
        .convertIntToBinaryString( ulUnicodeRange4 ) );
    writer.writeAttribute( "achvendorid", XtfReader
        .convertIntToHexString( achVendorID ) );
    writer.writeAttribute( "fsselection", XtfReader
        .convertIntToBinaryString( fsSelection ) );
    writer.writeAttribute( "usfirstcharindex", String
        .valueOf( usFirstCharIndex ) );
    writer.writeAttribute( "uslastcharindex", String
        .valueOf( usLastCharIndex ) );
    writer.writeAttribute( "stypoascender", String.valueOf( sTypoAscender ) );
    writer.writeAttribute( "stypodescender", String.valueOf( sTypoDescender ) );
    writer.writeAttribute( "stypolinegap", String.valueOf( sTypoLineGap ) );
    writer.writeAttribute( "uswinAscent", String.valueOf( usWinAscent ) );
    writer.writeAttribute( "uswindescent", String.valueOf( usWinDescent ) );
    writer.writeAttribute( "ulcodepagerange1", XtfReader
        .convertIntToBinaryString( ulCodePageRange1 ) );
    writer.writeAttribute( "ulcodepagerange2", XtfReader
        .convertIntToBinaryString( ulCodePageRange2 ) );
    if( panose != null ) {
      panose.writeXML( writer );
    }
    writer.writeEndElement();
  }

}
