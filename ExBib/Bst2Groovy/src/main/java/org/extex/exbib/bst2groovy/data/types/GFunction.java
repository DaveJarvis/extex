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
import java.util.List;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.evaluator.GLocal;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class GFunction implements GCode {

    /**
     * The field <tt>name</tt> contains the ...
     */
    private String name;

    /**
     * The field <tt>code</tt> contains the ...
     */
    private GCode code;

    /**
     * The field <tt>type</tt> contains the return value.
     */
    private GCode type;

    /**
     * The field <tt>args</tt> contains the arguments.
     */
    private List<GLocal> args;

    /**
     * Creates a new object.
     * 
     * @param returnType the return type
     * @param name the name
     * @param args the list of arguments
     * @param code the body code
     */
    public GFunction(GCode returnType, String name, List<GLocal> args,
            GCode code) {

        super();
        this.type = returnType;
        this.name = name;
        this.args = args;
        this.code = code;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#getType()
     */
    public GType getType() {

        // TODO gene: getType unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
     *      java.lang.String)
     */
    public void print(Writer writer, String prefix) throws IOException {

        writer.write("\n");
        GType t = (type == null ? null : type.getType());
        writer.write(t == null ? "def" : t.toString());
        writer.write(" ");
        writer.write(name);
        writer.write("(");
        boolean first = true;
        for (GCode arg : args) {
            if (first) {
                first = false;
            } else {
                writer.write(", ");
            }
            arg.print(writer, prefix);
        }
        writer.write(") {");
        code.print(writer, "  ");

        if (type != null) {
            writer.write("\n");
            writer.write(prefix);
            writer.write("  return ");
            type.print(writer, prefix);
        }
        writer.write("\n}\n");
    }

}
