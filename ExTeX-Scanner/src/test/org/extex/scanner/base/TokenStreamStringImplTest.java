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

package org.extex.scanner.base;

import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;

import org.extex.interpreter.Tokenizer;
import org.extex.scanner.TokenStream;
import org.extex.scanner.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.extex.type.Locator;
import org.extex.type.UnicodeChar;

/**
 * Test cases for the string implementation of a token stream.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class TokenStreamStringImplTest extends TestCase {

    /**
     * The constant <tt>SPACE</tt> contains the code point of space.
     */
    private static final int SPACE = 32;

    /**
     * The field <tt>factory</tt> contains the token factory to use.
     */
    protected static final TokenFactory FACTORY = new TokenFactoryImpl();

    /**
     * The field <tt>tokenizer</tt> contains the tokenizer to use for
     * categorizing characters.
     */
    protected static final Tokenizer TOKENIZER = new Tokenizer() {

        /**
         * Getter for the category code of a character.
         *
         * @param c the Unicode character to analyze
         *
         * @return the category code of a character
         *
         * @see org.extex.interpreter.Tokenizer#getCatcode(
         *      org.extex.type.UnicodeChar)
         */
        public Catcode getCatcode(final UnicodeChar c) {

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
                case '\\':
                    return Catcode.ESCAPE;
                case '~':
                    return Catcode.ACTIVE;
                case '\r':
                case '\n':
                    return Catcode.CR;
                case '\t':
                case ' ':
                    return Catcode.SPACE;
                case '\0':
                case '\f':
                    return Catcode.IGNORE;
                case '\b':
                    return Catcode.INVALID;
                default:
                    return Catcode.OTHER;
            }
        }

        /**
         * Getter for the name space.
         *
         * @return the name space
         *
         * @see org.extex.interpreter.Tokenizer#getNamespace()
         */
        public String getNamespace() {

            return "";
        }

    };;

    /**
     * Command line interface.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(TokenStreamStringImplTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param name the name
     */
    public TokenStreamStringImplTest(final String name) {

        super(name);
    }

    /**
     * Create a stream of tokens fed from a string.
     *
     * @param line the input string
     * @return the new token stream
     * @throws IOException in case of an error
     */
    protected TokenStream makeStream(final String line) throws IOException {

        return new TokenStreamImpl(null, null, new StringReader(line),
            Boolean.FALSE, "test");
    }

    /**
     * <testcase>
     *  The digit 1 is parsed as other character and nothing more.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        TokenStream stream = makeStream("1");
        assertEquals("the character 1", stream.get(FACTORY, TOKENIZER)
            .toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  The digits 1 and 2 are parsed as other character and nothing more.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test12() throws Exception {

        TokenStream stream = makeStream("12");
        assertEquals("the character 1", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("the character 2", stream.get(FACTORY, TOKENIZER)
            .toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCaret1() throws Exception {

        TokenStream stream = makeStream("^1");
        assertEquals("superscript character ^", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("the character 1", stream.get(FACTORY, TOKENIZER)
            .toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCaretA() throws Exception {

        TokenStream stream = makeStream("^^41");
        assertEquals("the letter A", stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCaretO() throws Exception {

        TokenStream stream = makeStream("^^4f");
        assertEquals("the letter O", stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCaretA2() throws Exception {

        TokenStream stream = makeStream("^^A");
        assertEquals("the character ^^1", stream.get(FACTORY, TOKENIZER)
            .toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCaretA3() throws Exception {

        TokenStream stream = makeStream("^^A;");
        assertEquals("the character ^^1", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("the character ;", stream.get(FACTORY, TOKENIZER)
            .toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCaretA4() throws Exception {

        TokenStream stream = makeStream("^^2;");
        assertEquals("the character ^^2", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("the character ;", stream.get(FACTORY, TOKENIZER)
            .toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCaretA5() throws Exception {

        TokenStream stream = makeStream("^^ ");
        assertEquals("the character `", stream.get(FACTORY, TOKENIZER)
            .toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCaretEnd() throws Exception {

        TokenStream stream = makeStream("^");
        assertEquals("superscript character ^", stream.get(FACTORY, TOKENIZER)
            .toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCr1() throws Exception {

        TokenStream stream = makeStream("x\nx");
        assertEquals("the letter x", stream.get(FACTORY, TOKENIZER).toString());
        assertEquals("blank space  ", stream.get(FACTORY, TOKENIZER).toString());
        assertEquals("the letter x", stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void ___testCr2() throws Exception {

        TokenStream stream = makeStream("x\n\n");
        assertEquals("the letter x", stream.get(FACTORY, TOKENIZER).toString());
        Token t = stream.get(FACTORY, TOKENIZER);
        assertEquals(SPACE, t.getChar().getCodePoint());
        assertNotNull(t);
        assertEquals("the control sequence \\par", t.toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void ___testCr3() throws Exception {

        TokenStream stream = makeStream("\naaa\n  x");
        assertEquals("the control sequence \\par", stream.get(FACTORY,
            TOKENIZER).toString());
        assertEquals("the letter x", stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void ___testCr4() throws Exception {

        TokenStream stream = makeStream("\n\nx");
        assertEquals("the control sequence \\par", stream.get(FACTORY,
            TOKENIZER).toString());
        assertEquals("the control sequence \\par", stream.get(FACTORY,
            TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  The empty string does not contain any characters
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEmpty() throws Exception {

        TokenStream stream = makeStream("");
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testIgnore() throws Exception {

        TokenStream stream = makeStream("\f.");
        assertEquals("the character .", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("blank space  ", stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInvalid() throws Exception {

        TokenStream stream = makeStream("\b.");
        try {
            stream.get(FACTORY, TOKENIZER);
            assertFalse(true);
        } catch (ScannerException e) {
            assertTrue(true);
        }
        assertEquals("the character .", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("blank space  ", stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetter() throws Exception {

        TokenStream stream = makeStream("A");
        assertEquals("the letter A", stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testHash() throws Exception {

        TokenStream stream = makeStream("#");
        assertEquals("macro parameter character #", //
            stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTab() throws Exception {

        TokenStream stream = makeStream("&");
        assertEquals("alignment tab character &", //
            stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testDollar() throws Exception {

        TokenStream stream = makeStream("$");
        assertEquals("math shift character $", //
            stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testUnderscore() throws Exception {

        TokenStream stream = makeStream("_");
        assertEquals("subscript character _", //
            stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLeftBrace() throws Exception {

        TokenStream stream = makeStream("{");
        assertEquals("begin-group character {", //
            stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testRightBrace() throws Exception {

        TokenStream stream = makeStream("}");
        assertEquals("end-group character }", //
            stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testMixed() throws Exception {

        TokenStream stream = makeStream("12 34");
        assertEquals("the character 1", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("the character 2", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("blank space  ", stream.get(FACTORY, TOKENIZER).toString());
        assertEquals("the character 3", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("the character 4", stream.get(FACTORY, TOKENIZER)
            .toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  A single space at the beginning of the processing is skipped
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testSpace() throws Exception {

        TokenStream stream = makeStream(" .");
        assertEquals("the character .", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("blank space  ", stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  The character period and space in sequence are parsed into appropriate
     *  tokens.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testSpace2() throws Exception {

        TokenStream stream = makeStream(". ");
        assertEquals("the character .", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("blank space  ", stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  The character period and two spaces in sequence are parsed into
     *  appropriate tokens. The two spaces are collapsed into one.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testSpace3() throws Exception {

        TokenStream stream = makeStream(".  ");
        assertEquals("the character .", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("blank space  ", stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  Two spaces at the beginning are ignored.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testSpaces() throws Exception {

        TokenStream stream = makeStream("  .");
        assertEquals("the character .", stream.get(FACTORY, TOKENIZER)
            .toString());
        assertEquals("blank space  ", stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEscape0() throws Exception {

        TokenStream stream = makeStream("\\abc");
        assertEquals("the control sequence \\abc", //
            stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEscape1() throws Exception {

        TokenStream stream = makeStream("\\abc ");
        assertEquals("the control sequence \\abc", //
            stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEscape2() throws Exception {

        TokenStream stream = makeStream("\\");
        assertEquals("the control sequence \\", //
            stream.get(FACTORY, TOKENIZER).toString());
        assertEquals("the control sequence \\par", // TODO: is this correct?
            stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNull(token);
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEscape3() throws Exception {

        TokenStream stream = makeStream("\\23");
        assertEquals("the control sequence \\2", //
            stream.get(FACTORY, TOKENIZER).toString());
        assertEquals("the character 3", //
            stream.get(FACTORY, TOKENIZER).toString());
        assertEquals("blank space  ", stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEscape4() throws Exception {

        TokenStream stream = makeStream("\\a^^41c ");
        assertEquals("the control sequence \\aAc", //
            stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEscape5() throws Exception {

        TokenStream stream = makeStream("\\^^41c ");
        assertEquals("the control sequence \\Ac", //
            stream.get(FACTORY, TOKENIZER).toString());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testActive() throws Exception {

        TokenStream stream = makeStream("~");
        assertEquals("the active character ~", //
            stream.get(FACTORY, TOKENIZER).toString());
        Token token = stream.get(FACTORY, TOKENIZER);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testComment() throws Exception {

        TokenStream stream = makeStream("%abc");
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testIsEof1() throws Exception {

        TokenStream stream = makeStream(". ");
        assertNotNull(stream.get(FACTORY, TOKENIZER));
        assertFalse("something left", stream.isEof());
        assertNotNull(stream.get(FACTORY, TOKENIZER));
        assertTrue("nothing left", stream.isEof());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testIsEof2() throws Exception {

        TokenStream stream = makeStream("\\ab cd");
        assertNotNull(stream.get(FACTORY, TOKENIZER));
        assertFalse("something left", stream.isEof());
        assertNotNull(stream.get(FACTORY, TOKENIZER));
        assertFalse("more left", stream.isEof());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLocator() throws Exception {

        TokenStream stream = makeStream("abc");
        Locator locator = stream.getLocator();
        assertNotNull(locator);
        assertEquals("test:0", locator.toString());
        assertNotNull(stream.get(FACTORY, TOKENIZER));
        assertEquals("test:0", locator.toString());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCR() throws Exception {

        TokenStream stream = makeStream("a\nc");
        assertEquals("the letter a", //
            stream.get(FACTORY, TOKENIZER).toString());
        Token t = stream.get(FACTORY, TOKENIZER);
        assertEquals("blank space  ", t.toString());
        assertEquals("the letter c", //
            stream.get(FACTORY, TOKENIZER).toString());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testReread() throws Exception {

        TokenStream stream = makeStream("");
        assertNull(stream.get(FACTORY, TOKENIZER));
        assertNull(stream.get(FACTORY, TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testIsEol1() throws Exception {

        TokenStream stream = makeStream("abc");
        assertNotNull(stream.get(FACTORY, TOKENIZER));
        assertFalse(stream.isEol());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testToString() throws Exception {

        TokenStream stream = makeStream("abc");
        assertEquals("test:0[1]:", stream.toString());
    }

}