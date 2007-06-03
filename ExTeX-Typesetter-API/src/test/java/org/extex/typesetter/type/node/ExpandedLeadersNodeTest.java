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

import org.extex.core.dimen.Dimen;
import org.extex.core.exception.GeneralException;
import org.extex.core.glue.FixedGlue;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.NodeList;
import org.extex.typesetter.type.OrientedNode;
import org.junit.Test;

/**
 * This is a test suite for expanded leaders nodes.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExpandedLeadersNodeTest extends LeadersNodeTester {

    /**
     * The field <tt>H_LIST</tt> contains the reference hlist.
     */
    private static final HorizontalListNode H_LIST = new HorizontalListNode();

    /**
     * The field <tt>V_LIST</tt> contains the reference vlist.
     */
    private static final VerticalListNode V_LIST = new VerticalListNode();

    static {
        H_LIST.setWidth(Dimen.ONE_INCH);
        H_LIST.setHeight(Dimen.ONE_PT);
        H_LIST.setDepth(Dimen.ONE_SP);
        V_LIST.setWidth(Dimen.ONE_PT);
        V_LIST.setHeight(Dimen.ONE_INCH);
        V_LIST.setDepth(Dimen.ONE_SP);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.type.node.LeadersNodeTester#makeNode(
     *      org.extex.typesetter.type.OrientedNode,
     *      org.extex.core.glue.FixedGlue)
     */
    protected AbstractLeadersNode makeNode(OrientedNode node, FixedGlue glue) {

        return new ExpandedLeadersNode(node, glue);
    }

    /**
     * <testcase>Not enough space left horizontally </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test
    public final void testAtShippingH1() throws GeneralException {

        AbstractLeadersNode node = makeNode(H_LIST, FixedGlue.S_S);
        assertNotNull(node);
        node.setWidth(Dimen.ONE_PT);
        Node n = node.atShipping(null, null, null, null);
        assertNotNull(n);
        assertEquals(node, n);
    }

    /**
     * <testcase>Not enough space left vertically </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test
    public final void testAtShippingV1() throws GeneralException {

        AbstractLeadersNode node = makeNode(V_LIST, FixedGlue.S_S);
        assertNotNull(node);
        node.setWidth(Dimen.ONE_PT);
        Node n = node.atShipping(null, null, null, null);
        assertNotNull(n);
        assertEquals(node, n);
    }

    /**
     * <testcase>Exactly one instance fits horizontally </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test
    public final void testAtShippingH2() throws GeneralException {

        AbstractLeadersNode node = makeNode(H_LIST, FixedGlue.S_S);
        assertNotNull(node);
        node.setWidth(Dimen.ONE_INCH);
        Node n = node.atShipping(null, null, null, null);
        assertNotNull(n);
        assertTrue(n instanceof HorizontalListNode);
        assertTrue("width " + n.getWidth().toString(), //
            n.getWidth().eq(Dimen.ONE_INCH));
        assertTrue("height " + n.getHeight().toString(), //
            n.getHeight().eq(Dimen.ONE_PT));
        assertTrue("depth " + n.getDepth().toString(), //
            n.getDepth().eq(Dimen.ONE_SP));
        NodeList nl = (NodeList) n;
        int size = nl.size();
        assertTrue("size " + Integer.toString(size), 1 == size);
    }

    /**
     * <testcase>Exactly one instance fits vertically </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test
    public final void testAtShippingV2() throws GeneralException {

        AbstractLeadersNode node = makeNode(V_LIST, FixedGlue.S_S);
        assertNotNull(node);
        Dimen ht = new Dimen(Dimen.ONE_INCH.getValue() + 1);
        node.setHeight(ht);
        Node n = node.atShipping(null, null, null, null);
        assertNotNull(n);
        assertTrue(n.getClass().getName(), n instanceof VerticalListNode);
        assertTrue("width " + n.getWidth().toString(), //
            n.getWidth().eq(Dimen.ONE_PT));
        assertTrue("height " + n.getHeight().toString(), //
            n.getHeight().eq(ht));
        assertTrue("depth " + n.getDepth().toString(), //
            n.getDepth().eq(Dimen.ZERO_PT));
        NodeList nl = (NodeList) n;
        int size = nl.size();
        assertTrue("size " + Integer.toString(size), 1 == size);
    }

    /**
     * <testcase>One instance plus some glue fits horizontally </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test
    public final void testAtShippingH3() throws GeneralException {

        AbstractLeadersNode node = makeNode(H_LIST, FixedGlue.S_S);
        assertNotNull(node);
        Dimen wd = new Dimen(Dimen.ONE_INCH.getValue() + 12);
        node.setWidth(wd);
        Node n = node.atShipping(null, null, null, null);
        assertNotNull(n);
        assertTrue(n instanceof HorizontalListNode);
        NodeList nl = (NodeList) n;
        assertTrue("width " + n.getWidth().toString(), //
            n.getWidth().eq(wd));
        assertTrue("height " + n.getHeight().toString(), //
            n.getHeight().eq(Dimen.ONE_PT));
        assertTrue("depth " + n.getDepth().toString(), //
            n.getDepth().eq(Dimen.ONE_SP));
        int size = nl.size();
        assertTrue("size " + Integer.toString(size), 2 == size);
        Node node0 = nl.get(0);
        assertTrue("0:", node0 instanceof ImplicitKernNode);
        assertTrue("0: width", node0.getWidth().getValue() == 6);
        assertTrue("1:", nl.get(1) == H_LIST);
    }

    /**
     * <testcase>One instance plus some glue fits vertically </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test
    public final void testAtShippingV3() throws GeneralException {

        AbstractLeadersNode node = makeNode(V_LIST, FixedGlue.S_S);
        assertNotNull(node);
        Dimen ht = new Dimen(Dimen.ONE_INCH.getValue() + 1 + 12);
        node.setHeight(ht);
        Node n = node.atShipping(null, null, null, null);
        assertNotNull(n);
        assertTrue(n.getClass().getName(), n instanceof VerticalListNode);
        assertTrue("width " + n.getWidth().toString(), //
            n.getWidth().eq(Dimen.ONE_PT));
        assertTrue("height " + n.getHeight().toString(), //
            n.getHeight().eq(ht));
        assertTrue("depth " + n.getDepth().toString(), //
            n.getDepth().eq(Dimen.ZERO_PT));
        NodeList nl = (NodeList) n;
        int size = nl.size();
        assertTrue("size " + Integer.toString(size), 2 == size);
        Node node0 = nl.get(0);
        assertTrue("0:", node0 instanceof ImplicitKernNode);
        assertTrue("0: height", node0.getHeight().getValue() == 6);
        assertTrue("1:", nl.get(1) == V_LIST);
    }

    /**
     * <testcase>Exactly two instances fits horizontally </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test
    public final void testAtShippingH4() throws GeneralException {

        AbstractLeadersNode node = makeNode(H_LIST, FixedGlue.S_S);
        assertNotNull(node);
        Dimen wd = new Dimen(Dimen.ONE_INCH.getValue()*2);
        node.setWidth(wd);
        Node n = node.atShipping(null, null, null, null);
        assertNotNull(n);
        assertTrue(n instanceof HorizontalListNode);
        assertTrue("width " + n.getWidth().toString(), //
            n.getWidth().eq(wd));
        assertTrue("height " + n.getHeight().toString(), //
            n.getHeight().eq(Dimen.ONE_PT));
        assertTrue("depth " + n.getDepth().toString(), //
            n.getDepth().eq(Dimen.ONE_SP));
        NodeList nl = (NodeList) n;
        int size = nl.size();
        assertTrue("size " + Integer.toString(size), 2 == size);
        assertTrue("0:", nl.get(0) == H_LIST);
        assertTrue("1:", nl.get(1) == H_LIST);
    }

    /**
     * <testcase>Exactly one instance fits vertically </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test
    public final void testAtShippingV4() throws GeneralException {

        AbstractLeadersNode node = makeNode(V_LIST, FixedGlue.S_S);
        assertNotNull(node);
        Dimen ht = new Dimen(2*(Dimen.ONE_INCH.getValue() + 1));
        node.setHeight(ht);
        Node n = node.atShipping(null, null, null, null);
        assertNotNull(n);
        assertTrue(n.getClass().getName(), n instanceof VerticalListNode);
        assertTrue("width " + n.getWidth().toString(), //
            n.getWidth().eq(Dimen.ONE_PT));
        assertTrue("height " + n.getHeight().toString(), //
            n.getHeight().eq(ht));
        assertTrue("depth " + n.getDepth().toString(), //
            n.getDepth().eq(Dimen.ZERO_PT));
        NodeList nl = (NodeList) n;
        int size = nl.size();
        assertTrue("size " + Integer.toString(size), 2 == size);
        assertTrue("0:", nl.get(0) == V_LIST);
        assertTrue("1:", nl.get(1) == V_LIST);
    }

    /**
     * <testcase>One instance plus some glue fits horizontally </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test
    public final void testAtShippingH5() throws GeneralException {

        AbstractLeadersNode node = makeNode(H_LIST, FixedGlue.S_S);
        assertNotNull(node);
        Dimen wd = new Dimen(2*Dimen.ONE_INCH.getValue() + 12);
        node.setWidth(wd);
        Node n = node.atShipping(null, null, null, null);
        assertNotNull(n);
        assertTrue(n instanceof HorizontalListNode);
        NodeList nl = (NodeList) n;
        assertTrue("width " + n.getWidth().toString(), //
            n.getWidth().eq(wd));
        assertTrue("height " + n.getHeight().toString(), //
            n.getHeight().eq(Dimen.ONE_PT));
        assertTrue("depth " + n.getDepth().toString(), //
            n.getDepth().eq(Dimen.ONE_SP));
        int size = nl.size();
        assertTrue("size " + Integer.toString(size), 4 == size);
        Node node0 = nl.get(0);
        assertTrue("0:", node0 instanceof ImplicitKernNode);
        assertTrue("0: width", node0.getWidth().getValue() == 4);
        assertTrue("1:", nl.get(1) == H_LIST);
        Node node2 = nl.get(2);
        assertTrue("2:", node2 instanceof ImplicitKernNode);
        assertTrue("2: width", node0.getWidth().getValue() == 4);
        assertTrue("3:", nl.get(3) == H_LIST);
    }

    /**
     * <testcase>Two instances plus some glue fits vertically </testcase>
     * 
     * @throws GeneralException in case of an error
     */
    @Test
    public final void testAtShippingV5() throws GeneralException {

        AbstractLeadersNode node = makeNode(V_LIST, FixedGlue.S_S);
        assertNotNull(node);
        Dimen ht = new Dimen(2*(Dimen.ONE_INCH.getValue() + 1) + 12);
        node.setHeight(ht);
        Node n = node.atShipping(null, null, null, null);
        assertNotNull(n);
        assertTrue(n.getClass().getName(), n instanceof VerticalListNode);
        assertTrue("width " + n.getWidth().toString(), //
            n.getWidth().eq(Dimen.ONE_PT));
        assertTrue("height " + n.getHeight().toString(), //
            n.getHeight().eq(ht));
        assertTrue("depth " + n.getDepth().toString(), //
            n.getDepth().eq(Dimen.ZERO_PT));
        NodeList nl = (NodeList) n;
        int size = nl.size();
        assertTrue("size " + Integer.toString(size), 4 == size);
        Node node0 = nl.get(0);
        assertTrue("0:", node0 instanceof ImplicitKernNode);
        assertTrue("0: height", node0.getHeight().getValue() == 4);
        assertTrue("1:", nl.get(1) == V_LIST);
        Node node2 = nl.get(2);
        assertTrue("2:", node2 instanceof ImplicitKernNode);
        assertTrue("2: height", node2.getHeight().getValue() == 4);
        assertTrue("3:", nl.get(3) == V_LIST);
    }

}
