/*
 * Copyright (C) 2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import org.junit.Test;

/**
 * This is a test suite for {@link ImpossibleException}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ImpossibleExceptionTest {

    /**
     * Test method for
     * {@link org.extex.exbib.bst2groovy.exception.ImpossibleException#ImpossibleException(java.lang.String)}
     * .
     */
    @Test
    public void testImpossibleExceptionString() {

        ImpossibleException e = new ImpossibleException("msg");
        assertNotNull(e);
        assertEquals("msg", e.getMessage());
        Locale.setDefault(Locale.ENGLISH);
        assertEquals(
            "org.extex.exbib.bst2groovy.exception.ImpossibleException: Impossible: msg\n",
            e.toString());
        Locale.setDefault(Locale.GERMAN);
        assertEquals(
            "org.extex.exbib.bst2groovy.exception.ImpossibleException: Unm\u00F6glich: msg\n",
            e.toString());
    }

    /**
     * Test method for
     * {@link org.extex.exbib.bst2groovy.exception.ImpossibleException#ImpossibleException(java.lang.Throwable)}
     * .
     */
    @Test
    public void testImpossibleExceptionThrowable() {

        ImpossibleException e = new ImpossibleException(new Exception("msg"));
        assertNotNull(e);
        assertEquals("java.lang.Exception: msg", e.getMessage());
        Locale.setDefault(Locale.ENGLISH);
        assertEquals(
            "org.extex.exbib.bst2groovy.exception.ImpossibleException: Impossible: java.lang.Exception: msg\n",
            e.toString());
        Locale.setDefault(Locale.GERMAN);
        assertEquals(
            "org.extex.exbib.bst2groovy.exception.ImpossibleException: Unm\u00F6glich: java.lang.Exception: msg\n",
            e.toString());
    }

}
