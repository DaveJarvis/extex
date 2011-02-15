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
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.sexpression.Expr;
import org.extex.ocpware.type.OcpCode;

/**
 * This class represents the reference to a character in the matched sequence.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class Char extends ArithExpr implements Expr {

    /**
     * The field <tt>n</tt> contains the index of the reference.
     */
    private int n;

    /**
     * Creates a new object.
     * 
     * @param n the index of the reference
     */
    public Char(int n) {

        this.n = n;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.arith.ArithExpr#outExpr( CompilerState)
     */
    @Override
    void outExpr(CompilerState cs) throws IOException, ArgmentTooBigException {

        cs.putInstruction(OcpCode.OP_PUSH_CHAR, n);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.sexpression.Expr#outRight(
     *      org.extex.ocpware.compiler.parser.CompilerState, boolean)
     */
    public void outRight(CompilerState cs, boolean withOffset)
            throws IOException,
                ArgmentTooBigException {

        cs.putInstruction(withOffset
                ? OcpCode.OP_PBACK_CHAR
                : OcpCode.OP_RIGHT_CHAR, n);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "\\" + Integer.toString(n);
    }

}
