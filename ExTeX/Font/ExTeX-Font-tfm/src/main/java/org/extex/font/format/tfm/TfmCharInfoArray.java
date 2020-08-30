/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

import org.extex.util.file.random.RandomAccessR;

/**
 * Class for TFM char info.
 * 
 * <p>
 * Each character has one char_info_word.
 * </p>
 * <p>
 * Each char_info_word contains six fields packed into four bytes as follows.
 * </p>
 * 
 * <table> <caption>TBD</caption> <thead>
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
 * Information from: The DVI Driver Standard, Level 0 The TUG DVI Driver
 * Standards Committee
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/

public class TfmCharInfoArray implements Serializable {

    /**
     * The field {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 1L;

    /**
     * smallest character code in the font.
     */
    protected int bc;

    /**
     * the char info.
     */
    private TfmCharInfoWord[] charinfoword;

    /**
     * the depth.
     */
    private TfmDepthArray depth;

    /**
     * Encoding table.
     */
    private String[] enctable;

    /**
     * the exten.
     */
    private TfmExtenArray exten;

    /**
     * the height.
     */
    private TfmHeightArray height;

    /**
     * the italic.
     */
    private TfmItalicArray italic;

    /**
     * Lig/kern programs in the final format.
     */
    private TfmLigKern[] ligKernTable;

    /**
     * the width.
     */
    private TfmWidthArray width;

    /**
     * Creates a new object.
     * 
     * Only for subclasses.
     */
    protected TfmCharInfoArray() {

    }

    /**
     * Create a new object.
     * 
     * @param rar the input
     * @param cc number of character
     * @throws IOException if an IO-error occurs.
     */
    public TfmCharInfoArray(RandomAccessR rar, int cc) throws IOException {

        charinfoword = new TfmCharInfoWord[cc];
        for (int i = 0; i < cc; i++) {
            charinfoword[i] = new TfmCharInfoWord(rar, i);
        }
    }

    /**
     * Check the existence of particular character in the font.
     * 
     * @param pos the checked character code.
     * @return {@code true} if the character is present.
     */
    private boolean charExists(short pos) {

        int c = pos - bc;
        return (c >= 0 && c < charinfoword.length && charinfoword[c].exists());
    }

    /**
     * Create the char table.
     * 
     * @param widtha the width table
     * @param heighta the height table
     * @param deptha the depth table
     * @param italica the italic table
     * @param extena the exten table
     * @param abc the bc
     * @param lk the ligKernTable
     */
    public void createCharTable(TfmWidthArray widtha, TfmHeightArray heighta,
            TfmDepthArray deptha, TfmItalicArray italica, TfmExtenArray extena,
            int abc, TfmLigKern[] lk) {

        width = widtha;
        height = heighta;
        depth = deptha;
        italic = italica;
        bc = abc;
        exten = extena;
        ligKernTable = lk;

        for (int pos = 0; pos < charinfoword.length; pos++) {
            charinfoword[pos].setLigKernTable(ligKernTable);
            charinfoword[pos].setBc(bc);
            if (charinfoword[pos].exists()) {
                TfmCharInfoWord ciw = charinfoword[pos];
                ciw.setWidth(takeDimen(width.getTable(), ciw.getWidthindex(),
                    pos));
                ciw.setHeight(takeDimen(height.getTable(),
                    ciw.getHeightindex(), pos));
                ciw.setDepth(takeDimen(depth.getTable(), ciw.getDepthindex(),
                    pos));
                ciw.setItalic(takeDimen(italic.getTable(),
                    ciw.getItalicindex(), pos));
                if (ciw.getTag() == TfmCharInfoWord.LIST_TAG) {
                    if (validCharList(pos)) {
                        ciw.setNextchar(ciw.getRemainder());
                    }
                } else if (ciw.getTag() == TfmCharInfoWord.EXT_TAG) {
                    if (ciw.getRemainder() < exten.getExtensiblerecipe().length) {
                        TfmExtensibleRecipe er =
                                exten.getExtensiblerecipe()[ciw.getRemainder()];
                        ciw.setTop((er.getTop() != 0)
                                ? er.getTop()
                                : TfmCharInfoWord.NOCHARCODE);
                        ciw.setMid((er.getMid() != 0)
                                ? er.getMid()
                                : TfmCharInfoWord.NOCHARCODE);
                        ciw.setBot((er.getBot() != 0)
                                ? er.getBot()
                                : TfmCharInfoWord.NOCHARCODE);
                        ciw.setRep((er.getRep() != 0)
                                ? er.getRep()
                                : TfmCharInfoWord.NOCHARCODE);
                    }
                }
            }
        }
    }

    /**
     * Create the ligature/kerning map.
     */
    public void createLigKernMap() {

        for (int i = 0; i < charinfoword.length; i++) {
            charinfoword[i].createLigKernMap();
        }

    }

    /**
     * Returns the bc.
     * 
     * @return Returns the bc.
     */
    public int getBc() {

        return bc;
    }

    /**
     * Returns the charinfoword.
     * 
     * @return Returns the charinfoword.
     */
    public TfmCharInfoWord[] getCharinfoword() {

        return charinfoword;
    }

    /**
     * Returns the charinfoword for the character. If the position less then bc
     * (first character in the font), {@code null} will be returned.
     * 
     * @param i the position of the character
     * @return Returns the charinfoword for the character.
     */
    public TfmCharInfoWord getCharInfoWord(int i) {

        if (i < bc) {
            return null;
        }
        int idx = i;
        if (bc != 0) {
            idx = i - bc;
        }
        if (idx >= 0 && idx < charinfoword.length) {
            return charinfoword[idx];
        }
        return null;
    }

    /**
     * Returns the depth.
     * 
     * @return Returns the depth.
     */
    public TfmDepthArray getDepth() {

        return depth;
    }

    /**
     * Returns the height.
     * 
     * @return Returns the height.
     */
    public TfmHeightArray getHeight() {

        return height;
    }

    /**
     * Returns the italic.
     * 
     * @return Returns the italic.
     */
    public TfmItalicArray getItalic() {

        return italic;
    }

    /**
     * Returns the width.
     * 
     * @return Returns the width.
     */
    public TfmWidthArray getWidth() {

        return width;
    }

    /**
     * Set the encdoing table.
     * 
     * @param et the encoding table
     */
    public void setEncodingTable(String[] et) {

        if (et != null) {
            enctable = et;
            for (int i = 0; i < charinfoword.length; i++) {
                if (i < enctable.length) {
                    charinfoword[i].setGlyphname(enctable[i]);
                }
            }
        }
    }

    /**
     * Gets referenced character dimension from apropriate table.
     * 
     * @param table referenced table of dimensions.
     * @param i referenced index to the dimension table.
     * @param pos the position of character in {@code charTable} for
     *        error messages.
     * @return Returns the FixWord
     */
    protected TfmFixWord takeDimen(TfmFixWord[] table, int i, int pos) {

        if (i < table.length) {
            return table[i];
        }
        return TfmFixWord.ZERO;
    }

    /**
     * Checks the consistency of larger character chain. It checks only the
     * characters which have less position in |charTable| then the given
     * character position and are supossed to have the corresponding
     * {@code CharInfo} already created.
     * 
     * @param pos position of currently processed character in
     *        {@code charTable}.
     * @return {@code true} if the associated chain is consistent.
     */
    private boolean validCharList(int pos) {

        TfmCharInfoWord ciw = charinfoword[pos];
        short next = ciw.getRemainder();
        if (!charExists(next)) {
            ciw.resetTag();
            return false;
        }
        while ((next -= bc) < pos
                && (ciw = charinfoword[next]).getTag() == TfmCharInfoWord.LIST_TAG) {
            next = ciw.getRemainder();
        }
        return true;
    }

}
