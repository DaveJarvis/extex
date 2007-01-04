/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package org.extex.language.hyphenation.base;

import junit.framework.TestCase;

import org.extex.font.FontByteArray;
import org.extex.font.FontKey;
import org.extex.font.FontKeyFactory;
import org.extex.font.Glyph;
import org.extex.font.Kerning;
import org.extex.font.Ligature;
import org.extex.font.type.BoundingBox;
import org.extex.font.type.other.NullFont;
import org.extex.font.type.tfm.TFMFixWord;
import org.extex.interpreter.context.MockContext;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.context.tc.TypesettingContextImpl;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.glue.Glue;
import org.extex.language.Language;
import org.extex.language.word.impl.TeXWords;
import org.extex.type.UnicodeChar;
import org.extex.type.UnicodeCharList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.DiscretionaryNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.SpaceNode;
import org.extex.typesetter.type.node.factory.CachingNodeFactory;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.extex.util.framework.configuration.exception.ConfigurationException;


/**
 * Test suite for the base hyphenation table.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4805 $
 */
public class BaseHyphenationTableTest extends TestCase {

    /**
     * Mock implementation of a font.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4805 $
     */
    private class MockFont extends NullFont {

        /**
         * The field <tt>serialVersionUID</tt> ...
         */
        private static final long serialVersionUID = 1L;

        /**
         * The field <tt>hyphen</tt> contains the hyphenation character.
         */
        private UnicodeChar hyphen = UnicodeChar.get('-');

        /**
         * The field <tt>hyphenGlyph</tt> contains the hyphen glyph.
         */
        private Glyph hyphenGlyph = new MockGlyph();

        /**
         * @see org.extex.font.type.Fount#getActualSize()
         */
        public FixedDimen getActualSize() {

            return Dimen.ONE_INCH;
        }

        /**
         * @see org.extex.font.type.Fount#getBoundingBox()
         */
        public BoundingBox getBoundingBox() {

            return null;
        }

        /**
         * @see org.extex.font.type.Fount#getCheckSum()
         */
        public int getCheckSum() {

            return 0;
        }

        /**
         * @see org.extex.font.type.Fount#getDesignSize()
         */
        public FixedDimen getDesignSize() {

            return Dimen.ONE_INCH;
        }

        /**
         * @see org.extex.interpreter.type.font.Font#getEfcode()
         */
        public long getEfCode(UnicodeChar uc) {

            // TODO gene: getEfcode unimplemented
            return 0;
        }

        /**
         * @see org.extex.font.type.Fount#getEm()
         */
        public FixedDimen getEm() {

            return Dimen.ONE_INCH;
        }

        /**
         * @see org.extex.font.type.Fount#getEx()
         */
        public FixedDimen getEx() {

            return Dimen.ONE_INCH;
        }

        /**
         * @see org.extex.font.type.Fount#getFontByteArray()
         */
        public FontByteArray getFontByteArray() {

            return null; // add by mgn
        }

        /**
         * @see org.extex.font.type.Fount#getFontDimen(java.lang.String)
         */
        public FixedDimen getFontDimen(final String key) {

            return Dimen.ONE_INCH;
        }

        /**
         * @see org.extex.font.type.Fount#getFontKey()
         */
        public FontKey getFontKey() {

            return (new FontKeyFactory()).newInstance("mockfont"); // add by mgn
        }

        /**
         * @see org.extex.font.type.Fount#getFontName()
         */
        public String getFontName() {

            return "mock";
        }

        /**
         * @see org.extex.font.type.Fount#getGlyph(org.extex.type.UnicodeChar)
         */
        public Glyph getGlyph(final UnicodeChar c) {

            if (hyphen.equals(c)) {
                return hyphenGlyph;
            }
            return null;
        }

        /**
         * @see org.extex.interpreter.type.font.Font#getHyphenChar()
         */
        public UnicodeChar getHyphenChar() {

            return hyphen;
        }

