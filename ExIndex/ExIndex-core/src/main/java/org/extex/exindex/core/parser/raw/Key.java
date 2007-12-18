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

package org.extex.exindex.core.parser.raw;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6617 $
 */
public class Key {

    /**
     * The field <tt>mainKey</tt> contains the main key as given by the user.
     */
    private String[] mainKey;

    /**
     * The field <tt>mergeKey</tt> contains the ...
     */
    private String[] mergeKey;

    /**
     * The field <tt>printKey</tt> contains the print key.
     */
    private String[] printKey;

    /**
     * The field <tt>sortKey</tt> contains the ...
     */
    private String[] sortKey;

    /**
     * Creates a new object.
     * 
     * @param key the main key
     * @param print the print key
     */
    public Key(String[] key, String[] print) {

        super();
        this.mainKey = key;
        this.printKey = print;
    }

    /**
     * Getter for the main key.
     * 
     * @return the main key
     */
    public String[] getMainKey() {

        return mainKey;
    }

    /**
     * Getter for mergeKey.
     * 
     * @return the mergeKey
     */
    public String[] getMergeKey() {

        return mergeKey;
    }

    /**
     * Getter for the print key.
     * 
     * @return the print key
     */
    public String[] getPrintKey() {

        return printKey;
    }

    /**
     * Getter for sortKey.
     * 
     * @return the sortKey
     */
    public String[] getSortKey() {

        return sortKey;
    }

    /**
     * Setter for mergeKey.
     * 
     * @param mergeKey the mergeKey to set
     */
    public void setMergeKey(String[] mergeKey) {

        if (this.mergeKey != null) {
            throw new RuntimeException("attempt to redefine the merge key");
        }
        this.mergeKey = mergeKey;
    }

    /**
     * Setter for sortKey.
     * 
     * @param sortKey the sortKey to set
     */
    public void setSortKey(String[] sortKey) {

        if (this.sortKey != null) {
            throw new RuntimeException("attempt to redefine the sort key");
        }
        this.sortKey = sortKey;
    }

}
