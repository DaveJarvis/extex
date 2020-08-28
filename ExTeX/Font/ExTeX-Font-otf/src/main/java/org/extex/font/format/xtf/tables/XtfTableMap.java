/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.font.format.xtf.tables;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map for a TTF/OTF table.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class XtfTableMap {

    /**
     * Create a new object.
     */
    public XtfTableMap() {

        data = new HashMap<>();
    }

    /**
     * The map for the table.
     */
    private final Map<Integer, XtfTable> data;

    /**
     * Clear the table.
     */
    public void clear() {

        data.clear();
    }

    /**
     * Returns the size of the table.
     * 
     * @return Returns the size of the table.
     */
    public int size() {

        return data.size();
    }

    /**
     * Check, if the table contains the table.
     * 
     * @param key The key of the table.
     * @return Returns <code>true</code>, if the map has the table, otherwise
     *         <code>false</code>.
     */
    public boolean containsKey(int key) {

        return data.containsKey( key );
    }

    /**
     * Returns the table.
     * 
     * @param key The key of the table.
     * @return Returns the table.
     */
    public XtfTable get(int key) {

        return data.get( key );
    }

    /**
     * Store a table.
     * 
     * @param key The key of the table.
     * @param table The table
     */
    public void put(int key, XtfTable table) {

        data.put( key, table);
    }

    /**
     * Returns the keys in an array
     * 
     * @return Returns the keys in an array
     */
    public int[] getKeys() {

        Set<Integer> set = data.keySet();
        Integer[] i = new Integer[set.size()];
        i = set.toArray(i);
        int[] keys = new int[i.length];
        for (int k = 0; k < i.length; k++) {
            keys[k] = i[ k ];
        }

        return keys;
    }

    /**
     * Returns a TTFTable array from the map.
     * 
     * @return Returns a TTFTable array from the map.
     */
    public XtfTable[] getTables() {

        XtfTable[] tab = new XtfTable[data.size()];
        int[] keys = getKeys();
        for (int i = 0; i < keys.length; i++) {
            tab[i] = get(keys[i]);
        }
        return tab;
    }
}
