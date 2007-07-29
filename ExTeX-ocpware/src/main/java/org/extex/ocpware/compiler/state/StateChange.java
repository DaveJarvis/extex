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

package org.extex.ocpware.compiler.state;

import java.io.IOException;

import org.extex.ocpware.compiler.exception.ArgmentTooBigException;
import org.extex.ocpware.compiler.exception.StateNotDefinedException;
import org.extex.ocpware.compiler.parser.CompilerState;
import org.extex.ocpware.type.OcpProgram;

/**
 * This state change instruction simply sets the current state to a new value.
 * In the input stream it is represented by a sequence of the following form:
 * <pre>
 *  &lt;&lang;state&rang;&gt;   </pre>
 * Here &lang;state&rang; denotes the symbolic name of a state.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class StateChange implements RightState {

    /**
     * The field <tt>state</tt> contains the state.
     */
    private String state;

    /**
     * Creates a new object.
     * 
     * @param state the state
     */
    public StateChange(String state) {

        super();
        this.state = state;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.state.RightState#compile(
     *      org.extex.ocpware.compiler.parser.CompilerState)
     */
    public void compile(CompilerState cs)
            throws IOException,
                StateNotDefinedException, ArgmentTooBigException {

        cs.putInstruction(OcpProgram.STATE_CHANGE, cs.lookupState(state));
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "<" + state + ">";
    }

}
