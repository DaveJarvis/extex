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

package org.extex.exindex.core.type.raw;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.core.type.Location;
import org.extex.exindex.core.type.LocationClassContainer;
import org.extex.exindex.core.type.MarkupContainer;
import org.extex.exindex.core.type.StructuredIndex;
import org.extex.exindex.core.type.attribute.AttributesContainer;
import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.core.util.StringUtils;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;

/**
 * This interface describes a location specification.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6617 $
 */
public class LocationReference implements Reference, Location {

    /**
     * The field <tt>location</tt> contains the location.
     */
    private String layer;

    /**
     * The field <tt>location</tt> contains the location.
     */
    private String[] location;

    /**
     * Creates a new object.
     * 
     * @param layer the layer
     * @param location the location
     */
    public LocationReference(String layer, String... location) {

        this.layer = layer;
        this.location = location;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.raw.Reference#check(java.util.logging.Logger,
     *      org.extex.exindex.core.type.raw.RawIndexentry, StructuredIndex,
     *      org.extex.exindex.core.type.LocationClassContainer, java.util.List,
     *      org.extex.exindex.core.type.attribute.AttributesContainer)
     */
    public boolean check(Logger logger, RawIndexentry entry,
            StructuredIndex index, LocationClassContainer crossrefClass,
            List<OpenLocationReference> openPages,
            AttributesContainer attributes) {

        // String attr = entry.getRef().getLayer();
        // if (attr != null && !attributes.isAttributeDefined(attr)) {
        // logger.severe(LocalizerFactory.getLocalizer(Indexer.class).format(
        // "AttributeUnknown", attr));
        // return false;
        // }

        // TODO gene: check unimplemented
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.raw.Reference#getLayer()
     */
    public String getLayer() {

        return layer;
    }

    /**
     * Getter for the location.
     * 
     * @return the location
     */
    public String[] getLocation() {

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
        boolean first = true;
        for (String s : location) {
            if (first) {
                first = false;
            } else {
                sb.append(' ');
            }
            StringUtils.putPrintable(sb, s);
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.Location#write(java.io.Writer,
     *      org.extex.exindex.lisp.LInterpreter, MarkupContainer, boolean)
     */
    public void write(Writer writer, LInterpreter interpreter,
            MarkupContainer markupContainer, boolean trace)
            throws IOException,
                LNonMatchingTypeException {

        // TODO depth unused
        Markup markup = markupContainer.getMarkup("markup-locref");
        Markup markupLayer = markupContainer.getMarkup("markup-locref-layer");
        boolean first = true;

        for (String loc : location) {
            if (first) {
                first = false;
            } else {
                markup.write(writer, markupContainer, layer, Markup.SEP, trace);
            }
            markupLayer.write(writer, markupContainer, layer, Markup.OPEN,
                trace);
            markup.write(writer, markupContainer, layer, Markup.OPEN, trace);
            writer.write(loc);
            markup.write(writer, markupContainer, layer, Markup.CLOSE, trace);
            markupLayer.write(writer, markupContainer, layer, Markup.CLOSE,
                trace);
        }
    }

}
