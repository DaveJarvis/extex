/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;
import org.extex.framework.configuration.exception.ConfigurationUnsupportedEncodingException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Test;

/**
 * This is a test suite for {@link BibReaderFactory}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BibReaderFactoryTest {

    /**
     * The field <tt>cfg</tt> contains the test configuration.
     */
    private Configuration cfg = new Configuration() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#findConfiguration(java.lang.String)
         */
        @Override
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
         * @see org.extex.framework.configuration.Configuration#findConfiguration(java.lang.String,
         *      java.lang.String)
         */
        @Override
        public Configuration findConfiguration(String key, String attribute)
                throws ConfigurationException {

            return this;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getAttribute(java.lang.String)
         */
        @Override
        public String getAttribute(String name) {

            if ("base".equals(name)) {
                return null;
            }
            if ("encoding".equals(name)) {
                return null;
            }
            assertTrue(name, "class".equals(name));
            return BibReader099Impl.class.getName();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getConfiguration(java.lang.String)
         */
        @Override
        public Configuration getConfiguration(String key)
                throws ConfigurationException {

            return this;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getConfiguration(java.lang.String,
         *      java.lang.String)
         */
        @Override
        public Configuration getConfiguration(String key, String attribute)
                throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValue()
         */
        @Override
        public String getValue() throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValue(java.lang.String)
         */
        @Override
        public String getValue(String key) throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValueAsInteger(java.lang.String,
         *      int)
         */
        @Override
        public int getValueAsInteger(String key, int defaultValue)
                throws ConfigurationException {

            return 0;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValues(java.util.List,
         *      java.lang.String)
         */
        @Override
        public void getValues(List<String> list, String key) {

            // getValues unimplemented
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValues(java.lang.String)
         */
        @Override
        public List<String> getValues(String key) {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#iterator()
         */
        @Override
        public Iterator<Configuration> iterator() throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#iterator(java.lang.String)
         */
        @Override
        public Iterator<Configuration> iterator(String key)
                throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#setConfigurationLoader(org.extex.framework.configuration.ConfigurationLoader)
         */
        @Override
        public void setConfigurationLoader(ConfigurationLoader loader) {

            // setConfigurationLoader unimplemented
        }

    };

    /**
     * The field <tt>resourceFinder</tt> contains the test finder.
     */
    private ResourceFinder resourceFinder = new ResourceFinder() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#enableTracing(boolean)
         */
        @Override
        public void enableTracing(boolean flag) {

            //
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
         *      java.lang.String)
         */
        @Override
        public NamedInputStream findResource(String name, String type)
                throws ConfigurationException {

            return new NamedInputStream(
                new ByteArrayInputStream("".getBytes()), "test");
        }
    };

    /**
     * <testcase> TOOD </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        BibReaderFactory factory =
                new BibReaderFactory(cfg, resourceFinder, null, null);
        BibReader ret = factory.newInstance("abc");
        assertNotNull(ret);
        assertTrue(ret instanceof BibReader099Impl);
        assertNull(factory.getEncoding());
    }

    /**
     * <testcase> TOOD </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        BibReaderFactory factory =
                new BibReaderFactory(cfg, resourceFinder, null, "ASCII");
        BibReader ret = factory.newInstance("abc");
        assertNotNull(ret);
        assertTrue(ret instanceof BibReader099Impl);
        assertEquals("ASCII", factory.getEncoding());
    }

    /**
     * <testcase> TOOD </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        BibReaderFactory factory =
                new BibReaderFactory(cfg, resourceFinder, "ASCII", "Latin1");
        BibReader ret = factory.newInstance("abc");
        assertNotNull(ret);
        assertTrue(ret instanceof BibReader099Impl);
        assertEquals("ASCII", factory.getEncoding());
    }

    /**
     * <testcase> TOOD </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        BibReaderFactory factory =
                new BibReaderFactory(cfg, resourceFinder, null, null);
        factory.setEncoding("ASCII");
        assertEquals("ASCII", factory.getEncoding());
    }

    /**
     * <testcase> TOOD </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = IllegalArgumentException.class)
    public void testError1() throws Exception {

        new BibReaderFactory(cfg, null, null, null).newInstance("abc");
    }

    /**
     * <testcase> TOOD </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationUnsupportedEncodingException.class)
    public void testError2() throws Exception {

        new BibReaderFactory(cfg, resourceFinder, null, "en")
            .newInstance("abc");
    }

}
