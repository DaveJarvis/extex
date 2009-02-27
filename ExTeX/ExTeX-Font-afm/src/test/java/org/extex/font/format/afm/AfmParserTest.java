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

package org.extex.font.format.afm;

import java.io.FileInputStream;

import junit.framework.TestCase;

/**
 * Test for the afm parser.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5464 $
 */
public class AfmParserTest extends TestCase {

    /**
     * test01.
     * 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        AfmParser parser =
                new AfmParser(new FileInputStream(
                    "../ExTeX-Font-afm/src/font/fxlr.afm"));

        assertNotNull(parser);

        AfmHeader header = parser.getHeader();

        assertEquals("LinLibertine", header.getFontname());
        assertEquals("Linux Libertine", header.getFamilyname());
        assertEquals(431, header.getXheight(), 0);
        assertEquals(-231, header.getDescender(), 0);

        AfmCharMetric cm = parser.getAfmCharMetric("zero");
        assertNotNull(cm);

        assertEquals(438, cm.getWx(), 0);
        assertEquals(48, cm.getC(), 0);

    }

    /**
     * test02.
     * 
     * @throws Exception if an error occurred.
     */
    public void test02() throws Exception {

        AfmParser parser =
                new AfmParser(new FileInputStream(
                    "../ExTeX-Font-afm/src/font/fxlr.afm"));

        assertNotNull(parser);

        AfmCharMetric cm = parser.getAfmCharMetric("at");
        assertNotNull(cm);

        assertEquals(1004, cm.getWx(), 0);
        assertEquals(64, cm.getC(), 0);

        AfmKernPairs kp = cm.getAfmKernPair("Delta");
        assertNotNull(kp);
        assertEquals(-29, kp.getKerningsize(), 0);

    }

}
