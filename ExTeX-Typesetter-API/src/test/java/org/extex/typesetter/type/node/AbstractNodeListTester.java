/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.type.node;

import java.util.Iterator;

import junit.framework.TestCase;

import org.extex.color.Color;
import org.extex.core.UnicodeChar;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.exception.ImpossibleException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.FontKey;
import org.extex.language.Language;
import org.extex.typesetter.tc.Direction;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5563 $
 */
public abstract class AbstractNodeListTester extends TestCase {

    /**
     * The field <tt>tc</tt> contains the ...
     */
    protected static final TypesettingContext TC = new TypesettingContext() {

        /**
         * The field <tt>serialVersionUID</tt> contains the ...
         */
        private static final long serialVersionUID = 1L;

        /**
         * The field <tt>f</tt> contains the font.
         */
        private Font f = new Font() {

            /**
             * The field <tt>serialVersionUID</tt> contains the ...
             */
            private static final long serialVersionUID = 1L;

            public FontKey getActualFontKey() {

                return null;
            }

            public FixedDimen getActualSize() {

                return null;
            }

            public int getCheckSum() {

                return 0;
            }

            public FixedGlue getDepth(UnicodeChar uc) {

                return new Glue(Dimen.ONE_SP);
            }

            public FixedDimen getDesignSize() {

                return null;
            }

            public long getEfCode(UnicodeChar uc) {

                return 0;
            }

            public FixedDimen getEm() {

                return null;
            }

            public FixedDimen getEx() {

                return null;
            }

            public FixedDimen getFontDimen(String name) {

                return null;
            }

            public FontKey getFontKey() {

                return null;
            }

            public String getFontName() {

                return "test";
            }

            public FixedGlue getHeight(UnicodeChar uc) {

                return new Glue(Dimen.ONE_INCH);
            }

            public UnicodeChar getHyphenChar() {

                return null;
            }

            public FixedDimen getItalicCorrection(UnicodeChar uc) {

                return null;
            }

            public FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2) {

                return null;
            }

            public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

                return null;
            }

            public FixedCount getScaleFactor() {

                return null;
            }

            public UnicodeChar getSkewChar() {

                return null;
            }

            public FixedGlue getSpace() {

                return new Glue(new Dimen(12 * Dimen.ONE));
            }

            public FixedGlue getWidth(UnicodeChar uc) {

                return new Glue(new Dimen(8 * Dimen.ONE));
            }

            public boolean hasGlyph(UnicodeChar uc) {

                return true;
            }

            public void setEfCode(UnicodeChar uc, long code) {

            }

            public void setFontDimen(String name, Dimen value) {

            }

            public void setHyphenChar(UnicodeChar uc) {

            }

