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
     * The MASK for the ValueFormat.
     */
    public static final int[] MASK =
            {0x0001, 0x0002, 0x0004, 0x0008, 0x0010, 0x0020, 0x0040, 0x0080,
                    0xF000};

    /**
     * The Name for the ValueFormat.
     */
    public static final String[] NAME =
            {"XPlacement", "YPlacement", "XAdvance", "YAdvance", "XPlaDevice",
                    "YPlaDevice", "XAdvDevice", "YAdvDevice", "Reserved"};

    /**
     * For future use.
     */
    public static final int Reserved = 8;

    /**
     * Includes horizontal adjustment for advance.
     */
    public static final int XAdvance = 2;

    /**
     * Includes horizontal Device table for advance.
     */
    public static final int XAdvDevice = 6;

    /**
     * Includes horizontal adjustment for placement.
     */
    public static final int XPlacement = 0;

    /**
     * Includes horizontal Device table for placement.
     */
    public static final int XPlaDevice = 4;

    /**
     * Includes vertical adjustment for advance.
     */
    public static final int YAdvance = 3;

    /**
     * Includes vertical Device table for advance.
     */
    public static final int YAdvDevice = 7;

    /**
     * Includes vertical adjustment for placement.
     */
    public static final int YPlacement = 1;

    /**
     * Includes vertical Device table for placement.
     */
    public static final int YPlaDevice = 5;

    /**
     * Returns the mask of the ValueFormat or -1, if not found.
     * 
     * @param value The value.
     * @return Returns the mask of the ValueFormat or -1, if not found.
     */
    public static int getMask(int value) {

        if (value >= 0 && value < NAME.length) {
            return MASK[value];
        }
        return -1;

    }

    /**
     * Returns the length of the mask array.
     * 
     * @return Returns the length of the mask array.
     */
    public static int getMaskLength() {

        return MASK.length;
    }

    /**
     * Returns the name of the ValueFormat.
     * 
     * @param value The value.
     * @return Returns the name of the ValueFormat.
     */
    public static String getName(int value) {

        if (value >= 0 && value < NAME.length) {
            return NAME[value];
        }
        return "???";
    }

    /**
     * ValueRecord use XAdvance.
     */
    private boolean isXAdvance = false;

    /**
     * ValueRecord use XAdvDevice.
     */
    private boolean isXAdvDevice = false;

    /**
     * ValueRecord use XPlacement.
     */
    private boolean isXPlacement = false;

    /**
     * ValueRecord use XPlaDevice.
     */
    private boolean isXPlaDevice = false;

    /**
     * ValueRecord use YAdvance.
     */
    private boolean isYAdvance = false;

    /**
     * ValueRecord use YAdvDevice.
     */
    private boolean isYAdvDevice = false;

    /**
     * ValueRecord use YPlacement.
     */
    private boolean isYPlacement = false;

    /**
     * ValueRecord use YPlaDevice.
     */
    private boolean isYPlaDevice = false;

    /**
     * A data format (ValueFormat), usually declared at the beginning of each
     * GPOS subtable, defines the types of positioning adjustment data that
     * ValueRecords specify. Usually, the same ValueFormat applies to every
     * ValueRecord defined in the particular GPOS subtable. The ValueFormat
     * determines whether the ValueRecords: Apply to placement, advance, or
     * both. Apply to the horizontal position (X coordinate), the vertical
     * position (Y coordinate), or both. May refer to one or more Device tables
     * for any of the specified values. Each one-bit in the ValueFormat
     * corresponds to a field in the ValueRecord and increases the size of the
     * ValueRecord by 2 bytes. A ValueFormat of 0x0000 corresponds to an empty
     * ValueRecord, which indicates no positioning changes. To identify the
     * fields in each ValueRecord, the ValueFormat uses the bit settings shown
     * below. To specify multiple fields with a ValueFormat, the bit settings of
     * the relevant fields are added with a logical OR operation.
     */
    private int valueFormat;

    /**
     * Horizontal adjustment for advance-in design units (only used for
     * horizontal writing).
     */
    private int xAdvance;

    /**
     * DeviceTable: xAdvDevice.
     */
    private DeviceTable xAdvDevice;

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
     * DeviceTable: xPlaDevice.
     */
    private DeviceTable xPlaDevice;

    /**
     * Offset to Device table for horizontal placement-measured from beginning
     * of PosTable (may be NULL).
     */
    private int xPlaDeviceOffset;

    // /**
    // * The glyph name.
    // */
    // private XtfGlyphName xtfGlyph;

    /**
     * Vertical adjustment for advance-in design units (only used for vertical
     * writing).
     */
    private int yAdvance;

    /**
     * DeviceTable: yAdvDevice.
     */
    private DeviceTable yAdvDevice;

    /**
     * Offset to Device table for vertical advance-measured from beginning of
     * PosTable (may be NULL)
     */
    private int yAdvDeviceOffset;

    /**
     * Vertical adjustment for placement-in design units.
     */
    private int yPlacement;

    /**
     * DeviceTable: yPlaDevice.
     */
    private DeviceTable yPlaDevice;

    /**
     * Offset to Device table for vertical placement-measured from beginning of
     * PosTable (may be NULL).
     */
    private int yPlaDeviceOffset;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @param offset The offset of the pos table.
     * @param xtfGlyph The glyph name.
     * @param valueFormat The ValueFormat.
     * @throws IOException if a io-error occurred.
     */
    public ValueRecord(RandomAccessR rar, int offset, XtfGlyphName xtfGlyph,
            int valueFormat) throws IOException {

        // this.xtfGlyph = xtfGlyph;
        this.valueFormat = valueFormat;

        xPlacement = rar.readUnsignedShort();
        yPlacement = rar.readUnsignedShort();
        xAdvance = rar.readUnsignedShort();
        yAdvance = rar.readUnsignedShort();
        xPlaDeviceOffset = rar.readUnsignedShort();
        yPlaDeviceOffset = rar.readUnsignedShort();
        xAdvDeviceOffset = rar.readUnsignedShort();
        yAdvDeviceOffset = rar.readUnsignedShort();

        for (int i = 0; i < MASK.length; i++) {
            int v = valueFormat & getMask(i);
            if (v > 0) {
                switch (i) {
                    case XPlacement:
                        isXPlacement = true;
                        break;
                    case YPlacement:
                        isYPlacement = true;
                        break;
                    case XAdvance:
                        isXAdvance = true;
                        break;
                    case YAdvance:
                        isYAdvance = true;
                        break;
                    case XPlaDevice:
                        isXPlaDevice = true;
                        break;
                    case YPlaDevice:
                        isYPlaDevice = true;
                        break;
                    case XAdvDevice:
                        isXAdvDevice = true;
                        break;
                    case YAdvDevice:
                        isYAdvDevice = true;
                        break;
                }
            }
        }

    }

    /**
     * Getter for valueFormat.
     * 
     * @return the valueFormat
     */
    public int getValueFormat() {

        return valueFormat;
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
     * Getter for xAdvDevice.
     * 
     * @return the xAdvDevice
     */
    public DeviceTable getXAdvDevice() {

        return xAdvDevice;
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
     * Getter for xPlaDevice.
     * 
     * @return the xPlaDevice
     */
    public DeviceTable getXPlaDevice() {

        return xPlaDevice;
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
     * Getter for yAdvDeviceOffset.
     * 
     * @return the yAdvDeviceOffset
     */
    public int getYAdvDevice() {

        return yAdvDeviceOffset;
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
     * Getter for yPlaDevice.
     * 
     * @return the yPlaDevice
     */
    public DeviceTable getYPlaDevice() {

        return yPlaDevice;
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
     * Initialize.
     * <p>
     * Read the DeviceTable
     * </p>
     * 
     * @param rar The input.
     * @param offset The ofset.
     * @throws IOException if a IO-error occurred.
     */
    public void init(RandomAccessR rar, int offset) throws IOException {

        if (isXPlaDevice && xPlaDeviceOffset != 0) {
            xPlaDevice = new DeviceTable(rar, offset + xPlaDeviceOffset);
        }
        if (isYPlaDevice && yPlaDeviceOffset != 0) {
            yPlaDevice = new DeviceTable(rar, offset + yPlaDeviceOffset);
        }
        if (isXAdvDevice && xAdvDeviceOffset != 0) {
            xAdvDevice = new DeviceTable(rar, offset + xAdvDeviceOffset);
        }
        if (isYAdvDevice && yAdvDeviceOffset != 0) {
            yAdvDevice = new DeviceTable(rar, offset + yAdvDeviceOffset);
        }
    }

    /**
     * Getter for isXAdvance.
     * 
     * @return the isXAdvance
     */
    public boolean isXAdvance() {

        return isXAdvance;
    }

    /**
     * Getter for isXAdvDevice.
     * 
     * @return the isXAdvDevice
     */
    public boolean isXAdvDevice() {

        return isXAdvDevice;
    }

    /**
     * Getter for isXPlacement.
     * 
     * @return the isXPlacement
     */
    public boolean isXPlacement() {

        return isXPlacement;
    }

    /**
     * Getter for isXPlaDevice.
     * 
     * @return the isXPlaDevice
     */
    public boolean isXPlaDevice() {

        return isXPlaDevice;
    }

    /**
     * Getter for isYAdvance.
     * 
     * @return the isYAdvance
     */
    public boolean isYAdvance() {

        return isYAdvance;
    }

    /**
     * Getter for isYAdvDevice.
     * 
     * @return the isYAdvDevice
     */
    public boolean isYAdvDevice() {

        return isYAdvDevice;
    }

    /**
     * Getter for isYPlacement.
     * 
     * @return the isYPlacement
     */
    public boolean isYPlacement() {

        return isYPlacement;
    }

    /**
     * Getter for isYPlaDevice.
     * 
     * @return the isYPlaDevice
     */
    public boolean isYPlaDevice() {

        return isYPlaDevice;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("valuerecord");
        writer.writeAttribute("valueFormat", valueFormat);

        if (isXPlacement) {
            writer.writeAttribute("xPlacement", xPlacement);
        }
        if (isYPlacement) {
            writer.writeAttribute("yPlacement", yPlacement);
        }
        if (isXAdvance) {
            writer.writeAttribute("xAdvance", xAdvance);
        }
        if (isYAdvance) {
            writer.writeAttribute("yAdvance", yAdvance);
        }

        // writer.writeAttribute("xAdvDeviceOffset", xAdvDeviceOffset);
        // writer.writeAttribute("xPlaDeviceOffset", xPlaDeviceOffset);
        // writer.writeAttribute("yAdvDeviceOffset", yAdvDeviceOffset);
        // writer.writeAttribute("yPlaDeviceOffset", yPlaDeviceOffset);

        if (isXPlaDevice && xPlaDevice != null) {
            xPlaDevice.writeXML(writer);
        }
        if (isYPlaDevice && yPlaDevice != null) {
            yPlaDevice.writeXML(writer);
        }
        if (isXAdvDevice && xAdvDevice != null) {
            xAdvDevice.writeXML(writer);
        }
        if (isYAdvDevice && yAdvDevice != null) {
            yAdvDevice.writeXML(writer);
        }
        writer.writeEndElement();
    }
}
