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

package org.extex.exbib.bst2groovy.data.builtin;

import java.io.IOException;
import java.io.Writer;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.types.GString;
import org.extex.exbib.bst2groovy.data.types.GType;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SetField implements GCode {

    /**
     * The field <tt>name</tt> contains the name of the field.
     */
    private String name;

    /**
     * The field <tt>value</tt> contains the the new value.
     */
    private GCode value;

    /**
     * The field <tt>entry</tt> contains name of the entry variable.
     */
    private String entry;

    /**
     * Creates a new object.
     * 
     * @param entry the name of the variable for the entry
     * @param name the name of the filed
     * @param value the new value
     */
    public SetField(String entry, String name, GCode value) {

        super();
        this.entry = entry;
        this.name = name;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#getType()
     */
    public GType getType() {

        return GType.VOID;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
     *      java.lang.String)
     */
    public void print(Writer writer, String prefix) throws IOException {

        writer.write("\n");
        writer.write(prefix);
        writer.write(entry);
        writer.write(".set(");
        writer.write(GString.translate(name));
        writer.write(", ");
        value.print(writer, prefix);
        writer.write(")");
    }
}
