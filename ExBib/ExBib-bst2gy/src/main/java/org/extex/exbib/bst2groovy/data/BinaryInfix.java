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

import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This is an abstract base class for binary infix operators.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class BinaryInfix extends GenericCode {

    /**
     * The field <tt>level</tt> contains the precedence.
     */
    private int level;

    /**
     * Creates a new object.
     * 
     * @param a the first argument
     * @param b the second argument
     * @param op the operator
     * @param level the precedence
     */
    public BinaryInfix(GCode a, GCode b, String op, int level) {

        super(ReturnType.INT, op, a, b);
        this.level = level;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
     *      java.lang.String)
     */
    @Override
    public void print(CodeWriter writer, String prefix) throws IOException {

        printSave(writer, prefix, getArg(1));
        writer.write(" ", getName(), " ");
        printSave(writer, prefix, getArg(0));
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
    private void printSave(CodeWriter writer, String prefix, GCode x)
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