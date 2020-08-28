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
 * This is a test suite for {@link ConfigurationIllegalAccessException}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConfigurationIllegalAccessExceptionTest {

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetLocalizedMessage() {

        ConfigurationIllegalAccessException e =
                new ConfigurationIllegalAccessException(new Exception("abc"));
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("Illegal access (abc)\n\tcaused by abc",
            e.getLocalizedMessage());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetLocalizer() {

        ConfigurationIllegalAccessException e =
                new ConfigurationIllegalAccessException(null);
        Locale.setDefault(Locale.ENGLISH);
        assertNotNull(e.getLocalizer());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetMessage1() {

        ConfigurationIllegalAccessException e =
                new ConfigurationIllegalAccessException(null);
        Locale.setDefault(Locale.ENGLISH);
        assertNull(e.getMessage());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetMessage2() {

        ConfigurationIllegalAccessException e =
                new ConfigurationIllegalAccessException(new Exception("abc"));
        Locale.setDefault(Locale.ENGLISH);
        assertEquals(null, e.getMessage());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetSource1() {

        ConfigurationIllegalAccessException e =
                new ConfigurationIllegalAccessException(null);
        Locale.setDefault(Locale.ENGLISH);
        assertNull(e.getSource());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetSource2() {

        ConfigurationIllegalAccessException e =
                new ConfigurationIllegalAccessException(new Exception("abc"));
        Locale.setDefault(Locale.ENGLISH);
        assertNull(e.getSource());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetText1() {

        ConfigurationIllegalAccessException e =
                new ConfigurationIllegalAccessException(null);
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("Illegal access ()", e.getText());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetText2() {

        ConfigurationIllegalAccessException e =
                new ConfigurationIllegalAccessException(new Exception("abc"));
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("Illegal access (abc)", e.getText());
    }

}
