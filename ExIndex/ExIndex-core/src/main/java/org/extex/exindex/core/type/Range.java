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

import org.extex.exindex.core.command.LMarkupRange;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.type.value.LBoolean;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This class represents a range of location references.
 * 
 * @see LMarkupRange
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Range implements Location {

    /**
     * The field <tt>from</tt> contains the starting location.
     */
    private LocationReference from;

    /**
     * The field <tt>to</tt> contains the terminating location.
     */
    private LocationReference to;

    /**
     * The field <tt>clazz</tt> contains the the current class.
     */
    private String clazz;

    /**
     * Creates a new object.
     * 
     * @param from the starting location
     * @param to the terminating location
     * @param clazz the current class
     */
    public Range(LocationReference from, LocationReference to, String clazz) {

        super();
        this.from = from;
        this.to = to;
        this.clazz = clazz;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.Location#write(java.io.Writer,
     *      org.extex.exindex.lisp.LInterpreter)
     */
    public void write(Writer writer, LInterpreter interpreter)
            throws IOException {

        interpreter.writeString(writer, "markup:range-" + clazz + "-open");
        from.write(writer, interpreter);
        interpreter.writeString(writer, "markup:range-" + clazz + "-sep");
        LValue value = interpreter.get("markup:range-" + clazz + "-ignore-end");
        if (value != LBoolean.TRUE) {
            to.write(writer, interpreter);
        }
        interpreter.writeString(writer, "markup:range-" + clazz + "-close");
    }

}
