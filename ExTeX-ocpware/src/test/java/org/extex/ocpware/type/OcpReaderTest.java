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
import org.extex.ocpware.engine.IllegalOpCodeException;
import org.extex.ocpware.engine.OcpEmptyStackException;
import org.extex.ocpware.engine.OcpReader;
import org.extex.ocpware.engine.OcpReaderObserver;
import org.junit.Test;

/**
 * This is a test suite for the OcpReader.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpReaderTest {

    /**
     * The field <tt>buffer</tt> contains the ...
     */
    private StringBuffer buffer = new StringBuffer();

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
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
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
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
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
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    public final void testClose2() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[1]);
        OcpReader r =
                new OcpReader(new CharArrayReader("".toCharArray()), ocpProgram);
        r.close();
        r.close();
        assertTrue(true);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testClose3() throws IOException {

        InputStream stream =
                new ByteArrayInputStream("expressions: . => `*';".getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocpProgram = cs.compile();
        OcpReader r =
                new OcpReader(new CharArrayReader("".toCharArray()), ocpProgram);
        r.close();
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = IllegalOpCodeException.class)
    public final void testCodeError1() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{-1});
        OcpReader r =
                new OcpReader(new CharArrayReader(".".toCharArray()),
                    ocpProgram);
        r.read();
        assertTrue(false);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = IllegalOpCodeException.class)
    public final void testCodeError2() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{127 << OcpCode.OPCODE_OFFSET});
        OcpReader r =
                new OcpReader(new CharArrayReader(".".toCharArray()),
                    ocpProgram);
        r.read();
        assertTrue(false);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = OcpEmptyStackException.class)
    public final void testCodeError3() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(//
            new int[]{OcpCode.OP_LOOKUP << OcpCode.OPCODE_OFFSET});
        OcpReader r =
                new OcpReader(new CharArrayReader(".".toCharArray()),
                    ocpProgram);
        r.read();
        assertTrue(false);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
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
                new OcpReader(new CharArrayReader("".toCharArray()), ocpProgram);

        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead01() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(
                    "input: 1;output: 1;expressions: . => `*';".getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocpProgram = cs.compile();
        OcpReader r =
                new OcpReader(new CharArrayReader("".toCharArray()), ocpProgram);

        assertEquals(-1, r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead2() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(
                    "input: 1;output: 1;expressions: . => `*';".getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocpProgram = cs.compile();
        OcpReader r =
                new OcpReader(new CharArrayReader("a".toCharArray()),
                    ocpProgram);

        assertEquals((int) '*', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead3() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(
                    "input: 1;output: 1;expressions: . => `*';".getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocpProgram = cs.compile();
        OcpReader r =
                new OcpReader(new CharArrayReader("a b".toCharArray()),
                    ocpProgram);

        assertEquals((int) '*', r.read());
        assertEquals((int) '*', r.read());
        assertEquals((int) '*', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead4() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(
                    "input: 1;output: 1;expressions: . => #\\$;".getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocpProgram = cs.compile();
        OcpReader r =
                new OcpReader(new CharArrayReader("a b".toCharArray()),
                    ocpProgram);

        assertEquals((int) 'a', r.read());
        assertEquals((int) ' ', r.read());
        assertEquals((int) 'b', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for
     * {@link org.extex.ocpware.engine.OcpReader#register(OcpReaderObserver)}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRegister1() throws Exception {

        if (buffer.length() > 0) {
            buffer.delete(0, buffer.length() - 1);
        }

        InputStream stream =
                new ByteArrayInputStream(
                    "input: 1;output: 1;expressions: . => #\\$;".getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocpProgram = cs.compile();
        OcpReader r =
                new OcpReader(new CharArrayReader("a b".toCharArray()),
                    ocpProgram);
        r.register(new OcpReaderObserver() {

            public void step(OcpReader reader, int opcode, int arg) {

                buffer.append(OcpCode.get(opcode).toString());
                buffer.append('\n');
            }

            public void close(OcpReader reader) {

                // nope
            }
        });

        assertEquals((int) 'a', r.read());
        assertEquals((int) ' ', r.read());
        assertEquals((int) 'b', r.read());
        assertEquals(-1, r.read());
        assertEquals("LEFT_START\n" + "PUSH_LCHAR\n" + "RIGHT_OUTPUT\n"
                + "STOP\n" + "LEFT_START\n" + "PUSH_LCHAR\n" + "RIGHT_OUTPUT\n"
                + "STOP\n" + "LEFT_START\n" + "PUSH_LCHAR\n" + "RIGHT_OUTPUT\n"
                + "STOP\n" // + "LEFT_START\n"
        , buffer.toString());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead5() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(
                    "input: 1;output: 1;expressions: .. => 42;".getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocpProgram = cs.compile();
        OcpReader r =
                new OcpReader(new CharArrayReader("ab".toCharArray()),
                    ocpProgram);

        assertEquals((int) '*', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead6() throws Exception {

        InputStream stream =
                new ByteArrayInputStream(("input: 1;output: 1;"
                        + "expressions: .. => \\*;\n. => \\*;").getBytes());
        CompilerState cs = new CompilerState(stream);
        stream.close();
        OcpProgram ocpProgram = cs.compile();
        OcpReader r =
                new OcpReader(new CharArrayReader("ab".toCharArray()),
                    ocpProgram);
        r.register(new OcpReaderObserver() {

            public void step(OcpReader reader, int opcode, int arg) {

                System.err.print(Integer.toHexString(reader.getPc()));
                System.err.print(" ");
                System.err.println(OcpCode.get(opcode).toString());
            }

            public void close(OcpReader reader) {

                // nope
            }
        });

        assertEquals((int) '*', r.read());
        assertEquals(-1, r.read());
    }

}
