/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.main;

import java.util.Comparator;
import java.util.regex.Pattern;

import org.extex.exindex.makeindex.Entry;

/**
 * This class provides a comparator for the sorting of makeindex entries.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class MakeindexComparator implements Comparator<Entry> {

    /**
     * The field <tt>NUMBER_PATTERN</tt> contains the pattern for numbers.
     */
    private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");

    /**
     * The field <tt>comparisons</tt> contains the number of comparisons
     * performed.
     */
    private long comparisons;

    /**
     * The field <tt>EQUAL</tt> contains the value for the equal comparison
     * result.
     */
    private static final int EQUAL = 0;

    /**
     * The field <tt>GREATER</tt> contains the value for the greater comparison
     * result, i.e. the first parameter is greater than the second one.
     */
    private static final int GREATER = 1;

    /**
     * The field <tt>LESS</tt> contains the value for the less comparison
     * result, i.e. the first parameter is less than the second one.
     */
    private static final int LESS = -1;

    /**
     * Compare two entries.
     * 
     * @param entry1 the first entry
     * @param entry2 the second entry
     * 
     * @return the result
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Entry entry1, Entry entry2) {

        comparisons++;
        int cmp = entry1.getHeading() - entry2.getHeading();
        if (cmp != EQUAL) {
            return cmp;
        }
        String[] keys1 = entry1.getKey();
        String[] keys2 = entry2.getKey();
        String[] values1 = entry1.getValue();
        String[] values2 = entry2.getValue();
        int len = Math.max(keys1.length, keys2.length);

        for (int i = 0; i < len; i++) {
            cmp = compareOne(keys1, keys2, i);
            if (cmp != EQUAL) {
                return cmp;
            }
            cmp = compareOne(values1, values2, i);
            if (cmp != EQUAL) {
                return cmp;
            }
        }

        return EQUAL;
    }

    /**
     * Compare two string arrays at a certain position.
     * 
     * @param a1 the first array of strings
     * @param a2 the second array of strings
     * @param i the index
     * 
     * @return the result of the comparison at the position
     */
    protected int compareOne(String[] a1, String[] a2, int i) {

        if (i >= a1.length) {
            return (i < a2.length ? LESS : EQUAL);
        } else if (i >= a2.length) {
            return GREATER;
        }
        return compareStrings(a1[i], a2[i]);
    }

    /**
     * Compare two strings for ordering.
     * <ul>
     * <li>Empty strings come before anything else.</li>
     * <li>Numbers consisting of digits only come next. They are ordered
     * according to the numerical value.</li>
     * <li>If the first character is a special character then they come after
     * the numbers and before the alphabetic segment.</li>
     * <li>Finally the alphabetic segment is ordered according to the
     * lower-cased equivalent. If the numbers are compared equal when ignoring
     * case they are compared case insensitive.</li>
     * </ul>
     * 
     * @param first the first string
     * @param second the second string
     * 
     * @return 0 if they are equal, -1 if the first one is before the second one
     *         and 1 if the second one is before the first one
     */
    protected int compareStrings(String first, String second) {

        if (first.equals("")) {
            return second.equals("") ? EQUAL : LESS;
        } else if (second.equals("")) {
            return GREATER;
        }

        if (NUMBER_PATTERN.matcher(first).matches()) {
            return NUMBER_PATTERN.matcher(second).matches() //
                    ? Integer.parseInt(first) - Integer.parseInt(second)
                    : LESS;
        } else if (NUMBER_PATTERN.matcher(second).matches()) {
            return GREATER;
        }
        char c1 = first.charAt(0);
        char c2 = second.charAt(0);
        if (issymbol(c1)) {
            if (issymbol(c2)) {
                if (Character.isDigit(c1)) {
                    return Character.isDigit(c2)
                            ? first.compareTo(second)
                            : GREATER;
                }
                return Character.isDigit(c2) ? LESS : first.compareTo(second);
            }
            return LESS;
        } else if (issymbol(c2)) {
            return GREATER;
        }
        int cmp = first.compareToIgnoreCase(second);
        return (cmp != EQUAL ? cmp : first.compareTo(second));
    }

    /**
     * Getter for the number of comparisons.
     * 
     * @return the number of comparisons
     */
    public long getComparisons() {

        return comparisons;
    }

    /**
     * Check whether the parameter is a symbol.
     * 
     * @param c the character
     * 
     * @return <code>true</code> iff the character is a symbol
     */
    protected boolean issymbol(char c) {

        return ('!' <= c && c <= '@') || //
                ('[' <= c && c <= '`') || //
                ('{' <= c && c <= '~');
    }

    /**
     * Reset the number of comparisons to 0.
     */
    public void reset() {

        comparisons = 0;
    }

}
