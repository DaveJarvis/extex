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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;

import org.extex.core.dimen.FixedDimen;
import org.extex.font.exception.FontException;
import org.extex.font.format.NullExtexFont;
import org.extex.font.manager.BackendFontManagerList;
import org.extex.framework.AbstractFactory;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.resource.PropertyConfigurable;
import org.extex.resource.ResourceConsumer;
import org.extex.resource.ResourceFinder;

/**
 * Factory to load a font.
 * 
 * The factory uses a cache (a weak map) to increase the speed.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FontFactoryImpl
        implements
            CoreFontFactory,
            ResourceConsumer,
            Configurable,
            PropertyConfigurable,
            BackendFontFactory {

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
     * Font map.
     */
    private Map<FontKey, LoadableFont> fontMap =
            new WeakHashMap<FontKey, LoadableFont>();

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
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configurable#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration configuration)
            throws ConfigurationException {

        this.config = configuration;

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.CoreFontFactory#getFontKey(java.lang.String)
     */
    public FontKey getFontKey(String fontName) {

        return keyFactory.newInstance(fontName);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.CoreFontFactory#getFontKey(java.lang.String,
     *      org.extex.core.dimen.FixedDimen)
     */
    public FontKey getFontKey(String fontName, FixedDimen size) {

        FontKey key = keyFactory.newInstance(fontName);
        return keyFactory.newInstance(key, FontKey.SIZE, size);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.CoreFontFactory#getFontKey(java.lang.String,
     *      org.extex.core.dimen.FixedDimen, java.util.Map)
     */
    public FontKey getFontKey(String fontName, FixedDimen size,
            Map<String, ?> map) {

        FontKey key = keyFactory.newInstance(fontName);
        key = keyFactory.newInstance(key, map);
        return keyFactory.newInstance(key, FontKey.SIZE, size);
    }

    /**
     * Return a new instance.
     * 
     * If the name is empty or null, then the <code>NullFont</code> are
     * returned.
     * 
     * If the font is found in the cache, the cached object is returned,
     * otherwise, the font is loaded from a file.
     * 
     * @param key the fount key
     * 
     * @return the new font instance.
     * 
     * @throws ConfigurationException from the resource finder.
     * @throws FontException if a font-error occurred.
     */
    public ExtexFont getInstance(FontKey key)
            throws ConfigurationException,
                FontException {

        if (key == null || key.getName() == null || key.getName().length() == 0) {
            return NULLFONT;
        }

        // in the cache
        LoadableFont font = fontMap.get(key);
        if (font != null) {
            return font;
        }

        Iterator<Configuration> it = config.iterator("Font");
        while (it.hasNext()) {
            Configuration subcfg = it.next();

            String attType = subcfg.getAttribute("type");

            InputStream in = finder.findResource(key.getName(), attType);

            if (in != null) {

                font = new Loader().getInstance(subcfg);
                if (font instanceof ResourceConsumer) {
                    ResourceConsumer consumer = (ResourceConsumer) font;
                    consumer.setResourceFinder(finder);
                }

                font.loadFont(in, this, key);

                // store in the cache
                fontMap.put(key, font);

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
         * @param subcfg the configuration.
         * @return the loadable font.
         * @throws ConfigurationException from the configuration system.
         */
        public LoadableFont getInstance(Configuration subcfg)
                throws ConfigurationException {

            return (LoadableFont) createInstanceForConfiguration(subcfg,
                LoadableFont.class);
        }

        /**
         * Returns the backend font manager.
         * 
         * @param subcfg the configuration.
         * @param finder the resource finder.
         * @param factory the backend font factory.
         * @return the backend font manager.
         * @throws ConfigurationException from the configuration system.
         */
        public BackendFontManager getManager(Configuration subcfg,
                ResourceFinder finder, BackendFontFactory factory)
                throws ConfigurationException {

            BackendFontManager manager =
                    (BackendFontManager) createInstanceForConfiguration(subcfg,
                        BackendFontManager.class);

            manager.setBackendFontFactory(factory);

            if (manager instanceof ResourceConsumer) {
                ((ResourceConsumer) manager).setResourceFinder(finder);
            }
            return manager;
        }
    }

    /**
     * @see org.extex.resource.PropertyConfigurable#setProperties(java.util.Properties)
     */
    public void setProperties(Properties properties) {

        prop = properties;
    }

    /**
     * @see org.extex.resource.ResourceConsumer#setResourceFinder(
     *      org.extex.resource.ResourceFinder)
     */
    public void setResourceFinder(ResourceFinder finder) {

        this.finder = finder;

    }

    /**
     * @see org.extex.resource.ResourceFinder#enableTracing(boolean)
     */
    public void enableTracing(boolean flag) {

        finder.enableTracing(flag);
    }

    /**
     * @see org.extex.resource.ResourceFinder#findResource(java.lang.String,
     *      java.lang.String)
     */
    public InputStream findResource(String name, String type)
            throws ConfigurationException {

        return finder.findResource(name, type);
    }

    /**
     * @see org.extex.font.CoreFontFactory#getFontKey(org.extex.font.FontKey,
     *      org.extex.core.dimen.FixedDimen)
     */
    public FontKey getFontKey(FontKey fontKey, FixedDimen size) {

        return keyFactory.newInstance(fontKey, FontKey.SIZE, size);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.CoreFontFactory#createManager(java.util.List)
     */
    public BackendFontManager createManager(List<String> fontTypes)
            throws ConfigurationException {

        if (fontTypes == null) {
            throw new IllegalArgumentException("fonttypes");
        }
        if (fontTypes.size() == 0) {
            throw new IllegalArgumentException("fonttypes");
        }
        BackendFontManagerList fmlist = new BackendFontManagerList();

        for (int i = 0, n = fontTypes.size(); i < n; i++) {

            Configuration cfg =
                    config.findConfiguration("FontType", fontTypes.get(i));
            if (cfg != null) {

                fmlist.add(new Loader().getManager(cfg, finder, this));
            }
        }

        if (fmlist.size() == 1) {
            return fmlist.get(0);
        }

        return fmlist;
    }

}
