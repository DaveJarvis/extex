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

package org.extex.exindex.core.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.extex.exindex.core.type.rules.Rule;
import org.extex.exindex.core.type.rules.StringRule;
import org.junit.Test;

/**
 * This is a test suite for the {@link StringRule}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StringRuleTest {

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.rules.StringRule#apply(java.lang.StringBuilder, int)}.
     */
    @Test(expected = RuntimeException.class)
    public final void testApply01() {

        new StringRule("b", "abc", true);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.rules.StringRule#apply(java.lang.StringBuilder, int)}.
     */
    @Test
    public final void testApply02() {

        assertNotNull(new StringRule("b", "abc", false));
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.rules.StringRule#apply(java.lang.StringBuilder, int)}.
     */
    @Test
    public final void testApply11() {

        Rule r = new StringRule("b", "B", false);
        StringBuilder sb = new StringBuilder("abc");
        assertTrue(r.apply(sb, 0) < 0);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.rules.StringRule#apply(java.lang.StringBuilder, int)}.
     */
    @Test
    public final void testApply12() {

        Rule r = new StringRule("b", "B", false);
        StringBuilder sb = new StringBuilder("abc");
        assertTrue(r.apply(sb, 2) < 0);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.rules.StringRule#apply(java.lang.StringBuilder, int)}.
     */
    @Test
    public final void testApply13() {

        Rule r = new StringRule("b", "B", false);
        StringBuilder sb = new StringBuilder("abc");
        assertTrue(r.apply(sb, 3) < 0);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.rules.StringRule#apply(java.lang.StringBuilder, int)}.
     */
    @Test
    public final void testApply14() {

        Rule r = new StringRule("b", "B", false);
        StringBuilder sb = new StringBuilder("abc");
        assertEquals(2, r.apply(sb, 1));
        assertEquals("aBc", sb.toString());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.rules.StringRule#apply(java.lang.StringBuilder, int)}.
     */
    @Test
    public final void testApply15() {

        Rule r = new StringRule("b", "B", false);
        StringBuilder sb = new StringBuilder("ab");
        assertEquals(2, r.apply(sb, 1));
        assertEquals("aB", sb.toString());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.rules.StringRule#apply(java.lang.StringBuilder, int)}.
     */
    @Test
    public final void testApply16() {

        Rule r = new StringRule("b", ".B.", false);
        StringBuilder sb = new StringBuilder("ab");
        assertEquals(2, r.apply(sb, 1));
        assertEquals("a.B.", sb.toString());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.rules.StringRule#apply(java.lang.StringBuilder, int)}.
     */
    @Test
    public final void testApply24() {

        Rule r = new StringRule("b", "B", true);
        StringBuilder sb = new StringBuilder("abc");
        assertEquals(2, r.apply(sb, 1));
        assertEquals("aBc", sb.toString());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.type.rules.StringRule#apply(java.lang.StringBuilder, int)}.
     */
    @Test
    public final void testApply25() {

        Rule r = new StringRule("b", "B", true);
        StringBuilder sb = new StringBuilder("ab");
        assertEquals(2, r.apply(sb, 1));
        assertEquals("aB", sb.toString());
    }

}
