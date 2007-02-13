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

package org.extex.scanner.stream.impl;

import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;

import org.extex.interpreter.Tokenizer;
import org.extex.scanner.TokenStream;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.extex.type.UnicodeChar;

/**
 * Test cases for the string implementation of a token stream.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class TokenStreamStringImplTest extends TestCase {

    /**
     * The constant <tt>SPACE</tt> contains the ...
     */
    private static final int SPACE = 32;

    /**
     * The field <tt>factory</tt> contains the token factory to use.
     */
    private static TokenFactory factory;

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
    private TokenStream makeStream(final String line) throws IOException {

        return new TokenStreamImpl(null, null, new StringReader(line),
            Boolean.FALSE, "");
    }

    /**
     * Set up the tests.
     * @throws Exception in case of an error
     *
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

        super.setUp();
        factory = new TokenFactoryImpl();
        tokenizer = new Tokenizer() {

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
                    case '\t':
                    case '\r':
                    case '\n':
                    case ' ':
                        return Catcode.SPACE;
                    case '\0':
                    case '\f':
                        return Catcode.IGNORE;
                    default:
                        return Catcode.OTHER;
                }
            }

            public String getNamespace() {

                return "";
            }

        };
    }

    /**
     * Tear down the test suite.
     *
     * @throws Exception in case of an error
     *
     * @see junit.framework.TestCase#tearDown()
     */
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
        assertEquals("the character 1", stream.get(factory, tokenizer).toString());
        Token token = stream.get(factory, tokenizer);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * The digits 1 and 2 are parsed as other character and nothing more.
     *
     * @throws Exception in case of an error
     */
    public void test12() throws Exception {

        TokenStream stream = makeStream("12");
        assertEquals("the character 1", stream.get(factory, tokenizer).toString());
        assertEquals("the character 2", stream.get(factory, tokenizer).toString());
        Token token = stream.get(factory, tokenizer);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void testCaret1() throws Exception {

        TokenStream stream = makeStream("^1");
        assertEquals("superscript character ^", stream.get(factory, tokenizer)
            .toString());
        assertEquals("the character 1", stream.get(factory, tokenizer).toString());
        Token token = stream.get(factory, tokenizer);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void testCaretA() throws Exception {

        TokenStream stream = makeStream("^^41");
        assertEquals("the letter A", stream.get(factory, tokenizer).toString());
        Token token = stream.get(factory, tokenizer);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void testCaretA2() throws Exception {

        TokenStream stream = makeStream("^^A");
        assertEquals("the character ^^1", stream.get(factory, tokenizer).toString());
        Token token = stream.get(factory, tokenizer);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void testCaretA3() throws Exception {

        TokenStream stream = makeStream("^^A;");
        assertEquals("the character ^^1", stream.get(factory, tokenizer).toString());
        assertEquals("the character ;", stream.get(factory, tokenizer).toString());
        Token token = stream.get(factory, tokenizer);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void testCaretEnd() throws Exception {

        TokenStream stream = makeStream("^");
        assertEquals("superscript character ^", stream.get(factory, tokenizer)
            .toString());
        Token token = stream.get(factory, tokenizer);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void testCr1() throws Exception {

        TokenStream stream = makeStream("x\nx");
        assertEquals("the letter x", stream.get(factory, tokenizer).toString());
        assertEquals("blank space  ", stream.get(factory, tokenizer).toString());
        assertEquals("the letter x", stream.get(factory, tokenizer).toString());
        Token token = stream.get(factory, tokenizer);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void ___testCr2() throws Exception {

        TokenStream stream = makeStream("x\n\n");
        assertEquals("the letter x", stream.get(factory, tokenizer).toString());
        Token t = stream.get(factory, tokenizer);
        assertEquals(SPACE, t.getChar().getCodePoint());
        assertNotNull(t);
        assertEquals("the control sequence \\par", t.toString());
        Token token = stream.get(factory, tokenizer);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void ___testCr3() throws Exception {

        TokenStream stream = makeStream("\naaa\n  x");
        assertEquals("the control sequence \\par", stream.get(factory, tokenizer)
            .toString());
        assertEquals("the letter x", stream.get(factory, tokenizer).toString());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void ___testCr4() throws Exception {

        TokenStream stream = makeStream("\n\nx");
        assertEquals("the control sequence \\par", stream.get(factory, tokenizer)
            .toString());
        assertEquals("the control sequence \\par", stream.get(factory, tokenizer)
            .toString());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * The empty string does not contain any characters
     *
     * @throws Exception in case of an error
     */
    public void testEmpty() throws Exception {

        TokenStream stream = makeStream("");
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void testIgnore() throws Exception {

        TokenStream stream = makeStream("\f.");
        assertEquals("the character .", stream.get(factory, tokenizer).toString());
        assertEquals("blank space  ", stream.get(factory, tokenizer).toString());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void testLetter() throws Exception {

        TokenStream stream = makeStream("A");
        assertEquals("the letter A", stream.get(factory, tokenizer).toString());
        Token token = stream.get(factory, tokenizer);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * ...
     *
     * @throws Exception in case of an error
     */
    public void testMixed() throws Exception {

        TokenStream stream = makeStream("12 34");
        assertEquals("the character 1", stream.get(factory, tokenizer).toString());
        assertEquals("the character 2", stream.get(factory, tokenizer).toString());
        assertEquals("blank space  ", stream.get(factory, tokenizer).toString());
        assertEquals("the character 3", stream.get(factory, tokenizer).toString());
        assertEquals("the character 4", stream.get(factory, tokenizer).toString());
        Token token = stream.get(factory, tokenizer);
        assertNotNull(token);
        assertEquals(SPACE, token.getChar().getCodePoint());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * A single space at the beginning of the processing is skipped
     *
     * @throws Exception in case of an error
     */
    public void testSpace() throws Exception {

        TokenStream stream = makeStream(" .");
        assertEquals("the character .", stream.get(factory, tokenizer).toString());
        assertEquals("blank space  ", stream.get(factory, tokenizer).toString());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * The character period and space in sequence are parsed into appropriate
     * tokens.
     *
     * @throws Exception in case of an error
     */
    public void testSpace2() throws Exception {

        TokenStream stream = makeStream(". ");
        assertEquals("the character .", stream.get(factory, tokenizer).toString());
        assertEquals("blank space  ", stream.get(factory, tokenizer).toString());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * The character period and two spaces in sequence are parsed into
     * appropriate tokens. The two spaces are collapsed into one.
     *
     * @throws Exception in case of an error
     */
    public void testSpace3() throws Exception {

        TokenStream stream = makeStream(".  ");
        assertEquals("the character .", stream.get(factory, tokenizer).toString());
        assertEquals("blank space  ", stream.get(factory, tokenizer).toString());
        assertNull(stream.get(factory, tokenizer));
    }

    /**
     * Two spaces at the beginning are ignored.
     *
     * @throws Exception in case of an error
     */
    public void testSpaces() throws Exception {

        TokenStream stream = makeStream("  .");
        assertEquals("the character .", stream.get(factory, tokenizer).toString());
        assertEquals("blank space  ", stream.get(factory, tokenizer).toString());
        assertNull(stream.get(factory, tokenizer));
    }

}
