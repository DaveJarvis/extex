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

package org.extex.exbib.core.io;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

/**
 * This is a test class for {@link Locator}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class LocatorTest {

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    @Test
    public void test1() {

        Locator locator = new Locator("abc", 12, 34);
        assertEquals("abc", locator.getResourceName());
        assertEquals(12, locator.getLineNumber());
        assertEquals(34, locator.getLinePointer());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    @Test
    public void test2() {

        Locator locator = new Locator("abc", 12);
        assertEquals("abc", locator.getResourceName());
        assertEquals(12, locator.getLineNumber());
        assertEquals(0, locator.getLinePointer());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    @Test
    public void testToString1de() {

        Locale.setDefault(Locale.GERMAN);
        assertEquals("Zeile 12 von Datei abc",
            new Locator("abc", 12, 34).toString());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    @Test
    public void testToString1en() {

        Locale.setDefault(Locale.ENGLISH);
        assertEquals("line 12 of file abc",
            new Locator("abc", 12, 34).toString());
    }

}