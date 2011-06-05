/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.extex.core.Locator;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.junit.Test;

/**
 * Test suite for TokenStreamBaseImpl.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TokenStreamBaseImplTest {

    /**
     * Creates a new object.
     */
    public TokenStreamBaseImplTest() {

    }

    /**
     * <testcase> This test case validates that closeFileStream() succeeds on an
     * empty file stream. </testcase>
     */
    @Test
    public void testCloseFileStream1() {

        assertTrue(new TokenStreamBaseImpl(true).closeFileStream());
    }

    /**
     * <testcase> This test case validates that closeFileStream() fails on an
     * empty non-file stream. </testcase>
     */
    @Test
    public void testCloseFileStream11() {

        assertFalse(new TokenStreamBaseImpl(false).closeFileStream());
    }

    /**
     * <testcase> This test case validates that closeFileStream() discards any
     * remaining characters. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCloseFileStream12() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(false);
        stream.put(TokenStreamStringImplTest.FACTORY.createToken(
            Catcode.LETTER, 'a', ""));
        assertFalse(stream.closeFileStream());
        assertNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
    }

    /**
     * <testcase> This test case validates that closeFileStream() discards any
     * remaining characters. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCloseFileStream2() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(true);
        stream.put(TokenStreamStringImplTest.FACTORY.createToken(
            Catcode.LETTER, 'a', ""));
        assertTrue(stream.closeFileStream());
        assertNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
    }

    /**
     * <testcase> This test case validates that get() returns <code>null</code>
     * on en empty stream. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGet1() throws Exception {

        assertNull(new TokenStreamBaseImpl(false).get(
            TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
    }

    /**
     * <testcase> This test case validates that getLocator() returns an locator
     * pointing initially to the line 0. </testcase>
     */
    @Test
    public void testGetLocator() {

        Locator locator = new TokenStreamBaseImpl(false).getLocator();
        assertNotNull(locator);
        assertNull(locator.getLine());
        assertNull(locator.getCause());
        assertEquals(0, locator.getLineNumber());
    }

    /**
     * <testcase> This test case validates that an empty stream reports isEof()
     * as <code>true</code>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIsEof1() throws Exception {

        assertTrue(new TokenStreamBaseImpl(false).isEof());
    }

    /**
     * <testcase> This test case validates that a non-empty stream reports
     * isEof() as <code>false</code> which changes when the token has been
     * retrieved. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIsEof2() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(false);
        stream.put(TokenStreamStringImplTest.FACTORY.createToken(
            Catcode.LETTER, 'a', ""));
        assertFalse(stream.isEof());
        assertNotNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
        assertTrue(stream.isEof());
    }

    /**
     * <testcase> This test case validates that an empty stream reports isEol()
     * as <code>true</code>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIsEol1() throws Exception {

        assertTrue(new TokenStreamBaseImpl(true).isEol());
    }

    /**
     * <testcase> This test case validates that a non-empty stream reports
     * isEol() as <code>false</code> which changes when the token has been
     * retrieved. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIsEol2() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(true);
        stream.put(TokenStreamStringImplTest.FACTORY.createToken(
            Catcode.LETTER, 'a', ""));
        assertFalse(stream.isEol());
    }

    /**
     * <testcase> This test case validates that the boolean constructor flag
     * indicates a file stream (positive case). </testcase>
     */
    @Test
    public void testIsFileStream1() {

        assertTrue(new TokenStreamBaseImpl(true).isFileStream());
    }

    /**
     * <testcase> This test case validates that the boolean constructor flag
     * indicates a file stream (negative case). </testcase>
     */
    @Test
    public void testIsFileStream2() {

        assertFalse(new TokenStreamBaseImpl(false).isFileStream());
    }

    /**
     * <testcase> This test case validates that put() puts a single token back
     * into the stream. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPut1() throws Exception {

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
     * <testcase> This test case validates that put() puts several tokens back
     * into the stream. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPut2() throws Exception {

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
     * <testcase> This test case validates that skipSpaces() does not harm a
     * letter token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSkipSpaces1() throws Exception {

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
     * <testcase> This test case validates that skipSpaces() forces the deletion
     * of a space token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSkipSpaces2() throws Exception {

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

    /**
     * <testcase> This test case validates that skipSpaces() forces the deletion
     * of multiple space token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSkipSpaces3() throws Exception {

        TokenStreamBaseImpl stream = new TokenStreamBaseImpl(false);
        Token token = TokenStreamStringImplTest.FACTORY.createToken(//
            Catcode.LETTER, 'a', "");
        Token space = TokenStreamStringImplTest.FACTORY.createToken(//
            Catcode.SPACE, ' ', "");
        stream.put(token);
        stream.put(space);
        stream.put(space);
        stream.put(space);
        stream.skipSpaces();
        assertEquals(token, stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
        assertNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
    }

    /**
     * <testcase> This test case validates that the constructor injects a token
     * list with one element. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTokenStreamBaseImplBooleanTokens() throws Exception {

        Token token = TokenStreamStringImplTest.FACTORY.createToken(//
            Catcode.LETTER, 'a', "");
        TokenStreamBaseImpl stream =
                new TokenStreamBaseImpl(true, new Tokens(token));
        assertEquals(token, stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
        assertNull(stream.get(TokenStreamStringImplTest.FACTORY,
            TokenStreamStringImplTest.TOKENIZER));
    }

}
