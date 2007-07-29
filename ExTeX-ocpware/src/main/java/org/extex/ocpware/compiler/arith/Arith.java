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

package org.extex.ocpware.compiler.arith;

import java.io.IOException;

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.TableNotDefinedException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.sexpression.Expr;
import org.extex.ocpware.type.OcpProgram;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Arith implements Expr {

    /**
     * The field <tt>expr</tt> contains the arithmetic expression.
     */
    private ArithExpr expr;

    /**
     * Creates a new object.
     * 
     * @param expr the arithmetic expression
     */
    public Arith(ArithExpr expr) {

        super();
        this.expr = expr;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.sexpression.Expr#outRight(
     *      org.extex.ocpware.compiler.parser.CompilerState)
     */
    public void outRight(CompilerState cs)
            throws IOException,
                TableNotDefinedException,
                ArgmentTooBigException {

        expr.outExpr(cs);
        cs.putInstruction(OcpProgram.RIGHT_OUTPUT);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "#(" + expr.toString() + ")";
    }

}
