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
 * This interface describes a page reference.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface PageReference {

    /**
     * Getter for the encapsulator.
     * 
     * @return the encapsulator
     */
    String getEncap();

    /**
     * Getter for the ordinal number. The ordinal number is used to map an
     * arbitrary page reference to the natural numbers. This is needed for
     * determining the ordering and length of ranges. If no such mapping is
     * desirable then a negative number is used as indicator.
     * 
     * @return the ordinal number or a negative number
     */
    int getOrd();

    /**
     * Getter for page.
     * 
     * @return the page
     */
    String getPage();
}
