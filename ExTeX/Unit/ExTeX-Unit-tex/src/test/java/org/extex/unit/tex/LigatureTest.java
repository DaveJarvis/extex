/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex;

import java.util.Properties;

import org.extex.core.exception.helping.HelpingException;
import org.extex.test.ExTeXLauncher;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the ligature management.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4527 $
 */
public class LigatureTest extends ExTeXLauncher {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(LigatureTest.class);
    }


    public LigatureTest() {

    }

    /**
     * <testcase> Test case checking that a ligature node is inserted.
     * </testcase>
     *
     * Ignored, possibly a font issue?
     * 
     * @throws HelpingException in case of an error
     */
    @Test
    @Ignore
    public void testXyz() throws HelpingException {

        Properties properties = getProps();
        properties.setProperty("extex.output", "dump");

        assertSuccess(properties,
        // --- input code ---
            "\\font\\f=cmr10 \\f " + "Affe",
            // --- output channel ---
            "\\vbox(6.94444pt+0.0pt)x3000.0pt\n"
                    + ".\\hbox(6.94444pt+0.0pt)x3000.0pt\n"
                    + "..A\n"
                    + "..?\n"
                    + "..e\n");
    }

}
