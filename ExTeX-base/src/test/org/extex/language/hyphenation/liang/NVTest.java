/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.language.hyphenation.liang;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.font.FontKey;
import org.extex.font.FontKeyFactory;
import org.extex.font.type.other.NullFont;
import org.extex.interpreter.context.tc.ModifiableTypesettingContext;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.context.tc.TypesettingContextImpl;
import org.extex.interpreter.type.font.Font;
import org.extex.language.ModifiableLanguage;
import org.extex.language.hyphenation.base.BaseHyphenationTable;
import org.extex.language.ligature.impl.LigatureBuilderImpl;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.token.TokenFactoryImpl;
import org.extex.typesetter.type.node.factory.CachingNodeFactory;
import org.extex.typesetter.type.node.factory.NodeFactory;

/**
 * This is the test class for NV.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4527 $
 */
public class NVTest extends TestCase {

    /**
     * This is a mock implementation of a font.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4527 $
     */
    private class MockFont extends NullFont {

        /**
         * The field <tt>serialVersionUID</tt> contains the ...
         */
        private static final long serialVersionUID = 1L;

        /**
         * The field <tt>FF</tt> contains the ligature character ff.
         */
        public static final char FF = 'F';

        /**
         * The field <tt>FFL</tt> contains the ligature character ffl.
         */
        public static final char FFL = 'L';

        /**
         * The field <tt>FL</tt> contains the ligature character ffl.
         */
        public static final char FL = 'G';

        /**
         * The field <tt>hyphen</tt> contains the hyphen character.
         */
        private UnicodeChar hyphen = UnicodeChar.get('-');

        /**
         * The field <tt>map</tt> contains the glyph cache.
         */
        private Map<UnicodeChar, MockGlyph> map =
                new HashMap<UnicodeChar, MockGlyph>();

        /**
         * Creates a new object.
         */
        public MockFont() {

            super();
            map.put(hyphen, new MockGlyph('-'));
            map.put(UnicodeChar.get('f'), new MockGlyph('f'));
            map.put(UnicodeChar.get('l'), new MockGlyph('l'));
            map.put(UnicodeChar.get(FL), new MockGlyph(FL));
            map.put(UnicodeChar.get(FFL), new MockGlyph(FFL));
            map.put(UnicodeChar.get(FF), new MockGlyph(FF));
        }

        /**
         * Returns the actual size.
         *
         * @return the actual size
         *
         * @see org.extex.font.type.Fount#getActualSize()
         */
        public FixedDimen getActualSize() {

            return Dimen.ONE_INCH;
        }

        /**
         * Returns the check sum.
         *
         * @return the check sum
         *
         * @see org.extex.font.type.Fount#getCheckSum()
         */
        public int getCheckSum() {

            return 0;
        }

        /**
         * Returns the design size.
         *
         * @return the design size.
         *
         * @see org.extex.font.type.Fount#getDesignSize()
         */
        public FixedDimen getDesignSize() {

            return Dimen.ONE_INCH;
        }

        /**
         * Return the em size of the font.
         *
         * @return em size
         *
         * @see org.extex.font.type.Fount#getEm()
         */
        public FixedDimen getEm() {

            return Dimen.ONE_INCH;
        }

        /**
         * Return the ex size of the font.
         *
         * @return ex size
         *
         * @see org.extex.font.type.Fount#getEx()
         */
        public FixedDimen getEx() {

            return Dimen.ONE_INCH;
        }

        /**
         * Return font dimen size with a key.
         *
         * @param key the key
         * @return the value for the key
         *
         * @see org.extex.font.type.Fount#getFontDimen(java.lang.String)
         */
        public FixedDimen getFontDimen(String key) {

            return Dimen.ONE_INCH;
        }

        /**
         * Returns the key for the font.
         *
         * @return the key for the font
         *
         * @see org.extex.font.type.Fount#getFontKey()
         */
        public FontKey getFontKey() {

            return (new FontKeyFactory()).newInstance("mockfont"); // add by mgn
        }

        /**
         * Return the font name.
         *
         * @return the font name
         *
         * @see org.extex.font.type.Fount#getFontName()
         */
        public String getFontName() {

            return "mock";
        }

        /**
         * Returns the hyphen char.
         *
         * @return the hyphen char
         *
         * @see org.extex.interpreter.type.font.Font#getHyphenChar()
         */
        public UnicodeChar getHyphenChar() {

            return hyphen;
        }

        /**
         * Return the letter spacing
         *
         * @return  the letter spacing
         *
         * @see org.extex.font.type.Fount#getLetterSpacing()
         */
        public FixedGlue getLetterSpacing() {

            return null;
        }

        /**
         * Returns the skew char.
         *
         * @return the skew char
         *
         * @see org.extex.interpreter.type.font.Font#getSkewChar()
         */
        public UnicodeChar getSkewChar() {

            return null;
        }

