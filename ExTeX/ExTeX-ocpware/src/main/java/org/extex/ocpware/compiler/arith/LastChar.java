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
 * This class represents an arithmetic expression which references a character
 * in the prefix relative to last.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LastChar extends ArithExpr implements Expr {

    /**
     * The field {@code n} contains the offset of the reference.
     */
    private final int n;

    /**
     * Creates a new object.
     * 
     * @param n the number of positions from the right
     */
    public LastChar(int n) {

        this.n = n;
    }

@Override
    void outExpr(CompilerState cs) throws IOException, ArgmentTooBigException {

        cs.putInstruction(OcpCode.OP_PUSH_LCHAR, n);
    }

    /**
*      org.extex.ocpware.compiler.parser.CompilerState, boolean)
     */
    public void outRight(CompilerState cs, boolean withOffset)
            throws IOException,
                ArgmentTooBigException {

        cs.putInstruction(withOffset
                ? OcpCode.OP_PBACK_LCHAR
                : OcpCode.OP_RIGHT_LCHAR, n);
    }

@Override
    public String toString() {

        if (n == 0) {
            return "\\$";
        }
        return "\\($-" + n + ")";
    }

}
