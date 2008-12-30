/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * This class provides a writer with a target in a {@link PrintStream} or a
 * {@link File}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class StreamWriter implements Writer {

    /**
     * The field <tt>writer</tt> contains the output writer.
     */
    private java.io.Writer writer = null;

    /**
     * Creates a new object.
     * 
     * @param stream the output stream
     * @param encoding the encoding to use for writing
     * 
     * @throws UnsupportedEncodingException in case that the given encoding is
     *         undefined
     */
    public StreamWriter(OutputStream stream, String encoding)
            throws UnsupportedEncodingException {

        super();

        if (encoding == null) {
            writer = new OutputStreamWriter(stream);
        } else {
            writer = new OutputStreamWriter(stream, encoding);
        }
    }

    /**
     * Creates a new object.
     * 
     * @param file the name of the file to write to
     * @param encoding the encoding to use for writing
     * 
     * @throws IOException in case of an I/O error
     */
    public StreamWriter(String file, String encoding) throws IOException {

        super();

        if (encoding == null) {
            writer = new FileWriter(file);
        } else {
            FileOutputStream stream = new FileOutputStream(file);
            writer = new OutputStreamWriter(stream, encoding);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#close()
     */
    public void close() throws IOException {

        writer.close();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#flush()
     */
    public void flush() throws IOException {

        writer.flush();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String[])
     */
    public void print(String... args) throws IOException {

        for (String s : args) {
            writer.write(s);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String[])
     */
    public void println(String... args) throws IOException {

        for (String s : args) {
            writer.write(s);
        }
        writer.write('\n');
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#write(int)
     */
    public void write(int c) throws IOException {

        writer.write(c);
    }
}
