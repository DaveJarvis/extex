/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.tc.font.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.FontKey;
import org.extex.font.FontKeyFactory;
import org.extex.typesetter.tc.font.Font;

/**
 * This class implements a dummy font which does not contain any characters.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class NullFont implements Font, Serializable {

    /**
     * The field {@code DEFAULT_EF_CODE} contains the default value for the
     * ef code.
     */
    private static final int DEFAULT_EF_CODE = 1000;

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code efCode} contains the ef code.
     */
    private Map<UnicodeChar, Long> efCode = null;

    /**
     * The field {@code fontDimens} contains the map for font dimens.
     */
    private Map<String, Dimen> fontDimens = null;

    /**
     * The field {@code hyphen} contains the hyphen char for this font.
     */
    private UnicodeChar hyphen = UnicodeChar.get('-');

    /**
     * The field {@code skew} contains the skew char for this font.
     */
    private UnicodeChar skew = null;


    public NullFont() {

    }

    /**
     * Returns the actual FontKey for this font.
     * The font key may differ from the one requested.
     *
     * @return the actual FontKey for this font.
     *
     * @see org.extex.typesetter.tc.font.Font#getActualFontKey()
     */
    public FontKey getActualFontKey() {

        return null;
    }

    /**
     * Returns the actual size.
     *
     * @return the actual size
     *
     * @see org.extex.typesetter.tc.font.Font#getActualSize()
     */
    public FixedDimen getActualSize() {

        return Dimen.ZERO_PT;
    }

    /**
     * Returns the check sum.
     *
     * @return the check sum
     *
     * @see org.extex.typesetter.tc.font.Font#getCheckSum()
     */
    public int getCheckSum() {

        return -1;
    }

    /**
     * Returns the depth of the char.
     *
     * @param uc the Unicode char
     *
     * @return the depth of the char
     *
     * @see org.extex.typesetter.tc.font.Font#getDepth(
     *      org.extex.core.UnicodeChar)
     */
    public FixedGlue getDepth(UnicodeChar uc) {

        return FixedGlue.ZERO;
    }

    /**
     * Returns the design size.
     *
     * @return the design size
     *
     * @see org.extex.typesetter.tc.font.Font#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return Dimen.ZERO_PT;
    }

    /**
     * Getter for the ef code.
     *
     * @param uc the character
     *
     * @return the ef code
     *
     * @see org.extex.typesetter.tc.font.Font#getEfCode(
     *      org.extex.core.UnicodeChar)
     */
    public long getEfCode(UnicodeChar uc) {

        if (efCode == null) {
            return DEFAULT_EF_CODE;
        }
        Long code = efCode.get(uc);
        return (code == null ? DEFAULT_EF_CODE : code.longValue());
    }

    /**
     * Returns the size of 1em.
     *
     * @return the size of 1em.
     *
     * @see org.extex.typesetter.tc.font.Font#getEm()
     */
    public FixedDimen getEm() {

        return Dimen.ZERO_PT;
    }

    /**
     * Returns the size of 1ex.
     *
     * @return the size of 1ex.
     *
     * @see org.extex.typesetter.tc.font.Font#getEx()
     */
    public FixedDimen getEx() {

        return Dimen.ZERO_PT;
    }

    /**
     * Returns the size of the parameter with the name 'name'.
     *
     * @param name the name of the parameter
     * @return the size of the parameter with the name 'name'
     *
     * @see org.extex.typesetter.tc.font.Font#getFontDimen(
     *      java.lang.String)
     */
    public FixedDimen getFontDimen(String name) {

        return (fontDimens == null ? null : fontDimens.get( name));
    }

    /**
     * Returns the key for the font.
     *
     * @return the key for the font
     *
     * @see org.extex.typesetter.tc.font.Font#getFontKey()
     */
    public FontKey getFontKey() {

        return FontKeyFactory.NULL_KEY;
    }

    /**
     * Returns the name of the font.
     *
     * @return Returns the name of the font.
     *
     * @see org.extex.typesetter.tc.font.Font#getFontName()
     */
    public String getFontName() {

        return "nullfont";
    }

    /**
     * Returns the height of the char.
     *
     * @param uc the Unicode char
     * @return the height of the char
     *
     * @see org.extex.typesetter.tc.font.Font#getHeight(
     *      org.extex.core.UnicodeChar)
     */
    public FixedGlue getHeight(UnicodeChar uc) {

        return null;
    }

    /**
     * Returns the hyphen char.
     *
     * @return the hyphen char
     *
     * @see org.extex.typesetter.tc.font.Font#getHyphenChar()
     */
    public UnicodeChar getHyphenChar() {

        return hyphen;
    }

    /**
     * Returns the italic correction of the char.
     *
     * @param uc the char
     *
     * @return the italic correction of the char
     *
     * @see org.extex.typesetter.tc.font.Font#getItalicCorrection(
     *      org.extex.core.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(UnicodeChar uc) {

        return Dimen.ZERO_PT;
    }

    /**
     * Returns the kerning between two chars.
     *
     * @param uc1 the first char
     * @param uc2 the second char
     * @return the kerning between two chars
     *
     * @see org.extex.typesetter.tc.font.Font#getKerning(
     *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar)
     */
    public FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2) {

        return Dimen.ZERO_PT;
    }

    /**
     * Returns the ligature for two chars.
     *
     * @param uc1 the first char
     * @param uc2 the second char
     * @return the ligature for two chars
     *
     * @see org.extex.typesetter.tc.font.Font#getLigature(
     *      org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

        return null;
    }

    /**
     * Returns the scale factor of the font.
     *
     * @return the scale factor of the font
     *
     * @see org.extex.typesetter.tc.font.Font#getScaleFactor()
     */
    public FixedCount getScaleFactor() {

        return Count.THOUSAND;
    }

    /**
     * Returns the skew character.
     *
     * @return the skew character
     *
     * @see org.extex.typesetter.tc.font.Font#getSkewChar()
     */
    public UnicodeChar getSkewChar() {

        return skew;
    }

    /**
     * Returns the size of the 'space'.
     *
     * @return the size of the 'space'
     *
     * @see org.extex.typesetter.tc.font.Font#getSpace()
     */
    public FixedGlue getSpace() {

        return new Glue();
    }

    /**
     * Returns the width of the character.
     *
     * @param uc the character
     * @return the width of the character
     *
     * @see org.extex.typesetter.tc.font.Font#getWidth(
     *      org.extex.core.UnicodeChar)
     */
    public FixedGlue getWidth(UnicodeChar uc) {

        return null;
    }

    /**
     * Determine whether the glyph for a given character is present in this font.
     *
     * @param uc the character
     *
     * @return {@code true} iff the glyph is present
     *
     * @see org.extex.typesetter.tc.font.Font#hasGlyph(
     *      org.extex.core.UnicodeChar)
     */
    public boolean hasGlyph(UnicodeChar uc) {

        return false;
    }

    /**
     * Setter for the ef code.
     * The ef code influences the stretchability of characters. It has a
     * positive value. 1000 means "normal" stretchability.
     *
     * @param uc the character
     * @param code the associated code
     *
     * @see org.extex.typesetter.tc.font.Font#setEfCode(
     *      org.extex.core.UnicodeChar, long)
     */
    public void setEfCode(UnicodeChar uc, long code) {

        if (efCode == null) {
            efCode = new HashMap<UnicodeChar, Long>();
        }
        efCode.put(uc, Long.valueOf(code));
    }

    /**
     * Set the new value for the font parameter.
     *
     * @param name  the name of the parameter
     * @param value the value to set
     *
     * @see org.extex.typesetter.tc.font.Font#setFontDimen(
     *      java.lang.String, org.extex.core.dimen.Dimen)
     */
    public void setFontDimen(String name, Dimen value) {

        if (fontDimens == null) {
            fontDimens = new HashMap<String, Dimen>();
        }
        fontDimens.put(name, value);
    }

    /**
     * Set the hyphen char.
     *
     * @param uc the hyphen char
     *
     * @see org.extex.typesetter.tc.font.Font#setHyphenChar(
     *      org.extex.core.UnicodeChar)
     */
    public void setHyphenChar(UnicodeChar uc) {

        this.hyphen = uc;
    }

    /**
     * Set the skew char.
     *
     * @param uc the skew char
     *
     * @see org.extex.typesetter.tc.font.Font#setSkewChar(
     *      org.extex.core.UnicodeChar)
     */
    public void setSkewChar(UnicodeChar uc) {

        this.skew = uc;
    }

}
