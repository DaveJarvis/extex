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
 * Type 1 dict number.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class T1DictNumber extends T1DictKey {

    /**
     * Create a new object.
     *
     * @param stack the stack
     * @param id    the operator-id for the value
     * @throws IOException if an IO-error occurs.
     */
    protected T1DictNumber(List<T2Number> stack, short[] id)
            throws IOException {

        super();
        if (stack.size() < 1) {
            throw new T2MissingNumberException();
        }
        value = stack.get(0);

        bytes = convertStackaddID(stack, id);

    }

    /**
     * bytes
     */
    private short[] bytes;

    /**
     * value
     */
    private T2Number value;

    /**
     * {@inheritDoc}
     *
     * @see org.extex.font.format.xtf.cff.T2CharString#getBytes()
     */
    public short[] getBytes() {

        return bytes;
    }

    /**
     * Check, if the object is a integer.
     * @return Returns <code>true</code>, if the object is a integer.
     */
    public boolean isInteger() {

        return value.isInteger();
    }

    /**
     * Check, if the object is a double.
     * @return Returns <code>true</code>, if the object is a double.
     */
    public boolean isDouble() {

        return value.isDouble();
    }

    /**
     * TODO mgn
     * @return ...
     */
    public double getDouble() {

        return value.getDouble();
    }

    /**
     * TODO mgn
     * @return ...
     */
    public int getInteger() {

        return value.getInteger();
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return value.toString();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.font.format.xtf.cff.T1DictKey#getValue()
     */
    public Object getValue() {

        return value;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("value", value);
        writer.writeEndElement();

    }
}
