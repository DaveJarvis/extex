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
import org.extex.font.format.xtf.tables.XtfTableDirectory.Entry;
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
 * Tests for the <code>XtfReader</code> with opentype files (GSUB-Single).
 * <p>
 * data from ttx
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
@Ignore
public class XtfReaderFxlrGsubSingleTest {

    private final XtfReader reader;

    public XtfReaderFxlrGsubSingleTest() throws IOException {
        reader = new XtfReader("../ExTeX-Font-otf/src/font/fxlr.otf");
        assertNotNull( reader );
    }

    /**
     * Check.
     *
     * @param featureName The feature tag name.
     * @param in The in glyph name.
     * @param out The out glyph name.
     */
    private void check(String featureName, String in, String out) {
        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull( table );
        assertEquals( XtfReader.GSUB, table.getType() );

        final OtfTableGSUB gsub = (OtfTableGSUB) table;
        final FeatureTag featureTag = FeatureTag.getInstance( featureName );
        final ScriptTag scriptTag = ScriptTag.getDefault();

        XtfLookup[] lookups = gsub.findLookup(scriptTag, null, featureTag);
        assertNotNull( lookups );
        assertEquals( 1, lookups.length );
        assertEquals( 1, lookups[ 0 ].getSubtableCount() );
        assertEquals( "Single", lookups[ 0 ].getTypeAsString() );

        int cnt = lookups[0].getSubtableCount();
        boolean found = false;
        for (int i = 0; i < cnt; i++) {
            XtfLookupTable subtable = lookups[0].getSubtable(i);
            assertTrue( subtable instanceof XtfGSUBSingleTable );
            XtfGSUBSingleTable single = (XtfGSUBSingleTable) subtable;
            String[][] glyphInOut = single.getSubGlyph();
            assertNotNull( glyphInOut );

            for( final String[] strings : glyphInOut ) {
                String inX = strings[ 0 ];
                String outX = strings[ 1 ];
                if( in.equals( inX ) ) {
                    if( out.equals( outX ) ) {
                        found = true;
                        break;
                    }
                }
            }
            if (found) {
                break;
            }
        }

        assertTrue( "Single found " + in + " " + out, found );
    }

    /**
     * Test, if the reader exits.
     */
    @Test
    public void testExists() {
        assertNotNull( reader );
    }

    /**
     * test a.
     */
    @Test
    public void testGSUBSinglea() {
        check("sups", "a", "a.superior");
    }

    /**
     * test b.
     */
    @Test
    public void testGSUBSingleb() {

        check("sups", "b", "b.superior");
    }

    /**
     * test c.
     */
    @Test
    public void testGSUBSinglec() {

        check("sups", "c", "c.superior");
    }

    /**
     * test A.
     */
    @Test
    public void testGSUBSinglec2scA() {

        check("c2sc", "A", "a.sc");
    }

    /**
     * test Aacute.
     */
    @Test
    public void testGSUBSinglec2scAacute() {

        check("c2sc", "Aacute", "aacute.sc");
    }

    /**
     * test Abreve.
     */
    @Test
    public void testGSUBSinglec2scAbreve() {

        check("c2sc", "Abreve", "abreve.sc");
    }

    /**
     * test Acircumflex.
     */
    @Test
    public void testGSUBSinglec2scAcircumflex() {

        check("c2sc", "Acircumflex", "acircumflex.sc");
    }

    /**
     * test Adieresis.
     */
    @Test
    public void testGSUBSinglec2scAdieresis() {

        check("c2sc", "Adieresis", "adieresis.sc");
    }

    /**
     * test Adieresis.alt.
     */
    @Test
    public void testGSUBSinglec2scAdieresisalt() {

        check("c2sc", "Adieresis.alt", "adieresis.sc");
    }

    /**
     * test AE.
     */
    @Test
    public void testGSUBSinglec2scAE() {

        check("c2sc", "AE", "ae.sc");
    }

    /**
     * test Agrave.
     */
    @Test
    public void testGSUBSinglec2scAgrave() {

        check("c2sc", "Agrave", "agrave.sc");
    }

    /**
     * test ampersand.
     */
    @Test
    public void testGSUBSinglec2scampersand() {

        check("c2sc", "ampersand", "ampersand.alt");
    }

    /**
     * test Aogonek.
     */
    @Test
    public void testGSUBSinglec2scAogonek() {

        check("c2sc", "Aogonek", "aogonek.sc");
    }

    /**
     * test Aring.
     */
    @Test
    public void testGSUBSinglec2scAring() {

        check("c2sc", "Aring", "aring.sc");
    }

    /**
     * test Atilde.
     */
    @Test
    public void testGSUBSinglec2scAtilde() {

        check("c2sc", "Atilde", "atilde.sc");
    }

    /**
     * test B.
     */
    @Test
    public void testGSUBSinglec2scB() {

        check("c2sc", "B", "b.sc");
    }

    /**
     * test braceleft.
     */
    @Test
    public void testGSUBSinglec2scbraceleft() {

        check("c2sc", "braceleft", "braceleft.sc");
    }

    /**
     * test braceright.
     */
    @Test
    public void testGSUBSinglec2scbraceright() {

        check("c2sc", "braceright", "braceright.sc");
    }

    /**
     * test bracketleft.
     */
    @Test
    public void testGSUBSinglec2scbracketleft() {

        check("c2sc", "bracketleft", "bracketleft.sc");
    }

    /**
     * test bracketright.
     */
    @Test
    public void testGSUBSinglec2scbracketright() {

        check("c2sc", "bracketright", "bracketright.sc");
    }

    /**
     * test C.
     */
    @Test
    public void testGSUBSinglec2scC() {

        check("c2sc", "C", "c.sc");
    }

    /**
     * test Cacute.
     */
    @Test
    public void testGSUBSinglec2scCacute() {

        check("c2sc", "Cacute", "cacute.sc");
    }

    /**
     * test Ccaron.
     */
    @Test
    public void testGSUBSinglec2scCcaron() {

        check("c2sc", "Ccaron", "ccaron.sc");
    }

    /**
     * test Ccedilla.
     */
    @Test
    public void testGSUBSinglec2scCcedilla() {

        check("c2sc", "Ccedilla", "ccedilla.sc");
    }

