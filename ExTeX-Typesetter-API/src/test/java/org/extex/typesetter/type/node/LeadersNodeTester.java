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

import junit.framework.TestCase;

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeVisitor;
import org.extex.typesetter.type.OrientedNode;
import org.junit.Test;

/**
 * This is a test suite for leaders notes.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class LeadersNodeTester extends TestCase {

    
    /**
     * Getter for visitor.
     *
     * @return the visitor
     */
    public NodeVisitor<Node, Boolean> getVisitor() {
    
        return visitor;
    }

    /**
     * Create a new instance to be tested.
     * 
     * @param node the repeated node
     * @param glue the glue specification
     * @return the new node
     */
    protected abstract AbstractLeadersNode makeNode(OrientedNode node,
            FixedGlue glue);

    /**
     * The field <tt>visitor</tt> contains the visitor.
     */
    private NodeVisitor<Node, Boolean> visitor =
            new NodeVisitor<Node, Boolean>() {

                public Node visitAdjust(AdjustNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitAfterMath(AfterMathNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitAlignedLeaders(AlignedLeadersNode node,
                        Boolean value) throws GeneralException {

                    return node;
                }

                public Node visitBeforeMath(BeforeMathNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitCenteredLeaders(CenteredLeadersNode node,
                        Boolean value) throws GeneralException {

                    return node;
                }

                public Node visitChar(CharNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitDiscretionary(DiscretionaryNode node,
                        Boolean value) throws GeneralException {

                    return node;
                }

                public Node visitExpandedLeaders(ExpandedLeadersNode node,
                        Boolean value) throws GeneralException {

                    return node;
                }

                public Node visitGlue(GlueNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitHorizontalList(HorizontalListNode node,
                        Boolean value) throws GeneralException {

                    return node;
                }

                public Node visitInsertion(InsertionNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitKern(KernNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitLigature(LigatureNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitMark(MarkNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitPenalty(PenaltyNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitRule(RuleNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitSpace(SpaceNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitVerticalList(VerticalListNode node,
                        Boolean value) throws GeneralException {

                    return node;
                }

                public Node visitVirtualChar(VirtualCharNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }

                public Node visitWhatsIt(WhatsItNode node, Boolean value)
                        throws GeneralException {

                    return node;
                }
            };

    /**
     * <testcase> </testcase>
     */
    @Test(expected = NullPointerException.class)
    public final void testAtShippingError1h() {

        makeNode(null, null);
    }

    /**
     * <testcase> </testcase>
     */
    @Test(expected = NullPointerException.class)
    public final void testAtShippingError1v() {

        makeNode(null, null);
    }

    /**
     * <testcase> </testcase>
     */
    @Test(expected = NullPointerException.class)
    public final void testAtShippingError2h() {

        makeNode(new RuleNode(Dimen.ONE_PT, Dimen.ONE_PT, Dimen.ONE_PT, null,
            true), null);
    }

    /**
     * <testcase> </testcase>
     */
    @Test(expected = NullPointerException.class)
    public final void testAtShippingError2v() {

        makeNode(new RuleNode(Dimen.ONE_PT, Dimen.ONE_PT, Dimen.ONE_PT, null,
            true), null);
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test()
    public final void testAtShippingRule1h() throws GeneralException {

        AbstractLeadersNode node =
                makeNode(new RuleNode(Dimen.ONE_PT, Dimen.ONE_PT, Dimen.ONE_PT,
                    null, true), FixedGlue.S_S);
        assertNotNull(node);
        node.setWidth(new Dimen(Dimen.ONE * 12));
        Node n = node.atShipping(null, null, null, null);
        assertTrue(n instanceof RuleNode);
        assertTrue("width", Dimen.ONE * 12 == n.getWidth().getValue());
        assertTrue("depth " + Long.toString(n.getDepth().getValue()),
            Dimen.ONE == n.getDepth().getValue());
        assertTrue("height " + Long.toString(n.getHeight().getValue()),
            Dimen.ONE == n.getHeight().getValue());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test()
    public final void testAtShippingRule1v() throws GeneralException {

        AbstractLeadersNode node =
                makeNode(new RuleNode(Dimen.ONE_PT, Dimen.ONE_PT, Dimen.ONE_PT,
                    null, false), FixedGlue.S_S);
        assertNotNull(node);
        node.setHeight(new Dimen(Dimen.ONE * 12));
        Node n = node.atShipping(null, null, null, null);
        assertTrue(n instanceof RuleNode);
        assertTrue("width", Dimen.ONE == n.getWidth().getValue());
        assertTrue("depth " + Long.toString(n.getDepth().getValue()),
            0 == n.getDepth().getValue());
        assertTrue("height " + Long.toString(n.getHeight().getValue()),
            Dimen.ONE * 12 == n.getHeight().getValue());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test()
    public final void testAtShippingRule2h() throws GeneralException {

        AbstractLeadersNode node =
                makeNode(new RuleNode(Dimen.ONE_PT, Dimen.ONE_PT, Dimen.ONE_PT,
                    null, true), FixedGlue.S_S);
        assertNotNull(node);
        node.setWidth(Dimen.ZERO_PT);
        Node n = node.atShipping(null, null, null, null);
        assertTrue(n instanceof RuleNode);
        assertTrue("width", 0 == n.getWidth().getValue());
        assertTrue("depth " + Long.toString(n.getDepth().getValue()),
            Dimen.ONE == n.getDepth().getValue());
        assertTrue("height " + Long.toString(n.getHeight().getValue()),
            Dimen.ONE == n.getHeight().getValue());
    }

    /**
     * <testcase> </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test()
    public final void testAtShippingRule2v() throws GeneralException {

        AbstractLeadersNode node =
                makeNode(new RuleNode(Dimen.ONE_PT, Dimen.ONE_PT, Dimen.ONE_PT,
                    null, false), FixedGlue.S_S);
        assertNotNull(node);
        node.setHeight(Dimen.ZERO_PT);
        Node n = node.atShipping(null, null, null, null);
        assertTrue(n instanceof RuleNode);
        assertTrue("width", Dimen.ONE == n.getWidth().getValue());
        assertTrue("depth " + Long.toString(n.getDepth().getValue()),
            0 == n.getDepth().getValue());
        assertTrue("height " + Long.toString(n.getHeight().getValue()), 0 == n
            .getHeight().getValue());
    }

}
