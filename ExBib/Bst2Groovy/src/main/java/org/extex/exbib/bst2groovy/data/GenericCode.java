/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bst2groovy.data;

import java.io.IOException;
import java.io.Writer;

import org.extex.exbib.bst2groovy.data.types.ReturnType;

/**
 * This class is a base class which handles a broad range of possible code
 * variants.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class GenericCode implements GCode {

    /**
     * The field <tt>type</tt> contains the return type.
     */
    private ReturnType type;

    /**
     * The field <tt>name</tt> contains the name.
     */
    private String name;

    /**
     * The field <tt>args</tt> contains the arguments.
     */
    private GCode[] args;

    /**
     * The field <tt>entry</tt> contains the name of the entry or
     * <code>null</code>.
     */
    private String entry = null;

    /**
     * Creates a new object.
     * 
     * @param type the type
     * @param name the name
     * @param args the arguments
     */
    public GenericCode(ReturnType type, String name, GCode... args) {

        this.type = type;
        this.name = name;
        this.args = args;
    }

    /**
     * Creates a new object.
     * 
     * @param type the type
     * @param name the name
     * @param entry the entry or <code>null</code>
     * @param args the arguments
     */
    public GenericCode(ReturnType type, String name, String entry,
            GCode... args) {

        this(type, name, args);
        this.entry = entry;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#getType()
     */
    public ReturnType getType() {

        return type;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
     *      java.lang.String)
     */
    public void print(Writer writer, String prefix) throws IOException {

        if (type == ReturnType.VOID) {
            writer.write(prefix);
        }
        writer.write(name);
        writer.write('(');
        boolean first = true;
        if (entry != null) {
            writer.write(entry);
            first = false;
        }
        for (GCode arg : args) {
            if (first) {
                first = false;
            } else {
                writer.write(", ");
            }
            arg.print(writer, prefix);
        }
        writer.write(')');
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();
        buffer.append(name);
        buffer.append('(');
        boolean first = true;
        if (entry != null) {
            buffer.append(entry);
            first = false;
        }
        for (GCode arg : args) {
            if (first) {
                first = false;
            } else {
                buffer.append(", ");
            }
            buffer.append(arg.toString());
        }
        buffer.append(')');
        return buffer.toString();
    }

}
