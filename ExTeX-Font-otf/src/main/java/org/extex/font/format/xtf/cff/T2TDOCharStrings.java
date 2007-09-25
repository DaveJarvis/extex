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

package org.extex.font.format.xtf.cff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.font.format.xtf.OtfTableCFF;
import org.extex.util.file.random.RandomAccessInputArray;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * CharStrings.
 * <p>
 * CharStrings INDEX
 * </p>
 * <p>
 * This contains the charstrings of all the glyphs in a font stored in an INDEX
 * structure. Charstring objects contained within this INDEX are accessed by
 * GID. The first charstring (GID 0) must be the .notdef glyph. The number of
 * glyphs available in a font may be determined from the count field in the
 * INDEX. The format of the charstring data, and therefore the method of
 * interpretation, is specified by the CharstringType operator in the Top DICT.
 * The CharstringType operator has a default value of 2 indicating the Type 2
 * charstring format which was designed in conjunction with CFF. Type 1
 * charstrings are documented in the "Adobe Type 1 Font Format". Type 2
 * charstrings are described in Adobe Technical Note #5177: "Type 2 Charstring
 * Format." Other charstring types may also be supported by this method.
 * </p>
 * <p>
 * Type 2
 * </p>
 * <p>
 * A Type 2 charstring program is a sequence of unsigned 8-bit bytes that encode
 * numbers and operators. The byte value specifies a operator, a number, or
 * subsequent bytes that are to be interpreted in a specific manner. The bytes
 * are decoded into numbers and operators. One reason * the format is more
 * economical than Type 1 is because the Type 2 charstring interpreter is
 * required to count the number of arguments on the argument stack. It can thus
 * detect additional sets of arguments for a single operator. The stack depth
 * implementation limit is specified in Appendix B. A number, decoded from a
 * charstring, is pushed onto the Type 2 argument stack. An operator expects its
 * arguments in order from this argument stack with all arguments generally
 * taken from the bottom of the stack (first argument bottom-most); however,
 * some operators, particularly the subroutine operators, normally work from the
 * top of the stack. If an operator returns results, they are pushed onto the
 * Type 2 argument stack (last result topmost). In the following discussion, all
 * numeric constants are decimal numbers, except where indicated.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class T2TDOCharStrings extends T2TDONumber {

    /**
     * Container for each char string.
     */
    public static class CharString implements XMLWriterConvertible {

        /**
         * The cff font.
         */
        private CffFont cffFont;

        /**
         * The index.
         */
        protected int idx;

        /**
         * The t2 operators.
         */
        private List<T2Operator> t2Ops = new ArrayList<T2Operator>();

        /**
         * Count the hints in the commands.
         * 
         * @return Returns the hints in the commands.
         */
        public int countHints() {

            return 0; // TODO incomplete

        }

        /**
         * The width.
         */
        protected Integer width = null;

        /**
         * Creates a new object.
         * 
         * @param idx The index.
         */
        public CharString(CffFont cffFont, int idx) {

            this.cffFont = cffFont;
            this.idx = idx;
        }

        /**
         * Creates a new object.
         * 
         * Only fpr sub classes.
         */
        protected CharString() {

            super();
        }

        /**
         * Add a {@link T2Operator}.
         * 
         * @param op The t2 operator to add.
         */
        public void add(T2Operator op) {

            t2Ops.add(op);
        }

        /**
         * Check, if the width is set.
         */
        public void checkWidth() {

            if (width == null) {
                width = new Integer(cffFont.getDefaultWidthX());
            }
        }

        /**
         * Getter for cffFont.
         * 
         * @return the cffFont
         */
        public CffFont getCffFont() {

            return cffFont;
        }

        /**
         * Getter for idx.
         * 
         * @return the idx
         */
        public int getIdx() {

            return idx;
        }

        /**
         * Getter for width.
         * 
         * @return the width
         */
        public Integer getWidth() {

            return width;
        }

        /**
         * Setter for width.
         * 
         * @param width the width to set
         */
        public void setWidth(T2Number width) {

            if (width != null) {
                this.width =
                        new Integer(cffFont.getNominalWidthX()
                                + width.getInteger());
            }
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("chars");
            writer.writeAttribute("id", idx);

            writer.writeAttribute("name", cffFont.getNameForPos(idx));
            writer.writeAttribute("width", width);

            for (int i = 0; i < t2Ops.size(); i++) {
                t2Ops.get(i).writeXML(writer);
            }
            writer.writeEndElement();

        }
    }

    /**
     * The array of the char strings.
     */
    private CharString[] chars;

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @throws IOException if an IO.error occurs.
     */
    public T2TDOCharStrings(List<T2CharString> stack) throws IOException {

        super(stack, new short[]{CFF_CHARSTRINGS});
    }

    @Override
    public int getID() {

        return T2TopDICTOperator.TYPE_CHARSTRINGS;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2CharString#getInitPrio()
     */
    @Override
    public int getInitPrio() {

        return -1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "charstring";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#init(org.extex.util.file.random.RandomAccessR,
     *      org.extex.font.format.xtf.OtfTableCFF, int,
     *      org.extex.font.format.xtf.cff.CffFont)
     */
    @Override
    public void init(RandomAccessR rar, OtfTableCFF cff, int baseoffset,
            CffFont cffFont) throws IOException {

        int offset = getInteger();

        if (offset > 0) {
            rar.seek(baseoffset + offset);

            int[] offsetarray = cff.readOffsets(rar);

            chars = new CharString[offsetarray.length - 1];

            // notdef: guid 0
            // chars[0] = new CharString(cffFont, 0);
            // chars[0].checkWidth();

            // get data
            for (int i = 0; i < offsetarray.length - 1; i++) {

                byte[] data =
                        cff.readDataFromIndex(offsetarray[i],
                            offsetarray[i + 1], rar);

                chars[i] = new CharString(cffFont, i);

                RandomAccessInputArray arar = new RandomAccessInputArray(data);
                try {
                    while (true) {
                        // read until eof
                        T2Operator op = T2Operator.newInstance(arar, chars[i]);
                        chars[i].add(op);
                        if (arar.isEOF()) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    // TODO change to EOFException ignore
                }
                chars[i].checkWidth();
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2TDONumber#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    @Override
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("count", chars.length);
        for (int i = 0; i < chars.length; i++) {
            chars[i].writeXML(writer);
        }
        writer.writeEndElement();
    }
}
