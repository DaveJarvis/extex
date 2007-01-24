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
 */

package org.extex.interpreter.type.font;

import org.extex.font.FontKey;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.type.UnicodeChar;

/**
 * Font Interface.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4388 $
 */
public interface Font {

    /**
     * Returns the actual FontKey for this font after a font substitution.
     *
     * @return Returns the actual FontKey for this font.
     */
    FontKey getActualFontKey();

    /**
     * Returns the actual size of the font.
     *
     * @return Returns the actual size of the font.
     */
    FixedDimen getActualSize();

    /**
     * Returns the checksum of the font.
     * TODO: verschieben ???
     * @return Returns the checksum of the font.
     */
    int getCheckSum();

    /**
     * Returns the depth of the char.
     *
     * @param uc     The Unicode char.
     * @return Returns the depth of the char.
     */
    FixedGlue getDepth(UnicodeChar uc);

    /**
     * Returns the design size of the font.
     *
     * @return Returns the design size of the font.
     */
    FixedDimen getDesignSize();

    /**
     * Getter for the ef code.
     *
     * @param uc the character
     *
     * @return the ef code
     */
    long getEfCode(UnicodeChar uc);

    /**
     * Returns the size of 'M'.
     *
     * @return Returns the size of 'M'.
     */
    FixedDimen getEm();

    /**
     * Returns the size of 'x'.
     *
     * @return Returns the size of 'x'.
     */
    FixedDimen getEx();

    /**
     * Returns the size of the parameter with the name 'name'.
     * <p>
     * The size are multiples of the design size!
     * </p>
     *
     * @param name  The name of the parameter.
     * @return Returns the size of the parameter with the name 'name'.
     */
    FixedDimen getFontDimen(String name);

    /**
     * Returns the FontKey for this font.
     *
     * @return Returns the FontKey for this font.
     */
    FontKey getFontKey();

    /**
     * Returns the name of the font.
     *
     * @return Returns the name of the font.
     */
    String getFontName();

    /**
     * Returns the height of the char.
     *
     * @param uc     The Unicode char.
     * @return Returns the height of the char.
     */
    FixedGlue getHeight(UnicodeChar uc);

    /**
     * Returns the hyphen char.
     *
     * @return Returns the hyphen char.
     */
    UnicodeChar getHyphenChar();

    /**
     * Returns the italic correction of the char.
     *
     * @param uc     The Unicode char.
     * @return Returns the italic correction of the char.
     */
    FixedDimen getItalicCorrection(UnicodeChar uc);

    /**
     * Returns the kerning between two chars.
     *
     * @param uc1     The Unicode char (first one).
     * @param uc2     The Unicode char (second one).
     * @return Returns the kerning between two chars.
     */
    FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2);

    /**
     * Returns the ligature for two chars.
     *
     * @param uc1     The Unicode char (first one).
     * @param uc2     The Unicode char (second one).
     * @return Returns the ligature for two chars.
     */
    UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2);

    /**
     * Returns the scale factor of the font.
     *
     * @return Returns the scale factor of the font.
     */
    FixedCount getScaleFactor();

    /**
     * Returns the skew char.
     *
     * @return Returns the skew char.
     */
    UnicodeChar getSkewChar();

    /**
     * Returns the size of the 'space'.
     *
     * @return Returns the size of the 'space'.
     */
    FixedGlue getSpace();

    /**
     * Returns the width of the char.
     *
     * @param uc     The Unicode char.
     * @return Returns the width of the char.
     */
    FixedGlue getWidth(UnicodeChar uc);

    /**
     * Determine whether the glyph for a given character is present in this font.
     *
     * @param uc the character
     *
     * @return <code>true</code> iff the glyph is present
     */
    boolean hasGlyph(UnicodeChar uc);

    /**
     * Setter for the ef code.
     * The ef code influences the stretchability of characters. It has a
     * positive value. 1000 means "normal" stretchability.
     *
     * @param uc the character
     * @param code the associated code
     */
    void setEfCode(UnicodeChar uc, long code);

    /**
     * Set the new value for the font parameter.
     *
     * @param name  The name of the parameter.
     * @param value The value to set.
     */
    void setFontDimen(String name, Dimen value);

    /**
     * Set the hyphen char.
     *
     * @param uc    The Unicode char.
     */
    void setHyphenChar(UnicodeChar uc);

    /**
     * Set the skew char.
     *
     * @param uc    The Unicode char.
     */
    void setSkewChar(UnicodeChar uc);

}
