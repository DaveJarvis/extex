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

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * Test for fontinst.
 * 
 * <pre>
 * \input fontinst.sty
 * \recordtransforms{driver.recs}
 *
 * \transformfont{fxlr-8r}{\reencodefont{8r}{%
 * \fromafm{fxlr}}}
 * 
 * \installfonts
 * \installfont{fxlr-t1}{fxlr-8r,newlatin}{t1}%
 *             {T1}{fxl}{m}{n}{}
 * \endinstallfonts
 * 
 * \endrecordtransforms
 * \input finstmsc.sty
 * \resetstr{PSfontsuffix}{.pfb}
 * \declarepsencoding{T1}{TeXBase1Encoding}%
 *                   {\download{8r.enc}}
 * \adddriver{dvips}{libertine_t1.map}
 * \input driver.recs
 * \donedrivers
 * \bye
 * </pre>
 * 
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontInst04Test extends ExTeXLauncher {

    /**
     * The calender.
     */
    private static final Calendar _CAL = Calendar.getInstance();

    /**
     * The day.
     */
    private static final int _D = _CAL.get(Calendar.DAY_OF_MONTH);

    /**
     * The month.
     */
    private static final int _M = _CAL.get(Calendar.MONTH) + 1;

    /**
     * The year.
     */
    private static final int _Y = _CAL.get(Calendar.YEAR);

    /**
     * The field <tt>SEP</tt> contains the separator for properties.
     */
    private static final String SEP = System.getProperty("path.separator", ":");

    /**
     * The tex commands.
     */
    private static final String TEX_CMDS = "\\input fontinst.sty\n"
            + "\\recordtransforms{driver.recs}\n" + "\n"
            + "\\transformfont{fxlr-8r}{\\reencodefont{8r}{%\n"
            + "     \\fromafm{fxlr}}}\n" + "\n" + "\\installfonts\n"
            + "\\installfont{fxlr-t1}{fxlr-8r,newlatin}{t1}%\n"
            + "     {T1}{fxl}{m}{n}{}\n" + "\\endinstallfonts\n" + "\n"
            + "\\endrecordtransforms\n" + "\\input finstmsc.sty\n"
            + "\\resetstr{PSfontsuffix}{.pfb}\n"
            + "\\declarepsencoding{T1}{TeXBase1Encoding}%\n"
            + "    {\\download{8r.enc}}\n"
            + "\\adddriver{dvips}{libertine_t1.map}\n"
            + "\\input driver.recs\n" + "\\donedrivers\n" + "\\bye\n";

    /**
     * The tex output (driver.recs)
     */
    private static final String TEXT_DRIVER =
            "\\storemapdata{fxlr}{\\fromafm{fxlr}{LinLibertine}}{}\n"
                    + "\\storemapdata{fxlr-8r}{\\frommtx{fxlr}}{\\reencodefont{8r}}\n"
                    + "\\makemapentry{fxlr-8r}\n";

    /**
     * The tex output (libertine_t1.map)
     */
    private static final String TEXT_MAP =
            "fxlr-8r LinLibertine <8r.enc <fxlr.pfb \" TeXBase1Encoding ReEncodeFont \"\n";

    /**
     * The tex output (t1fxl.fd)
     */
    private static final String TEXT_T1FXL_FD = "%Filename: t1fxl.fd\n"
            + "%Created by: tex create\n" + "%Created using fontinst v1.929\n"
            + "\n" + "%THIS FILE SHOULD BE PUT IN A TEX INPUTS DIRECTORY\n"
            + "\n" + "\\ProvidesFile{t1fxl.fd}\n" + "   [" + _Y + "/" + _M
            + "/" + _D + " Fontinst v1.929 font definitions for T1/fxl.]\n"
            + "\n" + "\\DeclareFontFamily{T1}{fxl}{}\n" + "\n"
            + "\\DeclareFontShape{T1}{fxl}{m}{n}{\n" + "   <-> fxlr-t1\n"
            + "}{}\n" + "\n" + "\n" + "\\endinput\n";

    /**
     * Creates a new object.
     */
    public FontInst04Test() {

        setConfig("fontinst-test.xml");

        // delete temp files after the test
        new File("texput.log").deleteOnExit();
        new File("fxlr.mtx").deleteOnExit();
        new File("fxlr.pl").deleteOnExit();
        new File("fxlr-8r.mtx").deleteOnExit();
        new File("fxlr-8r.pl").deleteOnExit();
        new File("fxlr-t1.vpl").deleteOnExit();
        new File("driver.recs").deleteOnExit();
        new File("libertine_t1.map").deleteOnExit();
        new File("t1fxl.fd").deleteOnExit();
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
                    "../texmf/src/texmf/fonts/afm/" + SEP + //
                    "../texmf/src/texmf/fonts/enc/" + SEP + //
                    "../ExTeX-fontware/src/texmf/tex/misc" //
        );
        // props.setProperty("extex.launcher.trace", "true");
        // props.setProperty("extex.launcher.time", "true");
        return props;
    }

    /**
     * Test for fontinst misc.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testTransformTestT1() throws Exception {

        setConfig("tex");
        assertOutput(getMyProps(), // --- input code ---
            TEX_CMDS,
            // --- log channel ---
            "No file fontinst.rc.\n" //
                    + "Metrics written on fxlr.mtx.\n"//
                    + "Raw font written on fxlr.pl.\n" // +
                    + "Transformed metrics written on fxlr-8r.mtx.\n" //
                    + "Raw font written on fxlr-8r.pl.\n"
                    + "Warning: missing glyph `perthousandzero'.\n"
                    + "Warning: missing glyph `Eng'.\n"
                    + "Warning: missing glyph `eng'.\n"
                    + "Virtual font written on fxlr-t1.vpl.\n"
                    + "Font definitions written on t1fxl.fd.\n"
                    + "Font transformation records written on driver.recs.\n"
                    + "No file finstmsc.rc.\n"
                    + "Map file fragments written on libertine_t1.map.\n",

            // --- output channel ---
            TERM);
    }

    /**
     * Test for fontinst misc.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testTransformTestT2() throws Exception {

        setConfig("tex");
        assertOutput(getMyProps(), // --- input code ---
            TEX_CMDS,
            // --- log channel ---
            "No file fontinst.rc.\n" //
                    + "Metrics written on fxlr.mtx.\n"//
                    + "Raw font written on fxlr.pl.\n" // +
                    + "Transformed metrics written on fxlr-8r.mtx.\n" //
                    + "Raw font written on fxlr-8r.pl.\n"
                    + "Warning: missing glyph `perthousandzero'.\n"
                    + "Warning: missing glyph `Eng'.\n"
                    + "Warning: missing glyph `eng'.\n"
                    + "Virtual font written on fxlr-t1.vpl.\n"
                    + "Font definitions written on t1fxl.fd.\n"
                    + "Font transformation records written on driver.recs.\n"
                    + "No file finstmsc.rc.\n"
                    + "Map file fragments written on libertine_t1.map.\n",

            // --- output channel ---
            TERM);

        // check the output file.
        File pl = new File("t1fxl.fd");
        FileInputStream stream = new FileInputStream(pl);
        assertNotNull(stream);
        StringBuilder sb = new StringBuilder();
        for (int c = stream.read(); c >= 0; c = stream.read()) {
            sb.append((char) c);
        }
        stream.close();
        assertEquals("generated file t1fxl.fd", TEXT_T1FXL_FD, sb.toString());

    }

    /**
     * Test for fontinst misc.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testTransformTestT3() throws Exception {

        setConfig("tex");
        assertOutput(getMyProps(), // --- input code ---
            TEX_CMDS,
            // --- log channel ---
            "No file fontinst.rc.\n" //
                    + "Metrics written on fxlr.mtx.\n"//
                    + "Raw font written on fxlr.pl.\n" // +
                    + "Transformed metrics written on fxlr-8r.mtx.\n" //
                    + "Raw font written on fxlr-8r.pl.\n"
                    + "Warning: missing glyph `perthousandzero'.\n"
                    + "Warning: missing glyph `Eng'.\n"
                    + "Warning: missing glyph `eng'.\n"
                    + "Virtual font written on fxlr-t1.vpl.\n"
                    + "Font definitions written on t1fxl.fd.\n"
                    + "Font transformation records written on driver.recs.\n"
                    + "No file finstmsc.rc.\n"
                    + "Map file fragments written on libertine_t1.map.\n",

            // --- output channel ---
            TERM);

        // check the output file.
        File pl = new File("libertine_t1.map");
        FileInputStream stream = new FileInputStream(pl);
        assertNotNull(stream);
        StringBuilder sb = new StringBuilder();
        for (int c = stream.read(); c >= 0; c = stream.read()) {
            sb.append((char) c);
        }
        stream.close();
        assertEquals("generated file libertine_t1.map", TEXT_MAP, sb.toString());

    }

    /**
     * Test for fontinst misc.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testTransformTestT4() throws Exception {

        setConfig("tex");
        assertOutput(getMyProps(), // --- input code ---
            TEX_CMDS,
            // --- log channel ---
            "No file fontinst.rc.\n" //
                    + "Metrics written on fxlr.mtx.\n"//
                    + "Raw font written on fxlr.pl.\n" // +
                    + "Transformed metrics written on fxlr-8r.mtx.\n" //
                    + "Raw font written on fxlr-8r.pl.\n"
                    + "Warning: missing glyph `perthousandzero'.\n"
                    + "Warning: missing glyph `Eng'.\n"
                    + "Warning: missing glyph `eng'.\n"
                    + "Virtual font written on fxlr-t1.vpl.\n"
                    + "Font definitions written on t1fxl.fd.\n"
                    + "Font transformation records written on driver.recs.\n"
                    + "No file finstmsc.rc.\n"
                    + "Map file fragments written on libertine_t1.map.\n",

            // --- output channel ---
            TERM);

        // check the output file.
        File pl = new File("driver.recs");
        FileInputStream stream = new FileInputStream(pl);
        assertNotNull(stream);
        StringBuilder sb = new StringBuilder();
        for (int c = stream.read(); c >= 0; c = stream.read()) {
            sb.append((char) c);
        }
        stream.close();
        assertEquals("generated file driver.recs", TEXT_DRIVER, sb.toString());

    }

}
