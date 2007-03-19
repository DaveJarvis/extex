/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.paragraphBuilder;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.extex.core.UnicodeChar;
import org.extex.core.UnicodeCharList;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Muskip;
import org.extex.font.FontKey;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.context.tc.TypesettingContextFactory;
import org.extex.interpreter.context.tc.TypesettingContextImpl;
import org.extex.interpreter.type.font.Font;
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.logging.LogFormatter;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.DiscretionaryNode;
import org.extex.typesetter.type.node.GlueNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.PenaltyNode;
import org.extex.typesetter.type.node.RuleNode;
import org.extex.typesetter.type.node.SpaceNode;
import org.extex.typesetter.type.node.VerticalListNode;
import org.extex.typesetter.type.node.factory.NodeFactory;

/**
 * This is the abstract base class to test a paragraph builder.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractParagraphBuiderTester extends TestCase {

    /**
     * Inner class for the typesetter options.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    private static class MockOptions implements TypesetterOptions {

        /**
         * Getter for a count register.
         *
         * @param name the name of the register
         *
         * @return the content of the count register
         *
         * @see org.extex.typesetter.TypesetterOptions#getCountOption(java.lang.String)
         */
        public FixedCount getCountOption(final String name) {

            if (name.equals("tracingparagraphs")) {
                return new Count(1);
            } else if (name.equals("pretolerance")) {
                return new Count(300);
            } else if (name.equals("tolerance")) {
                return new Count(10);
            } else if (name.equals("tolerance")) {
                return new Count(200);
            } else if (name.equals("hyphenpenalty")) {
                return new Count(20);
            } else if (name.equals("exhyphenpenalty")) {
                return new Count(30);
            }
            return new Count(0);
        }

        /**
         * Getter for a dimen register.
         *
         * @param name the name of the register
         *
         * @return the content of the dimen register
         *
         * @see org.extex.typesetter.TypesetterOptions#getDimenOption(java.lang.String)
         */
        public FixedDimen getDimenOption(final String name) {

            if (name.equals("hsize")) {
                return new Dimen(Dimen.ONE * 23);
            }
            return new Dimen(0);
        }

        /**
         * Getter for a current font register.
         *
         * @param name the name or the number of the register
         *
         * @return the named font register or <code>null</code> if none is set
         *
         * @see org.extex.typesetter.TypesetterOptions#getFont(java.lang.String)
         */
        public Font getFont(final String name) {

            return null;
        }

        /**
         * Getter for a glue register.
         *
         * @param name the name of the register
         *
         * @return the content of the glue register
         *
         * @see org.extex.typesetter.TypesetterOptions#getGlueOption(java.lang.String)
         */
        public FixedGlue getGlueOption(final String name) {

            if (name.equals("parfillskip")) {
                return new Glue(1000);
            }
            return new Glue(0);
        }

        /**
         * Getter for the lccode mapping of upper case characters to their
         * lower case equivalent.
         *
         * @param uc the upper case character
         *
         * @return the lower case equivalent or null if none exists
         *
         * @see org.extex.typesetter.TypesetterOptions#getLccode(org.extex.core.UnicodeChar)
         */
        public UnicodeChar getLccode(final UnicodeChar uc) {

            return null;
        }

        /**
         * Getter for a muskip register.
         *
         * @param name the name of the register
         *
         * @return te muskip register value
         *
         * @see org.extex.typesetter.TypesetterOptions#getMuskip(java.lang.String)
         */
        public Muskip getMuskip(final String name) {

            throw new RuntimeException("unimplemented");
        }

        /**
         * Getter for the current name space.
         *
         * @return the current name space
         *
         * @see org.extex.typesetter.TypesetterOptions#getNamespace()
         */
        public String getNamespace() {

            return null;
        }

        /**
         * Getter for the paragraph shape.
         *
         * @return the paragraph shape or <code>null</code> if no special shape
         *   is present
         *
         * @see org.extex.typesetter.TypesetterOptions#getParshape()
         */
        public ParagraphShape getParshape() {

            return null;
        }

        /**
         * Getter for the token factory. The token factory can be used to get new
         * tokens of some kind.
         *
         * @return the token factory
         *
         * @see org.extex.typesetter.TypesetterOptions#getTokenFactory()
         */
        public TokenFactory getTokenFactory() {

            return null;
        }

        /**
         * Getter for the typesetting context.
         *
         * @return the typesetting context
         *
         * @see org.extex.typesetter.TypesetterOptions#getTypesettingContext()
         */
        public TypesettingContext getTypesettingContext() {

            return null;
        }

        /**
         * Getter for the typesetting context factory.
         *
         * @return the typesetting context factory
         *
         * @see org.extex.typesetter.TypesetterOptions#getTypesettingContextFactory()
         */
        public TypesettingContextFactory getTypesettingContextFactory() {

            return null;
        }

        /**
         * Setter for a count register.
         *
         * @param name the name of the register
         * @param value the value
         *
         * @throws GeneralException in case of an error
         *
         * @see org.extex.typesetter.TypesetterOptions#setCountOption(
         *      java.lang.String,
         *      org.extex.core.count.FixedCount)
         */
        public void setCountOption(final String name, final long value)
                throws GeneralException {

        }

        /**
         * Setter for the paragraph shape.
         *
         * @param shape the new paragraph shape
         *
         * @see org.extex.typesetter.TypesetterOptions#setParshape(
         *      org.extex.typesetter.paragraphBuilder.ParagraphShape)
         */
        public void setParshape(final ParagraphShape shape) {

        }
    }

    /**
     * The field <tt>p1</tt> contains the ...
     */
    private static Pattern p1 = null;

    /**
     * The field <tt>p2</tt> contains the ...
     */
    private static Pattern p2 = null;

    /**
     * The field <tt>p3</tt> contains the ...
     */
    private static Pattern p3 = null;

    /**
     * The field <tt>tracer</tt> contains the logger for the output.
     */
    private static Logger tracer = null;;

    /**
     * The field <tt>VPT</tt> contains the constant for 5pt.
     */
    protected static final Dimen VPT = new Dimen(Dimen.ONE * 5);

    /**
     * The field <tt>pb</tt> contains the paragraph builder to test.
     */
    private ParagraphBuilder pb;

    /**
     * The field <tt>tc</tt> contains the mock typesetting context.
     */
    private TypesettingContextImpl tc = new TypesettingContextImpl(new Font() {

        /**
         * The field <tt>hyphenChar</tt> contains the hyphen character.
         */
        private UnicodeChar hyphenChar = UnicodeChar.get('-');

        /**
         * The field <tt>skewChar</tt> contains the skew character.
         */
        private UnicodeChar skewChar = null;

        /**
         * Returns the actual size.
         *
         * @return the actual size
         *
         * @see org.extex.interpreter.type.font.Font#getActualSize()
         */
        public FixedDimen getActualSize() {

            return VPT;
        }

        /**
         * Returns the check sum of the font.
         *
         * @return the check sum of the font
         *
         * @see org.extex.interpreter.type.font.Font#getCheckSum()
         */
        public int getCheckSum() {

            return 0;
        }

        /**
         * Returns the depth of the character.
         *
         * @param uc the character
         *
         * @return the depth of the character
         *
         * @see org.extex.interpreter.type.font.Font#getDepth(org.extex.core.UnicodeChar)
         */
        public FixedGlue getDepth(final UnicodeChar uc) {

            return null;
        }

        /**
         * Returns the design size of the font.
         *
         * @return the design size of the font
         *
         * @see org.extex.interpreter.type.font.Font#getDesignSize()
         */
        public FixedDimen getDesignSize() {

            return VPT;
        }

        /**
         * Getter for the ef code.
         *
         * @param uc the character
         *
         * @return the ef code
         *
         * @see org.extex.interpreter.type.font.Font#getEfCode(org.extex.core.UnicodeChar)
         */
        public long getEfCode(final UnicodeChar uc) {

            return 1000;
        }

        /**
         * Returns the size of 1em.
         *
         * @return the size of 1em.
         *
         * @see org.extex.interpreter.type.font.Font#getEm()
         */
        public FixedDimen getEm() {

            return VPT;
        }

        /**
         * Returns the size of 1ex.
         *
         * @return the size of 1ex.
         *
         * @see org.extex.interpreter.type.font.Font#getEx()
         */
        public FixedDimen getEx() {

            return VPT;
        }

        /**
         * Returns the size of the parameter with the name 'name'.
         *
         * @param name the name of the parameter
         *
         * @return the size of the parameter with the name 'name'.
         *
         * @see org.extex.interpreter.type.font.Font#getFontDimen(java.lang.String)
         */
        public FixedDimen getFontDimen(final String key) {

            return Dimen.ZERO_PT;
        }

        /**
         * Returns the FontKey for this font.
         *
         * @return the FontKey for this font
         *
         * @see org.extex.interpreter.type.font.Font#getFontKey()
         */
        public FontKey getFontKey() {

            return null;
        }

        /**
         * Returns the name of the font.
         *
         * @return the name of the font
         *
         * @see org.extex.interpreter.type.font.Font#getFontName()
         */
        public String getFontName() {

            return "fnt";
        }

        /**
         * Returns the height of a character.
         *
         * @param uc the character
         *
         * @return the height of the character
         *
         * @see org.extex.interpreter.type.font.Font#getHeight(org.extex.core.UnicodeChar)
         */
        public FixedGlue getHeight(final UnicodeChar uc) {

            return null;
        }

        /**
         * Returns the hyphen character.
         *
         * @return the hyphen character
         *
         * @see org.extex.interpreter.type.font.Font#getHyphenChar()
         */
        public UnicodeChar getHyphenChar() {

            return hyphenChar;
        }

        /**
         * Returns the italic correction of a character.
         *
         * @param uc the character
         *
         * @return the italic correction of the character
         *
         * @see org.extex.interpreter.type.font.Font#getItalicCorrection(org.extex.core.UnicodeChar)
         */
        public FixedDimen getItalicCorrection(final UnicodeChar uc) {

            return null;
        }

        /**
         * Returns the kerning between two characters.
         *
         * @param uc1 the first character
         * @param uc2 the second character
         *
         * @return the kerning between two characters
         *
         * @see org.extex.interpreter.type.font.Font#getKerning(
         *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar)
         */
        public FixedDimen getKerning(final UnicodeChar uc1,
                final UnicodeChar uc2) {

            return null;
        }

        /**
         * Returns the ligature for two characters.
         *
         * @param uc1 the first character
         * @param uc2 the second character
         *
         * @return Returns the ligature for two characters
         *
         * @see org.extex.interpreter.type.font.Font#getLigature(
         *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar)
         */
        public UnicodeChar getLigature(final UnicodeChar uc1,
                final UnicodeChar uc2) {

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

            return skewChar;
        }

        /**
         * Returns the size of the 'space'.
         *
         * @return the size of the 'space'.
         *
         * @see org.extex.interpreter.type.font.Font#getSpace()
         */
        public FixedGlue getSpace() {

            return null;
        }

        /**
         * Returns the width of a character.
         *
         * @param uc the character
         *
         * @return the width of the character
         *
         * @see org.extex.interpreter.type.font.Font#getWidth(org.extex.core.UnicodeChar)
         */
        public FixedGlue getWidth(final UnicodeChar uc) {

            return null;
        }

        /**
         * Determine whether the glyph for a given character is present in this
         * font.
         *
         * @param uc the character
         *
         * @return <code>true</code> iff the glyph is present
         *
         * @see org.extex.interpreter.type.font.Font#hasGlyph(org.extex.core.UnicodeChar)
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
         * @see org.extex.interpreter.type.font.Font#setEfCode(
         *      org.extex.core.UnicodeChar, long)
         */
        public void setEfCode(final UnicodeChar uc, final long code) {

            // TODO gene: setEfcode unimplemented

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
        public void setFontDimen(final String key, final Dimen value) {

        }

        /**
         * Set the hyphen character.
         *
         * @param uc the hyphen character
         *
         * @see org.extex.interpreter.type.font.Font#setHyphenChar(
         *      org.extex.core.UnicodeChar)
         */
        public void setHyphenChar(final UnicodeChar hyphen) {

            hyphenChar = hyphen;
        }

        /**
         * Set the skew character.
         *
         * @param uc the skew character
         *
         * @see org.extex.interpreter.type.font.Font#setSkewChar(org.extex.core.UnicodeChar)
         */
        public void setSkewChar(final UnicodeChar skew) {

            skewChar = skew;
        }

        /**
         * Returns the scale factor of the font.
         *
         * @return the scale factor of the font
         *
         * @see org.extex.interpreter.type.font.Font#getScaleFactor()
         */
        public FixedCount getScaleFactor() {

            return Count.THOUSAND;
        }

        /**
         * Returns the actual FontKey for this font.
         * The font key may differ from the one requested.
         *
         * @return the actual FontKey for this font.
         *
         * @see org.extex.interpreter.type.font.Font#getActualFontKey()
         */
        public FontKey getActualFontKey() {

            return null;
        }

    });
    {
        tc.setLanguage(new Language() {

            /**
             * The field <tt>serialVersionUID</tt> contains the ...
             */
            private static final long serialVersionUID = 1L;

            public void addHyphenation(final UnicodeCharList word,
                    final TypesetterOptions context)
                    throws HyphenationException {

                throw new RuntimeException("unimplemented");
            }

            public void addPattern(final Tokens pattern)
                    throws HyphenationException {

                throw new RuntimeException("unimplemented");
            }

            public int findWord(final NodeList nodes, final int start,
                    final UnicodeCharList word) throws HyphenationException {

                throw new RuntimeException("unimplemented");
            }

            public long getLeftHyphenmin() throws HyphenationException {

                return 0;
            }

            public UnicodeChar getLigature(final UnicodeChar c1,
                    final UnicodeChar c2, Font f) throws HyphenationException {

                return f.getLigature(c1, c2);
            }

            public String getName() {

                throw new RuntimeException("unimplemented");
            }

            public long getRightHyphenmin() throws HyphenationException {

                return 0;
            }

            public boolean hyphenate(NodeList nodelist,
                    TypesetterOptions context, UnicodeChar hyphen, int start,
                    boolean forall, NodeFactory nodeFactory)
                    throws HyphenationException {

                // TODO gene: hyphenate unimplemented
                return false;
            }

            public int insertLigatures(final NodeList list, final int start)
                    throws HyphenationException {

                // TODO gene: insertLigatures unimplemented
                return 0;
            }

            public void insertShy(final NodeList nodes,
                    final int insertionPoint, final boolean[] spec,
                    final CharNode hyphenNode) throws HyphenationException {

                throw new RuntimeException("unimplemented");
            }

            public boolean isHyphenActive() throws HyphenationException {

                return false;
            }

            public UnicodeCharList normalize(final UnicodeCharList word,
                    final TypesetterOptions options)
                    throws HyphenationException {

                throw new RuntimeException("unimplemented");
            }

            public void setHyphenActive(final boolean active)
                    throws HyphenationException {

            }

            public void setLeftHyphenmin(final long left)
                    throws HyphenationException {

            }

            public void setName(final String name) {

                throw new RuntimeException("unimplemented");
            }

            public void setRightHyphenmin(final long right)
                    throws HyphenationException {

            }

        });
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void _test4() throws Exception {

        HorizontalListNode nodes = new HorizontalListNode();
        nodes.add(new GlueNode(VPT, true));
        nodes.add(new CharNode(tc, UnicodeChar.get('a')));
        nodes.add(new CharNode(tc, UnicodeChar.get('b')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('c')));
        nodes.add(new CharNode(tc, UnicodeChar.get('d')));
        nodes.add(new DiscretionaryNode(null, null, null));
        nodes.add(new CharNode(tc, UnicodeChar.get('e')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('f')));

        NodeList list = pb.build(nodes);

        assertTrue(list instanceof VerticalListNode);
        assertEquals(2, list.size());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void _test5() throws Exception {

        HorizontalListNode nodes = new HorizontalListNode();
        nodes.add(new GlueNode(VPT, true));
        nodes.add(new CharNode(tc, UnicodeChar.get('a')));
        nodes.add(new CharNode(tc, UnicodeChar.get('b')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('c')));
        nodes.add(new CharNode(tc, UnicodeChar.get('d')));
        nodes.add(new DiscretionaryNode(new HorizontalListNode(),
            new HorizontalListNode(), new HorizontalListNode()));
        nodes.add(new CharNode(tc, UnicodeChar.get('e')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('f')));

        NodeList list = pb.build(nodes);

        assertTrue(list instanceof VerticalListNode);
        assertEquals(2, list.size());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void _testBreak1() throws Exception {

        NodeList list = pb.build(makeList("a"));

        assertTrue(list instanceof VerticalListNode);
        assertEquals(1, list.size());

        Node node = list.get(0);
        assertTrue(node instanceof HorizontalListNode);
        list = (NodeList) node;
        assertTrue(list.get(0) instanceof CharNode);
        assertTrue(list.get(1) instanceof PenaltyNode);
        assertTrue(list.get(2) instanceof GlueNode);
        assertEquals(3, list.size());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void _testBreak2() throws Exception {

        HorizontalListNode nodes = new HorizontalListNode();
        nodes.add(new GlueNode(VPT, true));
        nodes.add(new CharNode(tc, UnicodeChar.get('a')));
        nodes.add(new CharNode(tc, UnicodeChar.get('b')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('c')));
        nodes.add(new CharNode(tc, UnicodeChar.get('d')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('e')));

        NodeList list = pb.build(nodes);

        assertTrue(list instanceof VerticalListNode);
        assertEquals(2, list.size());
    }

    /**
     * <testcase>
     *  Test case checking that discretionary without content may be contained
     *  in the non-broken text.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void _testDisc2() throws Exception {

        HorizontalListNode nodes = new HorizontalListNode();
        nodes.add(new GlueNode(VPT, true));
        nodes.add(new CharNode(tc, UnicodeChar.get('a')));
        nodes.add(new CharNode(tc, UnicodeChar.get('b')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('c')));
        nodes.add(new DiscretionaryNode(new HorizontalListNode(),
            new HorizontalListNode(), new HorizontalListNode()));
        nodes.add(new CharNode(tc, UnicodeChar.get('d')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('e')));

        NodeList list = pb.build(nodes);

        assertTrue(list instanceof VerticalListNode);
        assertEquals(2, list.size());
    }

    /**
     * <testcase>
     *  Test case checking that the empty list is treated correctly.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void _testEmpty1() throws Exception {

        NodeList list = pb.build(makeList(""));

        assertTrue(list instanceof VerticalListNode);
        assertEquals(0, list.size());
    }

    /**
     * This method creates a new paragraph builder to be tested.
     *
     * @return the new paragraph builder
     */
    protected abstract ParagraphBuilder getParagraphBuilder();

    /**
     * Build a node list from a string specification.
     *
     * @param spec the spec
     * @return the node list
     */
    protected HorizontalListNode makeList(final String spec) {

        String s = spec;
        if (p1 == null) {
            p1 =
                    Pattern
                        .compile("^\\\\discretionary\\{([^{}]*)\\}\\{([^{}]*)\\}\\{([^{}]*)\\}(.*)");
            p2 = Pattern.compile("^\\\\rule\\{([^{}]*)\\}(.*)");
            p3 = Pattern.compile("^\\\\glue(.*)");
        }
        HorizontalListNode nodes = new HorizontalListNode();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
            } else if (c == '\\') {
                Matcher m = p1.matcher(s.substring(i));
                if (m.matches()) {
                    nodes.add(new DiscretionaryNode(makeList(m.group(1)),
                        makeList(m.group(2)), makeList(m.group(3))));
                    s = m.group(4);
                    i = -1;
                    continue;
                }
                m = p2.matcher(s.substring(i));
                if (m.matches()) {
                    nodes.add(new RuleNode(VPT, Dimen.ONE_PT, Dimen.ONE_PT, tc,
                        true));
                    s = m.group(2);
                    i = -1;
                    continue;
                }
                m = p3.matcher(s.substring(i));
                if (m.matches()) {
                    nodes.add(new GlueNode(Dimen.ONE_PT, true));
                    s = m.group(1);
                    i = -1;
                    continue;
                }
            } else {
                nodes.add(new CharNode(tc, UnicodeChar.get('a')));
            }
        }
        return nodes;
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

        super.setUp();

        if (tracer == null) {
            tracer =
                    Logger.getLogger(AbstractParagraphBuiderTester.class
                        .getName());
            tracer.setUseParentHandlers(false);
            if (traceonline()) {
                Handler handler = new ConsoleHandler();
                handler.setLevel(Level.ALL);
                handler.setFormatter(new LogFormatter());
                tracer.addHandler(handler);
                tracer.setLevel(Level.ALL);
            }
        }

        pb = getParagraphBuilder();
        if (pb instanceof LogEnabled) {
            ((LogEnabled) pb).enableLogging(tracer);
        }
        pb.setOptions(new MockOptions());
    }

    /**
     * <testcase>
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test6() throws Exception {

        HorizontalListNode nodes = new HorizontalListNode();
        nodes.add(new GlueNode(VPT, true));
        nodes.add(new CharNode(tc, UnicodeChar.get('a')));
        nodes.add(new CharNode(tc, UnicodeChar.get('b')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('c')));
        nodes.add(new CharNode(tc, UnicodeChar.get('d')));
        nodes.add(new DiscretionaryNode(new HorizontalListNode(new RuleNode(
            new Dimen(0x20), Dimen.ZERO_PT, Dimen.ZERO_PT, tc, true)),
            new HorizontalListNode(new RuleNode(new Dimen(0x30), Dimen.ZERO_PT,
                Dimen.ZERO_PT, tc, true)), new HorizontalListNode(new RuleNode(
                new Dimen(0x40), Dimen.ZERO_PT, Dimen.ZERO_PT, tc, true))));
        nodes.add(new CharNode(tc, UnicodeChar.get('e')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('f')));

        NodeList list = pb.build(nodes);

        assertTrue(list instanceof VerticalListNode);
        assertEquals(2, list.size());

        Node nl = list.get(0);
        assertTrue(nl instanceof HorizontalListNode);
        assertEquals(8, ((HorizontalListNode) nl).size());
        assertTrue(((HorizontalListNode) nl).get(6) instanceof RuleNode);

        nl = list.get(1);
        assertTrue(nl instanceof HorizontalListNode);
        assertEquals(6, ((HorizontalListNode) nl).size());
        assertTrue(((HorizontalListNode) nl).get(0) instanceof RuleNode);

        StringBuffer sb = new StringBuffer();
        list.toString(sb, "\n", Integer.MAX_VALUE, Integer.MAX_VALUE);
        tracer.info(sb.toString());
    }

    /**
     * <testcase>
     *  Test case checking that discretionary without content may be contained
     *  in the non-broken text.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testDisc1() throws Exception {

        HorizontalListNode nodes = new HorizontalListNode();
        nodes.add(new GlueNode(VPT, true));
        nodes.add(new CharNode(tc, UnicodeChar.get('a')));
        nodes.add(new CharNode(tc, UnicodeChar.get('b')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('c')));
        nodes.add(new DiscretionaryNode(null, null, null));
        nodes.add(new CharNode(tc, UnicodeChar.get('d')));
        nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
        nodes.add(new CharNode(tc, UnicodeChar.get('e')));

        NodeList list = pb.build(nodes);

        assertTrue(list instanceof VerticalListNode);
        assertEquals(2, list.size());
    }

    /**
     * This method provides an indicator whether or not the tracing should be
     * written to the console.
     * This method is meant to be overwritten by derived classes to change the
     * default behavior.
     *
     * @return <code>true</code> iff the tracing is requested
     */
    protected boolean traceonline() {

        return false;
    }

}
