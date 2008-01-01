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

package org.extex.exindex.core.parser.raw;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Logger;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.command.type.AttributesContainer;
import org.extex.exindex.core.command.type.CrossrefClassContainer;
import org.extex.exindex.core.command.type.LMarkup;
import org.extex.exindex.core.type.MarkupContainer;
import org.extex.exindex.core.util.StringUtils;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LNonMatchingTypeException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class represents a raw cross reference.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6617 $
 */
public class CrossReference implements RefSpec {

    /**
     * The constant <tt>LOCALIZER</tt> contains the localizer for obtaining
     * messages.
     */
    private static final Localizer LOCALIZER =
            LocalizerFactory.getLocalizer(CrossReference.class);

    /**
     * The field <tt>layers</tt> contains the reference list.
     */
    private String[] layers;

    /**
     * The field <tt>clazz</tt> contains the class name.
     */
    private String clazz;

    /**
     * Creates a new object.
     * 
     * @param keys the references
     * @param layer the layer
     */
    public CrossReference(String[] keys, String layer) {

        this.layers = keys;
        this.clazz = layer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.raw.RefSpec#check(
     *      java.util.logging.Logger,
     *      org.extex.exindex.core.parser.raw.RawIndexentry,
     *      org.extex.exindex.core.Indexer,
     *      org.extex.exindex.core.command.type.CrossrefClassContainer,
     *      java.util.List,
     *      org.extex.exindex.core.command.type.AttributesContainer)
     */
    public boolean check(Logger logger, RawIndexentry entry, Indexer index,
            CrossrefClassContainer crossrefClass, List<OpenLocRef> openPages,
            AttributesContainer attributes) {

        String clazz = entry.getRef().getLayer();
        Boolean unverified = crossrefClass.lookupCrossrefClass(clazz);
        if (unverified == null) {
            logger.warning(LOCALIZER.format("UndefinedCrossrefClass", clazz));
            return false;
        } else if (unverified.booleanValue()) {
            return true;
        }
        if (index.lookup(layers) != null) {
            logger.warning(LOCALIZER.format("UndefinedCrossref", toString()));
            return false;
        }
        return true;
    }

    /**
     * Getter for keys.
     * 
     * @return the keys
     */
    public String[] getKeys() {

        return layers;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.raw.RefSpec#getLayer()
     */
    public String getLayer() {

        return clazz;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.raw.RefSpec#getLocation()
     */
    public String getLocation() {

        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        boolean first = true;
        for (String s : layers) {
            if (first) {
                first = false;
            } else {
                sb.append(" ");
            }
            StringUtils.putPrintable(sb, s);
            sb.append("\"");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Write the cross-reference to a writer.
     * 
     * @param writer the writer
     * @param interpreter the interpreter
     * @param markupContainer the container for markup
     * @param trace the trace indicator
     * 
     * @throws IOException in case of an I/O error
     * @throws LNonMatchingTypeException in case of an error
     */
    public void write(Writer writer, LInterpreter interpreter,
            MarkupContainer markupContainer, boolean trace)
            throws IOException,
                LNonMatchingTypeException {

        LMarkup markupCrossrefLayerList =
                markupContainer.getMarkup("markup-crossref-layer-list");
        LMarkup markupCrossrefLayer =
                markupContainer.getMarkup("markup-crossref-layer");

        markupCrossrefLayerList.write(writer, markupContainer, clazz,
            LMarkup.OPEN, trace);

        boolean first = true;

        for (String layer : layers) {
            if (first) {
                first = false;
            } else {
                markupCrossrefLayerList.write(writer, markupContainer, clazz,
                    LMarkup.SEP, trace);
            }

            markupCrossrefLayer.write(writer, markupContainer, clazz,
                LMarkup.OPEN, trace);
            writer.write(layer);
            markupCrossrefLayer.write(writer, markupContainer, clazz,
                LMarkup.CLOSE, trace);
        }
        markupCrossrefLayerList.write(writer, markupContainer, clazz,
            LMarkup.CLOSE, trace);
    }

}
