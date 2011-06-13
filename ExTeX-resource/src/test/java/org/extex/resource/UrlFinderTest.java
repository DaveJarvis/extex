/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;
import org.extex.resource.io.NamedInputStream;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the UriFinder.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class UrlFinderTest {

    /**
     * This is the logging handler which collects the messages in memory.
     */
    private class LoggingHandler extends Handler {

        /**
         * The field <tt>buffer</tt> contains the collected messages.
         */
        private StringBuilder buffer = new StringBuilder();

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Handler#close()
         */
        @Override
        public void close() throws SecurityException {

            //
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Handler#flush()
         */
        @Override
        public void flush() {

            //
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
         */
        @Override
        public void publish(LogRecord record) {

            buffer.append(record.getMessage());
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return buffer.toString();
        }

    }

    /**
     * The constant <tt>CFG</tt> contains the configuration.
     */
    private static final Configuration CFG = new Configuration() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#findConfiguration(java.lang.String)
         */
        @Override
        public Configuration findConfiguration(String key) {

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

            if ("default".equals(name)) {
                return "xxx";
            }

            return null;
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

            return this;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValue()
         */
        @Override
        public String getValue() throws ConfigurationException {

            return "";
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

            return defaultValue;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValues(java.util.List,
         *      java.lang.String)
         */
        @Override
        public void getValues(List<String> list, String key) {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValues(java.lang.String)
         */
        @Override
        public List<String> getValues(String key) {

            if ("extension".equals(key)) {
                List<String> list = new ArrayList<String>();
                list.add("");
                list.add(".tex");
                return list;
            }
            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#iterator()
         */
        @Override
        public Iterator<Configuration> iterator() throws ConfigurationException {

            ArrayList<Configuration> list = new ArrayList<Configuration>();
            list.add(this);
            return list.iterator();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#iterator(java.lang.String)
         */
        @Override
        public Iterator<Configuration> iterator(String key)
                throws ConfigurationException {

            ArrayList<Configuration> list = new ArrayList<Configuration>();
            list.add(this);
            return list.iterator();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#setConfigurationLoader(org.extex.framework.configuration.ConfigurationLoader)
         */
        @Override
        public void setConfigurationLoader(ConfigurationLoader loader) {

            // noop
        }

    };

    /**
     * <testcase> Test that a <code>null</code> configuration leads to an error.
     * </testcase>
     * 
     */
    @Test(expected = ConfigurationMissingException.class)
    public void test001() {

        new UrlFinder(null);
    }

    /**
     * <testcase> Test an unknown protocol. </testcase>
     * 
     */
    @Test
    public void test002() {

        Locale.setDefault(Locale.ENGLISH);
        UrlFinder finder = new UrlFinder(CFG);
        Logger logger = Logger.getLogger("xxx");
        logger.setLevel(Level.ALL);
        Handler handler = new LoggingHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        finder.enableLogging(logger);
        finder.enableTracing(true);
        try {
            NamedInputStream s = finder.findResource("xx://abc", "tex");
            assertNull(s);
            String sep = System.getProperty("file.separator");
            assertEquals(
                "UrlFinder: Searching xx://abc [tex]\n"
                        + "UrlFinder: Trying file xx:"
                        + sep
                        + "abc\n"
                        + "UrlFinder: Not a valid URL: xx://abc: unknown protocol: xx\n"
                        + "UrlFinder: Trying file xx:"
                        + sep
                        + "abc.tex\n"
                        + "UrlFinder: Not a valid URL: xx://abc.tex: unknown protocol: xx\n",
                handler.toString().replaceAll("\r", ""));
        } finally {
            logger.removeHandler(handler);
        }
    }

    /**
     * <testcase> Test that the google home page can be accessed. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    @Ignore
    public void test010() throws IOException {

        NamedInputStream s = null;
        try {
            s = new UrlFinder(CFG).findResource("http://www.google.org", "tex");
            assertNotNull(s);
            for (int c = s.read(); c >= 0; c = s.read()) {
                System.out.print((char) c);
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}
