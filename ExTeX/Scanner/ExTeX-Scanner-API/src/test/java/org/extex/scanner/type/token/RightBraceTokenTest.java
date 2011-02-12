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

import static org.junit.Assert.assertFalse;

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * Test cases for right brace tokens.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4756 $
 */
public class RightBraceTokenTest extends TokenTester {

    /**
     * The field <tt>token</tt> contains the reference token.
     */
    private static Token token = new RightBraceToken(UnicodeChar.get('x'));

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(LeftBraceTokenTest.class);
    }

    /**
     * Creates a new object.
     */
    public RightBraceTokenTest() {

        super(token, Catcode.RIGHTBRACE, "x", "end-group character x");
    }

    /**
     */
    @Test
    public void testEqualsToken1() {

        Token t1 = new RightBraceToken(UnicodeChar.get(' '));
        Token t2 = new SpaceToken(" ");
        assertFalse(t1.equals(t2));
    }

}
