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
 * This class represents a left item with a lower bound of occurrences.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PlusLeft implements Left {

    /**
     * The field {@code from} contains the lower bound.
     */
    private int from;

    /**
     * The field {@code left} contains the left item.
     */
    private Left left;

    /**
     * Creates a new object.
     * 
     * @param left the left item
     * @param from the minimum
     */
    public PlusLeft(Left left, int from) {

        this.left = left;
        this.from = from;
    }

    /**
*      org.extex.ocpware.compiler.parser.State, CompilerState)
     */
    public List<Integer> genLeft(State state, CompilerState cs)
            throws IOException,
                ArgmentTooBigException,
                AliasNotDefinedException,
                IllegalOpcodeException {

        // false_holes=nil;
        // true_holes=nil;
        // backup_holes=nil;
        List<Integer> holes = new ArrayList<Integer>();
        List<Integer> trueHoles = new ArrayList<Integer>();

        for (int k = 1; k < from; k++) {
            holes.addAll(left.genLeft(state, cs));
            int ptr = state.putInstruction(OcpCode.OP_GOTO_NO_ADVANCE, 0);
            holes.add(Integer.valueOf(ptr - 1));
        }

        holes.addAll(left.genLeft(state, cs));
        int ptr = state.putInstruction(OcpCode.OP_GOTO_NO_ADVANCE, 0);
        int savePtr = ptr;
        trueHoles.add(Integer.valueOf(ptr - 1));
        List<Integer> backupHoles = left.genLeft(state, cs);
        state.putInstruction(OcpCode.OP_GOTO, savePtr - 1);
        state.fillIn(backupHoles);
        state.putInstruction(OcpCode.OP_LEFT_BACKUP, 0);
        state.fillIn(trueHoles);
        return holes;
    }

@Override
    public String toString() {

        StringBuilder sb = new StringBuilder(left.toString());
        sb.append("<");
        sb.append(from);
        sb.append(",>");
        return sb.toString();
    }

}
