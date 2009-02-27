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
 * hflex: dx1 dx2 dy2 dx3 dx4 dx5 dx6 hflex (12 34).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2HFlex extends T2PathConstruction {

    /**
     * The dx1.
     */
    private T2Number dx1;

    /**
     * The dx2.
     */
    private T2Number dx2;

    /**
     * The dx3.
     */
    private T2Number dx3;

    /**
     * The dx4.
     */
    private T2Number dx4;

    /**
     * The dx5.
     */
    private T2Number dx5;

    /**
     * The dx6.
     */
    private T2Number dx6;

    /**
     * The dy2.
     */
    private T2Number dy2;

    /**
     * Create a new object.
     * 
     * @param ch The char string.
     * @param stack The stack.
     * 
     * @throws IOException in case of an error
     */
    public T2HFlex(List<T2CharString> stack, CharString ch) throws IOException {

        super(stack, new short[]{ESCAPE_BYTE, T2HFLEX}, ch);

        int n = stack.size();

        if (n != 7) {
            throw new T2MissingNumberException();
        }

        dx1 = (T2Number) stack.remove(0);
        dx2 = (T2Number) stack.remove(0);
        dy2 = (T2Number) stack.remove(0);
        dx3 = (T2Number) stack.remove(0);
        dx4 = (T2Number) stack.remove(0);
        dx5 = (T2Number) stack.remove(0);
        dx6 = (T2Number) stack.remove(0);

    }

    /**
     * Getter for dx1.
     * 
     * @return the dx1
     */
    public T2Number getDx1() {

        return dx1;
    }

    /**
     * Getter for dx2.
     * 
     * @return the dx2
     */
    public T2Number getDx2() {

        return dx2;
    }

    /**
     * Getter for dx3.
     * 
     * @return the dx3
     */
    public T2Number getDx3() {

        return dx3;
    }

    /**
     * Getter for dx4.
     * 
     * @return the dx4
     */
    public T2Number getDx4() {

        return dx4;
    }

    /**
     * Getter for dx5.
     * 
     * @return the dx5
     */
    public T2Number getDx5() {

        return dx5;
    }

    /**
     * Getter for dx6.
     * 
     * @return the dx6
     */
    public T2Number getDx6() {

        return dx6;
    }

    /**
     * Getter for dy2.
     * 
     * @return the dy2
     */
    public T2Number getDy2() {

        return dy2;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getID()
     */
    @Override
    public int getID() {

        return TYPE_HFLEX;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "hflex";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getValue()
     */
    @Override
    public Object getValue() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("dx1", dx1);
        writer.writeAttribute("dx2", dx2);
        writer.writeAttribute("dy2", dy2);
        writer.writeAttribute("dx3", dx3);
        writer.writeAttribute("dx4", dx4);
        writer.writeAttribute("dx5", dx5);
        writer.writeAttribute("dx6", dx6);
        writer.writeEndElement();
    }

}
