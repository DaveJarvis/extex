/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font;

import java.util.Map;

import org.extex.font.exception.FontException;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.type.StringList;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.resource.ResourceFinder;

/**
 * This interface describes the features of a font factory needed by the core.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface CoreFontFactory extends ResourceFinder {

    /**
     * Return a new instance.
     *
     * If the name is empty or null, then the <code>NullFont</code> are
     * returned.
     *
     * If no font is found, then <code>null</code> is returned.
     *
     * @param key the fount key
     *
     * @return Returns the new font instance.
     *
     * @throws ConfigurationException from the resource finder.
     * @throws FontException if a font error occurred.
     */
    ExtexFont getInstance(FontKey key) throws FontException;

    /**
     * Returns a font key for the font.
     *
     * @param fontName the name of the font.
     * @return the key for the font.
     */
    FontKey getFontKey(String fontName);

    /**
     * Returns a font key for the font.
     *
     * @param fontName  the name of the font.
     * @param size      the size of the font.
     * @return the key for the font.
     */
    FontKey getFontKey(String fontName, FixedDimen size);

    /**
     * Returns a font key for the font.
     *
     * @param fontName  the name of the font.
     * @param size      the size of the font.
     * @param map       the map for the key.
     * @return the key for the font.
     */
    FontKey getFontKey(String fontName, FixedDimen size, Map map);

    /**
     * Returns a font key for the font.
     *
     * @param fontKey   the key of the font.
     * @param size      the size of the font.
     * @return the key for the font.
     */
    FontKey getFontKey(FontKey fontKey, FixedDimen size);

    /**
     * Create a manager for the back-end font.
     *
     * @param fontTypes the font types, which the back-end can use.
     * @return the new manager.
     * @throws ConfigurationException rom the configuration system.
     */
    BackendFontManager createManager(StringList fontTypes);
}
