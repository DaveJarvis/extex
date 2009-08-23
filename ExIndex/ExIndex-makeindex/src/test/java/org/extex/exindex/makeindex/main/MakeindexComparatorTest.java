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

import org.junit.Test;

/**
 *
 * TODO gene: missing JavaDoc.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class MakeindexComparatorTest extends MakeindexComparator {

    /**
     * <testcase> Numbers come before lowercase letters. </testcase>
     */
    @Test
    public void test01() {

        assertEquals(-1, compareStrings("1", "a"));
    }

    /**
     * <testcase> Numbers come before lowercase letters. </testcase>
     */
    @Test
    public void test01s() {

        assertEquals(1, compareStrings("a", "1"));
    }

    /**
     * <testcase> Numbers come before uppercase letters. </testcase>
     */
    @Test
    public void test02() {

        assertEquals(-1, compareStrings("1", "A"));
    }

    /**
     * <testcase> Numbers come before uppercase letters. </testcase>
     */
    @Test
    public void test02s() {

        assertEquals(1, compareStrings("A", "1"));
    }

    /**
     * <testcase> Empty strings compare to 0. </testcase>
     */
    @Test
    public void testEmpty1() {

        assertEquals(0, compareStrings("", ""));
    }

    /**
     * <testcase> Empty strings come before letters. </testcase>
     */
    @Test
    public void testEmpty2() {

        assertEquals(-1, compareStrings("", "a"));
    }

    /**
     * <testcase> Empty strings come before letters. </testcase>
     */
    @Test
    public void testEmpty2s() {

        assertEquals(1, compareStrings("a", ""));
    }

    /**
     * <testcase> Empty strings come before digits. </testcase>
     */
    @Test
    public void testEmpty3() {

        assertEquals(-1, compareStrings("", "1"));
    }

    /**
     * <testcase> Empty strings come before digits. </testcase>
     */
    @Test
    public void testEmpty3s() {

        assertEquals(1, compareStrings("1", ""));
    }

    /**
     * <testcase> Identical letters compare to 0. </testcase>
     */
    @Test
    public void testLetter1() {

        assertEquals(0, compareStrings("a", "a"));
    }

    /**
     * <testcase> Lowercase letters come before uppercase letters. </testcase>
     */
    @Test
    public void testLetter2() {

        assertTrue(compareStrings("a", "A") > 0);
    }

    /**
     * <testcase> Lowercase letters come before uppercase letters. </testcase>
     */
    @Test
    public void testLetter2s() {

        assertTrue(compareStrings("A", "a") < 0);
    }

    /**
     * <testcase> Numbers are compared numerically. </testcase>
     */
    @Test
    public void testNumber1() {

        assertEquals(0, compareStrings("11", "11"));
    }

    /**
     * <testcase> Numbers are compared numerically. </testcase>
     */
    @Test
    public void testNumber2() {

        assertTrue(compareStrings("2", "11") < 0);
    }

    /**
     * <testcase> Numbers are compared numerically. </testcase>
     */
    @Test
    public void testNumber2s() {

        assertTrue(compareStrings("11", "2") > 0);
    }

    /**
     * <testcase> Symbols come after numbers. </testcase>
     */
    @Test
    public void testSymbol1() {

        assertEquals(-1, compareStrings("1", "-"));
    }

    /**
     * <testcase> Symbols come after numbers. </testcase>
     */
    @Test
    public void testSymbol1s() {

        assertEquals(1, compareStrings("-", "1"));
    }

    /**
     * <testcase> Symbols come after numbers. </testcase>
     */
    @Test
    public void testSymbol2() {

        assertEquals(-1, compareStrings("1", "$"));
    }

    /**
     * <testcase> Symbols come after numbers. </testcase>
     */
    @Test
    public void testSymbol2s() {

        assertEquals(1, compareStrings("$", "1"));
    }

    /**
     * <testcase> Symbols come before letters. </testcase>
     */
    @Test
    public void testSymbol3() {

        assertEquals(-1, compareStrings("-", "a"));
    }

    /**
     * <testcase> Symbols come before letters. </testcase>
     */
    @Test
    public void testSymbol3s() {

        assertEquals(1, compareStrings("a", "-"));
    }

    /**
     * <testcase> Symbols come before letters. </testcase>
     */
    @Test
    public void testSymbol4() {

        assertEquals(-1, compareStrings("%", "a"));
    }

    /**
     * <testcase> Symbols come before letters. </testcase>
     */
    @Test
    public void testSymbol4s() {

        assertEquals(1, compareStrings("a", "%"));
    }

    /**
     * <testcase> Symbols are pordered according to their code point.
     * </testcase>
     */
    @Test
    public void testSymbol5() {

        assertTrue(compareStrings("-", "~") < 0);
    }

    /**
     * <testcase> Symbols are pordered according to their code point.
     * </testcase>
     */
    @Test
    public void testSymbol6() {

        assertTrue(compareStrings("-", "8x") < 0);
    }

}
