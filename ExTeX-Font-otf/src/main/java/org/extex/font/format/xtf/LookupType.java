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

package org.extex.font.format.xtf;

import java.io.IOException;

import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Abstract class for all lookup tables.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class LookupType implements XMLWriterConvertible {

    /**
     * Returns a instance of the lookup type.
     * 
     * @param rar The input.
     * @param type The lookup tpye.
     * @param offset The offset.
     * @return Returns a instance of the lookup type.
     * @throws IOException if a io-error occurred.
     */
    public static LookupType getInstance(RandomAccessR rar, int type, int offset)
            throws IOException {

        switch (type) {
            case 1:
                return LookupType1.getInstance(rar, type, offset);

            case 2:
                return LookupType2.getInstance(rar, type, offset);

            case 3:
                return null;
            case 4:
                return null;
            case 5:
                return null;
            case 6:
                return null;
            case 7:
                return null;
            case 8:
                return null;
            case 9:
                return null;
            case 10:
                return null;
            default:
                throw new IOException("wrong lookup type");
        }
    }

    /**
     * The offset of the table.
     */
    private int baseoffset;

    /**
     * The type.
     */
    private int type;

    /**
     * Creates a new object.
     * 
     * @param type The type.
     * @param offset The offset of the table.
     */
    protected LookupType(int type, int offset) {

        this.type = type;
        baseoffset = offset;
    }

    /**
     * Getter for baseoffset.
     * 
     * @return the baseoffset
     */
    public int getBaseoffset() {

        return baseoffset;
    }

    /**
     * Getter for type.
     * 
     * @return the type
     */
    public int getType() {

        return type;
    }

}
