/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exindex.makeindex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.makeindex.main.MakeindexComparator;
import org.extex.exindex.makeindex.pages.PageProcessor;

/**
 * This class is an indexer a la makeindex.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6731 $
 */
public class Index {

    /**
     * The field <tt>params</tt> contains the parameters.
     */
    private final Parameters params;

    /**
     * The field <tt>content</tt> contains the mapping from key to lists of
     * entries with this key.
     */
    private List<Entry> content = new ArrayList<Entry>();


    public Index() {

        params = MakeindexParameters.load();
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
     * Sort the entries and return a list of sorted entries.
     * 
     * @param comperator the comparator
     * @param pp the page processor
     * @param logger the logger
     * @param warn the number of warnings encoded in a single value array. The
     *        array is used to allow the modification of the value
     * 
     * @return the sorted list of the entries
     */
    public List<Entry> sort(MakeindexComparator comperator, PageProcessor pp,
            Logger logger, long[] warn) {

        int size = content.size();
        if (size <= 1) {
            return content;
        }
        Entry[] entries = content.toArray(new Entry[size]);
        Arrays.sort(entries, comperator);
        warn[1] = comperator.getComparisons();

        content = new ArrayList<Entry>();

        Entry entry = entries[0];
        content.add(entry);

        for (int i = 1; i < size; i++) {
            Entry x = entries[i];
            if (comperator.compare(entry, x) == 0) {
                entry.addPages(x.getPages());
            } else {
                warn[0] += pp.join(entry.getPages(), logger);
                pp.sort(entry.getPages());
                entry = x;
                content.add(entry);
            }
        }
        pp.sort(entry.getPages());
        warn[0] += pp.join(entry.getPages(), logger);

        return content;
    }
}
