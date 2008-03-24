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

package org.extex.exbib.core.db.sorter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.db.Entry;
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
 * @version $Revision: 1.1 $
 */
public class TestSorter extends TestCase {

    /**
     * TODO gene: missing JavaDoc.
     */
    private static class DummyConfig implements Configuration {

        /**
         * The field <tt>order</tt> contains the ...
         */
        private String order = null;

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

            return order;
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
         * @see org.extex.framework.configuration.Configuration#getValues(java.util.List,
         *      java.lang.String)
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
         * @see org.extex.framework.configuration.Configuration#setConfigurationLoader(org.extex.framework.configuration.ConfigurationLoader)
         */
        public void setConfigurationLoader(ConfigurationLoader loader) {

            // TODO gene: setConfigurationLoader unimplemented

        }

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param order
         */
        public void setOrder(String order) {

            this.order = order;
        }

    }

    /**
     * Run the tests stand-alone.
     * 
     * @param args the command line arguments
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

        return new TestSuite(TestSorter099.class);
    }

    /**
     * The field <tt>s</tt> contains the ...
     */
    private SorterImpl s = new SorterImpl();

    /**
     * The field <tt>a</tt> contains the ...
     */
    private Entry a;

    /**
     * The field <tt>b</tt> contains the ...
     */
    private Entry b;

    /**
     * The field <tt>c</tt> contains the ...
     */
    private Entry c;

    /**
     * Creates a new object.
     * 
     * @param name ...
     */
    public TestSorter(String name) {

        super(name);
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {

        super.setUp();
        s.configure(new DummyConfig());

        a = new Entry(null);
        a.setKey("abc");
        b = new Entry(null);
        b.setKey("def");
        c = new Entry(null);
        c.setKey("ghi");
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {

        super.tearDown();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void test_0() throws Exception {

        List<Entry> list = new ArrayList<Entry>();
        s.sort(list);
        assertEquals(0, list.size());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void test_1() throws Exception {

        List<Entry> list = new ArrayList<Entry>();
        list.add(a);
        s.sort(list);
        assertEquals(1, list.size());
        assertEquals(a, list.get(0));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void test_21() throws Exception {

        List<Entry> list = new ArrayList<Entry>();
        list.add(a);
        list.add(b);
        s.sort(list);
        assertEquals(2, list.size());
        assertEquals(a, list.get(0));
        assertEquals(b, list.get(1));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void test_22() throws Exception {

        List<Entry> list = new ArrayList<Entry>();
        list.add(b);
        list.add(a);
        s.sort(list);
        assertEquals(2, list.size());
        assertEquals(a, list.get(0));
        assertEquals(b, list.get(1));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void test_31() throws Exception {

        List<Entry> list = new ArrayList<Entry>();
        list.add(c);
        list.add(b);
        list.add(a);
        s.sort(list);
        assertEquals(3, list.size());
        assertEquals(a, list.get(0));
        assertEquals(b, list.get(1));
        assertEquals(c, list.get(2));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testSorter_0() throws Exception {

        assertEquals(0, s.compare(a, a));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testSorter_1() throws Exception {

        assertTrue(0 > s.compare(a, b));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testSorter_10() throws Exception {

        a.setSortKey("ghi");
        assertEquals(0, s.compare(a, a));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testSorter_11() throws Exception {

        a.setSortKey("ghi");
        assertTrue(0 < s.compare(a, b));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testSorter_12() throws Exception {

        a.setSortKey("ghi");
        assertTrue(0 > s.compare(b, a));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testSorter_2() throws Exception {

        assertTrue(0 < s.compare(b, a));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testSorter_21() throws Exception {

        a.setSortKey("ac");
        b.setSortKey("aa");
        assertTrue(0 < s.compare(a, b));
    }

    // TODO: more testcases for collating sequences

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testSorter_22() throws Exception {

        a.setSortKey("ac");
        b.setSortKey("aa");
        assertTrue(0 > s.compare(b, a));
    }

}
