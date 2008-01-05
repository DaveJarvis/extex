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

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.type.LocationClassContainer;
import org.extex.exindex.core.type.MarkupContainer;
import org.extex.exindex.core.type.attribute.AttributesContainer;
import org.extex.exindex.core.type.markup.Markup;
import org.extex.exindex.core.util.StringUtils;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.framework.i18n.LocalizerFactory;

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
     * The field <tt>layer</tt> contains the layer.
     */
    private String layer;

    /**
     * Creates a new object.
     * 
     * @param location the location
     * @param layer the layer
     */
    public LocRef(String location, String layer) {

        super();
        this.location = location;
        this.layer = layer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.raw.RefSpec#check(java.util.logging.Logger,
     *      org.extex.exindex.core.type.raw.RawIndexentry,
     *      org.extex.exindex.core.Indexer,
     *      org.extex.exindex.core.type.LocationClassContainer, java.util.List,
     *      org.extex.exindex.core.type.attribute.AttributesContainer)
     */
    public boolean check(Logger logger, RawIndexentry entry, Indexer index,
            LocationClassContainer crossrefClass, List<OpenLocRef> openPages,
            AttributesContainer attributes) {

        String attr = entry.getRef().getLayer();
        if (attr != null && !attributes.isAttributeDefined(attr)) {
            logger.severe(LocalizerFactory.getLocalizer(Indexer.class).format(
                "AttributeUnknown", attr));
            return false;
        }

        // TODO gene: check unimplemented
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.raw.RefSpec#getLayer()
     */
    public String getLayer() {

        return layer;
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

    /**
     * Write the location reference to a writer.
     * 
     * @param writer the writer
     * @param interpreter the interpreter
     * @param markupContainer the markup container
     * @param trace the trace indicator
     * 
     * @throws IOException in case of an I/O error
     * @throws LNonMatchingTypeException in case of an internal error
     */
    public void write(Writer writer, LInterpreter interpreter,
            MarkupContainer markupContainer, boolean trace)
            throws LNonMatchingTypeException,
                IOException {

        Markup markup = markupContainer.getMarkup("markup-locref");

        markup.write(writer, markupContainer, layer, Markup.OPEN, trace);
        writer.write(location);
        markup.write(writer, markupContainer, layer, Markup.CLOSE, trace);
    }

}
