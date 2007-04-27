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

import org.extex.font.format.xtf.cff.CffFont;
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
 * This table contains a compact representation of a PostScript Type 1, or
 * CIDFont and is structured according to <a
 * href="http://partners.adobe.com/asn/developer/pdfs/tn/5176.CFF.pdf"> Adobe
 * Technical Note #5176: " The Compact BaseFont Format Specification"</a> and
 * <a href="http://partners.adobe.com/asn/developer/pdfs/tn/5177.Type2.pdf">
 * Adobe Technical Note #5177: "Type 2 Charstring Format"</a>.
 * </p>
 * 
 * <p>
 * CFF Data Types
 * </p>
 * <table border="1"> <thead>
 * <tr>
 * <td>Name</td>
 * <td>Range</td>
 * <td>Description</td>
 * </tr>
 * </thead>
 * <tr>
 * <td>Card8</td>
 * <td>0 255</td>
 * <td> 1-byte unsigned number</td>
 * </tr>
 * <tr>
 * <td>Card16</td>
 * <td>0 65535</td>
 * <td> 2-byte unsigned number</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>varies</td>
 * <td> 1, 2, 3, or 4 byte offset (specified by OffSize field)</td>
 * </tr>
 * <tr>
 * <td>OffSize</td>
 * <td>1 - 4</td>
 * <td> 1-byte unsigned number specifies the size of an Offset field or fields</td>
 * </tr>
 * <tr>
 * <td>SID</td>
 * <td>0 - 64999</td>
 * <td> 2-byte string identifier</td>
 * </tr>
 * </table>
 * 
 * <p>
 * CFF Data Layout
 * </p>
 * 
 * <table border="1"> <thead>
 * <tr>
 * <td><b>Entry</b></td>
 * <td><b>Comments</b></td>
 * </tr>
 * </thead>
 * <tr>
 * <td>Header</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>Name INDEX</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>Top DICT INDEX</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>String INDEX</td>
 * <td>->/td></tr>
 * <tr>
 * <td>Global Subr INDEX</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>Encodings</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>Charsets</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>FDSelect</td>
 * <td>CIDFonts only</td>
 * </tr>
 * <tr>
 * <td>CharStrings INDEX</td>
 * <td>per-font</td>
 * </tr>
 * <tr>
 * <td>BaseFont DICT INDEX</td>
 * <td>per-font, CIDFonts only</td>
 * </tr>
 * <tr>
 * <td>Private DICT</td>
 * <td>per-font</td>
 * </tr>
 * <tr>
 * <td>Local Subr INDEX</td>
 * <td>per-font or per-Private DICT for CIDFonts</td>
 * </tr>
 * <tr>
 * <td>Copyright and Trademark Notices</td>
 * <td>-/td></tr>
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
     * The instance itself.
     */
    private OtfTableCFF cff;

    /**
     * The fonts in the cff table.
     */
    private CffFont[] fonts;

    /**
     * header size
     */
    private int hdrSize;

    /**
     * The named index.
     */
    private List<String> namedIndex;

    /**
     * offset size
     */
    private int offSize;

    /**
     * The string index.
     */
    private List<String> stringIndex;

    /**
     * The map for the string name - sid
     */
    private Map<String, Integer> stringIndexName = null;

    /**
     * The top dict index. TODO remove
     */
    private Map<String, T2Operator> topDictIndex;

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
     * @param tablemap the table map
     * @param de entry
     * @param rar input
     * @throws IOException if an IO-error occurs
     */
    OtfTableCFF(XtfTableMap tablemap, XtfTableDirectory.Entry de,
            RandomAccessR rar) throws IOException {

        super(tablemap);
        cff = this;
        int baseoffset = de.getOffset();
        rar.seek(baseoffset);

        // header
        readHeader(rar);

        // read the names for the fonts.
        long nextOffset = readNameIndex(de.getOffset() + hdrSize, rar);
        fonts = new CffFont[namedIndex.size()];
        for (int i = 0; i < fonts.length; i++) {
            fonts[i] = new CffFont(namedIndex.get(i), i);
        }

        // read the top dict index
        nextOffset = readTopDictIndex(nextOffset, rar);

        // read the string index
        nextOffset = readStringIndex(nextOffset, rar);

        // read the gsubr index
        nextOffset = readGSubrIndex(nextOffset, rar);

        // initialize the T2Operators
        for (int i = 0; i < fonts.length; i++) {
            fonts[i].init(rar, cff, baseoffset);
        }

        // incomplete
    }

    /**
     * Convert the array to a string.
     * 
     * @param data the data-array
     * @return Returns the String.
     */
    private String convertArrayToString(byte[] data) {

        StringBuffer buf = new StringBuffer(data.length);

        for (int i = 0; i < data.length; i++) {
            buf.append((char) data[i]);
        }

        return buf.toString();
    }

    /**
     * Returns the font.
     * 
     * @param number The font numer.
     * @return Returns the font.
     */
    public CffFont getFont(int number) {

        if (number >= 0 && number < fonts.length) {
            return fonts[number];
        }
        return null;
    }

    /**
     * Returns the hdrSize.
     * 
     * @return Returns the hdrSize.
     */
    public int getHdrSize() {

        return hdrSize;
    }

    // /**
    // * Getter for namedIndex.
    // *
    // * @return Returns the namedIndex.
    // */
    // public List getNamedIndex() {
    //
    // return namedIndex;
    // }

    /**
     * Returns the number of glyphs
     * 
     * @return Returns the number of glyphs
     */
    public int getNumGlyphs() {

        XtfTableMap map = getTableMap();
        TtfTableMAXP maxp = (TtfTableMAXP) map.get(XtfReader.MAXP);

        if (maxp != null) {
            return maxp.getNumGlyphs();
        }

        return 0;
    }

    /**
     * @see org.extex.font.format.xtf.XtfTable#getShortcut()
     */
    public String getShortcut() {

        return "cff";
    }

    /**
     * Returns the String for the SID.
     * 
     * First, test if SID is in standard range then fetch from internal table,
     * otherwise, fetch string from the String INDEX using a value of
     * (SID-nStdStrings) as the index.
     * 
     * @param sid the SID for the string.
     * @return Returns the String or <code>null</code>, if not found.
     */
    public String getStringIndex(int sid) {

        int highestSID = T2StandardStrings.getHighestSID();
        if (sid <= highestSID) {
            return T2StandardStrings.getString(sid);
        }
        if (sid - highestSID - 1 < stringIndex.size()) {
            return stringIndex.get(sid - highestSID - 1);
        }
        return null;
    }

    /**
     * Get the table type, as a table directory value.
     * 
     * @return Returns the table type
     */
    public int getType() {

        return XtfReader.CFF;
    }

    /**
     * Returns the version major.
     * 
     * @return Returns the version major.
     */
    public int getVersionmajor() {

        return versionmajor;
    }

    /**
     * Returns the version minor.
     * 
     * @return Returns the version minor.
     */
    public int getVersionminor() {

        return versionminor;
    }

    /**
     * Map the glyph name to the glyph position. If the glyph name not found, -1
     * is returned.
     * 
     * @param glyphname The glyph name
     * @param fontnumber The fontnumber.
     * @return Returns the position of the glyph name.
     */
    public int mapGlyphNameToGlyphPos(String glyphname, int fontnumber) {

        // find the sid for the name
        if (stringIndexName == null) {
            int maxgl =
                    T2StandardStrings.getHighestSID() + stringIndex.size() + 1;
            stringIndexName = new HashMap<String, Integer>(maxgl);
            for (int i = 0; i < maxgl; i++) {
                String name = getStringIndex(i);
                if (name == null) {
                    break;
                }
                stringIndexName.put(name, new Integer(i));
            }
        }
        Integer sid = stringIndexName.get(glyphname);
        if (sid != null) {

            // get charset
            CffFont fo = getFont(fontnumber);
            if (fo != null) {
                T2TDOCharset op = fo.getCharset();
                if (op != null) {

                    int glyphpossid = op.getSidForStringIndex(sid);
                    return glyphpossid;
                }
            }
        }
        return -1;
    }

    /**
     * Map the glyph position to the name of the glyph.
     * 
     * @param glyphpos The glyph position
     * @param fontnumber The font number.
     * @return Returns the name of the glpyh or <code>null</code>, if not
     *         found.
     */
    public String mapGlyphPosToGlyphName(int glyphpos, int fontnumber) {

        // get charset
        CffFont fo = getFont(fontnumber);
        T2TDOCharset op = fo.getCharset();
        if (op != null) {

            int sid = op.getSid(glyphpos);
            // get the name
            String name = getStringIndex(sid);
            return name;
        }

        return null;
    }

    /**
     * An INDEX is an array of variable-sized objects.
     * <p>
     * It comprises a header, an offset array, and object data. The offset array
     * specifies offsets within the object data. An object is retrieved by
     * indexing the offset array and fetching the object at the specified
     * offset. The object's length can be determined by subtracting its offset
     * from the next offset in the offset array. An additional offset is added
     * at the end of the offset array so the length of the last object may be
     * determined.
     * </p>
     * 
     * <table border="1"> <thead>
     * <tr>
     * <td><b>Type</b></td>
     * <td><b>Name</b></td>
     * <td><b> Description</b></td>
     * </tr>
     * </thead>
     * <tr>
     * <td>Card16</td>
     * <td>count</td>
     * <td> Number of objects stored in INDEX. An empty INDEX is represented by
     * a count field with a 0 value and no additional fields. Thus, the total
     * size of an empty INDEX is 2 bytes.</td>
     * </tr>
     * <tr>
     * <td>OffSize</td>
     * <td>offSize</td>
     * <td> Offset array element size</td>
     * </tr>
     * <tr>
     * <td>Offset</td>
     * <td>offset [count+1]</td>
     * <td> Offset array (from byte preceding object data). Offsets in the
     * offset array are relative to the byte that precedes the object data.
     * Therefore the first element of the offset array is always 1. (This
     * ensures that every object has a corresponding offset which is always
     * nonzero and permits the efficient implementation of dynamic object
     * loading.) </td>
     * </tr>
     * <tr>
     * <td>Card8</td>
     * <td>data [&lt;varies&gt;]</td>
     * <td> Object data</td>
     * </tr>
     * </table>
     * 
     * Offsets in the offset array are relative to the byte that precedes the
     * object data. Therefore the first element of the offset array is always 1.
     * (This ensures that every object has a corresponding offset which is
     * always nonzero and permits the efficient implementation of dynamic object
     * loading.)
     * 
     * An empty INDEX is represented by a count field with a 0 value and no
     * additional fields. Thus, the total size of an empty INDEX is 2 bytes.
     * 
     * @param start the start offset
     * @param end the end offset
     * @param rar the input
     * @return Returns the data
     * @throws IOException if an IO-error occurs
     */
    private byte[] readDataFromIndex(int start, int end, RandomAccessR rar)
            throws IOException {

        byte[] data = new byte[end - start];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) rar.readUnsignedByte();
        }
        return data;
    }

    private long readGSubrIndex(long nextOffset, RandomAccessR rar)
            throws IOException {

        rar.seek(nextOffset);
        int[] offsetarray = readOffsets(rar);

        // // get data
        // for (int i = 0; i < offsetarray.length - 1; i++) {
        // byte[] data =
        // readDataFromIndex(offsetarray[i], offsetarray[i + 1], rar);
        // stringIndex.add(convertArrayToString(data));
        // }
        return rar.getPointer();
    }

    /**
     * Read the Header. TODO: create html-table Type Name Description Card8
     * major Format major version (starting at 1) Card8 minor Format minor
     * version (starting at 0) Card8 hdrSize Header size (bytes) OffSize offSize
     * Absolute offset (0) size
     * 
     * @param rar The Input.
     * @throws IOException if an IO-error occurs
     */
    private void readHeader(RandomAccessR rar) throws IOException {

        versionmajor = rar.readUnsignedByte();
        versionminor = rar.readUnsignedByte();
        hdrSize = rar.readUnsignedByte();
        offSize = rar.readUnsignedByte();
    }

    /**
     * Read the name index.
     * 
     * This contains the PostScript language names (FontName or CIDFontName) of
     * all the fonts in the FontSet stored in an INDEX structure. The font names
     * are sorted, thereby permitting a binary search to be performed when
     * locating a specific font within a FontSet. The sort order is based on
     * character codes treated as 8-bit unsigned integers. A given font name
     * precedes another font name having the first name as its prefix. There
     * must be at least one entry in this INDEX, i.e. the FontSet must contain
     * at least one font.
     * 
     * @param rar The Input.
     * @return Returns the next offset.
     * @throws IOException if an io-error occurred.
     */
    private long readNameIndex(int offset, RandomAccessR rar)
            throws IOException {

        namedIndex = new ArrayList<String>();

        rar.seek(offset);
        int[] offsetarray = readOffsets(rar);

        // get data
        for (int i = 0; i < offsetarray.length - 1; i++) {
            byte[] data =
                    readDataFromIndex(offsetarray[i], offsetarray[i + 1], rar);
            namedIndex.add(convertArrayToString(data));
        }
        return rar.getPointer();
    }

    /**
     * Read the offset (see offsetsize).
     * 
     * @param os the offsetsize
     * @param rar the input
     * @return Returns the offset
     * @throws IOException if an IO-error occurs
     */
    private int readOffsetFromIndex(int os, RandomAccessR rar)
            throws IOException {

        int offset = 0;
        for (int i = 0; i < os; i++) {
            int b = rar.readUnsignedByte();
            offset = offset << XtfConstants.SHIFT8;
            offset += b;
        }
        return offset;
    }

    /**
     * Read the offsets.
     * 
     * @param rar The input.
     * @return Returns the offsets.
     * @throws IOException if a IO-error occurred.
     */
    private int[] readOffsets(RandomAccessR rar) throws IOException {

        int count = rar.readUnsignedShort();
        int ioffSize = rar.readUnsignedByte();
        int[] offsetarray = new int[count + 1];

        // read all offsets
        for (int offs = 0; offs < offsetarray.length; offs++) {
            offsetarray[offs] = readOffsetFromIndex(ioffSize, rar);
        }
        return offsetarray;
    }

    /**
     * Read the string index.
     * 
     * <p>
     * All the strings, with the exception of the FontName and CIDFontName
     * strings which appear in the Name INDEX, used by different fonts within
     * the FontSet are collected together into an INDEX structure and are
     * referenced by a 2-byte unsigned number called a string identifier or SID.
     * Only unique strings are stored in the table thereby removing duplication
     * across fonts. Further space saving is obtained by allocating commonly
     * occurring strings to predefined SIDs. These strings, known as the
     * standard strings, describe all the names used in the ISOAdobe and Expert
     * character sets along with a few other strings common to Type 1 fonts.
     * </p>
     * <p>
     * The client program will contain an array of standard strings with
     * nStdStrings elements. Thus, the standard strings take SIDs in the range 0
     * to (nStdStrings 1). The first string in the String INDEX corresponds to
     * the SID whose value is equal to nStdStrings, the first non-standard
     * string, and so on. When the client needs to determine the string that
     * corresponds to a particular SID it performs the following: test if SID is
     * in standard range then fetch from internal table, otherwise, fetch string
     * from the String INDEX using a value of (SID nStdStrings) as the index. An
     * SID is defined as a 2-byte unsigned number but only takes values in the
     * range 0 64999, inclusive. SID values 65000 and above are available for
     * implementation use. A FontSet with zero non-standard strings is
     * represented by an empty INDEX.
     * </p>
     * 
     * @param nextOffset The offset.
     * @param rar The input.
     * @return Returns the next offset.
     * @throws IOException if an IO-error occurred.
     */
    private long readStringIndex(long nextOffset, RandomAccessR rar)
            throws IOException {

        stringIndex = new ArrayList<String>();

        rar.seek(nextOffset);
        int[] offsetarray = readOffsets(rar);

        // get data
        for (int i = 0; i < offsetarray.length - 1; i++) {
            byte[] data =
                    readDataFromIndex(offsetarray[i], offsetarray[i + 1], rar);
            stringIndex.add(convertArrayToString(data));
        }
        return rar.getPointer();
    }

    /**
     * Read the top dict index.
     * 
     * @param offset The offset.
     * @param rar The input.
     * @return Returns the next offset.
     * @throws IOException if an IO-error occurred.
     */
    private long readTopDictIndex(long offset, RandomAccessR rar)
            throws IOException {

        topDictIndex = new HashMap<String, T2Operator>();

        int[] offsetarray = readOffsets(rar);

        // get data
        for (int i = 0; i < offsetarray.length - 1; i++) {
            byte[] data =
                    readDataFromIndex(offsetarray[i], offsetarray[i + 1], rar);
            RandomAccessInputArray arar = new RandomAccessInputArray(data);
            try {
                // read until end of input -> IOException
                while (true) {
                    T2Operator op = T2CharString.readTopDICTOperator(arar);
                    fonts[i].addTopDictIndex(op);
                }
            } catch (IOException e) {
                // EOF ignore
            }
        }
        return rar.getPointer();
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writeStartElement(writer);
        writer.writeAttribute("version", String.valueOf(versionmajor) + "."
                + String.valueOf(versionminor));
        writer.writeAttribute("hdrsize", String.valueOf(hdrSize));
        writer.writeAttribute("offsize", String.valueOf(offSize));
        writer.writeAttribute("fonts", fonts.length);

        // fonts
        for (int i = 0; i < fonts.length; i++) {
            fonts[i].writeXML(writer);
        }

        // stringindex
        writer.writeStartElement("stringindex");
        int highestSID = T2StandardStrings.getHighestSID();
        for (int i = 0, n = highestSID + stringIndex.size(); i < n; i++) {
            writer.writeStartElement("string");
            writer.writeAttribute("sid", i);
            writer.writeAttribute("value", getStringIndex(i));
            writer.writeEndElement();
        }
        writer.writeEndElement();

        // ------------
        writer.writeComment("incomplete");
        writer.writeEndElement();
    }
}
