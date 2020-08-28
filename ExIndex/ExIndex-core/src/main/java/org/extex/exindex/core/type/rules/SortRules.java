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

package org.extex.exindex.core.type.rules;

import java.util.ArrayList;


/**
 * This class provides a container for sort rules.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SortRules extends ArrayList<Rule> {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>ascending</tt> contains the indicator for ascending
     * order; the opposite is descending order.
     */
    private boolean ascending = true;


    public SortRules() {

    }

    /**
     * Getter for ascending.
     * 
     * @return the ascending
     */
    public boolean isAscending() {

        return ascending;
    }

    /**
     * Setter for ascending.
     * 
     * @param ascending the ascending to set
     */
    public void setAscending(boolean ascending) {

        this.ascending = ascending;
    }

}
