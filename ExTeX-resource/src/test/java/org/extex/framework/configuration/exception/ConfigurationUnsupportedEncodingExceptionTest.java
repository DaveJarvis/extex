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
 * This is a test suite for {@link ConfigurationUnsupportedEncodingException}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConfigurationUnsupportedEncodingExceptionTest {

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetLocalizedMessage() {

        ConfigurationUnsupportedEncodingException e =
                new ConfigurationUnsupportedEncodingException("abc", "def");
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("Unsupported encoding abc in def", e.getLocalizedMessage());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetLocalizer() {

        ConfigurationUnsupportedEncodingException e =
                new ConfigurationUnsupportedEncodingException(null, "def");
        Locale.setDefault(Locale.ENGLISH);
        assertNotNull(e.getLocalizer());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetMessage1() {

        ConfigurationUnsupportedEncodingException e =
                new ConfigurationUnsupportedEncodingException(null, "def");
        Locale.setDefault(Locale.ENGLISH);
        assertNull(e.getMessage());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetMessage2() {

        ConfigurationUnsupportedEncodingException e =
                new ConfigurationUnsupportedEncodingException(null, "def");
        Locale.setDefault(Locale.ENGLISH);
        assertEquals(null, e.getMessage());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetSource() {

        ConfigurationUnsupportedEncodingException e =
                new ConfigurationUnsupportedEncodingException(null, "def");
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("def", e.getSource());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetText0() {

        ConfigurationUnsupportedEncodingException e =
                new ConfigurationUnsupportedEncodingException(null, "def");
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("Unsupported encoding", e.getText());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetText1() {

        ConfigurationUnsupportedEncodingException e =
                new ConfigurationUnsupportedEncodingException("abc", "def");
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("Unsupported encoding", e.getText());
    }

}
