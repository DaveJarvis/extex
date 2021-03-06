/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf;

import org.extex.font.format.xtf.tables.XtfTable;
import org.extex.font.format.xtf.tables.gps.*;
import org.extex.font.format.xtf.tables.tag.FeatureTag;
import org.extex.font.format.xtf.tables.tag.ScriptTag;
import org.extex.util.xml.XMLStreamWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Tests for the {@code XtfReader} with opentype files.
 * <p>
 * Table GPOS
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class XtfReaderFxlrGposCmr10Test {

  private final static String DIR_TARGET = "build";

  private final XtfReader reader;

  /**
   * Creates a new object.
   *
   * @throws IOException if an error occurred.
   */
  public XtfReaderFxlrGposCmr10Test() throws IOException {
    reader = new XtfReader( "../ExTeX-Font-otf/src/font/cmr10.ttf" );
  }

  /**
   * Test, if the reader exits.
   */
  @Test
  public void testExists() {
    assertNotNull( reader );
  }

  /**
   * Test the gpos table.
   */
  @Test
  public void testGpos01() {

    assertNotNull( reader );
    XtfTable table = reader.getTable( XtfReader.GPOS );
    assertNotNull( table );

    Assert.assertTrue( table instanceof OtfTableGPOS );
    OtfTableGPOS gpos = (OtfTableGPOS) table;
    assertNotNull( gpos );

    XtfLookup[] lookups =
        gpos.findLookup( ScriptTag.getInstance( "latn" ), null, FeatureTag
            .getInstance( "kern" ) );

    assertNotNull( lookups );
    Assert.assertEquals( "count of pairtable", 1, lookups.length );
    Assert.assertEquals( "subtable count",
                         1,
                         lookups[ 0 ].getSubtableCount() );
    XtfLookupTable subtable = lookups[ 0 ].getSubtable( 0 );
    assertNotNull( subtable );
    Assert.assertTrue( subtable instanceof XtfGPOSPairTable );
    XtfGPOSPairTable pairtable = (XtfGPOSPairTable) subtable;
    XtfCoverage coverage = pairtable.getCoverage();
    assertNotNull( coverage );
    int[] glyphs = coverage.getGlyphs();
    assertNotNull( glyphs );
    Assert.assertEquals( "glyph count", 33, glyphs.length );

    String[] gNames =
        {"ff", "suppress", "quoteright", "A", "D", "F", "I", "K", "L",
            "O", "P", "R", "T", "V", "W", "X", "Y", "a", "b", "c",
            "f", "g", "h", "k", "m", "n", "o", "p", "t", "u", "v",
            "w", "y"};

    for( int i = 0; i < gNames.length; i++ ) {
      Assert.assertEquals( gNames[ i ],
                           coverage.getXtfGlyph().getGlyphName(
                               glyphs[ i ] ) );
    }

    // ff (14), excalm (36)
    // SecondGlyph value="exclam"
    // Value1 XAdvance="77"
    PairValue vr = pairtable.getPairValue( 14, 36 );
    assertNotNull( vr );
    assertNotNull( vr.getValue1() );
    assertNotNull( vr.getValue2() );
    Assert.assertTrue( vr.getValue1().isXAdvance() );
    Assert.assertEquals( "xadvanced", 77, vr.getValue1().getXAdvance() );

    // A (68), Y (92)
    // Value1 XAdvance="-83"
    vr = pairtable.getPairValue( 68, 92 );
    assertNotNull( vr );
    assertNotNull( vr.getValue1() );
    assertNotNull( vr.getValue2() );
    Assert.assertTrue( vr.getValue1().isXAdvance() );
    Assert.assertEquals( "xadvanced", -83, vr.getValue1().getXAdvance() );

    // y (124), o (114)
    // Value1 XAdvance="-27"
    vr = pairtable.getPairValue( 124, 114 );
    assertNotNull( vr );
    assertNotNull( vr.getValue1() );
    assertNotNull( vr.getValue2() );
    Assert.assertTrue( vr.getValue1().isXAdvance() );
    Assert.assertEquals( "xadvanced", -27, vr.getValue1().getXAdvance() );

  }

  /**
   * Test: write the xml output to 'tartet'
   *
   * @throws Exception if an error occurred.
   */
  @Test
  public void testXmlOut() throws Exception {

    assertNotNull( reader );
    XMLStreamWriter writer =
        new XMLStreamWriter( new FileOutputStream( DIR_TARGET + "/cmr10.xml" ),
                             "ISO8859-1" );
    writer.setBeauty( true );
    writer.writeStartDocument();
    reader.writeXML( writer );
    writer.writeEndDocument();
    writer.close();

  }
}
