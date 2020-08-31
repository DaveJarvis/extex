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

import org.extex.font.format.xtf.tables.XtfBoundingBox;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Tests for the {@code XtfReader}.
 * <p>
 * The test use the data from the {@code ttx} output.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class XtfReaderGara4Test {

  /**
   * The xtf reader.
   */
  private final XtfReader reader;

  /**
   * Creates a new object.
   *
   * @throws IOException if an error occurred.
   */
  public XtfReaderGara4Test() throws IOException {
    reader = new XtfReader( "../ExTeX-Font-otf/src/font/Gara.ttf" );
  }

  @Test
  public void test01() {
    assertNotNull( reader );
  }

  @Test
  public void test02() {

    assertEquals( "Garamond", reader.getFontFamilyName() );
    assertEquals( 662, reader.getNumberOfGlyphs() );
  }

  @Test
  public void test03() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "A", 0, (short) 3, (short) 1 );
    assertNotNull( bb );

    // name="A" xMin="-15" yMin="0" xMax="1371" yMax="1343"
    assertTrue( bb.eq( -15, 0, 1371, 1343 ) );
  }

  // ---------------------------------------------------

  @Test
  public void testGlyfa() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "a", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -24, 819, 817 ) );
  }

  @Test
  public void testGlyfA() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "A", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1343 ) );
  }

  @Test
  public void testGlyfaacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "aacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -24, 819, 1292 ) );
  }

  @Test
  public void testGlyfAacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Aacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1714 ) );
  }

  @Test
  public void testGlyfabreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "abreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -24, 819, 1233 ) );
  }

  @Test
  public void testGlyfAbreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Abreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1697 ) );
  }

  @Test
  public void testGlyfacircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "acircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -24, 819, 1332 ) );
  }

  @Test
  public void testGlyfAcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Acircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1761 ) );
  }

  @Test
  public void testGlyfacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "acute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 244, 983, 582, 1292 ) );
  }

  @Test
  public void testGlyfadieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "adieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -24, 819, 1229 ) );
  }

  @Test
  public void testGlyfAdieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Adieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1579 ) );
  }

  @Test
  public void testGlyfae() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ae", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 74, -32, 1149, 818 ) );
  }

  @Test
  public void testGlyfAE() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "AE", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -127, -10, 1697, 1286 ) );
  }

  @Test
  public void testGlyfaeacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "aeacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 74, -32, 1149, 1292 ) );
  }

  @Test
  public void testGlyfAEacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "AEacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -127, -10, 1697, 1720 ) );
  }

  @Test
  public void testGlyfafii00208() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii00208", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -12, 345, 1548, 438 ) );
  }

  @Test
  public void testGlyfafii08941() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii08941", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 60, -482, 1211, 1297 ) );
  }

  @Test
  public void testGlyfafii10017() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10017", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1343 ) );
  }

  @Test
  public void testGlyfafii10018() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10018", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 27, -2, 1180, 1274 ) );
  }

  @Test
  public void testGlyfafii10019() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10019", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 27, -2, 1164, 1298 ) );
  }

  @Test
  public void testGlyfafii10020() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10020", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 42, -6, 1105, 1273 ) );
  }

  @Test
  public void testGlyfafii10021() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10021", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -49, -299, 1385, 1343 ) );
  }

  @Test
  public void testGlyfafii10022() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10022", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1274 ) );
  }

  @Test
  public void testGlyfafii10023() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10023", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1577 ) );
  }

  @Test
  public void testGlyfafii10024() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10024", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -18, 2069, 1292 ) );
  }

  @Test
  public void testGlyfafii10025() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10025", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 42, -27, 1026, 1311 ) );
  }

  @Test
  public void testGlyfafii10026() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10026", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 55, -12, 1505, 1284 ) );
  }

  @Test
  public void testGlyfafii10027() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10027", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 55, -12, 1505, 1656 ) );
  }

  @Test
  public void testGlyfafii10028() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10028", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 58, -18, 1348, 1292 ) );
  }

  @Test
  public void testGlyfafii10029() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10029", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1343 ) );
  }

  @Test
  public void testGlyfafii10030() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10030", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 14, -9, 1692, 1289 ) );
  }

  @Test
  public void testGlyfafii10031() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10031", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -21, 1504, 1289 ) );
  }

  @Test
  public void testGlyfafii10032() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10032", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1502, 1292 ) );
  }

  @Test
  public void testGlyfafii10033() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10033", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -21, 1486, 1284 ) );
  }

  @Test
  public void testGlyfafii10034() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10034", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 38, -20, 1098, 1295 ) );
  }

  @Test
  public void testGlyfafii10035() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10035", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, -27, 1231, 1311 ) );
  }

  @Test
  public void testGlyfafii10036() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10036", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -4, -25, 1233, 1331 ) );
  }

  @Test
  public void testGlyfafii10037() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10037", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, -27, 1366, 1290 ) );
  }

  @Test
  public void testGlyfafii10038() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10038", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -8, 1411, 1284 ) );
  }

  @Test
  public void testGlyfafii10039() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10039", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 10, -21, 1449, 1277 ) );
  }

  @Test
  public void testGlyfafii10040() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10040", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -292, 1532, 1284 ) );
  }

  @Test
  public void testGlyfafii10041() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10041", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -6, 1302, 1284 ) );
  }

  @Test
  public void testGlyfafii10042() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10042", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -21, 2095, 1284 ) );
  }

  @Test
  public void testGlyfafii10043() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10043", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -292, 2103, 1284 ) );
  }

  @Test
  public void testGlyfafii10044() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10044", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -2, 1475, 1275 ) );
  }

  @Test
  public void testGlyfafii10045() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10045", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -6, 1807, 1284 ) );
  }

  @Test
  public void testGlyfafii10046() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10046", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 27, -2, 1180, 1284 ) );
  }

  @Test
  public void testGlyfafii10047() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10047", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 70, -26, 1213, 1311 ) );
  }

  @Test
  public void testGlyfafii10048() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10048", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -21, 2177, 1292 ) );
  }

  @Test
  public void testGlyfafii10049() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10049", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -33, -5, 1238, 1289 ) );
  }

  @Test
  public void testGlyfafii10050() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10050", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 27, -6, 1005, 1555 ) );
  }

  @Test
  public void testGlyfafii10051() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10051", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -4, -293, 1463, 1331 ) );
  }

  @Test
  public void testGlyfafii10052() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10052", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 42, -6, 1105, 1720 ) );
  }

  @Test
  public void testGlyfafii10053() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10053", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, -26, 1231, 1311 ) );
  }

  @Test
  public void testGlyfafii10054() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10054", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 77, -33, 895, 1316 ) );
  }

  @Test
  public void testGlyfafii10055() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10055", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, 0, 664, 1279 ) );
  }

  @Test
  public void testGlyfafii10056() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10056", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, -4, 664, 1573 ) );
  }

  @Test
  public void testGlyfafii10057() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10057", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -173, -518, 569, 1279 ) );
  }

  @Test
  public void testGlyfafii10058() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10058", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, -1, 1868, 1343 ) );
  }

  @Test
  public void testGlyfafii10059() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10059", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -21, 1928, 1289 ) );
  }

  @Test
  public void testGlyfafii10060() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10060", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -4, -25, 1513, 1331 ) );
  }

  @Test
  public void testGlyfafii10061() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10061", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 58, -18, 1348, 1720 ) );
  }

  @Test
  public void testGlyfafii10062() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10062", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, -27, 1366, 1656 ) );
  }

  @Test
  public void testGlyfafii10065() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10065", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -24, 819, 817 ) );
  }

  @Test
  public void testGlyfafii10066() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10066", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 76, -27, 976, 1339 ) );
  }

  @Test
  public void testGlyfafii10067() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10067", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -1, 804, 820 ) );
  }

  @Test
  public void testGlyfafii10068() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10068", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -5, 732, 796 ) );
  }

  @Test
  public void testGlyfafii10069() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10069", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 12, -213, 941, 847 ) );
  }

  @Test
  public void testGlyfafii10070() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10070", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -26, 804, 822 ) );
  }

  @Test
  public void testGlyfafii10071() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10071", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -26, 804, 1229 ) );
  }

  @Test
  public void testGlyfafii10072() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10072", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -3, 1287, 820 ) );
  }

  @Test
  public void testGlyfafii10073() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10073", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 60, -18, 695, 820 ) );
  }

  @Test
  public void testGlyfafii10074() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10074", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -5, 1093, 796 ) );
  }

  @Test
  public void testGlyfafii10075() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10075", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -5, 1093, 1256 ) );
  }

  @Test
  public void testGlyfafii10076() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10076", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 52, -3, 889, 820 ) );
  }

  @Test
  public void testGlyfafii10077() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10077", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 7, 0, 1002, 847 ) );
  }

  @Test
  public void testGlyfafii10078() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10078", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 12, -21, 1187, 795 ) );
  }

  @Test
  public void testGlyfafii10079() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10079", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -5, 1093, 796 ) );
  }

  @Test
  public void testGlyfafii10080() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10080", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -27, 971, 820 ) );
  }

  @Test
  public void testGlyfafii10081() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10081", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -5, 1093, 800 ) );
  }

  @Test
  public void testGlyfafii10082() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10082", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 24, -525, 971, 890 ) );
  }

  @Test
  public void testGlyfafii10083() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10083", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -31, 800, 816 ) );
  }

  @Test
  public void testGlyfafii10084() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10084", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 7, -17, 834, 851 ) );
  }

  @Test
  public void testGlyfafii10085() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10085", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 7, -505, 881, 792 ) );
  }

  @Test
  public void testGlyfafii10086() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10086", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 72, -525, 1493, 1329 ) );
  }

  @Test
  public void testGlyfafii10087() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10087", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 28, -1, 911, 789 ) );
  }

  @Test
  public void testGlyfafii10088() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10088", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -213, 1093, 795 ) );
  }

  @Test
  public void testGlyfafii10089() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10089", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 10, -5, 1026, 796 ) );
  }

  @Test
  public void testGlyfafii10090() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10090", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -5, 1607, 796 ) );
  }

  @Test
  public void testGlyfafii10091() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10091", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -213, 1607, 796 ) );
  }

  @Test
  public void testGlyfafii10092() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10092", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -7, -4, 1003, 793 ) );
  }

  @Test
  public void testGlyfafii10093() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10093", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -5, 1259, 796 ) );
  }

  @Test
  public void testGlyfafii10094() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10094", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -1, 821, 796 ) );
  }

  @Test
  public void testGlyfafii10095() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10095", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 63, -18, 763, 819 ) );
  }

  @Test
  public void testGlyfafii10096() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10096", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -27, 1521, 820 ) );
  }

  @Test
  public void testGlyfafii10097() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10097", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -5, 825, 820 ) );
  }

  @Test
  public void testGlyfafii10098() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10098", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 16, -5, 712, 933 ) );
  }

  @Test
  public void testGlyfafii10099() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10099", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -282, 947, 1332 ) );
  }

  @Test
  public void testGlyfafii10100() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10100", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -5, 732, 1292 ) );
  }

  @Test
  public void testGlyfafii10101() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10101", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -31, 800, 820 ) );
  }

  @Test
  public void testGlyfafii10102() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10102", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 113, -32, 659, 828 ) );
  }

  @Test
  public void testGlyfafii10103() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10103", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -5, 454, 1310 ) );
  }

  @Test
  public void testGlyfafii10104() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10104", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -3, -5, 542, 1229 ) );
  }

  @Test
  public void testGlyfafii10105() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10105", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 42, -539, 315, 1299 ) );
  }

  @Test
  public void testGlyfafii10106() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10106", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 7, 0, 1331, 847 ) );
  }

  @Test
  public void testGlyfafii10107() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10107", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -5, 1387, 796 ) );
  }

  @Test
  public void testGlyfafii10108() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10108", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -7, 1018, 1332 ) );
  }

  @Test
  public void testGlyfafii10109() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10109", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 52, -3, 889, 1292 ) );
  }

  @Test
  public void testGlyfafii10110() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10110", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 7, -505, 881, 1256 ) );
  }

  @Test
  public void testGlyfafii10145() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10145", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -236, 1486, 1284 ) );
  }

  @Test
  public void testGlyfafii10193() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii10193", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -213, 1093, 796 ) );
  }

  @Test
  public void testGlyfafii61248() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii61248", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 119, -67, 1506, 1305 ) );
  }

  @Test
  public void testGlyfafii61289() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii61289", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 100, -26, 893, 1384 ) );
  }

  @Test
  public void testGlyfafii61352() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "afii61352", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 60, -47, 2028, 1292 ) );
  }

  @Test
  public void testGlyfagrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "agrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -24, 819, 1293 ) );
  }

  @Test
  public void testGlyfAgrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Agrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1715 ) );
  }

  @Test
  public void testGlyfalpha() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "alpha", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 67, -22, 935, 820 ) );
  }

  @Test
  public void testGlyfAlpha() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Alpha", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1343 ) );
  }

  @Test
  public void testGlyfalphatonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "alphatonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 67, -22, 935, 1315 ) );
  }

  @Test
  public void testGlyfAlphatonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Alphatonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1343 ) );
  }

  @Test
  public void testGlyfamacron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "amacron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -24, 819, 1185 ) );
  }

  @Test
  public void testGlyfAmacron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Amacron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1593 ) );
  }

  @Test
  public void testGlyfampersand() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ampersand", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 55, -30, 1461, 1217 ) );
  }

  @Test
  public void testGlyfanoteleia() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "anoteleia", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 117, 568, 331, 794 ) );
  }

  @Test
  public void testGlyfaogonek() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "aogonek", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -328, 967, 817 ) );
  }

  @Test
  public void testGlyfAogonek() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Aogonek", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -14, -328, 1475, 1343 ) );
  }

  @Test
  public void testGlyfapproxequal() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "approxequal", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 147, 245, 1219, 1030 ) );
  }

  @Test
  public void testGlyfaring() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "aring", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -24, 819, 1259 ) );
  }

  @Test
  public void testGlyfAring() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Aring", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1654 ) );
  }

  @Test
  public void testGlyfaringacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "aringacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -24, 819, 1668 ) );
  }

  @Test
  public void testGlyfAringacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Aringacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 2020 ) );
  }

  @Test
  public void testGlyfarrowboth() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "arrowboth", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 79, 158, 1968, 878 ) );
  }

  @Test
  public void testGlyfarrowdown() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "arrowdown", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 153, -429, 872, 1339 ) );
  }

  @Test
  public void testGlyfarrowleft() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "arrowleft", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 79, 157, 1968, 876 ) );
  }

  @Test
  public void testGlyfarrowright() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "arrowright", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 79, 157, 1968, 876 ) );
  }

  @Test
  public void testGlyfarrowup() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "arrowup", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 153, -429, 872, 1339 ) );
  }

  @Test
  public void testGlyfarrowupdn() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "arrowupdn", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 152, -427, 871, 1463 ) );
  }

  @Test
  public void testGlyfarrowupdnbse() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "arrowupdnbse", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 152, -620, 871, 1463 ) );
  }

  @Test
  public void testGlyfasciicircum() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "asciicircum", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, 783, 961, 1374 ) );
  }

  @Test
  public void testGlyfasciitilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "asciitilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 150, 499, 1215, 775 ) );
  }

  @Test
  public void testGlyfasterisk() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "asterisk", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 59, 492, 805, 1294 ) );
  }

  @Test
  public void testGlyfat() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "at", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 97, -442, 1836, 1422 ) );
  }

  @Test
  public void testGlyfatilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "atilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 66, -24, 819, 1237 ) );
  }

  @Test
  public void testGlyfAtilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Atilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1608 ) );
  }

  @Test
  public void testGlyfb() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "b", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 34, -43, 965, 1349 ) );
  }

  @Test
  public void testGlyfB() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "B", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 27, -2, 1164, 1298 ) );
  }

  @Test
  public void testGlyfbackslash() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "backslash", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 113, -277, 911, 1426 ) );
  }

  @Test
  public void testGlyfbar() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "bar", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 468, -528, 557, 1339 ) );
  }

  @Test
  public void testGlyfbeta() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "beta", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 133, -505, 908, 1319 ) );
  }

  @Test
  public void testGlyfBeta() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Beta", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 27, -2, 1164, 1298 ) );
  }

  @Test
  public void testGlyfblock() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "block", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1473, 1864 ) );
  }

  @Test
  public void testGlyfbraceleft() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "braceleft", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 283, -442, 841, 1422 ) );
  }

  @Test
  public void testGlyfbraceright() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "braceright", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 177, -442, 735, 1422 ) );
  }

  @Test
  public void testGlyfbracketleft() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "bracketleft", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 208, -475, 605, 1285 ) );
  }

  @Test
  public void testGlyfbracketright() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "bracketright", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -43, -477, 357, 1285 ) );
  }

  @Test
  public void testGlyfbreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "breve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 133, 951, 625, 1233 ) );
  }

  @Test
  public void testGlyfbrokenbar() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "brokenbar", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 468, -528, 557, 1339 ) );
  }

  @Test
  public void testGlyfbullet() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "bullet", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 112, 426, 614, 928 ) );
  }

  @Test
  public void testGlyfc() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "c", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -31, 800, 816 ) );
  }

  @Test
  public void testGlyfC() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "C", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, -27, 1231, 1311 ) );
  }

  @Test
  public void testGlyfcacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "cacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -31, 800, 1292 ) );
  }

  @Test
  public void testGlyfCacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Cacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, -27, 1231, 1714 ) );
  }

  @Test
  public void testGlyfcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "caron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 148, 977, 587, 1332 ) );
  }

  @Test
  public void testGlyfccaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ccaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -31, 800, 1332 ) );
  }

  @Test
  public void testGlyfCcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ccaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, -27, 1231, 1761 ) );
  }

  @Test
  public void testGlyfccedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ccedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -431, 800, 816 ) );
  }

  @Test
  public void testGlyfCcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ccedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, -431, 1231, 1311 ) );
  }

  @Test
  public void testGlyfccircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ccircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -31, 800, 1332 ) );
  }

  @Test
  public void testGlyfCcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ccircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, -27, 1231, 1760 ) );
  }

  @Test
  public void testGlyfcdot() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "cdot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -31, 800, 1229 ) );
  }

  @Test
  public void testGlyfCdot() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Cdot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, -27, 1231, 1573 ) );
  }

  @Test
  public void testGlyfcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "cedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 1, -431, 300, 14 ) );
  }

  @Test
  public void testGlyfcent() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "cent", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 79, -346, 798, 1188 ) );
  }

  @Test
  public void testGlyfchi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "chi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 48, -506, 952, 820 ) );
  }

  @Test
  public void testGlyfChi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Chi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 10, -21, 1449, 1277 ) );
  }

  @Test
  public void testGlyfcircle() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "circle", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 178, 137, 1059, 1018 ) );
  }

  @Test
  public void testGlyfcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "circumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 147, 977, 586, 1332 ) );
  }

  @Test
  public void testGlyfclub() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "club", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 55, 0, 1288, 1231 ) );
  }

  @Test
  public void testGlyfcolon() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "colon", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 117, -27, 331, 794 ) );
  }

  @Test
  public void testGlyfcomma() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "comma", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 84, -356, 388, 141 ) );
  }

  @Test
  public void testGlyfcommaaccent() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "commaaccent", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 192, 851, 486, 1348 ) );
  }

  @Test
  public void testGlyfcopyright() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "copyright", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 69, -31, 1488, 1387 ) );
  }

  @Test
  public void testGlyfcurrency() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "currency", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 202, 183, 1157, 1137 ) );
  }

  @Test
  public void testGlyfd() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "d", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 67, -37, 999, 1348 ) );
  }

  @Test
  public void testGlyfD() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "D", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 22, -17, 1479, 1301 ) );
  }

  @Test
  public void testGlyfdagger() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "dagger", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -499, 865, 1312 ) );
  }

  @Test
  public void testGlyfdaggerdbl() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "daggerdbl", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 31, -493, 843, 1317 ) );
  }

  @Test
  public void testGlyfdcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "dcaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 67, -37, 1266, 1348 ) );
  }

  @Test
  public void testGlyfDcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Dcaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 22, -17, 1479, 1768 ) );
  }

  @Test
  public void testGlyfdegree() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "degree", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 98, 771, 713, 1386 ) );
  }

  @Test
  public void testGlyfdelta() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "delta", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 86, -22, 896, 1316 ) );
  }

  @Test
  public void testGlyfdiamond() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "diamond", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 213, -24, 1131, 1234 ) );
  }

  @Test
  public void testGlyfdieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "dieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 133, 1056, 649, 1229 ) );
  }

  @Test
  public void testGlyfdieresistonos() {

    XtfBoundingBox bb =
        reader
            .mapCharCodeToBB( "dieresistonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 41, 937, 643, 1315 ) );
  }

  @Test
  public void testGlyfdivide() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "divide", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 23, 279, 1101, 1075 ) );
  }

  @Test
  public void testGlyfdkshade() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "dkshade", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -628, 1447, 1864 ) );
  }

  @Test
  public void testGlyfdmacron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "dmacron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 67, -37, 1038, 1348 ) );
  }

  @Test
  public void testGlyfdnblock() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "dnblock", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1473, 621 ) );
  }

  @Test
  public void testGlyfdollar() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "dollar", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 85, -273, 829, 1342 ) );
  }

  @Test
  public void testGlyfdotaccent() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "dotaccent", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 291, 1056, 463, 1229 ) );
  }

  @Test
  public void testGlyfdotlessi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "dotlessi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -3, -5, 450, 890 ) );
  }

  @Test
  public void testGlyfDslash() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Dslash", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 15, -17, 1480, 1300 ) );
  }

  @Test
  public void testGlyfe() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "e", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -26, 804, 822 ) );
  }

  @Test
  public void testGlyfE() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "E", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1274 ) );
  }

  @Test
  public void testGlyfeacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "eacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -26, 804, 1292 ) );
  }

  @Test
  public void testGlyfEacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Eacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1714 ) );
  }

  @Test
  public void testGlyfebreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ebreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -26, 804, 1233 ) );
  }

  @Test
  public void testGlyfEbreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ebreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1697 ) );
  }

  @Test
  public void testGlyfecaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ecaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -26, 804, 1332 ) );
  }

  @Test
  public void testGlyfEcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ecaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1768 ) );
  }

  @Test
  public void testGlyfecircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ecircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -26, 804, 1332 ) );
  }

  @Test
  public void testGlyfEcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ecircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1761 ) );
  }

  @Test
  public void testGlyfedieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "edieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -26, 804, 1229 ) );
  }

  @Test
  public void testGlyfEdieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Edieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1579 ) );
  }

  @Test
  public void testGlyfedot() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "edot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -26, 804, 1229 ) );
  }

  @Test
  public void testGlyfEdot() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Edot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1573 ) );
  }

  @Test
  public void testGlyfegrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "egrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -26, 804, 1293 ) );
  }

  @Test
  public void testGlyfEgrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Egrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1715 ) );
  }

  @Test
  public void testGlyfeight() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "eight", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 116, -28, 879, 1297 ) );
  }

  @Test
  public void testGlyfeightsuperior() {

    XtfBoundingBox bb =
        reader
            .mapCharCodeToBB( "eightsuperior", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 84, 614, 559, 1304 ) );
  }

  @Test
  public void testGlyfellipsis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ellipsis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 234, -19, 1814, 197 ) );
  }

  @Test
  public void testGlyfemacron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "emacron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -26, 804, 1185 ) );
  }

  @Test
  public void testGlyfEmacron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Emacron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1593 ) );
  }

  @Test
  public void testGlyfemdash() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "emdash", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -12, 345, 2060, 438 ) );
  }

  @Test
  public void testGlyfendash() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "endash", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -12, 345, 1036, 438 ) );
  }

  @Test
  public void testGlyfeng() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "eng", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 35, -498, 869, 843 ) );
  }

  @Test
  public void testGlyfEng() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Eng", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 100, -17, 1437, 1311 ) );
  }

  @Test
  public void testGlyfeogonek() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "eogonek", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -328, 803, 822 ) );
  }

  @Test
  public void testGlyfEogonek() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Eogonek", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -328, 1296, 1274 ) );
  }

  @Test
  public void testGlyfepsilon() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "epsilon", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 80, -22, 716, 820 ) );
  }

  @Test
  public void testGlyfEpsilon() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Epsilon", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, -14, 1296, 1274 ) );
  }

  @Test
  public void testGlyfepsilontonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "epsilontonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 80, -22, 716, 1315 ) );
  }

  @Test
  public void testGlyfEpsilontonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Epsilontonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -48, -14, 1429, 1292 ) );
  }

  @Test
  public void testGlyfequal() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "equal", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 147, 361, 1219, 913 ) );
  }

  @Test
  public void testGlyfequivalence() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "equivalence", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 203, 131, 1275, 1147 ) );
  }

  @Test
  public void testGlyfestimated() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "estimated", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 90, -34, 1148, 1096 ) );
  }

  @Test
  public void testGlyfeta() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "eta", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 50, -505, 894, 821 ) );
  }

  @Test
  public void testGlyfEta() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Eta", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -21, 1504, 1289 ) );
  }

  @Test
  public void testGlyfetatonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "etatonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 50, -505, 894, 1315 ) );
  }

  @Test
  public void testGlyfEtatonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Etatonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -48, -21, 1598, 1292 ) );
  }

  @Test
  public void testGlyfeth() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "eth", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 92, -24, 995, 1315 ) );
  }

  @Test
  public void testGlyfEth() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Eth", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 15, -17, 1480, 1301 ) );
  }

  @Test
  public void testGlyfexclam() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "exclam", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 125, -25, 329, 1307 ) );
  }

  @Test
  public void testGlyfexclamdbl() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "exclamdbl", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 129, -25, 689, 1307 ) );
  }

  @Test
  public void testGlyfexclamdown() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "exclamdown", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 121, -492, 326, 836 ) );
  }

  @Test
  public void testGlyff() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "f", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 96, -1, 825, 1339 ) );
  }

  @Test
  public void testGlyfF() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "F", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 59, -20, 1107, 1294 ) );
  }

  @Test
  public void testGlyffemale() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "female", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 244, -439, 1291, 1507 ) );
  }

  @Test
  public void testGlyffi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "fi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, -1, 1066, 1343 ) );
  }

  @Test
  public void testGlyffi1() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "fi1", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, -1, 1066, 1343 ) );
  }

  @Test
  public void testGlyffilledbox() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "filledbox", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 146, 0, 1090, 944 ) );
  }

  @Test
  public void testGlyffilledrect() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "filledrect", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, 317, 2047, 703 ) );
  }

  @Test
  public void testGlyffive() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "five", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 105, -33, 857, 1307 ) );
  }

  @Test
  public void testGlyffiveeighths() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "fiveeighths", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 93, -67, 1607, 1305 ) );
  }

  @Test
  public void testGlyffivesuperior() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "fivesuperior", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, 608, 541, 1305 ) );
  }

  @Test
  public void testGlyffl() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "fl", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 93, -4, 1052, 1336 ) );
  }

  @Test
  public void testGlyffl1() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "fl1", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 93, -4, 1052, 1336 ) );
  }

  @Test
  public void testGlyfflorin() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "florin", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -2, -525, 1261, 1315 ) );
  }

  @Test
  public void testGlyffour() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "four", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 55, -24, 935, 1304 ) );
  }

  @Test
  public void testGlyffoursuperior() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "foursuperior", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 57, 612, 600, 1304 ) );
  }

  @Test
  public void testGlyffraction() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "fraction", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -284, -67, 645, 1305 ) );
  }

  @Test
  public void testGlyffraction1() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "fraction1", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -284, -67, 645, 1305 ) );
  }

  @Test
  public void testGlyffranc() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "franc", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 60, -20, 1341, 1294 ) );
  }

  @Test
  public void testGlyfg() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "g", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 13, -528, 944, 820 ) );
  }

  @Test
  public void testGlyfG() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "G", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 95, -25, 1553, 1312 ) );
  }

  @Test
  public void testGlyfgamma() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "gamma", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 12, -505, 791, 820 ) );
  }

  @Test
  public void testGlyfGamma() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Gamma", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 11, -6, 1177, 1274 ) );
  }

  @Test
  public void testGlyfgbreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "gbreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 13, -528, 944, 1233 ) );
  }

  @Test
  public void testGlyfGbreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Gbreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 95, -25, 1553, 1688 ) );
  }

  @Test
  public void testGlyfgcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "gcedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 13, -528, 944, 1407 ) );
  }

  @Test
  public void testGlyfGcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Gcedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 95, -621, 1553, 1312 ) );
  }

  @Test
  public void testGlyfgcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "gcircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 13, -528, 944, 1332 ) );
  }

  @Test
  public void testGlyfGcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Gcircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 95, -25, 1553, 1760 ) );
  }

  @Test
  public void testGlyfgdot() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "gdot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 13, -528, 944, 1229 ) );
  }

  @Test
  public void testGlyfGdot() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Gdot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 95, -25, 1553, 1573 ) );
  }

  @Test
  public void testGlyfgermandbls() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "germandbls", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 15, -32, 962, 1318 ) );
  }

  @Test
  public void testGlyfgrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "grave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 199, 983, 535, 1293 ) );
  }

  @Test
  public void testGlyfgreater() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "greater", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 147, 144, 1218, 1130 ) );
  }

  @Test
  public void testGlyfgreaterequal() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "greaterequal", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 146, -41, 1220, 1356 ) );
  }

  @Test
  public void testGlyfguillemotleft() {

    XtfBoundingBox bb =
        reader
            .mapCharCodeToBB( "guillemotleft", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 12, 11, 749, 800 ) );
  }

  @Test
  public void testGlyfguillemotright() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "guillemotright", 0, (short) 3,
                                (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 1, 11, 739, 799 ) );
  }

  @Test
  public void testGlyfguilsinglleft() {

    XtfBoundingBox bb =
        reader
            .mapCharCodeToBB( "guilsinglleft", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 14, 13, 390, 806 ) );
  }

  @Test
  public void testGlyfguilsinglright() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "guilsinglright", 0, (short) 3,
                                (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 17, 16, 391, 811 ) );
  }

  @Test
  public void testGlyfh() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "h", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 30, -7, 1018, 1332 ) );
  }

  @Test
  public void testGlyfH() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "H", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -21, 1504, 1289 ) );
  }

  @Test
  public void testGlyfH18533() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "H18533", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 178, 137, 1059, 1018 ) );
  }

  @Test
  public void testGlyfH18543() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "H18543", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 92, 405, 635, 948 ) );
  }

  @Test
  public void testGlyfH18551() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "H18551", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 92, 405, 635, 948 ) );
  }

  @Test
  public void testGlyfH22073() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "H22073", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 146, 0, 1090, 944 ) );
  }

  @Test
  public void testGlyfhbar() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "hbar", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -11, -7, 1018, 1332 ) );
  }

  @Test
  public void testGlyfHbar() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Hbar", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -21, 1504, 1289 ) );
  }

  @Test
  public void testGlyfhcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "hcircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 30, -7, 1018, 1792 ) );
  }

  @Test
  public void testGlyfHcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Hcircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -21, 1504, 1764 ) );
  }

  @Test
  public void testGlyfheart() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "heart", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 127, -24, 1217, 1231 ) );
  }

  @Test
  public void testGlyfhouse() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "house", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 157, 0, 1080, 1153 ) );
  }

  @Test
  public void testGlyfhungarumlaut() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "hungarumlaut", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 172, 983, 764, 1292 ) );
  }

  @Test
  public void testGlyfhyphen() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "hyphen", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 76, 352, 564, 446 ) );
  }

  @Test
  public void testGlyfi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "i", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -5, 454, 1310 ) );
  }

  @Test
  public void testGlyfI() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "I", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, 0, 664, 1279 ) );
  }

  @Test
  public void testGlyfiacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "iacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -3, -5, 475, 1292 ) );
  }

  @Test
  public void testGlyfIacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Iacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, 0, 664, 1714 ) );
  }

  @Test
  public void testGlyfibreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ibreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -3, -5, 506, 1269 ) );
  }

  @Test
  public void testGlyfIbreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ibreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, 0, 664, 1697 ) );
  }

  @Test
  public void testGlyficircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "icircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -3, -5, 459, 1332 ) );
  }

  @Test
  public void testGlyfIcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Icircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, 0, 664, 1761 ) );
  }

  @Test
  public void testGlyfidieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "idieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -4, -5, 512, 1229 ) );
  }

  @Test
  public void testGlyfIdieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Idieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, 0, 664, 1579 ) );
  }

  @Test
  public void testGlyfigrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "igrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -3, -5, 450, 1293 ) );
  }

  @Test
  public void testGlyfIgrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Igrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, 0, 664, 1715 ) );
  }

  @Test
  public void testGlyfij() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ij", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -3, -525, 831, 1313 ) );
  }

  @Test
  public void testGlyfIJ() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "IJ", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, -518, 1295, 1279 ) );
  }

  @Test
  public void testGlyfimacron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "imacron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -21, -5, 516, 1185 ) );
  }

  @Test
  public void testGlyfImacron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Imacron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, 0, 664, 1589 ) );
  }

  @Test
  public void testGlyfinfinity() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "infinity", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, 219, 1402, 1055 ) );
  }

  @Test
  public void testGlyfintegralbt() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "integralbt", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 261, -515, 691, 1737 ) );
  }

  @Test
  public void testGlyfintegraltp() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "integraltp", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 546, -515, 976, 1737 ) );
  }

  @Test
  public void testGlyfintersection() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "intersection", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 192, 0, 1281, 1297 ) );
  }

  @Test
  public void testGlyfinvbullet() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "invbullet", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 128, 0, 1108, 980 ) );
  }

  @Test
  public void testGlyfinvcircle() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "invcircle", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 42, 0, 1197, 1155 ) );
  }

  @Test
  public void testGlyfinvsmileface() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "invsmileface", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 386, -119, 1662, 1156 ) );
  }

  @Test
  public void testGlyfiogonek() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "iogonek", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -328, 489, 1310 ) );
  }

  @Test
  public void testGlyfIogonek() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Iogonek", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, -364, 664, 1279 ) );
  }

  @Test
  public void testGlyfiota() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "iota", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 113, -22, 409, 802 ) );
  }

  @Test
  public void testGlyfIota() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Iota", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, 0, 664, 1279 ) );
  }

  @Test
  public void testGlyfiotadieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "iotadieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -24, -22, 492, 1229 ) );
  }

  @Test
  public void testGlyfIotadieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Iotadieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, 0, 664, 1577 ) );
  }

  @Test
  public void testGlyfiotadieresistonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "iotadieresistonos", 0, (short) 3,
                                (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -101, -22, 501, 1315 ) );
  }

  @Test
  public void testGlyfiotatonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "iotatonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 113, -22, 409, 1315 ) );
  }

  @Test
  public void testGlyfIotatonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Iotatonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -48, 0, 814, 1292 ) );
  }

  @Test
  public void testGlyfitilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "itilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -29, -5, 544, 1237 ) );
  }

  @Test
  public void testGlyfItilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Itilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, 0, 664, 1589 ) );
  }

  @Test
  public void testGlyfj() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "j", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 42, -539, 315, 1299 ) );
  }

  @Test
  public void testGlyfJ() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "J", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -173, -518, 569, 1279 ) );
  }

  @Test
  public void testGlyfjcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "jcircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 27, -539, 466, 1332 ) );
  }

  @Test
  public void testGlyfJcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Jcircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -173, -518, 569, 1764 ) );
  }

  @Test
  public void testGlyfk() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "k", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 52, -2, 977, 1340 ) );
  }

  @Test
  public void testGlyfK() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "K", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 58, -18, 1556, 1282 ) );
  }

  @Test
  public void testGlyfkappa() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "kappa", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 24, -22, 899, 820 ) );
  }

  @Test
  public void testGlyfKappa() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Kappa", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 58, -18, 1556, 1282 ) );
  }

  @Test
  public void testGlyfkcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "kcedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 52, -435, 977, 1340 ) );
  }

  @Test
  public void testGlyfKcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Kcedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 58, -435, 1556, 1282 ) );
  }

  @Test
  public void testGlyfkgreenlandic() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "kgreenlandic", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 35, -13, 926, 781 ) );
  }

  @Test
  public void testGlyfl() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "l", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 10, -3, 465, 1329 ) );
  }

  @Test
  public void testGlyfL() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "L", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 11, -6, 1177, 1274 ) );
  }

  @Test
  public void testGlyflacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "lacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 10, -3, 465, 1662 ) );
  }

  @Test
  public void testGlyfLacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Lacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 11, -6, 1177, 1716 ) );
  }

  @Test
  public void testGlyflambda() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "lambda", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 24, -22, 863, 1297 ) );
  }

  @Test
  public void testGlyfLambda() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Lambda", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -15, 0, 1371, 1343 ) );
  }

  @Test
  public void testGlyflcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "lcaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 10, -3, 712, 1329 ) );
  }

  @Test
  public void testGlyfLcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Lcaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 11, -6, 1177, 1303 ) );
  }

  @Test
  public void testGlyflcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "lcedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 10, -435, 465, 1329 ) );
  }

  @Test
  public void testGlyfLcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Lcedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 11, -435, 1177, 1274 ) );
  }

  @Test
  public void testGlyfldot() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ldot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 10, -3, 615, 1329 ) );
  }

  @Test
  public void testGlyfLdot() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ldot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 11, -6, 1177, 1274 ) );
  }

  @Test
  public void testGlyfless() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "less", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 147, 144, 1218, 1130 ) );
  }

  @Test
  public void testGlyflessequal() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "lessequal", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 146, -41, 1220, 1356 ) );
  }

  @Test
  public void testGlyflfblock() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "lfblock", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 725, 1864 ) );
  }

  @Test
  public void testGlyflogicalnot() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "logicalnot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 146, 369, 1220, 945 ) );
  }

  @Test
  public void testGlyflongs() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "longs", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 79, -1, 806, 1339 ) );
  }

  @Test
  public void testGlyflozenge() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "lozenge", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 47, 0, 967, 1422 ) );
  }

  @Test
  public void testGlyflslash() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "lslash", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, 6, 563, 1338 ) );
  }

  @Test
  public void testGlyfLslash() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Lslash", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 18, -6, 1184, 1274 ) );
  }

  @Test
  public void testGlyfltshade() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ltshade", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -504, 1327, 1864 ) );
  }

  @Test
  public void testGlyfm() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "m", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 36, -2, 1544, 856 ) );
  }

  @Test
  public void testGlyfM() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "M", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 14, -9, 1692, 1289 ) );
  }

  @Test
  public void testGlyfmale() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "male", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 111, -262, 1415, 1620 ) );
  }

  @Test
  public void testGlyfminus() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "minus", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 146, 593, 1220, 681 ) );
  }

  @Test
  public void testGlyfminute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "minute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 49, 803, 250, 1387 ) );
  }

  @Test
  public void testGlyfmu() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "mu", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 140, -505, 1032, 820 ) );
  }

  @Test
  public void testGlyfMu() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Mu", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 14, -9, 1692, 1289 ) );
  }

  @Test
  public void testGlyfmu1() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "mu1", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 47, -443, 1018, 785 ) );
  }

  @Test
  public void testGlyfmultiply() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "multiply", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 197, 151, 1171, 1123 ) );
  }

  @Test
  public void testGlyfmusicalnote() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "musicalnote", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -37, 987, 1363 ) );
  }

  @Test
  public void testGlyfmusicalnotedbl() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "musicalnotedbl", 0, (short) 3,
                                (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 85, -128, 1330, 1519 ) );
  }

  @Test
  public void testGlyfn() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "n", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 35, -3, 1024, 843 ) );
  }

  @Test
  public void testGlyfN() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "N", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 26, -47, 1500, 1286 ) );
  }

  @Test
  public void testGlyfnacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "nacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 35, -3, 1024, 1292 ) );
  }

  @Test
  public void testGlyfNacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Nacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 26, -47, 1500, 1724 ) );
  }

  @Test
  public void testGlyfnapostrophe() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "napostrophe", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 96, 1, 1352, 1304 ) );
  }

  @Test
  public void testGlyfncaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ncaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 35, -3, 1024, 1332 ) );
  }

  @Test
  public void testGlyfNcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ncaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 26, -47, 1500, 1768 ) );
  }

  @Test
  public void testGlyfncedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ncedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 35, -435, 1024, 843 ) );
  }

  @Test
  public void testGlyfNcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ncedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 26, -435, 1500, 1286 ) );
  }

  @Test
  public void testGlyfnine() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "nine", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 90, -30, 864, 1307 ) );
  }

  @Test
  public void testGlyfnonbreakingspace() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "nbspace", 0, (short) 3, (short) 1 );
    assertNull( bb );
  }

  @Test
  public void testGlyfnotequal() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "notequal", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 147, -170, 1219, 1446 ) );
  }

  @Test
  public void testGlyfnsuperior() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "nsuperior", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 13, 519, 678, 1038 ) );
  }

  @Test
  public void testGlyfntilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ntilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 35, -3, 1024, 1237 ) );
  }

  @Test
  public void testGlyfNtilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ntilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 26, -47, 1500, 1608 ) );
  }

  @Test
  public void testGlyfnu() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "nu", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 36, -22, 700, 820 ) );
  }

  @Test
  public void testGlyfNu() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Nu", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 26, -47, 1500, 1286 ) );
  }

  @Test
  public void testGlyfnumbersign() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "numbersign", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -47, 1271, 1365 ) );
  }

  @Test
  public void testGlyfo() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "o", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -27, 971, 820 ) );
  }

  @Test
  public void testGlyfO() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "O", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1502, 1292 ) );
  }

  @Test
  public void testGlyfoacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "oacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -27, 971, 1292 ) );
  }

  @Test
  public void testGlyfOacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Oacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1502, 1714 ) );
  }

  @Test
  public void testGlyfobreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "obreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -27, 971, 1233 ) );
  }

  @Test
  public void testGlyfObreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Obreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1502, 1697 ) );
  }

  @Test
  public void testGlyfocircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ocircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -27, 971, 1332 ) );
  }

  @Test
  public void testGlyfOcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ocircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1502, 1761 ) );
  }

  @Test
  public void testGlyfodblacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "odblacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -27, 971, 1292 ) );
  }

  @Test
  public void testGlyfOdblacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Odblacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1502, 1720 ) );
  }

  @Test
  public void testGlyfodieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "odieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -27, 971, 1229 ) );
  }

  @Test
  public void testGlyfOdieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Odieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1502, 1579 ) );
  }

  @Test
  public void testGlyfoe() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "oe", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -33, 1364, 820 ) );
  }

  @Test
  public void testGlyfOE() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "OE", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 95, -17, 1863, 1289 ) );
  }

  @Test
  public void testGlyfogonek() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ogonek", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 185, -328, 603, 1 ) );
  }

  @Test
  public void testGlyfograve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ograve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -27, 971, 1293 ) );
  }

  @Test
  public void testGlyfOgrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ograve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1502, 1715 ) );
  }

  @Test
  public void testGlyfOhm() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ohm", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 57, 0, 1457, 1387 ) );
  }

  @Test
  public void testGlyfomacron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "omacron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -27, 971, 1185 ) );
  }

  @Test
  public void testGlyfOmacron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Omacron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1502, 1593 ) );
  }

  @Test
  public void testGlyfomega() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "omega", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 76, -22, 1095, 810 ) );
  }

  @Test
  public void testGlyfOmega() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Omega", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 103, -8, 1486, 1292 ) );
  }

  @Test
  public void testGlyfomegatonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "omegatonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 76, -22, 1095, 1315 ) );
  }

  @Test
  public void testGlyfOmegatonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Omegatonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -48, -8, 1498, 1292 ) );
  }

  @Test
  public void testGlyfomicron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "omicron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -22, 936, 820 ) );
  }

  @Test
  public void testGlyfOmicron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Omicron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1502, 1292 ) );
  }

  @Test
  public void testGlyfomicrontonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "omicrontonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -22, 936, 1315 ) );
  }

  @Test
  public void testGlyfOmicrontonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Omicrontonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -48, -20, 1502, 1292 ) );
  }

  @Test
  public void testGlyfone() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "one", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 154, 2, 727, 1298 ) );
  }

  @Test
  public void testGlyfoneeighth() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "oneeighth", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 112, -67, 1586, 1305 ) );
  }

  @Test
  public void testGlyfonehalf() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "onehalf", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 116, -67, 1590, 1305 ) );
  }

  @Test
  public void testGlyfonequarter() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "onequarter", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 116, -71, 1608, 1301 ) );
  }

  @Test
  public void testGlyfonesuperior() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "onesuperior", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 116, 626, 475, 1301 ) );
  }

  @Test
  public void testGlyfopenbullet() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "openbullet", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, 390, 649, 962 ) );
  }

  @Test
  public void testGlyfordfeminine() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ordfeminine", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 27, 774, 541, 1292 ) );
  }

  @Test
  public void testGlyfordmasculine() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ordmasculine", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, 771, 645, 1292 ) );
  }

  @Test
  public void testGlyforthogonal() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "orthogonal", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 362, 0, 1643, 1279 ) );
  }

  @Test
  public void testGlyfoslash() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "oslash", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -48, 976, 845 ) );
  }

  @Test
  public void testGlyfOslash() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Oslash", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -62, 1502, 1335 ) );
  }

  @Test
  public void testGlyfoslashacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "oslashacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -48, 976, 1292 ) );
  }

  @Test
  public void testGlyfOslashacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Oslashacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -62, 1502, 1728 ) );
  }

  @Test
  public void testGlyfotilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "otilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -27, 971, 1237 ) );
  }

  @Test
  public void testGlyfOtilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Otilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1502, 1608 ) );
  }

  @Test
  public void testGlyfp() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "p", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 24, -525, 971, 890 ) );
  }

  @Test
  public void testGlyfP() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "P", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 38, -20, 1098, 1295 ) );
  }

  @Test
  public void testGlyfparagraph() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "paragraph", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -14, -442, 931, 1356 ) );
  }

  @Test
  public void testGlyfparenleft() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "parenleft", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 157, -502, 634, 1309 ) );
  }

  @Test
  public void testGlyfparenright() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "parenright", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -44, -500, 438, 1311 ) );
  }

  @Test
  public void testGlyfpartialdiff() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "partialdiff", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 60, -31, 991, 1422 ) );
  }

  @Test
  public void testGlyfpercent() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "percent", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 74, -67, 1617, 1305 ) );
  }

  @Test
  public void testGlyfperiod() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "period", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 120, -29, 328, 191 ) );
  }

  @Test
  public void testGlyfperthousand() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "perthousand", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -67, 2023, 1305 ) );
  }

  @Test
  public void testGlyfpeseta() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "peseta", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -32, 1982, 1295 ) );
  }

  @Test
  public void testGlyfphi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "phi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -505, 935, 820 ) );
  }

  @Test
  public void testGlyfPhi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Phi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -8, 1556, 1284 ) );
  }

  @Test
  public void testGlyfpi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "pi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 44, -28, 1006, 916 ) );
  }

  @Test
  public void testGlyfPi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Pi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -21, 1486, 1284 ) );
  }

  @Test
  public void testGlyfpi1() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "pi1", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 60, -22, 914, 805 ) );
  }

  @Test
  public void testGlyfplus() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "plus", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 145, 101, 1220, 1173 ) );
  }

  @Test
  public void testGlyfplusminus() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "plusminus", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 145, -37, 1220, 1353 ) );
  }

  @Test
  public void testGlyfproduct() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "product", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 46, -528, 1640, 1339 ) );
  }

  @Test
  public void testGlyfpsi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "psi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -505, 953, 1315 ) );
  }

  @Test
  public void testGlyfPsi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Psi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 12, 0, 1537, 1292 ) );
  }

  @Test
  public void testGlyfq() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "q", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 70, -524, 1021, 845 ) );
  }

  @Test
  public void testGlyfQ() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Q", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 97, -446, 1532, 1316 ) );
  }

  @Test
  public void testGlyfquestion() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "question", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, -30, 677, 1311 ) );
  }

  @Test
  public void testGlyfquestiondown() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "questiondown", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -502, 619, 836 ) );
  }

  @Test
  public void testGlyfquotedbl() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "quotedbl", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 133, 803, 700, 1387 ) );
  }

  @Test
  public void testGlyfquotedblbase() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "quotedblbase", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 65, -354, 832, 147 ) );
  }

  @Test
  public void testGlyfquotedblleft() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "quotedblleft", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 89, 803, 858, 1301 ) );
  }

  @Test
  public void testGlyfquotedblright() {

    XtfBoundingBox bb =
        reader
            .mapCharCodeToBB( "quotedblright", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 72, 811, 845, 1317 ) );
  }

  @Test
  public void testGlyfquoteleft() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "quoteleft", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 105, 806, 408, 1306 ) );
  }

  @Test
  public void testGlyfquotereversed() {

    XtfBoundingBox bb =
        reader
            .mapCharCodeToBB( "quotereversed", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 102, 806, 396, 1303 ) );
  }

  @Test
  public void testGlyfquoteright() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "quoteright", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 102, 806, 396, 1303 ) );
  }

  @Test
  public void testGlyfquotesinglbase() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "quotesinglbase", 0, (short) 3,
                                (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 93, -356, 387, 141 ) );
  }

  @Test
  public void testGlyfquotesingle() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "quotesingle", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 81, 803, 282, 1387 ) );
  }

  @Test
  public void testGlyfr() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "r", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -3, 680, 865 ) );
  }

  @Test
  public void testGlyfR() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "R", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 42, -5, 1313, 1289 ) );
  }

  @Test
  public void testGlyfracute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "racute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -3, 680, 1292 ) );
  }

  @Test
  public void testGlyfRacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Racute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 42, -5, 1313, 1720 ) );
  }

  @Test
  public void testGlyfradical() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "radical", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 87, -78, 1127, 1869 ) );
  }

  @Test
  public void testGlyfradicalex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "radicalex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -24, 1795, 926, 1869 ) );
  }

  @Test
  public void testGlyfrcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "rcaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -3, 680, 1332 ) );
  }

  @Test
  public void testGlyfRcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Rcaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 42, -5, 1313, 1768 ) );
  }

  @Test
  public void testGlyfrcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "rcedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -435, 680, 865 ) );
  }

  @Test
  public void testGlyfRcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Rcedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 42, -435, 1313, 1289 ) );
  }

  @Test
  public void testGlyfregistered() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "registered", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 69, -31, 1488, 1387 ) );
  }

  @Test
  public void testGlyfrevlogicalnot() {

    XtfBoundingBox bb =
        reader
            .mapCharCodeToBB( "revlogicalnot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 146, 369, 1220, 945 ) );
  }

  @Test
  public void testGlyfrho() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "rho", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -505, 936, 820 ) );
  }

  @Test
  public void testGlyfRho() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Rho", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 38, -20, 1098, 1295 ) );
  }

  @Test
  public void testGlyfring() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ring", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 215, 930, 543, 1259 ) );
  }

  @Test
  public void testGlyfrtblock() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "rtblock", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 726, -621, 1474, 1864 ) );
  }

  @Test
  public void testGlyfs() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "s", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 113, -32, 659, 828 ) );
  }

  @Test
  public void testGlyfS() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "S", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 77, -33, 895, 1316 ) );
  }

  @Test
  public void testGlyfsacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "sacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 113, -32, 659, 1292 ) );
  }

  @Test
  public void testGlyfSacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Sacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 77, -33, 895, 1720 ) );
  }

  @Test
  public void testGlyfscaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "scaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 113, -32, 659, 1332 ) );
  }

  @Test
  public void testGlyfScaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Scaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 77, -33, 895, 1761 ) );
  }

  @Test
  public void testGlyfscedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "scedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 113, -431, 659, 828 ) );
  }

  @Test
  public void testGlyfScedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Scedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 77, -431, 895, 1316 ) );
  }

  @Test
  public void testGlyfscircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "scircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 113, -32, 659, 1332 ) );
  }

  @Test
  public void testGlyfScircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Scircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 77, -33, 895, 1764 ) );
  }

  @Test
  public void testGlyfsecond() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "second", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 101, 803, 668, 1387 ) );
  }

  @Test
  public void testGlyfsection() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "section", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 116, -498, 757, 1314 ) );
  }

  @Test
  public void testGlyfsemicolon() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "semicolon", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 87, -321, 387, 802 ) );
  }

  @Test
  public void testGlyfseven() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "seven", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -26, 883, 1269 ) );
  }

  @Test
  public void testGlyfseveneighths() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "seveneighths", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 87, -67, 1607, 1305 ) );
  }

  @Test
  public void testGlyfsevensuperior() {

    XtfBoundingBox bb =
        reader
            .mapCharCodeToBB( "sevensuperior", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 87, 612, 576, 1286 ) );
  }

  @Test
  public void testGlyfSF010000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF010000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 638, -621, 1474, 709 ) );
  }

  @Test
  public void testGlyfSF020000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF020000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 638, 534, 1474, 1864 ) );
  }

  @Test
  public void testGlyfSF030000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF030000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 812, 709 ) );
  }

  @Test
  public void testGlyfSF040000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF040000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, 534, 812, 1864 ) );
  }

  @Test
  public void testGlyfSF050000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF050000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1473, 1864 ) );
  }

  @Test
  public void testGlyfSF060000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF060000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1473, 709 ) );
  }

  @Test
  public void testGlyfSF070000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF070000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, 534, 1473, 1864 ) );
  }

  @Test
  public void testGlyfSF080000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF080000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 638, -621, 1474, 1864 ) );
  }

  @Test
  public void testGlyfSF090000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF090000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 812, 1864 ) );
  }

  @Test
  public void testGlyfSF100000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF100000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, 534, 1473, 709 ) );
  }

  @Test
  public void testGlyfSF110000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF110000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 638, -621, 813, 1864 ) );
  }

  @Test
  public void testGlyfSF190000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF190000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 812, 1864 ) );
  }

  @Test
  public void testGlyfSF200000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF200000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1003, 1864 ) );
  }

  @Test
  public void testGlyfSF210000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF210000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1003, 709 ) );
  }

  @Test
  public void testGlyfSF220000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF220000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 812, 899 ) );
  }

  @Test
  public void testGlyfSF230000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF230000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1003, 1864 ) );
  }

  @Test
  public void testGlyfSF240000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF240000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 448, -621, 1004, 1864 ) );
  }

  @Test
  public void testGlyfSF250000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF250000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1003, 899 ) );
  }

  @Test
  public void testGlyfSF260000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF260000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, 344, 1003, 1864 ) );
  }

  @Test
  public void testGlyfSF270000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF270000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, 534, 1003, 1864 ) );
  }

  @Test
  public void testGlyfSF280000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF280000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, 344, 812, 1864 ) );
  }

  @Test
  public void testGlyfSF360000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF360000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 638, -621, 1474, 1864 ) );
  }

  @Test
  public void testGlyfSF370000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF370000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 448, -621, 1474, 1864 ) );
  }

  @Test
  public void testGlyfSF380000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF380000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 448, 344, 1474, 1864 ) );
  }

  @Test
  public void testGlyfSF390000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF390000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 448, -621, 1474, 899 ) );
  }

  @Test
  public void testGlyfSF400000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF400000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, 344, 1473, 1864 ) );
  }

  @Test
  public void testGlyfSF410000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF410000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1473, 899 ) );
  }

  @Test
  public void testGlyfSF420000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF420000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 448, -621, 1474, 1864 ) );
  }

  @Test
  public void testGlyfSF430000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF430000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, 344, 1473, 899 ) );
  }

  @Test
  public void testGlyfSF440000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF440000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1473, 1864 ) );
  }

  @Test
  public void testGlyfSF450000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF450000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, 344, 1473, 1864 ) );
  }

  @Test
  public void testGlyfSF460000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF460000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, 534, 1473, 1864 ) );
  }

  @Test
  public void testGlyfSF470000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF470000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1473, 899 ) );
  }

  @Test
  public void testGlyfSF480000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF480000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1473, 709 ) );
  }

  @Test
  public void testGlyfSF490000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF490000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 448, 534, 1475, 1864 ) );
  }

  @Test
  public void testGlyfSF500000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF500000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 638, 344, 1474, 1864 ) );
  }

  @Test
  public void testGlyfSF510000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF510000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 638, -621, 1474, 899 ) );
  }

  @Test
  public void testGlyfSF520000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF520000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 448, -621, 1475, 709 ) );
  }

  @Test
  public void testGlyfSF530000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF530000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1473, 1864 ) );
  }

  @Test
  public void testGlyfSF540000() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "SF540000", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, -621, 1473, 1864 ) );
  }

  @Test
  public void testGlyfsfthyphen() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "sfthyphen", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 76, 352, 564, 446 ) );
  }

  @Test
  public void testGlyfshade() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "shade", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -504, 1447, 1864 ) );
  }

  @Test
  public void testGlyfsigma() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "sigma", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -20, 954, 788 ) );
  }

  @Test
  public void testGlyfSigma() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Sigma", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 60, -15, 1211, 1272 ) );
  }

  @Test
  public void testGlyfsigma1() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "sigma1", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 73, -300, 719, 820 ) );
  }

  @Test
  public void testGlyfsix() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "six", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 100, -28, 875, 1310 ) );
  }

  @Test
  public void testGlyfslash() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "slash", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 116, -277, 908, 1426 ) );
  }

  @Test
  public void testGlyfsmileface() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "smileface", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 386, -119, 1662, 1156 ) );
  }

  @Test
  public void testGlyfspace() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "space", 0, (short) 3, (short) 1 );
    assertNull( bb );
  }

  @Test
  public void testGlyfspade() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "spade", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 186, 0, 1158, 1231 ) );
  }

  @Test
  public void testGlyfsterling() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "sterling", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 60, -482, 1211, 1297 ) );
  }

  @Test
  public void testGlyfsummation() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "summation", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 43, -528, 1420, 1339 ) );
  }

  @Test
  public void testGlyfsun() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "sun", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 16, -223, 1862, 1621 ) );
  }

  @Test
  public void testGlyft() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "t", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 56, -21, 605, 988 ) );
  }

  @Test
  public void testGlyfT() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "T", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -4, -25, 1233, 1331 ) );
  }

  @Test
  public void testGlyftau() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "tau", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 60, -22, 702, 805 ) );
  }

  @Test
  public void testGlyfTau() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Tau", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -4, -25, 1233, 1331 ) );
  }

  @Test
  public void testGlyftbar() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "tbar", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 39, -21, 606, 988 ) );
  }

  @Test
  public void testGlyfTbar() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Tbar", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -4, -25, 1233, 1331 ) );
  }

  @Test
  public void testGlyftcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "tcaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 56, -21, 958, 1300 ) );
  }

  @Test
  public void testGlyfTcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Tcaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -4, -25, 1233, 1768 ) );
  }

  @Test
  public void testGlyftcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "tcedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 56, -581, 605, 988 ) );
  }

  @Test
  public void testGlyfTcedilla() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Tcedilla", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -4, -589, 1233, 1331 ) );
  }

  @Test
  public void testGlyftheta() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "theta", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 80, -22, 832, 1315 ) );
  }

  @Test
  public void testGlyfTheta() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Theta", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 94, -20, 1503, 1292 ) );
  }

  @Test
  public void testGlyfthorn() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "thorn", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 24, -525, 971, 1329 ) );
  }

  @Test
  public void testGlyfThorn() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Thorn", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 38, -20, 1098, 1280 ) );
  }

  @Test
  public void testGlyfthree() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "three", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 79, -28, 869, 1304 ) );
  }

  @Test
  public void testGlyfthreeeighths() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "threeeighths", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 78, -67, 1607, 1305 ) );
  }

  @Test
  public void testGlyfthreequarters() {

    XtfBoundingBox bb =
        reader
            .mapCharCodeToBB( "threequarters", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 72, -67, 1620, 1305 ) );
  }

  @Test
  public void testGlyfthreesuperior() {

    XtfBoundingBox bb =
        reader
            .mapCharCodeToBB( "threesuperior", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 72, 610, 562, 1304 ) );
  }

  @Test
  public void testGlyftilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "tilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 88, 1033, 661, 1237 ) );
  }

  @Test
  public void testGlyftonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "tonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 260, 937, 423, 1315 ) );
  }

  @Test
  public void testGlyftrademark() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "trademark", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 30, 549, 1973, 1356 ) );
  }

  @Test
  public void testGlyftriagdn() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "triagdn", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 304, -31, 1724, 1388 ) );
  }

  @Test
  public void testGlyftriaglf() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "triaglf", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 288, -31, 1739, 1417 ) );
  }

  @Test
  public void testGlyftriagrt() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "triagrt", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 288, -31, 1739, 1417 ) );
  }

  @Test
  public void testGlyftriagup() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "triagup", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 304, 0, 1724, 1419 ) );
  }

  @Test
  public void testGlyftwo() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "two", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 45, 2, 904, 1298 ) );
  }

  @Test
  public void testGlyftwosuperior() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "twosuperior", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 51, 626, 582, 1301 ) );
  }

  @Test
  public void testGlyfu() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "u", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -19, 991, 785 ) );
  }

  @Test
  public void testGlyfU() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "U", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -34, 1384, 1286 ) );
  }

  @Test
  public void testGlyfuacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "uacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -19, 991, 1292 ) );
  }

  @Test
  public void testGlyfUacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Uacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -34, 1384, 1714 ) );
  }

  @Test
  public void testGlyfubreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ubreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -19, 991, 1233 ) );
  }

  @Test
  public void testGlyfUbreve() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ubreve", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -34, 1384, 1697 ) );
  }

  @Test
  public void testGlyfucircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ucircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -19, 991, 1332 ) );
  }

  @Test
  public void testGlyfUcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ucircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -34, 1384, 1761 ) );
  }

  @Test
  public void testGlyfudblacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "udblacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -19, 991, 1292 ) );
  }

  @Test
  public void testGlyfUdblacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Udblacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -34, 1384, 1724 ) );
  }

  @Test
  public void testGlyfudieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "udieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -19, 991, 1229 ) );
  }

  @Test
  public void testGlyfUdieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Udieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -34, 1384, 1579 ) );
  }

  @Test
  public void testGlyfugrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ugrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -19, 991, 1293 ) );
  }

  @Test
  public void testGlyfUgrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ugrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -34, 1384, 1715 ) );
  }

  @Test
  public void testGlyfumacron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "umacron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -19, 991, 1185 ) );
  }

  @Test
  public void testGlyfUmacron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Umacron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -34, 1384, 1589 ) );
  }

  @Test
  public void testGlyfundercommaaccent() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "undercommaaccent", 0, (short) 3,
                                (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 274, -435, 518, -87 ) );
  }

  @Test
  public void testGlyfunderscore() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "underscore", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -12, -256, 1036, -154 ) );
  }

  @Test
  public void testGlyfunderscoredbl() {

    XtfBoundingBox bb =
        reader
            .mapCharCodeToBB( "underscoredbl", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -12, -509, 1036, -154 ) );
  }

  @Test
  public void testGlyfuogonek() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "uogonek", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -328, 1071, 785 ) );
  }

  @Test
  public void testGlyfUogonek() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Uogonek", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -364, 1384, 1286 ) );
  }

  @Test
  public void testGlyfupblock() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "upblock", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -23, 621, 1473, 1864 ) );
  }

  @Test
  public void testGlyfupsilon() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "upsilon", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 85, -22, 780, 820 ) );
  }

  @Test
  public void testGlyfUpsilon() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Upsilon", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -13, 1168, 1292 ) );
  }

  @Test
  public void testGlyfupsilondieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "upsilondieresis", 0, (short) 3,
                                (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 85, -22, 780, 1229 ) );
  }

  @Test
  public void testGlyfUpsilondieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Upsilondieresis", 0, (short) 3,
                                (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 0, -13, 1168, 1577 ) );
  }

  @Test
  public void testGlyfupsilondieresistonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "upsilondieresistonos", 0, (short) 3,
                                (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 85, -22, 780, 1315 ) );
  }

  @Test
  public void testGlyfupsilontonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "upsilontonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 85, -22, 780, 1315 ) );
  }

  @Test
  public void testGlyfUpsilontonos() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Upsilontonos", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -44, -13, 1352, 1292 ) );
  }

  @Test
  public void testGlyfuring() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "uring", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -19, 991, 1259 ) );
  }

  @Test
  public void testGlyfUring() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Uring", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -34, 1384, 1746 ) );
  }

  @Test
  public void testGlyfutilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "utilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 33, -19, 991, 1237 ) );
  }

  @Test
  public void testGlyfUtilde() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Utilde", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 37, -34, 1384, 1585 ) );
  }

  @Test
  public void testGlyfv() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "v", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -12, -42, 978, 794 ) );
  }

  @Test
  public void testGlyfV() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "V", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -17, -39, 1406, 1287 ) );
  }

  @Test
  public void testGlyfw() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "w", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -22, -47, 1383, 789 ) );
  }

  @Test
  public void testGlyfW() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "W", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -20, -56, 1826, 1279 ) );
  }

  @Test
  public void testGlyfwacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "wacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -22, -47, 1383, 1292 ) );
  }

  @Test
  public void testGlyfWacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Wacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -20, -56, 1826, 1720 ) );
  }

  @Test
  public void testGlyfwcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "wcircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -22, -47, 1383, 1332 ) );
  }

  @Test
  public void testGlyfWcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Wcircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -20, -56, 1826, 1764 ) );
  }

  @Test
  public void testGlyfwdieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "wdieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -22, -47, 1383, 1229 ) );
  }

  @Test
  public void testGlyfWdieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Wdieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -20, -56, 1826, 1577 ) );
  }

  @Test
  public void testGlyfwgrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "wgrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -22, -47, 1383, 1293 ) );
  }

  @Test
  public void testGlyfWgrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Wgrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -20, -56, 1826, 1717 ) );
  }

  @Test
  public void testGlyfx() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "x", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 28, -1, 911, 789 ) );
  }

  @Test
  public void testGlyfX() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "X", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 10, -21, 1449, 1277 ) );
  }

  @Test
  public void testGlyfxi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "xi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 80, -300, 762, 1315 ) );
  }

  @Test
  public void testGlyfXi() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Xi", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 69, -3, 1320, 1272 ) );
  }

  @Test
  public void testGlyfy() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "y", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 7, -505, 881, 792 ) );
  }

  @Test
  public void testGlyfY() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Y", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -20, -13, 1361, 1290 ) );
  }

  @Test
  public void testGlyfyacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "yacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 7, -505, 881, 1292 ) );
  }

  @Test
  public void testGlyfYacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Yacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -20, -13, 1361, 1714 ) );
  }

  @Test
  public void testGlyfycircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ycircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 7, -505, 881, 1332 ) );
  }

  @Test
  public void testGlyfYcircumflex() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ycircumflex", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -20, -13, 1361, 1760 ) );
  }

  @Test
  public void testGlyfydieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ydieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 7, -505, 881, 1229 ) );
  }

  @Test
  public void testGlyfYdieresis() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ydieresis", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -20, -13, 1361, 1579 ) );
  }

  @Test
  public void testGlyfyen() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "yen", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -20, -13, 1361, 1290 ) );
  }

  @Test
  public void testGlyfygrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "ygrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 7, -505, 881, 1293 ) );
  }

  @Test
  public void testGlyfYgrave() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Ygrave", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( -20, -13, 1361, 1717 ) );
  }

  @Test
  public void testGlyfz() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "z", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 54, -6, 797, 866 ) );
  }

  @Test
  public void testGlyfZ() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Z", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 72, -15, 1247, 1346 ) );
  }

  @Test
  public void testGlyfzacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "zacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 54, -6, 797, 1292 ) );
  }

  @Test
  public void testGlyfZacute() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Zacute", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 72, -15, 1247, 1720 ) );
  }

  @Test
  public void testGlyfzcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "zcaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 54, -6, 797, 1332 ) );
  }

  @Test
  public void testGlyfZcaron() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Zcaron", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 72, -15, 1247, 1761 ) );
  }

  @Test
  public void testGlyfzdot() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "zdot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 54, -6, 797, 1229 ) );
  }

  @Test
  public void testGlyfZdot() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Zdot", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 72, -15, 1247, 1573 ) );
  }

  @Test
  public void testGlyfzero() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "zero", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 72, -30, 896, 1304 ) );
  }

  @Test
  public void testGlyfzeta() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "zeta", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 80, -300, 762, 1315 ) );
  }

  @Test
  public void testGlyfZeta() {

    XtfBoundingBox bb =
        reader.mapCharCodeToBB( "Zeta", 0, (short) 3, (short) 1 );
    assertNotNull( bb );
    assertTrue( bb.eq( 72, -15, 1247, 1346 ) );
  }

}
