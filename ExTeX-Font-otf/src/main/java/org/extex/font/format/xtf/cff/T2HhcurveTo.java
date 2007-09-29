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
 * hhcurveto: dy1? {dxa dxb dyb dxc}+ hhcurveto (27).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2HhcurveTo extends T2PathConstruction {

    /**
     * The dy1.
     */
    private T2Number dy1;

    /**
     * The four values array.
     */
    private T2FourNumber[] four;

    /**
     * Create a new object.
     * 
     * @param ch The char string.
     * @param stack The stack.
     */
    public T2HhcurveTo(List<T2CharString> stack, CharString ch)
            throws IOException {

        super(stack, new short[]{T2HHCURVETO}, ch);

        int n = stack.size();

        if (n % 4 != 0) {
            // dy1 exists
            dy1 = (T2Number) stack.remove(0);
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
     * Getter for dy1.
     * 
     * @return the dy1
     */
    public T2Number getDy1() {

        return dy1;
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
     * @see org.extex.font.format.xtf.cff.T2Operator#getID()
     */
    @Override
    public int getID() {

        return TYPE_HHCURVETO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "hhcurveto";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getValue()
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
        if (dy1 != null) {
            writer.writeAttribute("dy1", dy1);
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
