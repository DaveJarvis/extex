/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.junit.Test;

/**
 * This is a test suite for the xindy parser.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6626 $
 */
public class XindyParserTest {

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test01() throws Exception {

        XindyParser xp =
                new XindyParser(new StringReader(
                    "(indexentry :key (\"abc\") :locref \"1\")"), "rsc");
        RawIndexentry ie = xp.parse();
        assertNotNull(ie);
        assertNull(xp.parse());
        assertTrue(ie.getMainKey() == ie.getPrintKey());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test02() throws Exception {

        XindyParser xp =
                new XindyParser(new StringReader(
                    "(indexentry :key (\"abc\") :attr \"see\" :locref \"1\")"),
                    "rsc");
        RawIndexentry ie = xp.parse();
        assertNotNull(ie);
        assertNull(xp.parse());
        assertTrue(ie.getMainKey() == ie.getPrintKey());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test03() throws Exception {

        XindyParser xp =
                new XindyParser(
                    new StringReader(
                        "(indexentry :key (\"abc\") :attr \"see\" :locref \"1\" :open-range)"),
                    "rsc");
        RawIndexentry ie = xp.parse();
        assertNotNull(ie);
        assertNull(xp.parse());
        assertTrue(ie.getMainKey() == ie.getPrintKey());
        assertNotNull(ie.getRef().getLayer());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test04() throws Exception {

        XindyParser xp =
                new XindyParser(
                    new StringReader(
                        "(indexentry :key (\"abc\") :attr \"see\" :locref \"1\" :close-range)"),
                    "rsc");
        RawIndexentry ie = xp.parse();
        assertNotNull(ie);
        assertNull(xp.parse());
        assertTrue(ie.getMainKey() == ie.getPrintKey());
        assertNotNull(ie.getRef().getLayer());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test05() throws Exception {

        XindyParser xp =
                new XindyParser(new StringReader(
                    "(indexentry :tkey (\"abc\") :locref \"1\")"), "rsc");
        RawIndexentry ie = xp.parse();
        assertNotNull(ie);
        assertNull(xp.parse());
        assertTrue(ie.getMainKey() == ie.getPrintKey());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test06() throws Exception {

        XindyParser xp =
                new XindyParser(new StringReader(
                    "(indexentry :tkey ((\"abc\")) :locref \"1\")"), "rsc");
        RawIndexentry ie = xp.parse();
        assertNotNull(ie);
        assertNull(xp.parse());
        assertTrue(ie.getMainKey() == ie.getPrintKey());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test07() throws Exception {

        XindyParser xp =
                new XindyParser(new StringReader(
                    "(indexentry :tkey ((\"abc\" \"def\")) :locref \"1\")"),
                    "rsc");
        RawIndexentry ie = xp.parse();
        assertNotNull(ie);
        assertNull(xp.parse());
        assertTrue(ie.getMainKey() == ie.getPrintKey());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test08() throws Exception {

        XindyParser xp =
                new XindyParser(new StringReader(
                    "(indexentry :key \"abc\" :locref \"1\")"), "rsc");
        RawIndexentry ie = xp.parse();
        assertNotNull(ie);
        assertNull(xp.parse());
        assertTrue(ie.getMainKey() == ie.getPrintKey());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test09() throws Exception {

        XindyParser xp =
                new XindyParser(new StringReader(
                    "(indexentry :key \"abc\" :print \"def\" :locref \"1\")"),
                    "rsc");
        RawIndexentry ie = xp.parse();
        assertNotNull(ie);
        assertNull(xp.parse());
        assertTrue(ie.getMainKey() == ie.getPrintKey());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test10() throws Exception {

        XindyParser xp =
                new XindyParser(new StringReader(
                    "(indexentry :key \"abc\" :print \"def\" :xref \"def\")"),
                    "rsc");
        RawIndexentry ie = xp.parse();
        assertNotNull(ie);
        assertNull(xp.parse());
        assertTrue(ie.getMainKey() == ie.getPrintKey());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testBound1() throws Exception {

        new XindyParser(new StringReader(
            "(indexentry :key (\"1\") :key (\"1\") )"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testBound2() throws Exception {

        new XindyParser(new StringReader(
            "(indexentry :print (\"1\") :print (\"1\") )"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testBound3() throws Exception {

        new XindyParser(new StringReader(
            "(indexentry :attr \"1\" :attr \"1\" )"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testBound5() throws Exception {

        new XindyParser(new StringReader(
            "(indexentry :xref (\"1\") :xref (\"1\") )"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testBound6() throws Exception {

        new XindyParser(new StringReader(
            "(indexentry :key (\"1\") :tkey (\"1\") )"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testEmpty1() throws Exception {

        XindyParser xp = new XindyParser(new StringReader(""), "rsc");
        assertNull(xp.parse());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testEmpty2() throws Exception {

        XindyParser xp = new XindyParser(new StringReader("   "), "rsc");
        assertNull(xp.parse());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testEmpty3() throws Exception {

        XindyParser xp = new XindyParser(new StringReader("; xxx\n   "), "rsc");
        assertNull(xp.parse());
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     */
    @Test(expected = NullPointerException.class)
    public final void testErr1() {

        new XindyParser(null, null);
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     */
    @Test(expected = NullPointerException.class)
    public final void testErr2() {

        new XindyParser(null, "");
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError01() throws Exception {

        new XindyParser(new StringReader("abc"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError02() throws Exception {

        new XindyParser(new StringReader("123"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError03() throws Exception {

        new XindyParser(new StringReader("nil"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError04() throws Exception {

        new XindyParser(new StringReader("()"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError05() throws Exception {

        new XindyParser(new StringReader("(123)"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError06() throws Exception {

        new XindyParser(new StringReader("(abc)"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError07() throws Exception {

        new XindyParser(new StringReader("(indexentry 123)"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError08() throws Exception {

        new XindyParser(new StringReader(
            "(indexentry :tkey ((\"\" \"\" \"\")))"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError09() throws Exception {

        new XindyParser(new StringReader("(indexentry :tkey (()))"), "rsc")
            .parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError10() throws Exception {

        new XindyParser(new StringReader("(indexentry :tkey (abc def))"), "rsc")
            .parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError11() throws Exception {

        new XindyParser(new StringReader(
            "(indexentry :key (\"1\") :xref (\"1\") :open-range)"), "rsc")
            .parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError12() throws Exception {

        new XindyParser(new StringReader(
            "(indexentry :key (\"1\") :xref (\"1\") :close-range)"), "rsc")
            .parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testError13() throws Exception {

        new XindyParser(
            new StringReader(
                "(indexentry :key (\"1\") :locref \"1\" :close-range :open-range)"),
            "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testMissing01() throws Exception {

        new XindyParser(new StringReader("(indexentry)"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testMissing02() throws Exception {

        new XindyParser(new StringReader("(indexentry :key (\"abc\"))"), "rsc")
            .parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testMissing03() throws Exception {

        new XindyParser(new StringReader("(indexentry :key )"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testMissing04() throws Exception {

        new XindyParser(new StringReader("(indexentry :key (abc))"), "rsc")
            .parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testMissing05() throws Exception {

        new XindyParser(new StringReader("(indexentry :xxx (abc))"), "rsc")
            .parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testMissing06() throws Exception {

        new XindyParser(new StringReader("(indexentry :print )"), "rsc")
            .parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testMissing07() throws Exception {

        new XindyParser(new StringReader("(indexentry :tkey )"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testMissing08() throws Exception {

        new XindyParser(new StringReader("(indexentry :attr )"), "rsc").parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testMissing09() throws Exception {

        new XindyParser(new StringReader("(indexentry :locref )"), "rsc")
            .parse();
    }

    /**
     * Test method for {@link org.extex.exindex.core.parser.XindyParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void testMissing10() throws Exception {

        new XindyParser(new StringReader("(indexentry :xref )"), "rsc").parse();
    }

}
