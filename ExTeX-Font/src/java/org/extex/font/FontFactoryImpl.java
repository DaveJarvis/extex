/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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
 */

package org.extex.font;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.extex.font.exception.FontException;
import org.extex.font.format.NullExtexFont;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.util.framework.AbstractFactory;
import org.extex.util.framework.configuration.Configurable;
import org.extex.util.framework.configuration.Configuration;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.resource.PropertyConfigurable;
import org.extex.util.resource.ResourceConsumer;
import org.extex.util.resource.ResourceFinder;

/**
 * Factory to load a font.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4728 $
 */
public class FontFactoryImpl
        implements
            CoreFontFactory,
            ResourceConsumer,
            Configurable,
            PropertyConfigurable {

    /**
     * The null font
     */
    private static final NullExtexFont NULLFONT = new NullExtexFont();

    /**
     * Configuration for <tt>Type</tt>
     */
    private Configuration config;

    /**
     * the file finder
     */
    private ResourceFinder finder;

    /**
     * BaseFont map.
     * mgn: WeakMap verwenden
     */
    private Map fountMap = new HashMap();

    /**
     * The font key factory.
     */
    private FontKeyFactory keyFactory = new FontKeyFactory();

    /**
     * properties
     */
    private Properties prop;

    /**
     * Creates a new object.
     */
    public FontFactoryImpl() {

        super();
    }

    /**
     * @see org.extex.util.framework.configuration.Configurable#configure(
     *      org.extex.util.framework.configuration.Configuration)
     */
    public void configure(final Configuration config)
            throws ConfigurationException {

        this.config = config;

    }

    /**
     * @see org.extex.font.CoreFontFactory#getFontKey(java.lang.String)
     */
    public FontKey getFontKey(final String fontName) {

        return keyFactory.newInstance(fontName);
    }

    /**
     * @see org.extex.font.CoreFontFactory#getFontKey(java.lang.String,
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public FontKey getFontKey(final String fontName, final FixedDimen size) {

        FontKey key = keyFactory.newInstance(fontName);
        return keyFactory.newInstance(key, "size", size);
    }

    /**
     * @see org.extex.font.CoreFontFactory#getFontKey(java.lang.String,
     *      org.extex.interpreter.type.dimen.FixedDimen, java.util.Map)
     */
    public FontKey getFontKey(final String fontName, final FixedDimen size,
            final Map map) {

        FontKey key = keyFactory.newInstance(fontName);
        key = keyFactory.newInstance(key, map);
        return keyFactory.newInstance(key, "size", size);
    }

    //    /**
    //     * Retrieve the fount; either one already known or a new one.
    //     *
    //     * @param key the font key
    //     *
    //     * @return the fount
    //     *
    //     * @throws ConfigurationException from the resource finder.
    //     * @throws FontException if a font-error occurred.
    //     */
    //    private ModifiableFount getFount(final FontKey key)
    //            throws ConfigurationException, FontException {
    //
    //        ModifiableFount fount = (ModifiableFount) (fountMap.get(key));
    //        if (fount == null) {
    //
    //            fount = loadFont(key);
    //            fountMap.put(key, fount);
    //        }
    //        return fount;
    //    }

    /**
     * Return a new instance.
     *
     * If the name is empty or null, then the <code>NullFont</code>
     * are returned.
     *
     * @param key the fount key
     *
     * @return the new font instance.
     *
     * @throws ConfigurationException from the resource finder.
     * @throws FontException if a font-error occurred.
     */
    public ExtexFont getInstance(final FontKey key)
            throws ConfigurationException, FontException {

        if (key == null || key.getName() == null || key.getName().length() == 0) {
            return NULLFONT;
        }

        Iterator it = config.iterator();
        while (it.hasNext()) {
            Configuration subcfg = (Configuration) it.next();

            String attType = subcfg.getAttribute("type");

            InputStream in = finder.findResource(key.getName(), attType);

            if (in != null) {

                LoadableFont font = new Loader().getInstance(subcfg);
                font.loadFont(in, this, key);

                return font;
            }

        }
        return null;
    }

    /**
     * Loader for the abstract factory.
     */
    private class Loader extends AbstractFactory {

        /**
         * Returns the loadable font. 
         *
         * @param subcfg    the configuration.
         * @return the loadable font.
         * @throws ConfigurationException from the configuration system.
         */
        public LoadableFont getInstance(final Configuration subcfg)
                throws ConfigurationException {

            return (LoadableFont) createInstanceForConfiguration(subcfg,
                    LoadableFont.class);
        }
    }

    //    /**
    //     * Return a new instance.
    //     *
    //     * If the name is empty or null, then the <code>NullFont</code>
    //     * are returned.
    //     *
    //     * @param tfm the tfm font
    //     * @param key the fount key
    //     *
    //     * @return Returns the new font instance.
    //     *
    //     * @throws ConfigurationException from the resource finder.
    //     * @throws FontException if a font error occurred.
    //     */
    //    public Font getInstance(final TFMFont tfm, final FontKey key)
    //            throws ConfigurationException, FontException {
    //
    //        if (tfm == null) {
    //            return new NullFont();
    //        }
    //
    //        ModifiableFount fount = (ModifiableFount) (fountMap.get(key));
    //        if (fount == null) {
    //            fount = new ModifiableFountTFM(key, tfm);
    //            fountMap.put(key, fount);
    //        }
    //
    //        return new FontImpl(fount);
    //    }

    //    /**
    //     * Return a new instance.
    //     *
    //     * If the name is empty or null, then the <code>NullFont</code>
    //     * are returned.
    //     *
    //     * @param vf the vf font
    //     * @param key the fount key
    //     *
    //     * @return Returns the new font instance.
    //     *
    //     * @throws ConfigurationException from the resource finder.
    //     * @throws FontException if a font-error occurred.
    //     */
    //    public Font getInstance(final VFFont vf, final FountKey key)
    //            throws ConfigurationException, FontException {
    //
    //        if (vf == null) {
    //            return new NullFont();
    //        }
    //
    //        ModifiableFount fount = (ModifiableFount) (fountMap.get(key));
    //        return new VirtualFontImpl(fount);
    //    }

    //    /**
    //     * Returns the psfontmapreader.
    //     *
    //     * @throws FontException if a font-error occurs.
    //     * @throws ConfigurationException from the config-system.
    //     * @return Returns the psfm.
    //     */
    //    public PSFontsMapReader getPsfm() throws FontException,
    //            ConfigurationException {
    //
    //        if (psfm == null) {
    //            ef = new EncFactory(finder);
    //
    //            InputStream psin = findResource("psfonts.map", "");
    //
    //            if (psin == null) {
    //                throw new FontMapNotFoundException();
    //            }
    //            psfm = new PSFontsMapReader(psin);
    //        }
    //        return psfm;
    //    }

    //    /**
    //     * Load the BaseFont.
    //     *
    //     * @param key the fount key
    //     *
    //     * @return the font or <code>null</code>, if not found
    //     *
    //     * @throws ConfigurationException from the resource finder
    //     * @throws FontException in case of a font error
    //     */
    //    private ModifiableFount loadFont(final FontKey key)
    //            throws ConfigurationException, FontException {
    //
    //        // efm ???
    //        try {
    //            EfmReader efmfont = readEFMFont(key.getName());
    //            if (efmfont != null) {
    //                return new ModifiableFountEFM(key, efmfont);
    //            }
    //        } catch (FontException e) {
    //            // try next
    //        }
    //
    //        // tfm ???
    //        try {
    //            TFMFont tfmfont = readTFMFont(key.getName());
    //            if (tfmfont != null) {
    //                return new ModifiableFountTFM(key, tfmfont);
    //            }
    //        } catch (FontException e) {
    //            // try next
    //        }
    //
    //        throw new FontNotFoundException(key.getName());
    //    }

    //    /**
    //     * Read an afm font.
    //     *
    //     * @param name the name of the afm file
    //     *
    //     * @return the afm font or <code>null</code>, if not found
    //     *
    //     * @throws ConfigurationException from the resource finder
    //     * @throws FontException in case of a font error
    //     */
    //    public AfmFont readAFMFont(final String name)
    //            throws ConfigurationException, FontException {
    //
    //        AfmFont font = null;
    //
    //        if (name != null) {
    //
    //            // efm
    //            InputStream fontfile = findResource(name, "afm");
    //
    //            if (fontfile != null) {
    //                try {
    //                    font = new AfmFont(fontfile, name);
    //                } catch (IOException e) {
    //                    throw new FontIOException(e.getMessage());
    //                }
    //            }
    //        }
    //        return font;
    //    }

    //    /**
    //     * Read a tfm font.
    //     *
    //     * @param name the name of the tfm file
    //     *
    //     * @return the tfm font or <code>null</code>, if not found
    //     *
    //     * @throws ConfigurationException from the resource finder
    //     * @throws FontException in case of a font error
    //     */
    //    public EfmReader readEFMFont(final String name)
    //            throws ConfigurationException, FontException {
    //
    //        EfmReader font = null;
    //
    //        if (name != null) {
    //
    //            InputStream fontfile = findResource(name, "efm");
    //
    //            if (fontfile != null) {
    //                font = new EfmReader(fontfile);
    //            }
    //        }
    //        return font;
    //    }

    //    /**
    //     * Read a tfm font.
    //     *
    //     * @param name  the name of the tfm file
    //     *
    //     * @return  the tfm font or <code>null</code>, if not found
    //     *
    //     * @throws ConfigurationException from the resource finder
    //     * @throws FontException in case of a font error
    //     */
    //    public TFMFont readTFMFont(final String name)
    //            throws ConfigurationException, FontException {
    //
    //        if (name == null) {
    //            return null;
    //        }
    //
    //        InputStream fontfile = findResource(name, "tfm");
    //
    //        if (fontfile == null) {
    //            return null;
    //        }
    //
    //        TFMFont font = null;
    //        String fontname = name.replaceAll("\\.tfm|\\.TFM", "");
    //
    //        try {
    //            font = new TFMFont(new RandomAccessInputStream(fontfile), fontname);
    //
    //            font.setFontMapEncoding(getPsfm(), ef);
    //
    //            String pfbfile = font.getPfbfilename();
    //            if (pfbfile != null) {
    //                // pfb file
    //                InputStream pfbin = findResource(pfbfile, "");
    //                if (pfbin == null) {
    //                    throw new FileNotFoundException(pfbfile);
    //                }
    //                try {
    //
    //                    font.setPfbParser(new PfbParser(pfbin));
    //                } catch (Exception e) {
    //                    throw new FontException(e.getMessage());
    //                }
    //            }
    //
    //            // cache the font ?
    //            String cachedir = (prop != null
    //                    ? prop.getProperty("fonts.cache")
    //                    : null);
    //            if (cachedir != null) {
    //                StringBuffer buf = new StringBuffer();
    //                buf.append(cachedir).append("/");
    //                buf.append(fontname).append(".").append("efm");
    //                // write to xml-file
    //                XMLStreamWriter writer = new XMLStreamWriter(
    //                        new FileOutputStream(buf.toString()), "ISO-8859-1");
    //                writer.setBeauty(true);
    //                writer.writeStartDocument();
    //                font.writeEFM(writer);
    //                writer.writeEndDocument();
    //                writer.close();
    //            }
    //        } catch (IOException e) {
    //            throw new FontIOException(e.getMessage());
    //        }
    //
    //        return font;
    //    }

    //    /**
    //     * Read a vf font.
    //     *
    //     * @param name  the name of the vf-file
    //     *
    //     * @return  the vf font or <code>null</code>, if not found
    //     *
    //     * @throws ConfigurationException from the resource finder
    //     * @throws FontException in case of a font error
    //     */
    //    public VFFont readVFFont(final String name) throws ConfigurationException,
    //            FontException {
    //
    //        VFFont font = null;
    //
    //        if (name != null) {
    //
    //            InputStream fontfile = findResource(name, "vf");
    //
    //            if (fontfile != null) {
    //
    //                //                try {
    //                //                    String fontname = name.replaceAll("\\.vf|\\.VF", "");
    //                //
    //                //                    // mgn: umbauen
    //                ////                    font = new VFFont(new RandomAccessInputStream(fontfile),
    //                ////                            fontname, this);
    //                //
    //                //                    //font.setFontMapEncoding(getPsfm(), ef);
    //                //
    //                //                } catch (IOException e) {
    //                //                    throw new FontIOException(e.getMessage());
    //                //                }
    //            }
    //        }
    //
    //        return font;
    //    }

    /**
     * @see org.extex.util.resource.PropertyConfigurable#setProperties(java.util.Properties)
     */
    public void setProperties(final Properties properties) {

        prop = properties;
    }

    /**
     * @see org.extex.util.resource.ResourceConsumer#setResourceFinder(
     *      org.extex.util.resource.ResourceFinder)
     */
    public void setResourceFinder(final ResourceFinder finder) {

        this.finder = finder;

    }

    /**
     * @see org.extex.util.resource.ResourceFinder#enableTracing(boolean)
     */
    public void enableTracing(final boolean flag) {

        finder.enableTracing(flag);
    }

    /**
     * @see org.extex.util.resource.ResourceFinder#findResource(java.lang.String,
     *      java.lang.String)
     */
    public InputStream findResource(final String name, final String type)
            throws ConfigurationException {

        return finder.findResource(name, type);
    }

    /**
     * @see org.extex.font.CoreFontFactory#getFontKey(org.extex.font.FontKey,
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public FontKey getFontKey(final FontKey fontKey, final FixedDimen size) {

        return keyFactory.newInstance(fontKey, "size", size);
    }

}
