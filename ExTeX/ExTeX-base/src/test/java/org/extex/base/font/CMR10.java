/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.base.font;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.FontKey;
import org.extex.font.FontKeyFactory;
import org.extex.typesetter.tc.font.Font;

/**
 * This class encapsulates cmr10.tfm for testing purposes.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class CMR10 implements Font, Serializable {

    /**
     * The container for the informations on a glyph.
     */
    private static class Glyph {

        /**
         * The field {@code code} contains the character code.
         */
        private final UnicodeChar code;

        /**
         * The field {@code wd} contains the width.
         */
        private FixedGlue wd;

        /**
         * The field {@code ht} contains the height.
         */
        private FixedGlue ht;

        /**
         * The field {@code dp} contains the depth.
         */
        private FixedGlue dp;

        /**
         * The field {@code ic} contains the italic correction.
         */
        private FixedDimen ic = Dimen.ZERO_PT;

        /**
         * The field {@code ef} contains the ef code.
         */
        private long ef = 0;

        /**
         * The field {@code ligatures} contains the ligatures.
         */
        private final Map<UnicodeChar, UnicodeChar> ligatures =
                new HashMap<UnicodeChar, UnicodeChar>();

        /**
         * The field {@code kernings} contains the kernings.
         */
        private final Map<UnicodeChar, FixedDimen> kernings =
                new HashMap<UnicodeChar, FixedDimen>();

        /**
         * Creates a new object.
         * 
         * @param code the code point
         */
        public Glyph(int code) {

            this.code = UnicodeChar.get(code);
            this.wd = Glue.ZERO;
            this.ht = Glue.ZERO;
            this.ic = Dimen.ZERO;
            this.dp = Glue.ZERO;
        }

        /**
         * Add kerning.
         * 
         * @param c the second character
         * @param kern the kerning
         */
        public void krn(int c, long kern) {

            kernings.put(UnicodeChar.get(c), new Dimen(DESIGNSIZE.getValue()
                    * kern / C_1000000));
        }

        /**
         * Define a ligature.
         * 
         * @param c the second character
         * @param lig the ligature
         */
        public void lig(int c, int lig) {

            ligatures.put(UnicodeChar.get(c), UnicodeChar.get(lig));
        }

    }

    /**
     * 
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2011L;

    /**
     * The field {@code C_1000000} contains the ...
     */
    private static final long C_1000000 = 1000000;

    /**
     * The field {@code FAMILY} contains the ...
     */
    private static final String FAMILY = "CMR";

    /**
     * The field {@code FACE} contains the ...
     */
    // private static final int FACE = 0352;

    /**
     * The field {@code CODINGSCHEME} contains the ...
     */
    // private static final String CODINGSCHEME = "TEX TEXT";

    /**
     * The field {@code DESIGNSIZE} contains the ...
     * 
     * (COMMENT DESIGNSIZE IS IN POINTS) (COMMENT OTHER SIZES ARE MULTIPLES OF
     * DESIGNSIZE)
     */
    private static final Dimen DESIGNSIZE = new Dimen(10 * Dimen.ONE);

    /**
     * The field {@code GLUE_1000003} contains the ...
     */
    private static final Glue GLUE_1000003 = new Glue(DESIGNSIZE.getValue()
            * 1000003 / C_1000000);

    /**
     * The field {@code DIMEN_27779} contains the ...
     */
    private static final Dimen DIMEN_27779 = new Dimen(DESIGNSIZE.getValue()
            * 27779 / C_1000000);

    /**
     * The field {@code GLUE_61508} contains the ...
     */
    private static final Glue GLUE_61508 = new Glue(DESIGNSIZE.getValue()
            * 61508 / C_1000000);

    /**
     * The field {@code GLUE_394445} contains the ...
     */
    private static final Glue GLUE_394445 = new Glue(DESIGNSIZE.getValue()
            * 394445 / C_1000000);

    /**
     * The field {@code GLUE_391668} contains the ...
     */
    private static final Glue GLUE_391668 = new Glue(DESIGNSIZE.getValue()
            * 391668 / C_1000000);

    /**
     * The field {@code GLUE_527781} contains the ...
     */
    private static final Glue GLUE_527781 = new Glue(DESIGNSIZE.getValue()
            * 527781 / C_1000000);

    /**
     * The field {@code GLUE_667859} contains the ...
     */
    private static final Glue GLUE_667859 = new Glue(DESIGNSIZE.getValue()
            * 667859 / C_1000000);

    /**
     * The field {@code GLUE_611113} contains the ...
     */
    private static final Glue GLUE_611113 = new Glue(DESIGNSIZE.getValue()
            * 611113 / C_1000000);

    /**
     * The field {@code DIMEN_250000} contains the ...
     */
    private static final Dimen DIMEN_250000 = new Dimen(DESIGNSIZE.getValue()
            * 250000 / C_1000000);

    /**
     * The field {@code GLUE_1027781} contains the ...
     */
    private static final Glue GLUE_1027781 = new Glue(DESIGNSIZE.getValue()
            * 1027781 / C_1000000);

    /**
     * The field {@code DIMEN_13888} contains the ...
     */
    private static final Dimen DIMEN_13888 = new Dimen(DESIGNSIZE.getValue()
            * 13888 / C_1000000);

    /**
     * The field {@code GLUE_736113} contains the ...
     */
    private static final Glue GLUE_736113 = new Glue(DESIGNSIZE.getValue()
            * 736113 / C_1000000);

    /**
     * The field {@code GLUE_916669} contains the ...
     */
    private static final Glue GLUE_916669 = new Glue(DESIGNSIZE.getValue()
            * 916669 / C_1000000);

    /**
     * The field {@code GLUE_51389} contains the ...
     */
    private static final Glue GLUE_51389 = new Glue(DESIGNSIZE.getValue()
            * 51389 / C_1000000);

    /**
     * The field {@code GLUE_361112} contains the ...
     */
    private static final Glue GLUE_361112 = new Glue(DESIGNSIZE.getValue()
            * 361112 / C_1000000);

    /**
     * The field {@code GLUE_784724} contains the ...
     */
    private static final Glue GLUE_784724 = new Glue(DESIGNSIZE.getValue()
            * 784724 / C_1000000);

    /**
     * The field {@code GLUE_652781} contains the ...
     */
    private static final Glue GLUE_652781 = new Glue(DESIGNSIZE.getValue()
            * 652781 / C_1000000);

    /**
     * The field {@code GLUE_680557} contains the ...
     */
    private static final Glue GLUE_680557 = new Glue(DESIGNSIZE.getValue()
            * 680557 / C_1000000);

    /**
     * The field {@code GLUE_763891} contains the ...
     */
    private static final Glue GLUE_763891 = new Glue(DESIGNSIZE.getValue()
            * 763891 / C_1000000);

    /**
     * The field {@code GLUE_708336} contains the ...
     */
    private static final Glue GLUE_708336 = new Glue(DESIGNSIZE.getValue()
            * 708336 / C_1000000);

    /**
     * The field {@code GLUE__133125} contains the ...
     */
    private static final Glue GLUE__133125 = new Glue(DESIGNSIZE.getValue()
            * -133125 / C_1000000);

    /**
     * The field {@code GLUE_366875} contains the ...
     */
    private static final Glue GLUE_366875 = new Glue(DESIGNSIZE.getValue()
            * 366875 / C_1000000);

    /**
     * The field {@code GLUE_472224} contains the ...
     */
    private static final Glue GLUE_472224 = new Glue(DESIGNSIZE.getValue()
            * 472224 / C_1000000);

    /**
     * The field {@code GLUE_500000} contains the ...
     */
    private static final Glue GLUE_500000 = new Glue(DESIGNSIZE.getValue()
            * 500000 / C_1000000);

    /**
     * The field {@code GLUE_644444} contains the ...
     */
    private static final Glue GLUE_644444 = new Glue(DESIGNSIZE.getValue()
            * 644444 / C_1000000);

    /**
     * The field {@code GLUE_333334} contains the ...
     */
    private static final Glue GLUE_333334 = new Glue(DESIGNSIZE.getValue()
            * 333334 / C_1000000);

    /**
     * The field {@code GLUE_105556} contains the ...
     */
    private static final Glue GLUE_105556 = new Glue(DESIGNSIZE.getValue()
            * 105556 / C_1000000);

    /**
     * The field {@code GLUE_83334} contains the ...
     */
    private static final Glue GLUE_83334 = new Glue(DESIGNSIZE.getValue()
            * 83334 / C_1000000);

    /**
     * The field {@code GLUE_583334} contains the ...
     */
    private static final Glue GLUE_583334 = new Glue(DESIGNSIZE.getValue()
            * 583334 / C_1000000);

    /**
     * The field {@code GLUE_250000} contains the ...
     */
    private static final Glue GLUE_250000 = new Glue(DESIGNSIZE.getValue()
            * 250000 / C_1000000);

    /**
     * The field {@code GLUE_38889} contains the ...
     */
    private static final Glue GLUE_38889 = new Glue(DESIGNSIZE.getValue()
            * 38889 / C_1000000);

    /**
     * The field {@code GLUE_55555} contains the ...
     */
    private static final Glue GLUE_55555 = new Glue(DESIGNSIZE.getValue()
            * 55555 / C_1000000);

    /**
     * The field {@code GLUE_750000} contains the ...
     */
    private static final Glue GLUE_750000 = new Glue(DESIGNSIZE.getValue()
            * 750000 / C_1000000);

    /**
     * The field {@code GLUE_194443} contains the ...
     */
    private static final Glue GLUE_194443 = new Glue(DESIGNSIZE.getValue()
            * 194443 / C_1000000);

    /**
     * The field {@code GLUE_48612} contains the ...
     */
    private static final Glue GLUE_48612 = new Glue(DESIGNSIZE.getValue()
            * 48612 / C_1000000);

    /**
     * The field {@code GLUE_731944} contains the ...
     */
    private static final Glue GLUE_731944 = new Glue(DESIGNSIZE.getValue()
            * 731944 / C_1000000);

    /**
     * The field {@code GLUE_1013891} contains the ...
     */
    private static final Glue GLUE_1013891 = new Glue(DESIGNSIZE.getValue()
            * 1013891 / C_1000000);

    /**
     * The field {@code GLUE_902781} contains the ...
     */
    private static final Glue GLUE_902781 = new Glue(DESIGNSIZE.getValue()
            * 902781 / C_1000000);

    /**
     * The field {@code GLUE_97223} contains the ...
     */
    private static final Glue GLUE_97223 = new Glue(DESIGNSIZE.getValue()
            * 97223 / C_1000000);

    /**
     * The field {@code GLUE_527779} contains the ...
     */
    private static final Glue GLUE_527779 = new Glue(DESIGNSIZE.getValue()
            * 527779 / C_1000000);

    /**
     * The field {@code GLUE_500003} contains the ...
     */
    private static final Glue GLUE_500003 = new Glue(DESIGNSIZE.getValue()
            * 500003 / C_1000000);

    /**
     * The field {@code GLUE_170138} contains the ...
     */
    private static final Glue GLUE_170138 = new Glue(DESIGNSIZE.getValue()
            * 170138 / C_1000000);

    /**
     * The field {@code GLUE_444446} contains the ...
     */
    private static final Glue GLUE_444446 = new Glue(DESIGNSIZE.getValue()
            * 444446 / C_1000000);

    /**
     * The field {@code GLUE_567777} contains the ...
     */
    private static final Glue GLUE_567777 = new Glue(DESIGNSIZE.getValue()
            * 567777 / C_1000000);

    /**
     * The field {@code GLUE_628473} contains the ...
     */
    private static final Glue GLUE_628473 = new Glue(DESIGNSIZE.getValue()
            * 628473 / C_1000000);

    /**
     * The field {@code GLUE_500002} contains the ...
     */
    private static final Glue GLUE_500002 = new Glue(DESIGNSIZE.getValue()
            * 500002 / C_1000000);

    /**
     * The field {@code GLUE_194445} contains the ...
     */
    private static final Glue GLUE_194445 = new Glue(DESIGNSIZE.getValue()
            * 194445 / C_1000000);

    /**
     * The field {@code GLUE_305557} contains the ...
     */
    private static final Glue GLUE_305557 = new Glue(DESIGNSIZE.getValue()
            * 305557 / C_1000000);

    /**
     * The field {@code GLUE_430555} contains the ...
     */
    private static final Glue GLUE_430555 = new Glue(DESIGNSIZE.getValue()
            * 430555 / C_1000000);

    /**
     * The field {@code GLUE_277779} contains the ...
     */
    private static final Glue GLUE_277779 = new Glue(DESIGNSIZE.getValue()
            * 277779 / C_1000000);

    /**
     * The field {@code GLUE_555557} contains the ...
     */
    private static final Glue GLUE_555557 = new Glue(DESIGNSIZE.getValue()
            * 555557 / C_1000000);

    /**
     * The field {@code DIMEN_77779} contains the ...
     */
    private static final Dimen DIMEN_77779 = new Dimen(DESIGNSIZE.getValue()
            * 77779 / C_1000000);

    /**
     * The field {@code GLUE_694445} contains the ...
     */
    private static final Glue GLUE_694445 = new Glue(DESIGNSIZE.getValue()
            * 694445 / C_1000000);

    /**
     * The field {@code GLUE_583336} contains the ...
     */
    private static final Glue GLUE_583336 = new Glue(DESIGNSIZE.getValue()
            * 583336 / C_1000000);

    /**
     * The field {@code GLUE_722224} contains the ...
     */
    private static final Glue GLUE_722224 = new Glue(DESIGNSIZE.getValue()
            * 722224 / C_1000000);

    /**
     * The field {@code GLUE_750002} contains the ...
     */
    private static final Glue GLUE_750002 = new Glue(DESIGNSIZE.getValue()
            * 750002 / C_1000000);

    /**
     * The field {@code GLUE_666669} contains the ...
     */
    private static final Glue GLUE_666669 = new Glue(DESIGNSIZE.getValue()
            * 666669 / C_1000000);

    /**
     * The field {@code GLUE_694446} contains the ...
     */
    private static final Glue GLUE_694446 = new Glue(DESIGNSIZE.getValue()
            * 694446 / C_1000000);

    /**
     * The field {@code GLUE_777781} contains the ...
     */
    private static final Glue GLUE_777781 = new Glue(DESIGNSIZE.getValue()
            * 777781 / C_1000000);

    /**
     * The field {@code GLUE_833336} contains the ...
     */
    private static final Glue GLUE_833336 = new Glue(DESIGNSIZE.getValue()
            * 833336 / C_1000000);

    /**
     * The field {@code GLUE_683332} contains the ...
     */
    private static final Glue GLUE_683332 = new Glue(DESIGNSIZE.getValue()
            * 683332 / C_1000000);

    /**
     * The field {@code GLUE_625002} contains the ...
     */
    private static final Glue GLUE_625002 = new Glue(DESIGNSIZE.getValue()
            * 625002 / C_1000000);

    /**
     * The field {@code CHECKSUM} contains the ...
     */
    private static final int CHECKSUM = 011374260171;

    /**
     * Create a glyph and put it into the glyph table.
     * 
     * @param code the character code
     * @param wd the width
     * @param ht the height
     * 
     * @return the new glyph
     */
    private static Glyph character(int code, Glue wd, Glue ht) {

        Glyph g = new Glyph(code);
        g.wd = wd;
        g.ht = ht;
        GLYPH_TABLE.put(g.code, g);
        return g;
    }

    /**
     * The field {@code fontdimen} contains the ...
     */
    private final FixedDimen[] fontdimen = {Dimen.ZERO, // SLANT
            new Dimen(DESIGNSIZE.getValue() * 333334 / C_1000000), // SPACE
            new Dimen(DESIGNSIZE.getValue() * 166667 / C_1000000), // STRETCH
            new Dimen(DESIGNSIZE.getValue() * 111112 / C_1000000), // SHRINK
            new Dimen(DESIGNSIZE.getValue() * 430555 / C_1000000), // XHEIGHT
            new Dimen(DESIGNSIZE.getValue() * 1000003 / C_1000000), // QUAD
            new Dimen(DESIGNSIZE.getValue() * 111112 / C_1000000) // EXTRASPACE
            };

    /**
     * The field {@code SLANT} contains the font dimen position for the slant
     * parameter.
     */
    protected static final int SLANT = 0;

    /**
     * The field {@code SPACE} contains the font dimen position for the space
     * parameter.
     */
    protected static final int SPACE = 1;

    /**
     * The field {@code STRETCH} contains the font dimen position for the
     * stretch parameter.
     */
    protected static final int STRETCH = 2;

    /**
     * The field {@code SHRINK} contains the font dimen position for the shrink
     * parameter.
     */
    protected static final int SHRINK = 3;

    /**
     * The field {@code XHEIGHT} contains the font dimen position for the
     * xheight parameter.
     */
    protected static final int XHEIGHT = 4;

    /**
     * The field {@code QUAD} contains the font dimen position for the quad
     * parameter.
     */
    protected static final int QUAD = 5;

    /**
     * The field {@code EXTRASPACE} contains the font dimen position for the
     * extraspace parameter.
     */
    protected static final int EXTRASPACE = 6;

    /**
     * The field {@code GLYPH_TABLE} contains the ...
     */
    private static final Map<UnicodeChar, Glyph> GLYPH_TABLE =
            new HashMap<UnicodeChar, Glyph>();

    static {
        Glyph glyph;
        character(00, GLUE_625002, GLUE_683332);
        character(01, GLUE_833336, GLUE_683332);
        character(02, GLUE_777781, GLUE_683332);
        character(03, GLUE_694446, GLUE_683332);
        character(04, GLUE_666669, GLUE_683332);
        character(05, GLUE_750002, GLUE_683332);
        character(06, GLUE_722224, GLUE_683332);
        character(07, GLUE_777781, GLUE_683332);
        character(010, GLUE_722224, GLUE_683332);
        character(011, GLUE_777781, GLUE_683332);
        character(012, GLUE_722224, GLUE_683332);
        glyph = character(013, GLUE_583336, GLUE_694445);
        glyph.ic = DIMEN_77779;
        glyph.lig('i', 016);
        glyph.lig('l', 017);
        glyph.krn(047, 77779);
        glyph.krn(077, 77779);
        glyph.krn(041, 77779);
        glyph.krn(051, 77779);
        glyph.krn(0135, 77779);
        character(014, GLUE_555557, GLUE_694445);
        character(015, GLUE_555557, GLUE_694445);
        character(016, GLUE_833336, GLUE_694445);
        character(017, GLUE_833336, GLUE_694445);
        character(020, GLUE_277779, GLUE_430555);
        glyph = character(021, GLUE_305557, GLUE_430555);
        glyph.dp = GLUE_194445;
        character(022, GLUE_500002, GLUE_694445);
        character(023, GLUE_500002, GLUE_694445);
        character(024, GLUE_500002, GLUE_628473);
        character(025, GLUE_500002, GLUE_694445);
        character(026, GLUE_500002, GLUE_567777);
        character(027, GLUE_750002, GLUE_694445);
        character(030, GLUE_444446, GLUE_170138);
        character(031, GLUE_500003, GLUE_694445);
        character(032, GLUE_722224, GLUE_430555);
        character(033, GLUE_777781, GLUE_430555);
        glyph = character(034, GLUE_500002, GLUE_527779);
        glyph.dp = GLUE_97223;
        character(035, GLUE_902781, GLUE_683332);
        character(036, GLUE_1013891, GLUE_683332);
        glyph = character(037, GLUE_777781, GLUE_731944);
        glyph.dp = GLUE_48612;
        glyph = character(040, GLUE_277779, GLUE_430555);
        glyph.krn('l', -277779);
        glyph.krn('L', -319446);
        glyph = character(041, GLUE_277779, GLUE_694445);
        glyph.lig(0140, 074);
        glyph = character(042, GLUE_500002, GLUE_694445);
        glyph = character(043, GLUE_833336, GLUE_694445);
        glyph.dp = GLUE_194443;
        glyph = character(044, GLUE_500002, GLUE_750000);
        glyph.dp = GLUE_55555;
        glyph = character(045, GLUE_833336, GLUE_750000);
        glyph.dp = GLUE_55555;
        glyph = character(046, GLUE_777781, GLUE_694445);
        glyph = character(047, GLUE_277779, GLUE_694445);
        glyph.lig(047, 042);
        glyph.krn(077, 111112);
        glyph.krn(041, 111112);
        glyph = character(050, GLUE_38889, GLUE_750000);
        glyph.dp = GLUE_250000;
        glyph = character(051, GLUE_38889, GLUE_750000);
        glyph.dp = GLUE_250000;
        glyph = character(052, GLUE_500002, GLUE_750000);
        glyph = character(053, GLUE_777781, GLUE_583334);
        glyph.dp = GLUE_83334;
        glyph = character(054, GLUE_277779, GLUE_105556);
        glyph.dp = GLUE_194445;
        glyph = character(055, GLUE_333334, GLUE_430555);
        glyph.lig(055, 0173);
        glyph = character(056, GLUE_277779, GLUE_105556);
        glyph = character(057, GLUE_500002, GLUE_750000);
        glyph.dp = GLUE_250000;
        character('0', GLUE_500002, GLUE_644444);
        character('1', GLUE_500002, GLUE_644444);
        character('2', GLUE_500002, GLUE_644444);
        character('3', GLUE_500002, GLUE_644444);
        character('4', GLUE_500002, GLUE_644444);
        character('5', GLUE_500002, GLUE_644444);
        character('6', GLUE_500002, GLUE_644444);
        character('7', GLUE_500002, GLUE_644444);
        character('8', GLUE_500002, GLUE_644444);
        character('9', GLUE_500002, GLUE_644444);
        character(072, GLUE_277779, GLUE_430555);
        glyph = character(073, GLUE_277779, GLUE_430555);
        glyph.dp = GLUE_194445;
        glyph = character(074, GLUE_277779, GLUE_500000);
        glyph.dp = GLUE_194445;
        glyph = character(075, GLUE_777781, GLUE_366875);
        glyph.dp = GLUE__133125;
        glyph = character(076, GLUE_472224, GLUE_500000);
        glyph.dp = GLUE_194445;
        glyph = character(077, GLUE_472224, GLUE_694445);
        glyph.lig(0140, 076);
        glyph = character(0100, GLUE_777781, GLUE_694445);
        glyph = character('A', GLUE_750002, GLUE_683332);
        glyph.krn('t', -27779);
        glyph.krn('C', -27779);
        glyph.krn('O', -27779);
        glyph.krn('G', -27779);
        glyph.krn('U', -27779);
        glyph.krn('Q', -27779);
        glyph.krn('T', -83334);
        glyph.krn('Y', -83334);
        glyph.krn('V', -111112);
        glyph.krn('W', -111112);
        character('B', GLUE_708336, GLUE_683332);
        character('C', GLUE_722224, GLUE_683332);
        glyph = character('D', GLUE_763891, GLUE_683332);
        glyph.krn('X', -27779);
        glyph.krn('W', -27779);
        glyph.krn('A', -27779);
        glyph.krn('V', -27779);
        glyph.krn('Y', -27779);
        character('E', GLUE_680557, GLUE_683332);
        glyph = character('F', GLUE_652781, GLUE_683332);
        glyph.krn('o', -83334);
        glyph.krn('e', -83334);
        glyph.krn('u', -83334);
        glyph.krn('r', -83334);
        glyph.krn('a', -83334);
        glyph.krn('A', -111112);
        glyph.krn('O', -27779);
        glyph.krn('C', -27779);
        glyph.krn('G', -27779);
        glyph.krn('Q', -27779);
        character('G', GLUE_784724, GLUE_683332);
        character('H', GLUE_750002, GLUE_683332);
        glyph = character('I', GLUE_361112, GLUE_683332);
        glyph.krn('I', 27779);
        character('J', GLUE_51389, GLUE_683332);
        glyph = character('K', GLUE_777781, GLUE_683332);
        glyph.krn('O', -27779);
        glyph.krn('C', -27779);
        glyph.krn('G', -27779);
        glyph.krn('Q', -27779);
        glyph = character('L', GLUE_625002, GLUE_683332);
        glyph.krn('T', -83334);
        glyph.krn('Y', -83334);
        glyph.krn('V', -111112);
        glyph.krn('W', -111112);
        character('M', GLUE_916669, GLUE_683332);
        character('N', GLUE_750002, GLUE_683332);
        glyph = character('O', GLUE_777781, GLUE_683332);
        glyph.krn('X', -27779);
        glyph.krn('W', -27779);
        glyph.krn('A', -27779);
        glyph.krn('V', -27779);
        glyph.krn('Y', -27779);
        glyph = character('P', GLUE_680557, GLUE_683332);
        glyph.krn('A', -83334);
        glyph.krn('o', -27779);
        glyph.krn('e', -27779);
        glyph.krn('a', -27779);
        glyph.krn(056, -83334);
        glyph.krn(054, -83334);
        glyph = character('Q', GLUE_777781, GLUE_683332);
        glyph.dp = GLUE_194445;
        glyph = character('R', GLUE_736113, GLUE_683332);
        glyph.krn('t', -27779);
        glyph.krn('C', -27779);
        glyph.krn('O', -27779);
        glyph.krn('G', -27779);
        glyph.krn('U', -27779);
        glyph.krn('Q', -27779);
        glyph.krn('T', -83334);
        glyph.krn('Y', -83334);
        glyph.krn('V', -111112);
        glyph.krn('W', -111112);
        character('S', GLUE_555557, GLUE_683332);
        glyph = character('T', GLUE_722224, GLUE_683332);
        glyph.krn('y', -27779);
        glyph.krn('e', -83334);
        glyph.krn('o', -83334);
        glyph.krn('r', -83334);
        glyph.krn('a', -83334);
        glyph.krn('A', -83334);
        glyph.krn('u', -83334);
        character('U', GLUE_750002, GLUE_683332);
        glyph = character('V', GLUE_750002, GLUE_683332);
        glyph.ic = DIMEN_13888;
        glyph.krn('o', -83334);
        glyph.krn('e', -83334);
        glyph.krn('u', -83334);
        glyph.krn('r', -83334);
        glyph.krn('a', -83334);
        glyph.krn('A', -111112);
        glyph.krn('O', -27779);
        glyph.krn('C', -27779);
        glyph.krn('G', -27779);
        glyph.krn('Q', -27779);
        glyph = character('W', GLUE_1027781, GLUE_683332);
        glyph.ic = DIMEN_13888;
        glyph.krn('o', -83334);
        glyph.krn('e', -83334);
        glyph.krn('u', -83334);
        glyph.krn('r', -83334);
        glyph.krn('a', -83334);
        glyph.krn('A', -111112);
        glyph.krn('O', -27779);
        glyph.krn('C', -27779);
        glyph.krn('G', -27779);
        glyph.krn('Q', -27779);
        glyph = character('X', GLUE_750002, GLUE_683332);
        glyph.krn('O', -27779);
        glyph.krn('C', -27779);
        glyph.krn('G', -27779);
        glyph.krn('Q', -27779);
        glyph = character('Y', GLUE_750002, GLUE_683332);
        glyph.ic = DIMEN_250000;
        glyph.krn('e', -83334);
        glyph.krn('o', -83334);
        glyph.krn('r', -83334);
        glyph.krn('a', -83334);
        glyph.krn('A', -83334);
        glyph.krn('u', -83334);
        character('Z', GLUE_611113, GLUE_683332);
        glyph = character(0133, GLUE_277779, GLUE_750000);
        glyph.dp = GLUE_250000;
        character(0134, GLUE_500002, GLUE_694445);
        glyph = character(0135, GLUE_277779, GLUE_750000);
        glyph.dp = GLUE_250000;
        character(0136, GLUE_500002, GLUE_694445);
        character(0137, GLUE_277779, GLUE_667859);
        glyph = character(0140, GLUE_277779, GLUE_694445);
        glyph.lig(0140, 0134);
        glyph = character('a', GLUE_500002, GLUE_430555);
        glyph.krn('v', -27779);
        glyph.krn('j', 55555);
        glyph.krn('y', -27779);
        glyph.krn('w', -27779);
        glyph = character('b', GLUE_555557, GLUE_694445);
        glyph.krn('e', 27779);
        glyph.krn('o', 27779);
        glyph.krn('x', -27779);
        glyph.krn('d', 27779);
        glyph.krn('c', 27779);
        glyph.krn('q', 27779);
        glyph.krn('v', -27779);
        glyph.krn('j', 55555);
        glyph.krn('y', -27779);
        glyph.krn('w', -27779);
        glyph = character('c', GLUE_444446, GLUE_430555);
        glyph.krn('h', -27779);
        glyph.krn('k', -27779);
        character('d', GLUE_555557, GLUE_694445);
        character('e', GLUE_444446, GLUE_430555);
        glyph = character('f', GLUE_305557, GLUE_694445);
        glyph.ic = DIMEN_77779;
        glyph.lig('i', 014);
        glyph.lig('f', 013);
        glyph.lig('l', 015);
        glyph.krn(047, 77779);
        glyph.krn(077, 77779);
        glyph.krn(041, 77779);
        glyph.krn(051, 77779);
        glyph.krn(0135, 77779);
        glyph = character('g', GLUE_500002, GLUE_430555);
        glyph.dp = GLUE_194445;
        glyph.ic = DIMEN_13888;
        glyph.krn('j', 27779);
        glyph = character('h', GLUE_555557, GLUE_694445);
        glyph.krn('t', -27779);
        glyph.krn('u', -27779);
        glyph.krn('b', -27779);
        glyph.krn('y', -27779);
        glyph.krn('v', -27779);
        glyph.krn('w', -27779);
        character('i', GLUE_277779, GLUE_667859);
        glyph = character('j', GLUE_305557, GLUE_667859);
        glyph.dp = GLUE_194445;
        glyph = character('k', GLUE_527781, GLUE_694445);
        glyph.krn('a', -55555);
        glyph.krn('e', -27779);
        glyph.krn('a', -27779);
        glyph.krn('o', -27779);
        glyph.krn('c', -27779);
        glyph = character('l', GLUE_277779, GLUE_694445);
        glyph = character('m', GLUE_833336, GLUE_430555);
        glyph.krn('t', -27779);
        glyph.krn('u', -27779);
        glyph.krn('b', -27779);
        glyph.krn('y', -27779);
        glyph.krn('v', -27779);
        glyph.krn('w', -27779);
        glyph = character('n', GLUE_555557, GLUE_430555);
        glyph.krn('t', -27779);
        glyph.krn('u', -27779);
        glyph.krn('b', -27779);
        glyph.krn('y', -27779);
        glyph.krn('v', -27779);
        glyph.krn('w', -27779);
        glyph = character('o', GLUE_500002, GLUE_430555);
        glyph.krn('e', 27779);
        glyph.krn('o', 27779);
        glyph.krn('x', -27779);
        glyph.krn('d', 27779);
        glyph.krn('c', 27779);
        glyph.krn('q', 27779);
        glyph.krn('v', -27779);
        glyph.krn('j', 55555);
        glyph.krn('y', -27779);
        glyph.krn('w', -27779);
        glyph = character('p', GLUE_555557, GLUE_430555);
        glyph.dp = GLUE_194445;
        glyph.krn('e', 27779);
        glyph.krn('o', 27779);
        glyph.krn('x', -27779);
        glyph.krn('d', 27779);
        glyph.krn('c', 27779);
        glyph.krn('q', 27779);
        glyph.krn('v', -27779);
        glyph.krn('j', 55555);
        glyph.krn('y', -27779);
        glyph.krn('w', -27779);
        glyph = character('q', GLUE_527779, GLUE_430555);
        glyph.dp = GLUE_194445;
        character('r', GLUE_391668, GLUE_430555);
        character('s', GLUE_394445, GLUE_430555);
        glyph = character('t', GLUE_38889, GLUE_61508);
        glyph.krn('y', -27779);
        glyph.krn('w', -27779);
        glyph = character('u', GLUE_555557, GLUE_430555);
        glyph.krn('w', -27779);
        glyph = character('v', GLUE_527781, GLUE_430555);
        glyph.ic = DIMEN_13888;
        glyph.krn('a', -55555);
        glyph.krn('e', -27779);
        glyph.krn('a', -27779);
        glyph.krn('o', -27779);
        glyph.krn('c', -27779);
        glyph = character('w', GLUE_722224, GLUE_430555);
        glyph.ic = DIMEN_13888;
        glyph.krn('e', -27779);
        glyph.krn('a', -27779);
        glyph.krn('o', -27779);
        glyph.krn('c', -27779);
        character('x', GLUE_527781, GLUE_430555);
        glyph = character('y', GLUE_527781, GLUE_430555);
        glyph.dp = GLUE_194445;
        glyph.ic = DIMEN_13888;
        glyph.krn('o', -27779);
        glyph.krn('e', -27779);
        glyph.krn('a', -27779);
        glyph.krn(056, -83334);
        glyph.krn(054, -83334);
        character('z', GLUE_444446, GLUE_430555);
        glyph = character(0173, GLUE_500002, GLUE_430555);
        glyph.ic = DIMEN_27779;
        glyph.lig(055, 0174);
        glyph = character(0174, GLUE_1000003, GLUE_430555);
        glyph.ic = DIMEN_27779;
        character(0175, GLUE_500002, GLUE_694445);
        character(0176, GLUE_500002, GLUE_667859);
        character(0177, GLUE_500002, GLUE_667859);
    }

    /**
     * The field {@code hyphenchar} contains the hyphen character.
     */
    private UnicodeChar hyphenchar = UnicodeChar.get('-');

    /**
     * The field {@code skewchar} contains the skew character.
     */
    private UnicodeChar skewchar = null;

    /**
     * The field {@code fontKey} contains the font key.
     */
    private final FontKey fontKey = new FontKeyFactory().newInstance( "cmr10");

    /**
     * The field {@code space} contains the space specification.
     */
    private final Glue space = new Glue( fontdimen[SPACE], fontdimen[STRETCH],
                                         fontdimen[SHRINK]);

