/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

import org.extex.core.Unicode;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.*;
import org.extex.font.exception.CorruptFontException;
import org.extex.font.exception.FontException;
import org.extex.font.format.TfmMetricFont;
import org.extex.font.format.pfb.PfbParser;
import org.extex.font.format.psfontmap.PsFontEncoding;
import org.extex.font.format.psfontmap.PsFontsMapReader;
import org.extex.font.format.texencoding.EncReader;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.framework.logger.LogEnabled;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.typesetter.CharNodeBuilder;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.TypesettingContextFactory;
import org.extex.typesetter.type.Node;
import org.extex.typesetter.type.node.CharNode;
import org.extex.typesetter.type.node.factory.NodeFactory;
import org.extex.util.file.random.RandomAccessInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class to load tfm fonts.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class LoadableTfmFont
        implements
            LoadableFont,
            BackendFont,
            ResourceAware,
            LogEnabled,
            TfmMetricFont,
            CharNodeBuilder {

    /**
     * The actual font key.
     */
    private FontKey actualFontKey;

    /**
     * The afm data.
     */
    private byte[] afmdata;

    /**
     * If type1 checked?
     */
    private boolean checkType1 = false;

    /**
     * If xtf checked?
     */
    private boolean checkXtf = false;

    /**
     * The code point map.
     */
    private Map<UnicodeChar, Integer> codepointmap;

    /**
     * The resource finder.
     */
    private ResourceFinder finder;

    /**
     * The encoding vector from the font.
     */
    private String[] fontEncvec;

    /**
     * The font key.
     */
    private FontKey fontKey;

    /**
     * Is the psfont.map file loaded?
     */
    private boolean loadPsFontMap = false;

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated with
     * a localizer for the name of this class.
     */
    private Localizer localizer = LocalizerFactory
        .getLocalizer(LoadableTfmFont.class);

    /**
     * The logger.
     */
    private Logger logger;

    /**
     * The psfonts.map entry.
     */
    private PsFontEncoding mapentry;

    /**
     * The pfa data.
     */
    private byte[] pfadata = null;

    /**
     * The pfb data.
     */
    private byte[] pfbdata = null;

    /**
     * The tfm reader.
     */
    private TfmReader reader;

    /**
     * Is this a type1 font?
     */
    private boolean type1 = false;

    /**
     * Is this a xtf font?
     */
    private boolean xtf = false;

    /**
     * The xtf data.
     */
    private byte[] xtfdata = null;

    /**
     * The code point map reverse.
     */
    private Map<Integer, UnicodeChar> codepointmapreverse = null;

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.CharNodeBuilder#buildCharNode(org.extex.core.UnicodeChar,
     *      org.extex.typesetter.tc.TypesettingContext,
     *      org.extex.typesetter.type.node.factory.NodeFactory,
     *      org.extex.typesetter.tc.TypesettingContextFactory)
     */
    @Override
    public CharNode buildCharNode(UnicodeChar uc, TypesettingContext tc,
            NodeFactory factory, TypesettingContextFactory tcFactory) {

        Node node = factory.getNode(tc, uc);
        if (node instanceof CharNode) {
            return (CharNode) node;
        }
        return null;
    }

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
                if (cp < 0 || cp > 255) {
                    cp = -1;
                }
            }
        }
        return cp;
    }

    /**
     * Check the tpye1.
     */
    private void checkType1() {

        if (!checkType1) {
            loadPsFontMap();

            if (mapentry != null) {
                String fontfile = mapentry.getFontfile();
                if (fontfile.matches(".*\\.[pP][fF][bBaA]")) {
                    type1 = true;

                    if (fontfile.matches(".*\\.[pP][fF][bB]")) {
                        InputStream pfbin =
                                finder.findResource(
                                    fontfile.replaceAll("\\.[pP][fF][bB]", ""),
                                    "pfb");
                        if (pfbin == null) {
                            logger.severe(localizer.format(
                                "Tfm.pfbFontNotFound", fontfile));
                        } else {
                            try {
                                RandomAccessInputStream rar =
                                        new RandomAccessInputStream(pfbin);
                                pfbdata = rar.getData();
                                rar.close();
                            } catch (IOException e) {
                                logger
                                    .severe(localizer.format(
                                        "Tfm.pfbReadError",
                                        e.getLocalizedMessage()));
                            }
                        }
                    } else {
                        // pfa
                        logger.severe("pfa file: not yet implemented");
                        // TODO mgn: add pfa ...
                    }
                }
                readerEncVec();
                createAfm();
            }
        }
        checkType1 = true;
    }

    /**
     * Check the xtf.
     */
    private void checkXtf() {

        if (!checkXtf) {
            loadPsFontMap();

            if (mapentry != null) {
                String fontfile = mapentry.getFontfile();
                if (fontfile.matches(".*\\.[tToO][tT][fF]")) {
                    xtf = true;
                    InputStream xtfin = null;
                    if (fontfile.matches(".*\\.[tT][tT][fF]")) {
                        finder.findResource(
                            fontfile.replaceAll("\\.[tT][tT][fF]", ""), "ttf");
                    } else {
                        finder
                            .findResource(
                                fontfile.replaceAll("\\.[tToO][tT][fF]", ""),
                                "otf");
                    }
                    if (xtfin == null) {
                        logger.severe(localizer.format("Tfm.xtfFontNotFound",
                            fontfile));
                    } else {
                        try {
                            RandomAccessInputStream rar =
                                    new RandomAccessInputStream(xtfin);
                            xtfdata = rar.getData();
                            rar.close();
                        } catch (IOException e) {
                            logger.severe(localizer.format("Tfm.xtfReadError",
                                e.getLocalizedMessage()));
                        }
                    }
                }
                readerEncVec();
            }
        }
        checkXtf = true;
    }

    /**
     * Convert Glue to the afm value.
     * 
     * @param val The glue value.
     * @return Return the afm value.
     */
    private int convertGlue2AfmValue(FixedGlue val) {

        return (int) (val.getLength().getValue() * 1000 / getActualSize()
            .getValue());
    }

    /**
     * Create the temporary afm data.
     */
    private void createAfm() {

        if (afmdata == null && fontEncvec != null) {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                BufferedWriter writer =
                        new BufferedWriter(new OutputStreamWriter(out,
                            "US-ASCII"));
                writer.write("StartFontMetrics 2.0\n");
                writer.write("FontName " + actualFontKey.getName() + "\n");
                writer.write("FullName " + actualFontKey.getName() + "\n");
                writer.write("FamilyName " + actualFontKey.getName() + "\n");
                writer.write("Weight Regular\n");
                writer.write("StartCharMetrics " + fontEncvec.length + "\n");
                for (int i = 0; i < fontEncvec.length; i++) {
                    writer.write("C ");
                    writer.write(String.valueOf(i));
                    writer.write(" ; WX ");
                    writer.write(String
                        .valueOf(convertGlue2AfmValue(getWidth(UnicodeChar
                            .get(i)))));
                    writer.write(" ; N ");
                    writer.write(fontEncvec[i]);
                    writer.write("\n");
                }
                writer.write("EndCharMetrics\n");
                writer.write("EndFontMetrics\n");
                writer.write("\n");
                writer.close();
                afmdata = out.toByteArray();

            } catch (Exception e) {
                logger.severe(localizer.format("Tfm.afmWriteError",
                    e.getLocalizedMessage()));
            }

        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
     */
    @Override
    public void enableLogging(Logger logger) {

        this.logger = logger;

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BaseFont#getActualFontKey()
     */
    @Override
    public FontKey getActualFontKey() {

        return actualFontKey;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getActualSize()
     */
    @Override
    public FixedDimen getActualSize() {

        return actualFontKey.getDimen(FontKey.SIZE);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getAfm()
     */
    public byte[] getAfm() {

        checkType1();
        return afmdata;
    }

    /**
     * Returns the checksum.
     * 
     * @return Returns the checksum.
     * @see org.extex.typesetter.tc.font.Font#getCheckSum()
     */
    public int getCheckSum() {

        return reader.getChecksum();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getDepth(org.extex.core.UnicodeChar)
     */
    @Override
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
    @Override
    public FixedDimen getDesignSize() {

        return reader.getDesignSize();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getEm()
     */
    @Override
    public FixedDimen getEm() {

        return getFontDimen("QUAD");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getEncodingForChar(int)
     */
    public int getEncodingForChar(int codepoint) {

        return -1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getEncodingVectors()
     */
    public List<String[]> getEncodingVectors() {

        checkType1();
        checkXtf();
        if (fontEncvec == null) {
            return null;
        }
        ArrayList<String[]> l = new ArrayList<String[]>();
        l.add(fontEncvec);
        return l;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getEx()
     */
    @Override
    public FixedDimen getEx() {

        return getFontDimen("XHEIGHT");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getFontDimen(java.lang.String)
     */
    @Override
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
    @Override
    public FontKey getFontKey() {

        return fontKey;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getFontName()
     */
    @Override
    public String getFontName() {

        return reader.getFontname();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getHeight(org.extex.core.UnicodeChar)
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public String getName() {

        return fontKey.getName();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getPfa()
     */
    @Override
    public byte[] getPfa() {

        checkType1();
        if (pfadata != null) {
            return pfadata;
        }
        if (pfbdata != null) {

            try {
                PfbParser parser = new PfbParser(pfbdata);
                ByteArrayOutputStream pfa = new ByteArrayOutputStream();
                parser.toPfa(pfa);
                pfadata = pfa.toByteArray();

            } catch (Exception e) {
                // logger.severe("pfb file: error: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#getPfb()
     */
    @Override
    public byte[] getPfb() {

        checkType1();
        return pfbdata;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getScaleFactor()
     */
    @Override
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
    @Override
    public FixedGlue getSpace() {

        return new Glue(getFontDimen("SPACE"), getFontDimen("STRETCH"),
            getFontDimen("SHRINK"));
    }

    /**
     * Returns the Unicode char for the position or <code>null</code>, if not
     * found.
     * 
     * @param pos The position.
     * @return Returns the Unicode char.
     */
    @SuppressWarnings("boxing")
    public UnicodeChar getUnicodeChar(int pos) {

        if (codepointmapreverse == null) {

            codepointmapreverse = new HashMap<Integer, UnicodeChar>();

            for (UnicodeChar uc : codepointmap.keySet()) {
                codepointmapreverse.put(codepointmap.get(uc), uc);
            }
        }

        return codepointmapreverse.get(pos);

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.ExtexFont#getWidth(org.extex.core.UnicodeChar)
     */
    @Override
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
    @Override
    public byte[] getXtf() {

        checkXtf();
        return xtfdata;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#hasEncodingVector()
     */
    public boolean hasEncodingVector() {

        checkType1();
        checkXtf();
        if (fontEncvec != null) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.tc.font.Font#hasGlyph(org.extex.core.UnicodeChar)
     */
    @Override
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

        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#isType1()
     */
    @Override
    public boolean isType1() {

        checkType1();
        return type1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#isXtf()
     */
    @Override
    public boolean isXtf() {

        checkXtf();
        return xtf;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.LoadableFont#loadFont(java.io.InputStream,
     *      org.extex.font.CoreFontFactory, org.extex.font.FontKey)
     */
    @Override
    public void loadFont(InputStream in, CoreFontFactory factory, FontKey key)
            throws CorruptFontException,
                ConfigurationException {

        this.fontKey = key;

        if (key == null) {
            throw new IllegalArgumentException("fontkey");
        }
        if (factory == null) {
            throw new IllegalArgumentException("factory");
        }

        try {
            reader = new TfmReader(in, key.getName());
        } catch (IOException e) {
            throw new CorruptFontException(key, e.getLocalizedMessage());
        }

        if (key.getDimen(FontKey.SIZE) == null
                && key.getCount(FontKey.SCALE) == null) {
            actualFontKey = factory.getFontKey(key, reader.getDesignSize());
        } else if (key.getCount(FontKey.SCALE) != null) {
            // design size * scale / 1000
            actualFontKey =
                    factory.getFontKey(key, new Dimen(reader.getDesignSize()
                        .getValue()
                            * key.getCount(FontKey.SCALE).getValue()
                            / 1000));
        } else {
            actualFontKey = key;
        }

        String cs =
                reader.getCodingscheme().replaceAll("[^A-Za-z0-9]", "")
                    .toLowerCase();

        try {
            codepointmap = U2tFactory.getInstance().loadU2t(cs, factory);
        } catch (IOException e) {
            throw new CorruptFontException(key, e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new CorruptFontException(key, e.getLocalizedMessage());
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
     * Load the psfonts.map file.
     */
    private void loadPsFontMap() {

        if (!loadPsFontMap) {
            try {
                InputStream mapin = finder.findResource("psfonts", "map");
                PsFontsMapReader mapReader =
                        PsFontsMapReader.getInstance(mapin);

                mapentry = mapReader.getPSFontEncoding(fontKey.getName());
                if (mapentry == null) {
                    logger.severe(localizer.format("Tfm.psfontmapFontNotFound",
                        fontKey.getName()));
                    type1 = false;
                    xtf = false;
                }
            } catch (FontException e) {
                // map file is not available!
                // not use any font file
                logger.severe(localizer.format("Tfm.psfontmapError",
                    e.getLocalizedMessage()));
                type1 = false;
                xtf = false;
            }
        }
        loadPsFontMap = true;
    }

    /**
     * Read the encoding vector.
     */
    private void readerEncVec() {

        if (fontEncvec == null) {
            String encfile = mapentry.getEncfile();
            if (encfile != null && encfile.length() > 0) {
                InputStream encin = finder.findResource(encfile, "enc");
                if (encin != null) {
                    try {
                        EncReader encReader = new EncReader(encin);
                        fontEncvec = encReader.getTableWithoutSlash();
                    } catch (FontException e) {
                        logger.severe(localizer.format("Tfm.encReadError",
                            e.getLocalizedMessage()));
                    }
                } else {
                    logger.severe(localizer.format("Tfm.encNotFound", encfile));
                }
            }
        }
        if (fontEncvec == null) {
            // try to get the encoding vector from the pfbdata
            if (pfbdata != null) {
                try {
                    PfbParser pfbParser = new PfbParser(pfbdata);
                    fontEncvec = pfbParser.getEncoding();
                } catch (FontException e) {
                    logger.severe(localizer.format("Tfm.pfbReadError",
                        e.getLocalizedMessage()));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceAware#setResourceFinder(org.extex.resource.ResourceFinder)
     */
    @Override
    public void setResourceFinder(ResourceFinder finder) {

        this.finder = finder;

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.BackendFont#usedCharacter(org.extex.font.BackendCharacter)
     */
    public void usedCharacter(BackendCharacter bc) {

        // ignored

    }
}
