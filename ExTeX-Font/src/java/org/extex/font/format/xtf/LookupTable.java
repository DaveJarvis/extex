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
 * Class for the lookup table.
 * <p>
 * A Lookup table (Lookup) defines the specific conditions, type, and results of
 * a substitution or positioning action that is used to implement a feature. For
 * example, a substitution operation requires a list of target glyph indices to
 * be replaced, a list of replacement glyph indices, and a description of the
 * type of substitution action. <br/> Each Lookup table may contain only one
 * type of information (LookupType), determined by whether the lookup is part of
 * a GSUB or GPOS table. GSUB supports five LookupTypes, and GPOS supports seven
 * LookupTypes (for details about LookupTypes, see the GSUB and GPOS chapters of
 * the document). <br/> Each LookupType is defined with one or more subtables,
 * and each subtable definition provides a different representation format. The
 * format is determined by the content of the information required for an
 * operation and by required storage efficiency. When glyph information is best
 * presented in more than one format, a single lookup may contain more than one
 * subtable, as long as all the subtables are the same LookupType. For example,
 * within a given lookup, a glyph index array format may best represent one set
 * of target glyphs, whereas a glyph index range format may be better for
 * another set of target glyphs. <br/> During text processing, a client applies
 * a lookup to each glyph in the string before moving to the next lookup. A
 * lookup is finished for a glyph after the client makes the
 * substitution/positioning operation. To move to the "next" glyph, the client
 * will typically skip all the glyphs that participated in the lookup operation:
 * glyphs that were substituted/positioned as well as any other glyphs that
 * formed a context for the operation. However, in the case of pair positioning
 * operations (i.e., kerning), the "next" glyph in a sequence may be the second
 * glyph of the positioned pair (see pair positioning lookup for details). <br/>
 * A Lookup table contains a LookupType, specified as an integer, that defines
 * the type of information stored in the lookup. The LookupFlag specifies lookup
 * qualifiers that assist a text-processing client in substituting or
 * positioning glyphs. The SubTableCount specifies the total number of
 * SubTables. The SubTable array specifies offsets, measured from the beginning
 * of the Lookup table, to each SubTable enumerated in the SubTable array.
 * </p>
 * <table border="1">
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>LookupType</td>
 * <td>Different enumerations for GSUB and GPOS</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>LookupFlag</td>
 * <td>Lookup qualifiers</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>SubTableCount</td>
 * <td>Number of SubTables for this lookup</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>SubTable<br>
 * [SubTableCount]</td>
 * <td>Array of offsets to SubTables-from beginning of Lookup table</td>
 * </tr>
 * </table>
 * <p>
 * </p>
 * 
 * <p>
 * The LookupFlag uses four bits and one byte:
 * </p>
 * <ul>
 * <li>Each of the first four bits can be set in order to specify additional
 * instructions for applying a lookup to a glyph string. The LookUpFlag bit
 * enumeration table provides details about the use of these bits.</li>
 * <li>The high byte is set to specify the type of mark attachment.</li>
 * </ul>
 * <p>
 * LookupFlag bit enumeration
 * </p>
 * <table border="1">
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>0x0001</td>
 * <td>RightToLeft</td>
 * <td>This bit relates only to the correct processing of the cursive
 * attachment lookup type (GPOS lookup type 3). When this bit is set, the last
 * glyph in a given sequence to which the cursive attachment lookup is applied,
 * will be positioned on the baseline.<br>
 * <b>Note: Setting of this bit is not intended to be used by operating systems
 * or applications to determine text direction.<br>
 * </b></td>
 * </tr>
 * <tr>
 * <td>0x0002</td>
 * <td>IgnoreBaseGlyphs</td>
 * <td>If set, skips over base glyphs</td>
 * </tr>
 * <tr>
 * <td>0x0004</td>
 * <td>IgnoreLigatures</td>
 * <td>If set, skips over ligatures</td>
 * </tr>
 * <tr>
 * <td>0x0008</td>
 * <td>IgnoreMarks</td>
 * <td>If set, skips over combining marks</td>
 * </tr>
 * <tr>
 * <td>0x00F0</td>
 * <td>Reserved</td>
 * <td>For future use</td>
 * </tr>
 * <tr>
 * <td>0xFF00</td>
 * <td>MarkAttachmentType</td>
 * <td>If not zero, skips over all marks of attachment type different from
 * specified.</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5438 $
 */
public class LookupTable implements XMLWriterConvertible {

