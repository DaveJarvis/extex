/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This is the test suite for the class Switch.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SwitchTest {

    /**
     * Test method for {@link org.extex.core.Switch#isOn()}.
     */
    @Test
    public final void testIsOn1() {

        Switch s = new Switch(true);
        assertEquals(true, s.isOn());
    }

    /**
     * Test method for {@link org.extex.core.Switch#isOn()}.
     */
    @Test
    public final void testIsOn2() {

        Switch s = new Switch(false);
        assertEquals(false, s.isOn());
    }

    /**
     * Test method for {@link org.extex.core.Switch#set(boolean)}.
     */
    @Test
    public final void testSet1() {

        Switch s = new Switch(true);
        s.set(false);
        assertEquals(false, s.isOn());
    }

    /**
     * Test method for {@link org.extex.core.Switch#set(boolean)}.
     */
    @Test
    public final void testSet2() {

        Switch s = new Switch(true);
        s.set(true);
        assertEquals(true, s.isOn());
    }

    /**
     * Test method for {@link org.extex.core.Switch#set(boolean)}.
     */
    @Test
    public final void testSet3() {

        Switch s = new Switch(false);
        s.set(false);
        assertEquals(false, s.isOn());
    }

    /**
     * Test method for {@link org.extex.core.Switch#set(boolean)}.
     */
    @Test
    public final void testSet4() {

        Switch s = new Switch(false);
        s.set(true);
        assertEquals(true, s.isOn());
    }

    /**
     * Test method for {@link org.extex.core.Switch#toString()}.
     */
    @Test
    public final void testToString1() {

        Switch s = new Switch(false);
        assertEquals("off", s.toString());
    }

    /**
     * Test method for {@link org.extex.core.Switch#toString()}.
     */
    @Test
    public final void testToString2() {

        Switch s = new Switch(true);
        assertEquals("on", s.toString());
    }

    /**
     * Test method for {@link org.extex.core.Switch#toString()}.
     */
    @Test
    public final void testToString3() {

        Switch s = new Switch(true);
        s.set(false);
        assertEquals("off", s.toString());
    }
}
