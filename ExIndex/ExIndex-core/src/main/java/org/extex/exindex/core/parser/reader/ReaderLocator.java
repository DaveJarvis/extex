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

package org.extex.exindex.core.parser.reader;

import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;

import org.extex.exindex.lisp.parser.ResourceLocator;

/**
 * This resource locator encapsulates a line number reader and can be used to
 * read single characters from the reader.
 * 
 */
public class ReaderLocator extends LineNumberReader implements ResourceLocator {

    /**
     * The field <tt>resource</tt> contains the name of the resource.
     */
    private String resource;

    /**
     * Creates a new object.
     * 
     * @param resource the resource
     * @param lineNumber the line number
     */
    public ReaderLocator(String resource, int lineNumber) {

        super(new StringReader(""));
        this.resource = resource;
        setLineNumber(lineNumber);
    }

    /**
     * Creates a new object.
     * 
     * @param resource the resource
     * @param reader the reader
     */
    public ReaderLocator(String resource, Reader reader) {

        super(reader);
        this.resource = resource;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.parser.ResourceLocator#getResource()
     */
    public String getResource() {

        return resource;
    }

}