    /**
     * If set, skips over base glyphs.
     */
    public static final int IgnoreBaseGlyphs = 0x0002;

    /**
     * If set, skips over ligatures
     */
    public static final int IgnoreLigatures = 0x0004;

    /**
     * If set, skips over combining marks.
     */
    public static final int IgnoreMarks = 0x0008;

    /**
     * If not zero, skips over all marks of attachment type different from
     * specified.
     */
    public static final int MarkAttachmentType = 0xff00;

    /**
     * For future use.
     */
    public static final int Reserved = 0x00f0;

    /**
     * This bit relates only to the correct processing of the cursive attachment
     * lookup type (GPOS lookup type 3). When this bit is set, the last glyph in
     * a given sequence to which the cursive attachment lookup is applied, will
     * be positioned on the baseline. Note: Setting of this bit is not intended
     * to be used by operating systems or applications to determine text
     * direction.
     */
    public static final int RightToLeft = 0x0001;

    /**
     * Lookup qualifiers.
     */
    private int lookupFlag;

    /**
     * Different enumerations for GSUB and GPOS.
     */
    private int lookupType;

    /**
     * Each subtable (except an Extension LookupType subtable) in a lookup
     * references a Coverage table (Coverage).
     */
    private LookupType[] lookupTypeArray;

    /**
     * Array of offsets to SubTables-from beginning of Lookup table.
     */
    private int[] subtablesOffset;

    /**
     * The gpos table.
     */
    private OtfTableGPOS gpos;

    /**
     * Creates a new object.
     * 
     * @param tableGPOS The gpos table.
     * @param rar input
     * @param offset The offset for the table.
     * @param lookupTableBaseOffset The base offset.
     * @throws IOException if a io-error occurred.
     */
    public LookupTable(OtfTableGPOS tableGPOS, RandomAccessR rar,
            int lookupTableBaseOffset, int offset) throws IOException {

        gpos = tableGPOS;
        rar.seek(lookupTableBaseOffset + offset);

        lookupType = rar.readUnsignedShort();
        lookupFlag = rar.readUnsignedShort();
        int subTableCount = rar.readUnsignedShort();

        subtablesOffset = new int[subTableCount];
        for (int i = 0; i < subTableCount; i++) {
            subtablesOffset[i] = rar.readUnsignedShort();
        }

        lookupTypeArray = new LookupType[subTableCount];
        for (int i = 0; i < subTableCount; i++) {
            lookupTypeArray[i] =
                    LookupType.getInstance(rar, lookupType,
                        lookupTableBaseOffset + offset + subtablesOffset[i]);
        }

    }

    /**
     * Check, if the flag is set.
     * 
     * @param value The value.
     * @param flag The flag.
     * @return Return <code>true</code>, if the flag is set.
     */
    private boolean checkFlag(int value, int flag) {

        int erg = value & flag;
        if (erg > 0) {
            return true;
        }
        return false;
    }

    /**
     * Getter for lookupFlag.
     * 
     * @return the lookupFlag
     */
    public int getLookupFlag() {

        return lookupFlag;
    }

    /**
     * Getter for lookupType.
     * 
     * @return the lookupType
     */
    public int getLookupType() {

        return lookupType;
    }

    /**
     * Getter for lookupTypeArray.
     * 
     * @return the lookupTypeArray
     */
    public LookupType[] getLookupTypeArray() {

        return lookupTypeArray;
    }

    /**
     * Getter for subtables.
     * 
     * @return the subtables
     */
    public int[] getSubtables() {

        return subtablesOffset;
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("table");
        writer.writeAttribute("flag", lookupFlag, 4);
        writer
            .writeAttribute("RightToLeft", checkFlag(lookupFlag, RightToLeft));
        writer.writeAttribute("IgnoreBaseGlyphs", checkFlag(lookupFlag,
            IgnoreBaseGlyphs));
        writer.writeAttribute("IgnoreBaseGlyphs", checkFlag(lookupFlag,
            IgnoreBaseGlyphs));
        writer
            .writeAttribute("IgnoreMarks", checkFlag(lookupFlag, IgnoreMarks));
        writer.writeAttribute("type", lookupType, 4);
        writer.writeIntArrayAsEntries(subtablesOffset);

        for (int i = 0; i < lookupTypeArray.length; i++) {
            if (lookupTypeArray[i] != null) {
                lookupTypeArray[i].writeXML(writer);
            }
        }
        writer.writeEndElement();
    }
}
