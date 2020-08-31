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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.extex.language.Language;
import org.extex.language.hyphenation.exception.HyphenationException;
import org.extex.logging.LogFormatter;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.TypesetterOptions;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.tc.TypesettingContextImpl;
import org.extex.typesetter.tc.font.Font;
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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is the abstract base class to test a paragraph builder.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractParagraphBuiderTester {

    /**
     * Inner class for the typesetter options.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    private static class MockOptions implements TypesetterOptions {

    @Override
        public FixedCount getCountOption(String name) {

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

    @Override
        public FixedDimen getDimenOption(String name) {

            if (name.equals("hsize")) {
                return new Dimen(Dimen.ONE * 23);
            }
            return new Dimen(0);
        }

    @Override
        public Font getFont(String name) {

            return null;
        }

    @Override
        public FixedGlue getGlueOption(String name) {

            if (name.equals("parfillskip")) {
                return new Glue(1000);
            }
            return new Glue(0);
        }

    @Override
        public UnicodeChar getLccode(UnicodeChar uc) {

            return null;
        }

    @Override
        public Muskip getMuskip(String name) {

            throw new RuntimeException("unimplemented");
        }

    @Override
        public String getNamespace() {

            return null;
        }

    @Override
        public ParagraphShape getParshape() {

            return null;
        }

    @Override
        public TokenFactory getTokenFactory() {

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

        /**
    *      long)
         */
        @Override
        public void setCountOption(String name, long value)
                throws GeneralException {

            // not needed
        }

    @Override
        public void setParshape(ParagraphShape shape) {

            // not needed
        }
    }

    /**
     * The field {@code pDisc} contains the pattern to discover a
     * discretionary.
     */
    private static Pattern pDisc = null;

    /**
     * The field {@code pGlue} contains the pattern to discover a glue.
     */
    private static Pattern pGlue = null;

    /**
     * The field {@code pRule} contains the pattern to discover a rule.
     */
    private static Pattern pRule = null;

    /**
     * The field {@code tracer} contains the logger for the output.
     */
    private static Logger tracer = null;

  /**
     * The field {@code VPT} contains the constant for 5pt.
     */
    protected static final Dimen VPT = new Dimen(Dimen.ONE * 5);

    /**
     * The field {@code pb} contains the paragraph builder to test.
     */
    private ParagraphBuilder pb;

    /**
     * The field {@code tc} contains the mock typesetting context.
     */
    private final TypesettingContextImpl tc = new TypesettingContextImpl( new Font() {

        /**
         * The field {@code serialVersionUID} contains the version number.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The field {@code hyphenChar} contains the hyphen character.
         */
        private UnicodeChar hyphenChar = UnicodeChar.get('-');

        /**
         * The field {@code skewChar} contains the skew character.
         */
        private UnicodeChar skewChar = null;

    @Override
        public FontKey getActualFontKey() {

            return null;
        }

    @Override
        public FixedDimen getActualSize() {

            return VPT;
        }

    @Override
        public int getCheckSum() {

            return 0;
        }

    @Override
        public FixedGlue getDepth(UnicodeChar uc) {

            return null;
        }

    @Override
        public FixedDimen getDesignSize() {

            return VPT;
        }

    @Override
        public long getEfCode(UnicodeChar uc) {

            return 1000;
        }

    @Override
        public FixedDimen getEm() {

            return VPT;
        }

    @Override
        public FixedDimen getEx() {

            return VPT;
        }

    @Override
        public FixedDimen getFontDimen(String key) {

            return Dimen.ZERO_PT;
        }

    @Override
        public FontKey getFontKey() {

            return null;
        }

    @Override
        public String getFontName() {

            return "fnt";
        }

    @Override
        public FixedGlue getHeight(UnicodeChar uc) {

            return null;
        }

    @Override
        public UnicodeChar getHyphenChar() {

            return hyphenChar;
        }

    @Override
        public FixedDimen getItalicCorrection(UnicodeChar uc) {

            return null;
        }

        /**
    *      org.extex.core.UnicodeChar)
         */
        @Override
        public FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2) {

            return null;
        }

        /**
    *      org.extex.core.UnicodeChar)
         */
        @Override
        public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

            return null;
        }

    @Override
        public FixedCount getScaleFactor() {

            return Count.THOUSAND;
        }

    @Override
        public UnicodeChar getSkewChar() {

            return skewChar;
        }

    @Override
        public FixedGlue getSpace() {

            return null;
        }

    @Override
        public FixedGlue getWidth(UnicodeChar uc) {

            return null;
        }

    @Override
        public boolean hasGlyph(UnicodeChar uc) {

            return true;
        }

        /**
    *      long)
         */
        @Override
        public void setEfCode(UnicodeChar uc, long code) {


        }

        /**
    *      org.extex.core.dimen.Dimen)
         */
        @Override
        public void setFontDimen(String key, Dimen value) {


        }

    @Override
        public void setHyphenChar(UnicodeChar hyphen) {

            hyphenChar = hyphen;
        }

    @Override
        public void setSkewChar(UnicodeChar skew) {

            skewChar = skew;
        }

    });
    {
        tc.setLanguage(new Language() {

            /**
             * The field {@code serialVersionUID} contains the version number.
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void addHyphenation(UnicodeCharList word,
                    TypesetterOptions context) throws HyphenationException {

                throw new RuntimeException("unimplemented");
            }

            @Override
            public void addPattern(Tokens pattern) throws HyphenationException {

                throw new RuntimeException("unimplemented");
            }

            @Override
            public int findWord(NodeList nodes, int start, UnicodeCharList word)
                    throws HyphenationException {

                throw new RuntimeException("unimplemented");
            }

        @Override
            public long getLeftHyphenMin() throws HyphenationException {

                return 0;
            }

            @Override
            public UnicodeChar getLigature(UnicodeChar c1, UnicodeChar c2,
                    Font f) throws HyphenationException {

                return f.getLigature(c1, c2);
            }

            @Override
            public String getName() {

                throw new RuntimeException("unimplemented");
            }

            @Override
            public long getRightHyphenMin() throws HyphenationException {

                return 0;
            }

            @Override
            public boolean hyphenate(NodeList nodelist,
                    TypesetterOptions context, UnicodeChar hyphen, int start,
                    boolean forall, NodeFactory nodeFactory)
                    throws HyphenationException {

                return false;
            }

            @Override
            public int insertLigatures(NodeList list, int start)
                    throws HyphenationException {

                return 0;
            }

            @Override
            public void insertShy(NodeList nodes, int insertionPoint,
                    boolean[] spec, CharNode hyphenNode)
                    throws HyphenationException {

                throw new RuntimeException("unimplemented");
            }

            @Override
            public boolean isHyphenating() throws HyphenationException {

                return false;
            }

            @Override
            public UnicodeCharList normalize(UnicodeCharList word,
                    TypesetterOptions options) throws HyphenationException {

                throw new RuntimeException("unimplemented");
            }

            @Override
            public void setHyphenating(boolean active)
                    throws HyphenationException {

                // not needed
            }

            @Override
            public void setLeftHyphenMin(long left) throws HyphenationException {

                // not needed
            }

            @Override
            public void setName(String name) {

                throw new RuntimeException("unimplemented");
            }

            @Override
            public void setRightHyphenMin(long right)
                    throws HyphenationException {

                // not needed
            }

        });
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
    protected HorizontalListNode makeList(String spec) {

        if (pDisc == null) {
            pDisc =
                    Pattern.compile("^\\\\discretionary"
                            + "\\{([^{}]*)\\}\\{([^{}]*)\\}"
                            + "\\{([^{}]*)\\}(.*)");
            pRule = Pattern.compile("^\\\\rule\\{([^{}]*)\\}(.*)");
            pGlue = Pattern.compile("^\\\\glue(.*)");
        }

        String s = spec;
        HorizontalListNode nodes = new HorizontalListNode();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                nodes.add(new SpaceNode(new Glue(Dimen.ONE_PT)));
            } else if (c == '\\') {
                Matcher m = pDisc.matcher(s.substring(i));
                if (m.matches()) {
                    nodes.add(new DiscretionaryNode(makeList(m.group(1)),
                        makeList(m.group(2)), makeList(m.group(3))));
                    s = m.group(4);
                    i = -1;
                    continue;
                }
                m = pRule.matcher(s.substring(i));
                if (m.matches()) {
                    nodes.add(new RuleNode(VPT, Dimen.ONE_PT, Dimen.ONE_PT, tc,
                        true));
                    s = m.group(2);
                    i = -1;
                    continue;
                }
                m = pGlue.matcher(s.substring(i));
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
     * Set up for each test case.
     */
    @Before
    public void setUp() {

        if (tracer == null) {
            tracer = Logger.getLogger(
                AbstractParagraphBuiderTester.class.getName());
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
     *  Test case checking that ...
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void test4() throws Exception {

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
     *  Test case checking that ...
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void test5() throws Exception {

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
     *  Test case checking that ...
     * 
     * @throws Exception in case of an error
     */
    @Test
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

        StringBuilder sb = new StringBuilder();
        list.toString(sb, "\n", Integer.MAX_VALUE, Integer.MAX_VALUE);
        tracer.info(sb.toString());
    }

    /**
     *  Test case checking that ...
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testBreak1() throws Exception {

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
     *  Test case checking that ...
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testBreak2() throws Exception {

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
     * Test case checking that discretionary without content may be contained in the non-broken text
* 
     * @throws Exception in case of an error
     */
    @Test
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

        // assertTrue(list instanceof VerticalListNode);
        // assertEquals(2, list.size());
        assertEquals("\\vbox(0.0pt+0.0pt)x23.0pt\n"
                + ".\\hbox(0.0pt+0.0pt)x23.0pt\n"
                + "..a\n"
                + "..b\n"
                + "..space 1.0pt\n"
                + "..c\n"
                + "..\\discretionary{}{}{}\n"
                + "..d\n"
                + "..space 1.0pt\n"
                + "..e\n"
                + "..\\penalty 10000\n"
                + "..\\glue0.01526pt", list.toString());
    }

    /**
     * Test case checking that discretionary without content may be contained in the non-broken text
* 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testDisc2() throws Exception {

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
     *  Test case checking that the empty list is treated correctly.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty1() throws Exception {

        NodeList list = pb.build(new HorizontalListNode());

        assertTrue(list instanceof VerticalListNode);
        assertEquals(0, list.size());
    }

    /**
     * This method provides an indicator whether or not the tracing should be
     * written to the console. This method is meant to be overwritten by derived
     * classes to change the default behavior.
     * 
     * @return {@code true} iff the tracing is requested
     */
    protected boolean traceonline() {

        return false;
    }

}
