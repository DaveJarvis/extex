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

import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.command.LDefineCrossrefClass;

/**
 * This interface describes a location specification.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6617 $
 */
public class LocRef implements RefSpec {

    /**
     * The field <tt>location</tt> contains the location.
     */
    private String location;

    /**
     * Creates a new object.
     * 
     * @param location the location
     */
    public LocRef(String location) {

        super();
        this.location = location;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.raw.RefSpec#check(java.util.logging.Logger,
     *      org.extex.exindex.core.parser.raw.RawIndexentry, Indexer,
     *      org.extex.exindex.core.command.LDefineCrossrefClass, java.util.List)
     */
    public boolean check(Logger logger, RawIndexentry entry, Indexer index,
            LDefineCrossrefClass crossrefClass, List<OpenLocRef> openPages) {

        // TODO gene: check unimplemented
        return true;
    }

    /**
     * Getter for the location.
     * 
     * @return the location
     */
    public String getLocation() {

        return location;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        StringUtils.putPrintable(sb, location);
        return sb.toString();
    }

}