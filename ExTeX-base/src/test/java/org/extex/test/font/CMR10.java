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

package org.extex.test.font;

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
 * @version $Revision: 4784 $
 */
public class CMR10 implements Font, Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * Private implementation of the glyph interface.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision: 4784 $
     */
    public static class MyGlyph implements Serializable {

        /**
         * The constant <tt>serialVersionUID</tt> contains the id for
         * serialization.
         */
        protected static final long serialVersionUID = 2006L;

        /**
         * Creates a new object.
         * 
         * @param c the character
         * @param w the width
         * @param h the height
         */
        public MyGlyph(UnicodeChar c, Dimen w, Dimen h) {

            // super(h, Dimen.ZERO_PT, w, Dimen.ZERO_PT);
            // setName(c.toString());
        }

        /**
         * Creates a new object.
         * 
         * @param c the character
         * @param w the width
         * @param h the height
         * @param d the depth
         * @param ic the italic correction
         */
        public MyGlyph(UnicodeChar c, Dimen w, Dimen h, Dimen d, Dimen ic) {

            // super(h, d, w, ic);
            // setName(c.toString());
        }

        // /**
        // * Add a ligature entry.
        // *
        // * @param c the next letter
        // * @param d the ligature letter
        // */
        // public void addLig(char c, char d) {
        //
        // Ligature lig = new Ligature();
        // lig.setLetter(Character.toString(c));
        // lig.setLetterid(Integer.toString(c));
        // lig.setLig(Character.toString(d));
        // lig.setLigid(Integer.toString(d));
        // addLigature(lig);
        // }
        //
        // /**
        // * Add a kerning entry.
        // *
        // * @param c the character
        // * @param dimen the kerning
        // */
        // public void addKern(char c, Dimen dimen) {
        //
        // String s = Character.toString(c);
        // addKerning(new Kerning(s, s, dimen));
        // }

    }

    /**
     * The field <tt>ONE_683332</tt> contains the ...
     */
