/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.unicodeFont;

import java.io.InputStream;
import java.util.Properties;

import org.extex.font.FontKey;
import org.extex.font.exception.FontException;
import org.extex.framework.AbstractFactory;
import org.extex.framework.Registrar;
import org.extex.framework.RegistrarException;
import org.extex.framework.RegistrarObserver;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.PropertyConfigurable;
import org.extex.resource.ResourceConsumer;
import org.extex.resource.ResourceFinder;

import de.dante.extex.unicodeFont.exception.FontNotFoundException;
import de.dante.extex.unicodeFont.format.tex.psfontmap.PsFontEncoding;
import de.dante.extex.unicodeFont.format.tex.psfontmap.PsFontsMapReader;
import de.dante.extex.unicodeFont.key.FontKeyConfigurable;
import de.dante.extex.unicodeFont.type.FontInit;
import de.dante.extex.unicodeFont.type.FontPfb;
import de.dante.extex.unicodeFont.type.InputStreamConfigurable;
import de.dante.extex.unicodeFont.type.TexFont;

/**
 * Factory for the font system.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class FontFactoryImpl extends AbstractFactory
        implements
            FontFactory,
            ResourceConsumer,
            RegistrarObserver,
            PropertyConfigurable {

    /**
     * Type tfm font.
     */
    private static final String TFM = "tfm";

    /**
     * Type vf font.
     */
    private static final String VF = "vf";

    /**
     * Create a new object.
     *
     * @param config    The configuration object.
     * @param theFinder The resource finder.
     * @throws ConfigurationException from the configuration system.
     */
    public FontFactoryImpl(Configuration config,
            ResourceFinder theFinder) throws ConfigurationException {

        super();

        finder = theFinder;
        configure(config);

        Registrar.register(this, TexFont.class);

    }

    // MGN cache!!!

    /**
     * @see de.dante.extex.unicodeFont.FontFactory#newInstance(org.extex.font.FontKey)
     */
    public TexFont newInstance(FontKey key)
            throws ConfigurationException, FontException {

        Configuration config = getConfiguration();

        String fontname = key.getName();
        String type = null;

        // check for psfont.map
        loadPsFontMap();

        // tfm or vf
        PsFontEncoding tfmVfEnc = psfontsmap.getPSFontEncoding(fontname);

        // file type
        String fileType = "";
        String externalfile = "";

        // ----------------------------------
        // tfm or vf with pfb
        // ----------------------------------
        if (tfmVfEnc != null) {
            // check the font file: pfb, ttf, ...
            String ext = tfmVfEnc.getFontfileExtension();
            externalfile = tfmVfEnc.getFontfile();

            if (isVF(fontname)) {
                type = VF + "_" + ext;
                fileType = VF;
            } else {
                type = TFM + "_" + ext;
                fileType = TFM;
            }
        }

        // other fonts: TODO incomplete

        Configuration cfg = config.findConfiguration(type);

        if (cfg != null) {
            InputStream in = finder.findResource(key.getName(), fileType);

            if (in != null) {
                TexFont texFont = (TexFont) createInstanceForConfiguration(cfg,
                        TexFont.class);

                //                if (texFont instanceof LogEnabled) {
                //                    ((LogEnabled) texFont).enableLogging(getLogger());
                //                }
                if (texFont instanceof PropertyConfigurable) {
                    ((PropertyConfigurable) texFont).setProperties(properties);
                }
                if (texFont instanceof InputStreamConfigurable) {
                    ((InputStreamConfigurable) texFont).setInputStream(in);
                }
                if (texFont instanceof FontKeyConfigurable) {
                    ((FontKeyConfigurable) texFont).setFontKey(key);
                }
                if (texFont instanceof FontPfb) {
                    InputStream inExtFont = finder.findResource(externalfile,
                            "");
                    if (inExtFont != null) {
                        ((FontPfb) texFont).setPfb(inExtFont);
                    }
                }

                // Initialize
                if (texFont instanceof FontInit) {
                    ((FontInit) texFont).init();
                }
                return texFont;
            }

        }

        //        Iterator it = config.iterator("font");
        //        while (it.hasNext()) {
        //            Configuration cfg = (Configuration) it.next();
        //
        //            String fileextension = cfg.getAttribute("fileextension");
        //            if (fileextension == null) {
        //                throw new FontNoFileExtensionFoundException();
        //            }
        //
        //            InputStream in = finder.findResource(key.getName(), fileextension);
        //
        //            if (in != null) {
        //                TexFont texFont = (TexFont) createInstanceForConfiguration(cfg,
        //                        TexFont.class);
        //
        //                //                if (texFont instanceof LogEnabled) {
        //                //                    ((LogEnabled) texFont).enableLogging(getLogger());
        //                //                }
        //                if (texFont instanceof PropertyConfigurable) {
        //                    ((PropertyConfigurable) texFont).setProperties(properties);
        //                }
        //                if (texFont instanceof InputStreamConfigurable) {
        //                    ((InputStreamConfigurable) texFont).setInputStream(in);
        //                }
        //                if (texFont instanceof FontKeyConfigurable) {
        //                    ((FontKeyConfigurable) texFont).setFontKey(key);
        //                }
        //                // init
        //                if (texFont instanceof FontInit) {
        //                    ((FontInit) texFont).init();
        //                }
        //                return texFont;
        //            }
        //        }

        throw new FontNotFoundException(key.getName());
    }

    /**
     * Check, if the font is a virtual font (vf).
     * <p>
     * Test, if a vf file exists.
     * </p>
     * @param fontname  The font name.
     * @return Returns <code>true</code>, if the font is a virtual font.
     * @throws ConfigurationException from the configuration system.
     */
    private boolean isVF(String fontname) throws ConfigurationException {

        InputStream in = finder.findResource(fontname, VF);
        if (in != null) {
            return true;
        }
        return false;
    }

    /**
     * The psfonts.map reader.
     */
    private PsFontsMapReader psfontsmap;

    /**
     * Load the psfont.map file (only once).
     * @throws ConfigurationException from the configuration system.
     * @throws FontException  if a font error occurs.
     */
    private void loadPsFontMap() throws ConfigurationException, FontException {

        if (psfontsmap == null) {
            InputStream in = finder.findResource("psfonts.map", "");

            psfontsmap = new PsFontsMapReader(in);
        }
    }

    /**
     * The resource finder.
     */
    private ResourceFinder finder;

    /**
     * @see org.extex.resource.ResourceConsumer#setResourceFinder(
     *      org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder theFinder) {

        finder = theFinder;
    }

    /**
     * @see org.extex.framework.AbstractFactory#reconnect(java.lang.Object)
     */
    public Object reconnect(Object instance) throws RegistrarException {

        Object object = super.reconnect(instance);

        if (object instanceof TexFont) {
            TexFont f = (TexFont) object;
            try {
                return newInstance(f.getFontKey());
            } catch (ConfigurationException e) {
                throw new RegistrarException(e);
            } catch (FontException e) {
                throw new RegistrarException(e);
            }
        }
        // TODO resourceBundle
        throw new RegistrarException("no TexFont");
    }

    /**
     * the properties
     */
    private Properties properties;

    /**
     * @see org.extex.resource.PropertyConfigurable#setProperties(
     *      java.util.Properties)
     */
    public void setProperties(Properties theProperties) {

        properties = theProperties;
    }
}
