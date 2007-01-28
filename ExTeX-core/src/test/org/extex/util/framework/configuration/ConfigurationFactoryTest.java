/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.util.framework.configuration;

import java.util.Locale;

import org.extex.util.framework.configuration.exception.ConfigurationException;

import junit.framework.TestCase;

/**
 * Test suite for the configuration factory.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConfigurationFactoryTest extends TestCase {

    /**
     * Creates a new object.
     */
    public ConfigurationFactoryTest() {

        super();
        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * Creates a new object.
     *
     * @param name the name
     */
    public ConfigurationFactoryTest(final String name) {

        super(name);
        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * <testcase>
     *  Test that null leads to an appropriate error message.
     * </testcase>
     */
    public void testNewInstance1() {

        try {
            new ConfigurationFactory().newInstance(null);
            assertFalse("Exception expected", true);
        } catch (ConfigurationException e) {
            assertEquals("Invalid configuration name found", //
                    e.getLocalizedMessage());
        }
    }

    /**
     * <testcase>
     *  Test that null leads to an appropriate error message.
     * </testcase>
     */
    public void testNewInstance2() {

        try {
            new ConfigurationFactory().newInstance("");
            assertFalse("Exception expected", true);
        } catch (ConfigurationException e) {
            assertEquals(
                    "Invalid configuration name found. The name is empty or null ",
                    e.getLocalizedMessage());
        }
    }

    /**
     * <testcase>
     *  Test that an undefined configuration leads to an appropriate error message.
     * </testcase>
     */
    public void testNewInstance3() {

        try {
            new ConfigurationFactory().newInstance("undefined");
            assertFalse("Exception expected", true);
        } catch (ConfigurationException e) {
            assertEquals("Configuration `undefined' not found", //
                    e.getLocalizedMessage());
        }
    }

    /**
     * <testcase>
     *  Test that an invalid configuration leads to an appropriate error message.
     * </testcase>
     */
    public void testNewInstance4() {

        try {
            new ConfigurationFactory()
                    .newInstance("org/extex/util/framework/configuration/EmptyConfiguration.xml");
            assertFalse("Exception expected", true);
        } catch (ConfigurationException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase>
     *  Test that a valid configuration leads is loaded.
     * </testcase>
     *
     * @throws ConfigurationException in case of an error
     */
    public void testNewInstance10() throws ConfigurationException {

        Configuration cfg = new ConfigurationFactory()
                .newInstance("org/extex/util/framework/configuration/Configuration.xml");
        assertNotNull(cfg);
    }

}
