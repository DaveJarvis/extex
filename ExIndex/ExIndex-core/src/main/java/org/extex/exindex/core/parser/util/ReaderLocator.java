/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.parser.util;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.extex.exindex.lisp.parser.ResourceLocator;

/**
 * This resource locator encapsulates a line number reader and can be used to
 * read single characters from the reader.
 * 
 */
public class ReaderLocator implements ResourceLocator {

    /**
     * The field <tt>resource</tt> contains the name of the resource.
     */
    private String resource;

    /**
     * The field <tt>reader</tt> contains the reader.
     */
    private LineNumberReader reader;

    /**
     * The field <tt>lineNumber</tt> contains the optional line number.
     */
    private String lineNumber = null;

    /**
     * Creates a new object.
     * 
     * @param resource the resource
     * @param reader the reader
     */
    public ReaderLocator(String resource, Reader reader) {

        super();
        this.resource = resource;
        this.reader = new LineNumberReader(reader);
    }

    /**
     * Creates a new object.
     * 
     * @param resource the resource
     * @param lineNumber the line number
     */
    public ReaderLocator(String resource, String lineNumber) {

        super();
        this.resource = resource;
        this.reader = null;
        this.lineNumber = lineNumber;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.parser.ResourceLocator#getLineNumber()
     */
    public String getLineNumber() {

        if (lineNumber != null) {
            return lineNumber;
        } else if (reader != null) {
            return Integer.toString(reader.getLineNumber());
        }
        return "?";
    }

    /**
     * Getter for reader.
     * 
     * @return the reader
     */
    public LineNumberReader getReader() {

        return reader;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.parser.ResourceLocator#getResource()
     */
    public String getResource() {

        return resource;
    }

    /**
     * Read a character from the stream.
     * 
     * @return the next character or -1 at eof
     * 
     * @throws IOException in case of an error
     */
    public int read() throws IOException {

        return reader.read();
    }

}
