/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.io;

import java.io.IOException;

/**
 * This {@link Writer Writer} stores its contents in a {@link StringBuffer}.
 * This can be used to write messages into memory.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StringBufferWriter implements Writer {

    /**
     * The field <tt>buffer</tt> contains the target.
     */
    private StringBuffer buffer = null;


    public StringBufferWriter() {

        this(new StringBuffer());
    }

    /**
     * Creates a new object.
     * 
     * @param buffer the target StringBuffer
     */
    public StringBufferWriter(StringBuffer buffer) {

        this.buffer = buffer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#close()
     */
    public void close() {

        
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#flush()
     */
    public void flush() {

        
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String[])
     */
    public void print(String... args) {

        for (String s : args) {
            buffer.append(s);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String[])
     */
    public void println(String... args) {

        for (String s : args) {
            buffer.append(s);
        }
        buffer.append("\n");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#write(int)
     */
    public void write(int c) throws IOException {

        buffer.append(c);
    }

}
