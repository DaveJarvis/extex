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

import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * List of Feature.
 * 
 * <p>
 * future list table
 * </p>
 * <p>
 * The headers of the GSUB and GPOS tables contain offsets to Feature List
 * tables (FeatureList) that enumerate all the features in a font. Features in a
 * particular FeatureList are not limited to any single script. A FeatureList
 * contains the entire list of either the GSUB or GPOS features that are used to
 * render the glyphs in all the scripts in the font. <br/> The FeatureList table
 * enumerates features in an array of records (FeatureRecord) and specifies the
 * total number of features (FeatureCount). Every feature must have a
 * FeatureRecord, which consists of a FeatureTag that identifies the feature and
 * an offset to a Feature table (described next). The FeatureRecord array is
 * arranged alphabetically by FeatureTag names. <br/> Note: The values stored in
 * the FeatureIndex array of a LangSys table are used to locate records in the
 * FeatureRecord array of a FeatureList table.
 * </p>
 * <p>
 * FeatureList table
 * </p>
 * <table border="1">
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>FeatureCount</td>
 * <td>Number of FeatureRecords in this table</td>
 * </tr>
 * <tr>
 * <td>struct</td>
 * <td>FeatureRecord[FeatureCount]</td>
 * <td>Array of FeatureRecords-zero-based (first feature has FeatureIndex =
 * 0)-listed alphabetically by FeatureTag</td>
 * </tr>
 * </table>
 * <p>
 * </p>
 * <p>
 * FeatureRecord
 * </p>
 * <table border="1">
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>Tag</td>
 * <td>FeatureTag</td>
 * <td>4-byte feature identification tag</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>Feature</td>
 * <td>Offset to Feature table-from beginning of FeatureList</td>
 * </tr>
 * </table>
 * <p>
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfFeatureList implements XMLWriterConvertible {

    /**
     * The feature name table allows you to include the font's text features,
     * the settings for each text feature, and the name table indices for common
     * (human-readable) names for the features and settings.
     */
    public class Feature implements XMLWriterConvertible {

        /**
         * feature params
         */
        private int featureParams;

        /**
         * lookup count
         */
        private int lookupCount;

        /**
         * lookup list index
         */
        private int[] lookupListIndex;

        /**
         * Create a new object
         * 
         * @param rar input
         * @param offset offset
         * @throws IOException if an IO-error occurs
         */
        Feature(RandomAccessR rar, int offset) throws IOException {

            rar.seek(offset);

            featureParams = rar.readUnsignedShort();
            lookupCount = rar.readUnsignedShort();
            lookupListIndex = new int[lookupCount];
            for (int i = 0; i < lookupCount; i++) {
                lookupListIndex[i] = rar.readUnsignedShort();
            }
        }

        /**
         * Returns the lookupCount
         * 
         * @return Returns the lookupCount
         */
        public int getLookupCount() {

            return lookupCount;
        }

        /**
         * Returns the lookuplistindex
         * 
         * @param i index
         * @return Returns the lookuplistindex
         */
        public int getLookupListIndex(int i) {

            return lookupListIndex[i];
        }

        /**
         * Returns the info for this class
         * 
         * @return Returns the info for this class
         */
        @Override
        public String toString() {

            StringBuffer buf = new StringBuffer();
            buf.append("Feature\n");
            buf.append("   featureparams  : ").append(featureParams).append(
                '\n');
            buf.append("   lookupcount    : ").append(lookupCount).append('\n');
            return buf.toString();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("feature");
            writer.writeAttribute("featureparams", featureParams);

            for (int i = 0; i < lookupCount; i++) {
                int lli = lookupListIndex[i];
                writer.writeStartElement("lookup");
                writer.writeAttribute("id", i);
                writer.writeAttribute("value", lli);
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
    }

    /**
     * record
     */
    public class Record implements XMLWriterConvertible {

        /**
         * offset
         */
        private int offset;

        /**
         * tag
         */
        private int tag;

        /**
         * Create a new object
         * 
         * @param rar input
         * @throws IOException if an IO-error occurs
         */
        Record(RandomAccessR rar) throws IOException {

            tag = rar.readInt();
            offset = rar.readUnsignedShort();
        }

        /**
         * Returns the offset.
         * 
         * @return Returns the offset.
         */
        public int getOffset() {

            return offset;
        }

        /**
         * Returns the tag.
         * 
         * @return Returns the tag.
         */
        public int getTag() {

            return tag;
        }

        /**
         * Returns the info for this class
         * 
         * @return Returns the info for this class
         */
        @Override
        public String toString() {

            StringBuffer buf = new StringBuffer();
            buf.append("FeatureRecord\n");
            buf.append("   tag    : ").append(tag).append('\n');
            buf.append("   offset : ").append(offset).append('\n');
            return buf.toString();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("record");
            writer.writeAttribute("tag", XtfScriptList.tag2String(tag));
            writer.writeEndElement();

        }

    }

    /**
     * feature count
     */
    private int featureCount;

    /**
     * feature records
     */
    private Record[] featureRecords;

    /**
     * features
     */
    private Feature[] features;

    /**
     * Create a new object
     * 
     * @param rar input
     * @param offset offset
     * @throws IOException if an IO-error occurs
     */
    XtfFeatureList(RandomAccessR rar, int offset) throws IOException {

        rar.seek(offset);
        featureCount = rar.readUnsignedShort();
        featureRecords = new Record[featureCount];
        features = new Feature[featureCount];
        for (int i = 0; i < featureCount; i++) {
            featureRecords[i] = new Record(rar);
        }
        for (int i = 0; i < featureCount; i++) {
            features[i] =
                    new Feature(rar, offset + featureRecords[i].getOffset());
        }
    }

    /**
     * Find a feature
     * 
     * @param langSys the langsys
     * @param tag the tag
     * @return Returns a feature
     */
    public Feature findFeature(XtfScriptList.LangSys langSys, String tag) {

        if (tag.length() != 4) {
            return null;
        }
        int tagVal =
                ((tag.charAt(0) << 24) | (tag.charAt(1) << 16)
                        | (tag.charAt(2) << 8) | tag.charAt(3));
        for (int i = 0; i < featureCount; i++) {
            if (featureRecords[i].getTag() == tagVal) {
                if (langSys.isFeatureIndexed(i)) {
                    return features[i];
                }
            }
        }
        return null;
    }

    /**
     * Returns the featureCount.
     * 
     * @return Returns the featureCount.
     */
    public int getFeatureCount() {

        return featureCount;
    }

    /**
     * Returns the featureRecords.
     * 
     * @return Returns the featureRecords.
     */
    public Record[] getFeatureRecords() {

        return featureRecords;
    }

    /**
     * Returns the features.
     * 
     * @return Returns the features.
     */
    public Feature[] getFeatures() {

        return features;
    }

    /**
     * Returns the info for this class
     * 
     * @return Returns the info for this class
     */
    @Override
    public String toString() {

        StringBuffer buf = new StringBuffer();
        buf.append("FeatureList\n");
        buf.append("   feature count : " + String.valueOf(featureCount) + '\n');
        return buf.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("featurelist");
        writer.writeAttribute("count", featureCount);

        writer.writeStartElement("records");
        for (int i = 0; i < featureCount; i++) {
            Record rec = featureRecords[i];
            rec.writeXML(writer);
        }
        writer.writeEndElement();

        writer.writeStartElement("features");
        for (int i = 0; i < featureCount; i++) {
            Feature fet = features[i];
            fet.writeXML(writer);
        }
        writer.writeEndElement();

        writer.writeEndElement();
    }
}
