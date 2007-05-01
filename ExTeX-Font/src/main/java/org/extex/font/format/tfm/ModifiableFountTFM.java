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

package org.extex.font.format.tfm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.extex.core.UnicodeChar;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.FontKey;
import org.extex.font.Glyph;
import org.extex.font.GlyphImpl;
import org.extex.font.type.ModifiableFount;

/**
 * Adapter for a ModifiableFount for TFM.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class ModifiableFountTFM implements ModifiableFount, Serializable {

    /**
     * Default unitsperem
     */
    public static final int DEFAULTUNITSPEREM = 1000;

    /**
     * permille factor for scale factor
     */
    private static final int PERMILLE_FACTOR = 1000;

    /**
     * The field <tt>serialVersionUID</tt> ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * the actual font size
     */
    private Dimen actualsize;

    /**
     * the design font size
     */
    private Dimen designsize;

    /**
     * the tfm font
     */
    private TfmReader font;

    /**
     * hash for fontdimen keys
     */
    private HashMap<String, Dimen> fontdimen = new HashMap<String, Dimen>();

    /**
     * the fount key
     */
    private FontKey fountkey;

    /**
     * the map for the glyphs
     */
    private Map<UnicodeChar, Glyph> glyphmap = new HashMap<UnicodeChar, Glyph>();

    /**
     * property map
     */
    private Map<String, String> property = new HashMap<String, String>();

    /**
     * Create a new object.
     * 
     * @param fk the fount key
     * @param tfmfont the tfm font
     */
    public ModifiableFountTFM(FontKey fk, TfmReader tfmfont) {

        fountkey = fk;
        font = tfmfont;

        calculateSize();
        setFontDimenValues();
    }

    /**
     * Calculate the sizes
     */
    private void calculateSize() {

        designsize =
                new Dimen(
                    (long) (font.getHeader().getDesignsize().toDouble() * Dimen.ONE));

        // // scale factor = 0 -> 1000
        // if (fountkey.getScale() == null || fountkey.getScale().getValue() ==
        // 0) {
        // fountkey.setScale(new Count(PERMILLE_FACTOR));
        // }
        //
        // // calculate actual size
        // if (fountkey.getSize() == null) {
        // actualsize = new Dimen((designsize.getValue()
        // * fountkey.getScale().getValue() / PERMILLE_FACTOR));
        // } else {
        // actualsize = new Dimen((fountkey.getSize().getValue()
        // * fountkey.getScale().getValue() / PERMILLE_FACTOR));
        // }
    }

    /**
     * convert the fixword value to a dimen value
     * 
     * the simple calculation fw.getValue() * actualsize.getValue() /
     * TFMFixWord.FIXWORDDENOMINATOR leads to different rounding than in TeX due
     * to a limitation to 4 byte integer precission. Note that fw.getValue() *
     * actualsize.getValue() might exceed the 4 byte range. Hence TTP 571
     * cancels actualsize.getValue() and TFMFixWord.FIXWORDDENOMINATOR to allow
     * for a bytewise calculation. We do not need this bytewise calculation
     * since we have long integers, but we need to be precise in performing the
     * same rounding.
     * 
     * @param fw the fixword value
     * @return Returns the Dimen value.
     */
    private Dimen convertFixWordToDimen(TfmFixWord fw) {

        int shift = 20;
        long z = actualsize.getValue();
        while (z >= 8388608) {
            z >>= 1;
            shift -= 1;
        }
        return new Dimen(z * fw.getValue() >> shift);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BaseFont#getActualFontKey()
     */
    public FontKey getActualFontKey() {

        // TODO mgn: getActualFontKey unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getActualSize()
     */
    public FixedDimen getActualSize() {

        return actualsize;
    }

    /**
     * TODO missing JavaDoc
     *
     * @return TODO
     */
    public int getCheckSum() {

        return font.getChecksum();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getDepth(org.extex.core.UnicodeChar)
     */
    public FixedGlue getDepth(UnicodeChar uc) {

        // TODO mgn: getDepth unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return designsize;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.type.Fount#getEm()
     */
    public FixedDimen getEm() {

        return actualsize;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.type.Fount#getEx()
     */
    public FixedDimen getEx() {

        Dimen xheight = fontdimen.get("XHEIGHT");
        if (xheight == null) {
            return new Dimen(actualsize);
        }
        return new Dimen(xheight.getValue() * actualsize.getValue()
                / DEFAULTUNITSPEREM);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.type.Fount#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(String key) {

        Dimen rt = fontdimen.get(key);
        if (rt == null) {
            rt = new Dimen(0);
        }
        return rt;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BaseFont#getFontKey()
     */
    public FontKey getFontKey() {

        return fountkey;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.type.Fount#getFontName()
     */
    public String getFontName() {

        return fountkey.getName();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.type.InternalFount#getGlyph(org.extex.core.UnicodeChar)
     */
    public Glyph getGlyph(UnicodeChar c) {

        Glyph g = glyphmap.get(c);
        if (g == null) {
            TfmCharInfoArray charinfo = font.getCharinfo();
            TfmCharInfoWord ci = charinfo.getCharInfoWord(c.getCodePoint());

            g = new GlyphImpl();
            if (ci != null) {
                g.setNumber(String.valueOf(c.getCodePoint() + ci.getBc()));
                if (ci.getGlyphname() != null) {
                    g.setName(ci.getGlyphname().replaceAll("/", ""));
                }
                g.setWidth(convertFixWordToDimen(ci.getWidth()));
                g.setDepth(convertFixWordToDimen(ci.getDepth()));
                g.setHeight(convertFixWordToDimen(ci.getHeight()));
                g.setItalicCorrection(convertFixWordToDimen(ci.getItalic()));

                TfmLigKern[] ligKernTable = font.getLigkern().getLigKernTable();

                // // ligature and kerning
                // if (fountkey.isLigatures() || fountkey.isKerning()) {
                // int ligstart = ci.getLigkernstart();
                // if (ligstart != TfmCharInfoWord.NOINDEX) {
                //
                // for (int k = ligstart; k != TfmCharInfoWord.NOINDEX; k =
                // ligKernTable[k]
                // .nextIndex(k)) {
                // TfmLigKern lk = ligKernTable[k];
                //
                // if (lk instanceof TfmLigature) {
                // if (fountkey.isLigatures()) {
                // TfmLigature lig = (TfmLigature) lk;
                //
                // Ligature lv = new Ligature();
                // lv.setLetterid(String.valueOf(lig
                // .getNextChar()));
                // String sl = Character.toString((char) lig
                // .getNextChar());
                // if (sl != null && sl.trim().length() > 0) {
                // lv.setLetter(sl.trim());
                // }
                // lv.setLigid(String.valueOf(lig
                // .getAddingChar()));
                // String slig = Character.toString((char) lig
                // .getAddingChar());
                // if (slig != null
                // && slig.trim().length() > 0) {
                // lv.setLig(slig.trim());
                // }
                // g.addLigature(lv);
                // }
                // } else if (lk instanceof TfmKerning) {
                // // if (fountkey.isKerning()) {
                // // TfmKerning kern = (TfmKerning) lk;
                // //
                // // Kerning kv = new Kerning();
                // // kv
                // // .setId(String.valueOf(kern
                // // .getNextChar()));
                // // String sk = Character.toString((char) kern
                // // .getNextChar());
                // // if (sk != null && sk.trim().length() > 0) {
                // // kv.setName(sk.trim());
                // // }
                // // kv.setSize(convertFixWordToDimen(kern
                // // .getKern()));
                // // g.addKerning(kv);
                // // }
                // }
                // }
                // }
                // }
            }
        }
        return g;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getHeight(org.extex.core.UnicodeChar)
     */
    public FixedGlue getHeight(UnicodeChar uc) {

        // TODO mgn: getHeight unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getItalicCorrection(org.extex.core.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(UnicodeChar uc) {

        // TODO mgn: getItalicCorrection unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getKerning(org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    public FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2) {

        // TODO mgn: getKerning unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getLigature(org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

        // TODO mgn: getLigature unimplemented
        return null;
    }
    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getScaleFactor()
     */
    public FixedCount getScaleFactor() {

        // TODO mgn: getScaleFactor unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.font.ExtexFont#getSpace()
     */
    public FixedGlue getSpace() {

        // glyph 'space' exists?
        Dimen spacedimen = fontdimen.get("SPACE");
        if (spacedimen != null) {
            return new Glue(spacedimen);
        }

        // mgn Walter fragen, welche spacebreite?
        return new Glue(actualsize);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getWidth(org.extex.core.UnicodeChar)
     */
    public FixedGlue getWidth(UnicodeChar uc) {

        // TODO mgn: getWidth unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#hasGlyph(org.extex.core.UnicodeChar)
     */
    public boolean hasGlyph(UnicodeChar uc) {

        // TODO mgn: hasGlyph unimplemented
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.type.ModifiableFount#setFontDimen( java.lang.String,
     *      org.extex.core.dimen.Dimen)
     */
    public void setFontDimen(String key, Dimen value) {

        fontdimen.put(key, value);
    }

    /**
     * set the font dimen values from the tfm parameter
     */
    private void setFontDimenValues() {

        // mgn: umbauen
        // TfmParamArray param = font.getParam();
        // TfmFixWord[] fw = param.getTable();
        // for (int i = 0; i < fw.length; i++) {
        // String labelname = param.getLabelName(i);
        // Dimen d = convertFixWordToDimen(fw[i]);
        // fontdimen.put(labelname, d);
        // }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.type.ModifiableFount#setProperty(java.lang.String,
     *      java.lang.String)
     */
    public void setProperty(String key, String value) {

        property.put(key, value);
    }
}