    /**
     * test D.
     */
    @Test
    public void testGSUBSinglec2scD() {

        check("c2sc", "D", "d.sc");
    }

    /**
     * test Dcaron.
     */
    @Test
    public void testGSUBSinglec2scDcaron() {

        check("c2sc", "Dcaron", "dcaron.sc");
    }

    /**
     * test Dcroat.
     */
    @Test
    public void testGSUBSinglec2scDcroat() {

        check("c2sc", "Dcroat", "dcroat.sc");
    }

    /**
     * test E.
     */
    @Test
    public void testGSUBSinglec2scE() {

        check("c2sc", "E", "e.sc");
    }

    /**
     * test Eacute.
     */
    @Test
    public void testGSUBSinglec2scEacute() {

        check("c2sc", "Eacute", "eacute.sc");
    }

    /**
     * test Ecaron.
     */
    @Test
    public void testGSUBSinglec2scEcaron() {

        check("c2sc", "Ecaron", "ecaron.sc");
    }

    /**
     * test Ecircumflex.
     */
    @Test
    public void testGSUBSinglec2scEcircumflex() {

        check("c2sc", "Ecircumflex", "ecircumflex.sc");
    }

    /**
     * test Edieresis.
     */
    @Test
    public void testGSUBSinglec2scEdieresis() {

        check("c2sc", "Edieresis", "edieresis.sc");
    }

    /**
     * test Egrave.
     */
    @Test
    public void testGSUBSinglec2scEgrave() {

        check("c2sc", "Egrave", "egrave.sc");
    }

    /**
     * test Eng.
     */
    @Test
    public void testGSUBSinglec2scEng() {

        check("c2sc", "Eng", "eng.sc");
    }

    /**
     * test Eogonek.
     */
    @Test
    public void testGSUBSinglec2scEogonek() {

        check("c2sc", "Eogonek", "eogonek.sc");
    }

    /**
     * test Eth.
     */
    @Test
    public void testGSUBSinglec2scEth() {

        check("c2sc", "Eth", "eth.sc");
    }

    /**
     * test exclamdown.
     */
    @Test
    public void testGSUBSinglec2scexclamdown() {

        check("c2sc", "exclamdown", "exclamdown.sc");
    }

    /**
     * test F.
     */
    @Test
    public void testGSUBSinglec2scF() {

        check("c2sc", "F", "f.sc");
    }

    /**
     * test G.
     */
    @Test
    public void testGSUBSinglec2scG() {

        check("c2sc", "G", "g.sc");
    }

    /**
     * test Gbreve.
     */
    @Test
    public void testGSUBSinglec2scGbreve() {

        check("c2sc", "Gbreve", "gbreve.sc");
    }

    /**
     * test germandbls.
     */
    @Test
    public void testGSUBSinglec2scgermandbls() {

        check("c2sc", "germandbls", "germandbls.sc");
    }

    /**
     * test Germandbls.
     */
    @Test
    public void testGSUBSinglec2scGermandbls() {

        check("c2sc", "Germandbls", "germandbls.sc");
    }

    /**
     * test guillemotleft.
     */
    @Test
    public void testGSUBSinglec2scguillemotleft() {

        check("c2sc", "guillemotleft", "guillemotleft.sc");
    }

    /**
     * test guillemotright.
     */
    @Test
    public void testGSUBSinglec2scguillemotright() {

        check("c2sc", "guillemotright", "guillemotright.sc");
    }

    /**
     * test guilsinglleft.
     */
    @Test
    public void testGSUBSinglec2scguilsinglleft() {

        check("c2sc", "guilsinglleft", "guilsinglleft.sc");
    }

    /**
     * test guilsinglright.
     */
    @Test
    public void testGSUBSinglec2scguilsinglright() {

        check("c2sc", "guilsinglright", "guilsinglright.sc");
    }

    /**
     * test H.
     */
    @Test
    public void testGSUBSinglec2scH() {

        check("c2sc", "H", "h.sc");
    }

    /**
     * test hyphen.
     */
    @Test
    public void testGSUBSinglec2schyphen() {

        check("c2sc", "hyphen", "hyphen.sc");
    }

    /**
     * test I.
     */
    @Test
    public void testGSUBSinglec2scI() {

        check("c2sc", "I", "i.sc");
    }

    /**
     * test Iacute.
     */
    @Test
    public void testGSUBSinglec2scIacute() {

        check("c2sc", "Iacute", "iacute.sc");
    }

    /**
     * test Icircumflex.
     */
    @Test
    public void testGSUBSinglec2scIcircumflex() {

        check("c2sc", "Icircumflex", "icircumflex.sc");
    }

    /**
     * test Idieresis.
     */
    @Test
    public void testGSUBSinglec2scIdieresis() {

        check("c2sc", "Idieresis", "idieresis.sc");
    }

    /**
     * test Igrave.
     */
    @Test
    public void testGSUBSinglec2scIgrave() {

        check("c2sc", "Igrave", "igrave.sc");
    }

    /**
     * test IJ.
     */
    @Test
    public void testGSUBSinglec2scIJ() {

        check("c2sc", "IJ", "ij.sc");
    }

    /**
     * test J.
     */
    @Test
    public void testGSUBSinglec2scJ() {

        check("c2sc", "J", "j.sc");
    }

    /**
     * test K.
     */
    @Test
    public void testGSUBSinglec2scK() {

        check("c2sc", "K", "k.sc");
    }

    /**
     * test L.
     */
    @Test
    public void testGSUBSinglec2scL() {

        check("c2sc", "L", "l.sc");
    }

    /**
     * test Lacute.
     */
    @Test
    public void testGSUBSinglec2scLacute() {

        check("c2sc", "Lacute", "lacute.sc");
    }

    /**
     * test Lcaron.
     */
    @Test
    public void testGSUBSinglec2scLcaron() {

        check("c2sc", "Lcaron", "lcaron.sc");
    }

    /**
     * test Lslash.
     */
    @Test
    public void testGSUBSinglec2scLslash() {

        check("c2sc", "Lslash", "lslash.sc");
    }

    /**
     * test M.
     */
    @Test
    public void testGSUBSinglec2scM() {

        check("c2sc", "M", "m.sc");
    }

    /**
     * test N.
     */
    @Test
    public void testGSUBSinglec2scN() {

        check("c2sc", "N", "n.sc");
    }

