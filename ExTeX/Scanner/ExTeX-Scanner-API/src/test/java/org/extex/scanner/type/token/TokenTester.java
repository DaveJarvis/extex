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

package org.extex.scanner.type.token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.junit.Test;

/**
 * Test class for ActiveCharacterToken.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TokenTester {

    /** The constant <tt>ACTIVE_TOKEN</tt> contains an active character token. */
    public static final ActiveCharacterToken ACTIVE_TOKEN =
            new ActiveCharacterToken(UnicodeChar.get('x'),
                Namespace.DEFAULT_NAMESPACE);

    /** The constant <tt>CR_TOKEN</tt> contains an cr token. */
    public static final CrToken CR_TOKEN = new CrToken(UnicodeChar.get('x'));

    /**
     * The constant <tt>CONTROL_SEQUENCE_TOKEN</tt> contains a control seqeunce
     * token.
     */
    public static final ControlSequenceToken CONTROL_SEQUENCE_TOKEN =
            new ControlSequenceToken(UnicodeChar.get('x'), "abc",
                Namespace.DEFAULT_NAMESPACE);

    /** The constant <tt>LEFT_BRACE_TOKEN</tt> contains a left brace token. */
    public static final LeftBraceToken LEFT_BRACE_TOKEN = new LeftBraceToken(
        UnicodeChar.get('x'));

    /** The constant <tt>LETTER_TOKEN</tt> contains a letter token. */
    public static final LetterToken LETTER_TOKEN = new LetterToken(
        UnicodeChar.get('x'));

    /** The constant <tt>MACRO_PARAM_TOKEN</tt> contains a macro param token. */
    public static final MacroParamToken MACRO_PARAM_TOKEN =
            new MacroParamToken(UnicodeChar.get('x'));

    /** The constant <tt>MATH_SHIF_TOKEN</tt> contains a math shift token. */
    public static final MathShiftToken MATH_SHIF_TOKEN = new MathShiftToken(
        UnicodeChar.get('x'));

    /** The constant <tt>OTHER_TOKEN</tt> contains an other token. */
    public static final OtherToken OTHER_TOKEN = new OtherToken(
        UnicodeChar.get('x'));

    /** The constant <tt>RIGHT_BRACE_TOKEN</tt> contains a right brace token. */
    public static final RightBraceToken RIGHT_BRACE_TOKEN =
            new RightBraceToken(UnicodeChar.get('x'));

    /** The constant <tt>SPACE_TOKEN</tt> contains a space token. */
    public static final SpaceToken SPACE_TOKEN = new SpaceToken(" ");

    /** The constant <tt>SUB_MARK_TOKEN</tt> contains a sub mark token. */
    public static final SubMarkToken SUB_MARK_TOKEN = new SubMarkToken(
        UnicodeChar.get('x'));

    /** The constant <tt>SUP_MARK_TOKEN</tt> contains a super mark token. */
    public static final SupMarkToken SUP_MARK_TOKEN = new SupMarkToken(
        UnicodeChar.get('x'));

    /** The constant <tt>TAB_MARK_TOKEN</tt> contains a tab mark token. */
    public static final TabMarkToken TAB_MARK_TOKEN = new TabMarkToken(
        UnicodeChar.get('x'));

    /**
     * The constant <tt>TV</tt> contains the token visitor returning the
     * catcode.
     */
    private static final TokenVisitor<Catcode, Object> TV =
            new TokenVisitor<Catcode, Object>() {

                @Override
                public Catcode visitActive(ActiveCharacterToken t, Object arg)
                        throws Exception {

                    return Catcode.ACTIVE;
                }

                @Override
                public Catcode visitCr(CrToken t, Object arg) throws Exception {

                    return Catcode.CR;
                }

                @Override
                public Catcode visitEscape(ControlSequenceToken t, Object arg)
                        throws Exception {

                    return Catcode.ESCAPE;
                }

                @Override
                public Catcode visitLeftBrace(LeftBraceToken t, Object arg)
                        throws Exception {

                    return Catcode.LEFTBRACE;
                }

                @Override
                public Catcode visitLetter(LetterToken t, Object arg)
                        throws Exception {

                    return Catcode.LETTER;
                }

                @Override
                public Catcode visitMacroParam(MacroParamToken t, Object arg)
                        throws Exception {

                    return Catcode.MACROPARAM;
                }

                @Override
                public Catcode visitMathShift(MathShiftToken t, Object arg)
                        throws Exception {

                    return Catcode.MATHSHIFT;
                }

                @Override
                public Catcode visitOther(OtherToken t, Object arg)
                        throws Exception {

                    return Catcode.OTHER;
                }

                @Override
                public Catcode visitRightBrace(RightBraceToken t, Object arg)
                        throws Exception {

                    return Catcode.RIGHTBRACE;
                }

                @Override
                public Catcode visitSpace(SpaceToken t, Object arg)
                        throws Exception {

                    return Catcode.SPACE;
                }

                @Override
                public Catcode visitSubMark(SubMarkToken t, Object arg)
                        throws Exception {

                    return Catcode.SUBMARK;
                }

                @Override
                public Catcode visitSupMark(SupMarkToken t, Object arg)
                        throws Exception {

                    return Catcode.SUPMARK;
                }

                @Override
                public Catcode visitTabMark(TabMarkToken t, Object arg)
                        throws Exception {

                    return Catcode.TABMARK;
                }

            };

    /**
     * The field <tt>catcode</tt> contains the expected result for getCatcode().
     */
    private Catcode catcode;

    /**
     * The field <tt>str</tt> contains the expected result for toString().
     */
    private String str;

    /**
     * The field <tt>text</tt> contains the expected result for toText().
     */
    private String text;

    /**
     * The field <tt>token</tt> contains the default token to perform the tests
     * on.
     */
    private Token token;

    /**
     * Creates a new object.
     * 
     * @param t the token
     * @param catcode the category code
     * @param text the text
     * @param str the string
     */
    public TokenTester(Token t, Catcode catcode, String text, String str) {

        this.token = t;
        this.catcode = catcode;
        this.text = text;
        this.str = str;
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testEq0() {

        assertTrue("", token.eq(catcode, text));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testEq1() {

        assertFalse(token.eq(catcode, "="));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testEq10() {

        assertTrue(token.eq(catcode, 'x'));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testEq11() {

        assertFalse(token.eq(Catcode.ESCAPE, 'x'));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testEq2() {

        assertFalse(token.eq(Catcode.ESCAPE, "x"));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testEq20() {

        assertTrue(token.toString(), token.eq('x'));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testEq21() {

        assertFalse(token.eq('.'));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testEquals0() {

        assertTrue(token.equals(token));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testGetCatcode0() {

        assertEquals(catcode, token.getCatcode());
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testGetChar0() {

        UnicodeChar x = token.getChar();
        assertNotNull(x);
        assertEquals(120, x.getCodePoint());
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa0() {

        assertEquals(catcode == Catcode.SPACE, token.isa(Catcode.SPACE));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa1() {

        assertEquals(catcode == Catcode.ACTIVE, token.isa(Catcode.ACTIVE));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa10() {

        assertEquals(catcode == Catcode.MATHSHIFT, token.isa(Catcode.MATHSHIFT));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa11() {

        assertEquals(catcode == Catcode.OTHER, token.isa(Catcode.OTHER));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa12() {

        assertEquals(catcode == Catcode.RIGHTBRACE,
            token.isa(Catcode.RIGHTBRACE));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa13() {

        assertEquals(catcode == Catcode.SUBMARK, token.isa(Catcode.SUBMARK));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa14() {

        assertEquals(catcode == Catcode.SUPMARK, token.isa(Catcode.SUPMARK));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa15() {

        assertEquals(catcode == Catcode.TABMARK, token.isa(Catcode.TABMARK));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa2() {

        assertEquals(catcode == Catcode.COMMENT, token.isa(Catcode.COMMENT));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa3() {

        assertEquals(catcode == Catcode.CR, token.isa(Catcode.CR));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa4() {

        assertEquals(catcode == Catcode.ESCAPE, token.isa(Catcode.ESCAPE));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa5() {

        assertEquals(catcode == Catcode.IGNORE, token.isa(Catcode.IGNORE));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa6() {

        assertEquals(catcode == Catcode.INVALID, token.isa(Catcode.INVALID));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa7() {

        assertEquals(catcode == Catcode.LEFTBRACE, token.isa(Catcode.LEFTBRACE));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa8() {

        assertEquals(catcode == Catcode.LETTER, token.isa(Catcode.LETTER));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testIsa9() {

        assertEquals(catcode == Catcode.MACROPARAM,
            token.isa(Catcode.MACROPARAM));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testToString0() {

        assertEquals(str, token.toString());
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testToStringBuilder1() {

        StringBuilder sb = new StringBuilder();
        token.toString(sb);
        assertEquals(str, sb.toString());
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testToText0() {

        assertEquals(text, token.toText());
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testToTextString0() {

        assertEquals(text, token.toText(null));
    }

    /**
     * <testcase> Test that ... </testcase>
     */
    @Test
    public void testToTextString1() {

        assertEquals(text, token.toText(UnicodeChar.get(92)));
    }

    /**
     * <testcase> Test that the corrct visit method is invoked. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVisit1() throws Exception {

        assertEquals(catcode, token.visit(TV, null));
    }

}
