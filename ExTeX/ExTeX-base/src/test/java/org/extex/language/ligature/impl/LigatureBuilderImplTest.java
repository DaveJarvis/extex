/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.language.ligature.impl;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.extex.core.UnicodeChar;
import org.extex.core.glue.Glue;
import org.extex.language.ligature.LigatureBuilder;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextImpl;
import org.extex.typesetter.tc.font.impl.NullFont;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.GlueNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.LigatureNode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the <tt>LigatureBuilderImpl</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4527 $
 */
public class LigatureBuilderImplTest {

    /**
     * This class provides a mock implementation for a font.
     */
    private static class MockFont extends NullFont {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new object.
         */
        public MockFont() {

        }

        /**
         * Returns the ligature for two chars.
         * 
         * @param uc1 the first char
         * @param uc2 the second char
         * @return the ligature for two chars
         * 
         * @see org.extex.typesetter.tc.font.Font#getLigature(org.extex.core.UnicodeChar,
         *      org.extex.core.UnicodeChar)
         */
        @Override
        public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

            switch (uc1.getCodePoint()) {
                case 'f':
                    if (uc2.getCodePoint() == 'f') {
                        return UnicodeChar.get(CC_FF);
                    } else if (uc2.getCodePoint() == 'l') {
                        return UnicodeChar.get(CC_FL);
                    }
                    break;
                case CC_FF:
                    if (uc2.getCodePoint() == 'l') {
                        return UnicodeChar.get(CC_FFL);
                    }
                    break;
                default:
            }
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
         * @see org.extex.typesetter.tc.font.Font#hasGlyph(org.extex.core.UnicodeChar)
         */
        @Override
        public boolean hasGlyph(UnicodeChar uc) {

            switch (uc.getCodePoint()) {
                case 'a':
                case 'f':
                case 'l':
                case CC_FF:
                case CC_FL:
                case CC_FFL:
                    return true;
                default:
                    return false;
            }

        }

    }

    /**
     * The field <tt>builder</tt> contains the ligature builder.
     */
    private static LigatureBuilder builder = new LigatureBuilderImpl();

    /**
     * The constant <tt>CC_FF</tt> contains the faked FF ligature.
     */
    private static final int CC_FF = '/';

    /**
     * The constant <tt>CC_FFL</tt> contains the faked FFL ligature.
     */
    private static final int CC_FFL = '&';

    /**
     * The constant <tt>CC_FL</tt> contains the faked FL ligature..
     */
    private static final int CC_FL = '$';

    // /**
    // * The constant <tt>FF</tt> contains the ff ligature.
    // */
    // private static final UnicodeChar FF = UnicodeChar.get(CC_FF);
    // /**
    // * The constant <tt>FFL</tt> contains the ffl ligature.
    // */
    // private static final UnicodeChar FFL = UnicodeChar.get(CC_FFL);
    // /**
    // * The constant <tt>FL</tt> contains the fl ligature.
    // */
    // private static final UnicodeChar FL = UnicodeChar.get(CC_FL);
    /**
     * The field <tt>tc1</tt> contains the typesetting context.
     */
    private static TypesettingContext tc1;

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(LigatureBuilderImplTest.class);
    }

    /**
     * Set up the test suite.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        if (tc1 == null) {
            tc1 = new TypesettingContextImpl(new MockFont());
        }
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty() throws Exception {

        NodeList nodes = new HorizontalListNode();
        builder.insertLigatures(nodes, 0);
        assertEquals(0, nodes.size());
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFour1() throws Exception {

        NodeList nodes = new HorizontalListNode();
        nodes.add(new CharNode(tc1, UnicodeChar.get('a')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('f')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('l')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('f')));
        builder.insertLigatures(nodes, 0);
        assertEquals(3, nodes.size());
        assertTrue(nodes.get(0) instanceof CharNode);
        assertTrue(nodes.get(1) instanceof LigatureNode);
        LigatureNode lig = (LigatureNode) nodes.get(1);
        assertEquals(CC_FL, lig.getCharacter().getCodePoint());
        assertEquals('f', ((CharNode) nodes.get(2)).getCharacter()
            .getCodePoint());
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOne1() throws Exception {

        NodeList nodes = new HorizontalListNode();
        Node n = new CharNode(tc1, UnicodeChar.get('a'));
        nodes.add(n);
        builder.insertLigatures(nodes, 0);
        assertEquals(1, nodes.size());
        assertEquals(n, nodes.get(0));
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOne2() throws Exception {

        NodeList nodes = new HorizontalListNode();
        Node n = new GlueNode(new Glue(3), true);
        nodes.add(n);
        builder.insertLigatures(nodes, 0);
        assertEquals(1, nodes.size());
        assertEquals(n, nodes.get(0));
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testThree0() throws Exception {

        NodeList nodes = new HorizontalListNode();
        nodes.add(new CharNode(tc1, UnicodeChar.get('a')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('f')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('a')));
        builder.insertLigatures(nodes, 0);
        assertEquals(3, nodes.size());
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testThree1() throws Exception {

        NodeList nodes = new HorizontalListNode();
        nodes.add(new CharNode(tc1, UnicodeChar.get('f')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('f')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('l')));
        builder.insertLigatures(nodes, 0);
        assertEquals(1, nodes.size());
        assertTrue(nodes.get(0) instanceof LigatureNode);
        LigatureNode lig = (LigatureNode) nodes.get(0);
        assertEquals(CC_FFL, lig.getCharacter().getCodePoint());
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testThree2() throws Exception {

        NodeList nodes = new HorizontalListNode();
        nodes.add(new CharNode(tc1, UnicodeChar.get('a')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('f')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('l')));
        builder.insertLigatures(nodes, 0);
        assertEquals(2, nodes.size());
        assertTrue(nodes.get(0) instanceof CharNode);
        assertTrue(nodes.get(1) instanceof LigatureNode);
        LigatureNode lig = (LigatureNode) nodes.get(1);
        assertEquals(CC_FL, lig.getCharacter().getCodePoint());
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTwo1() throws Exception {

        NodeList nodes = new HorizontalListNode();
        nodes.add(new CharNode(tc1, UnicodeChar.get('f')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('f')));
        builder.insertLigatures(nodes, 0);
        assertEquals(1, nodes.size());
        assertTrue(nodes.get(0) instanceof LigatureNode);
        LigatureNode lig = (LigatureNode) nodes.get(0);
        assertEquals(CC_FF, lig.getCharacter().getCodePoint());
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTwo2() throws Exception {

        NodeList nodes = new HorizontalListNode();
        nodes.add(new CharNode(tc1, UnicodeChar.get('f')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('a')));
        builder.insertLigatures(nodes, 0);
        assertEquals(2, nodes.size());
    }

}