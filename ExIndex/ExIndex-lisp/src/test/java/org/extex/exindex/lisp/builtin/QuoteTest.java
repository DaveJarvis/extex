/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exindex.lisp.builtin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.extex.exindex.lisp.LEngine;
import org.extex.exindex.lisp.parser.LParser;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;
import org.junit.Test;

/**
 * This is a test suite for the function {@code quote}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class QuoteTest {

    /**
     * Make a node.
     * 
     * @param s the string representation
     * @return the node
     * @throws IOException in case of an error
     */
    private LValue makeNode(String s) throws IOException {

        return new LParser(new InputStreamReader(new ByteArrayInputStream(s
            .getBytes())), "rsc").read();
    }

    /**
     *  A quoted symbol is returned as symbol. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        LValue node = makeNode("'a ");
        LValue n = new LEngine().eval(node);
        assertNotNull(n);
        assertTrue(n instanceof LSymbol);
        assertEquals("a", n.toString());
    }

    /**
     *  A quoted list is returned as list. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        LValue node = makeNode("'(a) ");
        LValue n = new LEngine().eval(node);
        assertNotNull(n);
        assertTrue(n instanceof LList);
        assertEquals("(a)", n.toString());
    }

}
