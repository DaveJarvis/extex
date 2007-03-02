/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.listMaker.math;

import org.extex.font.FontByteArray;
import org.extex.font.FontKey;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.type.UnicodeChar;

/**
 * This class encapsulates a font and provides access to the font dimens with
 * convenience methods.
 *
 *
 * @see "TTP [700]"
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4728 $
 */
public class MathFont implements Font {

    /**
     * The field <tt>font</tt> contains the encapsulated font.
     */
    private Font font;

    /**
     * Creates a new object.
     *
     * @param font the font encapsulated
     */
    public MathFont(final Font font) {

        super();
        this.font = font;
    }

    /**
     * Returns the actual FontKey for this font.
     * The font key may differ from the one requested.
     *
     * @return the actual FontKey for this font.
     *
     * @see org.extex.interpreter.type.font.Font#getActualFontKey()
     */
    public FontKey getActualFontKey() {

        // TODO getActualFontKey unimplemented
        return null;
    }

    /**
     * Returns the actual size of the font.
     *
     * @return the actual size of the font.
     *
     * @see org.extex.interpreter.type.font.Font#getActualSize()
     */
    public FixedDimen getActualSize() {

        // TODO getActualsize unimplemented
        return null;
    }

    /**
     * Returns the check sum of the font.
     *
     * @return the check sum of the font
     *
     * @see org.extex.interpreter.type.font.Font#getCheckSum()
     */
    public int getCheckSum() {

        return 0; //this.font.getCheckSum();
    }

    /**
     * Returns the depth of the character.
     *
     * @param uc the character
     *
     * @return the depth of the character
     *
     * @see org.extex.interpreter.type.font.Font#getDepth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getDepth(final UnicodeChar uc) {

        return font.getDepth(uc);
    }

    /**
     * Returns the design size of the font.
     *
     * @return the design size of the font
     *
     * @see org.extex.interpreter.type.font.Font#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return font.getDesignSize();
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

        return font.getEfCode(uc);
    }

    /**
     * Returns the size of 'M'.
     *
     * @return the size of 'M'.
     *
     * @see org.extex.interpreter.type.font.Font#getEm()
     */
    public FixedDimen getEm() {

        return this.font.getEm();
    }

    /**
     * Returns the size of 'x'.
     *
     * @return Returns the size of 'x'.
     *
     * @see org.extex.interpreter.type.font.Font#getEx()
     */
    public FixedDimen getEx() {

        return this.font.getEx();
    }

    /**
     * @see org.extex.font.type.Fount#getFontByteArray()
     */
    public FontByteArray getFontByteArray() {

        return null; //this.font.getFontByteArray(); // add by mgn
    }

    /**
     * Returns the size of the parameter with the name 'name'.
     * <p>
     * The size are multiples of the design size!
     * </p>
     *
     * @param key the name of the parameter.
     *
     * @return the size of the parameter with the name 'name'.
     *
     * @see org.extex.interpreter.type.font.Font#getFontDimen(String)
     */
    public FixedDimen getFontDimen(final String key) {

        return this.font.getFontDimen(key);
    }

    /**
     * Returns the FontKey for this font.
     *
     * @return the FontKey for this font
     *
     * @see org.extex.interpreter.type.font.Font#getFontKey()
     */
    public FontKey getFontKey() {

        return null; //this.font.getFontKey();
    }

    /**
     * Returns the name of the font.
     *
     * @return the name of the font
     *
     * @see org.extex.interpreter.type.font.Font#getFontName()
     */
    public String getFontName() {

        return this.font.getFontName();
    }

    /**
     * Returns the height of a character.
     *
     * @param uc the character
     *
     * @return the height of the character
     *
     * @see org.extex.interpreter.type.font.Font#getHeight(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getHeight(final UnicodeChar uc) {

        return font.getHeight(uc);
    }

    /**
     * Returns the hyphen character.
     *
     * @return the hyphen character
     *
     * @see org.extex.interpreter.type.font.Font#getHyphenChar()
     */
    public UnicodeChar getHyphenChar() {

        return this.font.getHyphenChar();
    }

    /**
     * Returns the italic correction of a character.
     *
     * @param uc the character
     *
     * @return the italic correction of the character
     *
     * @see org.extex.interpreter.type.font.Font#getItalicCorrection(
     *      org.extex.type.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(final UnicodeChar uc) {

        return font.getItalicCorrection(uc);
    }

    /**
     * Returns the kerning between two characters.
     *
     * @param uc1 the first character
     * @param uc2 the second character
     *
     * @return the kerning between two characters
     *
     * @see org.extex.interpreter.type.font.Font#getKerning(
     *      org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
     */
    public FixedDimen getKerning(final UnicodeChar uc1, final UnicodeChar uc2) {

        return font.getKerning(uc1, uc2);
    }

    /**
     * Returns the ligature for two characters.
     *
     * @param uc1 the first character
     * @param uc2 the second character
     *
     * @return Returns the ligature for two characters
     *
     * @see org.extex.interpreter.type.font.Font#getLigature(
     *      org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
     */
    public UnicodeChar getLigature(final UnicodeChar uc1, final UnicodeChar uc2) {

        return font.getLigature(uc1, uc2);
    }

    /**
     * Returns the scale factor of the font.
     *
     * @return the scale factor of the font
     *
     * @see org.extex.interpreter.type.font.Font#getScaleFactor()
     */
    public FixedCount getScaleFactor() {

        return font.getScaleFactor();
    }

    /**
     * Returns the skew char.
     *
     * @return the skew char
     *
     * @see org.extex.interpreter.type.font.Font#getSkewChar()
     */
    public UnicodeChar getSkewChar() {

        return this.font.getSkewChar();
    }

    /**
     * Returns the size of the 'space'.
     *
     * @return the size of the 'space'.
     *
     * @see org.extex.interpreter.type.font.Font#getSpace()
     */
    public FixedGlue getSpace() {

        return this.font.getSpace();
    }

    /**
     * Returns the width of a character.
     *
     * @param uc the character
     *
     * @return the width of the character
     *
     * @see org.extex.interpreter.type.font.Font#getWidth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getWidth(final UnicodeChar uc) {

        return font.getWidth(uc);
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

        return font.hasGlyph(uc);
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

        font.setEfCode(uc, code);
    }

    /**
     * Set the new value for the font parameter.
     *
     * @param key the name of the parameter
     * @param value the value to set
     *
     * @see org.extex.interpreter.type.font.Font#setFontDimen(String, Dimen)
     */
    public void setFontDimen(final String key, final Dimen value) {

        this.font.setFontDimen(key, value);
    }

    /**
     * Set the hyphen character.
     *
     * @param hyphen the hyphen character
     *
     * @see org.extex.interpreter.type.font.Font#setHyphenChar(
     *      org.extex.type.UnicodeChar)
     */
    public void setHyphenChar(final UnicodeChar hyphen) {

        this.font.setHyphenChar(hyphen);
    }

    /**
     * Set the skew character.
     *
     * @param skew the skew character
     *
     * @see org.extex.interpreter.type.font.Font#setSkewChar(
     *      org.extex.type.UnicodeChar)
     */
    public void setSkewChar(final UnicodeChar skew) {

        this.font.setSkewChar(skew);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return  a string representation of the object
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return this.font.toString();
    }

}
