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
import java.util.List;

import org.extex.font.format.xtf.cff.T2TDOCharStrings.CharString;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * hintmask |- hintmask (19 + mask) |.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2HintMask extends T2AbstractHintMask {

    /**
     * The hintmask.
     */
    private int mask;

    /**
     * The values.
     */
    private T2Number[] val;

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @param ch The char string.
     * @param rar The input.
     * @param id the operator-id for the value
     * @throws IOException if an IO-error occurs.
     */
    public T2HintMask(List<T2CharString> stack, CharString ch, RandomAccessR rar)
            throws IOException {

        super(stack, new short[]{T2HINTMASK}, ch);

        int n = stack.size();
        val = new T2Number[n];

        for (int i = 0; i < n; i++) {
            val[i] = (T2Number) stack.get(i);
        }

        // read a number
        // TODO count the hints
        if (n <= 8) {
            mask = rar.readUnsignedByte();
        } else if (n <= 16) {
            mask = rar.readUnsignedByte();
            mask = mask << 8;
            mask += rar.readUnsignedByte();
        } else {

        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getID()
     */
    @Override
    public int getID() {

        return TYPE_HINTMASK;
    }

    /**
     * Getter for mask.
     * 
     * @return the mask
     */
    public int getMask() {

        return mask;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "hintmask";
    }

    /**
     * Getter for val.
     * 
     * @return the val
     */
    public T2Number[] getVal() {

        return val;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getValue()
     */
    @Override
    public Object getValue() {

        return val;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("mask", Integer.toBinaryString(mask));
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < val.length; i++) {
            buf.append(" ").append(val[i]);
        }
        writer.writeAttribute("values", buf.toString().trim());
        writer.writeEndElement();
    }
}
