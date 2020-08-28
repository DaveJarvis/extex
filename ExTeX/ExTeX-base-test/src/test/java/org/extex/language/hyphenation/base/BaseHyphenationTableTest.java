/*
 * Copyright (C) 2005-2010 The ExTeX Group and individual authors listed below
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.extex.core.UnicodeChar;
import org.extex.core.UnicodeCharList;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.FontKey;
import org.extex.font.FontKeyFactory;
import org.extex.language.Language;
import org.extex.language.hyphenation.MockContext;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.language.word.impl.TeXWords;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextImpl;
import org.extex.typesetter.tc.font.impl.NullFont;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.DiscretionaryNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.SpaceNode;
import org.extex.typesetter.type.node.factory.CachingNodeFactory;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * Test suite for the base hyphenation table.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4805 $
 */
public class BaseHyphenationTableTest {

    /**
     * Mock implementation of a font.
     */
    private static class MockFont extends NullFont {

        /**
         * The field <tt>serialVersionUID</tt> contains the version number for
         * serialization.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The field <tt>hyphen</tt> contains the hyphenation character.
         */
        private UnicodeChar hyphen = UnicodeChar.get('-');

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

            return new Glue(10 * Dimen.ONE);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#hasGlyph(org.extex.core.UnicodeChar)
         */
        @Override
        public boolean hasGlyph(UnicodeChar uc) {

            return true;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#setEfCode(org.extex.core.UnicodeChar,
         *      long)
         */
        @Override
        public void setEfCode(UnicodeChar uc, long code) {

            // setEfcode unimplemented
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.typesetter.tc.font.impl.NullFont#setFontDimen(java.lang.String,
         *      org.extex.core.dimen.Dimen)
         */
        @Override
        public void setFontDimen(String name, Dimen value) {

            // nothing to do
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
         * Set the skew char.
         * 
         * @param s the skew char
         * 
         * @see org.extex.typesetter.tc.font.Font#setSkewChar(org.extex.core.UnicodeChar)
         */
        @Override
        public void setSkewChar(UnicodeChar s) {

            // nothing to do
        }

    }

    /**
     * This mock implementation is for test purposes only.
     */
    private static class MyMockContext extends MockContext {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        private static final long serialVersionUID = 1L;


        protected MyMockContext() {

            set(new MockFont(), true);
        }

        /**
         * Getter for the lccode mapping of upper case characters to their lower
         * case equivalent.
         * 
         * @param uc the upper case character
         * 
         * @return the lower case equivalent or null if none exists
         * 
         * @see org.extex.interpreter.context.Context#getLccode(org.extex.core.UnicodeChar)
         */
        @Override
        public UnicodeChar getLccode(UnicodeChar uc) {

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
    public static void main(String[] args) {

        (new JUnitCore()).run(BaseHyphenationTableTest.class);
    }

    /**
     * Make a list of Unicode characters from a String.
     * 
     * @param s the string to translate
     * 
     * @return the list
     */
    private static UnicodeCharList makeList(CharSequence s) {

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
    private MyMockContext context;

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
    private HorizontalListNode hlist(String s) {

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
     * Set-up the test suite.
     * 
     * @throws HyphenationException in case of an error
     */
    @Before
    public void setUp() throws HyphenationException {

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
    @Test
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
    @Test
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
    @Test
    public void test3() throws Exception {

        HorizontalListNode nodes = hlist("abcdef");
        language.hyphenate(nodes, context, HYPHEN, 0, true, nodeFactory);
        assertEquals(7, nodes.size());
        assertTrue(nodes.get(3) instanceof DiscretionaryNode);
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
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
    @Test
    @Ignore
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

    /**
     * <testcase> Test case checking that initially the left hyphen min is 0.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetLeftHyphenMin1() throws Exception {

        assertEquals(0, language.getLeftHyphenMin());
    }

    /**
     * <testcase> Test case checking that the left hyphen min set with
     * setLeftHyphenmin() can be read with getLeftHyphenmin(). </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetLeftHyphenMin2() throws Exception {

        language.setLeftHyphenMin(123);
        assertEquals(123, language.getLeftHyphenMin());
    }

    /**
     * <testcase> Test case checking that initially the name is
     * <code>null</code>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetName1() throws Exception {

        assertNull(language.getName());
    }

    /**
     * <testcase> Test case checking that the name set with setName() is
     * retrieved by getName(). </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetName2() throws Exception {

        String name = "abc";
        language.setName(name);
        assertEquals(name, language.getName());
    }

    /**
     * <testcase> Test case checking that initially the right hyphen min is 0.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetRightHyphenMin1() throws Exception {

        assertEquals(0, language.getRightHyphenMin());
    }

    /**
     * <testcase> Test case checking that the right hyphen min set with
     * setRightHyphenmin() can be read with getRightHyphenmin(). </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetRightHyphenMin2() throws Exception {

        language.setRightHyphenMin(123);
        assertEquals(123, language.getRightHyphenMin());
    }

    /**
     * <testcase> Test case checking that initially the language is hyphenating.
     * </testcase>
     * 
     * @throws HyphenationException in case of an error
     */
    @Test
    public void testIsHyphenating1() throws HyphenationException {

        assertTrue(language.isHyphenating());
    }

    /**
     * <testcase> Test case checking that the hyphenating indicator set with
     * setHyphenating() can be read with isHyphenating(). </testcase>
     * 
     * @throws HyphenationException in case of an error
     */
    @Test
    public void testIsHyphenating2() throws HyphenationException {

        language.setHyphenating(false);
        assertFalse(language.isHyphenating());
    }

    /**
     * <testcase> Test case checking that the hyphenating indicator set with
     * setHyphenating() can be read with isHyphenating(). </testcase>
     * 
     * @throws HyphenationException in case of an error
     */
    @Test
    public void testIsHyphenating3() throws HyphenationException {

        language.setHyphenating(false);
        assertFalse(language.isHyphenating());
        language.setHyphenating(true);
        assertTrue(language.isHyphenating());
    }

}
