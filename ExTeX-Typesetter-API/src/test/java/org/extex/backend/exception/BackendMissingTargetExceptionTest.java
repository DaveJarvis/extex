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

package org.extex.backend.exception;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test suite for the exception.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BackendMissingTargetExceptionTest {

    /**
     * Test method for
     * {@link org.extex.backend.exception.BackendMissingTargetException#BackendMissingTargetException()}.
     */
    @Test
    public final void testBackendMissingTargetException() {

        Exception e = new BackendMissingTargetException();
        assertNotNull(e);
    }

    /**
     * Test method for {@link java.lang.Throwable#getLocalizedMessage()}.
     */
    @Test
    public final void testGetLocalizedMessage0() {

        Exception e = new BackendMissingTargetException();
        assertNull(e.getLocalizedMessage());
    }

    /**
     * Test method for {@link java.lang.Throwable#getLocalizedMessage()}.
     */
    @Test
    public final void testGetLocalizedMessage1() {

        Exception e = new BackendMissingTargetException();
        assertNull(e.getMessage());
    }

}
