/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.typesetter.spacing;

import org.extex.test.AbstractHfillTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \hfill}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class HfillTest extends AbstractHfillTester {

    public HfillTest() {
        setPrimitive( "hfill" );
        setFil( "1.0fill" );
    }

    /**
     * <testcase primitive="\hfill"> Test case checking that {@code \hfill}
     * switches to vertical mode and inserts a glue node with 1fill. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void test10() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\font\\f cmr10 \\f\\hsize=100pt x\\hfill x\\end ",
            // --- output channel ---
            "\\vbox(4.30554pt+0.0pt)x100.0pt\n" +
                    ".\\hbox(4.30554pt+0.0pt)x100.0pt\n" +
                    "..x\n" +
                    "..\\glue0.0pt plus 1.0fill\n" +
                    "..x\n");
    }

    // TODO implement more primitive specific test cases
}
