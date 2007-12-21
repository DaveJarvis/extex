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
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class UpperRomanPage extends AbstractPage {

    /**
     * The field <tt>LETTER</tt> contains the ...
     */
    private static final String LETTER = "IVXLCDM";

    /**
     * The field <tt>VAL</tt> contains the ...
     */
    private static final int[] VAL = new int[]{1, 5, 10, 50, 100, 500, 1000};

    /**
     * Compute an ordinal number for a page.
     * 
     * @param page the page string
     * 
     * @return the ordinal number
     */
    private static int computeOrd(String page) {

        int ord = 0;
        int len = page.length();
        for (int i = 0; i < len; i++) {
            int j = LETTER.indexOf(page.charAt(i));
            if (j < 0) {
                throw new IllegalArgumentException(page);
            }
            if (i + 1 < len && j % 2 == 0 && j < 5) {
                int n = LETTER.indexOf(page.charAt(i + 1));
                if (n == j + 1 || n == j + 2) {
                    ord += VAL[n] - VAL[j];
                    i++;
                    continue;
                }
            }
            ord += VAL[j];
        }
        return ord;
    }

    /**
     * Creates a new object.
     * 
     * @param enc the encapsulator
     * @param page the page number
     */
    public UpperRomanPage(String enc, String page) {

        super(enc, page, computeOrd(page));
    }

}
