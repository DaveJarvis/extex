/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.io.bblio;

import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.io.StringBufferWriter;
import org.extex.exbib.core.io.Writer;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;

/**
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestBblWriter extends TestCase {

    // TODO: Complete the test suite
    /**
     * TODO gene: missing JavaDoc.
     * 
     */
    private static class DummyConfig implements Configuration {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#findConfiguration(java.lang.String)
         */
        public Configuration findConfiguration(String key)
                throws ConfigurationInvalidResourceException,
                    ConfigurationNotFoundException,
                    ConfigurationSyntaxException,
                    ConfigurationIOException {

            // TODO gene: findConfiguration unimplemented
            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#findConfiguration(java.lang.String,
         *      java.lang.String)
         */
        public Configuration findConfiguration(String key, String attribute)
                throws ConfigurationException {

            // TODO gene: findConfiguration unimplemented
            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getAttribute(
         *      java.lang.String)
         */
        public String getAttribute(String name) throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getConfiguration(java.lang.String)
         */
        public Configuration getConfiguration(String key)
                throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getConfiguration(java.lang.String,
         *      java.lang.String)
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

            // TODO gene: getValue unimplemented
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

            return 20;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValues(
         *      java.util.List, java.lang.String)
         */
        public void getValues(List<String> list, String key) {

            // TODO gene: getValues unimplemented

        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#getValues(
         *      java.lang.String)
         */
        public List<String> getValues(String key) throws ConfigurationException {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.framework.configuration.Configuration#iterator()
         */
        public Iterator<Configuration> iterator() throws ConfigurationException {

            // TODO gene: iterator unimplemented
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

            // TODO gene: setConfigurationLoader unimplemented
        }

    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args ...
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return ...
     */
    public static Test suite() {

        return new TestSuite(TestBblWriter.class);
    }

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public TestBblWriter(String name) {

        super(name);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param in
     * @param res
     * @throws Exception
     */
    private void testA(String in, String res) throws Exception {

        StringBuffer sb = new StringBuffer();
        Writer w = new BblWriter(new StringBufferWriter(sb));
        w.configure(new DummyConfig());
        w.print(in);
        w.close();

        if (!res.equals(sb.toString())) {
            System.err.println(res);
            System.err.println(sb.toString());
            System.err.println();
        }

        assertEquals(res, sb.toString());
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testBblWriterA0() throws Exception {

        testA(" abc xxxxxxxxx1xxxxxxx", " abc\n  xxxxxxxxx1xxxxxxx");
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testBblWriterA1() throws Exception {

        testA("z abc xxxxxxxxx1xxxxxxx", "z abc\n  xxxxxxxxx1xxxxxxx");
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testBblWriterA15() throws Exception {

        testA("zzzznzzzznzzzzn abc xxxxxxxxx1",
            "zzzznzzzznzzzzn abc\n  xxxxxxxxx1");
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testBblWriterA16() throws Exception {

        testA("zzzznzzzznzzzznz abc xxxxxxxxx1",
            "zzzznzzzznzzzznz abc\n  xxxxxxxxx1");
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testBblWriterA17() throws Exception {

        testA("zzzznzzzznzzzznzz abc xxxxxxxxx1",
            "zzzznzzzznzzzznzz\n  abc xxxxxxxxx1");
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testBblWriterA2() throws Exception {

        testA("zz abc xxxxxxxxx1xxxxxxx", "zz abc\n  xxxxxxxxx1xxxxxxx");
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testBblWriterA3() throws Exception {

        testA("zzz abc xxxxxxxxx1xxxxxxx", "zzz abc\n  xxxxxxxxx1xxxxxxx");
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testBblWriterA4() throws Exception {

        testA("zzzz abc xxxxxxxxx1xxxxxxx", "zzzz abc\n  xxxxxxxxx1xxxxxxx");
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testBblWriterA5() throws Exception {

        testA("zzzzn abc xxxxxxxxx1xxxxxxx", "zzzzn abc\n  xxxxxxxxx1xxxxxxx");
    }

}
