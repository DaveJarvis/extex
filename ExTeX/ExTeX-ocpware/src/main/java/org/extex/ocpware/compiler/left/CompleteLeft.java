/*
 * Copyright (C) 2007�-2011 The ExTeX Group and individual authors listed below
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

package org.extex.ocpware.compiler.left;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.ocpware.compiler.exception.AliasNotDefinedException;
import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.IllegalOpcodeException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.parser.State;
import org.extex.ocpware.type.OcpCode;

/**
 * This class represents a left item with minimum and maximum restrictions.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class CompleteLeft implements Left {

    /**
     * The field {@code from} contains the lower bound.
     */
    private final int from;

    /**
     * The field {@code left} contains the left item to apply the constraints
     * to.
     */
    private final Left left;

    /**
     * The field {@code to} contains the upper bound.
     */
    private final int to;

    /**
     * Creates a new object.
     * 
     * @param left the left item
     * @param from the minimum
     * @param to the maximum
     */
    public CompleteLeft(Left left, int from, int to) {

        this.left = left;
        this.from = from;
        this.to = to;
    }

    /**
*      org.extex.ocpware.compiler.parser.State, CompilerState)
     */
    public List<Integer> genLeft(State state, CompilerState cs)
            throws IOException,
                ArgmentTooBigException,
        IllegalOpcodeException {

        List<Integer> holes = new ArrayList<Integer>();
        if (from > to) {
            return holes;
        }
        List<Integer> trueHoles = new ArrayList<Integer>();
        List<Integer> backupHoles = new ArrayList<Integer>();

        int k = 1;
        for (; k <= from; k++) {
            holes.addAll(left.genLeft(state, cs));
            int ptr = state.putInstruction(OcpCode.OP_GOTO_NO_ADVANCE, 0);
            holes.add(Integer.valueOf(ptr - 1));
        }
        for (; k <= to; k++) {
            trueHoles.addAll(left.genLeft(state, cs));
            int ptr = state.putInstruction(OcpCode.OP_GOTO_NO_ADVANCE, 0);
            backupHoles.add(Integer.valueOf(ptr - 1));
        }
        trueHoles.addAll(left.genLeft(state, cs));
        state.putInstruction(OcpCode.OP_GOTO, state.getPointer() + 2);
        state.fillIn(trueHoles);
        state.putInstruction(OcpCode.OP_LEFT_BACKUP, 0);
        state.fillIn(backupHoles);

        return holes;
    }

@Override
    public String toString() {

        StringBuilder sb = new StringBuilder(left.toString());
        sb.append("<");
        sb.append(from);
        sb.append(",");
        sb.append(to);
        sb.append(">");
        return sb.toString();
    }

}
