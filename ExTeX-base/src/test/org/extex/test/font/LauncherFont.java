/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.test.font;

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
import org.extex.interpreter.type.font.Font;

/**
 * This class provides a memory-only font for test cases. Since no external file
 * is required no problem with parsing can happen.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4784 $
 */
public class LauncherFont implements Font, Serializable {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * The field <tt>efCode</tt> contains the ef code.
     */
    private Map<UnicodeChar, Long> efCode = new HashMap<UnicodeChar, Long>();

    /**
     * The field <tt>fontdimen</tt> contains the font dimens.
     */
    private Map<String, Dimen> fontdimen = new HashMap<String, Dimen>();

    /**
     * The field <tt>hyphen</tt> contains the hyphen char.
     */
    private UnicodeChar hyphen = UnicodeChar.get(45);

    /**
     * The field <tt>skew</tt> contains the skew char.
     */
    private UnicodeChar skew = null;

    /**
     * Returns the actual FontKey for this font.
     *
     * @return the actual FontKey for this font.
     *
     * @see org.extex.interpreter.type.font.Font#getActualFontKey()
     */
    public FontKey getActualFontKey() {

        return new FontKeyFactory().newInstance("testfont");
    }

    /**
     * Returns the actual size of the font.
     *
     * @return the actual size of the font
     *
     * @see org.extex.interpreter.type.font.Font#getActualSize()
     */
    public FixedDimen getActualSize() {

        return new Dimen(Dimen.ONE_PT.getValue() * 10);
    }

    /**
     * Returns the check sum of the font.
     *
     * @return the check sum of the font.
     *
     * @see org.extex.interpreter.type.font.Font#getCheckSum()
     */
    public int getCheckSum() {

        return 12345;
    }

    /**
     * Returns the depth of the character.
     *
     * @param uc the character
     *
     * @return the depth of the character
     *
     * @see org.extex.interpreter.type.font.Font#getDepth(
     *      org.extex.core.UnicodeChar)
     */
    public FixedGlue getDepth(UnicodeChar uc) {

        switch (uc.getCodePoint()) {
            case 'q':
            case 'p':
            case 'g':
            case 'j':
            case 'y':
                return new Glue(Dimen.ONE * 2);
            case ',':
            case ';':
            case '/':
            case '(':
            case ')':
                return new Glue(Dimen.ONE);
            default:
                return FixedGlue.ZERO;
        }
    }

    /**
     * Returns the design size of the font.
     *
     * @return the design size of the font
     *
     * @see org.extex.interpreter.type.font.Font#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return new Dimen(Dimen.ONE_PT.getValue() * 10);
    }

    /**
     * Getter for the ef code.
     *
     * @param uc the character
     *
     * @return the ef code
     *
     * @see org.extex.interpreter.type.font.Font#getEfCode(
     *      org.extex.core.UnicodeChar)
     */
    public long getEfCode(UnicodeChar uc) {

        Long l = efCode.get(uc);
        return l != null ? l.longValue() : 1000;
    }

    /**
     * Returns the size of 1em.
     *
     * @return the size of 1em.
     *
     * @see org.extex.interpreter.type.font.Font#getEm()
     */
    public FixedDimen getEm() {

        return new Dimen(Dimen.ONE_PT.getValue() * 10);
    }

    /**
     * Returns the size of 1ex.
     *
     * @return Returns the size of 1ex.
     *
     * @see org.extex.interpreter.type.font.Font#getEx()
     */
    public FixedDimen getEx() {

        return new Dimen(Dimen.ONE_PT.getValue() * 5);
    }

    /**
     * Returns the size of the parameter with the name 'name'.
     *
     * @param name the name of the parameter.
     *
     * @return the size of the parameter with the name 'name'.
     *
     * @see org.extex.interpreter.type.font.Font#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(String name) {

        Dimen d = fontdimen.get(name);
        return d != null ? d : Dimen.ZERO_PT;
    }

    /**
     * Returns the FontKey for this font.
     *
     * @return the FontKey for this font
     *
     * @see org.extex.interpreter.type.font.Font#getFontKey()
     */
    public FontKey getFontKey() {

        return new FontKeyFactory().newInstance("testfont");
    }