    /**
     * test Nacute.
     */
    @Test
    public void testGSUBSinglec2scNacute() {

        check("c2sc", "Nacute", "nacute.sc");
    }

    /**
     * test Ncaron.
     */
    @Test
    public void testGSUBSinglec2scNcaron() {

        check("c2sc", "Ncaron", "ncaron.sc");
    }

    /**
     * test Ntilde.
     */
    @Test
    public void testGSUBSinglec2scNtilde() {

        check("c2sc", "Ntilde", "ntilde.sc");
    }

    /**
     * test O.
     */
    @Test
    public void testGSUBSinglec2scO() {

        check("c2sc", "O", "o.sc");
    }

    /**
     * test Oacute.
     */
    @Test
    public void testGSUBSinglec2scOacute() {

        check("c2sc", "Oacute", "oacute.sc");
    }

    /**
     * test Ocircumflex.
     */
    @Test
    public void testGSUBSinglec2scOcircumflex() {

        check("c2sc", "Ocircumflex", "ocircumflex.sc");
    }

    /**
     * test Odieresis.
     */
    @Test
    public void testGSUBSinglec2scOdieresis() {

        check("c2sc", "Odieresis", "odieresis.sc");
    }

    /**
     * test Odieresis.alt.
     */
    @Test
    public void testGSUBSinglec2scOdieresisalt() {

        check("c2sc", "Odieresis.alt", "odieresis.sc");
    }

    /**
     * test Ograve.
     */
    @Test
    public void testGSUBSinglec2scOgrave() {

        check("c2sc", "Ograve", "ograve.sc");
    }

    /**
     * test Ohungarumlaut.
     */
    @Test
    public void testGSUBSinglec2scOhungarumlaut() {

        check("c2sc", "Ohungarumlaut", "ohungarumlaut.sc");
    }

    /**
     * test Oslash.
     */
    @Test
    public void testGSUBSinglec2scOslash() {

        check("c2sc", "Oslash", "oslash.sc");
    }

    /**
     * test Otilde.
     */
    @Test
    public void testGSUBSinglec2scOtilde() {

        check("c2sc", "Otilde", "otilde.sc");
    }

    /**
     * test P.
     */
    @Test
    public void testGSUBSinglec2scP() {

        check("c2sc", "P", "p.sc");
    }

    /**
     * test parenleft.
     */
    @Test
    public void testGSUBSinglec2scparenleft() {

        check("c2sc", "parenleft", "parenleft.sc");
    }

    /**
     * test parenright.
     */
    @Test
    public void testGSUBSinglec2scparenright() {

        check("c2sc", "parenright", "parenright.sc");
    }

    /**
     * test Q.
     */
    @Test
    public void testGSUBSinglec2scQ() {

        check("c2sc", "Q", "q.sc");
    }

    /**
     * test questiondown.
     */
    @Test
    public void testGSUBSinglec2scquestiondown() {

        check("c2sc", "questiondown", "questiondown.sc");
    }

    /**
     * test R.
     */
    @Test
    public void testGSUBSinglec2scR() {

        check("c2sc", "R", "r.sc");
    }

    /**
     * test Racute.
     */
    @Test
    public void testGSUBSinglec2scRacute() {

        check("c2sc", "Racute", "racute.sc");
    }

    /**
     * test Rcaron.
     */
    @Test
    public void testGSUBSinglec2scRcaron() {

        check("c2sc", "Rcaron", "rcaron.sc");
    }

    /**
     * test S.
     */
    @Test
    public void testGSUBSinglec2scS() {

        check("c2sc", "S", "s.sc");
    }

    /**
     * test Sacute.
     */
    @Test
    public void testGSUBSinglec2scSacute() {

        check("c2sc", "Sacute", "sacute.sc");
    }

    /**
     * test Scaron.
     */
    @Test
    public void testGSUBSinglec2scScaron() {

        check("c2sc", "Scaron", "scaron.sc");
    }

    /**
     * test Scedilla.
     */
    @Test
    public void testGSUBSinglec2scScedilla() {

        check("c2sc", "Scedilla", "scedilla.sc");
    }

    /**
     * test T.
     */
    @Test
    public void testGSUBSinglec2scT() {

        check("c2sc", "T", "t.sc");
    }

    /**
     * test Tbar.
     */
    @Test
    public void testGSUBSinglec2scTbar() {

        check("c2sc", "Tbar", "tbar.sc");
    }

    /**
     * test Tcaron.
     */
    @Test
    public void testGSUBSinglec2scTcaron() {

        check("c2sc", "Tcaron", "tcaron.sc");
    }

    /**
     * test Tcommaaccent.
     */
    @Test
    public void testGSUBSinglec2scTcommaaccent() {

        check("c2sc", "Tcommaaccent", "tcommaaccent.sc");
    }

    /**
     * test Thorn.
     */
    @Test
    public void testGSUBSinglec2scThorn() {

        check("c2sc", "Thorn", "thorn.sc");
    }

    /**
     * test U.
     */
    @Test
    public void testGSUBSinglec2scU() {

        check("c2sc", "U", "u.sc");
    }

    /**
     * test Uacute.
     */
    @Test
    public void testGSUBSinglec2scUacute() {

        check("c2sc", "Uacute", "uacute.sc");
    }

    /**
     * test Ucircumflex.
     */
    @Test
    public void testGSUBSinglec2scUcircumflex() {

        check("c2sc", "Ucircumflex", "ucircumflex.sc");
    }

    /**
     * test Udieresis.
     */
    @Test
    public void testGSUBSinglec2scUdieresis() {

        check("c2sc", "Udieresis", "udieresis.sc");
    }

    /**
     * test Udieresis.alt.
     */
    @Test
    public void testGSUBSinglec2scUdieresisalt() {

        check("c2sc", "Udieresis.alt", "udieresis.sc");
    }

    /**
     * test Ugrave.
     */
    @Test
    public void testGSUBSinglec2scUgrave() {

        check("c2sc", "Ugrave", "ugrave.sc");
    }

    /**
     * test Uhungarumlaut.
     */
    @Test
    public void testGSUBSinglec2scUhungarumlaut() {

        check("c2sc", "Uhungarumlaut", "uhungarumlaut.sc");
    }

