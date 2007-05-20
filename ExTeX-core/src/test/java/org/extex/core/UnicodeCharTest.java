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

package org.extex.core;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * This is a test suite for UnicodeChar.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class UnicodeCharTest extends TestCase {

    /**
     * Test method for {@link org.extex.core.UnicodeChar#get(int)}.
     */
    @Test
    public final void testGet1() {

        assertNull(UnicodeChar.get(-1));
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#get(int)}.
     */
    @Test
    public final void testGet2() {

        assertNotNull(UnicodeChar.get(0));
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#get(int)}.
     */
    @Test
    public final void testGet3() {

        UnicodeChar uc = UnicodeChar.get(0);
        assertNotNull(uc);
        assertSame(uc, UnicodeChar.get(0));
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#get(int)}.
     */
    @Test
    public final void testGet4() {

        UnicodeChar uc = UnicodeChar.get(512);
        assertNotNull(uc);
        assertSame(uc, UnicodeChar.get(512));
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#get(String)}.
     */
    @Test
    public final void testGetString0() {

        UnicodeChar uc = UnicodeChar.get("xyzzy");
        assertNull(uc);
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#get(String)}.
     */
    @Test
    public final void testGetString1() {

        UnicodeChar uc = UnicodeChar.get("asterisk");
        assertNotNull(uc);
        assertSame(uc, UnicodeChar.get('*'));
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#getCodePoint()}.
     */
    @Test
    public final void testGetCodePoint() {

        UnicodeChar uc = UnicodeChar.get('*');
        assertNotNull(uc);
        assertTrue(42 == uc.getCodePoint());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#getDirection()}.
     */
    @Test
    public final void testGetDirection() {

        UnicodeChar uc = UnicodeChar.get('*');
        assertNotNull(uc);
        assertTrue(10 == uc.getDirection());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#getUnicodeName()}.
     */
    @Test
    public final void testGetUnicodeName() {

        UnicodeChar uc = UnicodeChar.get('*');
        assertNotNull(uc);
        assertEquals("ASTERISK", uc.getUnicodeName());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#isDigit()}.
     */
    @Test
    public final void testIsDigit42() {

        UnicodeChar uc = UnicodeChar.get('*');
        assertNotNull(uc);
        assertFalse(uc.isDigit());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#isDigit()}.
     */
    @Test
    public final void testIsDigit48() {

        UnicodeChar uc = UnicodeChar.get('0');
        assertNotNull(uc);
        assertTrue(uc.isDigit());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#isLetter()}.
     */
    @Test
    public final void testIsLetter42() {

        UnicodeChar uc = UnicodeChar.get('*');
        assertNotNull(uc);
        assertFalse(uc.isLetter());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#isLetter()}.
     */
    @Test
    public final void testIsLetter65() {

        UnicodeChar uc = UnicodeChar.get('A');
        assertNotNull(uc);
        assertTrue(uc.isLetter());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#isPrintable()}.
     */
    @Test
    public final void testIsPrintable42() {

        UnicodeChar uc = UnicodeChar.get('*');
        assertNotNull(uc);
        assertTrue(uc.isPrintable());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#isPrintable()}.
     */
    @Test
    public final void testIsPrintable0() {

        UnicodeChar uc = UnicodeChar.get(0);
        assertNotNull(uc);
        assertFalse(uc.isPrintable());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#lower()}.
     */
    @Test
    public final void testLower42() {

        UnicodeChar uc = UnicodeChar.get('*');
        assertNotNull(uc);
        assertSame(uc, uc.lower());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#lower()}.
     */
    @Test
    public final void testLower92() {

        UnicodeChar uc = UnicodeChar.get('a');
        assertNotNull(uc);
        assertSame(uc, uc.lower());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#lower()}.
     */
    @Test
    public final void testLower65() {

        UnicodeChar uc = UnicodeChar.get('A');
        assertNotNull(uc);
        assertTrue(uc.lower().getCodePoint() == 'a');
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#toString()}.
     */
    @Test
    public final void testToString() {

        UnicodeChar uc = UnicodeChar.get('*');
        assertNotNull(uc);
        assertEquals("*", uc.toString());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#upper()}.
     */
    @Test
    public final void testUpper42() {

        UnicodeChar uc = UnicodeChar.get('*');
        assertNotNull(uc);
        assertSame(uc, uc.upper());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#upper()}.
     */
    @Test
    public final void testUpper65() {

        UnicodeChar uc = UnicodeChar.get('A');
        assertNotNull(uc);
        assertSame(uc, uc.upper());
    }

    /**
     * Test method for {@link org.extex.core.UnicodeChar#upper()}.
     */
    @Test
    public final void testUpper92() {

        UnicodeChar uc = UnicodeChar.get('a');
        assertNotNull(uc);
        assertSame(UnicodeChar.get('A'), uc.upper());
    }

}
