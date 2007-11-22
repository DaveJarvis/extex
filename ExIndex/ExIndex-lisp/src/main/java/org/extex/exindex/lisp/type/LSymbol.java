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

package org.extex.exindex.lisp.type;

import java.util.WeakHashMap;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LSymbol implements LValue {

    /**
     * The field <tt>symtab</tt> contains the ...
     */
    private static WeakHashMap<String, LSymbol> symtab =
            new WeakHashMap<String, LSymbol>();

    /**
     * This is the factory method for symbols.
     * 
     * @param value
     * @return
     */
    public synchronized static LSymbol get(String value) {

        LSymbol sym = symtab.get(value);
        if (sym == null) {
            sym = new LSymbol(value);
            symtab.put(value, sym);
        }

        return sym;
    }

    /**
     * The field <tt>value</tt> contains the value.
     */
    private String value;

    /**
     * Creates a new object. In fact these instances are managed via the factory
     * method.
     * 
     * @param value the value
     */
    private LSymbol(String value) {

        super();
        this.value = value;
    }

    /**
     * Getter for value.
     * 
     * @return the value
     */
    public String getValue() {

        return value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return value;
    }

}
