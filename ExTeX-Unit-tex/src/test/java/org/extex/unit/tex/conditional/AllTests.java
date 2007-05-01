/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.conditional;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test suite for the conditional primitives in unit tex.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class AllTests {

    /**
     * Creates a new object.
     */
    private AllTests() {

        super();
    }

    /**
     * Test suite for the conditional primitives in unit tex.
     *
     * @return the test suite
     */
    public static Test suite() {

        TestSuite suite =
                new TestSuite("Test for org.extex.unit.tex.conditional");
        //$JUnit-BEGIN$
        suite.addTestSuite(IfeofTest.class);
        suite.addTestSuite(IfhmodeTest.class);
        suite.addTestSuite(IftrueTest.class);
        suite.addTestSuite(IfoddTest.class);
        suite.addTestSuite(IfdimTest.class);
        suite.addTestSuite(IfnumTest.class);
        suite.addTestSuite(FiTest.class);
        suite.addTestSuite(OrTest.class);
        suite.addTestSuite(IfTest.class);
        suite.addTestSuite(IfxTest.class);
        suite.addTestSuite(IfvmodeTest.class);
        suite.addTestSuite(ElseTest.class);
        suite.addTestSuite(IfinnerTest.class);
        suite.addTestSuite(IffalseTest.class);
        suite.addTestSuite(IfmmodeTest.class);
        suite.addTestSuite(IfcaseTest.class);
        suite.addTestSuite(IfcatTest.class);
        suite.addTestSuite(IfhboxTest.class);
        suite.addTestSuite(IfvboxTest.class);
        suite.addTestSuite(IfvoidTest.class);
        //$JUnit-END$
        return suite;
    }

}