//    private static final long ONE_683332 = Dimen.ONE * 683332;

    /**
     * The field <tt>ONE_722224</tt> contains the ...
     */
    private static final long ONE_722224 = Dimen.ONE * 722224;

    /**
     * The field <tt>ONE_777781</tt> contains the ...
     */
    private static final long ONE_777781 = Dimen.ONE * 777781;

    /**
     * The field <tt>TEN_6</tt> contains the value 10<sup>6</sup>
     */
    private static final int TEN_6 = 1000000;

    /**
     * The field <tt>designSize</tt> contains the design size.
     */
    private Dimen designSize = new Dimen(10 * Dimen.ONE);

    /**
     * The field <tt>fontdimen</tt> contains the ...
     */
    private Dimen[] fontdimen = {new Dimen(0), // (SLANT R 0 / 10)
            new Dimen(Dimen.ONE * 33333 / 1000004), // (SPACE R 33333 / 1000004)
            new Dimen(Dimen.ONE * 16666 / 1000007), // (STRETCH R 16666 /
            // 1000007)
            new Dimen(Dimen.ONE * 11111 / 1000002), // (SHRINK R 11111 /
            // 1000002)
            new Dimen(Dimen.ONE * 43055 / 1000005), // (XHEIGHT R 43055 /
            // 1000005)
            new Dimen(Dimen.ONE * 1000003 / 1000000), // (QUAD R 1.000003)
            new Dimen(Dimen.ONE * 11111 / 1000002) // (EXTRASPACE R 11111 /
            // 1000002)
            };

    /**
     * The field <tt>SLANT</tt> contains the font dimen position for the slant
     * parameter.
     */
    protected static final int SLANT = 0;

    /**
     * The field <tt>SPACE</tt> contains the font dimen position for the space
     * parameter.
     */
    protected static final int SPACE = 1;

    /**
     * The field <tt>STRETCH</tt> contains the font dimen position for the
     * stretch parameter.
     */
    protected static final int STRETCH = 2;

    /**
     * The field <tt>SHRINK</tt> contains the font dimen position for the
     * shrink parameter.
     */
    protected static final int SHRINK = 3;

    /**
     * The field <tt>XHEIGHT</tt> contains the font dimen position for the
     * xheight parameter.
     */
    protected static final int XHEIGHT = 4;

    /**
     * The field <tt>QUAD</tt> contains the font dimen position for the quad
     * parameter.
     */
    protected static final int QUAD = 5;

    /**
     * The field <tt>EXTRASPACE</tt> contains the font dimen position for the
     * extraspace parameter.
     */
    protected static final int EXTRASPACE = 6;

    /**
     * The field <tt>glyphs</tt> contains the hash map for the glyphs.
     */
    private Map<UnicodeChar, MyGlyph> glyphs =
            new HashMap<UnicodeChar, MyGlyph>();

    /**
     * The field <tt>hypenChar</tt> contains the hyphen character.
     */
    private UnicodeChar hypenChar = UnicodeChar.get('-');

    /**
     * The field <tt>skewChar</tt> contains the skew character.
     */
    private UnicodeChar skewChar = null;

    /**
     * The field <tt>space</tt> contains the space specification.
     */
    private Glue space =
            new Glue(fontdimen[SPACE], fontdimen[STRETCH], fontdimen[SHRINK]);

    /**
     * Creates a new object.
     */
    public CMR10() {

        super();
        // MyGlyph g;
        // // (FAMILY CMR)
        // // (FACE O 352)
        // // (CODINGSCHEME TEX TEXT)
        // // (DESIGNSIZE R 10 / 10)
        // // (COMMENT DESIGNSIZE IS IN POINTS)
        // // (COMMENT OTHER SIZES ARE MULTIPLES OF DESIGNSIZE)
        // // (CHECKSUM O 11374260171)
        // // (FONTDIMEN
        // // (SLANT R 0 / 10)
        // // (SPACE R 33333 / 1000004)
        // // (STRETCH R 16666 / 1000007)
        // // (SHRINK R 11111 / 1000002)
        // // (XHEIGHT R 43055 / 1000005)
        // // (QUAD R 1.000003)
        // // (EXTRASPACE R 11111 / 1000002)
        // // )
        // // (LIGTABLE
        // // (LABEL O 40)
        // // (KRN C l R -27777 / 1000009)
        // // (KRN C L R -31944 / 1000006)
        // // (STOP)
        // // (LABEL C f)
        // // (LIG C i O 14)
        // // (LIG C f O 13)
        // // (LIG C l O 15)
        // // (KRN O 47 R 07777 / 1000009)
        // // (KRN O 77 R 07777 / 1000009)
        // // (KRN O 41 R 07777 / 1000009)
        // // (KRN O 51 R 07777 / 1000009)
        // // (KRN O 135 R 07777 / 1000009)
        // // (STOP)
        // // (LABEL O 13)
        // // (LIG C i O 16)
        // // (LIG C l O 17)
        // // (KRN O 47 R 07777 / 1000009)
        // // (KRN O 77 R 07777 / 1000009)
        // // (KRN O 41 R 07777 / 1000009)
        // // (KRN O 51 R 077779 / 1000000)
        // // (KRN O 135 R 077779 / 1000000)
        // // (STOP)
        // // (LABEL O 140)
        // // (LIG O 140 O 134)
        // // (STOP)
        // // (LABEL O 47)
        // // (LIG O 47 O 42)
        // // (KRN O 77 R 111112 / 1000000)
        // // (KRN O 41 R 111112 / 1000000)
        // // (STOP)
        // // (LABEL O 55)
        // // (LIG O 55 O 173)
        // // (STOP)
        // // (LABEL O 173)
        // // (LIG O 55 O 174)
        // // (STOP)
        // // (LABEL O 41)
        // // (LIG O 140 O 74)
        // // (STOP)
        // // (LABEL O 77)
        // // (LIG O 140 O 76)
        // // (STOP)
        // // (LABEL C k)
        // // (LABEL C v)
        // // (KRN C a R -055555 / 1000000)
        // // (LABEL C w)
        // // (KRN C e R -027779 / 1000000)
        // // (KRN C a R -027779 / 1000000)
        // // (KRN C o R -027779 / 1000000)
        // // (KRN C c R -027779 / 1000000)
        // // (STOP)
        // // (LABEL C P)
        // // (KRN C A R -083334 / 1000000)
        // // (LABEL C y)
        // // (KRN C o R -027779 / 1000000)
        // // (KRN C e R -027779 / 1000000)
        // // (KRN C a R -027779 / 1000000)
        // // (KRN O 56 R -083334 / 1000000)
        // // (KRN O 54 R -083334 / 1000000)
        // // (STOP)
        // // (LABEL C F)
        // // (LABEL C V)
        // // (LABEL C W)
        // // (KRN C o R -083334 / 1000000)
        // // (KRN C e R -083334 / 1000000)
        // // (KRN C u R -083334 / 1000000)
        // // (KRN C r R -083334 / 1000000)
        // // (KRN C a R -083334 / 1000000)
        // // (KRN C A R -111112 / 1000000)
        // // (LABEL C K)
        // // (LABEL C X)
        // // (KRN C O R -027779 / 1000000)
        // // (KRN C C R -027779 / 1000000)
        // // (KRN C G R -027779 / 1000000)
        // // (KRN C Q R -027779 / 1000000)
        // // (STOP)
        // // (LABEL C T)
        // // (KRN C y R -027779 / 1000000)
        // // (LABEL C Y)
        // // (KRN C e R -083334 / 1000000)
        // // (KRN C o R -083334 / 1000000)
        // // (KRN C r R -083334 / 1000000)
        // // (KRN C a R -083334 / 1000000)
        // // (KRN C A R -083334 / 1000000)
        // // (KRN C u R -083334 / 1000000)
        // // (STOP)
        // // (LABEL C D)
        // // (LABEL C O)
        // // (KRN C X R -027779 / 1000000)
        // // (KRN C W R -027779 / 1000000)
        // // (KRN C A R -027779 / 1000000)
        // // (KRN C V R -027779 / 1000000)
        // // (KRN C Y R -027779 / 1000000)
        // // (STOP)
        // // (LABEL C h)
        // // (LABEL C m)
        // // (LABEL C n)
        // // (KRN C t R -027779 / 1000000)
        // // (KRN C u R -027779 / 1000000)
        // // (KRN C b R -027779 / 1000000)
        // // (KRN C y R -027779 / 1000000)
        // // (KRN C v R -027779 / 1000000)
        // // (KRN C w R -027779 / 1000000)
        // // (STOP)
        // // (LABEL C c)
        // // (KRN C h R -027779 / 1000000)
        // // (KRN C k R -027779 / 1000000)
        // // (STOP)
        // // (LABEL C b)
        // // (LABEL C o)
        // // (LABEL C p)
        // // (KRN C e R 027779 / 1000000)
        // // (KRN C o R 027779 / 1000000)
        // // (KRN C x R -027779 / 1000000)
        // // (KRN C d R 027779 / 1000000)
        // // (KRN C c R 027779 / 1000000)
        // // (KRN C q R 027779 / 1000000)
        // // (LABEL C a)
        // // (KRN C v R -027779 / 1000000)
        // // (KRN C j R 055555 / 1000000)
        // // (LABEL C t)
        // // (KRN C y R -027779 / 1000000)
        // // (LABEL C u)
        // // (KRN C w R -027779 / 1000000)
        // // (STOP)
        // // (LABEL C A)
        // // (LABEL C R)
        // // (KRN C t R -027779 / 1000000)
        // // (KRN C C R -027779 / 1000000)
        // // (KRN C O R -027779 / 1000000)
        // // (KRN C G R -027779 / 1000000)
        // // (KRN C U R -027779 / 1000000)
        // // (KRN C Q R -027779 / 1000000)
        // // (LABEL C L)
        // // (KRN C T R -083334 / 1000000)
        // // (KRN C Y R -083334 / 1000000)
        // // (KRN C V R -111112 / 1000000)
        // // (KRN C W R -111112 / 1000000)
        // // (STOP)
        // // (LABEL C g)
        // // (KRN C j R 027779 / 1000000)
        // // (STOP)
        // // (LABEL C I)
        // // (KRN C I R 027779 / 1000000)
        // // (STOP)
        // makeGlyph('\0', new Dimen(Dimen.ONE * 625002 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\001', new Dimen(Dimen.ONE * 833336 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\002', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\003', new Dimen(Dimen.ONE * 694446 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\004', new Dimen(Dimen.ONE * 666669 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\005', new Dimen(Dimen.ONE * 750002 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\006', new Dimen(ONE_722224 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\007', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\010', new Dimen(ONE_722224 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\011', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\012', new Dimen(ONE_722224 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('\013', new Dimen(Dimen.ONE * 583336 / TEN_6), //
        // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6), // CHARHT
        // Dimen.ZERO_PT, new Dimen(Dimen.ONE * 77779 / TEN_6) // CHARIC
        // );
        // // (COMMENT
        // g.addLig('i', '\020'); // (LIG C i O 16)
        // g.addLig('l', '\021'); // (LIG C l O 17)
        // g.addKern((char) 047, new Dimen(Dimen.ONE * 77779 / TEN_6)); // (KRN
        // // O 47
        // // R
        // // 077779
        // // /
        // // 1000000)
        // g.addKern((char) 077, new Dimen(Dimen.ONE * 77779 / TEN_6)); // (KRN
        // // O 77
        // // R
        // // 077779
        // // /
        // // 1000000)
        // g.addKern((char) 041, new Dimen(Dimen.ONE * 77779 / TEN_6)); // (KRN
        // // O 41
        // // R
        // // 077779
        // // /
        // // 1000000)
        // g.addKern((char) 051, new Dimen(Dimen.ONE * 77779 / TEN_6)); // (KRN
        // // O 51
        // // R
        // // 077779
        // // /
        // // 1000000)
        // g.addKern((char) 0135, new Dimen(Dimen.ONE * 77779 / TEN_6)); // (KRN
        // // O 135
        // // R
        // // 077779
        // // /
        // // 1000000)
        // // )
        // makeGlyph('\014', new Dimen(Dimen.ONE * 555557 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\015', new Dimen(Dimen.ONE * 555557 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\016', new Dimen(Dimen.ONE * 833336 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\017', new Dimen(Dimen.ONE * 833336 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\020', new Dimen(Dimen.ONE * 277779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // makeGlyph('\021', new Dimen(Dimen.ONE * 305557 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 194445 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\022', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\023', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\024', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 628473 / TEN_6) // CHARHT
        // );
        // makeGlyph('\025', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\026', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 567777 / TEN_6) // CHARHT
        // );
        // makeGlyph('\027', new Dimen(Dimen.ONE * 750002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\030', new Dimen(Dimen.ONE * 444446 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 170138 / TEN_6) // CHARDP
        // );
        // makeGlyph('\031', new Dimen(Dimen.ONE * 500003 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\032', new Dimen(ONE_722224 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // makeGlyph('\033', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // makeGlyph('\034', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 527779 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 97223 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\035', new Dimen(Dimen.ONE * 902781 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\036', new Dimen(Dimen.ONE * 1013891 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\037', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 731944 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 48612 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // g = makeGlyph('\040', new Dimen(Dimen.ONE * 277779 / TEN_6), //
        // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('l', new Dimen(Dimen.ONE * -277779 / TEN_6)); // (KRN C l R
        // // -277779 /
        // // 1000000)
        // g.addKern('L', new Dimen(Dimen.ONE * -319446 / TEN_6)); // (KRN C L R
        // // -319446 /
        // // 1000000)
        // // )
        // g = makeGlyph('\041', new Dimen(Dimen.ONE * 277779 / TEN_6), //
        // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addLig((char) 0140, (char) 074); // (LIG O 140 O 74)
        // // )
        // makeGlyph('\042', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\043', new Dimen(Dimen.ONE * 833336 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 194443 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\044', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 75 / 100), // CHARHT
        // new Dimen(Dimen.ONE * 55555 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\045', new Dimen(Dimen.ONE * 833336 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 75 / 100), // CHARHT
        // new Dimen(Dimen.ONE * 55555 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\046', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('\047', new Dimen(Dimen.ONE * 277779 / TEN_6), //
        // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addLig((char) 047, (char) 042); // (LIG O 47 O 42)
        // g.addKern((char) 077, new Dimen(Dimen.ONE * 111112 / TEN_6)); // (KRN
        // // O 77
        // // R
        // // 111112
        // // /
        // // 1000000)
        // g.addKern((char) 041, new Dimen(Dimen.ONE * 111112 / TEN_6)); // (KRN
        // // O 41
        // // R
        // // 111112
        // // /
        // // 1000000)
        // // )
        // makeGlyph('\050', new Dimen(Dimen.ONE * 38889 / 100000), // CHARWD
        // new Dimen(Dimen.ONE * 75 / 100), // CHARHT
        // new Dimen(Dimen.ONE * 25 / 100), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\051', new Dimen(Dimen.ONE * 38889 / 100000), // CHARWD
        // new Dimen(Dimen.ONE * 75 / 100), // CHARHT
        // new Dimen(Dimen.ONE * 25 / 100), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\052', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 75 / 100) // CHARHT
        // );
        // makeGlyph('\053', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 583334 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 83334 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\054', new Dimen(Dimen.ONE * 277779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 105556 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 194445 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // g = makeGlyph('\055', new Dimen(Dimen.ONE * 333334 / TEN_6), //
        // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addLig((char) 055, (char) 0173); // (LIG O 55 O 173)
        // // )
        // makeGlyph('\056', new Dimen(Dimen.ONE * 277779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 105556 / TEN_6) // CHARHT
        // );
        // makeGlyph('\057', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 75 / 100), // CHARHT
        // new Dimen(Dimen.ONE * 25 / 100), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('0', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 644444 / TEN_6) // CHARHT
        // );
        // makeGlyph('1', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 644444 / TEN_6) // CHARHT
        // );
        // makeGlyph('2', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 644444 / TEN_6) // CHARHT
        // );
        // makeGlyph('3', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 644444 / TEN_6) // CHARHT
        // );
        // makeGlyph('4', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 644444 / TEN_6) // CHARHT
        // );
        // makeGlyph('5', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 644444 / TEN_6) // CHARHT
        // );
        // makeGlyph('6', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 644444 / TEN_6) // CHARHT
        // );
        // makeGlyph('7', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 644444 / TEN_6) // CHARHT
        // );
        // makeGlyph('8', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 644444 / TEN_6) // CHARHT
        // );
        // makeGlyph('9', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 644444 / TEN_6) // CHARHT
        // );
        // makeGlyph('\072', new Dimen(Dimen.ONE * 277779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // makeGlyph('\073', new Dimen(Dimen.ONE * 277779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 194445 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\074', new Dimen(Dimen.ONE * 277779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 5 / 10), // CHARHT
        // new Dimen(Dimen.ONE * 194445 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\075', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 366875 / TEN_6) // CHARHT
        // // (CHARDP R -133125 / 1000000)
        // );
        // makeGlyph('\076', new Dimen(Dimen.ONE * 472224 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 5 / 10), // CHARHT
        // new Dimen(Dimen.ONE * 194445 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // g = makeGlyph('\077', new Dimen(Dimen.ONE * 472224 / TEN_6), //
        // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addLig((char) 0140, (char) 076); // (LIG O 140 O 76)
        // // )
        // makeGlyph('\100', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('A', new Dimen(Dimen.ONE * 750002 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('t', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C t R
        // // -027779 /
        // // 1000000)
        // g.addKern('C', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C C R
        // // -027779 /
        // // 1000000)
        // g.addKern('O', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C O R
        // // -027779 /
        // // 1000000)
        // g.addKern('G', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C G R
        // // -027779 /
        // // 1000000)
        // g.addKern('U', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C U R
        // // -027779 /
        // // 1000000)
        // g.addKern('Q', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C Q R
        // // -027779 /
        // // 1000000)
        // g.addKern('T', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C T R
        // // -083334 /
        // // 1000000)
        // g.addKern('Y', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C Y R
        // // -083334 /
        // // 1000000)
        // g.addKern('V', new Dimen(Dimen.ONE * -111112 / TEN_6)); // (KRN C V R
        // // -111112 /
        // // 1000000)
        // g.addKern('W', new Dimen(Dimen.ONE * -111112 / TEN_6)); // (KRN C W R
        // // -111112 /
        // // 1000000)
        // // )
        // makeGlyph('B', new Dimen(Dimen.ONE * 708336 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('C', new Dimen(ONE_722224 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('D', new Dimen(Dimen.ONE * 763891 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('X', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C X R
        // // -027779 /
        // // 1000000)
        // g.addKern('W', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C W R
        // // -027779 /
        // // 1000000)
        // g.addKern('A', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C A R
        // // -027779 /
        // // 1000000)
        // g.addKern('V', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C V R
        // // -027779 /
        // // 1000000)
        // g.addKern('Y', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C Y R
        // // -027779 /
        // // 1000000)
        // // )
        // makeGlyph('E', new Dimen(Dimen.ONE * 680557 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('F', new Dimen(Dimen.ONE * 652781 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('o', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C o R
        // // -083334 /
        // // 1000000)
        // g.addKern('e', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C e R
        // // -083334 /
        // // 1000000)
        // g.addKern('u', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C u R
        // // -083334 /
        // // 1000000)
        // g.addKern('r', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C r R
        // // -083334 /
        // // 1000000)
        // g.addKern('a', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C a R
        // // -083334 /
        // // 1000000)
        // g.addKern('A', new Dimen(Dimen.ONE * -111112 / TEN_6)); // (KRN C A R
        // // -111112 /
        // // 1000000)
        // g.addKern('O', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C O R
        // // -027779 /
        // // 1000000)
        // g.addKern('C', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C C R
        // // -027779 /
        // // 1000000)
        // g.addKern('G', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C G R
        // // -027779 /
        // // 1000000)
        // g.addKern('Q', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C Q R
        // // -027779 /
        // // 1000000)
        // // )
        // makeGlyph('G', new Dimen(Dimen.ONE * 784724 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('H', new Dimen(Dimen.ONE * 750002 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('I', new Dimen(Dimen.ONE * 361112 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('I', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C I R
        // // 027779 /
        // // 1000000)
        // // )
        // makeGlyph('J', new Dimen(Dimen.ONE * 51389 / 100000), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('K', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('O', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C O R
        // // -027779 /
        // // 1000000)
        // g.addKern('C', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C C R
        // // -027779 /
        // // 1000000)
        // g.addKern('G', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C G R
        // // -027779 /
        // // 1000000)
        // g.addKern('Q', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C Q R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('L', new Dimen(Dimen.ONE * 625002 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('T', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C T R
        // // -083334 /
        // // 1000000)
        // g.addKern('Y', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C Y R
        // // -083334 /
        // // 1000000)
        // g.addKern('V', new Dimen(Dimen.ONE * -111112 / TEN_6)); // (KRN C V R
        // // -111112 /
        // // 1000000)
        // g.addKern('W', new Dimen(Dimen.ONE * -111112 / TEN_6)); // (KRN C W R
        // // -111112 /
        // // 1000000)
        // // )
        // makeGlyph('M', new Dimen(Dimen.ONE * 916669 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('N', new Dimen(Dimen.ONE * 750002 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('O', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('X', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C X R
        // // -027779 /
        // // 1000000)
        // g.addKern('W', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C W R
        // // -027779 /
        // // 1000000)
        // g.addKern('A', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C A R
        // // -027779 /
        // // 1000000)
        // g.addKern('V', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C V R
        // // -027779 /
        // // 1000000)
        // g.addKern('Y', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C Y R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('P', new Dimen(Dimen.ONE * 680557 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('A', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C A R
        // // -083334 /
        // // 1000000)
        // g.addKern('o', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C o R
        // // -027779 /
        // // 1000000)
        // g.addKern('e', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C e R
        // // -027779 /
        // // 1000000)
        // g.addKern('a', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C a R
        // // -027779 /
        // // 1000000)
        // g.addKern((char) 056, new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN
        // // O 56
        // // R
        // // -083334
        // // /
        // // 1000000)
        // g.addKern((char) 054, new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN
        // // O 54
        // // R
        // // -083334
        // // /
        // // 1000000)
        // // )
        // makeGlyph('Q', new Dimen(ONE_777781 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 194445 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // g = makeGlyph('R', new Dimen(Dimen.ONE * 736113 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('t', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C t R
        // // -027779 /
        // // 1000000)
        // g.addKern('C', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C C R
        // // -027779 /
        // // 1000000)
        // g.addKern('O', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C O R
        // // -027779 /
        // // 1000000)
        // g.addKern('G', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C G R
        // // -027779 /
        // // 1000000)
        // g.addKern('U', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C U R
        // // -027779 /
        // // 1000000)
        // g.addKern('Q', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C Q R
        // // -027779 /
        // // 1000000)
        // g.addKern('T', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C T R
        // // -083334 /
        // // 1000000)
        // g.addKern('Y', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C Y R
        // // -083334 /
        // // 1000000)
        // g.addKern('V', new Dimen(Dimen.ONE * -111112 / TEN_6)); // (KRN C V R
        // // -111112 /
        // // 1000000)
        // g.addKern('W', new Dimen(Dimen.ONE * -111112 / TEN_6)); // (KRN C W R
        // // -111112 /
        // // 1000000)
        // // )
        // makeGlyph('S', new Dimen(Dimen.ONE * 555557 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('T', new Dimen(ONE_722224 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('y', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C y R
        // // -027779 /
        // // 1000000)
        // g.addKern('e', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C e R
        // // -083334 /
        // // 1000000)
        // g.addKern('o', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C o R
        // // -083334 /
        // // 1000000)
        // g.addKern('r', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C r R
        // // -083334 /
        // // 1000000)
        // g.addKern('a', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C a R
        // // -083334 /
        // // 1000000)
        // g.addKern('A', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C A R
        // // -083334 /
        // // 1000000)
        // g.addKern('u', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C u R
        // // -083334 /
        // // 1000000)
        // // )
        // makeGlyph('U', new Dimen(Dimen.ONE * 750002 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('V', new Dimen(Dimen.ONE * 750002 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6), // CHARHT
        // Dimen.ZERO_PT, new Dimen(Dimen.ONE * 13888 / TEN_6) // CHARIC
        // );
        // // (COMMENT
        // g.addKern('o', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C o R
        // // -083334 /
        // // 1000000)
        // g.addKern('e', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C e R
        // // -083334 /
        // // 1000000)
        // g.addKern('u', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C u R
        // // -083334 /
        // // 1000000)
        // g.addKern('r', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C r R
        // // -083334 /
        // // 1000000)
        // g.addKern('a', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C a R
        // // -083334 /
        // // 1000000)
        // g.addKern('A', new Dimen(Dimen.ONE * -111112 / TEN_6)); // (KRN C A R
        // // -111112 /
        // // 1000000)
        // g.addKern('O', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C O R
        // // -027779 /
        // // 1000000)
        // g.addKern('C', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C C R
        // // -027779 /
        // // 1000000)
        // g.addKern('G', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C G R
        // // -027779 /
        // // 1000000)
        // g.addKern('Q', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C Q R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('W', new Dimen(Dimen.ONE * 1027781 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6), // CHARHT
        // Dimen.ZERO_PT, new Dimen(Dimen.ONE * 13888 / TEN_6) // CHARIC
        // );
        // // (COMMENT
        // g.addKern('o', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C o R
        // // -083334 /
        // // 1000000)
        // g.addKern('e', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C e R
        // // -083334 /
        // // 1000000)
        // g.addKern('u', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C u R
        // // -083334 /
        // // 1000000)
        // g.addKern('r', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C r R
        // // -083334 /
        // // 1000000)
        // g.addKern('a', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C a R
        // // -083334 /
        // // 1000000)
        // g.addKern('A', new Dimen(Dimen.ONE * -111112 / TEN_6)); // (KRN C A R
        // // -111112 /
        // // 1000000)
        // g.addKern('O', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C O R
        // // -027779 /
        // // 1000000)
        // g.addKern('C', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C C R
        // // -027779 /
        // // 1000000)
        // g.addKern('G', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C G R
        // // -027779 /
        // // 1000000)
        // g.addKern('Q', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C Q R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('X', new Dimen(Dimen.ONE * 750002 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('O', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C O R
        // // -027779 /
        // // 1000000)
        // g.addKern('C', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C C R
        // // -027779 /
        // // 1000000)
        // g.addKern('G', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C G R
        // // -027779 /
        // // 1000000)
        // g.addKern('Q', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C Q R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('Y', new Dimen(Dimen.ONE * 750002 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 0), // CHARDP
        // new Dimen(Dimen.ONE * 25 / 1000) // CHARIC
        // );
        // // (COMMENT
        // g.addKern('e', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C e R
        // // -083334 /
        // // 1000000)
        // g.addKern('o', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C o R
        // // -083334 /
        // // 1000000)
        // g.addKern('r', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C r R
        // // -083334 /
        // // 1000000)
        // g.addKern('a', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C a R
        // // -083334 /
        // // 1000000)
        // g.addKern('A', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C A R
        // // -083334 /
        // // 1000000)
        // g.addKern('u', new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN C u R
        // // -083334 /
        // // 1000000)
        // // )
        // makeGlyph('Z', new Dimen(Dimen.ONE * 611113 / TEN_6), // CHARWD
        // new Dimen(ONE_683332 / TEN_6) // CHARHT
        // );
        // makeGlyph('\133', new Dimen(Dimen.ONE * 277779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 75 / 100), // CHARHT
        // new Dimen(Dimen.ONE * 25 / 100), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\134', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\135', new Dimen(Dimen.ONE * 277779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 75 / 100), // CHARHT
        // new Dimen(Dimen.ONE * 25 / 100), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('\136', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\137', new Dimen(Dimen.ONE * 277779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 667859 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('\140', new Dimen(Dimen.ONE * 277779 / TEN_6), //
        // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addLig((char) 0140, (char) 0134); // (LIG O 140 O 134)
        // // )
        // g = makeGlyph('a', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('v', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C v R
        // // -027779 /
        // // 1000000)
        // g.addKern('j', new Dimen(Dimen.ONE * 55555 / TEN_6)); // (KRN C j R
        // // 055555 /
        // // 1000000)
        // g.addKern('y', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C y R
        // // -027779 /
        // // 1000000)
        // g.addKern('w', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C w R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('b', new Dimen(Dimen.ONE * 555557 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('e', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C e R
        // // 027779 /
        // // 1000000)
        // g.addKern('o', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C o R
        // // 027779 /
        // // 1000000)
        // g.addKern('x', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C x R
        // // -027779 /
        // // 1000000)
        // g.addKern('d', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C d R
        // // 027779 /
        // // 1000000)
        // g.addKern('c', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C c R
        // // 027779 /
        // // 1000000)
        // g.addKern('q', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C q R
        // // 027779 /
        // // 1000000)
        // g.addKern('v', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C v R
        // // -027779 /
        // // 1000000)
        // g.addKern('j', new Dimen(Dimen.ONE * 55555 / TEN_6)); // (KRN C j R
        // // 055555 /
        // // 1000000)
        // g.addKern('y', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C y R
        // // -027779 /
        // // 1000000)
        // g.addKern('w', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C w R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('c', new Dimen(Dimen.ONE * 444446 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('h', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C h R
        // // -027779 /
        // // 1000000)
        // g.addKern('k', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C k R
        // // -027779 /
        // // 1000000)
        // // )
        // makeGlyph('d', new Dimen(Dimen.ONE * 555557 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('e', new Dimen(Dimen.ONE * 444446 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('f', new Dimen(Dimen.ONE * 305557 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6), // CHARHT
        // Dimen.ZERO_PT, new Dimen(Dimen.ONE * 77779 / TEN_6) // CHARIC
        // );
        // // (COMMENT
        // g.addLig('i', (char) 014); // (LIG C i O 14)
        // g.addLig('f', (char) 013); // (LIG C f O 13)
        // g.addLig('l', (char) 015); // (LIG C l O 15)
        // g.addKern((char) 047, new Dimen(Dimen.ONE * 77779 / TEN_6)); // (KRN
        // // O 47
        // // R
        // // 077779
        // // /
        // // 1000000)
        // g.addKern((char) 077, new Dimen(Dimen.ONE * 77779 / TEN_6)); // (KRN
        // // O 77
        // // R
        // // 077779
        // // /
        // // 1000000)
        // g.addKern((char) 041, new Dimen(Dimen.ONE * 77779 / TEN_6)); // (KRN
        // // O 41
        // // R
        // // 077779
        // // /
        // // 1000000)
        // g.addKern((char) 051, new Dimen(Dimen.ONE * 77779 / TEN_6)); // (KRN
        // // O 51
        // // R
        // // 077779
        // // /
        // // 1000000)
        // g.addKern((char) 0135, new Dimen(Dimen.ONE * 77779 / TEN_6)); // (KRN
        // // O 135
        // // R
        // // 077779
        // // /
        // // 1000000)
        // // )
        // g = makeGlyph('g', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 194445 / TEN_6), // CHARDP
        // new Dimen(Dimen.ONE * 13888 / TEN_6) // CHARIC
        // );
        // // (COMMENT
        // g.addKern('j', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C j R
        // // 027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('h', new Dimen(Dimen.ONE * 555557 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('t', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C t R
        // // -027779 /
        // // 1000000)
        // g.addKern('u', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C u R
        // // -027779 /
        // // 1000000)
        // g.addKern('b', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C b R
        // // -027779 /
        // // 1000000)
        // g.addKern('y', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C y R
        // // -027779 /
        // // 1000000)
        // g.addKern('v', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C v R
        // // -027779 /
        // // 1000000)
        // g.addKern('w', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C w R
        // // -027779 /
        // // 1000000)
        // // )
        // makeGlyph('i', new Dimen(Dimen.ONE * 277779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 667859 / TEN_6) // CHARHT
        // );
        // makeGlyph('j', new Dimen(Dimen.ONE * 305557 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 667859 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 194445 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // g = makeGlyph('k', new Dimen(Dimen.ONE * 527781 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('a', new Dimen(Dimen.ONE * -55555 / TEN_6)); // (KRN C a R
        // // -055555 /
        // // 1000000)
        // g.addKern('e', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C e R
        // // -027779 /
        // // 1000000)
        // g.addKern('a', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C a R
        // // -027779 /
        // // 1000000)
        // g.addKern('o', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C o R
        // // -027779 /
        // // 1000000)
        // g.addKern('c', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C c R
        // // -027779 /
        // // 1000000)
        // // )
        // makeGlyph('l', new Dimen(Dimen.ONE * 277779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('m', new Dimen(Dimen.ONE * 833336 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('t', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C t R
        // // -027779 /
        // // 1000000)
        // g.addKern('u', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C u R
        // // -027779 /
        // // 1000000)
        // g.addKern('b', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C b R
        // // -027779 /
        // // 1000000)
        // g.addKern('y', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C y R
        // // -027779 /
        // // 1000000)
        // g.addKern('v', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C v R
        // // -027779 /
        // // 1000000)
        // g.addKern('w', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C w R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('n', new Dimen(Dimen.ONE * 555557 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('t', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C t R
        // // -027779 /
        // // 1000000)
        // g.addKern('u', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C u R
        // // -027779 /
        // // 1000000)
        // g.addKern('b', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C b R
        // // -027779 /
        // // 1000000)
        // g.addKern('y', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C y R
        // // -027779 /
        // // 1000000)
        // g.addKern('v', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C v R
        // // -027779 /
        // // 1000000)
        // g.addKern('w', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C w R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('o', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('e', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C e R
        // // 027779 /
        // // 1000000)
        // g.addKern('o', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C o R
        // // 027779 /
        // // 1000000)
        // g.addKern('x', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C x R
        // // -027779 /
        // // 1000000)
        // g.addKern('d', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C d R
        // // 027779 /
        // // 1000000)
        // g.addKern('c', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C c R
        // // 027779 /
        // // 1000000)
        // g.addKern('q', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C q R
        // // 027779 /
        // // 1000000)
        // g.addKern('v', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C v R
        // // -027779 /
        // // 1000000)
        // g.addKern('j', new Dimen(Dimen.ONE * 55555 / TEN_6)); // (KRN C j R
        // // 055555 /
        // // 1000000)
        // g.addKern('y', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C y R
        // // -027779 /
        // // 1000000)
        // g.addKern('w', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C w R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('p', new Dimen(Dimen.ONE * 555557 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 194445 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // // (COMMENT
        // g.addKern('e', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C e R
        // // 027779 /
        // // 1000000)
        // g.addKern('o', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C o R
        // // 027779 /
        // // 1000000)
        // g.addKern('x', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C x R
        // // -027779 /
        // // 1000000)
        // g.addKern('d', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C d R
        // // 027779 /
        // // 1000000)
        // g.addKern('c', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C c R
        // // 027779 /
        // // 1000000)
        // g.addKern('q', new Dimen(Dimen.ONE * 27779 / TEN_6)); // (KRN C q R
        // // 027779 /
        // // 1000000)
        // g.addKern('v', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C v R
        // // -027779 /
        // // 1000000)
        // g.addKern('j', new Dimen(Dimen.ONE * 55555 / TEN_6)); // (KRN C j R
        // // 055555 /
        // // 1000000)
        // g.addKern('y', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C y R
        // // -027779 /
        // // 1000000)
        // g.addKern('w', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C w R
        // // -027779 /
        // // 1000000)
        // // )
        // makeGlyph('q', new Dimen(Dimen.ONE * 527779 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 194445 / TEN_6), // CHARDP
        // Dimen.ZERO_PT);
        // makeGlyph('r', new Dimen(Dimen.ONE * 391668 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // makeGlyph('s', new Dimen(Dimen.ONE * 394445 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('t', new Dimen(Dimen.ONE * 38889 / 100000), // CHARWD
        // new Dimen(Dimen.ONE * 61508 / 100000) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('y', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C y R
        // // -027779 /
        // // 1000000)
        // g.addKern('w', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C w R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('u', new Dimen(Dimen.ONE * 555557 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // // (COMMENT
        // g.addKern('w', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C w R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('v', new Dimen(Dimen.ONE * 527781 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6), // CHARHT
        // Dimen.ZERO_PT, new Dimen(Dimen.ONE * 13888 / TEN_6) // CHARIC
        // );
        // // (COMMENT
        // g.addKern('a', new Dimen(Dimen.ONE * -55555 / TEN_6)); // (KRN C a R
        // // -055555 /
        // // 1000000)
        // g.addKern('e', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C e R
        // // -027779 /
        // // 1000000)
        // g.addKern('a', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C a R
        // // -027779 /
        // // 1000000)
        // g.addKern('o', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C o R
        // // -027779 /
        // // 1000000)
        // g.addKern('c', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C c R
        // // -027779 /
        // // 1000000)
        // // )
        // g = makeGlyph('w', new Dimen(ONE_722224 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6), // CHARHT
        // Dimen.ZERO_PT, new Dimen(Dimen.ONE * 13888 / TEN_6) // CHARIC
        // );
        // // (COMMENT
        // g.addKern('e', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C e R
        // // -027779 /
        // // 1000000)
        // g.addKern('a', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C a R
        // // -027779 /
        // // 1000000)
        // g.addKern('o', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C o R
        // // -027779 /
        // // 1000000)
        // g.addKern('c', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C c R
        // // -027779 /
        // // 1000000)
        // // )
        // makeGlyph('x', new Dimen(Dimen.ONE * 527781 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('y', new Dimen(Dimen.ONE * 527781 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6), // CHARHT
        // new Dimen(Dimen.ONE * 194445 / TEN_6), // CHARDP
        // new Dimen(Dimen.ONE * 13888 / TEN_6) // CHARIC
        // );
        // // (COMMENT
        // g.addKern('o', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C o R
        // // -027779 /
        // // 1000000)
        // g.addKern('e', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C e R
        // // -027779 /
        // // 1000000)
        // g.addKern('a', new Dimen(Dimen.ONE * -27779 / TEN_6)); // (KRN C a R
        // // -027779 /
        // // 1000000)
        // g.addKern((char) 056, new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN
        // // O 56
        // // R
        // // -083334
        // // /
        // // 1000000)
        // g.addKern((char) 054, new Dimen(Dimen.ONE * -83334 / TEN_6)); // (KRN
        // // O 54
        // // R
        // // -083334
        // // /
        // // 1000000)
        // // )
        // makeGlyph('z', new Dimen(Dimen.ONE * 444446 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6) // CHARHT
        // );
        // g = makeGlyph('\173', new Dimen(Dimen.ONE * 500002 / TEN_6), //
        // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6), // CHARHT
        // Dimen.ZERO_PT, new Dimen(Dimen.ONE * 27779 / TEN_6) // CHARIC
        // );
        // // (COMMENT
        // g.addLig((char) 055, (char) 0174); // (LIG O 55 O 174)
        // // )
        // makeGlyph('\174', new Dimen(Dimen.ONE * 1000003 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 430555 / TEN_6), // CHARHT
        // Dimen.ZERO_PT, new Dimen(Dimen.ONE * 27779 / TEN_6) // CHARIC
        // );
        // makeGlyph('\175', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 694445 / TEN_6) // CHARHT
        // );
        // makeGlyph('\176', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 667859 / TEN_6) // CHARHT
        // );
        // makeGlyph('\177', new Dimen(Dimen.ONE * 500002 / TEN_6), // CHARWD
        // new Dimen(Dimen.ONE * 667859 / TEN_6) // CHARHT
        // );

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.tc.font.Font#getActualSize()
     */
    public FixedDimen getActualSize() {

        return designSize;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.tc.font.Font#getCheckSum()
     */
    public int getCheckSum() {

        return 011374260171;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.tc.font.Font#getDesignSize()
     */
    public FixedDimen getDesignSize() {

        return designSize;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.tc.font.Font#getEm()
     */
    public FixedDimen getEm() {

        return fontdimen[QUAD];
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.tc.font.Font#getEx()
     */
    public FixedDimen getEx() {

        return fontdimen[XHEIGHT];
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.tc.font.Font#getFontDimen(java.lang.String)
     */
    public FixedDimen getFontDimen(String key) {

        return fontdimen[Integer.parseInt(key)];
    }

    /**
     * Returns the FontKey for this font.
     * 
     * @return the FontKey for this font
     * 
     * @see org.extex.typesetter.tc.font.Font#getFontKey()
     */
    public FontKey getFontKey() {

        return fontKey;
    }

    /**
     * Return the font name.
     * 
     * @return the font name
     * 
     * @see org.extex.typesetter.tc.font.Font#getFontName()
     */
    public String getFontName() {

        return "cmr10";
    }

    /**
     * Returns the hyphen character.
     * 
     * @return the hyphen character
     * 
     * @see org.extex.typesetter.tc.font.Font#getHyphenChar()
     */
    public UnicodeChar getHyphenChar() {

        return this.hypenChar;
    }

    /**
     * Returns the skew char.
     * 
     * @return the skew char
     * 
     * @see org.extex.typesetter.tc.font.Font#getSkewChar()
     */
    public UnicodeChar getSkewChar() {

        return this.skewChar;
    }

    /**
     * Returns the size of the 'space'.
     * 
     * @return the size of the 'space'.
     * 
     * @see org.extex.typesetter.tc.font.Font#getSpace()
     */
    public FixedGlue getSpace() {

        return space;
    }

    // private MyGlyph makeGlyph(char c, Dimen w, Dimen h) {
    //
    // UnicodeChar uc = UnicodeChar.get(c);
    // MyGlyph g = new MyGlyph(uc, w, h);
    // glyphs.put(uc, g);
    // return g;
    // }

    // private MyGlyph makeGlyph(char c, Dimen w, Dimen h,
    // Dimen d, Dimen ic) {
    //
    // UnicodeChar uc = UnicodeChar.get(c);
    // MyGlyph g = new MyGlyph(uc, w, h, d, ic);
    // glyphs.put(uc, g);
    // return g;
    // }

    /**
     * Set the new value for the font parameter.
     * 
     * @param key the name of the parameter
     * @param value the value to set
     * 
     * @see org.extex.typesetter.tc.font.Font#setFontDimen( java.lang.String,
     *      org.extex.core.dimen.Dimen)
     */
    public void setFontDimen(String key, Dimen value) {

        fontdimen[Integer.parseInt(key)].set(value);
    }

    /**
     * Set the hyphen character.
     * 
     * @param hyphen the hyphen character
     * 
     * @see org.extex.typesetter.tc.font.Font#setHyphenChar(
     *      org.extex.core.UnicodeChar)
     */
    public void setHyphenChar(UnicodeChar hyphen) {

        this.hypenChar = hyphen;
    }

    /**
     * Set the skew character.
     * 
     * @param skew the skew character
     * 
     * @see org.extex.typesetter.tc.font.Font#setSkewChar(
     *      org.extex.core.UnicodeChar)
     */
    public void setSkewChar(UnicodeChar skew) {

        this.skewChar = skew;
    }

    /**
     * Setter for the ef code. The ef code influences the stretchability of
     * characters. It has a positive value. 1000 means "normal" stretchability.
     * 
     * @param uc the character
     * @param code the associated code
     * 
     * @see org.extex.typesetter.tc.font.Font#setEfCode(
     *      org.extex.core.UnicodeChar, long)
     */
    public void setEfCode(UnicodeChar uc, long code) {

        efcode.put(uc, Long.valueOf(code));
    }

    /**
     * The field <tt>efcode</tt> contains the ef code.
     */
    private Map<UnicodeChar, Long> efcode = new HashMap<UnicodeChar, Long>();

    /**
     * Getter for the ef code.
     * 
     * @param uc the character
     * 
     * @return the ef code
     * 
     * @see org.extex.typesetter.tc.font.Font#getEfCode(
     *      org.extex.core.UnicodeChar)
     */
    public long getEfCode(UnicodeChar uc) {

        Long x = efcode.get(uc);
        return (x == null ? 0 : x.longValue());
    }

    /**
     * Returns the depth of the character.
     * 
     * @param uc the character
     * 
     * @return the depth of the character
     * 
     * @see org.extex.typesetter.tc.font.Font#getDepth(org.extex.core.UnicodeChar)
     */
    public FixedGlue getDepth(UnicodeChar uc) {

        // TODO gene: getDepth unimplemented
        return null;
    }

    /**
     * Returns the height of a character.
     * 
     * @param uc the character
     * 
     * @return the height of the character
     * 
     * @see org.extex.typesetter.tc.font.Font#getHeight(
     *      org.extex.core.UnicodeChar)
     */
    public FixedGlue getHeight(UnicodeChar uc) {

        // TODO gene: getHeight unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.tc.font.Font#getItalicCorrection(
     *      org.extex.core.UnicodeChar)
     */
    public FixedDimen getItalicCorrection(UnicodeChar uc) {

        // TODO gene: getItalicCorrection unimplemented
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.typesetter.tc.font.Font#getKerning(
     *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar)
     */
    public FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2) {

        // TODO gene: getKerning unimplemented
        return null;
    }

    /**
     * Returns the ligature for two characters.
     * 
     * @param uc1 the first character
     * @param uc2 the second character
     * 
     * @return Returns the ligature for two characters
     * 
     * @see org.extex.typesetter.tc.font.Font#getLigature(
     *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar)
     */
    public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

        // TODO gene: getLigature unimplemented
        return null;
    }

    /**
     * Returns the width of a character.
     * 
     * @param uc the character
     * 
     * @return the width of the character
     * 
     * @see org.extex.typesetter.tc.font.Font#getWidth(
     *      org.extex.core.UnicodeChar)
     */
    public FixedGlue getWidth(UnicodeChar uc) {

        // TODO gene: getWidth unimplemented
        return null;
    }

    /**
     * Determine whether the glyph for a given character is present in this
     * font.
     * 
     * @param uc the character
     * 
     * @return <code>true</code> iff the glyph is present
     * 
     * @see org.extex.typesetter.tc.font.Font#hasGlyph(
     *      org.extex.core.UnicodeChar)
     */
    public boolean hasGlyph(UnicodeChar uc) {

        return true;
    }

    /**
     * Returns the scale factor of the font.
     * 
     * @return the scale factor of the font
     * 
     * @see org.extex.typesetter.tc.font.Font#getScaleFactor()
     */
    public FixedCount getScaleFactor() {

        return Count.THOUSAND;
    }

    /**
     * Returns the actual FontKey for this font.
     * 
     * @return the actual FontKey for this font.
     * 
     * @see org.extex.typesetter.tc.font.Font#getActualFontKey()
     */
    public FontKey getActualFontKey() {

        return fontKey;
    }

    /**
     * The field <tt>fontKey</tt> contains the font key.
     */
    private FontKey fontKey = new FontKeyFactory().newInstance("cmr10");

}
