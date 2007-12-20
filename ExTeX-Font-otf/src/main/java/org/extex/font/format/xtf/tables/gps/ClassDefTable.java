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

package org.extex.font.format.xtf.tables.gps;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.extex.font.format.xtf.tables.XtfGlyphName;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Class for a ClassDef table.
 * 
 * <p>
 * In OpenType Layout, index values identify glyphs. For efficiency and ease of
 * representation, a font developer can group glyph indices to form glyph
 * classes. Class assignments vary in meaning from one lookup subtable to
 * another. For example, in the GSUB and GPOS tables, classes are used to
 * describe glyph contexts. GDEF tables also use the idea of glyph classes.
 * </p>
 * <p>
 * Consider a substitution action that replaces only the lowercase ascender
 * glyphs in a glyph string. To more easily describe the appropriate context for
 * the substitution, the font developer might divide the font's lowercase glyphs
 * into two classes, one that contains the ascenders and one that contains the
 * glyphs without ascenders.
 * </p>
 * <p>
 * A font developer can assign any glyph to any class, each identified with an
 * integer called a class value. A Class Definition table (ClassDef) groups
 * glyph indices by class, beginning with Class 1, then Class 2, and so on. All
 * glyphs not assigned to a class fall into Class 0. Within a given class
 * definition table, each glyph in the font belongs to exactly one class.
 * </p>
 * <p>
 * The ClassDef table can have either of two formats: one that assigns a range
 * of consecutive glyph indices to different classes, or one that puts groups of
 * consecutive glyph indices into the same class.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class ClassDefTable implements XMLWriterConvertible {

    /**
     * ClassDefTable for Format 1.
     * 
     * <table border="1">
     * <tr>
     * <td><b>Type</b></td>
     * <td><b>Name</b></td>
     * <td><b>Description</b></td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>ClassFormat</td>
     * <td>Format identifier-format = 1</td>
     * </tr>
     * <tr>
     * <td>GlyphID</td>
     * <td>StartGlyph</td>
     * <td>First GlyphID of the ClassValueArray</td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>GlyphCount</td>
     * <td>Size of the ClassValueArray</td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>ClassValueArray[GlyphCount]</td>
     * <td>Array of Class Values-one per GlyphID</td>
     * </tr>
     * </table>
     */
    public static class ClassDef1Table extends ClassDefTable {

        /**
         * Creates a new object.
         * 
         * @param rar The input.
         * @param xtfGlyph The glyph name.
         * @param classformat The format of the class.
         * @param defNr The class def number.
         * @throws IOException if a IO-error occurred.
         */
        public ClassDef1Table(RandomAccessR rar, XtfGlyphName xtfGlyph,
                int classformat, int defNr) throws IOException {

            format = classformat;
            this.defNr = defNr;

            int startGlyph = rar.readUnsignedShort();
            int glyphCount = rar.readUnsignedShort();

            classMap = new HashMap<Integer, Integer>(glyphCount);

            for (int i = 0; i < glyphCount; i++) {
                int classValue = rar.readUnsignedShort();
                classMap.put(startGlyph + i, classValue);
            }

        }
    }

    /**
     * ClassDefTable for Format 2.
     * 
     * <p>
     * The second class definition format (ClassDefFormat2) defines multiple
     * groups of glyph indices that belong to the same class. Each group
     * consists of a discrete range of glyph indices in consecutive order
     * (ranges cannot overlap).
     * </p>
     * <p>
     * The ClassDef Format 2 table contains a format identifier (ClassFormat), a
     * count of ClassRangeRecords that define the groups and assign class values
     * (ClassRangeCount), and an array of ClassRangeRecords ordered by the
     * GlyphID of the first glyph in each record (ClassRangeRecord).
     * </p>
     * <p>
     * Each ClassRangeRecord consists of a Start glyph index, an End glyph
     * index, and a Class value. All GlyphIDs in a range, from Start to End
     * inclusive, constitute the class identified by the Class value. Any glyph
     * not covered by a ClassRangeRecord is assumed to belong to Class 0.
     * </p>
     */
    public static class ClassDef2Table extends ClassDefTable {

        /**
         * Creates a new object.
         * 
         * @param rar The input.
         * @param xtfGlyph The glyph name.
         * @param classformat The class format.
         * @param defNr The class def number.
         * @throws IOException if an IO-error occurred.
         */
        public ClassDef2Table(RandomAccessR rar, XtfGlyphName xtfGlyph,
                int classformat, int defNr) throws IOException {

            this.xtfGlyph = xtfGlyph;
            this.defNr = defNr;
            format = classformat;

            int classRangeCount = rar.readUnsignedShort();
            classMap = new HashMap<Integer, Integer>(classRangeCount);

            for (int i = 0; i < classRangeCount; i++) {
                ClassRangeRecord classRangeRecord = new ClassRangeRecord(rar);
                for (int k = classRangeRecord.getStart(); k <= classRangeRecord
                    .getEnd(); k++) {
                    classMap.put(k, classRangeRecord.getGclass());
                }
            }
        }
    }

    /**
     * Format 1.
     */
    private static final int FORMAT1 = 1;

    /**
     * Format 2.
     */
    private static final int FORMAT2 = 2;

    /**
     * Returns a new instance of the ClassDefTable.
     * 
     * @param rar The input.
     * @param xtfGlyph The glyph name.
     * @param defNr The class def number.
     * @return Returns a new instance of the ClassDefTable.
     * @throws IOException if a IO-error occurred.
     */
    public static ClassDefTable newInstance(RandomAccessR rar,
            XtfGlyphName xtfGlyph, int defNr) throws IOException {

        int classformat = rar.readUnsignedShort();
        ClassDefTable table = null;

        if (classformat == FORMAT1) {
            table = new ClassDef1Table(rar, xtfGlyph, classformat, defNr);
        } else if (classformat == FORMAT2) {
            table = new ClassDef2Table(rar, xtfGlyph, classformat, defNr);
        }
        return table;
    }

    /**
     * The map for the classRangeRecord (to increase the search speed).
     */
    protected Map<Integer, Integer> classMap;

    /**
     * class def number.
     */
    protected int defNr;

    /**
     * The class format.
     */
    protected int format;

    /**
     * The glyph names.
     */
    protected XtfGlyphName xtfGlyph;

    /**
     * Add glyphs in Class 0.
     * <p>
     * All glyphs not assigned to a class fall into Class 0.
     * </p>
     * 
     * @param glyphs The glyphs (number) from the coverage list.
     */
    public void addClass0(int[] glyphs) {

        for (int i = 0; glyphs != null & i < glyphs.length; i++) {
            if (!classMap.containsKey(glyphs[i])) {
                classMap.put(glyphs[i], 0);
            }
        }
    }

    /**
     * Returns the class for the glyph or -1 if not found.
     * 
     * @param glyph The glyph number.
     * @return Returns the class for the glyph or -1 if not found.
     */
    public int getClass(int glyph) {

        Integer val = classMap.get(glyph);
        if (val != null) {
            return val.intValue();
        }
        return -1;
    }

    /**
     * Getter for format.
     * 
     * @return the format
     */
    public int getFormat() {

        return format;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("classdef");
        writer.writeAttribute("number", defNr);
        writer.writeAttribute("format", getFormat());
        int[] sort = new int[classMap.size()];
        Iterator<Integer> it = classMap.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            sort[i++] = it.next();
        }
        Arrays.sort(sort);
        for (int k = 0; k < sort.length; k++) {
            int key = sort[k];
            int val = classMap.get(key);
            writer.writeStartElement("glyph");
            writer.writeAttribute("glyph", key);
            writer.writeAttribute("glyphname", xtfGlyph.getGlyphName(key));
            writer.writeAttribute("class", val);
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

}
