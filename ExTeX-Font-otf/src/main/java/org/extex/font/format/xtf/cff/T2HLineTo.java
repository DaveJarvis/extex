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

import org.extex.util.xml.XMLStreamWriter;

/**
 * hlineto |- dx1 {dya dxb}* hlineto (6) | |- {dxa dyb}+ hlineto (6) |
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2HLineTo extends T2PathConstruction {

    /**
     * dx.
     */
    private T2Number dx;

    /**
     * The pair array.
     */
    private T2PairNumber[] pairs;

    /**
     * Create a new object.
     * 
     * @param ch The char string.
     * @param stack The stack.
     */
    public T2HLineTo(List<T2CharString> stack, CharString ch)
            throws IOException {

        super(stack, new short[]{T2HLINETO}, ch);

        int n = stack.size();

        if (n < 1) {
            throw new T2MissingNumberException();
        }

        if (n == 1) {
            // only dx
            dx = (T2Number) stack.get(0);
            pairs = new T2PairNumber[0];
        } else if (n % 2 == 0) {
            readPairs(stack);
        } else {
            dx = (T2Number) stack.get(0);
            stack.remove(0);
            readPairs(stack);
        }

    }

    /**
     * Getter for dx.
     * 
     * <p>
     * dx can be <code>null</code>!
     * </p>
     * 
     * @return the dx
     */
    public T2Number getDx() {

        return dx;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getID()
     */
    @Override
    public int getID() {

        return TYPE_HLINETO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "hlineto";
    }

    /**
     * Getter for pairs.
     * 
     * @return the pairs
     */
    public T2PairNumber[] getPairs() {

        return pairs;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getValue()
     */
    @Override
    public Object getValue() {

        return pairs;
    }

    /**
     * Reads the pairs.
     * 
     * @param stack The stack.
     */
    private void readPairs(List<T2CharString> stack) {

        int n = stack.size();
        pairs = new T2PairNumber[n / 2];

        for (int i = 0; i < n; i += 2) {
            T2Number v1 = (T2Number) stack.get(i);
            T2Number v2 = (T2Number) stack.get(i + 1);
            T2PairNumber pn = new T2PairNumber(v1, v2);
            pairs[i / 2] = pn;
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
        if (dx != null) {
            writer.writeAttribute("dx", dx.toString());
        }
        for (int i = 0; i < pairs.length; i++) {
            writer.writeStartElement("pair");
            writer.writeAttribute("id", i);
            writer.writeAttribute("value", pairs[i].toString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

}
