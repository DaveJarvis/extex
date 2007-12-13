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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.IOException;

import org.extex.font.format.xtf.tables.XtfTable;
import org.extex.font.format.xtf.tables.gps.OtfTableGSUB;
import org.extex.font.format.xtf.tables.gps.XtfGSUBSingleTable;
import org.extex.font.format.xtf.tables.gps.XtfLookup;
import org.extex.font.format.xtf.tables.gps.XtfLookupTable;
import org.extex.font.format.xtf.tables.tag.FeatureTag;
import org.extex.font.format.xtf.tables.tag.ScriptTag;
import org.extex.util.xml.XMLStreamWriter;
import org.junit.Test;

/**
 * Tests for the <code>XtfReader</code> with opentype files (GSUB-Single).
 * <p>
 * data from ttx
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderFxlrGsubSingleTest {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderFxlrGsubSingleTest() throws IOException {

        if (reader == null) {
            reader = new XtfReader("../ExTeX-Font-otf/src/font/fxlr.otf");
        }
    }

    /**
     * Check.
     * 
     * @param featureTag The feature tag.
     * @param in The in glyph name.
     * @param out The out glyph name.
     */
    private void check(String featureTag, String in, String out) {

        assertNotNull(reader);
        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGSUB);
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull(gsub);

        XtfLookup[] lookups =
                gsub.findLookup(ScriptTag.getInstance("DFLT"), null, FeatureTag
                    .getInstance(featureTag));
        assertNotNull(lookups);
        assertEquals(1, lookups.length);
        assertEquals(1, lookups[0].getSubtableCount());
        assertEquals("Single", lookups[0].getTypeAsString());

        int cnt = lookups[0].getSubtableCount();
        boolean found = false;
        for (int i = 0; i < cnt; i++) {
            XtfLookupTable subtable = lookups[0].getSubtable(i);
            assertTrue(subtable instanceof XtfGSUBSingleTable);
            XtfGSUBSingleTable single = (XtfGSUBSingleTable) subtable;
            String[][] glyphInOut = single.getSubGlyph();
            assertNotNull(glyphInOut);

            for (int k = 0; k < glyphInOut.length; k++) {
                String inX = glyphInOut[k][0];
                String outX = glyphInOut[k][1];
                if (in.equals(inX)) {
                    if (out.equals(outX)) {
                        found = true;
                        break;
                    }
                }
            }
            if (found) {
                break;
            }
        }
        assertTrue("Single found " + in + " " + out, found);

    }

    /**
     * Test, if the reader exits.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testExists() throws Exception {

        assertNotNull(reader);
    }

    /**
     * test a.
     */
    @Test
    public void testGSUBSingle_a() {

        check("sups", "a", "a.superior");
    }

    /**
     * test b.
     */
    @Test
    public void testGSUBSingle_b() {

        check("sups", "b", "b.superior");
    }

    /**
     * test c.
     */
    @Test
    public void testGSUBSingle_c() {

        check("sups", "c", "c.superior");
    }

    /**
     * test A.
     */
    @Test
    public void testGSUBSingle_c2sc_A() {

        check("c2sc", "A", "a.sc");
    }

    /**
     * test Aacute.
     */
    @Test
    public void testGSUBSingle_c2sc_Aacute() {

        check("c2sc", "Aacute", "aacute.sc");
    }

    /**
     * test Abreve.
     */
    @Test
    public void testGSUBSingle_c2sc_Abreve() {

        check("c2sc", "Abreve", "abreve.sc");
    }

    /**
     * test Acircumflex.
     */
    @Test
    public void testGSUBSingle_c2sc_Acircumflex() {

        check("c2sc", "Acircumflex", "acircumflex.sc");
    }

    /**
     * test Adieresis.
     */
    @Test
    public void testGSUBSingle_c2sc_Adieresis() {

        check("c2sc", "Adieresis", "adieresis.sc");
    }

    /**
     * test Adieresis.alt.
     */
    @Test
    public void testGSUBSingle_c2sc_Adieresisalt() {

        check("c2sc", "Adieresis.alt", "adieresis.sc");
    }

    /**
     * test AE.
     */
    @Test
    public void testGSUBSingle_c2sc_AE() {

        check("c2sc", "AE", "ae.sc");
    }

    /**
     * test Agrave.
     */
    @Test
    public void testGSUBSingle_c2sc_Agrave() {

        check("c2sc", "Agrave", "agrave.sc");
    }

    /**
     * test ampersand.
     */
    @Test
    public void testGSUBSingle_c2sc_ampersand() {

        check("c2sc", "ampersand", "ampersand.alt");
    }

    /**
     * test Aogonek.
     */
    @Test
    public void testGSUBSingle_c2sc_Aogonek() {

        check("c2sc", "Aogonek", "aogonek.sc");
    }

    /**
     * test Aring.
     */
    @Test
    public void testGSUBSingle_c2sc_Aring() {

        check("c2sc", "Aring", "aring.sc");
    }

    /**
     * test Atilde.
     */
    @Test
    public void testGSUBSingle_c2sc_Atilde() {

        check("c2sc", "Atilde", "atilde.sc");
    }

    /**
     * test B.
     */
    @Test
    public void testGSUBSingle_c2sc_B() {

        check("c2sc", "B", "b.sc");
    }

    /**
     * test braceleft.
     */
    @Test
    public void testGSUBSingle_c2sc_braceleft() {

        check("c2sc", "braceleft", "braceleft.sc");
    }

    /**
     * test braceright.
     */
    @Test
    public void testGSUBSingle_c2sc_braceright() {

        check("c2sc", "braceright", "braceright.sc");
    }

    /**
     * test bracketleft.
     */
    @Test
    public void testGSUBSingle_c2sc_bracketleft() {

        check("c2sc", "bracketleft", "bracketleft.sc");
    }

    /**
     * test bracketright.
     */
    @Test
    public void testGSUBSingle_c2sc_bracketright() {

        check("c2sc", "bracketright", "bracketright.sc");
    }

    /**
     * test C.
     */
    @Test
    public void testGSUBSingle_c2sc_C() {

        check("c2sc", "C", "c.sc");
    }

    /**
     * test Cacute.
     */
    @Test
    public void testGSUBSingle_c2sc_Cacute() {

        check("c2sc", "Cacute", "cacute.sc");
    }

    /**
     * test Ccaron.
     */
    @Test
    public void testGSUBSingle_c2sc_Ccaron() {

        check("c2sc", "Ccaron", "ccaron.sc");
    }

    /**
     * test Ccedilla.
     */
    @Test
    public void testGSUBSingle_c2sc_Ccedilla() {

        check("c2sc", "Ccedilla", "ccedilla.sc");
    }

    /**
     * test D.
     */
    @Test
    public void testGSUBSingle_c2sc_D() {

        check("c2sc", "D", "d.sc");
    }

    /**
     * test Dcaron.
     */
    @Test
    public void testGSUBSingle_c2sc_Dcaron() {

        check("c2sc", "Dcaron", "dcaron.sc");
    }

    /**
     * test Dcroat.
     */
    @Test
    public void testGSUBSingle_c2sc_Dcroat() {

        check("c2sc", "Dcroat", "dcroat.sc");
    }

    /**
     * test E.
     */
    @Test
    public void testGSUBSingle_c2sc_E() {

        check("c2sc", "E", "e.sc");
    }

    /**
     * test Eacute.
     */
    @Test
    public void testGSUBSingle_c2sc_Eacute() {

        check("c2sc", "Eacute", "eacute.sc");
    }

    /**
     * test Ecaron.
     */
    @Test
    public void testGSUBSingle_c2sc_Ecaron() {

        check("c2sc", "Ecaron", "ecaron.sc");
    }

    /**
     * test Ecircumflex.
     */
    @Test
    public void testGSUBSingle_c2sc_Ecircumflex() {

        check("c2sc", "Ecircumflex", "ecircumflex.sc");
    }

    /**
     * test Edieresis.
     */
    @Test
    public void testGSUBSingle_c2sc_Edieresis() {

        check("c2sc", "Edieresis", "edieresis.sc");
    }

    /**
     * test Egrave.
     */
    @Test
    public void testGSUBSingle_c2sc_Egrave() {

        check("c2sc", "Egrave", "egrave.sc");
    }

    /**
     * test Eng.
     */
    @Test
    public void testGSUBSingle_c2sc_Eng() {

        check("c2sc", "Eng", "eng.sc");
    }

    /**
     * test Eogonek.
     */
    @Test
    public void testGSUBSingle_c2sc_Eogonek() {

        check("c2sc", "Eogonek", "eogonek.sc");
    }

    /**
     * test Eth.
     */
    @Test
    public void testGSUBSingle_c2sc_Eth() {

        check("c2sc", "Eth", "eth.sc");
    }

    /**
     * test exclamdown.
     */
    @Test
    public void testGSUBSingle_c2sc_exclamdown() {

        check("c2sc", "exclamdown", "exclamdown.sc");
    }

    /**
     * test F.
     */
    @Test
    public void testGSUBSingle_c2sc_F() {

        check("c2sc", "F", "f.sc");
    }

    /**
     * test G.
     */
    @Test
    public void testGSUBSingle_c2sc_G() {

        check("c2sc", "G", "g.sc");
    }

    /**
     * test Gbreve.
     */
    @Test
    public void testGSUBSingle_c2sc_Gbreve() {

        check("c2sc", "Gbreve", "gbreve.sc");
    }

    /**
     * test germandbls.
     */
    @Test
    public void testGSUBSingle_c2sc_germandbls() {

        check("c2sc", "germandbls", "germandbls.sc");
    }

    /**
     * test Germandbls.
     */
    @Test
    public void testGSUBSingle_c2sc_Germandbls() {

        check("c2sc", "Germandbls", "germandbls.sc");
    }

    /**
     * test guillemotleft.
     */
    @Test
    public void testGSUBSingle_c2sc_guillemotleft() {

        check("c2sc", "guillemotleft", "guillemotleft.sc");
    }

    /**
     * test guillemotright.
     */
    @Test
    public void testGSUBSingle_c2sc_guillemotright() {

        check("c2sc", "guillemotright", "guillemotright.sc");
    }

    /**
     * test guilsinglleft.
     */
    @Test
    public void testGSUBSingle_c2sc_guilsinglleft() {

        check("c2sc", "guilsinglleft", "guilsinglleft.sc");
    }

    /**
     * test guilsinglright.
     */
    @Test
    public void testGSUBSingle_c2sc_guilsinglright() {

        check("c2sc", "guilsinglright", "guilsinglright.sc");
    }

    /**
     * test H.
     */
    @Test
    public void testGSUBSingle_c2sc_H() {

        check("c2sc", "H", "h.sc");
    }

    /**
     * test hyphen.
     */
    @Test
    public void testGSUBSingle_c2sc_hyphen() {

        check("c2sc", "hyphen", "hyphen.sc");
    }

    /**
     * test I.
     */
    @Test
    public void testGSUBSingle_c2sc_I() {

        check("c2sc", "I", "i.sc");
    }

    /**
     * test Iacute.
     */
    @Test
    public void testGSUBSingle_c2sc_Iacute() {

        check("c2sc", "Iacute", "iacute.sc");
    }

    /**
     * test Icircumflex.
     */
    @Test
    public void testGSUBSingle_c2sc_Icircumflex() {

        check("c2sc", "Icircumflex", "icircumflex.sc");
    }

    /**
     * test Idieresis.
     */
    @Test
    public void testGSUBSingle_c2sc_Idieresis() {

        check("c2sc", "Idieresis", "idieresis.sc");
    }

    /**
     * test Igrave.
     */
    @Test
    public void testGSUBSingle_c2sc_Igrave() {

        check("c2sc", "Igrave", "igrave.sc");
    }

    /**
     * test IJ.
     */
    @Test
    public void testGSUBSingle_c2sc_IJ() {

        check("c2sc", "IJ", "ij.sc");
    }

    /**
     * test J.
     */
    @Test
    public void testGSUBSingle_c2sc_J() {

        check("c2sc", "J", "j.sc");
    }

    /**
     * test K.
     */
    @Test
    public void testGSUBSingle_c2sc_K() {

        check("c2sc", "K", "k.sc");
    }

    /**
     * test L.
     */
    @Test
    public void testGSUBSingle_c2sc_L() {

        check("c2sc", "L", "l.sc");
    }

    /**
     * test Lacute.
     */
    @Test
    public void testGSUBSingle_c2sc_Lacute() {

        check("c2sc", "Lacute", "lacute.sc");
    }

    /**
     * test Lcaron.
     */
    @Test
    public void testGSUBSingle_c2sc_Lcaron() {

        check("c2sc", "Lcaron", "lcaron.sc");
    }

    /**
     * test Lslash.
     */
    @Test
    public void testGSUBSingle_c2sc_Lslash() {

        check("c2sc", "Lslash", "lslash.sc");
    }

    /**
     * test M.
     */
    @Test
    public void testGSUBSingle_c2sc_M() {

        check("c2sc", "M", "m.sc");
    }

    /**
     * test N.
     */
    @Test
    public void testGSUBSingle_c2sc_N() {

        check("c2sc", "N", "n.sc");
    }

    /**
     * test Nacute.
     */
    @Test
    public void testGSUBSingle_c2sc_Nacute() {

        check("c2sc", "Nacute", "nacute.sc");
    }

    /**
     * test Ncaron.
     */
    @Test
    public void testGSUBSingle_c2sc_Ncaron() {

        check("c2sc", "Ncaron", "ncaron.sc");
    }

    /**
     * test Ntilde.
     */
    @Test
    public void testGSUBSingle_c2sc_Ntilde() {

        check("c2sc", "Ntilde", "ntilde.sc");
    }

    /**
     * test O.
     */
    @Test
    public void testGSUBSingle_c2sc_O() {

        check("c2sc", "O", "o.sc");
    }

    /**
     * test Oacute.
     */
    @Test
    public void testGSUBSingle_c2sc_Oacute() {

        check("c2sc", "Oacute", "oacute.sc");
    }

    /**
     * test Ocircumflex.
     */
    @Test
    public void testGSUBSingle_c2sc_Ocircumflex() {

        check("c2sc", "Ocircumflex", "ocircumflex.sc");
    }

    /**
     * test Odieresis.
     */
    @Test
    public void testGSUBSingle_c2sc_Odieresis() {

        check("c2sc", "Odieresis", "odieresis.sc");
    }

    /**
     * test Odieresis.alt.
     */
    @Test
    public void testGSUBSingle_c2sc_Odieresisalt() {

        check("c2sc", "Odieresis.alt", "odieresis.sc");
    }

    /**
     * test Ograve.
     */
    @Test
    public void testGSUBSingle_c2sc_Ograve() {

        check("c2sc", "Ograve", "ograve.sc");
    }

    /**
     * test Ohungarumlaut.
     */
    @Test
    public void testGSUBSingle_c2sc_Ohungarumlaut() {

        check("c2sc", "Ohungarumlaut", "ohungarumlaut.sc");
    }

    /**
     * test Oslash.
     */
    @Test
    public void testGSUBSingle_c2sc_Oslash() {

        check("c2sc", "Oslash", "oslash.sc");
    }

    /**
     * test Otilde.
     */
    @Test
    public void testGSUBSingle_c2sc_Otilde() {

        check("c2sc", "Otilde", "otilde.sc");
    }

    /**
     * test P.
     */
    @Test
    public void testGSUBSingle_c2sc_P() {

        check("c2sc", "P", "p.sc");
    }

    /**
     * test parenleft.
     */
    @Test
    public void testGSUBSingle_c2sc_parenleft() {

        check("c2sc", "parenleft", "parenleft.sc");
    }

    /**
     * test parenright.
     */
    @Test
    public void testGSUBSingle_c2sc_parenright() {

        check("c2sc", "parenright", "parenright.sc");
    }

    /**
     * test Q.
     */
    @Test
    public void testGSUBSingle_c2sc_Q() {

        check("c2sc", "Q", "q.sc");
    }

    /**
     * test questiondown.
     */
    @Test
    public void testGSUBSingle_c2sc_questiondown() {

        check("c2sc", "questiondown", "questiondown.sc");
    }

    /**
     * test R.
     */
    @Test
    public void testGSUBSingle_c2sc_R() {

        check("c2sc", "R", "r.sc");
    }

    /**
     * test Racute.
     */
    @Test
    public void testGSUBSingle_c2sc_Racute() {

        check("c2sc", "Racute", "racute.sc");
    }

    /**
     * test Rcaron.
     */
    @Test
    public void testGSUBSingle_c2sc_Rcaron() {

        check("c2sc", "Rcaron", "rcaron.sc");
    }

    /**
     * test S.
     */
    @Test
    public void testGSUBSingle_c2sc_S() {

        check("c2sc", "S", "s.sc");
    }

    /**
     * test Sacute.
     */
    @Test
    public void testGSUBSingle_c2sc_Sacute() {

        check("c2sc", "Sacute", "sacute.sc");
    }

    /**
     * test Scaron.
     */
    @Test
    public void testGSUBSingle_c2sc_Scaron() {

        check("c2sc", "Scaron", "scaron.sc");
    }

    /**
     * test Scedilla.
     */
    @Test
    public void testGSUBSingle_c2sc_Scedilla() {

        check("c2sc", "Scedilla", "scedilla.sc");
    }

    /**
     * test T.
     */
    @Test
    public void testGSUBSingle_c2sc_T() {

        check("c2sc", "T", "t.sc");
    }

    /**
     * test Tbar.
     */
    @Test
    public void testGSUBSingle_c2sc_Tbar() {

        check("c2sc", "Tbar", "tbar.sc");
    }

    /**
     * test Tcaron.
     */
    @Test
    public void testGSUBSingle_c2sc_Tcaron() {

        check("c2sc", "Tcaron", "tcaron.sc");
    }

    /**
     * test Tcommaaccent.
     */
    @Test
    public void testGSUBSingle_c2sc_Tcommaaccent() {

        check("c2sc", "Tcommaaccent", "tcommaaccent.sc");
    }

    /**
     * test Thorn.
     */
    @Test
    public void testGSUBSingle_c2sc_Thorn() {

        check("c2sc", "Thorn", "thorn.sc");
    }

    /**
     * test U.
     */
    @Test
    public void testGSUBSingle_c2sc_U() {

        check("c2sc", "U", "u.sc");
    }

    /**
     * test Uacute.
     */
    @Test
    public void testGSUBSingle_c2sc_Uacute() {

        check("c2sc", "Uacute", "uacute.sc");
    }

    /**
     * test Ucircumflex.
     */
    @Test
    public void testGSUBSingle_c2sc_Ucircumflex() {

        check("c2sc", "Ucircumflex", "ucircumflex.sc");
    }

    /**
     * test Udieresis.
     */
    @Test
    public void testGSUBSingle_c2sc_Udieresis() {

        check("c2sc", "Udieresis", "udieresis.sc");
    }

    /**
     * test Udieresis.alt.
     */
    @Test
    public void testGSUBSingle_c2sc_Udieresisalt() {

        check("c2sc", "Udieresis.alt", "udieresis.sc");
    }

    /**
     * test Ugrave.
     */
    @Test
    public void testGSUBSingle_c2sc_Ugrave() {

        check("c2sc", "Ugrave", "ugrave.sc");
    }

    /**
     * test Uhungarumlaut.
     */
    @Test
    public void testGSUBSingle_c2sc_Uhungarumlaut() {

        check("c2sc", "Uhungarumlaut", "uhungarumlaut.sc");
    }

    /**
     * test Uring.
     */
    @Test
    public void testGSUBSingle_c2sc_Uring() {

        check("c2sc", "Uring", "uring.sc");
    }

    /**
     * test V.
     */
    @Test
    public void testGSUBSingle_c2sc_V() {

        check("c2sc", "V", "v.sc");
    }

    /**
     * test W.
     */
    @Test
    public void testGSUBSingle_c2sc_W() {

        check("c2sc", "W", "w.sc");
    }

    /**
     * test X.
     */
    @Test
    public void testGSUBSingle_c2sc_X() {

        check("c2sc", "X", "x.sc");
    }

    /**
     * test Y.
     */
    @Test
    public void testGSUBSingle_c2sc_Y() {

        check("c2sc", "Y", "y.sc");
    }

    /**
     * test Yacute.
     */
    @Test
    public void testGSUBSingle_c2sc_Yacute() {

        check("c2sc", "Yacute", "yacute.sc");
    }

    /**
     * test Ydieresis.
     */
    @Test
    public void testGSUBSingle_c2sc_Ydieresis() {

        check("c2sc", "Ydieresis", "ydieresis.sc");
    }

    /**
     * test Z.
     */
    @Test
    public void testGSUBSingle_c2sc_Z() {

        check("c2sc", "Z", "z.sc");
    }

    /**
     * test Zacute.
     */
    @Test
    public void testGSUBSingle_c2sc_Zacute() {

        check("c2sc", "Zacute", "zacute.sc");
    }

    /**
     * test Zcaron.
     */
    @Test
    public void testGSUBSingle_c2sc_Zcaron() {

        check("c2sc", "Zcaron", "zcaron.sc");
    }

    /**
     * test Zdotaccent.
     */
    @Test
    public void testGSUBSingle_c2sc_Zdotaccent() {

        check("c2sc", "Zdotaccent", "zdotaccent.sc");
    }

    /**
     * test d.
     */
    @Test
    public void testGSUBSingle_d() {

        check("sups", "d", "d.superior");
    }

    /**
     * test e.
     */
    @Test
    public void testGSUBSingle_e() {

        check("sups", "e", "e.superior");
    }

    /**
     * test eight.
     */
    @Test
    public void testGSUBSingle_eight() {

        check("sups", "eight", "eight.superior");
    }

    /**
     * test eight.oldstyle.
     */
    @Test
    public void testGSUBSingle_eightoldstyle() {

        check("sups", "eight.oldstyle", "eight.superior");
    }

    /**
     * test equal.
     */
    @Test
    public void testGSUBSingle_equal() {

        check("sups", "equal", "equal.superior");
    }

    /**
     * test f.
     */
    @Test
    public void testGSUBSingle_f() {

        check("sups", "f", "f.superior");
    }

    /**
     * test sigma.
     */
    @Test
    public void testGSUBSingle_fina_sigma() {

        check("fina", "sigma", "sigma1");
    }

    /**
     * test five.
     */
    @Test
    public void testGSUBSingle_five() {

        check("sups", "five", "five.superior");
    }

    /**
     * test four.
     */
    @Test
    public void testGSUBSingle_four() {

        check("sups", "four", "four.superior");
    }

    /**
     * test g.
     */
    @Test
    public void testGSUBSingle_g() {

        check("sups", "g", "g.superior");
    }

    /**
     * test gammalatin.
     */
    @Test
    public void testGSUBSingle_gammalatin() {

        check("sups", "gammalatin", "gammalatin.superior");
    }

    /**
     * test glottalstopreversed.
     */
    @Test
    public void testGSUBSingle_glottalstopreversed() {

        check("sups", "glottalstopreversed", "glottalstopreversed.superior");
    }

    /**
     * test h.
     */
    @Test
    public void testGSUBSingle_h() {

        check("sups", "h", "h.superior");
    }

    /**
     * test hhook.
     */
    @Test
    public void testGSUBSingle_hhook() {

        check("sups", "hhook", "hhook.superior");
    }

    /**
     * test i.
     */
    @Test
    public void testGSUBSingle_i() {

        check("sups", "i", "i.superior");
    }

    /**
     * test j.
     */
    @Test
    public void testGSUBSingle_j() {

        check("sups", "j", "j.superior");
    }

    /**
     * test k.
     */
    @Test
    public void testGSUBSingle_k() {

        check("sups", "k", "k.superior");
    }

    /**
     * test l.
     */
    @Test
    public void testGSUBSingle_l() {

        check("sups", "l", "l.superior");
    }

    /**
     * test m.
     */
    @Test
    public void testGSUBSingle_m() {

        check("sups", "m", "m.superior");
    }

    /**
     * test minus.
     */
    @Test
    public void testGSUBSingle_minus() {

        check("sups", "minus", "minus.superior");
    }

    /**
     * test n.
     */
    @Test
    public void testGSUBSingle_n() {

        check("sups", "n", "n.superior");
    }

    /**
     * test nine.
     */
    @Test
    public void testGSUBSingle_nine() {

        check("sups", "nine", "nine.superior");
    }

    /**
     * test o.
     */
    @Test
    public void testGSUBSingle_o() {

        check("sups", "o", "o.superior");
    }

    /**
     * test one.
     */
    @Test
    public void testGSUBSingle_one() {

        check("sups", "one", "one.superior");
    }

    /**
     * test p.
     */
    @Test
    public void testGSUBSingle_p() {

        check("sups", "p", "p.superior");
    }

    /**
     * test parenleft.
     */
    @Test
    public void testGSUBSingle_parenleft() {

        check("sups", "parenleft", "parenleft.superior");
    }

    /**
     * test parenright.
     */
    @Test
    public void testGSUBSingle_parenright() {

        check("sups", "parenright", "parenright.superior");
    }

    /**
     * test plus.
     */
    @Test
    public void testGSUBSingle_plus() {

        check("sups", "plus", "plus.superior");
    }

    /**
     * test q.
     */
    @Test
    public void testGSUBSingle_q() {

        check("sups", "q", "q.superior");
    }

    /**
     * test r.
     */
    @Test
    public void testGSUBSingle_r() {

        check("sups", "r", "r.superior");
    }

    /**
     * test rhookturned.
     */
    @Test
    public void testGSUBSingle_rhookturned() {

        check("sups", "rhookturned", "rhookturned.superior");
    }

    /**
     * test Rsmallinverted.
     */
    @Test
    public void testGSUBSingle_Rsmallinverted() {

        check("sups", "Rsmallinverted", "Rsmallinverted.superior");
    }

    /**
     * test rturned.
     */
    @Test
    public void testGSUBSingle_rturned() {

        check("sups", "rturned", "rturned.superior");
    }

    /**
     * test s.
     */
    @Test
    public void testGSUBSingle_s() {

        check("sups", "s", "s.superior");
    }

    /**
     * test seven.
     */
    @Test
    public void testGSUBSingle_seven() {

        check("sups", "seven", "seven.superior");
    }

    /**
     * test six.
     */
    @Test
    public void testGSUBSingle_six() {

        check("sups", "six", "six.superior");
    }

    /**
     * test a.
     */
    @Test
    public void testGSUBSingle_smcp_a() {

        check("smcp", "a", "a.sc");
    }

    /**
     * test aacute.
     */
    @Test
    public void testGSUBSingle_smcp_aacute() {

        check("smcp", "aacute", "aacute.sc");
    }

    /**
     * test abreve.
     */
    @Test
    public void testGSUBSingle_smcp_abreve() {

        check("smcp", "abreve", "abreve.sc");
    }

    /**
     * test acircumflex.
     */
    @Test
    public void testGSUBSingle_smcp_acircumflex() {

        check("smcp", "acircumflex", "acircumflex.sc");
    }

    /**
     * test adieresis.
     */
    @Test
    public void testGSUBSingle_smcp_adieresis() {

        check("smcp", "adieresis", "adieresis.sc");
    }

    /**
     * test ae.
     */
    @Test
    public void testGSUBSingle_smcp_ae() {

        check("smcp", "ae", "ae.sc");
    }

    /**
     * test agrave.
     */
    @Test
    public void testGSUBSingle_smcp_agrave() {

        check("smcp", "agrave", "agrave.sc");
    }

    /**
     * test ampersand.
     */
    @Test
    public void testGSUBSingle_smcp_ampersand() {

        check("smcp", "ampersand", "ampersand.alt");
    }

    /**
     * test aogonek.
     */
    @Test
    public void testGSUBSingle_smcp_aogonek() {

        check("smcp", "aogonek", "aogonek.sc");
    }

    /**
     * test aring.
     */
    @Test
    public void testGSUBSingle_smcp_aring() {

        check("smcp", "aring", "aring.sc");
    }

    /**
     * test atilde.
     */
    @Test
    public void testGSUBSingle_smcp_atilde() {

        check("smcp", "atilde", "atilde.sc");
    }

    /**
     * test b.
     */
    @Test
    public void testGSUBSingle_smcp_b() {

        check("smcp", "b", "b.sc");
    }

    /**
     * test c.
     */
    @Test
    public void testGSUBSingle_smcp_c() {

        check("smcp", "c", "c.sc");
    }

    /**
     * test cacute.
     */
    @Test
    public void testGSUBSingle_smcp_cacute() {

        check("smcp", "cacute", "cacute.sc");
    }

    /**
     * test ccaron.
     */
    @Test
    public void testGSUBSingle_smcp_ccaron() {

        check("smcp", "ccaron", "ccaron.sc");
    }

    /**
     * test ccedilla.
     */
    @Test
    public void testGSUBSingle_smcp_ccedilla() {

        check("smcp", "ccedilla", "ccedilla.sc");
    }

    /**
     * test d.
     */
    @Test
    public void testGSUBSingle_smcp_d() {

        check("smcp", "d", "d.sc");
    }

    /**
     * test dcaron.
     */
    @Test
    public void testGSUBSingle_smcp_dcaron() {

        check("smcp", "dcaron", "dcaron.sc");
    }

    /**
     * test dcroat.
     */
    @Test
    public void testGSUBSingle_smcp_dcroat() {

        check("smcp", "dcroat", "dcroat.sc");
    }

    /**
     * test e.
     */
    @Test
    public void testGSUBSingle_smcp_e() {

        check("smcp", "e", "e.sc");
    }

    /**
     * test eacute.
     */
    @Test
    public void testGSUBSingle_smcp_eacute() {

        check("smcp", "eacute", "eacute.sc");
    }

    /**
     * test ecaron.
     */
    @Test
    public void testGSUBSingle_smcp_ecaron() {

        check("smcp", "ecaron", "ecaron.sc");
    }

    /**
     * test ecircumflex.
     */
    @Test
    public void testGSUBSingle_smcp_ecircumflex() {

        check("smcp", "ecircumflex", "ecircumflex.sc");
    }

    /**
     * test edieresis.
     */
    @Test
    public void testGSUBSingle_smcp_edieresis() {

        check("smcp", "edieresis", "edieresis.sc");
    }

    /**
     * test egrave.
     */
    @Test
    public void testGSUBSingle_smcp_egrave() {

        check("smcp", "egrave", "egrave.sc");
    }

    /**
     * test eng.
     */
    @Test
    public void testGSUBSingle_smcp_eng() {

        check("smcp", "eng", "eng.sc");
    }

    /**
     * test eogonek.
     */
    @Test
    public void testGSUBSingle_smcp_eogonek() {

        check("smcp", "eogonek", "eogonek.sc");
    }

    /**
     * test eth.
     */
    @Test
    public void testGSUBSingle_smcp_eth() {

        check("smcp", "eth", "eth.sc");
    }

    /**
     * test f.
     */
    @Test
    public void testGSUBSingle_smcp_f() {

        check("smcp", "f", "f.sc");
    }

    /**
     * test f_i.
     */
    @Test
    public void testGSUBSingle_smcp_f_i() {

        check("smcp", "f_i", "f_i.sc");
    }

    /**
     * test f_l.
     */
    @Test
    public void testGSUBSingle_smcp_f_l() {

        check("smcp", "f_l", "f_l.sc");
    }

    /**
     * test g.
     */
    @Test
    public void testGSUBSingle_smcp_g() {

        check("smcp", "g", "g.sc");
    }

    /**
     * test gbreve.
     */
    @Test
    public void testGSUBSingle_smcp_gbreve() {

        check("smcp", "gbreve", "gbreve.sc");
    }

    /**
     * test germandbls.
     */
    @Test
    public void testGSUBSingle_smcp_germandbls() {

        check("smcp", "germandbls", "germandbls.sc");
    }

    /**
     * test h.
     */
    @Test
    public void testGSUBSingle_smcp_h() {

        check("smcp", "h", "h.sc");
    }

    /**
     * test hyphen.
     */
    @Test
    public void testGSUBSingle_smcp_hyphen() {

        check("smcp", "hyphen", "hyphen.sc");
    }

    /**
     * test i.
     */
    @Test
    public void testGSUBSingle_smcp_i() {

        check("smcp", "i", "i.sc");
    }

    /**
     * test iacute.
     */
    @Test
    public void testGSUBSingle_smcp_iacute() {

        check("smcp", "iacute", "iacute.sc");
    }

    /**
     * test icircumflex.
     */
    @Test
    public void testGSUBSingle_smcp_icircumflex() {

        check("smcp", "icircumflex", "icircumflex.sc");
    }

    /**
     * test idieresis.
     */
    @Test
    public void testGSUBSingle_smcp_idieresis() {

        check("smcp", "idieresis", "idieresis.sc");
    }

    /**
     * test igrave.
     */
    @Test
    public void testGSUBSingle_smcp_igrave() {

        check("smcp", "igrave", "igrave.sc");
    }

    /**
     * test ij.
     */
    @Test
    public void testGSUBSingle_smcp_ij() {

        check("smcp", "ij", "ij.sc");
    }

    /**
     * test j.
     */
    @Test
    public void testGSUBSingle_smcp_j() {

        check("smcp", "j", "j.sc");
    }

    /**
     * test k.
     */
    @Test
    public void testGSUBSingle_smcp_k() {

        check("smcp", "k", "k.sc");
    }

    /**
     * test kgreenlandic.
     */
    @Test
    public void testGSUBSingle_smcp_kgreenlandic() {

        check("smcp", "kgreenlandic", "k.sc");
    }

    /**
     * test l.
     */
    @Test
    public void testGSUBSingle_smcp_l() {

        check("smcp", "l", "l.sc");
    }

    /**
     * test lacute.
     */
    @Test
    public void testGSUBSingle_smcp_lacute() {

        check("smcp", "lacute", "lacute.sc");
    }

    /**
     * test lcaron.
     */
    @Test
    public void testGSUBSingle_smcp_lcaron() {

        check("smcp", "lcaron", "lcaron.sc");
    }

    /**
     * test longs.
     */
    @Test
    public void testGSUBSingle_smcp_longs() {

        check("smcp", "longs", "s.sc");
    }

    /**
     * test lslash.
     */
    @Test
    public void testGSUBSingle_smcp_lslash() {

        check("smcp", "lslash", "lslash.sc");
    }

    /**
     * test m.
     */
    @Test
    public void testGSUBSingle_smcp_m() {

        check("smcp", "m", "m.sc");
    }

    /**
     * test n.
     */
    @Test
    public void testGSUBSingle_smcp_n() {

        check("smcp", "n", "n.sc");
    }

    /**
     * test nacute.
     */
    @Test
    public void testGSUBSingle_smcp_nacute() {

        check("smcp", "nacute", "nacute.sc");
    }

    /**
     * test ncaron.
     */
    @Test
    public void testGSUBSingle_smcp_ncaron() {

        check("smcp", "ncaron", "ncaron.sc");
    }

    /**
     * test ntilde.
     */
    @Test
    public void testGSUBSingle_smcp_ntilde() {

        check("smcp", "ntilde", "ntilde.sc");
    }

    /**
     * test o.
     */
    @Test
    public void testGSUBSingle_smcp_o() {

        check("smcp", "o", "o.sc");
    }

    /**
     * test oacute.
     */
    @Test
    public void testGSUBSingle_smcp_oacute() {

        check("smcp", "oacute", "oacute.sc");
    }

    /**
     * test ocircumflex.
     */
    @Test
    public void testGSUBSingle_smcp_ocircumflex() {

        check("smcp", "ocircumflex", "ocircumflex.sc");
    }

    /**
     * test odieresis.
     */
    @Test
    public void testGSUBSingle_smcp_odieresis() {

        check("smcp", "odieresis", "odieresis.sc");
    }

    /**
     * test oe.
     */
    @Test
    public void testGSUBSingle_smcp_oe() {

        check("smcp", "oe", "oe.sc");
    }

    /**
     * test ograve.
     */
    @Test
    public void testGSUBSingle_smcp_ograve() {

        check("smcp", "ograve", "ograve.sc");
    }

    /**
     * test ohungarumlaut.
     */
    @Test
    public void testGSUBSingle_smcp_ohungarumlaut() {

        check("smcp", "ohungarumlaut", "ohungarumlaut.sc");
    }

    /**
     * test oslash.
     */
    @Test
    public void testGSUBSingle_smcp_oslash() {

        check("smcp", "oslash", "oslash.sc");
    }

    /**
     * test otilde.
     */
    @Test
    public void testGSUBSingle_smcp_otilde() {

        check("smcp", "otilde", "otilde.sc");
    }

    /**
     * test p.
     */
    @Test
    public void testGSUBSingle_smcp_p() {

        check("smcp", "p", "p.sc");
    }

    /**
     * test q.
     */
    @Test
    public void testGSUBSingle_smcp_q() {

        check("smcp", "q", "q.sc");
    }

    /**
     * test r.
     */
    @Test
    public void testGSUBSingle_smcp_r() {

        check("smcp", "r", "r.sc");
    }

    /**
     * test racute.
     */
    @Test
    public void testGSUBSingle_smcp_racute() {

        check("smcp", "racute", "racute.sc");
    }

    /**
     * test rcaron.
     */
    @Test
    public void testGSUBSingle_smcp_rcaron() {

        check("smcp", "rcaron", "rcaron.sc");
    }

    /**
     * test s.
     */
    @Test
    public void testGSUBSingle_smcp_s() {

        check("smcp", "s", "s.sc");
    }

    /**
     * test sacute.
     */
    @Test
    public void testGSUBSingle_smcp_sacute() {

        check("smcp", "sacute", "sacute.sc");
    }

    /**
     * test scaron.
     */
    @Test
    public void testGSUBSingle_smcp_scaron() {

        check("smcp", "scaron", "scaron.sc");
    }

    /**
     * test scedilla.
     */
    @Test
    public void testGSUBSingle_smcp_scedilla() {

        check("smcp", "scedilla", "scedilla.sc");
    }

    /**
     * test t.
     */
    @Test
    public void testGSUBSingle_smcp_t() {

        check("smcp", "t", "t.sc");
    }

    /**
     * test tbar.
     */
    @Test
    public void testGSUBSingle_smcp_tbar() {

        check("smcp", "tbar", "tbar.sc");
    }

    /**
     * test tcaron.
     */
    @Test
    public void testGSUBSingle_smcp_tcaron() {

        check("smcp", "tcaron", "tcaron.sc");
    }

    /**
     * test tcommaaccent.
     */
    @Test
    public void testGSUBSingle_smcp_tcommaaccent() {

        check("smcp", "tcommaaccent", "tcommaaccent.sc");
    }

    /**
     * test thorn.
     */
    @Test
    public void testGSUBSingle_smcp_thorn() {

        check("smcp", "thorn", "thorn.sc");
    }

    /**
     * test u.
     */
    @Test
    public void testGSUBSingle_smcp_u() {

        check("smcp", "u", "u.sc");
    }

    /**
     * test uacute.
     */
    @Test
    public void testGSUBSingle_smcp_uacute() {

        check("smcp", "uacute", "uacute.sc");
    }

    /**
     * test ucircumflex.
     */
    @Test
    public void testGSUBSingle_smcp_ucircumflex() {

        check("smcp", "ucircumflex", "ucircumflex.sc");
    }

    /**
     * test udieresis.
     */
    @Test
    public void testGSUBSingle_smcp_udieresis() {

        check("smcp", "udieresis", "udieresis.sc");
    }

    /**
     * test ugrave.
     */
    @Test
    public void testGSUBSingle_smcp_ugrave() {

        check("smcp", "ugrave", "ugrave.sc");
    }

    /**
     * test uhungarumlaut.
     */
    @Test
    public void testGSUBSingle_smcp_uhungarumlaut() {

        check("smcp", "uhungarumlaut", "uhungarumlaut.sc");
    }

    /**
     * test uring.
     */
    @Test
    public void testGSUBSingle_smcp_uring() {

        check("smcp", "uring", "uring.sc");
    }

    /**
     * test v.
     */
    @Test
    public void testGSUBSingle_smcp_v() {

        check("smcp", "v", "v.sc");
    }

    /**
     * test w.
     */
    @Test
    public void testGSUBSingle_smcp_w() {

        check("smcp", "w", "w.sc");
    }

    /**
     * test x.
     */
    @Test
    public void testGSUBSingle_smcp_x() {

        check("smcp", "x", "x.sc");
    }

    /**
     * test y.
     */
    @Test
    public void testGSUBSingle_smcp_y() {

        check("smcp", "y", "y.sc");
    }

    /**
     * test yacute.
     */
    @Test
    public void testGSUBSingle_smcp_yacute() {

        check("smcp", "yacute", "yacute.sc");
    }

    /**
     * test ydieresis.
     */
    @Test
    public void testGSUBSingle_smcp_ydieresis() {

        check("smcp", "ydieresis", "ydieresis.sc");
    }

    /**
     * test z.
     */
    @Test
    public void testGSUBSingle_smcp_z() {

        check("smcp", "z", "z.sc");
    }

    /**
     * test zacute.
     */
    @Test
    public void testGSUBSingle_smcp_zacute() {

        check("smcp", "zacute", "zacute.sc");
    }

    /**
     * test zcaron.
     */
    @Test
    public void testGSUBSingle_smcp_zcaron() {

        check("smcp", "zcaron", "zcaron.sc");
    }

    /**
     * test zdotaccent.
     */
    @Test
    public void testGSUBSingle_smcp_zdotaccent() {

        check("smcp", "zdotaccent", "zdotaccent.sc");
    }

    /**
     * test a.
     */
    @Test
    public void testGSUBSingle_sups_a() {

        check("sups", "a", "a.superior");
    }

    /**
     * test b.
     */
    @Test
    public void testGSUBSingle_sups_b() {

        check("sups", "b", "b.superior");
    }

    /**
     * test c.
     */
    @Test
    public void testGSUBSingle_sups_c() {

        check("sups", "c", "c.superior");
    }

    /**
     * test d.
     */
    @Test
    public void testGSUBSingle_sups_d() {

        check("sups", "d", "d.superior");
    }

    /**
     * test e.
     */
    @Test
    public void testGSUBSingle_sups_e() {

        check("sups", "e", "e.superior");
    }

    /**
     * test eight.
     */
    @Test
    public void testGSUBSingle_sups_eight() {

        check("sups", "eight", "eight.superior");
    }

    /**
     * test eight.oldstyle.
     */
    @Test
    public void testGSUBSingle_sups_eightoldstyle() {

        check("sups", "eight.oldstyle", "eight.superior");
    }

    /**
     * test equal.
     */
    @Test
    public void testGSUBSingle_sups_equal() {

        check("sups", "equal", "equal.superior");
    }

    /**
     * test f.
     */
    @Test
    public void testGSUBSingle_sups_f() {

        check("sups", "f", "f.superior");
    }

    /**
     * test five.
     */
    @Test
    public void testGSUBSingle_sups_five() {

        check("sups", "five", "five.superior");
    }

    /**
     * test four.
     */
    @Test
    public void testGSUBSingle_sups_four() {

        check("sups", "four", "four.superior");
    }

    /**
     * test g.
     */
    @Test
    public void testGSUBSingle_sups_g() {

        check("sups", "g", "g.superior");
    }

    /**
     * test gammalatin.
     */
    @Test
    public void testGSUBSingle_sups_gammalatin() {

        check("sups", "gammalatin", "gammalatin.superior");
    }

    /**
     * test glottalstopreversed.
     */
    @Test
    public void testGSUBSingle_sups_glottalstopreversed() {

        check("sups", "glottalstopreversed", "glottalstopreversed.superior");
    }

    /**
     * test h.
     */
    @Test
    public void testGSUBSingle_sups_h() {

        check("sups", "h", "h.superior");
    }

    /**
     * test hhook.
     */
    @Test
    public void testGSUBSingle_sups_hhook() {

        check("sups", "hhook", "hhook.superior");
    }

    /**
     * test i.
     */
    @Test
    public void testGSUBSingle_sups_i() {

        check("sups", "i", "i.superior");
    }

    /**
     * test j.
     */
    @Test
    public void testGSUBSingle_sups_j() {

        check("sups", "j", "j.superior");
    }

    /**
     * test k.
     */
    @Test
    public void testGSUBSingle_sups_k() {

        check("sups", "k", "k.superior");
    }

    /**
     * test l.
     */
    @Test
    public void testGSUBSingle_sups_l() {

        check("sups", "l", "l.superior");
    }

    /**
     * test m.
     */
    @Test
    public void testGSUBSingle_sups_m() {

        check("sups", "m", "m.superior");
    }

    /**
     * test minus.
     */
    @Test
    public void testGSUBSingle_sups_minus() {

        check("sups", "minus", "minus.superior");
    }

    /**
     * test n.
     */
    @Test
    public void testGSUBSingle_sups_n() {

        check("sups", "n", "n.superior");
    }

    /**
     * test nine.
     */
    @Test
    public void testGSUBSingle_sups_nine() {

        check("sups", "nine", "nine.superior");
    }

    /**
     * test o.
     */
    @Test
    public void testGSUBSingle_sups_o() {

        check("sups", "o", "o.superior");
    }

    /**
     * test one.
     */
    @Test
    public void testGSUBSingle_sups_one() {

        check("sups", "one", "one.superior");
    }

    /**
     * test p.
     */
    @Test
    public void testGSUBSingle_sups_p() {

        check("sups", "p", "p.superior");
    }

    /**
     * test parenleft.
     */
    @Test
    public void testGSUBSingle_sups_parenleft() {

        check("sups", "parenleft", "parenleft.superior");
    }

    /**
     * test parenright.
     */
    @Test
    public void testGSUBSingle_sups_parenright() {

        check("sups", "parenright", "parenright.superior");
    }

    /**
     * test plus.
     */
    @Test
    public void testGSUBSingle_sups_plus() {

        check("sups", "plus", "plus.superior");
    }

    /**
     * test q.
     */
    @Test
    public void testGSUBSingle_sups_q() {

        check("sups", "q", "q.superior");
    }

    /**
     * test r.
     */
    @Test
    public void testGSUBSingle_sups_r() {

        check("sups", "r", "r.superior");
    }

    /**
     * test rhookturned.
     */
    @Test
    public void testGSUBSingle_sups_rhookturned() {

        check("sups", "rhookturned", "rhookturned.superior");
    }

    /**
     * test Rsmallinverted.
     */
    @Test
    public void testGSUBSingle_sups_Rsmallinverted() {

        check("sups", "Rsmallinverted", "Rsmallinverted.superior");
    }

    /**
     * test rturned.
     */
    @Test
    public void testGSUBSingle_sups_rturned() {

        check("sups", "rturned", "rturned.superior");
    }

    /**
     * test s.
     */
    @Test
    public void testGSUBSingle_sups_s() {

        check("sups", "s", "s.superior");
    }

    /**
     * test seven.
     */
    @Test
    public void testGSUBSingle_sups_seven() {

        check("sups", "seven", "seven.superior");
    }

    /**
     * test six.
     */
    @Test
    public void testGSUBSingle_sups_six() {

        check("sups", "six", "six.superior");
    }

    /**
     * test t.
     */
    @Test
    public void testGSUBSingle_sups_t() {

        check("sups", "t", "t.superior");
    }

    /**
     * test three.
     */
    @Test
    public void testGSUBSingle_sups_three() {

        check("sups", "three", "three.superior");
    }

    /**
     * test two.
     */
    @Test
    public void testGSUBSingle_sups_two() {

        check("sups", "two", "two.superior");
    }

    /**
     * test u.
     */
    @Test
    public void testGSUBSingle_sups_u() {

        check("sups", "u", "u.superior");
    }

    /**
     * test v.
     */
    @Test
    public void testGSUBSingle_sups_v() {

        check("sups", "v", "v.superior");
    }

    /**
     * test w.
     */
    @Test
    public void testGSUBSingle_sups_w() {

        check("sups", "w", "w.superior");
    }

    /**
     * test x.
     */
    @Test
    public void testGSUBSingle_sups_x() {

        check("sups", "x", "x.superior");
    }

    /**
     * test y.
     */
    @Test
    public void testGSUBSingle_sups_y() {

        check("sups", "y", "y.superior");
    }

    /**
     * test z.
     */
    @Test
    public void testGSUBSingle_sups_z() {

        check("sups", "z", "z.superior");
    }

    /**
     * test zero.
     */
    @Test
    public void testGSUBSingle_sups_zero() {

        check("sups", "zero", "zero.superior");
    }

    /**
     * test zero.slash.
     */
    @Test
    public void testGSUBSingle_sups_zeroslash() {

        check("sups", "zero.slash", "zero.superior");
    }

    /**
     * test t.
     */
    @Test
    public void testGSUBSingle_t() {

        check("sups", "t", "t.superior");
    }

    /**
     * test three.
     */
    @Test
    public void testGSUBSingle_three() {

        check("sups", "three", "three.superior");
    }

    /**
     * test two.
     */
    @Test
    public void testGSUBSingle_two() {

        check("sups", "two", "two.superior");
    }

    /**
     * test u.
     */
    @Test
    public void testGSUBSingle_u() {

        check("sups", "u", "u.superior");
    }

    /**
     * test v.
     */
    @Test
    public void testGSUBSingle_v() {

        check("sups", "v", "v.superior");
    }

    /**
     * test w.
     */
    @Test
    public void testGSUBSingle_w() {

        check("sups", "w", "w.superior");
    }

    /**
     * test x.
     */
    @Test
    public void testGSUBSingle_x() {

        check("sups", "x", "x.superior");
    }

    /**
     * test y.
     */
    @Test
    public void testGSUBSingle_y() {

        check("sups", "y", "y.superior");
    }

    /**
     * test z.
     */
    @Test
    public void testGSUBSingle_z() {

        check("sups", "z", "z.superior");
    }

    /**
     * test zero.
     */
    @Test
    public void testGSUBSingle_zero() {

        check("zero", "zero", "zero.slash");
    }

    /**
     * test zero.fitted.
     */
    @Test
    public void testGSUBSingle_zerofitted() {

        check("zero", "zero.fitted", "zero.slashfitted");
    }

    /**
     * test zero.slash.
     */
    @Test
    public void testGSUBSingle_zeroslash() {

        check("sups", "zero.slash", "zero.superior");
    }

    /**
     * Test: write the xml output to 'target'
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testXmlOut() throws Exception {

        assertNotNull(reader);
        XMLStreamWriter writer =
                new XMLStreamWriter(new FileOutputStream("target/fxlr.xml"),
                    "ISO8859-1");
        writer.setBeauty(true);
        writer.writeStartDocument();
        reader.writeXML(writer);
        writer.writeEndDocument();
        writer.close();
    }

}
