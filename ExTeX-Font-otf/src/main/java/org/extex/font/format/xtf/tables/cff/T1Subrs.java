/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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
import java.util.List;

import org.extex.font.format.xtf.tables.OtfTableCFF;
import org.extex.util.file.random.RandomAccessInputArray;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Subrs.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T1Subrs extends T1DictNumber {

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
    public T1Subrs(List<T2Number> stack) throws IOException {

        super(stack, new short[]{Subrs});
    }

    /**
     * Returns the {@link CharString} with the index or <code>null</code>, if
     * not found.
     * 
     * @param idx The index.
     * @return Returns the {@link CharString} with the index or
     *         <code>null</code>, if not found.
     */
    public CharString getCharString(int idx) {

        if (chars != null && idx >= 0 && idx < chars.length) {
            return chars[idx];
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T1DictKey#getName()
     */
    @Override
    public String getName() {

        return "Subrs";
    }

    /**
     * Read the subrs.
     * 
     * @param rar The input.
     * @param cff The cff object.
     * @param privateDictOffset The offset.
     * @param cffFont The cff font.
     * @throws IOException if a IO-error occurred.
     */
    public void readSubrs(RandomAccessR rar, OtfTableCFF cff,
            int privateDictOffset, CffFont cffFont) throws IOException {

        // The local subrs offset is relative to the beginning of the Private
        // DICT data.
        int offset = getInteger();

        if (offset > 0) {
            rar.seek(privateDictOffset + offset);

            int[] offsetarray = cff.readOffsets(rar);

            chars = new CharString[offsetarray.length - 1];

            // get data
            for (int i = 0; i < offsetarray.length - 1; i++) {

                byte[] data =
                        cff.readDataFromIndex(offsetarray[i],
                            offsetarray[i + 1], rar);

                chars[i] = new CharString(cffFont, i, true);

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
                    e.printStackTrace();
                    // TODO change to EOFException ignore
                }
                chars[i].checkWidth();
            }
        }
    }

    /**
     * Returns the size of the {@link CharString} array.
     * 
     * @return Returns the size of the {@link CharString} array.
     */
    public int size() {

        if (chars != null) {
            return chars.length;
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    @Override
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        if (chars != null) {
            writer.writeAttribute("count", chars.length);
            for (int i = 0; i < chars.length; i++) {
                chars[i].writeXML(writer);
            }
        } else {
            writer.writeComment("no charstrings");
        }
        writer.writeEndElement();

    }

}
