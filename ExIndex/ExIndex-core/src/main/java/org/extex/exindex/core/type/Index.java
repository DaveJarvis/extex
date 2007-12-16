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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.extex.exindex.core.Parameters;
import org.extex.exindex.core.makeindex.MakeindexParameters;
import org.extex.exindex.core.makeindex.pages.PageProcessor;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Index implements LValue {

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
     */
    public Index() {

        super();
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
     * Test that key and value of two entries are equal.
     * 
     * @param a the first entry
     * @param b the second entry
     * 
     * @return <code>true</code> iff the entries can be considered equal
     */
    private boolean eq(Entry a, Entry b) {

        String[] ka = a.getKey();
        String[] kb = b.getKey();
        if (ka.length != kb.length || !a.getValue().equals(b.getValue())) {
            return false;
        }

        for (int i = 0; i < ka.length; i++) {
            if (!ka[i].equals(kb[i])) {
                return false;
            }
        }
        return true;
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
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.value.LValue#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        // TODO gene: print unimplemented

    }

    /**
     * Sort the entries and return an array of sorted entries.
     * 
     * @param comperator the comparator
     * @param pp the page processor
     * 
     * @return the sorted list of the entries
     */
    public List<Entry> sort(Comparator<Entry> comperator, PageProcessor pp) {

        int size = content.size();
        if (size == 0) {
            return content;
        }
        Entry[] ea = new Entry[size];
        int i = 0;
        for (Entry e : content) {
            ea[i++] = e;
        }
        Arrays.sort(ea, comperator);

        content = new ArrayList<Entry>();

        Entry e = ea[0];
        content.add(e);
        for (i = 1; i < size; i++) {
            Entry x = ea[i];
            if (eq(e, x)) {
                e.addPages(x.getPages());
            } else {
                pp.join(e.getPages());
                e = x;
                content.add(e);
            }
        }
        pp.join(e.getPages());

        return content;
    }
}
