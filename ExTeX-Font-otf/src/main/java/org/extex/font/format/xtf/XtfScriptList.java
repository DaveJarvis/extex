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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * ScriptList.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfScriptList implements XMLWriterConvertible {

    /**
     * The class for a langsys.
     */
    public static class LangSys implements XMLWriterConvertible {

        /**
         * feature count
         */
        private int featureCount;

        /**
         * The list of the feature index.
         */
        private List<Integer> featureIndexList;

        /**
         * The map of the feature index.
         */
        private Map<Integer, Integer> featureIndexMap;

        /**
         * The index. The value '-1' means the defaultLang.
         */
        private int index;

        /**
         * lookup order
         */
        private int lookupOrder;

        /**
         * reg feature index
         */
        private int reqFeatureIndex;

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
            featureIndexList = new ArrayList<Integer>(featureCount);
            featureIndexMap = new HashMap<Integer, Integer>(featureCount);
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
         * @return Returns the featureIndex.
         */
        public int getFeatureIndex(int idx) {

            return featureIndexList.get(idx);
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
         * @return Returns <code>true</code>, if is a feature index,
         *         otherwise <code>false</code>
         */
        public boolean isFeatureIndexed(int n) {

            return featureIndexMap.get(n) != null ? true : false;
        }

        /**
         * Returns the info for this class
         * 
         * @return Returns the info for this class
         */
        @Override
        public String toString() {

            StringBuffer buf = new StringBuffer("LangSys\n");
            buf.append("   feature count : ").append(featureCount).append("\n");
            for (int i = 0, n = featureIndexList.size(); i < n; i++) {
                buf.append(i).append(" ").append(featureIndexList.get(i))
                    .append("\n");
            }
            return buf.toString();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("langsys");
            writer.writeAttribute("lookuporder", lookupOrder);
            writer.writeAttribute("reqfeatureindex", reqFeatureIndex);
            writer.writeAttribute("featurecount", featureCount);

            for (int i = 0; i < featureCount; i++) {
                Integer fil = featureIndexList.get(i);
                writer.writeStartElement("featureindex");
                writer.writeAttribute("id", i);
                writer.writeAttribute("value", fil.intValue());
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
    }

    /**
     * The class for a langsysrecord.
     */
    public class LangSysRecord implements XMLWriterConvertible {

        /**
         * The index.
         */
        private int index;

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

            StringBuffer buf = new StringBuffer("LangSysRecord\n");
            buf.append("   index  : ").append(index).append("\n");
            buf.append("   tag    : ").append(tag).append("\n");
            buf.append("   tag    : ").append(tag2String(tag)).append("\n");
            buf.append("   offset : ").append(offset).append("\n");
            return buf.toString();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
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
    public class Record implements XMLWriterConvertible {

        /**
         * offset
         */
        private int offset;

        /**
         * The record index.
         */
        private int recIndex;

        /**
         * tag
         */
        private int tag;

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

            StringBuffer buf = new StringBuffer("ScriptRecord : ");
            buf.append(recIndex).append("\n");
            buf.append("   tag    : ").append(tag).append("\n");
            buf.append("   tag    : ").append(tag2String(tag)).append("\n");
            buf.append("   offset : ").append(offset).append("\n");
            return buf.toString();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
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
        private int defaultLangSysOffset;

        /**
         * langsys
         */
        private LangSys[] langSys;

        /**
         * LangSysCount
         */
        private int langSysCount;

        /**
         * LangSysRecord
         */
        private LangSysRecord[] langSysRecords;

        /**
         * The index of the script.
         */
        private int scriptIndex;

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
                    langSysRecords[i] = new LangSysRecord(rar, i);
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
         * Returns the info for this class
         * 
         * @return Returns the info for this class
         */
        @Override
        public String toString() {

            StringBuffer buf = new StringBuffer("Script\n");
            buf.append("   langSyscount : ").append(langSysCount).append("\n");
            return buf.toString();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("script");
            writer.writeAttribute("index", scriptIndex);
            writer.writeAttribute("langsyscount", langSysCount);

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

    /**
     * Convert the tag string into a int value.
     * 
     * @param tag The tag as string.
     * @return Returns the int value.
     */
    public static int tag2Int(String tag) {

        return ((tag.charAt(0) << 24) | (tag.charAt(1) << 16)
                | (tag.charAt(2) << 8) | tag.charAt(3));

    }

    /**
     * Convert the tag value to a string.
     * 
     * @param tag The tag as int value.
     * @return Return the tag string.
     */
    public static String tag2String(int tag) {

        return new StringBuffer().append((char) ((tag >> 24) & 0xff)).append(
            (char) ((tag >> 16) & 0xff)).append((char) ((tag >> 8) & 0xff))
            .append((char) ((tag) & 0xff)).toString();
    }

    /**
     * The list of the records.
     */
    private List<Record> recordList;

    /**
     * The map for the records (to improve the search in the list).
     */
    private Map<Integer, Record> recordMap;

    /**
     * script count
     */
    private int scriptCount = 0;

    /**
     * The list of the scripts.
     */
    private List<Script> scriptList;

    /**
     * Create a new object
     * 
     * @param rar input
     * @param offset offset
     * @throws IOException if an IO-error occurs
     */
    XtfScriptList(RandomAccessR rar, int offset) throws IOException {

        rar.seek(offset);

        scriptCount = rar.readUnsignedShort();
        recordList = new ArrayList<Record>(scriptCount);
        recordMap = new HashMap<Integer, Record>(scriptCount);
        scriptList = new ArrayList<Script>(scriptCount);

        for (int i = 0; i < scriptCount; i++) {
            Record record = new Record(rar, i);
            recordList.add(record);
            recordMap.put(record.getRecIndex(), record);
        }
        for (int i = 0; i < scriptCount; i++) {
            scriptList
                .add(new Script(rar, offset + getRecord(i).getOffset(), i));
        }
    }

    /**
     * Find a script. If no one is found, <code>null</code> is returned.
     * 
     * @param tag the tag for the script
     * @return Returns the script.
     */
    public Script findScript(String tag) {

        if (tag.length() != 4) {
            return null;
        }
        int tagVal = tag2Int(tag);
        Record recpos = recordMap.get(tagVal);
        if (recpos == null) {
            return null;
        }
        return scriptList.get(recpos.getRecIndex());
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
     * Returns the info for this class
     * 
     * @return Returns the info for this class
     */
    @Override
    public String toString() {

        StringBuffer buf = new StringBuffer();
        buf.append("ScriptList\n");
        buf.append("   count : " + String.valueOf(scriptCount) + '\n');
        for (int i = 0; i < getCount(); i++) {
            buf.append(getRecord(i).toString());
        }
        for (int i = 0; i < getCount(); i++) {
            buf.append(scriptList.get(i).toString());
        }
        return buf.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("scriptlist");

        writer.writeStartElement("recordlist");
        for (int i = 0; i < scriptCount; i++) {
            Record record = recordList.get(i);
            record.writeXML(writer);
        }
        writer.writeEndElement();

        writer.writeStartElement("scriptlists");
        for (int i = 0; i < scriptCount; i++) {
            Script script = scriptList.get(i);
            script.writeXML(writer);
        }
        writer.writeEndElement();

        writer.writeEndElement();

    }
}