        /**
         * @see org.extex.font.type.Fount#getLetterSpacing()
         */
        public FixedGlue getLetterSpacing() {

            return null;
        }

        /**
         * @see org.extex.font.type.Fount#getProperty(java.lang.String)
         */
        public String getProperty(final String key) {

            return null;
        }

        /**
         * @see org.extex.interpreter.type.font.Font#getSkewChar()
         */
        public UnicodeChar getSkewChar() {

            return null;
        }

        /**
         * @see org.extex.font.type.Fount#getSpace()
         */
        public FixedGlue getSpace() {

            return new Glue(10 * Dimen.ONE);
        }

        /**
         * @see org.extex.font.type.other.NullFont#hasGlyph(org.extex.type.UnicodeChar)
         */
        public boolean hasGlyph(final UnicodeChar uc) {

            return true;
        }

        /**
         * @see org.extex.interpreter.type.font.Font#setEfCode(org.extex.type.UnicodeChar,
         *      long)
         */
        public void setEfCode(final UnicodeChar uc, final long code) {

            // TODO gene: setEfcode unimplemented

        }

        /**
         * @see org.extex.interpreter.type.font.Font#setFontDimen(
         *      java.lang.String, org.extex.interpreter.type.dimen.Dimen)
         */
        public void setFontDimen(final String key, final Dimen value) {

        }

        /**
         * @see org.extex.interpreter.type.font.Font#setHyphenChar(
         *      org.extex.type.UnicodeChar)
         */
        public void setHyphenChar(final UnicodeChar hyphen) {

            this.hyphen = hyphen;
        }

        /**
         * @see org.extex.interpreter.type.font.Font#setSkewChar(
         *      org.extex.type.UnicodeChar)
         */
        public void setSkewChar(final UnicodeChar skew) {

        }

    }

    /**
     * This is a mock implementation of a glyph.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4805 $
     */
    private class MockGlyph implements Glyph {

        /**
         * @see org.extex.font.Glyph#addKerning(org.extex.font.Kerning)
         */
        public void addKerning(final Kerning kern) {

        }

        /**
         * @see org.extex.font.Glyph#addLigature(org.extex.font.Ligature)
         */
        public void addLigature(final Ligature lig) {

        }

        public Dimen getDepth() {

            return null;
        }

        public FontByteArray getExternalFile() {

            return null;
        }

        public Dimen getHeight() {

            return null;
        }

        public Dimen getItalicCorrection() {

            return null;
        }

        public Dimen getKerning(final UnicodeChar uc) {

            return null;
        }

        public Dimen getLeftSpace() {

            return null;
        }

        public UnicodeChar getLigature(final UnicodeChar uc) {

            return null;
        }

        public String getName() {

            return null;
        }

        public String getNumber() {

            return null;
        }

        public Dimen getRightSpace() {

            return null;
        }

        public Dimen getWidth() {

            return null;
        }

        public void setDepth(final Dimen d) {

        }

        public void setDepth(final String gsize, final Dimen em,
                final int unitsperem) {

        }

        public void setDepth(final TFMFixWord size, final Dimen em) {

        }

        public void setExternalFile(final FontByteArray file) {

        }

        public void setHeight(final Dimen h) {

        }

        public void setHeight(final String gsize, final Dimen em,
                final int unitsperem) {

        }

        public void setHeight(final TFMFixWord size, final Dimen em) {

        }

        public void setItalicCorrection(final Dimen d) {

        }

        public void setItalicCorrection(final String gsize, final Dimen em,
                final int unitsperem) {

        }

        public void setItalicCorrection(final TFMFixWord size, final Dimen em) {

        }

        public void setLeftSpace(final Dimen ls) {

        }

        public void setName(final String n) {

        }

        public void setNumber(final String nr) {

        }

        public void setRightSpace(final Dimen rs) {

        }

        public void setWidth(final Dimen w) {

        }

        public void setWidth(final String gsize, final Dimen em,
                final int unitsperem) {

        }