@Override
    public FontKey getActualFontKey() {

        return fontKey;
    }

@Override
    public FixedDimen getActualSize() {

        return DESIGNSIZE;
    }

@Override
    public int getCheckSum() {

        return CHECKSUM;
    }

@Override
    public FixedGlue getDepth(UnicodeChar uc) {

        Glyph glyph = GLYPH_TABLE.get(uc);
        return glyph == null ? Glue.ZERO : glyph.dp;
    }

@Override
    public FixedDimen getDesignSize() {

        return DESIGNSIZE;
    }

@Override
    public long getEfCode(UnicodeChar uc) {

        Glyph glyph = GLYPH_TABLE.get(uc);
        return glyph == null ? 0 : glyph.ef;
    }

@Override
    public FixedDimen getEm() {

        return fontdimen[QUAD];
    }

@Override
    public FixedDimen getEx() {

        return fontdimen[XHEIGHT];
    }

@Override
    public FixedDimen getFontDimen(String name) {

        throw new RuntimeException("unimplemented");
        // return FONTDIMEN.get(name);
    }

    @Override
    public FontKey getFontKey() {

        return fontKey;
    }

@Override
    public String getFontName() {

        return FAMILY + "10";
    }

@Override
    public FixedGlue getHeight(UnicodeChar uc) {

        Glyph glyph = GLYPH_TABLE.get(uc);
        return glyph == null ? Glue.ZERO : glyph.ht;
    }

