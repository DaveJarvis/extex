/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

import junit.framework.TestCase;

import org.extex.font.type.other.NullFont;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.context.tc.TypesettingContextImpl;
import org.extex.interpreter.type.glue.Glue;
import org.extex.language.ligature.LigatureBuilder;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.GlueNode;
import org.extex.typesetter.type.node.HorizontalListNode;
import org.extex.typesetter.type.node.LigatureNode;

/**
 * This is a test suite for the <tt>LigatureBuilderImpl</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4527 $
 */
public class LigatureBuilderImplTest extends TestCase {

    /**
     * This class provides a mock implementation for a font.
     */
    private class MockFont extends NullFont {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for serialization.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new object.
         */
        public MockFont() {

            super();
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

        /**
         * Returns the ligature for two chars.
         *
         * @param uc1 the first char
         * @param uc2 the second char
         * @return the ligature for two chars
         *
         * @see org.extex.interpreter.type.font.Font#getLigature(
         *      org.extex.type.UnicodeChar,
         *      org.extex.type.UnicodeChar)
         */
        public UnicodeChar getLigature(final UnicodeChar uc1,
                final UnicodeChar uc2) {

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

    }

    /**
     * The field <tt>builder</tt> contains the ligature builder.
     */
    private static LigatureBuilder builder = new LigatureBuilderImpl();

    /**
     * The constant <tt>CC_FF</tt> contains the ...
     */
    private static final int CC_FF = '§';

    /**
     * The constant <tt>CC_FFL</tt> contains the ...
     */
    private static final int CC_FFL = '&';

    /**
     * The constant <tt>CC_FL</tt> contains the ...
     */
    private static final int CC_FL = '$';

    /**
     * The constant <tt>FF</tt> contains the ...
     */
    private static final UnicodeChar FF = UnicodeChar.get(CC_FF);

    /**
     * The constant <tt>FFL</tt> contains the ...
     */
    private static final UnicodeChar FFL = UnicodeChar.get(CC_FFL);

    /**
     * The constant <tt>FL</tt> contains the ...
     */
    private static final UnicodeChar FL = UnicodeChar.get(CC_FL);

    /**
     * The field <tt>tc1</tt> contains the typesetting context.
     */
    private static TypesettingContext tc1;

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(LigatureBuilderImpl.class);
    }

    /**
     * Set up the test suite.
     *
     * @throws Exception in case of an error
     *
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

        super.setUp();
        tc1 = new TypesettingContextImpl(new MockFont());
    }

    /**
     * ...
     * @throws Exception in case of an error
     */
    public void testEmpty() throws Exception {

        NodeList nodes = new HorizontalListNode();
        builder.insertLigatures(nodes, 0);
        assertEquals(0, nodes.size());
    }

    /**
     * ...
     * @throws Exception in case of an error
     */
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
     * ...
     * @throws Exception in case of an error
     */
    public void testOne1() throws Exception {

        NodeList nodes = new HorizontalListNode();
        Node n = new CharNode(tc1, UnicodeChar.get('a'));
        nodes.add(n);
        builder.insertLigatures(nodes, 0);
        assertEquals(1, nodes.size());
        assertEquals(n, nodes.get(0));
    }

    /**
     * ...
     * @throws Exception in case of an error
     */
    public void testOne2() throws Exception {

        NodeList nodes = new HorizontalListNode();
        Node n = new GlueNode(new Glue(3), true);
        nodes.add(n);
        builder.insertLigatures(nodes, 0);
        assertEquals(1, nodes.size());
        assertEquals(n, nodes.get(0));
    }

    /**
     * ...
     * @throws Exception in case of an error
     */
    public void testThree0() throws Exception {

        NodeList nodes = new HorizontalListNode();
        nodes.add(new CharNode(tc1, UnicodeChar.get('a')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('f')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('a')));
        builder.insertLigatures(nodes, 0);
        assertEquals(3, nodes.size());
    }

    /**
     * ...
     * @throws Exception in case of an error
     */
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
     * ...
     * @throws Exception in case of an error
     */
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
     * ...
     * @throws Exception in case of an error
     */
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
     * ...
     * @throws Exception in case of an error
     */
    public void testTwo2() throws Exception {

        NodeList nodes = new HorizontalListNode();
        nodes.add(new CharNode(tc1, UnicodeChar.get('f')));
        nodes.add(new CharNode(tc1, UnicodeChar.get('a')));
        builder.insertLigatures(nodes, 0);
        assertEquals(2, nodes.size());
    }

}
