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
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontInst02Test extends ExTeXLauncher {

    /**
     * The field <tt>SEP</tt> contains the separator for properties.
     */
    private static final String SEP = System.getProperty("path.separator", ":");

    /**
     * Creates a new object.
     */
    public FontInst02Test() {

        super("FontInst02Test");

        setConfig("fontinst-test.xml");

        // delete temp files after the test
        new File("texput.log").deleteOnExit();
        new File("fxlr.mtx").deleteOnExit();
        new File("fxlr.pl").deleteOnExit();
        new File("fxlr8r.mtx").deleteOnExit();
        new File("fxlr8r.pl").deleteOnExit();
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
        return props;
    }

    /**
     * Test for fontinst misc.
     * 
     * @throws Exception if an error occurred.
     */
    public void testTransform01() throws Exception {

        setConfig("tex");
        assertOutput(
            getMyProps(), // --- input code ---
            "\\input fontinst.sty " //
                    + "\\transformfont{fxlr8r}{\\reencodefont{8r}{\\fromafm{fxlr}}} "//
                    + "\\bye",
            // --- log channel ---
            "No file finstmsc.rc.\n" //
                    + "Metrics written on fxlr.mtx.\n"//
                    + "Raw font written on fxlr.pl.\n" // +
                    + "Transformed metrics written on fxlr8r.mtx.\n" //
                    + "Raw font written on fxlr8r.pl.",
            // --- output channel ---
            TERM);

    }

}
