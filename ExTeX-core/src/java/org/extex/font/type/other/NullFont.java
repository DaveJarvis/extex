/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.type.other;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.extex.font.FontByteArray;
import org.extex.font.FontKey;
import org.extex.font.FontKeyFactory;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.glue.Glue;
import org.extex.type.UnicodeChar;

/**
 * This class implements a dummy font which does not contain any characters.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class NullFont implements Font, Serializable {

    /**
     * The field <tt>DEFAULT_EF_CODE</tt> contains the default value for the
     * ef code.
     */
    private static final int DEFAULT_EF_CODE = 1000;

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>efCode</tt> contains the ef code.
     */
    private Map efCode = null;

    /**
     * The field <tt>fontDimens</tt> contains the map for font dimens.
     */
    private Map fontDimens = null;

    /**
     * The field <tt>hyphen</tt> contains the hyphen char for this font.
     */
    private UnicodeChar hyphen = UnicodeChar.get('-');

    /**
     * The field <tt>skew</tt> contains the skew char for this font.
     */
    private UnicodeChar skew = null;

    /**
     * Creates a new object.
     */
    public NullFont() {

        super();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getActualFontKey()
     */
    public FontKey getActualFontKey() {

        return null;
    }

    /**
     * Returns the actual size.
     *
     * @return the actual size
     *
     * @see org.extex.font.type.Fount#getActualSize()
     */
    public FixedDimen getActualSize() {

        return Dimen.ZERO_PT;
    }

    /**
     * Returns the check sum.
     *
     * @return the check sum
     *
     * @see org.extex.font.type.Fount#getCheckSum()
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
     * @see org.extex.interpreter.type.font.Font#getDepth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getDepth(final UnicodeChar uc) {

        return Glue.ZERO;
    }

    /**
     * Returns the design size.
     *
     * @return the design size
     *
     * @see org.extex.font.type.Fount#getDesignSize()
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
     * @see org.extex.interpreter.type.font.Font#getEfCode(
     *      org.extex.type.UnicodeChar)
     */
    public long getEfCode(final UnicodeChar uc) {

        if (efCode == null) {
            return DEFAULT_EF_CODE;
        }
        Long code = (Long) efCode.get(uc);
        return (code == null ? DEFAULT_EF_CODE : code.longValue());
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getEm()
     */
    public FixedDimen getEm() {

        return Dimen.ZERO_PT;
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getEx()
     */
    public FixedDimen getEx() {

        return Dimen.ZERO_PT;
    }

    /**
     * @see org.extex.font.type.Fount#getFontByteArray()
     */
    public FontByteArray getFontByteArray() {

        return null;
    }

    /**
     * Returns the size of the parameter with the name 'name'.
     *
     * @param name the name of the parameter
     * @return the size of the parameter with the name 'name'
     *
     * @see org.extex.interpreter.type.font.Font#getFontDimen(
     *      java.lang.String)
     */
    public FixedDimen getFontDimen(final String name) {

        return (fontDimens == null ? null : (FixedDimen) fontDimens.get(name));
    }

    /**
     * Returns the key for the font.
     *
     * @return the key for the font
     *
     * @see org.extex.font.type.Fount#getFontKey()
     */
    public FontKey getFontKey() {

        return FontKeyFactory.NULL_KEY;
    }

    /**
     * Returns the name of the font.
     *
     * @return Returns the name of the font.
     *
     * @see org.extex.interpreter.type.font.Font#getFontName()
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
     * @see org.extex.interpreter.type.font.Font#getHeight(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getHeight(final UnicodeChar uc) {

        return null;
    }

    /**
     * Returns the hyphen char.
     *
     * @return the hyphen char
     *
     * @see org.extex.interpreter.type.font.Font#getHyphenChar()
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
     * @see org.extex.interpreter.type.font.Font#getItalicCorrection(
     *      org.extex.type.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(final UnicodeChar uc) {

        return Dimen.ZERO_PT;
    }

    /**
     * Returns the kerning between two chars.
     *
     * @param uc1 the first char
     * @param uc2 the second char
     * @return the kerning between two chars
     *
     * @see org.extex.interpreter.type.font.Font#getKerning(
     *      org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
     */
    public FixedDimen getKerning(final UnicodeChar uc1, final UnicodeChar uc2) {

        return Dimen.ZERO_PT;
    }

    /**
     * Returns the ligature for two chars.
     *
     * @param uc1 the first char
     * @param uc2 the second char
     * @return the ligature for two chars
     *
     * @see org.extex.interpreter.type.font.Font#getLigature(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar)
     */
    public UnicodeChar getLigature(final UnicodeChar uc1, final UnicodeChar uc2) {

        return null;
    }

    /**
     * Returns the scale factor of the font.
     *
     * @return the scale factor of the font
     *
     * @see org.extex.interpreter.type.font.Font#getScaleFactor()
     */
    public FixedCount getScaleFactor() {

        return Count.THOUSAND;
    }

    /**
     * Returns the skew character.
     *
     * @return the skew character
     *
     * @see org.extex.interpreter.type.font.Font#getSkewChar()
     */
    public UnicodeChar getSkewChar() {

        return skew;
    }

    /**
     * Returns the size of the 'space'.
     *
     * @return the size of the 'space'
     *
     * @see org.extex.interpreter.type.font.Font#getSpace()
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
     * @see org.extex.interpreter.type.font.Font#getWidth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getWidth(final UnicodeChar uc) {

        return null;
    }

    /**
     * Determine whether the glyph for a given character is present in this font.
     *
     * @param uc the character
     *
     * @return <code>true</code> iff the glyph is present
     *
     * @see org.extex.interpreter.type.font.Font#hasGlyph(
     *      org.extex.type.UnicodeChar)
     */
    public boolean hasGlyph(final UnicodeChar uc) {

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
     * @see org.extex.interpreter.type.font.Font#setEfCode(
     *      org.extex.type.UnicodeChar, long)
     */
    public void setEfCode(final UnicodeChar uc, final long code) {

        if (efCode == null) {
            efCode = new HashMap();
        }
        efCode.put(uc, new Long(code));
    }

    /**
     * Set the new value for the font parameter.
     *
     * @param name  the name of the parameter
     * @param value the value to set
     *
     * @see org.extex.interpreter.type.font.Font#setFontDimen(
     *      java.lang.String, org.extex.interpreter.type.dimen.Dimen)
     */
    public void setFontDimen(final String name, final Dimen value) {

        if (fontDimens == null) {
            fontDimens = new HashMap();
        }
        fontDimens.put(name, value);
    }

    /**
     * Set the hyphen char.
     *
     * @param uc the hyphen char
     *
     * @see org.extex.interpreter.type.font.Font#setHyphenChar(
     *      org.extex.type.UnicodeChar)
     */
    public void setHyphenChar(final UnicodeChar uc) {

        this.hyphen = uc;
    }

    /**
     * Set the skew char.
     *
     * @param uc the skew char
     *
     * @see org.extex.interpreter.type.font.Font#setSkewChar(
     *      org.extex.type.UnicodeChar)
     */
    public void setSkewChar(final UnicodeChar uc) {

        this.skew = uc;
    }

}
