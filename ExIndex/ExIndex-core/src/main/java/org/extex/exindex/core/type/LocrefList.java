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

package org.extex.exindex.core.type;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;

/**
 * This class represents a location reference group.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LocrefList implements LocationClassGroup {

    /**
     * The field <tt>clazz</tt> contains the class.
     */
    private String clazz;

    /**
     * The field <tt>list</tt> contains the list of location references.
     */
    private List<LocationReference> list = new ArrayList<LocationReference>();

    /**
     * Creates a new object.
     * 
     * @param clazz the class
     */
    public LocrefList(String clazz) {

        super();
        this.clazz = clazz;
    }

    /**
     * Store a location reference.
     * 
     * @param ref the reference
     */
    public void store(LocationReference ref) {

        list.add(ref);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.LocationClassGroup#write(java.io.Writer,
     *      org.extex.exindex.lisp.LInterpreter, MarkupContainer, boolean)
     */
    public void write(Writer writer, LInterpreter interpreter,
            MarkupContainer markupContainer, boolean trace)
            throws LNonMatchingTypeException,
                IOException {

        Markup markup = markupContainer.getMarkup("markup-locref-list");

        boolean first = true;
        markup.write(writer, markupContainer, clazz, Markup.OPEN, trace);

        for (LocationReference reference : list) {
            if (first) {
                first = false;
            } else {
                markup.write(writer, markupContainer, clazz, Markup.SEP, trace);
            }

            reference.write(writer, interpreter, markupContainer, trace);
        }
        markup.write(writer, markupContainer, clazz, Markup.CLOSE, trace);
    }

}