@Override
    public UnicodeChar getHyphenChar() {

        return hyphenchar;
    }

@Override
    public FixedDimen getItalicCorrection(UnicodeChar uc) {

        Glyph glyph = GLYPH_TABLE.get(uc);
        return glyph == null ? Dimen.ZERO : glyph.ic;
    }

    /**
*      org.extex.core.UnicodeChar)
     */
    @Override
    public FixedDimen getKerning(UnicodeChar uc, UnicodeChar uc2) {

        Glyph glyph = GLYPH_TABLE.get(uc);
        return glyph == null ? null : glyph.kernings.get(uc2);
    }

    /**
*      org.extex.core.UnicodeChar)
     */
    @Override
    public UnicodeChar getLigature(UnicodeChar uc, UnicodeChar uc2) {

        Glyph glyph = GLYPH_TABLE.get(uc);
        return glyph == null ? null : glyph.ligatures.get(uc2);
    }

@Override
    public FixedCount getScaleFactor() {

        return Count.THOUSAND;
    }

@Override
    public UnicodeChar getSkewChar() {

        return skewchar;
    }

@Override
    public FixedGlue getSpace() {

        return space;
    }

@Override
    public FixedGlue getWidth(UnicodeChar uc) {

        Glyph glyph = GLYPH_TABLE.get(uc);
        return glyph == null ? Glue.ZERO : glyph.wd;
    }

@Override
    public boolean hasGlyph(UnicodeChar uc) {

        return GLYPH_TABLE.get(uc) != null;
    }

    /**
*      long)
     */
    @Override
    public void setEfCode(UnicodeChar uc, long code) {

        Glyph glyph = GLYPH_TABLE.get(uc);
        glyph.ef = code;
    }

    /**
*      org.extex.core.dimen.Dimen)
     */
    @Override
    public void setFontDimen(String name, Dimen value) {

        throw new RuntimeException("unimplemented");
    }

@Override
    public void setHyphenChar(UnicodeChar uc) {

        this.hyphenchar = uc;
    }

@Override
    public void setSkewChar(UnicodeChar uc) {

        this.skewchar = uc;
    }

}
