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

package org.extex.exindex.core.type.page;

/**
 * This page reference consists of uppercase letters only.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class UpperPage extends AbstractPage {

    /**
     * Compute an ordinal number for a page consisting of uppercase letters.
     * 
     * @param page the page string
     * 
     * @return the ordinal number
     */
    private static int computeOrd(String page) {

        int ord = 0;
        for (int i = page.length() - 1; i >= 0; i--) {
            ord = ord * 26 - 'A' + page.charAt(i);
        }
        return ord;
    }

    /**
     * Creates a new object.
     * 
     * @param enc the encapsulator
     * @param page the page number
     */
    public UpperPage(String enc, String page) {

        super(enc, page, computeOrd(page));
    }

}
