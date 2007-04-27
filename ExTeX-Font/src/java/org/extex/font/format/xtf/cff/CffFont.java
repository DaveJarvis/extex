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

package org.extex.font.format.xtf.cff;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.extex.font.format.xtf.OtfTableCFF;
import org.extex.util.XMLWriterConvertible;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Class for a CffFont.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class CffFont implements XMLWriterConvertible {

    /**
     * The default for BlueScale.
     */
    private static final double BLUESCALE = 0.039625;

    /**
     * The default for EXPANSIONSFACTOR.
     */
    private static final double EXPANSIONSFACTOR = 0.06;

    /**
     * Default for the FontBBox.
     */
    private static final int[] FONTBBOX = {0, 0, 0, 0};

    /**
     * Default for the FontMatrix.
     */
    private static final double[] FONTMATRIX = {0.001, 0, 0, 0.001, 0, 0};

    /**
     * The cff table.
     */
    private OtfTableCFF cff;

    /**
     * The charset.
     */
    private T2TDOCharset charset;

    /**
     * The copyright string.
     */
    private String copyright = "";

    /**
     * The familyname.
     */
    private String familyname = "";

    /**
     * The font number in the cff table.
     */
    private int fontnumber;

    /**
     * The full name.
     */
    private String fullname = "";

    /**
     * is CID.
     */
    private boolean isCID = false;

    /**
     * is fixed pitch
     */
    private boolean isfixedpitch = false;

    /**
     * The italic angle.
     */
    private int italicangle = 0;

    /**
     * The name of the font.
     */
    private String name = "";

    /**
     * The notice.
     */
    private String notice = "";

    /**
     * The number of glyphs.
     */
    private int numberOfGlyphs = 0;

    /**
     * The paint type.
     */
    private int painttype = 0;

    /**
     * The postscript name.
     */
    private String postscript = "";

    /**
     * The stroke width.
     */
    private int strokewidth = 0;

    /**
     * The map for the top dict index.
     */
    private Map<String, T2Operator> topDictIndex =
            new HashMap<String, T2Operator>();

    /**
     * The underline position. (default: -100)
     */
    private int underlineposition = -100;

    /**
     * The underline thickness. (dedault: 50)
     */
    private int underlinethickness = 50;

    /**
     * The version.
     */
    private String version = "";

    /**
     * The weight.
     */
    private String weight = "";

    /**
     * Creates a new object.
     * 
     * @param fontname The name of the font.
     * @param number
     */
    public CffFont(String fontname, int number) {

        name = fontname;
        fontnumber = number;
    }

    /**
     * Add the default encoding.
     */
    private void addDefaultEncoding() {

        T2Operator op = topDictIndex.get("encoding");
        if (op == null) {
            // get charset
            T2Operator op2 = topDictIndex.get("charset");

            if (op2 != null && op2 instanceof T2TDOCharset) {

                T2TDOCharset charset = (T2TDOCharset) op2;
                T2TDOEncoding enc =
                        new T2TDOEncoding(cff, numberOfGlyphs, charset);
                topDictIndex.put("encoding", enc);
            }
        }
    }

    /**
     * Add an entry to the top dict index.
     * 
     * @param op The T2 operator.
     */
    public void addTopDictIndex(T2Operator op) {

        if (op != null) {
            topDictIndex.put(op.getName().toLowerCase(), op);
        }
    }

    /**
     * Returns the BlueFuzz. (default: 1)
     * 
     * @return Returns the BlueFuzz.
     */
    public int getBlueFuzz() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return 1;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("BlueFuzz");
            if (dictkey == null) {
                return 1;
            }
            if (dictkey instanceof T1DictNumber) {
                T1DictNumber t1 = (T1DictNumber) dictkey;
                return t1.getInteger();
            }
        }

        return 1;
    }

    /**
     * Returns the BlueScale. (default: 0.039625)
     * 
     * @return Returns the BlueScale.
     */
    public double getBlueScale() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return BLUESCALE;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("BlueScale");
            if (dictkey == null) {
                return BLUESCALE;
            }
            if (dictkey instanceof T1DictNumber) {
                T1DictNumber t1 = (T1DictNumber) dictkey;
                return t1.getDouble();
            }
        }

        return BLUESCALE;
    }

    /**
     * Returns the BlueShift. (default: 7)
     * 
     * @return Returns the BlueShift.
     */
    public int getBlueShift() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return 7;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("BlueShift");
            if (dictkey == null) {
                return 7;
            }
            if (dictkey instanceof T1DictNumber) {
                T1DictNumber t1 = (T1DictNumber) dictkey;
                return t1.getInteger();
            }
        }

        return 7;
    }

    /**
     * Returns the BlueValues. (default: -)
     * 
     * @return Returns the BlueValues.
     */
    public int[] getBlueValues() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return null;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("BlueValues");
            if (dictkey == null) {
                return null;
            }
            Integer[] iarr = (Integer[]) dictkey.getValue();
            if (iarr == null) {
                return null;
            }
            int[] ii = new int[iarr.length];
            for (int i = 0; i < ii.length; i++) {
                ii[i] = iarr[i].intValue();
            }
            return ii;
        }

        return null;
    }

    /**
     * Getter for charset.
     * 
     * @return Returns the charset.
     */
    public T2TDOCharset getCharset() {

        return charset;
    }

    /**
     * Returns the CharstringType. (default: 2)
     * 
     * @return Returns the CharstringType.
     */
    public int getCharstringType() {

        T2Operator op = topDictIndex.get("charstringtype");
        if (op == null) {
            return 2;
        }
        if (op instanceof T2TDOCharStringType) {
            T2TDOCharStringType val = (T2TDOCharStringType) op;
            return val.getInteger();
        }

        return 2;

    }

    /**
     * Getter for copyright.
     * 
     * @return Returns the copyright.
     */
    public String getCopyright() {

        return copyright;
    }

    /**
     * Returns the defaultWidthX. (default: 0)
     * 
     * @return Returns the defaultWidthX.
     */
    public int getDefaultWidthX() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return 0;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("defaultWidthX");
            if (dictkey == null) {
                return 0;
            }
            if (dictkey instanceof T1DictNumber) {
                T1DictNumber t1 = (T1DictNumber) dictkey;
                return t1.getInteger();
            }
        }

        return 0;
    }

    /**
     * Return the name of the encoding.
     * 
     * @return Return the name of the encoding.
     */
    public String getEncoding() {

        // exists an encoding?
        T2Operator op = topDictIndex.get("encoding");
        if (op != null) {
            T2TDOEncoding enc = (T2TDOEncoding) op;
            return enc.getEncodingName();
        }
        return "unknown";
    }

    /**
     * Returns the ExpansionFactor. (default: 0.06)
     * 
     * @return Returns the ExpansionFactor.
     */
    public double getExpansionFactor() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return EXPANSIONSFACTOR;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("ExpansionFactor");
            if (dictkey == null) {
                return EXPANSIONSFACTOR;
            }
            if (dictkey instanceof T1DictNumber) {
                T1DictNumber t1 = (T1DictNumber) dictkey;
                return t1.getDouble();
            }
        }

        return EXPANSIONSFACTOR;
    }

    /**
     * Getter for familyname.
     * 
     * @return Returns the familyname.
     */
    public String getFamilyName() {

        return familyname;
    }

    /**
     * Returns the FontBBox. (default: 0, 0, 0, 0)
     * 
     * @return Returns the FontBBox.
     */
    public int[] getFontBBox() {

        T2Operator op = topDictIndex.get("fontbbox");
        if (op == null) {
            return FONTBBOX;
        }
        if (op instanceof T2TDOFontBBox) {
            T2TDOFontBBox val = (T2TDOFontBBox) op;
            T2Number[] t2 = (T2Number[]) val.getValue();
            int[] darr = new int[t2.length];
            for (int i = 0; i < t2.length; i++) {
                darr[i] = t2[i].getInteger();
            }
            return darr;
        }

        return FONTBBOX;
    }

    /**
     * Returns the FontMatrix. (default: 0.001 0 0 0.001 0 0)
     * 
     * @return Returns the FontMatrix.
     */
    public double[] getFontMatrix() {

        T2Operator op = topDictIndex.get("fontmatrix");
        if (op == null) {
            return FONTMATRIX;
        }
        if (op instanceof T2TDOFontMatrix) {
            T2TDOFontMatrix val = (T2TDOFontMatrix) op;
            T2Number[] t2 = (T2Number[]) val.getValue();
            double[] darr = new double[t2.length];
            for (int i = 0; i < t2.length; i++) {
                darr[i] = t2[i].getDouble();
            }
            return darr;
        }

        return FONTMATRIX;
    }

    /**
     * Getter for fontnumber.
     * 
     * @return Returns the fontnumber.
     */
    public int getFontNumber() {

        return fontnumber;
    }

    /**
     * Returns the ForceBold. (default: false)
     * 
     * @return Returns the ForceBold.
     */
    public boolean getForceBold() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return false;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("ForceBold");
            if (dictkey == null) {
                return false;
            }
            if (dictkey instanceof T1DictBoolean) {
                T1DictBoolean t1 = (T1DictBoolean) dictkey;
                return t1.isValue();
            }
        }

        return false;
    }

    /**
     * Getter for fullname.
     * 
     * @return Returns the fullname.
     */
    public String getFullName() {

        return fullname;
    }

    /**
     * Returns the initialRandomSeed. (default: 0)
     * 
     * @return Returns the initialRandomSeed.
     */
    public int getInitialRandomSeed() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return 0;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("initialRandomSeed");
            if (dictkey == null) {
                return 0;
            }
            if (dictkey instanceof T1DictNumber) {
                T1DictNumber t1 = (T1DictNumber) dictkey;
                return t1.getInteger();
            }
        }

        return 0;
    }

    /**
     * Getter for italicangle.
     * 
     * @return Returns the italicangle.
     */
    public int getItalicAngle() {

        return italicangle;
    }

    /**
     * Returns the LanguageGroup. (default: 0)
     * 
     * @return Returns the LanguageGroup.
     */
    public int getLanguageGroup() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return 0;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("LanguageGroup");
            if (dictkey == null) {
                return 0;
            }
            if (dictkey instanceof T1DictNumber) {
                T1DictNumber t1 = (T1DictNumber) dictkey;
                return t1.getInteger();
            }
        }

        return 0;
    }

    /**
     * Getter for name.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Returns the nominalWidthX. (default: 0)
     * 
     * @return Returns the nominalWidthX.
     */
    public int getNominalWidthX() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return 0;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("nominalWidthX");
            if (dictkey == null) {
                return 0;
            }
            if (dictkey instanceof T1DictNumber) {
                T1DictNumber t1 = (T1DictNumber) dictkey;
                return t1.getInteger();
            }
        }

        return 0;
    }

    /**
     * Getter for notice.
     * 
     * @return Returns the notice.
     */
    public String getNotice() {

        return notice;
    }

    /**
     * Getter for numberOfGlyphs.
     * 
     * @return Returns the numberOfGlyphs.
     */
    public int getNumberOfGlyphs() {

        return numberOfGlyphs;
    }

    /**
     * Getter for painttype.
     * 
     * @return Returns the painttype.
     */
    public int getPaintType() {

        return painttype;
    }

    /**
     * Getter for postscript.
     * 
     * @return Returns the postscript.
     */
    public String getPostscript() {

        return postscript;
    }

    /**
     * Returns the StdHW. (default: -)
     * 
     * @return Returns the StdHW.
     */
    public int getStdHW() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return 0;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("StdHW");
            if (dictkey == null) {
                return 0;
            }
            if (dictkey instanceof T1DictNumber) {
                T1DictNumber t1 = (T1DictNumber) dictkey;
                return t1.getInteger();
            }
        }

        return 0;
    }

    /**
     * Returns the StdVW. (default: -)
     * 
     * @return Returns the StdVW.
     */
    public int getStdVW() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return 0;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("StdVW");
            if (dictkey == null) {
                return 0;
            }
            if (dictkey instanceof T1DictNumber) {
                T1DictNumber t1 = (T1DictNumber) dictkey;
                return t1.getInteger();
            }
        }

        return 0;
    }

    /**
     * Returns the StemSnapH. (default: -)
     * 
     * @return Returns the StemSnapH.
     */
    public int[] getStemSnapH() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return null;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("StemSnapH");
            if (dictkey == null) {
                return null;
            }
            Integer[] iarr = (Integer[]) dictkey.getValue();
            if (iarr == null) {
                return null;
            }
            int[] ii = new int[iarr.length];
            for (int i = 0; i < ii.length; i++) {
                ii[i] = iarr[i].intValue();
            }
            return ii;
        }

        return null;
    }

    /**
     * Returns the StemSnapV. (default: -)
     * 
     * @return Returns the StemSnapV.
     */
    public int[] getStemSnapV() {

        T2Operator op = topDictIndex.get("private");
        if (op == null) {
            return null;
        }
        if (op instanceof T2TDOPrivate) {
            T2TDOPrivate val = (T2TDOPrivate) op;

            T1DictKey dictkey = val.getT1DictKey("StemSnapV");
            if (dictkey == null) {
                return null;
            }
            Integer[] iarr = (Integer[]) dictkey.getValue();
            if (iarr == null) {
                return null;
            }
            int[] ii = new int[iarr.length];
            for (int i = 0; i < ii.length; i++) {
                ii[i] = iarr[i].intValue();
            }
            return ii;
        }

        return null;
    }

    /**
     * Getter for strokewidth.
     * 
     * @return Returns the strokewidth.
     */
    public int getStrokewidth() {

        return strokewidth;
    }

    /**
     * Returns the top dict index entry, if no entry found, <code>null</code>
     * is returned.
     * 
     * @param name The name of the entry.
     * @return Returns the top dict index entry.
     */
    public T2Operator getTopDictIndex(String name) {

        if (name == null) {
            return null;
        }
        return topDictIndex.get(name.toLowerCase());
    }

    /**
     * Getter for underlineposition.
     * 
     * @return Returns the underlineposition.
     */
    public int getUnderlinePosition() {

        return underlineposition;
    }

    /**
     * Getter for underlinethickness.
     * 
     * @return Returns the underlinethickness.
     */
    public int getUnderlineThickness() {

        return underlinethickness;
    }

    /**
     * Getter for version.
     * 
     * @return Returns the version.
     */
    public String getVersion() {

        return version;
    }

    /**
     * Getter for weight.
     * 
     * @return Returns the weight.
     */
    public String getWeight() {

        return weight;
    }

    /**
     * Initialize the font.
     * 
     * @param rar The input.
     * @param cff The cff table.
     * @param baseoffset The baseoffset of the table.
     * @throws IOException if a io-error occurred.
     */
    public void init(RandomAccessR rar, OtfTableCFF cff, int baseoffset)
            throws IOException {

        this.cff = cff;
        numberOfGlyphs = cff.getNumGlyphs();

        Iterator<String> it = topDictIndex.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            T2Operator op = topDictIndex.get(key);
            op.init(rar, cff, baseoffset);

            int id = op.getID();

            switch (id) {
                case T2TopDICTOperator.TYPE_VERSION:
                    version = op.getValue().toString();
                    break;
                case T2TopDICTOperator.TYPE_NOTICE:
                    notice = op.getValue().toString();
                    break;
                case T2TopDICTOperator.TYPE_FULLNAME:
                    fullname = op.getValue().toString();
                    break;
                case T2TopDICTOperator.TYPE_FAMILYNAME:
                    familyname = op.getValue().toString();
                    break;
                case T2TopDICTOperator.TYPE_WEIGHT:
                    weight = op.getValue().toString();
                    break;
                case T2TopDICTOperator.TYPE_FONTBBOX:
                    // see getFontBBox()
                    break;
                case T2TopDICTOperator.TYPE_COPYRIGHT:
                    copyright = op.getValue().toString();
                    break;
                case T2TopDICTOperator.TYPE_ISFIXEDPITCH:
                    isfixedpitch = ((T2TDOisFixedPitch) op).isValue();
                    break;
                case T2TopDICTOperator.TYPE_ITALICANGLE:
                    italicangle = ((T2TDOItalicAngle) op).getInteger();
                    break;
                case T2TopDICTOperator.TYPE_UNDERLINEPOSITION:
                    underlineposition =
                            ((T2TDOUnderlinePosition) op).getInteger();
                    break;
                case T2TopDICTOperator.TYPE_UNDERLINETHICKNESS:
                    underlinethickness =
                            ((T2TDOUnderlineThickness) op).getInteger();
                    break;
                case T2TopDICTOperator.TYPE_PAINTTYPE:
                    painttype = ((T2TDOPaintType) op).getInteger();
                    break;
                case T2TopDICTOperator.TYPE_CHARSTRINGTYPE:
                    // see getCharStringType()
                    break;
                case T2TopDICTOperator.TYPE_FONTMATRIX:
                    break;
                case T2TopDICTOperator.TYPE_STROKEWIDTH:
                    strokewidth = ((T2TDOStrokeWidth) op).getInteger();
                    break;
                case T2TopDICTOperator.TYPE_SYNTHETICBASE:
                    break;
                case T2TopDICTOperator.TYPE_POSTSCRIPT:
                    postscript = op.getValue().toString();
                    break;
                case T2TopDICTOperator.TYPE_UNIQUEID:
                    break;
                case T2TopDICTOperator.TYPE_XUID:
                    break;
                case T2TopDICTOperator.TYPE_CHARSET:
                    charset = (T2TDOCharset) op;
                    break;
                case T2TopDICTOperator.TYPE_ENCODING:
                    break;
                case T2TopDICTOperator.TYPE_CHARSTRINGS:
                    break;
                case T2TopDICTOperator.TYPE_PRIVATE:
                    break;
                case T2TopDICTOperator.TYPE_ROS:
                    isCID = true;
                    break;
                case T2TopDICTOperator.TYPE_CIDFONTVERSION:
                    isCID = true;
                    break;
                case T2TopDICTOperator.TYPE_CIDFONTREVISION:
                    isCID = true;
                    break;
                case T2TopDICTOperator.TYPE_CIDFONTTYPE:
                    isCID = true;
                    break;
                case T2TopDICTOperator.TYPE_CIDCOUNT:
                    isCID = true;
                    break;
                case T2TopDICTOperator.TYPE_UIDBASE:
                    isCID = true;
                    break;
                case T2TopDICTOperator.TYPE_FDARRAY:
                    isCID = true;
                    break;
                case T2TopDICTOperator.TYPE_FDSELECT:
                    isCID = true;
                    break;
                case T2TopDICTOperator.TYPE_FONTNAME:
                    isCID = true;
                    break;
                default:
                    break;
            }

        }

        addDefaultEncoding();

    }

    /**
     * Getter for isCID.
     * 
     * @return Returns the isCID.
     */
    public boolean isCid() {

        return isCID;
    }

    /**
     * Getter for isfixedpitch.
     * 
     * @return Returns the isfixedpitch.
     */
    public boolean isIsFixedPitch() {

        return isfixedpitch;
    }

    @Override
    public String toString() {

        return name;
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("font");
        writer.writeAttribute("number", fontnumber);
        writer.writeAttribute("name", name);
        writer.writeAttribute("CID", isCID);

        // topdict index
        writer.writeStartElement("topdictindex");
        Iterator<String> it = topDictIndex.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            T2Operator op = topDictIndex.get(key);
            op.writeXML(writer);
        }
        writer.writeEndElement();

        writer.writeEndElement();
    }

}
