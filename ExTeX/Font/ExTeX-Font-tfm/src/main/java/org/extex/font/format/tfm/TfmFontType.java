/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.tfm;

import java.io.Serializable;

/**
 * BaseFont type.
 * 
 * <p>
 * VANILLA, MATHSY, MATHEX
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class TfmFontType implements Serializable {

    /**
     * Type (type-safe class).
     */
    private static final class Type implements Serializable {

        /**
         * The field <tt>serialVersionUID</tt> ...
         */
        private static final long serialVersionUID = 1L;

    
        public Type() {

        }
    }

    /**
     * TeX Math Extension font metric.
     */
    public static final Type MATHEX = new Type();

    /**
     * TeX Math Symbols font metric.
     */
    public static final Type MATHSY = new Type();

    /**
     * Other font metric.
     */
    public static final Type OTHER = new Type();

    /**
     * The field <tt>serialVersionUID</tt>.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Typewriter font metric.
     */
    public static final Type TYPEWRITER = new Type();

    /**
     * Normal TeX font metric.
     */
    public static final Type VANILLA = new Type();

    /**
     * the type.
     */
    private Type type;

    /**
     * Create a new object.
     * 
     * @param codingscheme the coding scheme
     */
    public TfmFontType(String codingscheme) {

        if (codingscheme == null) {
            type = OTHER;
        } else if (codingscheme.startsWith("TEX MATH SY")) {
            type = MATHSY;
        } else if (codingscheme.startsWith("TEX MATH EX")) {
            type = MATHEX;
        } else if (codingscheme.startsWith("TEX TEXT")) {
            type = VANILLA;
        } else if (codingscheme.startsWith("TEX TYPEWRITER TEXT")) {
            type = TYPEWRITER;
        } else {
            type = OTHER;
        }
    }

    /**
     * Returns the type.
     * 
     * @return Returns the type.
     */
    public Type getType() {

        return type;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        if (type == MATHEX) {
            return "MATHEX";
        } else if (type == MATHSY) {
            return "MATHSY";
        } else if (type == VANILLA) {
            return "VANILLA";
        }
        return "OTHER";

    }

    /**
     * Returns the font type name for font metric file.
     * 
     * @return Returns the font type name for font metric file.
     */
    public String toTFMString() {

        if (type == MATHEX) {
            return "tfm-mathext";
        } else if (type == MATHSY) {
            return "tfm-mathsyml";
        } else if (type == VANILLA) {
            return "tfm-normal";
        }
        return "tfm -other";
    }

}
