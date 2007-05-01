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

import java.io.Serializable;

import org.extex.core.UnicodeChar;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.font.FontKey;

/**
 * Font Interface.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4388 $
 */
public interface Font extends Serializable {

    /**
     * Returns the actual FontKey for this font.
     * The font key may differ from the one requested.
     *
     * @return the actual FontKey for this font.
     */
    FontKey getActualFontKey();

    /**
     * Returns the actual size of the font.
     *
     * @return the actual size of the font.
     */
    FixedDimen getActualSize();

    /**
     * Returns the check sum of the font.
     * TODO: verschieben ???
     * @return the check sum of the font
     */
    int getCheckSum();

    /**
     * Returns the depth of the character.
     *
     * @param uc the character
     *
     * @return the depth of the character
     */
    FixedGlue getDepth(UnicodeChar uc);

    /**
     * Returns the design size of the font.
     *
     * @return the design size of the font
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
     * @return the size of 'M'.
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
     *
     * @return the size of the parameter with the name 'name'.
     */
    FixedDimen getFontDimen(String name);

    /**
     * Returns the FontKey for this font.
     *
     * @return the FontKey for this font
     */
    FontKey getFontKey();

    /**
     * Returns the name of the font.
     *
     * @return the name of the font
     */
    String getFontName();

    /**
     * Returns the height of a character.
     *
     * @param uc the character
     *
     * @return the height of the character
     */
    FixedGlue getHeight(UnicodeChar uc);

    /**
     * Returns the hyphen character.
     *
     * @return the hyphen character
     */
    UnicodeChar getHyphenChar();

    /**
     * Returns the italic correction of a character.
     *
     * @param uc the character
     *
     * @return the italic correction of the character
     */
    FixedDimen getItalicCorrection(UnicodeChar uc);

    /**
     * Returns the kerning between two characters.
     *
     * @param uc1 the first character
     * @param uc2 the second character
     *
     * @return the kerning between two characters
     */
    FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2);

    /**
     * Returns the ligature for two characters.
     *
     * @param uc1 the first character
     * @param uc2 the second character
     *
     * @return Returns the ligature for two characters
     */
    UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2);

    /**
     * Returns the scale factor of the font.
     *
     * @return the scale factor of the font
     */
    FixedCount getScaleFactor();

    /**
     * Returns the skew char.
     *
     * @return the skew char
     */
    UnicodeChar getSkewChar();

    /**
     * Returns the size of the 'space'.
     *
     * @return the size of the 'space'.
     */
    FixedGlue getSpace();

    /**
     * Returns the width of a character.
     *
     * @param uc the character
     *
     * @return the width of the character
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
     * @param name the name of the parameter
     * @param value the value to set
     */
    void setFontDimen(String name, Dimen value);

    /**
     * Set the hyphen character.
     *
     * @param uc the hyphen character
     */
    void setHyphenChar(UnicodeChar uc);

    /**
     * Set the skew character.
     *
     * @param uc the skew character
     */
    void setSkewChar(UnicodeChar uc);

}