        /**
         * Return the width of space character.
         *
         * @return the width of the space character
         *
         * @see org.extex.font.type.Fount#getSpace()
         */
        public FixedGlue getSpace() {

            return null;
        }

        /**
         * Set the new value for the font parameter.
         *
         * @param name the name of the parameter
         * @param value the value to set
         *
         * @see org.extex.interpreter.type.font.Font#setFontDimen(
         *      java.lang.String, org.extex.core.dimen.Dimen)
         */
        public void setFontDimen(String name, Dimen value) {

            // not needed
        }

        /**
         * Set the hyphen character.
         *
         * @param h The hyphen character
         *
         * @see org.extex.interpreter.type.font.Font#setHyphenChar(org.extex.core.UnicodeChar)
         */
        public void setHyphenChar(UnicodeChar h) {

            this.hyphen = h;
        }

        /**
         * Set the skew character.
         *
         * @param skew the skew character
         *
         * @see org.extex.interpreter.type.font.Font#setSkewChar(org.extex.core.UnicodeChar)
         */
        public void setSkewChar(UnicodeChar skew) {

            // not needed
        }

        /**
         * Setter for the ef code.
         * The ef code influences the stretchability of characters. It has a
         * positive value. 1000 means "normal" stretchability.
         *
         * @param uc the character
         * @param code the associated code
         *
         * @see org.extex.interpreter.type.font.Font#setEfCode(org.extex.core.UnicodeChar, long)
         */
        public void setEfCode(UnicodeChar uc, long code) {

            // TODO gene: setEfcode unimplemented
        }

        /**
         * Getter for the ef code.
         *
         * @param uc the character
         *
         * @return the ef code
         *
         * @see org.extex.font.type.other.NullFont#getEfCode(org.extex.core.UnicodeChar)
         */
        public long getEfCode(UnicodeChar uc) {

            // TODO gene: getEfcode unimplemented
            return 0;
        }
    }

    /**
     * This is a mock implementation of a glyph.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4527 $
     */
    private class MockGlyph {

        /**
         * The field <tt>c</tt> contains the character.
         */
        private char c;

        /**
         * Creates a new object.
         *
         * @param c the character
         */
        public MockGlyph(char c) {

            super();
            this.c = c;
        }

        /**
         * Try to build a ligature.
         *
         * @param uc the character
         *
         * @return the ligature
         */
        public UnicodeChar getLigature(UnicodeChar uc) {

            if (c == 'f') {
                if (uc.getCodePoint() == 'f') {
                    return UnicodeChar.get(MockFont.FF);
                } else if (uc.getCodePoint() == 'l') {
                    return UnicodeChar.get(MockFont.FL);
                }
            } else if (c == MockFont.FF) {
                if (uc.getCodePoint() == 'l') {
                    return UnicodeChar.get(MockFont.FFL);
                }
            }
            return null;
        }

    }

    /**
     * The field <tt>tokenFactory</tt> contains the token factory.
     */
    private static TokenFactory tokenFactory;

    /**
     * The command line interface.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(NVTest.class);
    }

    /**
     * The field <tt>cnf</tt> contains the char node factory.
     */
    private NodeFactory cnf;

    /**
     * The field <tt>f</tt> contains the token for f.
     */
    private Token f;

    /**
     * The field <tt>font</tt> contains the font.
     */
    private Font font;

    /**
     * The field <tt>l</tt> contains the token for l.
     */
    private Token l;

    /**
     * The field <tt>tc</tt> contains the typesetting context.
     */
    private TypesettingContext tc;

    /**
     * Set up the test suite
     *
     * @throws Exception in case of an error
     *
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception {

        super.setUp();
        font = new MockFont();
        cnf = new CachingNodeFactory();
        tokenFactory = new TokenFactoryImpl();
        f = tokenFactory.createToken(Catcode.LETTER, 'f', //
            Namespace.DEFAULT_NAMESPACE);
        l = tokenFactory.createToken(Catcode.LETTER, 'l', //
            Namespace.DEFAULT_NAMESPACE);
//        UnicodeChar hyphen = font.getHyphenChar();
        tc = new TypesettingContextImpl(font);
        ModifiableLanguage lang = new BaseHyphenationTable();
        lang.setLigatureBuilder(new LigatureBuilderImpl());
        ((ModifiableTypesettingContext) tc).setLanguage(lang);
    }

    /**
     * Dummy test case.
     *
     * @throws Exception in case of an error
     */
    public void test00000000() throws Exception {

        //
    }

