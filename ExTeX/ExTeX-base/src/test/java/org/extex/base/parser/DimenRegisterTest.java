/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.base.parser;

import static org.junit.Assert.assertEquals;

import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Interpreter;
import org.extex.interpreter.InterpreterFactory;
import org.extex.scanner.stream.TokenStreamFactory;
import org.junit.Test;

/**
 * Test cases for dimen registers.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4766 $
 */
public class DimenRegisterTest {


    public DimenRegisterTest() {

    }

    /**
     * Performs test on a given String.
     * 
     * @param spec the String to parse
     * 
     * @return the sp of the Dimen returned
     * 
     * @throws ConfigurationException in case of an error in the configuration
     * @throws GeneralException in case of an error during parsing
     */
    private long doTest(String spec) throws GeneralException {

        Configuration config =
                ConfigurationFactory.newInstance("config/base-test.xml");
        TokenStreamFactory fac = new TokenStreamFactory("base");
        fac.configure(config.getConfiguration("Scanner"));
        Interpreter source =
                new InterpreterFactory(config.getConfiguration("Interpreter"),
                    null).newInstance(null, null);
        source.addStream(fac.getStream(spec));
        source.setTokenStreamFactory(fac);

        return source.parseDimen(new MockContext(), source, null).getValue();
    }

    /**
     * Test that the parsing of big points works. A value in the middle (1.25bp)
     * is used. This results into 82227sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testBp1() throws Exception {

        assertEquals(82227L, doTest("1.25bp"));
    }

    /**
     * Test that the parsing of ciceros works. A value in the middle (7,777cc)
     * is used. This results into 6544254sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCc1() throws Exception {

        assertEquals(6544254L, doTest("7,777cc"));
    }

    /**
     * Test that the parsing of centimeter works. A value in the middle (1cm) is
     * used. This results into 1864679sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCm1() throws Exception {

        assertEquals(1864679L, doTest("1cm"));
    }

    /**
     * Test that the parsing of millimeter works. A value in the middle (1.33mm)
     * is used. This results into 2480027sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCm2() throws Exception {

        assertEquals(2480027L, doTest("1.33cm"));
    }

    /**
     * Test that the parsing of didot points works. A value in the middle (1dd)
     * is used. This results into 70124sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDd1() throws Exception {

        assertEquals(70124L, doTest("1dd"));
    }

    /**
     * Test that the parsing of didot points works. A value in the middle
     * (1.25dd) is used. This results into 87655sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDd2() throws Exception {

        assertEquals(87655L, doTest("1.25dd"));
    }

    /**
     * Test that the parsing of inch works. A value in the middle (1in) is used.
     * This results into 4736286sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIn1() throws Exception {

        assertEquals(4736286L, doTest("1in"));
    }

    /**
     * Test that the parsing of millimeter works. A value in the middle (1mm) is
     * used. This results into 186467sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMm1() throws Exception {

        assertEquals(186467L, doTest("1mm"));
    }

    /**
     * Test that the parsing of millimeter works. A value in the middle (1.33mm)
     * is used. This results into 248002sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMm2() throws Exception {

        assertEquals(248002L, doTest("1.33mm"));
    }

    /**
     * Test that the parsing of picas works. A value in the middle (1.25pc) is
     * used. This results into 983040sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPc1() throws Exception {

        assertEquals(983040L, doTest("1.25pc"));
    }

    /**
     * Test that the parsing of picas works. A value in the middle (1.7pc) is
     * used. This results into 1336932sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPc2() throws Exception {

        assertEquals(1336932L, doTest("1.7pc"));
    }

    /**
     * Test that the parsing of points works. A value in the middle (1.000pt) is
     * used. This results into 65536sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt1() throws Exception {

        assertEquals(65536L, doTest("1.000pt"));
    }

    /**
     * Test that the parsing of points works. A value in the middle (1.000pt) is
     * used. This results into 65536sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt1b() throws Exception {

        assertEquals(65536L, doTest("1 pt"));
    }

    /**
     * Test that the parsing of points works. A value in the middle (1.000pt) is
     * used. This results into 65536sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt1c() throws Exception {

        assertEquals(65536L, doTest("1. pt"));
    }

    /**
     * Test that the parsing of points works. A value in the middle (1.5pt) is
     * used. This results into 98304sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt2() throws Exception {

        assertEquals(98304L, doTest("1.5pt"));
    }

    /**
     * Test that the parsing of points works. A value in the middle (1.50pt) is
     * used. This results into 98304sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt3() throws Exception {

        assertEquals(98304L, doTest("1.50pt"));
    }

    /**
     * Test that the parsing of points works. A value in the middle (1.33pt) is
     * used. This results into 87163sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt4() throws Exception {

        assertEquals(87163L, doTest("1.33pt"));
    }

    /**
     * Test that the parsing of points works. A value in the middle (1.333pt) is
     * used. This results into 87359sp.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPt5() throws Exception {

        assertEquals(87359L, doTest("1.333pt"));
    }

    /**
     * Test that the parsing of scaled points works. A value in the middle
     * (1234sp) is used.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSp1() throws Exception {

        assertEquals(1234L, doTest("1234 sp"));
    }

}
