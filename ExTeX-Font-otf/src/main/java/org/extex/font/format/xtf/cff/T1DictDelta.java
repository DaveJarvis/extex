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
 * Type 1 dict delta.
 * 
 * <p>
 * The length of array or delta types is determined by counting the operands
 * preceding the operator. The second and subsequent numbers in a delta are
 * encoded as the difference between successive values. For example, an array
 * a0, a1, ..., an would be encoded as: a0 (a1-a0) (a2-a1) ..., (an-a(n-1)).
 * </p>
 * <p>
 * For calculation, you must use a0 (a1+a0) (a2+a1) ..., (an+a(n-1))
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class T1DictDelta extends T1DictKey {

    /**
     * bytes
     */
    private short[] bytes;

    /**
     * value
     */
    private Integer[] value;

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @param id the operator-id for the value
     * @throws IOException if an IO.error occurs.
     */
    protected T1DictDelta(List<T2Number> stack, short[] id)
            throws IOException {

        super();
        if (stack.size() < 1) {
            throw new T2MissingNumberException();
        }

        bytes = convertStackaddID(stack, id);

        value = new Integer[stack.size()];
        // a0 (a1-a0) (a2-a1) ..., (an-a(n-1))
        int old = 0;
        for (int i = 0; i < stack.size(); i++) {
            T2Number number = stack.get(i);
            int act = number.getInteger();
            old = act + old;
            value[i] = new Integer(old);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2CharString#getBytes()
     */
    @Override
    public short[] getBytes() {

        return bytes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getValue()
     */
    @Override
    public Object getValue() {

        return value;
    }

    /**
     * Check, if the object is a array.
     * 
     * @return Returns <code>true</code>, if the object is a array.
     */
    @Override
    public boolean isArray() {

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < value.length; i++) {
            buf.append(value[i].toString()).append(" ");
        }
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
        for (int i = 0; i < value.length; i++) {
            writer.writeAttribute("value_" + i, value[i].toString());
        }
        writer.writeEndElement();

    }

}