        public void setWidth(final TFMFixWord size, final Dimen em) {

        }
    }

    /**
     * This mock implementation is for test purposes only.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4805 $
     */
    private class MyMockContext extends MockContext {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new object.
         */
        protected MyMockContext() {

            super();
            try {
                set(new MockFont(), true);
            } catch (ConfigurationException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * @see org.extex.interpreter.context.Context#getLccode(
         *      org.extex.type.UnicodeChar)
         */
        public UnicodeChar getLccode(final UnicodeChar uc) {

            return null;
        }
    }

    /**
     * The field <tt>HYPHEN</tt> contains the hyphen character.
     */
    private static final UnicodeChar HYPHEN = UnicodeChar.get('-');

    /**
     * The command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(BaseHyphenationTableTest.class);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s ...
     * 
     * @return ...
     */
    private static UnicodeCharList makeList(final CharSequence s) {

        UnicodeCharList list = new UnicodeCharList();
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);
            if (c == '-') {
                c = 0xad;
            }
            list.add(UnicodeChar.get(c));
        }
        return list;
    }

    /**
     * The field <tt>context</tt> contains the mock context for the tests.
     */
    private MockContext context;

    /**
     * The field <tt>language</tt> contains the language.
     */
    private Language language;

    /**
     * The field <tt>nodeFactory</tt> contains the node factory.
     */
    private NodeFactory nodeFactory = new CachingNodeFactory();

    /**
     * Create a hlist from a string.
     * 
     * @param s the string with the characters to encode
     * 
     * @return a horizontal list
     */
    private HorizontalListNode hlist(final String s) {

        TypesettingContext tc = new TypesettingContextImpl(new MockFont());
        HorizontalListNode n = new HorizontalListNode();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                n.add(new SpaceNode(tc.getFont().getSpace()));
            } else {
                n.add(new CharNode(tc, UnicodeChar.get(c)));
            }
        }
        return n;
    }

    /**
     * Create a new object to test.
     * 
     * @return the object to test
     */
    protected Language makeLanguage() {

        BaseHyphenationTable lang = new BaseHyphenationTable();
        lang.setWordTokenizer(new TeXWords());
        return lang;
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

        context = new MyMockContext();
        language = makeLanguage();
        language.addHyphenation(makeList("abc-def"), context);
        language.addHyphenation(makeList("d-e-f"), context);
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        HorizontalListNode nodes = hlist("");
        language.hyphenate(nodes, context, HYPHEN, 0, true, nodeFactory);
        assertEquals(0, nodes.size());
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        HorizontalListNode nodes = hlist("abc");
        language.hyphenate(nodes, context, HYPHEN, 0, true, nodeFactory);
        assertEquals(3, nodes.size());
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test3() throws Exception {

        HorizontalListNode nodes = hlist("abcdef");
        language.hyphenate(nodes, context, HYPHEN, 0, true, nodeFactory);
        assertEquals(7, nodes.size());
        assertTrue(nodes.get(3) instanceof DiscretionaryNode);
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * 
     * @throws Exception in case of an error
     */
    public void test4() throws Exception {

        HorizontalListNode nodes = hlist("abcdefgh");
        language.hyphenate(nodes, context, HYPHEN, 0, true, nodeFactory);
        assertEquals(8, nodes.size());
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test5() throws Exception {

        HorizontalListNode nodes = hlist("def");
        language.hyphenate(nodes, context, HYPHEN, 0, true, nodeFactory);

        assertEquals("\\hbox(0.0pt+0.0pt)x0.0pt\n" + ".d\n"
                     + ".\\discretionary{\\hbox(0.0pt+0.0pt)x0.0pt\n"
                     + "...-}{}{}\n" + ".e\n"
                     + ".\\discretionary{\\hbox(0.0pt+0.0pt)x0.0pt\n"
                     + "...-}{}{}\n" + ".f", //
                     nodes.toString());
    }

}