    /**
     * Returns the name of the font.
     *
     * @return the name of the font
     *
     * @see org.extex.interpreter.type.font.Font#getFontName()
     */
    public String getFontName() {

        return "testfont";
    }

    /**
     * Returns the height of a character.
     *
     * @param uc the character
     *
     * @return the height of the character
     *
     * @see org.extex.interpreter.type.font.Font#getHeight(
     *      org.extex.core.UnicodeChar)
     */
    public FixedGlue getHeight(UnicodeChar uc) {

        return new Glue(Dimen.ONE * 8);
    }

    /**
     * Returns the hyphen character.
     *
     * @return the hyphen character
     *
     * @see org.extex.interpreter.type.font.Font#getHyphenChar()
     */
    public UnicodeChar getHyphenChar() {

        return hyphen;
    }

    /**
     * Returns the italic correction of a character.
     *
     * @param uc the character
     *
     * @return the italic correction of the character
     *
     * @see org.extex.interpreter.type.font.Font#getItalicCorrection(
     *      org.extex.core.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(UnicodeChar uc) {

        return new Dimen(Dimen.ZERO_PT);
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
     *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar)
     */
    public FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2) {

        return new Dimen(Dimen.ZERO_PT);
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
     *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar)
     */
    public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

        if (uc1.getCodePoint() == 'f') {
            if (uc2.getCodePoint() == 'f') {
                return UnicodeChar.get(222); // TODO
            }
        }
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

        return Count.ONE;
    }

    /**
     * Returns the skew char.
     *
     * @return the skew char
     *
     * @see org.extex.interpreter.type.font.Font#getSkewChar()
     */
    public UnicodeChar getSkewChar() {

        return skew;
    }

    /**
     * Returns the size of the 'space'.
     *
     * @return Returns the size of the 'space'.
     *
     * @see org.extex.interpreter.type.font.Font#getSpace()
     */
    public FixedGlue getSpace() {

        return new Glue(Dimen.ONE_PT.getValue() * 10);
    }

    /**
     * Returns the width of a character.
     *
     * @param uc the character
     *
     * @return Returns the width of the character
     *
     * @see org.extex.interpreter.type.font.Font#getWidth(
     *      org.extex.core.UnicodeChar)
     */
    public FixedGlue getWidth(UnicodeChar uc) {

        return new Glue(Dimen.ONE * 8);
    }

    /**
     * Determine whether the glyph for a given character is present in this font.
     *
     * @param uc the character
     *
     * @return <code>true</code> iff the glyph is present
     *
     * @see org.extex.interpreter.type.font.Font#hasGlyph(
     *      org.extex.core.UnicodeChar)
     */
    public boolean hasGlyph(UnicodeChar uc) {

        return true;
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
     *      org.extex.core.UnicodeChar,
     *      long)
     */
    public void setEfCode(UnicodeChar uc, long code) {

        efCode.put(uc, Long.valueOf(code));
    }

    /**
     * Set the new value for the font parameter.
     *
     * @param name the name of the parameter
     * @param value the value to set
     *
     * @see org.extex.interpreter.type.font.Font#setFontDimen(
     *      java.lang.String,
     *      org.extex.core.dimen.Dimen)
     */
    public void setFontDimen(String name, Dimen value) {

        fontdimen.put(name, value);
    }

    /**
     * Set the hyphen character.
     *
     * @param h the hyphen character
     *
     * @see org.extex.interpreter.type.font.Font#setHyphenChar(
     *      org.extex.core.UnicodeChar)
     */
    public void setHyphenChar(UnicodeChar h) {

        this.hyphen = h;
    }

    /**
     * Set the skew character.
     *
     * @param s the skew character
     *
     * @see org.extex.interpreter.type.font.Font#setSkewChar(
     *      org.extex.core.UnicodeChar)
     */
    public void setSkewChar(UnicodeChar s) {

        this.skew = s;
    }

}
