/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.interaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.extex.core.exception.GeneralException;
import org.extex.interpreter.exception.InteractionUnknownException;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the interaction constants.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class InteractionTest {

    /**
     * Sensor for the interaction mode.
     */
    private static class Sensor
            implements
                InteractionVisitor<Object, Object, Object> {

        /**
         * The field <tt>mode</tt> contains the interaction mode encountered
         * last.
         */
        private Interaction mode = null;

        /**
         * Invoke the method in case of a batchmode interaction.
         * 
         * @param arg1 the first argument
         * @param arg2 the second argument
         * @param arg3 the third argument
         * 
         * @return a boolean indicator
         * 
         * @throws GeneralException in case of an error
         * 
         * @see org.extex.interpreter.interaction.InteractionVisitor#visitBatchmode(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public boolean visitBatchmode(Object arg1, Object arg2, Object arg3)
                throws GeneralException {

            mode = Interaction.BATCHMODE;
            return false;
        }

        /**
         * Invoke the method in case of a error-stop mode interaction.
         * 
         * @param arg1 the first argument
         * @param arg2 the second argument
         * @param arg3 the third argument
         * 
         * @return a boolean indicator
         * 
         * @throws GeneralException in case of an error
         * 
         * @see org.extex.interpreter.interaction.InteractionVisitor#visitErrorstopmode(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public boolean visitErrorstopmode(Object arg1, Object arg2, Object arg3)
                throws GeneralException {

            mode = Interaction.ERRORSTOPMODE;
            return false;
        }

        /**
         * Invoke the method in case of a non-stop mode interaction.
         * 
         * @param arg1 the first argument
         * @param arg2 the second argument
         * @param arg3 the third argument
         * 
         * @return a boolean indicator
         * 
         * @throws GeneralException in case of an error
         * 
         * @see org.extex.interpreter.interaction.InteractionVisitor#visitNonstopmode(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public boolean visitNonstopmode(Object arg1, Object arg2, Object arg3)
                throws GeneralException {

            mode = Interaction.NONSTOPMODE;
            return false;
        }

        /**
         * Invoke the method in case of a scroll mode interaction.
         * 
         * @param arg1 the first argument
         * @param arg2 the second argument
         * @param arg3 the third argument
         * 
         * @return a boolean indicator
         * 
         * @throws GeneralException in case of an error
         * 
         * @see org.extex.interpreter.interaction.InteractionVisitor#visitScrollmode(java.lang.Object,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public boolean visitScrollmode(Object arg1, Object arg2, Object arg3)
                throws GeneralException {

            mode = Interaction.SCROLLMODE;
            return false;
        }

    };

    /**
     * Set up the test case.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#getIndex()}.
     */
    @Test
    public void testGetIndex1() {

        assertEquals("0", Interaction.BATCHMODE.getIndex());
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#getIndex()}.
     */
    @Test
    public void testGetIndex2() {

        assertEquals("1", Interaction.NONSTOPMODE.getIndex());
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#getIndex()}.
     */
    @Test
    public void testGetIndex3() {

        assertEquals("2", Interaction.SCROLLMODE.getIndex());
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#getIndex()}.
     */
    @Test
    public void testGetIndex4() {

        assertEquals("3", Interaction.ERRORSTOPMODE.getIndex());
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(int)}.
     */
    @Test
    public void testGetInt01() {

        try {
            Interaction.get(-1);
            assertTrue(false);
        } catch (InteractionUnknownException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(int)}.
     */
    @Test
    public void testGetInt02() {

        try {
            Interaction.get(4);
            assertTrue(false);
        } catch (InteractionUnknownException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(int)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetInt1() throws Exception {

        assertEquals(Interaction.BATCHMODE, Interaction.get(0));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(int)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetInt2() throws Exception {

        assertEquals(Interaction.NONSTOPMODE, Interaction.get(1));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(int)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetInt3() throws Exception {

        assertEquals(Interaction.SCROLLMODE, Interaction.get(2));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(int)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetInt4() throws Exception {

        assertEquals(Interaction.ERRORSTOPMODE, Interaction.get(3));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(org.extex.interpreter.interaction.Interaction)}
     * .
     */
    @Test
    public void testGetInteraction0() {

        try {
            Interaction.get((Interaction) null);
            assertTrue(false);
        } catch (InteractionUnknownException e) {
            assertEquals("message", "", e.getMessage());
            assertEquals("localized message", "Bad interaction mode ()", //
                e.getLocalizedMessage());
        }
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(org.extex.interpreter.interaction.Interaction)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetInteraction1() throws Exception {

        assertEquals(0, Interaction.get(Interaction.BATCHMODE));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(org.extex.interpreter.interaction.Interaction)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetInteraction2() throws Exception {

        assertEquals(1, Interaction.get(Interaction.NONSTOPMODE));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(org.extex.interpreter.interaction.Interaction)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetInteraction3() throws Exception {

        assertEquals(2, Interaction.get(Interaction.SCROLLMODE));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(org.extex.interpreter.interaction.Interaction)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetInteraction4() throws Exception {

        assertEquals(3, Interaction.get(Interaction.ERRORSTOPMODE));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(java.lang.String)}
     * .
     */
    @Test
    public void testGetString01() {

        try {
            Interaction.get((String) null);
            assertTrue(false);
        } catch (InteractionUnknownException e) {
            assertEquals("", e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(java.lang.String)}
     * .
     */
    @Test
    public void testGetString02() {

        try {
            Interaction.get("");
            assertTrue(false);
        } catch (InteractionUnknownException e) {
            assertEquals("", e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(java.lang.String)}
     * .
     */
    @Test
    public void testGetString03() {

        try {
            Interaction.get("abc");
            assertTrue(false);
        } catch (InteractionUnknownException e) {
            assertEquals("", e.getMessage());
            assertEquals("Bad interaction mode (abc)", e.getLocalizedMessage());
        }
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(java.lang.String)}
     * .
     */
    @Test
    public void testGetString04() {

        try {
            Interaction.get("Batchmode");
            assertTrue(false);
        } catch (InteractionUnknownException e) {
            assertEquals("", e.getMessage());
            assertEquals("Bad interaction mode (Batchmode)",
                e.getLocalizedMessage());
        }
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetString1() throws Exception {

        assertEquals(Interaction.BATCHMODE, Interaction.get("batchmode"));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetString11() throws Exception {

        assertEquals(Interaction.BATCHMODE, Interaction.get("batchmod"));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetString12() throws Exception {

        assertEquals(Interaction.BATCHMODE, Interaction.get("batch"));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetString13() throws Exception {

        assertEquals(Interaction.BATCHMODE, Interaction.get("b"));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetString2() throws Exception {

        assertEquals(Interaction.NONSTOPMODE, Interaction.get("nonstopmode"));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetString3() throws Exception {

        assertEquals(Interaction.SCROLLMODE, Interaction.get("scrollmode"));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#get(java.lang.String)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetString4() throws Exception {

        assertEquals(Interaction.ERRORSTOPMODE,
            Interaction.get("errorstopmode"));
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#visit(org.extex.interpreter.interaction.InteractionVisitor, java.lang.Object, java.lang.Object, java.lang.Object)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVisit0() throws Exception {

        Sensor visitor = new Sensor();
        Interaction.BATCHMODE.visit(visitor, "arg1", "arg2", "arg3");
        assertEquals(Interaction.BATCHMODE, visitor.mode);
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#visit(org.extex.interpreter.interaction.InteractionVisitor, java.lang.Object, java.lang.Object, java.lang.Object)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVisit1() throws Exception {

        Sensor visitor = new Sensor();
        Interaction.NONSTOPMODE.visit(visitor, "arg1", "arg2", "arg3");
        assertEquals(Interaction.NONSTOPMODE, visitor.mode);
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#visit(org.extex.interpreter.interaction.InteractionVisitor, java.lang.Object, java.lang.Object, java.lang.Object)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVisit2() throws Exception {

        Sensor visitor = new Sensor();
        Interaction.SCROLLMODE.visit(visitor, "arg1", "arg2", "arg3");
        assertEquals(Interaction.SCROLLMODE, visitor.mode);
    }

    /**
     * Test method for
     * {@link org.extex.interpreter.interaction.Interaction#visit(org.extex.interpreter.interaction.InteractionVisitor, java.lang.Object, java.lang.Object, java.lang.Object)}
     * .
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVisit3() throws Exception {

        Sensor visitor = new Sensor();
        Interaction.ERRORSTOPMODE.visit(visitor, "arg1", "arg2", "arg3");
        assertEquals(Interaction.ERRORSTOPMODE, visitor.mode);
    }

}
