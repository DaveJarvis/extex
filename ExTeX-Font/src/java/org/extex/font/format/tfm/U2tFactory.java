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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.extex.font.exception.CorruptedTfmFontMappingException;
import org.extex.type.UnicodeChar;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.resource.ResourceFinder;

/**
 * Factory for the Unicode to tex font mapping.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class U2tFactory {

    /**
     * The own factory.
     */
    private static U2tFactory factory;

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance.
     */
    public static U2tFactory getInstance() {

        if (factory == null) {
            factory = new U2tFactory();
        }
        return factory;
    }

    /**
     * Creates a new object only with getInstance().
     */
    private U2tFactory() {

        super();
    }

    /**
     * Returns the u2t map, or <code>null</code>,
     * if the property file is not found.
     *
     * mgn: use cache!
     *
     * @param name      the name of the property.
     * @param finder    the resource finder.
     * @return the u2t map, or <code>null</code>,
     *         if the property file is not found.
     * @throws IOException if a io error occurred.
     * @throws ConfigurationException from the configuration system.
     * @throws NumberFormatException if a parse error occured.
     */
    public Map loadU2t(final String name, final ResourceFinder finder)
            throws IOException, ConfigurationException, NumberFormatException {

        Map codepointmap = new HashMap();

        InputStream u2tin = finder.findResource(name, "u2t");

        if (u2tin != null) {
            Properties u2tprops = new Properties();
            u2tprops.load(u2tin);
            u2tin.close();

            Enumeration u2tenum = u2tprops.keys();
            while (u2tenum.hasMoreElements()) {
                String key = (String) u2tenum.nextElement();
                String value = u2tprops.getProperty(key);

                UnicodeChar uc = UnicodeChar.get(Integer.parseInt(key, 16));
                int texpos = Integer.parseInt(value, 16);
                codepointmap.put(uc, new Integer(texpos));
            }
            return codepointmap;
        }
        return null;
    }

}
