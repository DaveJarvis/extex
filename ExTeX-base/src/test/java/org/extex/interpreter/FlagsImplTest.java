/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter;

import java.util.Locale;

import junit.framework.TestCase;

/**
 * Test cases for the flags implementation.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4784 $
 */
public class FlagsImplTest extends TestCase {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(FlagsImplTest.class);
    }

    /**
     */
    public void testClear() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setImmediate();
        f.setLong();
        f.setOuter();
        f.setProtected();
        f.clear();
        assertFalse(f.isGlobal());
        assertFalse(f.isImmediate());
        assertFalse(f.isLong());
        assertFalse(f.isOuter());
        assertFalse(f.isProtected());
    }

    /**
     */
    public void testClearGlobal1() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setImmediate();
        f.setLong();
        f.setOuter();
        f.setProtected();
        assertTrue(f.clearGlobal());
        assertEquals("-LOIP", f.toString());
    }

    /**
     */
    public void testClearGlobal2() {

        Flags f = new FlagsImpl();
        f.setImmediate();
        f.setLong();
        f.setOuter();
        f.setProtected();
        assertFalse(f.clearGlobal());
        assertEquals("-LOIP", f.toString());
    }

    /**
     */
    public void testClearImmediate1() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setImmediate();
        f.setLong();
        f.setOuter();
        f.setProtected();
        assertTrue(f.clearImmediate());
        assertEquals("GLO-P", f.toString());
    }

    /**
     */
    public void testClearImmediate2() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setLong();
        f.setOuter();
        f.setProtected();
        assertFalse(f.clearImmediate());
        assertEquals("GLO-P", f.toString());
    }

    /**
     */
    public void testClearLong1() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setImmediate();
        f.setLong();
        f.setOuter();
        f.setProtected();
        assertTrue(f.clearLong());
        assertEquals("G-OIP", f.toString());
    }

    /**
     */
    public void testClearLong2() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setImmediate();
        f.setOuter();
        f.setProtected();
        assertFalse(f.clearLong());
        assertEquals("G-OIP", f.toString());
    }

    /**
     */
    public void testClearOuter1() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setImmediate();
        f.setLong();
        f.setOuter();
        f.setProtected();
        assertTrue(f.clearOuter());
        assertEquals("GL-IP", f.toString());
    }

    /**
     */
    public void testClearOuter2() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setImmediate();
        f.setLong();
        f.setProtected();
        assertFalse(f.clearOuter());
        assertEquals("GL-IP", f.toString());
    }

    /**
     */
    public void testClearProtected1() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setImmediate();
        f.setLong();
        f.setOuter();
        f.setProtected();
        assertTrue(f.clearProtected());
        assertEquals("GLOI-", f.toString());
    }

    /**
     */
    public void testClearProtected2() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setImmediate();
        f.setLong();
        f.setOuter();
        assertFalse(f.clearProtected());
        assertEquals("GLOI-", f.toString());
    }

    /**
     */
    public void testCopy0() {

        Flags fl = new FlagsImpl();
        Flags f = fl.copy();
        assertNotSame(fl, f);
        assertEquals("-----", f.toString());
    }

    /**
     */
    public void testCopy1() {

        Flags fl = new FlagsImpl();
        fl.setGlobal();
        fl.setImmediate();
        fl.setLong();
        fl.setOuter();
        fl.setProtected();
        Flags f = fl.copy();
        assertNotSame(fl, f);
        assertEquals("GLOIP", f.toString());
    }

    /**
     */
    public void testCopy2() {

        Flags fl = new FlagsImpl();
        fl.setGlobal();
        fl.setOuter();
        Flags f = fl.copy();
        assertNotSame(fl, f);
        assertEquals("G-O--", f.toString());
    }

    /**
     */
    public void testIsDirty0() {

        Flags f = new FlagsImpl();
        assertFalse(f.isDirty());
    }

    /**
     */
    public void testIsDirty1() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        assertTrue(f.isDirty());
    }

    /**
     */
    public void testIsDirty2() {

        Flags f = new FlagsImpl();
        f.setOuter();
        assertTrue(f.isDirty());
    }

    /**
     */
    public void testIsDirty3() {

        Flags f = new FlagsImpl();
        f.setImmediate();
        assertTrue(f.isDirty());
    }

    /**
     */
    public void testIsDirty5() {

        Flags f = new FlagsImpl();
        f.setProtected();
        assertTrue(f.isDirty());
    }

    /**
     */
    public void testIsDirty6() {

        Flags f = new FlagsImpl();
        f.setLong();
        assertTrue(f.isDirty());
    }

    /**
     */
    public void testIsDirty7() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setLong();
        f.setOuter();
        f.setProtected();
        f.setImmediate();
        assertTrue(f.isDirty());
    }

    /**
     */
    public void testIsGlobal() {

        Flags f = new FlagsImpl();
        assertFalse(f.isGlobal());
    }

    /**
     */
    public void testIsImmediate() {

        Flags f = new FlagsImpl();
        assertFalse(f.isImmediate());
    }

    /**
     */
    public void testIsLong() {

        Flags f = new FlagsImpl();
        assertFalse(f.isLong());
    }

    /**
     */
    public void testIsOuter() {

        Flags f = new FlagsImpl();
        assertFalse(f.isOuter());
    }

    /**
     */
    public void testIsProtected() {

        Flags f = new FlagsImpl();
        assertFalse(f.isProtected());
    }

    /**
     */
    public void testSet1() {

        Flags fl = new FlagsImpl();
        Flags f = new FlagsImpl();
        f.set(fl);
        assertEquals("-----", f.toString());
    }

    /**
     */
    public void testSet2() {

        Flags fl = new FlagsImpl();
        fl.setGlobal();
        Flags f = new FlagsImpl();
        f.set(fl);
        assertEquals("G----", f.toString());
    }

    /**
     */
    public void testSet3() {

        Flags fl = new FlagsImpl();
        fl.setGlobal();
        fl.setLong();
        fl.setOuter();
        fl.setProtected();
        fl.setImmediate();
        Flags f = new FlagsImpl();
        f.set(fl);
        assertEquals("GLOIP", f.toString());
    }

    /**
     */
    public void testSetGlobal() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        assertTrue(f.isGlobal());
        assertFalse(f.isImmediate());
        assertFalse(f.isLong());
        assertFalse(f.isOuter());
        assertFalse(f.isProtected());
    }

    /**
     */
    public void testSetGlobalBoolean1() {

        Flags f = new FlagsImpl();
        f.setGlobal(true);
        assertTrue(f.isGlobal());
        assertFalse(f.isImmediate());
        assertFalse(f.isLong());
        assertFalse(f.isOuter());
        assertFalse(f.isProtected());
    }

    /**
     */
    public void testSetGlobalBoolean2() {

        Flags f = new FlagsImpl();
        f.setGlobal(false);
        assertFalse(f.isGlobal());
        assertFalse(f.isImmediate());
        assertFalse(f.isLong());
        assertFalse(f.isOuter());
        assertFalse(f.isProtected());
    }

    /**
     */
    public void testSetImmediate() {

        Flags f = new FlagsImpl();
        f.setImmediate();
        assertFalse(f.isGlobal());
        assertTrue(f.isImmediate());
        assertFalse(f.isLong());
        assertFalse(f.isOuter());
        assertFalse(f.isProtected());
    }

    /**
     */
    public void testSetLong() {

        Flags f = new FlagsImpl();
        f.setLong();
        assertFalse(f.isGlobal());
        assertFalse(f.isImmediate());
        assertTrue(f.isLong());
        assertFalse(f.isOuter());
        assertFalse(f.isProtected());
    }

    /**
     */
    public void testSetOuter() {

        Flags f = new FlagsImpl();
        f.setOuter();
        assertFalse(f.isGlobal());
        assertFalse(f.isImmediate());
        assertFalse(f.isLong());
        assertTrue(f.isOuter());
        assertFalse(f.isProtected());
    }

    /**
     */
    public void testSetProtected() {

        Flags f = new FlagsImpl();
        f.setProtected();
        assertFalse(f.isGlobal());
        assertFalse(f.isImmediate());
        assertFalse(f.isLong());
        assertFalse(f.isOuter());
        assertTrue(f.isProtected());
    }

    /**
     */
    public void testToString0() {

        Flags f = new FlagsImpl();
        assertEquals("-----", f.toString());
    }

    /**
     */
    public void testToString1() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setLong();
        assertEquals("GL---", f.toString());
    }

    /**
     */
    public void testToString2() {

        Flags f = new FlagsImpl();
        f.setProtected();
        assertEquals("----P", f.toString());
    }

    /**
     */
    public void testToString3() {

        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setLong();
        f.setOuter();
        f.setProtected();
        f.setImmediate();
        assertEquals("GLOIP", f.toString());
    }

    /**
     */
    public void testToText0() {

        Flags f = new FlagsImpl();
        assertEquals("", f.toText());
    }

    /**
     */
    public void testToText1() {

        Locale.setDefault(Locale.ENGLISH);
        Flags f = new FlagsImpl();
        f.setGlobal();
        f.setLong();
        f.setOuter();
        f.setProtected();
        f.setImmediate();
        assertEquals("global, long, outer, immediate, and protected",
            f.toText());
    }

    /**
     */
    public void testToText2() {

        Locale.setDefault(Locale.ENGLISH);
        Flags f = new FlagsImpl();
        f.setLong();
        f.setOuter();
        f.setProtected();
        f.setImmediate();
        assertEquals("long, outer, immediate, and protected", f
            .toText());
    }

    /**
     */
    public void testToText3() {

        Locale.setDefault(Locale.ENGLISH);
        Flags f = new FlagsImpl();
        f.setOuter();
        f.setProtected();
        f.setImmediate();
        assertEquals("outer, immediate, and protected", f.toText());
    }

    /**
     */
    public void testToText4() {

        Locale.setDefault(Locale.ENGLISH);
        Flags f = new FlagsImpl();
        f.setProtected();
        f.setImmediate();
        assertEquals("immediate and protected", f.toText());
    }

    /**
     */
    public void testToText5() {

        Locale.setDefault(Locale.ENGLISH);
        Flags f = new FlagsImpl();
        f.setProtected();
        assertEquals("protected", f.toText());
    }

}
