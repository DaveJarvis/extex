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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.core.glue.GlueComponent;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is the test suite for a horizontal list node.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class HorizontalListNodeTest extends AbstractNodeListTester {

    /**
     * The field <tt>visitor</tt> contains the visitor which does nothing.
     */
    private static final NodeVisitor<Node, Boolean> VISITOR =
            new NodeVisitor<Node, Boolean>() {

                @Override
                public Node visitAdjust(AdjustNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitAfterMath(AfterMathNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitAlignedLeaders(AlignedLeadersNode node,
                        Boolean value) throws GeneralException {

                    return null;
                }

                @Override
                public Node visitBeforeMath(BeforeMathNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitCenteredLeaders(CenteredLeadersNode node,
                        Boolean value) throws GeneralException {

                    return null;
                }

                @Override
                public Node visitChar(CharNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitDiscretionary(DiscretionaryNode node,
                        Boolean value) throws GeneralException {

                    return null;
                }

                @Override
                public Node visitExpandedLeaders(ExpandedLeadersNode node,
                        Boolean value) throws GeneralException {

                    return null;
                }

                @Override
                public Node visitGlue(GlueNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitHorizontalList(HorizontalListNode node,
                        Boolean value) throws GeneralException {

                    return node;
                }

                @Override
                public Node visitInsertion(InsertionNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitKern(KernNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitLigature(LigatureNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitMark(MarkNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitPenalty(PenaltyNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitRule(RuleNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitSpace(SpaceNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitVerticalList(VerticalListNode node,
                        Boolean value) throws GeneralException {

                    return null;
                }

                @Override
                public Node visitVirtualChar(VirtualCharNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                @Override
                public Node visitWhatsIt(WhatsItNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }
            };

    /**
     * Command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(HorizontalListNodeTest.class);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.AbstractNodeListTester#makeList()
     */
    @Override
    protected NodeList makeList() {

        return new HorizontalListNode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.AbstractNodeListTester#makeList()
     */
    @Override
    protected NodeList makeList(Node node) {

        return new HorizontalListNode(node);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.AbstractNodeListTester#makeVisitor()
     */
    @Override
    protected NodeVisitor<Node, Boolean> makeVisitor() {

        return VISITOR;
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#addSkip(org.extex.core.glue.FixedGlue)}
     * .
     */
    @Test
    public final void testAddSkip0() {

        NodeList list = makeList();
        list.addSkip(FixedGlue.ZERO);
        assertList(list, 1, 0, 0, 0, 0, 0);
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#addSkip(org.extex.core.glue.FixedGlue)}
     * .
     */
    @Test
    public final void testAddSkip1() {

        NodeList list = makeList();
        list.addSkip(FixedGlue.S_S);
        assertList(list, 1, 0, 0, 0, 0, 0);
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#HorizontalListNode()}
     * .
     */
    @Test
    public final void testHorizontalListNode() {

        NodeList list = makeList();
        assertTrue(list.isEmpty());
        assertList(list, 0, 0, 0, 0, 0, 0);
        assertEquals(0L, list.getVerticalSize().getValue());
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#HorizontalListNode(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public final void testHorizontalListNodeFixedDimen() {

        HorizontalListNode list = new HorizontalListNode(Dimen.ONE_SP);
        assertList(list, 0, Dimen.ONE_SP.getValue(), 0, 0, 0, 0);
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#HorizontalListNode(org.extex.typesetter.type.Node)}
     * .
     */
    @Test
    public final void testHorizontalListNodeNode1() {

        HorizontalListNode list = new HorizontalListNode(new PenaltyNode(123));
        assertList(list, 1, 0, 0, 0, 0, 0);
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#HorizontalListNode(org.extex.typesetter.type.Node)}
     * .
     */
    @Test
    public final void testHorizontalListNodeNode2() {

        HorizontalListNode list =
                new HorizontalListNode(new RuleNode(new Dimen(1), new Dimen(2),
                    new Dimen(3), TC, true));
        assertList(list, 1, 1, 2, 3, 0, 0);
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#HorizontalListNode(org.extex.typesetter.type.Node, org.extex.typesetter.type.Node)}
     * .
     */
    @Test
    public final void testHorizontalListNodeNodeNode() {

        HorizontalListNode list =
                new HorizontalListNode(new PenaltyNode(123), new PenaltyNode(
                    456));
        assertFalse(list.isEmpty());
        assertList(list, 2, 0, 0, 0, 0, 0);
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack()}.
     */
    @Test
    public final void testHpack0() {

        HorizontalListNode list = new HorizontalListNode();
        list.hpack();
        assertList(list, 0, 0, 0, 0, 0, 0);
    }

    // TODO gene more test cases for hpack()

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public final void testHpackFixedDimen0() {

        HorizontalListNode list = new HorizontalListNode();
        list.hpack(null);
        assertList(list, 0, 0, 0, 0, 0, 0);
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public final void testHpackFixedDimen1() {

        HorizontalListNode list = new HorizontalListNode();
        list.hpack(Dimen.ZERO_PT);
        assertList(list, 0, 0, 0, 0, 0, 0);
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public final void testHpackFixedDimen2() {

        HorizontalListNode list = new HorizontalListNode();
        list.hpack(Dimen.ONE_PT);
        assertList(list, 0, Dimen.ONE, 0, 0, 0, 0);
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public final void testHpackFixedDimen3() {

        HorizontalListNode list = new HorizontalListNode();
        list.add(new PenaltyNode(123));
        list.hpack(Dimen.ZERO_PT);
        assertList(list, 1, 0, 0, 0, 0, 0);
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public final void testHpackFixedDimen4() {

        HorizontalListNode list = new HorizontalListNode();
        GlueNode n =
                new GlueNode(new Glue(Dimen.ONE_INCH, GlueComponent.ONE_FIL,
                    GlueComponent.ONE_FIL), true);
        list.add(n);
        list.hpack(Dimen.ZERO_PT);
        assertList(list, 1, 0, 0, 0, 0, 0);
        assertEquals(0L, n.getWidth().getValue());
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#hpack(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public final void testHpackFixedDimen5() {

        HorizontalListNode list = new HorizontalListNode();
        GlueNode n =
                new GlueNode(new Glue(Dimen.ONE_INCH, GlueComponent.ONE_FIL,
                    GlueComponent.ONE_FIL), true);
        list.add(n);
        Dimen twoInch = new Dimen(2 * Dimen.ONE_INCH.getValue());
        list.hpack(twoInch);
        assertList(list, 1, twoInch.getValue(), 0, 0, 0, 0);
        assertEquals(twoInch.getValue(), n.getWidth().getValue());
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#isHorizontal()}.
     */
    @Test
    public final void testIsHorizontal1() {

        HorizontalListNode list = new HorizontalListNode();
        assertTrue(list.isHorizontal());
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#spreadWidth(org.extex.core.dimen.FixedDimen, org.extex.core.glue.FixedGlueComponent)}
     * .
     */
    @Test
    public final void testSpreadWidth() {

        HorizontalListNode list = new HorizontalListNode();
        list.spreadWidth(Dimen.ONE_SP, GlueComponent.ONE_FI);
        assertList(list, 0, 0, 0, 0, 0, 0);
    }

    /**
     * 
     */
    @Test
    public final void testToString1() {

        NodeList list = makeList();
        assertEquals("\\hbox(0.0pt+0.0pt)x0.0pt", list.toString());
    }

    /**
     * 
     */
    @Test
    public final void testToString2() {

        NodeList list = makeList();
        list.setShift(Dimen.ONE_PT);
        assertEquals("\\hbox(0.0pt+0.0pt)x0.0pt, shifted 1.0pt",
            list.toString());
    }

    /**
     * 
     */
    @Test
    public final void testToString3() {

        NodeList list = makeList();
        list.setMove(Dimen.ONE_PT);
        assertEquals("\\hbox(0.0pt+0.0pt)x0.0pt, moved 1.0pt", list.toString());
    }

    /**
     * 
     */
    @Test
    public final void testToString4() {

        NodeList list = makeList();
        list.setShift(Dimen.ONE_PT);
        list.setMove(Dimen.ONE_PT);
        assertEquals("\\hbox(0.0pt+0.0pt)x0.0pt, shifted 1.0pt, moved 1.0pt",
            list.toString());
    }

    /**
     * 
     */
    @Test
    public final void testToString5() {

        NodeList list = makeList(new CharNode(TC, UnicodeChar.get('A')));
        list.setMove(Dimen.ONE_PT);
        assertEquals("\\hbox(72.26999pt+0.00002pt)x8.0pt, moved 1.0pt\n.A",
            list.toString());
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#toString(Appendable, java.lang.String, int, int)}
     * .
     */
    @Test
    @Ignore
    public final void testToStringStringBuilderStringIntInt() {

        fail("Not yet implemented"); // TODO
    }

    /**
     * 
     */
    @Test
    public final void testToText1() {

        NodeList list = makeList(new CharNode(TC, UnicodeChar.get('A')));
        list.setMove(Dimen.ONE_PT);
        assertEquals("\n(hlist \\test A)", list.toText());
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#toText(StringBuilder, java.lang.String)}
     * .
     */
    @Test
    public final void testToTextStringBuilderString0() {

        NodeList list = makeList();
        StringBuilder sb = new StringBuilder();
        list.toText(sb, "..");
        assertEquals("\n..(hlist )", sb.toString());
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#toText(StringBuilder, java.lang.String)}
     * .
     */
    @Test
    public final void testToTextStringBuilderString1() {

        HorizontalListNode list = new HorizontalListNode(new PenaltyNode(123));
        StringBuilder sb = new StringBuilder();
        list.toText(sb, "..");
        assertEquals("\n..(hlist [])", sb.toString());
    }

}
