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
import java.util.Map;

import org.extex.font.format.xtf.cff.T2CharString;
import org.extex.font.format.xtf.cff.T2Operator;
import org.extex.font.format.xtf.cff.T2StandardStrings;
import org.extex.font.format.xtf.cff.T2TDOCharset;
import org.extex.util.XMLWriterConvertible;
import org.extex.util.file.random.RandomAccessInputArray;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * The 'CFF' - PostScript font program.
 *
 * <p>
 * This table contains a compact representation of a PostScript Type 1,
 * or CIDFont and is structured according to
 * <a href="http://partners.adobe.com/asn/developer/pdfs/tn/5176.CFF.pdf">
 * Adobe Technical Note #5176: " The Compact BaseFont Format Specification"</a>
 * and
 * <a href="http://partners.adobe.com/asn/developer/pdfs/tn/5177.Type2.pdf">
 * Adobe Technical Note #5177: "Type 2 Charstring Format"</a>.
 * </p>
 *
 * <p>CFF Data Types</p>
 * <table border="1">
 *   <thead>
 *     <tr><td>Name</td><td>Range</td><td>Description</td></tr>
 *   </thead>
 *   <tr><td>Card8</td><td>0   255</td><td>
 *      1-byte unsigned number</td></tr>
 *   <tr><td>Card16</td><td>0   65535</td><td>
 *      2-byte unsigned number</td></tr>
 *   <tr><td>Offset</td><td>varies</td><td>
 *      1, 2, 3, or 4 byte offset (specified by OffSize field)</td></tr>
 *   <tr><td>OffSize</td><td>1 - 4</td><td>
 *      1-byte unsigned number specifies the size of an Offset
 *      field or fields</td></tr>
 *   <tr><td>SID</td><td>0 - 64999</td><td>
 *      2-byte string identifier</td></tr>
 * </table>
 *
 * <p>CFF Data Layout</p>
 *
 * <table border="1">
 *   <thead>
 *     <tr><td><b>Entry</b></td><td><b>Comments</b></td></tr>
 *   </thead>
 *   <tr><td>Header</td><td>-</td></tr>
 *   <tr><td>Name INDEX</td><td>-</td></tr>
 *   <tr><td>Top DICT INDEX</td><td>-</td></tr>
 *   <tr><td>String INDEX</td><td>->/td></tr>
 *   <tr><td>Global Subr INDEX</td><td>-</td></tr>
 *   <tr><td>Encodings</td><td>-</td></tr>
 *   <tr><td>Charsets</td><td>-</td></tr>
 *   <tr><td>FDSelect</td><td>CIDFonts only</td></tr>
 *   <tr><td>CharStrings INDEX</td><td>per-font</td></tr>
 *   <tr><td>BaseFont DICT INDEX</td><td>per-font, CIDFonts only</td></tr>
 *   <tr><td>Private DICT</td><td>per-font</td></tr>
 *   <tr><td>Local Subr INDEX</td><td>per-font or per-Private
 *           DICT for CIDFonts</td></tr>
 *   <tr><td>Copyright and Trademark Notices</td><td>-/td></tr>
 * </table>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class OtfTableCFF extends AbstractXtfTable
        implements
            XtfTable,
            XMLWriterConvertible {

    /**
     * INDEX-Table.
     * <p>
     * An INDEX is an array of variable-sized objects.
     * It comprises a header, an offset array, and object data.
     * The offset array specifies offsets within the object data.
     * An object is retrieved by indexing the offset array and fetching
     * the object at the specified offset. The object's length can
     * be determined by subtracting its offset from the next offset
     * in the offset array. An additional offset is added at the end
     * of the offset array so the length of the last object may be determined.
     * </p>
     *
     * <table border="1">
     *   <thead>
     *     <tr><td><b>Type</b></td><td><b>Name</b></td><td><b>
     *      Description</b></td></tr>
     *   </thead>
     *   <tr><td>Card16</td><td>count</td><td>
     *      Number of objects stored in INDEX.
     *      An empty INDEX is represented by a count
     *      field with a 0 value and no additional fields.
     *      Thus, the total size of an empty INDEX is 2 bytes.</td></tr>
     *   <tr><td>OffSize</td><td>offSize</td><td>
     *      Offset array element size</td></tr>
     *   <tr><td>Offset</td><td>offset [count+1]</td><td>
     *       Offset array (from byte preceding object data).
     *       Offsets in the offset array are relative to the
     *       byte that precedes the object data. Therefore
     *       the first element of the offset array is always 1.
     *       (This ensures that every object has a corresponding
     *       offset which is always nonzero and permits the efficient
     *       implementation of dynamic object loading.)
     * </td></tr>
     *   <tr><td>Card8</td><td>data [&lt;varies&gt;]</td><td>
     *       Object data</td></tr>
     * </table>
     *
     * Offsets in the offset array are relative to the byte that
     * precedes the object data. Therefore the first element of the
     * offset array is always 1. (This ensures that every object
     * has a corresponding offset which is always nonzero and
     * permits the efficient implementation of dynamic object loading.)
     *
     * An empty INDEX is represented by a count field with a 0 value
     * and no additional fields. Thus, the total size of an empty
     * INDEX is 2 bytes.
     */
    abstract class Index implements XMLWriterConvertible {

        /**
         * Number of objects
         */
        private int count;

        /**
         * all datas
         */
        private Object[] datas;

        /**
         * Create a new object.
         *
         * @param offset        the offset
         * @param rar           the input
         * @throws IOException if an IO-error occurs
         */
        Index(final int offset, final RandomAccessR rar) throws IOException {

            // go to the offset.
            // is the offset less than zero,
            // the input (pointer) is on the correct offset
            if (offset > 0) {
                rar.seek(offset);
            }

            count = rar.readUnsignedShort();
            if (count > 0) {
                int ioffSize = rar.readUnsignedByte();
                datas = new Object[count];
                int[] offsetarray = new int[count + 1];

                // read all offsets
                for (int offs = 0; offs < offsetarray.length; offs++) {
                    offsetarray[offs] = readOffset(ioffSize, rar);
                    //                    System.out.println("offset [" + offs + "]="
                    //                            + offsetarray[offs]);
                }

                // get data
                for (int i = 0; i < count; i++) {
                    datas[i] = readData(offsetarray[i], offsetarray[i + 1], rar);
                }
            }

        }

        /**
         * Returns the count.
         * @return Returns the count.
         */
        public int getCount() {

            return count;
        }

        /**
         * Returns the datas.
         * @return Returns the datas.
         */
        public Object[] getDatas() {

            return datas;
        }

        /**
         * Read the data
         * @param start the start offset
         * @param end   the end offset
         * @param rar   the input
         * @return Returns the data
         * @throws IOException if an IO-error occurs
         */
        private byte[] readData(final int start, final int end,
                final RandomAccessR rar) throws IOException {

            byte[] data = new byte[end - start];
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) rar.readUnsignedByte();
            }
            return data;
        }

        /**
         * Read the offset (see offsetsize)
         * @param os        the offsetsize
         * @param rar       the input
         * @return Returns the offset
         * @throws IOException if an IO-error occurs
         */
        private int readOffset(final int os, final RandomAccessR rar)
                throws IOException {

            int offset = 0;
            for (int i = 0; i < os; i++) {
                int b = rar.readUnsignedByte();
                offset = offset << XtfConstants.SHIFT8;
                offset += b;
            }
            return offset;
        }
    }

    /**
     * This contains the PostScript language names
     * (FontName or CIDFontName) of all the fonts
     * in the FontSet stored in an INDEX structure.
     */
    class NameIndex extends Index {

        /**
         * Create a new object.
         *
         * @param offset    the offset
         * @param rar       the input
         * @throws IOException if an IO-error occurs.
         */
        public NameIndex(final int offset, final RandomAccessR rar)
                throws IOException {

            super(offset, rar);
        }

        /**
         * Return the name as string
         * @param index     the index for the name
         * @return Returns the name as string
         */
        public String getName(final int index) {

            if (index < 0 || index > getCount()) {
                return null;
            }
            byte[] data = (byte[]) getDatas()[index];
            StringBuffer buf = new StringBuffer(data.length);

            for (int i = 0; i < data.length; i++) {
                buf.append((char) data[i]);
            }
            return buf.toString();
        }

        /**
         * @see org.extex.util.XMLWriterConvertible#writeXML(
         *      org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(final XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("nameindex");
            for (int i = 0; i < getDatas().length; i++) {
                writer.writeStartElement("name");
                writer.writeAttribute("id", i);
                writer.writeAttribute("value", getName(i));
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
    }

    /**
     * String INDEX
     *
     * <p>
     * All the strings, with the exception of the FontName
     * and CIDFontName strings which appear in the Name INDEX,
     * used by different fonts within the FontSet are collected
     * together into an INDEX structure and are referenced by
     * a 2-byte unsigned number called a string identifier or SID.
     * Only unique strings are stored in the table thereby removing
     * duplication across fonts. Further space saving is obtained by
     * allocating commonly occurring strings to predefined SIDs.
     * These strings, known as the standard strings, describe all
     * the names used in the ISOAdobe and Expert character sets along
     * with a few other strings common to Type 1 fonts.
     * </p>
     * <p>
     * The client program will contain an array of standard strings
     * with nStdStrings elements. Thus, the standard strings take
     * SIDs in the range 0 to (nStdStrings  1). The first string
     * in the String INDEX corresponds to the SID whose value is
     * equal to nStdStrings, the first non-standard string, and so on.
     * When the client needs to determine the string that corresponds
     * to a particular SID it performs the following: test if SID
     * is in standard range then fetch from internal table, otherwise,
     * fetch string from the String INDEX using a value of
     * (SID  nStdStrings) as the index. An SID is defined as a 2-byte
     * unsigned number but only takes values in the range 0 64999,
     * inclusive. SID values 65000 and above are available for
     * implementation use. A FontSet with zero non-standard strings
     * is represented by an empty INDEX.
     * </p>
     */
    class StringIndex extends Index {

        /**
         * The values.
         */
        private String[] values;

        /**
         * Create a new object.
         *
         * @param offset    the offset
         * @param rar       the input
         * @throws IOException if an IO-error occurs.
         */
        public StringIndex(final int offset, final RandomAccessR rar)
                throws IOException {

            super(offset, rar);

            values = new String[getDatas().length];

            for (int i = 0; i < getDatas().length; i++) {
                values[i] = convert((byte[]) getDatas()[i]);
            }
        }

        /**
         * Convert the array to a string.
         * @param data  the data-array
         * @return Returns the String.
         */
        private String convert(final byte[] data) {

            StringBuffer buf = new StringBuffer(data.length);

            for (int i = 0; i < data.length; i++) {
                buf.append((char) data[i]);
            }

            return buf.toString();
        }

        /**
         * Returns the String for the SID.
         *
         * First, test if SID is in standard range
         * then fetch from internal table, otherwise,
         * fetch string from the String INDEX using a value of (SID-nStdStrings)
         * as the index.
         *
         * @param sid   the SID for the string.
         * @return Returns the String or <code>null</code>, if not found.
         */
        public String getString(final int sid) {

            int highestSID = T2StandardStrings.getHighestSID();
            if (sid <= highestSID) {
                return T2StandardStrings.getString(sid);
            }
            if (sid - highestSID - 1 < values.length) {
                return values[sid - highestSID - 1];
            }
            return null;
        }

        /**
         * Returns the values.
         * @return Returns the values.
         */
        public String[] getValues() {

            return values;
        }

        /**
         * @see org.extex.util.XMLWriterConvertible#writeXML(
         *      org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(final XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("stringindex");

            int highestSID = T2StandardStrings.getHighestSID();
            for (int i = 0; i < highestSID + values.length; i++) {
                writer.writeStartElement("string");
                writer.writeAttribute("sid", i);
                writer.writeAttribute("value", getString(i));
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
    }

    /**
     * This contains the top-level DICTs of all the fonts
     * in the FontSet stored in an INDEX structure.
     * Top DICT Operator Entries
     * Name     Value      Operand(s)         Default, notes
     */
    class TopDictIndex extends Index {

        /**
         * values
         */
        private T2Operator[] values;

        /**
         * The hash map.
         */
        private Map hashValues;

        /**
         * Create a new object.
         *
         * @param offset    the offset
         * @param rar       the input
         * @throws IOException if an IO-error occurs.
         */
        public TopDictIndex(final int offset, final RandomAccessR rar)
                throws IOException {

            super(offset, rar);

            hashValues = new HashMap();

            if (getDatas().length > 0) {
                byte[] data = (byte[]) getDatas()[0];

                RandomAccessInputArray arar = new RandomAccessInputArray(data);

                ArrayList list = new ArrayList();
                try {
                    // read until end of input -> IOException
                    while (true) {
                        T2Operator op = T2CharString.readTopDICTOperator(arar,
                                cff);
                        list.add(op);
                        hashValues.put(op.getName().toLowerCase(), op);
                    }
                } catch (IOException e) {
                    values = new T2Operator[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        values[i] = (T2Operator) list.get(i);
                    }
                }
            }
        }

        /**
         * Returns the operator for the key.
         * 
         * @param key   The key (lowercase).
         * @return Returns the operator for the key
         *         or <code>null</code>, if not found.
         */
        public T2Operator get(String key) {

            return (T2Operator) hashValues.get(key.toLowerCase());
        }

        /**
         * Returns the values.
         * @return Returns the values.
         */
        public T2Operator[] getValues() {

            return values;
        }

        /**
         * @see org.extex.util.XMLWriterConvertible#writeXML(
         *      org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(final XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("topdictindex");
            for (int i = 0; i < values.length; i++) {
                values[i].writeXML(writer);
            }
            writer.writeEndElement();
        }

    }

    /**
     * The instance itself.
     */
    private OtfTableCFF cff;

    /**
     * header size
     */
    private int hdrSize;

    /**
     * The table NameINDEX
     */
    private NameIndex nameindex;

    /**
     * offset size
     */
    private int offSize;

    /**
     * The table StringINDEX
     */
    private StringIndex stringindex;

    /**
     * The table top DICT INDEX
     */
    private TopDictIndex topdictindex;

    /**
     * Version major
     */
    private int versionmajor;

    /**
     * Version minor
     */
    private int versionminor;

    /**
     * Create a new object
     *
     * @param tablemap  the tablemap
     * @param de        entry
     * @param rar       input
     * @throws IOException if an IO-error occurs
     */
    OtfTableCFF(final XtfTableMap tablemap, final XtfTableDirectory.Entry de,
            final RandomAccessR rar) throws IOException {

        super(tablemap);
        cff = this;
        rar.seek(de.getOffset());

        // header
        readHeader(rar);

        // index
        readNameIndex(de, rar);
        readTopDictIndex(rar);
        readStringIndex(rar);

        // read special index entries
        readCharset(rar);

        // Global Subr INDEX

        // incomplete
    }

    /**
     * Read the charset entry.
     * Charset data is located via the offset operand to the
     * charset operator in the Top DICT. Each charset
     * is described by a format-type identifier byte followed
     * by format-specific data.
     *
     * @param rar the input
     */
    private void readCharset(RandomAccessR rar) {

        T2Operator charset = getTopDictIndex("charset");
        if (charset != null) {
            int offset = ((T2TDOCharset)charset).getInteger();
            // TODO mgn hiuer aufgehört
        }
        
    }

    /**
     * Returns the hdrSize.
     * @return Returns the hdrSize.
     */
    public int getHdrSize() {

        return hdrSize;
    }

    /**
     * Returns the nameindex.
     * @return Returns the nameindex.
     */
    public NameIndex getNameindex() {

        return nameindex;
    }

    /**
     * @see org.extex.font.format.xtf.XtfTable#getShortcut()
     */
    public String getShortcut() {

        return "cff";
    }

    /**
     * @see org.extex.font.format.xtf.OtfTableCFF.StringIndex#getString(int)
     */
    public String getString(final int sid) {

        return stringindex.getString(sid);
    }

    /**
     * Get the table type, as a table directory value.
     * @return Returns the table type
     */
    public int getType() {

        return XtfReader.CFF;
    }

    /**
     * Returns the versionmajor.
     * @return Returns the versionmajor.
     */
    public int getVersionmajor() {

        return versionmajor;
    }

    /**
     * Returns the versionminor.
     * @return Returns the versionminor.
     */
    public int getVersionminor() {

        return versionminor;
    }

    // ---------------------------------------------------
    // ---------------------------------------------------
    // ---------------------------------------------------
    // ---------------------------------------------------
    // ---------------------------------------------------

    /**
     * Read the Header. TODO: create html-table
     * Type    Name     Description
     * Card8   major    Format major version (starting at 1)
     * Card8   minor    Format minor version (starting at 0)
     * Card8   hdrSize  Header size (bytes)
     * OffSize offSize  Absolute offset (0) size
     *
     * @param rar  The Input.
     * @throws IOException if an IO-error occurs
     */
    private void readHeader(final RandomAccessR rar) throws IOException {

        versionmajor = rar.readUnsignedByte();
        versionminor = rar.readUnsignedByte();
        hdrSize = rar.readUnsignedByte();
        offSize = rar.readUnsignedByte();
    }

    /**
     * Read the name index.
     *
     * This contains the PostScript language names (FontName or CIDFontName)
     * of all the fonts in the FontSet stored in an INDEX structure.
     * The font names are sorted, thereby permitting a
     * binary search to be performed when locating a specific font
     * within a FontSet. The sort order is based on character codes
     * treated as 8-bit unsigned integers. A given font name precedes
     * another font name having the first name as its prefix.
     * There must be at least one entry in this INDEX,
     * i.e. the FontSet must contain at least one font.
     *
     * @param de    The entry.
     * @param rar   The Input.
     * @throws IOException if an io-error occuured.
     */
    private void readNameIndex(final XtfTableDirectory.Entry de,
            final RandomAccessR rar) throws IOException {

        nameindex = new NameIndex(de.getOffset() + hdrSize, rar);
    }

    /**
     * Read the string index.
     *
     *
     * @param rar   The input.
     * @throws IOException if an IO-error occurred.
     */
    private void readStringIndex(final RandomAccessR rar) throws IOException {

        stringindex = new StringIndex(-1, rar);
    }

    /**
     * Read the top dict index.
     *
     * @param rar   The input.
     * @throws IOException if an IO-error occurred.
     */
    private void readTopDictIndex(final RandomAccessR rar) throws IOException {

        topdictindex = new TopDictIndex(-1, rar);
    }

    /**
     * print the array (only for test)
     * @param data  the array
     */
    private void tmp_print(final byte[] data) {

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < data.length; i++) {
            System.out.print("0x" + Integer.toHexString(data[i]) + " ");
            System.out.print("[");
            char c = (char) data[i];
            buf.append(c);
            if (Character.isLetterOrDigit(c)) {
                System.out.print(c);
            }
            System.out.print("]  ");
        }
        System.out.println();
        System.out.println(buf.toString());
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(final XMLStreamWriter writer) throws IOException {

        writeStartElement(writer);
        writer.writeAttribute("version", String.valueOf(versionmajor) + "."
                + String.valueOf(versionminor));
        writer.writeAttribute("hdrsize", String.valueOf(hdrSize));
        writer.writeAttribute("offsize", String.valueOf(offSize));
        if (nameindex != null) {
            nameindex.writeXML(writer);
        }
        if (topdictindex != null) {
            topdictindex.writeXML(writer);
        }
        if (stringindex != null) {
            stringindex.writeXML(writer);
        }
        writer.writeComment("incomplete");
        writer.writeEndElement();
    }

    /**
     * @see org.extex.font.format.xtf.OtfTableCFF.TopDictIndex#get(java.lang.String)
     */
    public T2Operator getTopDictIndex(String key) {

        return topdictindex.get(key);
    }

}