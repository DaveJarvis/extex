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

package org.extex.font.format.xtf.tables.cff;

import org.extex.font.format.xtf.tables.OtfTableCFF;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Charset.
 * 
 * <p>
 * Charset data is located via the offset operand to the charset operator in the
 * Top DICT. Each charset is described by a format-type identifier byte followed
 * by format-specific data.
 * </p>
 * 
 * <p>
 * Format 0
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
* <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>Card8</td>
 * <td>format</td>
 * <td>=0</td>
 * </tr>
 * <tr>
 * <td>SID</td>
 * <td>glyph<br>[nGlyphs-1]</td>
 * <td>Glyph name array</td>
 * </tr>
 * </table>
 * 
 * <p>
 * Each element of the glyph array represents the name of the corresponding
 * glyph. This format should be used when the SIDs are in a fairly random order.
 * The number of glyphs (nGlyphs) is the value of the count field in the
 * CharStrings INDEX. (There is one less element in the glyph name array than
 * nGlyphs because the .notdef glyph name is omitted.)
 * </p>
 * 
 * <p>
 * Format 1
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>Card8</td>
 * <td>format</td>
 * <td> =1</td>
 * </tr>
 * <tr>
 * <td>struct</td>
 * <td> Range1<br>[varies]</td>
 * <td> Range1 array</td>
 * </tr>
 * </table>
 * 
 * <p>
 * Range1 Format (Charset)
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>SID</td>
 * <td>first</td>
 * <td>First glyph in range</td>
 * </tr>
 * <tr>
 * <td>Card8</td>
 * <td>nLeft</td>
 * <td>Glyphs left in range (excluding first)</td>
 * </tr>
 * </table>
 * <p>
 * Each Range1 describes a group of sequential SIDs. The number of ranges is not
 * explicitly specified in the font. Instead, software utilizing this data
 * simply processes ranges until all glyphs in the font are covered. This format
 * is particularly suited to charsets that are well ordered.
 * </p>
 * 
 * <p>
 * Format 2
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>Card8</td>
 * <td>format</td>
 * <td> =2</td>
 * </tr>
 * <tr>
 * <td>struct</td>
 * <td>Range2 <br>[varies]</td>
 * <td>Range2 array</td>
 * </tr>
 * </table>
 * 
 * <p>
 * Range2 Format
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
* <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>SID</td>
 * <td>first</td>
 * <td>First glyph in range</td>
 * </tr>
 * <tr>
 * <td>Card16</td>
 * <td>nLeft</td>
 * <td>Glyphs left in range (excluding first)</td>
 * </tr>
 * </table>
 * <p>
 * Format 2 differs from format 1 only in the size of the nLeft field in each
 * range. This format is most suitable for fonts with a large well-ordered
 * charset - for example, for Asian CIDFonts.
 * </p>
 * <p>
 * Careful attention to the allocation order of SIDs typically yields very small
 * font charsets. Still more optimization is possible by observing that many
 * fonts adopt one of 3 common charsets. In these cases the operand to the
 * charset operator in the Top DICT specifies a predefined charset id, in place
 * of an offset, as shown in Table Charset ID.
 * </p>
 * <p>
 * Charset ID
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
* <td><b>Id</b></td>
 * <td><b>Name</b></td>
 * </tr>
 * <tr>
 * <td>0</td>
 * <td>ISOAdobe</td>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>Expert</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>ExpertSubset</td>
 * </tr>
 * </table>
 * <p>
 * If the font has an ISOAdobe charset, the charset operator can be omitted from
 * the Top DICT since its default value is 0. Details of predefined charsets can
 * be found in Appendix�C. A font may use a predefined charset if it exactly
 * matches in the first nGlyphs. CID fonts must not use predefined charsets.<br>
 * Two or more fonts may share the same charset by setting the offset operand of
 * the charset operator to the same value in each font.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/

public class T2TDOCharset extends T2TDONumber {

    /**
     * ISOAdobe
     */
    public static final int ISO_ADOPE = 0;

