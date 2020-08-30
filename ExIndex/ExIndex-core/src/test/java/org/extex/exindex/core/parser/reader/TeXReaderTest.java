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
 * This is a test suite for the {@link TeXReader}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TeXReaderTest {

    /**
     * Create a reader to be tested.
     * 
     * @param s the contents to be read
     * 
     * @return the reader
     */
    protected TeXReader makeReader(String s) {

        return new TeXReader("rsc", new StringReader(s));
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.TeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTeXRead00() throws Exception {

        TeXReader r = makeReader("");

        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.TeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTeXRead01() throws Exception {

        TeXReader r = makeReader("\\");

        assertEquals('\\', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.TeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTeXRead02() throws Exception {

        TeXReader r = makeReader("a");

        assertEquals('a', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.TeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTeXRead03() throws Exception {

        TeXReader r = makeReader("a\nb");

        assertEquals('a', r.read());
        assertEquals('\n', r.read());
        assertEquals('b', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.TeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTeXRead04() throws Exception {

        TeXReader r = makeReader("a\rb");

        assertEquals('a', r.read());
        assertEquals('\n', r.read());
        assertEquals('b', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.TeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTeXRead05() throws Exception {

        TeXReader r = makeReader("\\relax ");

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
     * {@link org.extex.exindex.core.parser.reader.TeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTeXRead06() throws Exception {

        TeXReader r = makeReader("^^@");

        assertEquals('\0', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.parser.reader.TeXReader#read()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTeXRead07() throws Exception {

        TeXReader r = makeReader("^^00");

        assertEquals('\0', r.read());
        assertEquals(-1, r.read());
    }

}
