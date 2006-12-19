/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 */

package org.extex.font.type.afm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.extex.font.FontByteArray;
import org.extex.font.FountKey;
import org.extex.font.Glyph;
import org.extex.font.type.BoundingBox;
import org.extex.font.type.ModifiableFount;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.type.UnicodeChar;

/**
 * ModifiableFount for AFM.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4402 $
 */
public class ModifiableFountAFM implements ModifiableFount, Serializable {

    /**
     * The field <tt>serialVersionUID</tt> ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default unitsperem (type1)
     */
    public static final int DEFAULTUNITSPEREM = 1000;

    /**
     * permille factor for scale factor
     */
    private static final int PERMILLE_FACTOR = 1000;

    /**
     * Create a new object.
     *
     * @param fk        the fount key
     * @param afmfont   the afm font
     */
    public ModifiableFountAFM(final FountKey fk, final AfmFont afmfont) {

        fountkey = fk;
        font = afmfont;

        calculateSize();
        //setFontDimenValues();
    }

    /**
     * the fount key
     */
    private FountKey fountkey;

    /**
     * the afm font
     */
    private AfmFont font;

    /**
     * property-map
     */
    private Map property = new HashMap();

    /**
     * calcualte the sizes
     */
    private void calculateSize() {

//        designsize = new Dimen((long) (font.getDefaultsize() * Dimen.ONE));
//
//        // scale factor = 0 -> 1000
//        if (fountkey.getScale() == null || fountkey.getScale().getValue() == 0) {
//            fountkey.setScale(new Count(PERMILLE_FACTOR));
//        }
//
//        // calculate actualsize
//        if (fountkey.getSize() == null) {
//            actualsize = new Dimen((long) (designsize.getValue()
//                    * fountkey.getScale().getValue() / PERMILLE_FACTOR));
//        } else {
//            actualsize = new Dimen((long) (fountkey.getSize().getValue()
//                    * fountkey.getScale().getValue() / PERMILLE_FACTOR));
//        }
    }

    /**
     * the actual font size
     */
    private Dimen actualsize;

    /**
     * the design font size
     */
    private Dimen designsize;


    
    /**
     * @see org.extex.font.type.ModifiableFount#setProperty(java.lang.String, java.lang.String)
     */
    public void setProperty(final String key, final String value) {

    }

    /**
     * @see org.extex.font.type.ModifiableFount#setFontDimen(java.lang.String, org.extex.interpreter.type.dimen.Dimen)
     */
    public void setFontDimen(final String key, final Dimen value) {

    }

    /**
     * @see org.extex.font.type.InternalFount#getGlyph(org.extex.type.UnicodeChar)
     */
    public Glyph getGlyph(final UnicodeChar c) {

        return null;
    }

    /**
     * @see org.extex.font.type.Fount#getSpace()
     */
    public FixedGlue getSpace() {

        return null;
    }

    /**
     * @see org.extex.font.type.Fount#getEm()
     */
    public FixedDimen getEm() {

        return null;
    }

    /**
     * @see org.extex.font.type.Fount#getEx()
     */
    public FixedDimen getEx() {

        return null;
    }

    /**
     * @see org.extex.font.type.Fount#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(final String key) {

        return null;
    }

    /**
     * @see org.extex.font.type.Fount#getProperty(java.lang.String)
     */
    public String getProperty(final String key) {

        return null;
    }

    /**
     * @see org.extex.font.type.Fount#getFontName()
     */
    public String getFontName() {

        return null;
    }

    /**
     * @see org.extex.font.type.Fount#getCheckSum()
     */
    public int getCheckSum() {

        return 0;
    }

    /**
     * @see org.extex.font.type.Fount#getLetterSpacing()
     */
    public FixedGlue getLetterSpacing() {

        return null;
    }

    /**
     * @see org.extex.font.type.Fount#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return null;
    }

    /**
     * @see org.extex.font.type.Fount#getActualSize()
     */
    public FixedDimen getActualSize() {

        return null;
    }

    /**
     * @see org.extex.font.type.Fount#getFontKey()
     */
    public FountKey getFontKey() {

        return null;
    }

    /**
     * @see org.extex.font.type.Fount#getFontByteArray()
     */
    public FontByteArray getFontByteArray() {
    
        return null;
    }
}
