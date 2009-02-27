/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.macro;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test suite for macro expansion.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class MacroExpansionTest extends ExTeXLauncher {

    /**
     * Creates a new object.
     */
    public MacroExpansionTest() {

        super();
    }

    /**
     * <testcase>
     *  Test case checking that the prefix global is passed in to the argument
     *  of a macro.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(//--- input code ---
                DEFINE_BRACES + "\\def\\abc{\\count0=123 }"
                        + "{\\abc\\the\\count0} \\the\\count0 -"
                        + "{\\global\\abc\\the\\count0} \\the\\count0" + "\\end",
                //--- output channel ---
                "123 0-123 123" + TERM);
    }

}
