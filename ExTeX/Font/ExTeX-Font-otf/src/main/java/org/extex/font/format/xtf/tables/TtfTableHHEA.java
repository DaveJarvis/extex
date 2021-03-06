/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
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
 * The 'hhea' table contains information needed to layout fonts whose characters
 * are written horizontally, that is, either left to right or right to left.
 * This table contains information that is general to the font as a whole.
 *
 *  <table> <caption>TBD</caption> <tbody>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * </tbody>
 * <tr>
 * <td>Fixed</td>
 * <td>Table version number</td>
 * <td> 0x00010000 for version 1.0.</td>
 * </tr>
 * <tr>
 * <td>FWord</td>
 * <td>Ascender</td>
 * <td>Typographic ascent.</td>
 * </tr>
 * <tr>
 * <td>FWord</td>
 * <td>Descender</td>
 * <td>Typographic descent.</td>
 * </tr>
 * <tr>
 * <td>FWord</td>
 * <td>LineGap</td>
 * <td> Typographic line gap. Negative LineGap values are treated as zero<br>
 * in Windows 3.1, System 6, and System 7.</td>
 * </tr>
 * <tr>
 * <td>uFWord</td>
 * <td>advanceWidthMax</td>
 * <td> Maximum advance width value in &lsquo;hmtx&rsquo; table.</td>
 * </tr>
 * <tr>
 * <td>FWord</td>
 * <td>minLeftSideBearing</td>
 * <td> Minimum left sidebearing value in &lsquo;hmtx&rsquo; table.</td>
 * </tr>
 * <tr>
 * <td>FWord</td>
 * <td>minRightSideBearing</td>
 * <td> Minimum right sidebearing value; calculated as Min(aw - lsb - (xMax -
 * xMin)).</td>
 * </tr>
 * <tr>
 * <td>FWord</td>
 * <td>xMaxExtent</td>
 * <td>Max(lsb + (xMax - xMin)).</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>caretSlopeRise</td>
 * <td> Used to calculate the slope of the cursor (rise/run); 1 for vertical
 * .</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>caretSlopeRun</td>
 * <td>0 for vertical.</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>(reserved)</td>
 * <td>set to 0</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>(reserved)</td>
 * <td>set to 0</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>(reserved)</td>
 * <td>set to 0</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>(reserved)</td>
 * <td>set to 0</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>(reserved)</td>
 * <td>set to 0</td>
 * </tr>
 * <tr>
 * <td>SHORT</td>
 * <td>metricDataFormat</td>
 * <td>0 for current format.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>numberOfHMetrics</td>
 * <td> Number of hMetric entries in &lsquo;hmtx&rsquo; table; may be smaller
 * than the total number of glyphs in the font.</td>
 * </tr>
 * </table>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TtfTableHHEA extends AbstractXtfTable
    implements
    XtfTable,
    XMLWriterConvertible {

  /**
   * advance width max
   */
  private final short advanceWidthMax;

  /**
   * ascender
   */
  private final short ascender;

  /**
   * caret slope rise
   */
  private final short caretSlopeRise;

  /**
   * caret slope run
   */
  private final short caretSlopeRun;

  /**
   * descender
   */
  private final short descender;

  /**
   * line gap
   */
  private final short lineGap;

  /**
   * metrix data format
   */
  private final short metricDataFormat;

  /**
   * min left side bearing
   */
  private final short minLeftSideBearing;

  /**
   * min right side bearing
   */
  private final short minRightSideBearing;

  /**
   * number of horizontal metrics
   */
  private final short numberOfHMetrics;

  /**
   * reserved 0
   */
  private final short reserved0;

  /**
   * reserved 1
   */
  private final short reserved1;

  /**
   * reserved 2
   */
  private final short reserved2;

  /**
   * reserved 3
   */
  private final short reserved3;

  /**
   * reserved 4
   */
  private final short reserved4;

  /**
   * version
   */
  private final int version;

  /**
   * max extent
   */
  private final short xMaxExtent;

  /**
   * Create a new object
   *
   * @param tablemap the tablemap
   * @param de       entry
   * @param rar      input
   * @throws IOException if an IO-error occurs
   */
  public TtfTableHHEA( XtfTableMap tablemap, XtfTableDirectory.Entry de,
                       RandomAccessR rar ) throws IOException {

    super( tablemap );
    rar.seek( de.getOffset() );
    version = rar.readInt();
    ascender = rar.readShort();
    descender = rar.readShort();
    lineGap = rar.readShort();
    advanceWidthMax = rar.readShort();
    minLeftSideBearing = rar.readShort();
    minRightSideBearing = rar.readShort();
    xMaxExtent = rar.readShort();
    caretSlopeRise = rar.readShort();
    caretSlopeRun = rar.readShort();
    reserved0 = rar.readShort();
    reserved1 = rar.readShort();
    reserved2 = rar.readShort();
    reserved3 = rar.readShort();
    reserved4 = rar.readShort();
    metricDataFormat = rar.readShort();
    numberOfHMetrics = rar.readShort();
  }

  /**
   * Returns the advanceWidthMax.
   *
   * @return Returns the advanceWidthMax.
   */
  public short getAdvanceWidthMax() {

    return advanceWidthMax;
  }

  /**
   * Returns the ascender.
   *
   * @return Returns the ascender.
   */
  public short getAscender() {

    return ascender;
  }

  /**
   * Returns the caretSlopeRise.
   *
   * @return Returns the caretSlopeRise.
   */
  public short getCaretSlopeRise() {

    return caretSlopeRise;
  }

  /**
   * Returns the caretSlopeRun.
   *
   * @return Returns the caretSlopeRun.
   */
  public short getCaretSlopeRun() {

    return caretSlopeRun;
  }

  /**
   * Returns the descender.
   *
   * @return Returns the descender.
   */
  public short getDescender() {

    return descender;
  }

  /**
   * Returns the lineGap.
   *
   * @return Returns the lineGap.
   */
  public short getLineGap() {

    return lineGap;
  }

  /**
   * Returns the metricDataFormat.
   *
   * @return Returns the metricDataFormat.
   */
  public short getMetricDataFormat() {

    return metricDataFormat;
  }

  /**
   * Returns the minLeftSideBearing.
   *
   * @return Returns the minLeftSideBearing.
   */
  public short getMinLeftSideBearing() {

    return minLeftSideBearing;
  }

  /**
   * Returns the minRightSideBearing.
   *
   * @return Returns the minRightSideBearing.
   */
  public short getMinRightSideBearing() {

    return minRightSideBearing;
  }

  /**
   * Returns the numberOfHMetrics.
   *
   * @return Returns the numberOfHMetrics.
   */
  public short getNumberOfHMetrics() {

    return numberOfHMetrics;
  }

  public String getShortcut() {

    return "hhea";
  }

  /**
   * Get the table type, as a table directory value.
   *
   * @return Returns the table type
   */
  public int getType() {

    return XtfReader.HHEA;
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
   * Returns the xMaxExtent.
   *
   * @return Returns the xMaxExtent.
   */
  public short getXMaxExtent() {

    return xMaxExtent;
  }

  /**
   * org.extex.util.xml.XMLStreamWriter)
   */
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writeStartElement( writer );
    writer.writeAttribute( "version", String.valueOf( XtfReader
                                                          .convertVersion(
                                                              version ) ) );
    writer.writeAttribute( "ascender", String.valueOf( ascender ) );
    writer.writeAttribute( "descender", String.valueOf( descender ) );
    writer.writeAttribute( "linegap", String.valueOf( lineGap ) );
    writer.writeAttribute( "advancewidthmax", String
        .valueOf( advanceWidthMax ) );
    writer.writeAttribute( "minleftsidebearing", String
        .valueOf( minLeftSideBearing ) );
    writer.writeAttribute( "minrightsidebearing", String
        .valueOf( minRightSideBearing ) );
    writer.writeAttribute( "xmaxextent", String.valueOf( xMaxExtent ) );
    writer.writeAttribute( "caretsloperise", String.valueOf( caretSlopeRise ) );
    writer.writeAttribute( "caretsloperun", String.valueOf( caretSlopeRun ) );
    writer.writeAttribute( "metricdataformat", String
        .valueOf( metricDataFormat ) );
    writer.writeAttribute( "numberofhmetrics", String
        .valueOf( numberOfHMetrics ) );
    writer.writeAttribute( "reserved0", String.valueOf( reserved0 ) );
    writer.writeAttribute( "reserved1", String.valueOf( reserved1 ) );
    writer.writeAttribute( "reserved2", String.valueOf( reserved2 ) );
    writer.writeAttribute( "reserved3", String.valueOf( reserved3 ) );
    writer.writeAttribute( "reserved4", String.valueOf( reserved4 ) );
    writer.writeEndElement();
  }
}
