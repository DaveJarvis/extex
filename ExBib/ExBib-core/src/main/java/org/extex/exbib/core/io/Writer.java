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

import org.extex.framework.configuration.Configurable;

/**
 * This is the generic interface for any class which implements some sort of
 * writing in bcd.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public interface Writer extends Configurable {

    /**
     * Explicit method th close a certain writer and all writers which can be
     * arrived via the "next" chain.
     * 
     * @throws IOException in case of an error during writing
     */
    public abstract void close() throws IOException;

    /**
     * Clears the output buffer and sends the remaining characters out.
     * 
     * @throws IOException in case of an error during writing
     */
    public abstract void flush() throws IOException;

    /**
     * Getter for an underlying print stream if one is present.
     * 
     * @return the PrintStream or <code>null</code> if none is present
     */
    public abstract PrintWriter getPrintWriter();

    /**
     * Print a single String to the destination of this writer. Additionally all
     * "next" writers are called to do the same.
     * 
     * @param s the string to write
     * 
     * @throws IOException in case of an error during writing
     */
    public abstract void print(String s) throws IOException;

    /**
     * Print a two Strings to the destination of this writer. Additionally all
     * "next" writers are called to do the same.
     * 
     * @param s1 the first String to write
     * @param s2 the second String to write
     * 
     * @throws IOException in case of an error during writing
     */
    public abstract void print(String s1, String s2) throws IOException;

    /**
     * Print a three Strings to the destination of this writer. Additionally all
     * "next" writers are called to do the same.
     * 
     * @param s1 the first String to write
     * @param s2 the second String to write
     * @param s3 the third String to write
     * 
     * @throws IOException in case of an error during writing
     */
    public abstract void print(String s1, String s2, String s3)
            throws IOException;

    /**
     * Print a newline to the destination of this writer.
     * 
     * @throws IOException in case of an error during writing
     */
    public abstract void println() throws IOException;

    /**
     * Print a single String followed by a newline to the destination of this
     * writer. Additionally all "next" writers are called to do the same.
     * 
     * @param s the string to write
     * 
     * @throws IOException in case of an I/O error
     */
    public abstract void println(String s) throws IOException;

    /**
     * Print a two Strings followed by a newline to the destination of this
     * writer. Additionally all "next" writers are called to do the same.
     * 
     * @param s1 the first String to write
     * @param s2 the second String to write
     * 
     * @throws IOException in case of an error during writing
     */
    public abstract void println(String s1, String s2) throws IOException;

    /**
     * Print a three Strings followed by a newline to the destination of this
     * writer. Additionally all "next" writers are called to do the same.
     * 
     * @param s1 the first String to write
     * @param s2 the second String to write
     * @param s3 the third String to write
     * 
     * @throws IOException in case of an error during writing
     */
    public abstract void println(String s1, String s2, String s3)
            throws IOException;

    /**
     * Write a single character to the destination of this writer. Additionally
     * all "next" writers are called to do the same.
     * 
     * @param c the Code of the character to write.
     * 
     * @throws IOException in case of an error during writing
     */
    public abstract void write(int c) throws IOException;
}
