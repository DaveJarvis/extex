/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.ocpware.engine;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Reader for single bytes.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class OneByteInputStreamReader extends Reader {

    /**
     * The field {@code stream} contains the stream to read bytes from.
     */
    private InputStream stream;

    /**
     * Creates a new object.
     * 
     * @param stream the stream to read from
     */
    public OneByteInputStreamReader(InputStream stream) {

        this.stream = stream;
    }

@Override
    public void close() throws IOException {

        stream.close();
        stream = null;
    }

@Override
    public int read(char[] cbuf, int off, int len) throws IOException {

        int n = 0;
        while (n < len) {
            int c = stream.read();
            if (c < 0) {
                return n;
            }
            cbuf[off + n] = (char) c;
            n++;
        }
        return n;
    }

}
