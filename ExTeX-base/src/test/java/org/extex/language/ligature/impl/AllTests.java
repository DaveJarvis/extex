/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.language.ligature.impl;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test cases for the ligature builder.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class AllTests {

    /**
     * The constructor is private to avoid that somebody uses it.
     */
    private AllTests() {

        super();
    }

    /**
     * Command line interface.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }

    /**
     * Combines all test in this package into one suite.
     *
     * @return the suite
     */
    public static Test suite() {

        TestSuite suite = new TestSuite("Test for org.extex.typesetter.ligatureBuilder.impl");
        //$JUnit-BEGIN$
        suite.addTestSuite(LigatureBuilderImplTest.class);
        //$JUnit-END$
        return suite;
    }
}