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
 * This class represents a string of characters as left item.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class StringLeft implements Left {

    /**
     * The field <tt>s</tt> contains the string.
     */
    private String s;

    /**
     * Creates a new object.
     * 
     * @param s the string
     */
    public StringLeft(String s) {

        super();
        this.s = s;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ocpware.compiler.left.Left#genLeft(State, CompilerState)
     */
    public List<Integer> genLeft(State state, CompilerState cs)
            throws IOException,
                ArgmentTooBigException {

        List<Integer> holes = new ArrayList<Integer>();
        int length = s.length();
        char c = s.charAt(0);
        for (int i = 0; i < length;) {

            int ptr = state.putInstruction(OcpProgram.GOTO_NE, c, 0);
            holes.add(Integer.valueOf(ptr - 1));

            if (++i < length) {
                c = s.charAt(i);
                ptr = state.putInstruction(OcpProgram.GOTO_NO_ADVANCE);
                holes.add(Integer.valueOf(ptr - 1));
            }
        }

        return holes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "\"" + s + "\"";
    }

}
