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

import org.extex.exindex.core.type.LocationClassContainer;
import org.extex.exindex.core.type.StructuredIndex;
import org.extex.exindex.core.type.attribute.AttributesContainer;

/**
 * This interface describes an open location specification.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6617 $
 */
public class OpenLocationReference extends LocationReference {

    /**
     * Creates a new object.
     * 
     * @param locref the location
     * @param layer the layer
     */
    public OpenLocationReference(String layer, String... locref) {

        super(layer, locref);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.raw.LocationReference#check(
     *      java.util.logging.Logger,
     *      org.extex.exindex.core.type.raw.RawIndexentry,
     *      StructuredIndex,
     *      org.extex.exindex.core.type.LocationClassContainer, java.util.List,
     *      org.extex.exindex.core.type.attribute.AttributesContainer)
     */
    @Override
    public boolean check(Logger logger, RawIndexentry entry, StructuredIndex index,
            LocationClassContainer crossrefClass,
            List<OpenLocationReference> openPages,
            AttributesContainer attributes) {

        openPages.add(this);
        return super.check(logger, entry, index, crossrefClass, openPages,
            attributes);
    }
}
