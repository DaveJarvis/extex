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

package org.extex.scanner.type.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * Test cases for CR tokens.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class CrTokenTest extends TokenTester {

    /**
     * The field {@code t} contains the reference token.
     */
    private static final CrToken token = new CrToken( null);

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(CrTokenTest.class);
    }


    public CrTokenTest() {

        setToken(token);
setCatcode( Catcode.CR);
setText( "[]");
setStr( "end of alignment template");
    }

    /**
     * A CR token ignores the character value.
     */
    @Test
    @Override
    public void testEq1() {

        assertTrue(token.eq(Catcode.CR, "="));
    }

    /**
     * A CR token ignores the character value.
     */
    @Test
    @Override
    public void testEq20() {

        assertFalse(token.toString(), token.eq('x'));
    }

@Test
    public void testEqualsToken1() {

        Token t1 = new CrToken(null);
        Token t2 = new SpaceToken(" ");
        assertFalse(t1.equals(t2));
    }

@Test
    @Override
    public void testGetChar0() {

        assertNull(token.getChar());
    }

@Test
    public void testToText3() {

        assertEquals("", token.toText(UnicodeChar.get(65)));
    }

@Test
    @Override
    public void testToTextString0() {

        assertEquals("", token.toText(null));
    }

@Test
    @Override
    public void testToTextString1() {

        assertEquals("", token.toText(UnicodeChar.get(65)));
    }

}
