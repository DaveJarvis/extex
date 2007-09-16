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

package org.extex.scanner.type.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.extex.core.UnicodeChar;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.api.exception.CatcodeVisitorException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.tokens.Tokens;
import org.junit.Test;

/**
 * This is a test suite for the token factory implementation.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TokenFactoryImplTest {

    /**
     * Create a code token and check it's values.
     * 
     * @param catcode the cat code
     * @param cc the character code
     * 
     * @return the token created
     * 
     * @throws CatcodeException in case of an error
     */
    private static Token checkCodeToken(Catcode catcode, char cc)
            throws CatcodeException {

        Token t = checkToken(catcode, cc);
        assertTrue(t instanceof CodeToken);
        assertEquals("namespace", ((CodeToken) t).getNamespace());
        return t;
    }

    /**
     * Create a token and check it's values.
     * 
     * @param catcode the cat code
     * @param cc the character code
     * 
     * @return the token created
     * 
     * @throws CatcodeException in case of an error
     */
    private static Token checkToken(Catcode catcode, char cc)
            throws CatcodeException {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(catcode, cc, "namespace");
        assertNotNull(t);
        assertEquals(catcode, t.getCatcode());
        assertEquals(UnicodeChar.get(cc), t.getChar());
        return t;
    }

    /**
     * Create a code token and check it's values.
     * 
     * @param catcode the cat code
     * @param uc the character code
     * 
     * @return the token created
     * 
     * @throws CatcodeException in case of an error
     */
    private static Token checkCodeToken(Catcode catcode, UnicodeChar uc)
            throws CatcodeException {

        Token t = checkToken(catcode, uc);
        assertTrue(t instanceof CodeToken);
        assertEquals("namespace", ((CodeToken) t).getNamespace());
        return t;
    }

    /**
     * Create a token and check it's values.
     * 
     * @param catcode the cat code
     * @param uc the character code
     * 
     * @return the token created
     * 
     * @throws CatcodeException in case of an error
     */
    private static Token checkToken(Catcode catcode, UnicodeChar uc)
            throws CatcodeException {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(catcode, uc, "namespace");
        assertNotNull(t);
        assertEquals(catcode, t.getCatcode());
        assertEquals(uc, t.getChar());
        return t;
    }

    /**
     */
    @Test
    public final void testTokenFactoryImpl() {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void testCreateTokenCatcodeIntString01() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(null, 'c', "namespace");
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeVisitorException.class)
    public final void testCreateTokenCatcodeIntString02() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(Catcode.LETTER, -1, "namespace");
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString1() throws Exception {

        checkCodeToken(Catcode.ACTIVE, 'c');
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString2() throws Exception {

        checkCodeToken(Catcode.ESCAPE, 'c');
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString3() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(Catcode.CR, 'c', "namespace");
        assertNotNull(t);
        assertEquals(Catcode.CR, t.getCatcode());
        assertTrue(t instanceof CrToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString4() throws Exception {

        Token t = checkToken(Catcode.LEFTBRACE, 'c');
        assertTrue(t instanceof LeftBraceToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString5() throws Exception {

        Token t = checkToken(Catcode.LETTER, 'c');
        assertTrue(t instanceof LetterToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString6() throws Exception {

        Token t = checkToken(Catcode.MACROPARAM, 'c');
        assertTrue(t instanceof MacroParamToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString7() throws Exception {

        Token t = checkToken(Catcode.MATHSHIFT, 'c');
        assertTrue(t instanceof MathShiftToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString8() throws Exception {

        Token t = checkToken(Catcode.OTHER, 'c');
        assertTrue(t instanceof OtherToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString9() throws Exception {

        Token t = checkToken(Catcode.RIGHTBRACE, 'c');
        assertTrue(t instanceof RightBraceToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString10() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(Catcode.SPACE, 'c', "namespace");
        assertNotNull(t);
        assertEquals(Catcode.SPACE, t.getCatcode());
        assertEquals(UnicodeChar.get(' '), t.getChar());
        assertTrue(t instanceof SpaceToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString11() throws Exception {

        Token t = checkToken(Catcode.SUBMARK, 'c');
        assertTrue(t instanceof SubMarkToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString12() throws Exception {

        Token t = checkToken(Catcode.SUPMARK, 'c');
        assertTrue(t instanceof SupMarkToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeIntString13() throws Exception {

        Token t = checkToken(Catcode.TABMARK, 'c');
        assertTrue(t instanceof TabMarkToken);
    }

    /**
     * There is only one SPACE token.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenSpace1() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t1 = tf.createToken(Catcode.SPACE, 'c', "namespace");
        assertNotNull(t1);
        Token t2 = tf.createToken(Catcode.SPACE, 'd', "namespace2");
        assertNotNull(t2);
        assertTrue(t1 == t2);
    }

    /**
     * There is only one SPACE token.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenSpace2() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t1 = tf.createToken(Catcode.SPACE, 'c', "namespace");
        assertNotNull(t1);
        Token t2 =
                tf.createToken(Catcode.SPACE, UnicodeChar.get('x'), "X",
                    "namespace2");
        assertNotNull(t2);
        assertTrue(t1 == t2);
    }

    /**
     * There is no IGNORE token.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenIgnore1() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        assertNull(tf.createToken(Catcode.IGNORE, 'c', "namespace"));
    }

    /**
     * There is no INVALID token.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenInvalid1() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        assertNull(tf.createToken(Catcode.INVALID, 'c', "namespace"));
    }

    /**
     * There is no COMMENT token.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenComment1() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        assertNull(tf.createToken(Catcode.COMMENT, 'c', "namespace"));
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void testCreateTokenCatcodeUnicodecharString01()
            throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(null, UnicodeChar.get('c'), "namespace");
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public final void testCreateTokenCatcodeUnicodecharString02()
            throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(Catcode.ACTIVE, null, "namespace");
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void testCreateTokenCatcodeUnicodecharString03()
            throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(null, null, "namespace");
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public final void testCreateTokenCatcodeUnicodecharString04()
            throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(Catcode.ESCAPE, null, "namespace");
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void testCreateTokenCatcodeUnicodecharString0()
            throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(null, UnicodeChar.get('c'), "namespace");
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString1()
            throws Exception {

        Token t = checkCodeToken(Catcode.ACTIVE, UnicodeChar.get('c'));
        assertTrue(t instanceof ActiveCharacterToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString2()
            throws Exception {

        Token t = checkCodeToken(Catcode.ESCAPE, UnicodeChar.get('c'));
        assertTrue(t instanceof ControlSequenceToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString2b()
            throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        Token t1 =
                tf.createToken(Catcode.ESCAPE, UnicodeChar.get('c'),
                    "namespace");
        Token t2 =
                tf.createToken(Catcode.ESCAPE, UnicodeChar.get('c'),
                    "namespace");
        assertTrue(t1 == t2);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString3()
            throws Exception {

        Catcode catcode = Catcode.CR;
        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(catcode, UnicodeChar.get('c'), "namespace");
        assertNotNull(t);
        assertEquals(catcode, t.getCatcode());
        assertTrue(t instanceof CrToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString4()
            throws Exception {

        Token t = checkToken(Catcode.LEFTBRACE, UnicodeChar.get('c'));
        assertTrue(t instanceof LeftBraceToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString5()
            throws Exception {

        Token t = checkToken(Catcode.LETTER, UnicodeChar.get('c'));
        assertTrue(t instanceof LetterToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString6()
            throws Exception {

        Token t = checkToken(Catcode.MACROPARAM, UnicodeChar.get('c'));
        assertTrue(t instanceof MacroParamToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString7()
            throws Exception {

        Token t = checkToken(Catcode.MATHSHIFT, UnicodeChar.get('c'));
        assertTrue(t instanceof MathShiftToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString8()
            throws Exception {

        Token t = checkToken(Catcode.OTHER, UnicodeChar.get('c'));
        assertTrue(t instanceof OtherToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString9()
            throws Exception {

        Token t = checkToken(Catcode.RIGHTBRACE, UnicodeChar.get('c'));
        assertTrue(t instanceof RightBraceToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString10()
            throws Exception {

        Catcode catcode = Catcode.SPACE;
        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(catcode, UnicodeChar.get('c'), "namespace");
        assertNotNull(t);
        assertEquals(catcode, t.getCatcode());
        assertEquals(UnicodeChar.get(' '), t.getChar());
        assertTrue(t instanceof SpaceToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString11()
            throws Exception {

        Token t = checkToken(Catcode.SUBMARK, UnicodeChar.get('c'));
        assertTrue(t instanceof SubMarkToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString12()
            throws Exception {

        Token t = checkToken(Catcode.SUPMARK, UnicodeChar.get('c'));
        assertTrue(t instanceof SupMarkToken);
    }

    /**
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodecharString13()
            throws Exception {

        Token t = checkToken(Catcode.TABMARK, UnicodeChar.get('c'));
        assertTrue(t instanceof TabMarkToken);
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#createToken(org.extex.scanner.type.Catcode, org.extex.core.UnicodeChar, java.lang.String, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodeCharStringString1()
            throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(Catcode.ACTIVE, UnicodeChar.get('c'), //
            "abc", "namespace");
        assertNotNull(t);
        assertTrue(t instanceof ActiveCharacterToken);
        assertEquals(Catcode.ACTIVE, t.getCatcode());
        assertEquals(UnicodeChar.get('c'), t.getChar());
        assertEquals("namespace", ((ActiveCharacterToken) t).getNamespace());
        assertEquals("", ((ActiveCharacterToken) t).getName());
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#createToken(org.extex.scanner.type.Catcode, org.extex.core.UnicodeChar, java.lang.String, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodeCharStringString21()
            throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(Catcode.ACTIVE, null, "c", "namespace");
        assertNotNull(t);
        assertTrue(t instanceof ActiveCharacterToken);
        assertEquals(Catcode.ACTIVE, t.getCatcode());
        assertEquals(UnicodeChar.get('c'), t.getChar());
        assertEquals("namespace", ((ActiveCharacterToken) t).getNamespace());
        assertEquals("", ((ActiveCharacterToken) t).getName());
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#createToken(org.extex.scanner.type.Catcode, org.extex.core.UnicodeChar, java.lang.String, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public final void testCreateTokenCatcodeUnicodeCharStringString22()
            throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(Catcode.ACTIVE, null, "cc", "namespace");
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#createToken(org.extex.scanner.type.Catcode, org.extex.core.UnicodeChar, java.lang.String, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public final void testCreateTokenCatcodeUnicodeCharStringString23()
            throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(Catcode.ACTIVE, null, null, "namespace");
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#createToken(org.extex.scanner.type.Catcode, org.extex.core.UnicodeChar, java.lang.String, java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCreateTokenCatcodeUnicodeCharStringString2()
            throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(Catcode.ESCAPE, UnicodeChar.get('c'), //
            "abc", "namespace");
        assertNotNull(t);
        assertTrue(t instanceof ControlSequenceToken);
        assertEquals(Catcode.ESCAPE, t.getCatcode());
        assertEquals(UnicodeChar.get('c'), t.getChar());
        assertEquals("namespace", ((ControlSequenceToken) t).getNamespace());
        assertEquals("abc", ((ControlSequenceToken) t).getName());
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#toTokens(java.lang.CharSequence)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testToTokensCharSequence0() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens(null);
        assertEquals(0, tokens.length());
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#toTokens(java.lang.CharSequence)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testToTokensCharSequence1() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens("");
        assertEquals(0, tokens.length());
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#toTokens(java.lang.CharSequence)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testToTokensCharSequence2() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens("abc");
        assertEquals(3, tokens.length());
        assertEquals("abc", tokens.toText());
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#toTokens(java.lang.CharSequence)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testToTokensCharSequence3() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens("a b");
        assertEquals(3, tokens.length());
        assertEquals("a b", tokens.toText());
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#toTokens(long)}.
     * 
     * @throws CatcodeException in case of an error
     */
    @Test
    public final void testToTokensLong1() throws CatcodeException {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens(0);
        assertEquals(1, tokens.length());
        assertEquals("0", tokens.toText());
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#toTokens(long)}.
     * 
     * @throws CatcodeException in case of an error
     */
    @Test
    public final void testToTokensLong2() throws CatcodeException {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens(1);
        assertEquals(1, tokens.length());
        assertEquals("1", tokens.toText());
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#toTokens(long)}.
     * 
     * @throws CatcodeException in case of an error
     */
    @Test
    public final void testToTokensLong3() throws CatcodeException {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens(-1);
        assertEquals(2, tokens.length());
        assertEquals("-1", tokens.toText());
    }

    /**
     * Test method for
     * {@link org.extex.scanner.type.token.TokenFactoryImpl#toTokens(long)}.
     * 
     * @throws CatcodeException in case of an error
     */
    @Test
    public final void testToTokensLong4() throws CatcodeException {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens(123);
        assertEquals(3, tokens.length());
        assertEquals("123", tokens.toText());
    }

}
