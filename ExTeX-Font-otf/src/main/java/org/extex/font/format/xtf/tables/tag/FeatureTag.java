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
    private static Map<String, FeatureTag> map =
            new HashMap<String, FeatureTag>(150);

    /**
     * The script tag list.
     */
    static {
        getInstance("aalt");// Access All Alternates");
        getInstance("abvf");// Above-base Forms
        getInstance("abvm");// Above-base Mark Positioning
        getInstance("abvs");// Above-base Substitutions
        getInstance("afrc");// Alternative Fractions
        getInstance("akhn");// Akhands
        getInstance("blwf");// Below-base Forms
        getInstance("blwm");// Below-base Mark Positioning
        getInstance("blws");// Below-base Substitutions
        getInstance("calt");// Contextual Alternates
        getInstance("case");// Case-Sensitive Forms
        getInstance("ccmp");// Glyph Composition / Decomposition
        getInstance("clig");// Contextual Ligatures
        getInstance("cpsp");// Capital Spacing
        getInstance("cswh");// Contextual Swash
        getInstance("curs");// Cursive Positioning
        getInstance("c2sc");// Small Capitals From Capitals
        getInstance("c2pc");// Petite Capitals From Capitals
        getInstance("dist");// Distances
        getInstance("dlig");// Discretionary Ligatures
        getInstance("dnom");// Denominators
        getInstance("expt");// Expert Forms
        getInstance("falt");// Final Glyph on Line Alternates
        getInstance("fin2");// Terminal Forms #2
        getInstance("fin3");// Terminal Forms #3
        getInstance("fina");// Terminal Forms
        getInstance("frac");// Fractions
        getInstance("fwid");// Full Widths
        getInstance("half");// Half Forms
        getInstance("haln");// Halant Forms
        getInstance("halt");// Alternate Half Widths
        getInstance("hist");// Historical Forms
        getInstance("hkna");// Horizontal Kana Alternates
        getInstance("hlig");// Historical Ligatures
        getInstance("hngl");// Hangul
        getInstance("hojo");// Hojo Kanji Forms
        getInstance("hwid");// Half Widths
        getInstance("init");// Initial Forms
        getInstance("isol");// Isolated Forms
        getInstance("ital");// Italics
        getInstance("jalt");// Justification Alternates
        getInstance("jp04");// JIS2004 Forms
        getInstance("jp78");// JIS78 Forms
        getInstance("jp83");// JIS83 Forms
        getInstance("jp90");// JIS90 Forms
        getInstance("kern");// Kerning
        getInstance("lfbd");// Left Bounds
        getInstance("liga");// Standard Ligatures
        getInstance("ljmo");// Leading Jamo Forms
        getInstance("lnum");// Lining Figures
        getInstance("locl");// Localized Forms
        getInstance("mark");// Mark Positioning
        getInstance("med2");// Medial Forms #2
        getInstance("medi");// Medial Forms
        getInstance("mgrk");// Mathematical Greek
        getInstance("mkmk");// Mark to Mark Positioning
        getInstance("mset");// Mark Positioning via Substitution
        getInstance("nalt");// Alternate Annotation Forms
        getInstance("nlck");// NLC Kanji Forms
        getInstance("nukt");// Nukta Forms
        getInstance("numr");// Numerators
        getInstance("onum");// Oldstyle Figures
        getInstance("opbd");// Optical Bounds
        getInstance("ordn");// Ordinals
        getInstance("ornm");// Ornaments
        getInstance("palt");// Proportional Alternate Widths
        getInstance("pcap");// Petite Capitals
        getInstance("pnum");// Proportional Figures
        getInstance("pres");// Pre-Base Substitutions
        getInstance("pstf");// Post-base Forms
        getInstance("psts");// Post-base Substitutions
        getInstance("pwid");// Proportional Widths
        getInstance("qwid");// Quarter Widths
        getInstance("rand");// Randomize
        getInstance("rlig");// Required Ligatures
        getInstance("rphf");// Reph Forms
        getInstance("rtbd");// Right Bounds
        getInstance("rtla");// Right-to-left Alternates
        getInstance("ruby");// Ruby Notation Forms
        getInstance("salt");// Stylistic Alternates
        getInstance("sinf");// Scientific Inferiors
        getInstance("size");// Optical size
        getInstance("smcp");// Small Capitals
        getInstance("smpl");// Simplified Forms");
        getInstance("ss01");// Stylistic Set 1");
        getInstance("ss02");// Stylistic Set 2");
        getInstance("ss03");// Stylistic Set 3
        getInstance("ss04");// Stylistic Set 4
        getInstance("ss05");// Stylistic Set 5
        getInstance("ss06");// Stylistic Set 6
        getInstance("ss07");// Stylistic Set 7
        getInstance("ss08");// Stylistic Set 8
        getInstance("ss09");// Stylistic Set 9
        getInstance("ss10");// Stylistic Set 10
        getInstance("ss11");// Stylistic Set 11
        getInstance("ss12");// Stylistic Set 12
        getInstance("ss13");// Stylistic Set 13
        getInstance("ss14");// Stylistic Set 14
        getInstance("ss15");// Stylistic Set 15
        getInstance("ss16");// Stylistic Set 16
        getInstance("ss17");// Stylistic Set 17
        getInstance("ss18");// Stylistic Set 18
        getInstance("ss19");// Stylistic Set 19
        getInstance("ss20");// Stylistic Set 20
        getInstance("subs");// Subscript
        getInstance("sups");// Superscript
        getInstance("swsh");// Swash
        getInstance("titl");// Titling
        getInstance("tjmo");// Trailing Jamo Forms
        getInstance("tnam");// Traditional Name Forms
        getInstance("tnum");// Tabular Figures
        getInstance("trad");// Traditional Forms
        getInstance("twid");// Third Widths
        getInstance("unic");// Unicase
        getInstance("valt");// Alternate Vertical Metrics
        getInstance("vatu");// Vattu Variants
        getInstance("vert");// Vertical Writing
        getInstance("vhal");// Alternate Vertical Half Metrics
        getInstance("vjmo");// Vowel Jamo Forms
        getInstance("vkna");// Vertical Kana Alternates
        getInstance("vkrn");// Vertical Kerning
        getInstance("vpal");// Proportional Alternate Vertical Metrics
        getInstance("vrt2");// Vertical Alternates and Rotation
        getInstance("zero");// Slashed Zero

    };

    /**
     * Get a new feature tag.
     * 
     * @param name The name of the feature tag.
     * @return Returns the new feature tag.
     */
    public static FeatureTag getInstance(String name) {

        String xname = format(name);
        FeatureTag ft = map.get(xname);
        if (ft == null) {
            ft = new FeatureTag(xname);
            map.put(xname, ft);
        }
        return ft;
    }

    /**
     * Check, if the name is in the feature tag list.
     * 
     * @param name The name of the feature tag.
     * @return Returns <code>true</code>, if found, otherwise
     *         <code>false</code>.
     */
    public static boolean isInList(String name) {

        return map.containsKey(format(name));
    }

    /**
     * Creates a new object.
     * 
     * @param name The name of the tag.
     */
    private FeatureTag(String name) {

        super(name);
    }

}
