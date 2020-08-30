/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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
*/
public abstract class T1DictDelta extends T1DictKey {

    /**
     * bytes
     */
    private final short[] bytes;

    /**
     * value
     */
    private final Integer[] value;

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @param id the operator-id for the value
     * @throws IOException if an IO.error occurs.
     */
    protected T1DictDelta(List<T2Number> stack, short[] id) throws IOException {

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

@Override
    public short[] getBytes() {

        return bytes;
    }

@Override
    public Object getValue() {

        return value;
    }

    /**
     * Check, if the object is a array.
     * 
     * @return Returns {@code true}, if the object is a array.
     */
    @Override
    public boolean isArray() {

        return true;
    }

@Override
    public String toString() {

        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            buf.append(value[i].toString()).append(" ");
        }
        return buf.toString();
    }

@Override
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        for (int i = 0; i < value.length; i++) {
            writer.writeAttribute("value_" + i, value[i].toString());
        }
        writer.writeEndElement();

    }

}
