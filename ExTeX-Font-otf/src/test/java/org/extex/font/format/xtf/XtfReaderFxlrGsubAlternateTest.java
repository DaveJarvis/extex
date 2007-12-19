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

import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.extex.font.format.xtf.tables.XtfTable;
import org.extex.font.format.xtf.tables.gps.OtfTableGSUB;
import org.extex.font.format.xtf.tables.gps.XtfGSUBAlternateTable;
import org.extex.font.format.xtf.tables.gps.XtfLookup;
import org.extex.font.format.xtf.tables.gps.XtfLookupTable;
import org.extex.font.format.xtf.tables.tag.FeatureTag;
import org.extex.font.format.xtf.tables.tag.ScriptTag;
import org.extex.util.xml.XMLStreamWriter;
import org.junit.Test;

/**
 * Tests for the <code>XtfReader</code> with opentype files (GSUB-Alternate).
 * <p>
 * data from ttx
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderFxlrGsubAlternateTest extends TestCase {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderFxlrGsubAlternateTest() throws IOException {

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
    private void check(String featureTag, String in, String[] out) {

        assertNotNull(reader);

        StringBuffer bufOut = new StringBuffer();
        for (int x = 0; out != null && x < out.length - 1; x++) {
            bufOut.append(out[x]);
            if (x < out.length - 2) {
                bufOut.append(",");
            }
        }

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
        assertEquals("Alternate", lookups[0].getTypeAsString());

        int cnt = lookups[0].getSubtableCount();
        boolean found = false;
        for (int i = 0; i < cnt; i++) {
            XtfLookupTable subtable = lookups[0].getSubtable(i);
            assertTrue(subtable instanceof XtfGSUBAlternateTable);
            XtfGSUBAlternateTable alter = (XtfGSUBAlternateTable) subtable;

            String[][] glyphInOut = alter.getSubGlyph();
            assertNotNull(glyphInOut);

            for (int k = 0; k < glyphInOut.length; k++) {
                String inX = glyphInOut[k][0];
                String outX = glyphInOut[k][1];
                if (in.equals(inX)) {
                    if (bufOut.toString().equals(outX)) {
                        found = true;
                        break;
                    }
                }
            }
            if (found) {
                break;
            }
        }
        assertTrue("Single found " + in + " " + bufOut.toString(), found);

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
    public void testGSUBAlternate_aalt_a() {

        check("aalt", "a", new String[]{"a.sc", null});
    }

    /**
     * test aacute.
     */
    @Test
    public void testGSUBAlternate_aalt_aacute() {

        check("aalt", "aacute", new String[]{"aacute.sc", null});
    }

    /**
     * test Aacute.
     */
    @Test
    public void testGSUBAlternate_aalt_Aacute() {

        check("aalt", "Aacute", new String[]{"aacute.sc", null});
    }

    /**
     * test abreve.
     */
    @Test
    public void testGSUBAlternate_aalt_abreve() {

        check("aalt", "abreve", new String[]{"abreve.sc", null});
    }

    /**
     * test acircumflex.
     */
    @Test
    public void testGSUBAlternate_aalt_acircumflex() {

        check("aalt", "acircumflex", new String[]{"acircumflex.sc", null});
    }

    /**
     * test Acircumflex.
     */
    @Test
    public void testGSUBAlternate_aalt_Acircumflex() {

        check("aalt", "Acircumflex", new String[]{"acircumflex.sc", null});
    }

    /**
     * test adieresis.
     */
    @Test
    public void testGSUBAlternate_aalt_adieresis() {

        check("aalt", "adieresis", new String[]{"adieresis.sc", null});
    }

    /**
     * test Adieresis.
     */
    @Test
    public void testGSUBAlternate_aalt_Adieresis() {

        check("aalt", "Adieresis", new String[]{"adieresis.sc", null});
    }

    /**
     * test Adieresis.alt.
     */
    @Test
    public void testGSUBAlternate_aalt_Adieresisalt() {

        check("aalt", "Adieresis.alt", new String[]{"adieresis.sc", null});
    }

    /**
     * test ae.
     */
    @Test
    public void testGSUBAlternate_aalt_ae() {

        check("aalt", "ae", new String[]{"ae.sc", "ae.alt", null});
    }

    /**
     * test AE.
     */
    @Test
    public void testGSUBAlternate_aalt_AE() {

        check("aalt", "AE", new String[]{"ae.sc", null});
    }

    /**
     * test ae.alt.
     */
    @Test
    public void testGSUBAlternate_aalt_aealt() {

        check("aalt", "ae.alt", new String[]{"ae", null});
    }

    /**
     * test agrave.
     */
    @Test
    public void testGSUBAlternate_aalt_agrave() {

        check("aalt", "agrave", new String[]{"agrave.sc", null});
    }

    /**
     * test Agrave.
     */
    @Test
    public void testGSUBAlternate_aalt_Agrave() {

        check("aalt", "Agrave", new String[]{"agrave.sc", null});
    }

    /**
     * test ampersand.
     */
    @Test
    public void testGSUBAlternate_aalt_ampersand() {

        check("aalt", "ampersand", new String[]{"ampersand.alt", null});
    }

    /**
     * test ampersand.alt.
     */
    @Test
    public void testGSUBAlternate_aalt_ampersandalt() {

        check("aalt", "ampersand.alt", new String[]{"ampersand", null});
    }

    /**
     * test aogonek.
     */
    @Test
    public void testGSUBAlternate_aalt_aogonek() {

        check("aalt", "aogonek", new String[]{"aogonek.sc", null});
    }

    /**
     * test aring.
     */
    @Test
    public void testGSUBAlternate_aalt_aring() {

        check("aalt", "aring", new String[]{"aring.sc", null});
    }

    /**
     * test Aring.
     */
    @Test
    public void testGSUBAlternate_aalt_Aring() {

        check("aalt", "Aring", new String[]{"aring.sc", null});
    }

    /**
     * test a.sc.
     */
    @Test
    public void testGSUBAlternate_aalt_asc() {

        check("aalt", "a.sc", new String[]{"a.scalt", "A", "a", null});
    }

    /**
     * test atilde.
     */
    @Test
    public void testGSUBAlternate_aalt_atilde() {

        check("aalt", "atilde", new String[]{"atilde.sc", null});
    }

    /**
     * test Atilde.
     */
    @Test
    public void testGSUBAlternate_aalt_Atilde() {

        check("aalt", "Atilde", new String[]{"atilde.sc", null});
    }

    /**
     * test b.
     */
    @Test
    public void testGSUBAlternate_aalt_b() {

        check("aalt", "b", new String[]{"b.sc", null});
    }

    /**
     * test beta.
     */
    @Test
    public void testGSUBAlternate_aalt_beta() {

        check("aalt", "beta", new String[]{"uni03D0", null});
    }

    /**
     * test braceleft.
     */
    @Test
    public void testGSUBAlternate_aalt_braceleft() {

        check("aalt", "braceleft", new String[]{"braceleft.sc", null});
    }

    /**
     * test braceright.
     */
    @Test
    public void testGSUBAlternate_aalt_braceright() {

        check("aalt", "braceright", new String[]{"braceright.sc", null});
    }

    /**
     * test bracketleft.
     */
    @Test
    public void testGSUBAlternate_aalt_bracketleft() {

        check("aalt", "bracketleft", new String[]{"bracketleft.sc", null});
    }

    /**
     * test bracketright.
     */
    @Test
    public void testGSUBAlternate_aalt_bracketright() {

        check("aalt", "bracketright", new String[]{"bracketright.sc", null});
    }

    /**
     * test c.
     */
    @Test
    public void testGSUBAlternate_aalt_c() {

        check("aalt", "c", new String[]{"c.sc", null});
    }

    /**
     * test cacute.
     */
    @Test
    public void testGSUBAlternate_aalt_cacute() {

        check("aalt", "cacute", new String[]{"cacute.sc", null});
    }

    /**
     * test ccaron.
     */
    @Test
    public void testGSUBAlternate_aalt_ccaron() {

        check("aalt", "ccaron", new String[]{"ccaron.sc", null});
    }

    /**
     * test ccedilla.
     */
    @Test
    public void testGSUBAlternate_aalt_ccedilla() {

        check("aalt", "ccedilla", new String[]{"ccedilla.sc", null});
    }

    /**
     * test Ccedilla.
     */
    @Test
    public void testGSUBAlternate_aalt_Ccedilla() {

        check("aalt", "Ccedilla", new String[]{"ccedilla.sc", null});
    }

    /**
     * test d.
     */
    @Test
    public void testGSUBAlternate_aalt_d() {

        check("aalt", "d", new String[]{"d.sc", null});
    }

    /**
     * test dcaron.
     */
    @Test
    public void testGSUBAlternate_aalt_dcaron() {

        check("aalt", "dcaron", new String[]{"dcaron.sc", null});
    }

    /**
     * test dcroat.
     */
    @Test
    public void testGSUBAlternate_aalt_dcroat() {

        check("aalt", "dcroat", new String[]{"dcroat.sc", null});
    }

    /**
     * test e.
     */
    @Test
    public void testGSUBAlternate_aalt_e() {

        check("aalt", "e", new String[]{"e.sc", null});
    }

    /**
     * test eacute.
     */
    @Test
    public void testGSUBAlternate_aalt_eacute() {

        check("aalt", "eacute", new String[]{"eacute.sc", null});
    }

    /**
     * test Eacute.
     */
    @Test
    public void testGSUBAlternate_aalt_Eacute() {

        check("aalt", "Eacute", new String[]{"eacute.sc", null});
    }

    /**
     * test ecaron.
     */
    @Test
    public void testGSUBAlternate_aalt_ecaron() {

        check("aalt", "ecaron", new String[]{"ecaron.sc", null});
    }

    /**
     * test ecircumflex.
     */
    @Test
    public void testGSUBAlternate_aalt_ecircumflex() {

        check("aalt", "ecircumflex", new String[]{"ecircumflex.sc", null});
    }

    /**
     * test Ecircumflex.
     */
    @Test
    public void testGSUBAlternate_aalt_Ecircumflex() {

        check("aalt", "Ecircumflex", new String[]{"ecircumflex.sc", null});
    }

    /**
     * test edieresis.
     */
    @Test
    public void testGSUBAlternate_aalt_edieresis() {

        check("aalt", "edieresis", new String[]{"edieresis.sc", null});
    }

    /**
     * test Edieresis.
     */
    @Test
    public void testGSUBAlternate_aalt_Edieresis() {

        check("aalt", "Edieresis", new String[]{"edieresis.sc", null});
    }

    /**
     * test egrave.
     */
    @Test
    public void testGSUBAlternate_aalt_egrave() {

        check("aalt", "egrave", new String[]{"egrave.sc", null});
    }

    /**
     * test Egrave.
     */
    @Test
    public void testGSUBAlternate_aalt_Egrave() {

        check("aalt", "Egrave", new String[]{"egrave.sc", null});
    }

    /**
     * test eight.
     */
    @Test
    public void testGSUBAlternate_aalt_eight() {

        check("aalt", "eight", new String[]{"eight.oldstyle", "eight.superior",
                "eight.fitted", null});
    }

    /**
     * test eight.fitted.
     */
    @Test
    public void testGSUBAlternate_aalt_eightfitted() {

        check("aalt", "eight.fitted", new String[]{"eight", "eight.oldstyle",
                null});
    }

    /**
     * test eng.
     */
    @Test
    public void testGSUBAlternate_aalt_eng() {

        check("aalt", "eng", new String[]{"eng.sc", null});
    }

    /**
     * test eogonek.
     */
    @Test
    public void testGSUBAlternate_aalt_eogonek() {

        check("aalt", "eogonek", new String[]{"eogonek.sc", null});
    }

    /**
     * test equal.
     */
    @Test
    public void testGSUBAlternate_aalt_equal() {

        check("aalt", "equal", new String[]{"equal.superior", null});
    }

    /**
     * test eth.
     */
    @Test
    public void testGSUBAlternate_aalt_eth() {

        check("aalt", "eth", new String[]{"eth.sc", null});
    }

    /**
     * test Eth.
     */
    @Test
    public void testGSUBAlternate_aalt_Eth() {

        check("aalt", "Eth", new String[]{"eth.sc", null});
    }

    /**
     * test Euro.
     */
    @Test
    public void testGSUBAlternate_aalt_Euro() {

        check("aalt", "Euro", new String[]{"Euro.fitted", null});
    }

    /**
     * test exclamdown.
     */
    @Test
    public void testGSUBAlternate_aalt_exclamdown() {

        check("aalt", "exclamdown", new String[]{"exclamdown.sc", null});
    }

    /**
     * test f.
     */
    @Test
    public void testGSUBAlternate_aalt_f() {

        check("aalt", "f", new String[]{"f.sc", null});
    }

    /**
     * test five.
     */
    @Test
    public void testGSUBAlternate_aalt_five() {

        check("aalt", "five", new String[]{"five.oldstyle", "five.superior",
                "five.fitted", null});
    }

    /**
     * test five.fitted.
     */
    @Test
    public void testGSUBAlternate_aalt_fivefitted() {

        check("aalt", "five.fitted",
            new String[]{"five", "five.oldstyle", null});
    }

    /**
     * test five.oldstyle.
     */
    @Test
    public void testGSUBAlternate_aalt_fiveoldstyle() {

        check("aalt", "five.oldstyle",
            new String[]{"five", "five.fitted", null});
    }

    /**
     * test four.
     */
    @Test
    public void testGSUBAlternate_aalt_four() {

        check("aalt", "four", new String[]{"four.oldstyle", "four.superior",
                "four.fitted", null});
    }

    /**
     * test four.fitted.
     */
    @Test
    public void testGSUBAlternate_aalt_fourfitted() {

        check("aalt", "four.fitted",
            new String[]{"four", "four.oldstyle", null});
    }

    /**
     * test four.oldstyle.
     */
    @Test
    public void testGSUBAlternate_aalt_fouroldstyle() {

        check("aalt", "four.oldstyle",
            new String[]{"four", "four.fitted", null});
    }

    /**
     * test g.
     */
    @Test
    public void testGSUBAlternate_aalt_g() {

        check("aalt", "g", new String[]{"g.sc", null});
    }

    /**
     * test gbreve.
     */
    @Test
    public void testGSUBAlternate_aalt_gbreve() {

        check("aalt", "gbreve", new String[]{"gbreve.sc", null});
    }

    /**
     * test germandbls.
     */
    @Test
    public void testGSUBAlternate_aalt_germandbls() {

        check("aalt", "germandbls", new String[]{"germandbls.sc",
                "germandbls.alt", "germandbls.scalt", "germandbls.ss03", null});
    }

    /**
     * test Germandbls.
     */
    @Test
    public void testGSUBAlternate_aalt_Germandbls() {

        check("aalt", "Germandbls", new String[]{"Germandbls.alt", null});
    }

    /**
     * test guillemotleft.
     */
    @Test
    public void testGSUBAlternate_aalt_guillemotleft() {

        check("aalt", "guillemotleft", new String[]{"guillemotleft.sc", null});
    }

    /**
     * test guillemotright.
     */
    @Test
    public void testGSUBAlternate_aalt_guillemotright() {

        check("aalt", "guillemotright", new String[]{"guillemotright.sc", null});
    }

    /**
     * test guilsinglleft.
     */
    @Test
    public void testGSUBAlternate_aalt_guilsinglleft() {

        check("aalt", "guilsinglleft", new String[]{"guilsinglleft.sc", null});
    }

    /**
     * test guilsinglright.
     */
    @Test
    public void testGSUBAlternate_aalt_guilsinglright() {

        check("aalt", "guilsinglright", new String[]{"guilsinglright.sc", null});
    }

    /**
     * test h.
     */
    @Test
    public void testGSUBAlternate_aalt_h() {

        check("aalt", "h", new String[]{"h.sc", "h.alt", null});
    }

    /**
     * test h.alt.
     */
    @Test
    public void testGSUBAlternate_aalt_halt() {

        check("aalt", "h.alt", new String[]{"h", null});
    }

    /**
     * test hyphen.
     */
    @Test
    public void testGSUBAlternate_aalt_hyphen() {

        check("aalt", "hyphen", new String[]{"hyphen.sc", null});
    }

    /**
     * test i.
     */
    @Test
    public void testGSUBAlternate_aalt_i() {

        check("aalt", "i", new String[]{"i.sc", null});
    }

    /**
     * test iacute.
     */
    @Test
    public void testGSUBAlternate_aalt_iacute() {

        check("aalt", "iacute", new String[]{"iacute.sc", null});
    }

    /**
     * test Iacute.
     */
    @Test
    public void testGSUBAlternate_aalt_Iacute() {

        check("aalt", "Iacute", new String[]{"iacute.sc", null});
    }

    /**
     * test icircumflex.
     */
    @Test
    public void testGSUBAlternate_aalt_icircumflex() {

        check("aalt", "icircumflex", new String[]{"icircumflex.sc", null});
    }

    /**
     * test Icircumflex.
     */
    @Test
    public void testGSUBAlternate_aalt_Icircumflex() {

        check("aalt", "Icircumflex", new String[]{"icircumflex.sc", null});
    }

    /**
     * test idieresis.
     */
    @Test
    public void testGSUBAlternate_aalt_idieresis() {

        check("aalt", "idieresis", new String[]{"idieresis.sc", null});
    }

    /**
     * test Idieresis.
     */
    @Test
    public void testGSUBAlternate_aalt_Idieresis() {

        check("aalt", "Idieresis", new String[]{"idieresis.sc", null});
    }

    /**
     * test igrave.
     */
    @Test
    public void testGSUBAlternate_aalt_igrave() {

        check("aalt", "igrave", new String[]{"igrave.sc", null});
    }

    /**
     * test Igrave.
     */
    @Test
    public void testGSUBAlternate_aalt_Igrave() {

        check("aalt", "Igrave", new String[]{"igrave.sc", null});
    }

    /**
     * test ij.
     */
    @Test
    public void testGSUBAlternate_aalt_ij() {

        check("aalt", "ij", new String[]{"ij.sc", null});
    }

    /**
     * test IJ.
     */
    @Test
    public void testGSUBAlternate_aalt_IJ() {

        check("aalt", "IJ", new String[]{"ij.sc", null});
    }

    /**
     * test j.
     */
    @Test
    public void testGSUBAlternate_aalt_j() {

        check("aalt", "j", new String[]{"j.sc", null});
    }

    /**
     * test k.
     */
    @Test
    public void testGSUBAlternate_aalt_k() {

        check("aalt", "k", new String[]{"k.sc", null});
    }

    /**
     * test K.
     */
    @Test
    public void testGSUBAlternate_aalt_K() {

        check("aalt", "K", new String[]{"K.alt",

        "k.sc", null});
    }

    /**
     * test kappa.
     */
    @Test
    public void testGSUBAlternate_aalt_kappa() {

        check("aalt", "kappa", new String[]{"uni03F0", null});
    }

    /**
     * test l.
     */
    @Test
    public void testGSUBAlternate_aalt_l() {

        check("aalt", "l", new String[]{"l.sc", null});
    }

    /**
     * test lacute.
     */
    @Test
    public void testGSUBAlternate_aalt_lacute() {

        check("aalt", "lacute", new String[]{"lacute.sc", null});
    }

    /**
     * test lcaron.
     */
    @Test
    public void testGSUBAlternate_aalt_lcaron() {

        check("aalt", "lcaron", new String[]{"lcaron.sc", null});
    }

    /**
     * test lslash.
     */
    @Test
    public void testGSUBAlternate_aalt_lslash() {

        check("aalt", "lslash", new String[]{"lslash.sc", null});
    }

    /**
     * test m.
     */
    @Test
    public void testGSUBAlternate_aalt_m() {

        check("aalt", "m", new String[]{"m.sc", null});
    }

    /**
     * test minus.
     */
    @Test
    public void testGSUBAlternate_aalt_minus() {

        check("aalt", "minus", new String[]{"minus.superior", "minus.inferior",
                "uni2099", null});
    }

    /**
     * test n.
     */
    @Test
    public void testGSUBAlternate_aalt_n() {

        check("aalt", "n", new String[]{"n.sc", null});
    }

    /**
     * test nacute.
     */
    @Test
    public void testGSUBAlternate_aalt_nacute() {

        check("aalt", "nacute", new String[]{"nacute.sc", null});
    }

    /**
     * test ncaron.
     */
    @Test
    public void testGSUBAlternate_aalt_ncaron() {

        check("aalt", "ncaron", new String[]{"ncaron.sc", null});
    }

    /**
     * test nine.
     */
    @Test
    public void testGSUBAlternate_aalt_nine() {

        check("aalt", "nine", new String[]{"nine.oldstyle", "nine.superior",
                "nine.fitted", null});
    }

    /**
     * test nine.oldstyle.
     */
    @Test
    public void testGSUBAlternate_aalt_nineoldstyle() {

        check("aalt", "nine.oldstyle",
            new String[]{"nine", "nine.fitted", null});
    }

    /**
     * test ntilde.
     */
    @Test
    public void testGSUBAlternate_aalt_ntilde() {

        check("aalt", "ntilde", new String[]{"ntilde.sc", null});
    }

    /**
     * test Ntilde.
     */
    @Test
    public void testGSUBAlternate_aalt_Ntilde() {

        check("aalt", "Ntilde", new String[]{"ntilde.sc", null});
    }

    /**
     * test o.
     */
    @Test
    public void testGSUBAlternate_aalt_o() {

        check("aalt", "o", new String[]{"o.sc", null});
    }

    /**
     * test oacute.
     */
    @Test
    public void testGSUBAlternate_aalt_oacute() {

        check("aalt", "oacute", new String[]{"oacute.sc", null});
    }

    /**
     * test Oacute.
     */
    @Test
    public void testGSUBAlternate_aalt_Oacute() {

        check("aalt", "Oacute", new String[]{"oacute.sc", null});
    }

    /**
     * test ocircumflex.
     */
    @Test
    public void testGSUBAlternate_aalt_ocircumflex() {

        check("aalt", "ocircumflex", new String[]{"ocircumflex.sc", null});
    }

    /**
     * test Ocircumflex.
     */
    @Test
    public void testGSUBAlternate_aalt_Ocircumflex() {

        check("aalt", "Ocircumflex", new String[]{"ocircumflex.sc", null});
    }

    /**
     * test odieresis.
     */
    @Test
    public void testGSUBAlternate_aalt_odieresis() {

        check("aalt", "odieresis", new String[]{"odieresis.sc", null});
    }

    /**
     * test Odieresis.
     */
    @Test
    public void testGSUBAlternate_aalt_Odieresis() {

        check("aalt", "Odieresis", new String[]{"odieresis.sc", null});
    }

    /**
     * test Odieresis.alt.
     */
    @Test
    public void testGSUBAlternate_aalt_Odieresisalt() {

        check("aalt", "Odieresis.alt", new String[]{"odieresis.sc", null});
    }

    /**
     * test oe.
     */
    @Test
    public void testGSUBAlternate_aalt_oe() {

        check("aalt", "oe", new String[]{"oe.sc", null});
    }

    /**
     * test ograve.
     */
    @Test
    public void testGSUBAlternate_aalt_ograve() {

        check("aalt", "ograve", new String[]{"ograve.sc", null});
    }

    /**
     * test Ograve.
     */
    @Test
    public void testGSUBAlternate_aalt_Ograve() {

        check("aalt", "Ograve", new String[]{"ograve.sc", null});
    }

    /**
     * test ohungarumlaut.
     */
    @Test
    public void testGSUBAlternate_aalt_ohungarumlaut() {

        check("aalt", "ohungarumlaut", new String[]{"ohungarumlaut.sc", null});
    }

    /**
     * test one.
     */
    @Test
    public void testGSUBAlternate_aalt_one() {

        check("aalt", "one", new String[]{"one.oldstyle", "one.superior",
                "one.fitted", null});
    }

    /**
     * test one.fitted.
     */
    @Test
    public void testGSUBAlternate_aalt_onefitted() {

        check("aalt", "one.fitted", new String[]{"one", "one.oldstyle", null});
    }

    /**
     * test one.oldstyle.
     */
    @Test
    public void testGSUBAlternate_aalt_oneoldstyle() {

        check("aalt", "one.oldstyle", new String[]{"one", "one.fitted", null});
    }

    /**
     * test oslash.
     */
    @Test
    public void testGSUBAlternate_aalt_oslash() {

        check("aalt", "oslash", new String[]{"oslash.sc", null});
    }

    /**
     * test Oslash.
     */
    @Test
    public void testGSUBAlternate_aalt_Oslash() {

        check("aalt", "Oslash", new String[]{"oslash.sc", null});
    }

    /**
     * test otilde.
     */
    @Test
    public void testGSUBAlternate_aalt_otilde() {

        check("aalt", "otilde", new String[]{"otilde.sc", null});
    }

    /**
     * test Otilde.
     */
    @Test
    public void testGSUBAlternate_aalt_Otilde() {

        check("aalt", "Otilde", new String[]{"otilde.sc", null});
    }

    /**
     * test p.
     */
    @Test
    public void testGSUBAlternate_aalt_p() {

        check("aalt", "p", new String[]{"p.sc", null});
    }

    /**
     * test parenleft.
     */
    @Test
    public void testGSUBAlternate_aalt_parenleft() {

        check("aalt", "parenleft", new String[]{"parenleft.sc", null});
    }

    /**
     * test parenright.
     */
    @Test
    public void testGSUBAlternate_aalt_parenright() {

        check("aalt", "parenright", new String[]{"parenright.sc", null});
    }

    /**
     * test phi.
     */
    @Test
    public void testGSUBAlternate_aalt_phi() {

        check("aalt", "phi", new String[]{"phi1", null});
    }

    /**
     * test plus.
     */
    @Test
    public void testGSUBAlternate_aalt_plus() {

        check("aalt", "plus", new String[]{"plus.superior", "plus.inferior",
                "uni2098", null});
    }

    /**
     * test q.
     */
    @Test
    public void testGSUBAlternate_aalt_q() {

        check("aalt", "q", new String[]{"q.sc", null});
    }

    /**
     * test questiondown.
     */
    @Test
    public void testGSUBAlternate_aalt_questiondown() {

        check("aalt", "questiondown", new String[]{"questiondown.sc", null});
    }

    /**
     * test r.
     */
    @Test
    public void testGSUBAlternate_aalt_r() {

        check("aalt", "r", new String[]{"r.sc", null});
    }

    /**
     * test R.
     */
    @Test
    public void testGSUBAlternate_aalt_R() {

        check("aalt", "R", new String[]{"R.alt",

        "r.sc", null});
    }

    /**
     * test racute.
     */
    @Test
    public void testGSUBAlternate_aalt_racute() {

        check("aalt", "racute", new String[]{"racute.sc", null});
    }

    /**
     * test rcaron.
     */
    @Test
    public void testGSUBAlternate_aalt_rcaron() {

        check("aalt", "rcaron", new String[]{"rcaron.sc", null});
    }

    /**
     * test rho.
     */
    @Test
    public void testGSUBAlternate_aalt_rho() {

        check("aalt", "rho", new String[]{"uni03F1", null});
    }

    /**
     * test s.
     */
    @Test
    public void testGSUBAlternate_aalt_s() {

        check("aalt", "s", new String[]{"s.sc", null});
    }

    /**
     * test sacute.
     */
    @Test
    public void testGSUBAlternate_aalt_sacute() {

        check("aalt", "sacute", new String[]{"sacute.sc", null});
    }

    /**
     * test scaron.
     */
    @Test
    public void testGSUBAlternate_aalt_scaron() {

        check("aalt", "scaron", new String[]{"scaron.sc", null});
    }

    /**
     * test scedilla.
     */
    @Test
    public void testGSUBAlternate_aalt_scedilla() {

        check("aalt", "scedilla", new String[]{"scedilla.sc", null});
    }

    /**
     * test seven.
     */
    @Test
    public void testGSUBAlternate_aalt_seven() {

        check("aalt", "seven", new String[]{"seven.oldstyle", "seven.superior",
                "seven.fitted", null});
    }

    /**
     * test seven.oldstyle.
     */
    @Test
    public void testGSUBAlternate_aalt_sevenoldstyle() {

        check("aalt", "seven.oldstyle", new String[]{"seven", "seven.fitted",
                null});
    }

    /**
     * test sigma1.
     */
    @Test
    public void testGSUBAlternate_aalt_sigma1() {

        check("aalt", "sigma1", new String[]{"sigma1", "uni03F2", null});
    }

    /**
     * test six.
     */
    @Test
    public void testGSUBAlternate_aalt_six() {

        check("aalt", "six", new String[]{"six.oldstyle", "six.superior",
                "six.fitted", null});
    }

    /**
     * test six.fitted.
     */
    @Test
    public void testGSUBAlternate_aalt_sixfitted() {

        check("aalt", "six.fitted", new String[]{"six", "six.oldstyle", null});
    }

    /**
     * test slash.
     */
    @Test
    public void testGSUBAlternate_aalt_slash() {

        check("aalt", "slash", new String[]{"uni2215", null});
    }

    /**
     * test t.
     */
    @Test
    public void testGSUBAlternate_aalt_t() {

        check("aalt", "t", new String[]{"t.sc", null});
    }

    /**
     * test tbar.
     */
    @Test
    public void testGSUBAlternate_aalt_tbar() {

        check("aalt", "tbar", new String[]{"tbar.sc", null});
    }

    /**
     * test tcaron.
     */
    @Test
    public void testGSUBAlternate_aalt_tcaron() {

        check("aalt", "tcaron", new String[]{"tcaron.sc", null});
    }

    /**
     * test tcommaaccent.
     */
    @Test
    public void testGSUBAlternate_aalt_tcommaaccent() {

        check("aalt", "tcommaaccent", new String[]{"tcommaaccent.sc", null});
    }

    /**
     * test theta.
     */
    @Test
    public void testGSUBAlternate_aalt_theta() {

        check("aalt", "theta", new String[]{"theta1", null});
    }

    /**
     * test thorn.
     */
    @Test
    public void testGSUBAlternate_aalt_thorn() {

        check("aalt", "thorn", new String[]{"thorn.sc", null});
    }

    /**
     * test Thorn.
     */
    @Test
    public void testGSUBAlternate_aalt_Thorn() {

        check("aalt", "Thorn", new String[]{"thorn.sc", null});
    }

    /**
     * test three.
     */
    @Test
    public void testGSUBAlternate_aalt_three() {

        check("aalt", "three", new String[]{"three.oldstyle", "three.superior",
                "three.fitted", null});
    }

    /**
     * test three.fitted.
     */
    @Test
    public void testGSUBAlternate_aalt_threefitted() {

        check("aalt", "three.fitted", new String[]{"three", "three.oldstyle",
                null});
    }

    /**
     * test three.oldstyle.
     */
    @Test
    public void testGSUBAlternate_aalt_threeoldstyle() {

        check("aalt", "three.oldstyle", new String[]{"three", "three.fitted",
                null});
    }

    /**
     * test two.
     */
    @Test
    public void testGSUBAlternate_aalt_two() {

        check("aalt", "two", new String[]{"two.oldstyle", "two.superior",
                "two.fitted", null});
    }

    /**
     * test two.fitted.
     */
    @Test
    public void testGSUBAlternate_aalt_twofitted() {

        check("aalt", "two.fitted", new String[]{"two", "two.oldstyle", null});
    }

    /**
     * test two.oldstyle.
     */
    @Test
    public void testGSUBAlternate_aalt_twooldstyle() {

        check("aalt", "two.oldstyle", new String[]{"two", "two.fitted", null});
    }

    /**
     * test u.
     */
    @Test
    public void testGSUBAlternate_aalt_u() {

        check("aalt", "u", new String[]{"u.sc", null});
    }

    /**
     * test uacute.
     */
    @Test
    public void testGSUBAlternate_aalt_uacute() {

        check("aalt", "uacute", new String[]{"uacute.sc", null});
    }

    /**
     * test Uacute.
     */
    @Test
    public void testGSUBAlternate_aalt_Uacute() {

        check("aalt", "Uacute", new String[]{"uacute.sc", null});
    }

    /**
     * test ucircumflex.
     */
    @Test
    public void testGSUBAlternate_aalt_ucircumflex() {

        check("aalt", "ucircumflex", new String[]{"ucircumflex.sc", null});
    }

    /**
     * test Ucircumflex.
     */
    @Test
    public void testGSUBAlternate_aalt_Ucircumflex() {

        check("aalt", "Ucircumflex", new String[]{"ucircumflex.sc", null});
    }

    /**
     * test udieresis.
     */
    @Test
    public void testGSUBAlternate_aalt_udieresis() {

        check("aalt", "udieresis", new String[]{"udieresis.sc", null});
    }

    /**
     * test Udieresis.
     */
    @Test
    public void testGSUBAlternate_aalt_Udieresis() {

        check("aalt", "Udieresis", new String[]{"udieresis.sc", null});
    }

    /**
     * test Udieresis.alt.
     */
    @Test
    public void testGSUBAlternate_aalt_Udieresisalt() {

        check("aalt", "Udieresis.alt", new String[]{"udieresis.sc", null});
    }

    /**
     * test ugrave.
     */
    @Test
    public void testGSUBAlternate_aalt_ugrave() {

        check("aalt", "ugrave", new String[]{"ugrave.sc", null});
    }

    /**
     * test Ugrave.
     */
    @Test
    public void testGSUBAlternate_aalt_Ugrave() {

        check("aalt", "Ugrave", new String[]{"ugrave.sc", null});
    }

    /**
     * test uhungarumlaut.
     */
    @Test
    public void testGSUBAlternate_aalt_uhungarumlaut() {

        check("aalt", "uhungarumlaut", new String[]{"uhungarumlaut.sc", null});
    }

    /**
     * test uring.
     */
    @Test
    public void testGSUBAlternate_aalt_uring() {

        check("aalt", "uring", new String[]{"uring.sc", null});
    }

    /**
     * test v.
     */
    @Test
    public void testGSUBAlternate_aalt_v() {

        check("aalt", "v", new String[]{"v.sc", null});
    }

    /**
     * test V.
     */
    @Test
    public void testGSUBAlternate_aalt_V() {

        check("aalt", "V", new String[]{"V.alt", null});
    }

    /**
     * test w.
     */
    @Test
    public void testGSUBAlternate_aalt_w() {

        check("aalt", "w", new String[]{"w.sc", null});
    }

    /**
     * test W.
     */
    @Test
    public void testGSUBAlternate_aalt_W() {

        check("aalt", "W", new String[]{"W.alt", null});
    }

    /**
     * test x.
     */
    @Test
    public void testGSUBAlternate_aalt_x() {

        check("aalt", "x", new String[]{"x.sc", null});
    }

    /**
     * test y.
     */
    @Test
    public void testGSUBAlternate_aalt_y() {

        check("aalt", "y", new String[]{"y.sc", null});
    }

    /**
     * test yacute.
     */
    @Test
    public void testGSUBAlternate_aalt_yacute() {

        check("aalt", "yacute", new String[]{"yacute.sc", null});
    }

    /**
     * test Yacute.
     */
    @Test
    public void testGSUBAlternate_aalt_Yacute() {

        check("aalt", "Yacute", new String[]{"yacute.sc", null});
    }

    /**
     * test ydieresis.
     */
    @Test
    public void testGSUBAlternate_aalt_ydieresis() {

        check("aalt", "ydieresis", new String[]{"ydieresis.sc", null});
    }

    /**
     * test z.
     */
    @Test
    public void testGSUBAlternate_aalt_z() {

        check("aalt", "z", new String[]{"z.sc", null});
    }

    /**
     * test zacute.
     */
    @Test
    public void testGSUBAlternate_aalt_zacute() {

        check("aalt", "zacute", new String[]{"zacute.sc", null});
    }

    /**
     * test zcaron.
     */
    @Test
    public void testGSUBAlternate_aalt_zcaron() {

        check("aalt", "zcaron", new String[]{"zcaron.sc", null});
    }

    /**
     * test zdotaccent.
     */
    @Test
    public void testGSUBAlternate_aalt_zdotaccent() {

        check("aalt", "zdotaccent", new String[]{"zdotaccent.sc", null});
    }

    /**
     * test zero.
     */
    @Test
    public void testGSUBAlternate_aalt_zero() {

        check("aalt", "zero", new String[]{"zero.oldstyle", "zero.fitted",
                "zero.slash", "zero.slashfitted", "perthousandzero", null});
    }

    /**
     * test zero.oldstyle.
     */
    @Test
    public void testGSUBAlternate_aalt_zerooldstyle() {

        check("aalt", "zero.oldstyle",
            new String[]{"zero", "zero.fitted", null});
    }

    /**
     * test zero.slashfitted.
     */
    @Test
    public void testGSUBAlternate_aalt_zeroslashfitted() {

        check("aalt", "zero.slashfitted", new String[]{"zero.fitted",
                "zero.oldstyle", "zero.slashfitted", null});
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
