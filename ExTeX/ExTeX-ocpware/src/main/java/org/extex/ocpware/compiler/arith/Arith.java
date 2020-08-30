/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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
import org.extex.ocpware.type.OcpCode;

/**
 * This class represents an arithmetic expression as a whole.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Arith implements Expr {

    /**
     * The field {@code expr} contains the arithmetic expression.
     */
    private ArithExpr expr;

    /**
     * Creates a new object.
     * 
     * @param expr the arithmetic expression
     */
    public Arith(ArithExpr expr) {

        this.expr = expr;
    }

    /**
*      org.extex.ocpware.compiler.parser.CompilerState, boolean)
     */
    public void outRight(CompilerState cs, boolean withOffset)
            throws IOException,
                TableNotDefinedException,
                ArgmentTooBigException {

        expr.outExpr(cs);
        cs.putInstruction(withOffset
                ? OcpCode.OP_PBACK_OUTPUT
                : OcpCode.OP_RIGHT_OUTPUT);
    }

@Override
    public String toString() {

        return "#(" + expr.toString() + ")";
    }

}
