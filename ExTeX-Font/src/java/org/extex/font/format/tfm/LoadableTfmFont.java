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

package org.extex.font.format.tfm;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.extex.font.CoreFontFactory;
import org.extex.font.FontKey;
import org.extex.font.LoadableFont;
import org.extex.font.exception.CorruptFontException;
import org.extex.font.exception.CorruptedTfmFontMappingException;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.glue.Glue;
import org.extex.type.UnicodeChar;
import org.extex.unicode.Unicode;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * Class to load tfm fonts.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class LoadableTfmFont implements LoadableFont {

    /**
     * The code point map.
     */
    private Map codepointmap;

    /**
     * The font key.
     */
    private FontKey fontKey;

    /**
     * The actual font key.
     */
    private FontKey actualFontKey;

    /**
     * The tfm reader.
     */
    private TfmReader reader;

    /**
     * @see org.extex.font.BaseFont#getActualFontKey()
     */
    public FontKey getActualFontKey() {

        return actualFontKey;
    }

    /**
     * @see org.extex.font.ExtexFont#getActualSize()
     */
    public FixedDimen getActualSize() {

        return actualFontKey.getDimen(FontKey.SIZE);
    }

    /**
     * @see org.extex.interpreter.type.font.Font#getCheckSum()
     */
    public int getCheckSum() {

        return reader.getChecksum();
    }

    /**
     * @see org.extex.font.ExtexFont#getDepth(org.extex.type.UnicodeChar)
     */
    public FixedGlue getDepth(final UnicodeChar uc) {

        int cp = charPos(uc);
        if (cp >= 0 && reader.getDepth(cp) != null) {
            return new Glue((getActualSize().getValue() * reader.getDepth(cp)
                    .getValue()) >> 20);
        }
        return Glue.ZERO;
    }

    /**
     * @see org.extex.font.ExtexFont#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return reader.getDesignSize();
    }

    /**
     * @see org.extex.font.ExtexFont#getEm()
     */
    public FixedDimen getEm() {

        return getFontDimen("QUAD");
    }

    /**
     * @see org.extex.font.ExtexFont#getEx()
     */
    public FixedDimen getEx() {

        return getFontDimen("XHEIGHT");
    }

    /**
     * @see org.extex.font.ExtexFont#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(final String name) {

        TfmFixWord param = reader.getParamAsFixWord(name);

        return new Dimen(
                ((getActualSize().getValue() * param.getValue()) >> 20));
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

        return reader.getFontname();
    }

    /**
     * @see org.extex.font.ExtexFont#getHeight(org.extex.type.UnicodeChar)
     */
    public FixedGlue getHeight(final UnicodeChar uc) {

        int cp = charPos(uc);
        if (cp >= 0 && reader.getHeight(cp) != null) {
            return new Glue((getActualSize().getValue() * reader.getHeight(cp)
                    .getValue()) >> 20);
        }
        return Glue.ZERO;
    }

    /**
     * @see org.extex.font.ExtexFont#getItalicCorrection(org.extex.type.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(final UnicodeChar uc) {

        int cp = charPos(uc);
        if (cp >= 0 && reader.getItalicCorrection(cp) != null) {
            return new Dimen((getActualSize().getValue() * reader
                    .getItalicCorrection(cp).getValue()) >> 20);
        }
        return Dimen.ZERO_PT;
    }

    /**
     * @see org.extex.font.ExtexFont#getKerning(org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
     */
    public FixedDimen getKerning(final UnicodeChar uc1, final UnicodeChar uc2) {

        int cp1 = charPos(uc1);
        int cp2 = charPos(uc2);

        if (cp1 < 0 || cp2 < 0) {
            return Dimen.ZERO_PT;
        }
        return reader.getKerning(cp1, cp2).toDimen(getDesignSize());
    }

    /**
     * @see org.extex.font.ExtexFont#getLigature(org.extex.type.UnicodeChar, org.extex.type.UnicodeChar)
     */
    public UnicodeChar getLigature(final UnicodeChar uc1, final UnicodeChar uc2) {

        int cp1 = charPos(uc1);
        int cp2 = charPos(uc2);

        if (cp1 < 0 || cp2 < 0) {
            return null;
        }
        int pos = reader.getLigature(cp1, cp2);
        if (pos < 0) {
            return null;
        }
        return UnicodeChar.get(Unicode.OFFSET + pos);
    }

    /**
     * @see org.extex.font.ExtexFont#getScaleFactor()
     */
    public FixedCount getScaleFactor() {

        FixedCount actualscale = actualFontKey.getCount(FontKey.SCALE);
        if (actualscale == null) {
            return new Count(getActualSize().getValue() * 1000
                    / getDesignSize().getValue());
        }
        return actualscale;
    }

    /**
     * @see org.extex.font.ExtexFont#getSpace()
     */
    public FixedGlue getSpace() {

        return new Glue(getFontDimen("SPACE"), getFontDimen("STRETCH"),
                getFontDimen("SHRINK"));
    }

    /**
     * @see org.extex.font.ExtexFont#getWidth(org.extex.type.UnicodeChar)
     */
    public FixedGlue getWidth(final UnicodeChar uc) {

        int cp = charPos(uc);
        if (cp >= 0 && reader.getWidth(cp) != null) {
            return new Glue((getActualSize().getValue() * reader.getWidth(cp)
                    .getValue()) >> 20);
        }
        return Glue.ZERO;

    }

    /**
     * Returns the char position of a Unicode char.
     *
     * If the Unicode char is not defined then a negative value is returned.
     *
     * @param uc    the Unicode char.
     * @return the char position of a Unicode char.
     */
    private int charPos(final UnicodeChar uc) {

        if (uc == null) {
            return -1;
        }

        int cp = uc.getCodePoint();
        if (cp >= Unicode.OFFSET && cp <= Unicode.OFFSET + 0xff) {
            cp = cp & 0xff;
        } else {
            // font: map code point to char position
            Integer ipos = (Integer) codepointmap.get(uc);
            if (ipos != null) {
                cp = ipos.intValue();
            } else {
                cp = -1;
            }
        }
        return cp;
    }

    /**
     * @see org.extex.interpreter.type.font.Font#hasGlyph(org.extex.type.UnicodeChar)
     */
    public boolean hasGlyph(final UnicodeChar uc) {

        int cp = charPos(uc);
        TfmFixWord w = reader.getWidth(cp);
        if (w == null || w.getValue() == 0) {
            return false;
        }
        return true;
    }

    /**
     * @see org.extex.font.LoadableFont#loadFont(java.io.InputStream,
     *      org.extex.font.CoreFontFactory, org.extex.font.FontKey)
     */
    public void loadFont(final InputStream in, final CoreFontFactory factory,
            final FontKey fontKey) throws CorruptFontException,
            ConfigurationException {

        this.fontKey = fontKey;

        if (fontKey == null) {
            throw new IllegalArgumentException("fontkey");
        }
        if (factory == null) {
            throw new IllegalArgumentException("factory");
        }

        try {
            reader = new TfmReader(in, fontKey.getName());
        } catch (IOException e) {
            throw new CorruptFontException(fontKey, e.getLocalizedMessage());
        }

        if (fontKey.getDimen(FontKey.SIZE) == null
                && fontKey.getCount(FontKey.SCALE) == null) {
            actualFontKey = factory.getFontKey(fontKey, reader.getDesignSize());
        } else if (fontKey.getCount(FontKey.SCALE) != null) {
            // design size * scale / 1000
            actualFontKey = factory.getFontKey(fontKey, new Dimen(reader
                    .getDesignSize().getValue()
                    * fontKey.getCount(FontKey.SCALE).getValue() / 1000));
        } else {
            actualFontKey = fontKey;
        }

        String cs = reader.getCodingscheme().replaceAll("[^A-Za-z0-9]", "")
                .toLowerCase();

        try {
            codepointmap = U2tFactory.getInstance().loadU2t(cs, factory);
        } catch (IOException e) {
            throw new CorruptedTfmFontMappingException(fontKey, e
                    .getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new CorruptedTfmFontMappingException(fontKey, e
                    .getLocalizedMessage());
        }

        if (codepointmap == null) {
            codepointmap = new HashMap();
            // use default map
            for (int i = 0; i <= 255; i++) {
                codepointmap.put(UnicodeChar.get(i), new Integer(i));
            }
        }
    }

}
