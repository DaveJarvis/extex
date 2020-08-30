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
 * endchar: endchar (14).
 * 
 * <p>
 * Finishes a charstring outline definition, and must be the last operator in a
 * character's outline.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class T2EndChar extends T2Operator {

    /**
     * bytes
     */
    private final short[] bytes;

    /**
     * The width (optional).
     */
    private T2Number width = null;

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @param ch The char string.
     * @throws IOException if an IO-error occurs.
     */
    public T2EndChar(List<T2CharString> stack, CharString ch)
            throws IOException {

        bytes = convertStackaddID(stack, new short[]{T2ENDCHAR});

        int n = stack.size();

        if (n > 0) {
            width = checkWidth(stack, ch);
        }

    }

@Override
    public short[] getBytes() {

        return bytes;
    }

@Override
    public int getID() {

        return TYPE_ENDCHAR;
    }

@Override
    public String getName() {

        return "endchar";
    }

@Override
    public Object getValue() {

        return null;
    }

@Override
    public String toText() {

        StringBuilder buf = new StringBuilder();
        if (width != null) {
            buf.append(width.toString()).append(' ');
        }
        return buf.append(getName()).toString();
    }

@Override
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeEndElement();
    }

}
