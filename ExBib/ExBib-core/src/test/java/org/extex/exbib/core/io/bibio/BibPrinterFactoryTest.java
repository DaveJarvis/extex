/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.bibio;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;
import org.junit.Test;

/**
 * This is a test suite for {@link BibPrinterFactory}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BibPrinterFactoryTest {

    /**
     * The field <tt>cfg</tt> contains the test configuration.
     */
    private final Configuration cfg = new Configuration() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#findConfiguration(
         *      java.lang.String)
         */
        public Configuration findConfiguration(String key)
                throws ConfigurationInvalidResourceException,
                    ConfigurationNotFoundException,
                    ConfigurationSyntaxException,
                    ConfigurationIOException {

            return this;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#findConfiguration(
         *      java.lang.String, java.lang.String)
         */
        public Configuration findConfiguration(String key, String attribute)
                throws ConfigurationException {

            return this;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getAttribute(
         *      java.lang.String)
         */
        public String getAttribute(String name) {

            if ("base".equals(name)) {
                return null;
            }
            assertTrue(name, "class".equals(name));
            return BibPrinterLispImpl.class.getName();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getConfiguration(
         *      java.lang.String)
         */
        public Configuration getConfiguration(String key)
                throws ConfigurationException {

            return this;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getConfiguration(
         *      java.lang.String, java.lang.String)
         */
        public Configuration getConfiguration(String key, String attribute)
                throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValue()
         */
        public String getValue() throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValue(
         *      java.lang.String)
         */
        public String getValue(String key) throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValueAsInteger(
         *      java.lang.String, int)
         */
        public int getValueAsInteger(String key, int defaultValue)
                throws ConfigurationException {

            return 0;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValues(
         *      java.util.List, java.lang.String)
         */
        public void getValues(List<String> list, String key) {

            // getValues unimplemented
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValues(
         *      java.lang.String)
         */
        public List<String> getValues(String key) {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#iterator()
         */
        public Iterator<Configuration> iterator() throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#iterator(
         *      java.lang.String)
         */
        public Iterator<Configuration> iterator(String key)
                throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#setConfigurationLoader(
         *      org.extex.framework.configuration.ConfigurationLoader)
         */
        public void setConfigurationLoader(ConfigurationLoader loader) {

            // setConfigurationLoader unimplemented
        }

    };

    /**
     * <testcase> The default value of encoding is <code>null</code>.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        BibPrinter ret = new BibPrinterFactory(cfg).newInstance("abc", null);
        assertNotNull(ret);
        assertTrue(ret instanceof BibPrinterLispImpl);
    }

}
