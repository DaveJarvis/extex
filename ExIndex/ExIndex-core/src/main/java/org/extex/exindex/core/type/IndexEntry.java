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
import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.lisp.LInterpreter;

/**
 * This class represents an index entry in the structured index.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class IndexEntry extends ArrayList<IndexEntry> {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>keywords</tt> contains the keywords.
     */
    private String[] keywords;

    /**
     * The field <tt>locationClassGroups</tt> contains the location class
     * groups.
     */
    private List<LocationClassGroup> locationClassGroups =
            new ArrayList<LocationClassGroup>();

    /**
     * Creates a new object.
     * 
     * @param keywords the keywords
     */
    public IndexEntry(String[] keywords) {

        super();
        this.keywords = keywords;
    }

    /**
     * Getter for keywords.
     * 
     * @return the keywords
     */
    public String[] getKeywords() {

        return keywords;
    }

    /**
     * Getter for locationClassGroups.
     * 
     * @return the locationClassGroups
     */
    public List<LocationClassGroup> getLocationClassGroups() {

        return locationClassGroups;
    }

    /**
     * Write an index entry to a writer.
     * 
     * @param writer the writer
     * @param interpreter the interpreter
     * @param level the current level
     * 
     * @throws IOException in case of an I/O error
     */
    public void write(Writer writer, LInterpreter interpreter, int level)
            throws IOException {

        String hier = Integer.toString(level);
        int level1 = level + 1;
        boolean first = true;
        interpreter.writeString(writer, "markup:indexentry-" + hier + "-open");

        boolean firstGroup = true;
        for (LocationClassGroup group : locationClassGroups) {
            if (firstGroup) {
                firstGroup = false;
            } else {
                interpreter.writeString(writer, "markup:indexentry-" + hier
                        + "-sep"); // TODO
            }
            group.write(writer, interpreter);
        }

        for (IndexEntry entry : this) {
            if (first) {
                first = false;
            } else {
                interpreter.writeString(writer, "markup:indexentry-" + hier
                        + "-sep");
            }
            entry.write(writer, interpreter, level1);
        }
        interpreter.writeString(writer, "markup:indexentry-" + hier + "-close");
    }

}
