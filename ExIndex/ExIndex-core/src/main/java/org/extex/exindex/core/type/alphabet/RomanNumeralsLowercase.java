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

package org.extex.exindex.core.type.alphabet;

import java.io.PrintStream;

import org.extex.exindex.core.type.alphabet.util.RomanNumber;
import org.extex.exindex.core.type.page.LowerRomanPage;
import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This location class represents a parser for roman numbers made of lowercase
 * letters.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class RomanNumeralsLowercase implements LValue, Alphabet, LocationClass {

    /**
     * Creates a new object.
     * 
     */
    public RomanNumeralsLowercase() {

    }

    /**
*      java.lang.String)
     */
    public PageReference match(String encap, String s) {

        if (RomanNumber.isLowerRomanNumber(s)) {
            return new LowerRomanPage(encap, s);
        }
        return null;
    }

public boolean match(StringBuilder s) {

        if (s.length() == 0) {
            return false;
        }
        char c = s.charAt(0);
        if (c != 'i' && c != 'v' && c != 'x' && c != 'c' && c != 'm') {
            return false;
        }
        s.deleteCharAt(0);
        while (s.length() > 0) {
            c = s.charAt(0);
            if (c != 'i' && c != 'v' && c != 'x' && c != 'c' && c != 'm') {
                return true;
            }
            s.deleteCharAt(0);
        }
        return true;
    }

public void print(PrintStream stream) {

        stream.print("#roman-numeral-lowercase");
    }

}
