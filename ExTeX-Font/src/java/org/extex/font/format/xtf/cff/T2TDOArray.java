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
import java.util.List;

import org.extex.util.xml.XMLStreamWriter;

/**
 * Abstract class for all array-values.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public abstract class T2TDOArray extends T2TopDICTOperator {

    /**
     * bytes
     */
    private short[] bytes;

    /**
     * value
     */
    private T2Number[] value;

    /**
     * Create a new object.
     *
     * @param stack the stack
     * @param id    the operator-id for the value
     * @throws IOException if an IO.error occurs.
     */
    protected T2TDOArray(final List stack, final short[] id) throws IOException {

        super();
        if (stack.size() < 1) {
            throw new T2MissingNumberException();
        }

        bytes = convertStackaddID(stack, id);

        value = new T2Number[stack.size()];
        for (int i = 0; i < stack.size(); i++) {
            value[i] = (T2Number) stack.get(i);
        }
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2CharString#getBytes()
     */
    public short[] getBytes() {

        return bytes;
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2Operator#getValue()
     */
    public Object getValue() {

        return value;
    }

    /**
     * Check, if the objekt is a array.
     * @return Returns <code>true</code>, if the object is a array.
     */
    public boolean isArray() {

        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < value.length; i++) {
            buf.append(value[i].toString()).append(" ");
        }
        return buf.toString();
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(final XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("topdict");
        writer.writeAttribute("name", getName());
        for (int i = 0; i < value.length; i++) {
            writer.writeAttribute("value_" + i, value[i].toString());
        }
        writer.writeEndElement();

    }

}