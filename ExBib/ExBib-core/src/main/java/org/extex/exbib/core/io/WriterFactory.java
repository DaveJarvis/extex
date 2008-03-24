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
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This class provides some static methods to get a new instance of some kind of
 * writer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class WriterFactory {

    /** ... */
    private Configuration config;

    /**
     * Creates a new object.
     * 
     * @param config the configuration
     */
    public WriterFactory(Configuration config) {

        super();
        this.config = config;
    }

    /**
     * Getter for a new writer without an argument.
     * 
     * @return a new NullWriter
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    public synchronized Writer newInstance() throws ConfigurationException {

        Writer writer = new NullWriter();
        writer.configure(config);
        return writer;
    }

    /**
     * Getter for a new writer from a PrintStream.
     * 
     * @param stream the PrintStream to write to
     * 
     * @return a new StreamWriter or a new NullWriter if the stream is null
     * 
     * @throws UnsupportedEncodingException in case of an unknown encoding
     * @throws ConfigurationException in case of an configuration error
     */
    public synchronized Writer newInstance(PrintStream stream)
            throws UnsupportedEncodingException,
                ConfigurationException {

        Writer writer =
                (stream == null
                        ? (Writer) new NullWriter()
                        : (Writer) new StreamWriter(stream, null));
        writer.configure(config);
        return writer;
    }

    /**
     * Getter for a new writer from a file.
     * 
     * @param file the name of the file to print to
     * 
     * @return a new StreamWriter or a new NullWriter if the file is null
     * 
     * @throws FileNotFoundException in case that the file cound not be opened
     * @throws UnsupportedEncodingException in case of an unknown encoding
     * @throws ConfigurationException in case of an configuration error
     */
    public synchronized Writer newInstance(String file)
            throws FileNotFoundException,
                UnsupportedEncodingException,
                ConfigurationException {

        if (file == null) {
            return new NullWriter();
        }

        Writer writer = new StreamWriter(file, null);
        writer.configure(config);
        return writer;
    }

    /**
     * Getter for a new writer with a StringBuffer
     * 
     * @param buffer the StringBuffer to fill
     * 
     * @return a new StringBufferWriter
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    public synchronized Writer newInstance(StringBuffer buffer)
            throws ConfigurationException {

        Writer writer = new StringBufferWriter(buffer);
        writer.configure(config);
        return writer;
    }

    /**
     * Getter for a new writer composed or two others.
     * 
     * @param a the first writer
     * @param b the second writer
     * 
     * @return a new multi writer if both writers are not null; one of the
     *         writers if the other one is null; a new NullWriter if both are
     *         null
     * 
     * @throws UnsupportedEncodingException in case of an unknown encoding
     * @throws ConfigurationException in case of an configuration error
     */
    public synchronized Writer newInstance(Writer a, Writer b)
            throws UnsupportedEncodingException,
                ConfigurationException {

        if (a == null) {
            return (b == null ? new NullWriter() : b);
        } else if (b == null) {
            return a;
        }

        Writer writer = new MultiWriter(a, b);
        writer.configure(config);
        return writer;
    }
}
