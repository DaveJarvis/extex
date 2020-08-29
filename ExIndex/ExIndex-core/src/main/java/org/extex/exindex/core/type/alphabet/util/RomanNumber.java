/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.type.alphabet.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides utilities for roman numbers.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class RomanNumber {

    /**
     * The field <tt>LOWER_MAP</tt> contains the map for lower case roman
     * numbers.
     */
    private static final Map<Character, RomanNumber> LOWER_MAP =
            new HashMap<Character, RomanNumber>();

    /**
     * The field <tt>UPPER_MAP</tt> contains the map for upper case roman
     * numbers.
     */
    private static final Map<Character, RomanNumber> UPPER_MAP =
            new HashMap<Character, RomanNumber>();

    static {
        LOWER_MAP.put(Character.valueOf('i'), new RomanNumber(1, "vx"));
        LOWER_MAP.put(Character.valueOf('v'), new RomanNumber(5, ""));
        LOWER_MAP.put(Character.valueOf('x'), new RomanNumber(10, "lc"));
        LOWER_MAP.put(Character.valueOf('l'), new RomanNumber(50, ""));
        LOWER_MAP.put(Character.valueOf('c'), new RomanNumber(100, "dm"));
        LOWER_MAP.put(Character.valueOf('d'), new RomanNumber(500, ""));
        LOWER_MAP.put(Character.valueOf('m'), new RomanNumber(1000, ""));

        LOWER_MAP.put(Character.valueOf('\u2170'), new RomanNumber(1,
            "\u2174\u2179"));
        LOWER_MAP.put(Character.valueOf('\u2171'), new RomanNumber(2, ""));
        LOWER_MAP.put(Character.valueOf('\u2172'), new RomanNumber(3, ""));
        LOWER_MAP.put(Character.valueOf('\u2173'), new RomanNumber(4, ""));
        LOWER_MAP.put(Character.valueOf('\u2174'), new RomanNumber(5, ""));
        LOWER_MAP.put(Character.valueOf('\u2175'), new RomanNumber(6, ""));
        LOWER_MAP.put(Character.valueOf('\u2176'), new RomanNumber(7, ""));
        LOWER_MAP.put(Character.valueOf('\u2177'), new RomanNumber(8, ""));
        LOWER_MAP.put(Character.valueOf('\u2178'), new RomanNumber(9, ""));
        LOWER_MAP.put(Character.valueOf('\u2179'), new RomanNumber(10,
            "\u217a\u217b"));
        LOWER_MAP.put(Character.valueOf('\u217a'), new RomanNumber(11, ""));
        LOWER_MAP.put(Character.valueOf('\u217b'), new RomanNumber(12, ""));
        LOWER_MAP.put(Character.valueOf('\u217c'), new RomanNumber(50, ""));
        LOWER_MAP.put(Character.valueOf('\u217d'), new RomanNumber(100,
            "\u217e\u217f"));
        LOWER_MAP.put(Character.valueOf('\u217e'), new RomanNumber(500, ""));
        LOWER_MAP.put(Character.valueOf('\u217f'), new RomanNumber(1000, ""));
    }

    static {
        UPPER_MAP.put(Character.valueOf('I'), new RomanNumber(1, "VX"));
        UPPER_MAP.put(Character.valueOf('V'), new RomanNumber(5, ""));
        UPPER_MAP.put(Character.valueOf('X'), new RomanNumber(10, "LC"));
        UPPER_MAP.put(Character.valueOf('L'), new RomanNumber(50, ""));
        UPPER_MAP.put(Character.valueOf('C'), new RomanNumber(100, "DM"));
        UPPER_MAP.put(Character.valueOf('D'), new RomanNumber(500, ""));
        UPPER_MAP.put(Character.valueOf('M'), new RomanNumber(1000, ""));

        UPPER_MAP.put(Character.valueOf('\u2160'), new RomanNumber(1,
            "\u2164\u2169"));
        UPPER_MAP.put(Character.valueOf('\u2161'), new RomanNumber(2, ""));
        UPPER_MAP.put(Character.valueOf('\u2162'), new RomanNumber(3, ""));
        UPPER_MAP.put(Character.valueOf('\u2163'), new RomanNumber(4, ""));
        UPPER_MAP.put(Character.valueOf('\u2164'), new RomanNumber(5, ""));
        UPPER_MAP.put(Character.valueOf('\u2165'), new RomanNumber(6, ""));
        UPPER_MAP.put(Character.valueOf('\u2166'), new RomanNumber(7, ""));
        UPPER_MAP.put(Character.valueOf('\u2167'), new RomanNumber(8, ""));
        UPPER_MAP.put(Character.valueOf('\u2168'), new RomanNumber(9, ""));
        UPPER_MAP.put(Character.valueOf('\u2169'), new RomanNumber(10,
            "\u216a\u216b"));
        UPPER_MAP.put(Character.valueOf('\u216a'), new RomanNumber(11, ""));
        UPPER_MAP.put(Character.valueOf('\u216b'), new RomanNumber(12, ""));
        UPPER_MAP.put(Character.valueOf('\u216c'), new RomanNumber(50, ""));
        UPPER_MAP.put(Character.valueOf('\u216d'), new RomanNumber(100,
            "\u216e\u216f"));
        UPPER_MAP.put(Character.valueOf('\u216e'), new RomanNumber(500, ""));
        UPPER_MAP.put(Character.valueOf('\u216f'), new RomanNumber(1000, ""));
    }

    /**
     * Compute an ordinal number for a page.
     * 
     * @param page the page string
     * @param map the map to use
     * 
     * @return the ordinal number
     */
    private static int computeOrd(String page, Map<Character, RomanNumber> map) {

        if (page.equals("")) {
            throw new IllegalArgumentException();
        }
        int ord = 0;
        int len = page.length();
        for (int i = 0; i < len; i++) {
            RomanNumber desc = map.get(Character.valueOf(page.charAt(i)));
            if (desc == null) {
                throw new IllegalArgumentException(page);
            }
            if (i + 1 < len) {
                RomanNumber d = desc.combined(page.charAt(i + 1), map);
                if (d != null) {
                    ord += d.getNumber() - desc.getNumber();
                    i++;
                    continue;
                }
            }
            ord += desc.getNumber();
        }
        return ord;
    }

    /**
     * Compute an ordinal number for a page made up of lower case roman
     * numerals.
     * 
     * @param page the page string
     * 
     * @return the ordinal number
     */
    public static int computeOrdLower(String page) {

        return computeOrd(page, LOWER_MAP);
    }

    /**
     * Compute an ordinal number for a page made up of upper case roman
     * numerals.
     * 
     * @param page the page string
     * 
     * @return the ordinal number
     */
    public static int computeOrdUpper(String page) {

        return computeOrd(page, UPPER_MAP);
    }

    /**
     * Check whether the argument is a roman number made up of lowercase
     * letters. Both the normal letters and the special number signs of Unicode
     * are taken into account
     * 
     * @param page the page
     * 
     * @return <code>true</code> iff the page constitutes a roman number with
     *         lowercase letters only
     */
    public static boolean isLowerRomanNumber(String page) {

        return isRomanNumber(page, LOWER_MAP);
    }

    /**
     * Check whether the argument is a roman number made up of letters from the
     * given ma. Both the normal letters and the special number signs of Unicode
     * are taken into account
     * 
     * @param page the page
     * @param map the map of acceptable letters
     * 
     * @return <code>true</code> iff the page constitutes a roman number
     */
    private static boolean isRomanNumber(String page,
            Map<Character, RomanNumber> map) {

        int len = page.length();
        for (int i = 0; i < len; i++) {
            RomanNumber desc = map.get(Character.valueOf(page.charAt(i)));
            if (desc == null) {
                return false;
            }
            if (i + 1 < len && desc.combined(page.charAt(i + 1), map) != null) {
                i++;
            }
        }
        return true;
    }

    /**
     * Check whether the argument is a roman number made up of uppercase
     * letters. Both the normal letters and the special number signs of Unicode
     * are taken into account
     * 
     * @param page the page
     * 
     * @return <code>true</code> iff the page constitutes a roman number with
     *         uppercase letters only
     */
    public static boolean isUpperRomanNumber(String page) {

        return isRomanNumber(page, UPPER_MAP);
    }

    /**
     * The field <tt>number</tt> contains the associated number.
     */
    private final int number;

    /**
     * The field <tt>combining</tt> contains the combining characters for the
     * subtraction rule.
     */
    private final String combining;

    /**
     * Creates a new object.
     * 
     * @param number the associated number
     * @param combining the combining characters for the subtraction rule
     */
    private RomanNumber(int number, String combining) {

        this.number = number;
        this.combining = combining;
    }

    /**
     * Get the combining character associated with the given character.
     * 
     * @param c the character
     * @param map the map in effect
     * 
     * @return the combining character or <code>null</code> for none
     */
    private RomanNumber combined(char c, Map<Character, RomanNumber> map) {

        if (combining.indexOf(c) >= 0) {
            return map.get(Character.valueOf(c));
        }
        return null;
    }

    /**
     * Getter for number.
     * 
     * @return the number
     */
    public int getNumber() {

        return number;
    }

}
