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
import java.util.ArrayList;
import java.util.List;

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.IllegalOpcodeException;
import org.extex.ocpware.compiler.left.Left;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.parser.State;
import org.extex.ocpware.compiler.sexpression.Expr;
import org.extex.ocpware.type.OcpProgram;

/**
 * This class provides a constant arithmetic expression. It holds a number.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Constant extends ArithExpr implements Expr, Left {

    /**
     * The field <tt>n</tt> contains the value.
     */
    private int n;

    /**
     * Creates a new object.
     * 
     * @param n the value
     */
    public Constant(int n) {

        super();
        this.n = n;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.left.Left#compile(
     *      org.extex.ocpware.compiler.parser.CompilerState)
     */
    public void compile(CompilerState cs) {

        // TODO gene: compile unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.left.Left#genLeft(
     *      org.extex.ocpware.compiler.parser.State, CompilerState)
     */
    public List<Integer> genLeft(State state, CompilerState cs)
            throws ArgmentTooBigException,
                IOException,
                IllegalOpcodeException {

        int ptr = state.putInstruction(OcpProgram.GOTO_NE, n, 0, 0);
        List<Integer> holes = new ArrayList<Integer>();
        holes.add(Integer.valueOf(ptr - 1));
        return holes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.arith.ArithExpr#outExpr(
     *      org.extex.ocpware.compiler.parser.CompilerState)
     */
    @Override
    void outExpr(CompilerState cs) throws IOException, ArgmentTooBigException {

        cs.putInstruction(OcpProgram.PUSH_NUM, n);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.sexpression.Expr#outRight(
     *      org.extex.ocpware.compiler.parser.CompilerState)
     */
    public void outRight(CompilerState cs)
            throws IOException,
                ArgmentTooBigException {

        cs.putInstruction(OcpProgram.RIGHT_NUM, n);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        if (n >= ' ' && n < 126) {
            return "`" + Character.toString((char) n) + "'";
        }
        return "@\"" + Integer.toHexString(n);
    }

}