    /**
     * test Uring.
     */
    @Test
    public void testGSUBSinglec2scUring() {

        check("c2sc", "Uring", "uring.sc");
    }

    /**
     * test V.
     */
    @Test
    public void testGSUBSinglec2scV() {

        check("c2sc", "V", "v.sc");
    }

    /**
     * test W.
     */
    @Test
    public void testGSUBSinglec2scW() {

        check("c2sc", "W", "w.sc");
    }

    /**
     * test X.
     */
    @Test
    public void testGSUBSinglec2scX() {

        check("c2sc", "X", "x.sc");
    }

    /**
     * test Y.
     */
    @Test
    public void testGSUBSinglec2scY() {

        check("c2sc", "Y", "y.sc");
    }

    /**
     * test Yacute.
     */
    @Test
    public void testGSUBSinglec2scYacute() {

        check("c2sc", "Yacute", "yacute.sc");
    }

    /**
     * test Ydieresis.
     */
    @Test
    public void testGSUBSinglec2scYdieresis() {

        check("c2sc", "Ydieresis", "ydieresis.sc");
    }

    /**
     * test Z.
     */
    @Test
    public void testGSUBSinglec2scZ() {

        check("c2sc", "Z", "z.sc");
    }

    /**
     * test Zacute.
     */
    @Test
    public void testGSUBSinglec2scZacute() {

        check("c2sc", "Zacute", "zacute.sc");
    }

    /**
     * test Zcaron.
     */
    @Test
    public void testGSUBSinglec2scZcaron() {

        check("c2sc", "Zcaron", "zcaron.sc");
    }

    /**
     * test Zdotaccent.
     */
    @Test
    public void testGSUBSinglec2scZdotaccent() {

        check("c2sc", "Zdotaccent", "zdotaccent.sc");
    }

    /**
     * test d.
     */
    @Test
    public void testGSUBSingled() {

        check("sups", "d", "d.superior");
    }

    /**
     * test e.
     */
    @Test
    public void testGSUBSinglee() {

        check("sups", "e", "e.superior");
    }

    /**
     * test eight.
     */
    @Test
    public void testGSUBSingleeight() {

        check("sups", "eight", "eight.superior");
    }

    /**
     * test eight.oldstyle.
     */
    @Test
    public void testGSUBSingleeightoldstyle() {

        check("sups", "eight.oldstyle", "eight.superior");
    }

    /**
     * test equal.
     */
    @Test
    public void testGSUBSingleequal() {

        check("sups", "equal", "equal.superior");
    }

    /**
     * test f.
     */
    @Test
    public void testGSUBSinglef() {

        check("sups", "f", "f.superior");
    }

    /**
     * test sigma.
     */
    @Test
    public void testGSUBSinglefinasigma() {

        check("fina", "sigma", "sigma1");
    }

    /**
     * test five.
     */
    @Test
    public void testGSUBSinglefive() {

        check("sups", "five", "five.superior");
    }

    /**
     * test four.
     */
    @Test
    public void testGSUBSinglefour() {

        check("sups", "four", "four.superior");
    }

    /**
     * test g.
     */
    @Test
    public void testGSUBSingleg() {

        check("sups", "g", "g.superior");
    }

    /**
     * test gammalatin.
     */
    @Test
    public void testGSUBSinglegammalatin() {

        check("sups", "gammalatin", "gammalatin.superior");
    }

    /**
     * test glottalstopreversed.
     */
    @Test
    public void testGSUBSingleglottalstopreversed() {

        check("sups", "glottalstopreversed", "glottalstopreversed.superior");
    }

    /**
     * test h.
     */
    @Test
    public void testGSUBSingleh() {

        check("sups", "h", "h.superior");
    }

    /**
     * test hhook.
     */
    @Test
    public void testGSUBSinglehhook() {

        check("sups", "hhook", "hhook.superior");
    }

    /**
     * test i.
     */
    @Test
    public void testGSUBSinglei() {

        check("sups", "i", "i.superior");
    }

    /**
     * test j.
     */
    @Test
    public void testGSUBSinglej() {

        check("sups", "j", "j.superior");
    }

    /**
     * test k.
     */
    @Test
    public void testGSUBSinglek() {

        check("sups", "k", "k.superior");
    }

    /**
     * test l.
     */
    @Test
    public void testGSUBSinglel() {

        check("sups", "l", "l.superior");
    }

    /**
     * test m.
     */
    @Test
    public void testGSUBSinglem() {

        check("sups", "m", "m.superior");
    }

    /**
     * test minus.
     */
    @Test
    public void testGSUBSingleminus() {

        check("sups", "minus", "minus.superior");
    }

    /**
     * test n.
     */
    @Test
    public void testGSUBSinglen() {

        check("sups", "n", "n.superior");
    }

    /**
     * test nine.
     */
    @Test
    public void testGSUBSinglenine() {

        check("sups", "nine", "nine.superior");
    }

    /**
     * test o.
     */
    @Test
    public void testGSUBSingleo() {

        check("sups", "o", "o.superior");
    }

    /**
     * test one.
     */
    @Test
    public void testGSUBSingleone() {

        check("sups", "one", "one.superior");
    }

    /**
     * test p.
     */
    @Test
    public void testGSUBSinglep() {

        check("sups", "p", "p.superior");
    }

    /**
     * test parenleft.
     */
    @Test
    public void testGSUBSingleparenleft() {

        check("sups", "parenleft", "parenleft.superior");
    }

    /**
     * test parenright.
     */
    @Test
    public void testGSUBSingleparenright() {

        check("sups", "parenright", "parenright.superior");
    }

    /**
     * test plus.
     */
    @Test
    public void testGSUBSingleplus() {

        check("sups", "plus", "plus.superior");
    }

    /**
     * test q.
     */
    @Test
    public void testGSUBSingleq() {

        check("sups", "q", "q.superior");
    }

    /**
     * test r.
     */
    @Test
    public void testGSUBSingler() {

        check("sups", "r", "r.superior");
    }

    /**
     * test rhookturned.
     */
    @Test
    public void testGSUBSinglerhookturned() {

        check("sups", "rhookturned", "rhookturned.superior");
    }

    /**
     * test Rsmallinverted.
     */
    @Test
    public void testGSUBSingleRsmallinverted() {

        check("sups", "Rsmallinverted", "Rsmallinverted.superior");
    }

