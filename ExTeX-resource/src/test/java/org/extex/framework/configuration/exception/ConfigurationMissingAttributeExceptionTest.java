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

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class ConfigurationMissingAttributeExceptionTest {

    /**
     * The field <tt>CONF</tt> contains the configuration for testing purposes.
     */
    private static final Configuration CONF = new Configuration() {

        @Override
        public Configuration findConfiguration(String key)
                throws ConfigurationInvalidResourceException,
                    ConfigurationNotFoundException,
                    ConfigurationSyntaxException,
                    ConfigurationIOException {

            return null;
        }

        @Override
        public Configuration findConfiguration(String key, String attribute)
                throws ConfigurationException {

            return null;
        }

        @Override
        public String getAttribute(String name) {

            return null;
        }

        @Override
        public Configuration getConfiguration(String key)
                throws ConfigurationException {

            return null;
        }

        @Override
        public Configuration getConfiguration(String key, String attribute)
                throws ConfigurationException {

            return null;
        }

        @Override
        public String getValue() throws ConfigurationException {

            return null;
        }

        @Override
        public String getValue(String key) throws ConfigurationException {

            return null;
        }

        @Override
        public int getValueAsInteger(String key, int defaultValue)
                throws ConfigurationException {

            return 0;
        }

        @Override
        public void getValues(List<String> list, String key) {

        }

        @Override
        public List<String> getValues(String key) {

            // TODO gene: getValues unimplemented
            return null;
        }

        @Override
        public Iterator<Configuration> iterator() throws ConfigurationException {

            return null;
        }

        @Override
        public Iterator<Configuration> iterator(String key)
                throws ConfigurationException {

            return null;
        }

        @Override
        public void setConfigurationLoader(ConfigurationLoader loader) {

        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "THE CONFIG";
        }

    };

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetLocalizedMessage() {

        ConfigurationMissingAttributeException e =
                new ConfigurationMissingAttributeException("abc", CONF);
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("Missing attribute abc in THE CONFIG",
            e.getLocalizedMessage());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetLocalizer() {

        ConfigurationMissingAttributeException e =
                new ConfigurationMissingAttributeException(null, CONF);
        Locale.setDefault(Locale.ENGLISH);
        assertNotNull(e.getLocalizer());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetMessage1() {

        ConfigurationMissingAttributeException e =
                new ConfigurationMissingAttributeException(null, CONF);
        Locale.setDefault(Locale.ENGLISH);
        assertNull(e.getMessage());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetMessage2() {

        ConfigurationMissingAttributeException e =
                new ConfigurationMissingAttributeException(null, CONF);
        Locale.setDefault(Locale.ENGLISH);
        assertEquals(null, e.getMessage());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetSource() {

        ConfigurationMissingAttributeException e =
                new ConfigurationMissingAttributeException(null, CONF);
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("THE CONFIG", e.getSource());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetText0() {

        ConfigurationMissingAttributeException e =
                new ConfigurationMissingAttributeException(null, CONF);
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("Missing attribute ", e.getText());
    }

    /**
     * <testcase> ... </testcase>
     */
    @Test
    public void testGetText1() {

        ConfigurationMissingAttributeException e =
                new ConfigurationMissingAttributeException("abc", CONF);
        Locale.setDefault(Locale.ENGLISH);
        assertEquals("Missing attribute abc", e.getText());
    }

}
