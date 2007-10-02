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

import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.font.FontKey;
import org.extex.font.FontKeyFactory;
import org.extex.language.ModifiableLanguage;
import org.extex.language.hyphenation.base.BaseHyphenationTable;
import org.extex.language.ligature.impl.LigatureBuilderImpl;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.tc.ModifiableTypesettingContext;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextImpl;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.tc.font.impl.NullFont;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is the test class for NV.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4527 $
 */
public class NVTest {

    /**
     * This is a mock implementation of a font.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4527 $
     */
    private class MockFont extends NullFont {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number.
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
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getActualSize()
         */
        @Override
        public FixedDimen getActualSize() {

            return Dimen.ONE_INCH;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getCheckSum()
         */
        @Override
        public int getCheckSum() {

            return 0;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getDesignSize()
         */
        @Override
        public FixedDimen getDesignSize() {

            return Dimen.ONE_INCH;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getEm()
         */
        @Override
        public FixedDimen getEm() {

            return Dimen.ONE_INCH;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getEx()
         */
        @Override
        public FixedDimen getEx() {

            return Dimen.ONE_INCH;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getFontDimen(java.lang.String)
         */
        @Override
        public FixedDimen getFontDimen(String key) {

            return Dimen.ONE_INCH;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getFontKey()
         */
        @Override
        public FontKey getFontKey() {

            return (new FontKeyFactory()).newInstance("mockfont"); // add by
            // mgn
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getFontName()
         */
        @Override
        public String getFontName() {

            return "mock";
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getHyphenChar()
         */
        @Override
        public UnicodeChar getHyphenChar() {

            return hyphen;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getSkewChar()
         */
        @Override
        public UnicodeChar getSkewChar() {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getSpace()
         */
        @Override
        public FixedGlue getSpace() {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#setFontDimen(java.lang.String,
         *      org.extex.core.dimen.Dimen)
         */
        @Override
        public void setFontDimen(String name, Dimen value) {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#setHyphenChar(org.extex.core.UnicodeChar)
         */
        @Override
        public void setHyphenChar(UnicodeChar h) {

            this.hyphen = h;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#setSkewChar(org.extex.core.UnicodeChar)
         */
        @Override
        public void setSkewChar(UnicodeChar skew) {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#setEfCode(org.extex.core.UnicodeChar,
         *      long)
         */
        @Override
        public void setEfCode(UnicodeChar uc, long code) {

            // not needed
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#getEfCode(org.extex.core.UnicodeChar)
         */
        @Override
        public long getEfCode(UnicodeChar uc) {

            // not needed
            return 0;
        }
    }

    /**
     * This is a mock implementation of a glyph.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4527 $
     */
    private static class MockGlyph {

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
    // private static TokenFactory tokenFactory;
    /**
     * The command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(NVTest.class);
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
     */
    @Before
    public void setUp() throws Exception {

        font = new MockFont();
        // cnf = new CachingNodeFactory();
        // tokenFactory = new TokenFactoryImpl();
        // f = tokenFactory.createToken(Catcode.LETTER, 'f', //
        // Namespace.DEFAULT_NAMESPACE);
        // l = tokenFactory.createToken(Catcode.LETTER, 'l', //
        // Namespace.DEFAULT_NAMESPACE);
        // UnicodeChar hyphen = font.getHyphenChar();
        tc = new TypesettingContextImpl(font);
        ModifiableLanguage lang = new BaseHyphenationTable();
        lang.setLigatureBuilder(new LigatureBuilderImpl());
        ((ModifiableTypesettingContext) tc).setLanguage(lang);
    }

    /**
     * f-fl ((f f) l)
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void test1() throws Exception {

        NodeList list = new HorizontalListNode();
        // LigatureNode ffl = new LigatureNode(tc,
        // UnicodeChar.get(MockFont.FFL), //
        // getCharNode(tc, f.getChar()), //
        // new LigatureNode(tc, UnicodeChar.get(MockFont.FF), //
        // getCharNode(tc, f.getChar()), //
        // getCharNode(tc, l.getChar())));
        //
        // NV nv = new NV(list, hyphen, tc, cnf, //
        // new boolean[]{false, true, false, false});
        //
        // Count idx = new Count(0);
        // ffl.visit(nv, idx);
        // assertEquals(1, idx.getValue());
        // assertEquals(1, list.size());
        // assertTrue(list.get(0) instanceof DiscretionaryNode);
        // DiscretionaryNode d = (DiscretionaryNode) list.get(0);
        // assertEquals(2, d.getPreBreak().size());
        // assertEquals(1, d.getPostBreak().size());
    }

    /**
     * f-fl (f (fl))
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void test2() throws Exception {

        NodeList list = new HorizontalListNode();
        // LigatureNode ffl = new LigatureNode(tc,
        // UnicodeChar.get(MockFont.FFL), //
        // new LigatureNode(tc, UnicodeChar.get(MockFont.FF), //
        // getCharNode(tc, f.getChar()), //
        // getCharNode(tc, f.getChar())), //
        // getCharNode(tc, l.getChar()));
        //
        // NV nv = new NV(list, hyphen, tc, cnf, //
        // new boolean[]{false, true, false, false});
        //
        // Count idx = new Count(0);
        // ffl.visit(nv, idx);
        // assertEquals(1, idx.getValue());
        // assertEquals(1, list.size());
        // assertTrue(list.get(0) instanceof DiscretionaryNode);
        // DiscretionaryNode d = (DiscretionaryNode) list.get(0);
        // assertEquals(2, d.getPreBreak().size());
        // assertEquals(1, d.getPostBreak().size());
    }

    /**
     * f-f-l ((f f) l)
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testDouble() throws Exception {

        NodeList list = new HorizontalListNode();
        // LigatureNode ffl = new LigatureNode(tc,
        // UnicodeChar.get(MockFont.FFL), //
        // getCharNode(tc, f.getChar()), //
        // new LigatureNode(tc, UnicodeChar.get(MockFont.FF), //
        // getCharNode(tc, f.getChar()), //
        // getCharNode(tc, l.getChar())));
        //
        // NV nv = new NV(list, hyphen, tc, cnf, //
        // new boolean[]{false, false, false, false});
        //
        // Count idx = new Count(0);
        // ffl.visit(nv, idx);
        // assertEquals(0, idx.getValue());
        // assertEquals(1, list.size());
        // assertEquals(list.get(0), ffl);
    }

    /**
     * ffl ((f f) l)
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testNone() throws Exception {

        NodeList list = new HorizontalListNode();
        // LigatureNode ffl = new LigatureNode(tc,
        // UnicodeChar.get(MockFont.FFL), //
        // getCharNode(tc, f.getChar()), //
        // new LigatureNode(tc, UnicodeChar.get(MockFont.FF), //
        // getCharNode(tc, f.getChar()), //
        // getCharNode(tc, l.getChar())));
        //
        // NV nv = new NV(list, hyphen, tc, cnf, //
        // new boolean[]{false, false, false, false});
        //
        // Count idx = new Count(0);
        // ffl.visit(nv, idx);
        // assertEquals(0, idx.getValue());
        // assertEquals(1, list.size());
        // assertEquals(list.get(0), ffl);
    }

    /**
     * -ffl ((f f) l)
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testPre() throws Exception {

        NodeList list = new HorizontalListNode();
        // LigatureNode ffl = new LigatureNode(tc,
        // UnicodeChar.get(MockFont.FFL), //
        // getCharNode(tc, f.getChar()), //
        // new LigatureNode(tc, UnicodeChar.get(MockFont.FF), //
        // getCharNode(tc, f.getChar()), //
        // getCharNode(tc, l.getChar())));
        //
        // NV nv = new NV(list, hyphen, tc, cnf, //
        // new boolean[]{true, false, false, false});
        //
        // Count idx = new Count(0);
        // ffl.visit(nv, idx);
        // assertEquals(1, idx.getValue());
        // assertEquals(2, list.size());
        // assertTrue(list.get(0) instanceof DiscretionaryNode);
        // assertEquals(list.get(1), ffl);
    }
}
