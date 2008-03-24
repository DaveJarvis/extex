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

import java.io.IOException;
import java.io.PrintWriter;

import org.extex.framework.configuration.Configuration;

/**
 * This {@link Writer Writer} stores its contents in a StringBuffer. This can be
 * used to write messages into memory.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class StringBufferWriter implements Writer {

    /** the target StringBuffer */
    private StringBuffer buffer = null;

    /**
     * Creates a new object.
     */
    public StringBufferWriter() {

        super();
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

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration cfg) {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#flush()
     */
    public void flush() {

        //
    }

    /**
     * @see org.extex.exbib.core.io.Writer#getPrintWriter()
     */
    public PrintWriter getPrintWriter() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String)
     */
    public void print(String s) {

        buffer.append(s);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String,
     *      java.lang.String)
     */
    public void print(String s1, String s2) {

        buffer.append(s1);
        buffer.append(s2);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#print(java.lang.String,
     *      java.lang.String)
     */
    public void print(String s1, String s2, String s3) {

        buffer.append(s1);
        buffer.append(s2);
        buffer.append(s3);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String)
     */
    public void println() {

        buffer.append("\n");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String)
     */
    public void println(String s) {

        buffer.append(s);
        buffer.append("\n");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String,
     *      java.lang.String)
     */
    public void println(String s1, String s2) {

        buffer.append(s1);
        buffer.append(s2);
        buffer.append("\n");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.io.Writer#println(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void println(String s1, String s2, String s3) {

        buffer.append(s1);
        buffer.append(s2);
        buffer.append(s3);
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
