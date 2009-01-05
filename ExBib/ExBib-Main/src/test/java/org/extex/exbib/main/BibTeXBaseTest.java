/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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
 * This is a test suite for the BibteX base styles.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BibTeXBaseTest {

    /**
     * The field <tt>DATA_DIR</tt> contains the directory containing database,
     * styles and results.
     */
    private static final String DATA_DIR = "src/test/resources/bibtex/base";

    /**
     * Creates a new object.
     */
    public BibTeXBaseTest() {

        super();
    }

    /**
     * Run a test.
     * 
     * @param style the style
     * 
     * @throws IOException in case of an I/O error
     */
    private void runTest(String style) throws IOException {

        TRunner.runTest(DATA_DIR + "/" + style, //
            DATA_DIR + "/xampl", //
            "*", //
            new File(DATA_DIR, style + ".result"), "--strict");
    }

    /**
     * <testcase> Apply the abbrev style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAbbrv() throws Exception {

        runTest("abbrv");
    }

    /**
     * <testcase> Apply the alpha style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAlpha() throws Exception {

        runTest("alpha");
    }

    /**
     * <testcase> Apply the plain style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPlain() throws Exception {

        runTest("plain");
    }

    /**
     * <testcase> Apply the unsrt style to xampl. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUnsrt() throws Exception {

        runTest("unsrt");
    }

}
