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

package org.extex.exindex.lisp.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LValue;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LParserTest {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param in the content of the input stream
     * 
     * @return the parser
     */
    private LParser makeParser(String in) {

        return new LParser(new InputStreamReader(new ByteArrayInputStream(in
            .getBytes())), "rsc");
    }

    /**
     * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
     * 
     * @throws IOException in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void testRead1() throws IOException {

        LParser parser = new LParser(null, "rsc");
        parser.read();
    }

    /**
     * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void testRead2() throws IOException {

        LParser parser = makeParser("");
        assertNull(parser.read());
    }

    /**
     * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void testRead3() throws IOException {

        LParser parser = makeParser(";abc");
        assertNull(parser.read());
    }

    /**
     * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void testRead4() throws IOException {

        LParser parser = makeParser(";abc\n   ; def");
        assertNull(parser.read());
    }

    /**
     * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void testReadNil1() throws IOException {

        LParser parser = makeParser("nil");
        LValue n = parser.read();
        assertTrue(n instanceof LList);
        assertEquals(0, ((LList) n).size());
    }

    /**
     * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void testReadNil2() throws IOException {

        LParser parser = makeParser("()");
        LValue n = parser.read();
        assertTrue(n instanceof LList);
        assertEquals(0, ((LList) n).size());
    }

    /**
     * Test method for {@link org.extex.exindex.lisp.parser.LParser#read()}.
     * 
     * @throws IOException in case of an error
     */
    @Test
    public final void testReadNil3() throws IOException {

        LParser parser = makeParser("(  )");
        LValue n = parser.read();
        assertTrue(n instanceof LList);
        assertEquals(0, ((LList) n).size());
    }

}
