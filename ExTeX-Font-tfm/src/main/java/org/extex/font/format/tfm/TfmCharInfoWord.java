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
 *
 */

package org.extex.font.format.tfm;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.extex.util.file.random.RandomAccessR;

/**
 * Class for TFM char info word.
 * 
 * <p>
 * Each char_info_word contains six fields packed into four bytes as follows.
 * </p>
 * 
 * <table border="1"> <thead>
 * <tr>
 * <td>byte</td>
 * <td>description</td>
 * </tr>
 * </thead>
 * <tr>
 * <td>first </td>
 * <td>width_index (8 bits)</td>
 * </tr>
 * <tr>
 * <td>second </td>
 * <td>height_index (4 bits) times 16, plus depth_index (4 bits)</td>
 * </tr>
 * <tr>
 * <td>third </td>
 * <td>italic_index (6 bits) times 4, plus tag (2 bits)</td>
 * </tr>
 * <tr>
 * <td>fourth </td>
 * <td>remainder (8 bits)</td>
 * </tr>
 * </table>
 * 
 * <p>
 * The tag field has four values that explain how to interpret the remainder
 * field.
 * </p>
 * 
 * <table border="1"> <thead>
 * <tr>
 * <td>tag</td>
 * <td>description</td>
 * </tr>
 * </thead>
 * <tr>
 * <td>0 </td>
 * <td>no_tag: means that remainder is unused.</td>
 * </tr>
 * <tr>
 * <td>1 </td>
 * <td>lig_tag: means that this character has a ligature/kerning program
 * starting at lig_kern[remainder].</td>
 * </tr>
 * <tr>
 * <td>2 </td>
 * <td>list_tag: means that this character is part of a chain of characters of
 * ascending sizes, and not the largest in the chain. The remainder field gives
 * the character code of the next larger character.</td>
 * </tr>
 * <tr>
 * <td>3 </td>
 * <td>ext_tag: means that this character code represents an extensible
 * character, i.e., a character that is built up of smaller pieces so that it
 * can be made arbitrarily large. The pieces are specified in exten[remainder].</td>
 * </tr>
 * </table>
 * 
 * <p>
 * Information from: The DVI Driver Standard, Level 0 The TUG DVI Driver
 * Standards Committee
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class TfmCharInfoWord implements Serializable {

    /**
     * Tag (type-safe class).
     */
    private static final class Tag implements Serializable {

        /**
         * The field <tt>serialVersionUID</tt>.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new object.
         */
        public Tag() {

            super();
        }
    }

    /**
     * ext_tag: character is extensible.
     */
    public static final Tag EXT_TAG = new Tag();

    /**
     * lig_tag: character has a ligature/kerning program.
     */
    public static final Tag LIG_TAG = new Tag();

    /**
     * list_tag: character has a successor in a charlist.
     */
    public static final Tag LIST_TAG = new Tag();

    /**
     * no_tag: vanilla character.
     */
    public static final Tag NO_TAG = new Tag();

    /**
     * Symbolic constant for nonexistent character code.
     */
    public static final short NOCHARCODE = -1;

    /**
     * Symbolic constant for index which is not valid.
     */
    public static final int NOINDEX = -1;

    /**
     * The field <tt>serialVersionUID</tt>.
     */
    private static final long serialVersionUID = 1L;

    /**
     * no_tag: 0.
     */
    public static final int TAG0 = 0;

    /**
     * no_tag: 1.
     */
    public static final int TAG1 = 1;

    /**
     * no_tag: 2.
     */
    public static final int TAG2 = 2;

    /**
     * no_tag: 3.
     */
    public static final int TAG3 = 3;

    /**
     * smallest character code in the font.
     */
    private short bc;

    /**
     * bottom part chracter code.
     */
    private short bot = NOCHARCODE;

    /**
     * the char id.
     */
    private int charid;

    /**
     * Character depth.
     */
    private TfmFixWord depth = TfmFixWord.ZERO;

    /**
     * the depth index.
     */
    private short depthindex;

    /**
     * the glyphname.
     */
    private String glyphname;

    /**
     * Character height.
     */
    private TfmFixWord height = TfmFixWord.ZERO;

    /**
     * the height index.
     */
    private short heightindex;

    /**
     * Character italic correction.
     */
    private TfmFixWord italic = TfmFixWord.ZERO;

    /**
     * the italic index.
     */
    private short italicindex;

    /**
     * The kerning map.
     */
    private Map<Integer, TfmFixWord> kernmap;

    /**
     * Index to newly created ligKernTable which is set during translation of
     * the original raw lig/kern table in the tfm file.
     */
    private int ligkernstart = NOINDEX;

    /**
     * Lig/kern programs in the final format.
     */
    private TfmLigKern[] ligKernTable;

    /**
     * The ligature map.
     */
    private Map<Integer, Integer> ligmap;

    /**
     * middle part chracter code.
     */
    private short mid = NOCHARCODE;

    /**
     * Next larger character code.
     */
    private short nextchar = NOINDEX;

    /**
     * the remainder.
     */
    private short remainder;

    /**
     * repeatable part character code.
     */
    private short rep = NOCHARCODE;

    /**
     * the tag (as number).
     */
    private short tag;

    /**
     * the tag.
     */
    private Tag tagT;

    /**
     * top part character code.
     */
    private short top = NOCHARCODE;

    /**
     * Character width.
     */
    private TfmFixWord width = TfmFixWord.ZERO;

    /**
     * the width index.
     */
    private short widthindex;

    /**
     * Create a new object.
     * 
     * @param rar the input
     * @param id the id
     * @throws IOException if an IO-error occurs.
     */
    public TfmCharInfoWord(RandomAccessR rar, int id) throws IOException {

        charid = id;
        widthindex = (short) rar.readByteAsInt();
        short heightdepthindex = (short) rar.readByteAsInt();
        heightindex =
                (short) (heightdepthindex >> TfmConstants.CONST_4 & TfmConstants.CONST_X0F);
        depthindex = (short) (heightdepthindex & TfmConstants.CONST_X0F);
        short italicindextag = (short) rar.readByteAsInt();
        italicindex = (short) (italicindextag >> 2 & TfmConstants.CONST_X3F);
        tag = (short) (italicindextag & TfmConstants.CONST_X03);
        remainder = (short) rar.readByteAsInt();

        switch (tag) {
            case TAG0:
                tagT = NO_TAG;
                break;
            case TAG1:
                tagT = LIG_TAG;
                break;
            case TAG2:
                tagT = LIST_TAG;
                break;
            case TAG3:
                tagT = EXT_TAG;
                break;
            default:
                // not defined: use no_tag
                tagT = NO_TAG;
        }

    }

    // /**
    // * Add glyph to the element.
    // *
    // * @param glyph the element
    // */
    // public void addGlyph(Element glyph) {
    //
    // glyph.setAttribute("width", getWidth().toStringComma());
    // glyph.setAttribute("height", getHeight().toStringComma());
    // glyph.setAttribute("depth", getDepth().toStringComma());
    // glyph.setAttribute("italic", getItalic().toStringComma());
    // glyph.setAttribute("width-fw", String.valueOf(getWidth().getValue()));
    // glyph.setAttribute("height-fw", String.valueOf(getHeight().getValue()));
    // glyph.setAttribute("depth-fw", String.valueOf(getDepth().getValue()));
    // glyph.setAttribute("italic-fw", String.valueOf(getItalic().getValue()));
    //
    // // ligature
    // int ligstart = getLigkernstart();
    // if (ligstart != TfmCharInfoWord.NOINDEX) {
    //
    // for (int k = ligstart; k != TfmCharInfoWord.NOINDEX; k =
    // ligKernTable[k].nextIndex(k)) {
    // TfmLigKern lk = ligKernTable[k];
    //
    // if (lk instanceof TfmLigature) {
    // TfmLigature lig = (TfmLigature) lk;
    //
    // Element ligature = new Element("ligature");
    //
    // ligature.setAttribute("letter-id", String.valueOf(lig
    // .getNextChar()));
    // String sl = Character.toString((char) lig.getNextChar());
    // if (sl != null && sl.trim().length() > 0) {
    // ligature.setAttribute("letter", sl.trim());
    // }
    //
    // ligature.setAttribute("lig-id", String.valueOf(lig
    // .getAddingChar()));
    // String slig =
    // Character.toString((char) lig.getAddingChar());
    // if (slig != null && slig.trim().length() > 0) {
    // ligature.setAttribute("lig", slig.trim());
    // }
    // glyph.addContent(ligature);
    // } else if (lk instanceof TfmKerning) {
    // TfmKerning kern = (TfmKerning) lk;
    //
    // Element kerning = new Element("kerning");
    //
    // kerning.setAttribute("glyph-id", String.valueOf(kern
    // .getNextChar()));
    // String sk = Character.toString((char) kern.getNextChar());
    // if (sk != null && sk.trim().length() > 0) {
    // kerning.setAttribute("char", sk.trim());
    // }
    // kerning
    // .setAttribute("size", kern.getKern().toStringComma());
    //
    // glyph.addContent(kerning);
    // }
    // }
    // }
    // }

    /**
     * Create the ligature/kerning map.
     */
    public void createLigKernMap() {

        kernmap = new HashMap<Integer, TfmFixWord>();
        ligmap = new HashMap<Integer, Integer>();

        // ligature
        int ligstart = getLigkernstart();
        if (ligstart != NOINDEX && ligKernTable != null) {

            for (int k = ligstart; k != NOINDEX; k =
                    ligKernTable[k].nextIndex(k)) {
                TfmLigKern lk = ligKernTable[k];

                if (lk instanceof TfmLigature) {
                    TfmLigature lig = (TfmLigature) lk;

                    ligmap.put(new Integer(lig.getNextChar()), new Integer(lig
                        .getAddingChar()));

                } else if (lk instanceof TfmKerning) {
                    TfmKerning kern = (TfmKerning) lk;

                    kernmap
                        .put(new Integer(kern.getNextChar()), kern.getKern());
                }
            }
        }
    }

    /**
     * Test, if the character exists in the font. (a character exists, if it
     * have a width)
     * 
     * @return Returns <code>true</code> if the character exists.
     */
    public boolean exists() {

        return widthindex != 0;
    }

    /**
     * Check, if char has a entry (glyph name, top, mid, bot, rep, ligature or
     * kern.
     * 
     * @return Returns true, if the char has an entry.
     */
    public boolean foundEntry() {

        boolean found = false;
        if (glyphname != null) {
            found = true;
        } else if (getTop() != NOCHARCODE) {
            found = true;
        } else if (getMid() != NOCHARCODE) {
            found = true;
        } else if (getBot() != NOCHARCODE) {
            found = true;
        } else if (getRep() != NOCHARCODE) {
            found = true;
        } else {
            int ligstart = getLigkernstart();
            if (ligstart != TfmCharInfoWord.NOINDEX && ligKernTable != null) {

                for (int k = ligstart; k != TfmCharInfoWord.NOINDEX; k =
                        ligKernTable[k].nextIndex(k)) {
                    TfmLigKern lk = ligKernTable[k];

                    if (lk instanceof TfmLigature || lk instanceof TfmKerning) {
                        found = true;
                        break;
                    }
                }
            }
        }
        return found;
    }

    /**
     * Returns the bc.
     * 
     * @return Returns the bc.
     */
    public short getBc() {

        return bc;
    }

    /**
     * Returns the bot.
     * 
     * @return Returns the bot.
     */
    public short getBot() {

        return bot;
    }

    /**
     * Returns the charid.
     * 
     * @return Returns the charid.
     */
    public int getCharid() {

        return charid;
    }

    /**
     * Returns the depth.
     * 
     * @return Returns the depth.
     */
    public TfmFixWord getDepth() {

        return depth;
    }

    /**
     * Returns the depth index.
     * 
     * @return Returns the depth index.
     */
    public short getDepthindex() {

        return depthindex;
    }

    /**
     * Returns the glyph name.
     * 
     * @return Returns the glyph name.
     */
    public String getGlyphname() {

        return glyphname;
    }

    /**
     * Returns the height.
     * 
     * @return Returns the height.
     */
    public TfmFixWord getHeight() {

        return height;
    }

    /**
     * Returns the height index.
     * 
     * @return Returns the height index.
     */
    public short getHeightindex() {

        return heightindex;
    }

    /**
     * Returns the italic.
     * 
     * @return Returns the italic.
     */
    public TfmFixWord getItalic() {

        return italic;
    }

    /**
     * Returns the italic index.
     * 
     * @return Returns the italic index.
     */
    public short getItalicindex() {

        return italicindex;
    }

    /**
     * Return the kerning.
     * 
     * @param cp2 the right char. This character has to exist.
     * @return the kerning.
     */
    public TfmFixWord getKerning(int cp2) {

        if (kernmap == null) {
            return TfmFixWord.ZERO;
        }
        TfmFixWord kern = kernmap.get(new Integer(cp2));
        if (kern != null) {
            return kern;
        }

        return TfmFixWord.ZERO;
    }

    /**
     * Return the ligature.
     * 
     * If no ligature exists, the -1 is returned.
     * 
     * @param cp2 the right char. This character has to exist.
     * @return the ligature.
     */
    public int getLigature(int cp2) {

        if (ligmap == null) {
            return -1;
        }
        Integer lig = ligmap.get(new Integer(cp2));
        if (lig != null) {
            return lig.intValue();
        }
        return -1;
    }

    /**
     * Returns the ligkern start.
     * 
     * @return Returns the ligkern start.
     */
    public int getLigkernstart() {

        return ligkernstart;
    }

    /**
     * Getter for ligKernTable.
     * 
     * @return Returns the ligKernTable.
     */
    public TfmLigKern[] getLigKernTable() {

        return ligKernTable;
    }

    /**
     * Returns the mid.
     * 
     * @return Returns the mid.
     */
    public short getMid() {

        return mid;
    }

    /**
     * Returns the next char.
     * 
     * @return Returns the next char.
     */
    public short getNextchar() {

        return nextchar;
    }

    /**
     * Returns the remainder.
     * 
     * @return Returns the remainder.
     */
    public short getRemainder() {

        return remainder;
    }

    /**
     * Returns the rep.
     * 
     * @return Returns the rep.
     */
    public short getRep() {

        return rep;
    }

    /**
     * Returns the tag.
     * 
     * @return Returns the tag.
     */
    public Tag getTag() {

        return tagT;
    }

    /**
     * Returns the tag as number.
     * 
     * @return Returns the tag as number.
     */
    public short getTagNumber() {

        return tag;
    }

    /**
     * Returns the top.
     * 
     * @return Returns the top.
     */
    public short getTop() {

        return top;
    }

    /**
     * Returns the width.
     * 
     * @return Returns the width.
     */
    public TfmFixWord getWidth() {

        return width;
    }

    /**
     * Returns the width index.
     * 
     * @return Returns the width index.
     */
    public short getWidthindex() {

        return widthindex;
    }

    /**
     * Resets the tag field to NOTAG (zero) value.
     */
    public void resetTag() {

        tag = TAG0;
        tagT = NO_TAG;
    }

    /**
     * Set bc.
     * 
     * @param abc The bc to set.
     */
    public void setBc(short abc) {

        bc = abc;
    }

    /**
     * Set the bot.
     * 
     * @param abot The bot to set.
     */
    public void setBot(short abot) {

        bot = abot;
    }

    /**
     * Det the depth.
     * 
     * @param adepth The depth to set.
     */
    public void setDepth(TfmFixWord adepth) {

        depth = adepth;
    }

    /**
     * Set the glyph name.
     * 
     * @param gn The glyph name to set.
     */
    public void setGlyphname(String gn) {

        glyphname = gn;
    }

    /**
     * Set the height.
     * 
     * @param aheight The height to set.
     */
    public void setHeight(TfmFixWord aheight) {

        height = aheight;
    }

    /**
     * Set the italic.
     * 
     * @param aitalic The italic to set.
     */
    public void setItalic(TfmFixWord aitalic) {

        italic = aitalic;
    }

    /**
     * Set the ligkern start.
     * 
     * @param ligkerns The ligkern start to set.
     */
    public void setLigkernstart(int ligkerns) {

        ligkernstart = ligkerns;
    }

    /**
     * Set the ligKernTable.
     * 
     * @param lk The ligKernTable to set.
     */
    public void setLigKernTable(TfmLigKern[] lk) {

        ligKernTable = lk;
    }

    /**
     * Set the mid.
     * 
     * @param amid The mid to set.
     */
    public void setMid(short amid) {

        mid = amid;
    }

    /**
     * Set the next char.
     * 
     * @param anextchar The next char to set.
     */
    public void setNextchar(short anextchar) {

        nextchar = anextchar;
    }

    /**
     * Set the rep.
     * 
     * @param arep The rep to set.
     */
    public void setRep(short arep) {

        rep = arep;
    }

    /**
     * Set the top.
     * 
     * @param atop The top to set.
     */
    public void setTop(short atop) {

        top = atop;
    }

    /**
     * Set the width.
     * 
     * @param awidth The width to set.
     */
    public void setWidth(TfmFixWord awidth) {

        width = awidth;
    }

}
