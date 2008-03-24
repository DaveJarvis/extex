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
public class TestEmpty extends CommandLineTester {

    /**
     * The field <tt>clear</tt> contains the ...
     */
    private boolean clear = true;

    /**
     * The field <tt>verbose</tt> contains the ...
     */
    private boolean verbose = false;

    /**
     * Create a new object.
     */
    public TestEmpty() {

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
        out.write("\\bibdata{a}\n");
        out.close();
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testEmpty1() throws Exception {

        runTest("src/test/resources/bibtex/empty", "empty_1", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testEmpty2() throws Exception {

        runTest("src/test/resources/bibtex/empty", "empty_2", verbose, clear);
    }

}
