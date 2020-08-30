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
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * This class provides a table-based {@link CharsetEncoder}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TableEncoder extends CharsetEncoder {

    /**
     * The field {@code table} contains the table.
     */
    private final char[] table;

    /**
     * The field {@code reverse} contains the reverse table.
     */
    private final byte[] reverse = new byte[256];

    /**
     * Creates a new object.
     * 
     * @param cs the char set
     * @param table the table of values
     */
    public TableEncoder(Charset cs, char[] table) {

        super(cs, 1.0f, 1.0f);
        this.table = table;
        int i = 0;
        for (char c : table) {
            if (c < reverse.length) {
                reverse[c] = (byte) i;
            }
            i++;
        }
    }

    /**
*      java.nio.ByteBuffer)
     */
    @Override
    protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {

        for (int i = 0; i < in.length(); i++) {
            char c = in.get();
            if (c < reverse.length) {
                out.put(reverse[c]);
            } else {
                int j = 0;
                for (char cc : table) {
                    if (table[cc] == c) {
                        out.put(reverse[c]);
                        break;
                    }
                    j++;
                }
                // TODO gene: c may not be defined at all
            }
        }

        return null;
    }

}
