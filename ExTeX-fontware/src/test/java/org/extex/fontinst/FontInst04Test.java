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

package org.extex.fontinst;

import java.io.File;
import java.util.Properties;

import org.extex.test.ExTeXLauncher;

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
     * The field <tt>SEP</tt> contains the separator for properties.
     */
    private static final String SEP = System.getProperty("path.separator", ":");

    /**
     * Creates a new object.
     */
    public FontInst04Test() {

        super("FontInst04Test");

        setConfig("fontinst-test.xml");

        // delete temp files after the test
        new File("texput.log").deleteOnExit();
        new File("fxlr.mtx").deleteOnExit();
        new File("fxlr.pl").deleteOnExit();
        new File("fxlr-8r.mtx").deleteOnExit();
        new File("fxlr-8r.pl").deleteOnExit();
        new File("fxlr-t1.vpl").deleteOnExit();
        new File("drivers.recs").deleteOnExit();
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
     * The tex commands.
     */
    private static final String TEX_CMDS =
            "\\input fontinst.sty\n" + "\\recordtransforms{driver.recs}\n"
                    + "\n" + "\\transformfont{fxlr-8r}{\\reencodefont{8r}{%\n"
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
     * Test for fontinst misc.
     * 
     * @throws Exception if an error occurred.
     */
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

}
