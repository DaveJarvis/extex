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
public class LetterTokenTest extends TokenTester {

    /**
     * Creates a new object.
     */
    public LetterTokenTest() {

        super(t, Catcode.LETTER, "x", "the letter x");
    }

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
    public void testToString2() {

        assertEquals("the letter ^^@", new LetterToken(UnicodeChar.get(0))
            .toString());
    }

    /**
     */
    public void testToString3() {

        assertEquals("the letter ^^A", new LetterToken(UnicodeChar.get(1))
            .toString());
    }

    /**
     */
    public void testToString4() {

        assertEquals("the letter ^^B", new LetterToken(UnicodeChar.get(2))
            .toString());
    }

    /**
     */
    public void testToString5() {

        assertEquals("the letter ^^?", new LetterToken(UnicodeChar.get(127))
            .toString());
    }

    /**
     */
    public void testToStringBuffer2() {

        StringBuffer sb = new StringBuffer();
        new LetterToken(UnicodeChar.get(1)).toString(sb);
        assertEquals("the letter ^^A", sb.toString());
    }

    /**
     */
    public void testEqualsToken1() {

        Token t1 = new LetterToken(UnicodeChar.get(' '));
        Token t2 = new OtherToken(UnicodeChar.get(' '));
        assertFalse(t1.equals(t2));
    }

}
