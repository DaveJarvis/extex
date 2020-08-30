/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db.sorter;

import java.io.Serializable;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.extex.exbib.core.db.Entry;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This sorter uses a locale and the Java built-in collator for it.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LocaleSorter implements Comparator<Entry>, Sorter, Serializable {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * The field {@code collator} contains the collator.
     */
    private final transient Collator collator;

    /**
     * The field {@code loc} contains the name of the locale.
     */
    private final String loc;

    /**
     * Creates a new object.
     * 
     * @param loc the argument
     */
    public LocaleSorter(String loc) {

        this(loc, Collator.getInstance(new Locale(loc)));
    }

    /**
     * Creates a new object.
     * 
     * @param loc the argument
     * @param collator the collator
     */
    protected LocaleSorter(String loc, Collator collator) {

        this.loc = loc;
        this.collator = collator;
    }

public int compare(Entry a, Entry b) {

        String ka = a.getSortKey();

        if (ka == null) {
            ka = a.getKey();
        }

        String kb = b.getSortKey();

        if (kb == null) {
            kb = b.getKey();
        }

        return collator.compare(ka, kb);
    }

public void sort(List<Entry> list) throws ConfigurationException {

        Collections.sort(list, this);
    }

@Override
    public String toString() {

        return "locale:" + loc;
    }

}
