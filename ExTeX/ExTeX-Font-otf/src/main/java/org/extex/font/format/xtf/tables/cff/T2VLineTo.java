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
 * vlineto: dy1 {dxa dyb}* vlineto (7) : {dya dxb}+ vlineto (7).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2VLineTo extends T2PathConstruction {

    /**
     * dy.
     */
    private T2Number dy;

    /**
     * The pair array.
     */
    private T2PairNumber[] pairs;

    /**
     * Create a new object.
     * 
     * @param ch The char string.
     * @param stack The stack.
     * 
     * @throws IOException in case of an error
     */
    public T2VLineTo(List<T2CharString> stack, CharString ch)
            throws IOException {

        super(stack, new short[]{T2VLINETO}, ch);

        int n = stack.size();

        if (n < 1) {
            throw new T2MissingNumberException();
        }

        if (n == 1) {
            // only dy
            dy = (T2Number) stack.get(0);
            pairs = new T2PairNumber[0];
        } else if (n % 2 == 0) {
            readPairs(stack);
        } else {
            dy = (T2Number) stack.get(0);
            stack.remove(0);
            readPairs(stack);
        }

    }

    /**
     * Getter for dy.
     * 
     * <p>
     * dy can be <code>null</code>!
     * </p>
     * 
     * @return the dy
     */
    public T2Number getDy() {

        return dy;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getID()
     */
    @Override
    public int getID() {

        return TYPE_VLINETO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "vlineto";
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
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getValue()
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
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#toText()
     */
    @Override
    public String toText() {

        StringBuffer buf = new StringBuffer();
        if (dy != null) {
            buf.append(dy.toString()).append(" ");
        }
        for (int i = 0; i < pairs.length; i++) {
            buf.append(pairs[i].toString()).append(" ");
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
        if (dy != null) {
            writer.writeAttribute("dy", dy.toString());
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
