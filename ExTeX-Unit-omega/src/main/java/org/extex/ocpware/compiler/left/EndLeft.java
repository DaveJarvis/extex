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

package org.extex.ocpware.compiler.left;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.parser.State;
import org.extex.ocpware.type.OcpProgram;

/**
 * This class represents a reference to the end of the sequence.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class EndLeft implements Left {

    /**
     * Creates a new object.
     */
    public EndLeft() {

        super();
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
            throws IOException,
                ArgmentTooBigException {

        List<Integer> trueHoles = new ArrayList<Integer>();
        int ptr = state.putInstruction(OcpProgram.GOTO_END);
        trueHoles.add(Integer.valueOf(ptr - 1));
        ptr = state.putInstruction(OcpProgram.GOTO, 0);
        List<Integer> holes = new ArrayList<Integer>();
        holes.add(Integer.valueOf(ptr - 1));
        state.fillIn(trueHoles);
        return holes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "end:";
    }

}
