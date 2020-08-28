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

package org.extex.font.format.xtf.tables.tag;

import java.io.IOException;

import org.extex.util.file.random.RandomAccessR;

/**
 * OpenType Layout Tag Registry.
 * <p>
 * The Tag Registry defines the OpenType Layout tags that Microsoft supports.
 * OpenType Layout tags are 4-byte character strings that identify the scripts,
 * language systems, features and baselines in a OpenType Layout font. The
 * registry establishes conventions for naming and using these tags. Registered
 * tags have a specific meaning and convey precise information to developers and
 * text-processing clients of OpenType Layout. Microsoft encourages font
 * developers to use registered tags to assure compatibility and ease of use
 * across fonts, applications, and operating systems.
 * </p>
 * <p>
 * The following sections contain sample sets of commonly used tags for scripts,
 * language systems, and baselines. Microsoft will supply a list of additional
 * tags upon&nbsp;request. In addition, the Feature Tag section defines all the
 * features and feature tags Microsoft has developed and registered to date,
 * including descriptions of the functions of each&nbsp;feature.
 * </p>
 * <p>
 * All tags are 4-byte character strings composed of a limited set of ASCII
 * characters in the 0x20-0x7E range. A script tag can consist of 4 or less
 * lowercase letters. If a script tag consists of three or less lowercase
 * letters, the letters are followed by the requisite number of spaces (0x20),
 * each consisting of a single byte.
 * </p>
 * 
 * <p>
 * OpenType layout tag registry for:
 * </p>
 * <ul>
 * <li>Script tags</li>
 * <li>Language tags</li>
 * <li>Feature tags</li>
 * <li>Baseline tags</li>
 * </ul>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class Tag {

    /**
     * Format the tag name.
     * 
     * @param name The name.
     * @return returns the formated (4 byte) name.
     */
    protected static String format(String name) {

        String xtagname;
        if (name == null) {
            xtagname = "    ";
        } else {
            xtagname = (name + "    ").substring(0, 4);
        }
        return xtagname;
    }

    /**
     * Get a new Tag.
     * <p>
     * The tag can be a {@link ScriptTag}, a {@link FeatureTag}, a
     * {@link LanguageSystemTag} or <code>null</code>, if not found.
     * </p>
     * 
     * @param name The name of the tag.
     * @return Returns a new tag.
     */
    public static Tag getInstance(String name) {

        if (ScriptTag.containsTag( name)) {
            return ScriptTag.getInstance(name);
        }
        if (FeatureTag.isInList(name)) {
            return FeatureTag.getInstance(name);
        }
        if (LanguageSystemTag.isInList(name)) {
            return LanguageSystemTag.getInstance(name);
        }
        return null;
    }

    /**
     * The tag name.
     */
    private String tagname;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @throws IOException if a io-error occurred.
     */
    protected Tag(RandomAccessR rar) throws IOException {

        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            buf.append((char) rar.readUnsignedByte());
        }
        tagname = format(buf.toString());
    }

    /**
     * Creates a new object.
     * 
     * @param name The name of the tag.
     */
    protected Tag(String name) {

        tagname = format(name);
    }

    /**
     * Getter for tag.
     * 
     * @return the tag
     */
    public String getTag() {

        return tagname;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return tagname;
    }

}
