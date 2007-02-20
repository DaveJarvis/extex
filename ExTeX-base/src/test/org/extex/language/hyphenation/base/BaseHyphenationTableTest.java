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

package org.extex.language.hyphenation.base;

import junit.framework.TestCase;

import org.extex.font.FontByteArray;
import org.extex.font.FontKey;
import org.extex.font.FontKeyFactory;
import org.extex.font.type.other.NullFont;
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

/**
 * Test suite for the base hyphenation table.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4805 $
 */
public class BaseHyphenationTableTest extends TestCase {

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
         * Returns the byte array for the external file. E.g. cmr12.pfb.
         *
         * @return Returns the byte array for the external file e.g. cmr12.pfb
         *
         * @deprecated this single method should be replaced by some way to
         *   retrieve an appropriate font format
         *
         * @see org.extex.font.type.Fount#getFontByteArray()
         */
        public FontByteArray getFontByteArray() {

            return null;
        }

        /**
         * Return font dimen size with a key.
         *
         * @param key the key
         * @return the value for the key
         *
         * @see org.extex.font.type.Fount#getFontDimen(java.lang.String)
         */
        public FixedDimen getFontDimen(final String key) {

            return Dimen.ONE_INCH;
        }

        /**
         * Returns the key for the font.
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
         * @return the letter spacing
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

            return new Glue(10 * Dimen.ONE);
        }

        /**
         * Determine whether the glyph for a given character is present in this font.
         *
         * @param uc the character
         *
         * @return <code>true</code> iff the glyph is present
         *
         * @see org.extex.interpreter.type.font.Font#hasGlyph(
         *      org.extex.type.UnicodeChar)
         */
        public boolean hasGlyph(final UnicodeChar uc) {

            return true;
        }

        /**
         * Setter for the ef code.
         * The ef code influences the stretchability of characters. It has a
         * positive value. 1000 means "normal" stretchability.
         *
         * @param uc the character
         * @param code the associated code
         *
         * @see org.extex.interpreter.type.font.Font#setEfCode(org.extex.type.UnicodeChar,
         *      long)
         */
        public void setEfCode(final UnicodeChar uc, final long code) {

            // TODO gene: setEfcode unimplemented
        }

        /**
         * Set the new value for the font parameter.
         *
         * @param name  The name of the parameter.
         * @param value The value to set.
         *
         * @see org.extex.interpreter.type.font.Font#setFontDimen(
         *      java.lang.String, org.extex.interpreter.type.dimen.Dimen)
         */
        public void setFontDimen(final String name, final Dimen value) {

        }

        /**
         * Set the hyphen char.
         *
         * @param h the hyphen char
         *
         * @see org.extex.interpreter.type.font.Font#setHyphenChar(
         *      org.extex.type.UnicodeChar)
         */
        public void setHyphenChar(final UnicodeChar h) {

            this.hyphen = h;
        }

        /**
         * Set the skew char.
         *
         * @param s the skew char
         *
         * @see org.extex.interpreter.type.font.Font#setSkewChar(
         *      org.extex.type.UnicodeChar)
         */
        public void setSkewChar(final UnicodeChar s) {

        }

    }

    /**
     * This mock implementation is for test purposes only.
     */
    private class MyMockContext extends MockContext {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new object.
         *
         * @throws ConfigurationException in case of an error
         */
        protected MyMockContext() {

            super();
            set(new MockFont(), true);
        }

        /**
         * Getter for the lccode mapping of upper case characters to their
         * lower case equivalent.
         *
         * @param uc the upper case character
         *
         * @return the lower case equivalent or null if none exists
         *
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
     * Make a list of Unicode characters from a String.
     *
     * @param s the string to translate
     *
     * @return the list
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
     * Set-up the test suite.
     *
     * @throws Exception in case of an error
     *
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
