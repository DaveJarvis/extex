/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io;

import java.io.IOException;

import org.extex.framework.configuration.Configurable;

/**
 * This is the generic interface for any class which implements some sort of
 * writing in ExBib.
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
    void close() throws IOException;

    /**
     * Clears the output buffer and sends the remaining characters out.
     * 
     * @throws IOException in case of an error during writing
     */
    void flush() throws IOException;

    /**
     * Print some Strings to the destination of this writer. Additionally all
     * "next" writers are called to do the same.
     * 
     * @param s the string to write
     * 
     * @throws IOException in case of an error during writing
     */
    void print(String... s) throws IOException;

    /**
     * Print some Strings followed by a newline to the destination of this
     * writer. Additionally all "next" writers are called to do the same.
     * 
     * @param s the string to write
     * 
     * @throws IOException in case of an I/O error
     */
    void println(String... s) throws IOException;

    /**
     * Write a single character to the destination of this writer. Additionally
     * all "next" writers are called to do the same.
     * 
     * @param c the Code of the character to write.
     * 
     * @throws IOException in case of an error during writing
     */
    void write(int c) throws IOException;

}
