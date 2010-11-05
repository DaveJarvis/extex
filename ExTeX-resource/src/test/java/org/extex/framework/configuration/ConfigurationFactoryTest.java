/*
 * Copyright (C) 2006-2010 The ExTeX Group and individual authors listed below
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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;

import org.extex.framework.configuration.exception.ConfigurationClassNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInstantiationException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;
import org.extex.framework.configuration.impl.XmlConfiguration;
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

        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * <testcase> Test that null leads to an appropriate error message.
     * </testcase>
     */
    @Test(expected = ConfigurationInvalidResourceException.class)
    public void testNewInstance01() {

        System.clearProperty("Util.Configuration.class");
        ConfigurationFactory.newInstance(null);
    }

    /**
     * <testcase> Test that the empty string leads to an appropriate error
     * message. </testcase>
     */
    @Test(expected = ConfigurationInvalidResourceException.class)
    public void testNewInstance02() {

        System.clearProperty("Util.Configuration.class");
        ConfigurationFactory.newInstance("");
    }

    /**
     * <testcase> Test that an undefined configuration leads to an appropriate
     * error message. </testcase>
     */
    @Test(expected = ConfigurationNotFoundException.class)
    public void testNewInstance03() {

        System.clearProperty("Util.Configuration.class");
        ConfigurationFactory.newInstance("undefined");
    }

    /**
     * <testcase> Test that an invalid configuration leads to an appropriate
     * error message. </testcase>
     * 
     * Note: Redirecting the Error stream is neccesary to get rid of irritating
     * messages on stderr.
     */
    @Test(expected = ConfigurationSyntaxException.class)
    public void testNewInstance04() {

        PrintStream err = System.err;
        System.setErr(new PrintStream(new OutputStream() {

            @Override
            public void write(int b) throws IOException {

            }
        }));
        try {
            System.clearProperty("Util.Configuration.class");
            ConfigurationFactory
                .newInstance("org/extex/framework/configuration/EmptyConfiguration.xml");
        } finally {
            System.setErr(err);
        }
    }

    /**
     * <testcase> Test that a valid configuration is loaded. </testcase>
     * 
     * @throws ConfigurationException in case of an error
     */
    @Test
    public void testNewInstance10() throws ConfigurationException {

        System.clearProperty("Util.Configuration.class");
        Configuration cfg =
                ConfigurationFactory
                    .newInstance("org/extex/framework/configuration/Configuration.xml");
        assertNotNull(cfg);
        assertEquals("", cfg.getValue());
        assertFalse(cfg.iterator().hasNext());
    }

    /**
     * <testcase> Test that a valid configuration is loaded. </testcase>
     * 
     * @throws ConfigurationException in case of an error
     */
    @Test
    public void testNewInstance11() throws ConfigurationException {

        System.setProperty("Util.Configuration.class",
            XmlConfiguration.class.getName());
        Configuration cfg =
                ConfigurationFactory
                    .newInstance("org/extex/framework/configuration/Configuration.xml");
        assertNotNull(cfg);
        assertEquals("", cfg.getValue());
        assertFalse(cfg.iterator().hasNext());
    }

    /**
     * <testcase> Test that an invalid class name leads to an error. </testcase>
     * 
     * @throws ConfigurationException in case of an error
     */
    @Test(expected = ConfigurationClassNotFoundException.class)
    public void testNewInstance12() throws ConfigurationException {

        System.setProperty("Util.Configuration.class", "xyzzy");
        ConfigurationFactory
            .newInstance("org/extex/framework/configuration/Configuration.xml");
    }

    /**
     * <testcase> Test that an invalid class name leads to an error. </testcase>
     * 
     * @throws ConfigurationException in case of an error
     */
    @Test(expected = ConfigurationInstantiationException.class)
    public void testNewInstance13() throws ConfigurationException {

        System.setProperty("Util.Configuration.class", "java.lang.String");
        ConfigurationFactory
            .newInstance("org/extex/framework/configuration/Configuration.xml");
    }

    /**
     * <testcase> Test that an invalid class leads to an error. </testcase>
     * 
     * @throws ConfigurationException in case of an error
     */
    @Test(expected = ConfigurationInstantiationException.class)
    public void testNewInstance14() throws ConfigurationException {

        class Xxx extends XmlConfiguration {

            /**
             * Creates a new object.
             * 
             * @param resource
             * @throws ConfigurationInvalidResourceException
             * @throws ConfigurationNotFoundException
             * @throws ConfigurationSyntaxException
             * @throws ConfigurationIOException
             */
            public Xxx(String resource)
                    throws ConfigurationInvalidResourceException,
                        ConfigurationNotFoundException,
                        ConfigurationSyntaxException,
                        ConfigurationIOException {

                super(resource);
                throw new NullPointerException();
            }

        }
        System.setProperty("Util.Configuration.class", Xxx.class.getName());
        ConfigurationFactory
            .newInstance("org/extex/framework/configuration/Configuration.xml");
    }

    /**
     * <testcase> Test that a valid configuration is loaded. </testcase>
     * 
     * @throws ConfigurationException in case of an error
     */
    @Test(expected = ConfigurationInvalidResourceException.class)
    public void testNewInstance15() throws ConfigurationException {

        System.setProperty("Util.Configuration.class",
            XmlConfiguration.class.getName());
        ConfigurationFactory.newInstance(null);
    }

}
