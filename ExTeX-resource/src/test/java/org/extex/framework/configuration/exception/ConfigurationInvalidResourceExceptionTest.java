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

package org.extex.framework.configuration.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Locale;

import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class ConfigurationInvalidResourceExceptionTest {

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetLocalizedMessage() {

        ConfigurationInvalidResourceException e =
                new ConfigurationInvalidResourceException();
        Locale.setDefault(Locale.ENGLISH);
        assertEquals(
            "Invalid configuration name found. The name is empty or null ",
            e.getLocalizedMessage());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetLocalizer() {

        ConfigurationInvalidResourceException e =
                new ConfigurationInvalidResourceException();
        Locale.setDefault(Locale.ENGLISH);
        assertNotNull(e.getLocalizer());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetMessage() {

        ConfigurationInvalidResourceException e =
                new ConfigurationInvalidResourceException();
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("", e.getMessage());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetSource() {

        ConfigurationInvalidResourceException e =
                new ConfigurationInvalidResourceException();
        Locale.setDefault(Locale.ENGLISH);
        assertNull(e.getSource());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetText() {

        ConfigurationInvalidResourceException e =
                new ConfigurationInvalidResourceException();
        Locale.setDefault(Locale.ENGLISH);
        assertEquals(
            "Invalid configuration name found. The name is empty or null",
            e.getText());
    }

}
