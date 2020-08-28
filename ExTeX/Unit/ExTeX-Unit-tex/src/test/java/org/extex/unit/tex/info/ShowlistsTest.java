/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.info;

import java.util.Properties;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive <tt>\showlists</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ShowlistsTest extends NoFlagsPrimitiveTester {


    public ShowlistsTest() {

        setPrimitive("showlists");setArguments("");setPrepare("");
    }

    /**
     * Prepare the properties to use a fine log level.
     * 
     * @return the properties to use
     */
    private Properties prepare() {

        Properties p = getProps();
        p.setProperty("extex.launcher.loglevel", "fine");
        return p;
    }

    /**
     * <testcase primitive="\showlists"> Test case checking that
     * <tt>\showlists</tt> on the top level brings the expected results.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        Properties p = prepare();

        assertOutput(
            p, // --- input code ---
            "\\showlists\\end ",
            // --- error channel ---
            "### vertical mode entered at line 0\n" + "prevdepth ignored\n"
                    + "Transcript written on ."
                    + System.getProperty("file.separator") + "texput.log.\n",
            "");
    }

    /**
     * <testcase primitive="\showlists"> Test case checking that
     * <tt>\showlists</tt> in a groupworks. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        Properties p = prepare();

        assertOutput(p, // --- input code ---
            DEFINE_BRACES + "{\\showlists}\\end ",
            // --- error channel ---
            "### vertical mode entered at line 0\n" + "prevdepth ignored\n"
                    + "Transcript written on ."
                    + System.getProperty("file.separator") + "texput.log.\n",

            "");
    }

    /**
     * <testcase primitive="\showlists"> Test case checking that
     * <tt>\showlists</tt> in an hbox works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        Properties p = prepare();

        assertOutput(p, // --- input code ---
            DEFINE_BRACES + "\\hbox{\\showlists}\\end ",
            // --- error channel ---
            "### restricted horizontal mode entered at line 1\n"
                    + "spacefactor 1000\n"
                    + "### vertical mode entered at line 0\n"
                    + "prevdepth ignored\n" + "Transcript written on ."
                    + System.getProperty("file.separator") + "texput.log.\n",

            "" + TERM);
    }

}
