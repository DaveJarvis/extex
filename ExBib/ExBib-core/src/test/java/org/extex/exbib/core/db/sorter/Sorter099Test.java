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
import java.util.List;

import org.extex.exbib.core.db.Entry;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link CodepointIgnoreCaseSorter}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Sorter099Test {

    /**
     * The field <tt>s</tt> contains the instance to be tested.
     */
    private CodepointIgnoreCaseSorter s = new CodepointIgnoreCaseSorter();

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
     * Set-up method.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        a = new Entry(null);
        a.setKey("abc");
        b = new Entry(null);
        b.setKey("def");
        c = new Entry(null);
        c.setKey("ghi");
    }

    /**
     * <testcase> Sorting an empty list results in an empty list. </testcase>
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
     * <testcase> Sorting a list with one element results in the same list.
     * </testcase>
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
     * <testcase> Sorting a sorted list with two elements results in the same
     * list. </testcase>
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
     * <testcase> Sorting an unsorted list with two elements results in the
     * inverted list. </testcase>
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
     * <testcase> Sorting a inversely sorted list with three elements results in
     * the sorted list. </testcase>
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
     * <testcase> Identical elements compare to equal if the sort key is not
     * set. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter099x0() throws Exception {

        assertEquals(0, s.compare(a, a));
    }

    /**
     * <testcase> a < b </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter099x1() throws Exception {

        assertTrue(0 > s.compare(a, b));
    }

    /**
     * <testcase> Identical elements compare to equal if the sort key is set.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter099x10() throws Exception {

        a.setSortKey("ghi");
        assertEquals(0, s.compare(a, a));
    }

    /**
     * <testcase> a[ghi] < b </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter099x11() throws Exception {

        a.setSortKey("ghi");
        assertTrue(0 < s.compare(a, b));
    }

    /**
     * <testcase> b > a[ghi] </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter099x12() throws Exception {

        a.setSortKey("ghi");
        assertTrue(0 > s.compare(b, a));
    }

    /**
     * <testcase> a < b </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter099x2() throws Exception {

        assertTrue(0 < s.compare(b, a));
    }

    /**
     * <testcase> "aa" < "ac". </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter099x21() throws Exception {

        a.setSortKey("ac");
        b.setSortKey("aa");
        assertTrue(0 < s.compare(a, b));
    }

    /**
     * <testcase> "ac" > "aa". </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter099x22() throws Exception {

        a.setSortKey("ac");
        b.setSortKey("aa");
        assertTrue(0 > s.compare(b, a));
    }

}
