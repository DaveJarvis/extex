/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables.gps;

import org.extex.font.format.xtf.tables.tag.FeatureTag;
import org.extex.font.format.xtf.tables.tag.LanguageSystemTag;
import org.extex.font.format.xtf.tables.tag.ScriptTag;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ScriptList.
 * 
 * <p>
 * ScriptList table
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>ScriptCount</td>
 * <td>Number of ScriptRecords</td>
 * </tr>
 * <tr>
 * <td>struct</td>
 * <td>ScriptRecord<br>
 * [ScriptCount]</td>
 * <td>Array of ScriptRecords<br>
 * -listed alphabetically by ScriptTag</td>
 * </tr>
 * </table>
 * <br>
 * <p>
 * ScriptRecord
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>Tag</td>
 * <td>ScriptTag</td>
 * <td>4-byte ScriptTag identifier</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>Script</td>
 * <td>Offset to Script table-from beginning of ScriptList</td>
 * </tr>
 * </table>
 * <br>
 * <p>
 * Script Table and Language System Record
 * </p>
 * <p>
 * A Script table identifies each language system that defines how to use the
 * glyphs in a script for a particular language. It also references a default
 * language system that defines how to use the script's glyphs in the absence of
 * language-specific knowledge.<br>
 * A Script table begins with an offset to the Default Language System table
 * (DefaultLangSys), which defines the set of features that regulate the default
 * behavior of the script. Next, Language System Count (LangSysCount) defines
 * the number of language systems (excluding the DefaultLangSys) that use the
 * script. In addition, an array of Language System Records (LangSysRecord)
 * defines each language system (excluding the default) with an identification
 * tag (LangSysTag) and an offset to a Language System table (LangSys). The
 * LangSysRecord array stores the records alphabetically by LangSysTag.<br>
 * If no language-specific script behavior is defined, the LangSysCount is set
 * to zero (0), and no LangSysRecords are allocated. <br>
 * <p>
 * ScriptList table
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
* <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>ScriptCount</td>
 * <td>Number of ScriptRecords</td>
 * </tr>
 * <tr>
 * <td>struct</td>
 * <td>ScriptRecord<br>
 * [ScriptCount]</td>
 * <td>Array of ScriptRecords<br>
 * -listed alphabetically by ScriptTag</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class XtfScriptList implements XMLWriterConvertible {

    /**
     * The class for a langsys.
     */
    @SuppressWarnings("unused")
    public class LangSys implements XMLWriterConvertible {

        /**
         * feature count
         */
        private final int featureCount;

        /**
         * The list of the feature index.
         */
        private final List<Integer> featureIndexList;

        /**
         * The map of the feature index.
         */
        private final Map<Integer, Integer> featureIndexMap;

        /**
         * The map of the feature with the name.
         */
        private Map<String, Integer> featureTagMap = null;

        /**
         * The index. The value '-1' means the defaultLang.
         */
        private final int index;

        /**
         * lookup order
         */
        private final int lookupOrder;

        /**
         * reg feature index
         */
        private final int reqFeatureIndex;

        /**
         * Create a new object
         * 
         * @param rar input
         * @param index The index.
         * @throws IOException if an IO-error occurs
         */
        LangSys(RandomAccessR rar, int index) throws IOException {

            this.index = index;
            lookupOrder = rar.readUnsignedShort();
            reqFeatureIndex = rar.readUnsignedShort();
            featureCount = rar.readUnsignedShort();
            featureIndexList = new ArrayList<>( featureCount );
            featureIndexMap = new HashMap<>( featureCount );
            for (int i = 0; i < featureCount; i++) {
                int value = rar.readUnsignedShort();
                featureIndexList.add(value);
                featureIndexMap.put(i, value);
            }
        }

        /**
         * Returns the featureIndex.
         * 
         * @return Returns the featureIndex.
         */
        public int getFeatureCount() {

            return featureCount;
        }

        /**
         * Returns the featureIndex.
         *
         * @param idx TODO
         * @return Returns the featureIndex.
         */
        public int getFeatureIndex(int idx) {

            return featureIndexList.get(idx);
        }

        /**
         * Getter for featureIndexList.
         * 
         * @return the featureIndexList
         */
        public List<Integer> getFeatureIndexList() {

            return featureIndexList;
        }

        /**
         * Returns the index of the feature tag or -1, if not found.
         * 
         * @param featureName The name of the feature tag.
         * @return Returns the index of the feature tag or -1, if not found.
         */
        public int getFeatureTag(FeatureTag featureName) {

            if (featureTagMap == null) {
                featureTagMap = new HashMap<>();
                for (Integer ii : featureIndexList) {
                    String key = gsub.getFeatureTag(ii);
                    Integer value = featureIndexList.get(ii);
                    featureTagMap.put(key, value);
                }
            }
            Integer intval = featureTagMap.get(featureName.getTag());
            if (intval != null) {
                return intval;
            }
            return -1;
        }

        /**
         * Getter for index.
         * 
         * @return the index
         */
        public int getIndex() {

            return index;
        }

        /**
         * @return Returns the lookupOrder.
         */
        public int getLookupOrder() {

            return lookupOrder;
        }

        /**
         * @return Returns the reqFeatureIndex.
         */
        public int getReqFeatureIndex() {

            return reqFeatureIndex;
        }

        /**
         * Check, if is a feature index.
         * 
         * @param n the index
         * @return Returns {@code true}, if is a feature index, otherwise
         *         {@code false}
         */
        public boolean isFeatureIndexed(int n) {

            return featureIndexMap.get( n ) != null;
        }

        /**
         * Returns the info for this class
         * 
         * @return Returns the info for this class
         */
        @Override
        public String toString() {

            StringBuilder buf = new StringBuilder("LangSys\n");
            buf.append("   feature count : ").append(featureCount).append("\n");
            for (int i = 0, n = featureIndexList.size(); i < n; i++) {
                buf.append(i).append(" ").append(featureIndexList.get(i))
                    .append('\n');
            }
            return buf.toString();
        }

    @Override
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("langsys");
            writer.writeAttribute("lookuporder", lookupOrder);
            writer.writeAttribute("reqfeatureindex", reqFeatureIndex);
            writer.writeAttribute("featurecount", featureCount);

            for (int i = 0; i < featureCount; i++) {
                Integer fil = featureIndexList.get(i);
                writer.writeStartElement("featureindex");
                writer.writeAttribute("id", i);
                writer.writeAttribute("feature", fil.intValue());
                writer.writeAttribute("featuretag",
                    gsub.getFeatureTag( fil ));
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
    }

    /**
     * The class for a langsysrecord.
     */
    public static class LangSysRecord implements XMLWriterConvertible {

        /**
         * The index.
         */
        private final int index;

        /**
         * offset
         */
        private final int offset;

        /**
         * tag
         */
        private final int tag;

        /**
         * Create a new object
         * 
         * @param rar input
         * @param index The index.
         * @throws IOException if an IO-error occurs
         */
        LangSysRecord(RandomAccessR rar, int index) throws IOException {

            this.index = index;
            tag = rar.readInt();
            offset = rar.readUnsignedShort();
        }

        /**
         * Getter for index.
         * 
         * @return the index
         */
        public int getIndex() {

            return index;
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

            StringBuilder buf = new StringBuilder("LangSysRecord\n");
            buf.append("   index  : ").append(index).append("\n");
            buf.append("   tag    : ").append(tag).append("\n");
            buf.append("   tag    : ").append(tag2String(tag)).append("\n");
            buf.append("   offset : ").append(offset).append("\n");
            return buf.toString();
        }

    @Override
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("langsysrecord");
            writer.writeAttribute("index", index);
            writer.writeAttribute("tag", tag2String(tag));
            writer.writeEndElement();

        }

    }

    /**
     * The class for a Record.
     */
    public static class Record implements XMLWriterConvertible {

        /**
         * offset
         */
        private final int offset;

        /**
         * The record index.
         */
        private final int recIndex;

        /**
         * tag
         */
        private final int tag;

        /**
         * Create a new object
         * 
         * @param rar input
         * @param recIndex The record index.
         * @throws IOException if an IO-error occurs
         */
        Record(RandomAccessR rar, int recIndex) throws IOException {

            this.recIndex = recIndex;
            tag = rar.readInt();
            offset = rar.readUnsignedShort();
        }

        /**
         * Returns the offset
         * 
         * @return returns the offset
         */
        public int getOffset() {

            return offset;
        }

        /**
         * Getter for recIndex.
         * 
         * @return the recIndex
         */
        public int getRecIndex() {

            return recIndex;
        }

        /**
         * Returns the tag
         * 
         * @return Returns the tag
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

            StringBuilder buf = new StringBuilder("ScriptRecord : ");
            buf.append(recIndex).append("\n");
            buf.append("   tag    : ").append(tag).append("\n");
            buf.append("   tag    : ").append(tag2String(tag)).append("\n");
            buf.append("   offset : ").append(offset).append("\n");
            return buf.toString();
        }

    @Override
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("record");
            writer.writeAttribute("index", recIndex);
            writer.writeAttribute("tag", tag2String(tag));
            writer.writeAttribute("offset", offset);
            writer.writeEndElement();

        }

    }

    /**
     * Script
     */
    public class Script implements XMLWriterConvertible {

        /**
         * LangSys
         */
        private LangSys defaultLangSys;

        /**
         * defaultLangSysOffset
         */
        private final int defaultLangSysOffset;

        /**
         * langsys
         */
        private LangSys[] langSys;

        /**
         * LangSysCount
         */
        private final int langSysCount;

        /**
         * LangSysRecord
         */
        private LangSysRecord[] langSysRecords;

        /**
         * The index of the script.
         */
        private final int scriptIndex;

        /**
         * Create a new object
         * 
         * @param rar input
         * @param offset offset
         * @param scriptIndex The script index.
         * @throws IOException if an IO-error occurs
         */
        Script(RandomAccessR rar, int offset, int scriptIndex)
                throws IOException {

            this.scriptIndex = scriptIndex;
            rar.seek(offset);

            defaultLangSysOffset = rar.readUnsignedShort();
            langSysCount = rar.readUnsignedShort();
            if (langSysCount > 0) {
                langSysRecords = new LangSysRecord[langSysCount];
                for (int i = 0; i < langSysCount; i++) {
                    langSysRecords[i] = new LangSysRecord( rar, i );
                }
            }

            // Read the LangSys tables
            if (langSysCount > 0) {
                langSys = new LangSys[langSysCount];
                for (int i = 0; i < langSysCount; i++) {
                    rar.seek(offset + langSysRecords[i].getOffset());
                    langSys[i] = new LangSys(rar, i);
                }
            }
            if (defaultLangSysOffset > 0) {
                rar.seek(offset + defaultLangSysOffset);
                defaultLangSys = new LangSys(rar, -1);
            }
        }

        /**
         * Returns the default LangSys
         * 
         * @return Returns the default LangSys
         */
        public LangSys getDefaultLangSys() {

            return defaultLangSys;
        }

        /**
         * Returns the defaultLangSysOffset.
         * 
         * @return Returns the defaultLangSysOffset.
         */
        public int getDefaultLangSysOffset() {

            return defaultLangSysOffset;
        }

        /**
         * Returns the langSys.
         * 
         * @return Returns the langSys.
         */
        public LangSys[] getLangSys() {

            return langSys;
        }

        /**
         * Returns the LangSys for the language. If the language is not found,
         * the default LangSys is returned.
         * 
         * @param language The language.
         * @return Returns the LangSys for the language. If the language is not
         *         found, the default LangSys is returned.
         */
        public LangSys getLangSys(LanguageSystemTag language) {

            // TODO at the moment only DefaultLangsys
            return defaultLangSys;
        }

        /**
         * @return Returns the langSysCount.
         */
        public int getLangSysCount() {

            return langSysCount;
        }

        /**
         * Returns the langSysRecords.
         * 
         * @return Returns the langSysRecords.
         */
        public LangSysRecord[] getLangSysRecords() {

            return langSysRecords;
        }

        /**
         * Getter for scriptIndex.
         * 
         * @return the scriptIndex
         */
        public int getScriptIndex() {

            return scriptIndex;
        }

        /**
         * Returns the name of the tag.
         * 
         * @return Returns the name of the tag.
         */
        public String getTag() {

            return getRecordTag(scriptIndex);
        }

        /**
         * Returns the info for this class
         * 
         * @return Returns the info for this class
         */
        @Override
        public String toString() {

            StringBuilder buf = new StringBuilder("Script\n");
            buf.append("   langSyscount : ").append(langSysCount).append("\n");
            return buf.toString();
        }

    @Override
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("script");
            writer.writeAttribute("index", scriptIndex);
            writer.writeAttribute("langsyscount", langSysCount);
            writer.writeAttribute("tag", getTag());

            writer.writeStartElement("langsysrecord");
            for (int i = 0; i < langSysCount; i++) {
                LangSysRecord lsr = langSysRecords[i];
                lsr.writeXML(writer);
            }
            writer.writeEndElement();

            writer.writeStartElement("langsys");
            for (int i = 0; i < langSysCount; i++) {
                LangSys ls = langSys[i];
                ls.writeXML(writer);
            }
            writer.writeEndElement();

            if (defaultLangSys != null) {
                writer.writeStartElement("dafaultlangsys");
                defaultLangSys.writeXML(writer);
                writer.writeEndElement();
            }

            writer.writeEndElement();

        }
    }

    // /**
    // * Convert the tag string into a int value.
    // *
    // * @param tag The tag as string.
    // * @return Returns the int value.
    // */
    // public static int tag2Int(String tag) {

    // return ((tag.charAt(0) << 24) | (tag.charAt(1) << 16)
    // | (tag.charAt(2) << 8) | tag.charAt(3));

    // }

    /**
     * Convert the tag value to a string.
     * 
     * @param tag The tag as int value.
     * @return Return the tag string.
     */
    public static String tag2String(int tag) {

        return String.valueOf( (char) ((tag >> 24) & 0xff) ) +
            (char) ((tag >> 16) & 0xff) +
            (char) ((tag >> 8) & 0xff) + (char) ((tag) & 0xff);
    }

    /**
     * The gsub table.
     */
    private final AbstractXtfSFLTable gsub;

    /**
     * The list of the records.
     */
    private final List<Record> recordList;

    /**
     * The map for the records (to improve the search in the list).
     */
    private final Map<Integer, Record> recordMap;

    /**
     * script count
     */
    private int scriptCount = 0;

    /**
     * The list of the scripts.
     */
    private final List<Script> scriptList;

    /**
     * The map of the scripts (to improve the search in the list).
     */
    private final Map<String, Script> scriptMap;

    /**
     * Create a new object
     * 
     * @param rar input
     * @param offset offset
     * @param gsub The gsub table.
     * @throws IOException if an IO-error occurs
     */
    public XtfScriptList(RandomAccessR rar, int offset, AbstractXtfSFLTable gsub)
            throws IOException {

        this.gsub = gsub;
        rar.seek(offset);

        scriptCount = rar.readUnsignedShort();
        recordList = new ArrayList<>( scriptCount );
        recordMap = new HashMap<>( scriptCount );
        scriptList = new ArrayList<>( scriptCount );
        scriptMap = new HashMap<>( scriptCount );

        for (int i = 0; i < scriptCount; i++) {
            Record record = new Record( rar, i );
            recordList.add(record);
            recordMap.put(record.getRecIndex(), record);
        }
        for (int i = 0; i < scriptCount; i++) {
            Script script =
                    new Script(rar, offset + getRecord(i).getOffset(), i);
            scriptList.add(script);
            scriptMap.put(script.getTag(), script);
        }
    }

    /**
     * Find the LangSys in a script or {@code null}, if not found.
     * 
     * @param tag The Tag for the script.
     * @param language The language.
     * @return Find the LangSys in a script or {@code null}, if not found.
     */
    public LangSys findLangSys(ScriptTag tag, LanguageSystemTag language) {

        Script s = findScript(tag);
        if (s != null) {
            return s.getLangSys(language);
        }
        return null;
    }

    /**
     * Find a script.
     * <p>
     * If no one is found, the 'DFLT' script is returned or {@code null},
     * if the default script not exists.
     * </p>
     * 
     * @param tag The tag for the script
     * @return Returns the script.
     */
    public Script findScript(ScriptTag tag) {

        Script script = scriptMap.get(tag.getTag());
        if (script == null) {
            script = scriptMap.get(ScriptTag.getDefault().getTag());
        }
        return script;
    }

    /**
     * Returns the scriptcount
     * 
     * @return Returns the scriptcount
     */
    public int getCount() {

        return scriptCount;
    }

    /**
     * Returns the script record.
     * 
     * @param i the number
     * @return Returns the script record
     */
    public Record getRecord(int i) {

        return recordList.get(i);
    }

    /**
     * Returns the record tag name.
     * 
     * @param idx The index.
     * @return Returns the record tag name.
     */
    public String getRecordTag(int idx) {

        Record record = recordMap.get(idx);
        if (record != null) {
            return tag2String(record.getTag());
        }
        return "???";
    }

    /**
     * Returns the info for this class
     * 
     * @return Returns the info for this class
     */
    @Override
    public String toString() {

        StringBuilder buf = new StringBuilder("ScriptList\n");
        buf.append("   count : " + scriptCount + '\n');
        for (int i = 0; i < getCount(); i++) {
            buf.append(getRecord(i).toString());
        }
        for (int i = 0; i < getCount(); i++) {
            buf.append(scriptList.get(i).toString());
        }
        return buf.toString();
    }

    @Override
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("scriptlist");
        writer.writeAttribute("count", scriptCount);

        // writer.writeStartElement("recordlist");
        // for (int i = 0; i < scriptCount; i++) {
        // Record record = recordList.get(i);
        // record.writeXML(writer);
        // }
        // writer.writeEndElement();

        writer.writeStartElement("scriptlists");
        for (int i = 0; i < scriptCount; i++) {
            Script script = scriptList.get(i);
            script.writeXML(writer);
        }
        writer.writeEndElement();

        writer.writeEndElement();

    }

}
