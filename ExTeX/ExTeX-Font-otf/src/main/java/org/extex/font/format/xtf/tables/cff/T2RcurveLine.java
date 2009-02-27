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
 * rcurveline: {dxa dya dxb dyb dxc dyc}+ dxd dyd rcurveline (24).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2RcurveLine extends T2PathConstruction {

    /**
     * The dxd.
     */
    private T2Number dxd;

    /**
     * The dyd.
     */
    private T2Number dyd;

    /**
     * The six values array.
     */
    private T2SixNumber[] eight;

    /**
     * Create a new object.
     * 
     * @param ch The char string.
     * @param stack The stack.
     * 
     * @throws IOException in case of an error
     */
    public T2RcurveLine(List<T2CharString> stack, CharString ch)
            throws IOException {

        super(stack, new short[]{T2RCURVELINE}, ch);

        int n = stack.size();

        eight = new T2SixNumber[n / 6];

        for (int i = 0; i < n; i += 6) {
            T2Number v1 = (T2Number) stack.remove(0);
            T2Number v2 = (T2Number) stack.remove(0);
            T2Number v3 = (T2Number) stack.remove(0);
            T2Number v4 = (T2Number) stack.remove(0);
            T2Number v5 = (T2Number) stack.remove(0);
            T2Number v6 = (T2Number) stack.remove(0);
            T2SixNumber si = new T2SixNumber(v1, v2, v3, v4, v5, v6);
            eight[i / 6] = si;
        }

        dxd = (T2Number) stack.remove(0);
        dyd = (T2Number) stack.remove(0);

    }

    /**
     * Getter for dxd.
     * 
     * @return the dxd
     */
    public T2Number getDxd() {

        return dxd;
    }

    /**
     * Getter for dyd.
     * 
     * @return the dyd
     */
    public T2Number getDyd() {

        return dyd;
    }

    /**
     * Getter for eight.
     * 
     * @return the eight
     */
    public T2SixNumber[] getEight() {

        return eight;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getID()
     */
    @Override
    public int getID() {

        return TYPE_RCURVELINE;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "rcurveline";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getValue()
     */
    @Override
    public Object getValue() {

        return eight;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#toText()
     */
    @Override
    public String toText() {

        StringBuffer buf = new StringBuffer();

        buf.append(dxd.toString()).append(" ");
        buf.append(dyd.toString()).append(" ");
        for (int i = 0; i < eight.length; i++) {
            buf.append(eight[i].toString()).append(" ");
        }
        buf.append(getName());
        return buf.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("dxd", dxd);
        writer.writeAttribute("dyd", dyd);
        for (int i = 0; i < eight.length; i++) {
            writer.writeStartElement("pair");
            writer.writeAttribute("id", i);
            writer.writeAttribute("value", eight[i].toString());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

}
