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

import java.util.HashMap;
import java.util.Map;

/**
 * Script tags identify the scripts represented in an OpenType Layout font.
 *
 * <p>
 * When the ScriptList table is searched for a script, and no entry is found,
 * and there is an entry for the 'DFLT' script, then this entry must be used.
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public final class ScriptTag extends Tag {

    private static final Map<String, ScriptTag> scriptTags =
        new HashMap<>( 30 );

    private final static String TAG_DEFAULT = "DFLT";

    static {
        getInstance("arab"); // Arabic
        getInstance("armn"); // Armenian
        getInstance("beng"); // Bengali
        getInstance("bopo"); // Bopomofo
        getInstance("brai"); // Braille
        getInstance("byzm"); // Byzantine Music
        getInstance("cans"); // Canadian Syllabics
        getInstance("cher"); // Cherokee
        getInstance("hani"); // CJK Ideographic
        getInstance("cyrl"); // Cyrillic
        getInstance(TAG_DEFAULT);
        getInstance("deva"); // Devanagari
        getInstance("ethi"); // Ethiopic
        getInstance("geor"); // Georgian
        getInstance("grek"); // Greek
        getInstance("gujr"); // Gujarati
        getInstance("guru"); // Gurmukhi
        getInstance("jamo"); // Hangul Jamo
        getInstance("hang"); // Hangul
        getInstance("hebr"); // Hebrew
        getInstance("kana"); // Hiragana
        getInstance("knda"); // Kannada
        getInstance("kana"); // Katakana
        getInstance("khmr"); // Khmer
        getInstance("lao"); // Lao
        getInstance("latn"); // Latin
        getInstance("mlym"); // Malayalam
        getInstance("mong"); // Mongolian
        getInstance("mymr"); // Myanmar
        getInstance("ogam"); // Ogham
        getInstance("orya"); // Oriya
    }

    /**
     * Return the name of the default script tag.
     *
     * @return Return the name of the default script tag.
     */
    public static ScriptTag getDefault() {
      return scriptTags.get( TAG_DEFAULT );
    }

    /**
     * Get a new script tag.
     *
     * @param name The name of the script tag.
     * @return Returns the new script tag.
     */
    public static ScriptTag getInstance(String name) {
      final String xname = format( name );
      ScriptTag st = scriptTags.get( xname );
      if( st == null ) {
        st = new ScriptTag( xname );
        scriptTags.put( xname, st );
      }
      return st;
    }

    /**
     * Check, if the name is in the script tag list.
     *
     * @param name The name of the script tag.
     * @return Returns {@code true}, if found, otherwise
     *         {@code false}.
     */
    public static boolean containsTag( String name ) {
      return scriptTags.containsKey( format( name ) );
    }

    /**
     * Creates a new object.
     *
     * @param name The name of the tag.
     */
    private ScriptTag(String name) {
        super(name);
    }
}
