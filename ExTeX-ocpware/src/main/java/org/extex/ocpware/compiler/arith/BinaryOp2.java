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

/**
 * This class represents the binary addition operation of two arithmetic
 * expressions.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BinaryOp2 extends BinaryOp {

    /**
     * Creates a new object.
     * 
     * @param opCode the op code
     * @param op the print presentation
     * @param left the left argument
     * @param right the right argument
     */
    public BinaryOp2(int opCode, String op, ArithExpr left, ArithExpr right) {

        super(opCode, op, left, right);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.ocpware.compiler.arith.BinaryOp#needsParen()
     */
    @Override
    public boolean needsParen() {

        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append(getLeft().toString());
        sb.append(getOp());
        sb.append(getRight().toString());
        return sb.toString();
    }

}
