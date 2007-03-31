/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.font.format.xtf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.extex.font.format.xtf.cff.T1DictBoolean;
import org.extex.font.format.xtf.cff.T1DictKey;
import org.extex.font.format.xtf.cff.T1DictNumber;
import org.extex.font.format.xtf.cff.T2CharString;
import org.extex.font.format.xtf.cff.T2Number;
import org.extex.font.format.xtf.cff.T2Operator;
import org.extex.font.format.xtf.cff.T2StandardStrings;
import org.extex.font.format.xtf.cff.T2TDOBoolean;
import org.extex.font.format.xtf.cff.T2TDOCharStringType;
import org.extex.font.format.xtf.cff.T2TDOCharset;
import org.extex.font.format.xtf.cff.T2TDOEncoding;
import org.extex.font.format.xtf.cff.T2TDOFontBBox;
import org.extex.font.format.xtf.cff.T2TDOFontMatrix;
import org.extex.font.format.xtf.cff.T2TDOItalicAngle;
import org.extex.font.format.xtf.cff.T2TDOPaintType;
import org.extex.font.format.xtf.cff.T2TDOPrivate;
import org.extex.font.format.xtf.cff.T2TDOStrokeWidth;
import org.extex.font.format.xtf.cff.T2TDOUnderlinePosition;
import org.extex.font.format.xtf.cff.T2TDOUnderlineThickness;
import org.extex.util.XMLWriterConvertible;
import org.extex.util.file.random.RandomAccessInputArray;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * The 'CFF' - PostScript font program.
 * 
 * <p>
 * This table contains a compact representation of a PostScript Type 1, or
 * CIDFont and is structured according to <a
 * href="http://partners.adobe.com/asn/developer/pdfs/tn/5176.CFF.pdf"> Adobe
 * Technical Note #5176: " The Compact BaseFont Format Specification"</a> and
 * <a href="http://partners.adobe.com/asn/developer/pdfs/tn/5177.Type2.pdf">
 * Adobe Technical Note #5177: "Type 2 Charstring Format"</a>.
 * </p>
 * 
 * <p>
 * CFF Data Types
 * </p>
 * <table border="1"> <thead>
 * <tr>
 * <td>Name</td>
 * <td>Range</td>
 * <td>Description</td>
 * </tr>
 * </thead>
 * <tr>
 * <td>Card8</td>
 * <td>0 255</td>
 * <td> 1-byte unsigned number</td>
 * </tr>
 * <tr>
 * <td>Card16</td>
 * <td>0 65535</td>
 * <td> 2-byte unsigned number</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>varies</td>
 * <td> 1, 2, 3, or 4 byte offset (specified by OffSize field)</td>
 * </tr>
 * <tr>
 * <td>OffSize</td>
 * <td>1 - 4</td>
 * <td> 1-byte unsigned number specifies the size of an Offset field or fields</td>
 * </tr>
 * <tr>
 * <td>SID</td>
 * <td>0 - 64999</td>
 * <td> 2-byte string identifier</td>
 * </tr>
 * </table>
 * 
 * <p>
 * CFF Data Layout
 * </p>
 * 
 * <table border="1"> <thead>
 * <tr>
 * <td><b>Entry</b></td>
 * <td><b>Comments</b></td>
 * </tr>
 * </thead>
 * <tr>
 * <td>Header</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>Name INDEX</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>Top DICT INDEX</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>String INDEX</td>
 * <td>->/td></tr>
 * <tr>
 * <td>Global Subr INDEX</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>Encodings</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>Charsets</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>FDSelect</td>
 * <td>CIDFonts only</td>
 * </tr>
 * <tr>
 * <td>CharStrings INDEX</td>
 * <td>per-font</td>
 * </tr>
 * <tr>
 * <td>BaseFont DICT INDEX</td>
 * <td>per-font, CIDFonts only</td>
 * </tr>
 * <tr>
 * <td>Private DICT</td>
 * <td>per-font</td>
 * </tr>
 * <tr>
 * <td>Local Subr INDEX</td>
 * <td>per-font or per-Private DICT for CIDFonts</td>
 * </tr>
 * <tr>
 * <td>Copyright and Trademark Notices</td>
 * <td>-/td></tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class OtfTableCFF extends AbstractXtfTable
        implements
            XtfTable,
            XMLWriterConvertible {

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
     * The instance itself.
     */
    private OtfTableCFF cff;

    /**
     * header size
     */
    private int hdrSize;

    /**
     * The named index.
     */
    private List namedIndex;

    /**
     * offset size
     */
    private int offSize;

    /**
     * The string index.
     */
    private List stringIndex;

    /**
     * The top dict index.
     */
    private Map topDictIndex;

    /**
     * Version major
     */
    private int versionmajor;

    /**
     * Version minor
     */
    private int versionminor;

    /**
     * Create a new object
     * 
     * @param tablemap the tablemap
     * @param de entry
     * @param rar input
     * @throws IOException if an IO-error occurs
     */
    OtfTableCFF(XtfTableMap tablemap, XtfTableDirectory.Entry de,
            RandomAccessR rar) throws IOException {

        super(tablemap);
        cff = this;
        int baseoffset = de.getOffset();
        rar.seek(baseoffset);

        // header
        readHeader(rar);

        // index
        readNameIndex(de, rar);
        readTopDictIndex(rar);
        readStringIndex(rar);

        // initialize the T2Operators
        Iterator it = topDictIndex.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            T2Operator op = (T2Operator) topDictIndex.get(key);
            op.init(rar, cff, baseoffset);
        }

        // add default values
        // encoding
        // exists an encoding?
        addDefaultEncoding();

        // Global Subr INDEX

        // incomplete
    }

    /**
     * Add the default encoding.
     */
    private void addDefaultEncoding() {

        T2Operator op = (T2Operator) topDictIndex.get("encoding");
        if (op == null) {
            // get charset
            T2Operator op2 = (T2Operator) topDictIndex.get("charset");

            if (op2 != null && op2 instanceof T2TDOCharset) {

                T2TDOCharset charset = (T2TDOCharset) op2;
                T2TDOEncoding enc =
                        new T2TDOEncoding(this, getNumGlyphs(), charset
                            .getSid());
                topDictIndex.put("encoding", enc);
            }
        }
    }

    /**
     * Convert the array to a string.
     * 
     * @param data the data-array
     * @return Returns the String.
     */
    private String convertArrayToString(byte[] data) {

        StringBuffer buf = new StringBuffer(data.length);

        for (int i = 0; i < data.length; i++) {
            buf.append((char) data[i]);
        }

        return buf.toString();
    }

    /**
     * Returns the BlueFuzz. (default: 1)
     * 
     * @return Returns the BlueFuzz.
     */
    public int getBlueFuzz() {

        T2Operator op = (T2Operator) topDictIndex.get("private");
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

        T2Operator op = (T2Operator) topDictIndex.get("private");
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

        T2Operator op = (T2Operator) topDictIndex.get("private");
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

        T2Operator op = (T2Operator) topDictIndex.get("private");
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
     * Returns the CharstringType. (default: 2)
     * 
     * @return Returns the CharstringType.
     */
    public int getCharstringType() {

        T2Operator op = (T2Operator) topDictIndex.get("charstringtype");
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
     * Returns the defaultWidthX. (default: 0)
     * 
     * @return Returns the defaultWidthX.
     */
    public int getDefaultWidthX() {

        T2Operator op = (T2Operator) topDictIndex.get("private");
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
        T2Operator op = (T2Operator) topDictIndex.get("encoding");
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

        T2Operator op = (T2Operator) topDictIndex.get("private");
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
     * Returns the FontBBox. (default: 0, 0, 0, 0)
     * 
     * @return Returns the FontBBox.
     */
    public int[] getFontBBox() {

        T2Operator op = (T2Operator) topDictIndex.get("fontbbox");
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

        T2Operator op = (T2Operator) topDictIndex.get("fontmatrix");
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
     * Returns the ForceBold. (default: false)
     * 
     * @return Returns the ForceBold.
     */
    public boolean getForceBold() {

        T2Operator op = (T2Operator) topDictIndex.get("private");
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
     * Returns the hdrSize.
     * 
     * @return Returns the hdrSize.
     */
    public int getHdrSize() {

        return hdrSize;
    }

    /**
     * Returns the initialRandomSeed. (default: 0)
     * 
     * @return Returns the initialRandomSeed.
     */
    public int getInitialRandomSeed() {

        T2Operator op = (T2Operator) topDictIndex.get("private");
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
     * Returns the ItalicAngle. (default: 0)
     * 
     * @return Returns the ItalicAngle.
     */
    public int getItalicAngle() {

        T2Operator op = (T2Operator) topDictIndex.get("ItalicAngle");
        if (op == null) {
            return 0;
        }
        if (op instanceof T2TDOItalicAngle) {
            T2TDOItalicAngle val = (T2TDOItalicAngle) op;
            return val.getInteger();
        }

        return 0;
    }

    /**
     * Returns the LanguageGroup. (default: 0)
     * 
     * @return Returns the LanguageGroup.
     */
    public int getLanguageGroup() {

        T2Operator op = (T2Operator) topDictIndex.get("private");
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
     * Getter for namedIndex.
     * 
     * @return Returns the namedIndex.
     */
    public List getNamedIndex() {

        return namedIndex;
    }

    /**
     * Returns the nominalWidthX. (default: 0)
     * 
     * @return Returns the nominalWidthX.
     */
    public int getNominalWidthX() {

        T2Operator op = (T2Operator) topDictIndex.get("private");
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
     * Returns the number of glyphs
     * 
     * @return Returns the number of glyphs
     */
    public int getNumGlyphs() {

        XtfTableMap map = getTableMap();
        TtfTableMAXP maxp = (TtfTableMAXP) map.get(XtfReader.MAXP);

        if (maxp != null) {
            return maxp.getNumGlyphs();
        }

        return 0;
    }

    /**
     * Returns the painttype. (default: 0)
     * 
     * @return Returns the painttype.
     */
    public int getPaintType() {

        T2Operator op = (T2Operator) topDictIndex.get("painttype");
        if (op == null) {
            return 0;
        }
        if (op instanceof T2TDOPaintType) {
            T2TDOPaintType val = (T2TDOPaintType) op;
            return val.getInteger();
        }

        return 0;
    }

    /**
     * @see org.extex.font.format.xtf.XtfTable#getShortcut()
     */
    public String getShortcut() {

        return "cff";
    }

    /**
     * Returns the StdHW. (default: -)
     * 
     * @return Returns the StdHW.
     */
    public int getStdHW() {

        T2Operator op = (T2Operator) topDictIndex.get("private");
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

        T2Operator op = (T2Operator) topDictIndex.get("private");
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

        T2Operator op = (T2Operator) topDictIndex.get("private");
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

        T2Operator op = (T2Operator) topDictIndex.get("private");
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
     * Returns the String for the SID.
     * 
     * First, test if SID is in standard range then fetch from internal table,
     * otherwise, fetch string from the String INDEX using a value of
     * (SID-nStdStrings) as the index.
     * 
     * @param sid the SID for the string.
     * @return Returns the String or <code>null</code>, if not found.
     */
    public String getStringIndex(int sid) {

        int highestSID = T2StandardStrings.getHighestSID();
        if (sid <= highestSID) {
            return T2StandardStrings.getString(sid);
        }
        if (sid - highestSID - 1 < stringIndex.size()) {
            return (String) stringIndex.get(sid - highestSID - 1);
        }
        return null;
    }

    /**
     * Returns the StrokeWidth. (default: 0)
     * 
     * @return Returns the StrokeWidth.
     */
    public int getStrokeWidth() {

        T2Operator op = (T2Operator) topDictIndex.get("strokewidth");
        if (op == null) {
            return 0;
        }
        if (op instanceof T2TDOStrokeWidth) {
            T2TDOStrokeWidth val = (T2TDOStrokeWidth) op;
            return val.getInteger();
        }

        return 0;

    }

    /**
     * Returns the type 2 operator for the key.
     * 
     * @param key The key.
     * @return Returns the type 2 operator for the key.
     */
    public T2Operator getTopDictIndex(String key) {

        return (T2Operator) topDictIndex.get(key);
    }

    // ---------------------------------------------------
    // ---------------------------------------------------
    // ---------------------------------------------------
    // ---------------------------------------------------
    // ---------------------------------------------------

    /**
     * Get the table type, as a table directory value.
     * 
     * @return Returns the table type
     */
    public int getType() {

        return XtfReader.CFF;
    }

    /**
     * Returns the UnderlinePosition. (default: -100)
     * 
     * @return Returns the UnderlinePosition.
     */
    public int getUnderlinePosition() {

        T2Operator op = (T2Operator) topDictIndex.get("underlineposition");
        if (op == null) {
            return -100;
        }
        if (op instanceof T2TDOUnderlinePosition) {
            T2TDOUnderlinePosition val = (T2TDOUnderlinePosition) op;
            return val.getInteger();
        }

        return -100;
    }

    /**
     * Returns the UnderlineThickness. (deault: 50)
     * 
     * @return Returns the UnderlineThickness.
     */
    public int getUnderlineThicknessn() {

        T2Operator op = (T2Operator) topDictIndex.get("underlinethickness");
        if (op == null) {
            return 50;
        }
        if (op instanceof T2TDOUnderlineThickness) {
            T2TDOUnderlineThickness val = (T2TDOUnderlineThickness) op;
            return val.getInteger();
        }

        return 50;
    }

    /**
     * Returns the versionmajor.
     * 
     * @return Returns the versionmajor.
     */
    public int getVersionmajor() {

        return versionmajor;
    }

    /**
     * Returns the versionminor.
     * 
     * @return Returns the versionminor.
     */
    public int getVersionminor() {

        return versionminor;
    }

    /**
     * Returns the value of fixed pitch. (default: false)
     * 
     * @return Returns the value of fixed pitch.
     */
    public boolean isFixedPitch() {

        T2Operator op = (T2Operator) topDictIndex.get("isfixedpitch");
        if (op == null) {
            return false;
        }
        if (op instanceof T2TDOBoolean) {
            T2TDOBoolean bool = (T2TDOBoolean) op;
            return bool.isValue();
        }
        return false;
    }

    /**
     * Map the glyph position to the name of the glyph.
     * 
     * @param glyphpos The glyph position
     * @return Returns the name of the glpyh or <code>null</code>, if not
     *         found.
     */
    public String mapGlyphPosToGlyphName(int glyphpos) {

        // get charset
        T2Operator op = (T2Operator) topDictIndex.get("charset");
        if (op != null && op instanceof T2TDOCharset) {

            T2TDOCharset val = (T2TDOCharset) op;
            int sid = val.getSid(glyphpos);
            // get the name
            String name = getStringIndex(sid);
            return name;
        }

        return null;
    }

    /**
     * An INDEX is an array of variable-sized objects.
     * <p>
     * It comprises a header, an offset array, and object data. The offset array
     * specifies offsets within the object data. An object is retrieved by
     * indexing the offset array and fetching the object at the specified
     * offset. The object's length can be determined by subtracting its offset
     * from the next offset in the offset array. An additional offset is added
     * at the end of the offset array so the length of the last object may be
     * determined.
     * </p>
     * 
     * <table border="1"> <thead>
     * <tr>
     * <td><b>Type</b></td>
     * <td><b>Name</b></td>
     * <td><b> Description</b></td>
     * </tr>
     * </thead>
     * <tr>
     * <td>Card16</td>
     * <td>count</td>
     * <td> Number of objects stored in INDEX. An empty INDEX is represented by
     * a count field with a 0 value and no additional fields. Thus, the total
     * size of an empty INDEX is 2 bytes.</td>
     * </tr>
     * <tr>
     * <td>OffSize</td>
     * <td>offSize</td>
     * <td> Offset array element size</td>
     * </tr>
     * <tr>
     * <td>Offset</td>
     * <td>offset [count+1]</td>
     * <td> Offset array (from byte preceding object data). Offsets in the
     * offset array are relative to the byte that precedes the object data.
     * Therefore the first element of the offset array is always 1. (This
     * ensures that every object has a corresponding offset which is always
     * nonzero and permits the efficient implementation of dynamic object
     * loading.) </td>
     * </tr>
     * <tr>
     * <td>Card8</td>
     * <td>data [&lt;varies&gt;]</td>
     * <td> Object data</td>
     * </tr>
     * </table>
     * 
     * Offsets in the offset array are relative to the byte that precedes the
     * object data. Therefore the first element of the offset array is always 1.
     * (This ensures that every object has a corresponding offset which is
     * always nonzero and permits the efficient implementation of dynamic object
     * loading.)
     * 
     * An empty INDEX is represented by a count field with a 0 value and no
     * additional fields. Thus, the total size of an empty INDEX is 2 bytes.
     * 
     * @param start the start offset
     * @param end the end offset
     * @param rar the input
     * @return Returns the data
     * @throws IOException if an IO-error occurs
     */
    private byte[] readDataFromIndex(int start, int end, RandomAccessR rar)
            throws IOException {

        byte[] data = new byte[end - start];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) rar.readUnsignedByte();
        }
        return data;
    }

    /**
     * Read the Header. TODO: create html-table Type Name Description Card8
     * major Format major version (starting at 1) Card8 minor Format minor
     * version (starting at 0) Card8 hdrSize Header size (bytes) OffSize offSize
     * Absolute offset (0) size
     * 
     * @param rar The Input.
     * @throws IOException if an IO-error occurs
     */
    private void readHeader(RandomAccessR rar) throws IOException {

        versionmajor = rar.readUnsignedByte();
        versionminor = rar.readUnsignedByte();
        hdrSize = rar.readUnsignedByte();
        offSize = rar.readUnsignedByte();
    }

    /**
     * Read the name index.
     * 
     * This contains the PostScript language names (FontName or CIDFontName) of
     * all the fonts in the FontSet stored in an INDEX structure. The font names
     * are sorted, thereby permitting a binary search to be performed when
     * locating a specific font within a FontSet. The sort order is based on
     * character codes treated as 8-bit unsigned integers. A given font name
     * precedes another font name having the first name as its prefix. There
     * must be at least one entry in this INDEX, i.e. the FontSet must contain
     * at least one font.
     * 
     * @param de The entry.
     * @param rar The Input.
     * @throws IOException if an io-error occuured.
     */
    private void readNameIndex(XtfTableDirectory.Entry de, RandomAccessR rar)
            throws IOException {

        int offset = de.getOffset() + hdrSize;
        namedIndex = new ArrayList();

        if (offset > 0) {
            rar.seek(offset);

            int count = rar.readUnsignedShort();
            if (count > 0) {
                int[] offsetarray = readOffsets(rar, count);

                // get data
                for (int i = 0; i < count; i++) {
                    byte[] data =
                            readDataFromIndex(offsetarray[i],
                                offsetarray[i + 1], rar);
                    namedIndex.add(convertArrayToString(data));
                }
            }

        }
    }

    /**
     * Read the offset (see offsetsize).
     * 
     * @param os the offsetsize
     * @param rar the input
     * @return Returns the offset
     * @throws IOException if an IO-error occurs
     */
    private int readOffsetFromIndex(int os, RandomAccessR rar)
            throws IOException {

        int offset = 0;
        for (int i = 0; i < os; i++) {
            int b = rar.readUnsignedByte();
            offset = offset << XtfConstants.SHIFT8;
            offset += b;
        }
        return offset;
    }

    /**
     * Read the offsets.
     * 
     * @param rar The input.
     * @param count The count.
     * @return Returns the offsets.
     * @throws IOException if a IO-error occurred.
     */
    private int[] readOffsets(RandomAccessR rar, int count) throws IOException {

        int ioffSize = rar.readUnsignedByte();
        int[] offsetarray = new int[count + 1];

        // read all offsets
        for (int offs = 0; offs < offsetarray.length; offs++) {
            offsetarray[offs] = readOffsetFromIndex(ioffSize, rar);
        }
        return offsetarray;
    }

    /**
     * Read the string index.
     * 
     * <p>
     * All the strings, with the exception of the FontName and CIDFontName
     * strings which appear in the Name INDEX, used by different fonts within
     * the FontSet are collected together into an INDEX structure and are
     * referenced by a 2-byte unsigned number called a string identifier or SID.
     * Only unique strings are stored in the table thereby removing duplication
     * across fonts. Further space saving is obtained by allocating commonly
     * occurring strings to predefined SIDs. These strings, known as the
     * standard strings, describe all the names used in the ISOAdobe and Expert
     * character sets along with a few other strings common to Type 1 fonts.
     * </p>
     * <p>
     * The client program will contain an array of standard strings with
     * nStdStrings elements. Thus, the standard strings take SIDs in the range 0
     * to (nStdStrings 1). The first string in the String INDEX corresponds to
     * the SID whose value is equal to nStdStrings, the first non-standard
     * string, and so on. When the client needs to determine the string that
     * corresponds to a particular SID it performs the following: test if SID is
     * in standard range then fetch from internal table, otherwise, fetch string
     * from the String INDEX using a value of (SID nStdStrings) as the index. An
     * SID is defined as a 2-byte unsigned number but only takes values in the
     * range 0 64999, inclusive. SID values 65000 and above are available for
     * implementation use. A FontSet with zero non-standard strings is
     * represented by an empty INDEX.
     * </p>
     * 
     * @param rar The input.
     * @throws IOException if an IO-error occurred.
     */
    private void readStringIndex(RandomAccessR rar) throws IOException {

        stringIndex = new ArrayList();

        int count = rar.readUnsignedShort();
        if (count > 0) {
            int[] offsetarray = readOffsets(rar, count);

            // get data
            for (int i = 0; i < count; i++) {
                byte[] data =
                        readDataFromIndex(offsetarray[i], offsetarray[i + 1],
                            rar);
                stringIndex.add(convertArrayToString(data));
            }
        }
    }

    /**
     * Read the top dict index.
     * 
     * @param rar The input.
     * @throws IOException if an IO-error occurred.
     */
    private void readTopDictIndex(RandomAccessR rar) throws IOException {

        topDictIndex = new HashMap();

        int count = rar.readUnsignedShort();
        if (count > 0) {
            int[] offsetarray = readOffsets(rar, count);

            // get data
            for (int i = 0; i < count; i++) {
                byte[] data =
                        readDataFromIndex(offsetarray[i], offsetarray[i + 1],
                            rar);
                RandomAccessInputArray arar = new RandomAccessInputArray(data);
                try {
                    // read until end of input -> IOException
                    while (true) {
                        T2Operator op = T2CharString.readTopDICTOperator(arar);
                        topDictIndex.put(op.getName().toLowerCase(), op);
                    }
                } catch (IOException e) {
                    // EOF
                }
            }
        }
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writeStartElement(writer);
        writer.writeAttribute("version", String.valueOf(versionmajor) + "."
                + String.valueOf(versionminor));
        writer.writeAttribute("hdrsize", String.valueOf(hdrSize));
        writer.writeAttribute("offsize", String.valueOf(offSize));

        // namedindex
        writer.writeStartElement("nameindex");
        for (int i = 0, n = namedIndex.size(); i < n; i++) {
            writer.writeStartElement("name");
            writer.writeAttribute("id", i);
            writer.writeAttribute("value", namedIndex.get(i));
            writer.writeEndElement();
        }
        writer.writeEndElement();

        // topdict index
        writer.writeStartElement("topdictindex");
        Iterator it = topDictIndex.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            T2Operator op = (T2Operator) topDictIndex.get(key);
            op.writeXML(writer);
        }
        writer.writeEndElement();

        // stringindex
        writer.writeStartElement("stringindex");
        int highestSID = T2StandardStrings.getHighestSID();
        for (int i = 0, n = highestSID + stringIndex.size(); i < n; i++) {
            writer.writeStartElement("string");
            writer.writeAttribute("sid", i);
            writer.writeAttribute("value", getStringIndex(i));
            writer.writeEndElement();
        }
        writer.writeEndElement();

        // ------------
        writer.writeComment("incomplete");
        writer.writeEndElement();
    }

}
