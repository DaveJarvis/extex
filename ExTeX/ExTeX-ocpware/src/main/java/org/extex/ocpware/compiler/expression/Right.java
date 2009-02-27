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

package org.extex.ocpware.compiler.expression;

import java.io.IOException;
import java.util.List;

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.StateNotDefinedException;
import org.extex.ocpware.compiler.exception.TableNotDefinedException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.sexpression.Expr;

/**
 * This class represents a list of expressions.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Right {

    /**
     * The field <tt>right</tt> contains the list of expressions.
     */
    private List<Expr> right;

    /**
     * Creates a new object.
     * 
     * @param right the list of expressions
     */
    public Right(List<Expr> right) {

        super();
        this.right = right;
    }

    /**
     * Compile a right item into appropriate instructions.
     * 
     * @param cs the compiler state
     * @param withOffset use push back instead of output
     *
     * @throws ArgmentTooBigException in case that an argument is encountered
     *         which does not fit into two bytes
     * @throws IOException in case of an I/O error
     * @throws StateNotDefinedException
     * @throws TableNotDefinedException in case that no matching table is known
     *         for a symbolic table reference
     */
    public void compile(CompilerState cs, boolean withOffset)
            throws StateNotDefinedException,
                IOException,
                TableNotDefinedException,
                ArgmentTooBigException {

        for (Expr x : right) {
            x.outRight(cs, withOffset);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(" => ");
        for (Expr r : right) {
            sb.append(r.toString());
        }
        return sb.toString();
    }

}
