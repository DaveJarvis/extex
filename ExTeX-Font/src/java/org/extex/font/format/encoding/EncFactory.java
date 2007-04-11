/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.encoding;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.extex.font.exception.FontException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.resource.ResourceFinder;

/**
 * Factory for enc-files.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class EncFactory implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -2024818780342719008L;

    /**
     * Map.
     */
    private Map<String, EncReader> data;

    /**
     * The resource finder.
     */
    private transient ResourceFinder finder;

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated
     * with a localizer for the name of this class.
     */
    private transient Localizer localizer =
            LocalizerFactory.getLocalizer(EncFactory.class);

    /**
     * Create a new object.
     * 
     * @param afinder finder
     */
    public EncFactory(ResourceFinder afinder) {

        finder = afinder;
        data = new HashMap<String, EncReader>();
    }

    /**
     * Returns the encoding table.
     * 
     * @param filename the file name.
     * @return Returns the encoding table.
     * @throws FontException if an font-error occurred.
     * @throws ConfigurationException from the resource finder.
     */
    public String[] getEncodingTable(String filename)
            throws FontException,
                ConfigurationException {

        EncReader encreader = getEncReader(filename);
        return encreader.getTable();
    }

    /**
     * Returns the encoding table (without a slash in the name).
     * 
     * @param filename the file name.
     * @return Returns the encoding table.
     * @throws FontException if an font-error occurred.
     * @throws ConfigurationException from the resource finder.
     */
    public String[] getEncodingTableWithoutSlash(String filename)
            throws FontException,
                ConfigurationException {

        String[] table = getEncodingTable(filename);

        if (table != null) {
            for (int i = 0; i < table.length; i++) {
                if (table[i].startsWith("/")) {
                    table[i] = table[i].substring(1);
                }
            }
        }
        return table;
    }

    /**
     * Returns the encoding reader.
     * 
     * @param filename the file name.
     * @return Returns the encoding reader.
     * @throws FontException if an font-error occurred.
     * @throws ConfigurationException from the resource finder.
     */
    public EncReader getEncReader(String filename)
            throws FontException,
                ConfigurationException {

        EncReader encreader = data.get(filename);

        if (encreader == null) {
            InputStream in = finder.findResource(filename, "enc");
            if (in == null) {
                throw new FontException(localizer.format(
                    "EncFactory.FileNotFound", filename));
            }
            encreader = new EncReader(in);
            data.put(filename, encreader);
        }
        return encreader;
    }
}
