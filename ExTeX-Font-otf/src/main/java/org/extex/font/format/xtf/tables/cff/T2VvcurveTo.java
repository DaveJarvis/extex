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

package org.extex.font.format.xtf.tables.cff;

import java.io.IOException;
import java.util.List;

import org.extex.util.xml.XMLStreamWriter;

/**
 * vvcurveto: dx1? {dya dxb dyb dyc}+ vvcurveto (26).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2VvcurveTo extends T2PathConstruction {

    /**
     * The dx1.
     */
    private T2Number dx1;

    /**
     * The four values array.
     */
    private T2FourNumber[] four;

    /**
     * Create a new object.
     * 
     * @param ch The char string.
     * @param stack The stack.
     * 
     * @throws IOException in case of an error
     */
    public T2VvcurveTo(List<T2CharString> stack, CharString ch)
            throws IOException {

        super(stack, new short[]{T2HHCURVETO}, ch);

        int n = stack.size();

        if (n % 4 != 0) {
            // dx1 exists
            dx1 = (T2Number) stack.remove(0);
        }

        n = stack.size();

        if (n % 4 != 0) {
            throw new T2MissingNumberException();
        }

        four = new T2FourNumber[n / 4];

        for (int i = 0; i < n; i += 4) {
            T2Number v1 = (T2Number) stack.get(i);
            T2Number v2 = (T2Number) stack.get(i + 1);
            T2Number v3 = (T2Number) stack.get(i + 2);
            T2Number v4 = (T2Number) stack.get(i + 3);
            T2FourNumber si = new T2FourNumber(v1, v2, v3, v4);
            four[i / 4] = si;
        }

    }

    /**
     * Getter for dx1.
     * 
     * @return the dx1
     */
    public T2Number getDx1() {

        return dx1;
    }

    /**
     * Getter for four.
     * 
     * @return the four
     */
    public T2FourNumber[] getFour() {

        return four;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getID()
     */
    @Override
    public int getID() {

        return TYPE_VVCURVETO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "vvcurveto";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getValue()
     */
    @Override
    public Object getValue() {

        return four;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        if (dx1 != null) {
            writer.writeAttribute("dx1", dx1);
        }
        for (int i = 0; i < four.length; i++) {
            writer.writeStartElement("pair");
            writer.writeAttribute("id", i);
            writer.writeAttribute("value", four[i].toString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

}
