/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package org.extex.unit.color;

import static org.junit.Assert.*;

import org.extex.color.Color;
import org.extex.color.model.GrayscaleColor;
import org.extex.color.model.RgbColor;
import org.extex.interpreter.Interpreter;
import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\color</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4732 $
 */
public class ColorTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * The field <tt>FIFTY_PERCENT</tt> contains the representation for .5.
     */
    private static final int FIFTY_PERCENT = 0x7fff;

    /**
     * The field <tt>THIRTY_PERCENT</tt> contains the representation for .3.
     */
    private static final int THIRTY_PERCENT = 19660;

    /**
     * The field <tt>TWENTY_PERCENT</tt> contains the representation for .2.
     */
    private static final int TWENTY_PERCENT = 13106;

    /**
     * The field <tt>TEN_PERCENT</tt> contains the representation for .1.
     */
    private static final int TEN_PERCENT = 6553;

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(ColorTest.class);
    }

    /**
     * Creates a new object.
     */
    public ColorTest() {

        super("color", "{.1 .2 .3}", "");
        setConfig("colorextex-test");
    }

    /**
     * <testcase primitive="\color"> Test case checking that <tt>\color</tt>
     * needs an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            "\\color ",
            // --- log message ---
            "Unexpected end of file while processing \\color");
    }

    /**
     * <testcase primitive="\color"> Test case checking that <tt>\color</tt>
     * complains about a missing brace. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMissing1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\color .1 .2 .3",
            // --- log message ---
            "Missing left brace for color value");
    }

    /**
     * <testcase primitive="\color"> Test case checking that <tt>\color</tt>
     * needs a left brace to start a RGB color triple. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMissing2() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\color rgb .1 .2 .3",
            // --- log message ---
            "Missing left brace for color value");
    }

    /**
     * <testcase primitive="\color"> Test case checking that <tt>\color</tt>
     * in default mode returns a RGB color and the components make it to the
     * output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDefault1() throws Exception {

        Interpreter interpreter = assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\color {.1 .2 .3}",
            // --- log message ---
            "");
        assertNotNull(interpreter);
        Color color =
                interpreter.getContext().getTypesettingContext().getColor();
        assertTrue(color instanceof RgbColor);
        RgbColor c = (RgbColor) color;
        assertEquals(TEN_PERCENT, c.getRed());
        assertEquals(TWENTY_PERCENT, c.getGreen());
        assertEquals(THIRTY_PERCENT, c.getBlue());
        assertEquals(0, c.getAlpha());
    }

    /**
     * <testcase primitive="\color"> Test case checking that <tt>\color</tt>
     * can digest an alpha channel value in default mode. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDefault2() throws Exception {

        Interpreter interpreter = assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\color alpha .5 {.1 .2 .3}",
            // --- log message ---
            "");
        assertNotNull(interpreter);
        Color color =
                interpreter.getContext().getTypesettingContext().getColor();
        assertTrue(color instanceof RgbColor);
        RgbColor c = (RgbColor) color;
        assertEquals(TEN_PERCENT, c.getRed());
        assertEquals(TWENTY_PERCENT, c.getGreen());
        assertEquals(THIRTY_PERCENT, c.getBlue());
        assertEquals(FIFTY_PERCENT, c.getAlpha());
    }

    /**
     * <testcase primitive="\color"> Test case checking that <tt>\color</tt>
     * can consume an explicit RGB color. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRgb1() throws Exception {

        Interpreter interpreter = assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\color rgb {.1 .2 .3}",
            // --- log message ---
            "");
        assertNotNull(interpreter);
        Color color =
                interpreter.getContext().getTypesettingContext().getColor();
        assertTrue(color instanceof RgbColor);
        RgbColor c = (RgbColor) color;
        assertEquals(TEN_PERCENT, c.getRed());
        assertEquals(TWENTY_PERCENT, c.getGreen());
        assertEquals(THIRTY_PERCENT, c.getBlue());
        assertEquals(0, c.getAlpha());
    }

    /**
     * <testcase primitive="\color"> Test case checking that <tt>\color</tt>
     * can consume an explicit RGB color with alpha channel. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRgb2() throws Exception {

        Interpreter interpreter = assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\color rgb alpha .5 {.1 .2 .3}",
            // --- log message ---
            "");
        assertNotNull(interpreter);
        Color color =
                interpreter.getContext().getTypesettingContext().getColor();
        assertTrue(color instanceof RgbColor);
        RgbColor c = (RgbColor) color;
        assertEquals(TEN_PERCENT, c.getRed());
        assertEquals(TWENTY_PERCENT, c.getGreen());
        assertEquals(THIRTY_PERCENT, c.getBlue());
        assertEquals(FIFTY_PERCENT, c.getAlpha());
    }

    /**
     * <testcase primitive="\color"> Test case checking that <tt>\color</tt>
     * can consume an alpha channel with an explicit RGB color. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRgb3() throws Exception {

        Interpreter interpreter = assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\color alpha .5 rgb {.1 .2 .3}",
            // --- log message ---
            "");
        assertNotNull(interpreter);
        Color color =
                interpreter.getContext().getTypesettingContext().getColor();
        assertTrue(color instanceof RgbColor);
        RgbColor c = (RgbColor) color;
        assertEquals(TEN_PERCENT, c.getRed());
        assertEquals(TWENTY_PERCENT, c.getGreen());
        assertEquals(THIRTY_PERCENT, c.getBlue());
        assertEquals(FIFTY_PERCENT, c.getAlpha());
    }

    /**
     * <testcase primitive="\color"> Test case checking that <tt>\color</tt>
     * can consume an explicit gray scale color. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGray1() throws Exception {

        Interpreter interpreter = assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\color gray {.1}",
            // --- log message ---
            "");
        assertNotNull(interpreter);
        Color color =
                interpreter.getContext().getTypesettingContext().getColor();
        assertTrue(color instanceof GrayscaleColor);
        GrayscaleColor c = (GrayscaleColor) color;
        assertEquals(TEN_PERCENT, c.getGray());
        assertEquals(0, c.getAlpha());
    }

    /**
     * <testcase primitive="\color"> Test case checking that <tt>\color</tt>
     * can consume an explicit gray scale color with an alpha channel.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGray2() throws Exception {

        Interpreter interpreter = assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\color gray alpha .5 {.1}",
            // --- log message ---
            "");
        assertNotNull(interpreter);
        Color color =
                interpreter.getContext().getTypesettingContext().getColor();
        assertTrue(color instanceof GrayscaleColor);
        GrayscaleColor c = (GrayscaleColor) color;
        assertEquals(TEN_PERCENT, c.getGray());
        assertEquals(FIFTY_PERCENT, c.getAlpha());
    }

    /**
     * <testcase primitive="\color"> Test case checking that <tt>\color</tt>
     * can consume an alpha value with an explicit gray scale color. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGray3() throws Exception {

        Interpreter interpreter = assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\color alpha .5 gray {.1}",
            // --- log message ---
            "");
        assertNotNull(interpreter);
        Color color =
                interpreter.getContext().getTypesettingContext().getColor();
        assertTrue(color instanceof GrayscaleColor);
        GrayscaleColor c = (GrayscaleColor) color;
        assertEquals(TEN_PERCENT, c.getGray());
        assertEquals(FIFTY_PERCENT, c.getAlpha());
    }

}