    /**
     * Expert
     */
    public static final int EXPERT = 1;

    /**
     * ExpertSubset
     */
    public static final int EXPERT_SUBSET = 2;

    /**
     * FontDefine
     */
    public static final int FONT_DEFINE = -1;

    /**
     * The cff table.
     */
    private OtfTableCFF cff;

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
     * The charset of the font
     */
    private int charSet = FONT_DEFINE;

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @throws IOException if an IO.error occurs.
     */
    public T2TDOCharset(List<T2CharString> stack) throws IOException {

        super(stack, new short[]{CFF_CHARSET});
    }

@Override
    public int getID() {

        return T2TopDICTOperator.TYPE_CHARSET;
    }

@Override
    public String getName() {

        return "charset";
    }

    /**
     * Returns the name for a position.
     * 
     * @param pos The position.
     * @return Returns the name for a position.
     */
    public String getNameForPos(int pos) {

        switch (charSet) {
            case FONT_DEFINE:
                if (pos >= 0 && pos < sid.length) {
                    return cff.getStringIndex(sid[pos]);
                }
                return ".notdef";
            case ISO_ADOPE:
                return T2PredefinedCharsetISOAdobe.getName(pos);
            case EXPERT:
                return T2PredefinedCharsetExpert.getName(pos);
            case EXPERT_SUBSET:
                return T2PredefinedCharsetExpertSubset.getName(pos);
            default:
                return ".notdef";
        }
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
     * @param stringIndexpos TODO
     * 
     * @return Returns the sid for a value.
     */
    @SuppressWarnings("boxing")
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
*      org.extex.font.format.xtf.tables.OtfTableCFF, int,
     *      org.extex.font.format.xtf.tables.cff.CffFont)
     */
    @Override
    public void init(RandomAccessR rar, OtfTableCFF cff, int baseoffset,
            CffFont cffFont) throws IOException {

        this.cff = cff;

        int offset = getInteger();
        if (offset == 0) {
            charSet = ISO_ADOPE;
        } else if (offset == 1) {
            charSet = EXPERT;
        } else if (offset == 2) {
            charSet = EXPERT_SUBSET;
        } else {
            charSet = FONT_DEFINE;
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

        }
    }

    /**
*      org.extex.util.xml.XMLStreamWriter)
     */
    @Override
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("format", format);

        switch (charSet) {
            case FONT_DEFINE:
                writer.writeAttribute("charset", "FontDefine");

                for (int i = 0; i < sid.length; i++) {
                    writer.writeStartElement("sid");
                    writer.writeAttribute("id", i);
                    writer.writeAttribute("name", cff.getStringIndex(sid[i]));
                    writer.writeEndElement();
                }

                break;
            case ISO_ADOPE:
                writer.writeAttribute("charset", "ISOAdobe");

                for (int i = 0; i < T2PredefinedCharsetISOAdobe.DATA.length; i++) {
                    writer.writeStartElement("sid");
                    writer.writeAttribute("id", i);
                    writer.writeAttribute("name", T2PredefinedCharsetISOAdobe
                        .getName(i));
                    writer.writeEndElement();
                }

                break;
            case EXPERT:
                writer.writeAttribute("charset", "Expert");

                for (int i = 0; i < T2PredefinedCharsetExpert.DATA.length; i++) {
                    writer.writeStartElement("sid");
                    writer.writeAttribute("id", i);
                    writer.writeAttribute("name", T2PredefinedCharsetExpert
                        .getName(i));
                    writer.writeEndElement();
                }

                break;
            case EXPERT_SUBSET:
                writer.writeAttribute("charset", "ExpertSubset");

                for (int i = 0; i < T2PredefinedCharsetExpertSubset.DATA.length; i++) {
                    writer.writeStartElement("sid");
                    writer.writeAttribute("id", i);
                    writer.writeAttribute("name",
                        T2PredefinedCharsetExpertSubset.getName(i));
                    writer.writeEndElement();
                }

                break;
            default:
        }

        writer.writeEndElement();
    }

}
