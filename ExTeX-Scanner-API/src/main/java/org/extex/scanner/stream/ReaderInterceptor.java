/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.stream;

import java.io.Reader;

/**
 * This interface describes the ability to manipulate an
 * {@link java.io.Reader Reader} by attaching additional processing units in a
 * pipe manner.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5563 $
 */
public interface ReaderInterceptor {

    /**
     * Attach a processor to an input stream. If the interceptor decides that no
     * additional pipe element is required it should simply return the reader
     * passed in as argument.
     * 
     * @param reader the reader to add some processing unit to
     * 
     * @return the new reader. This value should never be <code>null</code>.
     */
    Reader pipe(Reader reader);

}
