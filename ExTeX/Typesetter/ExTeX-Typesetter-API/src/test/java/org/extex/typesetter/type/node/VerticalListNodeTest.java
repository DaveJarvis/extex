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

package org.extex.typesetter.type.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This file contains test cases for the vertical list node.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class VerticalListNodeTest extends AbstractNodeListTester {

    /**
     * The field {@code visitor} contains the visitor to use.
     */
    private static final NodeVisitor<Node, Boolean> VISITOR =
            new NodeVisitor<Node, Boolean>() {

                public Node visitAdjust(AdjustNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitAfterMath(AfterMathNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitAlignedLeaders(AlignedLeadersNode node,
                        Boolean value) throws GeneralException {

                    return null;
                }

                public Node visitBeforeMath(BeforeMathNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitCenteredLeaders(CenteredLeadersNode node,
                        Boolean value) throws GeneralException {

                    return null;
                }

                public Node visitChar(CharNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitDiscretionary(DiscretionaryNode node,
                        Boolean value) throws GeneralException {

                    return null;
                }

                public Node visitExpandedLeaders(ExpandedLeadersNode node,
                        Boolean value) throws GeneralException {

                    return null;
                }

                public Node visitGlue(GlueNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitHorizontalList(HorizontalListNode node,
                        Boolean value) throws GeneralException {

                    return null;
                }

                public Node visitInsertion(InsertionNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitKern(KernNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitLigature(LigatureNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitMark(MarkNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitPenalty(PenaltyNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitRule(RuleNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitSpace(SpaceNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

                public Node visitVerticalList(VerticalListNode node,
                        Boolean value) throws GeneralException {

                    return node;
                }

                public Node visitVirtualChar(VirtualCharNode node, Boolean value)
                        throws GeneralException {

                    return null;
                }

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

        (new JUnitCore()).run(VerticalListNodeTest.class);
    }

@Override
    protected NodeList makeList() {

        return new VerticalListNode();
    }

    /**
*      org.extex.typesetter.type.Node)
     */
    @Override
    protected NodeList makeList(Node node) {

        return new VerticalListNode(node);
    }

@Override
    protected NodeVisitor<Node, Boolean> makeVisitor() {

        return VISITOR;
    }

    /**
     * Test method for
     * {@link org.extex.typesetter.type.node.HorizontalListNode#isHorizontal()}.
     */
    @Test
    public final void testIsHorizontal1() {

        VerticalListNode vlist = new VerticalListNode();
        assertFalse(vlist.isHorizontal());
    }

    /**
     * Test method
     */
    @Test
    public final void testIsTop1() {

        VerticalListNode vlist = new VerticalListNode();
        assertFalse(vlist.isTop());
    }

    /**
     * Test method
     */
    @Test
    public final void testIsTop2() {

        VerticalListNode vlist = new VerticalListNode();
        vlist.setTop(true);
        assertTrue(vlist.isTop());
    }

    /**
     * Test that splitting an empty list works for a positive target height.
     */
    @Test
    public void testSplitEmpty1() {

        VerticalListNode vlist = new VerticalListNode();
        VerticalListNode vl = vlist.split(Dimen.ONE_INCH, null, null);
        assertTrue(vlist.isEmpty());
        assertTrue(vl.isEmpty());
    }

    /**
     * Test that splitting an empty list works for a target height of 0pt.
     */
    @Test
    public void testSplitEmpty2() {

        VerticalListNode vlist = new VerticalListNode();
        VerticalListNode vl = vlist.split(Dimen.ZERO_PT, null, null);
        assertTrue(vlist.isEmpty());
        assertTrue(vl.isEmpty());
    }

    /**
     * Test that splitting an empty list works for a negative target height.
     */
    @Test
    public void testSplitEmpty3() {

        VerticalListNode vlist = new VerticalListNode();
        VerticalListNode vl = vlist.split(new Dimen(-42), null, null);
        assertTrue(vlist.isEmpty());
        assertTrue(vl.isEmpty());
    }

    /**
     * Test that splitting an empty list works for a positive target height.
     */
    @Test
    public void testVpack1() {

        VerticalListNode vlist = new VerticalListNode();
        vlist.add(new RuleNode(Dimen.ONE_INCH, Dimen.ONE_INCH, Dimen.ONE_INCH,
            null, true));
        Dimen h = new Dimen(Dimen.ONE_INCH);
        h.multiply(2);
        vlist.vpack(h);
        assertFalse(vlist.isEmpty());
        assertEquals("\\vbox(144.53998pt+72.26999pt)x72.26999pt\n"
                + ".\\rule72.26999pt+72.26999ptx72.26999pt", vlist.toString());
    }

    /**
     * Test that splitting an empty list works for a positive target height.
     */
    @Test
    public void testVpack2() {

        VerticalListNode vlist = new VerticalListNode();
        vlist.addSkip(FixedGlue.S_S);
        vlist.add(new RuleNode(Dimen.ONE_INCH, Dimen.ONE_INCH, Dimen.ONE_INCH,
            null, true));
        vlist.addSkip(FixedGlue.S_S);
        Dimen h = new Dimen(Dimen.ONE_INCH);
        h.multiply(2);
        assertEquals(0L, vlist.vpack(h));
        assertFalse(vlist.isEmpty());
        assertEquals("\\vbox(144.53998pt+0.0pt)x72.26999pt\n"
                + ".\\glue0.0pt plus 1.0fil minus 1.0fil\n"
                + ".\\rule72.26999pt+72.26999ptx72.26999pt\n"
                + ".\\glue0.0pt plus 1.0fil minus 1.0fil", vlist.toString());
    }

}
