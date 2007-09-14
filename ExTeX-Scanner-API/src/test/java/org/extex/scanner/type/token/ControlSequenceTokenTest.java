/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.junit.Test;

/**
 * Test cases for control sequence tokens.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4756 $
 */
public class ControlSequenceTokenTest extends TokenTester {

    /**
     * Creates a new object.
     */
    public ControlSequenceTokenTest() {

        super(token, Catcode.ESCAPE, "\\x", "the control sequence \\x");
    }

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(ControlSequenceTokenTest.class);
    }

    /**
     * The field <tt>token</tt> contains the reference token.
     */
    private static ControlSequenceToken token =
            new ControlSequenceToken(UnicodeChar.get('\\'), "x", "abc");

    /**
     */
    public void testEqualsToken1() {

        Token t1 = new ControlSequenceToken(UnicodeChar.get('\\'), " ", "");
        Token t2 = new OtherToken(UnicodeChar.get(' '));
        assertFalse(t1.equals(t2));
    }

    /**
     */
    @Test
    @Override
    public void testEq0() {

        assertFalse("", token.eq(Catcode.LETTER, "x"));
    }

    /**
     */
    @Test
    @Override
    public void testEq2() {

        assertFalse(token.eq(Catcode.OTHER, "x"));
    }

    /**
     */
    @Test
    @Override
    public void testEq11() {

        assertFalse(token.eq(Catcode.OTHER, 'x'));
    }

    /**
     */
    @Test
    @Override
    public void testGetChar0() {

        UnicodeChar x = token.getChar();
        assertNotNull(x);
        assertEquals(92, x.getCodePoint());
    }

    /**
     */
    @Test
    @Override
    public void testToTextString0() {

        assertEquals("x", token.toText(null));
    }

    /**
     */
    @Test
    public void testGetName1() {

        assertEquals("x", token.getName());
    }

    /**
     */
    @Test
    public void testGetNamespace1() {

        assertEquals("abc", token.getNamespace());
    }

    /**
     */
    @Test
    public void testCloneInNamespace0() {

        CodeToken x = token.cloneInNamespace(null);
        assertNotNull(x);
        assertEquals(x, token);
    }

    /**
     */
    @Test
    public void testCloneInNamespace1() {

        CodeToken x = token.cloneInNamespace("abc");
        assertNotNull(x);
        assertEquals(x, token);
    }

    /**
     */
    @Test
    public void testCloneInNamespace2() {

        CodeToken x = token.cloneInNamespace("xyz");
        assertNotNull(x);
        assertEquals("xyz", x.getNamespace());
        assertEquals(token.getCatcode(), x.getCatcode());
        assertEquals(token.getChar(), x.getChar());
    }

    /**
     */
    @Test
    public void testCloneInDefaultNamespace0() {

        CodeToken x = token.cloneInDefaultNamespace();
        assertNotNull(x);
        assertEquals("", x.getNamespace());
        assertEquals(token.getCatcode(), x.getCatcode());
        assertEquals(token.getChar(), x.getChar());
    }

    /**
     */
    @Test
    public void testCloneInDefaultNamespace1() {

        CodeToken y = token.cloneInNamespace("");
        CodeToken x = y.cloneInDefaultNamespace();
        assertNotNull(x);
        assertTrue("the same", x == y);
    }

}
