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

package org.extex.font.format.xtf.tables.cff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.util.file.random.RandomAccessR;

/**
 * Top DICT Operator.
 * 
 * <p>
 * Top DICT Operator Entries
 * <p>
 * <table border="1"> <thead>
 * <tr>
 * <td><b>Name</b></td>
 * <td><b>Value</b></td>
 * <td><b>Operand(s)</b></td>
 * <td><b>Default, notes</b></td>
 * </tr>
 * <thead>
 * <tr>
 * <td>Version</td>
 * <td>0</td>
 * <td>SID</td>
 * <td>- , FontInfo</td>
 * </tr>
 * <tr>
 * <td>Notice</td>
 * <td>1</td>
 * <td>SID</td>- , FontInfo</td>
 * </tr>
 * <tr>
 * <td>Copyright</td>
 * <td>12 0</td>
 * <td>SID</td>
 * <td>- , FontInfo</td>
 * </tr>
 * <tr>
 * <td>FullName</td>
 * <td>2</td>
 * <td>SID</td>
 * <td>- , FontInfo</td>
 * </tr>
 * <tr>
 * <td>FamilyName</td>
 * <td>3</td>
 * <td>SID</td>
 * <td>- , FontInfo</td>
 * </tr>
 * <tr>
 * <td>Weight</td>
 * <td>4</td>
 * <td>SID</td>
 * <td>- , FontInfo</td>
 * </tr>
 * <tr>
 * <td>isFixedPitch</td>
 * <td>12 1</td>
 * <td>boolean</td>
 * <td>0 (false), FontInfo</td>
 * </tr>
 * <tr>
 * <td>ItalicAngle</td>
 * <td>12 2</td>
 * <td>number</td>
 * <td>0, FontInfo</td>
 * </tr>
 * <tr>
 * <td>UnderlinePosition</td>
 * <td>12 3</td>
 * <td>number</td>
 * <td>-100, FontInfo</td>
 * </tr>
 * <tr>
 * <td>UnderlineThickness</td>
 * <td>12 4</td>
 * <td>number</td>
 * <td>50, FontInfo</td>
 * </tr>
 * <tr>
 * <td>PaintType</td>
 * <td>12 5</td>
 * <td>number</td>
 * <td>0</td>
 * </tr>
 * <tr>
 * <td>CharstringType</td>
 * <td>12 6</td>
 * <td>number</td>
 * <td>2</td>
 * </tr>
 * <tr>
 * <td>FontMatrix</td>
 * <td>12 7</td>
 * <td>array</td>
 * <td>0.001 0 0 0.001 0 0</td>
 * </tr>
 * <tr>
 * <td>UniqueID</td>
 * <td>13</td>
 * <td>number</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>FontBBox</td>
 * <td>5</td>
 * <td>array</td>
 * <td>0 0 0 0</td>
 * </tr>
 * <tr>
 * <td>StrokeWidth</td>
 * <td>12 8</td>
 * <td>number</td>
 * <td>0</td>
 * </tr>
 * <tr>
 * <td>XUID</td>
 * <td>14</td>
 * <td>array</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>charset</td>
 * <td>15</td>
 * <td>number</td>
 * <td>0, charset offset (0)</td>
 * </tr>
 * <tr>
 * <td>Encoding</td>
 * <td>16</td>
 * <td>number</td>
 * <td>0, encoding offset (0)</td>
 * </tr>
 * <tr>
 * <td>CharStrings</td>
 * <td>17</td>
 * <td>number</td>
 * <td> - , CharStrings offset (0)</td>
 * </tr>
 * <tr>
 * <td>Private</td>
 * <td>18</td>
 * <td>number number</td>
 * <td> - , Private DICT size and offset (0)</td>
 * </tr>
 * <tr>
 * <td>SyntheticBase</td>
 * <td>12 20</td>
 * <td>number</td>
 * <td> - , synthetic base font index</td>
 * </tr>
 * <tr>
 * <td>PostScript</td>
 * <td>12 21</td>
 * <td>SID</td>
 * <td> - , embedded PostScript language code</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public abstract class T2TopDICTOperator extends T2Operator {

    /**
     * CHARSET
     */
    public static final int CFF_CHARSET = 15;

    /**
     * CHARSTRINGS
     */
    public static final int CFF_CHARSTRINGS = 17;

    /**
     * CHARSTRINGTYPE
     */
    public static final int CFF_CHARSTRINGTYPE = 6;

    /**
     * CIDCOUNT
     */
    public static final int CFF_CIDCOUNT = 34;

    /**
     * CIDFONTREVISION
     */
    public static final int CFF_CIDFONTREVISION = 32;

    /**
     * CIDFONTTYPE
     */
    public static final int CFF_CIDFONTTYPE = 33;

    /**
     * CIDFONTVERSION
     */
    public static final int CFF_CIDFONTVERSION = 31;

    /**
     * COPYRIGHT
     */
    public static final int CFF_COPYRIGHT = 0;

    /**
     * ENCODING
     */
    public static final int CFF_ENCODING = 16;

    /**
     * FAMILYNAME
     */
    public static final int CFF_FAMILYNAME = 3;

    /**
     * FDARRAY
     */
    public static final int CFF_FDARRAY = 36;

    /**
     * FDSELECT
     */
    public static final int CFF_FDSELECT = 37;

    /**
     * FONTBBOX
     */
    public static final int CFF_FONTBBOX = 5;

    /**
     * FONTMATRIX
     */
    public static final int CFF_FONTMATRIX = 7;

    /**
     * FONTNAME
     */
    public static final int CFF_FONTNAME = 38;

    /**
     * FULLNAME
     */
    public static final int CFF_FULLNAME = 2;

    /**
     * ISFIXEDPITCH
     */
    public static final int CFF_ISFIXEDPITCH = 1;

    /**
     * ITALICANGLE
     */
    public static final int CFF_ITALICANGLE = 2;

    /**
     * NOTICE
     */
    public static final int CFF_NOTICE = 1;

    /**
     * PAINTTYPE
     */
    public static final int CFF_PAINTTYPE = 5;

    /**
     * POSTSCRIPT
     */
    public static final int CFF_POSTSCRIPT = 21;

    /**
     * PRIVATE
     */
    public static final int CFF_PRIVATE = 18;

    /**
     * ROS
     */
    public static final int CFF_ROS = 30;

    /**
     * STROKEWIDTH
     */
    public static final int CFF_STROKEWIDTH = 8;

    /**
     * SYNTHETICBASE
     */
    public static final int CFF_SYNTHETICBASE = 20;

    /**
     * UIDBASE
     */
    public static final int CFF_UIDBASE = 35;

    /**
     * UNDERLINEPOSITION
     */
    public static final int CFF_UNDERLINEPOSITION = 3;

    /**
     * UNDELINETHICKNESS
     */
    public static final int CFF_UNDERLINETHICKNESS = 4;

    /**
     * UNIQUEID
     */
    public static final int CFF_UNIQUEID = 13;

    /**
     * VERSION
     */
    public static final int CFF_VERSION = 0;

    /**
     * WEIGHT
     */
    public static final int CFF_WEIGHT = 4;

    /**
     * XUID
     */
    public static final int CFF_XUID = 14;

    /**
     * CHARSET
     */
    public static final int TYPE_CHARSET = 19;

    /**
     * CHARSTRINGS
     */
    public static final int TYPE_CHARSTRINGS = 21;

    /**
     * CHARSTRINGTYPE
     */
    public static final int TYPE_CHARSTRINGTYPE = 12;

    /**
     * CIDCOUNT
     */
    public static final int TYPE_CIDCOUNT = 27;

    /**
     * CIDFONTREVISION
     */
    public static final int TYPE_CIDFONTREVISION = 25;

    /**
     * CIDFONTTYPE
     */
    public static final int TYPE_CIDFONTTYPE = 26;

    /**
     * CIDFONTVERSION
     */
    public static final int TYPE_CIDFONTVERSION = 24;

    /**
     * COPYRIGHT
     */
    public static final int TYPE_COPYRIGHT = 6;

    /**
     * ENCODING
     */
    public static final int TYPE_ENCODING = 20;

    /**
     * FAMILYNAME
     */
    public static final int TYPE_FAMILYNAME = 3;

    /**
     * FDARRAY
     */
    public static final int TYPE_FDARRAY = 29;

    /**
     * FDSELECT
     */
    public static final int TYPE_FDSELECT = 30;

    /**
     * FONTBBOX
     */
    public static final int TYPE_FONTBBOX = 5;

    /**
     * FONTMATRIX
     */
    public static final int TYPE_FONTMATRIX = 13;

    /**
     * FONTNAME
     */
    public static final int TYPE_FONTNAME = 31;

    /**
     * FULLNAME
     */
    public static final int TYPE_FULLNAME = 2;

    /**
     * ISFIXEDPITCH
     */
    public static final int TYPE_ISFIXEDPITCH = 7;

    /**
     * ITALICANGLE
     */
    public static final int TYPE_ITALICANGLE = 8;

    /**
     * TYPE_NOTDEF
     */
    public static final int TYPE_NOTDEF = -1;

    /**
     * NOTICE
     */
    public static final int TYPE_NOTICE = 1;

    /**
     * PAINTTYPE
     */
    public static final int TYPE_PAINTTYPE = 11;

    /**
     * POSTSCRIPT
     */
    public static final int TYPE_POSTSCRIPT = 16;

    /**
     * PRIVATE
     */
    public static final int TYPE_PRIVATE = 22;

    /**
     * ROS
     */
    public static final int TYPE_ROS = 23;

    /**
     * STROKEWIDTH
     */
    public static final int TYPE_STROKEWIDTH = 14;

    /**
     * SYNTHETICBASE
     */
    public static final int TYPE_SYNTHETICBASE = 15;

    /**
     * UIDBASE
     */
    public static final int TYPE_UIDBASE = 28;

    /**
     * UNDERLINEPOSITION
     */
    public static final int TYPE_UNDERLINEPOSITION = 9;

    /**
     * UNDELINETHICKNESS
     */
    public static final int TYPE_UNDERLINETHICKNESS = 10;

    /**
     * UNIQUEID
     */
    public static final int TYPE_UNIQUEID = 17;

    /**
     * VERSION
     */
    public static final int TYPE_VERSION = 0;

    /**
     * WEIGHT
     */
    public static final int TYPE_WEIGHT = 4;

    /**
     * XUID
     */
    public static final int TYPE_XUID = 18;

    /**
     * Create a new instance.
     * 
     * @param rar the input
     * @return Returns the new T2Operatorr object.
     * @throws IOException if an IO-error occurs.
     */
    public static T2Operator newInstance(RandomAccessR rar) throws IOException {

        List<T2CharString> stack = new ArrayList<T2CharString>();

        while (true) {

            int b = rar.readUnsignedByte();

            switch (b) {
                case CFF_VERSION:
                    return new T2TDOVersion(stack);
                case CFF_NOTICE:
                    return new T2TDONotice(stack);
                case CFF_FULLNAME:
                    return new T2TDOFullName(stack);
                case CFF_FAMILYNAME:
                    return new T2TDOFamilyName(stack);
                case CFF_WEIGHT:
                    return new T2TDOWeight(stack);
                case CFF_FONTBBOX:
                    return new T2TDOFontBBox(stack);
                case ESCAPE_BYTE:
                    int b1 = rar.readUnsignedByte();
                    switch (b1) {
                        case CFF_COPYRIGHT:
                            return new T2TDOCopyright(stack);
                        case CFF_ISFIXEDPITCH:
                            return new T2TDOisFixedPitch(stack);
                        case CFF_ITALICANGLE:
                            return new T2TDOItalicAngle(stack);
                        case CFF_UNDERLINEPOSITION:
                            return new T2TDOUnderlinePosition(stack);
                        case CFF_UNDERLINETHICKNESS:
                            return new T2TDOUnderlineThickness(stack);
                        case CFF_PAINTTYPE:
                            return new T2TDOPaintType(stack);
                        case CFF_CHARSTRINGTYPE:
                            return new T2TDOCharStringType(stack);
                        case CFF_FONTMATRIX:
                            return new T2TDOFontMatrix(stack);
                        case CFF_STROKEWIDTH:
                            return new T2TDOStrokeWidth(stack);
                        case CFF_SYNTHETICBASE:
                            return new T2TDOSyntheticBase(stack);
                        case CFF_POSTSCRIPT:
                            return new T2TDOPostscript(stack);
                        case CFF_ROS:
                            return null;
                        case CFF_CIDFONTVERSION:
                            return null;
                        case CFF_CIDFONTREVISION:
                            return null;
                        case CFF_CIDFONTTYPE:
                            return null;
                        case CFF_CIDCOUNT:
                            return null;
                        case CFF_UIDBASE:
                            return null;
                        case CFF_FDARRAY:
                            return null;
                        case CFF_FDSELECT:
                            return null;
                        case CFF_FONTNAME:
                            return null;
                        default:
                            throw new T2NotAOperatorException();

                    }
                case CFF_UNIQUEID:
                    return new T2TDOUniqueID(stack);
                case CFF_XUID:
                    return new T2TDOXUID(stack);
                case CFF_CHARSET:
                    return new T2TDOCharset(stack);
                case CFF_ENCODING:
                    return new T2TDOEncoding(stack);
                case CFF_CHARSTRINGS:
                    return new T2TDOCharStrings(stack);
                case CFF_PRIVATE:
                    return new T2TDOPrivate(stack);
                default:
                    // number
                    T2Number number = T2CharString.readNumber(rar, b);
                    stack.add(number);
                    break;
            }
        }
    }

    /**
     * Create a new object
     */
    protected T2TopDICTOperator() {

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2CharString#isTopDICTOperator()
     */
    @Override
    public boolean isTopDICTOperator() {

        return true;
    }

}
