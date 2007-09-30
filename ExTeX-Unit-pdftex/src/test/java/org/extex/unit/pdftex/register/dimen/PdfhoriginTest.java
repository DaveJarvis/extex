/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.pdftex.register.dimen;

import org.extex.unit.tex.register.dimen.AbstractDimenRegisterTester;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\pdfhorigin</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4824 $
 */
public class PdfhoriginTest extends AbstractDimenRegisterTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(PdfhoriginTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public PdfhoriginTest(String arg) {

        super(arg, "pdfhorigin", "", "0.0pt");
        setConfig("pdftex-test");
    }

    // TODO implement the primitive specific test cases
}
