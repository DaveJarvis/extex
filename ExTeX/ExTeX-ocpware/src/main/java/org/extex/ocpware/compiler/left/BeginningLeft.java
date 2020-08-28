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

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.compiler.parser.State;
import org.extex.ocpware.type.OcpCode;

/**
 * This class represents a reference to the beginning of the sequence.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class BeginningLeft implements Left {


    public BeginningLeft() {

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.left.Left#genLeft(org.extex.ocpware.compiler.parser.State,
     *      CompilerState)
     */
    @Override
    public List<Integer> genLeft(State state, CompilerState cs)
            throws IOException,
                ArgmentTooBigException {

        int ptr = state.putInstruction(OcpCode.OP_GOTO_BEG, 0);
        List<Integer> holes = new ArrayList<Integer>();
        holes.add(Integer.valueOf(ptr - 1));
        ptr = state.putInstruction(OcpCode.OP_GOTO, 0);
        List<Integer> falseHoles = new ArrayList<Integer>();
        falseHoles.add(Integer.valueOf(ptr - 1));
        state.fillIn(holes);
        return falseHoles;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "beg:";
    }

}
