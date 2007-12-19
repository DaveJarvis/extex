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

package org.extex.exindex.core.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.extex.exindex.lisp.exception.LException;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LetterGroupContainerTest {

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#defineLetterGroup(java.lang.String)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testDefineLetterGroup() throws Exception {

        new LetterGroupContainer();
        assertTrue(true);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted00() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        List<LetterGroup> sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(0, sorted.size());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted10() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        container.defineLetterGroup("a");
        List<LetterGroup> sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(1, sorted.size());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testSorted1Loop1() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        ga.after(ga);
        container.sorted();
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testSorted1Loop2() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        LetterGroup gb = container.defineLetterGroup("b");
        ga.after(gb);
        gb.after(ga);
        container.sorted();
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testSorted1Loop3() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        LetterGroup gb = container.defineLetterGroup("b");
        LetterGroup gc = container.defineLetterGroup("c");
        ga.after(gb);
        gb.after(gc);
        gc.after(ga);
        container.sorted();
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted200() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        container.defineLetterGroup("a");
        container.defineLetterGroup("b");
        List<LetterGroup> sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(2, sorted.size());
        assertEquals("a", sorted.get(0).getName());
        assertEquals("b", sorted.get(1).getName());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted201() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        LetterGroup gb = container.defineLetterGroup("b");
        ga.after(gb);
        List<LetterGroup> sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(2, sorted.size());
        assertEquals("b", sorted.get(0).getName());
        assertEquals("a", sorted.get(1).getName());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted202() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup gb = container.defineLetterGroup("b");
        LetterGroup ga = container.defineLetterGroup("a");
        gb.after(ga);
        List<LetterGroup> sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(2, sorted.size());
        assertEquals("a", sorted.get(0).getName());
        assertEquals("b", sorted.get(1).getName());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted203() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        LetterGroup gb = container.defineLetterGroup("b");
        gb.after(ga);
        List<LetterGroup> sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(2, sorted.size());
        assertEquals("a", sorted.get(0).getName());
        assertEquals("b", sorted.get(1).getName());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted300() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        container.defineLetterGroup("a");
        container.defineLetterGroup("b");
        container.defineLetterGroup("c");
        List<LetterGroup> sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(3, sorted.size());
        assertEquals("a", sorted.get(0).getName());
        assertEquals("b", sorted.get(1).getName());
        assertEquals("c", sorted.get(2).getName());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted301() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        LetterGroup gb = container.defineLetterGroup("b");
        container.defineLetterGroup("c");
        ga.after(gb);
        List<LetterGroup> sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(3, sorted.size());
        assertEquals("b", sorted.get(0).getName());
        assertEquals("a", sorted.get(1).getName());
        assertEquals("c", sorted.get(2).getName());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted302() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        LetterGroup gb = container.defineLetterGroup("b");
        LetterGroup gc = container.defineLetterGroup("c");
        ga.after(gb);
        ga.after(gc);
        List<LetterGroup> sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(3, sorted.size());
        assertEquals("b", sorted.get(0).getName());
        assertEquals("c", sorted.get(1).getName());
        assertEquals("a", sorted.get(2).getName());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted303() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        LetterGroup gb = container.defineLetterGroup("b");
        LetterGroup gc = container.defineLetterGroup("c");
        ga.after(gb);
        gb.after(gc);
        List<LetterGroup> sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(3, sorted.size());
        assertEquals("c", sorted.get(0).getName());
        assertEquals("b", sorted.get(1).getName());
        assertEquals("a", sorted.get(2).getName());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LetterGroupContainer#sorted()}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted303Repeat() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        LetterGroup gb = container.defineLetterGroup("b");
        LetterGroup gc = container.defineLetterGroup("c");
        ga.after(gb);
        gb.after(gc);
        container.sorted();
        List<LetterGroup> sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(3, sorted.size());
        assertEquals("c", sorted.get(0).getName());
        assertEquals("b", sorted.get(1).getName());
        assertEquals("a", sorted.get(2).getName());
    }

}
