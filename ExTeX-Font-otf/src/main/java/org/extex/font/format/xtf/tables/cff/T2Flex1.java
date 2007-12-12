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
 * flex1: dx1 dy1 dx2 dy2 dx3 dy3 dx4 dy4 dx5 dy5 d6 flex1 (12 37).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2Flex1 extends T2PathConstruction {

    /**
     * The d6.
     */
    private T2Number d6;

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
     * The dy1.
     */
    private T2Number dy1;

    /**
     * The dy2.
     */
    private T2Number dy2;

    /**
     * The dy3.
     */
    private T2Number dy3;

    /**
     * The dy4.
     */
    private T2Number dy4;

    /**
     * The dy5.
     */
    private T2Number dy5;

    /**
     * Create a new object.
     * 
     * @param ch The char string.
     * @param stack The stack.
     */
    public T2Flex1(List<T2CharString> stack, CharString ch) throws IOException {

        super(stack, new short[]{ESCAPE_BYTE, T2FLEX1}, ch);

        int n = stack.size();

        if (n != 11) {
            throw new T2MissingNumberException();
        }

        dx1 = (T2Number) stack.remove(0);
        dy1 = (T2Number) stack.remove(0);
        dx2 = (T2Number) stack.remove(0);
        dy2 = (T2Number) stack.remove(0);
        dx3 = (T2Number) stack.remove(0);
        dy3 = (T2Number) stack.remove(0);
        dx4 = (T2Number) stack.remove(0);
        dy4 = (T2Number) stack.remove(0);
        dx5 = (T2Number) stack.remove(0);
        dy5 = (T2Number) stack.remove(0);
        d6 = (T2Number) stack.remove(0);

    }

    /**
     * Getter for d6.
     * 
     * @return the d6
     */
    public T2Number getD6() {

        return d6;
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
     * Getter for dy1.
     * 
     * @return the dy1
     */
    public T2Number getDy1() {

        return dy1;
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
     * Getter for dy3.
     * 
     * @return the dy3
     */
    public T2Number getDy3() {

        return dy3;
    }

    /**
     * Getter for dy4.
     * 
     * @return the dy4
     */
    public T2Number getDy4() {

        return dy4;
    }

    /**
     * Getter for dy5.
     * 
     * @return the dy5
     */
    public T2Number getDy5() {

        return dy5;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getID()
     */
    @Override
    public int getID() {

        return TYPE_FLEX1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "flex1";
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
        writer.writeAttribute("dy1", dy1);
        writer.writeAttribute("dx2", dx2);
        writer.writeAttribute("dy2", dy2);
        writer.writeAttribute("dx3", dx3);
        writer.writeAttribute("dy3", dy3);
        writer.writeAttribute("dx4", dx4);
        writer.writeAttribute("dy4", dy4);
        writer.writeAttribute("dx5", dx5);
        writer.writeAttribute("dy5", dy5);
        writer.writeAttribute("d6", d6);
        writer.writeEndElement();
    }

}
