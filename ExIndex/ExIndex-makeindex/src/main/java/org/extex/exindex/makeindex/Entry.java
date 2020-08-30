/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex;

import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.makeindex.pages.Pages;

/**
 * This class represents an entry in the index.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Entry {

    /**
     * The field {@code HEADING_NUMBER} contains the heading number constant.
     */
    public static final char HEADING_NUMBER = '1';

    /**
     * The field {@code HEADING_SYMBOL} contains the heading symbol constant.
     */
    public static final char HEADING_SYMBOL = ' ';

    /**
     * The field {@code value} contains the display value.
     */
    private final String[] display;

    /**
     * The field {@code key} contains the key.
     */
    private final String[] key;

    /**
     * The field {@code pages} contains the pages contained.
     */
    private final List<Pages> pages = new ArrayList<Pages>();

    /**
     * The field {@code heading} contains the heading character.
     */
    private final char heading;

    /**
     * Creates a new object.
     * 
     * @param k the key
     * @param display the display strings
     * @param page the page specification
     */
    public Entry(String[] k, String[] display, Pages page) {

        this.key = k;
        this.display = display;
        if (page != null) {
            this.pages.add(page);
        }
        String s = key[0] == null ? "" : key[0];
        if (s.equals("")) {
            this.heading = HEADING_SYMBOL;
        } else if (s.matches("[-]?[0-9]+")) {
            this.heading = HEADING_NUMBER;
        } else {
            char c = s.charAt(0);
            this.heading = (Character.isLetter(c))
                    ? Character.toLowerCase(c)
                    : HEADING_SYMBOL;
        }
    }

    /**
     * Add the given pages.
     * 
     * @param morePages the pages to add
     */
    public void addPages(List<Pages> morePages) {

        pages.addAll(morePages);
    }

    /**
     * Add the given pages.
     * 
     * @param morePages the pages to add
     */
    // public void addPages(List<PageReference> morePages) {

    // for (PageReference pr : morePages) {
    // pages.add(new PageRange(pr));
    // }
    // }
    /**
     * Getter for the heading character.
     * 
     * @return the heading character
     */
    public char getHeading() {

        return heading;
    }

    /**
     * Getter for key.
     * 
     * @return the key
     */
    public String[] getKey() {

        return key;
    }

    /**
     * Getter for the pages.
     * 
     * @return the pages
     */
    public List<Pages> getPages() {

        return pages;
    }

    /**
     * Getter for value.
     * 
     * @return the value
     */
    public String[] getValue() {

        return display;
    }

@Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();
        buffer.append("  entry ");
        for (String k : key) {
            buffer.append("\n\t");
            buffer.append(k);
        }
        buffer.append("\n  as ");
        for (String v : display) {
            buffer.append("\n\t");
            buffer.append(v);
        }
        buffer.append("\n  at ");
        for (Pages p : pages) {
            buffer.append("\n\t");
            buffer.append(p.toString());
        }
        return buffer.toString();
    }

}
