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

package org.extex.unit.etex.file;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\readline</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class ReadlineTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * The constant <tt>DATA_FILE</tt> contains the name of the file to be used
     * for reading.
     */
    private static final String DATA_FILE =
            "../ExTeX-Unit-tex/src/test/resources/tex/read_data.tex";

    /**
     * Method for running the tests standalone.
     *
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(ReadlineTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public ReadlineTest(String arg) {

        super(arg, "readline", "1 to \\x", "\\openin1 " + DATA_FILE + " ");
        setConfig("etex-test");
    }

    //TODO implement the primitive specific test cases

}
