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

package org.extex.exindex.core.type;

import java.io.IOException;
import java.io.Writer;

import org.extex.exindex.lisp.LInterpreter;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Range implements Location {

    /**
     * The field <tt>location1</tt> contains the starting location.
     */
    private LocationReference location1;

    /**
     * The field <tt>location2</tt> contains the terminating location.
     */
    private LocationReference location2;

    /**
     * Creates a new object.
     */
    public Range() {

        super();
    }

    /**
     * Getter for location1.
     * 
     * @return the location1
     */
    public LocationReference getLocation1() {

        return location1;
    }

    /**
     * Getter for location2.
     * 
     * @return the location2
     */
    public LocationReference getLocation2() {

        return location2;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.Location#write(java.io.Writer,
     *      org.extex.exindex.lisp.LInterpreter)
     */
    public void write(Writer writer, LInterpreter interpreter)
            throws IOException {

        // TODO
        location1.write(writer, interpreter);
        // TODO
        location2.write(writer, interpreter);
        // TODO
    }

}
