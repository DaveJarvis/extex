/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.type.raw;

import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.type.LocationClassContainer;
import org.extex.exindex.core.type.attribute.AttributesContainer;

/**
 * This interface describes a close location specification.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6617 $
 */
public class CloseLocRef extends LocRef {

    /**
     * Creates a new object.
     * 
     * @param location the location
     * @param layer the layer
     */
    public CloseLocRef(String location, String layer) {

        super(location, layer);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.raw.LocRef#check(java.util.logging.Logger,
     *      org.extex.exindex.core.type.raw.RawIndexentry,
     *      org.extex.exindex.core.Indexer,
     *      org.extex.exindex.core.type.LocationClassContainer, java.util.List,
     *      org.extex.exindex.core.type.attribute.AttributesContainer)
     */
    @Override
    public boolean check(Logger logger, RawIndexentry entry, Indexer index,
            LocationClassContainer crossrefClass, List<OpenLocRef> openPages,
            AttributesContainer attributes) {

        for (int i = openPages.size() - 1; i > 0; i--) {

            if (getLocation().equals(openPages.get(i).getLocation())) {
                openPages.remove(i);
                return super.check(logger, entry, index, crossrefClass,
                    openPages, attributes);
            }
        }
        // TODO
        return false;
    }
}
