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

package org.extex.font.format.xtf;

import java.io.IOException;

import org.extex.util.XMLWriterConvertible;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Class for a FeatureRecord.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FeatureRecord implements XMLWriterConvertible {

    /**
     * Offset to Feature Parameters table (if one has been defined for the
     * feature), relative to the beginning of the Feature Table; = NULL if not
     * required.
     */
    private int featureParamsOffset;

    /**
     * Array of LookupList indices for this feature -zero-based (first lookup is
     * LookupListIndex = 0).
     */
    private int[] lookupListIndex;

    /**
     * Offset to Feature table-from beginning of FeatureList.
     */
    private int offset;

    /**
     * The feature identification tag.
     */
    private FeatureTag tag;

    /**
     * The gpos table.
     */
    private OtfTableGPOS gpos;

    /**
     * Creates a new object.
     * 
     * @param tableGPOS The gpos table.
     * @param rar input
     * @throws IOException if a io-error occurred.
     */
    public FeatureRecord(OtfTableGPOS tableGPOS, RandomAccessR rar)
            throws IOException {

        gpos = tableGPOS;
        tag = new FeatureTag(rar);
        offset = rar.readUnsignedShort();

    }

    /**
     * Getter for featureParamsOffset.
     * 
     * @return the featureParamsOffset
     */
    public int getFeatureParamsOffset() {

        return featureParamsOffset;
    }

    /**
     * Getter for lookupListIndex.
     * 
     * @return the lookupListIndex
     */
    public int[] getLookupListIndex() {

        return lookupListIndex;
    }

    /**
     * Getter for offset.
     * 
     * @return the offset
     */
    public int getOffset() {

        return offset;
    }

    /**
     * Getter for tag.
     * 
     * @return the tag
     */
    public FeatureTag getTag() {

        return tag;
    }

    /**
     * Read the feature table.
     * <p>
     * A Feature table defines a feature with one or more lookups. The client
     * uses the lookups to substitute or position glyphs. <br/> Feature tables
     * defined within the GSUB table contain references to glyph substitution
     * lookups, and feature tables defined within the GPOS table contain
     * references to glyph positioning lookups. If a text-processing operation
     * requires both glyph substitution and positioning, then both the GSUB and
     * GPOS tables must each define a Feature table, and the tables must use the
     * same FeatureTags. <br/> A Feature table consists of an offset to a
     * Feature Parameters (FeatureParams) table (if one has been defined for
     * this feature - see note in the following paragraph), a count of the
     * lookups listed for the feature (LookupCount), and an arbitrarily ordered
     * array of indices into a LookupList (LookupListIndex). The LookupList
     * indices are references into an array of offsets to Lookup tables. <br/>
     * The format of the Feature Parameters table is specific to a particular
     * feature, and must be specified in the feature's entry in the Feature Tags
     * section of the OpenType Layout Tag Registry. The length of the Feature
     * Parameters table must be implicitly or explicitly specified in the
     * Feature Parameters table itself. The FeatureParams field in the Feature
     * Table records the offset relative to the beginning of the Feature Table.
     * If a Feature Parameters table is not needed, the FeatureParams field must
     * be set to NULL.
     * </p>
     * <table border="1">
     * <tr>
     * <td><b>Type</b></td>
     * <td><b>Name</b></td>
     * <td><b>Description</b></td>
     * </tr>
     * <tr>
     * <td>Offset</td>
     * <td>FeatureParams</td>
     * <td>Offset to Feature Parameters table (if one has been defined for the
     * feature), relative to the beginning of the Feature Table; = NULL if not
     * required</td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>LookupCount</td>
     * <td>Number of LookupList indices for this feature</td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>LookupListIndex<br>
     * [LookupCount]</td>
     * <td>Array of LookupList indices for this feature -zero-based (first
     * lookup is LookupListIndex = 0)</td>
     * </tr>
     * </table> <br/>
     * 
     * @param rar The input.
     * @param baseoffset The offset.
     * @throws IOException if a io-error occurred.
     */
    public void read(RandomAccessR rar, int baseoffset) throws IOException {

        rar.seek(baseoffset + offset);

        featureParamsOffset = rar.readUnsignedShort();
        int lookupCount = rar.readUnsignedShort();
        lookupListIndex = new int[lookupCount];

        for (int i = 0; i < lookupCount; i++) {
            lookupListIndex[i] = rar.readUnsignedShort();
        }

    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("record");
        writer.writeAttribute("tag", tag.toString());
        writer.writeAttribute("tagname", tag.getName());
        writer.writeAttribute("offset", getOffset(), 8);
        writer.writeAttribute("featureParamsOffset", featureParamsOffset, 8);
        writer.writeStartElement("lookupListIndex");
        writer.writeIntArrayAsEntries(lookupListIndex);
        writer.writeEndElement();
        writer.writeEndElement();
    }

}
