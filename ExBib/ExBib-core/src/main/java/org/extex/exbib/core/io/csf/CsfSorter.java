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

package org.extex.exbib.core.io.csf;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.sorter.Sorter;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is a sorter which used a csf file to read a configuration from.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CsfSorter implements Comparator<Entry>, Sorter {

    /**
     * The field <tt>ord</tt> contains the order mapping.
     */
    private int ord[] = new int[256];

    /**
     * The field <tt>upper</tt> contains the uppercase mapping.
     */
    private char upper[] = new char[256];

    /**
     * The field <tt>lower</tt> contains the lowercase mapping.
     */
    private char lower[] = new char[256];

    /**
     * Creates a new object.
     */
    public CsfSorter() {

        for (int i = 0; i < 128; i++) {
            ord[i] = i;
            lower[i] = (char) i;
            upper[i] = (char) i;
        }
        for (int i = 128; i < 256; i++) {
            ord[i] = -1;
            lower[i] = (char) i;
            upper[i] = (char) i;
        }
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

        int ia = 0;
        int ib = 0;
        int ca, cb;

        do {
            for (ca = -1; ia < ka.length() && ca < 0; ia++) {
                ca = ord[ka.charAt(ia)];
            }
            for (cb = -1; ib < kb.length() && cb < 0; ib++) {
                cb = ord[kb.charAt(ib)];
            }
        } while (ca >= 0 && ca == cb);

        return ca - cb;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        // unused
    }

    /**
     * Get the lower counterpart for a character. If a character has no such
     * counterpart the character itself is returned.
     * 
     * @param c the character
     * 
     * @return the lower case character
     */
    int getLower(char c) {

        return lower[c];
    }

    /**
     * Get the upper counterpart for a character. If a character has no such
     * counterpart the character itself is returned.
     * 
     * @param c the character
     * 
     * @return the uppercase character
     */
    int getUpper(char c) {

        return upper[c];
    }

    /**
     * Setter for the lowercase counterpart of a uppercase character.
     * 
     * @param uc the uppercase character
     * @param lc the lowercase character
     */
    public void setLower(char uc, char lc) {

        lower[uc] = lc;
    }

    /**
     * Setter for the order mapping.
     * 
     * @param c the character
     * @param on the ordinal number
     */
    public void setOrder(char c, int on) {

        ord[c] = on;
    }

    /**
     * Setter for the uppercase counterpart of a lowercase character.
     * 
     * @param lc the lowercase character
     * @param uc the uppercase character
     */
    public void setUpper(char lc, char uc) {

        upper[lc] = uc;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.db.sorter.Sorter#sort(java.util.List)
     */
    public void sort(List<Entry> list) throws ConfigurationException {

        Collections.sort(list, this);
    }

}
