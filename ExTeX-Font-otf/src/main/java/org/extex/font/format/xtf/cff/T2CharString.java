/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.cff;

import java.io.IOException;

import org.extex.font.format.xtf.OtfTableCFF;
import org.extex.util.file.random.RandomAccessR;

/**
 * The Type 2 Charstring format.
 * 
 * @see <a
 *      href="http://partners.adobe.com/asn/developer/pdfs/tn/5177.Type2.pdf">
 *      Adobe Technical Note #5177: Type 2 Charstring Format</a>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public abstract class T2CharString {

    /**
     * escape
     */
    public static final T2Escape ESCAPE = new T2Escape();

    /**
     * escape-byte.
     */
    public static final byte ESCAPE_BYTE = 12;

    /**
     * hstem.
     */
    public static final int T2HSTEM = 1;

    /**
     * hstemhm.
     */
    public static final int T2HSTEMHM = 18;

    /**
     * rmoveto
     */
    public static final int T2RMOVETO = 21;

    /**
     * hmoveto
     */
    public static final int T2HMOVETO = 22;

    /**
     * vmoveto
     */
    public static final int T2VMOVETO = 4;

    /**
     * rlineto
     */
    public static final int T2RLINETO = 5;

    /**
     * hlineto
     */
    public static final int T2HLINETO = 6;

    /**
     * vlineto
     */
    public static final int T2VLINETO = 7;

    /**
     * vstem.
     */
    public static final int T2VSTEM = 3;

    /**
     * vstemhm.
     */
    public static final int T2VSTEMHM = 23;

    /**
     * rrcurveto.
     */
    public static final int T2RRCURVETO = 8;

    /**
     * hintmask.
     */
    public static final int T2HINTMASK = 19;

    /**
     * Type hstem.
     */
    public static final int TYPE_HSTEM = 1;

    /**
     * Type hstemhm.
     */
    public static final int TYPE_HSTEMHM = 3;

    /**
     * Type vstem.
     */
    public static final int TYPE_VSTEM = 2;

    /**
     * Type vstemhm.
     */
    public static final int TYPE_VSTEMHM = 4;

    /**
     * Type rmoveto.
     */
    public static final int TYPE_RMOVETO = 5;

    /**
     * Type hmoveto.
     */
    public static final int TYPE_HMOVETO = 6;

    /**
     * Type vmoveto.
     */
    public static final int TYPE_VMOVETO = 7;

    /**
     * Type rlineto.
     */
    public static final int TYPE_RLINETO = 8;

    /**
     * Type hlineto.
     */
    public static final int TYPE_HLINETO = 9;

    /**
     * Type vlineto.
     */
    public static final int TYPE_VLINETO = 10;

    /**
     * Type rrcurveto.
     */
    public static final int TYPE_RRCURVETO = 11;

    /**
     * Type hintmask.
     */
    public static final int TYPE_HINTMASK = 12;

    /**
     * Read a number.
     * 
     * @param rar the input
     * @return Returns the number.
     * @throws IOException if an IO-error occurs.
     */
    public static T2Number readNumber(RandomAccessR rar) throws IOException {

        return T2Number.newInstance(rar);
    }

    /**
     * Read a number.
     * 
     * @param rar the input
     * @param b0 the first byte
     * @return Returns the number.
     * @throws IOException if an IO-error occurs.
     */
    public static T2Number readNumber(RandomAccessR rar, int b0)
            throws IOException {

        return T2Number.newInstance(rar, b0);
    }

    /**
     * Read a SID.
     * 
     * <p>
     * SID (0-64999) 2-byte string identifier
     * </p>
     * 
     * @param rar the input
     * @return Returns the SID.
     * @throws IOException if an IO-error occurs.
     */
    public static T2SID readSID(RandomAccessR rar) throws IOException {

        return new T2SID(rar);
    }

    /**
     * Read a top DICT operator.
     * 
     * @param rar the input
     * @return Return the Top DICT operator
     * @throws IOException if an IO-error occurs
     */
    public static T2Operator readTopDICTOperator(RandomAccessR rar)
            throws IOException {

        return T2TopDICTOperator.newInstance(rar);
    }

    /**
     * Create a new object.
     */
    protected T2CharString() {

        super();
    }

    /**
     * Returns the byte-array as short for the object.
     * 
     * @return Returns the byte-array for the object.
     */
    public abstract short[] getBytes();

    /**
     * Returns the priority for the init process.
     * 
     * @return Returns the priority.
     */
    public int getInitPrio() {

        return 0;
    }

    /**
     * Initialize.
     * 
     * @param rar The input
     * @param cff The cff table
     * @param baseoffset The base offset from cff.
     * @param cffFont The cff font.
     * @throws IOException if an IO-error occurred.
     */
    public abstract void init(RandomAccessR rar, OtfTableCFF cff,
            int baseoffset, CffFont cffFont) throws IOException;

    /**
     * Check, if the objekt is a array.
     * 
     * @return Returns <code>true</code>, if the object is a array.
     */
    public boolean isArray() {

        return false;
    }

    /**
     * Check, if the objekt is a boolean.
     * 
     * @return Returns <code>true</code>, if the object is a boolean.
     */
    public boolean isBoolean() {

        return false;
    }

    /**
     * Check, if the objekt is a double.
     * 
     * @return Returns <code>true</code>, if the object is a double.
     */
    public boolean isDouble() {

        return false;
    }

    /**
     * Check, if the objekt is an escape-marker.
     * 
     * @return Returns <code>true</code>, if the object is an escape-marker.
     */
    public boolean isEscape() {

        return false;
    }

    // /**
    // * Read a operator.
    // *
    // * @param rar the input
    // * @return Returns the operator.
    // * @throws IOException if an IO-error occurs.
    // */
    // public static T2Operator readOperator(RandomAccessR rar) throws
    // IOException {
    //
    // return T2Operator.newInstance(rar);
    // }

    /**
     * Check, if the objekt is a integer.
     * 
     * @return Returns <code>true</code>, if the object is a integer.
     */
    public boolean isInteger() {

        return false;
    }

    /**
     * Check, if the objekt is an operator.
     * 
     * @return Returns <code>true</code>, if the object is an operator.
     */
    public boolean isOperator() {

        return false;
    }

    /**
     * Check, if the objekt is a Top DICT operator.
     * 
     * @return Returns <code>true</code>, if the object is a Top DICT
     *         operator.
     */
    public boolean isTopDICTOperator() {

        return false;
    }

}
