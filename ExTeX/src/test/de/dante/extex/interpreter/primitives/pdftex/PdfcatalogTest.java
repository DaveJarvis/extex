/*
 * Copyright (C) 2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.pdftex;

import de.dante.test.NoFlagsButProtectedPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\pdfcatalog</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PdfcatalogTest extends NoFlagsButProtectedPrimitiveTester {

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public PdfcatalogTest(final String arg) {

        super(arg, "pdfcatalog", "{}", "");
        setConfig("pdftex-112");
    }

    /**
     * <testcase primitive="\pdfcatalog">
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testError1() throws Exception {

        assertFailure(//--- input code ---
                DEFINE_BRACES + "a \\pdfcatalog b",
                //--- output message ---
                "Missing `{' inserted");
    }

    //TODO implement more primitive specific test cases

}
