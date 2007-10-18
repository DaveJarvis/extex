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
 * vhcurveto: dy1 dx2 dy2 dx3 {dxa dxb dyb dyc dyd dxe dye dxf}* dyf? vhcurveto
 * (30) : {dya dxb dyb dxc dxd dxe dye dyf}+ dxf? vhcurveto (30).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2VhcurveTo extends T2PathConstruction {

    /**
     * The dx2.
     */
    private T2Number dx2;

    /**
     * The dx3.
     */
    private T2Number dx3;

    /**
     * The dy1.
     */
    private T2Number dy1;

    /**
     * The dy2.
     */
    private T2Number dy2;

    /**
     * The eight values array.
     */
    private T2EightNumber[] eight;

    /**
     * Create a new object.
     * 
     * @param ch The char string.
     * @param stack The stack.
     */
    public T2VhcurveTo(List<T2CharString> stack, CharString ch)
            throws IOException {

        super(stack, new short[]{T2VHCURVETO}, ch);

        int n = stack.size();
        try {

            type1 = true;
            if (n % 8 == 0 || n % 8 == 1) {
                type1 = false;
            }

            if (type1) {

                dy1 = (T2Number) stack.remove(0);
                dx2 = (T2Number) stack.remove(0);
                dy2 = (T2Number) stack.remove(0);
                dx3 = (T2Number) stack.remove(0);
            }

            n = stack.size();
            eight = new T2EightNumber[n / 8];

            for (int i = 0; i < n; i += 8) {
                T2Number v1 = (T2Number) stack.remove(0);
                T2Number v2 = (T2Number) stack.remove(0);
                T2Number v3 = (T2Number) stack.remove(0);
                T2Number v4 = (T2Number) stack.remove(0);
                T2Number v5 = (T2Number) stack.remove(0);
                T2Number v6 = (T2Number) stack.remove(0);
                T2Number v7 = (T2Number) stack.remove(0);
                T2Number v8 = (T2Number) stack.remove(0);
                T2EightNumber si =
                        new T2EightNumber(v1, v2, v3, v4, v5, v6, v7, v8);
                eight[i / 8] = si;

                if (stack.size() == 1) {
                    dxf = (T2Number) stack.remove(0);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // TODO mgn remove!
            System.out.println("XXX");
        }
    }

    /**
     * The dxf.
     */
    private T2Number dxf;

    /**
     * Type 1 command.
     */
    private boolean type1;

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
     * Getter for eight.
     * 
     * @return the eight
     */
    public T2EightNumber[] getEight() {

        return eight;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getID()
     */
    @Override
    public int getID() {

        return TYPE_VHCURVETO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getName()
     */
    @Override
    public String getName() {

        return "vhcurveto";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#getValue()
     */
    @Override
    public Object getValue() {

        return eight;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("dy1", dy1);
        writer.writeAttribute("dx2", dx2);
        writer.writeAttribute("dy2", dy2);
        writer.writeAttribute("dx3", dx3);
        writer.writeAttribute("dxf", dxf);
        if (eight != null) {
            for (int i = 0; i < eight.length; i++) {
                writer.writeStartElement("pair");
                writer.writeAttribute("id", i);
                writer.writeAttribute("value", eight[i].toString());
                writer.writeEndElement();
            }
        }
        writer.writeEndElement();
    }

    /**
     * Getter for dxf.
     * 
     * @return the dxf
     */
    public T2Number getDxf() {

        return dxf;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2Operator#toText()
     */
    @Override
    public String toText() {

        StringBuffer buf = new StringBuffer();

        if (type1) {
            buf.append(dy1.toString()).append(" ");
            buf.append(dx2.toString()).append(" ");
            buf.append(dy2.toString()).append(" ");
            buf.append(dx3.toString()).append(" ");
        }

        for (int i = 0; i < eight.length; i++) {
            buf.append(eight[i].toString()).append(" ");
        }

        if (dxf != null) {
            buf.append(dxf.toString()).append(" ");
        }

        buf.append(getName());
        return buf.toString();
    }

}
