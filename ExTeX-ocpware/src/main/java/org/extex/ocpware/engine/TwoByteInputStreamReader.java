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

package org.extex.ocpware.engine;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TwoByteInputStreamReader extends Reader {

    /**
     * The field <tt>stream</tt> contains the stream to read bytes from.
     */
    private InputStream stream;

    /**
     * Creates a new object.
     * 
     * @param stream the stream to read from
     */
    public TwoByteInputStreamReader(InputStream stream) {

        super();
        this.stream = stream;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#close()
     */
    @Override
    public void close() throws IOException {

        stream.close();
        stream = null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#read(char[], int, int)
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {

        int n = 0;
        while (n < len) {
            int c = stream.read();
            int d = stream.read();
            if (d < 0) {
                return n;
            }
            cbuf[off + n] = (char) (c << 8 | d);
            n++;
        }
        return n;
    }

}