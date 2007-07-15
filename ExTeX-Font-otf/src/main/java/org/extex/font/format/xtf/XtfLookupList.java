/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.font.format.xtf;

import java.io.IOException;

import org.extex.font.format.xtf.cff.LookupTableFactory;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * List for all LookupTables.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfLookupList implements XMLWriterConvertible {

    /**
     * lookup count
     */
    private int lookupCount;

    /**
     * lookupFactory
     */
    private LookupTableFactory lookupFactory;

    /**
     * lookup offsets
     */
    private int[] lookupOffsets;

    /**
     * lookups
     */
    private XtfLookup[] lookups;

    /**
     * Create a new object
     * 
     * @param rar The input.
     * @param offset The offset.
     * @param lookupFactory The factory for the lookup table.
     * @throws IOException if an IO-error occurs
     */
    XtfLookupList(RandomAccessR rar, int offset,
            LookupTableFactory lookupFactory) throws IOException {

        this.lookupFactory = lookupFactory;
        rar.seek(offset);
        lookupCount = rar.readUnsignedShort();
        lookupOffsets = new int[lookupCount];
        lookups = new XtfLookup[lookupCount];
        for (int i = 0; i < lookupCount; i++) {
            lookupOffsets[i] = rar.readUnsignedShort();
        }
        for (int i = 0; i < lookupCount; i++) {
            lookups[i] =
                    new XtfLookup(rar, offset + lookupOffsets[i],
                        lookupFactory, i);
        }
    }

    /**
     * Returns the lookup
     * 
     * @param feature feature
     * @param index index
     * @return Returns the lookup
     */
    public XtfLookup getLookup(XtfFeatureList.Feature feature, int index) {

        if (feature.getLookupCount() > index) {
            int i = feature.getLookupListIndex(index);
            return lookups[i];
        }
        return null;
    }

    /**
     * Returns the lookupCount.
     * 
     * @return Returns the lookupCount.
     */
    public int getLookupCount() {

        return lookupCount;
    }

    /**
     * Returns the lookupOffsets.
     * 
     * @return Returns the lookupOffsets.
     */
    public int[] getLookupOffsets() {

        return lookupOffsets;
    }

    /**
     * Returns the lookups.
     * 
     * @return Returns the lookups.
     */
    public XtfLookup[] getLookups() {

        return lookups;
    }

    /**
     * Returns the info for this class
     * 
     * @return Returns the info for this class
     */
    @Override
    public String toString() {

        StringBuffer buf = new StringBuffer();
        buf.append("LookupList\n");
        buf.append("   lookup count  : ").append(lookupCount).append('\n');
        return buf.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("lookuplist");
        writer.writeAttribute("count", lookupCount);

        for (int i = 0; i < lookupCount; i++) {
            XtfLookup lu = lookups[i];
            lu.writeXML(writer);
        }
        writer.writeEndElement();
    }
}
