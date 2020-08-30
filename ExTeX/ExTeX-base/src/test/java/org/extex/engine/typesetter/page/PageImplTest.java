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

package org.extex.engine.typesetter.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.extex.typesetter.type.node.VerticalListNode;
import org.junit.Test;

/**
 * This is a test suite for the page implementation.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PageImplTest {

    /**
     *  The default color is undefined.
     */
    @Test
    public final void testGetColor() {

        assertNull(new PageImpl(new VerticalListNode(), null).getColor());
    }

    /**
     *  The default media height is 845.0467pt.
     */
    @Test
    public final void testGetMediaHeight() {

        assertEquals("845.0467pt",
            new PageImpl(new VerticalListNode(), null).getMediaHeight()
                .toString());
    }

    /**
     *  The default media horizontal offset is 1in=72.27pt.
     *
     */
    @Test
    public final void testGetMediaHOffset() {

        assertEquals("72.26999pt",
            new PageImpl(new VerticalListNode(), null).getMediaHOffset()
                .toString());
    }

    /**
     *  The default media vertical offset is 1in=72.27pt.
     */
    @Test
    public final void testGetMediaVOffset() {

        assertEquals("72.26999pt",
            new PageImpl(new VerticalListNode(), null).getMediaVOffset()
                .toString());
    }

    /**
     * Test method for
     * {@link org.extex.engine.typesetter.page.PageImpl#getMediaWidth()}.
     */
    @Test
    public final void testGetMediaWidth() {

        assertEquals("597.50778pt",
            new PageImpl(new VerticalListNode(), null).getMediaWidth()
                .toString());
    }

    /**
     * Test method for
     * {@link org.extex.engine.typesetter.page.PageImpl#getNodes()}.
     */
    @Test
    public final void testGetNodes() {

        VerticalListNode list = new VerticalListNode();
        assertEquals("\\vbox(0.0pt+0.0pt)x0.0pt",
            new PageImpl(list, null).getNodes().toString());
    }

    /**
     * Test method for
     * {@link org.extex.engine.typesetter.page.PageImpl#getPageNo()}.
     */
    @Test
    public final void testGetPageNo() {

        assertNull(new PageImpl(new VerticalListNode(), null).getPageNo());
    }

}
