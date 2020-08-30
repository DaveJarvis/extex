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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.extex.core.exception.ImpossibleException;
import org.junit.Test;

/**
 * Test suite for the exception.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ImpossibleExceptionTest {

    /**
     * Test method for {@link
     * org.extex.core.exception.ImpossibleException#ImpossibleException(java.lang.String)}.
     */
    @Test
    public final void testImpossibleExceptionString() {

        ImpossibleException e = new ImpossibleException("xxx");
        assertNotNull(e);
        assertEquals("xxx", e.getMessage());
    }

    /**
     * Test method for {@link
     * org.extex.core.exception.ImpossibleException#ImpossibleException(java.lang.Throwable)}.
     */
    @Test
    public final void testImpossibleExceptionThrowable() {

        Throwable t = new NullPointerException();
        ImpossibleException e = new ImpossibleException(t);
        assertNotNull(e);
        assertEquals(t, e.getCause());
        assertEquals("java.lang.NullPointerException", e.getMessage());
    }

    /**
     * Test method for {@link
     * org.extex.core.exception.ImpossibleException#ImpossibleException(
     * java.lang.String, java.lang.Throwable)}.
     */
    @Test
    public final void testImpossibleExceptionStringThrowable() {

        Throwable t = new NullPointerException();
        ImpossibleException e = new ImpossibleException("xxx", t);
        assertNotNull(e);
        assertEquals(t, e.getCause());
        assertEquals("xxx", e.getMessage());
    }

}
