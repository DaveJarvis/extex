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

package org.extex.font;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.extex.font.format.xtf.XtfReaderFxlrTest;
import org.extex.font.format.xtf.XtfReaderGara1Test;
import org.extex.font.format.xtf.XtfReaderGara2Test;
import org.extex.font.format.xtf.XtfReaderGara3Test;
import org.extex.font.format.xtf.XtfReaderGara4Test;
import org.extex.font.format.xtf.XtfReaderLmRoman10Regular01Test;
import org.extex.font.format.xtf.XtfReaderLmRoman10Regular02Test;
import org.extex.font.format.xtf.XtfReaderLmRoman10Regular03Test;

/**
 * Test suite for the fonts.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public final class AllTests {

    /**
     * Create the test suite.
     * 
     * @return the suite
     */
    public static Test suite() {

        TestSuite suite = new TestSuite("Test for org.extex.font");
        // $JUnit-BEGIN$
        suite.addTestSuite(FontFactoryImplGenCmtt10Test.class);
        suite.addTestSuite(FontFactoryImplGenCmr10Test.class);
        suite.addTestSuite(FontFactoryImplGenLogosl9Test.class);
        suite.addTestSuite(FontFactoryImplGenLcirclew10Test.class);
        suite.addTestSuite(FontFactoryImplGenCmr10At5PtTest.class);
        suite.addTestSuite(FontFactoryImplGenLasy5Test.class);
        suite.addTestSuite(FontFactoryImplGenCmvtti10Test.class);
        suite.addTestSuite(FontFactoryImplTest.class);
        suite.addTestSuite(FontFactoryImplCmr10At12Test.class);
        suite.addTestSuite(FontFactoryImplGenCmr10At50PtTest.class);
        suite.addTestSuite(FontFactoryImplCmex10UndefTest.class);
        suite.addTestSuite(FontFactoryImplGenCmr10Scaled1440Test.class);
        suite.addTestSuite(FontFactoryImplAfmTest.class);
        suite.addTestSuite(FontFactoryImplXtfTest.class);
        suite.addTestSuite(FontFactoryImplCmr10Test.class);
        suite.addTestSuite(FontFactoryImplCmmanTest.class);
        suite.addTestSuite(XtfReaderGara1Test.class);
        suite.addTestSuite(XtfReaderGara2Test.class);
        suite.addTestSuite(XtfReaderGara3Test.class);
        suite.addTestSuite(XtfReaderGara4Test.class);
        // suite.addTestSuite(TfmReaderTest.class);
        // suite.addTestSuite(TfmFixWordTest.class);
        // suite.addTestSuite(U2tFactoryTest.class);
        // suite.addTestSuite(AfmParserTest.class);
        suite.addTestSuite(XtfReaderLmRoman10Regular01Test.class);
        suite.addTestSuite(XtfReaderLmRoman10Regular02Test.class);
        suite.addTestSuite(XtfReaderLmRoman10Regular03Test.class);
        suite.addTestSuite(XtfReaderFxlrTest.class);
        // $JUnit-END$
        return suite;
    }

}
