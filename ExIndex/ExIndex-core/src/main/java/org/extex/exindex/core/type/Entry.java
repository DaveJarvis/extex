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

package org.extex.exindex.core.type;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Entry {

    /**
     * The field <tt>value</tt> contains the value.
     */
    private String display;

    /**
     * The field <tt>key</tt> contains the key.
     */
    private String[] key;

    /**
     * The field <tt>pages</tt> contains the pages contained.
     */
    private List<PageReference> pages = new ArrayList<PageReference>();

    /**
     * Creates a new object.
     * 
     * @param k the key
     * @param display the display string
     * @param page the page specification
     */
    public Entry(String[] k, String display, PageReference page) {

        super();
        this.key = k;
        this.display = display;
        this.pages.add(page);
    }

    /**
     * Getter for the heading character.
     * 
     * @return the heading character
     */
    public char getHeading() {

        String key0 = key[0];
        return (key0 == null || key0.equals("") //
        ? 0 : Character.toLowerCase(key0.charAt(0)));
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
    public List<PageReference> getPages() {

        return pages;
    }

    /**
     * Getter for value.
     * 
     * @return the value
     */
    public String getValue() {

        return display;
    }

}
