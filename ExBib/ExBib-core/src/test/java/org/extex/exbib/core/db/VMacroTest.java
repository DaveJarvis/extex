/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.extex.exbib.core.db.impl.DBImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a test suite for {@link VMacro}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class VMacroTest {

    /**
     * The field {@code db} contains the database.
     */
    private DB db;

    /**
     * The field {@code hit} contains the hit indicator.
     */
    private boolean hit;

    /**
     * Create a new object.
     */
    public VMacroTest() {

    }

    /**
     * Setup function.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        db = new DBImpl();
        db.storeString("jan", new Value(new VString("January")));
        db.storeString("feb", new Value(new VString("February")));
        db.storeString("mar", new Value(new VString("March")));
    }

    /**
     *  An undefined macro expands to the empty string.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand0() throws Exception {

        StringBuilder sb = new StringBuilder();
        new VMacro("j").expand(sb, db);
        assertEquals("", sb.toString());
    }

    /**
     *  A defined macro in lowercase is expanded.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand1() throws Exception {

        StringBuilder sb = new StringBuilder();
        new VMacro("jan").expand(sb, db);
        assertEquals("January", sb.toString());
    }

    /**
     *  A defined macro in mixed case is expanded.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand2() throws Exception {

        StringBuilder sb = new StringBuilder();
        new VMacro("jAn").expand(sb, db);
        assertEquals("January", sb.toString());
    }

    /**
     *  toString() returns the argument of the constructor.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToString1() throws Exception {

        assertEquals("xyzzy", new VMacro("xyzzy").toString());
    }

    /**
     *  toString() returns the content.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToString2() throws Exception {

        VMacro macro = new VMacro("aaa");
        macro.setContent("xyzzy");
        assertEquals("xyzzy", macro.toString());
    }

    /**
     *  the visit method invokes the correct case.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVisit1() throws Exception {

        hit = false;
        new VMacro("xyzzy").visit(new ValueVisitor() {

            public void visitBlock(VBlock value, DB d) throws IOException {

                assertTrue(false);
            }

            public void visitMacro(VMacro value, DB d) throws IOException {

                hit = true;
            }

            public void visitNumber(VNumber value, DB d) throws IOException {

                assertTrue(false);
            }

            public void visitString(VString value, DB d) throws IOException {

                assertTrue(false);
            }

            public void visitValue(Value value, DB d) throws IOException {

                assertTrue(false);
            }
        }, db);
        assertTrue(hit);
    }
}
