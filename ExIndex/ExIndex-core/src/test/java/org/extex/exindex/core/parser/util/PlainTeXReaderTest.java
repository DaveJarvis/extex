/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.parser.util;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PlainTeXReaderTest {

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead00() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader(""));

        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead01() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\"));

        assertEquals('\\', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead02() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("a"));

        assertEquals('a', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead03() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("a\nb"));

        assertEquals('a', r.read());
        assertEquals('\n', r.read());
        assertEquals('b', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead04() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("a\rb"));

        assertEquals('a', r.read());
        assertEquals('\r', r.read());
        assertEquals('b', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead05() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\ss  "));

        assertEquals('ß', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead06() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\ss{} "));

        assertEquals('ß', r.read());
        assertEquals(' ', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead10() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\\"x"));

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals('x', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead11() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\\"a"));

        assertEquals('ä', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead12() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\\"A"));

        assertEquals('Ä', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead13() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\\"{"));

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals('{', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead14() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\\"{a"));

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals('{', r.read());
        assertEquals('a', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead15() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\\"{ax"));

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals('{', r.read());
        assertEquals('a', r.read());
        assertEquals('x', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead16() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\aa{"));

        assertEquals('å', r.read());
        assertEquals('{', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead17() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\relax "));

        assertEquals('\\', r.read());
        assertEquals('r', r.read());
        assertEquals('e', r.read());
        assertEquals('l', r.read());
        assertEquals('a', r.read());
        assertEquals('x', r.read());
        assertEquals(' ', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead18() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\c x"));

        assertEquals('\\', r.read());
        assertEquals('c', r.read());
        assertEquals(' ', r.read());
        assertEquals('x', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead19() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\\""));

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead20() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\\"{x}"));

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals('{', r.read());
        assertEquals('x', r.read());
        assertEquals('}', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead21() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\\"{a}"));

        assertEquals('ä', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead31() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\c c"));

        assertEquals('ç', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead32() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\c C"));

        assertEquals('Ç', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead33() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\AA "));

        assertEquals('Å', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead34() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\AE "));

        assertEquals('Æ', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead51() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\'a"));

        assertEquals('á', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead61() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\`a"));

        assertEquals('à', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead71() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\^a"));

        assertEquals('â', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead81() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("\\~n"));

        assertEquals('ñ', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead91() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("^^@"));

        assertEquals('\0', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.util.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead92() throws Exception {

        PlainTeXReader r = new PlainTeXReader(new StringReader("^^00"));

        assertEquals('\0', r.read());
        assertEquals(-1, r.read());
    }

}
