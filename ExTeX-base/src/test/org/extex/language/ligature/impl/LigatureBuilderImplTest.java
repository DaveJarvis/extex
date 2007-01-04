/*
 * Copyright (C) 2004-2006 The ExTeX Group and individual authors listed below
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

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.extex.font.FontByteArray;
import org.extex.font.Glyph;
import org.extex.font.Kerning;
import org.extex.font.Ligature;
import org.extex.font.format.tfm.TfmFixWord;
import org.extex.font.type.other.NullFont;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.context.tc.TypesettingContextImpl;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
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
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4527 $
     */
    private class MockFont extends NullFont {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for serialization.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The field <tt>cache</tt> contains the ...
         */
        private Map cache = new HashMap();

        /**
         * Creates a new object.
         */
        public MockFont() {

            super();
        }

        /**
         * ...
         */
        private Glyph getGlyph(final UnicodeChar c) {

            Glyph g = (Glyph) cache.get(c);
            if (g == null) {
                switch (c.getCodePoint()) {
                    case 'a':
                        g = new MockGlyph('a');
                        break;
                    case 'f':
                        g = new MockGlyph('f');
                        break;
                    case 'l':
                        g = new MockGlyph('l');
                        break;
                    case CC_FF:
                        g = new MockGlyph(CC_FF);
                        break;
                    case CC_FL:
                        g = new MockGlyph(CC_FL);
                        break;
                    case CC_FFL:
                        g = new MockGlyph(CC_FFL);
                        break;
                }
                cache.put(c, g);
            }
            return g;
        }

        /**
         * @see org.extex.font.type.other.NullFont#hasGlyph(org.extex.type.UnicodeChar)
         */
        public boolean hasGlyph(final UnicodeChar uc) {

            return getGlyph(uc) != null;
        }

        /**
         * @see org.extex.font.type.other.NullFont#getLigature(org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
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
            }

            return null;
        }

    }

    /**
     * This class provides a mock implementation for a glyph.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4527 $
     */
    private class MockGlyph implements Glyph {

        /**
         * @see org.extex.font.Glyph#setDepth(org.extex.font.type.tfm.TFMFixWord, org.extex.interpreter.type.dimen.Dimen)
         */
        public void setDepth(final TfmFixWord size, final Dimen em) {

        }

        /**
         * @see org.extex.font.Glyph#setHeight(org.extex.font.type.tfm.TFMFixWord, org.extex.interpreter.type.dimen.Dimen)
         */
        public void setHeight(final TfmFixWord size, final Dimen em) {

        }

        /**
         * @see org.extex.font.Glyph#setItalicCorrection(org.extex.font.type.tfm.TFMFixWord, org.extex.interpreter.type.dimen.Dimen)
         */
        public void setItalicCorrection(final TfmFixWord size, final Dimen em) {

        }

        /**
         * @see org.extex.font.Glyph#setWidth(org.extex.font.type.tfm.TFMFixWord, org.extex.interpreter.type.dimen.Dimen)
         */
        public void setWidth(final TfmFixWord size, final Dimen em) {

        }

        /**
         * The field <tt>c</tt> contains the encapsulatec character.
         */
        private int c;

        /**
         * Creates a new object.
         *
         * @param cp the character code point
         */
        public MockGlyph(final int cp) {

            super();
            c = cp;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#addKerning(
         *      org.extex.interpreter.type.font.Kerning)
         */
        public void addKerning(final Kerning kern) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#addLigature(
         *      org.extex.interpreter.type.font.Ligature)
         */
        public void addLigature(final Ligature lig) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#getDepth()
         */
        public Dimen getDepth() {

            return Dimen.ZERO_PT;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#getExternalFile()
         */
        public FontByteArray getExternalFile() {

            return null;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#getHeight()
         */
        public Dimen getHeight() {

            return Dimen.ONE_INCH;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#getItalicCorrection()
         */
        public Dimen getItalicCorrection() {

            return null;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#getKerning(
         *      org.extex.type.UnicodeChar)
         */
        public Dimen getKerning(final UnicodeChar uc) {

            return null;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#getLeftSpace()
         */
        public Dimen getLeftSpace() {

            return null;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#getLigature(
         *      org.extex.type.UnicodeChar)
         */
        public UnicodeChar getLigature(final UnicodeChar uc) {

            if (c == 'f') {
                switch (uc.getCodePoint()) {
                    case 'f':
                        return FF;
                    case 'l':
                        return FL;
                }
            } else if (c == CC_FF) {
                switch (uc.getCodePoint()) {
                    case 'l':
                        return FFL;
                }
            }
            return null;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#getName()
         */
        public String getName() {

            return null;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#getNumber()
         */
        public String getNumber() {

            return null;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#getRightSpace()
         */
        public Dimen getRightSpace() {

            return null;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#getWidth()
         */
        public Dimen getWidth() {

            return Dimen.ONE_INCH;
        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setDepth(org.extex.interpreter.type.dimen.Dimen)
         */
        public void setDepth(final Dimen d) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setDepth(java.lang.String, org.extex.interpreter.type.dimen.Dimen, int)
         */
        public void setDepth(final String gsize, final Dimen em,
                final int unitsperem) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setExternalFile(org.extex.interpreter.type.font.FontFile)
         */
        public void setExternalFile(final FontByteArray file) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setHeight(org.extex.interpreter.type.dimen.Dimen)
         */
        public void setHeight(final Dimen h) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setHeight(java.lang.String, org.extex.interpreter.type.dimen.Dimen, int)
         */
        public void setHeight(final String gsize, final Dimen em,
                final int unitsperem) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setItalicCorrection(org.extex.interpreter.type.dimen.Dimen)
         */
        public void setItalicCorrection(final Dimen d) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setItalicCorrection(java.lang.String, org.extex.interpreter.type.dimen.Dimen, int)
         */
        public void setItalicCorrection(final String gsize, final Dimen em,
                final int unitsperem) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setLeftSpace(org.extex.interpreter.type.dimen.Dimen)
         */
        public void setLeftSpace(final Dimen ls) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setName(java.lang.String)
         */
        public void setName(final String n) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setNumber(
         *      java.lang.String)
         */
        public void setNumber(final String nr) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setRightSpace(
         *      org.extex.interpreter.type.dimen.Dimen)
         */
        public void setRightSpace(final Dimen rs) {

        }

        /**
         * @see org.extex.font.Glyph#setWidth(
         *      org.extex.interpreter.type.dimen.Dimen)
         */
        public void setWidth(final Dimen w) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setWidth(FixedDimen)
         */
        public void setWidth(final FixedDimen w) {

        }

        /**
         * @see org.extex.interpreter.type.font.Glyph#setWidth(
         *      java.lang.String,
         *      org.extex.interpreter.type.dimen.Dimen, int)
         */
        public void setWidth(final String gsize, final Dimen em,
                final int unitsperem) {

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
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

        tc1 = new TypesettingContextImpl(new MockFont());
        super.setUp();
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