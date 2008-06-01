/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db.sorter;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.extex.exbib.core.db.Entry;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This sorter uses a locale and the Java built-in collator for it.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LocaleSorter implements Comparator<Entry>, Sorter {

    /**
     * The field <tt>collator</tt> contains the collator.
     */
    private Collator collator;

    /**
     * The field <tt>loc</tt> contains the name of the locale.
     */
    private String loc;

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

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
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

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.sorter.Sorter#sort(java.util.List)
     */
    public void sort(List<Entry> list) throws ConfigurationException {

        Collections.sort(list, this);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "locale:" + loc;
    }

}
