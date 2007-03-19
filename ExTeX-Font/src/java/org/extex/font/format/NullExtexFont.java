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

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.ExtexFont;
import org.extex.font.FontKey;

/**
 * This class implements a dummy font which does not contain any characters.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class NullExtexFont implements ExtexFont {

    /**
     * @see org.extex.font.BaseFont#getActualFontKey()
     */
    public FontKey getActualFontKey() {

        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getActualSize()
     */
    public FixedDimen getActualSize() {

        return Dimen.ZERO_PT;
    }

    /**
     * @see org.extex.font.ExtexFont#getDepth(org.extex.core.UnicodeChar)
     */
    public FixedGlue getDepth(final UnicodeChar uc) {

        return Glue.ZERO;
    }

    /**
     * @see org.extex.font.ExtexFont#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return Dimen.ONE_PT;
    }

    /**
     * @see org.extex.font.ExtexFont#getEm()
     */
    public FixedDimen getEm() {

        return Dimen.ZERO_PT;
    }

    /**
     * @see org.extex.font.ExtexFont#getEx()
     */
    public FixedDimen getEx() {

        return Dimen.ZERO_PT;
    }

    /**
     * @see org.extex.font.ExtexFont#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(final String name) {

        return null;
    }

    /**
     * @see org.extex.font.BaseFont#getFontKey()
     */
    public FontKey getFontKey() {

        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getFontName()
     */
    public String getFontName() {

        return "nullfont";
    }

    /**
     * @see org.extex.font.ExtexFont#getHeight(org.extex.core.UnicodeChar)
     */
    public FixedGlue getHeight(final UnicodeChar uc) {

        return Glue.ZERO;
    }

    /**
     * @see org.extex.font.ExtexFont#getItalicCorrection(org.extex.core.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(final UnicodeChar uc) {

        return Dimen.ZERO_PT;
    }

    /**
     * @see org.extex.font.ExtexFont#getKerning(org.extex.core.UnicodeChar,
     *       org.extex.core.UnicodeChar)
     */
    public FixedDimen getKerning(final UnicodeChar uc1, final UnicodeChar uc2) {

        return Dimen.ZERO_PT;
    }

    /**
     * @see org.extex.font.ExtexFont#getLigature(org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    public UnicodeChar getLigature(final UnicodeChar uc1, final UnicodeChar uc2) {

        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getScaleFactor()
     */
    public FixedCount getScaleFactor() {

        return Count.THOUSAND;
    }

    /**
     * @see org.extex.font.ExtexFont#getSpace()
     */
    public FixedGlue getSpace() {

        return Glue.ZERO;
    }

    /**
     * @see org.extex.font.ExtexFont#getWidth(org.extex.core.UnicodeChar)
     */
    public FixedGlue getWidth(final UnicodeChar uc) {

        return Glue.ZERO;
    }

    /**
     * @see org.extex.font.ExtexFont#hasGlyph(org.extex.core.UnicodeChar)
     */
    public boolean hasGlyph(final UnicodeChar uc) {

        return false;
    }

}
