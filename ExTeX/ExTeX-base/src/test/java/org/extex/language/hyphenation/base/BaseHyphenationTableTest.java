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

import java.util.Iterator;

import org.extex.color.Color;
import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.UnicodeCharList;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Muskip;
import org.extex.font.CoreFontFactory;
import org.extex.font.FontKey;
import org.extex.font.FontKeyFactory;
import org.extex.interpreter.Conditional;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.group.GroupInfo;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.context.observer.group.AfterGroupObserver;
import org.extex.interpreter.interaction.Interaction;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.unit.UnitInfo;
import org.extex.language.Language;
import org.extex.language.LanguageManager;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.language.word.impl.TeXWords;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.file.InFile;
import org.extex.scanner.type.file.OutFile;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.paragraphBuilder.ParagraphShape;
import org.extex.typesetter.tc.Direction;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.TypesettingContextImpl;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.tc.font.impl.NullFont;
import org.extex.typesetter.type.math.MathCode;
import org.extex.typesetter.type.math.MathDelimiter;
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
    private static class MyMockContext implements Context, TypesetterOptions {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new object.
         */
        protected MyMockContext() {

            set(new MockFont(), true);
        }

        @Override
        public void addUnit(UnitInfo info) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void afterGroup(AfterGroupObserver observer) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void afterGroup(Token t) throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void clearSplitMarks() {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void closeGroup(Typesetter typesetter, TokenSource source)
                throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public String esc(String name) {

            return null;
        }

        @Override
        public String esc(Token token) {

            return null;
        }

        @Override
        public UnicodeChar escapechar() {

            return null;
        }

        @Override
        public Object get(Object extension, Object key) {

            return null;
        }

        @Override
        public Token getAfterassignment() {

            return null;
        }

        @Override
        public Tokens getBottomMark(Object name) {

            return null;
        }

        @Override
        public Box getBox(String name) {

            return null;
        }

        @Override
        public Catcode getCatcode(UnicodeChar c) {

            return null;
        }

        @Override
        public Code getCode(CodeToken t) throws HelpingException {

            return null;
        }

        @Override
        public Conditional getConditional() {

            return null;
        }

        @Override
        public Count getCount(String name) {

            return null;
        }

        @Override
        public FixedCount getCountOption(String name) {

            return null;
        }

        @Override
        public MathDelimiter getDelcode(UnicodeChar c) {

            return null;
        }

        @Override
        public Dimen getDimen(String name) {

            return null;
        }

        @Override
        public FixedDimen getDimenOption(String name) {

            return null;
        }

        @Override
        public int getErrorCount() {

            return 0;
        }

        @Override
        public Tokens getFirstMark(Object name) {

            return null;
        }

        @Override
        public Font getFont(String name) {

            return null;
        }

        @Override
        public CoreFontFactory getFontFactory() {

            return null;
        }

        @Override
        public Glue getGlue(String name) {

            return null;
        }

        @Override
        public FixedGlue getGlueOption(String name) {

            return null;
        }

        @Override
        public GroupInfo[] getGroupInfos() {

            return null;
        }

        @Override
        public long getGroupLevel() {

            return 0;
        }

        @Override
        public GroupType getGroupType() {

            return null;
        }

        @Override
        public String getId() {

            return null;
        }

        @Override
        public long getIfLevel() {

            return 0;
        }

        @Override
        public InFile getInFile(String name) {

            return null;
        }

        @Override
        public Interaction getInteraction() {

            return null;
        }

        @Override
        public Language getLanguage(String language) throws HelpingException {

            return null;
        }

        @Override
        public LanguageManager getLanguageManager() {

            return null;
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

        @Override
        public long getMagnification() {

            return 0;
        }

        @Override
        public MathCode getMathcode(UnicodeChar uc) {

            return null;
        }

        @Override
        public Muskip getMuskip(String name) {

            return null;
        }

        @Override
        public String getNamespace() {

            return null;
        }

        @Override
        public OutFile getOutFile(String name) {

            return null;
        }

        @Override
        public ParagraphShape getParshape() {

            return null;
        }

        @Override
        public FixedCount getSfcode(UnicodeChar uc) {

            return null;
        }

        @Override
        public Tokens getSplitBottomMark(Object name) {

            return null;
        }

        @Override
        public Tokens getSplitFirstMark(Object name) {

            return null;
        }

        @Override
        public TokenStream getStandardTokenStream() {

            return null;
        }

        @Override
        public TokenFactory getTokenFactory() {

            return null;
        }

        @Override
        public Tokenizer getTokenizer() {

            return null;
        }

        @Override
        public Tokens getToks(String name) {

            return null;
        }

        @Override
        public Tokens getToksOrNull(String name) {

            return null;
        }

        @Override
        public Tokens getTopMark(Object name) {

            return null;
        }

        @Override
        public TypesettingContext getTypesettingContext() {

            return null;
        }

        @Override
        public TypesettingContextFactory getTypesettingContextFactory() {

            return null;
        }

        @Override
        public UnicodeChar getUccode(UnicodeChar lc) {

            return null;
        }

        @Override
        public int incrementErrorCount() {

            return 0;
        }

        @Override
        public boolean isGlobalGroup() {

            return false;
        }

        @Override
        public void openGroup(GroupType id, Locator locator, Token start)
                throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public Conditional popConditional() throws HelpingException {

            return null;
        }

        @Override
        public Direction popDirection() {

            return null;
        }

        @Override
        public void pushConditional(Locator locator, boolean value,
                Code primitive, long branch, boolean neg) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void pushDirection(Direction dir) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void set(Color color, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void set(Direction direction, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void set(Font font, boolean global) {

        }

        @Override
        public void set(Language language, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void set(Object extension, Object key, Object value,
                boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void set(TypesettingContext context, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setAfterassignment(Token token) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setBox(String name, Box value, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setCatcode(UnicodeChar c, Catcode catcode, boolean global)
                throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setCode(CodeToken t, Code code, boolean global)
                throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setCount(String name, long value, boolean global)
                throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setCountOption(String name, long value)
                throws GeneralException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setDelcode(UnicodeChar c, MathDelimiter delimiter,
                boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setDimen(String name, Dimen value, boolean global)
                throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setDimen(String name, long value, boolean global)
                throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setFont(String name, Font font, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setFontFactory(CoreFontFactory fontFactory) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setGlue(String name, Glue value, boolean global)
                throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setId(String id) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setInFile(String name, InFile file, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setInteraction(Interaction interaction)
                throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setLanguageManager(LanguageManager manager) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setLccode(UnicodeChar uc, UnicodeChar lc, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setMagnification(long mag, boolean lock)
                throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setMark(Object name, Tokens mark) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setMathcode(UnicodeChar uc, MathCode code, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setMuskip(String name, Muskip value, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setNamespace(String namespace, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setOutFile(String name, OutFile file, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setParshape(ParagraphShape shape) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setSfcode(UnicodeChar uc, Count code, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setSplitMark(Object name, Tokens mark) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setStandardTokenStream(TokenStream standardTokenStream) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setTokenFactory(TokenFactory factory) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setToks(String name, Tokens toks, boolean global)
                throws HelpingException {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void setUccode(UnicodeChar lc, UnicodeChar uc, boolean global) {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public void startMarks() {

            throw new RuntimeException("unimplemented");
        }

        @Override
        public Iterator<UnitInfo> unitIterator() {

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
