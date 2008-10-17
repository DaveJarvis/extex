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

package org.extex.exbib.bst2groovy.evaluator;

import java.io.IOException;
import java.io.Writer;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.types.GType;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class GLocal implements GCode {

    /**
     * The field <tt>name</tt> contains the name.
     */
    private String name;

    /**
     * The field <tt>value</tt> contains the vale to determine the type.
     */
    private GCode value = null;

    /**
     * The field <tt>reference</tt> contains the ...
     */
    private GLocal reference = null;

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public GLocal(String name) {

        this.name = name;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#getType()
     */
    public GType getType() {

        if (reference != null) {
            return reference.getType();
        }
        return value == null ? null : value.getType();
    }

    /**
     * Getter for the value.
     * 
     * @return the value
     */
    public GCode getValue() {

        return (reference != null ? reference.getValue() : value);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
     *      java.lang.String)
     */
    public void print(Writer writer, String prefix) throws IOException {

        if (reference != null) {
            reference.print(writer, prefix);
        } else {
            writer.write(name);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param writer
     * @param prefix
     * @throws IOException
     */
    public void printType(Writer writer, String prefix) throws IOException {

        if (reference != null) {
            reference.printType(writer, prefix);
        } else {
            if (value != null) {
                String t = value.getType().toString();
                if (t != null) {
                    writer.write(t);
                    return;
                }
            }
            writer.write("var");
        }
    }

    /**
     * Setter for the value.
     * 
     * @param value the value to set
     */
    public void setValue(GCode value) {

        if (reference != null) {
            reference.setValue(value);
        } else {
            this.value = value;
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param other
     */
    public void unify(GLocal other) {

        reference = other;
    }

}
