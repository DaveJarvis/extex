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

package org.extex.ocpware.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;

import org.extex.ocpware.compiler.parser.CompilerState;
import org.junit.Test;

/**
 * This is a test suite for the OcpReader.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpReaderTest {

    /**
     * Test method.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testError1() {

        new OcpReader(null, null);
    }

    /**
     * Test method.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testError2() {

        new OcpReader(new CharArrayReader("".toCharArray()), null);
    }

    /**
     * Test method for {@link org.extex.ocpware.type.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public final void testError3() throws IOException {

        OcpReader r =
                new OcpReader(new CharArrayReader("".toCharArray()),
                    new OcpProgram());
        r.close();
        assertTrue(true);
    }

    /**
     * Test method for {@link org.extex.ocpware.type.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    public final void testClose1() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[1]);
        OcpReader r =
                new OcpReader(new CharArrayReader("".toCharArray()), ocpProgram);
        r.close();
        assertTrue(true);
    }

    /**
     * Test method for {@link org.extex.ocpware.type.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead0() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(
                    "input: 1;output: 1;expressions: . => `*';".getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocpProgram = cs.compile();
        OcpReader r =
                new OcpReader(new CharArrayReader("".toCharArray()),
                    ocpProgram);

        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.type.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead1() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(
                    "input: 1;output: 1;expressions: . => `*';".getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocpProgram = cs.compile();
        OcpReader r =
                new OcpReader(new CharArrayReader("abc\n".toCharArray()),
                    ocpProgram);

        assertEquals(42, r.read());
        assertEquals(42, r.read());
        assertEquals(42, r.read());
    }

}
