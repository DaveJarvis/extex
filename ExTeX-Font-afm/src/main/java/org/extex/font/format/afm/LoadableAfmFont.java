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

package org.extex.font.format.afm;

import java.io.IOException;
import java.io.InputStream;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.CoreFontFactory;
import org.extex.font.FontKey;
import org.extex.font.LoadableFont;
import org.extex.font.exception.CorruptFontException;
import org.extex.font.exception.FontException;
import org.extex.font.unicode.GlyphName;

/**
 * Class to load afm fonts.
 * 
 * <ul>
 * <li>The font has no design size; this is set to the size of the font key.</li>
 * <li>The EM size is set to the size of the font.</li>
 * <li>The font has no font dimen values. (mgn: überdenken)</li>
 * <li>If the font has no glyph 'space', then ex is used for getSpace(). (mgn:
 * überprüfen)</li>
 * </ul>
 * 
 * mgn: glyphname - unicode mapping (zusätzlich) einfügen
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class LoadableAfmFont implements LoadableFont {

    /**
     * The actual font key.
     */
    private FontKey actualFontKey;

    /**
     * The font key.
     */
    private FontKey fontKey;

    /**
     * The glyph name Unicode table.
     */
    private GlyphName glyphname;

    /**
     * The last used char metric (for caching).
     */
    private AfmCharMetric lastUsedCm = null;

    /**
     * The last used Unicode char (for caching).
     */
    private UnicodeChar lastUsedUc = null;

    /**
     * The afm parser.
     */
    private AfmParser parser;

    /**
     * Convert a float value to a <code>Dimen</code>.
     * 
     * @param val the value
     * @return the <code>Dimen</code> value of the float value.
     */
    private FixedDimen floatToDimen(float val) {

        long l = (long) (getActualSize().getValue() * val / 1000);

        return new Dimen(l);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BaseFont#getActualFontKey()
     */
    public FontKey getActualFontKey() {

        return actualFontKey;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getActualSize()
     */
    public FixedDimen getActualSize() {

        return actualFontKey.getDimen("size");
    }

    /**
     * Returns the char metric for a Unicode char, or <code>null</code>, if
     * not found.
     * 
     * @param uc the Unicode char.
     * @return the char metric for a Unicode char, or <code>null</code>, if
     *         not found.
     */
    private AfmCharMetric getCharMetric(UnicodeChar uc) {

        if (uc != null) {
            if (uc == lastUsedUc) {
                return lastUsedCm;
            }
            // get thy glyph name
            String name = glyphname.getGlyphname(uc);
            if (name != null) {
                AfmCharMetric cm = parser.getAfmCharMetric(name);
                if (cm != null) {
                    lastUsedUc = uc;
                    lastUsedCm = cm;
                    return cm;
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getDepth(org.extex.core.UnicodeChar)
     */
    public FixedGlue getDepth(UnicodeChar uc) {

        AfmCharMetric cm = getCharMetric(uc);
        if (cm != null) {
            return new Glue(floatToDimen(cm.getDepth()));
        }
        return FixedGlue.ZERO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return fontKey.getDimen("size");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getEm()
     */
    public FixedDimen getEm() {

        return actualFontKey.getDimen("size");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getEx()
     */
    public FixedDimen getEx() {

        float xh = parser.getHeader().getXheight();
        return floatToDimen(xh);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(String name) {

        return Dimen.ZERO_PT;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BaseFont#getFontKey()
     */
    public FontKey getFontKey() {

        return fontKey;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getFontName()
     */
    public String getFontName() {

        return fontKey.getName();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getHeight(org.extex.core.UnicodeChar)
     */
    public FixedGlue getHeight(UnicodeChar uc) {

        AfmCharMetric cm = getCharMetric(uc);
        if (cm != null) {
            return new Glue(floatToDimen(cm.getHeight()));
        }
        return FixedGlue.ZERO;
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

        // TODO mgn: nokerning beachten

        AfmCharMetric cm1 = getCharMetric(uc1);

        if (cm1 != null) {
            AfmCharMetric cm2 = getCharMetric(uc2);
            if (cm2 != null) {
                AfmKernPairs kp = cm1.getAfmKernPair(cm2.getN());
                if (kp != null) {
                    float size = kp.getKerningsize();
                    return floatToDimen(size);
                }
            }
        }
        return Dimen.ZERO_PT;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getLigature(org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

        // TODO mgn: noligature beachten
        AfmCharMetric cm1 = getCharMetric(uc1);

        if (cm1 != null) {
            AfmCharMetric cm2 = getCharMetric(uc2);
            if (cm2 != null) {
                String lig = cm1.getLigature(cm2.getN());
                if (lig != null) {
                    try {
                        return GlyphName.getInstance().getUnicode(lig);
                    } catch (IOException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getScaleFactor()
     */
    public FixedCount getScaleFactor() {

        return Count.ONE;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getSpace()
     */
    public FixedGlue getSpace() {

        AfmCharMetric space = parser.getAfmCharMetric("space");
        if (space != null) {
            float wx = space.getWx();
            return new Glue(floatToDimen(wx));
        }
        return new Glue(getEx());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getWidth(org.extex.core.UnicodeChar)
     */
    public FixedGlue getWidth(UnicodeChar uc) {

        AfmCharMetric cm = getCharMetric(uc);
        if (cm != null) {
            return new Glue(floatToDimen(cm.getWidth()));
        }
        return FixedGlue.ZERO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#hasGlyph(org.extex.core.UnicodeChar)
     */
    public boolean hasGlyph(UnicodeChar uc) {

        return getCharMetric(uc) != null ? true : false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.LoadableFont#loadFont(java.io.InputStream,
     *      org.extex.font.CoreFontFactory, org.extex.font.FontKey)
     */
    public void loadFont(InputStream in, CoreFontFactory factory, FontKey key)
            throws CorruptFontException {

        this.fontKey = key;

        if (key == null) {
            throw new IllegalArgumentException("fontkey");
        }

        try {

            glyphname = GlyphName.getInstance();

            parser = new AfmParser(in);

        } catch (FontException e) {
            throw new CorruptFontException(key, e.getLocalizedMessage());
        } catch (IOException e) {
            throw new CorruptFontException(key, e.getLocalizedMessage());
        }

        if (key.getDimen("size") == null) {
            // use 10pt as default
            actualFontKey = factory.getFontKey(key, new Dimen(Dimen.ONE * 10));
        } else {
            actualFontKey = key;
        }

    }

}
