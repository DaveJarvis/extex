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

import org.extex.util.file.random.RandomAccessR;

/**
 * Interface for a Lookup table factory.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public interface LookupTableFactory {

    /**
     * Read the LookupTable or <code>null</code>, if not found.
     * 
     * <p>
     * Type:
     * </p>
     * <nl>
     * <li>1 - Single - Replace one glyph with one glyph</li>
     * <li>2 - Multiple - Replace one glyph with more than one glyph</li>
     * <li>3 - Alternate - Replace one glyph with one of many glyphs</li>
     * <li>4 - Ligature - Replace multiple glyphs with one glyph</li>
     * <li>5 - Context - Replace one or more glyphs in context</li>
     * <li>6 - Chaining - Context Replace one or more glyphs in chained context</li>
     * </nl>
     * 
     * @param rar The input.
     * @param type The table type.
     * @param offset The offset.
     * @param xtfGlyph The glyph name.
     * @throws IOException if a io-error occurred.
     * @return Returns the table.
     */
    XtfLookupTable read(RandomAccessR rar, int type, int offset,
            XtfGlyphName xtfGlyph) throws IOException;

    /**
     * Returns the name of the lookup type (The start index is 1!).
     * 
     * @param type The type.
     * @return Returns the name of the lookup type.
     */
    String lookupType(int type);

}
