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

import org.extex.font.format.xtf.cff.LookupTableFactory;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Lookup tables provide a way of looking up information about a glyph index.
 * 
 * <p>
 * Some lookup tables do simple array-type lookup. Others involve groupings,
 * allowing you to treat many different glyph indexes in the same way (that is,
 * to look up the same information about them). The top-level description of a
 * lookup table contains a format number and a format-specific header.
 * </p>
 * <p>
 * The format of the Lookup Table header is as follows:
 * </p>
 * 
 * <table border="1"> <tbody>
 * <tr>
 * <th>Type</th>
 * <th>Name</th>
 * <th>Description</th>
 * </tr>
 * </tbody>
 * <tr>
 * <td>uint16</td>
 * <td>format</td>
 * <td> Format of this lookup table. There are five lookup table formats, each
 * with a format number.</td>
 * </tr>
 * <tr>
 * <td>variable</td>
 * <td>fsHeader</td>
 * <td> Format-specific header (each of these is described in the following
 * sections), followed by the actual lookup data. The details of the fsHeader
 * structure are given with the different formats.</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfLookup implements XMLWriterConvertible {

    /**
     * 3 - Alternate - Replace one glyph with one of many glyphs
     */
    public static final int ALTERNATE = 3;

    /**
     * 6 - Chaining - Context Replace one or more glyphs in chained context
     */
    public static final int CHAINING = 6;

    /**
     * 5 - Context - Replace one or more glyphs in context
     */
    public static final int CONTEXT = 5;

    /**
     * LookupFlag IGNORE_BASE_GLYPHS
     */
    public static final int IGNORE_BASE_GLYPHS = 0x0002;

    /**
     * LookupFlag IGNORE_BASE_LIGATURES
     */
    public static final int IGNORE_BASE_LIGATURES = 0x0004;

    /**
     * LookupFlag IGNORE_BASE_MARKS
     */
    public static final int IGNORE_BASE_MARKS = 0x0008;

    /**
     * 4 - Ligature - Replace multiple glyphs with one glyph
     */
    public static final int LIGATURE = 4;

    /**
     * The name of the lookup types.
     */
    private final static String[] LOOKUP_TYPE_NAMES =
            {"Single", "Multiple", "Alternate", "Ligature", "Context",
                    "Chaining"};

    /**
     * LookupFlag MARK_ATTACHMENT_TYP
     */
    public static final int MARK_ATTACHMENT_TYPE = 0xFF00;

    /**
     * 2 - Multiple - Replace one glyph with more than one glyph
     */
    public static final int MULTIPLE = 2;

    /**
     * 1 - Single - Replace one glyph with one glyph
     */
    public static final int SINGLE = 1;

    /**
     * Returns the name of the lookup type (The start index is 1!).
     * 
     * @param type The type.
     * @return Returns the name of the lookup type.
     */
    public static String lookupType(int type) {

        if (type >= 1 && type < LOOKUP_TYPE_NAMES.length - 1) {
            return LOOKUP_TYPE_NAMES[type - 1];
        }
        return "Unknown";
    }

    /**
     * flag
     */
    private int flag;

    /**
     * The index.
     */
    private int index;

    /**
     * subtable count
     */
    private int subTableCount;

    /**
     * subtable offsets
     */
    private int[] subTableOffsets;

    /**
     * subtables
     */
    private XtfLookupTable[] subTables;

    /**
     * type
     */
    private int type;

    /**
     * Create a new object
     * 
     * @param rar input
     * @param offset offset
     * @param lookupFactory The factory for the lookup table.
     * @param index The index.
     * @throws IOException if an IO-error occurs
     */
    XtfLookup(RandomAccessR rar, int offset, LookupTableFactory lookupFactory,
            int index) throws IOException {

        this.index = index;
        rar.seek(offset);

        type = rar.readUnsignedShort();
        flag = rar.readUnsignedShort();
        subTableCount = rar.readUnsignedShort();
        subTableOffsets = new int[subTableCount];
        subTables = new XtfLookupTable[subTableCount];
        for (int i = 0; i < subTableCount; i++) {
            subTableOffsets[i] = rar.readUnsignedShort();
        }
        for (int i = 0; i < subTableCount; i++) {
            subTables[i] =
                    lookupFactory.read(rar, type, offset + subTableOffsets[i]);
        }
    }

    /**
     * Getter for index.
     * 
     * @return the index
     */
    public int getIndex() {

        return index;
    }

    /**
     * Returns the subtable
     * 
     * @param i index
     * @return Returns the subtable
     */
    public XtfLookupTable getSubtable(int i) {

        return subTables[i];
    }

    /**
     * Returns the subtable count
     * 
     * @return Returns the subtablecount
     */
    public int getSubtableCount() {

        return subTableCount;
    }

    /**
     * Returns the type
     * 
     * @return Returns the type
     */
    public int getType() {

        return type;
    }

    /**
     * Returns the info for this class
     * 
     * @return Returns the info for this class
     */
    @Override
    public String toString() {

        StringBuffer buf = new StringBuffer();
        buf.append("Lookup\n");
        buf.append("   type   : ").append(type).append('\n');
        return buf.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("lookup");
        writer.writeAttribute("index", index);
        writer.writeAttribute("typeid", type);
        writer.writeAttribute("type", lookupType(type));
        for (int i = 0; i < subTables.length; i++) {
            XtfLookupTable st = subTables[i];
            if (st != null) {
                st.writeXML(writer);
            }
        }
        writer.writeEndElement();

    }
}
