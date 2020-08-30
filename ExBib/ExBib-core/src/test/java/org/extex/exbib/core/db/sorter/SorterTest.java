/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db.sorter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.extex.exbib.core.db.Entry;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationLoader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.configuration.exception.ConfigurationInvalidResourceException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationSyntaxException;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a test suite for the {@link RbcSorter}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class SorterTest {

    /**
     * This is a dummy configuration for the tests.
     */
    private static class DummyConfig implements Configuration {

        /**
         * The field {@code order} contains the order value.
         */
        private String order = null;

    public Configuration findConfiguration(String key)
                throws ConfigurationInvalidResourceException,
                    ConfigurationNotFoundException,
                    ConfigurationSyntaxException,
                    ConfigurationIOException {

            // findConfiguration unimplemented
            return null;
        }

        /**
    *      java.lang.String)
         */
        public Configuration findConfiguration(String key, String attribute)
                throws ConfigurationException {

            // findConfiguration unimplemented
            return null;
        }

    public String getAttribute(String name) throws ConfigurationException {

            return null;
        }

    public Configuration getConfiguration(String key)
                throws ConfigurationException {

            return null;
        }

        /**
    *      java.lang.String)
         */
        public Configuration getConfiguration(String key, String attribute)
                throws ConfigurationException {

            return null;
        }

    public String getValue() throws ConfigurationException {

            return order;
        }

    public String getValue(String key) throws ConfigurationException {

            return order;
        }

        /**
    *      int)
         */
        public int getValueAsInteger(String key, int defaultValue)
                throws ConfigurationException {

            return 20;
        }

        /**
    *      java.lang.String)
         */
        public void getValues(List<String> list, String key) {

            // getValues unimplemented
        }

    public List<String> getValues(String key) throws ConfigurationException {

            return null;
        }

    public Iterator<Configuration> iterator() throws ConfigurationException {

            // iterator unimplemented
            return null;
        }

    public Iterator<Configuration> iterator(String key)
                throws ConfigurationException {

            return null;
        }

    public void setConfigurationLoader(ConfigurationLoader loader) {

            // setConfigurationLoader unimplemented
        }

        /**
         * Setter for the order.
         * 
         * @param order the order
         */
        public void setOrder(String order) {

            this.order = order;
        }

    }

    /**
     * The field {@code s} contains the instance to be tested.
     */
    private final RbcSorter s = new RbcSorter();

    /**
     * The field {@code a} contains an entry.
     */
    private Entry a;

    /**
     * The field {@code b} contains another entry.
     */
    private Entry b;

    /**
     * The field {@code c} contains yet another entry.
     */
    private Entry c;

    /**
     * Set-up the test case.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        DummyConfig cfg = new DummyConfig();
        cfg.setOrder("< a,A< b,B< c,C< d,D< e,E< f,F< g,G< h,H< i,I< j,J"
                + "< k,K< l,L< m,M< n,N< o,O< p,P< q,Q< r,R< s,S< t,T"
                + "< u,U< v,V< w,W< x,X< y,Y< z,Z");
        s.configure(cfg);

        a = new Entry(null);
        a.setKey("abc");
        b = new Entry(null);
        b.setKey("def");
        c = new Entry(null);
        c.setKey("ghi");
    }

    /**
     *  The empty list is sorted to itself. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        List<Entry> list = new ArrayList<Entry>();
        s.sort(list);
        assertEquals(0, list.size());
    }

    /**
     *  A one-element list is sorted to itself. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        List<Entry> list = new ArrayList<Entry>();
        list.add(a);
        s.sort(list);
        assertEquals(1, list.size());
        assertEquals(a, list.get(0));
    }

    /**
     *  A sorted two-element list is sorted to itself. 
     * 
     * @throws Exception in case of an error
     */
    @Test
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
     *  A unsorted two-element list is reversed. 
     * 
     * @throws Exception in case of an error
     */
    @Test
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
     *  A sorted three-element list is sorted to itself. 
     * 
     * @throws Exception in case of an error
     */
    @Test
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
     *  Identical elements are compared equal when no sort key is set.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter0() throws Exception {

        assertEquals(0, s.compare(a, a));
    }

    /**
     *  a < b 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter1() throws Exception {

        assertTrue(0 > s.compare(a, b));
    }

    /**
     *  Identical elements are compared equal when a sort key is set.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter10() throws Exception {

        a.setSortKey("ghi");
        assertEquals(0, s.compare(a, a));
    }

    /**
     *  a[ghi] < b 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter11() throws Exception {

        a.setSortKey("ghi");
        assertTrue(0 < s.compare(a, b));
    }

    /**
     *  b > a[ghi] 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter12() throws Exception {

        a.setSortKey("ghi");
        assertTrue(0 > s.compare(b, a));
    }

    /**
     *  b > a 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter2() throws Exception {

        assertTrue(0 < s.compare(b, a));
    }

    /**
     *  a[ac] > b[aa] 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter21() throws Exception {

        a.setSortKey("ac");
        b.setSortKey("aa");
        assertTrue(0 < s.compare(a, b));
    }

    /**
     *  b[aa] < a[ac] 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter22() throws Exception {

        a.setSortKey("ac");
        b.setSortKey("aa");
        assertTrue(0 > s.compare(b, a));
    }

    // TODO: more test cases for collating sequences

}
