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

import org.extex.util.file.random.RandomAccessR;

/**
 * Class for a ClassRangeRecord.
 * 
 * <p>
 * Each ClassRangeRecord consists of a Start glyph index, an End glyph index,
 * and a Class value. All GlyphIDs in a range, from Start to End inclusive,
 * constitute the class identified by the Class value. Any glyph not covered by
 * a ClassRangeRecord is assumed to belong to Class 0.
 * </p>
 * 
 * <table>
 * <caption>TBD</caption>
 * <tr>
* <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>GlyphID</td>
 * <td>Start</td>
 * <td>First GlyphID in the range</td>
 * </tr>
 * <tr>
 * <td>GlyphID</td>
 * <td>End</td>
 * <td>Last GlyphID in the range</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>Class</td>
 * <td>Applied to all glyphs in the range</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class ClassRangeRecord {

    /**
     * Last GlyphID in the range.
     */
    private final int end;

    /**
     * Applied to all glyphs in the range.
     */
    private final int gclass;

    /**
     * First GlyphID in the range.
     */
    private final int start;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @throws IOException if a IO-error occurred.
     */
    public ClassRangeRecord(RandomAccessR rar) throws IOException {

        start = rar.readUnsignedShort();
        end = rar.readUnsignedShort();
        gclass = rar.readUnsignedShort();
    }

    /**
     * Getter for end.
     * 
     * @return the end
     */
    public int getEnd() {

        return end;
    }

    /**
     * Getter for gclass.
     * 
     * @return the gclass
     */
    public int getGclass() {

        return gclass;
    }

    /**
     * Getter for start.
     * 
     * @return the start
     */
    public int getStart() {

        return start;
    }
}
