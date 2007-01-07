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

import org.extex.font.CoreFontFactory;
import org.extex.font.FontKey;
import org.extex.font.LoadableFont;
import org.extex.font.exception.CorruptFontException;
import org.extex.font.exception.FontException;
import org.extex.font.unicode.GlyphName;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.glue.Glue;
import org.extex.type.UnicodeChar;

/**
 * Class to load afm fonts.
 *
 * <ul>
 * <li>The font has no design size; this is set to the size of the font key.</li>
 * <li>The EM size is set to the size of the font.</li>
 * <li>The font has no font dimen values. (mgn: überdenken)</li>
 * <li>If the font has no glyph 'space', then ex is used for getSpace(). (mgn: überprüfen)</li>
 * </ul>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class LoadableAfmFont implements LoadableFont {

    /**
     * The font key.
     */
    private FontKey fontKey;

    /**
     * The actual font key.
     */
    private FontKey actualFontKey;

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
     * @param val   the value
     * @return the <code>Dimen</code> value of the float value.
     */
    private FixedDimen floatToDimen(final float val) {

        long l = (long) (getActualSize().getValue() * val / 1000);

        return new Dimen(l);
    }

    /**
     * @see org.extex.font.BaseFont#getActualFontKey()
     */
    public FontKey getActualFontKey() {

        return fontKey;
    }

    /**
     * @see org.extex.font.ExtexFont#getActualSize()
     */
    public FixedDimen getActualSize() {

        return actualFontKey.getDimen("size");
    }

    /**
     * Returns the char metric for a Unicode char,
     * or <code>null</code>, if not found.
     *
     * @param uc    the Unicode char.
     * @return the char metric for a Unicode char,
     *         or <code>null</code>, if not found.
     */
    private AfmCharMetric getCharMetric(final UnicodeChar uc) {

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
     * @see org.extex.font.ExtexFont#getDepth(org.extex.type.UnicodeChar)
     */
    public FixedGlue getDepth(final UnicodeChar uc) {

        AfmCharMetric cm = getCharMetric(uc);
        if (cm != null) {
            return new Glue(floatToDimen(cm.getDepth()));
        }
        return Glue.ZERO;
    }

    /**
     * @see org.extex.font.ExtexFont#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return fontKey.getDimen("size");
    }

    /**
     * @see org.extex.font.ExtexFont#getEm()
     */
    public FixedDimen getEm() {

        return actualFontKey.getDimen("size");
    }

    /**
     * @see org.extex.font.ExtexFont#getEx()
     */
    public FixedDimen getEx() {

        float xh = parser.getHeader().getXheight();
        return floatToDimen(xh);
    }

    /**
     * @see org.extex.font.ExtexFont#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(final String name) {

        return Dimen.ZERO_PT;
    }

    /**
     * @see org.extex.font.BaseFont#getFontKey()
     */
    public FontKey getFontKey() {

        return fontKey;
    }

    /**
     * @see org.extex.font.ExtexFont#getFontName()
     */
    public String getFontName() {

        return fontKey.getName();
    }

    /**
     * @see org.extex.font.ExtexFont#getHeight(org.extex.type.UnicodeChar)
     */
    public FixedGlue getHeight(final UnicodeChar uc) {

        AfmCharMetric cm = getCharMetric(uc);
        if (cm != null) {
            return new Glue(floatToDimen(cm.getHeight()));
        }
        return Glue.ZERO;
    }

    /**
     * @see org.extex.font.ExtexFont#getItalicCorrection(org.extex.type.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(final UnicodeChar uc) {

        // TODO mgn: getItalicCorrection unimplemented
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getKerning(org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
     */
    public FixedDimen getKerning(final UnicodeChar uc1, final UnicodeChar uc2) {

        // TODO mgn: getKerning unimplemented
        // nokerning beachten
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getLigature(org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
     */
    public UnicodeChar getLigature(final UnicodeChar uc1, final UnicodeChar uc2) {

        // TODO mgn: getLigature unimplemented
        // noligature beachten
        return null;
    }

    /**
     * @see org.extex.font.ExtexFont#getScaleFactor()
     */
    public FixedCount getScaleFactor() {

        return Count.ONE;
    }

    /**
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
     * @see org.extex.font.ExtexFont#getWidth(org.extex.type.UnicodeChar)
     */
    public FixedGlue getWidth(final UnicodeChar uc) {

        AfmCharMetric cm = getCharMetric(uc);
        if (cm != null) {
            return new Glue(floatToDimen(cm.getWidth()));
        }
        return Glue.ZERO;
    }

    /**
     * @see org.extex.font.ExtexFont#hasGlyph(org.extex.type.UnicodeChar)
     */
    public boolean hasGlyph(final UnicodeChar uc) {

        return getCharMetric(uc) != null ? true : false;
    }

    /**
     * @see org.extex.font.LoadableFont#loadFont(java.io.InputStream, org.extex.font.CoreFontFactory, org.extex.font.FontKey)
     */
    public void loadFont(final InputStream in, final CoreFontFactory factory,
            FontKey fontKey) throws CorruptFontException {

        this.fontKey = fontKey;

        if (fontKey == null) {
            throw new IllegalArgumentException("fontkey");
        }

        try {

            glyphname = GlyphName.getInstance();

            parser = new AfmParser(in);

        } catch (FontException e) {
            throw new CorruptFontException(fontKey, e.getLocalizedMessage());
        } catch (IOException e) {
            throw new CorruptFontException(fontKey, e.getLocalizedMessage());
        }

        if (fontKey.getDimen("size") == null) {
            // use 10pt as default
            actualFontKey = factory.getFontKey(fontKey, new Dimen(
                    Dimen.ONE * 10));
        } else {
            actualFontKey = fontKey;
        }

    }

}
