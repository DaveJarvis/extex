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

package org.extex.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This is a test suite for the class Locator.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LocatorTest {

    /**
     * Test method for {@link org.extex.core.Locator#toString()}.
     */
    @Test
    public final void testToString0() {

        Locator locator = new Locator(null, -1, null, 0);
        assertEquals(":", locator.toString());
    }

    /**
     * Test method for {@link org.extex.core.Locator#toString()}.
     */
    @Test
    public final void testToString1() {

        Locator locator = new Locator(null, 0, null, 0);
        assertEquals(":0", locator.toString());
    }

    /**
     * Test method for {@link org.extex.core.Locator#toString()}.
     */
    @Test
    public final void testToString2() {

        Locator locator = new Locator("XYZ", 42, null, 0);
        assertEquals("XYZ:42", locator.toString());
    }

}
