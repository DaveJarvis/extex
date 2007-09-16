/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.stream.impl32;

import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;

import org.extex.core.UnicodeChar;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.stream.exception.ScannerNoUnicodeNameException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.token.TokenFactoryImpl;

/**
 * Test cases for the string implementation of a token stream.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class TokenStreamStringImpl32Test extends TestCase {

    /**
     * const 32
     */
    private static final int C32 = 32;

    /**
     * The field <tt>fac</tt> contains the token factory to use.
     */
    private static TokenFactory fac;

    /**
     * The field <tt>tokenizer</tt> contains the tokenizer to use for
     * categorizing characters.
     */
    private static Tokenizer tokenizer;

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(TokenStreamStringImpl32Test.class);
    }

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public TokenStreamStringImpl32Test(String name) {

        super(name);
    }

    /**
     * Test cr2
     * 
     * @throws Exception in case of an error
     */
    public void ___testCr2() throws Exception {

        TokenStream stream = makeStream("x\n\n");
        assertEquals("the letter x", stream.get(fac, tokenizer).toString());
        Token t = stream.get(fac, tokenizer);
        assertEquals(C32, t.getChar().getCodePoint());
        assertNotNull(t);
        assertEquals("the control sequence \\par", t.toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * Test Cr3
     * 
     * @throws Exception in case of an error
     */
    public void ___testCr3() throws Exception {

        TokenStream stream = makeStream("\naaa\n  x");
        assertEquals("the control sequence \\par", stream.get(fac, tokenizer)
            .toString());
        assertEquals("the letter x", stream.get(fac, tokenizer).toString());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * Test cr4
     * 
     * @throws Exception in case of an error
     */
    public void ___testCr4() throws Exception {

        TokenStream stream = makeStream("\n\nx");
        assertEquals("the control sequence \\par", stream.get(fac, tokenizer)
            .toString());
        assertEquals("the control sequence \\par", stream.get(fac, tokenizer)
            .toString());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * A single space at the beginning of the processing is skipped
     * 
     * @throws Exception in case of an error
     */
    public void ___testSpace() throws Exception {

        TokenStream stream = makeStream(" ");
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * Two spaces at the beginning are ignored.
     * 
     * @throws Exception in case of an error
     */
    public void ___testSpaces() throws Exception {

        TokenStream stream = makeStream("  ");
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * Create a stream of tokens fed from a string.
     * 
     * @param line the input string
     * @return the new token stream
     * @throws IOException in case of an error
     */
    private TokenStream makeStream(String line) throws IOException {

        return new TokenStreamImpl32(null, null, new StringReader(line),
            Boolean.FALSE, "");
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {

        super.setUp();
        fac = new TokenFactoryImpl();
        tokenizer = new Tokenizer() {

            public Catcode getCatcode(UnicodeChar c) {

                if (c.isLetter()) {
                    return Catcode.LETTER;
                }
                switch (c.getCodePoint()) {
                    case '$':
                        return Catcode.MATHSHIFT;
                    case '^':
                        return Catcode.SUPMARK;
                    case '_':
                        return Catcode.SUBMARK;
                    case '%':
                        return Catcode.COMMENT;
                    case '&':
                        return Catcode.TABMARK;
                    case '#':
                        return Catcode.MACROPARAM;
                    case '{':
                        return Catcode.LEFTBRACE;
                    case '}':
                        return Catcode.RIGHTBRACE;
                    case '\t':
                    case '\r':
                    case '\n':
                    case ' ':
                        return Catcode.SPACE;
                    case '\0':
                    case '\f':
                        return Catcode.IGNORE;
                }
                return Catcode.OTHER;
            }

            public String getNamespace() {

                return "";
            }

        };
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {

        super.tearDown();
    }

    /**
     * The digit 1 is parsed as other character and nothing more.
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        TokenStream stream = makeStream("1");
        assertEquals("the character 1", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * The digits 1 and 2 are parsed as other character and nothing more.
     * 
     * @throws Exception in case of an error
     */
    public void test12() throws Exception {

        TokenStream stream = makeStream("12");
        assertEquals("the character 1", stream.get(fac, tokenizer).toString());
        assertEquals("the character 2", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * ...
     * 
     * @throws Exception in case of an error
     */
    public void testCaret1() throws Exception {

        TokenStream stream = makeStream("^1");
        assertEquals("superscript character ^", stream.get(fac, tokenizer)
            .toString());
        assertEquals("the character 1", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * ...
     * 
     * @throws Exception in case of an error
     */
    public void testCaretA() throws Exception {

        TokenStream stream = makeStream("^^41");
        assertEquals("the letter A", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * ...
     * 
     * @throws Exception in case of an error
     */
    public void testCaretA2() throws Exception {

        TokenStream stream = makeStream("^^A");
        assertEquals("the character ^^A", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * ...
     * 
     * @throws Exception in case of an error
     */
    public void testCaretA3() throws Exception {

        TokenStream stream = makeStream("^^A;");
        assertEquals("the character ^^A", stream.get(fac, tokenizer).toString());
        assertEquals("the character ;", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * ...
     * 
     * @throws Exception in case of an error
     */
    public void testCaretEnd() throws Exception {

        TokenStream stream = makeStream("^");
        assertEquals("superscript character ^", stream.get(fac, tokenizer)
            .toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * Test cr1
     * 
     * @throws Exception in case of an error
     */
    public void testCr1() throws Exception {

        TokenStream stream = makeStream("x\nx");
        assertEquals("the letter x", stream.get(fac, tokenizer).toString());
        assertEquals("blank space  ", stream.get(fac, tokenizer).toString());
        assertEquals("the letter x", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * The empty string does not contain any characters
     * 
     * @throws Exception in case of an error
     */
    public void testEmpty() throws Exception {

        TokenStream stream = makeStream("");
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * Test hex notation with '^^^^'
     * 
     * @throws Exception in case of an error
     */
    public void testHex1() throws Exception {

        TokenStream stream = makeStream("^^^^41 ");
        assertEquals("the letter A", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));

    }

    /**
     * Test hex notation with '^^^^'
     * 
     * @throws Exception in case of an error
     */
    public void testHex2() throws Exception {

        TokenStream stream = makeStream("^^^^fffe");
        Token t = stream.get(fac, tokenizer);
        if (t instanceof OtherToken) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * Test hex notation with '^^^^'
     * 
     * @throws Exception in case of an error
     */
    public void testHex3() throws Exception {

        TokenStream stream = makeStream("^^^^016e ");
        Token to = stream.get(fac, tokenizer);
        assertEquals(366, to.getChar().getCodePoint());

    }

    /**
     * ...
     * 
     * @throws Exception in case of an error
     */
    public void testIgnore() throws Exception {

        TokenStream stream = makeStream("\f.");
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals("the character .", token.toString());
        assertEquals("blank space  ", stream.get(fac, tokenizer).toString());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * ...
     * 
     * @throws Exception in case of an error
     */
    public void testLetter() throws Exception {

        TokenStream stream = makeStream("A");
        assertEquals("the letter A", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * ...
     * 
     * @throws Exception in case of an error
     */
    public void testMixed() throws Exception {

        TokenStream stream = makeStream("12 34");
        assertEquals("the character 1", stream.get(fac, tokenizer).toString());
        assertEquals("the character 2", stream.get(fac, tokenizer).toString());
        assertEquals("blank space  ", stream.get(fac, tokenizer).toString());
        assertEquals("the character 3", stream.get(fac, tokenizer).toString());
        assertEquals("the character 4", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * The character period and space in sequence are parsed into appropriate
     * tokens.
     * 
     * @throws Exception in case of an error
     */
    public void testSpace2() throws Exception {

        TokenStream stream = makeStream(". ");
        assertEquals("the character .", stream.get(fac, tokenizer).toString());
        assertEquals("blank space  ", stream.get(fac, tokenizer).toString());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * The character period and two spaces in sequence are parsed into
     * appropriate tokens. The two spaces are collapsed into one.
     * 
     * @throws Exception in case of an error
     */
    public void testSpace3() throws Exception {

        TokenStream stream = makeStream(".  ");
        assertEquals("the character .", stream.get(fac, tokenizer).toString());
        assertEquals("blank space  ", stream.get(fac, tokenizer).toString());
        assertNull(stream.get(fac, tokenizer));
    }

    /**
     * Test one Unicode name 'a'
     * 
     * @throws Exception in case of an error
     */
    public void testUnicodeName1() throws Exception {

        TokenStream stream = makeStream("^^^LATIN SMALL LETTER A;");
        assertEquals("the letter a", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));

    }

    /**
     * Test one Unicode name 'A'
     * 
     * @throws Exception in case of an error
     */
    public void testUnicodeName2() throws Exception {

        TokenStream stream = makeStream("^^^LATIN CAPITAL LETTER A;");
        assertEquals("the letter A", stream.get(fac, tokenizer).toString());
        Token token = stream.get(fac, tokenizer);
        assertNotNull(token);
        assertEquals(C32, token.getChar().getCodePoint());
        assertNull(stream.get(fac, tokenizer));

    }

    /**
     * Test one Unicode name 'error'
     * 
     * @throws Exception in case of an error
     */
    public void testUnicodeName3() throws Exception {

        TokenStream stream = makeStream("^^^LATIN ERROR LETTER A;");
        try {
            assertEquals("the letter A", stream.get(fac, tokenizer).toString());
            assertTrue(false);
        } catch (ScannerNoUnicodeNameException e) {
            assertTrue(true);
        }
    }

}
