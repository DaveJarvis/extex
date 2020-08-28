/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.extex.font.format.xtf.XtfReader;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Class for a TTF/OTF TableDirectory.
 * 
 * <table border="1">
 * <thead>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * </thead>
 * <tr>
 * <td>Fixed</td>
 * <td>sfnt version</td>
 * <td>0x00010000 for version 1.0.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>numTables</td>
 * <td>Number of tables.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>searchRange</td>
 * <td>(Maximum power of 2 &#61603; numTables) x 16.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>entrySelector</td>
 * <td>2 (maximum power of 2 &#61603; numTables).</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>rangeShift</td>
 * <td>NumTables x 16-searchRange.</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfTableDirectory implements XMLWriterConvertible {

    /**
     * Class for a TTF TableDirectory-Entry.
     * 
     * <table border="1">
     * <thead>
     * <tr>
     * <td><b>Type</b></td>
     * <td><b>Name</b></td>
     * <td><b>Description</b></td>
     * </tr>
     * </thead>
     * <tr>
     * <td>ULONG</td>
     * <td>tag</td>
     * <td>4 -byte identifier.</td>
     * </tr>
     * <tr>
     * <td>ULONG</td>
     * <td>checkSum</td>
     * <td>CheckSum for this table.</td>
     * </tr>
     * <tr>
     * <td>ULONG</td>
     * <td>offset</td>
     * <td>Offset from beginning of TrueType font file.</td>
     * </tr>
     * <tr>
     * <td>ULONG</td>
     * <td>length</td>
     * <td>Length of this table.</td>
     * </tr>
     * </table>
     */
    public static class Entry implements XMLWriterConvertible {

        /**
         * tag (ULONG)
         */
        private int tag;

        /**
         * checkSum (ULONG)
         */
        private int checkSum;

        /**
         * offset (ULONG)
         */
        private int offset;

        /**
         * length (ULONG)
         */
        private int length;

        /**
         * shift 8
         */
        private static final int SHIFT8 = 8;

        /**
         * shift 16
         */
        private static final int SHIFT16 = 16;

        /**
         * shift 24
         */
        private static final int SHIFT24 = 24;

        /**
         * 0xff
         */
        private static final int FF = 0xff;

        /**
         * Create a new object.
         * 
         * @param rar input
         * @throws IOException if an error occurs
         */
        Entry(RandomAccessR rar) throws IOException {

            tag = rar.readInt();
            checkSum = rar.readInt();
            offset = rar.readInt();
            length = rar.readInt();
        }

        /**
         * Returns the checkSum.
         * 
         * @return Returns the checkSum.
         */
        public int getCheckSum() {

            return checkSum;
        }

        /**
         * Returns the length.
         * 
         * @return Returns the length.
         */
        public int getLength() {

            return length;
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
         * Returns the tag name.
         * 
         * @return Returns the tag name.
         */
        public String getTagName() {

            StringBuilder buf = new StringBuilder();
            buf.append((char) ((tag >> SHIFT24) & FF));
            buf.append((char) ((tag >> SHIFT16) & FF));
            buf.append((char) ((tag >> SHIFT8) & FF));
            buf.append((char) ((tag) & FF));
            return buf.toString();
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        @Override
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("entry");

            writer.writeAttribute("tag", getTagName());
            writer.writeAttribute("offset",
                XtfReader.convertIntToHexString(offset));
            writer.writeAttribute("length", String.valueOf(length));
            writer.writeAttribute("checksum",
                XtfReader.convertIntToHexString(checkSum));
            writer.writeEndElement();
        }
    }

    /**
     * Version (Fixed)
     */
    private int version;

    /**
     * Number of tables (USHORT)
     */
    private short numTables;

    /**
     * searchRange (USHORT)
     */
    private short searchRange;

    /**
     * entrySelector (USHORT)
     */
    private short entrySelector;

    /**
     * rangeShift (USHORT)
     */
    private short rangeShift;

    /**
     * entries
     */
    private Entry[] entries;

    /**
     * Create a new object.
     * 
     * @param rar input
     * @throws IOException if an error occurs
     */
    public XtfTableDirectory(RandomAccessR rar) throws IOException {

        // read TableDirectory
        version = rar.readInt();
        numTables = rar.readShort();
        searchRange = rar.readShort();
        entrySelector = rar.readShort();
        rangeShift = rar.readShort();
        entries = new Entry[numTables];

        // read TableDirectory entries
        for (int i = 0; i < numTables; i++) {
            entries[i] = new Entry(rar);
        }

        // sort the entries by offset
        Arrays.sort(entries, new Comparator<Object>() {

            @Override
            public int compare(Object arg0, Object arg1) {

                Entry e0 = (Entry) arg0;
                Entry e1 = (Entry) arg1;
                if (e0.getOffset() > e1.getOffset()) {
                    return 1;
                }
                return 0;
            }
        });

    }

    /**
     * Returns the entries.
     * 
     * @return Returns the entries.
     */
    public Entry[] getEntries() {

        return entries;
    }

    /**
     * Returns the DirectoryEntry at the index
     * 
     * @param index the index
     * @return Returns the DirectoryEntry at the index
     */
    public Entry getEntry(int index) {

        return entries[index];
    }

    /**
     * Returns the DirectoryEntry with a tag
     * 
     * @param tag the tag
     * @return Returns the DirectoryEntry with a tag
     */
    public Entry getEntryByTag(int tag) {

        for (int i = 0; i < numTables; i++) {
            if (entries[i].getTag() == tag) {
                return entries[i];
            }
        }
        return null;
    }

    /**
     * Returns the entrySelector.
     * 
     * @return Returns the entrySelector.
     */
    public short getEntrySelector() {

        return entrySelector;
    }

    /**
     * Returns the numTables.
     * 
     * @return Returns the numTables.
     */
    public short getNumTables() {

        return numTables;
    }

    /**
     * Returns the rangeShift.
     * 
     * @return Returns the rangeShift.
     */
    public short getRangeShift() {

        return rangeShift;
    }

    /**
     * Returns the searchRange.
     * 
     * @return Returns the searchRange.
     */
    public short getSearchRange() {

        return searchRange;
    }

    /**
     * @return Returns the version.
     */
    public int getVersion() {

        return version;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    @Override
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("tabledirectory");
        writer.writeAttribute("version",
            XtfReader.convertIntToHexString(version));
        writer.writeAttribute("numberoftables", String.valueOf(numTables));
        writer.writeAttribute("searchrange", String.valueOf(searchRange));
        writer.writeAttribute("entryselector", String.valueOf(entrySelector));
        writer.writeAttribute("rangeshift", String.valueOf(rangeShift));
        for (int i = 0; i < entries.length; i++) {
            entries[i].writeXML(writer);
        }
        writer.writeEndElement();
    }

}
