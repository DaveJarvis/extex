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

package org.extex.scanner.base;

import junit.framework.TestCase;

import org.extex.core.Locator;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;

/**
 * Test suite for TokenStreamBaseImpl.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TokenStreamBaseImplTest extends TestCase {

    /**
     * Creates a new object.
     *
     * @param name the name
     */
    public TokenStreamBaseImplTest(String name) {

        super(name);
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testTokenStreamBaseImplBooleanTokens() throws Exception {

        Token token = TokenStreamStringImplTest.FACTORY.createToken(//
            Catcode.LETTER, 'a', "");
        TokenStreamBaseImpl stream =
                new TokenStreamBaseImpl(true, new Tokens(token));
        assertEquals(token, stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
        assertNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     */
    public final void testCloseFileStream1() {

        assertTrue(new TokenStreamBaseImpl(true).closeFileStream());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testCloseFileStream2() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(true);
        stream.put(TokenStreamStringImplTest.FACTORY.createToken(
            Catcode.LETTER, 'a', ""));
        assertTrue(stream.closeFileStream());
        assertNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     */
    public final void testCloseFileStream11() {

        assertFalse(new TokenStreamBaseImpl(false).closeFileStream());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testCloseFileStream12() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(false);
        stream.put(TokenStreamStringImplTest.FACTORY.createToken(
            Catcode.LETTER, 'a', ""));
        assertFalse(stream.closeFileStream());
        assertNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testGet1() throws Exception {

        assertNull(new TokenStreamBaseImpl(false).get(
            TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     */
    public final void testGetLocator() {

        Locator locator = new TokenStreamBaseImpl(false).getLocator();
        assertNotNull(locator);
        assertNull(locator.getLine());
        assertNull(locator.getCause());
        assertEquals(0, locator.getLineNumber());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testIsEof1() throws Exception {

        assertTrue(new TokenStreamBaseImpl(false).isEof());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testIsEof2() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(false);
        stream.put(TokenStreamStringImplTest.FACTORY.createToken(
            Catcode.LETTER, 'a', ""));
        assertFalse(stream.isEof());
        assertNotNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
        assertTrue(stream.isEof());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testIsEol1() throws Exception {

        assertTrue(new TokenStreamBaseImpl(true).isEol());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testIsEol2() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(true);
        stream.put(TokenStreamStringImplTest.FACTORY.createToken(
            Catcode.LETTER, 'a', ""));
        assertFalse(stream.isEol());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     */
    public final void testIsFileStream1() {

        assertTrue(new TokenStreamBaseImpl(true).isFileStream());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     */
    public final void testIsFileStream2() {

        assertFalse(new TokenStreamBaseImpl(false).isFileStream());
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testPut1() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(false);
        Token token = TokenStreamStringImplTest.FACTORY.createToken(//
            Catcode.LETTER, 'a', "");
        stream.put(token);
        Token token2 =
                stream.get(TokenStreamStringImplTest.FACTORY,
                    TokenStreamStringImplTest.TOKENIZER);
        assertNotNull(token2);
        assertEquals(token, token2);
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testPut2() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(false);
        Token token = TokenStreamStringImplTest.FACTORY.createToken(//
            Catcode.LETTER, 'a', "");
        Token t2 = TokenStreamStringImplTest.FACTORY.createToken(//
            Catcode.LETTER, 'b', "");
        stream.put(token);
        stream.put(t2);
        assertNotNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
        Token token2 =
                stream.get(TokenStreamStringImplTest.FACTORY,
                    TokenStreamStringImplTest.TOKENIZER);
        assertNotNull(token2);
        assertEquals(token, token2);
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testSkipSpaces1() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(false);
        Token token = TokenStreamStringImplTest.FACTORY.createToken(//
            Catcode.LETTER, 'a', "");
        stream.put(token);
        stream.skipSpaces();
        assertEquals(token, stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
        assertNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
    }

    /**
     * <testcase>
     *  ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public final void testSkipSpaces2() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(false);
        Token token = TokenStreamStringImplTest.FACTORY.createToken(//
            Catcode.LETTER, 'a', "");
        Token space = TokenStreamStringImplTest.FACTORY.createToken(//
            Catcode.SPACE, ' ', "");
        stream.put(token);
        stream.put(space);
        stream.skipSpaces();
        assertEquals(token, stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
        assertNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
    }

}
