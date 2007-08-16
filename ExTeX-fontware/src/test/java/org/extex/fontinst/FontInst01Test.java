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
public class FontInst01Test extends ExTeXLauncher {

    /**
     * The field <tt>SEP</tt> contains the separator for properties.
     */
    private static final String SEP = System.getProperty("path.separator", ":");

    /**
     * Creates a new object.
     */
    public FontInst01Test() {

        super("FontInst01Test");

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
                    "../ExTeX-fontware/src/texmf/tex/fontinst/smblmtx" //
        );
        return props;
    }

    /**
     * Test for fintinst misc.
     * 
     * @throws Exception if an error occurred.
     */
    public void testMisc01() throws Exception {

        assertSuccess(getMyProps(), // --- input code ---
            "\\input finstmsc.sty " + "\\etxtoenc{T1}{t1} " + "\\bye",
            // --- output channel ---
            "");

    }
}
