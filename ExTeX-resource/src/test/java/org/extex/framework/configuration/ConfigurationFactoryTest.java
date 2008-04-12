/*
 * Copyright (C) 2006-2008 The ExTeX Group and individual authors listed below
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

package org.extex.framework.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.junit.Test;

/**
 * Test suite for the configuration factory.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConfigurationFactoryTest {

    /**
     * Creates a new object.
     */
    public ConfigurationFactoryTest() {

        super();
        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * <testcase> Test that null leads to an appropriate error message.
     * </testcase>
     */
    @Test
    public void testNewInstance1() {

        try {
            ConfigurationFactory.newInstance(null);
            assertFalse("Exception expected", true);
        } catch (ConfigurationException e) {
            assertEquals(
                "Invalid configuration name found. The name is empty or null ", //
                e.getLocalizedMessage());
        }
    }

    /**
     * <testcase> Test that a valid configuration is loaded. </testcase>
     * 
     * @throws ConfigurationException in case of an error
     */
    @Test
    public void testNewInstance10() throws ConfigurationException {

        Configuration cfg =
                ConfigurationFactory
                    .newInstance("org/extex/framework/configuration/Configuration.xml");
        assertNotNull(cfg);
    }

    /**
     * <testcase> Test that null leads to an appropriate error message.
     * </testcase>
     */
    @Test
    public void testNewInstance2() {

        try {
            ConfigurationFactory.newInstance("");
            assertFalse("Exception expected", true);
        } catch (ConfigurationException e) {
            assertEquals(
                "Invalid configuration name found. The name is empty or null ",
                e.getLocalizedMessage());
        }
    }

    /**
     * <testcase> Test that an undefined configuration leads to an appropriate
     * error message. </testcase>
     */
    @Test
    public void testNewInstance3() {

        try {
            ConfigurationFactory.newInstance("undefined");
            assertFalse("Exception expected", true);
        } catch (ConfigurationException e) {
            assertEquals("Configuration `undefined' not found.\n", //
                e.getLocalizedMessage());
        }
    }

    /**
     * <testcase> Test that an invalid configuration leads to an appropriate
     * error message. </testcase>
     */
    @Test
    public void testNewInstance4() {

        try {
            ConfigurationFactory
                .newInstance("org/extex/framework/configuration/EmptyConfiguration.xml");
            assertFalse("Exception expected", true);
        } catch (ConfigurationException e) {
            assertTrue(true);
        }
    }

}
