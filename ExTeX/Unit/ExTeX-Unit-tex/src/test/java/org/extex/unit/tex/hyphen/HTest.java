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

package org.extex.unit.tex.hyphen;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test suite for the hyphenation.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class HTest extends ExTeXLauncher {


    public HTest() {

    }

    /**
     * Test case checking that ...
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPattern1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\patterns{a3b ab4c}" + "a xxxabcyyy" + "\\end ",
            // --- output channel ---
            "a xxxabcyyy" + TERM);
    }

    /**
     * Test case checking that ...
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPattern2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\patterns{ab4c a3b}" + "a xxxabcyyy" + "\\end ",
            // --- output channel ---
            "a xxxabcyyy" + TERM);
    }

}
