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

package org.extex.exindex.core.parser.reader;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

/**
 * This is a test suite for the {@link PlainTeXReader}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PlainTeXReaderTest extends TeXReaderTest {

    /**
     * Create a reader to be tested.
     * 
     * @param s the contents to be read
     * 
     * @return the reader
     */
    @Override
    protected TeXReader makeReader(String s) {

        return new PlainTeXReader("rsc", new StringReader(s));
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead05() throws Exception {

        TeXReader r = makeReader("\\ss  ");

        assertEquals('ß', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead06() throws Exception {

        TeXReader r = makeReader("\\ss{} ");

        assertEquals('ß', r.read());
        assertEquals(' ', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead10() throws Exception {

        TeXReader r = makeReader("\\\"x");

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals('x', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead11() throws Exception {

        TeXReader r = makeReader("\\\"a");

        assertEquals('ä', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead12() throws Exception {

        TeXReader r = makeReader("\\\"A");

        assertEquals('Ä', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead13() throws Exception {

        TeXReader r = makeReader("\\\"{");

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals('{', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead14() throws Exception {

        TeXReader r = makeReader("\\\"{a");

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals('{', r.read());
        assertEquals('a', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead15() throws Exception {

        TeXReader r = makeReader("\\\"{ax");

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals('{', r.read());
        assertEquals('a', r.read());
        assertEquals('x', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead16() throws Exception {

        TeXReader r = makeReader("\\aa{");

        assertEquals('\u00e5', r.read());
        assertEquals('{', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead18() throws Exception {

        TeXReader r = makeReader("\\c x");

        assertEquals('\\', r.read());
        assertEquals('c', r.read());
        assertEquals(' ', r.read());
        assertEquals('x', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead19() throws Exception {

        TeXReader r = makeReader("\\\"");

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead20() throws Exception {

        TeXReader r = makeReader("\\\"{x}");

        assertEquals('\\', r.read());
        assertEquals('"', r.read());
        assertEquals('{', r.read());
        assertEquals('x', r.read());
        assertEquals('}', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead21() throws Exception {

        TeXReader r = makeReader("\\\"{a}");

        assertEquals('\u00e4', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead31() throws Exception {

        TeXReader r = makeReader("\\c c");

        assertEquals('\u00e7', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead32() throws Exception {

        TeXReader r = makeReader("\\c C");

        assertEquals('\u00c7', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead33() throws Exception {

        TeXReader r = makeReader("\\AA ");

        assertEquals('\u00c5', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead34() throws Exception {

        TeXReader r = makeReader("\\AE ");

        assertEquals('\u00c6', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead51() throws Exception {

        TeXReader r = makeReader("\\'a");

        assertEquals('\u00e1', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead61() throws Exception {

        TeXReader r = makeReader("\\`a");

        assertEquals('\u00e0', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead71() throws Exception {

        TeXReader r = makeReader("\\^a");

        assertEquals('\u00e2', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.PlainTeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testRead81() throws Exception {

        TeXReader r = makeReader("\\~n");

        assertEquals('\u00f1', r.read());
        assertEquals(-1, r.read());
    }

}
