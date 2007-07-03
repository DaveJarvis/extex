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

package org.extex.font.format.xtf;

import java.io.IOException;
import java.io.InputStream;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.BackendFont;
import org.extex.font.CoreFontFactory;
import org.extex.font.FontKey;
import org.extex.font.LoadableFont;
import org.extex.font.exception.CorruptFontException;
import org.extex.font.format.XtfMetricFont;
import org.extex.font.unicode.GlyphName;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * Class to load xtf fonts.
 * 
 * <p>
 * At the moment only ttf!
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class LoadableXtfFont
        implements
            LoadableFont,
            XtfMetricFont,
            BackendFont {

    /**
     * The actual font key.
     */
    private FontKey actualFontKey;

    /**
     * The font key.
     */
    private FontKey fontKey;

    /**
     * use the first font! TODO MGN incomplete
     */
    private int fontnumber = 0;

    /**
     * The glyph name Unicode table.
     */
    private GlyphName glyphname;

    /**
     * The xtf reader.
     */
    private XtfReader reader;

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
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getAfm()
     */
    public byte[] getAfm() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getCheckSum()
     */
    public int getCheckSum() {

        // not aviable in ttf/otf.
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getDepth(org.extex.core.UnicodeChar)
     */
    public FixedGlue getDepth(UnicodeChar uc) {

        int d = 0;

        // TODO encoding
        XtfBoundingBox bb =
                reader.mapCharCodeToBB(uc.getCodePoint(), fontnumber,
                    (short) 3, (short) 1);
        if (bb != null) {

            d = bb.getDepth();
        }
        return new Glue(intToDimen(d));
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

        return getActualSize();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getEncodingVector()
     */
    public String[] getEncodingVector() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getEx()
     */
    public FixedDimen getEx() {

        int xh = 0;

        // TODO encoding
        XtfBoundingBox bb =
                reader.mapCharCodeToBB("x", fontnumber, (short) 3, (short) 1);
        if (bb != null) {

            xh = bb.getHeight();
        }
        return intToDimen(xh);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(String name) {

        // TODO mgn: getFontDimen unimplemented
        return null;
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

        int h = 0;

        // TODO encoding
        XtfBoundingBox bb =
                reader.mapCharCodeToBB(uc.getCodePoint(), fontnumber,
                    (short) 3, (short) 1);
        if (bb != null) {

            h = bb.getHeight();
        }
        return new Glue(intToDimen(h));
    }

    /**
     * {@inheritDoc}
     * 
     * Returns always 0pt.
     * 
     * @see org.extex.font.ExtexFont#getItalicCorrection(org.extex.core.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(UnicodeChar uc) {

        return Dimen.ZERO_PT;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getKerning(org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    public FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2) {

        // TODO mgn: nokerning beachten

        int size = 0;

        if (uc1 != null && uc2 != null) {
            size =
                    reader.mapCharCodetoKerning(uc1.getCodePoint(), uc2
                        .getCodePoint(), fontnumber, (short) 3, (short) 1);
        }

        return intToDimen(size);
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
     * @see org.extex.font.BackendFont#getName()
     */
    public String getName() {

        return getActualFontKey().getName();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getPfa()
     */
    public byte[] getPfa() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getPfb()
     */
    public byte[] getPfb() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * Returns allways 0.
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

        // TODO mgn: encoding!
        int width =
                reader.mapCharCodeToWidth("space", fontnumber, (short) 3,
                    (short) 1);
        if (width > 0) {
            return new Glue(intToDimen(width));
        }
        return new Glue(getEx());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getWidth(org.extex.core.UnicodeChar)
     */
    public FixedGlue getWidth(UnicodeChar uc) {

        int w =
                reader.mapCharCodeToWidth(uc.getCodePoint(), fontnumber,
                    (short) 3, (short) 1);
        return new Glue(intToDimen(w));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getXtf()
     */
    public byte[] getXtf() {

        return reader.getFontData();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#hasEncodingVector()
     */
    public boolean hasEncodingVector() {

        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#hasGlyph(org.extex.core.UnicodeChar)
     */
    public boolean hasGlyph(UnicodeChar uc) {

        String glyphname =
                reader.mapCharCodeToGlyphname(uc.getCodePoint(), fontnumber,
                    (short) 3, (short) 1);

        return reader.hasGlyph(glyphname, fontnumber);
    }

    /**
     * Convert a int value to a <code>Dimen</code>.
     * 
     * @param val the value
     * @return the <code>Dimen</code> value of the float value.
     */
    private FixedDimen intToDimen(int val) {

        int i = val * 1000 / reader.getUnitsPerEm();
        long l = getActualSize().getValue() * i / 1000;

        return new Dimen(l);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#isType1()
     */
    public boolean isType1() {

        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#isXtf()
     */
    public boolean isXtf() {

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.LoadableFont#loadFont(java.io.InputStream,
     *      org.extex.font.CoreFontFactory, org.extex.font.FontKey)
     */
    public void loadFont(InputStream in, CoreFontFactory factory, FontKey key)
            throws CorruptFontException,
                ConfigurationException {

        this.fontKey = key;

        if (key == null) {
            throw new IllegalArgumentException("fontkey");
        }

        try {

            glyphname = GlyphName.getInstance();

            reader = new XtfReader(in);

        } catch (IOException e) {
            throw new CorruptFontException(key, e.getLocalizedMessage());
        }

        if (key.getDimen("size") == null) {
            // use 10pt as default
            actualFontKey = factory.getFontKey(key, new Dimen(Dimen.ONE * 10));
        } else {
            actualFontKey = key;
        }

        // TODO mgn check the fontnumber
        // use at the moment the first font in cff

    }

}
