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

package org.extex.font.format;

import org.extex.font.ExtexFont;
import org.extex.font.FontKey;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.type.UnicodeChar;

/**
 * Null extex font.
 *
 * mgn: incomplete
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class NullExtexFont implements ExtexFont {

    /**
     * @see org.extex.font.ExtexFont#getActualSize()
     */
    public FixedDimen getActualSize() {

        // TODO mgn: getActualSize unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getDepth(org.extex.type.UnicodeChar)
     */
    public FixedGlue getDepth(UnicodeChar uc) {

        // TODO mgn: getDepth unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        // TODO mgn: getDesignSize unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getEm()
     */
    public FixedDimen getEm() {

        // TODO mgn: getEm unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getEx()
     */
    public FixedDimen getEx() {

        // TODO mgn: getEx unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(String name) {

        // TODO mgn: getFontDimen unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getFontName()
     */
    public String getFontName() {

        // TODO mgn: getFontName unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getHeight(org.extex.type.UnicodeChar)
     */
    public FixedGlue getHeight(UnicodeChar uc) {

        // TODO mgn: getHeight unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getItalicCorrection(org.extex.type.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(UnicodeChar uc) {

        // TODO mgn: getItalicCorrection unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getKerning(org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
     */
    public FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2) {

        // TODO mgn: getKerning unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getLigature(org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
     */
    public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

        // TODO mgn: getLigature unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getScaleFactor()
     */
    public FixedCount getScaleFactor() {

        // TODO mgn: getScaleFactor unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getSpace()
     */
    public FixedGlue getSpace() {

        // TODO mgn: getSpace unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getWidth(org.extex.type.UnicodeChar)
     */
    public FixedGlue getWidth(UnicodeChar uc) {

        // TODO mgn: getWidth unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#hasGlyph(org.extex.type.UnicodeChar)
     */
    public boolean hasGlyph(UnicodeChar uc) {

        // TODO mgn: hasGlyph unimplemented
        return false;
    }

    /**
     * @see org.extex.font.BaseFont#getActualFontKey()
     */
    public FontKey getActualFontKey() {

        // TODO mgn: getActualFontKey unimplemented
        return null;
    }

    /**
     * @see org.extex.font.BaseFont#getFontKey()
     */
    public FontKey getFontKey() {

        // TODO mgn: getFontKey unimplemented
        return null;
    }

}
