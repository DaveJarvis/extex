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

package org.extex.exbib.bst2groovy.data.types;

import java.io.IOException;
import java.io.Writer;

import org.extex.exbib.bst2groovy.data.GCode;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class GString implements GCode {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s
     * 
     * @return the translated string
     */
    public static String translate(String s) {

        return s.replace("\\", "\\\\").replace("\"", "\\\"")
            .replace("$", "\\$");
    }

    /**
     * The field <tt>value</tt> contains the value.
     */
    private String value;

    /**
     * Creates a new object.
     * 
     * @param value the value
     */
    public GString(String value) {

        super();
        this.value = value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#getType()
     */
    public GType getType() {

        return GType.STRING;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
     *      java.lang.String)
     */
    public void print(Writer writer, String prefix) throws IOException {

        writer.write("\"");
        writer.write(translate(value));
        writer.write("\"");
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "\"" + translate(value) + "\"";
    }

}
