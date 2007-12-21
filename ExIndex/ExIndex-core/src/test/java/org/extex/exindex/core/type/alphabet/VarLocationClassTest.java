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

package org.extex.exindex.core.type.alphabet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.core.type.page.VarPage;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class VarLocationClassTest {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        VarLocationClass var = new VarLocationClass();
        assertNotNull(var.match("", ""));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        VarLocationClass var = new VarLocationClass();
        var.add("123");
        PageReference pr = var.match("", "123");
        assertTrue(pr instanceof VarPage);
        assertEquals("123", pr.getPage());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        VarLocationClass var = new VarLocationClass();
        var.add(new ArabicNumbers());
        PageReference match = var.match("", "123");
        assertNotNull(match);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        VarLocationClass var = new VarLocationClass();
        var.add(new ArabicNumbers());
        assertNull(var.match("", "abc"));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test5() throws Exception {

        VarLocationClass var = new VarLocationClass();
        var.add(new ArabicNumbers());
        var.add(".");
        var.add(new ArabicNumbers());
        PageReference match = var.match("", "12.34");
        assertNotNull(match);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test6() throws Exception {

        VarLocationClass var = new VarLocationClass();
        var.add(new AlphaUppercase());
        var.add(new ArabicNumbers());
        PageReference match = var.match("", "A34");
        assertNotNull(match);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test7() throws Exception {

        VarLocationClass var = new VarLocationClass();
        var.add(new AlphaUppercase());
        var.add(new ArabicNumbers());
        PageReference match = var.match("", "A-34");
        assertNull(match);
    }

}
