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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.StringReader;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.core.parser.makeindex.MakeindexParser;
import org.extex.exindex.core.type.raw.RawIndexentry;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.value.LChar;
import org.extex.exindex.lisp.type.value.LString;
import org.junit.Test;

/**
 * This is a test suite for the xindy parser.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6626 $
 */
public class MakeindexParserTest {

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test01() throws Exception {

        LInterpreter interpreter = new Indexer();
        MakeindexParser xp =
                new MakeindexParser(new StringReader(""), "default", "rsc",
                    interpreter);
        assertNull(xp.parse());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexException.class)
    public final void test02() throws Exception {

        LInterpreter interpreter = new Indexer();
        interpreter.setq("makeindex:level", new LString(""));
        MakeindexParser xp =
                new MakeindexParser(new StringReader(""), "default", "rsc",
                    interpreter);
        assertNull(xp.parse());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test03() throws Exception {

        LInterpreter interpreter = new Indexer();
        interpreter.setq("makeindex:level", new LChar('!'));
        MakeindexParser xp =
                new MakeindexParser(new StringReader(""), "default", "rsc",
                    interpreter);
        assertNull(xp.parse());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test04() throws Exception {

        LInterpreter interpreter = new Indexer();
        MakeindexParser xp =
                new MakeindexParser(new StringReader("\\relax"), "default",
                    "rsc", interpreter);
        assertNull(xp.parse());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test05() throws Exception {

        LInterpreter interpreter = new Indexer();
        MakeindexParser xp =
                new MakeindexParser(new StringReader("\\indexentry{abc}{123}"),
                    "default", "rsc", interpreter);
        RawIndexentry entry = xp.parse();
        assertNotNull(entry);
        assertEquals(
            "(indexentry :key (\"abc\") :print (\"abc\") :locref \"123\")\n",
            entry.toString());
        assertNull(xp.parse());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test06() throws Exception {

        LInterpreter interpreter = new Indexer();
        MakeindexParser xp =
                new MakeindexParser(new StringReader(
                    "\\indexentry{abc@def}{123}"), "default", "rsc",
                    interpreter);
        RawIndexentry entry = xp.parse();
        assertNotNull(entry);
        assertEquals(
            "(indexentry :key (\"abc\") :print (\"def\") :locref \"123\")\n",
            entry.toString());
        assertNull(xp.parse());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test07() throws Exception {

        LInterpreter interpreter = new Indexer();
        MakeindexParser xp =
                new MakeindexParser(new StringReader(
                    "\\indexentry{abc|def}{123}"), "default", "rsc",
                    interpreter);
        RawIndexentry entry = xp.parse();
        assertNotNull(entry);
        assertEquals(
            "(indexentry :key (\"abc\") :print (\"abc\") :attr \"def\" :locref \"123\")\n",
            entry.toString());
        assertNull(xp.parse());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test08() throws Exception {

        LInterpreter interpreter = new Indexer();
        MakeindexParser xp =
                new MakeindexParser(new StringReader(
                    "\\indexentry{abc|def(}{123}"), "default", "rsc",
                    interpreter);
        RawIndexentry entry = xp.parse();
        assertNotNull(entry);
        assertEquals(
            "(indexentry :key (\"abc\") :print (\"abc\") :attr \"def\" :locref \"123\" :open-range)\n",
            entry.toString());
        assertNull(xp.parse());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test09() throws Exception {

        LInterpreter interpreter = new Indexer();
        MakeindexParser xp =
                new MakeindexParser(new StringReader(
                    "\\indexentry{abc|def)}{123}"), "default", "rsc",
                    interpreter);
        RawIndexentry entry = xp.parse();
        assertNotNull(entry);
        assertEquals(
            "(indexentry :key (\"abc\") :print (\"abc\") :attr \"def\" :locref \"123\" :close-range)\n",
            entry.toString());
        assertNull(xp.parse());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test10() throws Exception {

        LInterpreter interpreter = new Indexer();
        interpreter.setq("makeindex:keyword", new LString("\\gloentry"));
        MakeindexParser xp =
                new MakeindexParser(new StringReader(
                    "\\gloentry{abc|def)}{123}"), "default", "rsc", interpreter);
        RawIndexentry entry = xp.parse();
        assertNotNull(entry);
        assertEquals(
            "(indexentry :key (\"abc\") :print (\"abc\") :attr \"def\" :locref \"123\" :close-range)\n",
            entry.toString());
        assertNull(xp.parse());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.makeindex.MakeindexParser#parse()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = RawIndexMissingCharException.class)
    public final void testError01() throws Exception {

        LInterpreter interpreter = new Indexer();
        MakeindexParser xp =
                new MakeindexParser(new StringReader("\\indexentry abc"),
                    "default", "rsc", interpreter);
        xp.parse();
    }

}
