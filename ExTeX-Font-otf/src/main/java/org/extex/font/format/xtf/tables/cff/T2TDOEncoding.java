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

import java.io.IOException;
import java.util.List;

import org.extex.font.format.xtf.tables.OtfTableCFF;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Encoding.
 * <p>
 * Note: The encoding table in a CFF font is indexed by glyph index; the first
 * encoded glyph index is 1.
 * </p>
 * <p>
 * Encoding data is located via the offset operand to the Encoding operator in
 * the Top DICT. Only one Encoding operator can be specified per font except for
 * CIDFonts which specify no encoding. A glyph's encoding is specified by a
 * 1-byte code that permits values in the range 0-255. Each encoding is
 * described by a format-type identifier byte followed by format-specific data.
 * Two formats are currently defined.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class T2TDOEncoding extends T2TDONumber {

    /**
     * ExpertEncoding
     */
    public static final int ExpertEncoding = 1;

    /**
     * FontDefined
     */
    public static final int FontDefined = 2;

    /**
     * StandardEncoding
     */
    public static final int StandardEncoding = 0;

    /**
     * The cff table.
     */
    private OtfTableCFF cff;

    /**
     * The encoding vector.
     */
    private String[] enc = new String[256];

    /**
     * The encoding of the font.
     */
    private int encoding = StandardEncoding;

    /**
     * The name of the encoding.
     */
    private String encodingName = "StandardEncoding";

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @throws IOException if an IO-error occurs.
     */
    public T2TDOEncoding(List<T2CharString> stack) throws IOException {

        super(stack, new short[]{CFF_ENCODING});
    }

    /**
     * Creates a new object.
     * 
     * <p>
     * It use the default cff encoding.
     * </p>
     * 
     * @param cff The cff table.
     * @param numberOfGlyphs The number of glyphs.
     * @param charset The charset.
     */
    public T2TDOEncoding(OtfTableCFF cff, int numberOfGlyphs,
            T2TDOCharset charset) {

        this.cff = cff;
        createDefaultEncodingTable(numberOfGlyphs, charset);
    }

    /**
     * Crete the default encoding.
     * 
     * @param numberOfGlyphs The number of glyphs.
     * @param charset The charset.
     */
    private void createDefaultEncodingTable(int numberOfGlyphs,
            T2TDOCharset charset) {

        // We take into account the fact a CFF font can use a predefined
        // encoding without containing all of the glyphs encoded by this
        // encoding (see the note at the end of section 12 in the CFF
        // specification).

        for (int i = 0; i < enc.length; i++) {

            // copy the default encoding
            if (encoding == StandardEncoding) {
                enc[i] = T2StandardEncoding.getString(i);
            } else {
                enc[i] = T2ExpertEncoding.getString(i);
            }

            // if not defined, use the name from the charset
            if (enc[i].equals(".notdef")) {
                enc[i] = charset.getNameForPos(i);
            }
        }
    }

    /**
     * Getter for encodingName.
     * 
     * @return the encodingName
     */
    public String getEncodingName() {

        return encodingName;
    }

    /**
     * Returns the name of the glyph.
     * 
     * @param pos The position in the encoding.
     * @return Returns the name of the glyph.
     */
    public String getGlyphName(int pos) {

        if (pos >= 0 && pos < enc.length) {
            String name = enc[pos];
            if (name != null) {
                return name;
            }
        }

        return ".notdef";
    }

    @Override
    public int getID() {

        return T2TopDICTOperator.TYPE_ENCODING;
    }

    /**
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "encoding";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#init(org.extex.util.file.random.RandomAccessR,
     *      org.extex.font.format.xtf.tables.OtfTableCFF, int,
     *      org.extex.font.format.xtf.tables.cff.CffFont)
     */
    @Override
    public void init(RandomAccessR rar, OtfTableCFF cff, int baseoffset,
            CffFont cffFont) throws IOException {

        int offset = getInteger();
        this.cff = cff;

        if (offset == 0) {
            encoding = StandardEncoding;
            encodingName = "StandardEncoding";
        } else if (offset == 1) {
            encoding = ExpertEncoding;
            encodingName = "ExpertEncoding";
        } else {
            encoding = FontDefined;
            encodingName = "FontDefined";

            rar.seek(baseoffset + offset);

            /* int format = */rar.readUnsignedByte();

            // TODO mgn: incomplete
        }
    }

    @Override
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("name", getEncodingName());
        for (int i = 0; i < enc.length; i++) {
            writer.writeStartElement("sid");
            writer.writeAttribute("id", i);
            writer.writeAttribute("name", getGlyphName(i));
            writer.writeEndElement();
        }
        writer.writeEndElement();

    }

}
