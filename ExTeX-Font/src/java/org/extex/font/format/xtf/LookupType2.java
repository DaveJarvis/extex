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

import org.extex.util.file.random.RandomAccessR;

/**
 * Lookup Type 2: Pair Adjustment Positioning Subtable.
 * 
 * <p>
 * A pair adjustment positioning subtable (PairPos) is used to adjust the
 * positions of two glyphs in relation to one another-for instance, to specify
 * kerning data for pairs of glyphs. Compared to a typical kerning table,
 * however, a PairPos subtable offers more flexiblity and precise control over
 * glyph positioning. The PairPos subtable can adjust each glyph in a pair
 * independently in both the X and Y directions, and it can explicitly describe
 * the particular type of adjustment applied to each glyph. In addition, a
 * PairPos subtable can use Device tables to subtly adjust glyph positions at
 * each font size and device resolution.
 * </p>
 * <p>
 * PairPos subtables can be either of two formats: one that identifies glyphs
 * individually by index (Format 1), or one that identifies glyphs by class
 * (Format 2).
 * </p>
 * <p>
 * Pair Positioning Adjustment: Format 1
 * </p>
 * <p>
 * Format 1 uses glyph indices to access positioning data for one or more
 * specific pairs of glyphs. All pairs are specified in the order determined by
 * the layout direction of the text.
 * </p>
 * 
 * <p>
 * <i>Note:</i> For text written from right to left, the right-most glyph will
 * be the first glyph in a pair; conversely, for text written from left to
 * right, the left-most glyph will be first.
 * </p>
 * 
 * <p>
 * A PairPosFormat1 subtable contains a format identifier (PosFormat) and two
 * ValueFormats:
 * </p>
 * <ul>
 * <li>ValueFormat1 applies to the ValueRecord of the first glyph in each pair.
 * ValueRecords for all first glyphs must use ValueFormat1. If ValueFormat1 is
 * set to zero (0), the corresponding glyph has no ValueRecord and, therefore,
 * should not be repositioned.</li>
 * <li>ValueFormat2 applies to the ValueRecord of the second glyph in each
 * pair. ValueRecords for all second glyphs must use ValueFormat2. If
 * ValueFormat2 is set to null, then the second glyph of the pair is the
 * &quot;next&quot; glyph for which a lookup should be performed.</li>
 * </ul>
 * 
 * <p>
 * A PairPos subtable also defines an offset to a Coverage table (Coverage) that
 * lists the indices of the first glyphs in each pair. More than one pair can
 * have the same first glyph, but the Coverage table will list that glyph only
 * once.
 * </p>
 * <p>
 * The subtable also contains an array of offsets to PairSet tables (PairSet)
 * and a count of the defined tables (PairSetCount). The PairSet array contains
 * one offset for each glyph listed in the Coverage table and uses the same
 * order as the Coverage Index.
 * </p>
 * 
 * <p>
 * PairPosFormat1 subtable: Adjustments for glyph pairs
 * </p>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>PosFormat</td>
 * <td>Format identifier-format = 1</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>Coverage</td>
 * <td>Offset to Coverage table-from beginning of PairPos subtable-only the
 * first glyph in each pair</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>ValueFormat1</td>
 * <td>Defines the types of data in ValueRecord1-for the first glyph in the
 * pair -may be zero (0)</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>ValueFormat2</td>
 * <td>Defines the types of data in ValueRecord2-for the second glyph in the
 * pair -may be zero (0)</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>PairSetCount</td>
 * <td>Number of PairSet tables</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>PairSetOffset<br>
 * [PairSetCount]</td>
 * <td>Array of offsets to PairSet tables-from beginning of PairPos
 * subtable-ordered by Coverage Index</td>
 * </tr>
 * </table>
 * <p>
 * </p>
 * 
 * <p>
 * A PairSet table enumerates all the glyph pairs that begin with a covered
 * glyph. An array of PairValueRecords (PairValueRecord) contains one record for
 * each pair and lists the records sorted by the GlyphID of the second glyph in
 * each pair. PairValueCount specifies the number of PairValueRecords in the
 * set.
 * </p>
 * 
 * <p>
 * PairSet table
 * </p>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>PairValueCount</td>
 * <td>Number of PairValueRecords</td>
 * </tr>
 * <tr>
 * <td>struct</td>
 * <td>PairValueRecord<br>
 * [PairValueCount]</td>
 * <td>Array of PairValueRecords-ordered by GlyphID of the second glyph</td>
 * </tr>
 * </table>
 * <p>
 * </p>
 * 
 * <p>
 * A PairValueRecord specifies the second glyph in a pair (SecondGlyph) and
 * defines a ValueRecord for each glyph (Value1 and Value2). If ValueFormat1 is
 * set to zero (0) in the PairPos subtable, ValueRecord1 will be empty;
 * similarly, if ValueFormat2 is 0, Value2 will be empty.
 * </p>
 * 
 * <p>
 * PairValueRecord
 * </p>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>GlyphID</td>
 * <td>SecondGlyph</td>
 * <td>GlyphID of second glyph in the pair-first glyph is listed in the
 * Coverage table</td>
 * </tr>
 * <tr>
 * <td>ValueRecord</td>
 * <td>Value1</td>
 * <td>Positioning data for the first glyph in the pair</td>
 * </tr>
 * <tr>
 * <td>ValueRecord</td>
 * <td>Value2</td>
 * <td>Positioning data for the second glyph in the pair</td>
 * </tr>
 * </table>
 * <p>
 * </p>
 * 
 * <p>
 * Pair Positioning Adjustment: Format 2
 * </p>
 * 
 * <p>
 * Format 2 defines a pair as a set of two glyph classes and modifies the
 * positions of all the glyphs in a class. For example, this format is useful in
 * Japanese scripts that apply specific kerning operations to all glyph pairs
 * that contain punctuation glyphs. One class would be defined as all glyphs
 * that may be coupled with punctuation marks, and the other classes would be
 * groups of similar punctuation glyphs.
 * </p>
 * <p>
 * The PairPos Format2 subtable begins with a format identifier (PosFormat) and
 * an offset to a Coverage table (Coverage), measured from the beginning of the
 * PairPos subtable. The Coverage table lists the indices of the first glyphs
 * that may appear in each glyph pair. More than one pair may begin with the
 * same glyph, but the Coverage table lists the glyph index only once.
 * </p>
 * <p>
 * A PairPosFormat2 subtable also includes two ValueFormats:
 * </p>
 * <ul>
 * <li>ValueFormat1 applies to the ValueRecord of the first glyph in each pair.
 * ValueRecords for all first glyphs must use ValueFormat1. If ValueFormat1 is
 * set to zero (0), the corresponding glyph has no ValueRecord and, therefore,
 * should not be repositioned.</li>
 * <li>ValueFormat2 applies to the ValueRecord of the second glyph in each
 * pair. ValueRecords for all second glyphs must use ValueFormat2. If
 * ValueFormat2 is set to null, then the second glyph of the pair is the
 * &quot;next&quot; glyph for which a lookup should be performed.</li>
 * </ul>
 * 
 * <p>
 * PairPosFormat2 requires that each glyph in all pairs be assigned to a class,
 * which is identified by an integer called a class value. (For details about
 * classes, see the chapter, Common Table Formats.) Pairs are then represented
 * in a two-dimensional array as sequences of two class values. Multiple pairs
 * can be represented in one Format 2 subtable.
 * </p>
 * <p>
 * A PairPosFormat2 subtable contains offsets to two class definition tables:
 * one that assigns class values to all the first glyphs in all pairs
 * (ClassDef1), and one that assigns class values to all the second glyphs in
 * all pairs (ClassDef2). If both glyphs in a pair use the same class
 * definition, the offset value will be the same for ClassDef1 and ClassDef2.
 * The subtable also specifies the number of glyph classes defined in ClassDef1
 * (Class1Count) and in ClassDef2 (Class2Count), including Class0.
 * </p>
 * <p>
 * For each class identified in the ClassDef1 table, a Class1Record enumerates
 * all pairs that contain a particular class as a first component. The
 * Class1Record array stores all Class1Records according to class value.
 * </p>
 * <p>
 * <i>Note:</i> Class1Records are not tagged with a class value identifier.
 * Instead, the index value of a Class1Record in the array defines the class
 * value represented by the record. For example, the first Class1Record
 * enumerates pairs that begin with a Class 0 glyph, the second Class1Record
 * enumerates pairs that begin with a Class1 glyph, and so on.
 * </p>
 * 
 * <p>
 * PairPosFormat2 subtable: Class pair adjustment
 * </p>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>PosFormat</td>
 * <td>Format identifier-format = 2</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>Coverage</td>
 * <td>Offset to Coverage table-from beginning of PairPos subtable-for the
 * first glyph of the pair</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>ValueFormat1</td>
 * <td>ValueRecord definition-for the first glyph of the pair-may be zero (0)</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>ValueFormat2</td>
 * <td>ValueRecord definition-for the second glyph of the pair-may be zero (0)</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>ClassDef1</td>
 * <td>Offset to ClassDef table-from beginning of PairPos subtable-for the
 * first glyph of the pair</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>ClassDef2</td>
 * <td>Offset to ClassDef table-from beginning of PairPos subtable-for the
 * second glyph of the pair</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>Class1Count</td>
 * <td>Number of classes in ClassDef1 table-includes Class0</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>Class2Count</td>
 * <td>Number of classes in ClassDef2 table-includes Class0</td>
 * </tr>
 * <tr>
 * <td>struct</td>
 * <td>Class1Record<br>
 * [Class1Count]</td>
 * <td>Array of Class1 records-ordered by Class1</td>
 * </tr>
 * </table>
 * <p>
 * </p>
 * 
 * 
 * <p>
 * Each Class1Record contains an array of Class2Records (Class2Record), which
 * also are ordered by class value. One Class2Record must be declared for each
 * class in the ClassDef2 table, including Class 0.
 * </p>
 * 
 * <p>
 * Class1Record
 * </p>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>struct</td>
 * <td>Class2Record[Class2Count]</td>
 * <td>Array of Class2 records-ordered by Class2</td>
 * </tr>
 * </table>
 * <p>
 * </p>
 * 
 * <p>
 * A Class2Record consists of two ValueRecords, one for the first glyph in a
 * class pair (Value1) and one for the second glyph (Value2). If the PairPos
 * subtable has a value of zero (0) for ValueFormat1 or ValueFormat2, the
 * corresponding record (ValueRecord1 or ValueRecord2) will be empty.
 * </p>
 * 
 * <p>
 * Class2Record
 * </p>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>ValueRecord</td>
 * <td>Value1</td>
 * <td>Positioning for first glyph-empty if ValueFormat1 = 0</td>
 * </tr>
 * <tr>
 * <td>ValueRecord</td>
 * <td>Value2</td>
 * <td>Positioning for second glyph-empty if ValueFormat2 = 0</td>
 * </tr>
 * </table>
 * <p>
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5438 $
 */
public abstract class LookupType2 extends LookupType {

    /**
     * Format identifier-format.
     */
    private int posFormat;

    /**
     * Creates a new object.
     * 
     * @param type The lookup tpye.
     * @param format The pos format.
     * @throws IOException if a io-error occurred.
     */
    protected LookupType2(int type, int format) throws IOException {

        super(type);

        posFormat = format;

    }

    /**
     * Returns a instance of the lookup type.
     * 
     * @param rar The input.
     * @param type The lookup tpye.
     * @param offset The offset.
     * @return Returns a instance of the lookup type.
     * @throws IOException if a io-error occurred.
     */
    public static LookupType getInstance(RandomAccessR rar, int type, int offset)
            throws IOException {

        rar.seek(offset);

        int posFormat = rar.readUnsignedShort();

        switch (posFormat) {
            case 1:
                return new LookupType21(rar, type, posFormat);
            case 2:
                return null;// new LookupType12(rar, type, posFormat);

            default:
                throw new IOException("wrong posformat");
        }
    }

    /**
     * Getter for posFormat.
     * 
     * @return the posFormat
     */
    public int getPosFormat() {

        return posFormat;
    }

}
