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

package org.extex.typesetter.type.noad.util;

import org.extex.scanner.type.Namespace;
import org.extex.typesetter.TypesetterOptions;

/**
 * This class provides symbolic constants for the font parameters used in math
 * mode.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class MathFontParameter {

    /**
     * Construct the reference key for a numbered font.
     *
     * @param context the interpreter context
     * @param theName the base name of the font
     * @param theNumber the number of the font
     *
     * @return the key
     */
    public static String key(TypesetterOptions context,
            String theName, String theNumber) {

        if (Namespace.SUPPORT_NAMESPACE_FONT) {
            return context.getNamespace() + "\b" + theName + "#" + theNumber;
        }
        return theName + "#" + theNumber;
    }

    /**
     * The constant {@code MATH_X_HEIGHT} contains the height of `x'.
     */
    public static final MathFontParameter MATH_X_HEIGHT =
            new MathFontParameter(true, "5");

    /**
     * The constant {@code MATH_QUAD} contains 18mu.
     */
    public static final MathFontParameter MATH_QUAD =
            new MathFontParameter(true, "6");

    /**
     * The constant {@code NUM1} contains the numerator shift-up in display
     * styles.
     */
    public static final MathFontParameter NUM1 =
            new MathFontParameter(true, "8");

    /**
     * The constant {@code NUM2} contains the numerator shift-up in
     * non-display, non-\atop.
     */
    public static final MathFontParameter NUM2 =
            new MathFontParameter(true, "9");

    /**
     * The constant {@code NUM3} contains the numerator shift-up in
     * non-display \atop.
     */
    public static final MathFontParameter NUM3 =
            new MathFontParameter(true, "10");

    /**
     * The constant {@code DENOM1} contains the denominator shift-down in
     * display styles.
     */
    public static final MathFontParameter DENOM1 =
            new MathFontParameter(true, "11");

    /**
     * The constant {@code DENOM2} contains the denominator shift-down in
     * non-display styles.
     */
    public static final MathFontParameter DENOM2 =
            new MathFontParameter(true, "12");

    /**
     * The constant {@code SUP1} contains the superscript shift-up in
     * uncramped display style.
     */
    public static final MathFontParameter SUP1 =
            new MathFontParameter(true, "13");

    /**
     * The constant {@code SUP2} contains the superscript shift-up in
     * uncramped non-display.
     */
    public static final MathFontParameter SUP2 =
            new MathFontParameter(true, "14");

    /**
     * The constant {@code SUP3} contains the superscript shift-up in cramped
     * styles.
     */
    public static final MathFontParameter SUP3 =
            new MathFontParameter(true, "15");

    /**
     * The constant {@code SUB1} contains the subscript shift-down if
     * superscript is absent.
     */
    public static final MathFontParameter SUB1 =
            new MathFontParameter(true, "16");

    /**
     * The constant {@code SUB2} contains the subscript shift-down if
     * superscript is present.
     */
    public static final MathFontParameter SUB2 =
            new MathFontParameter(true, "17");

    /**
     * The constant {@code SUP_DROP} contains the superscript baseline below
     * top of large box.
     */
    public static final MathFontParameter SUP_DROP =
            new MathFontParameter(true, "18");

    /**
     * The constant {@code SUB_DROP} contains the subscript baseline below
     * bottom of large box.
     */
    public static final MathFontParameter SUB_DROP =
            new MathFontParameter(true, "19");

    /**
     * The constant {@code DELIM1} contains the size of
     * {@code \atopwithdelims} delimiters in display styles.
     */
    public static final MathFontParameter DELIM1 =
            new MathFontParameter(true, "20");

    /**
     * The constant {@code DELIM2} contains the size of
     * {@code \atopwithdelims} delimiters in non-displays.
     */
    public static final MathFontParameter DELIM2 =
            new MathFontParameter(true, "21");

    /**
     * The constant {@code AXIS_HEIGHT} contains the height of fraction lines
     * above the baseline.
     */
    public static final MathFontParameter AXIS_HEIGHT =
            new MathFontParameter(true, "22");

    /**
     * The constant {@code DEFAULT_RULE_THICKNESS} contains the thickness of
     * {@code \over} bars.
     */
    public static final MathFontParameter DEFAULT_RULE_THICKNESS =
            new MathFontParameter(false, "8");

    /**
     * The constant {@code BIG_OP_SPACING1} contains the minimum clearance
     * above a displayed op.
     */
    public static final MathFontParameter BIG_OP_SPACING1 =
            new MathFontParameter(false, "9");

    /**
     * The constant {@code BIG_OP_SPACING2} contains the minimum clearance
     * below a displayed op.
     */
    public static final MathFontParameter BIG_OP_SPACING2 =
            new MathFontParameter(false, "10");

    /**
     * The constant {@code BIG_OP_SPACING3} contains the minimum baselineskip
     * above displayed op.
     */
    public static final MathFontParameter BIG_OP_SPACING3 =
            new MathFontParameter(false, "11");

    /**
     * The constant {@code BIG_OP_SPACING4} contains the minimum baselineskip
     * below displayed op.
     */
    public static final MathFontParameter BIG_OP_SPACING4 =
            new MathFontParameter(false, "12");

    /**
     * The constant {@code BIG_OP_SPACING5} contains the padding above and
     * below displayed limits.
     */
    public static final MathFontParameter BIG_OP_SPACING5 =
            new MathFontParameter(false, "13");

    /**
     * The field {@code inSymbol} contains the indicator that the parameter
     * should be taken from the symbol font. Otherwise it is taken from the
     * extension font.
     */
    private final boolean inSymbol;

    /**
     * The field {@code no} contains the number of the font parameter as
     * string.
     */
    private final String no;

    /**
     * Creates a new object.
     *
     * @param inSymbol the indicator that the parameter should be taken from the
     *            symbol font. Otherwise it is taken from the extension font.
     * @param no the number of the font parameter as string.
     */
    private MathFontParameter(boolean inSymbol, String no) {

        this.inSymbol = inSymbol;
        this.no = no;
    }

    /**
     * Getter for inSymbol.
     *
     * @return the inSymbol indicator
     */
    public boolean inSymbol() {

        return this.inSymbol;
    }

    /**
     * Getter for no.
     *
     * @return the no
     */
    public String getNo() {

        return this.no;
    }

    /**
     * Get the string representation of this object for debugging purposes.
     *
     * @return the string representation
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return (inSymbol ? "S" : "E") + this.no;
    }

}
