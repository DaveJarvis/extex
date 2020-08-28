/*
 * Copyright (C) 2003-2010 The ExTeX Group and individual authors listed below
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.extex.exbib.core.db.Entry;

/**
 * This class provides a Sorter compatible to the B<small>IB</small><span
 * style="margin-left: -0.15em;" >T</span><span style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X 0.99c sorting routine. The sorting order is determined by the byte
 * order of the internal representation of the sorting key. Thus accented
 * characters are located behind all not accented characters.
 * 
 * <p>
 * Note: this comparator imposes orderings that are inconsistent with equals.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CodepointIgnoreCaseSorter
        implements
            Comparator<Entry>,
            Sorter,
            Serializable {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * Provide a Comparator which just uses the appropriate keys and compares
     * them without respect to the case.
     * 
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

        return ka.compareToIgnoreCase(kb);
    }

    /**
     * Sort the given List according to the predefined order. As a side effect
     * the list is modified to reflect the new order.
     * 
     * @param list the list to sort
     */
    public void sort(List<Entry> list) {

        Collections.sort(list, this);
    }

}