    /**
     * f-fl ((f f) l)
     *
     * @throws Exception in case of an error
     */
    //    public void test1() throws Exception {
    //
    //        NodeList list = new HorizontalListNode();
    //        LigatureNode ffl = new LigatureNode(tc, UnicodeChar.get(MockFont.FFL), //
    //                getCharNode(tc, f.getChar()), //
    //                new LigatureNode(tc, UnicodeChar.get(MockFont.FF), //
    //                        getCharNode(tc, f.getChar()), //
    //                        getCharNode(tc, l.getChar())));
    //
    //        NV nv = new NV(list, hyphen, tc, cnf, //
    //                new boolean[]{false, true, false, false});
    //
    //        Count idx = new Count(0);
    //        ffl.visit(nv, idx);
    //        assertEquals(1, idx.getValue());
    //        assertEquals(1, list.size());
    //        assertTrue(list.get(0) instanceof DiscretionaryNode);
    //        DiscretionaryNode d = (DiscretionaryNode) list.get(0);
    //        assertEquals(2, d.getPreBreak().size());
    //        assertEquals(1, d.getPostBreak().size());
    //    }
    /**
     * f-fl (f (fl))
     *
     * @throws Exception in case of an error
     */
    //    public void test2() throws Exception {
    //
    //        NodeList list = new HorizontalListNode();
    //        LigatureNode ffl = new LigatureNode(tc, UnicodeChar.get(MockFont.FFL), //
    //                new LigatureNode(tc, UnicodeChar.get(MockFont.FF), //
    //                        getCharNode(tc, f.getChar()), //
    //                        getCharNode(tc, f.getChar())), //
    //                getCharNode(tc, l.getChar()));
    //
    //        NV nv = new NV(list, hyphen, tc, cnf, //
    //                new boolean[]{false, true, false, false});
    //
    //        Count idx = new Count(0);
    //        ffl.visit(nv, idx);
    //        assertEquals(1, idx.getValue());
    //        assertEquals(1, list.size());
    //        assertTrue(list.get(0) instanceof DiscretionaryNode);
    //        DiscretionaryNode d = (DiscretionaryNode) list.get(0);
    //        assertEquals(2, d.getPreBreak().size());
    //        assertEquals(1, d.getPostBreak().size());
    //    }
    /**
     * f-f-l ((f f) l)
     *
     * @throws Exception in case of an error
     */
    //    public void testDouble() throws Exception {
    //
    //        NodeList list = new HorizontalListNode();
    //        LigatureNode ffl = new LigatureNode(tc, UnicodeChar.get(MockFont.FFL), //
    //                getCharNode(tc, f.getChar()), //
    //                new LigatureNode(tc, UnicodeChar.get(MockFont.FF), //
    //                        getCharNode(tc, f.getChar()), //
    //                        getCharNode(tc, l.getChar())));
    //
    //        NV nv = new NV(list, hyphen, tc, cnf, //
    //                new boolean[]{false, false, false, false});
    //
    //        Count idx = new Count(0);
    //        ffl.visit(nv, idx);
    //        assertEquals(0, idx.getValue());
    //        assertEquals(1, list.size());
    //        assertEquals(list.get(0), ffl);
    //    }
    /**
     * ffl ((f f) l)
     *
     * @throws Exception in case of an error
     */
    //    public void testNone() throws Exception {
    //
    //        NodeList list = new HorizontalListNode();
    //        LigatureNode ffl = new LigatureNode(tc, UnicodeChar.get(MockFont.FFL), //
    //                getCharNode(tc, f.getChar()), //
    //                new LigatureNode(tc, UnicodeChar.get(MockFont.FF), //
    //                        getCharNode(tc, f.getChar()), //
    //                        getCharNode(tc, l.getChar())));
    //
    //        NV nv = new NV(list, hyphen, tc, cnf, //
    //                new boolean[]{false, false, false, false});
    //
    //        Count idx = new Count(0);
    //        ffl.visit(nv, idx);
    //        assertEquals(0, idx.getValue());
    //        assertEquals(1, list.size());
    //        assertEquals(list.get(0), ffl);
    //    }
    /**
     * -ffl ((f f) l)
     *
     * @throws Exception in case of an error
     */
    //    public void testPre() throws Exception {
    //
    //        NodeList list = new HorizontalListNode();
    //        LigatureNode ffl = new LigatureNode(tc, UnicodeChar.get(MockFont.FFL), //
    //                getCharNode(tc, f.getChar()), //
    //                new LigatureNode(tc, UnicodeChar.get(MockFont.FF), //
    //                        getCharNode(tc, f.getChar()), //
    //                        getCharNode(tc, l.getChar())));
    //
    //        NV nv = new NV(list, hyphen, tc, cnf, //
    //                new boolean[]{true, false, false, false});
    //
    //        Count idx = new Count(0);
    //        ffl.visit(nv, idx);
    //        assertEquals(1, idx.getValue());
    //        assertEquals(2, list.size());
    //        assertTrue(list.get(0) instanceof DiscretionaryNode);
    //        assertEquals(list.get(1), ffl);
    //    }
}
