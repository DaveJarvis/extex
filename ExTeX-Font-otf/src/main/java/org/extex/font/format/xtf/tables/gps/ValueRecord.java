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

package org.extex.font.format.xtf.tables.gps;

import java.io.IOException;

import org.extex.font.format.xtf.tables.XtfGlyphName;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Class for a <code>ValueRecord</code>.
 * <p>
 * GPOS subtables use ValueRecords to describe all the variables and values used
 * to adjust the position of a glyph or set of glyphs. A ValueRecord may define
 * any combination of X and Y values (in design units) to add to (positive
 * values) or subtract from (negative values) the placement and advance values
 * provided in the font. A ValueRecord also may contain an offset to a Device
 * table for each of the specified values. If a ValueRecord specifies more than
 * one value, the values should be listed in the order shown in the ValueRecord
 * definition.
 * </p>
 * <p>
 * The text-processing client must be aware of the flexible and
 * multi-dimensional nature of ValueRecords in the GPOS table. Because the GPOS
 * table uses ValueRecords for many purposes, the sizes and contents of
 * ValueRecords may vary from subtable to subtable.
 * </p>
 * 
 * <p>
 * ValueRecord (all fields are optional)
 * </p>
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>int16</td>
 * <td>XPlacement</td>
 * <td>Horizontal adjustment for placement-in design units</td>
 * </tr>
 * <tr>
 * <td>int16</td>
 * <td>YPlacement</td>
 * <td>Vertical adjustment for placement-in design units</td>
 * </tr>
 * <tr>
 * <td>int16</td>
 * <td>XAdvance</td>
 * <td>Horizontal adjustment for advance-in design units (only used for
 * horizontal writing)</td>
 * </tr>
 * <tr>
 * <td>int16</td>
 * <td>YAdvance</td>
 * <td>Vertical adjustment for advance-in design units (only used for vertical
 * writing)</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>XPlaDevice</td>
 * <td>Offset to Device table for horizontal placement-measured from beginning
 * of PosTable (may be NULL)</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>YPlaDevice</td>
 * <td>Offset to Device table for vertical placement-measured from beginning of
 * PosTable (may be NULL)</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>XAdvDevice</td>
 * <td>Offset to Device table for horizontal advance-measured from beginning of
 * PosTable (may be NULL)</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>YAdvDevice</td>
 * <td>Offset to Device table for vertical advance-measured from beginning of
 * PosTable (may be NULL)</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class ValueRecord implements XMLWriterConvertible {

    /**
     * Horizontal adjustment for advance-in design units (only used for
     * horizontal writing).
     */
    private int xAdvance;

    /**
     * Offset to Device table for horizontal advance-measured from beginning of
     * PosTable (may be NULL).
     */
    private int xAdvDeviceOffset;

    /**
     * Horizontal adjustment for placement-in design units.
     */
    private int xPlacement;

    /**
     * Offset to Device table for horizontal placement-measured from beginning
     * of PosTable (may be NULL).
     */
    private int xPlaDeviceOffset;

    /**
     * Vertical adjustment for advance-in design units (only used for vertical
     * writing).
     */
    private int yAdvance;

    /**
     * Offset to Device table for vertical advance-measured from beginning of
     * PosTable (may be NULL)
     */
    private int yAdvDevice;

    /**
     * Vertical adjustment for placement-in design units.
     */
    private int yPlacement;

    /**
     * Offset to Device table for vertical placement-measured from beginning of
     * PosTable (may be NULL).
     */
    private int yPlaDeviceOffset;

    /**
     * The glyph name.
     */
    private XtfGlyphName xtfGlyph;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @param xtfGlyph The glyph name.
     * @throws IOException if a io-error occurred.
     */
    public ValueRecord(RandomAccessR rar, XtfGlyphName xtfGlyph)
            throws IOException {

        this.xtfGlyph = xtfGlyph;
        xPlacement = rar.readUnsignedShort();
        yPlacement = rar.readUnsignedShort();
        xAdvance = rar.readUnsignedShort();
        yAdvance = rar.readUnsignedShort();
        xPlaDeviceOffset = rar.readUnsignedShort();
        yPlaDeviceOffset = rar.readUnsignedShort();
        xAdvDeviceOffset = rar.readUnsignedShort();
        yAdvDevice = rar.readUnsignedShort();

    }

    /**
     * Getter for xAdvance.
     * 
     * @return the xAdvance
     */
    public int getXAdvance() {

        return xAdvance;
    }

    /**
     * Getter for xAdvDeviceOffset.
     * 
     * @return the xAdvDeviceOffset
     */
    public int getXAdvDeviceOffset() {

        return xAdvDeviceOffset;
    }

    /**
     * Getter for xPlacement.
     * 
     * @return the xPlacement
     */
    public int getXPlacement() {

        return xPlacement;
    }

    /**
     * Getter for xPlaDeviceOffset.
     * 
     * @return the xPlaDeviceOffset
     */
    public int getXPlaDeviceOffset() {

        return xPlaDeviceOffset;
    }

    /**
     * Getter for yAdvance.
     * 
     * @return the yAdvance
     */
    public int getYAdvance() {

        return yAdvance;
    }

    /**
     * Getter for yAdvDevice.
     * 
     * @return the yAdvDevice
     */
    public int getYAdvDevice() {

        return yAdvDevice;
    }

    /**
     * Getter for yPlacement.
     * 
     * @return the yPlacement
     */
    public int getYPlacement() {

        return yPlacement;
    }

    /**
     * Getter for yPlaDeviceOffset.
     * 
     * @return the yPlaDeviceOffset
     */
    public int getYPlaDeviceOffset() {

        return yPlaDeviceOffset;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("valuerecord");
        writer.writeAttribute("xAdvance", xAdvance);
        writer.writeAttribute("xAdvDeviceOffset", xAdvDeviceOffset);
        writer.writeAttribute("xPlacement", xPlacement);
        writer.writeAttribute("xPlaDeviceOffset", xPlaDeviceOffset);
        writer.writeAttribute("yAdvance", yAdvance);
        writer.writeAttribute("yAdvDevice", yAdvDevice);
        writer.writeAttribute("yPlacement", yPlacement);
        writer.writeAttribute("yPlaDeviceOffset", yPlaDeviceOffset);
        writer.writeEndElement();
    }
}
