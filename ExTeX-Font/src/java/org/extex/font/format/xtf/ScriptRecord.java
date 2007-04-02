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
 * Class for a script record.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5438 $
 */
public class ScriptRecord implements XMLWriterConvertible {

    /**
     * Offset to DefaultLangSys table-from beginning of Script table-may be
     * NULL.
     */
    private int defaultLangSysOffset;

    /**
     * The <code>LangSysRecord</code> array.
     */
    private LangSysRecord[] langSysRecord;

    /**
     * Offset to Script table-from beginning of ScriptList.
     */
    private int offset;

    /**
     * The tag: 4-byte ScriptTag identifier
     */
    private int[] tag = new int[4];

    /**
     * Creates a new object.
     * 
     * @param rar input
     * @throws IOException if a io-error occurred.
     */
    public ScriptRecord(RandomAccessR rar) throws IOException {

        for (int i = 0; i < 4; i++) {
            tag[i] = rar.readUnsignedByte();
        }

        offset = rar.readUnsignedShort();
    }

    /**
     * Getter for defaultLangSysOffset.
     * 
     * @return the defaultLangSysOffset
     */
    public int getDefaultLangSysOffset() {

        return defaultLangSysOffset;
    }

    /**
     * Getter for langSysRecord.
     * 
     * @return the langSysRecord
     */
    public LangSysRecord[] getLangSysRecord() {

        return langSysRecord;
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
    public int[] getTag() {

        return tag;
    }

    /**
     * Read the script table.
     * 
     * @param rar The input.
     * @param baseoffsetScriptList The offset.
     * @throws IOException if a io-error occurred.
     */
    public void readScriptTable(RandomAccessR rar, int baseoffsetScriptList)
            throws IOException {

        rar.seek(baseoffsetScriptList + offset);

        defaultLangSysOffset = rar.readUnsignedShort();
        int langSysCount = rar.readUnsignedShort();

        langSysRecord = new LangSysRecord[langSysCount];
        for (int i = 0; i < langSysCount; i++) {
            langSysRecord[i] = new LangSysRecord(rar);
        }

        // read language system table
        for (int i = 0; i < langSysCount; i++) {
            langSysRecord[i].read(rar, baseoffsetScriptList + offset);
        }

    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("record");
        int[] tag = getTag();
        for (int t = 0; t < tag.length; t++) {
            writer.writeAttribute("tag_" + t, tag[t]);
        }
        writer.writeAttribute("offset", getOffset(), 8);
        writer.writeAttribute("defaultLangSysOffset",
            getDefaultLangSysOffset(), 8);

        writer.writeStartElement("langsysrecord");
        LangSysRecord[] langsys = getLangSysRecord();
        for (int l = 0; l < langsys.length; l++) {
            langsys[l].writeXML(writer);
        }
        writer.writeEndElement();
        writer.writeEndElement();
    }
}