    /**
     * test rturned.
     */
    @Test
    public void testGSUBSinglerturned() {

        check("sups", "rturned", "rturned.superior");
    }

    /**
     * test s.
     */
    @Test
    public void testGSUBSingles() {

        check("sups", "s", "s.superior");
    }

    /**
     * test seven.
     */
    @Test
    public void testGSUBSingleseven() {

        check("sups", "seven", "seven.superior");
    }

    /**
     * test six.
     */
    @Test
    public void testGSUBSinglesix() {

        check("sups", "six", "six.superior");
    }

    /**
     * test a.
     */
    @Test
    public void testGSUBSinglesmcpa() {

        check("smcp", "a", "a.sc");
    }

    /**
     * test aacute.
     */
    @Test
    public void testGSUBSinglesmcpaacute() {

        check("smcp", "aacute", "aacute.sc");
    }

    /**
     * test abreve.
     */
    @Test
    public void testGSUBSinglesmcpabreve() {

        check("smcp", "abreve", "abreve.sc");
    }

    /**
     * test acircumflex.
     */
    @Test
    public void testGSUBSinglesmcpacircumflex() {

        check("smcp", "acircumflex", "acircumflex.sc");
    }

    /**
     * test adieresis.
     */
    @Test
    public void testGSUBSinglesmcpadieresis() {

        check("smcp", "adieresis", "adieresis.sc");
    }

    /**
     * test ae.
     */
    @Test
    public void testGSUBSinglesmcpae() {

        check("smcp", "ae", "ae.sc");
    }

    /**
     * test agrave.
     */
    @Test
    public void testGSUBSinglesmcpagrave() {

        check("smcp", "agrave", "agrave.sc");
    }

    /**
     * test ampersand.
     */
    @Test
    public void testGSUBSinglesmcpampersand() {

        check("smcp", "ampersand", "ampersand.alt");
    }

    /**
     * test aogonek.
     */
    @Test
    public void testGSUBSinglesmcpaogonek() {

        check("smcp", "aogonek", "aogonek.sc");
    }

    /**
     * test aring.
     */
    @Test
    public void testGSUBSinglesmcparing() {

        check("smcp", "aring", "aring.sc");
    }

    /**
     * test atilde.
     */
    @Test
    public void testGSUBSinglesmcpatilde() {

        check("smcp", "atilde", "atilde.sc");
    }

    /**
     * test b.
     */
    @Test
    public void testGSUBSinglesmcpb() {

        check("smcp", "b", "b.sc");
    }

    /**
     * test c.
     */
    @Test
    public void testGSUBSinglesmcpc() {

        check("smcp", "c", "c.sc");
    }

    /**
     * test cacute.
     */
    @Test
    public void testGSUBSinglesmcpcacute() {

        check("smcp", "cacute", "cacute.sc");
    }

    /**
     * test ccaron.
     */
    @Test
    public void testGSUBSinglesmcpccaron() {

        check("smcp", "ccaron", "ccaron.sc");
    }

    /**
     * test ccedilla.
     */
    @Test
    public void testGSUBSinglesmcpccedilla() {

        check("smcp", "ccedilla", "ccedilla.sc");
    }

    /**
     * test d.
     */
    @Test
    public void testGSUBSinglesmcpd() {

        check("smcp", "d", "d.sc");
    }

    /**
     * test dcaron.
     */
    @Test
    public void testGSUBSinglesmcpdcaron() {

        check("smcp", "dcaron", "dcaron.sc");
    }

    /**
     * test dcroat.
     */
    @Test
    public void testGSUBSinglesmcpdcroat() {

        check("smcp", "dcroat", "dcroat.sc");
    }

    /**
     * test e.
     */
    @Test
    public void testGSUBSinglesmcpe() {

        check("smcp", "e", "e.sc");
    }

    /**
     * test eacute.
     */
    @Test
    public void testGSUBSinglesmcpeacute() {

        check("smcp", "eacute", "eacute.sc");
    }

    /**
     * test ecaron.
     */
    @Test
    public void testGSUBSinglesmcpecaron() {

        check("smcp", "ecaron", "ecaron.sc");
    }

    /**
     * test ecircumflex.
     */
    @Test
    public void testGSUBSinglesmcpecircumflex() {

        check("smcp", "ecircumflex", "ecircumflex.sc");
    }

    /**
     * test edieresis.
     */
    @Test
    public void testGSUBSinglesmcpedieresis() {

        check("smcp", "edieresis", "edieresis.sc");
    }

    /**
     * test egrave.
     */
    @Test
    public void testGSUBSinglesmcpegrave() {

        check("smcp", "egrave", "egrave.sc");
    }

    /**
     * test eng.
     */
    @Test
    public void testGSUBSinglesmcpeng() {

        check("smcp", "eng", "eng.sc");
    }

    /**
     * test eogonek.
     */
    @Test
    public void testGSUBSinglesmcpeogonek() {

        check("smcp", "eogonek", "eogonek.sc");
    }

    /**
     * test eth.
     */
    @Test
    public void testGSUBSinglesmcpeth() {

        check("smcp", "eth", "eth.sc");
    }

    /**
     * test f.
     */
    @Test
    public void testGSUBSinglesmcpf() {

        check("smcp", "f", "f.sc");
    }

    /**
     * test fi.
     */
    @Test
    @Ignore("FIXME")
    public void testGSUBSinglesmcpfi() {

        check("smcp", "fi", "fi.sc");
    }

    /**
     * test fl.
     */
    @Test
    @Ignore("FIXME")
    public void testGSUBSinglesmcpfl() {

        check("smcp", "fl", "fl.sc");
    }

    /**
     * test g.
     */
    @Test
    public void testGSUBSinglesmcpg() {

        check("smcp", "g", "g.sc");
    }

    /**
     * test gbreve.
     */
    @Test
    public void testGSUBSinglesmcpgbreve() {

        check("smcp", "gbreve", "gbreve.sc");
    }

    /**
     * test germandbls.
     */
    @Test
    public void testGSUBSinglesmcpgermandbls() {

        check("smcp", "germandbls", "germandbls.sc");
    }

    /**
     * test h.
     */
    @Test
    public void testGSUBSinglesmcph() {

        check("smcp", "h", "h.sc");
    }

