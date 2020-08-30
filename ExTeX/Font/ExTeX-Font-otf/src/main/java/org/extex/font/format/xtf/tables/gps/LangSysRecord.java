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

package org.extex.font.format.xtf.tables.gps;

import java.io.IOException;

import org.extex.font.format.xtf.tables.tag.LanguageSystemTag;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Class for a langsys table.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class LangSysRecord implements XMLWriterConvertible {

    /**
     * Array of indices into the FeatureList-in arbitrary order.
     */
    private int[] featureIndex;

    /**
     * = NULL (reserved for an offset to a reordering table).
     */
    private int lookupOrderOffset;

    /**
     * Offset to LangSys table-from beginning of Script table.
     */
    private final int offset;

    /**
     * Index of a feature required for this language system- if no required
     * features = 0xFFFF.
     */
    private int reqFeatureIndex;

    /**
     * The LangSysTag identifier.
     */
    private final LanguageSystemTag tag;

    /**
     * Creates a new object.
     * 
     * @param rar input
     * @throws IOException if a io-error occurred.
     */
    public LangSysRecord(RandomAccessR rar) throws IOException {

        tag = LanguageSystemTag.add(rar);
        offset = rar.readUnsignedShort();
    }

    /**
     * Getter for featureIndex.
     * 
     * @return the featureIndex
     */
    public int[] getFeatureIndex() {

        return featureIndex;
    }

    /**
     * Getter for lookupOrderOffset.
     * 
     * @return the lookupOrderOffset
     */
    public int getLookupOrderOffset() {

        return lookupOrderOffset;
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
     * Getter for reqFeatureIndex.
     * 
     * @return the reqFeatureIndex
     */
    public int getReqFeatureIndex() {

        return reqFeatureIndex;
    }

    /**
     * Getter for tag.
     * 
     * @return the tag
     */
    public LanguageSystemTag getTag() {

        return tag;
    }

    /**
     * Read the language system table.
     * <p>
     * The Language System table (LangSys) identifies language-system features
     * used to render the glyphs in a script. (The LookupOrder offset is
     * reserved for future use.) <br> Optionally, a LangSys table may define a
     * Required Feature Index (ReqFeatureIndex) to specify one feature as
     * required within the context of a particular language system. For example,
     * in the Cyrillic script, the Serbian language system always renders
     * certain glyphs differently than the Russian language system. <br> Only
     * one feature index value can be tagged as the ReqFeatureIndex. This is not
     * a functional limitation, however, because the feature and lookup
     * definitions in OpenType Layout are structured so that one feature table
     * can reference many glyph substitution and positioning lookups. When no
     * required features are defined, then the ReqFeatureIndex is set to 0xFFFF.
     * <br> All other features are optional. For each optional feature, a
     * zero-based index value references a record (FeatureRecord) in the
     * FeatureRecord array, which is stored in a Feature List table
     * (FeatureList). The feature indices themselves (excluding the
     * ReqFeatureIndex) are stored in arbitrary order in the FeatureIndex array.
     * The FeatureCount specifies the total number of features listed in the
     * FeatureIndex array. <br> Features are specified in full in the
     * FeatureList table, FeatureRecord, and Feature table, which are described
     * later in this chapter. Example 2 at the end of this chapter shows a
     * Script table, LangSysRecord, and LangSys table used for contextual
     * positioning in the Arabic script.
     * </p>
     * <p>
     * LangSys table
     * </p>
     * <table>
 * <caption>TBD</caption>
 * <tr>
* <td><b>Type</b></td>
     * <td><b>Name</b></td>
     * <td><b>Description</b></td>
     * </tr>
     * <tr>
     * <td>Offset</td>
     * <td>LookupOrder</td>
     * <td>= NULL (reserved for an offset to a reordering table)</td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>ReqFeatureIndex</td>
     * <td>Index of a feature required for this language system- if no required
     * features = 0xFFFF</td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>FeatureCount</td>
     * <td>Number of FeatureIndex values for this language system-excludes the
     * required feature</td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>FeatureIndex[FeatureCount]</td>
     * <td>Array of indices into the FeatureList-in arbitrary order</td>
     * </tr>
     * </table> <br>
     * 
     * @param rar The input.
     * @param baseoffset The base offset.
     * @throws IOException if a io-error occurred.
     */
    public void read(RandomAccessR rar, int baseoffset) throws IOException {

        rar.seek(baseoffset + offset);

        lookupOrderOffset = rar.readUnsignedShort();
        reqFeatureIndex = rar.readUnsignedShort();
        int featureCount = rar.readUnsignedShort();
        featureIndex = new int[featureCount];
        for (int i = 0; i < featureCount; i++) {
            featureIndex[i] = rar.readUnsignedShort();
        }
    }

    /**
*      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("langsystemtable");
        writer.writeAttribute("tag", tag.toString());
        writer.writeAttribute("offset", getOffset(), 8);
        // writer.writeAttribute("lookupOrderOffset", lookupOrderOffset, 8);
        writer.writeAttribute("reqFeatureIndex", reqFeatureIndex, 8);
        writer.writeStartElement("featureindex");
        writer.writeIntArrayAsEntries(featureIndex);
        writer.writeEndElement();
        writer.writeEndElement();
    }
}
