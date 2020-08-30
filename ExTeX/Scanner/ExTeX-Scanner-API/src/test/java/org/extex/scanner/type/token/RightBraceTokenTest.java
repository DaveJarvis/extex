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

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertNotEquals;

/**
 * Test cases for right brace tokens.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class RightBraceTokenTest extends TokenTester {

    /**
     * The field {@code token} contains the reference token.
     */
    private static final Token token = new RightBraceToken( UnicodeChar.get( 'x'));

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(LeftBraceTokenTest.class);
    }


    public RightBraceTokenTest() {

        setToken(token);
        setCatcode( Catcode.RIGHTBRACE);
        setText( "x");
        setStr( "end-group character x");
    }

@Test
    public void testEqualsToken1() {

        Token t1 = new RightBraceToken(UnicodeChar.get(' '));
        Token t2 = new SpaceToken(" ");
        assertNotEquals( t1, t2 );
    }

}
