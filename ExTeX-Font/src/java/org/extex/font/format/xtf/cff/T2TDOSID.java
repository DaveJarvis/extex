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
 * Abstract class for all SID-values.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public abstract class T2TDOSID extends T2TopDICTOperator {

    /**
     * bytes
     */
    private short[] bytes;

    /**
     * value
     */
    private int value;

    /**
     * Create a new object.
     *
     * @param stack the stack
     * @param id    the operator-id for the value
     * @throws IOException if an IO-error occurs.
     */
    protected T2TDOSID(final List stack, final short[] id) throws IOException {

        super();

        if (stack.size() < 1) {
            throw new T2MissingNumberException();
        }
        value = ((T2Number) stack.get(0)).getInteger();

        bytes = convertStackaddID(stack, id);
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2CharString#getBytes()
     */
    public short[] getBytes() {

        return bytes;
    }

    /**
     * Returns the SID.
     * @return Returns the SID.
     */
    public int getSID() {

        return value;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return String.valueOf(value);
    }

    /**
     * @see org.extex.font.format.xtf.cff.T2Operator#getValue()
     */
    public Object getValue() {

        return cff.getString(getSID());
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(final XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("topdict");
        writer.writeAttribute("name", getName());
        writer.writeAttribute("value", getValue());
        writer.writeEndElement();

    }
}