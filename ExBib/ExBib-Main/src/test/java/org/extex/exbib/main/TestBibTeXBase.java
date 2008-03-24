/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * ...
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class TestBibTeXBase extends CommandLineTester {

    /**
     * The field <tt>clear</tt> contains the ...
     */
    private boolean clear = true;

    /**
     * The field <tt>verbose</tt> contains the ...
     */
    private boolean verbose = false;

    /**
     * Creates a new object.
     */
    public TestBibTeXBase() {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.main.CommandLineTester#setupAux(java.io.File,
     *      java.lang.String)
     */
    @Override
    protected void setupAux(File aux, String name) throws IOException {

        FileWriter out = new FileWriter(aux);
        out.write("\\relax\n");
        out.write("\\citation{*}\n");
        out.write("\\bibstyle{" + name + "}\n");
        out.write("\\bibdata{xampl}\n");
        out.close();
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testAbbrv() throws Exception {

        runTest("src/test/resources/bibtex/base", "abbrv", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testAlpha() throws Exception {

        runTest("src/test/resources/bibtex/base", "alpha", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testPlain() throws Exception {

        runTest("src/test/resources/bibtex/base", "plain", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testUnsrt() throws Exception {

        runTest("src/test/resources/bibtex/base", "unsrt", verbose, clear);
    }

}
