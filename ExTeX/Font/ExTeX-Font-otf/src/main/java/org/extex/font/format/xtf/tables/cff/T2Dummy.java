/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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
 * T2 dummy.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class T2Dummy extends T2Operator {

    /**
     * The command.
     */
    private final String cmd;

    /**
     * The values of the stack.
     */
    private final T2CharString[] cs;

    /**
     * Create a new object.
     * 
     * @param ch The char string.
     * @param stack The stack.
     * @param cmd TODO
     * 
     * @throws IOException in case of an error
     */
    public T2Dummy(List<T2CharString> stack, CharString ch, String cmd)
            throws IOException {

        this.cmd = cmd;
        int n = stack.size();

        cs = new T2CharString[n];
        for (int i = 0; i < n; i++) {
            cs[i] = stack.get(i);
        }
    }

@Override
    public short[] getBytes() {

        return new short[0];
    }

@Override
    public int getID() {

        return -1;
    }

@Override
    public String getName() {

        return "dummy";
    }

@Override
    public Object getValue() {

        return cs;
    }

@Override
    public String toText() {

        return "dummy";
    }

    /**
*      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement(getName());
        writer.writeAttribute("name", cmd);
        for (int i = 0; i < cs.length; i++) {
            if (cs[i] instanceof T2Operator) {
                T2Operator t2 = (T2Operator) cs[i];
                t2.writeXML(writer);
            }
        }
        writer.writeEndElement();
    }
}
