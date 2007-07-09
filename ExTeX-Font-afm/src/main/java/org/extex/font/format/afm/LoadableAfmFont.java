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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
import org.extex.font.fontparameter.FontParameter;
import org.extex.font.format.AfmMetricFont;
import org.extex.font.format.pfb.PfbParser;
import org.extex.font.unicode.GlyphName;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

/**
 * Class to load afm fonts.
 * 
 * <ul>
 * <li>The font has no design size; this is set to the size of the font key.</li>
 * <li>The EM size is set to the size of the font.</li>
 * <li>The font load the font dimen values over <code>FontParameter</code>.
 * <li>If the font has no glyph 'space', then ex is used for getSpace().</li>
 * </ul>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class LoadableAfmFont
        implements
            LoadableFont,
            ResourceAware,
            BackendFont,
            LogEnabled,
            AfmMetricFont {

    /**
     * The actual dynamic encoding vector.
     */
    private String[] actualDynEnc = null;

    /**
     * The actual position in the encoding vector.
     */
    private int actualDynPos = 0;

    /**
     * The actual font key.
     */
    private FontKey actualFontKey;

    /**
     * The list of encoding vectors.
     */
    private List<String[]> encodingVectors = null;

    /**
     * The position of the encoding vector.
     */
    private int encodingVectorsPos = 0;

    /**
     * The resource finder.
     */
    private ResourceFinder finder;

    /**
     * The map for the font dimen values.
     */
    private Map<String, FixedDimen> fontDimen =
            new HashMap<String, FixedDimen>();

    /**
     * The font key.
     */
    private FontKey fontKey;

    /**
     * The glyph name Unicode table.
     */
    private GlyphName glyphname;

    /**
     * Has multi fonts.
     */
    private boolean hasMultiFonts = false;

    /**
     * The last used char metric (for caching).
     */
    private AfmCharMetric lastUsedCm = null;

    /**
     * The last used Unicode char (for caching).
     */
    private UnicodeChar lastUsedUc = null;

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated
     * with a localizer for the name of this class.
     */
    private Localizer localizer =
            LocalizerFactory.getLocalizer(LoadableAfmFont.class);

    /**
     * The logger.
     */
    private Logger logger;

    /**
     * Use no kerning information.
     */
    private boolean nokerning = false;

    /**
     * Use no ligature information.
     */
    private boolean noligature = false;

    /**
     * The font parameter
     */
    private FontParameter param = null;

    /**
     * The afm parser.
     */
    private AfmParser parser;

    /**
     * The pfa data.
     */
    private byte[] pfadata;

    /**
     * The pfb data.
     */
    private byte[] pfbdata;

    /**
     * Map for the used code points and the corresponding encoding vector
     * position.
     */
    private Map<Integer, Integer> useCodepoints =
            new HashMap<Integer, Integer>();

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
     */
    public void enableLogging(Logger logger) {

        this.logger = logger;
    }

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
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getAfm()
     */
    public byte[] getAfm() {

        return parser.getFontData();
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
            String name = null;
            if (param != null) {
                name = param.getGlyphname(uc);
            }
            if (name == null) {
                name = glyphname.getGlyphname(uc);
            }
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
     * @see org.extex.font.BackendFont#getCheckSum()
     */
    public int getCheckSum() {

        return 0;
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
     * @see org.extex.font.BackendFont#getEncodingForChar(int)
     */
    public int getEncodingForChar(int codepoint) {

        Integer pos = useCodepoints.get(codepoint);
        if (pos != null) {
            return pos.intValue();
        }

        return -1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getEncodingVectors()
     */
    public List<String[]> getEncodingVectors() {

        return encodingVectors;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getEx()
     */
    public FixedDimen getEx() {

        FixedDimen val = fontDimen.get("XHEIGHT");
        if (val != null) {
            return val;
        }
        float xh = parser.getHeader().getXheight();
        return floatToDimen(xh);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(String name) {

        FixedDimen val = fontDimen.get(name);
        if (val != null) {
            return val;
        }
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

        if (nokerning) {
            return Dimen.ZERO_PT;
        }
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

        if (noligature) {
            return null;
        }
        AfmCharMetric cm1 = getCharMetric(uc1);

        if (cm1 != null) {
            AfmCharMetric cm2 = getCharMetric(uc2);
            if (cm2 != null) {
                String lig = cm1.getLigature(cm2.getN());
                if (lig != null) {
                    try {
                        UnicodeChar uc = null;
                        if (param != null) {
                            uc = param.getUnicode(lig);
                        }
                        if (uc == null) {
                            uc = GlyphName.getInstance().getUnicode(lig);
                        }
                        return uc;
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
     * @see org.extex.font.BackendFont#getName()
     */
    public String getName() {

        return actualFontKey.getName();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getPfa()
     */
    public byte[] getPfa() {

        return pfadata;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getPfb()
     */
    public byte[] getPfb() {

        if (pfbdata == null) {
            // search pfb file
            InputStream pfbin =
                    finder.findResource(actualFontKey.getName(), "pfb");
            if (pfbin == null) {
                logger.severe(localizer.format("Afm.missingPfb", actualFontKey
                    .getName()));
            } else {
                try {
                    PfbParser pfbParser = new PfbParser(pfbin);
                    pfbdata = pfbParser.getPfbdata();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    pfbParser.toPfa(out);
                    pfadata = out.toByteArray();
                } catch (Exception e) {
                    logger.severe(e.getLocalizedMessage());
                }
            }
        }
        return pfbdata;
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

        FixedDimen val = fontDimen.get("SPACE");
        if (val != null) {
            return new Glue(val);
        }
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
     * @see org.extex.font.BackendFont#getXtf()
     */
    public byte[] getXtf() {

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

        return getCharMetric(uc) != null ? true : false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#hasMultiFonts()
     */
    public boolean hasMultiFonts() {

        return hasMultiFonts;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#isType1()
     */
    public boolean isType1() {

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#isXtf()
     */
    public boolean isXtf() {

        return false;
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

            InputStream paramin =
                    finder.findResource(fontKey.getName(), "fontinfo");
            if (paramin != null) {
                param = new FontParameter(paramin);
            }

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

        setFontDimenValues();

        // noligature, nokerning
        if (actualFontKey.hasBoolean(FontKey.KERNING)) {
            boolean b = actualFontKey.getBoolean(FontKey.KERNING);
            nokerning = !b;
        }
        if (actualFontKey.hasBoolean(FontKey.LIGATURES)) {
            boolean b = actualFontKey.getBoolean(FontKey.LIGATURES);
            noligature = !b;
        }
    }

    /**
     * Set the font dimen values.
     */
    private void setFontDimenValues() {

        if (param != null) {
            Map<String, Integer> fd = param.getFontDimen();
            Iterator<String> it = fd.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Integer val = fd.get(key);
                if (val != null) {
                    int value = val.intValue();
                    fontDimen.put(key, floatToDimen(value));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceConsumer#setResourceFinder(org.extex.resource.ResourceFinder)
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

        // check, if the char is in the default encoding.
        int codepoint = bc.getId();
        if (codepoint >= 0 && codepoint < 256) {
            String glyph = parser.getDefaultEncodingVector()[codepoint];
            if (glyph != null && !glyph.equals(".notdef")) {
                // default encoding -> exit
                return;
            }
        }

        if (actualDynEnc == null) {
            actualDynEnc = new String[256];
            Arrays.fill(actualDynEnc, ".notdef");
            if (encodingVectors == null) {
                encodingVectors = new ArrayList<String[]>();
            }
            encodingVectors.add(actualDynEnc);
            encodingVectorsPos = encodingVectors.size() - 1;
            if (encodingVectors.size() > 1) {
                hasMultiFonts = true;
            }
        }
        if (!useCodepoints.containsKey(codepoint)) {
            useCodepoints.put(codepoint, encodingVectorsPos);

            try {
                String name =
                        GlyphName.getInstance().getGlyphname(
                            UnicodeChar.get(codepoint));
                actualDynEnc[actualDynPos++] = name;
                if (actualDynPos >= actualDynEnc.length) {
                    actualDynEnc = null;
                }

            } catch (IOException e) {
                logger.severe(localizer.format("Afm.errorGlyphName", e
                    .getLocalizedMessage()));
            }
        }
    }

}
