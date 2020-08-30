/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.extex.core.dimen.Dimen;
import org.junit.Test;

/**
 * Test for TfmFixWord.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class TfmFixWordTest {

    /**
     * Test method for
     * {@link org.extex.font.format.tfm.TfmFixWord#toDimen(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public void testToDimen01() {

        TfmFixWord fw = new TfmFixWord(0);
        try {
            fw.toDimen(null);
            assertFalse(true);
        } catch (IllegalArgumentException e) {
            assertEquals("size", e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link org.extex.font.format.tfm.TfmFixWord#toDimen(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public void testToDimen02() {

        TfmFixWord fw = new TfmFixWord(0);
        Dimen d = fw.toDimen(Dimen.ONE_PT);
        assertEquals(0, d.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.font.format.tfm.TfmFixWord#toDimen(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public void testToDimen03() {

        TfmFixWord fw = new TfmFixWord(1);
        Dimen d = fw.toDimen(Dimen.ONE_PT);
        assertEquals(Dimen.ONE, d.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.font.format.tfm.TfmFixWord#toDimen(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public void testToDimen04() {

        TfmFixWord fw = new TfmFixWord(12);
        Dimen d = fw.toDimen(Dimen.ONE_PT);
        assertEquals(Dimen.ONE * 12, d.getValue());
    }

    /**
     * Test method for
     * {@link org.extex.font.format.tfm.TfmFixWord#toDimen(org.extex.core.dimen.FixedDimen)}
     * .
     */
    @Test
    public void testToDimen05() {

        TfmFixWord fw = new TfmFixWord(1, 2);
        Dimen d = fw.toDimen(Dimen.ONE_PT);
        assertEquals(Dimen.ONE / 2, d.getValue());
    }

}
