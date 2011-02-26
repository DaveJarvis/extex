/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.font.fontparameter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileInputStream;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for FontParameterTest.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontParameterTest {

    /**
     * The font parameter.
     */
    private static FontParameter param;

    /**
     * The file.
     */
    private static final String file =
            "../../../texmf/src/texmf/fonts/afm/fxlr.fontinfo";

    @Before
    public void setUp() throws Exception {

        if (param == null) {
            param = new FontParameter(new FileInputStream(file));
        }
    }

    /**
     * Test 01.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test01() throws Exception {

        assertNotNull(param);
    }

    /**
     * Test 02.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test02() throws Exception {

        assertEquals(0xFB00, param.getUnicode("f_f").getCodePoint());
        assertEquals(0xFB01, param.getUnicode("f_i").getCodePoint());
        assertEquals(0xFB03, param.getUnicode("f_f_i").getCodePoint());
        assertEquals(0xFB04, param.getUnicode("f_f_l").getCodePoint());
    }

    /**
     * Test 03.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test03() throws Exception {

        assertEquals(0x0041, param.getUnicode("A").getCodePoint());

        param.setUseGlyphName(false);
        assertNull(param.getUnicode("A"));

        param.setUseGlyphName(true);
        assertEquals(0x0042, param.getUnicode("B").getCodePoint());
    }

    /**
     * Test 04.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test04() throws Exception {

        assertNull(param.getFontDimen("NOT_EXISTS"));
        assertEquals(250, param.getFontDimen("SPACE").intValue());
        assertEquals(431, param.getFontDimen("XHEIGHT").intValue());
        assertEquals(111, param.getFontDimen("EXTRASPACE").intValue());
    }

}
