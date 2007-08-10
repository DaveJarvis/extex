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
import java.io.PrintStream;

import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.engine.IllegalOpCodeException;
import org.extex.ocpware.engine.OcpEmptyStackException;
import org.extex.ocpware.engine.OcpLineOverflowException;
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
     * The field <tt>buffer</tt> contains the buffer for transporting
     * information from a visitor out.
     */
    private StringBuffer buffer = new StringBuffer();

    /**
     * Attach an observer for debugging.
     * 
     * @param reader the reader
     */
    protected void attachObserver(OcpReader reader) {

        reader.register(new OcpReaderObserver() {

            public void step(OcpReader reader, int opcode, int arg) {

                PrintStream stream = System.err;
                stream.print(Integer.toHexString(reader.getPc()));
                stream.print(" ");
                OcpCode ocpCode = OcpCode.get(opcode);
                stream.print(ocpCode.toString());
                switch (ocpCode.getArguments().length) {
                    case 1:
                        stream.print(" 0x");
                        stream.print(Integer.toHexString(arg));
                        break;
                    case 2:
                        stream.print(" 0x");
                        stream.print(Integer.toHexString(arg));
                        stream.print(", 0x");
                        stream.print(Integer.toHexString(reader.getCodeWord()));
                        break;
                    default:
                        // case 0 : nothing to do
                }
                stream.println();
            }

            public void close(OcpReader reader) {

                // nope
            }
        });
    }

    /**
     * Prepare the reader by compiling a program and attaching an input stream.
     * 
     * @param prog the program code
     * @param in the input stream
     * 
     * @return the reader
     * 
     * @throws IOException in case of an error
     */
    private OcpReader prepare(String prog, String in) throws IOException {

        InputStream stream = new ByteArrayInputStream(prog.getBytes());
        OcpProgram p = new CompilerState(stream).compile();
        stream.close();
        return new OcpReader(new CharArrayReader(in.toCharArray()), p);
    }

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
    @Test(expected = OcpLineOverflowException.class)
    @SuppressWarnings("boxing")
    public final void testError4() throws IOException {

        OcpReader r = prepare("expressions:\n. => \\(*-2);", //
            "x");
        r.read();
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

        OcpReader r = prepare("expressions:\n. => `*';", //
            "");
        r.close();
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testClose4() throws IOException {

        OcpReader r = prepare("expressions:\n. => `*'; % xxx", //
            "");
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
    public final void testAdd1() throws Exception {

        OcpReader r = prepare("expressions:\n. => #(64+1);", //
            "x");

        assertEquals((int) 'A', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testSub1() throws Exception {

        OcpReader r = prepare("expressions:\n. => #(66-1);", //
            "x");

        assertEquals((int) 'A', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMult1() throws Exception {

        OcpReader r = prepare("expressions:\n. => #(33*2);", //
            "x");

        assertEquals((int) 'B', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMod1() throws Exception {

        OcpReader r = prepare("expressions:\n. => #(12 mod: 2);", //
            "x");

        assertEquals(0, r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMod2() throws Exception {

        OcpReader r = prepare("expressions:\n. => #(13 mod: 2);", //
            "x");

        assertEquals(1, r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testDiv1() throws Exception {

        OcpReader r = prepare("expressions:\n. => #(12 div: 2);", //
            "x");

        assertEquals(6, r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test(expected = ArithmeticException.class)
    @SuppressWarnings("boxing")
    public final void testDiv0() throws Exception {

        OcpReader r = prepare("expressions:\n. => #(12 div: 0);", //
            "x");

        r.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead0() throws Exception {

        OcpReader r =
                prepare("input: 1;output: 1;" + "expressions:\n. => `*';", //
                    "");

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

        OcpReader r =
                prepare("input: 1;output: 1;" + "expressions:\n. => `*';", //
                    "");

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

        OcpReader r =
                prepare("input: 1;output: 1;" + "expressions:\n. => `*';", //
                    "a");

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

        OcpReader r =
                prepare("input: 1;output: 1;" + "expressions:\n. => `*';", //
                    "a b");

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

        OcpReader r =
                prepare("input: 1;output: 1;" + "expressions:\n. => #\\$;", //
                    "a b");

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

        OcpReader r =
                prepare("input: 1;output: 1;" + "expressions:\n. => #\\$;", //
                    "a b");
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

        OcpReader r = prepare("expressions:\n.. => 42;", //
            "ab");

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

        OcpReader r = prepare("expressions:\n. => \\*;", //
            "ab");

        assertEquals((int) 'a', r.read());
        assertEquals((int) 'b', r.read());
        assertEquals(-1, r.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead7() throws Exception {

        OcpReader r = prepare("expressions: .. => \\*;\n. => \\*;", //
            "ab");
        // attachObserver(r);

        assertEquals((int) 'a', r.read());
        assertEquals(-1, r.read());
    }
}
