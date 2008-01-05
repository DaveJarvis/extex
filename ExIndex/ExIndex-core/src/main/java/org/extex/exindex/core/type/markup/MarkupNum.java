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

package org.extex.exindex.core.type.markup;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides a map of arrays with a default value.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MarkupNum extends Markup {

    /**
     * The field <tt>numMap</tt> contains the content.
     */
    private Map<String, int[]> numMap = new HashMap<String, int[]>();

    /**
     * Creates a new object.
     * 
     * @param displayName the name for debugging
     */
    public MarkupNum(String displayName) {

        super(displayName);
    }

    /**
     * Get an element at a certain position. If the position is empty then an
     * attempt is made to use the default value at position <code>null</code>.
     * If everything fails 0 is returned.
     * 
     * @param key the key
     * @param index the index
     * 
     * @return the specified element or <tt>nil</tt>
     */
    public int getNumber(String key, int index) {

        int[] value = numMap.get(key);
        if (value == null && key != null) {
            value = numMap.get(null);
        }
        return (value == null ? 0 : value[index]);
    }

    /**
     * Add a value to the end.
     * 
     * @param key the key
     * @param index the index
     * @param value the value to add
     */
    public void setNumber(String key, int index, int value) {

        int[] a = numMap.get(key);
        if (a == null) {
            a = new int[2];
            numMap.put(key, a);
            a[0] = 0;
            a[1] = 0;
        }
        a[index] = value;
    }

}
