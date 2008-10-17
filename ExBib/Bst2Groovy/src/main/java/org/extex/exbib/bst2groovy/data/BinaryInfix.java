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

import org.extex.exbib.bst2groovy.data.types.GType;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class BinaryInfix implements GCode {

    /**
     * The field <tt>a</tt> contains the ...
     */
    private GCode a;

    /**
     * The field <tt>b</tt> contains the ...
     */
    private GCode b;

    /**
     * The field <tt>op</tt> contains the ...
     */
    private String op;

    /**
     * The field <tt>level</tt> contains the ...
     */
    private int level;

    /**
     * Creates a new object.
     * 
     * @param a
     * @param b
     * @param op
     * @param level
     */
    public BinaryInfix(GCode a, GCode b, String op, int level) {

        super();
        this.a = a;
        this.b = b;
        this.op = op;
        this.level = level;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#getType()
     */
    public GType getType() {

        return GType.INT;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
     *      java.lang.String)
     */
    public void print(Writer writer, String prefix) throws IOException {

        printSave(writer, prefix, b);
        writer.write(" ");
        writer.write(op);
        writer.write(" ");
        printSave(writer, prefix, a);
    }

    /**
     * Print code in infix notation and surround it by parentheses if necessary.
     * 
     * @param writer the target writer
     * @param prefix the prefix
     * @param x the code
     * 
     * @throws IOException in case of an I/O error
     */
    private void printSave(Writer writer, String prefix, GCode x)
            throws IOException {

        if (x instanceof BinaryInfix && ((BinaryInfix) x).level <= level) {
            writer.write("(");
            x.print(writer, prefix);
            writer.write(")");
        } else {
            x.print(writer, prefix);
        }
    }

}
