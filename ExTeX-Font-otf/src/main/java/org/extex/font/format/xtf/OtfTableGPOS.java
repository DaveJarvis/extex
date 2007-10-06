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
 * Table gpos.
 * <p>
 * http://partners.adobe.com/public/developer/opentype/index_table_formats2.html
 * </p>
 * <p>
 * The Glyph Positioning table (GPOS) provides precise control over glyph
 * placement for sophisticated text layout and rendering in each script and
 * language system that a font supports.
 * </p>
 * <p>
 * Table Organization
 * </p>
 * <p>
 * The GPOS table begins with a header that defines offsets to a ScriptList, a
 * FeatureList, and a LookupList:
 * </p>
 * 
 * <ul>
 * <li>The ScriptList identifies all the scripts and language systems in the
 * font that use glyph positioning.
 * <li>The FeatureList defines all the glyph positioning features required to
 * render these scripts and language systems.
 * <li>The LookupList contains all the lookup data needed to implement each
 * glyph positioning feature.
 * </ul>
 * 
 * <p>
 * The GPOS table is organized so text processing clients can easily locate the
 * features and lookups that apply to a particular script or language system. To
 * access GPOS information, clients should use the following procedure:
 * </p>
 * <ol>
 * <li>Locate the current script in the GPOS ScriptList table.
 * <li>If the language system is known, search the script for the correct
 * LangSys table; otherwise, use the script's default language system
 * (DefaultLangSys table).
 * <li>The LangSys table provides index numbers into the GPOS FeatureList table
 * to access a required feature and a number of additional features.
 * <li>Inspect the FeatureTag of each feature, and select the features to apply
 * to an input glyph string.
 * <li>Each feature provides an array of index numbers into the GPOS LookupList
 * table. Lookup data is defined in one or more subtables that contain
 * information about specific glyphs and the kinds of operations to be performed
 * on them.
 * <li>Assemble all lookups from the set of chosen features, and apply the
 * lookups in the order given in the LookupList table.
 * </ol>
 * 
 * <p>
 * A lookup uses subtables to define the specific conditions, type, and results
 * of a positioning action used to implement a feature. All subtables in a
 * lookup must be of the same LookupType, as listed in the LookupType
 * Enumeration table:
 * </p>
 * <p>
 * LookupType Enumeration table for glyph positioning
 * </p>
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>Single adjustment</td>
 * <td>Adjust position of a single glyph</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>Pair adjustment</td>
 * <td>Adjust position of a pair of glyphs</td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>Cursive attachment</td>
 * <td>Attach cursive glyphs</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>MarkToBase attachment</td>
 * <td>Attach a combining mark to a base glyph</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>MarkToLigature attachment</td>
 * <td>Attach a combining mark to a ligature</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>MarkToMark attachment</td>
 * <td>Attach a combining mark to another mark</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td>Context positioning</td>
 * <td>Position one or more glyphs in context</td>
 * </tr>
 * <tr>
 * <td>8</td>
 * <td>Chained Context positioning</td>
 * <td>Position one or more glyphs in chained context</td>
 * </tr>
 * <tr>
 * <td>9</td>
 * <td>Extension positioning</td>
 * <td>Extension mechanism for other positionings</td>
 * </tr>
 * <tr>
 * <td>10+</td>
 * <td>Reserved</td>
 * <td>For future use</td>
 * </tr>
 * </table>
 * 
 * <p>
 * Each LookupType is defined by one or more subtables, whose format depends on
 * the type of positioning operation and the resulting storage efficiency. When
 * glyph information is best presented in more than one format, a single lookup
 * may define more than one subtable, as long as all the subtables are of the
 * same LookupType. For example, within a given lookup, a glyph index array
 * format may best represent one set of target glyphs, whereas a glyph index
 * range format may be better for another set.
 * </p>
 * <p>
 * A series of positioning operations on the same glyph or string requires
 * multiple lookups, one for each separate action. The values in the
 * ValueRecords are accumulated in these cases. Each lookup is given a different
 * array number in the LookupList table and is applied in the LookupList order.
 * </p>
 * 
 * <p>
 * During text processing, a client applies a lookup to each glyph in the string
 * before moving to the next lookup. A lookup is finished for a glyph after the
 * client locates the target glyph or glyph context and performs a positioning,
 * if specified. To move to the &quot;next&quot; glyph, the client will
 * typically skip all the glyphs that participated in the lookup operation:
 * glyphs that were positioned as well as any other glyphs that formed a context
 * for the operation.
 * </p>
 * <p>
 * There is just one exception: the &quot;next&quot; glyph in a sequence may be
 * one of those that formed a context for the operation just performed. For
 * example, in the case of pair positioning operations (i.e., kerning), if the
 * position value record for the second glyph is null, that glyph is treated as
 * the &quot;next&quot; glyph in the sequence.
 * </p>
 * <p>
 * This rest of this chapter describes the GPOS header and the subtables defined
 * for each LookupType. Several GPOS subtables share other tables: ValueRecords,
 * Anchor tables, and MarkArrays. For easy reference, the shared tables are
 * described at the end of this chapter.
 * </p>
 * <p>
 * <b>GPOS Header</b>
 * </p>
 * <p>
 * The GPOS table begins with a header that contains a version number (Version)
 * initially set to 1.0 (0x00010000) and offsets to three tables: ScriptList,
 * FeatureList, and LookupList.
 * </p>
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>Fixed</td>
 * <td>Version</td>
 * <td>Version of the GPOS table-initially = 0x00010000</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>ScriptList</td>
 * <td>Offset to ScriptList table-from beginning of GPOS table</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>FeatureList</td>
 * <td>Offset to FeatureList table-from beginning of GPOS table</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>LookupList</td>
 * <td>Offset to LookupList table-from beginning of GPOS table</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class OtfTableGPOS extends AbstractXtfTable
        implements
            XtfTable,
            LookupTableFactory,
            XMLWriterConvertible {

    /**
     * featurelist
     */
    private XtfFeatureList featureList;

    /**
     * lookuplist
     */
    private XtfLookupList lookupList;

    /**
     * scriptlist
     */
    private XtfScriptList scriptList;

    /**
     * Version.
     */
    private int version;

    /**
     * Create a new object.
     * 
     * @param tablemap the table map
     * @param de directory entry
     * @param rar input
     * @throws IOException if an IO-error occurs
     */
    OtfTableGPOS(XtfTableMap tablemap, XtfTableDirectory.Entry de,
            RandomAccessR rar) throws IOException {

        super(tablemap);
        rar.seek(de.getOffset());

        // GPOS Header
        version = rar.readInt();
        int scriptListOffset = rar.readUnsignedShort();
        int featureListOffset = rar.readUnsignedShort();
        int lookupListOffset = rar.readUnsignedShort();

        // Script List
        scriptList = new XtfScriptList(rar, de.getOffset() + scriptListOffset);

        // Feature List
        featureList =
                new XtfFeatureList(rar, de.getOffset() + featureListOffset);

        // Lookup List
        // lookupList =
        // new XtfLookupList(rar, de.getOffset() + lookupListOffset, this);

        // TODO mgn: error by reading the GPOS LinLibertine.ttf
    }

    // /**
    // * Returns the ScriptList with the tag name. If no scriptlist found,
    // * <code>null</code> is returned.
    // *
    // * @param tag The tag.
    // * @return Returns the ScriptList with the tag name.
    // */
    // public ScriptRecord getScriptRecord(String tag) {
    //
    // return scriptListMap.get(tag);
    // }

    /**
     * Getter for featureList.
     * 
     * @return the featureList
     */
    public XtfFeatureList getFeatureList() {

        return featureList;
    }

    /**
     * Getter for lookupList.
     * 
     * @return the lookupList
     */
    public XtfLookupList getLookupList() {

        return lookupList;
    }

    /**
     * Getter for scriptList.
     * 
     * @return the scriptList
     */
    public XtfScriptList getScriptList() {

        return scriptList;
    }

    // /**
    // * Read the future list table.
    // * <p>
    // * The headers of the GSUB and GPOS tables contain offsets to Feature List
    // * tables (FeatureList) that enumerate all the features in a font.
    // Features
    // * in a particular FeatureList are not limited to any single script. A
    // * FeatureList contains the entire list of either the GSUB or GPOS
    // features
    // * that are used to render the glyphs in all the scripts in the font.
    // <br/>
    // * The FeatureList table enumerates features in an array of records
    // * (FeatureRecord) and specifies the total number of features
    // * (FeatureCount). Every feature must have a FeatureRecord, which consists
    // * of a FeatureTag that identifies the feature and an offset to a Feature
    // * table (described next). The FeatureRecord array is arranged
    // * alphabetically by FeatureTag names. <br/> Note: The values stored in
    // the
    // * FeatureIndex array of a LangSys table are used to locate records in the
    // * FeatureRecord array of a FeatureList table.
    // * </p>
    // * <p>
    // * FeatureList table
    // * </p>
    // * <table border="1">
    // * <tr>
    // * <td><b>Type</b></td>
    // * <td><b>Name</b></td>
    // * <td><b>Description</b></td>
    // * </tr>
    // * <tr>
    // * <td>uint16</td>
    // * <td>FeatureCount</td>
    // * <td>Number of FeatureRecords in this table</td>
    // * </tr>
    // * <tr>
    // * <td>struct</td>
    // * <td>FeatureRecord[FeatureCount]</td>
    // * <td>Array of FeatureRecords-zero-based (first feature has FeatureIndex
    // =
    // * 0)-listed alphabetically by FeatureTag</td>
    // * </tr>
    // * </table>
    // * <p>
    // * </p>
    // * <p>
    // * FeatureRecord
    // * </p>
    // * <table border="1">
    // * <tr>
    // * <td><b>Type</b></td>
    // * <td><b>Name</b></td>
    // * <td><b>Description</b></td>
    // * </tr>
    // * <tr>
    // * <td>Tag</td>
    // * <td>FeatureTag</td>
    // * <td>4-byte feature identification tag</td>
    // * </tr>
    // * <tr>
    // * <td>Offset</td>
    // * <td>Feature</td>
    // * <td>Offset to Feature table-from beginning of FeatureList</td>
    // * </tr>
    // * </table>
    // * <p>
    // * </p>
    // *
    // * @param rar The input.
    // * @throws IOException if a io-error occurred.
    // */
    // private void readFeatureList(RandomAccessR rar) throws IOException {
    //
    // rar.seek(gposBaseOffset + featureListOffset);
    //
    // int featureCount = rar.readUnsignedShort();
    //
    // featureRecord = new FeatureRecord[featureCount];
    //
    // for (int i = 0; i < featureCount; i++) {
    // featureRecord[i] = new FeatureRecord(this, rar);
    // }
    //
    // // read the feature table
    // for (int i = 0; i < featureCount; i++) {
    // featureRecord[i].read(rar, gposBaseOffset + featureListOffset);
    // }
    //
    // }

    // /**
    // * Read the lookup list table.
    // * <p>
    // * The headers of the GSUB and GPOS tables contain offsets to Lookup List
    // * tables (LookupList) for glyph substitution (GSUB table) and glyph
    // * positioning (GPOS table). The LookupList table contains an array of
    // * offsets to Lookup tables (Lookup). The font developer defines the
    // Lookup
    // * sequence in the Lookup array to control the order in which a
    // * text-processing client applies lookup data to glyph substitution and
    // * positioning operations. LookupCount specifies the total number of
    // Lookup
    // * table offsets in the array.
    // * </p>
    // *
    // * <table border="1">
    // * <tr>
    // * <td><b>Type</b></td>
    // * <td><b>Name</b></td>
    // * <td><b>Description</b></td>
    // * </tr>
    // * <tr>
    // * <td>uint16</td>
    // * <td>LookupCount</td>
    // * <td>Number of lookups in this table</td>
    // * </tr>
    // * <tr>
    // * <td>Offset</td>
    // * <td>Lookup[LookupCount]</td>
    // * <td>Array of offsets to Lookup tables-from beginning of LookupList
    // -zero
    // * based (first lookup is Lookup index = 0)</td>
    // * </tr>
    // * </td>
    // * </table>
    // * <p>
    // * </p>
    // *
    // * @param rar The input.
    // * @throws IOException if a io-error occurred.
    // */
    // private void readLookupList(RandomAccessR rar) throws IOException {
    //
    // rar.seek(gposBaseOffset + lookupListOffset);
    //
    // int lookupCount = rar.readUnsignedShort();
    //
    // int[] lookupOffsetArray = new int[lookupCount];
    //
    // for (int i = 0; i < lookupCount; i++) {
    // lookupOffsetArray[i] = rar.readUnsignedShort();
    // }
    //
    // lookupTable = new LookupTable[lookupCount];
    // for (int i = 0; i < lookupCount; i++) {
    // lookupTable[i] =
    // new LookupTable(this, rar, gposBaseOffset
    // + lookupListOffset, lookupOffsetArray[i]);
    //
    // }
    //
    // }

    // /**
    // * Read the script list.
    // *
    // * <p>
    // * ScriptList table
    // * </p>
    // * <table border="1" >
    // * <tr>
    // * <td><b>Type</b></td>
    // * <td><b>Name</b></td>
    // * <td><b>Description</b></td>
    // * </tr>
    // * <tr>
    // * <td>uint16</td>
    // * <td>ScriptCount</td>
    // * <td>Number of ScriptRecords</td>
    // * </tr>
    // * <tr>
    // * <td>struct</td>
    // * <td>ScriptRecord<br>
    // * [ScriptCount]</td>
    // * <td>Array of ScriptRecords<br>
    // * -listed alphabetically by ScriptTag</td>
    // * </tr>
    // * </table> <br/>
    // * <p>
    // * ScriptRecord
    // * </p>
    // * <table border="1">
    // * <tr>
    // * <td><b>Type</b></td>
    // * <td><b>Name</b></td>
    // * <td><b>Description</b></td>
    // * </tr>
    // * <tr>
    // * <td>Tag</td>
    // * <td>ScriptTag</td>
    // * <td>4-byte ScriptTag identifier</td>
    // * </tr>
    // * <tr>
    // * <td>Offset</td>
    // * <td>Script</td>
    // * <td>Offset to Script table-from beginning of ScriptList</td>
    // * </tr>
    // * </table> <br/>
    // * <p>
    // * Script Table and Language System Record
    // * </p>
    // * <p>
    // * A Script table identifies each language system that defines how to use
    // * the glyphs in a script for a particular language. It also references a
    // * default language system that defines how to use the script's glyphs in
    // * the absence of language-specific knowledge.<br/> A Script table begins
    // * with an offset to the Default Language System table (DefaultLangSys),
    // * which defines the set of features that regulate the default behavior of
    // * the script. Next, Language System Count (LangSysCount) defines the
    // number
    // * of language systems (excluding the DefaultLangSys) that use the script.
    // * In addition, an array of Language System Records (LangSysRecord)
    // defines
    // * each language system (excluding the default) with an identification tag
    // * (LangSysTag) and an offset to a Language System table (LangSys). The
    // * LangSysRecord array stores the records alphabetically by
    // LangSysTag.<br/>
    // * If no language-specific script behavior is defined, the LangSysCount is
    // * set to zero (0), and no LangSysRecords are allocated. <br/>
    // * <p>
    // * ScriptList table
    // * </p>
    // * <table border="1">
    // * <tr>
    // * <td><b>Type</b></td>
    // * <td><b>Name</b></td>
    // * <td><b>Description</b></td>
    // * </tr>
    // * <tr>
    // * <td>uint16</td>
    // * <td>ScriptCount</td>
    // * <td>Number of ScriptRecords</td>
    // * </tr>
    // * <tr>
    // * <td>struct</td>
    // * <td>ScriptRecord<br>
    // * [ScriptCount]</td>
    // * <td>Array of ScriptRecords<br>
    // * -listed alphabetically by ScriptTag</td>
    // * </tr>
    // * </table>
    // *
    // * @param rar input
    // * @throws IOException if an IO-error occurs
    // */
    // private void readScriptList(RandomAccessR rar) throws IOException {
    //
    // rar.seek(gposBaseOffset + scriptListOffset);
    //
    // int striptCount = rar.readUnsignedShort();
    //
    // scriptRecord = new ScriptRecord[striptCount];
    // for (int i = 0; i < striptCount; i++) {
    // scriptRecord[i] = new ScriptRecord(this, rar);
    // }
    //
    // // read the script table
    // for (int i = 0; i < striptCount; i++) {
    // scriptRecord[i].readScriptTable(rar, gposBaseOffset
    // + scriptListOffset);
    // scriptListMap.put(scriptRecord[i].getTag().toString(),
    // scriptRecord[i]);
    // }
    //
    // }

    /**
     * @see org.extex.font.format.xtf.XtfTable#getShortcut()
     */
    public String getShortcut() {

        return "gpos";
    }

    /**
     * Get the table type, as a table directory value.
     * 
     * @return Returns the table type
     */
    public int getType() {

        return XtfReader.GPOS;
    }

    /**
     * Returns the version.
     * 
     * @return Returns the version.
     */
    public int getVersion() {

        return version;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.LookupTableFactory#lookupType(int)
     */
    public String lookupType(int type) {

        if (type >= 1 && type < XtfLookup.LOOKUP_TYPE_NAMES_GPOS.length - 1) {
            return XtfLookup.LOOKUP_TYPE_NAMES_GPOS[type - 1];
        }
        return "Unknown";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.LookupTableFactory#read(org.extex.util.file.random.RandomAccessR,
     *      int, int)
     */
    public XtfLookupTable read(RandomAccessR rar, int type, int offset)
            throws IOException {

        switch (type) {
            case XtfLookup.GPOS_1_SINGLE:
                return XtfGPOSSingleTable.newInstance(rar, offset);
            case XtfLookup.GPOS_2_PAIR:
                return XtfGPOSPairTable.newInstance(rar, offset);
            case XtfLookup.GPOS_3_CURSIVE_ATTACHMENT:
                return XtfGPOSCursiveTable.newInstance(rar, offset);
            case XtfLookup.GPOS_4_MARKTOBASE_ATTACHMENT:
                return XtfGPOSMarkToBaseTable.newInstance(rar, offset);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writeStartElement(writer);
        writer.writeAttribute("version", version);
        scriptList.writeXML(writer);
        featureList.writeXML(writer);
        //lookupList.writeXML(writer);
        writer.writeEndElement();
    }
}
