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
import org.extex.scanner.api.exception.CatcodeWrongLengthException;
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
     * Create a token and check it's values.
     * 
     * @param catcode the cat code
     * @param s the string
     * 
     * @return the token created
     * 
     * @throws CatcodeException in case of an error
     */
    private static Token checkToken(Catcode catcode, String s)
            throws CatcodeException {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(catcode, null, s, "namespace");
        assertNotNull(t);
        assertEquals(catcode, t.getCatcode());
        assertEquals(s.charAt(0), t.getChar().getCodePoint());
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
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testActive01() throws Exception {

        Token t = checkCodeToken(Catcode.ACTIVE, UnicodeChar.get('c'));
        assertTrue(t instanceof ActiveCharacterToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testActive02() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(Catcode.ACTIVE, null, "namespace");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testActive03() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(Catcode.ACTIVE, UnicodeChar.get('c'),
            "abc", "namespace");
        assertNotNull(t);
        assertTrue(t instanceof ActiveCharacterToken);
        assertEquals(Catcode.ACTIVE, t.getCatcode());
        assertEquals(UnicodeChar.get('c'), t.getChar());
        assertEquals("namespace", ((ActiveCharacterToken) t).getNamespace());
        assertEquals("", ((ActiveCharacterToken) t).getName());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testActive04() throws Exception {

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
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testActive05() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(Catcode.ACTIVE, null, "cc", "namespace");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testActive06() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(Catcode.ACTIVE, null, null, "namespace");
    }

    /**
     * <testcase> There is no COMMENT token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testComment01() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        assertNull(tf.createToken(Catcode.COMMENT, 'c', "namespace"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCr01() throws Exception {

        Catcode catcode = Catcode.CR;
        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(catcode, UnicodeChar.get('c'), "namespace");
        assertNotNull(t);
        assertEquals(catcode, t.getCatcode());
        assertTrue(t instanceof CrToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString01() throws Exception {

        checkCodeToken(Catcode.ACTIVE, 'c');
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString02() throws Exception {

        checkCodeToken(Catcode.ESCAPE, 'c');
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString03() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(Catcode.CR, 'c', "namespace");
        assertNotNull(t);
        assertEquals(Catcode.CR, t.getCatcode());
        assertTrue(t instanceof CrToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString04() throws Exception {

        Token t = checkToken(Catcode.LEFTBRACE, 'c');
        assertTrue(t instanceof LeftBraceToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString05() throws Exception {

        Token t = checkToken(Catcode.LETTER, 'c');
        assertTrue(t instanceof LetterToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString06() throws Exception {

        Token t = checkToken(Catcode.MACROPARAM, 'c');
        assertTrue(t instanceof MacroParamToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString07() throws Exception {

        Token t = checkToken(Catcode.MATHSHIFT, 'c');
        assertTrue(t instanceof MathShiftToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString08() throws Exception {

        Token t = checkToken(Catcode.OTHER, 'c');
        assertTrue(t instanceof OtherToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString09() throws Exception {

        Token t = checkToken(Catcode.RIGHTBRACE, 'c');
        assertTrue(t instanceof RightBraceToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString10() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(Catcode.SPACE, 'c', "namespace");
        assertNotNull(t);
        assertEquals(Catcode.SPACE, t.getCatcode());
        assertEquals(UnicodeChar.get(' '), t.getChar());
        assertTrue(t instanceof SpaceToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString11() throws Exception {

        Token t = checkToken(Catcode.SUBMARK, 'c');
        assertTrue(t instanceof SubMarkToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString12() throws Exception {

        Token t = checkToken(Catcode.SUPMARK, 'c');
        assertTrue(t instanceof SupMarkToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCreateTokenCatcodeIntString13() throws Exception {

        Token t = checkToken(Catcode.TABMARK, 'c');
        assertTrue(t instanceof TabMarkToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public void testCreateTokenCatcodeIntStringErr01() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(null, 'c', "namespace");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public void testCreateTokenCatcodeUnicodecharString00() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(null, UnicodeChar.get('c'), "namespace");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public void testCreateTokenCatcodeUnicodecharString01() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(null, UnicodeChar.get('c'), "namespace");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public void testCreateTokenCatcodeUnicodecharString03() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(null, null, "namespace");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testCreateTokenCatcodeUnicodecharString04() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(Catcode.ESCAPE, null, "namespace");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEscape01() throws Exception {

        Token t = checkCodeToken(Catcode.ESCAPE, UnicodeChar.get('c'));
        assertTrue(t instanceof ControlSequenceToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEscape02() throws Exception {

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
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEscape03() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t = tf.createToken(Catcode.ESCAPE, UnicodeChar.get('c'),
            "abc", "namespace");
        assertNotNull(t);
        assertTrue(t instanceof ControlSequenceToken);
        assertEquals(Catcode.ESCAPE, t.getCatcode());
        assertEquals(UnicodeChar.get('c'), t.getChar());
        assertEquals("namespace", ((ControlSequenceToken) t).getNamespace());
        assertEquals("abc", ((ControlSequenceToken) t).getName());
    }

    /**
     * <testcase> There is no IGNORE token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIgnore01() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        assertNull(tf.createToken(Catcode.IGNORE, 'c', "namespace"));
    }

    /**
     * <testcase> There is no INVALID token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testInvalid01() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        assertNull(tf.createToken(Catcode.INVALID, 'c', "namespace"));
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLeftbrace01() throws Exception {

        Token t = checkToken(Catcode.LEFTBRACE, UnicodeChar.get('c'));
        assertTrue(t instanceof LeftBraceToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLeftbrace02() throws Exception {

        Token t = checkToken(Catcode.LEFTBRACE, "c");
        assertTrue(t instanceof LeftBraceToken);
    }

    /**
     * <testcase> An empty string for the character code leads to an error when
     * creating a Leftbrace token.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testLeftbraceErr1() throws Exception {

        checkToken(Catcode.LEFTBRACE, "");
    }

    /**
     * <testcase> A long string for the character code leads to an error when
     * creating a Leftbrace token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testLeftbraceErr2() throws Exception {

        checkToken(Catcode.LEFTBRACE, "xx");
    }

    /**
     * <testcase> A null string leads to an error when creating a Leftbrace
     * token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testLeftbraceErr3() throws Exception {

        checkToken(Catcode.LEFTBRACE, (String) null);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeVisitorException.class)
    public void testLetter01() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(Catcode.LETTER, -1, null);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeVisitorException.class)
    public void testLetter02() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        tf.createToken(Catcode.LETTER, -1, "namespace");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLetter03() throws Exception {

        Token t = checkToken(Catcode.LETTER, UnicodeChar.get('c'));
        assertTrue(t instanceof LetterToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLetter04() throws Exception {

        Token t = checkToken(Catcode.LETTER, "c");
        assertTrue(t instanceof LetterToken);
    }

    /**
     * <testcase> Letter tokens are identical for the same parameters.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLetter10() throws Exception {

        Token t1 = checkToken(Catcode.LETTER, "c");
        assertTrue(t1 instanceof LetterToken);
        Token t2 = checkToken(Catcode.LETTER, "c");
        assertTrue(t2 instanceof LetterToken);
        assertEquals(t1, t2);
    }

    /**
     * <testcase> An empty string for the character code leads to an error when
     * creating a letter token.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testLetterErr1() throws Exception {

        checkToken(Catcode.LETTER, "");
    }

    /**
     * <testcase> A long string for the character code leads to an error when
     * creating a letter token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testLetterErr2() throws Exception {

        checkToken(Catcode.LETTER, "xx");
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacroparam01() throws Exception {

        Token t = checkToken(Catcode.MACROPARAM, UnicodeChar.get('c'));
        assertTrue(t instanceof MacroParamToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacroparam02() throws Exception {

        Token t = checkToken(Catcode.MACROPARAM, "c");
        assertTrue(t instanceof MacroParamToken);
    }

    /**
     * <testcase> An empty string for the character code leads to an error when
     * creating a macroparam token.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testMacroparamErr1() throws Exception {

        checkToken(Catcode.MACROPARAM, "");
    }

    /**
     * <testcase> A long string for the character code leads to an error when
     * creating a macroparam token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testMacroparamErr2() throws Exception {

        checkToken(Catcode.MACROPARAM, "xx");
    }

    /**
     * <testcase> A null string leads to an error when creating a macroparam
     * token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testMacroparamErr3() throws Exception {

        checkToken(Catcode.MACROPARAM, (String) null);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathshift01() throws Exception {

        Token t = checkToken(Catcode.MATHSHIFT, UnicodeChar.get('c'));
        assertTrue(t instanceof MathShiftToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathshift02() throws Exception {

        Token t = checkToken(Catcode.MATHSHIFT, "c");
        assertTrue(t instanceof MathShiftToken);
    }

    /**
     * <testcase> An empty string for the character code leads to an error when
     * creating a mathshift token.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testMathshiftErr1() throws Exception {

        checkToken(Catcode.MATHSHIFT, "");
    }

    /**
     * <testcase> A long string for the character code leads to an error when
     * creating a mathshift token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testMathshiftErr2() throws Exception {

        checkToken(Catcode.MATHSHIFT, "xx");
    }

    /**
     * <testcase> A null string leads to an error when creating a mathshift
     * token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testMathshiftErr3() throws Exception {

        checkToken(Catcode.MATHSHIFT, (String) null);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOther01() throws Exception {

        Token t = checkToken(Catcode.OTHER, UnicodeChar.get('c'));
        assertTrue(t instanceof OtherToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOther02() throws Exception {

        Token t = checkToken(Catcode.OTHER, "c");
        assertTrue(t instanceof OtherToken);
    }

    /**
     * <testcase> An empty string for the character code leads to an error when
     * creating a Other token.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testOtherErr1() throws Exception {

        checkToken(Catcode.OTHER, "");
    }

    /**
     * <testcase> A long string for the character code leads to an error when
     * creating a Other token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testOtherErr2() throws Exception {

        checkToken(Catcode.OTHER, "xx");
    }

    /**
     * <testcase> A null string leads to an error when creating a Other token.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testOtherErr3() throws Exception {

        checkToken(Catcode.OTHER, (String) null);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRightbrace01() throws Exception {

        Token t = checkToken(Catcode.RIGHTBRACE, UnicodeChar.get('c'));
        assertTrue(t instanceof RightBraceToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRightbrace02() throws Exception {

        Token t = checkToken(Catcode.RIGHTBRACE, "c");
        assertTrue(t instanceof RightBraceToken);
    }

    /**
     * <testcase> An empty string for the character code leads to an error when
     * creating a Rightbrace token.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testRightbraceErr1() throws Exception {

        checkToken(Catcode.RIGHTBRACE, "");
    }

    /**
     * <testcase> A long string for the character code leads to an error when
     * creating a Rightbrace token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testRightbraceErr2() throws Exception {

        checkToken(Catcode.RIGHTBRACE, "xx");
    }

    /**
     * <testcase> A null string leads to an error when creating a Rightbrace
     * token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testRightbraceErr3() throws Exception {

        checkToken(Catcode.RIGHTBRACE, (String) null);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSpace01() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Catcode catcode = Catcode.SPACE;
        Token t = tf.createToken(catcode, UnicodeChar.get('c'), "namespace");
        assertNotNull(t);
        assertEquals(catcode, t.getCatcode());
        assertEquals(UnicodeChar.get(' '), t.getChar());
        assertTrue(t instanceof SpaceToken);
    }

    /**
     * <testcase> There is only one SPACE token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSpace02() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Token t1 = tf.createToken(Catcode.SPACE, 'c', "namespace");
        assertNotNull(t1);
        Token t2 = tf.createToken(Catcode.SPACE, 'd', "namespace2");
        assertNotNull(t2);
        assertTrue(t1 == t2);
    }

    /**
     * <testcase> There is only one SPACE token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSpace03() throws Exception {

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
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubmark01() throws Exception {

        Token t = checkToken(Catcode.SUBMARK, UnicodeChar.get('c'));
        assertTrue(t instanceof SubMarkToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubmark02() throws Exception {

        Token t = checkToken(Catcode.SUBMARK, "c");
        assertTrue(t instanceof SubMarkToken);
    }

    /**
     * <testcase> An empty string for the character code leads to an error when
     * creating a Submark token.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testSubmarkErr1() throws Exception {

        checkToken(Catcode.SUBMARK, "");
    }

    /**
     * <testcase> A long string for the character code leads to an error when
     * creating a Submark token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testSubmarkErr2() throws Exception {

        checkToken(Catcode.SUBMARK, "xx");
    }

    /**
     * <testcase> A null string leads to an error when creating a Submark token.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testSubmarkErr3() throws Exception {

        checkToken(Catcode.SUBMARK, (String) null);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSupmark01() throws Exception {

        Token t = checkToken(Catcode.SUPMARK, UnicodeChar.get('c'));
        assertTrue(t instanceof SupMarkToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSupmark02() throws Exception {

        Token t = checkToken(Catcode.SUPMARK, "c");
        assertTrue(t instanceof SupMarkToken);
    }

    /**
     * <testcase> An empty string for the character code leads to an error when
     * creating a Supmark token.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testSupmarkErr1() throws Exception {

        checkToken(Catcode.SUPMARK, "");
    }

    /**
     * <testcase> A long string for the character code leads to an error when
     * creating a Supmark token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testSupmarkErr2() throws Exception {

        checkToken(Catcode.SUPMARK, "xx");
    }

    /**
     * <testcase> A null string leads to an error when creating a Supmark token.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testSupmarkErr3() throws Exception {

        checkToken(Catcode.SUPMARK, (String) null);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTabmark01() throws Exception {

        Token t = checkToken(Catcode.TABMARK, UnicodeChar.get('c'));
        assertTrue(t instanceof TabMarkToken);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTabmark02() throws Exception {

        Token t = checkToken(Catcode.TABMARK, "c");
        assertTrue(t instanceof TabMarkToken);
    }

    /**
     * <testcase> An empty string for the character code leads to an error when
     * creating a Tabmark token.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testTabmarkErr1() throws Exception {

        checkToken(Catcode.TABMARK, "");
    }

    /**
     * <testcase> A long string for the character code leads to an error when
     * creating a Tabmark token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeWrongLengthException.class)
    public void testTabmarkErr2() throws Exception {

        checkToken(Catcode.TABMARK, "xx");
    }

    /**
     * <testcase> A null string leads to an error when creating a Tabmark token.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CatcodeException.class)
    public void testTabmarkErr3() throws Exception {

        checkToken(Catcode.TABMARK, (String) null);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToTokensCharSequence0() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens(null);
        assertEquals(0, tokens.length());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToTokensCharSequence1() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens("");
        assertEquals(0, tokens.length());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToTokensCharSequence2() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens("abc");
        assertEquals(3, tokens.length());
        assertEquals("abc", tokens.toText());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToTokensCharSequence3() throws Exception {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens("a b");
        assertEquals(3, tokens.length());
        assertEquals("a b", tokens.toText());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws CatcodeException in case of an error
     */
    @Test
    public void testToTokensLong1() throws CatcodeException {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens(0);
        assertEquals(1, tokens.length());
        assertEquals("0", tokens.toText());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws CatcodeException in case of an error
     */
    @Test
    public void testToTokensLong2() throws CatcodeException {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens(1);
        assertEquals(1, tokens.length());
        assertEquals("1", tokens.toText());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws CatcodeException in case of an error
     */
    @Test
    public void testToTokensLong3() throws CatcodeException {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens(-1);
        assertEquals(2, tokens.length());
        assertEquals("-1", tokens.toText());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws CatcodeException in case of an error
     */
    @Test
    public void testToTokensLong4() throws CatcodeException {

        TokenFactoryImpl tf = new TokenFactoryImpl();
        assertNotNull(tf);
        Tokens tokens = tf.toTokens(123);
        assertEquals(3, tokens.length());
        assertEquals("123", tokens.toText());
    }

}
