/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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
import org.extex.interpreter.type.count.Count;
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
     * @see org.extex.interpreter.type.font.Font#getCheckSum()
     */
    public int getCheckSum() {

        return 0; //this.font.getCheckSum();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getDepth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getDepth(final UnicodeChar uc) {

        return font.getDepth(uc);
    }


    /**
     * @see org.extex.interpreter.type.font.Font#getEfCode(org.extex.type.UnicodeChar)
     */
    public long getEfCode(final UnicodeChar uc) {

        return font.getEfCode(uc);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getEm()
     */
    public FixedDimen getEm() {

        return this.font.getEm();
    }

    /**
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
     * @see org.extex.interpreter.type.font.Font#getFontDimen(String)
     */
    public FixedDimen getFontDimen(final String key) {

        return this.font.getFontDimen(key);
    }

    /**
     * @see org.extex.font.type.Fount#getFontKey()
     */
    public FontKey getFontKey() {

        return null; //this.font.getFontKey();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getFontName()
     */
    public String getFontName() {

        return this.font.getFontName();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getHeight(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getHeight(final UnicodeChar uc) {

        return font.getHeight(uc);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getHyphenChar()
     */
    public UnicodeChar getHyphenChar() {

        return this.font.getHyphenChar();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getItalicCorrection(
     *      org.extex.type.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(final UnicodeChar uc) {

        return font.getItalicCorrection(uc);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getKerning(
     *      org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
     */
    public FixedDimen getKerning(final UnicodeChar uc1, final UnicodeChar uc2) {

        return font.getKerning(uc1, uc2);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getLetterSpacing()
     */
    public FixedGlue getLetterSpacing() {

        return null; //this.font.getLetterSpacing();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getLigature(
     *      org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
     */
    public UnicodeChar getLigature(final UnicodeChar uc1, final UnicodeChar uc2) {

        return font.getLigature(uc1, uc2);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getProperty(String)
     */
    public String getProperty(final String key) {

        return null; //this.font.getProperty(key);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getSkewChar()
     */
    public UnicodeChar getSkewChar() {

        return this.font.getSkewChar();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getSpace()
     */
    public FixedGlue getSpace() {

        return this.font.getSpace();
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getWidth(
     *      org.extex.type.UnicodeChar)
     */
    public FixedGlue getWidth(final UnicodeChar uc) {

        return font.getWidth(uc);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#hasGlyph(org.extex.type.UnicodeChar)
     */
    public boolean hasGlyph(final UnicodeChar uc) {

        return font.hasGlyph(uc);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#setEfCode(
     *      org.extex.type.UnicodeChar, long)
     */
    public void setEfCode(final UnicodeChar uc, final long code) {

        font.setEfCode(uc, code);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#setFontDimen(String, Dimen)
     */
    public void setFontDimen(final String key, final Dimen value) {

        this.font.setFontDimen(key, value);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#setHyphenChar(UnicodeChar)
     */
    public void setHyphenChar(final UnicodeChar hyphen) {

        this.font.setHyphenChar(hyphen);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#setSkewChar(UnicodeChar)
     */
    public void setSkewChar(final UnicodeChar skew) {

        this.font.setSkewChar(skew);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return this.font.toString();
    }

    public void setActualSize(Dimen size) {

        // TODO mgn: setActualsize unimplemented
        
    }

    public void setScaleFactor(Count scaleFactor) {

        // TODO mgn: setScalefactor unimplemented
        
    }

    public FixedDimen getActualSize() {

        // TODO mgn: getActualsize unimplemented
        return null;
    }

    public FixedDimen getDesignSize() {

        // TODO mgn: getDesignsize unimplemented
        return null;
    }

    public FixedCount getScaleFactor() {

        // TODO mgn: getScalefactor unimplemented
        return null;
    }

    public FontKey getActualFontKey() {

        // TODO mgn: getActualFontKey unimplemented
        return null;
    }

}
