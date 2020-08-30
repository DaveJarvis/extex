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

package org.extex.font.unicode;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.extex.core.UnicodeChar;

/**
 * This class manage the correlation between the glyph name and the Unicode
 * value.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/

public final class GlyphName {

    /**
     * The file name.
     */
    private static final String FILE = "GlyphName.properties";

    /**
     * The singleton instance.
     */
    private static GlyphName glyphname;

    /**
     * hex value.
     */
    private static final int HEX = 16;

    /**
     * The initial size for the map.
     */
    private static final int INITSIZE = 5000;

    /**
     * Return the instance of {@code GlyphName}.
     * 
     * @return Return the instance of {@code GlyphName}.
     * @throws IOException if an IO-error occurred.
     */
    public static GlyphName getInstance() throws IOException {

        if (glyphname == null) {
            glyphname = new GlyphName();
        }
        return glyphname;
    }

    /**
     * The glyph name map.
     */
    private final Map<String, UnicodeChar> glyphmap;

    /**
     * The Unicode map.
     */
    private final Map<UnicodeChar, String> unicodemap;

    /**
     * Create a new object.
     * 
     * Read the {@code FILE} and parse it.
     * 
     * @throws IOException if an IO-error occurred.
     */
    private GlyphName() throws IOException {

        glyphmap = new HashMap<String, UnicodeChar>(INITSIZE);
        unicodemap = new HashMap<UnicodeChar, String>(INITSIZE);

        Properties prop = new Properties();
        prop.load(getClass().getResourceAsStream(FILE));

        Enumeration<Object> keyenum = prop.keys();

        while (keyenum.hasMoreElements()) {
            String key = (String) keyenum.nextElement();
            String value = prop.getProperty(key).trim();

            // only one 16-bit value; rest ignored
            // TODO: add combined Unicode characters
            if (value.length() <= 4) {
                try {
                    UnicodeChar uc =
                            UnicodeChar.get(Integer.parseInt(value, HEX));
                    glyphmap.put(key, uc);
                    unicodemap.put(uc, key);
                } catch (NumberFormatException e) {
                    throw new IOException(e.getMessage());
                }
            }
        }
    }

    /**
     * Returns the name of the glyph, or {@code null}, if not found.
     * 
     * @param uc the Unicode char.
     * @return the name of the glyph, or {@code null}, if not found.
     */
    public String getGlyphname(UnicodeChar uc) {

        return unicodemap.get(uc);
    }

    /**
     * Returns the {@code UnicodeChar} for the glyph name or
     * {@code null}, if not found.
     * 
     * @param name The glyph name.
     * @return Returns the {@code UnicodeChar} for the glyph name or
     *         {@code null}, if not found.
     */
    public UnicodeChar getUnicode(String name) {

        return glyphmap.get(name);
    }

}