    /**
     * test hyphen.
     */
    @Test
    public void testGSUBSinglesmcphyphen() {

        check("smcp", "hyphen", "hyphen.sc");
    }

    /**
     * test i.
     */
    @Test
    public void testGSUBSinglesmcpi() {

        check("smcp", "i", "i.sc");
    }

    /**
     * test iacute.
     */
    @Test
    public void testGSUBSinglesmcpiacute() {

        check("smcp", "iacute", "iacute.sc");
    }

    /**
     * test icircumflex.
     */
    @Test
    public void testGSUBSinglesmcpicircumflex() {

        check("smcp", "icircumflex", "icircumflex.sc");
    }

    /**
     * test idieresis.
     */
    @Test
    public void testGSUBSinglesmcpidieresis() {

        check("smcp", "idieresis", "idieresis.sc");
    }

    /**
     * test igrave.
     */
    @Test
    public void testGSUBSinglesmcpigrave() {

        check("smcp", "igrave", "igrave.sc");
    }

    /**
     * test ij.
     */
    @Test
    public void testGSUBSinglesmcpij() {

        check("smcp", "ij", "ij.sc");
    }

    /**
     * test j.
     */
    @Test
    public void testGSUBSinglesmcpj() {

        check("smcp", "j", "j.sc");
    }

    /**
     * test k.
     */
    @Test
    public void testGSUBSinglesmcpk() {

        check("smcp", "k", "k.sc");
    }

    /**
     * test kgreenlandic.
     */
    @Test
    public void testGSUBSinglesmcpkgreenlandic() {

        check("smcp", "kgreenlandic", "k.sc");
    }

    /**
     * test l.
     */
    @Test
    public void testGSUBSinglesmcpl() {

        check("smcp", "l", "l.sc");
    }

    /**
     * test lacute.
     */
    @Test
    public void testGSUBSinglesmcplacute() {

        check("smcp", "lacute", "lacute.sc");
    }

    /**
     * test lcaron.
     */
    @Test
    public void testGSUBSinglesmcplcaron() {

        check("smcp", "lcaron", "lcaron.sc");
    }

    /**
     * test longs.
     */
    @Test
    public void testGSUBSinglesmcplongs() {

        check("smcp", "longs", "s.sc");
    }

    /**
     * test lslash.
     */
    @Test
    public void testGSUBSinglesmcplslash() {

        check("smcp", "lslash", "lslash.sc");
    }

    /**
     * test m.
     */
    @Test
    public void testGSUBSinglesmcpm() {

        check("smcp", "m", "m.sc");
    }

    /**
     * test n.
     */
    @Test
    public void testGSUBSinglesmcpn() {

        check("smcp", "n", "n.sc");
    }

    /**
     * test nacute.
     */
    @Test
    public void testGSUBSinglesmcpnacute() {

        check("smcp", "nacute", "nacute.sc");
    }

    /**
     * test ncaron.
     */
    @Test
    public void testGSUBSinglesmcpncaron() {

        check("smcp", "ncaron", "ncaron.sc");
    }

    /**
     * test ntilde.
     */
    @Test
    public void testGSUBSinglesmcpntilde() {

        check("smcp", "ntilde", "ntilde.sc");
    }

    /**
     * test o.
     */
    @Test
    public void testGSUBSinglesmcpo() {

        check("smcp", "o", "o.sc");
    }

    /**
     * test oacute.
     */
    @Test
    public void testGSUBSinglesmcpoacute() {

        check("smcp", "oacute", "oacute.sc");
    }

    /**
     * test ocircumflex.
     */
    @Test
    public void testGSUBSinglesmcpocircumflex() {

        check("smcp", "ocircumflex", "ocircumflex.sc");
    }

    /**
     * test odieresis.
     */
    @Test
    public void testGSUBSinglesmcpodieresis() {

        check("smcp", "odieresis", "odieresis.sc");
    }

    /**
     * test oe.
     */
    @Test
    public void testGSUBSinglesmcpoe() {

        check("smcp", "oe", "oe.sc");
    }

    /**
     * test ograve.
     */
    @Test
    public void testGSUBSinglesmcpograve() {

        check("smcp", "ograve", "ograve.sc");
    }

    /**
     * test ohungarumlaut.
     */
    @Test
    public void testGSUBSinglesmcpohungarumlaut() {

        check("smcp", "ohungarumlaut", "ohungarumlaut.sc");
    }

    /**
     * test oslash.
     */
    @Test
    public void testGSUBSinglesmcposlash() {

        check("smcp", "oslash", "oslash.sc");
    }

    /**
     * test otilde.
     */
    @Test
    public void testGSUBSinglesmcpotilde() {

        check("smcp", "otilde", "otilde.sc");
    }

    /**
     * test p.
     */
    @Test
    public void testGSUBSinglesmcpp() {

        check("smcp", "p", "p.sc");
    }

    /**
     * test q.
     */
    @Test
    public void testGSUBSinglesmcpq() {

        check("smcp", "q", "q.sc");
    }

    /**
     * test r.
     */
    @Test
    public void testGSUBSinglesmcpr() {

        check("smcp", "r", "r.sc");
    }

    /**
     * test racute.
     */
    @Test
    public void testGSUBSinglesmcpracute() {

        check("smcp", "racute", "racute.sc");
    }

    /**
     * test rcaron.
     */
    @Test
    public void testGSUBSinglesmcprcaron() {

        check("smcp", "rcaron", "rcaron.sc");
    }

    /**
     * test s.
     */
    @Test
    public void testGSUBSinglesmcps() {

        check("smcp", "s", "s.sc");
    }

    /**
     * test sacute.
     */
    @Test
    public void testGSUBSinglesmcpsacute() {

        check("smcp", "sacute", "sacute.sc");
    }

    /**
     * test scaron.
     */
    @Test
    public void testGSUBSinglesmcpscaron() {

        check("smcp", "scaron", "scaron.sc");
    }

    /**
     * test scedilla.
     */
    @Test
    public void testGSUBSinglesmcpscedilla() {

        check("smcp", "scedilla", "scedilla.sc");
    }

    /**
     * test t.
     */
    @Test
    public void testGSUBSinglesmcpt() {

        check("smcp", "t", "t.sc");
    }

