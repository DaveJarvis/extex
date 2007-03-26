/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 */

package org.extex.util;

import java.text.NumberFormat;
import java.util.Locale;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.GlueComponent;

/**
 * This class implements a converter e.g for dimen values.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public final class Unit {

    /**
     * private: no instance
     */
    private Unit() {

    }

    /**
     * basis 10
     */
    private static final int BASIS10 = 10;

    /**
     * den
     */
    private static final int DEN = 7227;

    /**
     * mul for bp
     */
    private static final int MULBP = 7200;

    /**
     * mul for mm
     */
    private static final int MULMM = 2540;

    /**
     * mul for cm
     */
    private static final int MULCM = 254;

    /**
     * mul for in
     */
    private static final int MULIN = 100;

    /**
     * shift
     */
    private static final int SHIFT = 16;

    /**
     * Returns the <code>Dimen</code>-value as BP (big point)
     * @param value the <code>Dimen</code>
     * @return Returns the <code>Dimen</code>-value as BP
     */
    public static float getDimenAsBP(FixedDimen value) {

        return ((float) value.getValue() * MULBP) / (DEN << SHIFT);
    }

    /**
     * Return the <code>Dimen</code>-value as PT (point)
     * @param value the <code>Dimen</code>
     * @return Returns the <code>Dimen</code>-value as PT
     */
    public static double getDimenAsPT(FixedDimen value) {

        return ((double) value.getValue()) / GlueComponent.ONE;
    }

    /**
     * Return the <code>Dimen</code>-value (round) as PT (point) as  String.
     * @param value the <code>Dimen</code>
     * @return Returns the <code>Dimen</code>-value (round) as PT as String
     */
    public static String getDimenAsPTString(FixedDimen value) {

        return getDimenAsPTString(value, 2);
    }

    /**
     * Return the <code>Dimen</code>-value (round) as PT (point) as  String.
     * @param value the <code>Dimen</code>
     * @param round the round position
     * @return Returns the <code>Dimen</code>-value (round) as PT as String
     */
    public static String getDimenAsPTString(FixedDimen value,
            int round) {

        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setGroupingUsed(false);
        nf.setMinimumFractionDigits(round);
        return nf.format(getDimenAsPT(value));
    }

    /**
     * Returns the <code>Dimen</code>-value as MM (milimeter)
     * @param value the <code>Dimen</code>
     * @return Returns the <code>Dimen</code>-value as MM
     */
    public static double getDimenAsMM(FixedDimen value) {

        return ((double) value.getValue() * MULMM) / (DEN << SHIFT);
    }

    /**
     * Returns the <code>Dimen</code>-value as CM (centimeter)
     * @param value the <code>Dimen</code>
     * @return Returns the <code>Dimen</code>-value as CM
     */
    public static double getDimenAsCM(FixedDimen value) {

        return ((double) value.getValue() * MULCM) / (DEN << SHIFT);
    }

    /**
     * Returns the <code>Dimen</code>-value as IN (inch)
     * @param value the <code>Dimen</code>
     * @return Returns the <code>Dimen</code>-value as IN
     */
    public static double getDimenAsIN(FixedDimen value) {

        return ((double) value.getValue() * MULIN) / (DEN << SHIFT);
    }

    /**
     * Create a new <code>Dimen</code> from a CM-value
     * @param cm    the cm-value
     * @return Returns the new <code>Dimne</code> from cm-value.
     */
    public static Dimen createDimenFromCM(double cm) {

        return new Dimen((long) (cm * (DEN << SHIFT)) / MULCM);
    }

    /**
     * Set the <code>Dimen</code>-value from a BP-value
     * @param d     the dimen
     * @param bp    the bp-value
     */
    public static void setDimenFromCM(Dimen d, float bp) {

        d.setValue((long) ((bp * (DEN << SHIFT)) / MULBP));
    }

    /**
     * Round the double-value to a number of decimals.
     * @param value the double-value
     * @param round the number of decimals to round (not round: negative value)
     * @return Returns the rounded double-value
     */
    public static double round(double value, int round) {

        if (round < 0) {
            return value;
        }
        return Math.round(value * Math.pow(BASIS10, round))
                / Math.pow(BASIS10, round);
    }

}
