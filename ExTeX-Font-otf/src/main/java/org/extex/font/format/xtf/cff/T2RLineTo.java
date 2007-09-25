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
import org.extex.util.xml.XMLStreamWriter;

/**
 * rlineto |- {dxa dya}+ rlineto (5) |
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2RLineTo extends T2PathConstruction {

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
    public T2RLineTo(List<T2CharString> stack, CharString ch)
            throws IOException {

        super(stack, new short[]{T2RLINETO}, ch);

        int n = stack.size();

        if (n < 2 || n % 2 != 0) {
            throw new T2MissingNumberException();
        }
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
     * @see org.extex.font.format.xtf.cff.T2Operator#getID()
     */
    @Override
    public int getID() {

        return TYPE_RLINETO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "rlineto";
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
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        for (int i = 0; i < pairs.length; i++) {
            writer.writeStartElement("pair");
            writer.writeAttribute("id", i);
            writer.writeAttribute("value", pairs[i].toString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

}
