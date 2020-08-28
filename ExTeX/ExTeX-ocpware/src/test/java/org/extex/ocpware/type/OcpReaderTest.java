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
import org.extex.ocpware.engine.CharacterOutOfRangeException;
import org.extex.ocpware.engine.IllegalOpCodeException;
import org.extex.ocpware.engine.IllegalPcException;
import org.extex.ocpware.engine.IllegalTableException;
import org.extex.ocpware.engine.IllegalTableItemException;
import org.extex.ocpware.engine.OcpEmptyStackException;
import org.extex.ocpware.engine.OcpLineOverflowException;
import org.extex.ocpware.engine.OcpLineUnderflowException;
import org.extex.ocpware.engine.OcpReader;
import org.extex.ocpware.engine.OcpReaderObserver;
import org.extex.ocpware.engine.UnsupportedOutputException;
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
    private StringBuilder buffer = new StringBuilder();

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
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testLeftBackup1() throws IOException {

        OcpReader reader = prepare("expressions:\n`a'<1,> => `x'; . => \\1;",
            "aaab");
        // new OcpExTeXWriter().write(System.out, r.getProgram());
        // attachObserver(r);

        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'b', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method.
     * 
     * @throws IOException in case of an error
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testError1() throws IOException {

        new OcpReader(null, null);
    }

    /**
     * Test method.
     * 
     * @throws IOException in case of an error
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testError2() throws IOException {

        new OcpReader(new CharArrayReader("".toCharArray()), null);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public final void testError3() throws IOException {

        OcpReader reader =
                new OcpReader(new CharArrayReader("".toCharArray()),
                    new OcpProgram());
        reader.close();
        assertTrue(true);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = UnsupportedOutputException.class)
    public final void testError4() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[1]);
        ocpProgram.setOutput(3);
        new OcpReader(new CharArrayReader("".toCharArray()), ocpProgram);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#getState()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGetState1() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[1]);
        OcpReader reader =
                new OcpReader(new CharArrayReader("".toCharArray()), ocpProgram);
        assertEquals(0, reader.getState());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#getProgram()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    public final void testGetProgram1() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[1]);
        OcpReader reader =
                new OcpReader(new CharArrayReader("".toCharArray()), ocpProgram);
        assertEquals(ocpProgram, reader.getProgram());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#getPc()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGetPc1() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[1]);
        OcpReader reader =
                new OcpReader(new CharArrayReader("".toCharArray()), ocpProgram);
        assertEquals(0, reader.getPc());
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
        OcpReader reader =
                new OcpReader(new CharArrayReader("".toCharArray()), ocpProgram);
        reader.close();
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
        OcpReader reader =
                new OcpReader(new CharArrayReader("".toCharArray()), ocpProgram);
        reader.close();
        reader.close();
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

        OcpReader reader = prepare("expressions:\n. => `*';",
            "");
        reader.close();
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testClose4() throws IOException {

        OcpReader reader = prepare("expressions:\n. => `*'; % xxx",
            "");
        reader.close();
        assertEquals(-1, reader.read());
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
        OcpReader reader =
                new OcpReader(new CharArrayReader(".".toCharArray()),
                    ocpProgram);
        reader.read();
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
        OcpReader reader =
                new OcpReader(new CharArrayReader(".".toCharArray()),
                    ocpProgram);
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = IllegalPcException.class)
    public final void testCodeError3() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{});
        OcpReader reader =
                new OcpReader(new CharArrayReader(".".toCharArray()),
                    ocpProgram);
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = org.extex.ocpware.engine.IllegalStateException.class)
    public final void testStateChange4() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{OcpCode.STATE_CHANGE.inst(1)});
        OcpReader reader =
                new OcpReader(new CharArrayReader(".".toCharArray()),
                    ocpProgram);
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = org.extex.ocpware.engine.IllegalStateException.class)
    public final void testStatePush4() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{OcpCode.STATE_PUSH.inst(1)});
        OcpReader reader =
                new OcpReader(new CharArrayReader(".".toCharArray()),
                    ocpProgram);
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testReadEof1() throws Exception {

        OcpReader reader =
                prepare("input: 1;output: 1;" + "expressions:\n. => `*';",
                    "");

        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testReadEof2() throws Exception {

        OcpReader reader =
                prepare("input: 1;output: 1;" + "expressions:\n. => `*';",
                    "");

        assertEquals(-1, reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRightNum1() throws Exception {

        OcpReader reader = prepare("expressions:\n.. => 42;",
            "ab");

        assertEquals((int) '*', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testPushNum1() throws Exception {

        OcpReader reader =
                prepare("input: 1;output: 1;" + "expressions:\n. => `*';",
                    "a");

        assertEquals((int) '*', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testPushNum2() throws Exception {

        OcpReader reader =
                prepare("input: 1;output: 1;" + "expressions:\n. => `*';",
                    "a b");

        assertEquals((int) '*', reader.read());
        assertEquals((int) '*', reader.read());
        assertEquals((int) '*', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRightChar1() throws Exception {

        OcpReader reader =
                prepare("input: 1;output: 1;" + "expressions:\n. => \\1;",
                    "a");

        assertEquals((int) 'a', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRightLchar1() throws Exception {

        OcpReader reader =
                prepare("input: 1;output: 1;" + "expressions:\n. => \\$;",
                    "a");

        assertEquals((int) 'a', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testPushChar1() throws Exception {

        OcpReader reader = prepare("expressions:\n. => `-'#(\\1);",
            "x");

        assertEquals((int) '-', reader.read());
        assertEquals((int) 'x', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testAdd1() throws Exception {

        OcpReader reader = prepare("expressions:\n. => #(64+1);",
            "x");

        assertEquals((int) 'A', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testSub1() throws Exception {

        OcpReader reader = prepare("expressions:\n. => #(66-1);",
            "x");

        assertEquals((int) 'A', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMult1() throws Exception {

        OcpReader reader = prepare("expressions:\n. => #(33*2);",
            "x");

        assertEquals((int) 'B', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMod1() throws Exception {

        OcpReader reader = prepare("expressions:\n. => #(12 mod: 2);",
            "x");

        assertEquals(0, reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testMod2() throws Exception {

        OcpReader reader = prepare("expressions:\n. => #(13 mod: 2);",
            "x");

        assertEquals(1, reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testDiv1() throws Exception {

        OcpReader reader = prepare("expressions:\n. => #(12 div: 2);",
            "x");

        assertEquals(6, reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test(expected = ArithmeticException.class)
    @SuppressWarnings("boxing")
    public final void testDiv0() throws Exception {

        OcpReader reader = prepare("expressions:\n. => #(12 div: 0);",
            "x");
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testLookup1() throws Exception {

        OcpReader reader =
                prepare("tables: a[1] = {123}; "
                        + "expressions:\n. => #(a[0]);",
                    "x");
        assertEquals(123, reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test(expected = IllegalTableItemException.class)
    @SuppressWarnings("boxing")
    public final void testLookup2() throws Exception {

        OcpReader reader =
                prepare("tables: a[1] = {123}; "
                        + "expressions:\n. => #(a[2]);",
                    "x");
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = OcpEmptyStackException.class)
    public final void testLookup3() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{OcpCode.LOOKUP.inst()});
        OcpReader reader =
                new OcpReader(new CharArrayReader(".".toCharArray()),
                    ocpProgram);
        reader.read();
        assertTrue(false);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = IllegalTableException.class)
    public final void testLookup4() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(
            new int[]{OcpCode.PUSH_NUM.inst(2), OcpCode.PUSH_NUM.inst(2),
                    OcpCode.LOOKUP.inst()});
        OcpReader reader =
                new OcpReader(new CharArrayReader(".".toCharArray()),
                    ocpProgram);
        reader.read();
        assertTrue(false);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = OcpLineOverflowException.class)
    @SuppressWarnings("boxing")
    public final void testRightSome1() throws IOException {

        OcpReader reader = prepare("expressions:\n. => \\(*-2);",
            "x");
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRightSome2() throws IOException {

        OcpReader reader = prepare("expressions:\n.. => \\(*+0-1);",
            "xy");

        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'y', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testPBackOutput1() throws IOException {

        OcpReader reader =
                prepare("expressions:\n`a' => `x' <= #(65); . => \\*;",
                    "a");

        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'A', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = CharacterOutOfRangeException.class)
    @SuppressWarnings("boxing")
    public final void testPBackOutput2() throws IOException {

        OcpReader reader =
                prepare("expressions:\n`a' => `.' <= #@\"10000; . => \\*;",
                    "a");

        reader.read();
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testPBackNum1() throws IOException {

        OcpReader reader = prepare("expressions:\n`a' => `x' <= 65; . => \\*;",
            "a");

        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'A', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = CharacterOutOfRangeException.class)
    @SuppressWarnings("boxing")
    public final void testPBackNum2() throws IOException {

        OcpReader reader =
                prepare("expressions:\n`a' => `.' <= @\"10000; . => \\*;",
                    "a");

        reader.read();
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testPBackChar1() throws IOException {

        OcpReader reader =
                prepare("expressions:\n`a'`b' => `x' <= \\1; . => \\*;",
                    "ab");

        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'a', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = OcpLineUnderflowException.class)
    @SuppressWarnings("boxing")
    public final void testPBackChar2() throws IOException {

        OcpReader reader =
                prepare("expressions:\n`a'`b' => `x' <= \\0; . => \\1;",
                    "ab");

        reader.read();
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = OcpLineOverflowException.class)
    @SuppressWarnings("boxing")
    public final void testPBackChar3() throws IOException {

        OcpReader reader =
                prepare("expressions:\n`a'`b' => `x' <= \\3; . => \\1;",
                    "ab");

        reader.read();
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testPBackLchar1() throws IOException {

        OcpReader reader =
                prepare("expressions:\n`a'`b' => `x' <= \\$; . => \\1;",
                    "ab");

        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'b', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testPBackSome1() throws IOException {

        OcpReader reader =
                prepare("expressions:\n`a'`b' => `x' <= \\(*+1-1); . => \\1;",
                    "ab");

        assertEquals((int) 'x', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testPBackSome2() throws IOException {

        OcpReader reader =
                prepare("expressions:\n`a'`b' => `x' <= \\(*+0-1); . => \\1;",
                    "ab");

        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'a', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testLeftRetrun1() throws IOException {

        OcpReader reader =
                prepare("expressions:\n" + "`a'`b'`c' => 123;"
                        + "\n`a\'`b\' => `x' <= \\$; . => \\1;",
                    "ab");

        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'b', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testLeftRetrun2() throws IOException {

        OcpReader reader =
                prepare("expressions:\n" + "`a'`b'`c' => 123;\n"
                        + "`a' => `x' <= `p'; . => \\1;",
                    "ab");

        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'p', reader.read());
        assertEquals((int) 'b', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testLeftRetrun3() throws IOException {

        OcpReader reader =
                prepare("expressions:\n" + "`a'`b'`c' => 123;"
                        + "\n`a'`b' => `x' <= \\$; . => \\1;",
                    "abd");

        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'b', reader.read());
        assertEquals((int) 'd', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = OcpLineUnderflowException.class)
    @SuppressWarnings("boxing")
    public final void testPBackLchar3() throws IOException {

        OcpReader reader =
                prepare("expressions:\n`a'`b' => `x' <= \\($-3); . => \\1;",
                    "ab");

        reader.read();
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test(expected = OcpEmptyStackException.class)
    @SuppressWarnings("boxing")
    public final void testStatePop1() throws IOException {

        OcpReader reader = prepare("expressions:\n. => `x' <pop:>;",
            "ab");

        assertEquals((int) 'x', reader.read());
        reader.read();
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testStatePop2() throws IOException {

        OcpReader reader =
                prepare("states: a;\n" + "expressions:\n"
                        + "`a' => `y' <push: a>;\n" + "<a>. =>`x' <pop:>;\n",
                    "ab");

        assertEquals((int) 'y', reader.read());
        assertEquals((int) 'x', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testStatePop3() throws IOException {

        OcpReader reader =
                prepare("states: a;\n" + "expressions:\n"
                        + "`a' => `y' <push: a>;\n" + "<a>. =>`x' <pop:>;\n",
                    "aba");

        assertEquals((int) 'y', reader.read());
        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'y', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testStateChange1() throws IOException {

        OcpReader reader =
                prepare("states: a;\n" + "expressions:\n" + "`a' => `y' <a>;\n"
                        + "<a>. =>`x';\n",
                    "aba");

        assertEquals((int) 'y', reader.read());
        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'x', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoLtGt1() throws IOException {

        OcpReader reader =
                prepare("states: a;\n" + "expressions:\n" + "`a'-`z' => `.';\n"
                        + ". =>`-';\n",
                    "a");

        assertEquals((int) '.', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoLtGt2() throws IOException {

        OcpReader reader =
                prepare("states: a;\n" + "expressions:\n" + "`a'-`z' => `.';\n"
                        + ". =>`-';\n",
                    "A");
        assertEquals((int) '-', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoLtGt3() throws IOException {

        OcpReader reader =
                prepare("states: a;\n" + "expressions:\n" + "`A'-`Z' => `.';\n"
                        + ". =>`-';\n",
                    "a");
        assertEquals((int) '-', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGoto1() throws IOException {

        OcpReader reader =
                prepare("states: a;\n" + "expressions:\n"
                        + "(`A'|`B') => `.';\n" + ". =>`-';\n",
                    "A");
        assertEquals((int) '.', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoEq1() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{
            OcpCode.LEFT_START.inst(), OcpCode.GOTO_EQ.inst('x'), 4,
                    OcpCode.RIGHT_NUM.inst('X'), OcpCode.STOP.inst()});
        OcpReader reader =
                new OcpReader(new CharArrayReader("x".toCharArray()),
                    ocpProgram);

        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoEq2() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{
            OcpCode.LEFT_START.inst(), OcpCode.GOTO_EQ.inst('x'), 4,
                    OcpCode.RIGHT_NUM.inst('X'), OcpCode.STOP.inst()});
        OcpReader reader =
                new OcpReader(new CharArrayReader("y".toCharArray()),
                    ocpProgram);

        assertEquals((int) 'X', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoEq3() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{
            OcpCode.LEFT_START.inst(), OcpCode.GOTO_EQ.inst('x'), 4,
                    OcpCode.RIGHT_NUM.inst('X'), OcpCode.STOP.inst()});
        OcpReader reader =
                new OcpReader(new CharArrayReader("w".toCharArray()),
                    ocpProgram);

        assertEquals((int) 'X', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoLe1() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{
            OcpCode.LEFT_START.inst(), OcpCode.GOTO_LE.inst('x'), 4,
                    OcpCode.RIGHT_NUM.inst('X'), OcpCode.STOP.inst()});
        OcpReader reader =
                new OcpReader(new CharArrayReader("x".toCharArray()),
                    ocpProgram);

        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoLe2() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{
            OcpCode.LEFT_START.inst(), OcpCode.GOTO_LE.inst('x'), 4,
                    OcpCode.RIGHT_NUM.inst('X'), OcpCode.STOP.inst()});
        OcpReader reader =
                new OcpReader(new CharArrayReader("y".toCharArray()),
                    ocpProgram);

        assertEquals((int) 'X', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoLe3() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{
            OcpCode.LEFT_START.inst(), OcpCode.GOTO_LE.inst('x'), 4,
                    OcpCode.RIGHT_NUM.inst('X'), OcpCode.STOP.inst()});
        OcpReader reader =
                new OcpReader(new CharArrayReader("w".toCharArray()),
                    ocpProgram);

        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoGe1() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{
            OcpCode.LEFT_START.inst(), OcpCode.GOTO_GE.inst('x'), 4,
                    OcpCode.RIGHT_NUM.inst('X'), OcpCode.STOP.inst()});
        OcpReader reader =
                new OcpReader(new CharArrayReader("x".toCharArray()),
                    ocpProgram);

        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoGe2() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{
            OcpCode.LEFT_START.inst(), OcpCode.GOTO_GE.inst('x'), 4,
                    OcpCode.RIGHT_NUM.inst('X'), OcpCode.STOP.inst()});
        OcpReader reader =
                new OcpReader(new CharArrayReader("y".toCharArray()),
                    ocpProgram);

        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#close()}.
     * 
     * @throws IOException not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testGotoGe3() throws IOException {

        OcpProgram ocpProgram = new OcpProgram();
        ocpProgram.addState(new int[]{
            OcpCode.LEFT_START.inst(), OcpCode.GOTO_GE.inst('x'), 4,
                    OcpCode.RIGHT_NUM.inst('X'), OcpCode.STOP.inst()});
        OcpReader reader =
                new OcpReader(new CharArrayReader("w".toCharArray()),
                    ocpProgram);

        assertEquals((int) 'X', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead4() throws Exception {

        OcpReader reader =
                prepare("input: 1;output: 1;" + "expressions:\n. => #\\$;",
                    "a b");

        assertEquals((int) 'a', reader.read());
        assertEquals((int) ' ', reader.read());
        assertEquals((int) 'b', reader.read());
        assertEquals(-1, reader.read());
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

        OcpReader reader =
                prepare("input: 1;output: 1;" + "expressions:\n. => #\\$;",
                    "a b");
        reader.register(new OcpReaderObserver() {

            public void step(OcpReader reader, int opcode, int arg) {

                buffer.append(OcpCode.get(opcode).toString());
                buffer.append('\n');
            }

            public void close(OcpReader reader) {

                buffer.append("*\n");
            }
        });

        assertEquals((int) 'a', reader.read());
        assertEquals((int) ' ', reader.read());
        assertEquals((int) 'b', reader.read());
        assertEquals(-1, reader.read());
        reader.close();
        assertEquals("LEFT_START\n" + "PUSH_LCHAR\n" + "RIGHT_OUTPUT\n"
                + "STOP\n" + "LEFT_START\n" + "PUSH_LCHAR\n" + "RIGHT_OUTPUT\n"
                + "STOP\n" + "LEFT_START\n" + "PUSH_LCHAR\n" + "RIGHT_OUTPUT\n"
                + "STOP\n*\n", buffer.toString());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead6() throws Exception {

        OcpReader reader = prepare("expressions:\n. => \\*;",
            "ab");

        assertEquals((int) 'a', reader.read());
        assertEquals((int) 'b', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead7() throws Exception {

        OcpReader reader = prepare("expressions: .. => \\*;\n. => \\*;",
            "ab");

        assertEquals((int) 'a', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead100() throws Exception {

        OcpReader reader = prepare("expressions: .. => \\*;\n. => \\*;",
            "ab");

        char[] cbuff = new char[12];
        assertEquals(1, reader.read(cbuff));
        assertEquals('a', cbuff[0]);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead101() throws Exception {

        OcpReader reader = prepare("expressions:\n. => \\*;",
            "abc");

        char[] cbuff = new char[2];
        assertEquals(2, reader.read(cbuff));
        assertEquals('a', cbuff[0]);
        assertEquals('b', cbuff[1]);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testRead102() throws Exception {

        OcpReader reader = prepare("expressions:\n.. => \\(*+0-1);",
            "abcd");
        // attachObserver(r);

        char[] cbuff = new char[2];
        assertEquals(2, reader.read(cbuff));
        assertEquals('a', cbuff[0]);
        assertEquals('b', cbuff[1]);
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testEnd1() throws Exception {

        OcpReader reader =
                prepare("expressions:\n" + "`x' end: => `A';" + "\n. => \\*;",
                    "x");

        assertEquals((int) 'A', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testEnd2() throws Exception {

        OcpReader reader =
                prepare("expressions:\n" + "`x' end: => `A';" + "\n. => \\*;",
                    "xx");

        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'A', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testEnd3() throws Exception {

        OcpReader reader =
                prepare("expressions:\n" + "`x' end: => `A';" + "\n. => \\*;",
                    "x\nxa");

        assertEquals((int) 'A', reader.read());
        assertEquals((int) '\n', reader.read());
        assertEquals((int) 'x', reader.read());
        assertEquals((int) 'a', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testBeg1() throws Exception {

        OcpReader reader = prepare("expressions:\n"
                + "beg: `x' => `A';\n"
                + ". => \\*;",
            "x");

        assertEquals((int) 'A', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testBeg2() throws Exception {

        OcpReader reader = prepare("expressions:\n"
                + "beg: `x' => `A';\n"
                + ". => \\*;",
            "xx");

        assertEquals((int) 'A', reader.read());
        assertEquals((int) 'x', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testBeg3() throws Exception {

        OcpReader reader = prepare("expressions:\n"
                + "beg: `x' => `A';\n"
                + ". => \\*;",
            "ax");

        assertEquals((int) 'a', reader.read());
        assertEquals((int) 'x', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testBeg4() throws Exception {

        OcpReader reader = prepare("expressions:\n"
                + "beg: `x' => `A';\n"
                + ". => \\*;",
            "a\nxx");

        assertEquals((int) 'a', reader.read());
        assertEquals((int) '\n', reader.read());
        assertEquals((int) 'A', reader.read());
        assertEquals((int) 'x', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testBeg5() throws Exception {

        OcpReader reader = prepare("expressions:\n"
                + "beg: `x' => `A';\n"
                + "`y' => `.' <= `z';\n"
                + ". => \\*;",
            "yx");

        assertEquals((int) '.', reader.read());
        assertEquals((int) 'z', reader.read());
        assertEquals((int) 'x', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testNewline1() throws Exception {

        OcpReader reader = prepare("expressions:\n"
                + ". => \\*;",
            "abc\ndef");

        assertEquals((int) 'a', reader.read());
        assertEquals((int) 'b', reader.read());
        assertEquals((int) 'c', reader.read());
        assertEquals((int) '\n', reader.read());
        assertEquals((int) 'd', reader.read());
        assertEquals("d", reader.getLine());
        assertEquals((int) 'e', reader.read());
        assertEquals((int) 'f', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#read()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    @SuppressWarnings("boxing")
    public final void testNewline2() throws Exception {

        OcpReader reader = prepare("expressions:\n"
                + "`a' => `.' <= `z';\n"
                + ". => \\*;",
            "aa\ncd");

        assertEquals((int) '.', reader.read());
        assertEquals((int) 'z', reader.read());
        assertEquals((int) '.', reader.read());
        assertEquals((int) 'z', reader.read());
        assertEquals((int) '\n', reader.read());
        assertEquals((int) 'c', reader.read());
        assertEquals((int) 'd', reader.read());
        assertEquals(-1, reader.read());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#toString()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    public final void testToString0() throws Exception {

        OcpReader reader = prepare("expressions:\n.. => \\(*+0-1);",
            "abcd");
        assertEquals("[0/0] \n", reader.toString());
    }

    /**
     * Test method for {@link org.extex.ocpware.engine.OcpReader#toString()}.
     * 
     * @throws Exception not very likely
     */
    @Test
    public final void testToString1() throws Exception {

        OcpReader reader = prepare("expressions:\n.. => \\(*+0-1);",
            "abcd");
        reader.read(new char[2]);
        assertEquals("[0/4] ab\n", reader.toString());
    }

}
