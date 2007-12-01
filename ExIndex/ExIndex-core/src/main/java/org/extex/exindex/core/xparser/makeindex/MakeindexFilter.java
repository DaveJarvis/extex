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

package org.extex.exindex.core.xparser.makeindex;

import java.io.IOException;
import java.io.Reader;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MakeindexFilter extends Reader {

    /**
     * The field <tt>in</tt> contains the ...
     */
    private Reader in;

    /**
     * The field <tt>buffer</tt> contains the ...
     */
    private StringBuilder buffer = new StringBuilder();

    /**
     * Creates a new object.
     * 
     * @param in the input reader
     */
    public MakeindexFilter(Reader in) {

        super();
        this.in = in;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#close()
     */
    @Override
    public void close() throws IOException {

        in.close();
        in = null;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    private void fillBuffer() {

        // TODO gene: fillBuffer unimplemented

    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.Reader#read(char[], int, int)
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {

        if (buffer.length() <= 0) {
            if (in == null) {
                return -1;
            }
            fillBuffer();
        }

        int length = buffer.length();
        int bp = 0;
        int i;
        for (i = 0; i < len && bp < length; i++) {
            cbuf[off + i] = buffer.charAt(bp++);
        }

        buffer.delete(0, i);
        return i;
    }

}