    /**
     * test tbar.
     */
    @Test
    public void testGSUBSinglesmcptbar() {

        check("smcp", "tbar", "tbar.sc");
    }

    /**
     * test tcaron.
     */
    @Test
    public void testGSUBSinglesmcptcaron() {

        check("smcp", "tcaron", "tcaron.sc");
    }

    /**
     * test tcommaaccent.
     */
    @Test
    public void testGSUBSinglesmcptcommaaccent() {

        check("smcp", "tcommaaccent", "tcommaaccent.sc");
    }

    /**
     * test thorn.
     */
    @Test
    public void testGSUBSinglesmcpthorn() {

        check("smcp", "thorn", "thorn.sc");
    }

    /**
     * test u.
     */
    @Test
    public void testGSUBSinglesmcpu() {

        check("smcp", "u", "u.sc");
    }

    /**
     * test uacute.
     */
    @Test
    public void testGSUBSinglesmcpuacute() {

        check("smcp", "uacute", "uacute.sc");
    }

    /**
     * test ucircumflex.
     */
    @Test
    public void testGSUBSinglesmcpucircumflex() {

        check("smcp", "ucircumflex", "ucircumflex.sc");
    }

    /**
     * test udieresis.
     */
    @Test
    public void testGSUBSinglesmcpudieresis() {

        check("smcp", "udieresis", "udieresis.sc");
    }

    /**
     * test ugrave.
     */
    @Test
    public void testGSUBSinglesmcpugrave() {

        check("smcp", "ugrave", "ugrave.sc");
    }

    /**
     * test uhungarumlaut.
     */
    @Test
    public void testGSUBSinglesmcpuhungarumlaut() {

        check("smcp", "uhungarumlaut", "uhungarumlaut.sc");
    }

    /**
     * test uring.
     */
    @Test
    public void testGSUBSinglesmcpuring() {

        check("smcp", "uring", "uring.sc");
    }

    /**
     * test v.
     */
    @Test
    public void testGSUBSinglesmcpv() {

        check("smcp", "v", "v.sc");
    }

    /**
     * test w.
     */
    @Test
    public void testGSUBSinglesmcpw() {

        check("smcp", "w", "w.sc");
    }

    /**
     * test x.
     */
    @Test
    public void testGSUBSinglesmcpx() {

        check("smcp", "x", "x.sc");
    }

    /**
     * test y.
     */
    @Test
    public void testGSUBSinglesmcpy() {

        check("smcp", "y", "y.sc");
    }

    /**
     * test yacute.
     */
    @Test
    public void testGSUBSinglesmcpyacute() {

        check("smcp", "yacute", "yacute.sc");
    }

    /**
     * test ydieresis.
     */
    @Test
    public void testGSUBSinglesmcpydieresis() {

        check("smcp", "ydieresis", "ydieresis.sc");
    }

    /**
     * test z.
     */
    @Test
    public void testGSUBSinglesmcpz() {

        check("smcp", "z", "z.sc");
    }

    /**
     * test zacute.
     */
    @Test
    public void testGSUBSinglesmcpzacute() {

        check("smcp", "zacute", "zacute.sc");
    }

    /**
     * test zcaron.
     */
    @Test
    public void testGSUBSinglesmcpzcaron() {

        check("smcp", "zcaron", "zcaron.sc");
    }

    /**
     * test zdotaccent.
     */
    @Test
    public void testGSUBSinglesmcpzdotaccent() {

        check("smcp", "zdotaccent", "zdotaccent.sc");
    }

    /**
     * test a.
     */
    @Test
    public void testGSUBSinglesupsa() {

        check("sups", "a", "a.superior");
    }

    /**
     * test b.
     */
    @Test
    public void testGSUBSinglesupsb() {

        check("sups", "b", "b.superior");
    }

    /**
     * test c.
     */
    @Test
    public void testGSUBSinglesupsc() {

        check("sups", "c", "c.superior");
    }

    /**
     * test d.
     */
    @Test
    public void testGSUBSinglesupsd() {

        check("sups", "d", "d.superior");
    }

    /**
     * test e.
     */
    @Test
    public void testGSUBSinglesupse() {

        check("sups", "e", "e.superior");
    }

    /**
     * test eight.
     */
    @Test
    public void testGSUBSinglesupseight() {

        check("sups", "eight", "eight.superior");
    }

    /**
     * test eight.oldstyle.
     */
    @Test
    public void testGSUBSinglesupseightoldstyle() {

        check("sups", "eight.oldstyle", "eight.superior");
    }

    /**
     * test equal.
     */
    @Test
    public void testGSUBSinglesupsequal() {

        check("sups", "equal", "equal.superior");
    }

    /**
     * test f.
     */
    @Test
    public void testGSUBSinglesupsf() {

        check("sups", "f", "f.superior");
    }

    /**
     * test five.
     */
    @Test
    public void testGSUBSinglesupsfive() {

        check("sups", "five", "five.superior");
    }

    /**
     * test four.
     */
    @Test
    public void testGSUBSinglesupsfour() {

        check("sups", "four", "four.superior");
    }

    /**
     * test g.
     */
    @Test
    public void testGSUBSinglesupsg() {

        check("sups", "g", "g.superior");
    }

    /**
     * test gammalatin.
     */
    @Test
    public void testGSUBSinglesupsgammalatin() {

        check("sups", "gammalatin", "gammalatin.superior");
    }

    /**
     * test glottalstopreversed.
     */
    @Test
    public void testGSUBSinglesupsglottalstopreversed() {

        check("sups", "glottalstopreversed", "glottalstopreversed.superior");
    }

    /**
     * test h.
     */
    @Test
    public void testGSUBSinglesupsh() {

        check("sups", "h", "h.superior");
    }

    /**
     * test hhook.
     */
    @Test
    public void testGSUBSinglesupshhook() {

        check("sups", "hhook", "hhook.superior");
    }

    /**
     * test i.
     */
    @Test
    public void testGSUBSinglesupsi() {

        check("sups", "i", "i.superior");
    }

    /**
     * test j.
     */
    @Test
    public void testGSUBSinglesupsj() {

        check("sups", "j", "j.superior");
    }

    /**
     * test k.
     */
    @Test
    public void testGSUBSinglesupsk() {

        check("sups", "k", "k.superior");
    }

