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
 * This class represents the binary multiplication operation of two arithmetic
 * expressions.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Times extends ArithExpr {

    /**
     * The field <tt>left</tt> contains the left argument.
     */
    private ArithExpr left;

    /**
     * The field <tt>right</tt> contains the right argument.
     */
    private ArithExpr right;

    /**
     * Creates a new object.
     * 
     * @param left the left argument 
     * @param right the right argument
     */
    public Times(ArithExpr left, ArithExpr right) {

        super();
        this.left = left;
        this.right = right;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.arith.ArithExpr#eval()
     */
    @Override
    public int eval() {

        return left.eval() * right.eval();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return ArithExpr.toString(left, " div: ", right);
    }

}
