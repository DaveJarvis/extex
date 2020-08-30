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

package org.extex.io.charsets;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/**
 * This class provides a table-based {@link CharsetDecoder}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TableDecoder extends CharsetDecoder {

    /**
     * The field {@code table} contains the table.
     */
    private final char[] table;

    /**
     * Creates a new object.
     *
     * @param cs the character set
     * @param table the table
     */
    public TableDecoder(Charset cs, char[] table) {

        super(cs, 1.0f, 1.0f);
        this.table = table;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.nio.charset.CharsetDecoder#decodeLoop(java.nio.ByteBuffer,
     *      java.nio.CharBuffer)
     */
    @Override
    protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {

        for (int i = 0; i < in.limit(); i++) {
            byte c = in.get();
            out.put(table[c]);
        }

        return null;
    }

}
