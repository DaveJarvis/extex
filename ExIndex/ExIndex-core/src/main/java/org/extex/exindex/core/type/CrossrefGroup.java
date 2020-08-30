/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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
import java.util.HashMap;
import java.util.Map;

import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.core.type.raw.CrossReference;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;

/**
 * This class represents a cross-reference group.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class CrossrefGroup implements LocationClassGroup {

    /**
     * The field {@code map} contains the mapping of layers to a
     * cross-reference.
     */
    private final Map<String[], CrossReference> map =
            new HashMap<String[], CrossReference>();

    /**
     * The field {@code clazz} contains the class.
     */
    private final String clazz;

    /**
     * Creates a new object.
     * 
     * @param clazz the class
     */
    public CrossrefGroup(String clazz) {

        this.clazz = clazz;
    }

    /**
     * Store the keys in the group.
     * 
     * @param keys the keys to store
     * @param layer the class
     */
    public void store(String[] keys, String layer) {

        if (map.get(keys) != null) {
            return;
        }

        map.put(keys, new CrossReference(layer, keys));
    }

    /**
*      org.extex.exindex.lisp.LInterpreter, MarkupContainer, boolean)
     */
    public void write(Writer writer, LInterpreter interpreter,
            MarkupContainer markupContainer, boolean trace)
            throws IOException,
                LNonMatchingTypeException {

        Markup markupCrossrefGroup =
                markupContainer.getMarkup("markup-crossref-list");

        markupCrossrefGroup.write(writer, markupContainer, clazz, Markup.OPEN,
            trace);
        boolean first = true;

        for (CrossReference xref : map.values()) {
            if (first) {
                first = false;
            } else {
                markupCrossrefGroup.write(writer, markupContainer, clazz,
                    Markup.SEP, trace);
            }

            xref.write(writer, interpreter, markupContainer, trace);
        }
        markupCrossrefGroup.write(writer, markupContainer, clazz,
            Markup.CLOSE, trace);
    }

}
