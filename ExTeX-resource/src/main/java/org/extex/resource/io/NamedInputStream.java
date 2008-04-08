/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.resource.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NamedInputStream extends InputStream {

    /**
     * The field <tt>name</tt> contains the name.
     */
    private String name;

    /**
     * The field <tt>stream</tt> contains the stream.
     */
    private InputStream stream;

    /**
     * Creates a new object.
     * 
     * @param stream the stream
     * @param name the name
     */
    public NamedInputStream(InputStream stream, String name) {

        super();
        this.stream = stream;
        this.name = name;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.InputStream#available()
     */
    @Override
    public int available() throws IOException {

        return stream.available();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.InputStream#close()
     */
    @Override
    public void close() throws IOException {

        stream.close();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        return stream.equals(obj);
    }

    /**
     * Getter for name.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return stream.hashCode();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.InputStream#mark(int)
     */
    @Override
    public void mark(int readlimit) {

        stream.mark(readlimit);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.InputStream#markSupported()
     */
    @Override
    public boolean markSupported() {

        return stream.markSupported();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException {

        return stream.read();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.InputStream#read(byte[])
     */
    @Override
    public int read(byte[] b) throws IOException {

        return stream.read(b);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.InputStream#read(byte[], int, int)
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {

        return stream.read(b, off, len);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.InputStream#reset()
     */
    @Override
    public void reset() throws IOException {

        stream.reset();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.io.InputStream#skip(long)
     */
    @Override
    public long skip(long n) throws IOException {

        return stream.skip(n);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return stream.toString();
    }

}
