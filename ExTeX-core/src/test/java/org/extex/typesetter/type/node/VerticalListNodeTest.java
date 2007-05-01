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

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.NodeVisitor;
import org.junit.Test;

/**
 * This file contains test cases for the vertical list node.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4756 $
 */
public class VerticalListNodeTest extends AbstractNodeListTester {

    /**
     * The field <tt>visitor</tt> contains the ...
     */
    private static final NodeVisitor VISITOR = new NodeVisitor() {
    
                public Object visitAdjust(AdjustNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitAfterMath(AfterMathNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitAlignedLeaders(AlignedLeadersNode node,
                        Object value) throws GeneralException {
    
                    return null;
                }
    
                public Object visitBeforeMath(BeforeMathNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitCenteredLeaders(CenteredLeadersNode node,
                        Object value) throws GeneralException {
    
                    return null;
                }
    
                public Object visitChar(CharNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitDiscretionary(DiscretionaryNode node,
                        Object value) throws GeneralException {
    
                    return null;
                }
    
                public Object visitExpandedLeaders(ExpandedLeadersNode node,
                        Object value) throws GeneralException {
    
                    return null;
                }
    
                public Object visitGlue(GlueNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitHorizontalList(HorizontalListNode node,
                        Object value) throws GeneralException {
    
                    return null;
                }
    
                public Object visitInsertion(InsertionNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitKern(KernNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitLigature(LigatureNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitMark(MarkNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitPenalty(PenaltyNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitRule(RuleNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitSpace(SpaceNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitVerticalList(VerticalListNode node, Object value)
                        throws GeneralException {
    
                    return node;
                }
    
                public Object visitVirtualChar(VirtualCharNode node, Object value)
                        throws GeneralException {
    
                    return null;
                }
    
                public Object visitWhatsIt(WhatsItNode node, Object value)
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

        junit.textui.TestRunner.run(VerticalListNodeTest.class);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.node.AbstractNodeListTester#makeList()
     */
    protected NodeList makeList() {

        return new VerticalListNode();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.node.AbstractNodeListTester#makeList(org.extex.typesetter.type.Node)
     */
    protected NodeList makeList(Node node) {

        return new VerticalListNode(node);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.typesetter.type.node.AbstractNodeListTester#makeVisitor()
     */
    protected NodeVisitor makeVisitor() {

        return VISITOR;
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
        assertEquals(0, vlist.vpack(h));
        assertFalse(vlist.isEmpty());
        assertEquals("\\vbox(144.53998pt+0.0pt)x72.26999pt\n"
                + ".\\glue0.0pt plus 1.0fil minus 1.0fil\n"
                + ".\\rule72.26999pt+72.26999ptx72.26999pt\n"
                + ".\\glue0.0pt plus 1.0fil minus 1.0fil", vlist.toString());
    }

}
