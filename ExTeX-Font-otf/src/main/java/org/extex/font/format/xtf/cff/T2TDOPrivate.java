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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.font.format.xtf.OtfTableCFF;
import org.extex.util.file.random.RandomAccessInputArray;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Private.
 * 
 * Private DICT Operators Name Value Operand(s) Default, notes BlueValues 6
 * delta - OtherBlues 7 delta - FamilyBlues 8 delta - FamilyOtherBlues 9 delta -
 * BlueScale 12 9 number - 0.039625 BlueShift 12 10 number 7 BlueFuzz 12 11
 * number 1 StdHW 10 number - StdVW 11 number - StemSnapH 12 12 delta -
 * StemSnapV 12 13 delta - ForceBold 12 14 boolean false LanguageGroup 12 17
 * number 0 ExpansionFactor 12 18 number 0.06 initialRandomSeed 12 19 number 0
 * Subrs 19 number -, Offset (self) to local subrs defaultWidthX 20 number 0,
 * see below nominalWidthX 21 number 0, see below
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class T2TDOPrivate extends T2TDOArray {

    /**
     * The dict key array.
     */
    private T1DictKey[] dictkeys;

    /**
     * The hash map.
     */
    private Map<String, T1DictKey> hashValues;

    /**
     * The offset.
     */
    private int offset = -1;

    /**
     * The size.
     */
    private int size = -1;

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @throws IOException if an IO-error occurs.
     */
    public T2TDOPrivate(List<T2CharString> stack) throws IOException {

        super(stack, new short[]{CFF_PRIVATE});
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    public String getName() {

        return "private";
    }

    /**
     * Returns the dict key for the name.
     * 
     * @param name The name of the key.
     * @return Returns the dict key for the name.
     */
    public T1DictKey getT1DictKey(String name) {

        return hashValues.get(name.toLowerCase());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#init(
     *      org.extex.util.file.random.RandomAccessR,
     *      org.extex.font.format.xtf.OtfTableCFF, int)
     */
    public void init(RandomAccessR rar, OtfTableCFF cff, int baseoffset)
            throws IOException {

        hashValues = new HashMap<String, T1DictKey>();

        T2Number[] val = (T2Number[]) getValue();
        if (val.length >= 1) {
            size = val[0].getInteger();
        }
        if (val.length >= 2) {
            offset = val[1].getInteger();
        }

        if (offset > 0) {
            rar.seek(baseoffset + offset);

            byte[] data = new byte[size];
            rar.readFully(data);
            RandomAccessInputArray arar = new RandomAccessInputArray(data);

            ArrayList<T1DictKey> list = new ArrayList<T1DictKey>();
            try {
                // read until end of input -> IOException
                while (true) {
                    T1DictKey op = T1DictKey.newInstance(arar);
                    list.add(op);
                    hashValues.put(op.getName().toLowerCase(), op);
                }
            } catch (IOException e) {
                dictkeys = new T1DictKey[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    dictkeys[i] = list.get(i);
                }
            }

        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("size", size);
        writer.writeAttribute("offset", offset);

        if (dictkeys != null) {
            for (int i = 0; i < dictkeys.length; i++) {
                T1DictKey dk = dictkeys[i];
                dk.writeXML(writer);
            }
        }
        writer.writeEndElement();

    }

    @Override
    public int getID() {

        return T2TopDICTOperator.TYPE_PRIVATE;
    }

}
