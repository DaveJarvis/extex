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

/**
 * This class represents the binary addition operation of two arithmetic
 * expressions.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class BinaryOp extends ArithExpr {

    /**
     * The field <tt>left</tt> contains the left argument.
     */
    private ArithExpr left;

    /**
     * The field <tt>op</tt> contains the name of the operator
     */
    private String op;

    /**
     * The field <tt>opCode</tt> contains the op code of the associated
     * &Omega;CP instruction
     */
    private int opCode;

    /**
     * The field <tt>right</tt> contains the right argument.
     */
    private ArithExpr right;

    /**
     * The field <tt>needsParen</tt> contains the indicator for the precedence
     * of the operator.
     */
    private boolean needsParen = true;

    /**
     * Creates a new object.
     * 
     * @param opCode the op code
     * @param op the print presentation
     * @param left the left argument
     * @param right the right argument
     */
    public BinaryOp(int opCode, String op, ArithExpr left, ArithExpr right) {

        super();
        this.left = left;
        this.right = right;
        this.opCode = opCode;
        this.op = op;
    }

    /**
     * Creates a new object.
     * 
     * @param opCode the op code
     * @param op the print presentation
     * @param left the left argument
     * @param right the right argument
     * @param needsParen the indicator for the precedence of the operator
     */
    public BinaryOp(int opCode, String op, ArithExpr left, ArithExpr right,
            boolean needsParen) {

        this(opCode, op, left, right);
        this.needsParen = needsParen;
    }

    /**
     * Getter for left.
     * 
     * @return the left
     */
    public ArithExpr getLeft() {

        return left;
    }

    /**
     * Getter for op.
     * 
     * @return the op
     */
    public String getOp() {

        return op;
    }

    /**
     * Getter for right.
     * 
     * @return the right
     */
    public ArithExpr getRight() {

        return right;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.arith.ArithExpr#needsParen()
     */
    @Override
    public boolean needsParen() {

        return needsParen;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.arith.ArithExpr#outExpr(CompilerState)
     */
    @Override
    void outExpr(CompilerState cs)
            throws IOException,
                TableNotDefinedException,
                ArgmentTooBigException {

        left.outExpr(cs);
        right.outExpr(cs);
        cs.putInstruction(opCode);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append(left.toString());
        sb.append(op);
        sb.append(right.toString());
        return sb.toString();
    }

}
