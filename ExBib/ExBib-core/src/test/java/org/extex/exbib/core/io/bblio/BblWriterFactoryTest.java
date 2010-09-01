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

package org.extex.exbib.core.io.bblio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.extex.exbib.core.io.Writer;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class BblWriterFactoryTest {

    private class Testee extends BblWriterFactory {

        public Testee(Configuration configuration, String encoding)
                throws ConfigurationException,
                    UnsupportedEncodingException {

            super(configuration, encoding);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.io.bblio.BblWriterFactory#infoDiscarted()
         */
        @Override
        protected void infoDiscarted() {

            dis = true;
            super.infoDiscarted();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.io.bblio.BblWriterFactory#infoOutput(java.lang.String)
         */
        @Override
        protected void infoOutput(String file) {

            out = true;
            super.infoOutput(file);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.io.bblio.BblWriterFactory#infoStdout()
         */
        @Override
        protected void infoStdout() {

            std = true;
            super.infoStdout();
        }
    }

    /**
     * The field <tt>CFG</tt> contains the ...
     */
    private static final Configuration CFG = new Configuration() {

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

            if ("class".equals(name)) {
                return BblWriter.class.getName();
            }

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
    };

    /**
     * The field <tt>dis</tt> contains the ...
     */
    private boolean dis;

    /**
     * The field <tt>out</tt> contains the ...
     */
    private boolean out;

    /**
     * The field <tt>std</tt> contains the ...
     */
    private boolean std;

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNewInstance1() throws Exception {

        Writer w = new BblWriterFactory(CFG, null).newInstance(null);
        assertNotNull(w);
        w.close();
    }

    /**
     * <testcase> Test that the discarded notifier is triggered and none of the
     * others when invoked with null as argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNewInstance2() throws Exception {

        dis = false;
        out = false;
        std = false;
        Writer w = new Testee(CFG, null).newInstance(null);
        assertNotNull(w);
        assertTrue(dis);
        assertFalse(out);
        assertFalse(std);
        w.close();
    }

    /**
     * <testcase> Test that the discarded notifier is triggered and none of the
     * others when invoked with the empty string as argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNewInstance3() throws Exception {

        dis = false;
        out = false;
        std = false;
        Writer w = new Testee(CFG, null).newInstance("");
        assertNotNull(w);
        assertTrue(dis);
        assertFalse(out);
        assertFalse(std);
        w.close();
    }

    /**
     * <testcase> Test that the stdout notifier is triggered and none of the
     * others when invoked with the string "-" as argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNewInstance4() throws Exception {

        dis = false;
        out = false;
        std = false;
        Writer w = new Testee(CFG, null).newInstance("-");
        assertNotNull(w);
        assertFalse(dis);
        assertFalse(out);
        assertTrue(std);
        w.close();
    }

    /**
     * <testcase> Test that the stdout notifier is triggered and none of the
     * others when invoked with the string "-" as argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNewInstance5() throws Exception {

        dis = false;
        out = false;
        std = false;
        Writer w = new Testee(CFG, null).newInstance("target/test");
        assertNotNull(w);
        assertFalse(dis);
        assertTrue(out);
        assertFalse(std);
        w.close();
    }

}
