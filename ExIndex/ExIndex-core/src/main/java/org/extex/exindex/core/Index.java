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

package org.extex.exindex.core;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.core.type.Entry;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Index {

    /**
     * The field <tt>params</tt> contains the parameters.
     */
    private Parameters params;

    /**
     * The field <tt>content</tt> contains the mapping from key to lists of
     * entries with this key.
     */
    private List<Entry> content = new ArrayList<Entry>();

    /**
     * Creates a new object.
     * 
     * @throws IOException
     */
    public Index() throws IOException {

        super();
        params = Parameters.load();
    }

    /**
     * Add an entry.
     * 
     * @param entry the entry to add
     */
    public void add(Entry entry) {

        content.add(entry);
    }

    /**
     * Getter for params.
     * 
     * @return the params
     */
    public Parameters getParams() {

        return params;
    }

    /**
     * Merge some parameters with the ones already there.
     * 
     * @param reader the reader
     * 
     * @return a pair of numbers denoting the number of attributes set and
     *         rejected
     * 
     * @throws IOException in case of an I/O error
     */
    public int[] loadStyle(Reader reader) throws IOException {

        return Parameters.load(reader, params);
    }

    /**
     * Sort the entries and return an array of sorted entries.
     * 
     * @param logger the logger
     * @param comperator the comparator
     * 
     * @return the sorted array of the entries
     */
    public Entry[] sort(Comparator<Entry> comperator, Logger logger) {

        Entry[] ea = new Entry[content.size()];
        int i = 0;
        for (Entry e : content) {
            ea[i++] = e;
        }
        Arrays.sort(ea, comperator);
        return ea;
    }
}
