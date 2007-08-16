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

package org.extex.font.format.vf;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.extex.core.Unicode;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.BackendCharacter;
import org.extex.font.BackendFont;
import org.extex.font.CoreFontFactory;
import org.extex.font.FontKey;
import org.extex.font.LoadableFont;
import org.extex.font.exception.CorruptFontException;
import org.extex.font.exception.FontException;
import org.extex.font.format.tfm.TfmFixWord;
import org.extex.font.format.tfm.TfmReader;
import org.extex.font.format.tfm.U2tFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

/**
 * Class to load the virtual font.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class LoadableVfFont
        implements
            LoadableFont,
            BackendFont,
            ResourceAware,
            LogEnabled {

    /**
     * The actual font key.
     */
    private FontKey actualFontKey;

    /**
     * The code point map.
     */
    private Map<UnicodeChar, Integer> codepointmap;

    /**
     * The resource finder.
     */
    private ResourceFinder finder;

    /**
     * The font key.
     */
    private FontKey fontKey;

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated
     * with a localizer for the name of this class.
     */
    private Localizer localizer =
            LocalizerFactory.getLocalizer(LoadableVfFont.class);

    /**
     * The logger.
     */
    private Logger logger;

    /**
     * The tfm reader.
     */
    private TfmReader reader;

    /**
     * The virtual font.
     */
    private VfFont vfFont;

    /**
     * Returns the char position of a Unicode char.
     * 
     * If the Unicode char is not defined then a negative value is returned.
     * 
     * @param uc the Unicode char.
     * @return the char position of a Unicode char.
     */
    private int charPos(UnicodeChar uc) {

        if (uc == null) {
            return -1;
        }

        int cp = uc.getCodePoint();
        if (cp >= Unicode.OFFSET && cp <= Unicode.OFFSET + 0xff) {
            cp = cp & 0xff;
        } else {
            // font: map code point to char position
            Integer ipos = codepointmap.get(uc);
            if (ipos != null) {
                cp = ipos.intValue();
            } else {
                cp = -1;
            }
        }
        return cp;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
     */
    public void enableLogging(Logger logger) {

        this.logger = logger;

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

        return actualFontKey.getDimen(FontKey.SIZE);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getAfm()
     */
    public byte[] getAfm() {

        // TODO mgn: getAfm unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getCheckSum()
     */
    public int getCheckSum() {

        return reader.getChecksum();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getDepth(org.extex.core.UnicodeChar)
     */
    public FixedGlue getDepth(UnicodeChar uc) {

        int cp = charPos(uc);
        if (cp >= 0 && reader.getDepth(cp) != null) {
            return new Glue((getActualSize().getValue() * reader.getDepth(cp)
                .getValue()) >> 20);
        }
        return FixedGlue.ZERO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return reader.getDesignSize();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getEm()
     */
    public FixedDimen getEm() {

        return getFontDimen("QUAD");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getEncodingForChar(int)
     */
    public int getEncodingForChar(int codepoint) {

        // TODO mgn: getEncodingForChar unimplemented
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getEncodingVectors()
     */
    public List<String[]> getEncodingVectors() {

        // TODO mgn: getEncodingVectors unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getEx()
     */
    public FixedDimen getEx() {

        return getFontDimen("XHEIGHT");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(String name) {

        TfmFixWord param = reader.getParamAsFixWord(name);

        return new Dimen(
            ((getActualSize().getValue() * param.getValue()) >> 20));
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

        return reader.getFontname();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getHeight(org.extex.core.UnicodeChar)
     */
    public FixedGlue getHeight(UnicodeChar uc) {

        int cp = charPos(uc);
        if (cp >= 0 && reader.getHeight(cp) != null) {
            return new Glue((getActualSize().getValue() * reader.getHeight(cp)
                .getValue()) >> 20);
        }
        return FixedGlue.ZERO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getItalicCorrection(org.extex.core.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(UnicodeChar uc) {

        int cp = charPos(uc);
        if (cp >= 0 && reader.getItalicCorrection(cp) != null) {
            return new Dimen((getActualSize().getValue() * reader
                .getItalicCorrection(cp).getValue()) >> 20);
        }
        return Dimen.ZERO_PT;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getKerning(org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    public FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2) {

        int cp1 = charPos(uc1);
        int cp2 = charPos(uc2);

        if (cp1 < 0 || cp2 < 0) {
            return Dimen.ZERO_PT;
        }
        return reader.getKerning(cp1, cp2).toDimen(getDesignSize());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getLigature(org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

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
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getName()
     */
    public String getName() {

        return fontKey.getName();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getPfa()
     */
    public byte[] getPfa() {

        // TODO mgn: getPfa unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getPfb()
     */
    public byte[] getPfb() {

        // TODO mgn: getPfb unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
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
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getSpace()
     */
    public FixedGlue getSpace() {

        return new Glue(getFontDimen("SPACE"), getFontDimen("STRETCH"),
            getFontDimen("SHRINK"));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getWidth(org.extex.core.UnicodeChar)
     */
    public FixedGlue getWidth(UnicodeChar uc) {

        int cp = charPos(uc);
        if (cp >= 0 && reader.getWidth(cp) != null) {
            return new Glue((getActualSize().getValue() * reader.getWidth(cp)
                .getValue()) >> 20);
        }
        return FixedGlue.ZERO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getXtf()
     */
    public byte[] getXtf() {

        // TODO mgn: getXtf unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#hasEncodingVector()
     */
    public boolean hasEncodingVector() {

        // TODO mgn: hasEncodingVector unimplemented
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#hasGlyph(org.extex.core.UnicodeChar)
     */
    public boolean hasGlyph(UnicodeChar uc) {

        int cp = charPos(uc);
        TfmFixWord w = reader.getWidth(cp);
        if (w == null || w.getValue() == 0) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#hasMultiFonts()
     */
    public boolean hasMultiFonts() {

        // TODO mgn: hasMultiFonts unimplemented
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#isType1()
     */
    public boolean isType1() {

        // TODO mgn: isType1 unimplemented
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#isXtf()
     */
    public boolean isXtf() {

        // TODO mgn: isXtf unimplemented
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.LoadableFont#loadFont(java.io.InputStream,
     *      org.extex.font.CoreFontFactory, org.extex.font.FontKey)
     */
    public void loadFont(InputStream in, CoreFontFactory factory,
            FontKey fontKey)
            throws CorruptFontException,
                ConfigurationException {

        this.fontKey = fontKey;

        if (fontKey == null) {
            throw new IllegalArgumentException("fontkey");
        }
        if (factory == null) {
            throw new IllegalArgumentException("factory");
        }

        try {

            InputStream tfmin = finder.findResource(fontKey.getName(), "tfm");
            if (tfmin == null) {
                throw new CorruptFontException(fontKey, localizer.format(
                    "VF.TfmNotFound", fontKey.getName()));
            }

            reader = new TfmReader(tfmin, fontKey.getName());

            // load vf
            vfFont = new VfFont(fontKey.getName(), in, reader);

        } catch (IOException e) {
            throw new CorruptFontException(fontKey, e.getLocalizedMessage());
        } catch (FontException e) {
            throw new CorruptFontException(fontKey, e.getLocalizedMessage());
        }

        if (fontKey.getDimen(FontKey.SIZE) == null
                && fontKey.getCount(FontKey.SCALE) == null) {
            actualFontKey = factory.getFontKey(fontKey, reader.getDesignSize());
        } else if (fontKey.getCount(FontKey.SCALE) != null) {
            // design size * scale / 1000
            actualFontKey =
                    factory.getFontKey(fontKey,
                        new Dimen(reader.getDesignSize().getValue()
                                * fontKey.getCount(FontKey.SCALE).getValue()
                                / 1000));
        } else {
            actualFontKey = fontKey;
        }

        String cs =
                reader.getCodingscheme().replaceAll("[^A-Za-z0-9]", "")
                    .toLowerCase();

        try {
            codepointmap = U2tFactory.getInstance().loadU2t(cs, factory);
        } catch (IOException e) {
            throw new CorruptFontException(fontKey, e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new CorruptFontException(fontKey, e.getLocalizedMessage());
        }

        if (codepointmap == null) {
            codepointmap = new HashMap<UnicodeChar, Integer>();
            // use default map
            for (int i = 0; i <= 255; i++) {
                codepointmap.put(UnicodeChar.get(i), new Integer(i));
            }
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceAware#setResourceFinder(org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder finder) {

        this.finder = finder;

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#usedCharacter(org.extex.font.BackendCharacter)
     */
    public void usedCharacter(BackendCharacter bc) {

        // TODO mgn: usedCharacter unimplemented

    }

}
