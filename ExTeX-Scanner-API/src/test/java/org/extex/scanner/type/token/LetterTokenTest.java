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

import junit.framework.TestCase;

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;

/**
 * This class provides the test cases for the letter tokens.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4756 $
 */
public class LetterTokenTest extends TestCase {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(LeftBraceTokenTest.class);
    }

    /**
     * The field <tt>t</tt> contains the reference token.
     */
    private static Token t = new LetterToken(UnicodeChar.get('x'));

    /**
     */
    public void testGetCatcode() {

        assertEquals(Catcode.LETTER, t.getCatcode());
    }

    /**
     */
    public void testToString1() {

        assertEquals("the letter x", t.toString());
    }

    /**
     */
    public void testToString2() {

        assertEquals("the letter ^^1", new LetterToken(UnicodeChar.get(1))
            .toString());
    }

    /**
     */
    public void testToStringBuffer1() {

        StringBuffer sb = new StringBuffer();
        t.toString(sb);
        assertEquals("the letter x", sb.toString());
    }

    /**
     */
    public void testToStringBuffer2() {

        StringBuffer sb = new StringBuffer();
        new LetterToken(UnicodeChar.get(1)).toString(sb);
        assertEquals("the letter ^^1", sb.toString());
    }

    /**
     */
    public void testToText() {

        assertEquals("x", t.toText());
    }

    /**
     */
    public void testGetChar() {

        assertEquals('x', t.getChar().getCodePoint());
    }

    /**
     */
    public void testEqualsToken0() {

        assertTrue(t.equals(t));
    }

    /**
     */
    public void testEqualsToken1() {

        Token t1 = new LetterToken(UnicodeChar.get(' '));
        Token t2 = new OtherToken(UnicodeChar.get(' '));
        assertFalse(t1.equals(t2));
    }

    /**
     */
    public void testEqualsCatcodeString0() {

        assertTrue(t.equals(Catcode.LETTER, "x"));
    }

    /**
     */
    public void testEqualsCatcodeString1() {

        assertFalse(t.equals(Catcode.OTHER, "x"));
    }

    /**
     */
    public void testEqualsCatcodechar0() {

        assertTrue(t.equals(Catcode.LETTER, 'x'));
    }

    /**
     */
    public void testEqualsCatcodechar1() {

        assertFalse(t.equals(Catcode.OTHER, ' '));
    }

    /**
     */
    public void testEqualschar0() {

        assertTrue(t.equals('x'));
    }

    /**
     */
    public void testEqualschar1() {

        assertFalse(t.equals('.'));
    }

    /**
     */
    public void testIsa0() {

        assertFalse(t.isa(Catcode.SPACE));
    }

    /**
     */
    public void testIsa1() {

        assertFalse(t.isa(Catcode.ACTIVE));
    }

    /**
     */
    public void testIsa2() {

        assertFalse(t.isa(Catcode.COMMENT));
    }

    /**
     */
    public void testIsa3() {

        assertFalse(t.isa(Catcode.CR));
    }

    /**
     */
    public void testIsa4() {

        assertFalse(t.isa(Catcode.ESCAPE));
    }

    /**
     */
    public void testIsa5() {

        assertFalse(t.isa(Catcode.IGNORE));
    }

    /**
     */
    public void testIsa6() {

        assertFalse(t.isa(Catcode.INVALID));
    }

    /**
     */
    public void testIsa7() {

        assertFalse(t.isa(Catcode.LEFTBRACE));
    }

    /**
     */
    public void testIsa8() {

        assertTrue(t.isa(Catcode.LETTER));
    }

    /**
     */
    public void testIsa9() {

        assertFalse(t.isa(Catcode.MACROPARAM));
    }

    /**
     */
    public void testIsa10() {

        assertFalse(t.isa(Catcode.MATHSHIFT));
    }

    /**
     */
    public void testIsa11() {

        assertFalse(t.isa(Catcode.OTHER));
    }

    /**
     */
    public void testIsa12() {

        assertFalse(t.isa(Catcode.RIGHTBRACE));
    }

    /**
     */
    public void testIsa13() {

        assertFalse(t.isa(Catcode.SUBMARK));
    }

    /**
     */
    public void testIsa14() {

        assertFalse(t.isa(Catcode.SUPMARK));
    }

    /**
     */
    public void testIsa15() {

        assertFalse(t.isa(Catcode.TABMARK));
    }

}