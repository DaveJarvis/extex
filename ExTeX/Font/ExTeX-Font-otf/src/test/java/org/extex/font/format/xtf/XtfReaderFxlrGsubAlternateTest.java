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

import java.io.FileNotFoundException;
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
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for the {@code XtfReader} with opentype files (GSUB-Alternate).
 * <p>
 * data from ttx
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
@Ignore
public class XtfReaderFxlrGsubAlternateTest {

    private final static String DIR_TARGET = "build";

    private final XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderFxlrGsubAlternateTest() throws IOException {
        reader = new XtfReader("../ExTeX-Font-otf/src/font/fxlr.otf");
    }

    /**
     * Check.
     * 
     * @param featureTag The feature tag.
     * @param in The in glyph name.
     * @param out The out glyph name.
     */
    private void check(String featureTag, String in, String[] out) {

        Assert.assertNotNull( reader );

        StringBuilder bufOut = new StringBuilder();
        for (int x = 0; out != null && x < out.length - 1; x++) {
            bufOut.append(out[x]);
            if (x < out.length - 2) {
                bufOut.append(',');
            }
        }

        XtfTable table = reader.getTable(XtfReader.GSUB);
        Assert.assertNotNull( table );

        Assert.assertTrue( table instanceof OtfTableGSUB );
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        Assert.assertNotNull( gsub );

        XtfLookup[] lookups =
                gsub.findLookup(ScriptTag.getDefault(), null,
                    FeatureTag.getInstance(featureTag));
        Assert.assertNotNull( lookups );
        Assert.assertEquals( 1, lookups.length );
        Assert.assertEquals( 1, lookups[ 0 ].getSubtableCount() );
        Assert.assertEquals( "Alternate", lookups[ 0 ].getTypeAsString() );

        int cnt = lookups[0].getSubtableCount();
        boolean found = false;
        for (int i = 0; i < cnt; i++) {
            XtfLookupTable subtable = lookups[0].getSubtable(i);
            Assert.assertTrue( subtable instanceof XtfGSUBAlternateTable );
            XtfGSUBAlternateTable alter = (XtfGSUBAlternateTable) subtable;

            String[][] glyphInOut = alter.getSubGlyph();
            Assert.assertNotNull( glyphInOut );

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
        Assert.assertTrue( "Single found " + in + " " + bufOut.toString(),
                           found );

    }

    /**
     * Test, if the reader exits.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testExists() {

        Assert.assertNotNull( reader );
    }

    /**
     * test a.
     */
    @Test
    public void testGSUBAlternateAAlta() {

        check("aalt", "a", new String[]{"a.sc", null});
    }

    /**
     * test aacute.
     */
    @Test
    public void testGSUBAlternateAAltaacute() {

        check("aalt", "aacute", new String[]{"aacute.sc", null});
    }

    /**
     * test Aacute.
     */
    @Test
    public void testGSUBAlternateAAltAacute() {

        check("aalt", "Aacute", new String[]{"aacute.sc", null});
    }

    /**
     * test abreve.
     */
    @Test
    public void testGSUBAlternateAAltabreve() {

        check("aalt", "abreve", new String[]{"abreve.sc", null});
    }

    /**
     * test acircumflex.
     */
    @Test
    public void testGSUBAlternateAAltacircumflex() {

        check("aalt", "acircumflex", new String[]{"acircumflex.sc", null});
    }

    /**
     * test Acircumflex.
     */
    @Test
    public void testGSUBAlternateAAltAcircumflex() {

        check("aalt", "Acircumflex", new String[]{"acircumflex.sc", null});
    }

    /**
     * test adieresis.
     */
    @Test
    public void testGSUBAlternateAAltadieresis() {

        check("aalt", "adieresis", new String[]{"adieresis.sc", null});
    }

    /**
     * test Adieresis.
     */
    @Test
    public void testGSUBAlternateAAltAdieresis() {

        check("aalt", "Adieresis", new String[]{"adieresis.sc", null});
    }

    /**
     * test Adieresis.alt.
     */
    @Test
    public void testGSUBAlternateAAltAdieresisalt() {

        check("aalt", "Adieresis.alt", new String[]{"adieresis.sc", null});
    }

    /**
     * test ae.
     */
    @Test
    public void testGSUBAlternateAAltae() {

        check("aalt", "ae", new String[]{"ae.sc", "ae.alt", null});
    }

    /**
     * test AE.
     */
    @Test
    public void testGSUBAlternateAAltAE() {

        check("aalt", "AE", new String[]{"ae.sc", null});
    }

    /**
     * test ae.alt.
     */
    @Test
    public void testGSUBAlternateAAltaealt() {

        check("aalt", "ae.alt", new String[]{"ae", null});
    }

    /**
     * test agrave.
     */
    @Test
    public void testGSUBAlternateAAltagrave() {

        check("aalt", "agrave", new String[]{"agrave.sc", null});
    }

    /**
     * test Agrave.
     */
    @Test
    public void testGSUBAlternateAAltAgrave() {

        check("aalt", "Agrave", new String[]{"agrave.sc", null});
    }

    /**
     * test ampersand.
     */
    @Test
    public void testGSUBAlternateAAltampersand() {

        check("aalt", "ampersand", new String[]{"ampersand.alt", null});
    }

    /**
     * test ampersand.alt.
     */
    @Test
    public void testGSUBAlternateAAltampersandalt() {

        check("aalt", "ampersand.alt", new String[]{"ampersand", null});
    }

    /**
     * test aogonek.
     */
    @Test
    public void testGSUBAlternateAAltaogonek() {

        check("aalt", "aogonek", new String[]{"aogonek.sc", null});
    }

    /**
     * test aring.
     */
    @Test
    public void testGSUBAlternateAAltaring() {

        check("aalt", "aring", new String[]{"aring.sc", null});
    }

    /**
     * test Aring.
     */
    @Test
    public void testGSUBAlternateAAltAring() {

        check("aalt", "Aring", new String[]{"aring.sc", null});
    }

    /**
     * test a.sc.
     */
    @Test
    public void testGSUBAlternateAAltasc() {

        check("aalt", "a.sc", new String[]{"a.scalt", "A", "a", null});
    }

    /**
     * test atilde.
     */
    @Test
    public void testGSUBAlternateAAltatilde() {

        check("aalt", "atilde", new String[]{"atilde.sc", null});
    }

    /**
     * test Atilde.
     */
    @Test
    public void testGSUBAlternateAAltAtilde() {

        check("aalt", "Atilde", new String[]{"atilde.sc", null});
    }

    /**
     * test b.
     */
    @Test
    public void testGSUBAlternateAAltb() {

        check("aalt", "b", new String[]{"b.sc", null});
    }

    /**
     * test beta.
     */
    @Test
    public void testGSUBAlternateAAltbeta() {

        check("aalt", "beta", new String[]{"uni03D0", null});
    }

    /**
     * test braceleft.
     */
    @Test
    public void testGSUBAlternateAAltbraceleft() {

        check("aalt", "braceleft", new String[]{"braceleft.sc", null});
    }

    /**
     * test braceright.
     */
    @Test
    public void testGSUBAlternateAAltbraceright() {

        check("aalt", "braceright", new String[]{"braceright.sc", null});
    }

    /**
     * test bracketleft.
     */
    @Test
    public void testGSUBAlternateAAltbracketleft() {

        check("aalt", "bracketleft", new String[]{"bracketleft.sc", null});
    }

    /**
     * test bracketright.
     */
    @Test
    public void testGSUBAlternateAAltbracketright() {

        check("aalt", "bracketright", new String[]{"bracketright.sc", null});
    }

    /**
     * test c.
     */
    @Test
    public void testGSUBAlternateAAltc() {

        check("aalt", "c", new String[]{"c.sc", null});
    }

    /**
     * test cacute.
     */
    @Test
    public void testGSUBAlternateAAltcacute() {

        check("aalt", "cacute", new String[]{"cacute.sc", null});
    }

    /**
     * test ccaron.
     */
    @Test
    public void testGSUBAlternateAAltccaron() {

        check("aalt", "ccaron", new String[]{"ccaron.sc", null});
    }

    /**
     * test ccedilla.
     */
    @Test
    public void testGSUBAlternateAAltccedilla() {

        check("aalt", "ccedilla", new String[]{"ccedilla.sc", null});
    }

    /**
     * test Ccedilla.
     */
    @Test
    public void testGSUBAlternateAAltCcedilla() {

        check("aalt", "Ccedilla", new String[]{"ccedilla.sc", null});
    }

    /**
     * test d.
     */
    @Test
    public void testGSUBAlternateAAltd() {

        check("aalt", "d", new String[]{"d.sc", null});
    }

    /**
     * test dcaron.
     */
    @Test
    public void testGSUBAlternateAAltdcaron() {

        check("aalt", "dcaron", new String[]{"dcaron.sc", null});
    }

    /**
     * test dcroat.
     */
    @Test
    public void testGSUBAlternateAAltdcroat() {

        check("aalt", "dcroat", new String[]{"dcroat.sc", null});
    }

    /**
     * test e.
     */
    @Test
    public void testGSUBAlternateAAlte() {

        check("aalt", "e", new String[]{"e.sc", null});
    }

    /**
     * test eacute.
     */
    @Test
    public void testGSUBAlternateAAlteacute() {

        check("aalt", "eacute", new String[]{"eacute.sc", null});
    }

    /**
     * test Eacute.
     */
    @Test
    public void testGSUBAlternateAAltEacute() {

        check("aalt", "Eacute", new String[]{"eacute.sc", null});
    }

    /**
     * test ecaron.
     */
    @Test
    public void testGSUBAlternateAAltecaron() {

        check("aalt", "ecaron", new String[]{"ecaron.sc", null});
    }

    /**
     * test ecircumflex.
     */
    @Test
    public void testGSUBAlternateAAltecircumflex() {

        check("aalt", "ecircumflex", new String[]{"ecircumflex.sc", null});
    }

    /**
     * test Ecircumflex.
     */
    @Test
    public void testGSUBAlternateAAltEcircumflex() {

        check("aalt", "Ecircumflex", new String[]{"ecircumflex.sc", null});
    }

    /**
     * test edieresis.
     */
    @Test
    public void testGSUBAlternateAAltedieresis() {

        check("aalt", "edieresis", new String[]{"edieresis.sc", null});
    }

    /**
     * test Edieresis.
     */
    @Test
    public void testGSUBAlternateAAltEdieresis() {

        check("aalt", "Edieresis", new String[]{"edieresis.sc", null});
    }

    /**
     * test egrave.
     */
    @Test
    public void testGSUBAlternateAAltegrave() {

        check("aalt", "egrave", new String[]{"egrave.sc", null});
    }

    /**
     * test Egrave.
     */
    @Test
    public void testGSUBAlternateAAltEgrave() {

        check("aalt", "Egrave", new String[]{"egrave.sc", null});
    }

    /**
     * test eight.
     */
    @Test
    public void testGSUBAlternateAAlteight() {

        check("aalt", "eight", new String[]{"eight.oldstyle", "eight.superior",
                "eight.fitted", null});
    }

    /**
     * test eight.fitted.
     */
    @Test
    public void testGSUBAlternateAAlteightfitted() {

        check("aalt", "eight.fitted", new String[]{"eight", "eight.oldstyle",
                null});
    }

    /**
     * test eng.
     */
    @Test
    public void testGSUBAlternateAAlteng() {

        check("aalt", "eng", new String[]{"eng.sc", null});
    }

    /**
     * test eogonek.
     */
    @Test
    public void testGSUBAlternateAAlteogonek() {

        check("aalt", "eogonek", new String[]{"eogonek.sc", null});
    }

    /**
     * test equal.
     */
    @Test
    public void testGSUBAlternateAAltequal() {

        check("aalt", "equal", new String[]{"equal.superior", null});
    }

    /**
     * test eth.
     */
    @Test
    public void testGSUBAlternateAAlteth() {

        check("aalt", "eth", new String[]{"eth.sc", null});
    }

    /**
     * test Eth.
     */
    @Test
    public void testGSUBAlternateAAltEth() {

        check("aalt", "Eth", new String[]{"eth.sc", null});
    }

    /**
     * test Euro.
     */
    @Test
    public void testGSUBAlternateAAltEuro() {

        check("aalt", "Euro", new String[]{"Euro.fitted", null});
    }

    /**
     * test exclamdown.
     */
    @Test
    public void testGSUBAlternateAAltexclamdown() {

        check("aalt", "exclamdown", new String[]{"exclamdown.sc", null});
    }

    /**
     * test f.
     */
    @Test
    public void testGSUBAlternateAAltf() {

        check("aalt", "f", new String[]{"f.sc", null});
    }

    /**
     * test five.
     */
    @Test
    public void testGSUBAlternateAAltfive() {

        check("aalt", "five", new String[]{"five.oldstyle", "five.superior",
                "five.fitted", null});
    }

    /**
     * test five.fitted.
     */
    @Test
    public void testGSUBAlternateAAltfivefitted() {

        check("aalt", "five.fitted",
            new String[]{"five", "five.oldstyle", null});
    }

    /**
     * test five.oldstyle.
     */
    @Test
    public void testGSUBAlternateAAltfiveoldstyle() {

        check("aalt", "five.oldstyle",
            new String[]{"five", "five.fitted", null});
    }

    /**
     * test four.
     */
    @Test
    public void testGSUBAlternateAAltfour() {

        check("aalt", "four", new String[]{"four.oldstyle", "four.superior",
                "four.fitted", null});
    }

    /**
     * test four.fitted.
     */
    @Test
    public void testGSUBAlternateAAltfourfitted() {

        check("aalt", "four.fitted",
            new String[]{"four", "four.oldstyle", null});
    }

    /**
     * test four.oldstyle.
     */
    @Test
    public void testGSUBAlternateAAltfouroldstyle() {

        check("aalt", "four.oldstyle",
            new String[]{"four", "four.fitted", null});
    }

    /**
     * test g.
     */
    @Test
    public void testGSUBAlternateAAltg() {

        check("aalt", "g", new String[]{"g.sc", null});
    }

    /**
     * test gbreve.
     */
    @Test
    public void testGSUBAlternateAAltgbreve() {

        check("aalt", "gbreve", new String[]{"gbreve.sc", null});
    }

    /**
     * test germandbls.
     */
    @Test
    public void testGSUBAlternateAAltgermandbls() {

        check("aalt", "germandbls", new String[]{"germandbls.sc",
                "germandbls.alt", "germandbls.scalt", "germandbls.ss03", null});
    }

    /**
     * test Germandbls.
     */
    @Test
    public void testGSUBAlternateAAltGermandbls() {

        check("aalt", "Germandbls", new String[]{"Germandbls.alt", null});
    }

    /**
     * test guillemotleft.
     */
    @Test
    public void testGSUBAlternateAAltguillemotleft() {

        check("aalt", "guillemotleft", new String[]{"guillemotleft.sc", null});
    }

    /**
     * test guillemotright.
     */
    @Test
    public void testGSUBAlternateAAltguillemotright() {

        check("aalt", "guillemotright", new String[]{"guillemotright.sc", null});
    }

    /**
     * test guilsinglleft.
     */
    @Test
    public void testGSUBAlternateAAltguilsinglleft() {

        check("aalt", "guilsinglleft", new String[]{"guilsinglleft.sc", null});
    }

    /**
     * test guilsinglright.
     */
    @Test
    public void testGSUBAlternateAAltguilsinglright() {

        check("aalt", "guilsinglright", new String[]{"guilsinglright.sc", null});
    }

    /**
     * test h.
     */
    @Test
    public void testGSUBAlternateAAlth() {

        check("aalt", "h", new String[]{"h.sc", "h.alt", null});
    }

    /**
     * test h.alt.
     */
    @Test
    public void testGSUBAlternateAAlthalt() {

        check("aalt", "h.alt", new String[]{"h", null});
    }

    /**
     * test hyphen.
     */
    @Test
    public void testGSUBAlternateAAlthyphen() {

        check("aalt", "hyphen", new String[]{"hyphen.sc", null});
    }

    /**
     * test i.
     */
    @Test
    public void testGSUBAlternateAAlti() {

        check("aalt", "i", new String[]{"i.sc", null});
    }

    /**
     * test iacute.
     */
    @Test
    public void testGSUBAlternateAAltiacute() {

        check("aalt", "iacute", new String[]{"iacute.sc", null});
    }

    /**
     * test Iacute.
     */
    @Test
    public void testGSUBAlternateAAltIacute() {

        check("aalt", "Iacute", new String[]{"iacute.sc", null});
    }

    /**
     * test icircumflex.
     */
    @Test
    public void testGSUBAlternateAAlticircumflex() {

        check("aalt", "icircumflex", new String[]{"icircumflex.sc", null});
    }

    /**
     * test Icircumflex.
     */
    @Test
    public void testGSUBAlternateAAltIcircumflex() {

        check("aalt", "Icircumflex", new String[]{"icircumflex.sc", null});
    }

    /**
     * test idieresis.
     */
    @Test
    public void testGSUBAlternateAAltidieresis() {

        check("aalt", "idieresis", new String[]{"idieresis.sc", null});
    }

    /**
     * test Idieresis.
     */
    @Test
    public void testGSUBAlternateAAltIdieresis() {

        check("aalt", "Idieresis", new String[]{"idieresis.sc", null});
    }

    /**
     * test igrave.
     */
    @Test
    public void testGSUBAlternateAAltigrave() {

        check("aalt", "igrave", new String[]{"igrave.sc", null});
    }

    /**
     * test Igrave.
     */
    @Test
    public void testGSUBAlternateAAltIgrave() {

        check("aalt", "Igrave", new String[]{"igrave.sc", null});
    }

    /**
     * test ij.
     */
    @Test
    public void testGSUBAlternateAAltij() {

        check("aalt", "ij", new String[]{"ij.sc", null});
    }

    /**
     * test IJ.
     */
    @Test
    public void testGSUBAlternateAAltIJ() {

        check("aalt", "IJ", new String[]{"ij.sc", null});
    }

    /**
     * test j.
     */
    @Test
    public void testGSUBAlternateAAltj() {

        check("aalt", "j", new String[]{"j.sc", null});
    }

    /**
     * test k.
     */
    @Test
    public void testGSUBAlternateAAltk() {

        check("aalt", "k", new String[]{"k.sc", null});
    }

    /**
     * test K.
     */
    @Test
    public void testGSUBAlternateAAltK() {

        check("aalt", "K", new String[]{"K.alt",

        "k.sc", null});
    }

    /**
     * test kappa.
     */
    @Test
    public void testGSUBAlternateAAltkappa() {

        check("aalt", "kappa", new String[]{"uni03F0", null});
    }

    /**
     * test l.
     */
    @Test
    public void testGSUBAlternateAAltl() {

        check("aalt", "l", new String[]{"l.sc", null});
    }

    /**
     * test lacute.
     */
    @Test
    public void testGSUBAlternateAAltlacute() {

        check("aalt", "lacute", new String[]{"lacute.sc", null});
    }

    /**
     * test lcaron.
     */
    @Test
    public void testGSUBAlternateAAltlcaron() {

        check("aalt", "lcaron", new String[]{"lcaron.sc", null});
    }

    /**
     * test lslash.
     */
    @Test
    public void testGSUBAlternateAAltlslash() {

        check("aalt", "lslash", new String[]{"lslash.sc", null});
    }

    /**
     * test m.
     */
    @Test
    public void testGSUBAlternateAAltm() {

        check("aalt", "m", new String[]{"m.sc", null});
    }

    /**
     * test minus.
     */
    @Test
    public void testGSUBAlternateAAltminus() {

        check("aalt", "minus", new String[]{"minus.superior", "minus.inferior",
                "uni2099", null});
    }

    /**
     * test n.
     */
    @Test
    public void testGSUBAlternateAAltn() {

        check("aalt", "n", new String[]{"n.sc", null});
    }

    /**
     * test nacute.
     */
    @Test
    public void testGSUBAlternateAAltnacute() {

        check("aalt", "nacute", new String[]{"nacute.sc", null});
    }

    /**
     * test ncaron.
     */
    @Test
    public void testGSUBAlternateAAltncaron() {

        check("aalt", "ncaron", new String[]{"ncaron.sc", null});
    }

    /**
     * test nine.
     */
    @Test
    public void testGSUBAlternateAAltnine() {

        check("aalt", "nine", new String[]{"nine.oldstyle", "nine.superior",
                "nine.fitted", null});
    }

    /**
     * test nine.oldstyle.
     */
    @Test
    public void testGSUBAlternateAAltnineoldstyle() {

        check("aalt", "nine.oldstyle",
            new String[]{"nine", "nine.fitted", null});
    }

    /**
     * test ntilde.
     */
    @Test
    public void testGSUBAlternateAAltntilde() {

        check("aalt", "ntilde", new String[]{"ntilde.sc", null});
    }

    /**
     * test Ntilde.
     */
    @Test
    public void testGSUBAlternateAAltNtilde() {

        check("aalt", "Ntilde", new String[]{"ntilde.sc", null});
    }

    /**
     * test o.
     */
    @Test
    public void testGSUBAlternateAAlto() {

        check("aalt", "o", new String[]{"o.sc", null});
    }

    /**
     * test oacute.
     */
    @Test
    public void testGSUBAlternateAAltoacute() {

        check("aalt", "oacute", new String[]{"oacute.sc", null});
    }

    /**
     * test Oacute.
     */
    @Test
    public void testGSUBAlternateAAltOacute() {

        check("aalt", "Oacute", new String[]{"oacute.sc", null});
    }

    /**
     * test ocircumflex.
     */
    @Test
    public void testGSUBAlternateAAltocircumflex() {

        check("aalt", "ocircumflex", new String[]{"ocircumflex.sc", null});
    }

    /**
     * test Ocircumflex.
     */
    @Test
    public void testGSUBAlternateAAltOcircumflex() {

        check("aalt", "Ocircumflex", new String[]{"ocircumflex.sc", null});
    }

    /**
     * test odieresis.
     */
    @Test
    public void testGSUBAlternateAAltodieresis() {

        check("aalt", "odieresis", new String[]{"odieresis.sc", null});
    }

    /**
     * test Odieresis.
     */
    @Test
    public void testGSUBAlternateAAltOdieresis() {

        check("aalt", "Odieresis", new String[]{"odieresis.sc", null});
    }

    /**
     * test Odieresis.alt.
     */
    @Test
    public void testGSUBAlternateAAltOdieresisalt() {

        check("aalt", "Odieresis.alt", new String[]{"odieresis.sc", null});
    }

    /**
     * test oe.
     */
    @Test
    public void testGSUBAlternateAAltoe() {

        check("aalt", "oe", new String[]{"oe.sc", null});
    }

    /**
     * test ograve.
     */
    @Test
    public void testGSUBAlternateAAltograve() {

        check("aalt", "ograve", new String[]{"ograve.sc", null});
    }

    /**
     * test Ograve.
     */
    @Test
    public void testGSUBAlternateAAltOgrave() {

        check("aalt", "Ograve", new String[]{"ograve.sc", null});
    }

    /**
     * test ohungarumlaut.
     */
    @Test
    public void testGSUBAlternateAAltohungarumlaut() {

        check("aalt", "ohungarumlaut", new String[]{"ohungarumlaut.sc", null});
    }

    /**
     * test one.
     */
    @Test
    public void testGSUBAlternateAAltone() {

        check("aalt", "one", new String[]{"one.oldstyle", "one.superior",
                "one.fitted", null});
    }

    /**
     * test one.fitted.
     */
    @Test
    public void testGSUBAlternateAAltonefitted() {

        check("aalt", "one.fitted", new String[]{"one", "one.oldstyle", null});
    }

    /**
     * test one.oldstyle.
     */
    @Test
    public void testGSUBAlternateAAltoneoldstyle() {

        check("aalt", "one.oldstyle", new String[]{"one", "one.fitted", null});
    }

    /**
     * test oslash.
     */
    @Test
    public void testGSUBAlternateAAltoslash() {

        check("aalt", "oslash", new String[]{"oslash.sc", null});
    }

    /**
     * test Oslash.
     */
    @Test
    public void testGSUBAlternateAAltOslash() {

        check("aalt", "Oslash", new String[]{"oslash.sc", null});
    }

    /**
     * test otilde.
     */
    @Test
    public void testGSUBAlternateAAltotilde() {

        check("aalt", "otilde", new String[]{"otilde.sc", null});
    }

    /**
     * test Otilde.
     */
    @Test
    public void testGSUBAlternateAAltOtilde() {

        check("aalt", "Otilde", new String[]{"otilde.sc", null});
    }

    /**
     * test p.
     */
    @Test
    public void testGSUBAlternateAAltp() {

        check("aalt", "p", new String[]{"p.sc", null});
    }

    /**
     * test parenleft.
     */
    @Test
    public void testGSUBAlternateAAltparenleft() {

        check("aalt", "parenleft", new String[]{"parenleft.sc", null});
    }

    /**
     * test parenright.
     */
    @Test
    public void testGSUBAlternateAAltparenright() {

        check("aalt", "parenright", new String[]{"parenright.sc", null});
    }

    /**
     * test phi.
     */
    @Test
    public void testGSUBAlternateAAltphi() {

        check("aalt", "phi", new String[]{"phi1", null});
    }

    /**
     * test plus.
     */
    @Test
    public void testGSUBAlternateAAltplus() {

        check("aalt", "plus", new String[]{"plus.superior", "plus.inferior",
                "uni2098", null});
    }

    /**
     * test q.
     */
    @Test
    public void testGSUBAlternateAAltq() {

        check("aalt", "q", new String[]{"q.sc", null});
    }

    /**
     * test questiondown.
     */
    @Test
    public void testGSUBAlternateAAltquestiondown() {

        check("aalt", "questiondown", new String[]{"questiondown.sc", null});
    }

    /**
     * test r.
     */
    @Test
    public void testGSUBAlternateAAltr() {

        check("aalt", "r", new String[]{"r.sc", null});
    }

    /**
     * test R.
     */
    @Test
    public void testGSUBAlternateAAltR() {

        check("aalt", "R", new String[]{"R.alt",

        "r.sc", null});
    }

    /**
     * test racute.
     */
    @Test
    public void testGSUBAlternateAAltracute() {

        check("aalt", "racute", new String[]{"racute.sc", null});
    }

    /**
     * test rcaron.
     */
    @Test
    public void testGSUBAlternateAAltrcaron() {

        check("aalt", "rcaron", new String[]{"rcaron.sc", null});
    }

    /**
     * test rho.
     */
    @Test
    public void testGSUBAlternateAAltrho() {

        check("aalt", "rho", new String[]{"uni03F1", null});
    }

    /**
     * test s.
     */
    @Test
    public void testGSUBAlternateAAlts() {

        check("aalt", "s", new String[]{"s.sc", null});
    }

    /**
     * test sacute.
     */
    @Test
    public void testGSUBAlternateAAltsacute() {

        check("aalt", "sacute", new String[]{"sacute.sc", null});
    }

    /**
     * test scaron.
     */
    @Test
    public void testGSUBAlternateAAltscaron() {

        check("aalt", "scaron", new String[]{"scaron.sc", null});
    }

    /**
     * test scedilla.
     */
    @Test
    public void testGSUBAlternateAAltscedilla() {

        check("aalt", "scedilla", new String[]{"scedilla.sc", null});
    }

    /**
     * test seven.
     */
    @Test
    public void testGSUBAlternateAAltseven() {

        check("aalt", "seven", new String[]{"seven.oldstyle", "seven.superior",
                "seven.fitted", null});
    }

    /**
     * test seven.oldstyle.
     */
    @Test
    public void testGSUBAlternateAAltsevenoldstyle() {

        check("aalt", "seven.oldstyle", new String[]{"seven", "seven.fitted",
                null});
    }

    /**
     * test sigma1.
     */
    @Test
    public void testGSUBAlternateAAltsigma1() {

        check("aalt", "sigma1", new String[]{"sigma1", "uni03F2", null});
    }

    /**
     * test six.
     */
    @Test
    public void testGSUBAlternateAAltsix() {

        check("aalt", "six", new String[]{"six.oldstyle", "six.superior",
                "six.fitted", null});
    }

    /**
     * test six.fitted.
     */
    @Test
    public void testGSUBAlternateAAltsixfitted() {

        check("aalt", "six.fitted", new String[]{"six", "six.oldstyle", null});
    }

    /**
     * test slash.
     */
    @Test
    public void testGSUBAlternateAAltslash() {

        check("aalt", "slash", new String[]{"uni2215", null});
    }

    /**
     * test t.
     */
    @Test
    public void testGSUBAlternateAAltt() {

        check("aalt", "t", new String[]{"t.sc", null});
    }

    /**
     * test tbar.
     */
    @Test
    public void testGSUBAlternateAAlttbar() {

        check("aalt", "tbar", new String[]{"tbar.sc", null});
    }

    /**
     * test tcaron.
     */
    @Test
    public void testGSUBAlternateAAlttcaron() {

        check("aalt", "tcaron", new String[]{"tcaron.sc", null});
    }

    /**
     * test tcommaaccent.
     */
    @Test
    public void testGSUBAlternateAAlttcommaaccent() {

        check("aalt", "tcommaaccent", new String[]{"tcommaaccent.sc", null});
    }

    /**
     * test theta.
     */
    @Test
    public void testGSUBAlternateAAlttheta() {

        check("aalt", "theta", new String[]{"theta1", null});
    }

    /**
     * test thorn.
     */
    @Test
    public void testGSUBAlternateAAltthorn() {

        check("aalt", "thorn", new String[]{"thorn.sc", null});
    }

    /**
     * test Thorn.
     */
    @Test
    public void testGSUBAlternateAAltThorn() {

        check("aalt", "Thorn", new String[]{"thorn.sc", null});
    }

    /**
     * test three.
     */
    @Test
    public void testGSUBAlternateAAltthree() {

        check("aalt", "three", new String[]{"three.oldstyle", "three.superior",
                "three.fitted", null});
    }

    /**
     * test three.fitted.
     */
    @Test
    public void testGSUBAlternateAAltthreefitted() {

        check("aalt", "three.fitted", new String[]{"three", "three.oldstyle",
                null});
    }

    /**
     * test three.oldstyle.
     */
    @Test
    public void testGSUBAlternateAAltthreeoldstyle() {

        check("aalt", "three.oldstyle", new String[]{"three", "three.fitted",
                null});
    }

    /**
     * test two.
     */
    @Test
    public void testGSUBAlternateAAlttwo() {

        check("aalt", "two", new String[]{"two.oldstyle", "two.superior",
                "two.fitted", null});
    }

    /**
     * test two.fitted.
     */
    @Test
    public void testGSUBAlternateAAlttwofitted() {

        check("aalt", "two.fitted", new String[]{"two", "two.oldstyle", null});
    }

    /**
     * test two.oldstyle.
     */
    @Test
    public void testGSUBAlternateAAlttwooldstyle() {

        check("aalt", "two.oldstyle", new String[]{"two", "two.fitted", null});
    }

    /**
     * test u.
     */
    @Test
    public void testGSUBAlternateAAltu() {

        check("aalt", "u", new String[]{"u.sc", null});
    }

    /**
     * test uacute.
     */
    @Test
    public void testGSUBAlternateAAltuacute() {

        check("aalt", "uacute", new String[]{"uacute.sc", null});
    }

    /**
     * test Uacute.
     */
    @Test
    public void testGSUBAlternateAAltUacute() {

        check("aalt", "Uacute", new String[]{"uacute.sc", null});
    }

    /**
     * test ucircumflex.
     */
    @Test
    public void testGSUBAlternateAAltucircumflex() {

        check("aalt", "ucircumflex", new String[]{"ucircumflex.sc", null});
    }

    /**
     * test Ucircumflex.
     */
    @Test
    public void testGSUBAlternateAAltUcircumflex() {

        check("aalt", "Ucircumflex", new String[]{"ucircumflex.sc", null});
    }

    /**
     * test udieresis.
     */
    @Test
    public void testGSUBAlternateAAltudieresis() {

        check("aalt", "udieresis", new String[]{"udieresis.sc", null});
    }

    /**
     * test Udieresis.
     */
    @Test
    public void testGSUBAlternateAAltUdieresis() {

        check("aalt", "Udieresis", new String[]{"udieresis.sc", null});
    }

    /**
     * test Udieresis.alt.
     */
    @Test
    public void testGSUBAlternateAAltUdieresisalt() {

        check("aalt", "Udieresis.alt", new String[]{"udieresis.sc", null});
    }

    /**
     * test ugrave.
     */
    @Test
    public void testGSUBAlternateAAltugrave() {

        check("aalt", "ugrave", new String[]{"ugrave.sc", null});
    }

    /**
     * test Ugrave.
     */
    @Test
    public void testGSUBAlternateAAltUgrave() {

        check("aalt", "Ugrave", new String[]{"ugrave.sc", null});
    }

    /**
     * test uhungarumlaut.
     */
    @Test
    public void testGSUBAlternateAAltuhungarumlaut() {

        check("aalt", "uhungarumlaut", new String[]{"uhungarumlaut.sc", null});
    }

    /**
     * test uring.
     */
    @Test
    public void testGSUBAlternateAAlturing() {

        check("aalt", "uring", new String[]{"uring.sc", null});
    }

    /**
     * test v.
     */
    @Test
    public void testGSUBAlternateAAltv() {

        check("aalt", "v", new String[]{"v.sc", null});
    }

    /**
     * test V.
     */
    @Test
    public void testGSUBAlternateAAltV() {

        check("aalt", "V", new String[]{"V.alt", null});
    }

    /**
     * test w.
     */
    @Test
    public void testGSUBAlternateAAltw() {

        check("aalt", "w", new String[]{"w.sc", null});
    }

    /**
     * test W.
     */
    @Test
    public void testGSUBAlternateAAltW() {

        check("aalt", "W", new String[]{"W.alt", null});
    }

    /**
     * test x.
     */
    @Test
    public void testGSUBAlternateAAltx() {

        check("aalt", "x", new String[]{"x.sc", null});
    }

    /**
     * test y.
     */
    @Test
    public void testGSUBAlternateAAlty() {

        check("aalt", "y", new String[]{"y.sc", null});
    }

    /**
     * test yacute.
     */
    @Test
    public void testGSUBAlternateAAltyacute() {

        check("aalt", "yacute", new String[]{"yacute.sc", null});
    }

    /**
     * test Yacute.
     */
    @Test
    public void testGSUBAlternateAAltYacute() {

        check("aalt", "Yacute", new String[]{"yacute.sc", null});
    }

    /**
     * test ydieresis.
     */
    @Test
    public void testGSUBAlternateAAltydieresis() {

        check("aalt", "ydieresis", new String[]{"ydieresis.sc", null});
    }

    /**
     * test z.
     */
    @Test
    public void testGSUBAlternateAAltz() {

        check("aalt", "z", new String[]{"z.sc", null});
    }

    /**
     * test zacute.
     */
    @Test
    public void testGSUBAlternateAAltzacute() {

        check("aalt", "zacute", new String[]{"zacute.sc", null});
    }

    /**
     * test zcaron.
     */
    @Test
    public void testGSUBAlternateAAltzcaron() {

        check("aalt", "zcaron", new String[]{"zcaron.sc", null});
    }

    /**
     * test zdotaccent.
     */
    @Test
    public void testGSUBAlternateAAltzdotaccent() {

        check("aalt", "zdotaccent", new String[]{"zdotaccent.sc", null});
    }

    /**
     * test zero.
     */
    @Test
    public void testGSUBAlternateAAltzero() {

        check("aalt", "zero", new String[]{"zero.oldstyle", "zero.fitted",
                "zero.slash", "zero.slashfitted", "perthousandzero", null});
    }

    /**
     * test zero.oldstyle.
     */
    @Test
    public void testGSUBAlternateAAltzerooldstyle() {

        check("aalt", "zero.oldstyle",
            new String[]{"zero", "zero.fitted", null});
    }

    /**
     * test zero.slashfitted.
     */
    @Test
    public void testGSUBAlternateAAltzeroslashfitted() {

        check("aalt", "zero.slashfitted", new String[]{"zero.fitted",
                "zero.oldstyle", "zero.slashfitted", null});
    }

    /**
     * Test: write the xml output to 'target'
     */
    @Test
    public void testXmlOut() throws IOException {

        Assert.assertNotNull( reader );
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
