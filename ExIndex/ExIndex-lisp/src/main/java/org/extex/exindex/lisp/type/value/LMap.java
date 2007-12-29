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

package org.extex.exindex.lisp.type.value;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides a map for the L system.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LMap implements LValue {

    /**
     * The field <tt>array</tt> contains the content.
     */
    private Map<LValue, LValue> map = new HashMap<LValue, LValue>();

    /**
     * Creates a new object.
     */
    public LMap() {

        super();
    }

    /**
     * Get an element at a certain position. If the position is empty then
     * <tt>nil</tt> is returned.
     * 
     * @param key the key
     * 
     * @return the specified element or <tt>nil</tt>
     */
    public LValue get(LValue key) {

        LValue value = map.get(key);
        if (value == null) {
            return LList.NIL;
        }
        return value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.value.LValue#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        boolean first = true;
        stream.print("#{");
        for (LValue key : map.keySet()) {
            if (first) {
                first = false;
            } else {
                stream.print("\n");
            }
            key.print(stream);
            stream.print(" => ");
            map.get(key).print(stream);
        }
        stream.print("} ");
    }

    /**
     * Add a value to the end.
     * 
     * @param key the key
     * @param value the value to add
     */
    public void put(LValue key, LValue value) {

        map.put(key, value);
    }

}
