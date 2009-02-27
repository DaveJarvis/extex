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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * Test class for ActiveCharacterToken.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4756 $
 */
public class ActiveCharacterTokenTest extends TokenTester {

    /**
     * The field <tt>token</tt> contains the reference token
     */
    private static ActiveCharacterToken token =
            new ActiveCharacterToken(UnicodeChar.get('x'), "abc");

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(ActiveCharacterTokenTest.class);
    }

    /**
     * Creates a new object.
     */
    public ActiveCharacterTokenTest() {

        super(token, Catcode.ACTIVE, "x", "the active character x");
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
    public void testEqualsToken1() {

        Token t2 = new OtherToken(UnicodeChar.get('x'));
        assertFalse(token.equals(t2));
    }

    /**
     */
    @Test
    public void testGetName1() {

        assertEquals("", token.getName());
    }

    /**
     */
    @Test
    public void testGetNamespace1() {

        assertEquals("abc", token.getNamespace());
    }

}
