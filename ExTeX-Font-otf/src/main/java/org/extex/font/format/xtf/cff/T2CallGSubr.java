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
 * T2: callgsubr: globalsubr# callgsubr (29).
 * 
 * <p>
 * Operates in the same manner as callsubr except that it calls a global
 * subroutine.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2CallGSubr extends T2Subroutine {

    /**
     * subroutine index.
     */
    private T2Number subr;

    /**
     * Create a new object.
     * 
     * @param stack the stack
     * @param ch The char string.
     * @throws IOException if an IO-error occurs.
     */
    public T2CallGSubr(List<T2CharString> stack, CharString ch)
            throws IOException {

        super(stack, new short[]{T2CALLGSUBR}, ch);

        int n = stack.size();

        if (n != 1) {
            throw new T2MissingNumberException();
        }

        subr = (T2Number) stack.get(0);

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getID()
     */
    @Override
    public int getID() {

        return TYPE_CALLGSUBR;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "callgsubr";
    }

    /**
     * Getter for subr.
     * 
     * @return the subr
     */
    public T2Number getSubr() {

        return subr;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getValue()
     */
    @Override
    public Object getValue() {

        return subr;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("subr", subr.getInteger());
        writer.writeEndElement();

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#toText()
     */
    @Override
    public String toText() {

        StringBuffer buf = new StringBuffer();
        buf.append(subr.toString()).append(" ");
        buf.append(getName());
        return buf.toString();
    }

}
