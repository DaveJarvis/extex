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

package org.extex.font.format.tfm;

import java.io.FileInputStream;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;

import junit.framework.TestCase;

/**
 * Test for the TfmReader.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class TfmReaderTest extends TestCase {

    /**
     * The tfm reader.
     */
    private TfmReader reader;

    /**
     * Creates a new object.
     *
     * @throws Exception if an error occurred.
     */
    public TfmReaderTest() throws Exception {

        reader = new TfmReader(new FileInputStream(
                "../texmf/src/texmf/fonts/tfm/public/cm/cmr10.tfm"), "cmr10");
    }

    /**
     * Test method for {@link org.extex.font.format.tfm.TfmReader#getParam(java.lang.String)}.
     * @throws Exception TODO mgn
     */
    public void testGetParamString01() throws Exception {

        FixedDimen ex = reader.getParam("XHEIGHT");
        assertNotNull(ex);
        assertTrue(ex.toString(), new Dimen(282168).eq(ex));
    }

    /**
     * Test method for {@link org.extex.font.format.tfm.TfmReader#getParam(java.lang.String)}.
     * @throws Exception TODO mgn
     */
    public void testGetParamString02() throws Exception {

        FixedDimen ex = reader.getParam("xheight");
        assertNotNull(ex);
        assertTrue(ex.toString(), new Dimen(282168).eq(ex));
    }

    /**
     * Test method for {@link org.extex.font.format.tfm.TfmReader#getParam(java.lang.String)}.
     * @throws Exception TODO mgn
     */
    public void testGetParamString03() throws Exception {

        FixedDimen ex = reader.getParam("5");
        assertNotNull(ex);
        assertTrue(ex.toString(), new Dimen(Dimen.ONE * 430555 / 100000).eq(ex));
    }

    /**
     * Test method for {@link org.extex.font.format.tfm.TfmReader#getParam(java.lang.String)}.
     * @throws Exception TODO mgn
     */
    public void testGetParamString04() throws Exception {

        FixedDimen ex = reader.getParam("45");
        assertNotNull(ex);
        assertTrue(ex.toString(), Dimen.ZERO_PT.eq(ex));
    }

    /**
     * Test method for {@link org.extex.font.format.tfm.TfmReader#getParam(java.lang.String)}.
     * @throws Exception TODO mgn
     */
    public void testGetParamString05() throws Exception {

        FixedDimen ex = reader.getParam(null);
        assertNotNull(ex);
        assertTrue(ex.toString(), Dimen.ZERO_PT.eq(ex));
    }

    //    {"SLANT", "SPACE", "STRETCH",
    //        "SHRINK", "XHEIGHT", "QUAD", "EXTRASPACE"}

    /**
     * test width
     * @throws Exception TODO mgn
     */
    public void testWidth01() throws Exception {

        TfmFixWord w = reader.getWidth(-1);
        assertNull(w);
    }

    /**
     * Dimen for 10pt
     */
    private static final Dimen DIM10 = new Dimen(Dimen.ONE * 10);

    /**
     * test width
     * @throws Exception TODO mgn
     */
    public void testWidth02() throws Exception {

        TfmFixWord w = reader.getWidth(0);
        assertNotNull(w);
        assertTrue(w.toString(), new Dimen(409601).eq(w.toDimen(DIM10)));

    }

    /**
     * test width
     * @throws Exception TODO mgn
     */
    public void testWidth03() throws Exception {

        TfmFixWord w = reader.getWidth(0x100);
        assertNull(w);

    }

    /**
     * test width
     * @throws Exception TODO mgn
     */
    public void testHeight01() throws Exception {

        TfmFixWord h = reader.getHeight(-1);
        assertNull(h);
    }

    /**
     * test width
     * @throws Exception TODO mgn
     */
    public void testHeight02() throws Exception {

        TfmFixWord h = reader.getHeight(0);
        assertNotNull(h);
        assertTrue(h.toString(), new Dimen(447828).eq(h.toDimen(DIM10)));

    }

    /**
     * test width
     * @throws Exception TODO mgn
     */
    public void testHeight03() throws Exception {

        TfmFixWord h = reader.getWidth(0x100);
        assertNull(h);

    }

    /**
     * test depth
     * @throws Exception TODO mgn
     */
    public void testDepth01() throws Exception {

        TfmFixWord d = reader.getDepth(-1);
        assertNull(d);
    }

    /**
     * test depth
     * @throws Exception TODO mgn
     */
    public void testDepth02() throws Exception {

        TfmFixWord d = reader.getDepth(0);
        assertNotNull(d);
        assertTrue(d.toString(), new Dimen(0).eq(d.toDimen(DIM10)));

    }

    /**
     * test depth
     * @throws Exception TODO mgn
     */
    public void testDepth03() throws Exception {

        TfmFixWord d = reader.getDepth(0x100);
        assertNull(d);

    }

    /**
     * test depth
     * @throws Exception TODO mgn
     */
    public void testDepth04() throws Exception {

        TfmFixWord d = reader.getDepth(17);
        assertNotNull(d);
        assertTrue(d.toString(), new Dimen(127431).eq(d.toDimen(DIM10)));

    }

    /**
     * test italic correction
     * @throws Exception TODO mgn
     */
    public void testItalic01() throws Exception {

        TfmFixWord i = reader.getItalicCorrection(-1);
        assertNull(i);
    }

    /**
     * test italic correction
     * @throws Exception TODO mgn
     */
    public void testItalic02() throws Exception {

        TfmFixWord i = reader.getItalicCorrection(0);
        assertNotNull(i);
        assertTrue(i.toString(), new Dimen(0).eq(i.toDimen(DIM10)));

    }

    /**
     * test italic correction
     * @throws Exception TODO mgn
     */
    public void testItalic03() throws Exception {

        TfmFixWord i = reader.getItalicCorrection(0x100);
        assertNull(i);

    }

    /**
     * test italic correction
     * @throws Exception TODO mgn
     */
    public void testItalic04() throws Exception {

        TfmFixWord i = reader.getItalicCorrection(86);
        assertNotNull(i);
        assertTrue(i.toString(), new Dimen(9101).eq(i.toDimen(DIM10)));

    }

}
