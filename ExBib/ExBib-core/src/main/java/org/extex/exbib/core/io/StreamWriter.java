/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This class provides a writer with a target in an outputStream.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class StreamWriter implements Writer, Configurable {

    /** the internal output stream */
    private OutputStreamWriter theStream = null;

    /**
     * Creates a new object.
     * 
     * @param stream the output stream
     * @param encoding the encoding to use for writing
     * 
     * @throws UnsupportedEncodingException in case that the given encoding is
     *         undefined
     */
    public StreamWriter(PrintStream stream, String encoding)
            throws UnsupportedEncodingException {

        super();

        if (encoding == null) {
            theStream = new OutputStreamWriter(stream);
        } else {
            theStream = new OutputStreamWriter(stream, encoding);
        }
    }

    /**
     * Creates a new object.
     * 
     * @param file the name of the file to write to
     * @param encoding the encoding to use for writing
     * 
     * @throws FileNotFoundException in case that the file could not be opened
     *         for writing
     * @throws UnsupportedEncodingException in case that the given encoding is
     *         undefined
     */
    public StreamWriter(String file, String encoding)
            throws FileNotFoundException,
                UnsupportedEncodingException {

        super();

        FileOutputStream stream = new FileOutputStream(file);

        if (encoding == null) {
            theStream = new OutputStreamWriter(stream);
        } else {
            theStream = new OutputStreamWriter(stream, encoding);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#close()
     */
    public void close() throws IOException {

        theStream.close();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration cfg) throws ConfigurationException {

        //
    }

    /**
     * @see org.extex.exbib.core.io.Writer#flush()
     */
    public void flush() throws IOException {

        theStream.flush();
    }

    /**
     * Getter for the print stream.
     * 
     * @return the print stream
     */
    public PrintWriter getPrintWriter() {

        return new PrintWriter(theStream, true);
    }

    /**
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String)
     */
    public void print(String s) throws IOException {

        theStream.write(s);
    }

    /**
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String,
     *      java.lang.String)
     */
    public void print(String s1, String s2) throws IOException {

        theStream.write(s1);
        theStream.write(s2);
    }

    /**
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void print(String s1, String s2, String s3) throws IOException {

        theStream.write(s1);
        theStream.write(s2);
        theStream.write(s3);
    }

    /**
     * @see org.extex.exbib.core.io.Writer#println()
     */
    public void println() throws IOException {

        theStream.write("\n");
    }

    /**
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String)
     */
    public void println(String s) throws IOException {

        theStream.write(s);
        theStream.write("\n");
    }

    /**
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String,
     *      java.lang.String)
     */
    public void println(String s1, String s2) throws IOException {

        theStream.write(s1);
        theStream.write(s2);
        theStream.write("\n");
    }

    /**
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void println(String s1, String s2, String s3) throws IOException {

        theStream.write(s1);
        theStream.write(s2);
        theStream.write(s3);
        theStream.write("\n");
    }

    /**
     * @see org.extex.exbib.core.io.Writer#write(int)
     */
    public void write(int c) throws IOException {

        theStream.write(c);
    }
}
