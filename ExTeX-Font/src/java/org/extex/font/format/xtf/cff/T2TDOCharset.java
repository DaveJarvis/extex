/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.cff;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.font.format.xtf.OtfTableCFF;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Charset.
 * 
 * <p>
 * Charset data is located via the offset operand to the charset operator in the
 * Top DICT. Each charset is described by a format-type identifier byte followed
 * by format-specific data.
 * </p>
 * <p>
 * TODO change to HTML table Format 0 Type Name Description Card8 format =0 SID
 * glyph [nGlyphs-1] Glyph name array
 * </p>
 * <p>
 * Format 1 Type Name Description Card8 format =1 struct Range1 [varies] Range1
 * array (see Table 19)
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class T2TDOCharset extends T2TDONumber {

    /**
     * The format.
     */
    private int format = -1;

    /**
     * The charset sid.
     */
    private int[] sid;

    /**
     * The map for the values.
     */
    private Map<Integer, Integer> valuesid = null;

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @throws IOException if an IO.error occurs.
     */
    public T2TDOCharset(List stack) throws IOException {

        super(stack, new short[]{CFF_CHARSET});
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    public String getName() {

        return "charset";
    }

    /**
     * Getter for sid.
     * 
     * @return Returns the sid.
     */
    public int[] getSid() {

        return sid;
    }

    /**
     * Returns the sid.
     * <p>
     * If the pos out of range, 0 (.notdef) is returned.
     * </p>
     * 
     * @param pos nThe position in the array.
     * @return Returns the sid.
     */
    public int getSid(int pos) {

        if (pos >= 0 && pos < sid.length) {
            return sid[pos];
        }
        return 0;
    }

    /**
     * Returns the sid for a value. If the value is not found, 0 is returned.
     * 
     * @param value The value.
     * @return Returns the sid for a value.
     */
    public int getSidForStringIndex(int stringIndexpos) {

        // initialize
        if (valuesid == null) {
            valuesid = new HashMap<Integer, Integer>(sid.length);
            for (int i = 0; i < sid.length; i++) {
                valuesid.put(sid[i], i);
            }
        }
        Integer ii = valuesid.get(stringIndexpos);
        if (ii != null) {
            return ii;
        }

        return 0;
    }

    /**
     * Read the charset entry. Charset data is located via the offset operand to
     * the charset operator in the Top DICT. Each charset is described by a
     * format-type identifier byte followed by format-specific data.
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#init(
     *      org.extex.util.file.random.RandomAccessR,
     *      org.extex.font.format.xtf.OtfTableCFF, int)
     */
    public void init(RandomAccessR rar, OtfTableCFF cff, int baseoffset)
            throws IOException {

        int offset = getInteger();
        if (offset > 0) {
            rar.seek(baseoffset + offset);

            format = rar.readUnsignedByte();

            int numberOfGlyphs = cff.getNumGlyphs();

            sid = new int[numberOfGlyphs];
            if (numberOfGlyphs > 0) {

                // assign the .notdef glyph
                sid[0] = 0;

                int j = 1;

                switch (format) {
                    case 0:

                        for (j = 1; j < numberOfGlyphs; j++) {
                            sid[j] = rar.readUnsignedShort();
                        }
                        break;

                    case 1:
                        // Range1 Format (Charset)
                        // Type Name Description
                        // sid first First glyph in range
                        // Card8 nLeft Glyphs left in range (excluding first)
                        //
                        // Each Range1 describes a group of sequential SIDs.
                        // The number of ranges is not explicitly specified
                        // in the font. Instead, software utilizing this data
                        // simply processes ranges until all glyphs in the
                        // font are covered. This format is particularly
                        // suited to charsets that are well ordered.

                        j = 1;

                        while (j < numberOfGlyphs) {
                            // Read the first glyph sid of the range.
                            int first = rar.readUnsignedShort();

                            // Read the number of glyphs in the range.
                            int nleft = rar.readUnsignedByte();

                            // Fill in the range of sids -- 'nleft + 1' glyphs.
                            for (int i = 0; j < numberOfGlyphs && i <= nleft; i++, j++, first++) {
                                sid[j] = first;
                            }
                        }

                        break;
                    case 2:
                        // Range2 Format
                        // Type Name Description
                        // sid first First glyph in range
                        // Card16 nLeft Glyphs left in range (excluding first)
                        // Format 2 differs from format 1 only in the size of
                        // the nLeft field in each range. This format is most
                        // suitable for fonts with a large well-ordered charset
                        // - for example, for Asian CIDFonts.

                        j = 1;

                        while (j < numberOfGlyphs) {
                            // Read the first glyph sid of the range.
                            int first = rar.readUnsignedShort();

                            // Read the number of glyphs in the range.
                            int nleft = rar.readUnsignedShort();

                            // Fill in the range of sids -- 'nleft + 1' glyphs.
                            for (int i = 0; j < numberOfGlyphs && i <= nleft; i++, j++, first++) {
                                sid[j] = first;
                            }
                        }

                        break;
                    default:
                        break;
                }
            }

        } else {
            sid = new int[0];
        }
    }

    /**
     * Returns the value as hex string.
     * 
     * @param i The int value.
     * @return Returns the value as hex string.
     */
    private String toHex(int i) {

        String hex = "0000" + Integer.toHexString(i);
        hex = "0x" + hex.substring(hex.length() - 4);
        return hex;
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        // writer.writeAttribute("value", getValue());
        writer.writeAttribute("format", format);
        switch (format) {
            case 0:

                break;
            case 1:
                for (int i = 0; i < sid.length; i++) {
                    writer.writeStartElement("entry");
                    writer.writeAttribute("sid", i);
                    writer.writeAttribute("char", toHex(sid[i]));
                    writer.writeAttribute("value", sid[i]);
                    writer.writeEndElement();
                }
                break;
            case 2:

                break;

            default:
                break;
        }
        writer.writeEndElement();

    }

    @Override
    public int getID() {

        return T2TopDICTOperator.TYPE_CHARSET;
    }

}
