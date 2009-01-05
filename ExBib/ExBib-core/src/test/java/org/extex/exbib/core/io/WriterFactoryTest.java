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

package org.extex.exbib.core.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
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
import org.junit.Test;

/**
 * This is a test suite for a {@link WriterFactory}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class WriterFactoryTest {

    /**
     * Test configuration.
     * 
     */
    private static class MyConfig implements Configuration {

        /**
         * The field <tt>encoding</tt> contains the encoding.
         */
        private String encoding;

        /**
         * Creates a new object.
         * 
         * @param encoding the encoding
         */
        public MyConfig(String encoding) {

            super();
            this.encoding = encoding;
        }

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

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#findConfiguration(
         *      java.lang.String, java.lang.String)
         */
        public Configuration findConfiguration(String key, String attribute)
                throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getAttribute(
         *      java.lang.String)
         */
        public String getAttribute(String name) {

            if ("encoding".equals(name)) {
                return encoding;
            }
            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getConfiguration(
         *      java.lang.String)
         */
        public Configuration getConfiguration(String key)
                throws ConfigurationException {

            return null;
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

            //
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

            //
        }
    }

    /**
     * <testcase> {@link WriterFactory#configure(Configuration)} recognizes an
     * unsupported encoding. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationUnsupportedEncodingException.class)
    public final void testConfigureConfiguration() throws Exception {

        new WriterFactory(new MyConfig(null)).configure(new MyConfig("xyzzy"));
    }

    /**
     * <testcase> {@link WriterFactory#newInstance()} returns a
     * {@link NullWriter}. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNewInstance() throws Exception {

        assertTrue(new WriterFactory(new MyConfig(null)).newInstance() instanceof NullWriter);
    }

    /**
     * <testcase> A <code>null</code> argument leads to a {@link NullWriter}.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNewInstancePrintStream0() throws Exception {

        assertTrue(new WriterFactory(new MyConfig(null))
            .newInstance((PrintStream) null) instanceof NullWriter);
    }

    /**
     * <testcase> A non-<code>null</code> argument leads to some writer.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNewInstancePrintStream1() throws Exception {

        PrintStream ps = new PrintStream(new ByteArrayOutputStream());
        assertFalse(new WriterFactory(new MyConfig(null)).newInstance(ps) instanceof NullWriter);
    }

    /**
     * <testcase> A <code>null</code> argument leads to an {@link NullWriter}.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNewInstanceString0() throws Exception {

        assertTrue(new WriterFactory(new MyConfig(null))
            .newInstance((String) null) instanceof NullWriter);
    }

    /**
     * <testcase> A non-<code>null</code> argument leads to an exception if
     * the file can not be opened. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = FileNotFoundException.class)
    public final void testNewInstanceString1() throws Exception {

        new WriterFactory(new MyConfig(null))
            .newInstance("target/does/not/exist");
    }

    /**
     * <testcase> A non-<code>null</code> argument opens a file for writing.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNewInstanceString2() throws Exception {

        File file = new File("target", "writer.test");
        if (file.exists()) {
            assertTrue(file.delete());
        }
        Writer writer =
                new WriterFactory(new MyConfig(null)).newInstance(file
                    .toString());
        assertNotNull(writer);
        writer.close();
        if (file.exists()) {
            assertTrue(file.delete());
        } else {
            assertTrue(file.toString() + " has not been created", false);
        }
    }

    /**
     * <testcase> A non-<code>null</code> argument leads to some writer.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNewInstanceStringBuffer() throws Exception {

        assertFalse(new WriterFactory(new MyConfig(null))
            .newInstance(new StringBuffer()) instanceof NullWriter);
    }

    /**
     * <testcase> Two <code>null</code> writers lead to an {@link NullWriter}.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNewInstanceWriterWriter0() throws Exception {

        assertTrue(new WriterFactory(new MyConfig(null))
            .newInstance(null, null) instanceof NullWriter);
    }

    /**
     * <testcase> One <code>null</code> writer lead to the other writer.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNewInstanceWriterWriter1() throws Exception {

        Writer w = new NullWriter();
        assertEquals(w, new WriterFactory(new MyConfig(null)).newInstance(w,
            null));
    }

    /**
     * <testcase> One <code>null</code> writer lead to the other writer.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNewInstanceWriterWriter2() throws Exception {

        Writer w = new NullWriter();
        assertEquals(w, new WriterFactory(new MyConfig(null)).newInstance(null,
            w));
    }

    /**
     * <testcase> One <code>null</code> writer lead to the other writer.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testNewInstanceWriterWriter3() throws Exception {

        Writer w = new NullWriter();
        Writer writer = new WriterFactory(new MyConfig(null)).newInstance(w, w);
        assertFalse(w == writer);
        assertTrue(writer instanceof MultiWriter);
    }

    /**
     * <testcase> <code>null</code> is a legal value for the encoding.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSetEncoding0() throws Exception {

        new WriterFactory(new MyConfig(null)).setEncoding(null);
    }

    /**
     * <testcase> An unknown encoding leads to an Exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = UnsupportedEncodingException.class)
    public final void testSetEncoding1() throws Exception {

        new WriterFactory(new MyConfig(null)).setEncoding("xyzzy");
    }

    /**
     * <testcase> A <code>null</code> argument leads to a
     * {@link NullPointerException}. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void testWriterFactory1() throws Exception {

        new WriterFactory(null);
    }

    /**
     * <testcase> An unknown encoding leads to an Exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationUnsupportedEncodingException.class)
    public final void testWriterFactory2() throws Exception {

        new WriterFactory(new MyConfig("xyzzy"));
    }

}
