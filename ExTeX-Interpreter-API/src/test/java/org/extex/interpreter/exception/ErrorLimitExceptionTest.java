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

package org.extex.interpreter.exception;

import java.util.Locale;

import junit.framework.TestCase;

/**
 * Test suite for the exception.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ErrorLimitExceptionTest extends TestCase {

    /**
     * Test method for {@link org.extex.interpreter.exception.ErrorLimitException#ErrorLimitException(long)}.
     */
    public final void testErrorLimitException() {

        Exception e = new ErrorLimitException(-123);
        assertNotNull(e);
    }

    /**
     * Test method for {@link org.extex.interpreter.exception.ErrorLimitException#getLocalizedMessage()}.
     */
    public final void testGetLocalizedMessage1() {

        Locale.setDefault(Locale.ENGLISH);
        assertEquals("(That makes -123 errors; please try again.)",
            new ErrorLimitException(-123).getLocalizedMessage());
    }

    /**
     * Test method for {@link org.extex.interpreter.exception.ErrorLimitException#getLocalizedMessage()}.
     */
    public final void testGetLocalizedMessage2() {

        Locale.setDefault(Locale.GERMAN);
        assertEquals(
            "(Das macht -123 Fehler; Bitte versuchen Sie es noch einmal.)",
            new ErrorLimitException(-123).getLocalizedMessage());
    }

    /**
     * Test method for {@link org.extex.interpreter.exception.ErrorLimitException#getMessage()}.
     */
    public final void testGetMessage() {

        Locale.setDefault(Locale.ENGLISH);
        assertEquals("(That makes -123 errors; please try again.)",
            new ErrorLimitException(-123).getMessage());
    }

}
