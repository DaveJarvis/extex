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

import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Class for a script record.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
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
     * The ScriptTag.
     */
    private ScriptTag tag;

    /**
     * The default Langsys.
     */
    private LangSysRecord defaultLangSys;

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
    public ScriptRecord(OtfTableGPOS tableGPOS, RandomAccessR rar)
            throws IOException {

        gpos = tableGPOS;
        tag = new ScriptTag(rar);
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
    public ScriptTag getTag() {

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
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("record");
        writer.writeAttribute("tag", tag);
        writer.writeAttribute("tagname", tag.getName());
        writer.writeAttribute("offset", getOffset(), 8);
        writer.writeAttribute("defaultLangSysOffset",
            getDefaultLangSysOffset(), 8);

        writer.writeStartElement("defaultlangsysrecord");
        if (defaultLangSys != null) {
            defaultLangSys.writeXML(writer);
        }
        writer.writeEndElement();

        writer.writeStartElement("langsysrecord");
        writer.writeAttribute("count", langSysRecord.length);
        for (int l = 0; l < langSysRecord.length; l++) {
            langSysRecord[l].writeXML(writer);
        }
        writer.writeEndElement();
        writer.writeEndElement();
    }
}
