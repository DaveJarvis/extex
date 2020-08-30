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
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Tests for the <code>XtfReader</code> with opentype files.
 * <p>
 * Table GPOS
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderFxlrGpos1Test {

    private final static String DIR_TARGET = "build";

    private final XtfReader reader;

    public XtfReaderFxlrGpos1Test() throws IOException {
        reader = new XtfReader("../ExTeX-Font-otf/src/font/fxlr.otf");
        assertNotNull( reader );
    }

    /**
     * Test the gpos table.
     */
    @Test
    public void testGpos01() {
        XtfTable table = reader.getTable(XtfReader.GPOS);
        assertNotNull( table );

        assertTrue( table instanceof OtfTableGPOS );
        OtfTableGPOS gpos = (OtfTableGPOS) table;
        assertNotNull( gpos );
    }

    /**
     * Test the coverage.
     */
    @Test
    @Ignore
    public void testGposCoverage01() {
        XtfTable table = reader.getTable(XtfReader.GPOS);
        assertNotNull( table );

        assertTrue( table instanceof OtfTableGPOS );
        OtfTableGPOS gpos = (OtfTableGPOS) table;
        assertNotNull( gpos );

        XtfLookup[] lookups =
                gpos.findLookup(ScriptTag.getDefault(), null, FeatureTag
                    .getInstance("cpsp"));

        assertNotNull( lookups );
        assertEquals( "count of singletable", 1, lookups.length );
        assertEquals( "subtable count",
                             1,
                             lookups[ 0 ].getSubtableCount() );
        XtfLookupTable subtable = lookups[0].getSubtable(0);
        assertNotNull( subtable );
        assertTrue( subtable instanceof XtfGPOSSingleTable );
        XtfGPOSSingleTable singletable = (XtfGPOSSingleTable) subtable;

        XtfCoverage coverage = singletable.getCoverage();
        assertNotNull( coverage );
        int[] glyphs = coverage.getGlyphs();
        assertNotNull( glyphs );
        assertEquals( "glyph count", 121, glyphs.length );

        String[] gNames =
                {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                        "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
                        "X", "Y", "Z", "Agrave", "Aacute", "Acircumflex",
                        "Atilde", "Adieresis", "Aring", "AE", "Ccedilla",
                        "Egrave", "Eacute", "Ecircumflex", "Edieresis",
                        "Igrave", "Iacute", "Icircumflex", "Idieresis", "Eth",
                        "Ntilde", "Ograve", "Oacute", "Ocircumflex", "Otilde",
                        "Odieresis", "Oslash", "Ugrave", "Uacute",
                        "Ucircumflex", "Udieresis", "Yacute", "Thorn",
                        "Amacron", "Abreve", "Aogonek", "Cacute",
                        "Ccircumflex", "Cdotaccent", "Ccaron", "Dcaron",
                        "Dcroat", "Emacron", "Ebreve", "Edotaccent", "Eogonek",
                        "Ecaron", "Gcircumflex", "Gbreve", "Gdotaccent",
                        "Gcommaaccent", "Hcircumflex", "Hbar", "Itilde",
                        "Imacron", "Ibreve", "Iogonek", "Idotaccent", "IJ",
                        "Jcircumflex", "Kcommaaccent", "Lcommaaccent",
                        "Lcaron", "Ldot", "Lslash", "Nacute", "Ncommaaccent",
                        "Ncaron", "Eng", "Omacron", "Obreve", "Ohungarumlaut",
                        "OE", "Racute", "Rcommaaccent", "Rcaron", "Sacute",
                        "Scircumflex", "Scedilla", "Scaron", "Tcommaaccent",
                        "Tcaron", "Tbar", "Utilde", "Umacron", "Ubreve",
                        "Uring", "Uhungarumlaut", "Uogonek", "Wcircumflex",
                        "Ycircumflex", "Ydieresis", "Zacute", "Zcaron",
                        "AEacute", "Adieresis.alt", "Odieresis.alt",
                        "Udieresis.alt"};

        for( int i = 0; i < gNames.length; i++ ) {
          assertEquals(
              gNames[ i ],
              coverage.getXtfGlyph().getGlyphName( glyphs[ i ] ) );
        }
    }

    /**
     * Test the coverage.
     */
    @Test
    @Ignore
    public void testGposCoverage02() {
        XtfTable table = reader.getTable(XtfReader.GPOS);
        assertNotNull( table );

        assertTrue( table instanceof OtfTableGPOS );
        OtfTableGPOS gpos = (OtfTableGPOS) table;
        assertNotNull( gpos );

        XtfLookup[] lookups =
                gpos.findLookup(ScriptTag.getDefault(), null, FeatureTag
                    .getInstance("kern"));

        assertNotNull( lookups );
        assertEquals( "count of paittable", 1, lookups.length );
        assertEquals( "subtable count",
                             1,
                             lookups[ 0 ].getSubtableCount() );
        XtfLookupTable subtable = lookups[0].getSubtable(0);
        assertNotNull( subtable );
        assertTrue( subtable instanceof XtfGPOSPairTable );
        XtfGPOSPairTable pairtable = (XtfGPOSPairTable) subtable;

        XtfCoverage coverage = pairtable.getCoverage();
        assertNotNull( coverage );
        int[] glyphs = coverage.getGlyphs();
        assertNotNull( glyphs );

        String[] gNames =
                {"space", "parenleft", "plus", "comma", "hyphen", "period",
                        "less", "equal", "greater", "at", "A", "D", "F", "H",
                        "I", "K", "N", "O", "P", "Q", "T", "U", "V", "W", "X",
                        "Y", "bracketleft", "underscore", "b", "e", "f", "g",
                        "k", "o", "p", "r", "v", "w", "x", "y", "braceleft",
                        "asciitilde", "guillemotleft", "uni00AD",
                        "periodcentered", "guillemotright", "Agrave", "Aacute",
                        "Acircumflex", "Atilde", "Adieresis", "Aring",
                        "Igrave", "Iacute", "Icircumflex", "Idieresis",
                        "Ntilde", "Ograve", "Oacute", "Ocircumflex", "Otilde",
                        "Odieresis", "Oslash", "Ugrave", "Uacute",
                        "Ucircumflex", "Udieresis", "Yacute", "Thorn",
                        "agrave", "egrave", "eacute", "ecircumflex",
                        "edieresis", "ograve", "oacute", "ocircumflex",
                        "otilde", "odieresis", "divide", "yacute", "thorn",
                        "ydieresis", "Amacron", "Abreve", "Aogonek", "Dcaron",
                        "Dcroat", "gcircumflex", "gbreve", "gdotaccent",
                        "gcommaaccent", "Hcircumflex", "Hbar", "Imacron",
                        "Ibreve", "Iogonek", "Idotaccent", "IJ", "Jcircumflex",
                        "Kcommaaccent", "kcommaaccent", "kgreenlandic",
                        "Nacute", "Ncommaaccent", "Ncaron", "Eng", "Omacron",
                        "Obreve", "Ohungarumlaut", "racute", "rcommaaccent",
                        "rcaron", "Tcommaaccent", "Tcaron", "Utilde",
                        "Umacron", "Ubreve", "Uring", "Uhungarumlaut",
                        "Uogonek", "Wcircumflex", "wcircumflex", "Ycircumflex",
                        "ycircumflex", "Ydieresis", "longs", "uni0186",
                        "uni0189", "uni018A", "uni018E", "uni018F", "uni01A4",
                        "uni01AC", "uni01CD", "uni01D3", "uni01D5", "uni01D7",
                        "uni01D9", "uni01DB", "uni01DE", "uni01E0", "gcaron",
                        "uni01E9", "uni01EA", "uni01EC", "Aringacute",
                        "uni0200", "uni0202", "uni020C", "uni020E", "uni0214",
                        "uni0216", "uni021A", "uni021E", "uni0226", "uni022A",
                        "uni022C", "uni022E", "uni0230", "Alphatonos",
                        "Omicrontonos", "Upsilontonos", "Alpha", "Gamma",
                        "Delta", "Eta", "Theta", "Iota", "Kappa", "Lambda",
                        "Nu", "Omicron", "Pi", "Rho", "Tau", "Upsilon", "Phi",
                        "Chi", "Upsilondieresis", "kappa", "omicron", "rho",
                        "phi", "omicrontonos", "Upsilon1", "uni03D3",
                        "uni03D4", "phi1", "uni03D8", "uni03DB", "uni03F4",
                        "uni03FD", "uni03FF", "afii10052", "afii10055",
                        "afii10056", "afii10057", "afii10061", "uni040D",
                        "afii10062", "afii10145", "afii10017", "afii10020",
                        "afii10021", "afii10024", "afii10026", "afii10027",
                        "afii10028", "afii10029", "afii10031", "afii10032",
                        "afii10033", "afii10034", "afii10036", "afii10037",
                        "afii10039", "afii10040", "afii10042", "afii10043",
                        "afii10045", "afii10048", "afii10049", "afii10070",
                        "afii10072", "afii10076", "afii10080", "afii10082",
                        "afii10085", "afii10086", "uni0450", "afii10071",
                        "afii10109", "afii10110", "afii10147", "afii10148",
                        "uni0476", "afii10050", "uni0492", "uni0496",
                        "uni049A", "uni049C", "uni049E", "uni049F", "uni04A0",
                        "uni04A1", "uni04A2", "uni04AC", "uni04B2", "uni04B3",
                        "uni04C1", "uni04D2", "uni04DA", "uni04DC", "uni04DD",
                        "uni04E2", "uni04E4", "uni04E6", "uni04E7", "uni04E8",
                        "uni04E9", "uni04EA", "uni04EC", "uni04EE", "uni04EF",
                        "uni04F0", "uni04F1", "uni04F2", "uni04F6", "uni04F8",
                        "uni1E00", "uni1E0A", "uni1E0C", "uni1E0E", "uni1E10",
                        "uni1E12", "uni1E1E", "uni1E1F", "uni1E22", "uni1E24",
                        "uni1E26", "uni1E28", "uni1E2A", "uni1E2C", "uni1E2E",
                        "uni1E30", "uni1E31", "uni1E32", "uni1E33", "uni1E34",
                        "uni1E35", "uni1E44", "uni1E46", "uni1E48", "uni1E4A",
                        "uni1E4C", "uni1E4E", "uni1E50", "uni1E52", "uni1E54",
                        "uni1E56", "uni1E6A", "uni1E6C", "uni1E6E", "uni1E70",
                        "uni1E72", "uni1E74", "uni1E76", "uni1E78", "uni1E7A",
                        "uni1E7C", "uni1E7D", "uni1E7E", "uni1E7F", "Wgrave",
                        "wgrave", "Wacute", "wacute", "Wdieresis", "wdieresis",
                        "uni1E86", "uni1E87", "uni1E88", "uni1E89", "uni1E8A",
                        "uni1E8C", "uni1E8E", "uni1E8F", "uni1E98", "uni1EA0",
                        "uni1EA2", "uni1EA4", "uni1EA6", "uni1EA8", "uni1EAA",
                        "uni1EAC", "uni1EAE", "uni1EB0", "uni1EB2", "uni1EB4",
                        "uni1EB6", "uni1EC8", "uni1ECA", "uni1EE4", "uni1EE6",
                        "uni1EE8", "uni1EEA", "uni1EEC", "uni1EEE", "uni1EF0",
                        "Ygrave", "uni1EF4", "uni1EF6", "uni1EF8", "uni1F08",
                        "uni1F09", "uni1F0A", "uni1F0B", "uni1F0C", "uni1F0D",
                        "uni1F0E", "uni1F0F", "uni1F28", "uni1F29", "uni1F2A",
                        "uni1F2B", "uni1F2C", "uni1F2D", "uni1F2E", "uni1F2F",
                        "uni1F38", "uni1F39", "uni1F3A", "uni1F3B", "uni1F3C",
                        "uni1F3D", "uni1F3E", "uni1F3F", "uni1F59", "uni1F5B",
                        "uni1F5D", "uni1F5F", "uni1FB8", "uni1FB9", "uni1FBA",
                        "uni1FBB", "uni1FCA", "uni1FCB", "uni1FD8", "uni1FD9",
                        "uni1FDA", "uni1FDB", "uni1FE8", "uni1FE9", "uni1FEA",
                        "uni1FEB", "enquad", "emquad", "enspace", "emspace",
                        "threeperemspace", "fourperemspace", "sixperemspace",
                        "figurespace", "punctuationspace", "thinspace",
                        "hairspace", "endash", "emdash", "horizontalbar",
                        "underscoredbl", "quotesinglbase", "quotedblbase",
                        "onedotenleader", "twodotenleader", "ellipsis",
                        "uni202F", "zero.inferior", "one.inferior",
                        "two.inferior", "three.inferior", "four.inferior",
                        "five.inferior", "six.inferior", "seven.inferior",
                        "eight.inferior", "nine.inferior", "plus.inferior",
                        "minus.inferior", "equal.inferior", "a.inferior",
                        "e.inferior", "o.inferior", "x.inferior", "uni2094",
                        "uni2C69", "Adieresis.alt", "Odieresis.alt", "f_b",
                        "f_f_b", "f_f_k", "f_k", "c_k", "d.sc", "k.sc", "o.sc",
                        "q.sc", "v.sc", "w.sc", "x.sc", "y.sc", "hyphen.sc",
                        "ograve.sc", "oacute.sc", "ocircumflex.sc",
                        "otilde.sc", "odieresis.sc", "oslash.sc", "yacute.sc",
                        "ydieresis.sc", "W.alt", "V.alt", "K.alt", "f_f"};

        assertEquals( "glyph count", gNames.length, glyphs.length );

        for (int i = 0; i < gNames.length; i++) {
            assertEquals( gNames[ i ],
                                 coverage.getXtfGlyph().getGlyphName(
                                     glyphs[ i ] ) );
        }

    }

    /**
     * Test the pair pos.
     */
    @Test
    public void testGposPair01() {
        XtfTable table = reader.getTable(XtfReader.GPOS);
        assertNotNull( table );

        assertTrue( table instanceof OtfTableGPOS );
        OtfTableGPOS gpos = (OtfTableGPOS) table;
        assertNotNull( gpos );

        XtfLookup[] lookups =
                gpos.findLookup(ScriptTag.getInstance("DFLT"), null, FeatureTag
                    .getInstance("kern"));

        assertNotNull( lookups );
        assertEquals( "count of paittable", 1, lookups.length );
        assertEquals( "subtable count",
                             1,
                             lookups[ 0 ].getSubtableCount() );
        XtfLookupTable subtable = lookups[0].getSubtable(0);
        assertNotNull( subtable );
        assertTrue( subtable instanceof XtfGPOSPairTable );
        XtfGPOSPairTable pairtable = (XtfGPOSPairTable) subtable;

        // space (1) class 12, dotlessj (2300) class 12
        // 1: class="12" 2: class="0" xAdvance="0"
        PairValue pv = pairtable.getPairValue(1, 1);
        assertNotNull( pv );
        assertNotNull( pv.getValue1() );
        assertNotNull( pv.getValue2() );
        assertTrue( pv.getValue1().isXAdvance() );
        assertEquals( "xadvanced", 0, pv.getValue1().getXAdvance() );

        // f_f (2301) class 1, space (1) class 0
        // xAdvance="0"
        pv = pairtable.getPairValue(1, 1);
        assertNotNull( pv );
        assertNotNull( pv.getValue1() );
        assertNotNull( pv.getValue2() );
        assertTrue( pv.getValue1().isXAdvance() );
        assertEquals( "xadvanced", 0, pv.getValue1().getXAdvance() );

        // f (71) class 1, parenright (10) class 13
        // xAdvance="61"
        pv = pairtable.getPairValue(71, 10);
        assertNotNull( pv );
        assertNotNull( pv.getValue1() );
        assertNotNull( pv.getValue2() );
        assertTrue( pv.getValue1().isXAdvance() );
        assertEquals( "xadvanced", 61, pv.getValue1().getXAdvance() );

    }

    /**
     * Test the coverage.
     */
    @Test
    public void testGposSingle01() {
        XtfTable table = reader.getTable(XtfReader.GPOS);
        assertNotNull( table );

        assertTrue( table instanceof OtfTableGPOS );
        OtfTableGPOS gpos = (OtfTableGPOS) table;
        assertNotNull( gpos );

        XtfLookup[] lookups =
                gpos.findLookup(ScriptTag.getInstance("DFLT"), null, FeatureTag
                    .getInstance("cpsp"));

        assertNotNull( lookups );
        assertEquals( "count of singletable", 1, lookups.length );
        assertEquals( "subtable count",
                             1,
                             lookups[ 0 ].getSubtableCount() );
        XtfLookupTable subtable = lookups[0].getSubtable(0);
        assertNotNull( subtable );
        assertTrue( subtable instanceof XtfGPOSSingleTable );
        XtfGPOSSingleTable singletable = (XtfGPOSSingleTable) subtable;

        ValueRecord vr = singletable.getValueRecord(-1);
        assertNull( vr );

        // A (34) - XPlacement="2" XAdvance="5"
        vr = singletable.getValueRecord(34);
        assertNotNull( vr );
        assertTrue( vr.isXPlacement() );
        assertTrue( vr.isXAdvance() );
        assertEquals( 2, vr.getXPlacement() );
        assertEquals( 5, vr.getXAdvance() );

    }

    /**
     * Test the name of the font.
     */
    @Test
    public void testName() {
        assertEquals( "Linux Libertine", reader.getFontFamilyName() );
        assertEquals( 2309, reader.getNumberOfGlyphs() );
    }

    /**
     * Test: write the xml output to 'tartet'
     */
    @Test
    public void testXmlOut() throws IOException {
        XMLStreamWriter writer =
                new XMLStreamWriter(new FileOutputStream(DIR_TARGET + "/fxlr.xml"),
                    "ISO8859-1");
        writer.setBeauty(true);
        writer.writeStartDocument();
        reader.writeXML(writer);
        writer.writeEndDocument();
        writer.close();

    }
}
