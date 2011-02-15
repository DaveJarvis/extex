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

package org.extex.font.format.xtf.tables.cff;

import java.io.IOException;
import java.util.List;

/**
 * Subroutine.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:5595 $
 */

public abstract class T2Subroutine extends T2Operator {

    /**
     * bytes
     */
    private short[] bytes;

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @param id the operator-id for the value
     * @param ch The char string.
     * 
     * @throws IOException in case of an error
     */
    protected T2Subroutine(List<T2CharString> stack, short[] id, CharString ch)
            throws IOException {

        bytes = convertStackaddID(stack, id);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2CharString#getBytes()
     */
    @Override
    public short[] getBytes() {

        return bytes;
    }
}
