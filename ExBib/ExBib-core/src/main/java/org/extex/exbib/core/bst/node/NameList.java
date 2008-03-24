/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.bst.node;

import java.util.ArrayList;
import java.util.List;

import org.extex.exbib.core.bst.exception.ExBibNoNameException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.exceptions.ExBibSyntaxException;
import org.extex.exbib.core.io.Locator;

/**
 * This class represents a list of
 * {@link org.extex.exbib.core.bst.node.Name Name}s.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class NameList {

    /** The list of names */
    public List<Name> theValue = new ArrayList<Name>();

    /**
     * Creates a new object. The list is filled from a string representation.
     * 
     * @param names the string representation of the name list
     * @param locator the locator
     * 
     * @throws ExBibException in case that something goes wrong. See
     *         {@link #parse(String,Locator) parse()} for a detailed description
     *         of the possible cases
     */
    public NameList(String names, Locator locator) throws ExBibException {

        super();
        parse(names, locator);
    }

    /**
     * Getter for the i<sup>th</sup> name in the name list.
     * 
     * @param i the index of the name to extract
     * 
     * @return the i<super>th</super> name (0-based)
     * 
     * @throws ExBibException in case that the index does not point to a valid
     *         position in the name list
     */
    public Name get(int i) throws ExBibException {

        if (i < 0 || i >= theValue.size()) {
            throw new ExBibException("index out of bounds");
        }

        return theValue.get(i);
    }

    /**
     * Getter for the number of names in the list.
     * 
     * @return the number of names
     */
    public int length() {

        return theValue.size();
    }

    /**
     * Parses a string of names separated by the string "and" enclosed in
     * whitespace at brace level 0.
     * 
     * @param names the string of names to parse
     * @param locator the locator
     * 
     * @throws ExBibSyntaxException in case that there are too many commas
     * @throws ExBibNoNameException in case that a substring which should be a
     *         name could not be parsed as such
     * @throws ExBibImpossibleException in case that a programming error is
     *         detected
     */
    private void parse(String names, Locator locator)
            throws ExBibSyntaxException,
                ExBibNoNameException,
                ExBibImpossibleException {

        int start = 0;
        int level = 0;

        while (start < names.length()
                && Character.isWhitespace(names.charAt(start))) {
            start++;
        }

        for (int i = start; i < names.length(); i++) {
            char c = names.charAt(i);

            if (Character.isWhitespace(c) && level == 0) {
                if (i < names.length() - 4
                        && (names.charAt(i + 1) == 'a' || names.charAt(i + 1) == 'A')
                        && (names.charAt(i + 2) == 'n' || names.charAt(i + 2) == 'N')
                        && (names.charAt(i + 3) == 'd' || names.charAt(i + 3) == 'D')
                        && Character.isWhitespace(names.charAt(i + 4))) {
                    theValue.add(new Name(names.substring(start, i), locator));
                    i += 4;
                    start = i + 1;
                }
            } else if (c == '{') {
                level++;
            } else if (c == '}') {
                level--;
            }
        }

        if (start < names.length()) {
            theValue.add(new Name(names.substring(start), locator));
        }
    }
}