            public void setSkewChar(UnicodeChar uc) {

            }
        };

        public Color getColor() {

            return null;
        }

        public Direction getDirection() {

            return null;
        }

        public Font getFont() {

            return f;
        }

        public Language getLanguage() {

            return null;
        }
    };

    /**
     * Validate dimensions.
     * 
     * @param list the list to check
     * @param size the size
     * @param w the width
     * @param h the height
     * @param d the depth
     * @param s the shift
     * @param m the move
     */
    protected void assertList(NodeList list, long size, long w, long h, long d,
            long s, long m) {

        assertEquals("size", size, list.size());
        assertEquals("height", h, list.getHeight().getValue());
        assertEquals("width", w, list.getWidth().getValue());
        assertEquals("depth", d, list.getDepth().getValue());
        assertEquals("shift", s, list.getShift().getValue());
        assertEquals("move", m, list.getMove().getValue());
    }

    /**
     * Create a new instance with the empty constructor.
     * 
     * @return the new instance
     */
    protected abstract NodeList makeList();

    /**
     * Create a new instance with the node constructor.
     * 
     * @param node the initial node
     * 
     * @return the new instance
     */
    protected abstract NodeList makeList(Node node);

    /**
     * Make a node visitor which returns the argument only for the tested node
     * type and <code>null</code> otherwise.
     * 
     * @return the visitor
     */
    protected abstract NodeVisitor<Node, Boolean> makeVisitor();

    /**
     * <testcase> Adding a <code>null</code> node is silently ignored.
     * </testcase>
     */
    public final void testAddIntNode0() {

        NodeList list = makeList();
        list.add(0, null);
        assertList(list, 0, 0, 0, 0, 0, 0);
    }

    /**
     * <testcase> Adding a <code>null</code> node is silently ignored.
     * </testcase>
     */
    public final void testAddIntNode1() {

        NodeList list = makeList();
        list.add(-1, null);
        assertList(list, 0, 0, 0, 0, 0, 0);
    }

    /**
     * <testcase> Adding a <code>null</code> node is silently ignored.
     * </testcase>
     */
    public final void testAddIntNode2() {

        NodeList list = makeList();
        list.add(1, null);
        assertList(list, 0, 0, 0, 0, 0, 0);
    }

    /**
     * <testcase> Adding a non-null node at a negative position lead to an
     * exception. </testcase>
     */
    public final void testAddIntNode3() {

        NodeList list = makeList();
        try {
            list.add(-1, new PenaltyNode(123));
            assertTrue(false);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Adding a non-null node at position past the end lead to an
     * exception. </testcase>
     */
    public final void testAddIntNode4() {

        NodeList list = makeList();
        try {
            list.add(1, new PenaltyNode(123));
            assertTrue(false);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Adding a ... </testcase>
     */
    public final void testAddIntNode5() {

        NodeList list = makeList();
        list.add(0, new PenaltyNode(123));
        assertEquals(1, list.size());
    }

    /**
     * <testcase> Adding a ... </testcase>
     */
    public final void testAddIntNode6() {

        PenaltyNode n = new PenaltyNode(999);
        NodeList list = makeList(n);
        PenaltyNode pn = new PenaltyNode(123);
        list.add(0, pn);
        assertEquals(2, list.size());
        assertEquals(pn, list.get(0));
        assertEquals(n, list.get(1));
    }

    /**
     * <testcase> Adding a ... </testcase>
     */
    public final void testAddIntNode7() {

        PenaltyNode n = new PenaltyNode(999);
        NodeList list = makeList(n);
        PenaltyNode pn = new PenaltyNode(123);
        list.add(1, pn);
        assertEquals(2, list.size());
        assertEquals(n, list.get(0));
        assertEquals(pn, list.get(1));
    }

    /**
     * <testcase> Adding a <code>null</code> node is silently ignored.
     * </testcase>
     */
    public final void testAddNode0() {

        NodeList list = makeList();
        list.add(null);
        assertList(list, 0, 0, 0, 0, 0, 0);
    }

    /**
     * <testcase> Adding a rule node puts the node into the list. </testcase>
     */
    public void testAddNode1() {

        NodeList list = makeList();
        list.add(new RuleNode(new Dimen(2 * Dimen.ONE),
            new Dimen(3 * Dimen.ONE), new Dimen(4 * Dimen.ONE), null, true));
        assertList(list, 1, 2 * Dimen.ONE, 3 * Dimen.ONE, 4 * Dimen.ONE, 0, 0);
    }

    /**
     * <testcase> Adding a penalty node puts the node into the list. </testcase>
     */
    public final void testAddNode2() {

        NodeList list = makeList();
        list.add(new PenaltyNode(123));
        assertList(list, 1, 0, 0, 0, 0, 0);
    }

    /**
     * <testcase> Adding a penalty node puts the node into the list. </testcase>
     */
    public final void testAddNode3() {

        NodeList list = makeList();
        list.add(new PenaltyNode(123));
        list.add(new PenaltyNode(456));
        assertList(list, 2, 0, 0, 0, 0, 0);
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#atShipping(org.extex.interpreter.context.Context, org.extex.typesetter.Typesetter, FixedDimen, FixedDimen)}.
     * 
     * @throws GeneralException in case of an error
     */
    public final void testAtShipping0() throws GeneralException {

        NodeList list = makeList();
        NodeVisitor<Node, Boolean> v = makeVisitor();
        if (v != null) {
            list.atShipping(null, null, null, null);
            assertEquals(0, list.size());
        } else {
            try {
                list.atShipping(null, null, null, null);
                assertFalse(true);
            } catch (ImpossibleException e) {
                assertTrue(true);
            }
        }
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#atShipping(org.extex.interpreter.context.Context, org.extex.typesetter.Typesetter, FixedDimen, FixedDimen)}.
     * 
     * @throws GeneralException in case of an error
     */
    public final void testAtShipping1() throws GeneralException {

        NodeList list = makeList(new PenaltyNode(123));
        NodeVisitor<Node, Boolean> v = makeVisitor();
        if (v != null) {
            list.atShipping(null, null, null, null);
            assertEquals(0, list.size());
        } else {
            // try {
            // list.atShipping(null, null, v, true);
            // assertFalse(true);
            // } catch (ImpossibleException e) {
            // assertTrue(true);
            // }
        }
    }

    /**
     * 
     */
    public final void testClear0() {

        NodeList list = makeList();
        list.clear();
        assertList(list, 0, 0, 0, 0, 0, 0);
        // assertNull(list.getTargetWidth());
        // assertNull(list.getTargetHeight());
        // assertNull(list.getTargetDepth());
    }

    /**
     * 
     */
    public final void testClear1() {

        NodeList list = makeList(new PenaltyNode(123));
        list.clear();
        assertList(list, 0, 0, 0, 0, 0, 0);
        // assertNull(list.getTargetWidth());
        // assertNull(list.getTargetHeight());
        // assertNull(list.getTargetDepth());
    }

    /**
     * 
     */
    public final void testClear2() {

        NodeList list = makeList(new PenaltyNode(123));
        list.add(new PenaltyNode(456));
        list.clear();
        assertList(list, 0, 0, 0, 0, 0, 0);
        // assertNull(list.getTargetWidth());
        // assertNull(list.getTargetHeight());
        // assertNull(list.getTargetDepth());
    }

    /**
     * 
     */
    public final void testCopy1() {

        NodeList list = makeList();
        NodeList copy = list.copy();
        assertNotNull(copy);
        assertEquals(list.getClass(), copy.getClass());
        assertNotSame(list, copy);
        assertList(copy, 0, 0, 0, 0, 0, 0);
    }

    /**
     * 
     */
    public final void testCopy2() {

        NodeList list = makeList(new PenaltyNode(123));
        NodeList copy = list.copy();
        assertNotNull(copy);
        assertEquals(list.getClass(), copy.getClass());
        assertNotSame(list, copy);
        assertList(copy, 1, 0, 0, 0, 0, 0);
    }

    /**
     * 
     */
    public final void testGetChars0() {

        NodeList list = makeList();
        CharNode[] chars = list.getChars();
        assertNotNull(chars);
        assertEquals(0, chars.length);
    }

    /**
     * 
     */
    public final void testGetChars1() {

        NodeList list = makeList();
        list.add(new PenaltyNode(456));
        CharNode[] chars = list.getChars();
        assertNotNull(chars);
        assertEquals(0, chars.length);
    }

    /**
     * 
     */
    public final void testGetChars2() {

        CharNode ca = new CharNode(TC, UnicodeChar.get('A'));
        HorizontalListNode list = new HorizontalListNode(ca);
        list.add(new PenaltyNode(456));
        CharNode[] chars = list.getChars();
        assertNotNull(chars);
        assertEquals(1, chars.length);
        assertEquals(ca, chars[0]);
    }

    /**
     * 
     */
    public final void testGetChars3() {

        CharNode ca = new CharNode(TC, UnicodeChar.get('A'));
        CharNode cb = new CharNode(TC, UnicodeChar.get('B'));
        HorizontalListNode list = new HorizontalListNode(ca);
        list.add(new PenaltyNode(456));
        list.add(cb);
        CharNode[] chars = list.getChars();
        assertNotNull(chars);
        assertEquals(2, chars.length);
        assertEquals(ca, chars[0]);
        assertEquals(cb, chars[1]);
    }

    /**
     * 
     */
    public final void testGetChars4() {

        CharNode ca = new CharNode(TC, UnicodeChar.get('A'));
        CharNode cb = new CharNode(TC, UnicodeChar.get('B'));
        HorizontalListNode list = new HorizontalListNode(ca);
        list.add(new PenaltyNode(456));
        list.add(new HorizontalListNode(cb));
        CharNode[] chars = list.getChars();
        assertNotNull(chars);
        assertEquals(2, chars.length);
        assertEquals(ca, chars[0]);
        assertEquals(cb, chars[1]);
    }

    /**
     * 
     */
    public final void testGetChars5() {

        CharNode ca = new CharNode(TC, UnicodeChar.get('A'));
        CharNode cb = new CharNode(TC, UnicodeChar.get('B'));
        HorizontalListNode list = new HorizontalListNode(ca);
        list.add(new PenaltyNode(456));
        list.add(new VerticalListNode(cb));
        CharNode[] chars = list.getChars();
        assertNotNull(chars);
        assertEquals(2, chars.length);
        assertEquals(ca, chars[0]);
        assertEquals(cb, chars[1]);
    }

    /**
     * 
     */
    public final void testIterator1() {

        NodeList list = makeList();
        Iterator<Node> iterator = list.iterator();
        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
    }

    /**
     * 
     */
    public final void testIterator2() {

        PenaltyNode n = new PenaltyNode(123);
        NodeList list = makeList(n);
        Iterator<Node> iterator = list.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
        assertEquals(n, iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * 
     */
    public final void testRemove1() {

        NodeList list = makeList();
        try {
            list.remove(-1);
            assertFalse(true);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    /**
     * 
     */
    public final void testRemove2() {

        NodeList list = makeList();
        try {
            list.remove(0);
            assertFalse(true);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    /**
     * 
     */
    public final void testRemove3() {

        NodeList list = makeList(new PenaltyNode(123));
        try {
            list.remove(-1);
            assertFalse(true);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    /**
     * 
     */
    public final void testRemove4() {

        NodeList list = makeList(new PenaltyNode(123));
        try {
            list.remove(1);
            assertFalse(true);
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    /**
     * 
     */
    public final void testRemove5() {

        NodeList list = makeList(new PenaltyNode(123));
        list.remove(0);
        assertList(list, 0, 0, 0, 0, 0, 0);
    }

    /**
     * <testcase> Calling the visit() method invokes the appropriate method in
     * the visitor and the argument is passed down. </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    public final void testVisit() throws GeneralException {

        NodeList list = makeList();
        NodeVisitor<Node, Boolean> nv = makeVisitor();
        try {
            list.visit(nv, Long.valueOf(1));
            assertFalse(true);
        } catch (ImpossibleException e) {
            assertTrue(true);
        }
    }

}
