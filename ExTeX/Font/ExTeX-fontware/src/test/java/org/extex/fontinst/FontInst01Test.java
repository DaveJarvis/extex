/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.fontinst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Properties;

import org.extex.font.format.texencoding.EncReader;
import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * Test for fontinst.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontInst01Test extends ExTeXLauncher {

    /**
     * The field <tt>SEP</tt> contains the separator for properties.
     */
    private static final String SEP = System.getProperty("path.separator", ":");


    public FontInst01Test() {

        setConfig("fontinst-test.xml");

        // delete temp files after the test
        new File("texput.log").deleteOnExit();
    }

    /**
     * Returns the properties for the test case.
     * 
     * @return Returns the properties for the test case.
     */
    private Properties getMyProps() {

        Properties props = getProps();
        props.setProperty("extex.texinputs", //
            "../ExTeX-fontware/src/texmf/tex/fontinst/base" + SEP + //
                    "../ExTeX-fontware/src/texmf/tex/fontinst/latinetx" + SEP + //
                    "../ExTeX-fontware/src/texmf/tex/fontinst/latinmtx" + SEP + //
                    "../ExTeX-fontware/src/texmf/tex/fontinst/mathetx" + SEP + //
                    "../ExTeX-fontware/src/texmf/tex/fontinst/mathmtx" + SEP + //
                    "../ExTeX-fontware/src/texmf/tex/fontinst/misc" + SEP + //
                    "../ExTeX-fontware/src/texmf/tex/fontinst/smbletx" + SEP + //
                    "../ExTeX-fontware/src/texmf/tex/fontinst/smblmtx" + SEP + //
                    "../ExTeX-fontware/src/texmf/tex/misc" //
        );
        // props.setProperty("extex.launcher.trace", "true");
        return props;
    }

    /**
     * Test for fontinst misc.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testMisc01() throws Exception {

        setConfig("tex");
        assertOutput(getMyProps(), // --- input code ---
            "\\input finstmsc.sty " + "\\etxtoenc{T1}{t1} " + "\\bye",
            // --- log channel ---
            "No file finstmsc.rc.\nEncoding vector written on t1.enc.\n",
            // --- output channel ---
            TERM);

        Calendar cal = Calendar.getInstance();
        File enc = new File("t1.enc");
        FileInputStream stream = new FileInputStream(enc);
        assertNotNull(stream);
        StringBuilder sb = new StringBuilder();
        for (int c = stream.read(); c >= 0; c = stream.read()) {
            sb.append((char) c);
        }
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DAY_OF_MONTH);
        stream.close();
        assertEquals(
            "generated file",
            "%!PS-Adobe-3.0 Resource-Encoding\n" //
                    + "% @psencodingfile{\n"
                    + "%    author = \"See file T1.etx\",\n"
                    + "%    version = \"See file T1.etx\",\n"
                    + "%    date = \"generated "
                    + Integer.toString(cal.get(Calendar.YEAR))
                    + "/"
                    + (m < 10 ? "0" : "")
                    + Integer.toString(m)
                    + "/"
                    + (d < 10 ? "0" : "")
                    + Integer.toString(d)
                    + "\",\n"
                    + "%    filename = \"t1.enc\",\n"
                    + "%    email = \"See file T1.etx\",\n"
                    + "%    codetable = \"ISO/ASCII\",\n"
                    + "%    checksum = \"\",\n"
                    + "%    abstract = \"This is a postscript encoding file, automatically generated by fontinst from T1.etx.\"\n"
                    + "% }\n"
                    + "\n"
                    + "% Created by: tex texput\n"
                    + "% Created using: \\etxtoenc{T1}{t1}\n"
                    + "\n"
                    + "% This file should be installed somewhere that your DVI\n"
                    + "% to postscript driver looks for files. It is needed for\n"
                    + "% reencoding some font you have transformed.\n"
                    + "\n"
                    + "% After installing this file, you should add the following\n"
                    + "% line (minus %) to your finstmsc.rc file:\n"
                    + "% \\declarepsencoding{T1}{fontinst-autoenc-T1}{\\download{t1.enc}}\n"
                    + "\n"
                    + "%%BeginResource: encoding fontinst-autoenc-T1\n"
                    + "/fontinst-autoenc-T1 [\n"
                    + "% 0\n"
                    + "/grave\n"
                    + "/acute\n"
                    + "/circumflex\n"
                    + "/tilde\n"
                    + "/dieresis\n"
                    + "/hungarumlaut\n"
                    + "/ring\n"
                    + "/caron\n"
                    + "% 8\n"
                    + "/breve\n"
                    + "/macron\n"
                    + "/dotaccent\n"
                    + "/cedilla\n"
                    + "/ogonek\n"
                    + "/quotesinglbase\n"
                    + "/guilsinglleft\n"
                    + "/guilsinglright\n"
                    + "% 16\n"
                    + "/quotedblleft\n"
                    + "/quotedblright\n"
                    + "/quotedblbase\n"
                    + "/guillemotleft\n"
                    + "/guillemotright\n"
                    + "/rangedash\n"
                    + "/punctdash\n"
                    + "/compwordmark\n"
                    + "% 24\n"
                    + "/perthousandzero\n"
                    + "/dotlessi\n"
                    + "/dotlessj\n"
                    + "/ff\n"
                    + "/fi\n"
                    + "/fl\n"
                    + "/ffi\n"
                    + "/ffl\n"
                    + "% 32\n"
                    + "/visiblespace\n"
                    + "/exclam\n"
                    + "/quotedbl\n"
                    + "/numbersign\n"
                    + "/dollar\n"
                    + "/percent\n"
                    + "/ampersand\n"
                    + "/quoteright\n"
                    + "% 40\n"
                    + "/parenleft\n"
                    + "/parenright\n"
                    + "/asterisk\n"
                    + "/plus\n"
                    + "/comma\n"
                    + "/hyphen\n"
                    + "/period\n"
                    + "/slash\n"
                    + "% 48\n"
                    + "/zero\n"
                    + "/one\n"
                    + "/two\n"
                    + "/three\n"
                    + "/four\n"
                    + "/five\n"
                    + "/six\n"
                    + "/seven\n"
                    + "% 56\n"
                    + "/eight\n"
                    + "/nine\n"
                    + "/colon\n"
                    + "/semicolon\n"
                    + "/less\n"
                    + "/equal\n"
                    + "/greater\n"
                    + "/question\n"
                    + "% 64\n"
                    + "/at\n"
                    + "/A\n"
                    + "/B\n"
                    + "/C\n"
                    + "/D\n"
                    + "/E\n"
                    + "/F\n"
                    + "/G\n"
                    + "% 72\n"
                    + "/H\n"
                    + "/I\n"
                    + "/J\n"
                    + "/K\n"
                    + "/L\n"
                    + "/M\n"
                    + "/N\n"
                    + "/O\n"
                    + "% 80\n"
                    + "/P\n"
                    + "/Q\n"
                    + "/R\n"
                    + "/S\n"
                    + "/T\n"
                    + "/U\n"
                    + "/V\n"
                    + "/W\n"
                    + "% 88\n"
                    + "/X\n"
                    + "/Y\n"
                    + "/Z\n"
                    + "/bracketleft\n"
                    + "/backslash\n"
                    + "/bracketright\n"
                    + "/asciicircum\n"
                    + "/underscore\n"
                    + "% 96\n"
                    + "/quoteleft\n"
                    + "/a\n"
                    + "/b\n"
                    + "/c\n"
                    + "/d\n"
                    + "/e\n"
                    + "/f\n"
                    + "/g\n"
                    + "% 104\n"
                    + "/h\n"
                    + "/i\n"
                    + "/j\n"
                    + "/k\n"
                    + "/l\n"
                    + "/m\n"
                    + "/n\n"
                    + "/o\n"
                    + "% 112\n"
                    + "/p\n"
                    + "/q\n"
                    + "/r\n"
                    + "/s\n"
                    + "/t\n"
                    + "/u\n"
                    + "/v\n"
                    + "/w\n"
                    + "% 120\n"
                    + "/x\n"
                    + "/y\n"
                    + "/z\n"
                    + "/braceleft\n"
                    + "/bar\n"
                    + "/braceright\n"
                    + "/asciitilde\n"
                    + "/hyphenchar\n"
                    + "% 128\n"
                    + "/Abreve\n"
                    + "/Aogonek\n"
                    + "/Cacute\n"
                    + "/Ccaron\n"
                    + "/Dcaron\n"
                    + "/Ecaron\n"
                    + "/Eogonek\n"
                    + "/Gbreve\n"
                    + "% 136\n"
                    + "/Lacute\n"
                    + "/Lcaron\n"
                    + "/Lslash\n"
                    + "/Nacute\n"
                    + "/Ncaron\n"
                    + "/Ng\n"
                    + "/Ohungarumlaut\n"
                    + "/Racute\n"
                    + "% 144\n"
                    + "/Rcaron\n"
                    + "/Sacute\n"
                    + "/Scaron\n"
                    + "/Scedilla\n"
                    + "/Tcaron\n"
                    + "/Tcedilla\n"
                    + "/Uhungarumlaut\n"
                    + "/Uring\n"
                    + "% 152\n"
                    + "/Ydieresis\n"
                    + "/Zacute\n"
                    + "/Zcaron\n"
                    + "/Zdotaccent\n"
                    + "/IJ\n"
                    + "/Idotaccent\n"
                    + "/dbar\n"
                    + "/section\n"
                    + "% 160\n"
                    + "/abreve\n"
                    + "/aogonek\n"
                    + "/cacute\n"
                    + "/ccaron\n"
                    + "/dcaron\n"
                    + "/ecaron\n"
                    + "/eogonek\n"
                    + "/gbreve\n"
                    + "% 168\n"
                    + "/lacute\n"
                    + "/lcaron\n"
                    + "/lslash\n"
                    + "/nacute\n"
                    + "/ncaron\n"
                    + "/ng\n"
                    + "/ohungarumlaut\n"
                    + "/racute\n"
                    + "% 176\n"
                    + "/rcaron\n"
                    + "/sacute\n"
                    + "/scaron\n"
                    + "/scedilla\n"
                    + "/tcaron\n"
                    + "/tcedilla\n"
                    + "/uhungarumlaut\n"
                    + "/uring\n"
                    + "% 184\n"
                    + "/ydieresis\n"
                    + "/zacute\n"
                    + "/zcaron\n"
                    + "/zdotaccent\n"
                    + "/ij\n"
                    + "/exclamdown\n"
                    + "/questiondown\n"
                    + "/sterling\n"
                    + "% 192\n"
                    + "/Agrave\n"
                    + "/Aacute\n"
                    + "/Acircumflex\n"
                    + "/Atilde\n"
                    + "/Adieresis\n"
                    + "/Aring\n"
                    + "/AE\n"
                    + "/Ccedilla\n"
                    + "% 200\n"
                    + "/Egrave\n"
                    + "/Eacute\n"
                    + "/Ecircumflex\n"
                    + "/Edieresis\n"
                    + "/Igrave\n"
                    + "/Iacute\n"
                    + "/Icircumflex\n"
                    + "/Idieresis\n"
                    + "% 208\n"
                    + "/Eth\n"
                    + "/Ntilde\n"
                    + "/Ograve\n"
                    + "/Oacute\n"
                    + "/Ocircumflex\n"
                    + "/Otilde\n"
                    + "/Odieresis\n"
                    + "/OE\n"
                    + "% 216\n"
                    + "/Oslash\n"
                    + "/Ugrave\n"
                    + "/Uacute\n"
                    + "/Ucircumflex\n"
                    + "/Udieresis\n"
                    + "/Yacute\n"
                    + "/Thorn\n"
                    + "/SS\n"
                    + "% 224\n"
                    + "/agrave\n"
                    + "/aacute\n"
                    + "/acircumflex\n"
                    + "/atilde\n"
                    + "/adieresis\n"
                    + "/aring\n"
                    + "/ae\n"
                    + "/ccedilla\n"
                    + "% 232\n"
                    + "/egrave\n"
                    + "/eacute\n"
                    + "/ecircumflex\n"
                    + "/edieresis\n"
                    + "/igrave\n"
                    + "/iacute\n"
                    + "/icircumflex\n"
                    + "/idieresis\n"
                    + "% 240\n"
                    + "/eth\n"
                    + "/ntilde\n"
                    + "/ograve\n"
                    + "/oacute\n"
                    + "/ocircumflex\n"
                    + "/otilde\n"
                    + "/odieresis\n"
                    + "/oe\n"
                    + "% 248\n"
                    + "/oslash\n"
                    + "/ugrave\n"
                    + "/uacute\n"
                    + "/ucircumflex\n"
                    + "/udieresis\n"
                    + "/yacute\n"
                    + "/thorn\n"
                    + "/germandbls\n"
                    + "] def\n"
                    + "%%EndResource\n"
                    + "\n"
                    + "% End of file t1.enc.\n", sb.toString());

        enc.deleteOnExit();
    }

    /**
     * Test for fontinst misc.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testMisc02() throws Exception {

        setConfig("tex");
        assertOutput(getMyProps(), // --- input code ---
            "\\input finstmsc.sty " + "\\etxtoenc{T1}{t1} " + "\\bye",
            // --- log channel ---
            "No file finstmsc.rc.\nEncoding vector written on t1.enc.\n",
            // --- output channel ---
            TERM);

        File enc = new File("t1.enc");
        assertNotNull(enc.canRead());
        FileInputStream in = new FileInputStream(enc);
        assertNotNull(in);
        EncReader reader = new EncReader(in);

        assertEquals("fontinst-autoenc-T1", reader.getEncname());

        String[] tab = reader.getTableWithoutSlash();
        assertNotNull(tab);
        assertEquals(256, tab.length);
        assertEquals("grave", tab[0]);
        assertEquals("quotedblleft", tab[16]);
        assertEquals("P", tab[80]);
        assertEquals("oslash", tab[248]);
        assertEquals("germandbls", tab[255]);

        enc.deleteOnExit();
    }

}
