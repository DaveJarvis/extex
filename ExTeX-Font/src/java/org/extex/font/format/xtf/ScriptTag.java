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

package org.extex.font.format.xtf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.extex.util.file.random.RandomAccessR;

/**
 * Script Tags.
 * 
 * <p>
 * Script tags identify the scripts represented in a OpenType Layout font.
 * </p>
 * <p>
 * All tags are 4-byte character strings composed of a limited set of ASCII
 * characters in the 0x20-0x7E range. A script tag can consist of 4 or less
 * lowercase letters. If a script tag consists of three or less lowercase
 * letters, the letters are followed by the requisite number of spaces (0x20),
 * each consisting of a single byte.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 0001 $
 */
public class ScriptTag extends Tag {

    /**
     * The map for the names.
     */
    private static Map<String, String> map = null;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @throws IOException if a io-error occurred.
     */
    public ScriptTag(RandomAccessR rar) throws IOException {

        super(rar);

        if (map == null) {
            map = new HashMap<String, String>(40);
            map.put("arab", "Arabic");
            map.put("armn", "Armenian");
            map.put("beng", "Bengali");
            map.put("bopo", "Bopomofo");
            map.put("brai", "Braille");
            map.put("byzm", "Byzantine Music");
            map.put("cans", "Canadian Syllabics");
            map.put("cher", "Cherokee");
            map.put("hani", "CJK Ideographic");
            map.put("cyrl", "Cyrillic");
            map.put("DFLT", "Default");
            map.put("deva", "Devanagari");
            map.put("ethi", "Ethiopic");
            map.put("geor", "Georgian");
            map.put("grek", "Greek");
            map.put("gujr", "Gujarati");
            map.put("guru", "Gurmukhi");
            map.put("jamo", "Hangul Jamo");
            map.put("hang", "Hangul");
            map.put("hebr", "Hebrew");
            map.put("kana", "Hiragana");
            map.put("knda", "Kannada");
            map.put("kana", "Katakana");
            map.put("khmr", "Khmer");
            map.put("lao", "Lao");
            map.put("latn", "Latin");
            map.put("mlym", "Malayalam");
            map.put("mong", "Mongolian");
            map.put("mymr", "Myanmar");
            map.put("ogam", "Ogham");
            map.put("orya", "Oriya");

        }
    }

    /**
     * Returns the name of the tag.
     * <p>
     * When the ScriptList table is searched for a script, and no entry is
     * found, and there is an entry for the 'dflt' script, then this entry must
     * be used. Furthermore, the Script table for the 'dflt' script must have a
     * non-NULL DefaultLangSys and a LangSysCount equal to 0; in other words,
     * there is only a default language for the default script.
     * </p>
     * 
     * @return Returns the name of the tag.
     */
    public String getName() {

        String name = map.get(toString().trim());
        if (name != null) {
            return name;
        }
        return "Default";

    }

}
