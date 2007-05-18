/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.backend.outputStream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class provides a delegate to an output stream with an additional name.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NamedOutputStream extends OutputStream {

    /**
     * The field <tt>name</tt> contains the name.
     */
    private String name;

    /**
     * The field <tt>stream</tt> contains the encapsulated stream.
     */
    private OutputStream stream;

    /**
     * Creates a new object.
     *
     * @param name the name
     * @param stream the stream
     */
    public NamedOutputStream(String name, OutputStream stream) {

        super();
        this.name = name;
        this.stream = stream;
    }

    /**
     * Closes this output stream and releases any system resources
     * associated with this stream. The general contract of <code>close</code>
     * is that it closes the output stream. A closed stream cannot perform
     * output operations and cannot be reopened.
     *
     * @exception  IOException  if an I/O error occurs.
     *
     * @see java.io.OutputStream#close()
     */
    @Override
    public void close() throws IOException {

        this.stream.close();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param   obj   the reference object with which to compare.
     *
     * @return  <code>true</code> if this object is the same as the obj
     *          argument; <code>false</code> otherwise.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        return this.stream.equals(obj);
    }

    /**
     * Flushes this output stream and forces any buffered output bytes
     * to be written out. The general contract of <code>flush</code> is
     * that calling it is an indication that, if any bytes previously
     * written have been buffered by the implementation of the output
     * stream, such bytes should immediately be written to their
     * intended destination.
     *
     * @exception  IOException  if an I/O error occurs.
     *
     * @see java.io.OutputStream#flush()
     */
    @Override
    public void flush() throws IOException {

        this.stream.flush();
    }

    /**
     * Getter for the destination.
     * The destination is some printable representation describing where the
     * output went to.
     *
     * @return the name of the destination
     */
    public String getName() {

        return name;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return  a hash code value for this object
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return this.stream.hashCode();
    }

    /**
     * Returns a string representation of the object.
     *
     * @return  a string representation of the object
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return this.stream.toString();
    }

    /**
     * Writes <code>b.length</code> bytes from the specified byte array
     * to this output stream. The general contract for <code>write(b)</code>
     * is that it should have exactly the same effect as the call
     * <code>write(b, 0, b.length)</code>.
     *
     * @param      b   the data.
     * @exception  IOException  if an I/O error occurs.
     *
     * @see java.io.OutputStream#write(byte[])
     */
    @Override
    public void write(byte[] b) throws IOException {

        this.stream.write(b);
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array
     * starting at offset <code>off</code> to this output stream.
     *
     * @param      b     the data.
     * @param      off   the start offset in the data.
     * @param      len   the number of bytes to write.
     * @exception  IOException  if an I/O error occurs. In particular,
     *             an <code>IOException</code> is thrown if the output
     *             stream is closed.
     *
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    @Override
    public void write(byte[] b, int off, int len)
            throws IOException {

        this.stream.write(b, off, len);
    }

    /**
     * Writes the specified byte to this output stream.
     *
     * @param      b   the <code>byte</code>.
     * @exception  IOException  if an I/O error occurs. In particular,
     *             an <code>IOException</code> may be thrown if the
     *             output stream has been closed.
     *
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(int b) throws IOException {

        this.stream.write(b);
    }

}
