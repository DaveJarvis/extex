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

import org.extex.exindex.core.exception.InconsistentLetterGroupException;
import org.extex.exindex.core.exception.LetterGroupCycleException;
import org.extex.exindex.core.exception.LetterGroupsClosedException;
import org.junit.Test;

/**
 * This is a test suite for the {@link LetterGroupContainer}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LetterGroupContainerTest {

    /**
     *  A letter group container can be instantiated.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testDefineLetterGroup() throws Exception {

        new LetterGroupContainer();
        assertTrue(true);
    }

    /**
     *  sorted() returns the empty list for an empty container.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted00() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup[] sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(0, sorted.length);
    }

    /**
     *  sorted() returns the list with one elements for an container
     * with one element.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted10() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        container.defineLetterGroup("a");
        LetterGroup[] sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(1, sorted.length);
    }

    /**
     *  sorted() throws an exception if a loop of length 1 is
     * contained in the container.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LetterGroupCycleException.class)
    public final void testSorted1Loop1() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        ga.after(ga);
        container.sorted();
    }

    /**
     *  sorted() throws an exception if a loop of length 2 is
     * contained in the container.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LetterGroupCycleException.class)
    public final void testSorted1Loop2() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        LetterGroup gb = container.defineLetterGroup("b");
        ga.after(gb);
        gb.after(ga);
        container.sorted();
    }

    /**
     *  sorted() throws an exception if a loop of length 3 is
     * contained in the container.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LetterGroupCycleException.class)
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
     *  sorted() returns a list with all elements if no constraint on
     * the order is given for a container with two elements.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted200() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        container.defineLetterGroup("a");
        container.defineLetterGroup("b");
        LetterGroup[] sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(2, sorted.length);
        assertEquals("a", sorted[0].getName());
        assertEquals("b", sorted[1].getName());
    }

    /**
     *  sorted() returns a properly sorted list with all elements for
     * two elements with a constraint in the container.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted201() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        LetterGroup gb = container.defineLetterGroup("b");
        ga.after(gb);
        LetterGroup[] sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(2, sorted.length);
        assertEquals("b", sorted[0].getName());
        assertEquals("a", sorted[1].getName());
    }

    /**
     *  sorted() returns a list with all elements if no constraint on
     * the order is given. Even if the elements are defined in reverse order.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted202() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup gb = container.defineLetterGroup("b");
        LetterGroup ga = container.defineLetterGroup("a");
        gb.after(ga);
        LetterGroup[] sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(2, sorted.length);
        assertEquals("a", sorted[0].getName());
        assertEquals("b", sorted[1].getName());
    }

    /**
     *  sorted() returns a list with all elements if no constraint on
     * the order is given. Another variant.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted203() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        LetterGroup ga = container.defineLetterGroup("a");
        LetterGroup gb = container.defineLetterGroup("b");
        gb.after(ga);
        LetterGroup[] sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(2, sorted.length);
        assertEquals("a", sorted[0].getName());
        assertEquals("b", sorted[1].getName());
    }

    /**
     *  sorted() returns a list with all elements if no constraint on
     * the order is given for a container with three elements.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testSorted300() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        container.defineLetterGroup("a");
        container.defineLetterGroup("b");
        container.defineLetterGroup("c");
        LetterGroup[] sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(3, sorted.length);
        assertEquals("a", sorted[0].getName());
        assertEquals("b", sorted[1].getName());
        assertEquals("c", sorted[2].getName());
    }

    /**
     *  sorted() returns a list with all elements if one constraint on
     * the order is given for a container with three elements.
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
        LetterGroup[] sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(3, sorted.length);
        assertEquals("b", sorted[0].getName());
        assertEquals("a", sorted[1].getName());
        assertEquals("c", sorted[2].getName());
    }

    /**
     *  sorted() returns a properly sorted list with all elements if
     * two constraint on the order is given for a container with three elements.
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
        LetterGroup[] sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(3, sorted.length);
        assertEquals("b", sorted[0].getName());
        assertEquals("c", sorted[1].getName());
        assertEquals("a", sorted[2].getName());
    }

    /**
     *  sorted() returns a properly sorted list with all elements if
     * two constraint on the order is given for a container with three elements.
     * Another variant.
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
        LetterGroup[] sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(3, sorted.length);
        assertEquals("c", sorted[0].getName());
        assertEquals("b", sorted[1].getName());
        assertEquals("a", sorted[2].getName());
    }

    /**
     *  sorted() returns a properly sorted list with all elements if
     * two constraint on the order is given for a container with three elements.
     * One more variant.
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
        LetterGroup[] sorted = container.sorted();
        assertNotNull(sorted);
        assertEquals(3, sorted.length);
        assertEquals("c", sorted[0].getName());
        assertEquals("b", sorted[1].getName());
        assertEquals("a", sorted[2].getName());
    }

    /**
     *  defineLetterGroup() can not be applied after sorted().
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LetterGroupsClosedException.class)
    public final void testSortedError1() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        container.defineLetterGroup("a");
        container.sorted();
        container.defineLetterGroup("b");
    }

    /**
     *  ...
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = InconsistentLetterGroupException.class)
    public final void testSortedInconsistent1() throws Exception {

        LetterGroupContainer container = new LetterGroupContainer();
        container.defineLetterGroup("a", "a", "b");
        container.defineLetterGroup("b", "b");
    }

}
