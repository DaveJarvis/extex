/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.extex.exindex.makeindex.Entry;
import org.junit.Test;

/**
 * This is a test suite for MakeindexComparator.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class MakeindexComparatorTest {

    /**
     * <testcase> The compare() returns 0 for two arrays with empty strings.
     * </testcase>
     */
    @Test()
    public void testCompare01() {

        MakeindexComparator mc = new MakeindexComparator();
        assertEquals(0, mc.compare(
            new Entry(new String[]{""}, new String[]{""}, null), 
            new Entry(new String[]{""}, new String[]{""}, null)));
        assertEquals(1, mc.getComparisons());
    }

    /**
     * <testcase> The compare() returns 0 for two arrays with "x". </testcase>
     */
    @Test()
    public void testCompare02() {

        MakeindexComparator mc = new MakeindexComparator();
        assertEquals(0, mc.compare(
            new Entry(new String[]{"x"}, new String[]{""}, null), 
            new Entry(new String[]{"x"}, new String[]{""}, null)));
        assertEquals(1, mc.getComparisons());
    }

    /**
     * <testcase> The compare() returns -1 for two arrays ["1"] and ["2"].
     * </testcase>
     */
    @Test()
    public void testCompare03() {

        MakeindexComparator mc = new MakeindexComparator();
        assertEquals(-1, mc.compare(
            new Entry(new String[]{"1"}, new String[]{""}, null), 
            new Entry(new String[]{"2"}, new String[]{""}, null)));
        assertEquals(1, mc.getComparisons());
    }

    /**
     * <testcase> The compare() returns 1 for two arrays ["2"] and ["1"].
     * </testcase>
     */
    @Test()
    public void testCompare04() {

        MakeindexComparator mc = new MakeindexComparator();
        assertEquals(1, mc.compare(
            new Entry(new String[]{"2"}, new String[]{""}, null), 
            new Entry(new String[]{"1"}, new String[]{""}, null)));
        assertEquals(1, mc.getComparisons());
    }

    /**
     * <testcase> The compare() returns >0 for two arrays ["1"] and ["*"].
     * </testcase>
     */
    @Test()
    public void testCompare05() {

        MakeindexComparator mc = new MakeindexComparator();
        assertTrue(mc.compare(
            new Entry(new String[]{"1"}, new String[]{""}, null), 
            new Entry(new String[]{"*"}, new String[]{""}, null)) > 0);
        assertEquals(1, mc.getComparisons());
    }

    /**
     * <testcase> The compare() returns -1 for two arrays with "x" and the
     * second one with one additional element. </testcase>
     */
    @Test()
    public void testCompare10() {

        MakeindexComparator mc = new MakeindexComparator();
        assertEquals(-1, mc.compare(
            new Entry(new String[]{"x"}, new String[]{""}, null), 
            new Entry(new String[]{"x", "x"}, new String[]{""}, null)));
        assertEquals(1, mc.getComparisons());
    }

    /**
     * <testcase> The first argument of compareOne() may not be
     * <code>null</code>. </testcase>
     */
    @Test(expected = NullPointerException.class)
    public void testCompareOne01() {

        new MakeindexComparator().compareOne(null, null, 0);
    }

    /**
     * <testcase> The second argument of compareOne() may not be
     * <code>null</code>. </testcase>
     */
    @Test(expected = NullPointerException.class)
    public void testCompareOne02() {

        new MakeindexComparator().compareOne(new String[]{}, null, 0);
    }

    /**
     * <testcase> The compareOne() returns 0 for two empty arrays and the index
     * 0. </testcase>
     */
    @Test()
    public void testCompareOne03() {

        assertEquals(0, new MakeindexComparator().compareOne(new String[]{},
            new String[]{}, 0));
    }

    /**
     * <testcase> The compareOne() orders an empty array before a longer array.
     * </testcase>
     */
    @Test()
    public void testCompareOne11() {

        assertEquals(1, new MakeindexComparator().compareOne(new String[]{""},
            new String[]{}, 0));
    }

    /**
     * <testcase> The compareOne() orders an empty array before a longer array.
     * </testcase>
     */
    @Test()
    public void testCompareOne12() {

        assertEquals(-1, new MakeindexComparator().compareOne(new String[]{},
            new String[]{""}, 0));
    }

    /**
     * <testcase> The compareOne() returns 0 for two arrays with an empty
     * string. </testcase>
     */
    @Test()
    public void testCompareOne13() {

        assertEquals(0, new MakeindexComparator().compareOne(new String[]{""},
            new String[]{""}, 0));
    }

    /**
     * <testcase> Numbers come before lower case letters. </testcase>
     */
    @Test
    public void testCompareStrings01() {

        assertEquals(-1, new MakeindexComparator().compareStrings("1", "a"));
    }

    /**
     * <testcase> Numbers come before lower case letters. </testcase>
     */
    @Test
    public void testCompareStrings01s() {

        assertEquals(1, new MakeindexComparator().compareStrings("a", "1"));
    }

    /**
     * <testcase> Numbers come before upper case letters. </testcase>
     */
    @Test
    public void testCompareStrings02() {

        assertEquals(-1, new MakeindexComparator().compareStrings("1", "A"));
    }

    /**
     * <testcase> Numbers come before upper case letters. </testcase>
     */
    @Test
    public void testCompareStrings02s() {

        assertEquals(1, new MakeindexComparator().compareStrings("A", "1"));
    }

    /**
     * <testcase> Empty strings compare to 0. </testcase>
     */
    @Test
    public void testCompareStringsEmpty1() {

        assertEquals(0, new MakeindexComparator().compareStrings("", ""));
    }

    /**
     * <testcase> Empty strings come before letters. </testcase>
     */
    @Test
    public void testCompareStringsEmpty2() {

        assertEquals(-1, new MakeindexComparator().compareStrings("", "a"));
    }

    /**
     * <testcase> Empty strings come before letters. </testcase>
     */
    @Test
    public void testCompareStringsEmpty2s() {

        assertEquals(1, new MakeindexComparator().compareStrings("a", ""));
    }

    /**
     * <testcase> Empty strings come before digits. </testcase>
     */
    @Test
    public void testCompareStringsEmpty3() {

        assertEquals(-1, new MakeindexComparator().compareStrings("", "1"));
    }

    /**
     * <testcase> Empty strings come before digits. </testcase>
     */
    @Test
    public void testCompareStringsEmpty3s() {

        assertEquals(1, new MakeindexComparator().compareStrings("1", ""));
    }

    /**
     * <testcase> Identical letters compare to 0. </testcase>
     */
    @Test
    public void testCompareStringsLetter1() {

        assertEquals(0, new MakeindexComparator().compareStrings("a", "a"));
    }

    /**
     * <testcase> Lower case letters come before upper case letters. </testcase>
     */
    @Test
    public void testCompareStringsLetter2() {

        assertTrue(new MakeindexComparator().compareStrings("a", "A") > 0);
    }

    /**
     * <testcase> Lower case letters come before upper case letters. </testcase>
     */
    @Test
    public void testCompareStringsLetter2s() {

        assertTrue(new MakeindexComparator().compareStrings("A", "a") < 0);
    }

    /**
     * <testcase> Numbers are compared numerically. </testcase>
     */
    @Test
    public void testCompareStringsNumber1() {

        assertEquals(0, new MakeindexComparator().compareStrings("11", "11"));
    }

    /**
     * <testcase> Numbers are compared numerically. </testcase>
     */
    @Test
    public void testCompareStringsNumber2() {

        assertTrue(new MakeindexComparator().compareStrings("2", "11") < 0);
    }

    /**
     * <testcase> Numbers are compared numerically. </testcase>
     */
    @Test
    public void testCompareStringsNumber2s() {

        assertTrue(new MakeindexComparator().compareStrings("11", "2") > 0);
    }

    /**
     * <testcase> Symbols come after numbers. </testcase>
     */
    @Test
    public void testCompareStringsSymbol1() {

        assertEquals(-1, new MakeindexComparator().compareStrings("1", "-"));
    }

    /**
     * <testcase> Symbols come after numbers. </testcase>
     */
    @Test
    public void testCompareStringsSymbol1s() {

        assertEquals(1, new MakeindexComparator().compareStrings("-", "1"));
    }

    /**
     * <testcase> Symbols come after numbers. </testcase>
     */
    @Test
    public void testCompareStringsSymbol2() {

        assertEquals(-1, new MakeindexComparator().compareStrings("1", "$"));
    }

    /**
     * <testcase> Symbols come after numbers. </testcase>
     */
    @Test
    public void testCompareStringsSymbol2s() {

        assertEquals(1, new MakeindexComparator().compareStrings("$", "1"));
    }

    /**
     * <testcase> Symbols come before letters. </testcase>
     */
    @Test
    public void testCompareStringsSymbol3() {

        assertEquals(-1, new MakeindexComparator().compareStrings("-", "a"));
    }

    /**
     * <testcase> Symbols come before letters. </testcase>
     */
    @Test
    public void testCompareStringsSymbol3s() {

        assertEquals(1, new MakeindexComparator().compareStrings("a", "-"));
    }

    /**
     * <testcase> Symbols come before letters. </testcase>
     */
    @Test
    public void testCompareStringsSymbol4() {

        assertEquals(-1, new MakeindexComparator().compareStrings("%", "a"));
    }

    /**
     * <testcase> Symbols come before letters. </testcase>
     */
    @Test
    public void testCompareStringsSymbol4s() {

        assertEquals(1, new MakeindexComparator().compareStrings("a", "%"));
    }

    /**
     * <testcase> Symbols are ordered according to their code point. </testcase>
     */
    @Test
    public void testCompareStringsSymbol5() {

        assertTrue(new MakeindexComparator().compareStrings("-", "~") < 0);
    }

    /**
     * <testcase> Symbols are ordered according to their code point. </testcase>
     */
    @Test
    public void testCompareStringsSymbol6() {

        assertTrue(new MakeindexComparator().compareStrings("-", "8x") < 0);
    }

    /**
     * <testcase> Symbols are ordered according to their code point. </testcase>
     */
    @Test
    public void testCompareStringsSymbol6s() {

        assertTrue(new MakeindexComparator().compareStrings("8x", "-") > 0);
    }

    /**
     * <testcase> Symbols are ordered according to their code point. </testcase>
     */
    @Test
    public void testCompareStringsSymbol7() {

        assertTrue(new MakeindexComparator().compareStrings("8x", "9x") < 0);
    }

    /**
     * <testcase> Symbols are ordered according to their code point. </testcase>
     */
    @Test
    public void testCompareStringsSymbol7s() {

        assertTrue(new MakeindexComparator().compareStrings("9x", "8x") > 0);
    }

    /**
     * <testcase> Comparisons are empty for new instances. </testcase>
     */
    @Test
    public void testGetComparisons1() {

        assertEquals(0, new MakeindexComparator().getComparisons());
    }

    /**
     * <testcase> Comparisons are empty for new instances after rest().
     * </testcase>
     */
    @Test
    public void testReset1() {

        MakeindexComparator comparator = new MakeindexComparator();
        comparator.reset();
        assertEquals(0, comparator.getComparisons());
    }
}
