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

/**
 * Class for a Coverage Table.
 * <p>
 * Each subtable (except an Extension LookupType subtable) in a lookup
 * references a Coverage table (Coverage), which specifies all the glyphs
 * affected by a substitution or positioning operation described in the
 * subtable. The GSUB, GPOS, and GDEF tables rely on this notion of coverage. If
 * a glyph does not appear in a Coverage table, the client can skip that
 * subtable and move immediately to the next subtable.
 * </p>
 * <p>
 * A Coverage table identifies glyphs by glyph indices (GlyphIDs) either of two
 * ways:
 * </p>
 * <ul>
 * <li>As a list of individual glyph indices in the glyph set.</li>
 * <li>As ranges of consecutive indices. The range format gives a number of
 * start-glyph and end-glyph index pairs to denote the consecutive glyphs
 * covered by the table.</li>
 * </ul>
 * <p>
 * In a Coverage table, a format code (CoverageFormat) specifies the format as
 * an integer: 1 = lists, and 2 = ranges.
 * </p>
 * <p>
 * A Coverage table defines a unique index value (Coverage Index) for each
 * covered glyph. This unique value specifies the position of the covered glyph
 * in the Coverage table. The client uses the Coverage Index to look up values
 * in the subtable for each glyph.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5438 $
 */
public abstract class CoverageTable implements XMLWriterConvertible {

    /**
     * The CoverageFormat.
     */
    private int coverageFormat;

    /**
     * Getter for coverageFormat.
     * 
     * @return the coverageFormat
     */
    public int getCoverageFormat() {

        return coverageFormat;
    }

    /**
     * Creates a new object.
     * 
     * @param format The format
     */
    protected CoverageTable(int format) {

        coverageFormat = format;
    }

    /**
     * Return a instance.
     * 
     * @param rar input
     * @param offset The offset for the table.
     * @param baseoffset The baseofset.
     * @throws IOException if a io-error occurred.
     */
    public static CoverageTable getInstance(RandomAccessR rar, int baseoffset,
            int offset) throws IOException {

        rar.seek(baseoffset + offset);

        int format = rar.readUnsignedShort();

        switch (format) {
            case 1:
                return new CoverageTable1(format, rar);
            case 2:
                return new CoverageTable2(format, rar);
            default:
                throw new IOException("wrong format tpye");
        }
    }
}
