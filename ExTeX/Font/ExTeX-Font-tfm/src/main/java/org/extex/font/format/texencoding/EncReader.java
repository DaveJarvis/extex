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

package org.extex.font.format.texencoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.extex.font.exception.FontException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * Reader for encoding-files.
 * 
 * @see <a href="package-summary.html#font-enc">font encoding file</a>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class EncReader implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 4119582362704393554L;

    /**
     * The encoding name.
     */
    private String encname = "";

    /**
     * The list for the glyphs.
     */
    private Map<String, Integer> glyphlist = null;

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated with
     * a localizer for the name of this class.
     */
    private transient Localizer localizer = LocalizerFactory
        .getLocalizer(EncReader.class);

    /**
     * encoding table.
     */
    private String[] table;

    /**
     * Create a new object.
     * 
     * @param in input stream for reading
     * @throws FontException if an IO-error occurred
     */
    public EncReader(InputStream in) throws FontException {

        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(in));

            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!(line.startsWith("%") || line.equals(""))) {
                    // ignore all after '%'
                    int pos = line.indexOf('%');
                    if (pos >= 0) {
                        line = line.substring(0, pos - 1).trim();
                    }
                    buf.append(line).append(' ');
                }
            }
            reader.close();
            in.close();

            int fs = buf.indexOf("/");
            int first = buf.indexOf("[");
            int last = buf.lastIndexOf("]");
            if (fs < 0 || first < 0 || last < 0) {
                throw new FontException(
                    localizer.format("EncReader.WrongRange"));

            }
            String tablestring = buf.substring(first + 1, last).trim();
            table = tablestring.split("\\s");
            encname = buf.substring(fs + 1, first).trim();

        } catch (IOException e) {
            throw new FontException(e.getMessage());
        }
    }

    /**
     * Returns the encname.
     * 
     * @return Returns the encname.
     */
    public String getEncname() {

        return encname;
    }

    /**
     * Returns the position of the glyph in the table.
     * 
     * @param name The glyph name.
     * @return Returns the position of the glyph in the table or -1 if not
     *         found.
     */
    @SuppressWarnings("boxing")
    public int getPosition(String name) {

        if (glyphlist == null) {
            glyphlist = new HashMap<String, Integer>(table.length);
            for (int i = 0; i < table.length; i++) {
                glyphlist.put(table[i], i);
            }
        }
        String n = "/" + name.replaceAll("/", "");

        Integer val = glyphlist.get(n);
        if (val != null) {
            return val.intValue();
        }
        return -1;
    }

    /**
     * Returns the encoding table.
     * 
     * @return Returns the encoding table.
     */
    public String[] getTable() {

        return table;
    }

    /**
     * Returns the encoding table without a slash in the name.
     * 
     * @return Returns the encoding table.
     */
    public String[] getTableWithoutSlash() {

        String[] xtab = new String[table.length];
        for (int i = 0, n = xtab.length; i < n; i++) {
            xtab[i] = table[i].replaceAll("/", "");
        }
        return xtab;
    }
}