    /**
     * test l.
     */
    @Test
    public void testGSUBSinglesupsl() {

        check("sups", "l", "l.superior");
    }

    /**
     * test m.
     */
    @Test
    public void testGSUBSinglesupsm() {

        check("sups", "m", "m.superior");
    }

    /**
     * test minus.
     */
    @Test
    public void testGSUBSinglesupsminus() {

        check("sups", "minus", "minus.superior");
    }

    /**
     * test n.
     */
    @Test
    public void testGSUBSinglesupsn() {

        check("sups", "n", "n.superior");
    }

    /**
     * test nine.
     */
    @Test
    public void testGSUBSinglesupsnine() {

        check("sups", "nine", "nine.superior");
    }

    /**
     * test o.
     */
    @Test
    public void testGSUBSinglesupso() {

        check("sups", "o", "o.superior");
    }

    /**
     * test one.
     */
    @Test
    public void testGSUBSinglesupsone() {

        check("sups", "one", "one.superior");
    }

    /**
     * test p.
     */
    @Test
    public void testGSUBSinglesupsp() {

        check("sups", "p", "p.superior");
    }

    /**
     * test parenleft.
     */
    @Test
    public void testGSUBSinglesupsparenleft() {

        check("sups", "parenleft", "parenleft.superior");
    }

    /**
     * test parenright.
     */
    @Test
    public void testGSUBSinglesupsparenright() {

        check("sups", "parenright", "parenright.superior");
    }

    /**
     * test plus.
     */
    @Test
    public void testGSUBSinglesupsplus() {

        check("sups", "plus", "plus.superior");
    }

    /**
     * test q.
     */
    @Test
    public void testGSUBSinglesupsq() {

        check("sups", "q", "q.superior");
    }

    /**
     * test r.
     */
    @Test
    public void testGSUBSinglesupsr() {

        check("sups", "r", "r.superior");
    }

    /**
     * test rhookturned.
     */
    @Test
    public void testGSUBSinglesupsrhookturned() {

        check("sups", "rhookturned", "rhookturned.superior");
    }

    /**
     * test Rsmallinverted.
     */
    @Test
    public void testGSUBSinglesupsRsmallinverted() {

        check("sups", "Rsmallinverted", "Rsmallinverted.superior");
    }

    /**
     * test rturned.
     */
    @Test
    public void testGSUBSinglesupsrturned() {

        check("sups", "rturned", "rturned.superior");
    }

    /**
     * test s.
     */
    @Test
    public void testGSUBSinglesupss() {

        check("sups", "s", "s.superior");
    }

    /**
     * test seven.
     */
    @Test
    public void testGSUBSinglesupsseven() {

        check("sups", "seven", "seven.superior");
    }

    /**
     * test six.
     */
    @Test
    public void testGSUBSinglesupssix() {

        check("sups", "six", "six.superior");
    }

    /**
     * test t.
     */
    @Test
    public void testGSUBSinglesupst() {

        check("sups", "t", "t.superior");
    }

    /**
     * test three.
     */
    @Test
    public void testGSUBSinglesupsthree() {

        check("sups", "three", "three.superior");
    }

    /**
     * test two.
     */
    @Test
    public void testGSUBSinglesupstwo() {

        check("sups", "two", "two.superior");
    }

    /**
     * test u.
     */
    @Test
    public void testGSUBSinglesupsu() {

        check("sups", "u", "u.superior");
    }

    /**
     * test v.
     */
    @Test
    public void testGSUBSinglesupsv() {

        check("sups", "v", "v.superior");
    }

    /**
     * test w.
     */
    @Test
    public void testGSUBSinglesupsw() {

        check("sups", "w", "w.superior");
    }

    /**
     * test x.
     */
    @Test
    public void testGSUBSinglesupsx() {

        check("sups", "x", "x.superior");
    }

    /**
     * test y.
     */
    @Test
    public void testGSUBSinglesupsy() {

        check("sups", "y", "y.superior");
    }

    /**
     * test z.
     */
    @Test
    public void testGSUBSinglesupsz() {

        check("sups", "z", "z.superior");
    }

    /**
     * test zero.
     */
    @Test
    public void testGSUBSinglesupszero() {

        check("sups", "zero", "zero.superior");
    }

    /**
     * test zero.slash.
     */
    @Test
    public void testGSUBSinglesupszeroslash() {

        check("sups", "zero.slash", "zero.superior");
    }

    /**
     * test t.
     */
    @Test
    public void testGSUBSinglet() {

        check("sups", "t", "t.superior");
    }

    /**
     * test three.
     */
    @Test
    public void testGSUBSinglethree() {

        check("sups", "three", "three.superior");
    }

    /**
     * test two.
     */
    @Test
    public void testGSUBSingletwo() {

        check("sups", "two", "two.superior");
    }

    /**
     * test u.
     */
    @Test
    public void testGSUBSingleu() {

        check("sups", "u", "u.superior");
    }

    /**
     * test v.
     */
    @Test
    public void testGSUBSinglev() {

        check("sups", "v", "v.superior");
    }

    /**
     * test w.
     */
    @Test
    public void testGSUBSinglew() {

        check("sups", "w", "w.superior");
    }

    /**
     * test x.
     */
    @Test
    public void testGSUBSinglex() {

        check("sups", "x", "x.superior");
    }

    /**
     * test y.
     */
    @Test
    public void testGSUBSingley() {

        check("sups", "y", "y.superior");
    }

    /**
     * test z.
     */
    @Test
    public void testGSUBSinglez() {

        check("sups", "z", "z.superior");
    }

    /**
     * test zero.
     */
    @Test
    public void testGSUBSinglezero() {

        check("zero", "zero", "zero.slash");
    }

    /**
     * test zero.fitted.
     */
    @Test
    public void testGSUBSinglezerofitted() {

        check("zero", "zero.fitted", "zero.slashfitted");
    }

    /**
     * test zero.slash.
     */
    @Test
    public void testGSUBSinglezeroslash() {

        check("sups", "zero.slash", "zero.superior");
    }

    /**
     * Test: write the xml output to 'target'
     *
     * @throws Exception if an error occurred.
     */
    @Test
    public void testXmlOut() throws Exception {

        assertNotNull( reader );
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
