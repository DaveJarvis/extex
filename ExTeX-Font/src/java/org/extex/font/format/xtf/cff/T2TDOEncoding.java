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
import java.util.List;

import org.extex.font.format.xtf.OtfTableCFF;
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
     * The cff table.
     */
    private OtfTableCFF cff;

    /**
     * The encoding code.
     */
    private int encodingCode[];

    /**
     * The name of the encoding.
     */
    private String encodingName = "StandardEncoding";

    /**
     * The encoding sids.
     */
    private int encodingSid[];

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
     * @param charsetSid The charset sids.
     */
    public T2TDOEncoding(OtfTableCFF cff, int numberOfGlyphs, int[] charsetSid) {

        this.cff = cff;
        createDefaultEncodingTable(numberOfGlyphs, charsetSid);
    }

    /**
     * Crete the default encoding.
     * 
     * @param numberOfGlyphs The number of glyphs.
     * @param charsetSid The charset sid.
     */
    private void createDefaultEncodingTable(int numberOfGlyphs, int[] charsetSid) {

        // We take into account the fact a CFF font can use a predefined
        // encoding without containing all of the glyphs encoded by this
        // encoding (see the note at the end of section 12 in the CFF
        // specification).

        encodingSid = T2StandardEncoding.getSidArray();
        encodingCode = new int[encodingSid.length];

        int count = 0;
        int i = 0;
        for (int j = 0; j < 256; j++) {
            // If j is encoded, find the GID for it.
            if (encodingSid[j] == 0) {
                for (i = 1; i < numberOfGlyphs; i++) {
                    // We matched, so break.
                    if (charsetSid[i] == encodingSid[j]) {
                        break;
                    }
                }
                /* i will be equal to num_glyphs if we exited the above */
                /* loop without a match. In this case, we also have to */
                /* fix the code to SID mapping. */
                if (i == numberOfGlyphs) {
                    encodingCode[j] = 0;
                    encodingSid[j] = 0;
                } else {
                    encodingCode[j] = i;

                    /* update encoding count */
                    if (count < j + 1) {
                        count = j + 1;
                    }
                }
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
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    public String getName() {

        return "encoding";
    }

    @Override
    public void init(RandomAccessR rar, OtfTableCFF cff, int baseoffset)
            throws IOException {

        int offset = getInteger();
        this.cff = cff;

        if (offset > 0) {
            rar.seek(baseoffset + offset);

            int format = rar.readUnsignedByte();

            // TODO mgn: incomplete
        }
    }

    @Override
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("name", getEncodingName());
        for (int i = 0; i < encodingSid.length; i++) {
            writer.writeStartElement("sid");
            writer.writeAttribute("id", i);
            writer.writeAttribute("sid", encodingSid[i]);
            writer.writeAttribute("code", encodingCode[i]);
            if (cff != null) {
                writer.writeAttribute("name", cff
                    .getStringIndex(encodingSid[i]));
            }
            writer.writeEndElement();
        }
        writer.writeEndElement();

    }

    @Override
    public int getID() {

        return T2TopDICTOperator.TYPE_ENCODING;
    }

}
