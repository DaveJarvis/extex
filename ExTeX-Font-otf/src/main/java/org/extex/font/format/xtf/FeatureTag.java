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
 * Feature Tags.
 * 
 * <p>
 * Registered features.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FeatureTag extends Tag {

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
    public FeatureTag(RandomAccessR rar) throws IOException {

        super(rar);

        if (map == null) {
            map = new HashMap<String, String>(150);
            map.put("aalt", "Access All Alternates");
            map.put("abvf", "Above-base Forms");
            map.put("abvm", "Above-base Mark Positioning");
            map.put("abvs", "Above-base Substitutions");
            map.put("afrc", "Alternative Fractions");
            map.put("akhn", "Akhands");
            map.put("blwf", "Below-base Forms");
            map.put("blwm", "Below-base Mark Positioning");
            map.put("blws", "Below-base Substitutions");
            map.put("calt", "Contextual Alternates");
            map.put("case", "Case-Sensitive Forms");
            map.put("ccmp", "Glyph Composition / Decomposition");
            map.put("clig", "Contextual Ligatures");
            map.put("cpsp", "Capital Spacing");
            map.put("cswh", "Contextual Swash");
            map.put("curs", "Cursive Positioning");
            map.put("c2sc", "Small Capitals From Capitals");
            map.put("c2pc", "Petite Capitals From Capitals");
            map.put("dist", "Distances");
            map.put("dlig", "Discretionary Ligatures");
            map.put("dnom", "Denominators");
            map.put("expt", "Expert Forms");
            map.put("falt", "Final Glyph on Line Alternates");
            map.put("fin2", "Terminal Forms #2");
            map.put("fin3", "Terminal Forms #3");
            map.put("fina", "Terminal Forms");
            map.put("frac", "Fractions");
            map.put("fwid", "Full Widths");
            map.put("half", "Half Forms");
            map.put("haln", "Halant Forms");
            map.put("halt", "Alternate Half Widths");
            map.put("hist", "Historical Forms");
            map.put("hkna", "Horizontal Kana Alternates");
            map.put("hlig", "Historical Ligatures");
            map.put("hngl", "Hangul");
            map.put("hojo", "Hojo Kanji Forms");
            map.put("hwid", "Half Widths");
            map.put("init", "Initial Forms");
            map.put("isol", "Isolated Forms");
            map.put("ital", "Italics");
            map.put("jalt", "Justification Alternates");
            map.put("jp04", "JIS2004 Forms");
            map.put("jp78", "JIS78 Forms");
            map.put("jp83", "JIS83 Forms");
            map.put("jp90", "JIS90 Forms");
            map.put("kern", "Kerning");
            map.put("lfbd", "Left Bounds");
            map.put("liga", "Standard Ligatures");
            map.put("ljmo", "Leading Jamo Forms");
            map.put("lnum", "Lining Figures");
            map.put("locl", "Localized Forms");
            map.put("mark", "Mark Positioning");
            map.put("med2", "Medial Forms #2");
            map.put("medi", "Medial Forms");
            map.put("mgrk", "Mathematical Greek");
            map.put("mkmk", "Mark to Mark Positioning");
            map.put("mset", "Mark Positioning via Substitution");
            map.put("nalt", "Alternate Annotation Forms");
            map.put("nlck", "NLC Kanji Forms");
            map.put("nukt", "Nukta Forms");
            map.put("numr", "Numerators");
            map.put("onum", "Oldstyle Figures");
            map.put("opbd", "Optical Bounds");
            map.put("ordn", "Ordinals");
            map.put("ornm", "Ornaments");
            map.put("palt", "Proportional Alternate Widths");
            map.put("pcap", "Petite Capitals");
            map.put("pnum", "Proportional Figures");
            map.put("pres", "Pre-Base Substitutions");
            map.put("pstf", "Post-base Forms");
            map.put("psts", "Post-base Substitutions");
            map.put("pwid", "Proportional Widths");
            map.put("qwid", "Quarter Widths");
            map.put("rand", "Randomize");
            map.put("rlig", "Required Ligatures");
            map.put("rphf", "Reph Forms");
            map.put("rtbd", "Right Bounds");
            map.put("rtla", "Right-to-left Alternates");
            map.put("ruby", "Ruby Notation Forms");
            map.put("salt", "Stylistic Alternates");
            map.put("sinf", "Scientific Inferiors");
            map.put("size", "Optical size");
            map.put("smcp", "Small Capitals");
            map.put("smpl", "Simplified Forms");
            map.put("ss01", "Stylistic Set 1");
            map.put("ss02", "Stylistic Set 2");
            map.put("ss03", "Stylistic Set 3");
            map.put("ss04", "Stylistic Set 4");
            map.put("ss05", "Stylistic Set 5");
            map.put("ss06", "Stylistic Set 6");
            map.put("ss07", "Stylistic Set 7");
            map.put("ss08", "Stylistic Set 8");
            map.put("ss09", "Stylistic Set 9");
            map.put("ss10", "Stylistic Set 10");
            map.put("ss11", "Stylistic Set 11");
            map.put("ss12", "Stylistic Set 12");
            map.put("ss13", "Stylistic Set 13");
            map.put("ss14", "Stylistic Set 14");
            map.put("ss15", "Stylistic Set 15");
            map.put("ss16", "Stylistic Set 16");
            map.put("ss17", "Stylistic Set 17");
            map.put("ss18", "Stylistic Set 18");
            map.put("ss19", "Stylistic Set 19");
            map.put("ss20", "Stylistic Set 20");
            map.put("subs", "Subscript");
            map.put("sups", "Superscript");
            map.put("swsh", "Swash");
            map.put("titl", "Titling");
            map.put("tjmo", "Trailing Jamo Forms");
            map.put("tnam", "Traditional Name Forms");
            map.put("tnum", "Tabular Figures");
            map.put("trad", "Traditional Forms");
            map.put("twid", "Third Widths");
            map.put("unic", "Unicase");
            map.put("valt", "Alternate Vertical Metrics");
            map.put("vatu", "Vattu Variants");
            map.put("vert", "Vertical Writing");
            map.put("vhal", "Alternate Vertical Half Metrics");
            map.put("vjmo", "Vowel Jamo Forms");
            map.put("vkna", "Vertical Kana Alternates");
            map.put("vkrn", "Vertical Kerning");
            map.put("vpal", "Proportional Alternate Vertical Metrics");
            map.put("vrt2", "Vertical Alternates and Rotation");
            map.put("zero", "Slashed Zero");

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
