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
 * Abstract class for all number-values.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public abstract class T2TDONumber extends T2TopDICTOperator {

    /**
     * bytes
     */
    private short[] bytes;

    /**
     * value
     */
    private T2Number value;

    /**
     * Creates a new object.
     */
    protected T2TDONumber() {

        value = new T2DummyNumber();
        bytes = value.getBytes();
    }

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @param id the operator-id for the value
     * @throws IOException if an IO-error occurs.
     */
    protected T2TDONumber(List<T2CharString> stack, short[] id)
            throws IOException {

        super();
        if (stack.size() < 1) {
            throw new T2MissingNumberException();
        }
        value = ((T2Number) stack.get(0));

        bytes = convertStackaddID(stack, id);

    }

    /**
     * @see org.extex.font.format.xtf.cff.T2CharString#getBytes()
     */
    public short[] getBytes() {

        return bytes;
    }

    /**
     * @see org.extex.font.type.ttf.cff.T2Number#getDouble()
     */
    public double getDouble() {

        return value.getDouble();
    }

    /**
     * @see org.extex.font.type.ttf.cff.T2Number#getInteger()
     */
    public int getInteger() {

        return value.getInteger();
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2Operator#getValue()
     */
    public Object getValue() {

        return value;
    }

    /**
     * Check, if the objekt is a double.
     * 
     * @return Returns <code>true</code>, if the object is a double.
     */
    public boolean isDouble() {

        return value.isDouble();
    }

    /**
     * Check, if the objekt is a integer.
     * 
     * @return Returns <code>true</code>, if the object is a integer.
     */
    public boolean isInteger() {

        return value.isInteger();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return value.toString();
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("value", value);
        writer.writeEndElement();

    }

}