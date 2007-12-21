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
import org.extex.exindex.core.type.page.SomePage;
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
    public void test01() throws Exception {

        VarLocationClass var = new VarLocationClass();
        assertNotNull(var.match("", ""));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test02() throws Exception {

        VarLocationClass var = new VarLocationClass();
        var.add("123");
        PageReference pr = var.match("", "123");
        assertTrue(pr instanceof SomePage);
        assertEquals("123", pr.getPage());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test03() throws Exception {

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
    public void test04() throws Exception {

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
    public void test05() throws Exception {

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
    public void test06() throws Exception {

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
    public void test07() throws Exception {

        VarLocationClass var = new VarLocationClass();
        var.add(new AlphaUppercase());
        var.add(new ArabicNumbers());
        assertNull(var.match("", "A-34"));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test08() throws Exception {

        VarLocationClass var = new VarLocationClass();
        var.add(new AlphaUppercase());
        var.add("-");
        var.add("-");
        var.add(new ArabicNumbers());
        PageReference match = var.match("", "A--34");
        assertNotNull(match);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test09() throws Exception {

        VarLocationClass var = new VarLocationClass();
        var.add(new AlphaUppercase());
        var.add("-");
        var.add("");
        var.add("-");
        var.add(new ArabicNumbers());
        PageReference match = var.match("", "A--34");
        assertNotNull(match);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        VarLocationClass var = new VarLocationClass();
        var.add("abc");
        assertNull(var.match("", "aBC"));
    }

}
