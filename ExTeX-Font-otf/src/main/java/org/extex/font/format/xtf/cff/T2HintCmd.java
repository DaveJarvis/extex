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

import org.extex.font.format.xtf.cff.T2TDOCharStrings.CharString;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Abstract class for all hints commands.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class T2HintCmd extends T2Hints {

    /**
     * bytes
     */
    private short[] bytes;

    /**
     * The pair array.
     */
    private T2PairNumber[] pairs;

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @param id the operator-id for the value
     * @param ch The char string.
     */
    protected T2HintCmd(List<T2CharString> stack, short[] id, CharString ch)
            throws IOException {

        super();

        int n = stack.size();
        bytes = convertStackaddID(stack, id);

        if (n % 2 != 0) {
            checkWidth(stack, ch);
        }
        n = stack.size();
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
     * @see org.extex.font.format.xtf.cff.T2CharString#getBytes()
     */
    @Override
    public short[] getBytes() {

        return bytes;
    }

    @Override
    public Object getValue() {

        return pairs;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        for (int i = 0; i < pairs.length; i++) {
            writer.writeStartElement("pair");
            writer.writeAttribute("id", i);
            writer.writeAttribute("value", pairs[i].toString());
            writer.writeEndElement();
        }
        writer.writeEndElement();

    }

    /**
     * Count the hints in the command.
     * 
     * @return Returns the count of the hints.
     */
    public abstract int countHints();
}