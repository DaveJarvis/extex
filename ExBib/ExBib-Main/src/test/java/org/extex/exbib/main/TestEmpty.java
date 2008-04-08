/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.main;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Test suite for the empty tests.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class TestEmpty {

    /**
     * The field <tt>DATA_DIR</tt> contains the directory containing database,
     * styles and results.
     */
    private static final String DATA_DIR = "src/test/resources/bibtex/empty";

    /**
     * Create a new object.
     */
    public TestEmpty() {

        super();
    }

    /**
     * Run a test case.
     * 
     * @param style the file name
     * 
     * @throws IOException in case of an I/O error
     */
    private void runTest(String style) throws IOException {

        BibTeXBaseTest.runTest(DATA_DIR + "/" + style, //
            DATA_DIR + "/a", //
            "*", //
            new File(DATA_DIR, style + ".result"));
    }

    /**
     * <testcase> Run test empty_1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty1() throws Exception {

        runTest("empty_1");
    }

    /**
     * <testcase> Run test empty_2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty2() throws Exception {

        runTest("empty_2");
    }

}
