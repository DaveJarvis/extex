/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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
 * Test suite for {@link Sorter099Impl}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TestSorter099 extends TestCase {

    /**
     * A dummy configuration.
     */
    private static class DummyConfig implements Configuration {

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

            // findConfiguration unimplemented
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

            // findConfiguration unimplemented
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

            // getValue unimplemented
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

            // getValues unimplemented
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

            // iterator unimplemented
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

    }

    /**
     * Command line program to run the tests.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }

    /**
     * Get the test suite.
     * 
     * @return the suite
     */
    public static Test suite() {

        return new TestSuite(TestSorter099.class);
    }

    /**
     * The field <tt>s</tt> contains the instance to be tested.
     */
    private Sorter099Impl s = new Sorter099Impl();

    /**
     * The field <tt>a</tt> contains an entry.
     */
    private Entry a;

    /**
     * The field <tt>b</tt> contains another entry.
     */
    private Entry b;

    /**
     * The field <tt>c</tt> contains yet another entry.
     */
    private Entry c;

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public TestSorter099(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
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
     * <testcase> Sorting an empty list results in an empty list. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test0() throws Exception {

        List<Entry> list = new ArrayList<Entry>();
        s.sort(list);
        assertEquals(0, list.size());
    }

    /**
     * <testcase> Sorting a list with one element results in the same list.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        List<Entry> list = new ArrayList<Entry>();
        list.add(a);
        s.sort(list);
        assertEquals(1, list.size());
        assertEquals(a, list.get(0));
    }

    /**
     * <testcase> Sorting a sorted list with two elements results in the same
     * list. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test21() throws Exception {

        List<Entry> list = new ArrayList<Entry>();
        list.add(a);
        list.add(b);
        s.sort(list);
        assertEquals(2, list.size());
        assertEquals(a, list.get(0));
        assertEquals(b, list.get(1));
    }

    /**
     * <testcase> Sorting an unsorted list with two elements results in the
     * inverted list. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test22() throws Exception {

        List<Entry> list = new ArrayList<Entry>();
        list.add(b);
        list.add(a);
        s.sort(list);
        assertEquals(2, list.size());
        assertEquals(a, list.get(0));
        assertEquals(b, list.get(1));
    }

    /**
     * <testcase> Sorting a inversely sorted list with three elements results in
     * the sorted list. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test31() throws Exception {

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
     * <testcase> Identical elements compare to equal if the sort key is not
     * set. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSorter099_0() throws Exception {

        assertEquals(0, s.compare(a, a));
    }

    /**
     * <testcase> a < b </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSorter099_1() throws Exception {

        assertTrue(0 > s.compare(a, b));
    }

    /**
     * <testcase> Identical elements compare to equal if the sort key is set.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSorter099_10() throws Exception {

        a.setSortKey("ghi");
        assertEquals(0, s.compare(a, a));
    }

    /**
     * <testcase> a[ghi] < b </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSorter099_11() throws Exception {

        a.setSortKey("ghi");
        assertTrue(0 < s.compare(a, b));
    }

    /**
     * <testcase> b > a[ghi] </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSorter099_12() throws Exception {

        a.setSortKey("ghi");
        assertTrue(0 > s.compare(b, a));
    }

    /**
     * <testcase> a < b </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSorter099_2() throws Exception {

        assertTrue(0 < s.compare(b, a));
    }

    /**
     * <testcase> "aa" < "ac". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSorter099_21() throws Exception {

        a.setSortKey("ac");
        b.setSortKey("aa");
        assertTrue(0 < s.compare(a, b));
    }

    /**
     * <testcase> "ac" > "aa". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSorter099_22() throws Exception {

        a.setSortKey("ac");
        b.setSortKey("aa");
        assertTrue(0 > s.compare(b, a));
    }

}
