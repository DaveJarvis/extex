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
 * Escape.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class T2Escape extends T2CharString {

    /**
     * Create a new object.
     */
    T2Escape() {

        super();
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2CharString#isEscape()
     */
    public boolean isEscape() {

        return true;
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2CharString#getBytes()
     */
    public short[] getBytes() {

        return new short[]{ESCAPE_BYTE};
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2CharString#init(
     *      org.extex.util.file.random.RandomAccessR,
     *      org.extex.font.format.xtf.OtfTableCFF, int)
     */
    public void init(final RandomAccessR rar, final OtfTableCFF cff,
            final int baseoffset) throws IOException {

    }
}