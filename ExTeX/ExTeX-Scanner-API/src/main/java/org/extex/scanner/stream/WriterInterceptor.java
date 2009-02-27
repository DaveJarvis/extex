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

import java.io.Writer;

/**
 * This interface describes the ability to manipulate an
 * {@link java.io.Writer Writer} by attaching additional processing units in a
 * pipe manner.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5563 $
 */
public interface WriterInterceptor {

    /**
     * Attach a processor to a writer. If the interceptor decides that no
     * additional pipe element is required it should simply return the writer
     * passed in as argument.
     * 
     * @param writer the writer to add some processing unit to
     * 
     * @return the new writer. This value should never be <code>null</code>.
     */
    Writer pipe(Writer writer);

}
