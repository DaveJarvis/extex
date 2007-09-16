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

package org.extex.scanner.exception;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * Test suite for the exception.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5563 $
 */
public class CatcodeWrongLengthExceptionTest {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        JUnitCore.runClasses(CatcodeWrongLengthExceptionTest.class);
    }

    /**
     * Test method for
     * {@link org.extex.scanner.exception.CatcodeWrongLengthException#getLocalizedMessage()}.
     */
    @Test
    public final void testGetLocalizedMessage1() {

        Locale.setDefault(Locale.ENGLISH);
        assertEquals("Expecting a single character instead of xxx",
            new CatcodeWrongLengthException("xxx").getLocalizedMessage());
    }

    /**
     * Test method for
     * {@link org.extex.scanner.exception.CatcodeWrongLengthException#getLocalizedMessage()}.
     */
    @Test
    public final void testGetLocalizedMessage2() {

        Locale.setDefault(Locale.GERMAN);
        assertEquals("Einzelnes Zeichen erwartet anstatt xxx",
            new CatcodeWrongLengthException("xxx").getLocalizedMessage());
    }

}
